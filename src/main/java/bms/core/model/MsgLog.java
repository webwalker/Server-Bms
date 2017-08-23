package bms.core.model;

import java.util.Date;

public class MsgLog {
	private Integer ID;

	private String userID;

	private String userGroup;

	private String area;

	private String osVersion;

	private String apkVersion;

	private String moduleUrl;

	private String payAgentUrl;

	private String appCode;

	private Integer type = 0;

	public String msgType;

	private String label;

	private String tempCode;

	private String ext;

	private Boolean isValid;

	private int msgStatus;

	private Integer createIndex;

	private Date createTime;

	private String message;

	private String data;

	private int logCount;

	public Integer getID() {
		return ID;
	}

	public void setID(Integer ID) {
		this.ID = ID;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID == null ? null : userID.trim();
	}

	public String getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(String userGroup) {
		this.userGroup = userGroup == null ? null : userGroup.trim();
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area == null ? null : area.trim();
	}

	public String getOsVersion() {
		return osVersion;
	}

	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion == null ? null : osVersion.trim();
	}

	public String getApkVersion() {
		return apkVersion;
	}

	public void setApkVersion(String apkVersion) {
		this.apkVersion = apkVersion == null ? null : apkVersion.trim();
	}

	public String getModuleUrl() {
		return moduleUrl;
	}

	public void setModuleUrl(String moduleUrl) {
		this.moduleUrl = moduleUrl == null ? null : moduleUrl.trim();
	}

	public String getPayAgentUrl() {
		return payAgentUrl;
	}

	public void setPayAgentUrl(String payAgentUrl) {
		this.payAgentUrl = payAgentUrl == null ? null : payAgentUrl.trim();
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode == null ? null : appCode.trim();
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label == null ? null : label.trim();
	}

	public String getTempCode() {
		return tempCode;
	}

	public void setTempCode(String tempCode) {
		this.tempCode = tempCode;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext == null ? null : ext.trim();
	}

	public Boolean getIsValid() {
		return isValid;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}

	public int getMsgStatus() {
		return msgStatus;
	}

	public void setMsgStatus(int msgStatus) {
		this.msgStatus = msgStatus;
	}

	public Integer getCreateIndex() {
		return createIndex;
	}

	public void setCreateIndex(Integer createIndex) {
		this.createIndex = createIndex;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data == null ? null : data.trim();
	}

	public int getLogCount() {
		return logCount;
	}

	public void setLogCount(int logCount) {
		this.logCount = logCount;
	}

}