package bms.core.controller;

import java.util.List;

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
import org.springframework.web.bind.support.SessionStatus;

import bms.core.common.Consts;
import bms.core.common.Consts.Keys;
import bms.core.model.MyResponse;
import bms.core.model.User;
import bms.core.model.UserType;
import bms.core.model.json.PageJson;
import bms.core.model.json.Tree;
import bms.core.service.MenuService;
import bms.core.service.UserService;

/**
 * 用户管理
 * 
 * @author XuJian
 * 
 */
@Controller
@RequestMapping(Keys.PATH_PREFIX + "/user")
public class UserController extends BaseController {

	@Autowired
	private UserService service;
	@Autowired
	MenuService menuService;

	@RequestMapping(value = { "", "view" }, method = RequestMethod.GET)
	public String user(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("type", UserType.getList(auths.getUserType()));
		return Consts.Path.user;
	}

	@RequestMapping("list")
	@ResponseBody
	public PageJson showList(HttpServletRequest request) {
		// 除了管理员、代理商，其他的账户没有权限管理
		this.initPage(request);
		List<User> users = service.getUsers(auths.getUser());
		return getJson(users);
	}

	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public MyResponse add(@Valid User user, BindingResult result,
			SessionStatus status) {
		if (result.hasErrors())
			return msg.getFieldError(result.getFieldError());
		user.setCreateUser(auths.getUserName());
		user.setCreateUserID(auths.getUser().getID());
		return service.add(user, result);
	}

	@RequestMapping("update")
	@ResponseBody
	public MyResponse update(@Valid User user, BindingResult result) {
		// TO-DO
		if (result.hasErrors())
			return msg.getFieldError(result.getFieldError());

		user.setCreateUser(auths.getUserName());
		user.setCreateUserID(auths.getUser().getID());
		return service.update(user, result);
	}

	@RequestMapping("delete/{id}")
	@ResponseBody
	public MyResponse delete(@PathVariable String id) {
		return service.delete(id);
	}

	/**
	 * 显示用户有权限的菜单
	 * 
	 * @return
	 */
	@RequestMapping("tree")
	@ResponseBody
	public List<Tree> getMenu() {
		return menuService.getAuthMenus(auths.getUser());
	}
}
