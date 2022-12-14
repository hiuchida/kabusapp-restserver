package v45;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import bean.MergeChartInfo_r10;
import bean.TriggerIndicatorInfo_r11;
import server.repository.IndicatorDataRepository;
import util.AppCommon;
import util.FileUtil;
import util.StringUtil;
import v45.bean.EventInfo_r17;
import v45.factory.IndicatorCode;
import v45.i.TriggerIndicator_r17;

/**
 * テクニカル指標からイベントトリガーを発火する抽象クラス。
 */
public abstract class TriggerIndicatorCommon_r17 extends AppCommon implements TriggerIndicator_r17 {
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
	private static final String CALC_FILENAME = "CalcIndicator%s_%d.out";
	/**
	 * イベントトリガーのstdoutのファイル名。
	 */
	private static final String OUT_FILENAME = "TriggerIndicator%s_%d.out";

	/**
	 * ディレクトリ名。
	 */
	protected String name;
	/**
	 * 足名。
	 */
	protected String bar;
	/**
	 * マージしたチャートデータを時系列に並べたリスト。
	 */
	protected List<MergeChartInfo_r10> chartList = new ArrayList<>();
	/**
	 * テクニカル指標のリスト。
	 */
	protected List<TriggerIndicatorInfo_r11> indicatorList = new ArrayList<>();
	/**
	 * イベントトリガー情報のリスト。
	 */
	protected List<EventInfo_r17> eventList = new ArrayList<>();
	/**
	 * テクニカル指標のstdoutのファイル名。
	 */
	protected String indicatorFileName;
	/**
	 * イベントトリガーのstdoutのファイルパス。
	 */
	protected String outFilePath;
	/**
	 * 計算値(values）の個数。
	 */
	private int maxValues;
	/**
	 * 例外処理タイプ。
	 */
	private String type;

	/**
	 * テクニカル指標の種別コードを取得する。
	 * 
	 * @return テクニカル指標の種別コード。
	 */
	abstract public IndicatorCode getCode();

	/**
	 * テクニカル指標をチェックする。
	 */
	abstract protected void checkIndicator();

	/**
	 * コンストラクタ。
	 * 
	 * @param name      ディレクトリ名。
	 * @param bar       足名。
	 * @param maxValues 計算値(values）の個数。
	 * @param type      例外処理タイプ。
	 */
	public TriggerIndicatorCommon_r17(String name, String bar, int maxValues, String type) {
		this.name = name;
		this.bar = bar;
		String chartDirPath = SERVER_CHART_DIR_PATH + name + "/";
		this.indicatorFileName = String.format(CALC_FILENAME, bar, getCode().intValue());
		this.outFilePath = chartDirPath + String.format(OUT_FILENAME, bar, getCode().intValue());
		this.maxValues = maxValues;
		this.type = type;
	}

	/**
	 * テクニカル指標からイベントトリガーを発火する。
	 * 
	 * @param chartList               マージしたチャートデータを時系列に並べたリスト。
	 * @param indicatorDataRepository
	 */
	public void execute(List<MergeChartInfo_r10> chartList, IndicatorDataRepository indicatorDataRepository) {
		this.chartList = chartList;
		eventList.clear();
		readIndicatorData(indicatorDataRepository);
		checkIndicator();
		if (eventList.size() > 0) {
			try (PrintWriter pw = FileUtil.writer(outFilePath, FileUtil.UTF8)) {
				printEvent(pw);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			FileUtil.deleteFile(outFilePath);
		}
	}

	/**
	 * テクニカル指標のstdoutファイルを読み込む。
	 * 
	 * @param indicatorDataRepository
	 */
	private void readIndicatorData(IndicatorDataRepository indicatorDataRepository) {
		indicatorList.clear();
		List<String> lines = indicatorDataRepository.lines(name, indicatorFileName);
		for (String s : lines) {
			if (s.startsWith("#")) {
				continue;
			}
			String[] cols = StringUtil.splitComma(s);
			if (cols.length != TriggerIndicatorInfo_r11.MAX_COMMON_COLS + maxValues) {
				System.out.println("Warning: SKIP cols.length=" + cols.length + ", line=" + s);
				continue;
			}
			TriggerIndicatorInfo_r11 tii;
			if (type != null) {
				tii = new TriggerIndicatorInfo_r11(cols, type);
			} else {
				tii = new TriggerIndicatorInfo_r11(cols);
			}
			indicatorList.add(tii);
		}
		logger.info("readIndicatorData(): " + indicatorFileName + ", indicatorList.size=" + indicatorList.size());
	}

	/**
	 * イベントトリガー情報を追加する。
	 * 
	 * @param date   日付。
	 * @param type   トリガー種別。
	 * @param report レポート本文。
	 */
	protected void addEvent(String date, String type, String report) {
		EventInfo_r17 event = new EventInfo_r17(name, bar, getCode().toString(), date, type, report);
		eventList.add(event);
	}

	/**
	 * イベントトリガーを表示する。
	 * 
	 * @param pw stdoutファイル。
	 */
	private void printEvent(PrintWriter pw) {
		int writeCnt = 0;
		for (EventInfo_r17 event : eventList) {
			pw.println(event.toLineString());
			writeCnt++;
		}
		logger.info("printEvent(): " + outFilePath + ", writeCnt=" + writeCnt);
	}

	@Override
	public String toString() {
		return "TriggerIndicatorCommon_r11 [name=" + name + ", bar=" + bar + ", chartList.size=" + chartList.size()
				+ ", indicatorList.size=" + indicatorList.size() + ", eventList.size=" + eventList.size() + ", indicatorFileName="
				+ indicatorFileName + ", outFilePath=" + outFilePath + ", maxValues=" + maxValues + ", type=" + type
				+ "]";
	}

}
