package config;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import like.LikeDao;
import like.LikeRegisterService;

@Configuration
public class LikeConfig extends UserConfig {
	ApplicationContext ac = new AnnotationConfigApplicationContext(DatabaseConfig.class);
	
	@Bean
	public LikeDao likeDao() {
		return new LikeDao(ac.getBean(DataSource.class), simpleUserDao());
	}
	
	@Bean
	public LikeRegisterService likeRegSvc() {
		return new LikeRegisterService(likeDao());
	}
}
