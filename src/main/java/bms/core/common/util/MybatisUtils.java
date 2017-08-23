package bms.core.common.util;

import org.apache.ibatis.session.SqlSession;

/**
 * 注意引起异常： No operations allowed after connection closed
 * 
 * @author xu.jian
 * 
 */
public class MybatisUtils {

	public enum CRUD_Enum {

		Add,

		Delete,

		Update,

		Query,

		List
	}

	/*
	 * 获取数据库访问链接
	 */
	public static SqlSession getSqlSession() {
		SqlSession session = null;

		return session;
	}

	/*
	 * 获取数据库访问链接
	 */
	public static void closeSession(SqlSession session) {
		session.close();
	}

	/*
	 * 返回操作记录消息
	 */
	public static void showMessages(CRUD_Enum type, int count) {
		switch (type) {
		case Add:
			System.out.println("添加了" + count + "条记录。");
			break;
		case Delete:
			System.out.println("删除了" + count + "条记录。");
			break;
		case Update:
			System.out.println("更新了" + count + "条记录。");
			break;
		case Query:
			System.out.println("匹配了" + count + "条记录。");
			break;
		case List:
			System.out.println("共有" + count + "条记录。");
			break;
		default:
			break;
		}
	}
}
