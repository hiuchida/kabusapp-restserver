package server.service;

import java.lang.invoke.MethodHandles;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import server.repository.ChartDataRepository;
import v40.MainTriggerTickData_r12;

@Service
public class TriggerTickService_r10 {
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
			new MainTriggerTickData_r12(chartDataRepository, code).execute();
		}
		return "OK";
	}

}
