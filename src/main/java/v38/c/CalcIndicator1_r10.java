package v38.c;

import java.util.List;

import bean.MergeChartInfo_r10;
import v38.CalcIndicatorCommon_r10;
import v38.bean.CalcIndicatorInfo_r10;
import v38.factory.IndicatorCode;
import v38.i.CalcIndicator_r10;

/**
 * テクニカル指標(SMA5,SMA25,SMA75)を計算するクラス。
 */
public class CalcIndicator1_r10 extends CalcIndicatorCommon_r10 implements CalcIndicator_r10 {

	/**
	 * コンストラクタ。
	 * 
	 * @param name ディレクトリ名。
	 * @param bar  足名。
	 */
	public CalcIndicator1_r10(String name, String bar) {
		super(name, bar);
	}

	/**
	 * テクニカル指標の種別コードを取得する。
	 * 
	 * @return テクニカル指標の種別コード。
	 */
	public IndicatorCode getCode() {
		return IndicatorCode.SMA;
	}

	/**
	 * テクニカル指標を計算する。
	 * 
	 * @param chartList マージしたチャートデータを時系列に並べたリスト。
	 */
	protected void calcIndicator(List<MergeChartInfo_r10> chartList) {
		final int param1 = 5;
		final int param2 = 25;
		final int param3 = 75;
		int sum1 = 0;
		int cnt1 = 0;
		int sum2 = 0;
		int cnt2 = 0;
		int sum3 = 0;
		int cnt3 = 0;
		for (int i = 0; i < chartList.size(); i++) {
			MergeChartInfo_r10 mci = chartList.get(i);
			int price = mci.closePrice;
			if (cnt1 < param1) {
				sum1 += price;
				cnt1++;
			} else {
				sum1 += price - chartList.get(i - param1).closePrice;
			}
			if (cnt2 < param2) {
				sum2 += price;
				cnt2++;
			} else {
				sum2 += price - chartList.get(i - param2).closePrice;
			}
			if (cnt3 < param3) {
				sum3 += price;
				cnt3++;
			} else {
				sum3 += price - chartList.get(i - param3).closePrice;
			}
			if (cnt1 == param1) {
				double mean1 = (double) sum1 / cnt1;
				if (cnt2 == param2) {
					double mean2 = (double) sum2 / cnt2;
					if (cnt3 == param3) {
						double mean3 = (double) sum3 / cnt3;
						indicatorList.add(new CalcIndicatorInfo_r10(mci, mean1, mean2, mean3));
					}
				}
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
		double mean1 = cii.values[0];
		double mean2 = cii.values[1];
		double mean3 = cii.values[2];
		sb.append(String.format("%s,%d,%d", cii.date, cii.closePrice, cii.flag));
		sb.append(String.format(",%.2f,%.2f,%.2f", mean1, mean2, mean3));
		return sb.toString();
	}

}
