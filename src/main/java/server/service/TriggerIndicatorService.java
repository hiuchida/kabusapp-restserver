package server.service;

import java.lang.invoke.MethodHandles;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import server.repository.ChartDataRepository;
import v39.MainTriggerIndicator_r11;

@Service
public class TriggerIndicatorService {
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
	 * 保存した4本値チャートデータと、PUSH APIで受信したチャートデータをマージした4本値と売買高を出力する。
	 * 
	 * @return レスポンス文字列。
	 */
	public String execute() {
		List<String> codes = chartDataRepository.list();
		logger.info("execute(): " + codes);
		for (String code : codes) {
			new MainTriggerIndicator_r11(code).execute();
		}
		return "OK";
	}

}
