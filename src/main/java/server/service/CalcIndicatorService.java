package server.service;

import java.lang.invoke.MethodHandles;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import server.repository.IndicatorDataRepository;
import server.repository.MergeDataRepository;
import v45.MainCalcIndicator_r17;

@Service
public class CalcIndicatorService {
	/**
	 * クラス。
	 */
	private static Class<?> clazz = MethodHandles.lookup().lookupClass();
	/**
	 * ロガー。
	 */
	private static Log logger = LogFactory.getLog(clazz);

	@Autowired
	private MergeDataRepository mergeDataRepository;
	
	@Autowired
	private IndicatorDataRepository indicatorDataRepository;

	/**
	 * テクニカル指標を計算する。
	 * 
	 * @return レスポンス文字列。
	 */
	public String execute() {
		List<String> codes = mergeDataRepository.list();
		logger.info("execute(): " + codes);
		for (String code : codes) {
			new MainCalcIndicator_r17(mergeDataRepository, indicatorDataRepository, code).execute();
		}
		return "OK";
	}

	/**
	 * テクニカル指標を計算する。
	 * 
	 * @param code 銘柄コード。
	 * @return レスポンス文字列。
	 */
	public String execute(String code) {
		mergeDataRepository.list();
		logger.info("execute(" + code + "):");
		new MainCalcIndicator_r17(mergeDataRepository, indicatorDataRepository, code).execute();
		return "OK";
	}

}
