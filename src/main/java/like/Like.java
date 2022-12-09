package like;

public class Like {
	private Long like_id;
	private Long portfolio_id;
	private Long user_id;
	
	public Like(Long portfolio_id, Long user_id) {
		this.portfolio_id = portfolio_id;
		this.user_id = user_id;
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
	
	public Long getUser_id() {
		return user_id;
	}
}
