package server.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import server.service.CalcIndicatorService;

@CrossOrigin
@RestController
public class CalcIndicatorController {

	@Autowired
	private CalcIndicatorService calcIndicatorService;

	@GetMapping("/calc")
	@ResponseBody
	public String calc() {
		String resp = calcIndicatorService.execute();
		return resp;
	}

}
