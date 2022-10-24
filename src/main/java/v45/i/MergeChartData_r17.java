package v45.i;

import java.util.List;
import java.util.Map;

import server.repository.ChartDataRepository;
import server.repository.ChartDbRepository;
import server.repository.MergeDataRepository;
import v38.factory.BarCode;
import v45.bean.MergeChartInfo_r17;

/**
 * 保存した4本値チャートデータと、PUSH APIで受信したチャートデータをマージした4本値と売買高を出力するインターフェイス。
 */
public interface MergeChartData_r17 {
	/**
	 * 足名の種別コードを取得する。
	 * 
	 * @return 足名の種別コード。
	 */
	public BarCode getCode();

	/**
	 * 保存した4本値チャートデータと、PUSH APIで受信したチャートデータをマージする。
	 * 
	 * @param chartDataRepository
	 * @param chartDbRepository
	 * @param mergeDataRepository
	 * @param lines               チャートデータのリスト。
	 * @return チャートデータのリスト。
	 */
	public List<String> execute(ChartDataRepository chartDataRepository, ChartDbRepository chartDbRepository, MergeDataRepository mergeDataRepository, List<String> lines);

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
	 * 
	 * @param mergeDataRepository
	 */
	public void writeChartMap(MergeDataRepository mergeDataRepository);

	/**
	 * マージしたチャートデータを取得する。
	 * 
	 * @return マージしたチャートデータのマップ。
	 */
	public Map<String, MergeChartInfo_r17> getChartMap();

}
