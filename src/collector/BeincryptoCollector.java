package collector;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class BeincryptoCollector extends WebCollector{
	public BeincryptoCollector(String path) {
		this.path = path;
		this.url = "https://beincrypto.com/news/";
	}
	
	public List<ArticleData> scrapData(){//thu thap du lieu tu trang web tra ve 1 List đối tượng Article
		List<ArticleData> articles = new ArrayList<>();// tạo list ArData

        try {
        	String existingLinks = new String(Files.readAllBytes(Paths.get(path)));//ghi hết dữ liệu trong path ra ,
        	
    		Document doc = Jsoup.connect(url)
    				.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3")
    				.timeout(10000)// đặt thời gian chờ tối đa
    				.get();// kết nối tới url
    		Elements articleElements  = doc.select("div[class*='shrink-0 rounded-lg md:rounded-xl mb-2.5 md:mb-3 max-w-full overflow-hidden']");//lấy các element của class '...'
    		
    		System.out.println("tìm được " + articleElements.size() + " bài viết");
    		for (Element articleElement  : articleElements) {// với mỗi Element thu thập được
    			try {
    	            Thread.sleep(3000); 
    	        } catch (InterruptedException e) {
    	            e.printStackTrace();
    	        }
    			try {
    				Element articleLink = articleElement.selectFirst("a");// lấy link
    				String link =  articleLink.attr("href");
    				System.out.println(link);
        			if(!existingLinks.contains(link)) {// nếu link đã tồn tạo trong file, bỏ qua
        				Document doc2 = Jsoup.connect(link).get();// kết nối tới link đã ghép
            			String websiteSource = getElementAttr(doc2, "meta[property='og:site_name']", "content");;
                        String type = getElementAttr(doc2, "meta[property='og:type']", "content");
                        String summary = ""; 
                        String title = getElementAttr(doc2, "meta[property='og:title']", "content");
                        String detailedContent = getElementText(doc2, "div[class*='entry-content-inner'] > p");
                        String date = getElementAttr(doc2, "meta[property='article:modified_time']", "content");
                        String tags = getElementAttr(doc2, "meta[property='article:tag']", "content");
                        String author = getElementAttr(doc2, "meta[name='author']", "content");
                        
                        Elements categoryElement = doc2.select("a.whitespace-nowrap.inline-flex.items-center.p4.font-sans.font-normal.text-currentColor.hover\\:underline");
                        String category = categoryElement.text();
                        
            			ArticleData article = new ArticleData(link, websiteSource, type, summary, title, detailedContent, date, tags, author, category);// tạo đối tượng ArData
                        articles.add(article);// thêm vào List
                        System.out.println(article.getCategory());
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
