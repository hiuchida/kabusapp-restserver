package util;

import java.util.Collections;
import java.util.List;

/**
 * 営業日に関するユーティリティクラス。
 */
public class CalendarUtil {

	/**
	 * 前営業日の日付を検索する。
	 * 
	 * @param calendarList チャートカレンダーのリスト。
	 * @param date         今日の日付。
	 * @return 前営業日の日付。存在しない場合はnull。
	 */
	public static String prevWorkday(List<String> calendarList, String date) {
		int idx = Collections.binarySearch(calendarList, date);
		if (idx > 0) {
			return calendarList.get(idx - 1);
		}
		return null;
	}

	/**
	 * 翌営業日の日付を検索する。
	 * 
	 * @param calendarList チャートカレンダーのリスト。
	 * @param date         今日の日付。
	 * @return 翌営業日の日付。存在しない場合はnull。
	 */
	public static String nextWorkday(List<String> calendarList, String date) {
		int idx = Collections.binarySearch(calendarList, date);
		if (0 <= idx && idx < calendarList.size() - 1) {
			return calendarList.get(idx + 1);
		}
		return null;
	}

}
