package main.java.com.newsaggregator.collector;

import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import main.java.com.newsaggregator.model.NewsArticle;

public abstract class NewsCollector {
	protected String url;
	protected String path;
	
	public abstract List<NewsArticle> scrapData();
	
	public String getElementAttr(Document doc, String cssQuery, String attr) {
        Element element = doc.selectFirst(cssQuery);//tìm tất cả các phần tử trong HTML khớp với cssQuery
        return (element != null) ? element.attr(attr) : "";// nếu = null ,trả về chuỗi rỗng, nếu ko , trả về giá trị thuộc tính attr
    }

    public String getElementText(Document doc, String cssQuery) {// lấy hết các đoạn văn trong bài viết
    	Elements elements = doc.select(cssQuery);
        return (elements != null) ? elements.text() : "";//nếu bằng null trả về chuỗi rỗng , nếu ko trả về văn bản kết hợp của tất cả các phần tử 
    }
    
    public int count(String data) {// đếm số bài viết của web trong 1 String
		String[] substrings = data.split(url, -1);
		return substrings.length -1;
	}
}
