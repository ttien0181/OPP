/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package object;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.FileWriter;
import org.jsoup.HttpStatusException;


public class DataCollector {
	private String url;

    public DataCollector(String link) {//phương thức khởi tạo
        this.url = link;
    }
    
	public List<ArticleData> scrapData(){//thu thap du lieu tu trang web tra ve 1 List đối tượng Article
		List<ArticleData> articles = new ArrayList<>();

        try {
    		Document doc = Jsoup.connect(url)
    				.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3")
    				.timeout(10000)// đặt thời gian chờ tối đa
    				.get();
    		Elements articleElements  = doc.select("a[class*='card-titlestyles']");
    		
    		System.out.println("tìm được " + articleElements.size() + " bài viết");
    		
    		for (Element articleElement  : articleElements) {//vòng lặp lấy các phần tử từ HTML và thêm các đối tượng tương ứng vào List "articles"
    			try {
    				String link =  url + articleElement.attr("href");
        			Document doc2 = Jsoup.connect(link).get();
        			String websiteSource = "CoinDesk";
                    String type = getElementAttr(doc2, "meta[property='og:type']", "content");
                    String summary = null; 
                    String title = getElementAttr(doc2, "meta[property='og:title']", "content");
                    String detailedContent = getElementText(doc2, "div[class*='at-text'] > p");
                    String date = getElementAttr(doc2, "meta[property='article:modified_time']", "content");
                    String tags = getElementAttr(doc2, "meta[property='article:tag']", "content");
                    String author = getElementAttr(doc2, "meta[property='article:author']", "content");
                    String category = getElementAttr(doc2, "meta[property='article:section']", "content");

        			ArticleData article = new ArticleData(link, websiteSource, type, summary, title, detailedContent, date, tags, author, category);
                    articles.add(article);
                    System.out.println(articleElement);
    			} catch (HttpStatusException e) {
    		        // Nếu gặp lỗi HTTP, bỏ qua bài viết và tiếp tục vòng lặp
    		        System.err.println("Error fetching URL: " + e.getUrl() + ", Status: " + e.getStatusCode());
    		        continue;
    		    } catch (IOException e) {
    		        e.printStackTrace();
    		        continue;
    		    }
                
    		}

    		System.out.println("lưu được " + articles.size() + " bài viết");
        }
    		catch (IOException e) {
    			e.printStackTrace();
    		}
        return articles;
    	}
	
	private String getElementAttr(Document doc, String cssQuery, String attr) {
        Element element = doc.selectFirst(cssQuery);
        return (element != null) ? element.attr(attr) : "";
    }

    private String getElementText(Document doc, String cssQuery) {
        Element element = doc.selectFirst(cssQuery);
        return (element != null) ? element.text() : "";
    }
}

    
