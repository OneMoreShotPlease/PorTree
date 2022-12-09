package authentication;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class SimpleUser {
	private Long user_id;
	private String name;
	private String picture;
	
	public SimpleUser(String name, String picture) {
		this.name = name;
		this.picture = picture;
	}
	
	void setId(Long id) {
		this.user_id = id;
	}
	public Long getId() {
		return user_id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getPicture() {
		return picture;
	}
}
