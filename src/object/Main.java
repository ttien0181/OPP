package object;

public class Main {
	
	public static void main(String[] args) {
		
		String link ="https://www.coindesk.com";
		WebScrapper webscrapper = new WebScrapper(link);
		webscrapper.exportDataToCSV(webscrapper.scrapData());
		
	}
}
