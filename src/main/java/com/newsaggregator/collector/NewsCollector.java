package main.java.com.newsaggregator.collector;

import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import main.java.com.newsaggregator.model.NewsArticle;

public abstract class NewsCollector {
	String url;
	String path;
	
	public abstract List<NewsArticle> scrapData();
	
	public String getElementAttr(Document doc, String cssQuery, String attr) {
        Element element = doc.selectFirst(cssQuery);
        return (element != null) ? element.attr(attr) : "";
    }

    public String getElementText(Document doc, String cssQuery) {// lấy hết các đoạn văn trong bài viết
    	Elements elements = doc.select(cssQuery);
        return (elements != null) ? elements.text() : "";
    }
    
    public int Count(String data) {
		String[] substrings = data.split(url, -1);
		return substrings.length -1;
	}
}
