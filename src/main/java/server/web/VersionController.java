package server.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import util.Consts;

@Controller
public class VersionController {

	@RequestMapping("/")
	@ResponseBody
	public String helloWorld() {
		return Consts.VERSION;
	}

}
