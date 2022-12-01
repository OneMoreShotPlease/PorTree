package portfolio;

import java.time.LocalDateTime;

public class Portfolio {
	private Long portfolio_id;
	private Long user_id;
	private String title;
	private LocalDateTime publish_date;
	private String github = null;
	private String demo = null;
	private String category = null;
	private String description;
	
	public Portfolio(Long user_id, String title, LocalDateTime publish_date,
			String github, String demo, String category, String description) {
		this.user_id = user_id;
		this.title = title;
		this.publish_date = publish_date;
		this.github = github;
		this.demo = demo;
		this.category = category;
		this.description = description;
	}
	
	void setPortfolio_id(Long id) {
		this.portfolio_id = id;
	}
	public Long getPortfolio_id() {
		return portfolio_id;
	}
	
	public Long getUser_id() {
		return user_id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public LocalDateTime getPublish_date() {
		return publish_date;
	}
	
	public String getGithub() {
		return github;
	}
	
	public String getDemo() {
		return demo;
	}
	
	public String getCategory() {
		return category;
	}
	
	public String getDescription() {
		return description;
	}
	
}
