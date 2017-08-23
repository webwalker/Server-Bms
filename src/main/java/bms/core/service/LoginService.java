package bms.core.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import bms.core.auth.UserAuths;
import bms.core.common.Consts;
import bms.core.common.Consts.Messages;
import bms.core.common.Encrypter;
import bms.core.dao.UserMapper;
import bms.core.model.User;

/**
 * @author xu.jian
 * 
 */
@Service
public class LoginService extends BaseService {

	@Autowired
	UserMapper userMapper;
	@Autowired
	UserAuths userAuth;

	public String login(User user, BindingResult result,
			HttpServletRequest request, Model model) {

		String password = Encrypter.MD5.GetMD5Code(user.getUserPass());
		user.setUserPass(password.toUpperCase());
		User queryUser = userMapper.selectByPassword(user);
		if (queryUser == null)
			return msg.toError(model, Messages.User_Login);
		queryUser.setLogonIp(userAuth.getIp());

		// save to session
		userAuth.setLoginUser(request, queryUser);
		// get auths
		userAuth.initAuth();

		// 更新登录信息
		userMapper.updateLogin(queryUser);

		return Consts.Path.R_Home;
	}
}
