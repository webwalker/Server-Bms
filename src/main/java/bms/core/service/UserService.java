package bms.core.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import bms.core.common.Consts;
import bms.core.common.Encrypter;
import bms.core.common.util.StringUtil;
import bms.core.dao.UserMapper;
import bms.core.model.MyResponse;
import bms.core.model.User;
import bms.core.model.UserType;

/**
 * @author XuJian
 * 
 */
@Service
public class UserService extends BaseService {
	@Autowired
	UserMapper userMapper;

	public User getUser(int id) {
		return userMapper.selectByPrimaryKey(id);
	}

	public List<User> getUsers(User user) {
		UserType userType = user.getUserTypeEnum();
		int userId = user.getID();
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		List<User> users = null;
		if (userType == UserType.Admin) {
			users = userMapper.selectAll();
		} else {
			users = userMapper.selectMyUsers(userId);
		}
		return users;
	}

	public MyResponse add(User user, BindingResult result) {
		try {
			User retUser = userMapper.selectByUserName(user);
			if (retUser != null)
				return msg.getResponse(false, Consts.Messages.User_Add);

			if (StringUtil.isNotEmpty(user.getUserPass())) {
				String password = Encrypter.MD5.GetMD5Code(user.getUserPass());
				user.setUserPass(password.toUpperCase());
			}
			userMapper.insert(user);
		} catch (Exception e) {
			e.printStackTrace();
			return msg.getFailed();
		}
		return msg.getSuccess();
	}

	public MyResponse update(User user, BindingResult result) {
		if (StringUtil.isNotEmpty(user.getUserPass())) {
			String password = Encrypter.MD5.GetMD5Code(user.getUserPass());
			user.setUserPass(password.toUpperCase());
		}
		user.setUpdateTime(new Date());

		userMapper.updateByPrimaryKeySelective(user);
		return msg.getSuccess();
	}

	public MyResponse delete(String id) {
		if (id.isEmpty())
			return msg.getFailed();

		int retCode = userMapper.deleteByIDs(id.split(","));
		if (retCode > 0)
			return msg.getSuccess();
		return msg.getFailed();
	}
}
