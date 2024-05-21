package collector;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebCollector {
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

    public String getElementText(Document doc, String cssQuery) {// lấy hết các đoạn văn trong bài viết
    	Elements elements = doc.select(cssQuery);
        return (elements != null) ? elements.text() : "";
    }
}
