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

@Entity
@Table(name = "CORP_ACCT_TRANS_LIMIT_USERS")
public class CorpAcctTransLimitUsersEntity {
	
	
	@javax.persistence.Id
	@Column(name = "ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "corp_acct_trans_limit_user_SEQ")
	@SequenceGenerator(name = "corp_acct_trans_limit_user_SEQ", sequenceName = "corp_acct_trans_limit_user_SEQ", allocationSize=1)
	private BigDecimal id;

	@Column(name = "CORPORATE_COMP_ID")
	BigDecimal corporateCompId;
	
	@Column(name = "CATM_ID")
	BigDecimal catmId;

	@Column(name = "CORP_USER_ID")
	BigDecimal corpUserId;


	@Column(name = "CREATEDON")
	Date createdon;

	@Column(name = "CREATEDBY")
	BigDecimal createdBy;

	
	@Column(name = "STATUSID")
	BigDecimal statusId;
	
	@Column(name = "APPID")
	BigDecimal appId;

	@Transient
	String userType;
	
	@Transient
	String accountType;
	
	@Transient
	String statusName;
	    
	@Transient
	private String productName;

	@Transient  
	private String createdByName;
	
	@Transient  
	private String firstName;
	
	@Transient  
	private String lastName;
	
	

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public BigDecimal getCorporateCompId() {
		return corporateCompId;
	}

	public void setCorporateCompId(BigDecimal corporateCompId) {
		this.corporateCompId = corporateCompId;
	}

	public BigDecimal getCatmId() {
		return catmId;
	}

	public void setCatmId(BigDecimal catmId) {
		this.catmId = catmId;
	}

	public BigDecimal getCorpUserId() {
		return corpUserId;
	}

	public void setCorpUserId(BigDecimal corpUserId) {
		this.corpUserId = corpUserId;
	}

	public Date getCreatedon() {
		return createdon;
	}

	public void setCreatedon(Date createdon) {
		this.createdon = createdon;
	}

	public BigDecimal getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(BigDecimal createdBy) {
		this.createdBy = createdBy;
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

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}



}
