package bms.core.common.pager;

import java.sql.Connection;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Description: MybatisHelper Author: liuzh Update: liuzh(2014-06-06 13:33)
 */
public class MybatisHelper {

	@Autowired
	static SqlSessionFactory sqlSessionFactory;

	static {
		// 创建数据库
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			Connection conn = session.getConnection();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	/**
	 * 获取Session
	 * 
	 * @return
	 */
	public static SqlSession getSqlSession() {
		return sqlSessionFactory.openSession();
	}
}
