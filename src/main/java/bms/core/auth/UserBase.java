package bms.core.auth;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;

import bms.core.common.BMSContext;
import bms.core.common.Consts.Keys;
import bms.core.model.User;
import bms.core.model.UserType;

/**
 * @author xu.jian
 * 
 */
public class UserBase {

	@Autowired
	BMSContext context;

	public void logout() {
		// context.getSession().setAttribute(Keys.SESSION_USER, null);
		context.getSession().invalidate();
	}

	public void setLoginUser(HttpServletRequest request, Object user) {
		request.getSession().setAttribute(Keys.SESSION_USER, user);
	}

	/**
	 * 用户信息为空时，拦截器会自动转向登录界面
	 * 
	 * @return
	 */
	public User getUser() {
		Object user = context.getSessionValue(Keys.SESSION_USER);
		// if (user == null) {
		// try {
		// response.sendRedirect("/" + Consts.Path.login);
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// return null;
		// }
		return (User) user;
	}

	public String getUserName() {
		User user = getUser();
		if (user != null)
			return user.getUserName();
		return "";
	}

	public UserType getUserType() {
		User user = getUser();
		if (user != null)
			return user.getUserTypeEnum();
		return UserType.Member;
	}

	// 判断是否为Admin注解校验
	public boolean isAdmin(HttpServletRequest req, HttpServletResponse resp,
			Object handler) throws IOException {
		if (!handler.getClass().isAssignableFrom(HandlerMethod.class))
			return true;

		// 方法注解校验
		Admin method = ((HandlerMethod) handler)
				.getMethodAnnotation(Admin.class);
		// 类注解校验
		Admin cls = ((HandlerMethod) handler).getBeanType().getAnnotation(
				Admin.class);
		if (cls == null && (method == null || method.check() == false))
			return true;
		if (getUser() != null && !getUser().isAdmin())
			return false;
		return true;
	}
}
