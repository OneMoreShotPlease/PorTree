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
					rs.getString("GITHUB"),
					rs.getString("DEMO"),
					rs.getString("CATEGORY"),
					rs.getString("DESCRIPTION")
			);
			portfolio.setPortfolio_id(rs.getLong("PORTFOLIO_ID"));
			return portfolio;
		}	
	};	
	
	public PortfolioDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	// 모든 portfolio 정보 불러오기
	public List<Portfolio> selectAll() {
		List<Portfolio> results = jdbcTemplate.query(
				"select * from PORTFOLIO",
				portfolioRowMapper);
		return results;
	}
	
	// portfolio_id로 특정 portfolio 정보 불러오기
	public Portfolio selectById(Long id) {
		List<Portfolio> results = jdbcTemplate.query(
				"select * from PORTFOLIO where PORTFOLIO_ID = ?", 
				portfolioRowMapper, id);
		return results.isEmpty()?null:results.get(0);
	}

	// Title, Description으로 특정 portfolio 정보 불러오기
	public List<Portfolio> selectBySearch(String search) {
		if (search.isEmpty())
			return null;
		String sql = "select * from PORTFOLIO where TITLE LIKE '%" + search + "%' or DESCRIPTION LIKE '%" + search + "%'";
		List<Portfolio> results = jdbcTemplate.query(
				sql, portfolioRowMapper);
		return results.isEmpty()?null:results;
	}
	
	public void update(Portfolio portfolio) {
		jdbcTemplate.update(
				"update PORTFOLIO set TITLE = ?, PUBLISH_DATE = ?, GITHUB = ?, DEMO = ?, CATEGORY = ?, DESCRIPTION = ? where PORTFOLIO_ID = ?",
				portfolio.getTitle(), Timestamp.valueOf(portfolio.getPublish_date()), portfolio.getGithub(), portfolio.getDemo(), portfolio.getCategory(), portfolio.getDescription(), portfolio.getPortfolio_id());
	}
	
	public void insert(Portfolio portfolio) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement psmt = con.prepareStatement(
						"insert into PORTFOLIO (USER_ID, TITLE, PUBLISH_DATE, GITHUB, DEMO, CATEGORY, DESCRIPTION) " +
						"values (?, ?, ?, ?, ?, ?, ?)",
						new String[] {"PORTFOLIO_ID"}
				);
				psmt.setLong(1,  portfolio.getUser_id());
				psmt.setString(2,  portfolio.getTitle());
				psmt.setTimestamp(3, Timestamp.valueOf(portfolio.getPublish_date()));
				psmt.setString(4, portfolio.getGithub());
				psmt.setString(5, portfolio.getDemo());
				psmt.setString(6, portfolio.getCategory());
				psmt.setString(7, portfolio.getDescription());
				
				return psmt;
			}
		}, keyHolder);
		Number keyValue = keyHolder.getKey();
		portfolio.setPortfolio_id(keyValue.longValue());
	}
	
	public void delete(Long id) {
		jdbcTemplate.update(
				"delete from PORTFOLIO where PORTFOLIO_ID = ?", id);
	}
}
