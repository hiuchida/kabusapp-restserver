package v39.t;

import v38.factory.IndicatorCode;
import v39.TriggerIndicatorCommon_r11;
import v39.i.TriggerIndicator_r11;

/**
 * テクニカル指標(HV20)からイベントトリガーを発火するクラス。
 */
public class TriggerIndicator4_r11 extends TriggerIndicatorCommon_r11 implements TriggerIndicator_r11 {

	/**
	 * コンストラクタ。
	 * 
	 * @param name ディレクトリ名。
	 * @param bar  足名。
	 */
	public TriggerIndicator4_r11(String name, String bar) {
		super(name, bar, 2, null);
	}

	/**
	 * テクニカル指標の種別コードを取得する。
	 * 
	 * @return テクニカル指標の種別コード。
	 */
	public IndicatorCode getCode() {
		return IndicatorCode.HV;
	}

	/**
	 * テクニカル指標をチェックする。
	 */
	protected void checkIndicator() {
		
	}

}
