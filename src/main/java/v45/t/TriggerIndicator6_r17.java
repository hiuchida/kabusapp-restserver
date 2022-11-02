package v45.t;

import v38.factory.IndicatorCode;
import v45.TriggerIndicatorCommon_r17;
import v45.i.TriggerIndicator_r17;

/**
 * テクニカル指標(DMI)からイベントトリガーを発火するクラス。
 */
public class TriggerIndicator6_r17 extends TriggerIndicatorCommon_r17 implements TriggerIndicator_r17 {

	/**
	 * コンストラクタ。
	 * 
	 * @param name ディレクトリ名。
	 * @param bar  足名。
	 */
	public TriggerIndicator6_r17(String name, String bar) {
		super(name, bar, 4, null);
	}

	/**
	 * テクニカル指標の種別コードを取得する。
	 * 
	 * @return テクニカル指標の種別コード。
	 */
	public IndicatorCode getCode() {
		return IndicatorCode.DMI;
	}

	/**
	 * テクニカル指標をチェックする。
	 */
	protected void checkIndicator() {
		
	}

}
