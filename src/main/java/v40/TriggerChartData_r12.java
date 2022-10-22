package v40;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import server.repository.ChartDataRepository;
import util.DateTimeUtil;
import util.FileUtil;
import util.StringUtil;

/**
 * ティックデータからイベントトリガーを発火するクラス。
 */
public class TriggerChartData_r12 {
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
	 * イベントトリガーのstdoutのファイル名。
	 */
	private static final String OUT_FILENAME = "TriggerIndicator.out";

	/**
	 * ディレクトリ名。
	 */
	private String name;
	/**
	 * イベントトリガーのstdoutのファイルパス。
	 */
	protected String outFilePath;
	/**
	 * チャートデータのリスト。
	 */
	private List<String> chartList = new ArrayList<>();
	/**
	 * イベントトリガーのリスト。
	 */
	protected List<String> reportList = new ArrayList<>();

	/**
	 * コンストラクタ。
	 * 
	 * @param name ディレクトリ名。
	 */
	public TriggerChartData_r12(String name) {
		this.name = name;
		String dirChartPath = SERVER_DIR_CHARTPATH + name;
		this.outFilePath = dirChartPath + "/" + OUT_FILENAME;
	}

	/**
	 * ティックデータからイベントトリガーを発火する。
	 * 
	 * @param chartDataRepository
	 */
	public void execute(ChartDataRepository chartDataRepository) {
		readChartData(chartDataRepository);
		reportList.clear();
		try (PrintWriter pw = FileUtil.writer(outFilePath, FileUtil.UTF8)) {
			checkChartData();
			printEvent(pw);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * マージしたチャートデータを読み込む。
	 * 
	 * @param chartDataRepository
	 */
	private void readChartData(ChartDataRepository chartDataRepository) {
		List<String> lines = chartDataRepository.lines(name);
		for (String s : lines) {
			if (s.startsWith("#")) {
				continue;
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
	 * チャートデータをチェックする。
	 */
	private void checkChartData() {
		final int TEST_PRICE = 26420;
		for (int i = 0; i < chartList.size(); i++) {
			String s = chartList.get(i);
			String[] cols = StringUtil.splitComma(s);
			String date = cols[0];
			int price = (int) StringUtil.parseDouble(cols[1]);
			if (price >= TEST_PRICE) {
				String msg = String.format("[%s] price %d >= %d", date, price, TEST_PRICE);
				reportList.add(msg);
			}
		}
	}

	/**
	 * イベントトリガーを表示する。
	 * 
	 * @param pw stdoutファイル。
	 */
	private void printEvent(PrintWriter pw) {
		int writeCnt = 0;
		for (String msg : reportList) {
			String now = DateTimeUtil.nowToString();
			pw.printf("%s %s", now, msg);
			pw.println();
			writeCnt++;
		}
		logger.info("printEvent(): " + outFilePath + ", writeCnt=" + writeCnt);
	}

}
