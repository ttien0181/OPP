package object;

public class Main {
	
	public static void main(String[] args) {
		
		String link ="https://coinmarketcap.com/academy/categories/blog";
		String path = "blockchain_news.json";
		//CoindeskCollector coindeskCollector = new CoindeskCollector(link,path);
		CoinmarketcapCollector coinmarketcapCollector = new CoinmarketcapCollector(path,link);
		DataExporter dataExporter = new DataExporter(path);
		
		//dataExporter.exportDataToCSV(coindeskCollector.scrapData());
		dataExporter.exportDataToCSV(coinmarketcapCollector.scrapData());
		
		
	}
}
