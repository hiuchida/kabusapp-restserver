package server.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import server.prop.KabusapisrvProperties;

@Controller
public class VersionController {

	@Autowired
	private KabusapisrvProperties prop;

	@RequestMapping("/")
	@ResponseBody
	public String version() {
		return prop.getVersion();
	}

}
