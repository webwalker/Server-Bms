package bms.core.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bms.core.dao.GroupAuthMapper;
import bms.core.dao.GroupMenuMapper;
import bms.core.model.GroupAuth;
import bms.core.model.GroupMenu;
import bms.core.model.MyResponse;
import bms.core.model.User;
import bms.core.model.UserType;
import bms.core.model.json.SimpleGroupAuth;
import bms.core.model.json.SimpleGroupMenu;
import bms.core.model.json.Tree;

/**
 * @author xu.jian
 * 
 */
@Service
public class GroupMenuService extends BaseService {
	@Autowired
	GroupMenuMapper gmMapper;
	@Autowired
	GroupAuthMapper gaMapper;
	@Autowired
	MenuTreeService treeService;

	public List<Tree> getAuthMenus(User user) {
		UserType type = user.getUserTypeEnum();
		int userId = user.getID();
		if (type == UserType.Admin)
			return treeService.getAllMenus();
		else
			return treeService.getAuthMenus(userId);
	}

	public List<SimpleGroupMenu> getGroupMenus(int groupID) {
		List<SimpleGroupMenu> gms = new ArrayList<SimpleGroupMenu>();
		List<GroupMenu> list = gmMapper.selectByGroupID(groupID);
		for (GroupMenu g : list) {
			SimpleGroupMenu gm = new SimpleGroupMenu();
			gm.setGroupID(g.getGroupID());
			gm.setMenuID(g.getMenuID());
			gms.add(gm);
		}
		return gms;
	}

	public List<SimpleGroupAuth> getGroupAuths(int groupID) {
		List<SimpleGroupAuth> gas = new ArrayList<SimpleGroupAuth>();
		List<GroupAuth> list = gaMapper.selectByGroupID(groupID);
		for (GroupAuth g : list) {
			SimpleGroupAuth ga = new SimpleGroupAuth();
			ga.setGroupID(g.getGroupID());
			ga.setAuthID(g.getAuthID());
			gas.add(ga);
		}
		return gas;
	}

	public MyResponse updateAuthMapping(int groupID, String user, int[] menus,
			int[] auths) {
		try {
			if (groupID <= 0 || menus.length == 0)
				return msg.getFailed();

			gmMapper.deleteByGroupID(groupID);
			gaMapper.deleteByGroupID(groupID);

			List<GroupMenu> gms = new ArrayList<GroupMenu>();
			List<GroupAuth> gas = new ArrayList<GroupAuth>();
			for (int i = 0; i < menus.length; i++) {
				GroupMenu gm = new GroupMenu();
				gm.setGroupID(groupID);
				gm.setMenuID(menus[i]);
				gm.setCreateUser(user);
				gms.add(gm);
			}
			for (int i = 0; i < auths.length; i++) {
				GroupAuth ga = new GroupAuth();
				ga.setGroupID(groupID);
				ga.setAuthID(auths[i]);
				ga.setCreateUser(user);
				gas.add(ga);
			}
			if (gms.size() > 0)
				gmMapper.batchInsert(gms);
			if (gas.size() > 0)
				gaMapper.batchInsert(gas);
		} catch (Exception e) {
			e.printStackTrace();
			return msg.getFailed();
		}

		return msg.getSuccess();
	}
}
