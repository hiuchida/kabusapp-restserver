package v39.t;

import v38.factory.IndicatorCode;
import v39.TriggerIndicatorCommon_r11;
import v39.i.TriggerIndicator_r11;

/**
 * テクニカル指標(パラボリック)からイベントトリガーを発火するクラス。
 */
public class TriggerIndicator7_r11 extends TriggerIndicatorCommon_r11 implements TriggerIndicator_r11 {

	/**
	 * コンストラクタ。
	 * 
	 * @param name ディレクトリ名。
	 * @param bar  足名。
	 */
	public TriggerIndicator7_r11(String name, String bar) {
		super(name, bar, 4, "2:LorS");
	}

	/**
	 * テクニカル指標の種別コードを取得する。
	 * 
	 * @return テクニカル指標の種別コード。
	 */
	public IndicatorCode getCode() {
		return IndicatorCode.Parabolic;
	}

	/**
	 * テクニカル指標をチェックする。
	 */
	protected void checkIndicator() {
		
	}

}
