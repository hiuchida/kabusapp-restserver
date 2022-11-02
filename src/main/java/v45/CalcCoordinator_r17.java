package v45;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import bean.MergeChartInfo_r10;
import server.repository.IndicatorDataRepository;
import server.repository.MergeDataRepository;
import util.StringUtil;
import v38.factory.CalcIndicatorFactory_r10;
import v38.i.CalcIndicator_r10;

/**
 * テクニカル指標を計算するクラス。
 */
public class CalcCoordinator_r17 {
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
	 * マージしたチャートデータファイル名。
	 */
	private String txtFileName;
	/**
	 * マージしたチャートデータを時系列に並べたリスト。
	 */
	private List<MergeChartInfo_r10> chartList = new ArrayList<>();
	/**
	 * テクニカル指標を計算するクラス。
	 */
	private List<CalcIndicator_r10> calcList = new ArrayList<>();

	/**
	 * コンストラクタ。
	 * 
	 * @param name ディレクトリ名。
	 * @param bar  足名。
	 */
	public CalcCoordinator_r17(String name, String bar) {
		this.name = name;
		this.bar = bar;
		this.txtFileName = String.format(CHART_TXT_FILENAME, bar);
		this.calcList = CalcIndicatorFactory_r10.create(name, bar);
	}

	/**
	 * テクニカル指標を計算する。
	 * 
	 * @param mergeDataRepository
	 * @param indicatorDataRepository
	 */
	public void execute(MergeDataRepository mergeDataRepository, IndicatorDataRepository indicatorDataRepository) {
		readChartData(mergeDataRepository);
		for (CalcIndicator_r10 ci : calcList) {
			ci.execute(chartList, indicatorDataRepository);
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

	/**
	 * テクニカル指標を計算するクラスを取得する。
	 * 
	 * @return テクニカル指標を計算するクラスのリスト。
	 */
	public Map<Integer, CalcIndicator_r10> getCalcMap() {
		Map<Integer, CalcIndicator_r10> calcMap = new TreeMap<>();
		for (CalcIndicator_r10 ci : calcList) {
			calcMap.put(ci.getCode().intValue(), ci);
		}
		return calcMap;
	}

	@Override
	public String toString() {
		return "CalcCoordinator_r10 [bar=" + bar + ", txtFileName=" + txtFileName + ", chartList.size=" + chartList.size()
				+ ", calcList=" + calcList + "]";
	}

}
