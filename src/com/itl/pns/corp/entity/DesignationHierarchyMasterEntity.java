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

/**
 * @author shubham.lokhande
 *
 */
@Entity
@Table(name = "DESIGNATION_HIERARCHY_MASTER")
public class DesignationHierarchyMasterEntity {

	
	@javax.persistence.Id
	@Column(name = "ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "designation_hierarchy_mast_SEQ")
	@SequenceGenerator(name = "designation_hierarchy_mast_SEQ", sequenceName = "designation_hierarchy_mast_SEQ", allocationSize=1)
	BigDecimal id;

	@Column(name = "DESIGNATIONNAME")    
	String  designationName;
	
	
	@Column(name = "DESIGNATIONCODE")    
	BigDecimal designationCode;
	
	@Column(name = "HIERARCHYLEVEL")    
	BigDecimal hierarchyLevel;
	
	
	@Column(name = "CREATEDON")    
	Date createdOn;
	
	@Column(name = "CREATEDBY")    
	BigDecimal createdBy;
	
	@Column(name = "UPDATEDON")    
	Date updatedOn;
	
	@Column(name = "UPDATEDBY")    
	BigDecimal updatedBy;
	
	@Column(name = "STATUSID")    
	BigDecimal statusId;
	
	@Column(name = "APPID")    
	BigDecimal appId;

	@Column(name = "COMPANYID")
	BigDecimal companyId;
	
	@Column(name = "AUTHTYPE")    
	String  authType;
	
	@Transient
    private String statusName;
  
	@Transient  
	private String action;

	@Transient  
	private String roleName;

	@Transient  
	private String createdByName;
	
	@Transient
	private BigDecimal user_ID;
	
	@Transient
	private BigDecimal subMenu_ID;
	
	@Transient
	private BigDecimal role_ID;
	
	@Transient
	private String remark;

	@Transient
	private String activityName;

	@Transient
	private BigDecimal userAction;
	
	@Transient
	private String companyName;

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getDesignationName() {
		return designationName;
	}

	public void setDesignationName(String designationName) {
		this.designationName = designationName;
	}

	public BigDecimal getDesignationCode() {
		return designationCode;
	}

	public void setDesignationCode(BigDecimal designationCode) {
		this.designationCode = designationCode;
	}

	public BigDecimal getHierarchyLevel() {
		return hierarchyLevel;
	}

	public void setHierarchyLevel(BigDecimal hierarchyLevel) {
		this.hierarchyLevel = hierarchyLevel;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public BigDecimal getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(BigDecimal createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public BigDecimal getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(BigDecimal updatedBy) {
		this.updatedBy = updatedBy;
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



	public BigDecimal getCompanyId() {
		return companyId;
	}

	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
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

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getAuthType() {
		return authType;
	}

	public void setAuthType(String authType) {
		this.authType = authType;
	}

}


