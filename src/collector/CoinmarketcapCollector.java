package collector;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONObject;


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


public class CoinmarketcapCollector extends WebCollector{
	public CoinmarketcapCollector(String path,String url){
		this.url = url;
		this.path = path;
	}
	
	@Override
	public List<ArticleData> scrapData(){
		List<ArticleData> articles = new ArrayList<>();

        try {
        	for(int i=1;i<20;i++) {
        		String existingLinks = new String(Files.readAllBytes(Paths.get(path)));//ghi hết dữ liệu trong path ra ,
            	
        		Document doc = Jsoup.connect(url+"?page=" + i)
        				.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3")
        				.timeout(10000)// đặt thời gian chờ tối đa
        				.get();
        		Elements articleElements  = doc.select("a[class*='sc-bdfBwQ ArticleCard']");
        		
        		System.out.println("tìm được " + articleElements.size() + " bài viết");
        		for (Element articleElement  : articleElements) {
        			try {
        	            Thread.sleep(3000); 
        	        } catch (InterruptedException e) {
        	            e.printStackTrace();
        	        }
        			try {
        				String link =  "https://coinmarketcap.com" + articleElement.attr("href");
            			if(!existingLinks.contains(link)) {//nếu có link trùng, bỏ qua
            				Document doc2 = Jsoup.connect(link).get();
            				
            				//lấy date
            				Elements scripts = doc2.select("script[type=\"application/ld+json\"]");
            				String jsonLd = scripts.html();
            				JSONObject jsonObject = new JSONObject(jsonLd);
            				String date = jsonObject.getString("datePublished");
            				
                			String websiteSource = "CoinMarketCap";
                            String type = getElementText(doc2,"a.sc-bdfBwQ.Text-msjfkz-0");
                            String summary = getElementText(doc2,"div.sc-bdfBwQ.ArticleContent__ArticleSummaryBox-sc-18n1x4l-1"); 
                            String title = getElementText(doc2,"h1.sc-bdfBwQ.Text-msjfkz-0.Heading-juwhnu-0.hoXOTC.dAnDNe");;
                            String detailedContent = getElementText(doc2,"article.sc-bdfBwQ.Container-sc-4c5vqs-0.ArticleContent__ArticleContainer-sc-18n1x4l-0");
                            //String date = null;
                            String tags = null;
                            
                            String author = getElementText(doc2,"a.ArticleContent__AuthorLink-sc-18n1x4l-3").replaceAll("By ","");
                            String category = getElementText(doc2,"a.sc-bdfBwQ.Text-msjfkz-0.jStWLp");
                            
                			ArticleData article = new ArticleData(link, websiteSource, type, summary, title, detailedContent, date, tags, author, category);
                            articles.add(article);
                            System.out.println(article.getDate() + "  ////  " + article.getSummary());
                            
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
