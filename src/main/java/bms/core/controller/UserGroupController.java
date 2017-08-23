package bms.core.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import bms.core.common.Consts;
import bms.core.common.Consts.Keys;
import bms.core.model.Group;
import bms.core.model.MyResponse;
import bms.core.model.User;
import bms.core.model.UserGroup;
import bms.core.model.json.PageJson;
import bms.core.service.GroupService;
import bms.core.service.UserGroupService;
import bms.core.service.UserService;

/**
 * 用户分组管理
 * 
 * @author xu.jian
 * 
 */
@Controller
@RequestMapping(Keys.PATH_PREFIX + "/usergroup")
public class UserGroupController extends BaseController {

	@Autowired
	UserService service;
	@Autowired
	UserGroupService ugService;
	@Autowired
	GroupService gService;

	// 为分组添加用户
	@RequestMapping(value = { "/{id}", "/view/{id}" }, method = RequestMethod.GET)
	public String groupuser(@PathVariable int id, Model model) {
		Group group = ugService.getGroup(id);
		model.addAttribute("group", group);
		return Consts.Path.groupuser;
	}

	@RequestMapping("list/{id}")
	@ResponseBody
	public PageJson showList(@PathVariable int id, Model model,
			HttpServletRequest request) {
		this.initPage(request);

		List<UserGroup> groups = ugService.getGroupUsers(auths.getUser(), id);
		return getJson(groups);
	}

	@RequestMapping("add/{groupID}/{ids}/{all}")
	@ResponseBody
	public MyResponse add(@PathVariable int groupID, @PathVariable String ids,
			@PathVariable String all) {
		String createUser = auths.getUserName();
		return ugService.addUsers(createUser, groupID, ids, all);
	}

	@RequestMapping("delete/{groupID}/{ids}")
	@ResponseBody
	public MyResponse delete(@PathVariable int groupID, @PathVariable String ids) {
		return ugService.deleteUsers(groupID, ids);
	}

	// 为用户绑定分组
	@RequestMapping("groups/view/{id}")
	public String usergroup(@PathVariable int id, Model model) {
		User user = service.getUser(id);
		model.addAttribute("user", user);
		return Consts.Path.usergroup;
	}

	@RequestMapping("groups/list/{id}")
	@ResponseBody
	public PageJson showList(@PathVariable int id, HttpServletRequest request) {
		this.initPage(request);
		List<UserGroup> groups = ugService.getUserGroups(auths.getUser(), id);
		return getJson(groups);
	}

	@RequestMapping("groups/bind/{userID}/{ids}/{all}")
	@ResponseBody
	public MyResponse updateBinds(@PathVariable int userID,
			@PathVariable String ids, @PathVariable String all) {
		String createUser = auths.getUserName();
		return ugService.addGroups(createUser, userID, ids, all);
	}
}
