package v45.bean;

import util.StringUtil;

/**
 * チャート情報クラス。
 */
public class MergeChartInfo_r17 {
	/**
	 * 4本値チャートＤＢファイルのカラム数。
	 */
	public static final int MAX_DB_COLS = 6;
	/**
	 * マージファイルのカラム数。
	 */
	public static final int MAX_MERGE_COLS = 7;
	/**
	 * 時間足の場合は日時。日足の場合は日付。
	 */
	public String date;
	/**
	 * 始値。
	 */
	public int openPrice;
	/**
	 * 高値。
	 */
	public int highPrice;
	/**
	 * 安値。
	 */
	public int lowPrice;
	/**
	 * 終値。
	 */
	public int closePrice;
	/**
	 * 売買高。
	 */
	public int tradeVolume;
	/**
	 * データフラグ。0:データなし、1:4本値のデータ、2:PUSH APIで取得したデータ、3:コピーされたデータ。
	 */
	public int flag;

	/**
	 * コンストラクタ（データなし）。
	 * 
	 * @param date 時間足の場合は日時。日足の場合は日付。
	 */
	public MergeChartInfo_r17(String date) {
		this.date = date;
		this.openPrice = 0;
		this.highPrice = 0;
		this.lowPrice = 0;
		this.closePrice = 0;
		this.tradeVolume = 0;
		this.flag = 0;
	}

	/**
	 * コンストラクタ（4本値チャートＤＢファイル）。
	 * 
	 * @param cols 4本値チャートＤＢファイルの1レコードの全てのカラム文字列。
	 */
	public MergeChartInfo_r17(String[] cols) {
		int i = 0;
		this.date = cols[i++];
		this.openPrice = StringUtil.parseInt(cols[i++]);
		this.highPrice = StringUtil.parseInt(cols[i++]);
		this.lowPrice = StringUtil.parseInt(cols[i++]);
		this.closePrice = StringUtil.parseInt(cols[i++]);
		this.tradeVolume = StringUtil.parseInt(cols[i++]);
		if (cols.length == MAX_DB_COLS) { // MainMergeChartData向け
			this.flag = 1;
		} else { // MainCalcIndicator向け
			this.flag = StringUtil.parseInt(cols[i++]);
		}
	}

	/**
	 * コンストラクタ（PUSH APIで受信したチャートデータ）。
	 * 
	 * @param date   時間足の場合は日時。日足の場合は日付。
	 * @param price  現値。
	 * @param volume 売買高。
	 */
	public MergeChartInfo_r17(String date, int price, int volume) {
		this.date = date;
		this.openPrice = price;
		this.highPrice = price;
		this.lowPrice = price;
		this.closePrice = price;
		this.tradeVolume = volume;
		this.flag = 2;
	}

	/**
	 * コンストラクタ（別のチャートデータをコピーする）。
	 * 
	 * @param date   時間足の場合は日時。日足の場合は日付。
	 * @param price  現値。
	 * @param volume 売買高。
	 * @param flag   データフラグ。0:データなし、1:4本値のデータ、2:PUSH APIで取得したデータ、3:コピーされたデータ。
	 */
	public MergeChartInfo_r17(String date, int price, int volume, int flag) {
		this.date = date;
		this.openPrice = price;
		this.highPrice = price;
		this.lowPrice = price;
		this.closePrice = price;
		this.tradeVolume = volume;
		this.flag = flag;
	}

	/**
	 * マージしたチャートデータファイルのヘッダ文字列を生成する。
	 * 
	 * @return ヘッダ文字列。
	 */
	public static String toHeaderString() {
		String[] sa = new String[MAX_MERGE_COLS];
		int i = 0;
		sa[i++] = "date             ";
		sa[i++] = "open";
		sa[i++] = "high";
		sa[i++] = "low";
		sa[i++] = "close";
		sa[i++] = "volume";
		sa[i++] = "flag";
		String val = "# " + StringUtil.joinTab(sa);
		return val;
	}

	/**
	 * インスタンスの主キー(date)を取得する。
	 * 
	 * @return 主キー。
	 */
	public String getKey() {
		return date;
	}

	/**
	 * マージしたチャートデータファイルのレコード文字列を生成する。
	 * 
	 * @return レコード文字列。
	 */
	public String toLineString() {
		String[] sa = new String[MAX_MERGE_COLS];
		int i = 0;
		sa[i++] = date;
		sa[i++] = "" + openPrice;
		sa[i++] = "" + highPrice;
		sa[i++] = "" + lowPrice;
		sa[i++] = "" + closePrice;
		sa[i++] = "" + tradeVolume;
		sa[i++] = "" + flag;
		String val = StringUtil.joinTab(sa);
		return val;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{date=").append(date);
		sb.append(", close=").append(closePrice);
		sb.append(", flag=").append(flag);
		sb.append("}");
		return sb.toString();
	}

}
