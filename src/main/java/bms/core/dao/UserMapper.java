package bms.core.dao;

import java.util.List;

import bms.core.model.User;

public interface UserMapper {
	int deleteByPrimaryKey(Integer ID);

	int deleteByIDs(String[] userIDs);

	int insert(User record);

	int insertSelective(User record);

	List<User> selectAll();

	List<User> selectMyUsers(Integer ID);

	List<String> selectMenuAuths(Integer ID);

	List<String> selectAllAuths(Integer ID);

	User selectByPrimaryKey(Integer ID);

	User selectByPassword(User record);

	User selectByUserName(User record);

	int updateLogin(User user);

	int updateByPrimaryKeySelective(User record);

	int updateByPrimaryKey(User record);
}