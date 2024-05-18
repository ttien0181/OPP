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

public class Output {
	
	String filePath ;
	
	public Output(String path) {
		this.filePath = path; 
	}
	
	public void exportDataToCSV(List<ArticleData> articles) {
        try {
        	//Tạo file writer có đường dẫn filePath
        	FileWriter writer = new FileWriter(this.filePath,true);
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
