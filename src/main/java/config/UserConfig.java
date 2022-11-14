package config;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import authentication.UserDao;
import authentication.UserRegisterService;
import authentication.UserAuthService;
import authentication.JwtManager;

@Configuration
public class UserConfig {
	
	/*
	@Value("${db.driver}")
	private String driverClassName;
	@Value("${db.url}")
	private String url;
	@Value("${db.username}")
	private String username;
	@Value("${db.password}")
	private String password;
	*/
	private String driverClassName = "com.mysql.jdbc.Driver";
	private String url = "jdbc:mysql://localhost/portree?characterEncoding=utf8&enabledTLSProtocols=TLSv1.2";
	private String username = "portree";
	private String password = "portree";
	
	@Bean
	public DataSource dataSource() {
		DataSource ds = new DataSource();
		
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUrl(url);
		ds.setUsername(username);
		ds.setPassword(password);
		ds.setInitialSize(2);
		ds.setMaxActive(10);
		ds.setTestWhileIdle(true);
		ds.setMinEvictableIdleTimeMillis(60000 * 3);
		ds.setTimeBetweenEvictionRunsMillis(10 * 1000);

		return ds;
	}
	
	@Bean
	public UserDao userDao() {
		return new UserDao(dataSource());
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
}