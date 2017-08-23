package bms.core.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bms.core.dao.MenuMapper;
import bms.core.dao.UserMenuMapper;
import bms.core.model.Menu;
import bms.core.model.json.Tree;

/**
 * @author xu.jian
 * 
 */
@Service
public class MenuTreeService {
	@Autowired
	private UserMenuMapper userMenuMapper;
	@Autowired
	private MenuMapper menuMapper;

	private List<Menu> menus = new ArrayList<Menu>();
	private List<Tree> trees = new ArrayList<Tree>();

	private List<Menu> getChilds(int parentId) {
		List<Menu> result = new ArrayList<Menu>();
		for (Menu m : menus) {
			if (m != null && m.getParentID() == parentId) {
				result.add(m);
			}
		}
		return result;
	}

	private boolean hasChilds(int parentId) {
		for (Menu m : menus) {
			if (m != null && m.getParentID() == parentId)
				return true;
		}
		return false;
	}

	private Tree createTreeNode(Menu m) {
		Tree t = new Tree();
		t.setId(m.getID());
		t.setText(m.getMenuName());
		t.setUrl(m.getUrl());
		t.setState("open");
		t.setSort(m.getSort());
		return t;
	}

	private void buildTree(Tree tree, int parentId) {
		List<Menu> childs = getChilds(parentId);
		for (Menu m : childs) {
			Tree treeNode = createTreeNode(m);
			tree.getChildren().add(treeNode);
			if (hasChilds(m.getID())) {
				buildTree(treeNode, m.getID());
			}
		}
	}

	// 获取登录时菜单信息
	public List<Tree> getAuthMenus(int userId) {
		menus = userMenuMapper.selectByPrimaryKey(userId);
		return getTree(menus);
	}

	// 获取带排序的所有菜单信息
	public List<Tree> getAllMenus() {
		menus = menuMapper.selectAll();
		return getTree(menus);
	}

	public List<Tree> getTree(List<Menu> menus) {
		List<Menu> parents = getChilds(0); // root
		trees.clear();

		for (Menu m : parents) {
			Tree tree = createTreeNode(m);
			trees.add(tree);
			buildTree(tree, m.getID());
		}
		return trees;
	}
}
