package com.itl.pns.entity;

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
@Table(name = "ACCOUNTSCHEMEMASTER")
public class AccountSchemeMasterEntity {

	
	
	@javax.persistence.Id
	@Column(name = "ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "ACC_SCHEMEID_SEQ")
	@SequenceGenerator(name = "ACC_SCHEMEID_SEQ", sequenceName = "ACC_SCHEMEID_SEQ", allocationSize=1)
	BigDecimal id;

	@Column(name = "SCHEMETYPE")
	String schemetype;
	
	@Column(name = "SCHEMECODE")
	String schemecode;
	
		
	@Column(name = "SCHEMEDESCRIPTION")
	String schemedescription;
	
	@Column(name = "SCHEMEMAPPING")
	String schememapping;
	
	@Column(name = "CREATEDBY")
	BigDecimal createdby;
	
	@Column(name = "CREATEDON")
	Date createdon;
	
	
	@Column(name = "UPDATEDBY")
	BigDecimal updatedby;
	
	@Column(name = "UPDATEDON")
	Date updatedon;
	
	@Column(name = "STATUSID")
	BigDecimal statusid;
	

	
	@Transient
	String statusname;
	
	@Transient
	String createdbyname;
	
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
	String roleName;
	
	@Transient
	String action;
	

	@Transient
    private String productName;


	public BigDecimal getId() {
		return id;
	}


	public void setId(BigDecimal id) {
		this.id = id;
	}


	public String getSchemetype() {
		return schemetype;
	}


	public void setSchemetype(String schemetype) {
		this.schemetype = schemetype;
	}


	public String getSchemecode() {
		return schemecode;
	}


	public void setSchemecode(String schemecode) {
		this.schemecode = schemecode;
	}


	public String getSchemedescription() {
		return schemedescription;
	}


	public void setSchemedescription(String schemedescription) {
		this.schemedescription = schemedescription;
	}


	public String getSchememapping() {
		return schememapping;
	}


	public void setSchememapping(String schememapping) {
		this.schememapping = schememapping;
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


	public BigDecimal getStatusid() {
		return statusid;
	}


	public void setStatusid(BigDecimal statusid) {
		this.statusid = statusid;
	}


	public String getStatusname() {
		return statusname;
	}


	public void setStatusname(String statusname) {
		this.statusname = statusname;
	}


	public String getCreatedbyname() {
		return createdbyname;
	}


	public void setCreatedbyname(String createdbyname) {
		this.createdbyname = createdbyname;
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


	public String getProductName() {
		return productName;
	}


	public void setProductName(String productName) {
		this.productName = productName;
	}
	


	
	
}
