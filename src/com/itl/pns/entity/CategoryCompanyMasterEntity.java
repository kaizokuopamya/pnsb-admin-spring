package com.itl.pns.entity;

import java.math.BigDecimal;
import java.sql.Clob;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "CATEGORY_COMP_MASTER")
public class CategoryCompanyMasterEntity {

	@javax.persistence.Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
	@SequenceGenerator(name = "id_Sequence", sequenceName = "category_comp_master_id_SEQ", allocationSize = 1)
	private BigDecimal id;

	@Column(name = "COMPANYNAME")
	private String companyName;

	@Column(name = "CATEGORYID")
	private BigDecimal categoryId;

	@Column(name = "LOGO")
	private Clob cloblogo;

	@Column(name = "COMPANYINFO")
	private String companyInfo;

	@Column(name = "CREATEDBY")
	private BigDecimal createdby;

	@Column(name = "CREATEDON")
	private Date createdon;

	@Column(name = "UPDATEDBY")
	private BigDecimal updatedby;

	@Column(name = "UPDATEDON")
	private Date updatedon;

	@Column(name = "STATUSID")
	private BigDecimal statusId;

	@Transient
	private String statusName;

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
	private String categoryName;

	@Transient
	private String logo;

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public BigDecimal getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(BigDecimal categoryId) {
		this.categoryId = categoryId;
	}

	public String getCompanyInfo() {
		return companyInfo;
	}

	public void setCompanyInfo(String companyInfo) {
		this.companyInfo = companyInfo;
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

	public BigDecimal getUpdatedby() {
		return updatedby;
	}

	public void setUpdatedby(BigDecimal updatedby) {
		this.updatedby = updatedby;
	}

	public Date getUpdatedon() {
		return updatedon;
	}

	public void setUpdatedon(Date updatedon) {
		this.updatedon = updatedon;
	}

	public BigDecimal getStatusId() {
		return statusId;
	}

	public void setStatusId(BigDecimal statusId) {
		this.statusId = statusId;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
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

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Clob getCloblogo() {
		return cloblogo;
	}

	public void setCloblogo(Clob cloblogo) {
		this.cloblogo = cloblogo;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

}
