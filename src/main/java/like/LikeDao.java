package like;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import authentication.User;
import authentication.UserDao;

public class LikeDao {

	private JdbcTemplate jdbcTemplate;
	private UserDao userDao;
	private RowMapper<Like> likeRowMapper = new RowMapper<Like>() {

		@Override
		public Like mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			Like like = new Like(
					rs.getLong("PORTFOLIO_ID"),
					rs.getLong("USER_ID")
			);
			like.setLike_id(rs.getLong("LIKE_ID"));
			return like;
		}
		
	};
	
	public LikeDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	// 해당 사용자 + 게시글의 좋아요 존재 여부 확인
	public Like checkExist(Long portfolio_id, Long user_id) {
		List<Like> results = jdbcTemplate.query(
				"select * from `LIKE` where PORTFOLIO_ID = ? and USER_ID = ?",
				likeRowMapper, portfolio_id, user_id
		);
		return results.isEmpty() ? null:results.get(0);
	}
	// 좋아요 등록
	public void insert(Like like) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				// TODO Auto-generated method stub
				PreparedStatement psmt = con.prepareStatement(
						"insert into `LIKE` (PORTFOLIO_ID, USER_ID) values (?, ?)",
						new String[] {"LIKE_ID"});
				psmt.setLong(1, like.getPortfolio_id());
				psmt.setLong(2, like.getUser_id());
				return psmt;
			}
		}, keyHolder);
		Number keyValue = keyHolder.getKey();
		like.setLike_id(keyValue.longValue());
	}
	// 좋아요 삭제
	public void delete(Like like) {
		jdbcTemplate.update("delete from `LIKE` where LIKE_ID = ?", like.getLike_id());
	}
	
	// 특정 portfolio의 좋아요 사용자 불러오기
	public List<Integer> selectByPortfolio(Long portfolio_id) {
		List<Map<String, Object>> like_results = jdbcTemplate.queryForList("select USER_ID from `LIKE` where PORTFOLIO_ID = ?", portfolio_id);
		List<Integer> user_list = new ArrayList();
		for (int i = 0; i < like_results.size(); i++) {
			user_list.add((Integer)like_results.get(i).get("USER_ID"));
		}
		return user_list.isEmpty() ? null:user_list;
	}
}
