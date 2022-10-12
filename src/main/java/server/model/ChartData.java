package server.model;

import java.util.ArrayList;
import java.util.List;

/**
 * PUSH APIから受信したチャートデータ。
 */
public class ChartData {
	/**
	 * 銘柄コード。
	 */
	public String code;
	/**
	 * CSVレコードのリスト。
	 */
	public List<String> list;

	/**
	 * コンストラクタ。
	 * 
	 * @param code 銘柄コード。
	 */
	public ChartData(String code) {
		this.code = code;
		this.list = new ArrayList<>();
	}

	/**
	 * レコードを追加する。
	 * 
	 * @param line レコード文字列。
	 */
	public void addRecord(String line) {
		list.add(line);
	}

	@Override
	public String toString() {
		return "ChartDataInfo [code=" + code + ", list=" + list + "]";
	}

}
