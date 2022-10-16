package v38.i;

import java.util.List;
import java.util.Map;

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
	 */
	public void execute();

	/**
	 * 1分足の4本値チャートＤＢファイルから終値を読み込む。
	 */
	public void readDbChartData();

	/**
	 * PUSH APIで受信したチャートデータファイルから現値を読み込む。
	 * 
	 * @return チャートデータ。
	 */
	public List<String> readCsvChartData();

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
