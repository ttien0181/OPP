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

public class CoindeskCollector extends WebCollector{
    public CoindeskCollector(String path) {//phương thức khởi tạo
        this.url = "https://www.coindesk.com/";
        this.path = path;
    }
    
    @Override
	public List<ArticleData> scrapData(){//thu thap du lieu tu trang web tra ve 1 List đối tượng Article
		List<ArticleData> articles = new ArrayList<>();// tạo list ArData

        try {
        	String existingLinks = new String(Files.readAllBytes(Paths.get(path)));//ghi hết dữ liệu trong path ra ,
        	
    		Document doc = Jsoup.connect(url)
    				.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3")
    				.timeout(10000)// đặt thời gian chờ tối đa
    				.get();// kết nối tới url
    		Elements articleElements  = doc.select("a[class*='card-titlestyles']");//lấy các element của class '...'
    		
    		System.out.println("tìm được " + articleElements.size() + " bài viết");
    		
    		for (Element articleElement  : articleElements) {// với mỗi Element thu thập được
    			try {
    	            Thread.sleep(3000); 
    	        } catch (InterruptedException e) {
    	            e.printStackTrace();
    	        }
    			try {
    				String link =  url + articleElement.attr("href");// lấy trong element phần "hrer"(phần sau link), tạo link bằng cách thêm url(đoạn đầu, có thể chỉnh sửa)
        			if(!existingLinks.contains(link)) {// nếu link đã tồn tạo trong file, bỏ qua
        				Document doc2 = Jsoup.connect(link).get();// kết nối tới link đã ghép
            			String websiteSource = "CoinDesk";
                        String type = getElementAttr(doc2, "meta[property='og:type']", "content");
                        String summary = ""; 
                        String title = getElementAttr(doc2, "meta[property='og:title']", "content");
                        String detailedContent = getElementText(doc2, "div[class*='at-text'] > p");
                        String date = getElementAttr(doc2, "meta[property='article:modified_time']", "content");
                        String tags = getElementAttr(doc2, "meta[property='article:tag']", "content");
                        String author = getElementAttr(doc2, "meta[property='article:author']", "content");
                        String category = getElementAttr(doc2, "meta[property='article:section']", "content");
                        
            			ArticleData article = new ArticleData(link, websiteSource, type, summary, title, detailedContent, date, tags, author, category);// tạo đối tượng ArData
                        articles.add(article);// thêm vào List
                        System.out.println(article.getDetailedContent());
        			}
    			} catch (HttpStatusException e) {
    		        // Nếu gặp lỗi HTTP, bỏ qua bài viết và tiếp tục vòng lặp
    		        System.err.println("Error fetching URL: " + e.getUrl() + ", Status: " + e.getStatusCode());
    		        continue;
    		    } catch (IOException e) {// lỗi vào ra , bỏ qua và tiếp tục
    		        e.printStackTrace();
    		        continue;
    		    }
                
    		}

    		System.out.println("lưu được " + articles.size() + " bài viết");
        }
    		catch (IOException e) {
    			e.printStackTrace();
    		}
        return articles;// trả về List
    	}

}

    
