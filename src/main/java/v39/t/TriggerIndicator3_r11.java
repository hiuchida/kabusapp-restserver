package v39.t;

import v38.factory.IndicatorCode;
import v39.TriggerIndicatorCommon_r11;
import v39.i.TriggerIndicator_r11;

/**
 * テクニカル指標(MACD(5,20,9))からイベントトリガーを発火するクラス。
 */
public class TriggerIndicator3_r11 extends TriggerIndicatorCommon_r11 implements TriggerIndicator_r11 {

	/**
	 * コンストラクタ。
	 * 
	 * @param name ディレクトリ名。
	 * @param bar  足名。
	 */
	public TriggerIndicator3_r11(String name, String bar) {
		super(name, bar, 4, null);
	}

	/**
	 * テクニカル指標の種別コードを取得する。
	 * 
	 * @return テクニカル指標の種別コード。
	 */
	public IndicatorCode getCode() {
		return IndicatorCode.MACD;
	}

	/**
	 * テクニカル指標をチェックする。
	 */
	protected void checkIndicator() {
		
	}

}
