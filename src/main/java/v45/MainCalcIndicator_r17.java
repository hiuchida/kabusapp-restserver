package v45;

import java.util.ArrayList;
import java.util.List;

import server.repository.IndicatorDataRepository;
import server.repository.MergeDataRepository;
import v38.factory.BarNameFactory_r10;

/**
 * テクニカル指標を計算するクラス。
 */
public class MainCalcIndicator_r17 {

	private MergeDataRepository mergeDataRepository;

	private IndicatorDataRepository indicatorDataRepository;

	/**
	 * テクニカル指標を計算するクラス。
	 */
	private List<CalcCoordinator_r17> calcList = new ArrayList<>();

	/**
	 * コンストラクタ。
	 * 
	 * @param mergeDataRepository
	 * @param indicatorDataRepository
	 * @param name                    ディレクトリ名。
	 */
	public MainCalcIndicator_r17(MergeDataRepository mergeDataRepository, IndicatorDataRepository indicatorDataRepository, String name) {
		this.mergeDataRepository = mergeDataRepository;
		this.indicatorDataRepository = indicatorDataRepository;
		for (String bar : BarNameFactory_r10.getBarNames()) {
			CalcCoordinator_r17 calc = new CalcCoordinator_r17(name, bar);
			calcList.add(calc);
		}
	}

	/**
	 * テクニカル指標を計算する。
	 */
	public void execute() {
		for (CalcCoordinator_r17 calc : calcList) {
			calc.execute(mergeDataRepository, indicatorDataRepository);
		}
	}

}
