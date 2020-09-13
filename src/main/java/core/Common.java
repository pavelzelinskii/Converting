package core;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Properties;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Common {

	static Properties info = new Properties();

	static String getGeoValue1(String ip) throws Exception {
		info.load(new FileInputStream("./input.properties"));
		String name = info.getProperty("name1");
		String geo_url = info.getProperty("uri1");
		JSONParser jp = new JSONParser();
		URL json = new URL(geo_url + "?ip=" + ip);
		BufferedReader in = new BufferedReader(new InputStreamReader(json.openConnection().getInputStream()));
		JSONObject jo = (JSONObject) jp.parse(in);
		return (String) jo.get(name);
	}

	static String getGeoValue2(String ip) throws Exception {
		info.load(new FileInputStream("./input.properties"));
		String name = info.getProperty("name2");
		String name1 = info.getProperty("name3");
		String geo_url = info.getProperty("uri1");
		JSONParser jp = new JSONParser();
		URL json = new URL(geo_url + "?ip=" + ip);
		BufferedReader in = new BufferedReader(new InputStreamReader(json.openConnection().getInputStream()));
		JSONObject jo = (JSONObject) jp.parse(in);
		return (String) jo.get(name) + " " + jo.get(name1);
	}

	static String CurrencyRate(String pair_code) throws Exception {
		info.load(new FileInputStream("./input.properties"));
		String url_currconv = info.getProperty("uri2");
		String api_key = info.getProperty("key");
		JSONObject json = new JSONObject();
		JSONParser jp = new JSONParser();
		URL rate_url = new URL(url_currconv + "?q=" + pair_code + "&compact=ultra&apiKey=" + api_key);
		json = (JSONObject) jp
				.parse(new BufferedReader(new InputStreamReader(rate_url.openConnection().getInputStream())));
		return json.get(pair_code).toString();
	}

}
