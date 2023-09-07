package com.itl.pns.corp.entity;

import java.math.BigDecimal;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
@Entity
@Table(name="CORP_TEMP_SAL_PROCESS")
public class CorpTempSalProcessEntity {


	@javax.persistence.Id
	@Column(name = "ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "corp_temp_sal_process_id_SEQ")
	@SequenceGenerator(name = "corp_temp_sal_process_id_SEQ", sequenceName = "corp_temp_sal_process_id_SEQ", allocationSize=1)
	BigDecimal id;

	@Column(name = "ACTIVITYCODE")
	String activityCode;
	
	@Column(name = "PAYEENAME")
	String payeeName;

	@Column(name = "PAYEEACCOUNTNUMBER")
	String payeeAccountNumber;
	
	@Column(name = "DESCRIPTION")
	String description;
	
	@Column(name = "FILENAME")
	String fileName;
	
	@Column(name = "AMOUNT")
	BigDecimal amount;
	
	@Column(name = "CURRENCY")
	String currency;
	
	@Column(name = "REMARKS")
	String remarks;

	@Column(name = "CREATEDBY")
	BigDecimal createdby;
	
	@Column(name = "CREATEDON")
	Date createdon;

	@Column(name = "STATUSID")
	BigDecimal statusId;

	@Column(name = "APPID")
	BigDecimal appId;
	

	@Column(name = "COMPANYID")
	BigDecimal companyId;
	
	
	@Transient
    private String statusName;
    
	@Transient
    private String appName; 
	
	
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

	public String getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}

	public String getPayeeName() {
		return payeeName;
	}

	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	public String getPayeeAccountNumber() {
		return payeeAccountNumber;
	}

	public void setPayeeAccountNumber(String payeeAccountNumber) {
		this.payeeAccountNumber = payeeAccountNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public BigDecimal getCreatedby() {
		return createdby;
	}

	public void setCreatedby(BigDecimal createdby) {
		this.createdby = createdby;
	}

	public Date getCreatedon() {
		return createdon;
	}

	public void setCreatedon(Date createdon) {
		this.createdon = createdon;
	}

	public BigDecimal getStatusId() {
		return statusId;
	}

	public void setStatusId(BigDecimal statusId) {
		this.statusId = statusId;
	}

	public BigDecimal getAppId() {
		return appId;
	}

	public void setAppId(BigDecimal appId) {
		this.appId = appId;
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

	public BigDecimal getCompanyId() {
		return companyId;
	}

	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}
	
	

	
}
