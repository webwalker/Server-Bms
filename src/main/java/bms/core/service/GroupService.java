package bms.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import bms.core.dao.GroupMapper;
import bms.core.dao.UserGroupMapper;
import bms.core.model.Group;
import bms.core.model.MyResponse;
import bms.core.model.User;
import bms.core.model.UserType;

/**
 * @author xu.jian
 * 
 */
@Service
public class GroupService extends BaseService {
	@Autowired
	GroupMapper groupMapper;
	@Autowired
	UserGroupMapper userGroupMapper;

	public MyResponse add(Group group, BindingResult result) {
		try {
			groupMapper.insert(group);
		} catch (Exception e) {
			e.printStackTrace();
			return msg.getFailed();
		}
		return msg.getSuccess();
	}

	public MyResponse update(Group group, BindingResult result) {
		groupMapper.updateByPrimaryKeySelective(group);
		return msg.getSuccess();
	}

	public MyResponse delete(String id) {
		if (id.isEmpty())
			return msg.getFailed();

		int retCode = groupMapper.deleteByIDs(id.split(","));
		if (retCode > 0)
			return msg.getSuccess();
		return msg.getFailed();
	}

	public List<Group> getGroups(User user) {
		UserType userType = user.getUserTypeEnum();
		int userId = user.getID();
		GroupMapper groupMapper = sqlSession.getMapper(GroupMapper.class);
		List<Group> groups = null;
		if (userType == UserType.Admin) {
			groups = groupMapper.selectAll();
		} else {
			groups = userGroupMapper.selectUserGroup(userId);
		}
		return groups;
	}

	public List<Group> getAuthGroups(int userId) {
		return userGroupMapper.selectAuthGroup(userId);
	}

	public List<Group> selectByType(String type) {
		return groupMapper.selectByType(type);
	}

}
