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
	 */
	public ChartData() {
		this.list = new ArrayList<>();
	}

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
	 * コンストラクタ。
	 * 
	 * @param code  銘柄コード。
	 * @param lines CSVレコードのリスト。
	 */
	public ChartData(String code, List<String> lines) {
		this.code = code;
		this.list = lines;
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
		return "ChartData [code=" + code + ", list=" + list + "]";
	}

}
