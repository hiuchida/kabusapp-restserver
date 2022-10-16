package v38.i;

import java.util.List;
import java.util.Map;

import server.repository.ChartDataRepository;
import server.repository.ChartDbRepository;
import v38.bean.MergeChartInfo_r10;
import v38.factory.BarCode;

/**
 * 保存した4本値チャートデータと、PUSH APIで受信したチャートデータをマージした4本値と売買高を出力するインターフェイス。
 */
public interface MergeChartData_r10 {
	/**
	 * 足名の種別コードを取得する。
	 * 
	 * @return 足名の種別コード。
	 */
	public BarCode getCode();

	/**
	 * 保存した4本値チャートデータの終値と、PUSH APIで受信したチャートデータをマージする。
	 * 
	 * @param chartDataRepository
	 * @param chartDbRepository
	 */
	public void execute(ChartDataRepository chartDataRepository, ChartDbRepository chartDbRepository);

	/**
	 * 1分足の4本値チャートＤＢファイルから終値を読み込む。
	 * 
	 * @param chartDbRepository
	 */
	public void readDbChartData(ChartDbRepository chartDbRepository);

	/**
	 * PUSH APIで受信したチャートデータファイルから現値を読み込む。
	 * 
	 * @param chartDataRepository
	 * @return チャートデータ。
	 */
	public List<String> readCsvChartData(ChartDataRepository chartDataRepository);

	/**
	 * 4本値チャートデータの終値と、PUSH APIで受信したチャートデータをマージする。
	 * 
	 * @param lines チャートデータ。
	 */
	public void mergeCsvChartData(List<String> lines);

	/**
	 * マージしたチャートデータファイルを書き込む。
	 */
	public void writeChartMap();

	/**
	 * マージしたチャートデータを取得する。
	 * 
	 * @return マージしたチャートデータのマップ。
	 */
	public Map<String, MergeChartInfo_r10> getChartMap();

}
