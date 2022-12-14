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

import logic.CalendarLogic;
import server.logic.ChartDbLogic;
import server.model.ChartDb;
import util.AppCommon;
import util.FileUtil;

@Repository
public class ChartDbRepository extends AppCommon {
	/**
	 * クラス。
	 */
	private static Class<?> clazz = MethodHandles.lookup().lookupClass();
	/**
	 * ロガー。
	 */
	private static Log logger = LogFactory.getLog(clazz);

	/**
	 * 初期化済フラグ。
	 */
	private boolean bInit = false;
	/**
	 * チャートDBを管理する。
	 */
	private Map<String, ChartDbLogic> chartMap = new TreeMap<>();

	/**
	 * 「銘柄コードとファイル名」のリストを取得する。
	 * 
	 * @return 「銘柄コードとファイル名」のリスト。
	 */
	public synchronized List<String> list() {
		load();
		List<String> codes = new ArrayList<>(chartMap.keySet());
		return codes;
	}

	/**
	 * 件数を取得する。
	 * 
	 * @param code     銘柄コード。
	 * @param filename ファイル名。
	 * @return 件数。
	 */
	public synchronized int count(String code, String filename) {
		ChartDbLogic cdl = loadChartDb(code, filename);
		return cdl.count();
	}

	/**
	 * チャートデータのリストを取得する。
	 * 
	 * @param code     銘柄コード。
	 * @param filename ファイル名。
	 * @return チャートデータのリスト。
	 */
	public synchronized List<String> lines(String code, String filename) {
		ChartDbLogic cdl = loadChartDb(code, filename);
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
			ChartDbLogic cdl = chartMap.get(key);
			total += cdl.count();
		}
		sb.append("File: ").append(chartMap.size()).append(" , Line: ").append(total);
		return sb.toString();
	}

	/**
	 * チャートDBの一覧を取得する。
	 * 
	 * @return チャートDBの一覧。
	 */
	public synchronized String toString() {
		StringBuilder sb = new StringBuilder();
		for (String key : chartMap.keySet()) {
			ChartDbLogic cdl = chartMap.get(key);
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
		loadAll("load");
	}

	/**
	 * すべてのファイルをリフレッシュする。
	 */
	public synchronized void refresh() {
		loadAll("refresh");
	}

	/**
	 * すべてのファイルをリロードする。
	 */
	public synchronized void reload() {
		chartMap.clear();
		loadAll("reload");
	}

	/**
	 * load(), refresh(), reload()の共通ロード。
	 * 
	 * @param method メソッド名。
	 */
	private synchronized void loadAll(String method) {
		bInit = true;
		FileUtil.mkdirs(SERVER_DB_DIR_PATH);
		{
			ChartDbLogic cdl = loadChartDb("", CalendarLogic.DB_FILENAME);
			List<String> lines = cdl.list();
			CalendarLogic.initCalendar(lines);
		}
		List<String> dirs = FileUtil.listDirs(SERVER_DB_DIR_PATH);
		logger.info(method + "(): " + dirs);
		for (String code : dirs) {
			List<String> files = FileUtil.listFiles(SERVER_DB_DIR_PATH + code);
			logger.info(method + "(" + code + "): " + files);
			for (String name : files) {
				loadChartDb(code, name);
			}
		}
	}

	/**
	 * チャートDBをファイルに上書き保存する。
	 * 
	 * @param cd チャートDB。
	 * @return 保存したレコード数。
	 * @throws IOException 
	 */
	public synchronized int write(ChartDb cd) throws IOException {
		ChartDbLogic cdl = loadChartDb(cd.code, cd.filename);
		cdl.clear();
		String filepath;
		if (cd.code.length() > 0) {
			String dirpath = SERVER_DB_DIR_PATH + cd.code;
			FileUtil.mkdirs(dirpath);
			filepath = dirpath + "/" + cd.filename;
		} else {
			FileUtil.mkdirs(SERVER_DB_DIR_PATH);
			filepath = SERVER_DB_DIR_PATH + cd.filename;
		}
		try (PrintWriter pw = FileUtil.writer(filepath, FileUtil.UTF8)) {
			int writeCnt = 0;
			for (String s : cd.list) {
				cdl.append(s);
				pw.println(s);
				writeCnt++;
			}
			if (cd.code.length() == 0 && cd.filename.equals(CalendarLogic.DB_FILENAME)) {
				CalendarLogic.initCalendar(cdl.list());
			}
			return writeCnt;
		}
	}

	/**
	 * チャートDBを取得する。
	 * 
	 * @param code     銘柄コード。
	 * @param filename ファイル名。
	 * @return チャートDBを管理するクラス。
	 */
	private synchronized ChartDbLogic loadChartDb(String code, String filename) {
		String key = code + "/" + filename;
		ChartDbLogic cdl = chartMap.get(key);
		if (cdl != null) {
			return cdl;
		}
		String filepath;
		if (code.length() > 0) {
			String dirpath = SERVER_DB_DIR_PATH + code;
			filepath = dirpath + "/" + filename;
		} else {
			filepath = SERVER_DB_DIR_PATH + filename;
		}
		List<String> lines = FileUtil.readAllLines(filepath);
		cdl = new ChartDbLogic(code, filename, lines);
		chartMap.put(key, cdl);
		logger.info("loadChartDb(" + key + "): lines.size=" + lines.size());
		return cdl;
	}

}
