package bms.core.controller;

import java.util.Date;
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

import bms.core.auth.Admin;
import bms.core.common.Consts;
import bms.core.common.Consts.Keys;
import bms.core.dao.AuthMapper;
import bms.core.model.Auth;
import bms.core.model.MyResponse;
import bms.core.model.json.PageJson;
import bms.core.service.AuthSetService;

/**
 * 系统权限元数据信息维护
 * 
 * @author xu.jian
 * 
 */
@Controller
@RequestMapping(Keys.PATH_PREFIX + "/authset")
@Admin
public class AuthSetController extends BaseController {
	@Autowired
	AuthSetService service;

	@RequestMapping(method = RequestMethod.GET)
	public String auth(Model model) {
		model.addAttribute("auth", new Auth());
		return Consts.Path.auth;
	}

	@RequestMapping("list")
	@ResponseBody
	public PageJson showList(HttpServletRequest request) {
		this.initPage(request);
		AuthMapper mapper = sqlSession.getMapper(AuthMapper.class);
		List<Auth> list = mapper.selectAll();
		return getJson(list);
	}

	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public MyResponse add(@Valid Auth auth, BindingResult result) {
		// TO-DO
		if (result.hasErrors())
			return msg.getFieldError(result.getFieldError());

		auth.setCreateUser(auths.getUserName());
		return service.add(auth, result);
	}

	@RequestMapping("update")
	@ResponseBody
	public MyResponse update(@Valid Auth auth, BindingResult result) {
		// TO-DO
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
