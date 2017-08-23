package bms.core.model;

import java.util.Date;

public class UserGroup {
	private Integer ID;

	private Integer userID;

	// 被操作的目标用户
	private Integer targetUserID;

	private Integer groupID;

	private Date createTime;

	private String createUser;

	private Date updateTime;

	private String groupName;

	private String description;

	private String userName;

	private int userType;

	private String userTypes;

	private String email;

	private boolean ck;

	public boolean isCk() {
		if (groupID != null) {
			return true;
		}
		return false;
	}

	public void setCk(boolean ck) {
		this.ck = ck;
	}

	public String getUserTypes() {
		if (this.userType == 0)
			return "";
		return UserType.getByCode(this.userType).getDesc();
	}

	public void setUserTypes(String userTypes) {
		this.userTypes = userTypes;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	private Boolean active = true;

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getID() {
		return ID;
	}

	public void setID(Integer ID) {
		this.ID = ID;
	}

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	public Integer getTargetUserID() {
		return targetUserID;
	}

	public void setTargetUserID(Integer targetUserID) {
		this.targetUserID = targetUserID;
	}

	public Integer getGroupID() {
		return groupID;
	}

	public void setGroupID(Integer groupID) {
		this.groupID = groupID;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser == null ? null : createUser.trim();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}