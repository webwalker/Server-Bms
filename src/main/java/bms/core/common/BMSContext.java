package bms.core.common;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import bms.core.common.Consts.Keys;
import bms.core.common.util.StringUtil;

/**
 * @author xu.jian
 * 
 */
@Component
public class BMSContext {

	public static List<String> systemUrl = null;

	// 权限是否只对应到菜单层级
	private boolean menuAuthType;

	private final static Pattern pattern = Pattern.compile("(?://|\\\\)");

	public boolean isMenuAuthType() {
		return menuAuthType;
	}

	public void setMenuAuthType(boolean menuAuthType) {
		this.menuAuthType = menuAuthType;
	}

	public HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
	}

	public String getPath() {
		String path = getRequest().getServletPath();
		Matcher matcher = pattern.matcher(path);
		path = matcher.replaceAll("/");
		System.out.println("getPath:" + path);
		return path;
	}

	public void setSession(String key, Object data) {
		HttpSession session = getRequest().getSession();
		session.setAttribute(Keys.SESSION_AUTHS, data);
	}

	public Object getSessionValue(String key) {
		return getRequest().getSession().getAttribute(key) != null ? getRequest()
				.getSession().getAttribute(key) : null;
	}

	public HttpSession getSession() {
		return getRequest().getSession();
	}

	public String getUserIp() {
		return StringUtil.getIpAddr(getRequest());
	}
}
