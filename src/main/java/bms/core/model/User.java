package bms.core.model;

import java.util.Date;

import javax.validation.constraints.Pattern;

public class User {
	private Integer ID;

	@Pattern(regexp = "^\\s*|\\w{4,50}$", message = "{user.name.error}")
	private String userName;

	@Pattern(regexp = "^\\s*|.{4,50}$", message = "{user.password.error}")
	private String userPass;

	private String confPass;

	private int userType;

	private String userTypes;

	@Pattern(regexp = "^\\s*|(\\w)+(.\\w+)*@(\\w)+((.\\w+)+)$", message = "{user.email.error}")
	private String email;

	@Pattern(regexp = "^\\s*|(13|15|18)\\d{9}$", message = "{user.mobile.error}")
	private String mobile;

	@Pattern(regexp = "^\\s*|((\\(\\d{2,3}\\))|(\\d{3}\\-))?(\\(0\\d{2,3}\\)|0\\d{2,3}-)?[1-9]\\d{6,7}(\\-\\d{1,4})?$", message = "{user.phone.error}")
	private String phone;

	@Pattern(regexp = "^\\s*|.{4,}$", message = "{user.desc.error}")
	private String description;

	private Boolean active = true;

	private Integer logonTimes = 0;

	private String logonIp;

	private Date lastLogonTime;

	private Date createTime;

	private String createUser;

	private int createUserID;

	private Date updateTime;

	public boolean isAdmin() {
		if (UserType.getByCode(userType) == UserType.Admin)
			return true;
		return false;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName == null ? null : userName.trim();
	}

	public String getUserPass() {
		return userPass;
	}

	public void setUserPass(String userPass) {
		this.userPass = userPass == null ? null : userPass.trim();
	}

	public String getConfPass() {
		return confPass;
	}

	public void setConfPass(String confPass) {
		this.confPass = confPass;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public String getUserTypes() {
		return UserType.getByCode(this.userType).getDesc();
	}

	public void setUserTypes(String userTypes) {
		this.userTypes = userTypes;
	}

	public UserType getUserTypeEnum() {
		return UserType.getByCode(userType);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email == null ? null : email.trim();
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone == null ? null : phone.trim();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description == null ? null : description.trim();
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Integer getLogonTimes() {
		return logonTimes;
	}

	public void setLogonTimes(Integer logonTimes) {
		this.logonTimes = logonTimes;
	}

	public String getLogonIp() {
		return logonIp;
	}

	public void setLogonIp(String logonIp) {
		this.logonIp = logonIp;
	}

	public Date getLastLogonTime() {
		return lastLogonTime;
	}

	public void setLastLogonTime(Date lastLogonTime) {
		this.lastLogonTime = lastLogonTime;
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