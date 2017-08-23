package bms.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import bms.core.dao.AuthMapper;
import bms.core.model.Auth;
import bms.core.model.MyResponse;

/**
 * @author xu.jian
 * 
 */
@Service
public class AuthSetService extends BaseService {
	@Autowired
	AuthMapper authMapper;

	public MyResponse add(Auth auth, BindingResult result) {
		try {
			authMapper.insertSelective(auth);
		} catch (Exception e) {
			e.printStackTrace();
			return msg.getFailed();
		}
		return msg.getSuccess();
	}

	public MyResponse update(Auth auth, BindingResult result) {
		authMapper.updateByPrimaryKeySelective(auth);
		return msg.getSuccess();
	}

	public MyResponse delete(String id) {
		if (id.isEmpty())
			return msg.getFailed();

		int retCode = authMapper.deleteByIDs(id.split(","));
		if (retCode > 0)
			return msg.getSuccess();
		return msg.getFailed();
	}
}
