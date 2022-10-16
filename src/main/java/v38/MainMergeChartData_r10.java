package v38;

import java.util.Map;
import java.util.TreeMap;

import v38.factory.BarNameFactory_r10;
import v38.factory.MergeChartDataFactory_r10;
import v38.i.MergeChartData_r10;

/**
 * 保存した4本値チャートデータと、PUSH APIで受信したチャートデータをマージした4本値と売買高を出力するツール。
 */
public class MainMergeChartData_r10 {

	/**
	 * 保存した4本値チャートデータと、PUSH APIで受信したチャートデータをマージした4本値を出力するクラス。
	 */
	private Map<String, MergeChartData_r10> mergeMap = new TreeMap<>();

	/**
	 * コンストラクタ。
	 * 
	 * @param name ディレクトリ名。
	 */
	public MainMergeChartData_r10(String name) {
		this.mergeMap = MergeChartDataFactory_r10.create(name);
	}

	/**
	 * 保存した4本値チャートデータの終値と、PUSH APIで受信したチャートデータをマージする。
	 */
	public void execute() {
		for (String key : BarNameFactory_r10.getBarNames()) {
			MergeChartData_r10 merge = mergeMap.get(key);
			merge.execute();
		}
	}

}
