package comment;

public class CommentRequest {
	private Long portfolio_id;
	private String contents;
	
	public Long getPortfolio_id() {
		return portfolio_id;
	}
	public void setPortfolio_id(Long portfolio_id) {
		this.portfolio_id = portfolio_id;
	}
	
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
}
