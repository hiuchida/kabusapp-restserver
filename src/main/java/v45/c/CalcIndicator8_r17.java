package v45.c;

import java.util.List;

import bean.CalcIndicatorInfo_r10;
import bean.MergeChartInfo_r10;
import v45.CalcIndicatorCommon_r17;
import v45.factory.IndicatorCode;
import v45.i.CalcIndicator_r17;

/**
 * テクニカル指標(転換点)を計算するクラス。
 */
public class CalcIndicator8_r17 extends CalcIndicatorCommon_r17 implements CalcIndicator_r17 {

	/**
	 * コンストラクタ。
	 * 
	 * @param name ディレクトリ名。
	 * @param bar  足名。
	 */
	public CalcIndicator8_r17(String name, String bar) {
		super(name, bar);
	}

	/**
	 * テクニカル指標の種別コードを取得する。
	 * 
	 * @return テクニカル指標の種別コード。
	 */
	public IndicatorCode getCode() {
		return IndicatorCode.Turning;
	}

	/**
	 * テクニカル指標を計算する。
	 * 
	 * @param chartList マージしたチャートデータを時系列に並べたリスト。
	 */
	protected void calcIndicator(List<MergeChartInfo_r10> chartList) {
		if (chartList.size() < 1) {
			return;
		}
		MergeChartInfo_r10 mci0 = chartList.get(0);
		boolean bHigh = true;
		int highPrice = mci0.highPrice;
		int lowPrice = mci0.lowPrice;
		double reversal = highPrice;
		indicatorList.add(new CalcIndicatorInfo_r10(mci0, highPrice, bHigh ? 1 : -1, reversal, highPrice));
		for (int i = 10; i < chartList.size(); i++) {
			if (i + 10 >= chartList.size()) {
				break;
			}
			MergeChartInfo_r10 mci = chartList.get(i);
			int high0 = mci.highPrice;
			int low0 = mci.lowPrice;
			int high1 = high0;
			int low1 = low0;
			int high2 = high1;
			int low2 = low1;
			for (int j = 1; j <= 10; j++) {
				MergeChartInfo_r10 mci1 = chartList.get(i - j);
				high1 = Math.max(high1, mci1.highPrice);
				low1 = Math.min(low1, mci1.lowPrice);
				high2 = Math.max(high2, mci1.highPrice);
				low2 = Math.min(low2, mci1.lowPrice);
				MergeChartInfo_r10 mci2 = chartList.get(i + j);
				high2 = Math.max(high2, mci2.highPrice);
				low2 = Math.min(low2, mci2.lowPrice);
			}
			if (bHigh) {
				if (high0 >= high2 && high0 >= highPrice) {
					indicatorList.remove(indicatorList.size() - 1);
					highPrice = high0;
					reversal = highPrice + (low1 - highPrice) * 0.5;
					indicatorList.add(new CalcIndicatorInfo_r10(mci, highPrice, bHigh ? 1 : -1, reversal, low1));
				} else if (low0 <= low2 && low0 <= reversal) {
					bHigh = !bHigh;
					lowPrice = low0;
					reversal = lowPrice + (high1 - lowPrice) * 0.5;
					indicatorList.add(new CalcIndicatorInfo_r10(mci, lowPrice, bHigh ? 1 : -1, reversal, high1));
				}
			} else {
				if (low0 <= low2 && low0 <= lowPrice) {
					indicatorList.remove(indicatorList.size() - 1);
					lowPrice = low0;
					reversal = lowPrice + (high1 - lowPrice) * 0.5;
					indicatorList.add(new CalcIndicatorInfo_r10(mci, lowPrice, bHigh ? 1 : -1, reversal, high1));
				} else if (high0 >= high2 && high0 >= reversal) {
					bHigh = !bHigh;
					highPrice = high0;
					reversal = highPrice + (low1 - highPrice) * 0.5;
					indicatorList.add(new CalcIndicatorInfo_r10(mci, highPrice, bHigh ? 1 : -1, reversal, low1));
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
		int price = (int) cii.values[0];
		double bHigh = cii.values[1];
		double reversal = cii.values[2];
		int target = (int) cii.values[3];
		sb.append(String.format("%s,%d,%d", cii.date, cii.closePrice, cii.flag));
		sb.append(String.format(",%d,%s,%.2f,%d", price, bHigh > 0 ? "H" : "L", reversal, target));
		return sb.toString();
	}

}
