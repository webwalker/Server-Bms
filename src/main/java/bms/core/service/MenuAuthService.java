package bms.core.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bms.core.dao.MenuAuthMapper;
import bms.core.dao.MenuMapper;
import bms.core.model.Menu;
import bms.core.model.MenuAuth;
import bms.core.model.MyResponse;
import bms.core.model.json.SimpleAuth;

/**
 * @author xu.jian
 * 
 */
@Service
public class MenuAuthService extends BaseService {
	@Autowired
	MenuMapper menuMapper;
	@Autowired
	MenuAuthMapper menuAuthMapper;

	public Menu getMenu(int id) {
		return menuMapper.selectByPrimaryKey(id);
	}

	public MyResponse add(String createUser, int menuID, String authIDs,
			String all) {
		try {
			delete(menuID, all);

			List<MenuAuth> list = new ArrayList<MenuAuth>();
			String[] ids = authIDs.split(",");
			for (String id : ids) {
				MenuAuth ug = new MenuAuth();
				ug.setCreateUser(createUser);
				ug.setMenuID(menuID);
				ug.setAuthID(Integer.valueOf(id));
				list.add(ug);
			}
			menuAuthMapper.batchInsert(list);
		} catch (Exception e) {
			e.printStackTrace();
			return msg.getFailed();
		}
		return msg.getSuccess();
	}

	public MyResponse delete(int menuID, String authIDs) {
		if (menuID <= 0 || authIDs.isEmpty())
			return msg.getFailed();

		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("menuID", menuID);
		maps.put("authID", authIDs.split(","));

		int retCode = menuAuthMapper.batchDelete(maps);
		if (retCode > 0)
			return msg.getSuccess();
		return msg.getFailed();
	}

	// 获取所有待关联的权限元素
	public List<MenuAuth> getAuthElements(int menuID) {
		MenuAuthMapper authMapper = sqlSession.getMapper(MenuAuthMapper.class);
		List<MenuAuth> auths = authMapper.getAuthElements(menuID);
		return auths;
	}

	public List<MenuAuth> getAllAuthElements() {
		return menuAuthMapper.selectAll();
	}

	public List<SimpleAuth> getAllSimpleAuths() {
		List<MenuAuth> list = menuAuthMapper.selectAll();
		List<SimpleAuth> auths = new ArrayList<SimpleAuth>();
		for (MenuAuth m : list) {
			SimpleAuth sa = new SimpleAuth();
			sa.setMenuID(m.getMenuID());
			sa.setAuthID(m.getAuthID());
			sa.setAuthName(m.getAuthName());
			auths.add(sa);
		}
		return auths;
	}
}
