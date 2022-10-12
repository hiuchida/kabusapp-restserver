package server.service;

import java.io.IOException;
import java.lang.invoke.MethodHandles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import server.model.ChartData;
import server.repository.ChartDataRepository;
import util.StdoutLog;

@Service
public class ChartDataService {
	/**
	 * クラス。
	 */
	private static Class<?> clazz = MethodHandles.lookup().lookupClass();

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
			int writeCnt = chartDataRepository.append(cd);
			int totalCnt = chartDataRepository.count(cd.code);
			StdoutLog.timeprintln(clazz, "register(" + cd.code + ")", "writeCnt=" + writeCnt + ", totalCnt=" + totalCnt);
			return "OK" + "-" + writeCnt + "/" + totalCnt;
		} catch (IOException e) {
			e.printStackTrace();
			return "NG";
		}
	}

	/**
	 * チャートデータが存在しない場合のみ登録する。
	 * 
	 * @param cd チャートデータ。
	 * @return レスポンス文字列。
	 */
	public String update(ChartData cd) {
		try {
			int writeCnt = chartDataRepository.update(cd);
			int totalCnt = chartDataRepository.count(cd.code);
			StdoutLog.timeprintln(clazz, "update(" + cd.code + ")", "writeCnt=" + writeCnt + ", totalCnt=" + totalCnt);
			return "OK" + "-" + writeCnt + "/" + totalCnt;
		} catch (IOException e) {
			e.printStackTrace();
			return "NG";
		}
	}

}
