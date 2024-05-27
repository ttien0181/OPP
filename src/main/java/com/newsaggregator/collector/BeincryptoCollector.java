package main.java.com.newsaggregator.collector;

import main.java.com.newsaggregator.model.NewsArticle;
import java.io.IOException;
import java.util.*;
import java.nio.file.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;
import org.jsoup.HttpStatusException;

public class BeincryptoCollector extends NewsCollector{
	
	public BeincryptoCollector(String path) {
		this.path = path;
		this.url = "https://beincrypto.com/";
	}
	
	@Override
	public List<NewsArticle> scrapData(){//thu thap du lieu tu trang web tra ve 1 List đối tượng Article
		List<NewsArticle> articles = new ArrayList<>();// tạo list ArData
		String allData = "";
		
		try {
			allData= new String(Files.readAllBytes(Paths.get(path)));
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		int ID = 4000000 + count(allData);
		
        try {
        	for(int i=2;i<62;i++) {
        		try {
        			allData = new String(Files.readAllBytes(Paths.get(path)));
        		}catch (IOException e) {
        			e.printStackTrace();
        		}
        		Document doc = Jsoup.connect(url + "page/" + i + "/?s=blockchain")
        				.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3")
        				.timeout(10000)// đặt thời gian chờ tối đa
        				.get();// kết nối tới url
        		Elements articleElements = doc.select("div[class*='shrink-0 rounded-lg md:rounded-xl mb-2.5 md:mb-3 max-w-full overflow-hidden']");//lấy các element của class '...'
        		
        		System.out.println("tìm được " + articleElements.size() + " bài viết");
        		for (Element articleElement : articleElements) {// với mỗi Element thu thập được
        			try {
        	            Thread.sleep(500); 
        	        } catch (InterruptedException e) {
        	            e.printStackTrace();
        	        }
        			try {
        				Element articleLink = articleElement.selectFirst("a");// lấy link
        				String link =  articleLink.attr("href");
        				Document doc2 = Jsoup.connect(link).get();// kết nối tới link đã ghép
        				String detailedContent = getElementText(doc2, "div[class*='entry-content-inner'] > p");
            			if(!allData.contains(link) && !detailedContent.isEmpty() && !isLinkInArticles(articles, link)) {// nếu link đã tồn tạo trong file, bỏ qua
            				
                			String websiteSource = getElementAttr(doc2, "meta[property='og:site_name']", "content");;
                            String type = getElementAttr(doc2, "meta[property='og:type']", "content");
                            String summary = ""; 
                            String title = getElementAttr(doc2, "meta[property='og:title']", "content");
                            
                            String date = getElementAttr(doc2, "meta[property='article:modified_time']", "content");
                            String tags = getElementAttr(doc2, "meta[property='article:tag']", "content");
                            String author = getElementAttr(doc2, "meta[name='author']", "content");
                            
                            Elements categoryElement = doc2.select("a.whitespace-nowrap.inline-flex.items-center.p4.font-sans.font-normal.text-currentColor.hover\\:underline");
                            String category = categoryElement.text();
                			NewsArticle article = new NewsArticle(++ID,link, websiteSource, type, summary, title, detailedContent, date, tags, author, category);// tạo đối tượng ArData
                            articles.add(article);// thêm vào List
                            System.out.println(article.getID());
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

        	}
    		System.out.println("lưu được " + articles.size() + " bài viết");
        }
    		catch (IOException e) {
    			e.printStackTrace();
    		}
        return articles;// trả về List
    	}
	
}
