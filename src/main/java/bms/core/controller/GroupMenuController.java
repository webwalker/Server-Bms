package bms.core.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import bms.core.common.Consts;
import bms.core.common.Consts.Keys;
import bms.core.common.util.JacksonJsonUtil;
import bms.core.model.Group;
import bms.core.model.MyResponse;
import bms.core.model.json.SimpleAuth;
import bms.core.model.json.SimpleGroupAuth;
import bms.core.model.json.SimpleGroupMenu;
import bms.core.model.json.Tree;
import bms.core.service.GroupMenuService;
import bms.core.service.MenuAuthService;
import bms.core.service.UserGroupService;

/**
 * 分组菜单与授权管理
 * 
 * @author xu.jian
 * 
 */
@Controller
@RequestMapping(Keys.PATH_PREFIX + "/groupmenu")
public class GroupMenuController extends BaseController {
	@Autowired
	GroupMenuService service;
	@Autowired
	MenuAuthService menuService;
	@Autowired
	UserGroupService ugService;

	@RequestMapping(value = { "/{id}", "/view/{id}" }, method = RequestMethod.GET)
	public String usergroup(@PathVariable int id, Model model) {
		Group group = ugService.getGroup(id);
		model.addAttribute("group", group);

		try {
			List<SimpleAuth> auths = menuService.getAllSimpleAuths();
			List<SimpleGroupMenu> cbmenus = service.getGroupMenus(id);
			List<SimpleGroupAuth> cbauths = service.getGroupAuths(id);

			model.addAttribute("auth", JacksonJsonUtil.beanToJson(auths));
			model.addAttribute("checkedmenu",
					JacksonJsonUtil.beanToJson(cbmenus));
			model.addAttribute("checkedauth",
					JacksonJsonUtil.beanToJson(cbauths));
			model.addAttribute("authType", context.isMenuAuthType());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Consts.Path.groupmenu;
	}

	@RequestMapping("list")
	@ResponseBody
	public List<Tree> showList(Model model, HttpServletRequest request) {
		return service.getAuthMenus(auths.getUser());
	}

	@RequestMapping(value = "saveauth", method = RequestMethod.POST)
	@ResponseBody
	public MyResponse auths(
			@RequestParam(value = "groupid", required = true) int groupid,
			@RequestParam(value = "menu[]", required = true) int[] menu,
			@RequestParam(value = "auth[]", required = true) int[] auth) {

		String user = auths.getUserName();
		return service.updateAuthMapping(groupid, user, menu, auth);
	}
}
