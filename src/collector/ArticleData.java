package collector;




public class ArticleData {
    private String link;
    private String websiteSource;
    private String type;
    private String summary;
    private String title;
    private String detailedContent;
    private String date;
    private String tags;
    private String author;
    private String category;
    
    public ArticleData (String link, String websiteSource, String type, //phương thức khởi tạo
    		String summary, String title, String detailedContent, String date, 
    		String tags, String author, String category){
        this.link = link;
        this.websiteSource = websiteSource;
        this.type = type;
        this.summary = summary;
        this.title = title;
        this.detailedContent = detailedContent;
        this.date = date;
        this.tags = tags;
        this.author = author;
        this.category = category;
    }
  //geter và seter
	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getWebsiteSource() {
		return websiteSource.replaceAll(",", "/");
	}

	public void setWebsiteSource(String websiteSource) {
		this.websiteSource = websiteSource;
	}

	public String getType() {
		return type.replaceAll(",", "/");
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getTitle() {
		return title.replaceAll(",", "/");
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDetailedContent() {
		return detailedContent.replaceAll(",", "/");
	}

	public void setDetailedContent(String detailedContent) {
		this.detailedContent = detailedContent;
	}

	public String getDate() {
		return date.replaceAll(",", "/");
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTags() {
		return tags.replaceAll(",", "/");
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getAuthor() {
		return author.replaceAll(",", "/");
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getCategory() {
		return category.replaceAll(",", "/");
	}

	public void setCategory(String category) {
		this.category = category;
	}

}
