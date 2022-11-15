package portfolio;

import java.time.LocalDateTime;

public class Portfolio {
	private Long portfolio_id;
	private Long user_id;
	private String title;
	private LocalDateTime publish_date;
	private String picture = null;
	private String description;
	
	public Portfolio(Long user_id, String title, LocalDateTime publish_date,
			String picture, String description) {
		this.user_id = user_id;
		this.title = title;
		this.publish_date = publish_date;
		this.picture = picture;
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
	
	public String getPicture() {
		return picture;
	}
	
	public String getDescription() {
		return description;
	}
	
}
