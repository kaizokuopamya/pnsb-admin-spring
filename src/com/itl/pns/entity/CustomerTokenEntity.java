package com.itl.pns.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "CUSTOMERTOKEN")
public class CustomerTokenEntity {

	@javax.persistence.Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
	@SequenceGenerator(name = "id_Sequence", sequenceName = "", allocationSize = 1)
	public BigDecimal id;

	@Column(name = "CHANNELID")
	public BigDecimal channelId;

	@Column(name = "APPID")
	public BigDecimal appId;

	@Column(name = "RRN")
	public String rrn;

	@Column(name = "TOKEN")
	public int token;

	@Column(name = "TOKENVALIDATEDON")
	public Date tokenValidatedOn;

	@Column(name = "GENERATEDONCHANNELID")
	public BigDecimal generatedOnChannelId;

	@Column(name = "CUSTOMERID")
	public BigDecimal customerId;

	@Column(name = "VALIDATEDONCHANNELID")
	public BigDecimal validatedOnChannelId;

	@Column(name = "INVALIDTOKENATTEMPTS")
	public int invalidTokenAttempts;

	@Column(name = "ACTIVITYID")
	public BigDecimal activityId;

	@Column(name = "CREATEDBY")
	public int createdBy;

	@Column(name = "CREATEDON")
	public Date createdOn;

	@Column(name = "UPDATEDBY")
	public int updatedBy;

	@Column(name = "UPDATEDON")
	public Date updatedOn;

	@Column(name = "STATUSID")
	public BigDecimal statusId;

	@Column(name = "TYPE")
	public String type;

	@Transient
	public String statusName;

	@Transient
	public String appName;

	@Transient
	private String createdByName;

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
	private BigDecimal userAction;

	@Transient
	private String roleName;

	@Transient
	private String action;

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public BigDecimal getChannelId() {
		return channelId;
	}

	public void setChannelId(BigDecimal channelId) {
		this.channelId = channelId;
	}

	public BigDecimal getAppId() {
		return appId;
	}

	public void setAppId(BigDecimal appId) {
		this.appId = appId;
	}

	public String getRrn() {
		return rrn;
	}

	public void setRrn(String rrn) {
		this.rrn = rrn;
	}

	public int getToken() {
		return token;
	}

	public void setToken(int token) {
		this.token = token;
	}

	public Date getTokenValidatedOn() {
		return tokenValidatedOn;
	}

	public void setTokenValidatedOn(Date tokenValidatedOn) {
		this.tokenValidatedOn = tokenValidatedOn;
	}

	public BigDecimal getGeneratedOnChannelId() {
		return generatedOnChannelId;
	}

	public void setGeneratedOnChannelId(BigDecimal generatedOnChannelId) {
		this.generatedOnChannelId = generatedOnChannelId;
	}

	public BigDecimal getCustomerId() {
		return customerId;
	}

	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}

	public BigDecimal getValidatedOnChannelId() {
		return validatedOnChannelId;
	}

	public void setValidatedOnChannelId(BigDecimal validatedOnChannelId) {
		this.validatedOnChannelId = validatedOnChannelId;
	}

	public int getInvalidTokenAttempts() {
		return invalidTokenAttempts;
	}

	public void setInvalidTokenAttempts(int invalidTokenAttempts) {
		this.invalidTokenAttempts = invalidTokenAttempts;
	}

	public BigDecimal getActivityId() {
		return activityId;
	}

	public void setActivityId(BigDecimal activityId) {
		this.activityId = activityId;
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

	public BigDecimal getStatusId() {
		return statusId;
	}

	public void setStatusId(BigDecimal statusId) {
		this.statusId = statusId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
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

	public BigDecimal getUserAction() {
		return userAction;
	}

	public void setUserAction(BigDecimal userAction) {
		this.userAction = userAction;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

}
