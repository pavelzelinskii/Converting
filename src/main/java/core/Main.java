package core;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class Main {
	static String ip = System.getProperty("ip");
	static Properties info = new Properties();
	static WebClient driver;
	static String by;

	public static boolean isElementPresentHtmlUnit(HtmlPage page, String by) {
		if (page.getElementsById(by).size() > 0)
			return true;
		else
			return false;
	}

	public static String getValueHtmlUnit(HtmlPage page, String by) {
		if (isElementPresentHtmlUnit(page, by))
			return page.getElementById(by).getTextContent().trim();
		else
			return "null";
	}

	public static void main(String[] args) throws Exception {
		
        info.load(new FileInputStream("./input.properties"));
        Logger logger = Logger.getLogger("");logger.setLevel(Level.OFF);
        
		String us_currency_symbol = info.getProperty("simbol");
		String url = info.getProperty("uri");
		by =info.getProperty("id");
		
		driver = new WebClient();
		HtmlPage index_page = driver.getPage(url);

		String price = getValueHtmlUnit(index_page, by);
		String regex = info.getProperty("regx");
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(price);
		m.find();

		double original_price = Double.parseDouble(m.group(1).replace("$", ""));
		
		String simbol1ForPairCode = info.getProperty("usd");
		String simbol2ForPairCode = Common.getGeoValue1(ip);
		String rate = Common.CurrencyRate(simbol1ForPairCode + simbol2ForPairCode);
		double rate01 = Double.parseDouble(rate);
		double actualPrice = new BigDecimal(original_price * rate01).setScale(2, RoundingMode.HALF_UP).doubleValue();
		String countrySimbol = Common.getGeoValue2(ip);
		
		System.out.println("US Price: " + us_currency_symbol + original_price + "; " + "for country: " + countrySimbol
				+ " " + actualPrice);
		driver.close();
	}

}
