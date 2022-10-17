package v38.c;

import java.io.PrintWriter;
import java.util.List;

import v38.CalcIndicatorCommon_r10;
import v38.bean.CalcIndicatorInfo_r10;
import v38.bean.MergeChartInfo_r10;
import v38.factory.IndicatorCode;
import v38.i.CalcIndicator_r10;

/**
 * テクニカル指標(ボリンジャーバンド)を計算するクラス。
 */
public class CalcIndicator2_r10 extends CalcIndicatorCommon_r10 implements CalcIndicator_r10 {

	/**
	 * コンストラクタ。
	 * 
	 * @param name ディレクトリ名。
	 * @param bar  足名。
	 */
	public CalcIndicator2_r10(String name, String bar) {
		super(name, bar);
	}

	/**
	 * テクニカル指標の種別コードを取得する。
	 * 
	 * @return テクニカル指標の種別コード。
	 */
	public IndicatorCode getCode() {
		return IndicatorCode.BollingerBands;
	}

	/**
	 * テクニカル指標を計算する。
	 * 
	 * @param chartList マージしたチャートデータを時系列に並べたリスト。
	 */
	protected void calcIndicator(List<MergeChartInfo_r10> chartList) {
		final int param1 = 25;
		long sqr = 0;
		int sum = 0;
		int cnt = 0;
		for (int i = 0; i < chartList.size(); i++) {
			MergeChartInfo_r10 mci = chartList.get(i);
			int price = mci.closePrice;
			if (cnt < param1) {
				sqr += price * price;
				sum += price;
				cnt++;
			} else {
				int p_1 = chartList.get(i - param1).closePrice;
				sqr += price * price - p_1 * p_1;
				sum += price - p_1;
			}
			if (cnt == param1) {
				double mean = (double) sum / cnt;
				double variance = (double)sqr / cnt - mean * mean;
				double sd = Math.sqrt(variance);
				indicatorList.add(new CalcIndicatorInfo_r10(mci, mean, sd));
			}
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
			double mean = cii.values[0];
			double sd = cii.values[1];
			pw.printf("%s,%d,%d", cii.date, cii.closePrice, cii.flag);
			pw.printf(",%.2f,%.2f,%.2f,%.2f,%.2f,%.2f", sd, mean - 2 * sd, mean - sd, mean, mean + sd, mean + 2 * sd);
			pw.println();
			writeCnt++;
		}
		return writeCnt;
	}

}
