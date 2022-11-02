package v45.t;

import bean.TriggerIndicatorInfo_r11;
import v38.factory.IndicatorCode;
import v45.TriggerIndicatorCommon_r17;
import v45.i.TriggerIndicator_r17;

/**
 * テクニカル指標(SMA5,SMA25,SMA75)からイベントトリガーを発火するクラス。
 */
public class TriggerIndicator1_r17 extends TriggerIndicatorCommon_r17 implements TriggerIndicator_r17 {
	/**
	 * トリガー種別：ゴールデンクロス。
	 */
	public static final String GC = "GC";
	/**
	 * トリガー種別：デッドクロス。
	 */
	public static final String DC = "DC";
	
	/**
	 * コンストラクタ。
	 * 
	 * @param name ディレクトリ名。
	 * @param bar  足名。
	 */
	public TriggerIndicator1_r17(String name, String bar) {
		super(name, bar, 3, null);
	}

	/**
	 * テクニカル指標の種別コードを取得する。
	 * 
	 * @return テクニカル指標の種別コード。
	 */
	public IndicatorCode getCode() {
		return IndicatorCode.SMA;
	}

	/**
	 * テクニカル指標をチェックする。
	 */
	protected void checkIndicator() {
		for (int i = 1; i < indicatorList.size(); i++) {
			TriggerIndicatorInfo_r11 tii1 = indicatorList.get(i);
			TriggerIndicatorInfo_r11 tii0 = indicatorList.get(i - 1);
			String date = tii1.date;
			double mean11 = tii1.values[0];
			double mean10 = tii0.values[0];
			double mean21 = tii1.values[1];
			double mean20 = tii0.values[1];
			double mean31 = tii1.values[2];
			double mean30 = tii0.values[2];
			if (mean10 < mean20 && mean11 > mean21) {
				String msg = String.format("SMA5  > SMA25 (%.2f %.2f)->(%.2f %.2f)", mean10, mean20, mean11, mean21);
				addEvent(date, GC, msg);
			}
			if (mean10 > mean20 && mean11 < mean21) {
				String msg = String.format("SMA5  < SMA25 (%.2f %.2f)->(%.2f %.2f)", mean10, mean20, mean11, mean21);
				addEvent(date, DC, msg);
			}
			if (mean20 < mean30 && mean21 > mean31) {
				String msg = String.format("SMA25 > SMA75 (%.2f %.2f)->(%.2f %.2f)", mean20, mean30, mean21, mean31);
				addEvent(date, GC, msg);
			}
			if (mean20 > mean30 && mean21 < mean31) {
				String msg = String.format("SMA25 < SMA75 (%.2f %.2f)->(%.2f %.2f)", mean20, mean30, mean21, mean31);
				addEvent(date, DC, msg);
			}
			if (mean10 < mean30 && mean11 > mean31) {
				String msg = String.format("SMA5  > SMA75 (%.2f %.2f)->(%.2f %.2f)", mean10, mean30, mean11, mean31);
				addEvent(date, GC, msg);
			}
			if (mean10 > mean30 && mean11 < mean31) {
				String msg = String.format("SMA5  < SMA75 (%.2f %.2f)->(%.2f %.2f)", mean10, mean30, mean11, mean31);
				addEvent(date, DC, msg);
			}
		}
	}

}
