package server.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import server.service.RepositoryService;

@Controller
public class RepositoryController {

	@Autowired
	private RepositoryService repositoryService;

	@RequestMapping("/repo")
	@ResponseBody
	public String repo() {
		return this.repositoryService.summary();
	}

	@RequestMapping("/repo/list")
	@ResponseBody
	public String list() {
		return this.repositoryService.list();
	}

	@RequestMapping("/repo/load")
	@ResponseBody
	public void load() {
		this.repositoryService.load();
	}

}
