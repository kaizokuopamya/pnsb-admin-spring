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
@Table(name = "CORP_COMP_MENU_MAPPING")
public class CorpCompanyMenuMappingEntity {

	@javax.persistence.Id
	@Column(name = "ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "corp_comp_menu_mapping_Id_SEQ")
	@SequenceGenerator(name = "corp_comp_menu_mapping_Id_SEQ", sequenceName = "corp_comp_menu_mapping_Id_SEQ", allocationSize=1)
	private BigDecimal id;

	@Column(name = "Company_Id")
	BigDecimal companyId;

	@Column(name = "Corp_Menu_Id")
	BigDecimal corpMenuId;

	@Column(name = "StatusId")
	BigDecimal statusId;
	
	
	@Column(name = "Createdon")
	private Date createdon;

	@Column(name = "Updatedon")
	private Date updatedon;

	@Column(name = "Createdby")
	private BigDecimal createdby;

	@Column(name = "UPDATEDBY")
	private BigDecimal updatedby;


	@Transient
	private String menuName;
	
	@Transient
	private String createdByName;
	
	@Transient
	private String statusName;
	    
	@Transient
	private String productName;

	@Transient  
	private String action;

	@Transient  
	private String roleName;

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
	
	@Transient
	private String menuSelected;
	
	@Transient
	private String companyName;
	
	

	
	
	public BigDecimal getId() {
		return id;
	}	

	public BigDecimal getCompanyId() {
		return companyId;
	}



	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}



	public BigDecimal getCorpMenuId() {
		return corpMenuId;
	}



	public void setCorpMenuId(BigDecimal corpMenuId) {
		this.corpMenuId = corpMenuId;
	}



	public BigDecimal getStatusId() {
		return statusId;
	}



	public void setStatusId(BigDecimal statusId) {
		this.statusId = statusId;
	}



	public Date getCreatedon() {
		return createdon;
	}

	public void setCreatedon(Date createdon) {
		this.createdon = createdon;
	}

	public Date getUpdatedon() {
		return updatedon;
	}

	public void setUpdatedon(Date updatedon) {
		this.updatedon = updatedon;
	}

	public BigDecimal getCreatedby() {
		return createdby;
	}

	public void setCreatedby(BigDecimal createdby) {
		this.createdby = createdby;
	}

	public BigDecimal getUpdatedby() {
		return updatedby;
	}

	public void setUpdatedby(BigDecimal updatedby) {
		this.updatedby = updatedby;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
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

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuSelected() {
		return menuSelected;
	}

	public void setMenuSelected(String menuSelected) {
		this.menuSelected = menuSelected;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}



	}
