package collector;

public class Main {
	
	public static void main(String[] args) {
		String path = "blockchain_newss.json";
		//CoindeskCollector coindeskCollector = new CoindeskCollector(path);
		//CoinmarketcapCollector coinmarketcapCollector = new CoinmarketcapCollector(path);
		CryptoslateCollector cryptoslateCollector = new CryptoslateCollector(path);
		//BeincryptoCollector beincryptoCollector =new BeincryptoCollector(path);
		
		DataExporter dataExporter = new DataExporter(path);

		
		
		//dataExporter.exportDataToCSV(coindeskCollector.scrapData());
		//dataExporter.exportDataToCSV(coinmarketcapCollector.scrapData());
		dataExporter.exportDataToCSV(cryptoslateCollector.scrapData());
		//dataExporter.exportDataToCSV(beincryptoCollector.scrapData());
		
	}
}
