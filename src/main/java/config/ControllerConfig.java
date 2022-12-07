package config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import authentication.UserDao;
import authentication.UserRegisterService;
import authentication.UserAuthService;
import authentication.JwtManager;
import portfolio.PortfolioDao;
import portfolio.PortfolioRegisterService;
import controller.RestPortfolioController;
import controller.RestJwtController;
import controller.RestPortfolioAuthController;
import controller.RestUserController;

@Configuration
public class ControllerConfig {
	
	@Autowired
	private UserDao userDao;
	@Autowired
	private UserRegisterService userRegSvc;
	@Autowired
	private UserAuthService userAuthSvc;
	@Autowired
	private PortfolioDao portfolioDao;
	@Autowired
	private PortfolioRegisterService portfolioRegSvc;
	@Autowired
	private JwtManager jwtManager;

	@Bean
	public RestUserController restAPI() {
		RestUserController cont = new RestUserController();
		cont.setUserDao(userDao);
		cont.setRegisterService(userRegSvc);
		cont.setAuthService(userAuthSvc);
		cont.setJwtManager(jwtManager);
		return cont;
	}
	
	@Bean
	public RestPortfolioController restAPI2() {
		RestPortfolioController cont = new RestPortfolioController();
		cont.setPortfolioDao(portfolioDao);
		return cont;
	}
	
	
	@Bean
	public RestPortfolioAuthController authRestAPI2() {
		RestPortfolioAuthController cont = new RestPortfolioAuthController();
		cont.setPortfolioDao(portfolioDao);
		cont.setRegisterService(portfolioRegSvc);
		return cont;
	}
	
	@Bean
	public RestJwtController jwtRestAPI() {
		RestJwtController cont = new RestJwtController();
		cont.setJwtManager(jwtManager);
		cont.setUserDao(userDao);
		return cont;
	}
}
