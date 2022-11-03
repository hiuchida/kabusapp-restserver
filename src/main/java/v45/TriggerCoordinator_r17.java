package v45;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import bean.MergeChartInfo_r10;
import server.repository.IndicatorDataRepository;
import server.repository.MergeDataRepository;
import util.AppCommon;
import util.StringUtil;
import v45.factory.TriggerIndicatorFactory_r17;
import v45.i.TriggerIndicator_r17;

/**
 * テクニカル指標からイベントトリガーを発火するクラス。
 */
public class TriggerCoordinator_r17 extends AppCommon {
	/**
	 * クラス。
	 */
	private static Class<?> clazz = MethodHandles.lookup().lookupClass();
	/**
	 * ロガー。
	 */
	private static Log logger = LogFactory.getLog(clazz);

	/**
	 * マージしたチャートデータファイル名。
	 */
	private static final String CHART_TXT_FILENAME = "ChartData%s_r10.txt";

	/**
	 * ディレクトリ名。
	 */
	private String name;
	/**
	 * 足名。
	 */
	private String bar;
	/**
	 * マージしたチャートデータファイルパス。
	 */
	private String txtFileName;
	/**
	 * マージしたチャートデータを時系列に並べたリスト。
	 */
	private List<MergeChartInfo_r10> chartList = new ArrayList<>();
	/**
	 * テクニカル指標からイベントトリガーを発火するクラス。
	 */
	private List<TriggerIndicator_r17> triggerList = new ArrayList<>();

	/**
	 * コンストラクタ。
	 * 
	 * @param name ディレクトリ名。
	 * @param bar  足名。
	 */
	public TriggerCoordinator_r17(String name, String bar) {
		this.name = name;
		this.bar = bar;
		this.txtFileName = String.format(CHART_TXT_FILENAME, bar);
		this.triggerList = TriggerIndicatorFactory_r17.create(name, bar);
	}

	/**
	 * テクニカル指標からイベントトリガーを発火する。
	 * 
	 * @param mergeDataRepository
	 * @param indicatorDataRepository
	 */
	public void execute(MergeDataRepository mergeDataRepository, IndicatorDataRepository indicatorDataRepository) {
		readChartData(mergeDataRepository);
		for (TriggerIndicator_r17 ti : triggerList) {
			ti.execute(chartList, indicatorDataRepository);
		}
	}

	/**
	 * マージしたチャートデータを読み込む。
	 * 
	 * @param mergeDataRepository
	 */
	private void readChartData(MergeDataRepository mergeDataRepository) {
		List<String> lines = mergeDataRepository.lines(name, txtFileName);
		for (String s : lines) {
			if (s.startsWith("#")) {
				continue;
			}
			String[] cols = StringUtil.splitTab(s);
			if (cols.length != MergeChartInfo_r10.MAX_MERGE_COLS) {
				System.out.println("Warning: SKIP cols.length=" + cols.length + ", line=" + s);
				continue;
			}
			MergeChartInfo_r10 mci = new MergeChartInfo_r10(cols);
			chartList.add(mci);
		}
		logger.info("readChartData(): " + txtFileName + ", chartList.size=" + chartList.size());
	}

	@Override
	public String toString() {
		return "TriggerCoordinator_r11 [name=" + name + ", bar=" + bar + ", txtFileName=" + txtFileName + ", chartList.size="
				+ chartList.size() + ", triggerList.size=" + triggerList.size() + "]";
	}

}
