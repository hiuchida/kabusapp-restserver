package server.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import server.model.ChartData;
import server.repository.ChartDataRepository;

@Service
public class ChartDataService {

	@Autowired
	private ChartDataRepository chartDataRepository;

	/**
	 * チャートデータを登録する。
	 * 
	 * @param cd チャートデータ。
	 * @return レスポンス文字列。
	 */
	public String register(ChartData cd) {
		try {
			chartDataRepository.append(cd);
			System.out.println(cd);
			return "OK";
		} catch (IOException e) {
			e.printStackTrace();
			return "NG";
		}
	}

}
