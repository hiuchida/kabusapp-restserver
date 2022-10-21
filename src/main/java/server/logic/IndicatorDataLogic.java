package server.logic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * テクニカル指標データを管理するクラス。
 */
public class IndicatorDataLogic {
	/**
	 * 銘柄コード。
	 */
	private String code;
	/**
	 * ファイル名。
	 */
	private String filename;
	/**
	 * チャートデータのリスト。
	 */
	private List<String> chartList = null;
	/**
	 * チャートデータのセット。
	 */
	private Set<String> chartSet = null;

	/**
	 * コンストラクタ。
	 * 
	 * @param code     銘柄コード。
	 * @param filename ファイル名。
	 * @param lines    CSVレコードのリスト。
	 */
	public IndicatorDataLogic(String code, String filename, List<String> lines) {
		this.code = code;
		this.filename = filename;
		this.chartList = new ArrayList<>(lines);
		this.chartSet = new HashSet<>(lines);
	}

	/**
	 * リスト件数を取得する。
	 * 
	 * @return 件数。
	 */
	public int count() {
		return chartList.size();
	}

	/**
	 * リストを取得する。
	 * 
	 * @return リスト。
	 */
	public List<String> list() {
		return chartList;
	}

	/**
	 * リストをクリアする。
	 */
	public void clear() {
		chartList.clear();
		chartSet.clear();
	}

	/**
	 * 指定されたCSVレコードが存在するかチェックする。
	 * 
	 * @param s CSVレコード文字列。
	 * @return true:存在する、false:存在しない。
	 */
	public boolean contains(String s) {
		return chartSet.contains(s);
	}

	/**
	 * 無条件にCSVレコードを追加する。
	 * 
	 * @param s CSVレコード文字列。
	 */
	public void append(String s) {
		chartList.add(s);
		chartSet.add(s);
	}

}
