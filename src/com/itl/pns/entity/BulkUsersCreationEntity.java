package com.itl.pns.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name = "BULK_USERS_DETAILS")
public class BulkUsersCreationEntity {
	
	@javax.persistence.Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BULK_USERS_DETAILS_ID_SEQ")
	@SequenceGenerator(name = "BULK_USERS_DETAILS_ID_SEQ", sequenceName = "BULK_USERS_DETAILS_ID_SEQ", allocationSize = 1, initialValue =1)
	private BigDecimal id;
	
	@Column(name = "USER_ID")
	private String userId;
	
	@Column(name = "FIRST_NAME")
	private String firstName;
	
	@Column(name = "LAST_NAME")
	private String lastName;	
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "ROLE")
	private Integer role;	
	
	@Column(name = "MOBILE_NUMBER")
	private String mobileNumber;
	
	@Column(name = "BRANCH_CODE")
	private String branchCode;	
	
	@Column(name = "REPORTING_BRANCH")
	private String reportingBranch; 
	
	@Column(name = "BRANCH_NAME")
	private String branchName;
	
	@Column(name = "ACTION")
	private String action;
	
	@Column(name = "STATUS")
	private Integer status;
	
	@Column(name = "REMARK")
	private String remark;
	
	@Column(name = "CREATEDBY")
	private Integer createdby;
	
	@Column(name = "UPDATEBY")
	private Integer updateby;
	
	@Column(name = "CREATEDON")
	private Date createdon;
	
	@Column(name = "UPDATEON")
	private Date updateon;

	@Column(name = "EXCEL_ID")
	private Integer excelid;

	@Transient
	private String roles;
	
	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getReportingBranch() {
		return reportingBranch;
	}

	public void setReportingBranch(String reportingBranch) {
		this.reportingBranch = reportingBranch;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getCreatedby() {
		return createdby;
	}

	public void setCreatedby(Integer createdby) {
		this.createdby = createdby;
	}

	public Integer getUpdateby() {
		return updateby;
	}

	public void setUpdateby(Integer updateby) {
		this.updateby = updateby;
	}

	public Date getCreatedon() {
		return createdon;
	}

	public void setCreatedon(Date createdon) {
		this.createdon = createdon;
	}

	public Date getUpdateon() {
		return updateon;
	}

	public void setUpdateon(Date updateon) {
		this.updateon = updateon;
	}

	public Integer getExcelid() {
		return excelid;
	}

	public void setExcelid(Integer excelid) {
		this.excelid = excelid;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}
	
	

	
	
	
}
