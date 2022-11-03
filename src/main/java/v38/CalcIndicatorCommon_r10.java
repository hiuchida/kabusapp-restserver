package v38;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import bean.CalcIndicatorInfo_r10;
import bean.MergeChartInfo_r10;
import server.repository.IndicatorDataRepository;
import util.AppCommon;
import v38.factory.IndicatorCode;
import v38.i.CalcIndicator_r10;

/**
 * テクニカル指標を計算する抽象クラス。
 */
public abstract class CalcIndicatorCommon_r10 extends AppCommon implements CalcIndicator_r10 {
	/**
	 * クラス。
	 */
	private static Class<?> clazz = MethodHandles.lookup().lookupClass();
	/**
	 * ロガー。
	 */
	private static Log logger = LogFactory.getLog(clazz);

	/**
	 * テクニカル指標のstdoutのファイル名。
	 */
	private static final String OUT_FILENAME = "CalcIndicator%s_%d.out";

	/**
	 * ディレクトリ名。
	 */
	protected String name;
	/**
	 * 足名。
	 */
	protected String bar;
	/**
	 * テクニカル指標のリスト。
	 */
	protected List<CalcIndicatorInfo_r10> indicatorList = new ArrayList<>();
	/**
	 * テクニカル指標のstdoutのファイル名。
	 */
	protected String outFileName;

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
	 * 計算値の文字列に変換する。
	 * 
	 * @param cii テクニカル指標情報。
	 * @return ファイルに保存する文字列。
	 */
	abstract protected String toLineString(CalcIndicatorInfo_r10 cii);

	/**
	 * コンストラクタ。
	 * 
	 * @param name ディレクトリ名。
	 * @param bar  足名。
	 */
	public CalcIndicatorCommon_r10(String name, String bar) {
		this.name = name;
		this.bar = bar;
		this.outFileName = String.format(OUT_FILENAME, bar, getCode().intValue());
	}

	/**
	 * テクニカル指標を計算する。
	 * 
	 * @param chartList               マージしたチャートデータを時系列に並べたリスト。
	 * @param indicatorDataRepository
	 */
	public void execute(List<MergeChartInfo_r10> chartList, IndicatorDataRepository indicatorDataRepository) {
		indicatorList.clear();
		calcIndicator(chartList);
		removeOld();
		printIndicator(indicatorDataRepository);
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
	 * 
	 * @param indicatorDataRepository
	 */
	private void printIndicator(IndicatorDataRepository indicatorDataRepository) {
		List<String> lines = new ArrayList<>();
		int writeCnt = 0;
		for (CalcIndicatorInfo_r10 cii : indicatorList) {
			lines.add(toLineString(cii));
			writeCnt++;
		}
		indicatorDataRepository.writeAllLines(name, outFileName, lines);
		logger.info("printIndicator(): " + outFileName + ", writeCnt=" + writeCnt);
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
		return "CalcIndicatorCommon_r10 [name=" + name + ", bar=" + bar + ", indicatorList.size=" + indicatorList.size()
				+ ", outFileName=" + outFileName + "]";
	}

}
