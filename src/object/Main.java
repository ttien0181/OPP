package object;

public class Main {
	public static void main(String[] args) {
		WebScrapper webscrapper = new WebScrapper("https://www.coindesk.com/");
		webscrapper.exportDataToCSV(webscrapper.scrapData());
		
	}
}
