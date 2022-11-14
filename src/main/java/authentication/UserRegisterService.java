package authentication;

import org.springframework.security.crypto.password.PasswordEncoder;

public class UserRegisterService {
	private UserDao userDao;
	private PasswordEncoder passwordEncoder;
	
	public UserRegisterService(UserDao userDao, PasswordEncoder passwordEncoder) {
		this.userDao = userDao;
		this.passwordEncoder = passwordEncoder;
	}
	
	public Long regist(UserRequest req) {
		User user = userDao.selectByEmail(req.getEmail());
		if (user != null) {
			throw new DuplicateUserException("Duplicate Email " + req.getEmail());
		}
		// password 암호화
		String password = passwordEncoder.encode(req.getPassword());
		// 회원가입 시 picture은 안 넣어도 되도록
		User newUser = new User(
				req.getEmail(), password, req.getName(),
				null, null, null); 
		userDao.insert(newUser);
		return newUser.getId();
	}

}
