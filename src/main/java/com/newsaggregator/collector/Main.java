package main.java.com.newsaggregator.collector;

import main.java.com.newsaggregator.utils.JSONUtils;

public class Main {
	
	public static void main(String[] args) {
		String path = "blockchain_news.json";
		CoindeskCollector coindeskCollector = new CoindeskCollector(path);
		CoinmarketcapCollector coinmarketcapCollector = new CoinmarketcapCollector(path);
		CryptoslateCollector cryptoslateCollector = new CryptoslateCollector(path);
		BeincryptoCollector beincryptoCollector =new BeincryptoCollector(path);
		
		JSONUtils dataExporter = new JSONUtils(path);

		
		
		//dataExporter.exportDataToCSV(coindeskCollector.scrapData());
		dataExporter.exportDataToCSV(coinmarketcapCollector.scrapData());
		//dataExporter.exportDataToCSV(cryptoslateCollector.scrapData());
		//dataExporter.exportDataToCSV(beincryptoCollector.scrapData());
		
	}
}
