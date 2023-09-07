package com.itl.pns.entity;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "USERDEVICESMASTER")
public class UserDeviceDetails {

	@javax.persistence.Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USERDEVICESMASTEREntity_Sequence")
	@SequenceGenerator(name = "USERDEVICESMASTEREntity_Sequence", sequenceName = "userdevicesmaster_id_SEQ")
	public int Id;

	@Column(name = "CUSTOMERID")
	public int customerId;

	@Column(nullable = true, name = "DEVICEUUID")
	public String deviceUUID;

	@Column(name = "DEVICEMODEL")
	public String deviceModel;

	@Column(nullable = true, name = "BIOMETRICSUPPORT")
	public String bioMetricSupport;

	@Column(nullable = true, name = "IMEI")
	public String imei;

	@Column(nullable = true, name = "IMSI")
	public String imsi;

	@Column(nullable = true, name = "OSVERSION")
	public String osVersion;

	@Column(nullable = true, name = "OS")
	public String os;

	@Column(name = "MACADDRESS")
	public String macAddress;

	@Column(nullable = true, name = "PUSHNOTIFICATIONTOKEN")
	public String pushNotificationToken;

	@Column(nullable = true, name = "CREATEDBY")
	public int createdBy;

	@Column(nullable = true, name = "CREATEDON")
	public Date createdOn;

	@Column(nullable = true, name = "UPDATEDBY")
	public int updatedBy;

	@Column(nullable = true, name = "UPDATEDON")
	public Date updatedOn;

	@Column(nullable = true, name = "STATUSID")
	public int statusId;
	
	@Column(name = "APPID")
	public BigDecimal appId;

	@Transient
	private BigDecimal user_ID;

	@Transient
	private BigDecimal role_ID;

	@Transient
	private BigDecimal subMenu_ID;

	@Transient
	private String remark;

	@Transient
	private String activityName;

	@Transient
	private BigInteger userAction;

	@Transient
	private String statusName;

	@Transient
	private String customerName;

	@Transient
	private String roleName;

	@Transient
	private String createdByName;

	@Transient
	public String mobileNumber;

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getDeviceUUID() {
		return deviceUUID;
	}

	public void setDeviceUUID(String deviceUUID) {
		this.deviceUUID = deviceUUID;
	}

	public String getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

	public String getBioMetricSupport() {
		return bioMetricSupport;
	}

	public void setBioMetricSupport(String bioMetricSupport) {
		this.bioMetricSupport = bioMetricSupport;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getOsVersion() {
		return osVersion;
	}

	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public String getPushNotificationToken() {
		return pushNotificationToken;
	}

	public void setPushNotificationToken(String pushNotificationToken) {
		this.pushNotificationToken = pushNotificationToken;
	}

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public int getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(int updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	public BigDecimal getUser_ID() {
		return user_ID;
	}

	public void setUser_ID(BigDecimal user_ID) {
		this.user_ID = user_ID;
	}

	public BigDecimal getRole_ID() {
		return role_ID;
	}

	public void setRole_ID(BigDecimal role_ID) {
		this.role_ID = role_ID;
	}

	public BigDecimal getSubMenu_ID() {
		return subMenu_ID;
	}

	public void setSubMenu_ID(BigDecimal subMenu_ID) {
		this.subMenu_ID = subMenu_ID;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public BigInteger getUserAction() {
		return userAction;
	}

	public void setUserAction(BigInteger userAction) {
		this.userAction = userAction;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public BigDecimal getAppId() {
		return appId;
	}

	public void setAppId(BigDecimal appId) {
		this.appId = appId;
	}

}
