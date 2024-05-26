package main.java.com.newsaggregator.collector;

import main.java.com.newsaggregator.model.NewsArticle;
import java.io.IOException;
import java.util.*;
import java.nio.file.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;
import org.jsoup.HttpStatusException;

public class CryptoslateCollector extends NewsCollector{
	
	public CryptoslateCollector(String path) {
		this.url = "https://cryptoslate.com/";
		this.path = path;
	}
	
	@Override
	public List<NewsArticle> scrapData(){
		List<NewsArticle> articles = new ArrayList<>();
		String allData = "";
		
		try {
			allData= new String(Files.readAllBytes(Paths.get(path)));
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		int ID = 3000000 + count(allData);
		try {
			for(int i=60;i<80;i++) {
	        	
	    		Document doc = Jsoup.connect(url + "news/page/" + i +"/")
	    				.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3")
	    				.timeout(10000)// đặt thời gian chờ tối đa
	    				.get();
	    		Elements articleElements  = doc.select("div[class*='list-feed slate']");//1 cái mỗi bài
	    		Elements post = articleElements.select("div[class*='list-post']");// nhiều cái , nhưng cần tìm trong feed slate
	    		
	    		System.out.println("tìm được " + post.size() + " bài viết");
	    		
	    		for (Element articleElement  : post) {
	    			try {
	    	            Thread.sleep(3000); 
	    	        } catch (InterruptedException e) {
	    	            e.printStackTrace();
	    	        }
	    			try {
	    				
	    				Element articleLink = articleElement.selectFirst("a");// lấy link
	    				String link =  articleLink.attr("href");
	    				
	    				System.out.println(link);
	        			if(!allData.contains(link)) {
	    				//if(true) {
	        				Document doc2 = Jsoup.connect(link).get();
	            			String websiteSource = getElementAttr(doc2, "meta[property='og:site_name']", "content");
	                        String type = getElementAttr(doc2, "meta[property='og:type']", "content");
	                        String summary = "";
	                        String title = getElementAttr(doc2, "meta[property='og:title']", "content");
	                        String detailedContent = getElementText(doc2, "article[class*='full-article'] > p");
	                        String date = getElementAttr(doc2, "meta[property='article:modified_time']", "content");
	                        String tags = "";
	                        String author = getElementAttr(doc2, "meta[name='author']", "content");
	                        
	                        Elements allCategory = doc2.select("div[class*='posted-in']");
	                        String category = allCategory.text().replace("Posted In: ", "");
	                        
	            			NewsArticle article = new NewsArticle(++ID,link, websiteSource, type, summary, title, detailedContent, date, tags, author, category);
	                        articles.add(article);
	                        System.out.println(article.getID() + "  \n  " + article.getLink());
	                        
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
