package v38.c;

import java.util.List;

import bean.CalcIndicatorInfo_r10;
import bean.MergeChartInfo_r10;
import v38.CalcIndicatorCommon_r10;
import v38.factory.IndicatorCode;
import v38.i.CalcIndicator_r10;

/**
 * テクニカル指標(MACD(5,20,9))を計算するクラス。
 */
public class CalcIndicator3_r10 extends CalcIndicatorCommon_r10 implements CalcIndicator_r10 {

	/**
	 * コンストラクタ。
	 * 
	 * @param name ディレクトリ名。
	 * @param bar  足名。
	 */
	public CalcIndicator3_r10(String name, String bar) {
		super(name, bar);
	}

	/**
	 * テクニカル指標の種別コードを取得する。
	 * 
	 * @return テクニカル指標の種別コード。
	 */
	public IndicatorCode getCode() {
		return IndicatorCode.MACD;
	}

	/**
	 * テクニカル指標を計算する。
	 * 
	 * @param chartList マージしたチャートデータを時系列に並べたリスト。
	 */
	protected void calcIndicator(List<MergeChartInfo_r10> chartList) {
		if (chartList.size() < 2) {
			return;
		}
		// 平滑定数を事前に計算する
		final double a5 = 2.0 / (5 + 1);
		final double a20 = 2.0 / (20 + 1);
		final double a9 = 2.0 / (9 + 1);
		// 初項を初期化する
		MergeChartInfo_r10 mci = chartList.get(0);
		double ema5 = mci.closePrice;
		double ema20 = mci.closePrice;
		double signal9 = Double.MIN_VALUE;
		for (int i = 1; i < chartList.size(); i++) {
			mci = chartList.get(i);
			ema5 += a5 * (mci.closePrice - ema5);
			ema20 += a20 * (mci.closePrice - ema20);
			double macd = ema5 - ema20;
			if (signal9 == Double.MIN_VALUE) {
				signal9 = macd;
			} else {
				signal9 += a9 * (macd - signal9);
				indicatorList.add(new CalcIndicatorInfo_r10(mci, ema5, ema20, macd, signal9));
			}
		}
	}

	/**
	 * 計算値の文字列に変換する。
	 * 
	 * @param cii テクニカル指標情報。
	 * @return ファイルに保存する文字列。
	 */
	protected String toLineString(CalcIndicatorInfo_r10 cii) {
		StringBuilder sb = new StringBuilder();
		double ema5 = cii.values[0];
		double ema20 = cii.values[1];
		double macd = cii.values[2];
		double signal9 = cii.values[3];
		sb.append(String.format("%s,%d,%d", cii.date, cii.closePrice, cii.flag));
		sb.append(String.format(",%.2f,%.2f,%.2f,%.2f", ema5, ema20, macd, signal9));
		return sb.toString();
	}

}
