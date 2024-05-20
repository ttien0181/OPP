package object;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Collector {
	String url;
	String path;
	
	public List<ArticleData> scrapData(){
		List<ArticleData> articles = new ArrayList<>();
		return articles; 
	}
	
	public String getElementAttr(Document doc, String cssQuery, String attr) {
        Element element = doc.selectFirst(cssQuery);
        return (element != null) ? element.attr(attr) : "";
    }

    public String getElementText(Document doc, String cssQuery) {
        Element element = doc.selectFirst(cssQuery);
        return (element != null) ? element.text() : "";
    }
}
