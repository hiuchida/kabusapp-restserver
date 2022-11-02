package v45;

import server.repository.ChartDataRepository;

/**
 * ティックデータからイベントトリガーを発火するクラス。
 */
public class MainTriggerTickData_r17 {

	private ChartDataRepository chartDataRepository;

	/**
	 * ティックデータからイベントトリガーを発火する。
	 */
	private TriggerTickData_r17 triggerObj;

	/**
	 * コンストラクタ。
	 * 
	 * @param chartDataRepository
	 * @param name                ディレクトリ名。
	 */
	public MainTriggerTickData_r17(ChartDataRepository chartDataRepository, String name) {
		this.chartDataRepository = chartDataRepository;
		this.triggerObj = new TriggerTickData_r17(name);
	}

	/**
	 * ティックデータからイベントトリガーを発火する。
	 */
	public void execute() {
		triggerObj.execute(chartDataRepository);
	}

}
