package bms.core.dao;

import java.util.List;

import bms.core.model.GroupAuth;

public interface GroupAuthMapper {
	int deleteByGroupID(Integer ID);

	int deleteByPrimaryKey(Integer ID);

	int batchInsert(List<GroupAuth> list);

	int insert(GroupAuth record);

	int insertSelective(GroupAuth record);

	List<GroupAuth> selectByGroupID(Integer ID);

	GroupAuth selectByPrimaryKey(Integer ID);

	int updateByPrimaryKeySelective(GroupAuth record);

	int updateByPrimaryKey(GroupAuth record);
}