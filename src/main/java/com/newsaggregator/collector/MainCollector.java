package main.java.com.newsaggregator.collector;

import main.java.com.newsaggregator.utils.JSONUtils;

public class MainCollector {
	
	public static void main(String[] args) {
		
		String path = "blockchain_news.json";
		CoindeskCollector coindeskCollector = new CoindeskCollector(path);
		CoinmarketcapCollector coinmarketcapCollector = new CoinmarketcapCollector(path);
		CryptoslateCollector cryptoslateCollector = new CryptoslateCollector(path);
		BeincryptoCollector beincryptoCollector = new BeincryptoCollector(path);
		NewsbtcCollector newsbtcCollector = new NewsbtcCollector(path);
		JSONUtils dataExporter = new JSONUtils(path);

		//dataExporter.exportDataToJSON(coindeskCollector.scrapData());
		//dataExporter.exportDataToJSON(coinmarketcapCollector.scrapData());
		//dataExporter.exportDataToJSON(cryptoslateCollector.scrapData());
		//dataExporter.exportDataToJSON(beincryptoCollector.scrapData());
		dataExporter.exportDataToJSON(newsbtcCollector.scrapData());
	}
}
