package bms.core.dao;

import java.util.List;

import bms.core.model.Auth;

public interface AuthMapper {

	int deleteByIDs(String[] groupIDs);

	int deleteByPrimaryKey(Integer ID);

	int insert(Auth record);

	int insertSelective(Auth record);

	List<Auth> selectAll();

	List<String> selectAuthIDs(Integer ID);

	Auth selectByPrimaryKey(Integer ID);

	int updateByPrimaryKeySelective(Auth record);

	int updateByPrimaryKey(Auth record);
}