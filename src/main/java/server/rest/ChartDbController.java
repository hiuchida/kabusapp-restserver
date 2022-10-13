package server.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import server.model.ChartDb;
import server.service.ChartDbService;

@CrossOrigin
@RequestMapping("/chartDb")
@RestController
public class ChartDbController {

	@Autowired
	private ChartDbService chartDbService;

	@PostMapping("/r1")
	@ResponseBody
	public String postChartDb_r1(@RequestBody ChartDb cd) {
		String resp = chartDbService.register(cd);
		return resp;
	}

}
