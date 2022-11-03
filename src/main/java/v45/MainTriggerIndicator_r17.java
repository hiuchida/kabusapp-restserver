package v45;

import java.util.ArrayList;
import java.util.List;

import server.repository.IndicatorDataRepository;
import server.repository.MergeDataRepository;
import util.AppCommon;
import v38.factory.BarNameFactory_r10;

/**
 * テクニカル指標からイベントトリガーを発火するクラス。
 */
public class MainTriggerIndicator_r17 extends AppCommon {

	private MergeDataRepository mergeDataRepository;

	private IndicatorDataRepository indicatorDataRepository;

	/**
	 * テクニカル指標からイベントトリガーを発火するクラス。
	 */
	private List<TriggerCoordinator_r17> triggerList = new ArrayList<>();

	/**
	 * コンストラクタ。
	 * 
	 * @param mergeDataRepository
	 * @param indicatorDataRepository
	 * @param name                    ディレクトリ名。
	 */
	public MainTriggerIndicator_r17(MergeDataRepository mergeDataRepository, IndicatorDataRepository indicatorDataRepository, String name) {
		this.mergeDataRepository = mergeDataRepository;
		this.indicatorDataRepository = indicatorDataRepository;
		for (String bar : BarNameFactory_r10.getBarNames()) {
			TriggerCoordinator_r17 trigger = new TriggerCoordinator_r17(name, bar);
			triggerList.add(trigger);
		}
	}

	/**
	 * テクニカル指標からイベントトリガーを発火する。
	 */
	public void execute() {
		for (TriggerCoordinator_r17 trigger : triggerList) {
			trigger.execute(mergeDataRepository, indicatorDataRepository);
		}
	}

}
