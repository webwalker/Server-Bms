package bms.core.dao;

import java.util.List;

import bms.core.model.MenuAuth;

public interface UrlSetMapper {
	int deleteByIDs(String[] ids);

	int deleteByPrimaryKey(Integer ID);

	int insert(MenuAuth record);

	int insertSelective(MenuAuth record);

	List<MenuAuth> selectMenuUrl(Integer ID);

	List<MenuAuth> selectAuthUrl(Integer ID);

	int deleteMenuUrl(Integer ID);

	int deleteAuthUrl(Integer ID);

	MenuAuth selectByPrimaryKey(Integer ID);

	int updateByPrimaryKeySelective(MenuAuth record);

	int updateByPrimaryKey(MenuAuth record);
}