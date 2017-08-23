package bms.core.auth;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bms.core.common.BMSContext;
import bms.core.common.Consts;
import bms.core.common.Consts.Keys;
import bms.core.common.Consts.Values;
import bms.core.common.util.StringUtil;
import bms.core.dao.AuthMapper;
import bms.core.dao.MenuMapper;
import bms.core.dao.UserMapper;

/**
 * 负责校验用户权限信息
 * 
 * @author xu.jian
 * 
 */
@Component
public class UserAuths extends UserBase {

	@Autowired
	BMSContext context;
	@Autowired
	UserMapper userMapper;
	@Autowired
	MenuMapper menuMapper;
	@Autowired
	AuthMapper authMapper;

	// 权限元素集合,逗号分隔
	public String functionIds = "";

	// 针对某一个url判断是否有权限
	public boolean hasAuth(HttpServletRequest request) {
		// 不在系统配置的范围内，请求放行
		if (!isInsideSystem(request))
			return true;
		// 检查权限
		Object auths = context.getSessionValue(Keys.SESSION_AUTHS);
		if (auths == null)
			initAuth();
		List<String> list = (List<String>) auths;
		if (isStartWith(list)) {
			return true;
		}
		System.out.println("pemission is denied!");
		return false;
	}

	// 单独验证某一个元素是否有权限
	public boolean hasFuncAuth(String functionId) {
		functionId = Values.SPLITER + functionId + Values.SPLITER;
		String ids = (String) context.getSessionValue(Keys.SESSION_AUTH_IDS);
		if (ids == null) {
			List<String> list = authMapper.selectAuthIDs(getUser().getID());
			String result = StringUtil.listToString(Consts.Values.SPLITER, list);
			context.setSession(Keys.SESSION_AUTH_IDS, result);
		}
		ids = (String) context.getSessionValue(Keys.SESSION_AUTH_IDS);
		if (ids.indexOf(functionId) > -1)
			return true;
		return false;
	}

	public void initAuth() {
		System.out.println("initAuth...");
		List<String> list = null;
		int userId = getUser().getID();
		if (context.isMenuAuthType()) {
			list = userMapper.selectMenuAuths(userId);
		} else {
			list = userMapper.selectAllAuths(userId);
		}
		// format and write to session
		String result = StringUtil.listToString(Consts.Values.SPLITER, list);
		System.out.println("initAuths:" + result);
		context.setSession(Keys.SESSION_AUTHS, list);
	}

	public boolean isInsideSystem(HttpServletRequest request) {
		// init system url
		if (BMSContext.systemUrl == null) {
			List<String> list = menuMapper.selectAllMenuUrls();
			BMSContext.systemUrl = list;

			String result = StringUtil.listToString(Values.SPLITER, list);
			System.out.println("system url:" + result);
		}
		if (!isStartWith(BMSContext.systemUrl)) {
			System.out.println("is not system url");
			return false;
		}
		return true;
	}

	public String getIp() {
		return context.getUserIp();
	}

	// 非rest时，可通过无参的路径匹配到一个完整的URL
	public String getPath() {
		// return Values.SPLITER + context.getPath() + Values.SPLITER;
		return context.getPath();
	}

	// 处理rest请求匹配权限URL
	public boolean isStartWith(List<String> list) {
		String path = getPath();
		for (String s : list) {
			if (path.startsWith(s))
				return true;
		}
		return false;
	}
}
