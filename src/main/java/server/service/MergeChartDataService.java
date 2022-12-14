package server.service;

import java.lang.invoke.MethodHandles;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import server.repository.ChartDataRepository;
import server.repository.ChartDbRepository;
import server.repository.MergeDataRepository;
import v45.MainMergeChartData_r17;

@Service
public class MergeChartDataService {
	/**
	 * クラス。
	 */
	private static Class<?> clazz = MethodHandles.lookup().lookupClass();
	/**
	 * ロガー。
	 */
	private static Log logger = LogFactory.getLog(clazz);

	@Autowired
	private ChartDataRepository chartDataRepository;

	@Autowired
	private ChartDbRepository chartDbRepository;

	@Autowired
	private MergeDataRepository mergeDataRepository;

	/**
	 * 保存した4本値チャートデータと、PUSH APIで受信したチャートデータをマージした4本値と売買高を出力する。
	 * 
	 * @return レスポンス文字列。
	 */
	public String execute() {
		chartDbRepository.list();
		List<String> codes = chartDataRepository.list();
		logger.info("execute(): " + codes);
		for (String code : codes) {
			new MainMergeChartData_r17(chartDataRepository, chartDbRepository, mergeDataRepository, code).execute();
		}
		return "OK";
	}

	/**
	 * 保存した4本値チャートデータと、PUSH APIで受信したチャートデータをマージした4本値と売買高を出力する。
	 * 
	 * @param code 銘柄コード。
	 * @return レスポンス文字列。
	 */
	public String execute(String code) {
		chartDbRepository.list();
		chartDataRepository.list();
		logger.info("execute(" + code + "):");
		new MainMergeChartData_r17(chartDataRepository, chartDbRepository, mergeDataRepository, code).execute();
		return "OK";
	}

}
