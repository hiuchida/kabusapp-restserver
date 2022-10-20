package v39;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import util.FileUtil;
import util.StringUtil;
import v38.CalcCoordinator_r10;
import v38.bean.MergeChartInfo_r10;
import v38.i.CalcIndicator_r10;
import v38.i.MergeChartData_r10;
import v39.factory.TriggerIndicatorFactory_r11;
import v39.i.TriggerIndicator_r11;

/**
 * テクニカル指標からイベントトリガーを発火するクラス。
 */
public class TriggerCoordinator_r11 {
	/**
	 * クラス。
	 */
	private static Class<?> clazz = MethodHandles.lookup().lookupClass();
	/**
	 * ロガー。
	 */
	private static Log logger = LogFactory.getLog(clazz);
	/**
	 * 基準パス。
	 */
	private static final String DIRPATH = "/tmp/";
	/**
	 * チャートデータディレクトリパス。
	 */
	private static final String DIR_CHARTPATH = DIRPATH + "chart/";
	/**
	 * マージしたチャートデータファイル名。
	 */
	private static final String CHART_TXT_FILENAME = "ChartData%s_r10.txt";

	/**
	 * 足名。
	 */
	private String bar;
	/**
	 * マージしたチャートデータファイルパス。
	 */
	private String txtFilePath;
	/**
	 * マージしたチャートデータを時系列に並べたリスト。
	 */
	private List<MergeChartInfo_r10> chartList = new ArrayList<>();
	/**
	 * テクニカル指標からイベントトリガーを発火するクラス。
	 */
	private List<TriggerIndicator_r11> triggerList = new ArrayList<>();

	/**
	 * コンストラクタ。
	 * 
	 * @param name ディレクトリ名。
	 * @param bar  足名。
	 */
	public TriggerCoordinator_r11(String name, String bar) {
		this.bar = bar;
		String dirChartPath = DIR_CHARTPATH + name;
		this.txtFilePath = dirChartPath + "/" + String.format(CHART_TXT_FILENAME, bar);
		this.triggerList = TriggerIndicatorFactory_r11.create(name, bar);
	}

	/**
	 * テクニカル指標からイベントトリガーを発火する。
	 * 
	 * @param merge マージインターフェイス。
	 * @param calc  テクニカル指標計算クラス。
	 */
	public void execute(MergeChartData_r10 merge, CalcCoordinator_r10 calc) {
		readChartData(merge);
		Map<Integer, CalcIndicator_r10> calcMap = calc.getCalcMap();
		for (TriggerIndicator_r11 ti : triggerList) {
			CalcIndicator_r10 ci = calcMap.get(ti.getCode().intValue());
			ti.execute(chartList, ci);
		}
	}

	/**
	 * マージしたチャートデータを読み込む。
	 * 
	 * @param マージインターフェイス。
	 */
	private void readChartData(MergeChartData_r10 merge) {
		chartList.clear();
		for (String key : merge.getChartMap().keySet()) {
			MergeChartInfo_r10 mciSrc = merge.getChartMap().get(key);
			String line = mciSrc.toLineString();
			String[] cols = StringUtil.splitTab(line);
			MergeChartInfo_r10 mci = new MergeChartInfo_r10(cols);
			chartList.add(mci);
		}
		logger.info("readChartData(): bar=" + bar + ", chartList.size=" + chartList.size());
	}

	/**
	 * テクニカル指標からイベントトリガーを発火する。
	 */
	public void execute() {
		readChartData();
		for (TriggerIndicator_r11 ti : triggerList) {
			ti.execute(chartList);
		}
	}

	/**
	 * マージしたチャートデータを読み込む。
	 */
	private void readChartData() {
		List<String> lines = FileUtil.readAllLines(txtFilePath);
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
		logger.info("readChartData(): " + txtFilePath + ", chartList.size=" + chartList.size());
	}

}
