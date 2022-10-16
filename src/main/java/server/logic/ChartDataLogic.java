package server.logic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * チャートデータを管理するクラス。
 */
public class ChartDataLogic {
	/**
	 * 銘柄コード。
	 */
	private String code;
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
	 * @param code  銘柄コード。
	 * @param lines CSVレコードのリスト。
	 */
	public ChartDataLogic(String code, List<String> lines) {
		this.code = code;
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
