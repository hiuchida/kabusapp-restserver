package server.repository;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import server.logic.IndicatorDataLogic;
import util.StringUtil;

@Repository
public class IndicatorDataRepository {
	/**
	 * クラス。
	 */
	private static Class<?> clazz = MethodHandles.lookup().lookupClass();
	/**
	 * ロガー。
	 */
	private static Log logger = LogFactory.getLog(clazz);

	/**
	 * チャートデータを管理する。
	 */
	private Map<String, IndicatorDataLogic> chartMap = new TreeMap<>();

	/**
	 * 「銘柄コードのみ」のリストを取得する。
	 * 
	 * @return 「銘柄コードのみ」のリスト。
	 */
	public synchronized List<String> list() {
		Set<String> codeSet = new TreeSet<>();
		for (String key : chartMap.keySet()) {
			String code = StringUtil.parseString(key, "/");
			codeSet.add(code);
		}
		List<String> codes = new ArrayList<>(codeSet);
		return codes;
	}

	/**
	 * チャートデータのリストを取得する。
	 * 
	 * @param code     銘柄コード。
	 * @param filename ファイル名。
	 * @return チャートデータのリスト。
	 */
	public synchronized List<String> lines(String code, String filename) {
		String key = code + "/" + filename;
		IndicatorDataLogic idl = chartMap.get(key);
		if (idl == null) {
			return new ArrayList<>();
		}
		return idl.list();
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
			IndicatorDataLogic idl = chartMap.get(key);
			total += idl.count();
		}
		sb.append("Code: ").append(chartMap.size()).append(" , File: ").append(total);
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
			IndicatorDataLogic idl = chartMap.get(key);
			sb.append(key).append(":").append(idl.count()).append("\r\n");
		}
		return sb.toString();
	}

	/**
	 * マージデータをメモリに保存する。
	 * 
	 * @param code     銘柄コード。
	 * @param filename ファイル名。
	 * @param lines    チャートデータのリスト。
	 * @return 保存した件数。
	 */
	public synchronized int writeAllLines(String code, String filename, List<String> lines) {
		String key = code + "/" + filename;
		IndicatorDataLogic idl = new IndicatorDataLogic(code, filename, lines);
		chartMap.put(key, idl);
		return lines.size();
	}

}
