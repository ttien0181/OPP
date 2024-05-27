package main.java.com.newsaggregator.collector;

import main.java.com.newsaggregator.model.NewsArticle;
import java.io.IOException;
import java.util.*;
import java.nio.file.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;
import org.jsoup.HttpStatusException;

public class NewsbtcCollector extends NewsCollector{
	
	public NewsbtcCollector(String path){
		this.url = "https://www.newsbtc.com/";
		this.path = path;
	}
	
	@Override
	public List<NewsArticle> scrapData(){
		List<NewsArticle> articles = new ArrayList<>();
		String allData = "";
		
		try {
			allData = new String(Files.readAllBytes(Paths.get(path)));
		}catch (IOException e) {
			e.printStackTrace();
		}
		int ID = 5000000 + count(allData);
		
        try {
        	for(int i=2 ; i<62 ; i++) {
        		try {
        			allData = new String(Files.readAllBytes(Paths.get(path)));
        		}catch (IOException e) {
        			e.printStackTrace();
        		}
        		Document doc = Jsoup.connect(url+"page/" + i + "/?s=blockchain")
        				.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3")
        				.timeout(20000)// đặt thời gian chờ tối đa
        				.get();
        		Elements articleElements = doc.select("div.jeg_meta_date");
        		
        		System.out.println("tìm được " + articleElements.size() + " bài viết");
        		for (Element articleElement : articleElements) {
        			try {
        	            Thread.sleep(500); 
        	        } catch (InterruptedException e) {
        	            e.printStackTrace();
        	        }
        			try {
        				Element a = articleElement.selectFirst("a");
        				String link = a.attr("href");
        				Document doc2 = Jsoup.connect(link).get();
        				String detailedContent = getElementText(doc2, "div[class*='content-inner'] > p");
            			if(!allData.contains(link) && !detailedContent.isEmpty()) {//nếu có link trùng, bỏ qua
//            				Document doc2 = Jsoup.connect(link).get();
	            			String websiteSource = getElementAttr(doc2, "meta[property='og:site_name']", "content");
	                        String type = getElementAttr(doc2, "meta[property='og:type']", "content");
	                        String summary = getElementAttr(doc2, "meta[property='og:description']", "content");
	                        String title = getElementAttr(doc2, "meta[property='og:title']", "content");
//	                        String detailedContent = getElementText(doc2, "div[class*='content-inner'] > p");
	                        String date = getElementAttr(doc2, "meta[property='article:modified_time']", "content");
	                        String tags = "";
	                        String author = getElementAttr(doc2, "meta[name='author']", "content");

	                     // Tìm vị trí của từ "news/" trong URL
	                        int indexStart = link.indexOf("www.newsbtc.com/") + 16; // +5 để bỏ qua "news/"

	                        // Tìm vị trí của dấu "/" đầu tiên sau "news/"
	                        int indexEnd = link.indexOf("/", indexStart);

	                        // Trích xuất phần của URL giữa "news/" và "/"
	                        String category = link.substring(indexStart, indexEnd);
	                        
	            			NewsArticle article = new NewsArticle(++ID,link, websiteSource, type, summary, title, detailedContent, date, tags, author, category);
	                        articles.add(article);
	                        System.out.println(article.getID() + "  \n  " + article.getLink() +"\n" + article.getDetailedContent());
	                        
            			}
        			} catch (HttpStatusException e) {
        		        // Nếu gặp lỗi HTTP, bỏ qua bài viết và tiếp tục vòng lặp
        		        System.err.println("Error fetching URL: " + e.getUrl() + ", Status: " + e.getStatusCode());
        		        continue;
        		    } catch (IOException e) {
        		        e.printStackTrace();
        		        continue;
        		    }
        		}
        	}
    		System.out.println("lưu được " + articles.size() + " bài viết");
        }
    		catch (IOException e) {
    			e.printStackTrace();
    		}
        return articles;
	}
}
