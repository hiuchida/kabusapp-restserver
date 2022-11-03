package v45;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import server.repository.ChartDataRepository;
import server.repository.ChartDbRepository;
import server.repository.MergeDataRepository;
import util.AppCommon;
import v38.factory.BarNameFactory_r10;
import v45.factory.MergeChartDataFactory_r17;
import v45.i.MergeChartData_r17;

/**
 * 保存した4本値チャートデータと、PUSH APIで受信したチャートデータをマージした4本値と売買高を出力するツール。
 */
public class MainMergeChartData_r17 extends AppCommon {

	private ChartDataRepository chartDataRepository;

	private ChartDbRepository chartDbRepository;

	private MergeDataRepository mergeDataRepository;

	/**
	 * 保存した4本値チャートデータと、PUSH APIで受信したチャートデータをマージした4本値を出力するクラス。
	 */
	private Map<String, MergeChartData_r17> mergeMap = new TreeMap<>();

	/**
	 * コンストラクタ。
	 * 
	 * @param chartDataRepository
	 * @param chartDbRepository
	 * @param mergeDataRepository
	 * @param name                ディレクトリ名。
	 */
	public MainMergeChartData_r17(ChartDataRepository chartDataRepository, ChartDbRepository chartDbRepository,
			MergeDataRepository mergeDataRepository, String name) {
		this.chartDataRepository = chartDataRepository;
		this.chartDbRepository = chartDbRepository;
		this.mergeDataRepository = mergeDataRepository;
		this.mergeMap = MergeChartDataFactory_r17.create(name);
	}

	/**
	 * 保存した4本値チャートデータと、PUSH APIで受信したチャートデータをマージする。
	 */
	public void execute() {
		List<String> lines = null;
		for (String key : BarNameFactory_r10.getBarNames()) {
			MergeChartData_r17 merge = mergeMap.get(key);
			lines = merge.execute(chartDataRepository, chartDbRepository, mergeDataRepository, lines);
		}
	}

}
