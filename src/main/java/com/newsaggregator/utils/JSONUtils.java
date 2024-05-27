package main.java.com.newsaggregator.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import main.java.com.newsaggregator.model.NewsArticle;
import com.fasterxml.jackson.annotation.JsonView;
import com.google.gson.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class JSONUtils {
	private String filePath;
	
	public JSONUtils(String path) {
		this.filePath = path; 
	}
	
	public void exportDataToJSON(List<NewsArticle> articles) {
        try {
        	String jsons = "";
        	ObjectMapper objectMapper = new ObjectMapper();
        	objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        	
//        	Gson gson = new GsonBuilder().setPrettyPrinting().create();

            
            jsons = new String(Files.readAllBytes(Paths.get(filePath)));
            FileWriter writer = new FileWriter(this.filePath);
            if (jsons.endsWith("]")) {
            	jsons = jsons.substring(0, jsons.length() - 1);
            	jsons = jsons + ",";
            }
            for (NewsArticle article : articles) {
                String json = objectMapper.writeValueAsString(article);
                jsons=jsons + json + "\n"+",";
            }
            if (!jsons.startsWith("[")) {
                jsons = "[\n" + jsons;
            }
            if (jsons.endsWith(",")) {
                jsons = jsons.substring(0, jsons.length() - 1);
            }
            if (!jsons.endsWith("]")) {
            	jsons = jsons + "]";
            }
            writer.write(jsons);
            // Đóng file writer
            writer.close();
            System.out.println("Dữ liệu đã được lưu vào " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
