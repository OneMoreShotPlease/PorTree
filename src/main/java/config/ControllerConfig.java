package config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import authentication.UserDao;
import authentication.UserRegisterService;
import authentication.UserAuthService;
import authentication.JwtManager;
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
}
