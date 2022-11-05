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
	public static final String CHARTDATA_URI = "http://localhost:8080/chartData/r2";
	/**
	 * ChartDb APIのURI。
	 */
	public static final String CHARTDB_URI = "http://localhost:8080/chartDb/r1";

	public static void main(String[] args) throws IOException {
		ChartDataApi.execute();
//		ChartDbApi.execute();
	}

	public static class ChartDataApi {

		public static void execute() throws IOException {
			ChartData cd = testData();
			String data = jsonData(cd);
			HttpUtil.postJson(CHARTDATA_URI, data);
		}

		private static ChartData testData() {
			ChartData cd = new ChartData("167110019_F202211");
			cd.addRecord("2022/11/05 05:54:16.839,2022-11-05 05:54:15,27515.0,19493.0");
			cd.addRecord("2022/11/05 05:54:53.227,2022-11-05 05:54:53,27515.0,19505.0");
			cd.addRecord("2022/11/05 06:00:00.874,2022-11-05 06:00:01,27505.0,19555.0");
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
