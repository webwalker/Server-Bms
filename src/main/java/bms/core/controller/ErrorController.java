package bms.core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import bms.core.common.Consts;
import bms.core.common.Consts.Keys;

/**
 * @author xu.jian
 * 
 */
@Controller
@RequestMapping(Keys.PATH_PREFIX + "/error")
public class ErrorController extends BaseController {

	@RequestMapping(method = RequestMethod.GET)
	public String baisc(Model model) {
		return Consts.Path.error;
	}

	@RequestMapping(value = "validator", method = RequestMethod.GET)
	public String validate(Model model) {
		return Consts.Path.error;
	}

}
