package comment;

import java.time.LocalDateTime;
import authentication.SimpleUser;

public class Comment {
	private Long comment_id;
	private Long portfolio_id;
	private SimpleUser user;
	private LocalDateTime publish_date;
	private String contents;
	
	public Comment(Long portfolio_id, SimpleUser user, LocalDateTime publish_date, String contents) {
		this.portfolio_id = portfolio_id;
		this.user = user;
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
	
	public SimpleUser getUser() {
		return user;
	}
	
	public LocalDateTime getPublish_date() {
		return publish_date;
	}
	
	public String getContents() {
		return contents;
	}
}
