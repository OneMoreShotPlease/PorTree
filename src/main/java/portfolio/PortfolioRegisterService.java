package portfolio;

import java.time.LocalDateTime;

import authentication.SimpleUser;

public class PortfolioRegisterService {
	private PortfolioDao portfolioDao;
	
	public PortfolioRegisterService(PortfolioDao portfolioDao) {
		this.portfolioDao = portfolioDao;
	}
	
	public Long regist(SimpleUser user, PortfolioRequest req) {
		Portfolio portfolio = new Portfolio(
				user, req.getTitle(), LocalDateTime.now(), req.getGithub(), req.getDemo(), req.getCategory(), req.getDescription());
		portfolioDao.insert(portfolio);
		return portfolio.getPortfolio_id();
	}
	
	public Long update(SimpleUser user, Long portfolio_id, PortfolioRequest req) {
		Portfolio portfolio = new Portfolio(
				user, req.getTitle(), LocalDateTime.now(), req.getGithub(), req.getDemo(), req.getCategory(), req.getDescription());
		portfolio.setPortfolio_id(portfolio_id);
		portfolioDao.update(portfolio);
		return portfolio.getPortfolio_id();
	}
	
	public void delete(Long portfolio_id) {
		portfolioDao.delete(portfolio_id);
	}

}
