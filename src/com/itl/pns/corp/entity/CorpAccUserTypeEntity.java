package com.itl.pns.corp.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author shubham.lokhande
 *
 */
@Entity
@Table(name = "CORP_ACC_USER_TYPE")
public class CorpAccUserTypeEntity {

	@javax.persistence.Id
	@Column(name = "ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "corp_acc_user_type_ID_SEQ")
	@SequenceGenerator(name = "corp_acc_user_type_ID_SEQ", sequenceName = "corp_acc_user_type_ID_SEQ", allocationSize=1)
	private BigDecimal id;

	@Column(name = "ACCOUNT_TYPE_ID")
	BigDecimal accountTypeId;

	@Column(name = "CORP_USER_TYPE_ID")
	BigDecimal corpUserTypeId;

	@Column(name = "CREATEDON")
	Date createdon;

	@Column(name = "CREATEDBY")
	BigDecimal createdby;

	
	@Column(name = "STATUSID")
	BigDecimal statusid;

	@Transient
	String userType;
	
	@Transient
	String accountType;
	
	@Transient
	String statusName;
	    
	@Transient
	private String productName;

	@Transient  
	private String action;

	@Transient  
	private String roleName;

	@Transient  
	private String createdByName;

	@Transient
	private BigDecimal role_ID;

	@Transient
	private String remark;

	@Transient
	private String activityName;

	@Transient
	private BigDecimal userAction;
	
	@Transient
	private BigDecimal user_ID;
	
	@Transient
	private BigDecimal subMenu_ID;


	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public BigDecimal getAccountTypeId() {
		return accountTypeId;
	}

	public void setAccountTypeId(BigDecimal accountTypeId) {
		this.accountTypeId = accountTypeId;
	}

	public BigDecimal getCorpUserTypeId() {
		return corpUserTypeId;
	}

	public void setCorpUserTypeId(BigDecimal corpUserTypeId) {
		this.corpUserTypeId = corpUserTypeId;
	}

	public Date getCreatedon() {
		return createdon;
	}

	public void setCreatedon(Date createdon) {
		this.createdon = createdon;
	}

	public BigDecimal getCreatedby() {
		return createdby;
	}

	public void setCreatedby(BigDecimal createdby) {
		this.createdby = createdby;
	}

	public BigDecimal getStatusid() {
		return statusid;
	}

	public void setStatusid(BigDecimal statusid) {
		this.statusid = statusid;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
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

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
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

	public BigDecimal getRole_ID() {
		return role_ID;
	}

	public void setRole_ID(BigDecimal role_ID) {
		this.role_ID = role_ID;
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

	public BigDecimal getUser_ID() {
		return user_ID;
	}

	public void setUser_ID(BigDecimal user_ID) {
		this.user_ID = user_ID;
	}

	public BigDecimal getSubMenu_ID() {
		return subMenu_ID;
	}

	public void setSubMenu_ID(BigDecimal subMenu_ID) {
		this.subMenu_ID = subMenu_ID;
	}
	
	

}
