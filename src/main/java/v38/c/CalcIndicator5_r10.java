package v38.c;

import java.io.PrintWriter;
import java.util.List;

import v38.CalcIndicatorCommon_r10;
import v38.bean.CalcIndicatorInfo_r10;
import v38.bean.MergeChartInfo_r10;
import v38.factory.IndicatorCode;
import v38.i.CalcIndicator_r10;

/**
 * テクニカル指標(Pivot)を計算するクラス。
 */
public class CalcIndicator5_r10 extends CalcIndicatorCommon_r10 implements CalcIndicator_r10 {

	/**
	 * コンストラクタ。
	 * 
	 * @param name ディレクトリ名。
	 * @param bar  足名。
	 */
	public CalcIndicator5_r10(String name, String bar) {
		super(name, bar);
	}

	/**
	 * テクニカル指標の種別コードを取得する。
	 * 
	 * @return テクニカル指標の種別コード。
	 */
	public IndicatorCode getCode() {
		return IndicatorCode.Pivot;
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
		for (int i = 1; i < chartList.size(); i++) {
			MergeChartInfo_r10 mci0 = chartList.get(i - 1);
			MergeChartInfo_r10 mci1 = chartList.get(i);
			int high = mci0.highPrice;
			int low = mci0.lowPrice;
			int close = mci0.closePrice;
			double p = (high + low + close) / 3.0;
			double d1 = high - p;
			double d2 = p - low;
			double d3 = high - low;
			indicatorList.add(new CalcIndicatorInfo_r10(mci1, p, d1, d2, d3));
		}
	}

	/**
	 * 計算値を表示する。
	 * 
	 * @param pw stdoutファイル。
	 * @return 保存した件数。
	 */
	protected int printIndicator(PrintWriter pw) {
		int writeCnt = 0;
		for (CalcIndicatorInfo_r10 cii : indicatorList) {
			double p = cii.values[0];
			double d1 = cii.values[1];
			double d2 = cii.values[2];
			double d3 = cii.values[3];
			double b1 = p - d1;
			double b2 = p - d3;
			double lbop = b1 - d3;
			double s1 = p + d2;
			double s2 = p + d3;
			double hbop = s1 + d3;
			pw.printf("%s,%d,%d", cii.date, cii.closePrice, cii.flag);
			pw.printf(",%.2f,%.2f,%.2f,%.2f,%.2f,%.2f,%.2f", lbop, b2, b1, p, s1, s2, hbop);
			pw.println();
			writeCnt++;
		}
		return writeCnt;
	}

}
