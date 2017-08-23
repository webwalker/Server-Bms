package bms.core.dao;

import java.util.List;
import java.util.Map;

import bms.core.model.MenuAuth;

public interface MenuAuthMapper {

	int batchInsert(List<MenuAuth> ug);

	int batchDelete(Map<String, Object> map);

	List<MenuAuth> getAuthElements(Integer ID);

	int deleteByPrimaryKey(Integer ID);

	int insert(MenuAuth record);

	int insertSelective(MenuAuth record);

	MenuAuth selectByPrimaryKey(Integer ID);

	List<MenuAuth> selectAll();

	int updateByPrimaryKeySelective(MenuAuth record);

	int updateByPrimaryKey(MenuAuth record);
}