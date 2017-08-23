package bms.core.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import bms.core.common.Consts;
import bms.core.common.Consts.Keys;
import bms.core.model.Group;
import bms.core.model.MyResponse;
import bms.core.model.json.PageJson;
import bms.core.service.GroupService;

/**
 * 分组管理
 * 
 * @author xu.jian
 * 
 */
@Controller
@RequestMapping(Keys.PATH_PREFIX + "/group")
public class GroupController extends BaseController {

	@Autowired
	GroupService service;

	@RequestMapping(value = { "", "view" }, method = RequestMethod.GET)
	public String group(Model model) {
		model.addAttribute("group", new Group());
		return Consts.Path.group;
	}

	@RequestMapping("list")
	@ResponseBody
	public PageJson showList(HttpServletRequest request) {
		this.initPage(request);
		return getJson(service.getGroups(auths.getUser()));
	}

	@RequestMapping("add")
	@ResponseBody
	public MyResponse add(@Valid Group group, BindingResult result) {
		if (result.hasErrors())
			return msg.getFieldError(result.getFieldError());

		group.setCreateUserID(auths.getUser().getID());
		group.setCreateUser(auths.getUserName());
		return service.add(group, result);
	}

	@RequestMapping("update")
	@ResponseBody
	public MyResponse update(@Valid Group group, BindingResult result) {
		if (result.hasErrors())
			return msg.getFieldError(result.getFieldError());

		group.setCreateUser(auths.getUserName());
		group.setCreateUserID(auths.getUser().getID());
		group.setUpdateTime(new Date());
		return service.update(group, result);
	}

	@RequestMapping("delete/{id}")
	@ResponseBody
	public MyResponse delete(@PathVariable String id) {
		return service.delete(id);
	}
}
