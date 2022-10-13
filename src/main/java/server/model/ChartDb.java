package server.model;

import java.util.ArrayList;
import java.util.List;

/**
 * チャートDB。
 */
public class ChartDb {
	/**
	 * 銘柄コード。
	 */
	public String code;
	/**
	 * ファイル名。
	 */
	public String filename;
	/**
	 * CSVレコードのリスト。
	 */
	public List<String> list;

	/**
	 * コンストラクタ。
	 */
	public ChartDb() {
		this.list = new ArrayList<>();
	}

	/**
	 * コンストラクタ。
	 *
	 * @param code     銘柄コード。
	 * @param filename ファイル名。
	 */
	public ChartDb(String code, String filename) {
		this.code = code;
		this.filename = filename;
		this.list = new ArrayList<>();
	}

	/**
	 * コンストラクタ。
	 * 
	 * @param code     銘柄コード。
	 * @param filename ファイル名。
	 * @param lines    CSVレコードのリスト。
	 */
	public ChartDb(String code, String filename, List<String> lines) {
		this.code = code;
		this.filename = filename;
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
		return "ChartDb [code=" + code + ", filename=" + filename + ", list=" + list + "]";
	}

}
