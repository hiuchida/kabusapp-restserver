package v38;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import util.FileUtil;
import v38.bean.CalcIndicatorInfo_r10;
import v38.bean.MergeChartInfo_r10;
import v38.factory.IndicatorCode;
import v38.i.CalcIndicator_r10;

/**
 * テクニカル指標を計算する抽象クラス。
 */
public abstract class CalcIndicatorCommon_r10 implements CalcIndicator_r10 {
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
	 * テクニカル指標のstdoutのファイル名。
	 */
	private static final String OUT_FILENAME = "CalcIndicator%s_%d.out";

	/**
	 * 足名。
	 */
	protected String bar;
	/**
	 * テクニカル指標のリスト。
	 */
	protected List<CalcIndicatorInfo_r10> indicatorList = new ArrayList<>();
	/**
	 * テクニカル指標のstdoutのファイルパス。
	 */
	protected String outFilePath;

	/**
	 * テクニカル指標の種別コードを取得する。
	 * 
	 * @return テクニカル指標の種別コード。
	 */
	abstract public IndicatorCode getCode();

	/**
	 * テクニカル指標を計算する。
	 * 
	 * @param chartList マージしたチャートデータを時系列に並べたリスト。
	 */
	abstract protected void calcIndicator(List<MergeChartInfo_r10> chartList);

	/**
	 * 計算値を表示する。
	 * 
	 * @param pw stdoutファイル。
	 * @return 保存した件数。
	 */
	abstract protected int printIndicator(PrintWriter pw);

	/**
	 * コンストラクタ。
	 * 
	 * @param name ディレクトリ名。
	 * @param bar  足名。
	 */
	public CalcIndicatorCommon_r10(String name, String bar) {
		this.bar = bar;
		String dirChartPath = SERVER_DIR_CHARTPATH + name;
		this.outFilePath = dirChartPath + "/" + String.format(OUT_FILENAME, bar, getCode().intValue());
	}

	/**
	 * テクニカル指標を計算する。
	 * 
	 * @param chartList マージしたチャートデータを時系列に並べたリスト。
	 */
	public void execute(List<MergeChartInfo_r10> chartList) {
		indicatorList.clear();
		calcIndicator(chartList);
		removeOld();
		printIndicator();
	}

	/**
	 * 古いデータを削除する。
	 * 
	 * @return 削除した件数。
	 */
	private int removeOld() {
		int remainCnt = 60;
		int delCnt = 0;
		if (indicatorList.size() <= remainCnt) {
			return delCnt;
		}
		List<CalcIndicatorInfo_r10> list = new ArrayList<>();
		for (int i = indicatorList.size() - remainCnt; i < indicatorList.size(); i++) {
			CalcIndicatorInfo_r10 cii = indicatorList.get(i);
			list.add(cii);
		}
		delCnt = indicatorList.size() - list.size();
		indicatorList = list;
		return delCnt;
	}

	/**
	 * 計算値を表示する。
	 */
	private void printIndicator() {
		try (PrintWriter pw = FileUtil.writer(outFilePath, FileUtil.UTF8)) {
			int writeCnt = printIndicator(pw);
			logger.info("printIndicator(): " + outFilePath + ", writeCnt=" + writeCnt);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * テクニカル指標のリストを取得する。
	 * 
	 * @return テクニカル指標のリスト。
	 */
	public List<CalcIndicatorInfo_r10> getIndicatorList() {
		return indicatorList;
	}

	@Override
	public String toString() {
		return "CalcIndicatorCommon_r10 [bar=" + bar + ", indicatorList.size=" + indicatorList.size() + ", outFilePath="
				+ outFilePath + "]";
	}

}
