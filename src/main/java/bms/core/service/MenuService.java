package bms.core.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import bms.core.dao.MenuMapper;
import bms.core.model.Menu;
import bms.core.model.MyResponse;
import bms.core.model.User;
import bms.core.model.UserType;
import bms.core.model.json.Tree;

/**
 * @author xu.jian
 * 
 */
@Service
public class MenuService extends BaseService {
	@Autowired
	MenuMapper menuMapper;
	@Autowired
	MenuTreeService treeService;

	public Menu getMenu(int id) {
		return menuMapper.selectByPrimaryKey(id);
	}

	public List<Tree> getAuthMenus(User user) {
		UserType type = user.getUserTypeEnum();
		int userId = user.getID();
		if (type == UserType.Admin)
			return treeService.getAllMenus();
		else
			return treeService.getAuthMenus(userId);
	}

	public MyResponse add(Menu menu, BindingResult result) {
		try {
			menuMapper.insertSelective(menu);
		} catch (Exception e) {
			e.printStackTrace();
			return msg.getFailed();
		}
		return msg.getSuccess();
	}

	public MyResponse update(Menu menu, BindingResult result) {
		menuMapper.updateByPrimaryKeySelective(menu);
		return msg.getSuccess();
	}

	public MyResponse delete(String id) {
		if (id.isEmpty())
			return msg.getFailed();

		String[] ids = id.split(",");
		int retCode = menuMapper.deleteByIDs(ids);

		// 递归删除子菜单
		List<String> list = new ArrayList<String>();
		for (String s : ids) {
			getParentIDs(list, Integer.valueOf(s));
		}
		menuMapper.deleteByParentID(list.toArray(ids));

		if (retCode > 0)
			return msg.getSuccess();
		return msg.getFailed();
	}

	public MyResponse sort(HttpServletRequest request) {
		int id = Integer.valueOf(request.getParameter("id"));
		int sort = Integer.valueOf(request.getParameter("sort"));
		int parent = Integer.valueOf(request.getParameter("parent"));

		Menu menu = new Menu();
		menu.setParentID(parent);
		menu.setSort(sort);
		menu.setID(id);
		int retCode = menuMapper.updateMenuSort(menu);
		if (retCode > 0)
			return msg.getSuccess();
		return msg.getFailed();
	}

	// 递归获取父节点
	private void getParentIDs(List<String> list, int parentID) {
		list.add(parentID + "");
		List<Menu> menus = menuMapper.selectByParentID(parentID);
		if (menus != null && menus.size() > 0) {
			for (Menu m : menus) {
				getParentIDs(list, m.getID());
			}
		}
	}
}
