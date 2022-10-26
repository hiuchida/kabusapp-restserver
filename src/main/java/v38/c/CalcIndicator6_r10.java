package v38.c;

import java.util.List;

import bean.MergeChartInfo_r10;
import v38.CalcIndicatorCommon_r10;
import v38.bean.CalcIndicatorInfo_r10;
import v38.factory.IndicatorCode;
import v38.i.CalcIndicator_r10;

/**
 * テクニカル指標(DMI)を計算するクラス。
 */
public class CalcIndicator6_r10 extends CalcIndicatorCommon_r10 implements CalcIndicator_r10 {

	/**
	 * テクニカル指標作業用クラス。
	 */
	public static class IndicatorWork {
		/**
		 * +Direction Movement
		 */
		public int pdm;
		/**
		 * -Direction Movement
		 */
		public int mdm;
		/**
		 * True Range
		 */
		public int tr;
		/**
		 * +Direction Indicator
		 */
		public double pdi;
		/**
		 * -Direction Indicator
		 */
		public double mdi;
		/**
		 * DX
		 */
		public double dx;
		/**
		 * ADX
		 */
		public double adx;

		/**
		 * コンストラクタ。
		 * 
		 * @param pdm +DM。
		 * @param mdm -DM。
		 * @param tr TR。
		 */
		public IndicatorWork(int pdm, int mdm, int tr) {
			this.pdm = pdm;
			this.mdm = mdm;
			this.tr = tr;
			this.pdi = -1;
			this.mdi = -1;
			this.dx = -1;
			this.adx = -1;
		}

	}

	/**
	 * コンストラクタ。
	 * 
	 * @param name ディレクトリ名。
	 * @param bar  足名。
	 */
	public CalcIndicator6_r10(String name, String bar) {
		super(name, bar);
	}

	/**
	 * テクニカル指標の種別コードを取得する。
	 * 
	 * @return テクニカル指標の種別コード。
	 */
	public IndicatorCode getCode() {
		return IndicatorCode.DMI;
	}

	/**
	 * テクニカル指標を計算する。
	 * 
	 * @param chartList マージしたチャートデータを時系列に並べたリスト。
	 */
	protected void calcIndicator(List<MergeChartInfo_r10> chartList) {
		final int param1 = 14;
		final int param2 = 9;
		final int param3 = 14;
		if (chartList.size() < 2) {
			return;
		}
		IndicatorWork[] work = new IndicatorWork[chartList.size()];
		for (int i = 1; i < chartList.size(); i++) {
			MergeChartInfo_r10 mci0 = chartList.get(i - 1);
			MergeChartInfo_r10 mci1 = chartList.get(i);
			int high0 = mci0.highPrice;
			int low0 = mci0.lowPrice;
			int close0 = mci0.closePrice;
			int high1 = mci1.highPrice;
			int low1 = mci1.lowPrice;
			int pdm = high1 - high0;
			int mdm = low0 - low1;
			if (pdm == mdm) {
				pdm = 0;
				mdm = 0;
			} else if (pdm < 0 && mdm < 0) {
				pdm = 0;
				mdm = 0;
			} else if (pdm > mdm) {
				mdm = 0;
			} else {
				pdm = 0;
			}
			int tr1 = high1 - close0;
			int tr2 = close0 - low1;
			int tr3 = high1 - low1;
			int tr = Math.max(tr1,  Math.max(tr2, tr3));
			work[i] = new IndicatorWork(pdm, mdm, tr);
		}
		int cnt = 0;
		int sum1 = 0;
		int sum2 = 0;
		int sum3 = 0;
		for (int i = 1; i < chartList.size(); i++) {
			if (cnt < param1) {
				sum1 += work[i].pdm;
				sum2 += work[i].mdm;
				sum3 += work[i].tr;
				cnt++;
			} else {
				sum1 += work[i].pdm - work[i - param1].pdm;
				sum2 += work[i].mdm - work[i - param1].mdm;
				sum3 += work[i].tr - work[i - param1].tr;
			}
			if (cnt == param1) {
				double pdi = 0.0;
				double mdi = 0.0;
				if (sum3 > 0) {
					pdi = (double) sum1 / sum3 * 100.0;
					mdi = (double) sum2 / sum3 * 100.0;
				}
				double dx = 0.0;
				if (pdi + mdi > 0.0) {
					dx = Math.abs(pdi - mdi) / (pdi + mdi) * 100.0;
				}
				work[i].pdi = pdi;
				work[i].mdi = mdi;
				work[i].dx = dx;
			}
		}
		cnt = 0;
		double sum4 = 0.0;
		for (int i = 1; i < chartList.size(); i++) {
			double dx = work[i].dx;
			if (dx < 0.0) {
				continue;
			}
			if (cnt < param2) {
				sum4 += dx;
				cnt++;
			} else {
				sum4 += dx - work[i - param2].dx;
			}
			if (cnt == param2) {
				double adx = sum4 / cnt;
				work[i].adx = adx;
			}
		}
		for (int i = param3; i < chartList.size(); i++) {
			MergeChartInfo_r10 mci = chartList.get(i);
			if (work[i - param3] == null) {
				continue;
			}
			double adx0 = work[i - param3].adx;
			double pdi = work[i].pdi;
			double mdi = work[i].mdi;
			double adx = work[i].adx;
			if (adx0 < 0.0) {
				continue;
			}
			double adxr = (adx + adx0) / 2.0; 
			indicatorList.add(new CalcIndicatorInfo_r10(mci, pdi, mdi, adx, adxr));
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
		double pdi = cii.values[0];
		double mdi = cii.values[1];
		double adx = cii.values[2];
		double adxr = cii.values[3];
		sb.append(String.format("%s,%d,%d", cii.date, cii.closePrice, cii.flag));
		sb.append(String.format(",%.2f,%.2f,%.2f,%.2f", pdi, mdi, adx, adxr));
		return sb.toString();
	}

}
