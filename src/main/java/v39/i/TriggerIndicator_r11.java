package v39.i;

import java.util.List;

import v38.bean.MergeChartInfo_r10;
import v38.factory.IndicatorCode;
import v38.i.CalcIndicator_r10;

/**
 * テクニカル指標からイベントトリガーを発火するインターフェイス。
 */
public interface TriggerIndicator_r11 {
	/**
	 * テクニカル指標の種別コードを取得する。
	 * 
	 * @return テクニカル指標の種別コード。
	 */
	public IndicatorCode getCode();

	/**
	 * テクニカル指標からイベントトリガーを発火する。
	 * 
	 * @param chartList マージしたチャートデータを時系列に並べたリスト。
	 * @param ci        テクニカル指標を計算するクラス。
	 */
	public void execute(List<MergeChartInfo_r10> chartList, CalcIndicator_r10 ci);

	/**
	 * テクニカル指標からイベントトリガーを発火する。
	 * 
	 * @param chartList マージしたチャートデータを時系列に並べたリスト。
	 */
	public void execute(List<MergeChartInfo_r10> chartList);

}
