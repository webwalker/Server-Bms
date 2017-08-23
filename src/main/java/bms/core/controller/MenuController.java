package bms.core.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import bms.core.auth.Admin;
import bms.core.common.Consts;
import bms.core.common.Consts.Keys;
import bms.core.dao.MenuMapper;
import bms.core.model.Menu;
import bms.core.model.MyResponse;
import bms.core.model.json.PageJson;
import bms.core.model.json.Tree;
import bms.core.service.MenuService;
import bms.core.service.MenuTreeService;

/**
 * 菜单管理
 * 
 * @author xu.jian
 * 
 */
@Controller
@RequestMapping(Keys.PATH_PREFIX + "/menu")
@Admin
public class MenuController extends BaseController {

	@Autowired
	MenuService service;
	@Autowired
	MenuTreeService treeService;
	@Autowired
	SqlSession sqlSession;

	@RequestMapping(value = { "/", "home" }, method = RequestMethod.GET)
	public String menu(Model model) {
		Menu menu = new Menu();
		model.addAttribute("menu", menu);
		return Consts.Path.menu;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String menu(@RequestParam("pid") int pid, Model model) {
		Menu menu = new Menu();
		menu.setParentID(pid);
		model.addAttribute("menu", menu);
		return Consts.Path.menu;
	}

	@RequestMapping("list/{pid}")
	@ResponseBody
	public PageJson showList(HttpServletRequest request, @PathVariable int pid) {
		this.initPage(request);
		MenuMapper mapper = sqlSession.getMapper(MenuMapper.class);
		List<Menu> list = mapper.selectByParentID(pid);
		return getJson(list);
	}

	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public MyResponse add(@Valid Menu menu, BindingResult result) {
		if (result.hasErrors())
			return msg.getFieldError(result.getFieldError());
		menu.setCreateUser(auths.getUserName());
		return service.add(menu, result);
	}

	@RequestMapping("update")
	@ResponseBody
	public MyResponse update(@Valid Menu menu, BindingResult result) {
		if (result.hasErrors())
			return msg.getFieldError(result.getFieldError());

		menu.setCreateUser(auths.getUserName());
		menu.setUpdateTime(new Date());
		return service.update(menu, result);
	}

	@RequestMapping("delete/{id}")
	@ResponseBody
	public MyResponse delete(@PathVariable String id) {
		return service.delete(id);
	}

	@RequestMapping("sortree")
	@ResponseBody
	public List<Tree> getAllMenus() {
		return treeService.getAllMenus();
	}

	@RequestMapping("sort")
	@ResponseBody
	public MyResponse sort(HttpServletRequest request) {
		return service.sort(request);
	}
}
