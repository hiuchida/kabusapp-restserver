package logic;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import util.CalendarUtil;

/**
 * 営業日を管理するクラス。
 */
public class CalendarLogic {
	/**
	 * クラス。
	 */
	private static Class<?> clazz = MethodHandles.lookup().lookupClass();
	/**
	 * ロガー。
	 */
	private static Log logger = LogFactory.getLog(clazz);
	/**
	 * チャートカレンダーファイル名。
	 */
	public static final String DB_FILENAME = "ChartCalendar.db";
	/**
	 * チャートカレンダーのリスト。
	 */
	private static List<String> calendarList;

	/**
	 * チャートカレンダーを初期化する。
	 * 
	 * @param lines チャートカレンダーのリスト。
	 */
	public static void initCalendar(List<String> lines) {
		calendarList = new ArrayList<>();
		for (String s : lines) {
			if (s.startsWith("#")) {
				continue;
			}
			calendarList.add(s);
		}
		logger.info("initCalendar(): calendarList.size=" + calendarList.size());
	}

	/**
	 * 前営業日の日付を検索する。
	 * 
	 * @param date 今日の日付。
	 * @return 前営業日の日付。存在しない場合はnull。
	 */
	public static String prevWorkday(String date) {
		if (calendarList == null) {
			throw new InternalError("logic bug: must call initCalendar().");
		}
		return CalendarUtil.prevWorkday(calendarList, date);
	}

	/**
	 * 翌営業日を取得する。
	 * 
	 * @param date 基準となる日付文字列(yyyy/MM/dd)。
	 * @return 翌営業日の日付文字列(yyyy/MM/dd)。
	 */
	public static String nextWorkday(String date) {
		if (calendarList == null) {
			throw new InternalError("logic bug: must call initCalendar().");
		}
		return CalendarUtil.nextWorkday(calendarList, date);
	}

}
