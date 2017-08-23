package bms.core.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xu.jian
 * 
 */
public enum UserType {

	Admin(1, "管理员"),

	Agent(2, "代理商"),

	Merchant(3, "商户"),

	Member(4, "会员");

	public final int code;
	public final String desc;

	UserType(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public static UserType getByCode(int code) {
		for (UserType resultCode : UserType.values()) {
			if (resultCode.getCode() == code) {
				return resultCode;
			}
		}
		return null;
	}

	public int getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

	public static List<UserType> getList(UserType type) {
		List<UserType> list = new ArrayList<UserType>();
		switch (type) {
		case Admin:
			list.add(Admin);
			list.add(Agent);
			list.add(Merchant);
			list.add(Member);
			break;
		case Agent:
			list.add(Merchant);
			list.add(Member);
			break;
		case Merchant:
			list.add(Member);
			break;
		default:
			list.add(Member);
			break;
		}
		return list;
	}
}
