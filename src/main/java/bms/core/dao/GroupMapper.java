package bms.core.dao;

import java.util.List;
import java.util.Map;

import bms.core.model.Group;

public interface GroupMapper {
	void deleteByPrimaryKey(Map<String, Object> map);

	int deleteByIDs(String[] groupIDs);

	int insert(Group record);

	int insertSelective(Group record);

	List<Group> selectAll();

	List<Group> selectByType(String type);

	Group selectByPrimaryKey(Integer ID);

	int updateByPrimaryKeySelective(Group record);

	int updateByPrimaryKey(Group record);
}