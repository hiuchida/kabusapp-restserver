package server.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import server.service.TriggerIndicatorService;

@CrossOrigin
@RestController
public class TriggerIndicatorController {

	@Autowired
	private TriggerIndicatorService triggerIndicatorService;

	@GetMapping("/trigger")
	@ResponseBody
	public String merge() {
		String resp = triggerIndicatorService.execute();
		return resp;
	}

}
