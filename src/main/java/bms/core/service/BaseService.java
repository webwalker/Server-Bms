package bms.core.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import bms.core.common.Loggers;
import bms.core.common.util.MessageUtil;
import bms.core.model.UserGroup;

/**
 * @author xu.jian
 * 
 */
public class BaseService {

	@Autowired
	protected MessageUtil msg;

	@Autowired
	protected SqlSession sqlSession;

	public String[] getRecepts(List<UserGroup> users) {
		String[] recepts = new String[users.size()];
		for (int i = 0; i < users.size(); i++) {
			UserGroup ug = users.get(i);
			recepts[i] = ug.getEmail();
		}
		Loggers.info("getRecepts size:" + recepts.length);
		return recepts;
	}
}
