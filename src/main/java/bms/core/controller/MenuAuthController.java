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

import bms.core.auth.Admin;
import bms.core.common.Consts;
import bms.core.common.Consts.Keys;
import bms.core.model.Menu;
import bms.core.model.MenuAuth;
import bms.core.model.MyResponse;
import bms.core.model.json.PageJson;
import bms.core.service.MenuAuthService;
import bms.core.service.MenuService;

/**
 * 菜单、授权元数据信息映射
 * 
 * @author xu.jian
 * 
 */
@Controller
@RequestMapping(Keys.PATH_PREFIX + "/menuauth")
@Admin
public class MenuAuthController extends BaseController {
	@Autowired
	MenuService service;
	@Autowired
	MenuAuthService maService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String usergroup(@PathVariable int id, Model model) {
		Menu menu = service.getMenu(id);
		model.addAttribute("menu", menu);
		return Consts.Path.menuauth;
	}

	@RequestMapping("list/{id}")
	@ResponseBody
	public PageJson showList(@PathVariable int id, Model model,
			HttpServletRequest request) {
		this.initPage(request);
		List<MenuAuth> auths = maService.getAuthElements(id);
		return getJson(auths);
	}

	@RequestMapping("add/{menuID}/{ids}/{all}")
	@ResponseBody
	public MyResponse add(@PathVariable int menuID, @PathVariable String ids,
			@PathVariable String all) {
		String createUser = auths.getUserName();
		return maService.add(createUser, menuID, ids, all);
	}

	@RequestMapping("delete/{menuID}/{ids}")
	@ResponseBody
	public MyResponse delete(@PathVariable int menuID, @PathVariable String ids) {
		return maService.delete(menuID, ids);
	}
}
