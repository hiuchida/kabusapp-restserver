package v38.c;

import java.util.List;

import v38.CalcIndicatorCommon_r10;
import v38.bean.CalcIndicatorInfo_r10;
import v38.bean.MergeChartInfo_r10;
import v38.factory.IndicatorCode;
import v38.i.CalcIndicator_r10;

/**
 * テクニカル指標(パラボリック)を計算するクラス。
 */
public class CalcIndicator7_r10 extends CalcIndicatorCommon_r10 implements CalcIndicator_r10 {

	/**
	 * コンストラクタ。
	 * 
	 * @param name ディレクトリ名。
	 * @param bar  足名。
	 */
	public CalcIndicator7_r10(String name, String bar) {
		super(name, bar);
	}

	/**
	 * テクニカル指標の種別コードを取得する。
	 * 
	 * @return テクニカル指標の種別コード。
	 */
	public IndicatorCode getCode() {
		return IndicatorCode.Parabolic;
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
		MergeChartInfo_r10 mci = chartList.get(0);
		double sar = mci.openPrice;
		boolean bBull = true;
		int ep = mci.openPrice;
		int eph = ep;
		int epl = ep;
		int af = 0;
		indicatorList.add(new CalcIndicatorInfo_r10(mci, sar, bBull ? 1 : -1, ep, af));
		for (int i = 1; i < chartList.size(); i++) {
			MergeChartInfo_r10 mci0 = chartList.get(i - 1);
			MergeChartInfo_r10 mci1 = chartList.get(i);
			int high0 = mci0.highPrice;
			int low0 = mci0.lowPrice;
			int high1 = mci1.highPrice;
			int low1 = mci1.lowPrice;
			if (low0 < epl) {
				epl = low0;
			}
			if (eph < high0) {
				eph = high0;
			}
			if (bBull) {
				if (ep < high0) {
					ep = high0;
					af += 2;
				}
			} else {
				if (low0 < ep) {
					ep = low0;
					af += 2;
				}
			}
			if (af <= 0) {
				af = 2;
			} else if (af > 20) {
				af = 20;
			}
			sar = (ep - sar) * af / 100 + sar;
			if (bBull) {
				if (low1 < sar) {
					sar = ep;
					ep = eph;
					eph = epl;
					epl = eph;
					af = 0;
					bBull = !bBull;
				}
			} else {
				if (sar < high1) {
					sar = ep;
					ep = epl;
					epl = eph;
					eph = epl;
					af = 0;
					bBull = !bBull;
				}
			}
			indicatorList.add(new CalcIndicatorInfo_r10(mci1, sar, bBull ? 1 : -1, ep, af));
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
		double sar = cii.values[0];
		double bBull = cii.values[1];
		double ep = cii.values[2];
		double af = cii.values[3] / 100;
		sb.append(String.format("%s,%d,%d", cii.date, cii.closePrice, cii.flag));
		sb.append(String.format(",%.2f,%s,%.2f,%.2f", sar, bBull > 0 ? "L" : "S", ep, af));
		return sb.toString();
	}

}