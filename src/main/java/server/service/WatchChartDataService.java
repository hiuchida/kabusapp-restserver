package server.service;

import java.lang.invoke.MethodHandles;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WatchChartDataService {
	/**
	 * クラス。
	 */
	private static Class<?> clazz = MethodHandles.lookup().lookupClass();
	/**
	 * ロガー。
	 */
	private static Log logger = LogFactory.getLog(clazz);

	@Autowired
	private MergeChartDataService mergeChartDataService;

	@Autowired
	private CalcIndicatorService calcIndicatorService;

	@Autowired
	private TriggerIndicatorService triggerIndicatorService;

	@Autowired
	private TriggerTickService triggerTickService;

	/**
	 * リアルタイムにファイル監視する。
	 * 
	 * @return レスポンス文字列。
	 */
	public String execute() {
		logger.info("execute():");
		String resp1 = mergeChartDataService.execute();
		String resp2 = calcIndicatorService.execute();
		String resp3 = triggerIndicatorService.execute();
		String resp4 = triggerTickService.execute();
		// 前のサービスの実行結果に寄らず、常にサービスを実行する
		if (!resp1.equals("OK")) {
			return resp1;
		}
		if (!resp2.equals("OK")) {
			return resp2;
		}
		if (!resp3.equals("OK")) {
			return resp3;
		}
		return resp4;
	}

	/**
	 * リアルタイムにファイル監視する。
	 * 
	 * @param code 銘柄コード。
	 * @return レスポンス文字列。
	 */
	public String execute(String code) {
		logger.info("execute(" + code + "):");
		String resp1 = mergeChartDataService.execute(code);
		String resp2 = calcIndicatorService.execute(code);
		String resp3 = triggerIndicatorService.execute(code);
		String resp4 = triggerTickService.execute(code);
		// 前のサービスの実行結果に寄らず、常にサービスを実行する
		if (!resp1.equals("OK")) {
			return resp1;
		}
		if (!resp2.equals("OK")) {
			return resp2;
		}
		if (!resp3.equals("OK")) {
			return resp3;
		}
		return resp4;
	}

}
