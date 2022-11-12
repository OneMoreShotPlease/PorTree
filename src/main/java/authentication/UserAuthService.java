package authentication;

public class UserAuthService {
	private UserDao userDao;
	
	public UserAuthService(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public User authenticate(UserAuth auth) {
		User user = userDao.selectByEmail(auth.getEmail());
		if (user == null) {
			throw new WrongIdPasswordException();
		}
		if (!user.matchPassword(auth.getPassword())) {
			throw new WrongIdPasswordException();
		}
		return user;
	}

}
