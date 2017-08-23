package bms.core.dao;

import java.util.List;

import bms.core.model.Menu;

public interface UserMenuMapper {

	/**
	 * 查询用户又权限的菜单
	 */
	List<Menu> selectByPrimaryKey(Integer UserID);
}