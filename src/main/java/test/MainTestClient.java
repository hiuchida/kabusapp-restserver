package test;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import server.model.ChartData;
import server.model.ChartDb;
import util.HttpUtil;

public class MainTestClient {
	/**
	 * ChartData APIのURI。
	 */
	public static final String CHARTDATA_URI = "http://localhost:8080/chartData/r1";
	/**
	 * ChartDb APIのURI。
	 */
	public static final String CHARTDB_URI = "http://localhost:8080/chartDb/r1";

	public static void main(String[] args) throws IOException {
//		ChartDataApi.execute();
		ChartDbApi.execute();
	}

	public static class ChartDataApi {

		public static void execute() throws IOException {
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

	public static class ChartDbApi {

		public static void execute() throws IOException {
			{
				ChartDb cd = testData1();
				String data = jsonData(cd);
				HttpUtil.postJson(CHARTDB_URI, data);
			}
			{
				ChartDb cd = testData2();
				String data = jsonData(cd);
				HttpUtil.postJson(CHARTDB_URI, data);
			}
		}

		private static ChartDb testData1() {
			ChartDb cd = new ChartDb("", "123.db");
			cd.addRecord("a");
			cd.addRecord("b");
			cd.addRecord("c");
			return cd;
		}

		private static ChartDb testData2() {
			ChartDb cd = new ChartDb("123", "123.db");
			cd.addRecord("a");
			cd.addRecord("b");
			cd.addRecord("c");
			return cd;
		}

		private static String jsonData(ChartDb cd) throws JsonProcessingException {
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(cd);
			return json;
		}

	}

}
