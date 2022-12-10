package authentication;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class SimpleUserDao {
	
	private JdbcTemplate jdbcTemplate;
	
	private RowMapper<SimpleUser> simpleUserRowMapper = 
			new RowMapper<SimpleUser>() {
				@Override
				public SimpleUser mapRow(ResultSet rs, int rowNum) throws SQLException {
					SimpleUser simpleUser = new SimpleUser(
							rs.getString("NAME"),
							rs.getString("PICTURE"));
					simpleUser.setId(rs.getLong("USER_ID"));
					return simpleUser;
				}		
	};
	
	public SimpleUserDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public SimpleUser selectById(Long user_id) {
		List<SimpleUser> results = jdbcTemplate.query(
				"SELECT * from `USER` where USER_ID = ?",
				simpleUserRowMapper, user_id);
		return results.isEmpty() ? null : results.get(0);
	}
}
