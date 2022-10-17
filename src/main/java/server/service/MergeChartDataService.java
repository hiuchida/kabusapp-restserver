package server.service;

import java.lang.invoke.MethodHandles;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import server.repository.ChartDataRepository;
import server.repository.ChartDbRepository;
import v38.MainMergeChartData_r10;

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
			new MainMergeChartData_r10(chartDataRepository, chartDbRepository, code).execute();
		}
		return "OK";
	}

}
