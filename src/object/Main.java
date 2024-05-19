package object;

public class Main {
	
	public static void main(String[] args) {
		
		String link ="https://www.coindesk.com/";
		String path = "blockchain_newss.json";
		DataCollector dataCollector = new DataCollector(link,path);
		DataExporter dataExporter = new DataExporter(path);
		
		dataExporter.exportDataToCSV(dataCollector.scrapData());
		
	}
}
