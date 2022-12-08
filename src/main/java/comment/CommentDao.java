package comment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class CommentDao {

	private JdbcTemplate jdbcTemplate;
	private RowMapper<Comment> commentRowMapper = new RowMapper<Comment>() {

		@Override
		public Comment mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			Comment comment = new Comment(
					rs.getLong("PORTFOLIO_ID"),
					rs.getLong("USER_ID"),
					rs.getTimestamp("PUBLISH_DATE").toLocalDateTime(),
					rs.getString("CONTENTS")
			);
			comment.setComment_id(rs.getLong("COMMENT_ID"));
			return comment;
		}
		
	};
	
	public CommentDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	// 특정 댓글 불러오기
	public Comment selectById(Long comment_id) {
		List<Comment> results = jdbcTemplate.query(
				"select * from COMMENT where COMMENT_ID = ?",
				commentRowMapper, comment_id
		);
		return results.isEmpty() ? null:results.get(0);
	}
	// 특정 portfolio의 댓글 불러오기
	public List<Comment> selectByPortfolio(Long portfolio_id) {
		List<Comment> results = jdbcTemplate.query(
				"select * from COMMENT where PORTFOLIO_ID = ?", 
				commentRowMapper, portfolio_id);
		return results.isEmpty() ? null:results;
	}
	
	// 댓글 생성
	public void insert(Comment comment) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				// TODO Auto-generated method stub
				String query = "insert into COMMENT (PORTFOLIO_ID, USER_ID, PUBLISH_DATE, CONTENTS) values (?, ?, ?, ?)";
				PreparedStatement psmt = con.prepareStatement(query, new String[] {"COMMENT_ID"});
				psmt.setLong(1, comment.getPortfolio_id());
				psmt.setLong(2, comment.getUser_id());
				psmt.setTimestamp(3, Timestamp.valueOf(comment.getPublish_date()));
				psmt.setString(4, comment.getContents());
				
				return psmt;
			}		
		}, keyHolder);
		Number keyValue = keyHolder.getKey();
		comment.setComment_id(keyValue.longValue());
	}
	
	// 댓글 수정
	public void update(Comment comment) {
		jdbcTemplate.update(
				"update COMMENT set PUBLISH_DATE = ?, CONTENTS = ? where COMMENT_ID = ?",
				Timestamp.valueOf(comment.getPublish_date()), comment.getContents(), comment.getComment_id()
		);
	}
	
	// 댓글 삭제
	public void delete(Long id) {
		jdbcTemplate.update(
				"delete from COMMENT where COMMENT_ID = ?", id
		);
	}
}
