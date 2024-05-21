package main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONObject;
import org.json.JSONException;
import org.json.JSONArray;


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

public class Main {
	public static void main(String[] args) {
		String path = "blockchain_newss.json";
		String[] jsonObjects = null;
		try {
			String jsonString = new String(Files.readAllBytes(Paths.get(path)));
			jsonObjects = jsonString.split("}\n,");
		}catch (IOException e) {
            e.printStackTrace();
        }

        // Điều chỉnh chuỗi để đảm bảo mỗi đối tượng là một JSON hợp lệ
        for (int i = 0; i < jsonObjects.length; i++) {
            if (!jsonObjects[i].startsWith("{")) {
                jsonObjects[i] = "{" + jsonObjects[i];
            }
            if (!jsonObjects[i].endsWith("}")) {
                jsonObjects[i] = jsonObjects[i] + "}";
            }
        }

        List<JSONObject> jsonObjectList = new ArrayList<>();
        
        // Tạo danh sách các đối tượng JSONObject
        try {
            for (String jsonObjectString : jsonObjects) {
                JSONObject jsonObject = new JSONObject(jsonObjectString);
                jsonObjectList.add(jsonObject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // In ra các đối tượng JSONObject
        for (JSONObject jsonObject : jsonObjectList) {
            System.out.println(jsonObject.getString("title"));
            
        }
		
	}
	
	
}





        
