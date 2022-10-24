package server.repository;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import server.logic.ChartDataLogic;
import server.model.ChartData;
import util.FileUtil;

@Repository
public class ChartDataRepository {
	/**
	 * クラス。
	 */
	private static Class<?> clazz = MethodHandles.lookup().lookupClass();
	/**
	 * ロガー。
	 */
	private static Log logger = LogFactory.getLog(clazz);
	/**
	 * 基準パス。
	 */
	private static final String DIRPATH = "/tmp/server/chart/";
	/**
	 * PUSH APIで受信したティックデータファイル名。
	 */
	private static final String CHART_CSV_FILENAME = "ChartData.csv";

	/**
	 * 初期化済フラグ。
	 */
	private boolean bInit = false;
	/**
	 * チャートデータを管理する。
	 */
	private Map<String, ChartDataLogic> chartMap = new TreeMap<>();

	/**
	 * 銘柄コードのリストを取得する。
	 * 
	 * @return 銘柄コードのリスト。
	 */
	public synchronized List<String> list() {
		load();
		List<String> codes = new ArrayList<>(chartMap.keySet());
		return codes;
	}

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
	 * チャートデータのリストを取得する。
	 * 
	 * @param code     銘柄コード。
	 * @param filename ファイル名。
	 * @return チャートデータのリスト。
	 */
	public synchronized List<String> lines(String code) {
		ChartDataLogic cdl = loadChartData(code);
		return cdl.list();
	}

	/**
	 * チャートDBの概要を取得する。
	 * 
	 * @return チャートDBの一覧。
	 */
	public synchronized String toSummaryString() {
		StringBuilder sb = new StringBuilder();
		int total = 0;
		for (String key : chartMap.keySet()) {
			ChartDataLogic cdl = chartMap.get(key);
			total += cdl.count();
		}
		sb.append("File: ").append(chartMap.size()).append(" , Line: ").append(total);
		return sb.toString();
	}

	/**
	 * チャートデータの一覧を取得する。
	 * 
	 * @return チャートデータの一覧。
	 */
	public synchronized String toString() {
		StringBuilder sb = new StringBuilder();
		for (String key : chartMap.keySet()) {
			ChartDataLogic cdl = chartMap.get(key);
			sb.append(key).append(":").append(cdl.count()).append("\r\n");
		}
		return sb.toString();
	}

	/**
	 * すべてのファイルをロードする。
	 */
	public synchronized void load() {
		if (bInit) {
			return;
		}
		bInit = true;
		List<String> dirs = FileUtil.listDirs(DIRPATH);
		logger.info("load(): " + dirs);
		for (String code : dirs) {
			loadChartData(code);
		}
	}

	/**
	 * すべてのファイルをリフレッシュする。
	 */
	public synchronized void refresh() {
		bInit = true;
		List<String> dirs = FileUtil.listDirs(DIRPATH);
		logger.info("refresh(): " + dirs);
		for (String code : dirs) {
			loadChartData(code);
		}
	}

	/**
	 * すべてのファイルをリロードする。
	 */
	public synchronized void reload() {
		bInit = true;
		chartMap.clear();
		List<String> dirs = FileUtil.listDirs(DIRPATH);
		logger.info("reload(): " + dirs);
		for (String code : dirs) {
			loadChartData(code);
		}
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
		String filepath = dirpath + "/" + CHART_CSV_FILENAME;
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
		String filepath = dirpath + "/" + CHART_CSV_FILENAME;
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
		String filepath = DIRPATH + code + "/" + CHART_CSV_FILENAME;
		List<String> lines = FileUtil.readAllLines(filepath);
		cdl = new ChartDataLogic(code, lines);
		chartMap.put(code, cdl);
		logger.info("loadChartData(" + code + "): lines.size=" + lines.size());
		return cdl;
	}

}
