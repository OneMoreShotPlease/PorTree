package config;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import authentication.UserDao;
import authentication.SimpleUserDao;
import authentication.UserRegisterService;
import authentication.UserAuthService;
import authentication.JwtFilter;
import authentication.JwtManager;

@Configuration
public class UserConfig {
	ApplicationContext ac = new AnnotationConfigApplicationContext(DatabaseConfig.class);
	
	@Bean
	public UserDao userDao() {
		return new UserDao(ac.getBean(DataSource.class));
	}
	
	@Bean
	public SimpleUserDao simpleUserDao() {
		return new SimpleUserDao(ac.getBean(DataSource.class));
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public UserRegisterService userRegSvc() {
		return new UserRegisterService(userDao(), passwordEncoder());
	}
	
	@Bean
	public UserAuthService userAuthSvc() {
		return new UserAuthService(userDao());
	}
	
	@Bean
	public JwtManager jwtManager() {
		return new JwtManager();
	}
	
	@Bean
	public JwtFilter jwtFilter() {
		return new JwtFilter(jwtManager());
	}
}