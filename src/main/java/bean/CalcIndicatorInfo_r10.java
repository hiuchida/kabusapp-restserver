package bean;

/**
 * テクニカル指標を計算した結果のテクニカル指標情報クラス。
 */
public class CalcIndicatorInfo_r10 {
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
	 * 計算値の配列。
	 */
	public double[] values;

	/**
	 * コンストラクタ。
	 * 
	 * @param mci 対象日のチャートデータ。
	 */
	public CalcIndicatorInfo_r10(MergeChartInfo_r10 mci) {
		this.date = mci.date;
		this.openPrice = mci.openPrice;
		this.highPrice = mci.highPrice;
		this.lowPrice = mci.lowPrice;
		this.closePrice = mci.closePrice;
		this.tradeVolume = mci.tradeVolume;
		this.flag = mci.flag;
	}

	/**
	 * コンストラクタ。
	 * 
	 * @param mci 対象日のチャートデータ。
	 * @param d1  計算値1。
	 */
	public CalcIndicatorInfo_r10(MergeChartInfo_r10 mci, double d1) {
		this(mci);
		this.values = new double[1];
		values[0] = d1;
	}

	/**
	 * コンストラクタ。
	 * 
	 * @param mci 対象日のチャートデータ。
	 * @param d1  計算値1。
	 * @param d2  計算値2。
	 */
	public CalcIndicatorInfo_r10(MergeChartInfo_r10 mci, double d1, double d2) {
		this(mci);
		this.values = new double[2];
		values[0] = d1;
		values[1] = d2;
	}

	/**
	 * コンストラクタ。
	 * 
	 * @param mci 対象日のチャートデータ。
	 * @param d1  計算値1。
	 * @param d2  計算値2。
	 * @param d3  計算値3。
	 */
	public CalcIndicatorInfo_r10(MergeChartInfo_r10 mci, double d1, double d2, double d3) {
		this(mci);
		this.values = new double[3];
		values[0] = d1;
		values[1] = d2;
		values[2] = d3;
	}

	/**
	 * コンストラクタ。
	 * 
	 * @param mci 対象日のチャートデータ。
	 * @param d1  計算値1。
	 * @param d2  計算値2。
	 * @param d3  計算値3。
	 * @param d4  計算値4。
	 */
	public CalcIndicatorInfo_r10(MergeChartInfo_r10 mci, double d1, double d2, double d3, double d4) {
		this(mci);
		this.values = new double[4];
		values[0] = d1;
		values[1] = d2;
		values[2] = d3;
		values[3] = d4;
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
