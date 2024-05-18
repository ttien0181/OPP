package object;

public class Main {
	
	public static void main(String[] args) {
		
		String link ="https://www.coindesk.com";
		String path = "blockchain_news.csv";
		DataCollector dataCollector = new DataCollector(link);
		DataExporter dataExporter = new DataExporter(path);
		
		dataExporter.exportDataToCSV(dataCollector.scrapData());
		
	}
}
