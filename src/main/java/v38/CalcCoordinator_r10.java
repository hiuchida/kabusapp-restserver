package v38;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import util.FileUtil;
import util.StringUtil;
import v38.bean.MergeChartInfo_r10;
import v38.factory.CalcIndicatorFactory_r10;
import v38.i.CalcIndicator_r10;
import v38.i.MergeChartData_r10;

/**
 * テクニカル指標を計算するクラス。
 */
public class CalcCoordinator_r10 {
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
	private static final String SERVER_DIRPATH = "/tmp/server/";
	/**
	 * チャートデータディレクトリパス。
	 */
	private static final String SERVER_DIR_CHARTPATH = SERVER_DIRPATH + "chart/";
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
	 * テクニカル指標を計算するクラス。
	 */
	private List<CalcIndicator_r10> calcList = new ArrayList<>();

	/**
	 * コンストラクタ。
	 * 
	 * @param name ディレクトリ名。
	 * @param bar  足名。
	 */
	public CalcCoordinator_r10(String name, String bar) {
		this.bar = bar;
		String dirChartPath = SERVER_DIR_CHARTPATH + name;
		this.txtFilePath = dirChartPath + "/" + String.format(CHART_TXT_FILENAME, bar);
		this.calcList = CalcIndicatorFactory_r10.create(name, bar);
	}

	/**
	 * テクニカル指標を計算する。
	 * 
	 * @param merge マージインターフェイス。
	 */
	public void execute(MergeChartData_r10 merge) {
		readChartData(merge);
		for (CalcIndicator_r10 ci : calcList) {
			ci.execute(chartList);
		}
	}

	/**
	 * マージしたチャートデータを読み込む。
	 * 
	 * @param merge マージインターフェイス。
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
	 * テクニカル指標を計算する。
	 */
	public void execute() {
		readChartData();
		for (CalcIndicator_r10 ci : calcList) {
			ci.execute(chartList);
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
		return "CalcCoordinator_r10 [bar=" + bar + ", txtFilePath=" + txtFilePath + ", chartList.size=" + chartList.size()
				+ ", calcList=" + calcList + "]";
	}

}
