package server.repository;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Repository;

import server.logic.ChartDataLogic;
import server.model.ChartData;
import util.FileUtil;
import util.StdoutLog;

@Repository
public class ChartDataRepository {
	/**
	 * クラス。
	 */
	private static Class<?> clazz = MethodHandles.lookup().lookupClass();
	/**
	 * 基準パス。
	 */
	private static final String DIRPATH = "/tmp/server/chart/";
	/**
	 * チャートデータファイル名。
	 */
	private static final String DB_FILENAME = "ChartData.csv";

	/**
	 * チャートデータを管理する。
	 */
	private Map<String, ChartDataLogic> chartMap = new TreeMap<>();

	/**
	 * 件数を取得する。
	 * 
	 * @param code 銘柄コード。
	 * @return 件数。
	 */
	public synchronized int count(String code) {
		ChartDataLogic cdl = loadChartData(code);
		return cdl.count();
	}

	/**
	 * チャートデータの一覧を取得する。
	 * 
	 * @return チャートデータの一覧。
	 */
	public synchronized String list() {
		StringBuilder sb = new StringBuilder();
		for (String key : chartMap.keySet()) {
			ChartDataLogic cdl = chartMap.get(key);
			sb.append(key).append(":").append(cdl.count()).append("\r\n");
		}
		return sb.toString();
	}

	/**
	 * チャートデータをファイルに追記する。
	 * 
	 * @param cd チャートデータ。
	 * @return 追記したレコード数。
	 * @throws IOException 
	 */
	public synchronized int append(ChartData cd) throws IOException {
		ChartDataLogic cdl = loadChartData(cd.code);
		String dirpath = DIRPATH + cd.code;
		FileUtil.mkdirs(dirpath);
		String filepath = dirpath + "/" + DB_FILENAME;
		try (PrintWriter pw = FileUtil.writer(filepath, FileUtil.UTF8, true)) {
			int writeCnt = 0;
			for (String s : cd.list) {
				cdl.append(s);
				pw.println(s);
				writeCnt++;
			}
			return writeCnt;
		}
	}

	/**
	 * チャートデータが存在しない場合のみファイルに追記する。
	 * 
	 * @param cd チャートデータ。
	 * @return 追記したレコード数。
	 * @throws IOException 
	 */
	public synchronized int update(ChartData cd) throws IOException {
		ChartDataLogic cdl = loadChartData(cd.code);
		String dirpath = DIRPATH + cd.code;
		FileUtil.mkdirs(dirpath);
		String filepath = dirpath + "/" + DB_FILENAME;
		try (PrintWriter pw = FileUtil.writer(filepath, FileUtil.UTF8, true)) {
			int writeCnt = 0;
			for (String s : cd.list) {
				if (!cdl.contains(s)) {
					cdl.append(s);
					pw.println(s);
					writeCnt++;
				}
			}
			return writeCnt;
		}
	}

	/**
	 * チャートデータをロードする。
	 * 
	 * @param code 銘柄コード。
	 * @return チャートデータを管理するクラス。
	 */
	private synchronized ChartDataLogic loadChartData(String code) {
		ChartDataLogic cdl = chartMap.get(code);
		if (cdl != null) {
			return cdl;
		}
		String filepath = DIRPATH + code + "/" + DB_FILENAME;
		List<String> lines = FileUtil.readAllLines(filepath);
		cdl = new ChartDataLogic(code, lines);
		chartMap.put(code, cdl);
		StdoutLog.timeprintln(clazz, "loadChartData(" + code + ")", "lines.size=" + lines.size());
		return cdl;
	}

}
