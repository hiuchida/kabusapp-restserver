package v38;

import java.util.ArrayList;
import java.util.List;

import v38.factory.BarNameFactory_r10;

/**
 * テクニカル指標を計算するクラス。
 */
public class MainCalcIndicator_r10 {

	/**
	 * テクニカル指標を計算するクラス。
	 */
	private List<CalcCoordinator_r10> calcList = new ArrayList<>();

	/**
	 * コンストラクタ。
	 * 
	 * @param name ディレクトリ名。
	 */
	public MainCalcIndicator_r10(String name) {
		for (String bar : BarNameFactory_r10.getBarNames()) {
			calcList.add(new CalcCoordinator_r10(name, bar));
		}
	}

	/**
	 * テクニカル指標を計算する。
	 */
	public void execute() {
		for (CalcCoordinator_r10 calc : calcList) {
			calc.execute();
		}
	}

}
