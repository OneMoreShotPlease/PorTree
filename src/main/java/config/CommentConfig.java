package config;

import org.apache.tomcat.jdbc.pool.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import authentication.SimpleUserDao;
import comment.CommentDao;
import comment.CommentRegisterService;

@Configuration
public class CommentConfig extends UserConfig {
	ApplicationContext ac = new AnnotationConfigApplicationContext(DatabaseConfig.class);
	
	@Bean
	public CommentDao commentDao() {
		return new CommentDao(ac.getBean(DataSource.class), simpleUserDao());
	}
	
	@Bean
	public CommentRegisterService commentRegSvc() {
		return new CommentRegisterService(commentDao());
	}
}
