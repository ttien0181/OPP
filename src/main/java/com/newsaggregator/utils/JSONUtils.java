package main.java.com.newsaggregator.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import main.java.com.newsaggregator.model.NewsArticle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.FileWriter;

public class JSONUtils {
	
	String filePath ;
	
	public JSONUtils(String path) {
		this.filePath = path; 
	}
	
	public void exportDataToCSV(List<NewsArticle> articles) {
        try {
        	
        	Gson gson = new GsonBuilder().setPrettyPrinting().create();

            FileWriter writer = new FileWriter(this.filePath,true);
        	//FileWriter writer = new FileWriter(this.filePath);
            for (NewsArticle article : articles) {
                String json = gson.toJson(article);
                writer.write(json + "\n"+",");
            }
            // Đóng file writer
            writer.close();
            System.out.println("Dữ liệu đã được lưu vào " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
}
