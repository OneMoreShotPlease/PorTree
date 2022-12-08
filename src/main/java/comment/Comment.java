package comment;

import java.time.LocalDateTime;

public class Comment {
	private Long comment_id;
	private Long portfolio_id;
	private Long user_id;
	private LocalDateTime publish_date;
	private String contents;
	
	public Comment(Long portfolio_id, Long user_id, LocalDateTime publish_date, String contents) {
		this.portfolio_id = portfolio_id;
		this.user_id = user_id;
		this.publish_date = publish_date;
		this.contents = contents;
	}
	
	void setComment_id(Long id) {
		this.comment_id = id;
	}
	public Long getComment_id() {
		return comment_id;
	}
	
	public Long getPortfolio_id() {
		return portfolio_id;
	}
	
	public Long getUser_id() {
		return user_id;
	}
	
	public LocalDateTime getPublish_date() {
		return publish_date;
	}
	
	public String getContents() {
		return contents;
	}
}
