package v45.i;

import java.util.List;

import bean.CalcIndicatorInfo_r10;
import bean.MergeChartInfo_r10;
import server.repository.IndicatorDataRepository;
import v45.factory.IndicatorCode;

/**
 * テクニカル指標を計算するインターフェイス。
 */
public interface CalcIndicator_r17 {
	/**
	 * テクニカル指標の種別コードを取得する。
	 * 
	 * @return テクニカル指標の種別コード。
	 */
	public IndicatorCode getCode();

	/**
	 * テクニカル指標を計算する。
	 * 
	 * @param chartList               マージしたチャートデータを時系列に並べたリスト。
	 * @param indicatorDataRepository
	 */
	public void execute(List<MergeChartInfo_r10> chartList, IndicatorDataRepository indicatorDataRepository);

	/**
	 * テクニカル指標のリストを取得する。
	 * 
	 * @return テクニカル指標のリスト。
	 */
	public List<CalcIndicatorInfo_r10> getIndicatorList();

}
