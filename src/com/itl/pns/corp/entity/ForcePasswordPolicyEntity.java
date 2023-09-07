package com.itl.pns.corp.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "FORCE_PWD_POLICY")
public class ForcePasswordPolicyEntity {

	@javax.persistence.Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FORCE_PWD_POLICY_ID_SEQ")
	@SequenceGenerator(name = "FORCE_PWD_POLICY_ID_SEQ", sequenceName = "FORCE_PWD_POLICY_ID_SEQ", allocationSize = 1)
	private Long id;

	@Column(name = "POLICY_APPLICABLE_ON")
	private String policyApplicationOn;

	@Column(name = "CHANNEL")
	private String channel;

	@Column(name = "PWD_EXP_DAYS")
	private Integer passwordExpDays;

	@Column(name = "MPIN_EXP_DAYS")
	private Integer mpinExpiryDays;

	@Column(name = "TPIN_EXP_DAYS")
	private Integer tpinExpiryDays;

	@Column(name = "PWD_RMD_DAYS")
	private Integer passwordExpReminderDays;

	@Column(name = "MPIN_RMD_DAYS")
	private Integer mpinExpiryReminderDays;

	@Column(name = "TPIN_RMD_DAYS")
	private Integer tpinExpiryReminderDays;

	@Column(name = "CREATED_BY")
	private Integer createdBy;

	@Column(name = "CREATED_ON")
	private Timestamp createdOn;

	@Column(name = "UPDATED_BY")
	private Integer updatedBy;

	@Column(name = "UPDATED_ON")
	private Timestamp updatedOn;

	@Column(name = "STATUSID")
	private Integer statusId;

	@Column(name = "APPID")
	private Integer appId;

	@Transient
	private Integer createdby;

	@Transient
	private String statusName;

	@Transient
	private String productName;

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

	@Transient
	private String branchCode;

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public Integer getCreatedby() {
		return createdby;
	}

	public void setCreatedby(Integer createdby) {
		this.createdby = createdby;
	}

	public Integer getAppId() {
		return appId;
	}

	public void setAppId(Integer appId) {
		this.appId = appId;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPolicyApplicationOn() {
		return policyApplicationOn;
	}

	public void setPolicyApplicationOn(String policyApplicationOn) {
		this.policyApplicationOn = policyApplicationOn;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public Integer getPasswordExpDays() {
		return passwordExpDays;
	}

	public void setPasswordExpDays(Integer passwordExpDays) {
		this.passwordExpDays = passwordExpDays;
	}

	public Integer getPasswordExpReminderDays() {
		return passwordExpReminderDays;
	}

	public void setPasswordExpReminderDays(Integer passwordExpReminderDays) {
		this.passwordExpReminderDays = passwordExpReminderDays;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public Integer getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Timestamp getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
	}

	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	public Integer getMpinExpiryDays() {
		return mpinExpiryDays;
	}

	public void setMpinExpiryDays(Integer mpinExpiryDays) {
		this.mpinExpiryDays = mpinExpiryDays;
	}

	public Integer getTpinExpiryDays() {
		return tpinExpiryDays;
	}

	public void setTpinExpiryDays(Integer tpinExpiryDays) {
		this.tpinExpiryDays = tpinExpiryDays;
	}

	public Integer getMpinExpiryReminderDays() {
		return mpinExpiryReminderDays;
	}

	public void setMpinExpiryReminderDays(Integer mpinExpiryReminderDays) {
		this.mpinExpiryReminderDays = mpinExpiryReminderDays;
	}

	public Integer getTpinExpiryReminderDays() {
		return tpinExpiryReminderDays;
	}

	public void setTpinExpiryReminderDays(Integer tpinExpiryReminderDays) {
		this.tpinExpiryReminderDays = tpinExpiryReminderDays;
	}

}
