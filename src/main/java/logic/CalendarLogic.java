package logic;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import util.CalendarUtil;
import util.FileUtil;
import util.StdoutLog;

/**
 * 営業日を管理するクラス。
 */
public class CalendarLogic {
	/**
	 * クラス。
	 */
	private static Class<?> clazz = MethodHandles.lookup().lookupClass();
	/**
	 * 基準パス。
	 */
	private static final String DIRPATH = "/tmp/";
	/**
	 * チャートＤＢディレクトリパス。
	 */
	private static final String DIR_DBPATH = DIRPATH + "db";
	/**
	 * チャートカレンダーファイル名。
	 */
	private static final String DB_FILENAME = "ChartCalendar.db";
	/**
	 * チャートカレンダーファイルパス。存在しなければ生成される。
	 */
	private static final String DB_FILEPATH = DIR_DBPATH + "/" + DB_FILENAME;

	/**
	 * チャートカレンダーのリスト。
	 */
	private static List<String> calendarList;

	static {
		readCalendar();
	}

	/**
	 * チャートカレンダーファイルを読み込む。
	 */
	private static void readCalendar() {
		Set<String> calendarSet = new TreeSet<>();
		List<String> lines = FileUtil.readAllLines(DB_FILEPATH);
		for (String s : lines) {
			if (s.startsWith("#")) {
				continue;
			}
			calendarSet.add(s);
		}
		StdoutLog.println(clazz, "readCalendar()", "calendarSet.size=" + calendarSet.size());
		calendarList = new ArrayList<>();
		calendarList.addAll(calendarSet);
	}

	/**
	 * 前営業日の日付を検索する。
	 * 
	 * @param date 今日の日付。
	 * @return 前営業日の日付。存在しない場合はnull。
	 */
	public static String prevWorkday(String date) {
		return CalendarUtil.prevWorkday(calendarList, date);
	}

	/**
	 * 翌営業日を取得する。
	 * 
	 * @param date 基準となる日付文字列(yyyy/MM/dd)。
	 * @return 翌営業日の日付文字列(yyyy/MM/dd)。
	 */
	public static String nextWorkday(String date) {
		return CalendarUtil.nextWorkday(calendarList, date);
	}

}
