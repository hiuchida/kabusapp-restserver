package v40;

import server.repository.ChartDataRepository;
import util.AppCommon;

/**
 * ティックデータからイベントトリガーを発火するクラス。
 */
public class MainTriggerTickData_r12 extends AppCommon {

	private ChartDataRepository chartDataRepository;

	/**
	 * ティックデータからイベントトリガーを発火する。
	 */
	private TriggerTickData_r12 triggerObj;

	/**
	 * コンストラクタ。
	 * 
	 * @param chartDataRepository
	 * @param name                ディレクトリ名。
	 */
	public MainTriggerTickData_r12(ChartDataRepository chartDataRepository, String name) {
		this.chartDataRepository = chartDataRepository;
		this.triggerObj = new TriggerTickData_r12(name);
	}

	/**
	 * ティックデータからイベントトリガーを発火する。
	 */
	public void execute() {
		triggerObj.execute(chartDataRepository);
	}

}
