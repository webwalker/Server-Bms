package bms.core.model.json;


/**
 * 用于页面输出简化
 * 
 * @author xu.jian
 * 
 */
public class SimpleAuth {
	private Integer menuID;

	private Integer authID;

	private String authName;

	public Integer getMenuID() {
		return menuID;
	}

	public void setMenuID(Integer menuID) {
		this.menuID = menuID;
	}

	public Integer getAuthID() {
		return authID;
	}

	public void setAuthID(Integer authID) {
		this.authID = authID;
	}

	public String getAuthName() {
		return authName;
	}

	public void setAuthName(String authName) {
		this.authName = authName == null ? null : authName.trim();
	}
}