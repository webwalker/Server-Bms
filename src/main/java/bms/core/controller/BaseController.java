package bms.core.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import bms.core.auth.UserAuths;
import bms.core.common.BMSContext;
import bms.core.common.pager.Page;
import bms.core.common.pager.PageHelper;
import bms.core.common.util.MessageUtil;
import bms.core.model.json.PageJson;

/**
 * @author xu.jian
 * 
 */
public class BaseController {
	@Autowired
	protected MessageUtil msg;
	@Autowired
	protected SqlSession sqlSession;
	@Autowired
	protected UserAuths auths;
	@Autowired
	BMSContext context;

	protected String pageNo;
	protected String pageSize;

	protected void setPage(HttpServletRequest request) {
		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize");
		if (pageNo == null || pageNo.isEmpty())
			pageNo = "1";
		if (pageSize == null || pageSize.isEmpty())
			pageSize = "15";

		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}

	protected void initPage(HttpServletRequest request) {
		setPage(request);
		PageHelper
				.startPage(Integer.valueOf(pageNo), Integer.valueOf(pageSize));
	}

	protected PageJson getJson(List list) {
		Page page = (Page) list;
		PageJson json = new PageJson();
		json.setPageNumber(page.getPageNum());
		json.setTotal(page.getTotal());
		json.setRows(list);
		return json;
	}

	protected PageJson getJson(List list, int total, String pageNO) {
		PageJson json = new PageJson();
		json.setPageNumber(Integer.valueOf(pageNO));
		json.setTotal(total);
		json.setRows(list);
		return json;
	}
}
