package server.service;

import java.io.IOException;
import java.lang.invoke.MethodHandles;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
	/**
	 * ロガー。
	 */
	private static Log logger = LogFactory.getLog(clazz);

	@Autowired
	private ChartDataRepository chartDataRepository;

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
			logger.info("update(" + cd.code + "): writeCnt=" + writeCnt + ", totalCnt=" + totalCnt);
			StdoutLog.timeprintln(clazz, "update(" + cd.code + ")", "writeCnt=" + writeCnt + ", totalCnt=" + totalCnt);
			return "OK" + "-" + writeCnt + "/" + totalCnt;
		} catch (IOException e) {
			e.printStackTrace();
			return "NG";
		}
	}

}
