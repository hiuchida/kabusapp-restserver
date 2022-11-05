package server.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import server.service.MergeChartDataService;

@CrossOrigin
@RestController
public class MergeChartDataController {

	@Autowired
	private MergeChartDataService mergeChartDataService;

	@GetMapping("/merge")
	@ResponseBody
	public String merge() {
		String resp = mergeChartDataService.execute();
		return resp;
	}

	@GetMapping("/merge/{code}")
	@ResponseBody
	public String mergeCode(@PathVariable String code) {
		String resp = mergeChartDataService.execute(code);
		return resp;
	}

}
