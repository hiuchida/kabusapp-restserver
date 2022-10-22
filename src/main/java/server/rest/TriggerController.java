package server.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import server.service.TriggerIndicatorService;
import server.service.TriggerTickService;

@CrossOrigin
@RestController
public class TriggerController {

	@Autowired
	private TriggerIndicatorService triggerIndicatorService;

	@Autowired
	private TriggerTickService triggerTickService;

	@GetMapping("/trigger")
	@ResponseBody
	public String trigger() {
		String resp1 = triggerIndicatorService.execute();
		String resp2 = triggerTickService.execute();
		// TriggerIndicatorServiceの実行結果に寄らず、常にTriggerTickServiceを実行する
		if (!resp1.equals("OK")) {
			return resp1;
		}
		return resp2;
	}

	@GetMapping("/trigger/indicator")
	@ResponseBody
	public String triggerIndicator() {
		String resp = triggerIndicatorService.execute();
		return resp;
	}

	@GetMapping("/trigger/tick")
	@ResponseBody
	public String triggerTick() {
		String resp = triggerTickService.execute();
		return resp;
	}

}
