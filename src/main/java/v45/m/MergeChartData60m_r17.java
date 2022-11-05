package v45.m;

import java.util.List;

import logic.ChartTime60mLogic;
import v45.MergeChartDataCommon_r17;
import v45.factory.BarCode;
import v45.i.MergeChartData_r17;

/**
 * 60分足の保存した4本値チャートデータと、PUSH APIで受信したチャートデータをマージした4本値と売買高を出力する。
 */
public class MergeChartData60m_r17 extends MergeChartDataCommon_r17 implements MergeChartData_r17 {
	/**
	 * コンストラクタ。
	 * 
	 * @param name ディレクトリ名。
	 * @param bar  足名。
	 */
	public MergeChartData60m_r17(String name, String bar) {
		super(name, bar);
	}

	/**
	 * 足名の種別コードを取得する。
	 * 
	 * @return 足名の種別コード。
	 */
	public BarCode getCode() {
		return BarCode.M60;
	}

	/**
	 * 指定した時刻が前日扱いかどうか判定する。
	 * 
	 * @param time 現値の時刻。
	 * @return true:前日扱い、false:当日扱いまたは無効。
	 */
	protected boolean isYesterday(String time) {
		boolean bYesterday = ChartTime60mLogic.isYesterday(time);
		return bYesterday;
	}

	/**
	 * 指定した時刻が所属する時刻を検索する。
	 * 
	 * @param time 現値の時刻。
	 * @return 所属する時刻。範囲外の場合はnull。
	 */
	protected String search(String time) {
		String startTime = ChartTime60mLogic.search(super.isFuture(), time);
		return startTime;
	}

	/**
	 * 日中（08:45-23:59）の時刻の一覧を取得する。
	 * 
	 * @return 時刻文字列のリスト。
	 */
	protected List<String> daylightTimes() {
		List<String> dayTimes = ChartTime60mLogic.daylightTimes(super.isFuture());
		return dayTimes;
	}

	/**
	 * 深夜(00:00-06:00)の時刻の一覧を取得する。
	 * 
	 * @return 時刻文字列のリスト。
	 */
	protected List<String> midnightTimes() {
		List<String> nightTimes = ChartTime60mLogic.midnightTimes(super.isFuture());
		return nightTimes;
	}

}
