package com.itl.pns.entity;

import java.math.BigDecimal;
import java.sql.Clob;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "ADMINWORKFLOWREQUEST")
public class AdminWorkFlowRequestEntity {

	@javax.persistence.Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "adminworkflowrequest_id_SEQ")
	@SequenceGenerator(name = "adminworkflowrequest_id_SEQ", sequenceName = "adminworkflowrequest_id_SEQ", allocationSize = 1)
	private BigDecimal id;

	@Column(name = "APPID")
	private BigDecimal appId;

	@Column(name = "CREATEDBYUSERID")
	private BigDecimal createdByUserId;

	@Column(name = "PENDINGWITHUSERID")
	private BigDecimal pendingWithUserId;

	@Column(name = "CREATEDBYROLEID")
	private BigDecimal createdByRoleId;

	@Column(name = "PENDINGWITHROLEID")
	private BigDecimal pendingWithRoleId;

	@Column(name = "PAGEID")
	private BigDecimal pageId;

	@Column(name = "ACTIVITYID")
	private BigDecimal activityId;

	@Column(name = "ACTIVITYNAME")
	private String activityName;

	@Column(name = "ACTIVITYREFNO")
	private BigDecimal activityRefNo;

	@Column(name = "CREATEDON")
	private Date createdOn;

	@Column(name = "UPDATEDON")
	private Date updatedOn;

	@Column(name = "CREATEDBY")
	private BigDecimal createdBy;

	@Column(name = "UPDATEDBY")
	private BigDecimal updatedBy;

	@Column(name = "CONTENT")
	private String content;

	@Column(name = "STATUSID")
	private BigDecimal statusId;

	@Column(name = "REMARK")
	private String remark;

	@Column(name = "TABLENAME")
	private String tableName;

	@Column(name = "USERACTION")
	private BigDecimal userAction;

	@Column(name = "BRANCH_CODE")
	private String branchCode;

	@Transient
	private String statusName;

	@Transient
	private String type;

	@Transient
	private BigDecimal adminWorkFlowId;

	@Transient
	private List<String> contentList;

	@Transient
	private Clob contentClob;

	@Transient
	private String appName;

	@Transient
	private BigDecimal role_ID;

	@Transient
	private BigDecimal subMenu_ID;

	@Transient
	private String created_By;

	@Transient
	private String updated_By;

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getCreated_By() {
		return created_By;
	}

	public void setCreated_By(String created_By) {
		this.created_By = created_By;
	}

	public String getUpdated_By() {
		return updated_By;
	}

	public void setUpdated_By(String updated_By) {
		this.updated_By = updated_By;
	}

	public BigDecimal getSubMenu_ID() {
		return subMenu_ID;
	}

	public void setSubMenu_ID(BigDecimal subMenu_ID) {
		this.subMenu_ID = subMenu_ID;
	}

	public BigDecimal getRole_ID() {
		return role_ID;
	}

	public void setRole_ID(BigDecimal role_ID) {
		this.role_ID = role_ID;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public BigDecimal getAppId() {
		return appId;
	}

	public void setAppId(BigDecimal appId) {
		this.appId = appId;
	}

	public BigDecimal getCreatedByUserId() {
		return createdByUserId;
	}

	public void setCreatedByUserId(BigDecimal createdByUserId) {
		this.createdByUserId = createdByUserId;
	}

	public BigDecimal getPendingWithUserId() {
		return pendingWithUserId;
	}

	public void setPendingWithUserId(BigDecimal pendingWithUserId) {
		this.pendingWithUserId = pendingWithUserId;
	}

	public BigDecimal getCreatedByRoleId() {
		return createdByRoleId;
	}

	public void setCreatedByRoleId(BigDecimal createdByRoleId) {
		this.createdByRoleId = createdByRoleId;
	}

	public BigDecimal getPendingWithRoleId() {
		return pendingWithRoleId;
	}

	public void setPendingWithRoleId(BigDecimal pendingWithRoleId) {
		this.pendingWithRoleId = pendingWithRoleId;
	}

	public BigDecimal getPageId() {
		return pageId;
	}

	public void setPageId(BigDecimal pageId) {
		this.pageId = pageId;
	}

	public BigDecimal getActivityId() {
		return activityId;
	}

	public void setActivityId(BigDecimal activityId) {
		this.activityId = activityId;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public BigDecimal getActivityRefNo() {
		return activityRefNo;
	}

	public void setActivityRefNo(BigDecimal activityRefNo) {
		this.activityRefNo = activityRefNo;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public BigDecimal getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(BigDecimal createdBy) {
		this.createdBy = createdBy;
	}

	public BigDecimal getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(BigDecimal updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public BigDecimal getStatusId() {
		return statusId;
	}

	public void setStatusId(BigDecimal statusId) {
		this.statusId = statusId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public BigDecimal getUserAction() {
		return userAction;
	}

	public void setUserAction(BigDecimal userAction) {
		this.userAction = userAction;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<String> getContentList() {
		return contentList;
	}

	public void setContentList(List<String> contentList) {
		this.contentList = contentList;
	}

	public BigDecimal getAdminWorkFlowId() {
		return adminWorkFlowId;
	}

	public void setAdminWorkFlowId(BigDecimal adminWorkFlowId) {
		this.adminWorkFlowId = adminWorkFlowId;
	}

	public Clob getContentClob() {
		return contentClob;
	}

	public void setContentClob(Clob contentClob) {
		this.contentClob = contentClob;
	}

}
