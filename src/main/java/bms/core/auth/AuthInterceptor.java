package bms.core.auth;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import bms.core.common.Consts;
import bms.core.common.Consts.Messages;
import bms.core.common.util.MessageUtil;
import bms.core.model.User;

/**
 * Session过期、权限验证、日志记录、请求处理时间
 * 
 * @author XuJian
 * 
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {

	private HttpServletRequest req;
	private HttpServletResponse resp;
	private String msg = "";
	private List<String> unCheckUrls;

	@Autowired
	UserAuths user;
	@Autowired
	MessageUtil util;

	// 请求处理之前
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) {
		try {
			req = request;
			resp = response;
			if (!isNeedCheck())
				return true;
			// check user session and auth
			if (checkSession() == false) {
				System.out.println("session timeout");
				resp.sendRedirect(req.getContextPath() + "/"
						+ Consts.Path.login);
				return false;
			}
			// check admin
			if (user.isAdmin(request, response, handler) == false) {
				System.out.println("op is only for admin!");
				util.getMessage(Messages.AUTH_NO_AUTH);
				resp.sendRedirect(Consts.Path.R_Error);
				return false;
			}
			// check auth
			if (user.hasAuth(req) == false) {
				System.out.println("not permitted!");
				resp.sendRedirect(req.getContextPath() + "/"
						+ Consts.Path.permission);
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	// 请求处理之后
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// 可以修改model、header……
		if (modelAndView != null)
			modelAndView.addObject("msg", msg);
	}

	// 在action返回视图后执行
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		super.afterCompletion(request, response, handler, ex);
	}

	private boolean checkSession() throws IOException {
		User u = user.getUser();
		if (u == null) {
			return false; // 中断执行
		}
		return true;
	}

	public boolean isNeedCheck() {
		String path = user.getPath();
		if (path != null) {
			for (String s : unCheckUrls) {
				if (path.indexOf(s) > -1) {
					return false;
				}
			}
		}
		return true;
	}

	public List<String> getUnCheckUrls() {
		return unCheckUrls;
	}

	public void setUnCheckUrls(List<String> unCheckUrls) {
		this.unCheckUrls = unCheckUrls;
	}
}
