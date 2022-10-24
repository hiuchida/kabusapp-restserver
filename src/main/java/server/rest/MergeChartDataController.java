package server.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import server.service.MergeChartDataService;
import server.service.MergeChartDataService_r10;

@CrossOrigin
@RestController
public class MergeChartDataController {

	@Autowired
	private MergeChartDataService mergeChartDataService;

	@Autowired
	private MergeChartDataService_r10 mergeChartDataService_r10;

	@GetMapping("/merge")
	@ResponseBody
	public String merge() {
		String resp = mergeChartDataService.execute();
		return resp;
	}

	@GetMapping("/merge_r10")
	@ResponseBody
	public String merge_r10() {
		String resp = mergeChartDataService_r10.execute();
		return resp;
	}

}
