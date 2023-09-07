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
@Table(name = "CORP_ACCT_TRANS_MASTER")
public class CorpAcctTransMasterEntity {

	@javax.persistence.Id
	@Column(name = "ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "corp_acct_trans_master_ID_SEQ")
	@SequenceGenerator(name = "corp_acct_trans_master_ID_SEQ", sequenceName = "corp_acct_trans_master_ID_SEQ", allocationSize=1)
	private BigDecimal id;
	
	@Column(name = "CORPORATE_COMP_ID")
	private BigDecimal corporateCompId;

	@Column(name = "ACCOUNT_NUMBER")
	private String accountNumber;
	
	@Column(name = "TRAN_LIMIT_ID")
	private BigDecimal tranLimitId;
	
	@Column(name = "APPROVER_LEVEL_ID")
	private BigDecimal approverLevelId;
	
	@Column(name = "APPROVER_MASTER_ID")
	private BigDecimal approverMasterId;
	
	@Column(name = "HIERARCHY_MASTER_ID")
	private BigDecimal hierarchyMasterId;
		
	@Column(name = "CREATEDBY")
	private BigDecimal createdBy;
	
	@Column(name = "CREATEDON")
	private Date createdOn;

	@Column(name = "STATUSID")
	private BigDecimal statusId;
	
	@Column(name = "APPID")
	private BigDecimal appId;
	
	@Transient  
	private String approverMasterName;
	
	@Transient  
	private String hierarchyMasterName;
	


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

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public BigDecimal getTranLimitId() {
		return tranLimitId;
	}

	public void setTranLimitId(BigDecimal tranLimitId) {
		this.tranLimitId = tranLimitId;
	}

	public BigDecimal getApproverLevelId() {
		return approverLevelId;
	}

	public void setApproverLevelId(BigDecimal approverLevelId) {
		this.approverLevelId = approverLevelId;
	}

	public BigDecimal getApproverMasterId() {
		return approverMasterId;
	}

	public void setApproverMasterId(BigDecimal approverMasterId) {
		this.approverMasterId = approverMasterId;
	}

	public BigDecimal getHierarchyMasterId() {
		return hierarchyMasterId;
	}

	public void setHierarchyMasterId(BigDecimal hierarchyMasterId) {
		this.hierarchyMasterId = hierarchyMasterId;
	}

	public BigDecimal getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(BigDecimal createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
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

	public String getApproverMasterName() {
		return approverMasterName;
	}

	public void setApproverMasterName(String approverMasterName) {
		this.approverMasterName = approverMasterName;
	}

	public String getHierarchyMasterName() {
		return hierarchyMasterName;
	}

	public void setHierarchyMasterName(String hierarchyMasterName) {
		this.hierarchyMasterName = hierarchyMasterName;
	}
	
	

	
	
	
}
