package portfolio;

public class PortfolioRequest {

	private String title;
	private String github;
	private String demo;
	private String category;
	private String description;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getGithub() {
		return github;
	}
	public void setGithub(String github) {
		this.github = github;
	}
	
	public String getDemo() {
		return demo;
	}
	public void setDemo(String demo) {
		this.demo = demo;
	}
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
