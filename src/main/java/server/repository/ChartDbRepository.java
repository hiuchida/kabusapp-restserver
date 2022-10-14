package server.repository;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Repository;

import server.logic.ChartDbLogic;
import server.model.ChartDb;
import util.FileUtil;
import util.StdoutLog;

@Repository
public class ChartDbRepository {
	/**
	 * クラス。
	 */
	private static Class<?> clazz = MethodHandles.lookup().lookupClass();
	/**
	 * 基準パス。
	 */
	private static final String DIRPATH = "/tmp/server/db/";

	/**
	 * チャートDBを管理する。
	 */
	private Map<String, ChartDbLogic> chartMap = new TreeMap<>();

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
	 * チャートDBの一覧を取得する。
	 * 
	 * @return チャートDBの一覧。
	 */
	public synchronized String list() {
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
	public void load() {
		List<String> dirs = FileUtil.listDirs(DIRPATH);
		StdoutLog.timeprintln(clazz, "load()", "" + dirs);
		for (String code : dirs) {
			List<String> files = FileUtil.listFiles(DIRPATH + code);
			StdoutLog.timeprintln(clazz, "load(" + code + ")", "" + files);
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
			String dirpath = DIRPATH + cd.code;
			FileUtil.mkdirs(dirpath);
			filepath = dirpath + "/" + cd.filename;
		} else {
			filepath = DIRPATH + cd.filename;
		}
		try (PrintWriter pw = FileUtil.writer(filepath, FileUtil.UTF8)) {
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
			String dirpath = DIRPATH + code;
			filepath = dirpath + "/" + filename;
		} else {
			filepath = DIRPATH + filename;
		}
		List<String> lines = FileUtil.readAllLines(filepath);
		cdl = new ChartDbLogic(code, filename, lines);
		chartMap.put(key, cdl);
		StdoutLog.timeprintln(clazz, "loadChartDb(" + key + ")", "lines.size=" + lines.size());
		return cdl;
	}

}
