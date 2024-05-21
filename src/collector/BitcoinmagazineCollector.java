package collector;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.FileWriter;
import org.jsoup.HttpStatusException;

public class BitcoinmagazineCollector extends WebCollector{
	public BitcoinmagazineCollector( String path) {
		this.url = "https://cryptoslate.com/news/";
		this.path = path;
	}
	
	@Override
	public List<ArticleData> scrapData(){
		List<ArticleData> articles = new ArrayList<>();
		
		try {
			for(int i=1;i<51;i++) {
	        	String existingLinks = new String(Files.readAllBytes(Paths.get(path)));
	        	
	    		Document doc = Jsoup.connect(url + "page/" + i +"/")
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
	        			if(!existingLinks.contains(link)) {
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
	                        
	            			ArticleData article = new ArticleData(link, websiteSource, type, summary, title, detailedContent, date, tags, author, category);
	                        articles.add(article);
	                        System.out.println(article.getDetailedContent() + "  \n  " + article.getLink());
	                        
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
