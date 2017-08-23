package bms.core.dao;

import java.util.List;
import java.util.Map;

import bms.core.model.Group;
import bms.core.model.UserGroup;

public interface UserGroupMapper {
	int deleteByPrimaryKey(Integer ID);

	int insert(UserGroup record);

	int insertSelective(UserGroup record);

	List<Group> selectUserGroup(Integer ID);

	List<Group> selectAuthGroup(Integer ID);

	List<UserGroup> selectGroupUsersByType(String groupType);

	int batchInsert(List<UserGroup> ug);

	int batchDelete(Map<String, Object> map);

	int batchDeleteUserGroups(Map<String, Object> map);

	List<UserGroup> selectAllGroupUsers(UserGroup record);

	List<UserGroup> selectGroupUsers(UserGroup record);

	List<UserGroup> selectAllUserGroups(UserGroup record);

	List<UserGroup> selectUserGroups(UserGroup record);

	UserGroup selectByPrimaryKey(Integer ID);

	int updateByPrimaryKeySelective(UserGroup record);

	int updateByPrimaryKey(UserGroup record);

	List<UserGroup> selectUsersByGroupIDs(String[] groupIDs);

	List<UserGroup> selectAutoMailGroupUsers();
}