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
import server.service.WatchChartDataService;

@CrossOrigin
@RequestMapping("/chartData")
@RestController
public class ChartDataController {
	/**
	 * 期近先物ディレクトリ名。
	 */
	public static final String NEAR_FUTURE_DIR_NAME = "167110019_F202211";

	@Autowired
	private ChartDataService chartDataService;

	@Autowired
	private WatchChartDataService watchChartDataService;

	@PostMapping("/r2")
	@ResponseBody
	public String postChartData_r2(@RequestBody ChartData cd) {
		String resp = chartDataService.update(cd);
		if (!resp.startsWith("OK")) {
			return resp;
		}
		if (NEAR_FUTURE_DIR_NAME.equals(cd.code)) {
			resp = watchChartDataService.execute(cd.code);
		}
		return resp;
	}

}
