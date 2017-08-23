package bms.core.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bms.core.dao.GroupMapper;
import bms.core.dao.UserGroupMapper;
import bms.core.model.Group;
import bms.core.model.MyResponse;
import bms.core.model.User;
import bms.core.model.UserGroup;
import bms.core.model.UserType;

/**
 * @author xu.jian
 * 
 */
@Service
public class UserGroupService extends BaseService {
	@Autowired
	GroupMapper groupMapper;
	@Autowired
	UserGroupMapper userGroupMapper;

	public Group getGroup(int id) {
		return groupMapper.selectByPrimaryKey(id);
	}

	public List<UserGroup> selectGroupUsersByType(String groupType) {
		return userGroupMapper.selectGroupUsersByType(groupType);
	}

	// 为分组添加用户
	public MyResponse addUsers(String createUser, int groupID, String userIDs,
			String all) {
		try {
			deleteUsers(groupID, all);

			List<UserGroup> list = new ArrayList<UserGroup>();
			String[] ids = userIDs.split(",");
			for (String id : ids) {
				UserGroup ug = new UserGroup();
				ug.setCreateUser(createUser);
				ug.setGroupID(groupID);
				ug.setUserID(Integer.valueOf(id));
				list.add(ug);
			}
			userGroupMapper.batchInsert(list);
		} catch (Exception e) {
			e.printStackTrace();
			return msg.getFailed();
		}
		return msg.getSuccess();
	}

	// 删除分组中的用户列表
	public MyResponse deleteUsers(int groupID, String userIDs) {
		if (groupID <= 0 || userIDs.isEmpty())
			return msg.getFailed();

		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("groupID", groupID);
		maps.put("userID", userIDs.split(","));

		int retCode = userGroupMapper.batchDelete(maps);
		if (retCode > 0)
			return msg.getSuccess();
		return msg.getFailed();
	}

	// 获取用户有权限用户列表
	public List<UserGroup> getGroupUsers(User user, int groupID) {
		UserType userType = user.getUserTypeEnum();
		int userID = user.getID();
		UserGroupMapper groupMapper = sqlSession
				.getMapper(UserGroupMapper.class);
		List<UserGroup> groups = null;
		UserGroup ug = new UserGroup();
		ug.setGroupID(groupID);
		ug.setUserID(userID);

		if (userType == UserType.Admin) {
			groups = groupMapper.selectAllGroupUsers(ug);
		} else {
			groups = groupMapper.selectGroupUsers(ug);
		}
		return groups;
	}

	// 为用户绑定多个分组信息
	public MyResponse addGroups(String createUser, int userID, String groupIDs,
			String all) {
		try {
			deleteGroups(userID, all);

			List<UserGroup> list = new ArrayList<UserGroup>();
			String[] ids = groupIDs.split(",");
			for (String id : ids) {
				UserGroup ug = new UserGroup();
				ug.setCreateUser(createUser);
				ug.setGroupID(Integer.valueOf(id));
				ug.setUserID(userID);
				list.add(ug);
			}
			userGroupMapper.batchInsert(list);
		} catch (Exception e) {
			e.printStackTrace();
			return msg.getFailed();
		}
		return msg.getSuccess();
	}

	// 删除用户绑定的分组信息
	public MyResponse deleteGroups(int userID, String groupIDs) {
		if (userID <= 0 || groupIDs.isEmpty())
			return msg.getFailed();

		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("userID", userID);
		maps.put("groupID", groupIDs.split(","));

		int retCode = userGroupMapper.batchDeleteUserGroups(maps);
		if (retCode > 0)
			return msg.getSuccess();
		return msg.getFailed();
	}

	// 获取用户有权限的分组列表，附带当前被操作用户的已分配分组状态
	public List<UserGroup> getUserGroups(User user, int userID) {
		UserType userType = user.getUserTypeEnum();
		UserGroupMapper groupMapper = sqlSession
				.getMapper(UserGroupMapper.class);
		List<UserGroup> groups = null;
		UserGroup ug = new UserGroup();
		ug.setUserID(user.getID());
		ug.setTargetUserID(userID);

		if (userType == UserType.Admin) {
			groups = groupMapper.selectAllUserGroups(ug);
		} else {
			groups = groupMapper.selectUserGroups(ug);
		}
		return groups;
	}

	public List<UserGroup> selectUsersByGroupIDs(String[] groupIDs) {
		return userGroupMapper.selectUsersByGroupIDs(groupIDs);
	}
}
