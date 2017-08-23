package bms.core.dao;

import java.util.List;

import bms.core.model.Menu;

public interface MenuMapper {
	int deleteByPrimaryKey(Integer ID);

	int deleteByIDs(String[] groupIDs);

	int deleteByParentID(String[] groupIDs);

	int insert(Menu record);

	int insertSelective(Menu record);

	List<String> selectAllMenuUrls();

	List<Menu> selectAll();

	List<Menu> selectByParentID(int parentID);

	Menu selectByPrimaryKey(Integer ID);

	int updateMenuSort(Menu record);

	int updateByPrimaryKeySelective(Menu record);

	int updateByPrimaryKey(Menu record);
}