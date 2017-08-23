package bms.core.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import bms.core.auth.Admin;
import bms.core.common.Consts;
import bms.core.common.Consts.Keys;
import bms.core.dao.AuthMapper;
import bms.core.dao.UrlSetMapper;
import bms.core.model.Auth;
import bms.core.model.Menu;
import bms.core.model.MenuAuth;
import bms.core.model.MyResponse;
import bms.core.model.json.PageJson;
import bms.core.service.MenuService;
import bms.core.service.UrlSetService;

/**
 * 映射菜单级、元素级权限
 * 
 * @author xu.jian
 * 
 */
@Controller
@RequestMapping(Keys.PATH_PREFIX + "/urlset")
@SessionAttributes("auth")
@Admin
public class UrlMapController extends BaseController {
	@Autowired
	UrlSetService service;
	@Autowired
	MenuService menuService;
	@Autowired
	AuthMapper authService;

	// type:1 菜单地址映射,type:2 auth地址映射
	@RequestMapping(value = "/{type}/{id}", method = RequestMethod.GET)
	public String auth(@PathVariable int type, @PathVariable int id, Model model) {
		String name = "";
		if (type == 1) {
			Menu menu = menuService.getMenu(id);
			name = "菜单[" + menu.getMenuName() + "]";
		} else {
			Auth auth = authService.selectByPrimaryKey(id);
			name = "权限[" + auth.getAuthName() + "]";
		}

		MenuAuth auth = new MenuAuth();
		auth.setAuthName(name);
		auth.setUrlID(id);
		auth.setType(type);
		model.addAttribute("auth", auth);
		return Consts.Path.urlset;
	}

	@RequestMapping("list")
	@ResponseBody
	public PageJson showList(@ModelAttribute("auth") MenuAuth auth,
			HttpServletRequest request) {
		this.initPage(request);

		List<MenuAuth> list = null;
		UrlSetMapper mapper = sqlSession.getMapper(UrlSetMapper.class);
		if (auth.getType() == 1) {
			list = mapper.selectMenuUrl(auth.getUrlID());
		} else {
			list = mapper.selectAuthUrl(auth.getUrlID());
		}

		return getJson(list);
	}

	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public MyResponse add(@Valid @ModelAttribute("auth") MenuAuth auth,
			BindingResult result) {
		if (result.hasErrors())
			return msg.getFieldError(result.getFieldError());

		auth.setCreateUser(auths.getUserName());
		return service.add(auth, result);
	}

	@RequestMapping("update")
	@ResponseBody
	public MyResponse update(@Valid @ModelAttribute("auth") MenuAuth auth,
			BindingResult result) {
		if (result.hasErrors())
			return msg.getFieldError(result.getFieldError());

		auth.setCreateUser(auths.getUserName());
		auth.setUpdateTime(new Date());
		return service.update(auth, result);
	}

	@RequestMapping("delete/{id}")
	@ResponseBody
	public MyResponse delete(@PathVariable String id) {
		return service.delete(id);
	}
}
