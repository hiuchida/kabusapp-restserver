package util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 時刻に関するユーティリティクラス。
 */
public class TimeUtil {

	/**
	 * 日付型から時刻を文字列で取得する。
	 * 
	 * @param date 日付型。
	 * @return 時刻文字列(HH:mm)。
	 */
	public static String toString(Date date) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		return sdf.format(date);
	}

	/**
	 * 00:00:00を基準とした経過時間（秒）を計算する。
	 * 
	 * @param hour 時。
	 * @param min  分。
	 * @param sec  秒。
	 * @return 経過時間。
	 */
	public static int time_val(int hour, int min, int sec) {
		return hour * 60 * 60 + min * 60 + sec;
	}

	/**
	 * 00:00:00を基準とした経過時間（秒）を計算する。
	 * 
	 * @param s 時刻文字列(HH:mm:ss)。
	 * @return 経過時間。
	 */
	public static int time_val(String s) {
		if (s == null || s.length() < 8 || s.charAt(2) != ':' || s.charAt(5) != ':') {
			return 0;
		}
		if (s.length() > 8) {
			s = s.substring(0, 8);
		}
		int hour = StringUtil.parseInt(s.substring(0, 2));
		int min = StringUtil.parseInt(s.substring(3, 5));
		int sec = StringUtil.parseInt(s.substring(6, 8));
		return time_val(hour, min, sec);
	}

	/**
	 * 00:00:00を基準とした経過時間（秒）の時を取得する。
	 * 
	 * @param time_val 経過時間。
	 * @return 時(0-23)。
	 */
	public static int hourValue(int time_val) {
		while (time_val < 0) {
			time_val += 24 * 60 * 60;
		}
		int hour = time_val / 3600 % 24;
		return hour;
	}

	/**
	 * 00:00:00を基準とした経過時間（秒）を文字列で取得する。
	 * 
	 * @param time_val 経過時間。
	 * @return 時刻文字列(00:00:00-23:59:59)。
	 */
	public static String toString(int time_val) {
		while (time_val < 0) {
			time_val += 24 * 60 * 60;
		}
		int hour = time_val / 3600 % 24;
		int min = time_val / 60 % 60;
		int sec = time_val % 60;
		String time = String.format("%02d:%02d:%02d", hour, min, sec);
		return time;
	}

	/**
	 * 00:00:00を基準とした経過時間（秒）を文字列で取得する。
	 * 
	 * @param time_val 経過時間。
	 * @param b99h     true:99時まで、false:23時まで。
	 * @return 時刻文字列(00:00:00-23:59:59または99:59:59)。
	 */
	public static String toString(int time_val, boolean b99h) {
		while (time_val < 0) {
			time_val += 24 * 60 * 60;
		}
		int hour = time_val / 3600;
		if (b99h) {
			while (hour > 99) {
				hour -= 24;
			}
		} else {
			hour %= 24;
		}
		int min = time_val / 60 % 60;
		int sec = time_val % 60;
		String time = String.format("%02d:%02d:%02d", hour, min, sec);
		return time;
	}

	private TimeUtil() {
	}

}
