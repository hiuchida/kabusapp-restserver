package server.model;

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

	@Override
	public String toString() {
		return "ChartDataInfo [code=" + code + ", list=" + list + "]";
	}

}
