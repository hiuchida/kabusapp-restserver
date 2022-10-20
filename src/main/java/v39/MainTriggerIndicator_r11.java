package v39;

import java.util.ArrayList;
import java.util.List;

import v38.factory.BarNameFactory_r10;

/**
 * テクニカル指標からイベントトリガーを発火するクラス。
 */
public class MainTriggerIndicator_r11 {
	/**
	 * テクニカル指標からイベントトリガーを発火するクラス。
	 */
	private List<TriggerCoordinator_r11> triggerList = new ArrayList<>();

	/**
	 * コンストラクタ。
	 * 
	 * @param name ディレクトリ名。
	 */
	public MainTriggerIndicator_r11(String name) {
		for (String bar : BarNameFactory_r10.getBarNames()) {
			triggerList.add(new TriggerCoordinator_r11(name, bar));
		}
	}

	/**
	 * テクニカル指標からイベントトリガーを発火する。
	 */
	public void execute() {
		for (TriggerCoordinator_r11 trigger : triggerList) {
			trigger.execute();
		}
	}

}
