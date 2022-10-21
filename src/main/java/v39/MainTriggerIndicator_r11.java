package v39;

import java.util.ArrayList;
import java.util.List;

import server.repository.IndicatorDataRepository;
import server.repository.MergeDataRepository;
import v38.factory.BarNameFactory_r10;

/**
 * テクニカル指標からイベントトリガーを発火するクラス。
 */
public class MainTriggerIndicator_r11 {

	private MergeDataRepository mergeDataRepository;

	private IndicatorDataRepository indicatorDataRepository;

	/**
	 * テクニカル指標からイベントトリガーを発火するクラス。
	 */
	private List<TriggerCoordinator_r11> triggerList = new ArrayList<>();

	/**
	 * コンストラクタ。
	 * 
	 * @param mergeDataRepository
	 * @param indicatorDataRepository
	 * @param name                    ディレクトリ名。
	 */
	public MainTriggerIndicator_r11(MergeDataRepository mergeDataRepository, IndicatorDataRepository indicatorDataRepository, String name) {
		this.mergeDataRepository = mergeDataRepository;
		this.indicatorDataRepository = indicatorDataRepository;
		for (String bar : BarNameFactory_r10.getBarNames()) {
			triggerList.add(new TriggerCoordinator_r11(name, bar));
		}
	}

	/**
	 * テクニカル指標からイベントトリガーを発火する。
	 */
	public void execute() {
		for (TriggerCoordinator_r11 trigger : triggerList) {
			trigger.execute(mergeDataRepository, indicatorDataRepository);
		}
	}

}
