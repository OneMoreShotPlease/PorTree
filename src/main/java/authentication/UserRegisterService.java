package authentication;

public class UserRegisterService {
	private UserDao userDao;
	
	public UserRegisterService(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public Long regist(UserRequest req) {
		User user = userDao.selectByEmail(req.getEmail());
		if (user != null) {
			throw new DuplicateUserException("Duplicate Email " + req.getEmail());
		}
		
		// 회원가입 시 picture은 안 넣어도 되도록
		User newUser = new User(
				req.getEmail(), req.getPassword(), req.getName(),
				null, null, null); 
		userDao.insert(newUser);
		return newUser.getId();
	}

}
