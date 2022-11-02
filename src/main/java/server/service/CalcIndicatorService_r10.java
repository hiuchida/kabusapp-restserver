package server.service;

import java.lang.invoke.MethodHandles;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import server.repository.IndicatorDataRepository;
import server.repository.MergeDataRepository;
import v38.MainCalcIndicator_r10;

@Service
public class CalcIndicatorService_r10 {
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
			new MainCalcIndicator_r10(mergeDataRepository, indicatorDataRepository, code).execute();
		}
		return "OK";
	}

}
