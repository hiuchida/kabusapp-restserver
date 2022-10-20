package v39.bean;

import util.StringUtil;
import v38.bean.CalcIndicatorInfo_r10;

/**
 * テクニカル指標を計算した結果のテクニカル指標情報クラス。
 */
public class TriggerIndicatorInfo_r11 {
	/**
	 * 共通項目のカラム数。
	 */
	public static final int MAX_COMMON_COLS = 3;
	/**
	 * 時間足の場合は日時。日足の場合は日付。
	 */
	public String date;
	/**
	 * 終値。
	 */
	public int closePrice;
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
	 * @param cols テクニカル指標stdoutファイルの1レコードの全てのカラム文字列。
	 */
	public TriggerIndicatorInfo_r11(String[] cols) {
		int i = 0;
		this.date = cols[i++];
		this.closePrice = StringUtil.parseInt(cols[i++]);
		this.flag = StringUtil.parseInt(cols[i++]);
		this.values = new double[cols.length - MAX_COMMON_COLS];
		for (; i < cols.length; i++) {
			this.values[i - MAX_COMMON_COLS] = StringUtil.parseDouble(cols[i]);
		}
	}

	/**
	 * コンストラクタ。
	 * 
	 * @param cols テクニカル指標stdoutファイルの1レコードの全てのカラム文字列。
	 * @param type 例外処理タイプ。
	 */
	public TriggerIndicatorInfo_r11(String[] cols, String type) {
		int i = 0;
		this.date = cols[i++];
		this.closePrice = StringUtil.parseInt(cols[i++]);
		this.flag = StringUtil.parseInt(cols[i++]);
		this.values = new double[cols.length - MAX_COMMON_COLS];
		for (; i < cols.length; i++) {
			if ("2:LorS".equals(type) && i == 1 + MAX_COMMON_COLS) {
				if ("L".equals(cols[i])) {
					this.values[i - MAX_COMMON_COLS] = 1;
				} else if ("S".equals(cols[i])) {
					this.values[i - MAX_COMMON_COLS] = -1;
				} else {
					this.values[i - MAX_COMMON_COLS] = 0;
					System.out.println("Warning: Unknown LorS " + cols[i]);
				}
			} else if ("2:HorL".equals(type) && i == 1 + MAX_COMMON_COLS) {
				if ("H".equals(cols[i])) {
					this.values[i - MAX_COMMON_COLS] = 1;
				} else if ("L".equals(cols[i])) {
					this.values[i - MAX_COMMON_COLS] = -1;
				} else {
					this.values[i - MAX_COMMON_COLS] = 0;
					System.out.println("Warning: Unknown HorL " + cols[i]);
				}
			} else {
				this.values[i - MAX_COMMON_COLS] = StringUtil.parseDouble(cols[i]);
			}
		}
	}

	/**
	 * コンストラクタ。
	 * 
	 * @param cii テクニカル指標。
	 */
	public TriggerIndicatorInfo_r11(CalcIndicatorInfo_r10 cii) {
		this.date = cii.date;
		this.closePrice = cii.closePrice;
		this.flag = cii.flag;
		this.values = new double[cii.values.length];
		for (int i = 0; i < cii.values.length; i++) {
			this.values[i] = cii.values[i];
		}
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
