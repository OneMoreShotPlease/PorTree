package portfolio;

import java.time.LocalDateTime;

public class PortfolioRegisterService {
	private PortfolioDao portfolioDao;
	
	public PortfolioRegisterService(PortfolioDao portfolioDao) {
		this.portfolioDao = portfolioDao;
	}
	
	public Long regist(Long user_id, PortfolioRequest req) {
		Portfolio portfolio = new Portfolio(
				user_id, req.getTitle(), LocalDateTime.now(), req.getPicture(), req.getDescription());
		portfolioDao.insert(portfolio);
		return portfolio.getPortfolio_id();
	}

}
