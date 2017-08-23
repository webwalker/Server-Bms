package bms.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import bms.core.dao.UrlSetMapper;
import bms.core.model.MenuAuth;
import bms.core.model.MyResponse;

/**
 * @author xu.jian
 * 
 */
@Service
public class UrlSetService extends BaseService {
	@Autowired
	UrlSetMapper urlsetMapper;

	public MyResponse add(MenuAuth auth, BindingResult result) {
		try {
			setID(auth);
			urlsetMapper.insertSelective(auth);
		} catch (Exception e) {
			e.printStackTrace();
			return msg.getFailed();
		}
		return msg.getSuccess();
	}

	public MyResponse update(MenuAuth auth, BindingResult result) {
		setID(auth);
		urlsetMapper.updateByPrimaryKeySelective(auth);
		return msg.getSuccess();
	}

	public MyResponse delete(String id) {
		if (id.isEmpty())
			return msg.getFailed();

		int retCode = urlsetMapper.deleteByIDs(id.split(","));
		if (retCode > 0)
			return msg.getSuccess();
		return msg.getFailed();
	}

	public void setID(MenuAuth auth) {
		if (auth.getType() == 1) {
			auth.setMenuID(auth.getUrlID());
			auth.setAuthID(0);
		} else {
			auth.setMenuID(0);
			auth.setAuthID(auth.getUrlID());
		}
	}
}
