package v40;

import server.repository.ChartDataRepository;

/**
 * ティックデータからイベントトリガーを発火するクラス。
 */
public class MainTriggerChartData_r12 {

	private ChartDataRepository chartDataRepository;

	/**
	 * ティックデータからイベントトリガーを発火する。
	 */
	private TriggerChartData_r12 triggerObj;

	/**
	 * コンストラクタ。
	 * 
	 * @param chartDataRepository
	 * @param name                ディレクトリ名。
	 */
	public MainTriggerChartData_r12(ChartDataRepository chartDataRepository, String name) {
		this.chartDataRepository = chartDataRepository;
		this.triggerObj = new TriggerChartData_r12(name);
	}

	/**
	 * ティックデータからイベントトリガーを発火する。
	 */
	public void execute() {
		triggerObj.execute(chartDataRepository);
	}

}
