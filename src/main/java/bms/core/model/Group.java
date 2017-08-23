package bms.core.model;

import java.util.Date;

import javax.validation.constraints.NotNull;

public class Group {
	private Integer ID;

	@NotNull(message = "{group.name.error}")
	private String groupName;

	private String groupType;

	@NotNull(message = "{group.desc.error}")
	private String description;

	private Date createTime;

	private String createUser;

	private int createUserID;

	private Date updateTime;

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

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName == null ? null : groupName.trim();
	}

	public String getGroupType() {
		return groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description == null ? null : description.trim();
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

	public int getCreateUserID() {
		return createUserID;
	}

	public void setCreateUserID(int createUserID) {
		this.createUserID = createUserID;
	}
}