package v38.i;

import java.util.List;

import v38.bean.CalcIndicatorInfo_r10;
import v38.bean.MergeChartInfo_r10;
import v38.factory.IndicatorCode;

/**
 * テクニカル指標を計算するインターフェイス。
 */
public interface CalcIndicator_r10 {
	/**
	 * テクニカル指標の種別コードを取得する。
	 * 
	 * @return テクニカル指標の種別コード。
	 */
	public IndicatorCode getCode();

	/**
	 * テクニカル指標を計算する。
	 * 
	 * @param chartList マージしたチャートデータを時系列に並べたリスト。
	 */
	public void execute(List<MergeChartInfo_r10> chartList);

	/**
	 * テクニカル指標のリストを取得する。
	 * 
	 * @return テクニカル指標のリスト。
	 */
	public List<CalcIndicatorInfo_r10> getIndicatorList();

}
