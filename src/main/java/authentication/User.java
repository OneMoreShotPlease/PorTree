package authentication;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class User extends SimpleUser {
	private String email;
	@JsonIgnore
	private String password;
	private String github;
	private String field;
	@JsonIgnore
	private String refreshToken;
	
	public User(String email, String password, String refreshToken, String name,
			String github, String field, String picture) {
		super(name, picture);
		this.email = email;
		this.password = password;
		this.refreshToken = refreshToken;
		this.github = github;
		this.field = field;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getRefreshToken() {
		return refreshToken;
	}
	
	public String getGithub() {
		return github;
	}
	
	public String getField() {
		return field;
	}
}
