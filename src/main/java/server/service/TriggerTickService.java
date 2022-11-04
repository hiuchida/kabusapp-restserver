package server.service;

import java.lang.invoke.MethodHandles;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import server.repository.ChartDataRepository;
import v45.MainTriggerTickData_r17;

@Service
public class TriggerTickService {
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

	/**
	 * ティックデータからイベントトリガーを発火する。
	 * 
	 * @return レスポンス文字列。
	 */
	public String execute() {
		List<String> codes = chartDataRepository.list();
		logger.info("execute(): " + codes);
		for (String code : codes) {
			new MainTriggerTickData_r17(chartDataRepository, code).execute();
		}
		return "OK";
	}

	/**
	 * ティックデータからイベントトリガーを発火する。
	 * 
	 * @param code 銘柄コード。
	 * @return レスポンス文字列。
	 */
	public String execute(String code) {
		chartDataRepository.list();
		logger.info("execute(" + code + "):");
		new MainTriggerTickData_r17(chartDataRepository, code).execute();
		return "OK";
	}

}
