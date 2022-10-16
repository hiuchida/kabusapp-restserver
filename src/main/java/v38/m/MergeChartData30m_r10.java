package v38.m;

import java.util.List;

import logic.ChartTime30mLogic;
import v38.MergeChartDataCommon_r10;
import v38.factory.BarCode;
import v38.i.MergeChartData_r10;

/**
 * 30分足の保存した4本値チャートデータと、PUSH APIで受信したチャートデータをマージした4本値と売買高を出力する。
 */
public class MergeChartData30m_r10 extends MergeChartDataCommon_r10 implements MergeChartData_r10 {
	/**
	 * コンストラクタ。
	 * 
	 * @param name ディレクトリ名。
	 * @param bar  足名。
	 */
	public MergeChartData30m_r10(String name, String bar) {
		super(name, bar);
	}

	/**
	 * 足名の種別コードを取得する。
	 * 
	 * @return 足名の種別コード。
	 */
	public BarCode getCode() {
		return BarCode.M30;
	}

	/**
	 * 指定した時刻が所属する時刻を検索する。
	 * 
	 * @param time 現値の時刻。
	 * @return 所属する時刻。範囲外の場合はnull。
	 */
	protected String search(String time) {
		String startTime = ChartTime30mLogic.search(super.isFuture(), time);
		return startTime;
	}

	/**
	 * 日中（08:45-23:59）の時刻の一覧を取得する。
	 * 
	 * @return 時刻文字列のリスト。
	 */
	protected List<String> daylightTimes() {
		List<String> dayTimes = ChartTime30mLogic.daylightTimes(super.isFuture());
		return dayTimes;
	}

	/**
	 * 深夜(00:00-06:00)の時刻の一覧を取得する。
	 * 
	 * @return 時刻文字列のリスト。
	 */
	protected List<String> midnightTimes() {
		List<String> nightTimes = ChartTime30mLogic.midnightTimes(super.isFuture());
		return nightTimes;
	}

}
