package v45.bean;

import util.DateTimeUtil;
import util.StringUtil;

/**
 * イベントトリガー情報。
 */
public class EventInfo_r17 implements Comparable<EventInfo_r17> {
	/**
	 * イベントトリガー情報のカラム数。
	 */
	public static final int MAX_COLS = 8;
	/**
	 * ID
	 */
	public String id;
	/**
	 * 作成日時。
	 */
	public String createDate;
	/**
	 * ディレクトリ名。
	 */
	public String name;
	/**
	 * 足名。
	 */
	public String bar;
	/**
	 * テクニカル指標の種別。
	 */
	public String indicator;
	/**
	 * 日時。
	 */
	public String date;
	/**
	 * トリガー種別。
	 */
	public String type;
	/**
	 * レポート本文。
	 */
	public String report;

	/**
	 * コンストラクタ。
	 * 
	 * @param name      ディレクトリ名。
	 * @param bar       足名。
	 * @param indicator テクニカル指標の種別。
	 * @param date      日付。
	 * @param type      トリガー種別。
	 * @param report    レポート本文。
	 */
	public EventInfo_r17(String name, String bar, String indicator, String date, String type, String report) {
		this.id = makeId(name, bar, indicator, date, type);
		this.createDate = DateTimeUtil.nowToString();
		this.name = name;
		this.bar = bar;
		this.indicator = indicator;
		this.date = date;
		this.type = type;
		this.report = report;
	}

	/**
	 * 複数のパラメータからID文字列を作成する。
	 * 
	 * @param name      ディレクトリ名。
	 * @param bar       足名。
	 * @param indicator テクニカル指標の種別。
	 * @param date      日付。
	 * @param type      トリガー種別。
	 * @return ID文字列。
	 */
	private String makeId(String name, String bar, String indicator, String date, String type) {
		String d = date.substring(0, 4) + date.substring(5, 7) + date.substring(8, 10);
		String t = date.substring(11, 13) + date.substring(14, 16) + date.substring(17, 19);
		return name + "_" + d + "_" + t + "_" + bar + "_" + indicator + "_" + type;
	}

	/**
	 * マージしたチャートデータファイルのレコード文字列を生成する。
	 * 
	 * @return レコード文字列。
	 */
	public String toLineString() {
		String[] sa = new String[MAX_COLS];
		int i = 0;
		sa[i++] = id;
		sa[i++] = createDate;
		sa[i++] = name;
		sa[i++] = bar;
		sa[i++] = indicator;
		sa[i++] = date;
		sa[i++] = type;
		sa[i++] = report;
		String val = StringUtil.joinTab(sa);
		return val;
	}

	@Override
	public int compareTo(EventInfo_r17 that) {
		return this.id.compareTo(that.id);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{id=").append(id);
		sb.append(", createDate=").append(createDate);
		sb.append(", report=").append(report);
		sb.append("}");
		return sb.toString();
	}

}
