package main.java.com.newsaggregator.collector;

import main.java.com.newsaggregator.model.NewsArticle;
import java.io.IOException;
import java.util.*;
import java.nio.file.*;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;
import org.jsoup.HttpStatusException;

public class CoinmarketcapCollector extends NewsCollector{
	
	public CoinmarketcapCollector(String path){
		this.url = "https://coinmarketcap.com/academy/";
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
		
		int ID = 2000000 + count(allData);
		
        try {
        	for(int i=2 ; i<20 ; i++) {
        		System.out.println(i);
        		try {
    	            Thread.sleep(50000); 
    	        } catch (InterruptedException e) {
    	            e.printStackTrace();
    	        }
        		Document doc = Jsoup.connect(url+"categories/blockchain?page=" + i )
        				.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3")
        				.timeout(10000)// đặt thời gian chờ tối đa
        				.get();
        		System.out.println(doc);
        		try {
    	            Thread.sleep(500); 
    	        } catch (InterruptedException e) {
    	            e.printStackTrace();
    	        }
        		Elements articleElements = doc.select("a[class*='sc-bdfBwQ ArticleCard']");
        		
        		System.out.println("tìm được " + articleElements.size() + " bài viết");
        		for (Element articleElement : articleElements) {
        			try {
        	            Thread.sleep(500); 
        	        } catch (InterruptedException e) {
        	            e.printStackTrace();
        	        }
        			
        			
        			try {
        				String link = "https://coinmarketcap.com" + articleElement.attr("href");
        				Document doc2 = Jsoup.connect(link).get();
        				String detailedContent = getElementText(doc2,"article.sc-bdfBwQ.Container-sc-4c5vqs-0.ArticleContent__ArticleContainer-sc-18n1x4l-0");
        				
            			if(!allData.contains(link) && !detailedContent.isEmpty() && !isLinkInArticles(articles, link)) {//nếu có link trùng hoặc bài viết rỗng, bỏ qua
            				
            				
            				Elements scripts = doc2.select("script[type=\"application/ld+json\"]");
            				String jsonLd = scripts.html();
            				JSONObject jsonObject = new JSONObject(jsonLd);
            				String date = jsonObject.getString("datePublished");
            				//lấy date
            				
            				
                			String websiteSource = "CoinMarketCap";
                			
                            String type = getElementAttr(doc2, "meta[property='og:type']", "content");
                			
                					
                            String summary = getElementText(doc2,"div.sc-bdfBwQ.ArticleContent__ArticleSummaryBox-sc-18n1x4l-1"); 
                            String title = getElementAttr(doc2, "meta[property='og:title']", "content");
                            
                            String tags = "";
                            
                            String author = getElementText(doc2,"a.ArticleContent__AuthorLink-sc-18n1x4l-3").replaceAll("By ","");
                            String category = doc2.selectFirst("a[class*=sc-bdfBwQ Text-msjfkz-0 jStWLp]").text();
                            
                			NewsArticle article = new NewsArticle(++ID,link, websiteSource, type, summary, title, detailedContent, date, tags, author, category);
                            articles.add(article);
                            System.out.println(article.getLink() + "\n  " + article.getID());
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
