package authentication;

public class User {
	private Long user_id;
	private String email;
	private String password;
	private String name;
	private String github;
	private String field;
	private String picture;
	
	public User(String email, String password, String name,
			String github, String field, String picture) {
		this.email = email;
		this.password = password;
		this.name = name;
		this.github = github;
		this.field = field;
		this.picture = picture;
	}
	
	public boolean matchPassword(String password) { // UserAuthService에서 사용 (로그인 시)
		return this.password.equals(password);
	}
	
	void setId(Long id) {
		this.user_id = id;
	}
	public Long getId() {
		return user_id;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getName() {
		return name;
	}
	
	public String getGithub() {
		return github;
	}
	
	public String getField() {
		return field;
	}
	
	public String getPicture() {
		return picture;
	}
}
