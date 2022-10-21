package server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import server.repository.ChartDataRepository;
import server.repository.ChartDbRepository;
import server.repository.IndicatorDataRepository;
import server.repository.MergeDataRepository;

@Service
public class RepositoryService {

	@Autowired
	private ChartDataRepository chartDataRepository;

	@Autowired
	private ChartDbRepository chartDbRepository;

	@Autowired
	private MergeDataRepository mergeDataRepository;

	@Autowired
	private IndicatorDataRepository indicatorDataRepository;

	public String summary() {
		StringBuilder sb = new StringBuilder();
		sb.append("ChartDataRepository: ").append(chartDataRepository.toSummaryString()).append("\r\n");
		sb.append("ChartDbRepository: ").append(chartDbRepository.toSummaryString()).append("\r\n");
		sb.append("MergeDataRepository: ").append(mergeDataRepository.toSummaryString()).append("\r\n");
		sb.append("IndicatorDataRepository: ").append(indicatorDataRepository.toSummaryString()).append("\r\n");
		return sb.toString();
	}

	public String list() {
		StringBuilder sb = new StringBuilder();
		sb.append("ChartDataRepository").append("\r\n");
		sb.append(chartDataRepository.toString()).append("\r\n");
		sb.append("ChartDbRepository").append("\r\n");
		sb.append(chartDbRepository.toString()).append("\r\n");
		sb.append("MergeDataRepository").append("\r\n");
		sb.append(mergeDataRepository.toString()).append("\r\n");
		sb.append("IndicatorDataRepository").append("\r\n");
		sb.append(indicatorDataRepository.toString()).append("\r\n");
		return sb.toString();
	}

	public void load() {
		chartDataRepository.load();
		chartDbRepository.load();
	}

}
