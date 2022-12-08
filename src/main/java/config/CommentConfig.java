package config;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import comment.CommentDao;
import comment.CommentRegisterService;

@Configuration
public class CommentConfig {
	ApplicationContext ac = new AnnotationConfigApplicationContext(DatabaseConfig.class);
	
	@Bean
	public CommentDao commentDao() {
		return new CommentDao(ac.getBean(DataSource.class));
	}
	
	@Bean
	public CommentRegisterService commentRegSvc() {
		return new CommentRegisterService(commentDao());
	}
}
