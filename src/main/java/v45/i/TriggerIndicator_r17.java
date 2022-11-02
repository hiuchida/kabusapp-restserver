package v45.i;

import java.util.List;

import bean.MergeChartInfo_r10;
import server.repository.IndicatorDataRepository;
import v38.factory.IndicatorCode;

/**
 * テクニカル指標からイベントトリガーを発火するインターフェイス。
 */
public interface TriggerIndicator_r17 {
	/**
	 * テクニカル指標の種別コードを取得する。
	 * 
	 * @return テクニカル指標の種別コード。
	 */
	public IndicatorCode getCode();

	/**
	 * テクニカル指標からイベントトリガーを発火する。
	 * 
	 * @param chartList               マージしたチャートデータを時系列に並べたリスト。
	 * @param indicatorDataRepository
	 */
	public void execute(List<MergeChartInfo_r10> chartList, IndicatorDataRepository indicatorDataRepository);

}
