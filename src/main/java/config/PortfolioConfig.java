package config;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import portfolio.PortfolioDao;
import portfolio.PortfolioRegisterService;

@Configuration
public class PortfolioConfig extends UserConfig {
	ApplicationContext ac = new AnnotationConfigApplicationContext(DatabaseConfig.class);
	
	@Bean
	public PortfolioDao portfolioDao() {
		return new PortfolioDao(ac.getBean(DataSource.class), simpleUserDao());
	}
	
	@Bean
	public PortfolioRegisterService portfolioReqSvc() {
		return new PortfolioRegisterService(portfolioDao());
	}
	
}