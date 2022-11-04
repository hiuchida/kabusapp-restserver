package server.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import server.service.CalcIndicatorService;
import server.service.CalcIndicatorService_r10;

@CrossOrigin
@RestController
public class CalcIndicatorController {

	@Autowired
	private CalcIndicatorService calcIndicatorService;

	@Autowired
	private CalcIndicatorService_r10 calcIndicatorService_r10;

	@GetMapping("/calc")
	@ResponseBody
	public String calc() {
		String resp = calcIndicatorService.execute();
		return resp;
	}

	@GetMapping("/calc/{code}")
	@ResponseBody
	public String calc(@PathVariable String code) {
		String resp = calcIndicatorService.execute(code);
		return resp;
	}

	@GetMapping("/calc_r10")
	@ResponseBody
	public String calc_r10() {
		String resp = calcIndicatorService_r10.execute();
		return resp;
	}

}
