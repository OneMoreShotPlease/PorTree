package authentication;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserAuthService {
	private UserDao userDao;
	
	public UserAuthService(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public User authenticate(UserAuth auth) {
		User user = userDao.selectByEmail(auth.getEmail());
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		if (user == null) {
			throw new WrongIdPasswordException();
		}
		if (!encoder.matches(auth.getPassword(), user.getPassword())) {
			throw new WrongIdPasswordException();
		}
		return user;
	}

}
