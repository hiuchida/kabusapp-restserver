package v38.c;

import java.util.List;

import v38.CalcIndicatorCommon_r10;
import v38.bean.CalcIndicatorInfo_r10;
import v38.bean.MergeChartInfo_r10;
import v38.factory.IndicatorCode;
import v38.i.CalcIndicator_r10;

/**
 * テクニカル指標(HV20)を計算するクラス。
 */
public class CalcIndicator4_r10 extends CalcIndicatorCommon_r10 implements CalcIndicator_r10 {

	/**
	 * コンストラクタ。
	 * 
	 * @param name ディレクトリ名。
	 * @param bar  足名。
	 */
	public CalcIndicator4_r10(String name, String bar) {
		super(name, bar);
	}

	/**
	 * テクニカル指標の種別コードを取得する。
	 * 
	 * @return テクニカル指標の種別コード。
	 */
	public IndicatorCode getCode() {
		return IndicatorCode.HV;
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
		// 2件目以降の前日比の自然対数を求める
		double[] ratio = new double[chartList.size()];
		for (int i = 1; i < chartList.size(); i++) {
			int p1 = chartList.get(i).closePrice;
			int p0 = chartList.get(i - 1).closePrice;
			double r = (double) p1 / (double) p0;
			ratio[i] = Math.log(r);
		}
		// 標準偏差を求める
		double sqr = 0;
		double sum = 0;
		int cnt = 0;
		for (int i = 1; i < ratio.length; i++) {
			MergeChartInfo_r10 mci = chartList.get(i);
			double r = ratio[i];
			if (cnt < 20) {
				sqr += r * r;
				sum += r;
				cnt++;
			} else {
				double r_20 = ratio[i - 20];
				sqr += r * r - r_20 * r_20;
				sum += r - r_20;
			}
			if (cnt == 20) {
				double mean = (double) sum / cnt;
				double variance = (double)sqr / cnt - mean * mean;
				double sd = Math.sqrt(variance);
				double hv = sd * Math.sqrt(250 * 20 * 60);
				indicatorList.add(new CalcIndicatorInfo_r10(mci, sd, hv));
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
		double sd = cii.values[0];
		double hv = cii.values[1];
		sb.append(String.format("%s,%d,%d", cii.date, cii.closePrice, cii.flag));
		sb.append(String.format(",%.5f,%.5f", sd, hv));
		return sb.toString();
	}

}
