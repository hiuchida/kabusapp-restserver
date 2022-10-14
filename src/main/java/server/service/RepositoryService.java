package server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import server.repository.ChartDataRepository;
import server.repository.ChartDbRepository;

@Service
public class RepositoryService {

	@Autowired
	private ChartDataRepository chartDataRepository;

	@Autowired
	private ChartDbRepository chartDbRepository;

	public String list() {
		StringBuilder sb = new StringBuilder();
		sb.append("ChartDataRepository").append("\r\n");
		sb.append(chartDataRepository.list()).append("\r\n");
		sb.append("ChartDbRepository").append("\r\n");
		sb.append(chartDbRepository.list()).append("\r\n");
		return sb.toString();
	}

	public void load() {
		chartDataRepository.load();
		chartDbRepository.load();
	}

}
