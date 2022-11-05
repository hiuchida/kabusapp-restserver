package server.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import server.service.WatchChartDataService;

@CrossOrigin
@RestController
public class WatchChartDataController {

	@Autowired
	private WatchChartDataService watchChartDataService;

	@GetMapping("/watch")
	@ResponseBody
	public String trigger() {
		String resp = watchChartDataService.execute();
		return resp;
	}

	@GetMapping("/watch/{code}")
	@ResponseBody
	public String trigger(@PathVariable String code) {
		String resp = watchChartDataService.execute(code);
		return resp;
	}

}
