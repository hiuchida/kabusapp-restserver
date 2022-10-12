package server.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import server.model.ChartData;
import server.service.ChartDataService;

@CrossOrigin
@RequestMapping("/chartData")
@RestController
public class ChartDataController {

	@Autowired
	private ChartDataService chartDataService;

	@PostMapping("/r1")
	@ResponseBody
	public String postChartData_r1(@RequestBody ChartData cd) {
		String resp = chartDataService.register(cd);
		return resp;
	}

}