package v45;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import server.repository.ChartDataRepository;
import util.AppCommon;
import util.FileUtil;
import util.StringUtil;
import v39.bean.EventInfo_r11;

/**
 * ティックデータからイベントトリガーを発火するクラス。
 */
public class TriggerTickData_r17 extends AppCommon {
	/**
	 * 足名：ティック。
	 */
	public static final String TICK = "tick";
	/**
	 * テクニカル指標の種別：ティック。
	 */
	public static final String TICK_CODE = "0";
	/**
	 * トリガー種別：以上。
	 */
	public static final String GE = "GE";
	/**
	 * トリガー種別：以下。
	 */
	public static final String LE = "LE";
	
	/**
	 * クラス。
	 */
	private static Class<?> clazz = MethodHandles.lookup().lookupClass();
	/**
	 * ロガー。
	 */
	private static Log logger = LogFactory.getLog(clazz);

	/**
	 * イベントトリガーのstdoutのファイル名。
	 */
	private static final String OUT_FILENAME = "TriggerTickData.out";

	/**
	 * ディレクトリ名。
	 */
	private String name;
	/**
	 * イベントトリガーのstdoutのファイルパス。
	 */
	protected String outFilePath;
	/**
	 * ティックデータのリスト。
	 */
	private List<String> chartList = new ArrayList<>();
	/**
	 * イベントトリガー情報のリスト。
	 */
	private List<EventInfo_r11> eventList = new ArrayList<>();

	/**
	 * コンストラクタ。
	 * 
	 * @param name ディレクトリ名。
	 */
	public TriggerTickData_r17(String name) {
		this.name = name;
		String dirChartPath = SERVER_CHART_DIR_PATH + name + "/";
		this.outFilePath = dirChartPath + OUT_FILENAME;
	}

	/**
	 * ティックデータからイベントトリガーを発火する。
	 * 
	 * @param chartDataRepository
	 */
	public void execute(ChartDataRepository chartDataRepository) {
		readChartData(chartDataRepository);
		eventList.clear();
		try (PrintWriter pw = FileUtil.writer(outFilePath, FileUtil.UTF8)) {
			checkChartData();
			printEvent(pw);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 抽出したティックデータを読み込む。
	 * 
	 * @param chartDataRepository
	 */
	private void readChartData(ChartDataRepository chartDataRepository) {
		List<String> lines = chartDataRepository.lines(name);
		for (String s : lines) {
			if (s.startsWith("#")) {
				continue;
			}
			// カラム1にクライアントのタイムスタンプが含まれている場合、データ本体はカラム2以降
			if (s.length() > 8 && s.charAt(4) == '/' && s.charAt(7) == '/') {
				String[] cols = StringUtil.splitComma(s);
				String[] newCols = new String[cols.length - 1];
				for (int i = 0; i < newCols.length; i++) {
					newCols[i] = cols[i + 1];
				}
				s = StringUtil.joinComma(newCols);
			}
			chartList.add(s);
		}
		removeOld();
		logger.info("readChartData(): name=" + name + ", chartList.size=" + chartList.size());
	}

	/**
	 * 古いデータを削除する。
	 * 
	 * @return 削除した件数。
	 */
	private int removeOld() {
		int remainCnt = 60;
		int delCnt = 0;
		if (chartList.size() <= remainCnt) {
			return delCnt;
		}
		List<String> list = new ArrayList<>();
		for (int i = chartList.size() - remainCnt; i < chartList.size(); i++) {
			String s = chartList.get(i);
			list.add(s);
		}
		delCnt = chartList.size() - list.size();
		chartList = list;
		return delCnt;
	}

	/**
	 * ティックデータをチェックする。
	 */
	private void checkChartData() {
		final int TEST_PRICE = 26420;
		for (int i = 0; i < chartList.size(); i++) {
			String s = chartList.get(i);
			String[] cols = StringUtil.splitComma(s);
			String date = cols[0];
			int price = (int) StringUtil.parseDouble(cols[1]);
			if (price >= TEST_PRICE) {
				String msg = String.format("price %d >= %d", price, TEST_PRICE);
				addEvent(date, GE, msg);
			}
		}
	}

	/**
	 * イベントトリガー情報を追加する。
	 * 
	 * @param date   日付。
	 * @param type   トリガー種別。
	 * @param report レポート本文。
	 */
	private void addEvent(String date, String type, String report) {
		EventInfo_r11 event = new EventInfo_r11(name, TICK, TICK_CODE, date, type, report);
		eventList.add(event);
	}

	/**
	 * イベントトリガーを表示する。
	 * 
	 * @param pw stdoutファイル。
	 */
	private void printEvent(PrintWriter pw) {
		int writeCnt = 0;
		for (EventInfo_r11 event : eventList) {
			pw.println(event.toLineString());
			writeCnt++;
		}
		logger.info("printEvent(): " + outFilePath + ", writeCnt=" + writeCnt);
	}

	@Override
	public String toString() {
		return "TriggerChartData_r12 [name=" + name + ", outFilePath=" + outFilePath + ", chartList.size=" + chartList.size()
				+ ", eventList.size=" + eventList.size() + "]";
	}

}
