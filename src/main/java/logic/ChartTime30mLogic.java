package logic;

import java.util.ArrayList;
import java.util.List;

/**
 * 30分足の時刻を管理するクラス。
 */
public class ChartTime30mLogic {
	/**
	 * 先物日中の時刻リスト。
	 */
	private static List<String> list1 = new ArrayList<>();
	/**
	 * 現物日中の時刻リスト。
	 */
	private static List<String> list2 = new ArrayList<>();
	/**
	 * 先物夜間の時刻リスト。
	 */
	private static List<String> list3 = new ArrayList<>();
	/**
	 * 現物夜間の時刻リスト。
	 */
	private static List<String> list4 = new ArrayList<>();

	static {
		int dt = ChartTimeLogic.DELTA30m;
		ChartTimeLogic.init1(list1, dt);
		ChartTimeLogic.init2(list2, dt);
		ChartTimeLogic.init3(list3, dt);
	}

	/**
	 * 指定した時刻が所属する時刻を検索する。
	 * 
	 * @param bFuture true:先物/OP、false:現物/指数
	 * @param time    現値の時刻。
	 * @return 所属する時刻。範囲外の場合はnull。
	 */
	public static String search(boolean bFuture, String time) {
		int dt = ChartTimeLogic.DELTA30m;
		String startTime = ChartTimeLogic.search(dt, bFuture, time);
		return startTime;
	}

	/**
	 * 日中（08:45-23:59）の時刻の一覧を取得する。
	 * 
	 * @param bFuture true:先物/OP、false:現物/指数
	 * @return 時刻文字列のリスト。
	 */
	public static List<String> daylightTimes(boolean bFuture) {
		if (bFuture) {
			return list1;
		} else {
			return list2;
		}
	}

	/**
	 * 深夜(00:00-06:00)の時刻の一覧を取得する。
	 * 
	 * @param bFuture true:先物/OP、false:現物/指数
	 * @return 時刻文字列のリスト。
	 */
	public static List<String> midnightTimes(boolean bFuture) {
		if (bFuture) {
			return list3;
		} else {
			return list4;
		}
	}

	private ChartTime30mLogic() {
	}

}
