package collector;

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

public class DataExporter {
	
	String filePath ;
	
	public DataExporter(String path) {
		this.filePath = path; 
	}
	
	public void exportDataToCSV(List<ArticleData> articles) {
        try {
        	
        	Gson gson = new GsonBuilder().setPrettyPrinting().create();
        	String json = gson.toJson(articles);

            FileWriter writer = new FileWriter(this.filePath,true);
        	//FileWriter writer = new FileWriter(this.filePath);
            writer.write(json + "\n");

            // Đóng file writer
            writer.close();
            System.out.println("Dữ liệu đã được lưu vào " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
