package bms.core.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import bms.core.service.UploadService;

/**
 * 文件上传处理
 * 
 * @author xu.jian
 * 
 */
@Controller
@RequestMapping("/upload")
public class UploadController extends BaseController {
	@Autowired
	UploadService service;

	@RequestMapping(method = RequestMethod.GET)
	public String page() {
		return "/upload";
	}

	// mvc处理器上传
	@RequestMapping("/do")
	public String upload2(HttpServletRequest request,
			HttpServletResponse response) throws IllegalStateException,
			IOException {
		boolean isValid = service.uploadByMVC(request);
		if (isValid)
			return "/success";
		return "";
	}

	// MVC 字节流方式上传文件, 速度很慢
	@RequestMapping("/test")
	public String addUser(@RequestParam("file") CommonsMultipartFile[] files,
			HttpServletRequest request) {
		boolean isValid = service.uploadByBytes(files);
		if (isValid)
			return "/success";
		return "";
	}
}
