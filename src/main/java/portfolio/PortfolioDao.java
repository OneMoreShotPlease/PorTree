package portfolio;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class PortfolioDao {

	private JdbcTemplate jdbcTemplate;
	private RowMapper<Portfolio> portfolioRowMapper = new RowMapper<Portfolio>() {
		@Override
		public Portfolio mapRow(ResultSet rs, int rowNum) throws SQLException {
			Portfolio portfolio = new Portfolio(
					rs.getLong("USER_ID"),
					rs.getString("TITLE"),
					rs.getTimestamp("PUBLISH_DATE").toLocalDateTime(),
					rs.getString("PICTURE"),
					rs.getString("DESCRIPTION")
			);
			portfolio.setPortfolio_id(rs.getLong("PORTFOLIO_ID"));
			return portfolio;
		}	
	};	
	
	public PortfolioDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public void insert(Portfolio portfolio) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement psmt = con.prepareStatement(
						"insert into portfolio (user_id, title, publish_date, picture, description) " +
						"values (?, ?, ?, ?, ?)",
						new String[] {"PORTFOLIO_ID"}
				);
				psmt.setLong(1,  portfolio.getUser_id());
				psmt.setString(2,  portfolio.getTitle());
				psmt.setTimestamp(3, Timestamp.valueOf(portfolio.getPublish_date()));
				psmt.setString(4, portfolio.getPicture());
				psmt.setString(5, portfolio.getDescription());
				
				return psmt;
			}
		}, keyHolder);
		Number keyValue = keyHolder.getKey();
		portfolio.setPortfolio_id(keyValue.longValue());
	}
	
	public List<Portfolio> selectAll() {
		List<Portfolio> results = jdbcTemplate.query(
				"select * from PORTFOLIO",
				portfolioRowMapper);
		return results;
	}
	
	public Portfolio selectById(Long id) {
		List<Portfolio> results = jdbcTemplate.query(
				"select * from PORTFOLIO where portfolio_id =?", 
				portfolioRowMapper, id);
		return results.isEmpty()?null:results.get(0);
	}
}
