package test;

import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import server.model.ChartData;
import util.HttpUtil;

public class MainTestClient {

	public static void main(String[] args) throws IOException {
		ChartData cd = testData();
		String data = jsonData(cd);
		HttpUtil.postJson("http://localhost:8080/chartData/r1", data);
	}

	private static ChartData testData() {
		ChartData cd = new ChartData();
		cd.code = "123";
		cd.list = new ArrayList<>();
		cd.list.add("a");
		cd.list.add("b");
		cd.list.add("c");
		return cd;
	}

	private static String jsonData(ChartData cd) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(cd);
		return json;
	}

}
