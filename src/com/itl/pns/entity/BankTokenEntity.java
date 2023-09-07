package com.itl.pns.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "BANKTOKEN")
public class BankTokenEntity {

	@javax.persistence.Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "id_Sequence", sequenceName = "banktoken_id_SEQ", allocationSize = 1)
	private BigDecimal id;

	@Column(name = "CUSTOMERID")
	private BigDecimal customerId;

	@Column(name = "STATUSID")
	private BigDecimal statusId;

	@Column(name = "REFERENCENUMBER")
	private BigDecimal referencenumber;

	@Column(name = "APPID")
	private BigDecimal appId;

	@Column(name = "REQINITIATEDFOR")
	private BigDecimal reqInitiatedFor;

	@Column(name = "ACCOUNTNUMBER")
	private String accountNumber;

	@Column(name = "TYPEOFREQUEST")
	private String typeOfRequest;

	@Column(name = "BRANCH_CODE")
	private String branchCode;

	@Column(name = "USER_ROLE")
	private String userRole;

	@Column(name = "CREATEDBY")
	private BigDecimal createdBy;

	@Column(name = "CREATEDON")
	private Timestamp createdOn;

	@Column(name = "UPDATEDBY")
	private BigDecimal updatedBy;

	@Column(name = "UPDATEDON")
	private Timestamp updatedOn;

	@Column(name = "REMARK")
	private String remarkGp;

	@Transient
	private Character isCorporate;

	@Transient
	private BigDecimal createdUpdatedBy;

	@Transient
	private String statusName;

	@Transient
	private String appName;

	@Transient
	private String calculatorname;

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
	private String customername;

	@Transient
	private String mobile;

	@Transient
	private String action;

	@Transient
	private String cif;

	@Transient
	private String companyName;

	@Transient
	private List<Integer> statusList;

	@Transient
	private String channel;

	@Transient
	private String approvalLevel;

	public String getApprovalLevel() {
		return approvalLevel;
	}

	public void setApprovalLevel(String approvalLevel) {
		this.approvalLevel = approvalLevel;
	}

	public Character getIsCorporate() {
		return isCorporate;
	}

	public void setIsCorporate(Character isCorporate) {
		this.isCorporate = isCorporate;
	}

	public BigDecimal getCreatedUpdatedBy() {
		return createdUpdatedBy;
	}

	public void setCreatedUpdatedBy(BigDecimal createdUpdatedBy) {
		this.createdUpdatedBy = createdUpdatedBy;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public BigDecimal getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(BigDecimal createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public BigDecimal getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(BigDecimal updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Timestamp getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public List<Integer> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<Integer> statusList) {
		this.statusList = statusList;
	}

	public String getTypeOfRequest() {
		return typeOfRequest;
	}

	public void setTypeOfRequest(String typeOfRequest) {
		this.typeOfRequest = typeOfRequest;
	}

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public BigDecimal getCustomerId() {
		return customerId;
	}

	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
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

	public BigDecimal getReferencenumber() {
		return referencenumber;
	}

	public void setReferencenumber(BigDecimal referencenumber) {
		this.referencenumber = referencenumber;
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

	public String getCalculatorname() {
		return calculatorname;
	}

	public void setCalculatorname(String calculatorname) {
		this.calculatorname = calculatorname;
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

	public String getCustomername() {
		return customername;
	}

	public void setCustomername(String customername) {
		this.customername = customername;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public BigDecimal getReqInitiatedFor() {
		return reqInitiatedFor;
	}

	public void setReqInitiatedFor(BigDecimal reqInitiatedFor) {
		this.reqInitiatedFor = reqInitiatedFor;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getCif() {
		return cif;
	}

	public void setCif(String cif) {
		this.cif = cif;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public String getRemarkGp() {
		return remarkGp;
	}

	public void setRemarkGp(String remarkGp) {
		this.remarkGp = remarkGp;
	}

	@Override
	public String toString() {
		return "BankTokenEntity [id=" + id + ", customerId=" + customerId + ", statusId=" + statusId
				+ ", referencenumber=" + referencenumber + ", appId=" + appId + ", reqInitiatedFor=" + reqInitiatedFor
				+ ", accountNumber=" + accountNumber + ", typeOfRequest=" + typeOfRequest + ", branchCode=" + branchCode
				+ ", userRole=" + userRole + ", createdBy=" + createdBy + ", createdOn=" + createdOn + ", updatedBy="
				+ updatedBy + ", updatedOn=" + updatedOn + ", remarkGp=" + remarkGp + ", isCorporate=" + isCorporate
				+ ", createdUpdatedBy=" + createdUpdatedBy + ", statusName=" + statusName + ", appName=" + appName
				+ ", calculatorname=" + calculatorname + ", createdByName=" + createdByName + ", user_ID=" + user_ID
				+ ", role_ID=" + role_ID + ", subMenu_ID=" + subMenu_ID + ", remark=" + remark + ", activityName="
				+ activityName + ", userAction=" + userAction + ", roleName=" + roleName + ", customername="
				+ customername + ", mobile=" + mobile + ", action=" + action + ", cif=" + cif + ", companyName="
				+ companyName + ", statusList=" + statusList + ", channel=" + channel + "]";
	}

}
