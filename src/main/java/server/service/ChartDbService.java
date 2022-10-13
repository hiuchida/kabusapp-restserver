package server.service;

import java.io.IOException;
import java.lang.invoke.MethodHandles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import server.model.ChartDb;
import server.repository.ChartDbRepository;
import util.StdoutLog;

@Service
public class ChartDbService {
	/**
	 * クラス。
	 */
	private static Class<?> clazz = MethodHandles.lookup().lookupClass();

	@Autowired
	private ChartDbRepository chartDbRepository;

	/**
	 * チャートDBを登録する。
	 * 
	 * @param cd チャートDB。
	 * @return レスポンス文字列。
	 */
	public String register(ChartDb cd) {
		try {
			int writeCnt = chartDbRepository.write(cd);
			int totalCnt = chartDbRepository.count(cd.code, cd.filename);
			StdoutLog.timeprintln(clazz, "register(" + cd.code + "/" + cd.filename + ")", "writeCnt=" + writeCnt + ", totalCnt=" + totalCnt);
			return "OK" + "-" + writeCnt + "/" + totalCnt;
		} catch (IOException e) {
			e.printStackTrace();
			return "NG";
		}
	}

}
