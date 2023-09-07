package com.itl.pns.bean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class CorpTransLimitBean {

	private BigDecimal id;

	private BigDecimal corpCompId;

	private String currency;

	private int createdBy;

	private Date createdOn;

	private BigDecimal statusId;

	private BigDecimal appId;

	private BigDecimal transLimitId;

	private String accountNumber;

	private BigDecimal approverLimitId;

	private BigDecimal catmId;

	private BigDecimal corpUserId;

	List<CorpLimitDataBean> corpLimitData;

	List<List<CorpLimitDataBean>> corpLimitListData;

	private String statusName;

	private String productName;

	private String createdByName;

	private BigDecimal user_ID;

	private BigDecimal role_ID;

	private BigDecimal subMenu_ID;

	private String remark;

	private String activityName;

	private BigDecimal userAction;

	private String roleName;

	private String action;

	private BigDecimal adminWorkFlowId;

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public BigDecimal getCorpCompId() {
		return corpCompId;
	}

	public void setCorpCompId(BigDecimal corpCompId) {
		this.corpCompId = corpCompId;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
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

	public BigDecimal getTransLimitId() {
		return transLimitId;
	}

	public void setTransLimitId(BigDecimal transLimitId) {
		this.transLimitId = transLimitId;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public BigDecimal getApproverLimitId() {
		return approverLimitId;
	}

	public void setApproverLimitId(BigDecimal approverLimitId) {
		this.approverLimitId = approverLimitId;
	}

	public BigDecimal getCatmId() {
		return catmId;
	}

	public void setCatmId(BigDecimal catmId) {
		this.catmId = catmId;
	}

	public List<CorpLimitDataBean> getCorpLimitData() {
		return corpLimitData;
	}

	public void setCorpLimitData(List<CorpLimitDataBean> corpLimitData) {
		this.corpLimitData = corpLimitData;
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

	public List<List<CorpLimitDataBean>> getCorpLimitListData() {
		return corpLimitListData;
	}

	public void setCorpLimitListData(List<List<CorpLimitDataBean>> corpLimitListData) {
		this.corpLimitListData = corpLimitListData;
	}

	public BigDecimal getCorpUserId() {
		return corpUserId;
	}

	public void setCorpUserId(BigDecimal corpUserId) {
		this.corpUserId = corpUserId;
	}

	public BigDecimal getAdminWorkFlowId() {
		return adminWorkFlowId;
	}

	public void setAdminWorkFlowId(BigDecimal adminWorkFlowId) {
		this.adminWorkFlowId = adminWorkFlowId;
	}

}
