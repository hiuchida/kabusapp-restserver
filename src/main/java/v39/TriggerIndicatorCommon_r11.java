package v39;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import util.DateTimeUtil;
import util.FileUtil;
import util.StringUtil;
import v38.bean.CalcIndicatorInfo_r10;
import v38.bean.MergeChartInfo_r10;
import v38.factory.IndicatorCode;
import v38.i.CalcIndicator_r10;
import v39.bean.TriggerIndicatorInfo_r11;
import v39.i.TriggerIndicator_r11;

/**
 * テクニカル指標からイベントトリガーを発火する抽象クラス。
 */
public abstract class TriggerIndicatorCommon_r11 implements TriggerIndicator_r11 {
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
	 * テクニカル指標のstdoutのファイル名。
	 */
	private static final String CALC_FILENAME = "CalcIndicator%s_%d.out";
	/**
	 * イベントトリガーのstdoutのファイル名。
	 */
	private static final String OUT_FILENAME = "TriggerIndicator%s_%d.out";

	/**
	 * イベントトリガー情報。
	 */
	public static class EventInfo {
		/**
		 * イベントトリガー情報のカラム数。
		 */
		public static final int MAX_COLS = 5;
		/**
		 * 作成日時。
		 */
		public String createDate;
		/**
		 * ディレクトリ名。
		 */
		public String name;
		/**
		 * 足名。
		 */
		public String bar;
		/**
		 * テクニカル指標の種別。
		 */
		public String indicator;
		/**
		 * レポート本文。
		 */
		public String report;

		/**
		 * コンストラクタ。
		 * 
		 * @param name      ディレクトリ名。
		 * @param bar       足名。
		 * @param indicator テクニカル指標の種別。
		 * @param report    レポート本文。
		 */
		public EventInfo(String name, String bar, String indicator, String report) {
			this.createDate = DateTimeUtil.nowToString();
			this.name = name;
			this.bar = bar;
			this.indicator = indicator;
			this.report = report;
		}

		/**
		 * マージしたチャートデータファイルのレコード文字列を生成する。
		 * 
		 * @return レコード文字列。
		 */
		public String toLineString() {
			String[] sa = new String[MAX_COLS];
			int i = 0;
			sa[i++] = createDate;
			sa[i++] = name;
			sa[i++] = bar;
			sa[i++] = indicator;
			sa[i++] = report;
			String val = StringUtil.joinTab(sa);
			return val;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("{createDate=").append(createDate);
			sb.append(", name=").append(name);
			sb.append(", bar=").append(bar);
			sb.append(", indicator=").append(indicator);
			sb.append(", report=").append(report);
			sb.append("}");
			return sb.toString();
		}
	}

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
	protected List<EventInfo> eventList = new ArrayList<>();
	/**
	 * テクニカル指標のstdoutのファイルパス。
	 */
	protected String indicatorFilePath;
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
	public TriggerIndicatorCommon_r11(String name, String bar, int maxValues, String type) {
		this.name = name;
		this.bar = bar;
		String dirChartPath = DIR_CHARTPATH + name;
		this.indicatorFilePath = dirChartPath + "/" + String.format(CALC_FILENAME, bar, getCode().intValue());
		this.outFilePath = dirChartPath + "/" + String.format(OUT_FILENAME, bar, getCode().intValue());
		this.maxValues = maxValues;
		this.type = type;
	}

	/**
	 * テクニカル指標からイベントトリガーを発火する。
	 * 
	 * @param chartList マージしたチャートデータを時系列に並べたリスト。
	 * @param ci        テクニカル指標を計算するクラス。
	 */
	public void execute(List<MergeChartInfo_r10> chartList, CalcIndicator_r10 ci) {
		this.chartList = chartList;
		eventList.clear();
		readIndicatorData(ci);
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
	 * @param ci テクニカル指標を計算するクラス。
	 */
	private void readIndicatorData(CalcIndicator_r10 ci) {
		indicatorList.clear();
		for (CalcIndicatorInfo_r10 cii : ci.getIndicatorList()) {
			TriggerIndicatorInfo_r11 tii = new TriggerIndicatorInfo_r11(cii);
			indicatorList.add(tii);
		}
		logger.info("readIndicatorData(): bar=" + bar + ", indicatorList.size=" + indicatorList.size());
	}

	/**
	 * テクニカル指標からイベントトリガーを発火する。
	 * 
	 * @param chartList マージしたチャートデータを時系列に並べたリスト。
	 */
	public void execute(List<MergeChartInfo_r10> chartList) {
		this.chartList = chartList;
		eventList.clear();
		readIndicatorData();
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
	 */
	private void readIndicatorData() {
		indicatorList.clear();
		List<String> lines = FileUtil.readAllLines(indicatorFilePath);
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
		logger.info("readIndicatorData(): " + indicatorFilePath + ", indicatorList.size=" + indicatorList.size());
	}

	/**
	 * イベントトリガー情報を追加する。
	 * 
	 * @param report レポート本文。
	 */
	protected void addEvent(String report) {
		EventInfo event = new EventInfo(name, bar, getCode().toString(), report);
		eventList.add(event);
	}

	/**
	 * イベントトリガーを表示する。
	 * 
	 * @param pw stdoutファイル。
	 */
	private void printEvent(PrintWriter pw) {
		int writeCnt = 0;
		for (EventInfo event : eventList) {
			pw.println(event.toLineString());
			writeCnt++;
		}
		logger.info("printEvent(): " + outFilePath + ", writeCnt=" + writeCnt);
	}

}
