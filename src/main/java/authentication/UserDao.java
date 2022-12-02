package authentication;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;

public class UserDao {
	
	private JdbcTemplate jdbcTemplate;
	private RowMapper<User> userRowMapper = 
			new RowMapper<User>() {
				@Override
				public User mapRow(ResultSet rs, int rowNum) throws SQLException {
					User user = new User(
							rs.getString("EMAIL"),
							rs.getString("PASSWORD"),
							rs.getString("REFRESH_TOKEN"),
							rs.getString("NAME"),
							rs.getString("GITHUB"),
							rs.getString("FIELD"),
							rs.getString("PICTURE"));
					user.setId(rs.getLong("USER_ID"));
					return user;
				}		
	};
	public UserDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public User selectByEmail(String email) {
		List<User> results = jdbcTemplate.query(
				"SELECT * from `USER` where EMAIL = ?",
				userRowMapper,
				email);
		return results.isEmpty() ? null : results.get(0);
	}
	
	public void insert(User user) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pstmt = con.prepareStatement(
						"INSERT into `USER` (EMAIL, PASSWORD, NAME, GITHUB, FIELD, PICTURE) " +
						"values (?, ?, ?, ?, ?, ?)",
						new String[] {"USER_ID"});
				pstmt.setString(1, user.getEmail());
				pstmt.setString(2, user.getPassword());
				pstmt.setString(3, user.getName());
				pstmt.setString(4, user.getGithub());
				pstmt.setString(5, user.getField());
				pstmt.setString(6, user.getPicture());
				
				return pstmt;
			}
		
			
		}, keyHolder);
		Number keyValue = keyHolder.getKey(); // ID 값 넣어주기
		user.setId(keyValue.longValue());
	}
	
	// JWT Refresh Token Update
	public void updateRefreshToken(User user, String refresh_token) {
		jdbcTemplate.update("update `USER` set REFRESH_TOKEN = ? where USER_ID = ?",
				refresh_token, user.getId());
	}
	
	public User selectById(Long user_id) {
		List<User> results = jdbcTemplate.query(
				"SELECT * from `USER` where USER_ID = ?",
				userRowMapper, user_id);
		return results.isEmpty() ? null : results.get(0);
	}
}
