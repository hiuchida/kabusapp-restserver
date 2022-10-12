package test;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import server.model.ChartData;
import util.HttpUtil;

public class MainTestClient {
	/**
	 * ChartData APIのURI。
	 */
	public static final String CHARTDATA_URI = "http://localhost:8080/chartData/r1";

	public static void main(String[] args) throws IOException {
		ChartData cd = testData();
		String data = jsonData(cd);
		HttpUtil.postJson(CHARTDATA_URI, data);
	}

	private static ChartData testData() {
		ChartData cd = new ChartData("123");
		cd.addRecord("a");
		cd.addRecord("b");
		cd.addRecord("c");
		return cd;
	}

	private static String jsonData(ChartData cd) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(cd);
		return json;
	}

}
