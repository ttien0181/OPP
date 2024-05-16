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

public class WebScrapper {
	private String url;

    public WebScrapper(String url) {//phương thức khởi tạo
        this.url = url;
    }
	public List<ArticleData> scrapData(){//thu thap du lieu tu trang web 
		List<ArticleData> articles = new ArrayList<>();

        try {
    		Document doc = Jsoup.connect(url).get();
    		/*Sử dụng phương thức connect() của Jsoup để kết nối đến URL được cung cấp
    		 *  và sau đó sử dụng phương thức get() để lấy nội dung HTML của trang web 
    		 *  và lưu vào một đối tượng Document.
    		 */
    		
    		Elements articleElements  = doc.select("a[class*='card-titlestyles']");
    		/*
    		 * 
    		 * 
    		 * 
    		 */
    		System.out.println(articleElements.size() + "d");
    		
    		
    		for (Element articleElement  : articleElements) {//vòng lặp lấy các phần tử từ HTML và thêm các đối tượng tương ứng vào List "articles"
    			String link =  "https://www.coindesk.com" + articleElement.attr("href");
    			Document doc2 = Jsoup.connect(link).get();
    			String websiteSource = "CoinDesk";
                String type = getElementAttr(doc2, "meta[property='og:type']", "content");
                String summary = null; // Assuming there is no summary available
                String title = getElementAttr(doc2, "meta[property='og:title']", "content");
                String detailedContent = getElementText(doc2, "div[class*='at-text'] > p"); // Assuming the full content is inside a div
                String date = getElementAttr(doc2, "meta[property='article:modified_time']", "content");
                String tags = getElementAttr(doc2, "meta[property='article:tag']", "content");
                String author = getElementAttr(doc2, "meta[property='article:author']", "content");
                String category = getElementAttr(doc2, "meta[property='article:section']", "content");

    			ArticleData article = new ArticleData(link, websiteSource, type, summary, title, detailedContent, date, tags, author, category);
                articles.add(article);
                //System.out.println(articles.size()+"a");
                System.out.println(articleElement);
                
    		}

    		System.out.println(articles.size());
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
    
    
    public void exportDataToCSV(List<ArticleData> articles) {
        try {
            FileWriter writer = new FileWriter("blockchain_news.csv");

            // Ghi tiêu đề cho file CSV
            writer.append("Link,Source,Type,Summary,Title,Content,Date,Tags,Author,Category\n");

            // Duyệt qua danh sách các bài viết và ghi vào file CSV
            for (ArticleData article : articles) {
                writer.append(article.getLink()).append(",");
                writer.append(article.getWebsiteSource()).append(",");
                writer.append(article.getType()).append(",");
                writer.append(article.getSummary()).append(",");
                writer.append(article.getTitle()).append(",");
                writer.append(article.getDetailedContent()).append(",");
                writer.append(article.getDate()).append(",");
                writer.append(article.getTags()).append(",");
                writer.append(article.getAuthor()).append(",");
                writer.append(article.getCategory()).append("\n");
            }

            // Đóng file writer
            writer.close();
            System.out.println("Dữ liệu đã được lưu vào blockchain_news.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }

    
