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

import authentication.SimpleUser;
import authentication.SimpleUserDao;

public class LikeDao {

	private JdbcTemplate jdbcTemplate;
	private SimpleUserDao simpleUserDao;
	private RowMapper<Like> likeRowMapper = new RowMapper<Like>() {

		@Override
		public Like mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			SimpleUser user = simpleUserDao.selectById(rs.getLong("USER_ID"));
			Like like = new Like(
					rs.getLong("PORTFOLIO_ID"),
					user
			);
			like.setLike_id(rs.getLong("LIKE_ID"));
			return like;
		}
		
	};
	
	public LikeDao(DataSource dataSource, SimpleUserDao simpleUserDao) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.simpleUserDao = simpleUserDao;
	}
	
	// 해당 사용자 + 게시글의 좋아요 존재 여부 확인
	public Like checkExist(Long portfolio_id, SimpleUser user) {
		List<Like> results = jdbcTemplate.query(
				"select * from `LIKE` where PORTFOLIO_ID = ? and USER_ID = ?",
				likeRowMapper, portfolio_id, user.getId()
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
				psmt.setLong(2, like.getUser().getId());
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
	public List<SimpleUser> selectByPortfolio(Long portfolio_id) {
		List<Map<String, Object>> like_results = jdbcTemplate.queryForList("select USER_ID from `LIKE` where PORTFOLIO_ID = ?", portfolio_id);
		List<SimpleUser> user_list = new ArrayList();
		for (int i = 0; i < like_results.size(); i++) {
			Integer user_id = (Integer) like_results.get(i).get("USER_ID");
			Long long_user_id = new Long(user_id);
			user_list.add(simpleUserDao.selectById(long_user_id));
		}
		return user_list.isEmpty() ? null:user_list;
	}
}
