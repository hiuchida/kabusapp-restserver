package logic;

import java.util.List;

import util.TimeUtil;

/**
 * 足の時刻を管理する共通クラス。
 */
public class ChartTimeLogic {
	/**
	 * 1分足の間隔（秒）。
	 */
	public static final int DELTA1m = 1 * 60;
	/**
	 * 3分足の間隔（秒）。
	 */
	public static final int DELTA3m = 3 * 60;
	/**
	 * 5分足の間隔（秒）。
	 */
	public static final int DELTA5m = 5 * 60;
	/**
	 * 10分足の間隔（秒）。
	 */
	public static final int DELTA10m = 10 * 60;
	/**
	 * 15分足の間隔（秒）。
	 */
	public static final int DELTA15m = 15 * 60;
	/**
	 * 20分足の間隔（秒）。
	 */
	public static final int DELTA20m = 20 * 60;
	/**
	 * 30分足の間隔（秒）。
	 */
	public static final int DELTA30m = 30 * 60;
	/**
	 * 60分足の間隔（秒）。
	 */
	public static final int DELTA60m = 60 * 60;
	/**
	 * 1日の間隔（秒）。
	 */
	public static final int DELTA1d = 24 * 60 * 60;

	/**
	 * 日付変更線の時刻。
	 */
	public static final int 日付変更線 = TimeUtil.time_val(8, 0, 0);
	/**
	 * 先物日中の開始時刻。
	 */
	public static final int 先物日中開始時刻 = TimeUtil.time_val(8, 45, 0);
	/**
	 * 先物日中の終了時刻。
	 */
	public static final int 先物日中終了時刻 = TimeUtil.time_val(15, 15, 0);
	/**
	 * 先物夜間の開始時刻。
	 */
	public static final int 先物夜間開始時刻 = TimeUtil.time_val(16, 30, 0);
	/**
	 * 先物夜間の終了時刻。
	 */
	public static final int 先物夜間終了時刻 = TimeUtil.time_val(30, 0, 0);
	/**
	 * 現物午前の開始時刻。
	 */
	public static final int 現物午前開始時刻 = TimeUtil.time_val(9, 0, 0);
	/**
	 * 現物午前の終了時刻。
	 */
	public static final int 現物午前終了時刻 = TimeUtil.time_val(11, 30, 0);
	/**
	 * 現物午後の開始時刻。
	 */
	public static final int 現物午後開始時刻 = TimeUtil.time_val(12, 30, 0);
	/**
	 * 現物午後の終了時刻。
	 */
	public static final int 現物午後終了時刻 = TimeUtil.time_val(15, 0, 0);

	/**
	 * 時刻リストを作成する。
	 * 
	 * @param times 追加する時刻リスト。
	 * @param st    開始時刻。
	 * @param ed    終了時刻。
	 * @param dt    間隔。
	 */
	public static void init(List<String> list, int st, int ed, int dt) {
		int tim = st;
		int cnt = (ed - tim) / dt + 1;
		for (int i = 0; i < cnt; i++) {
			String time = TimeUtil.toString(tim, false);
			list.add(time);
			tim += dt;
		}
	}

	/**
	 * 先物日中の時刻リストを初期化する。
	 * 
	 * @param times 初期化する時刻リスト。
	 * @param dt    間隔。
	 */
	public static void init1(List<String> list, int dt) {
		int st;
		int ed;
		{
			st = ChartTimeLogic.先物日中開始時刻;
			ed = ChartTimeLogic.先物日中終了時刻;
			ChartTimeLogic.init(list, st, ed, dt);
		}
		{
			st = ChartTimeLogic.先物夜間開始時刻;
			ed = TimeUtil.time_val(23, 59, 59);
			ChartTimeLogic.init(list, st, ed, dt);
		}
	}

	/**
	 * 現物日中の時刻リストを初期化する。
	 * 
	 * @param times 初期化する時刻リスト。
	 * @param dt    間隔。
	 */
	public static void init2(List<String> list, int dt) {
		int st;
		int ed;
		{
			st = ChartTimeLogic.現物午前開始時刻;
			ed = ChartTimeLogic.現物午前終了時刻;
			ChartTimeLogic.init(list, st, ed, dt);
		}
		{
			st = ChartTimeLogic.現物午後開始時刻;
			ed = ChartTimeLogic.現物午後終了時刻;
			ChartTimeLogic.init(list, st, ed, dt);
		}
	}

	/**
	 * 先物夜間の時刻リストを初期化する。
	 * 
	 * @param times 初期化する時刻リスト。
	 * @param st    開始時刻。
	 * @param dt    間隔。
	 */
	public static void init3(List<String> list, int st, int dt) {
		int ed;
		{
			ed = TimeUtil.time_val(6, 0, 0);
			ChartTimeLogic.init(list, st, ed, dt);
		}
	}

	/**
	 * 先物夜間の時刻リストを初期化する。
	 * 
	 * @param times 初期化する時刻リスト。
	 * @param dt    間隔。
	 */
	public static void init3(List<String> list, int dt) {
		int st;
		int ed;
		{
			st = TimeUtil.time_val(0, 0, 0);
			ed = TimeUtil.time_val(6, 0, 0);
			ChartTimeLogic.init(list, st, ed, dt);
		}
	}

	/**
	 * 指定した時刻が所属する時刻を検索する。
	 * 
	 * @param dt      間隔。
	 * @param bFuture true:先物/OP、false:現物/指数
	 * @param time    現値の時刻。
	 * @return 所属する時刻。範囲外の場合はnull。
	 */
	public static String search(int dt, boolean bFuture, String time) {
		if (time == null) {
			return null;
		}
		int off = 0;
		int ed = -1;
		int tim = TimeUtil.time_val(time);
		if (bFuture) {
			if (tim < ChartTimeLogic.日付変更線) {
				tim += ChartTimeLogic.DELTA1d;
			}
			if (tim >= ChartTimeLogic.先物夜間開始時刻) {
				off = ChartTimeLogic.先物夜間開始時刻;
				ed = ChartTimeLogic.先物夜間終了時刻;
			} else if (tim >= ChartTimeLogic.先物日中開始時刻) {
				off = ChartTimeLogic.先物日中開始時刻;
				ed = ChartTimeLogic.先物日中終了時刻;
			}
		} else {
			if (tim >= ChartTimeLogic.現物午後開始時刻) {
				off = ChartTimeLogic.現物午後開始時刻;
				ed = ChartTimeLogic.現物午後終了時刻;
			} else if (tim >= ChartTimeLogic.現物午前開始時刻) {
				off = ChartTimeLogic.現物午前開始時刻;
				ed = ChartTimeLogic.現物午前終了時刻;
			}
		}
		tim -= off;
		tim = tim / dt * dt;
		tim += off;
		if (tim > ed) {
			return null;
		}
		time = TimeUtil.toString(tim);
		return time;
	}

	private ChartTimeLogic() {
	}

}
