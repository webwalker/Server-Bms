package bms.core.dao;

import java.util.List;

import bms.core.model.GroupMenu;

public interface GroupMenuMapper {

	int deleteByGroupID(Integer ID);

	int deleteByPrimaryKey(Integer ID);

	int batchInsert(List<GroupMenu> list);

	int insert(GroupMenu record);

	int insertSelective(GroupMenu record);

	List<GroupMenu> selectByGroupID(Integer ID);

	GroupMenu selectByPrimaryKey(Integer ID);

	int updateByPrimaryKeySelective(GroupMenu record);

	int updateByPrimaryKey(GroupMenu record);
}