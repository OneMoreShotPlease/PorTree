package like;

import authentication.SimpleUser;

public class Like {
	private Long like_id;
	private Long portfolio_id;
	private SimpleUser user;
	
	public Like(Long portfolio_id, SimpleUser user) {
		this.portfolio_id = portfolio_id;
		this.user = user;
	}
	
	public void setLike_id(Long id) {
		this.like_id = id;
	}
	public Long getLike_id() {
		return like_id;
	}
	
	public Long getPortfolio_id() {
		return portfolio_id;
	}
	
	public SimpleUser getUser() {
		return user;
	}
}
