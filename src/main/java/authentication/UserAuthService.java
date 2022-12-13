package authentication;

import java.time.LocalDateTime;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import portfolio.Portfolio;
import portfolio.PortfolioRequest;

public class UserAuthService {
	private UserDao userDao;
	
	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
	public UserAuthService(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public User authenticate(UserAuth auth) {
		User user = userDao.selectByEmail(auth.getEmail());
		
		if (user == null) {
			throw new WrongIdPasswordException();
		}
		if (!encoder.matches(auth.getPassword(), user.getPassword())) {
			throw new WrongIdPasswordException();
		}
		return user;
	}
}
