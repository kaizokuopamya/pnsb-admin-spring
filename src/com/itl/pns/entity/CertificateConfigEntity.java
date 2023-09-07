package com.itl.pns.entity;

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

@Entity
@Table(name = "CERTIFICATE_CONFIGS")
public class CertificateConfigEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "CERTIFICATE_CONFIGS_SEQ")
	@SequenceGenerator(name = "CERTIFICATE_CONFIGS_SEQ", sequenceName = "CERTIFICATE_CONFIGS_SEQ", allocationSize=1)
	BigDecimal id;

	
	@Column(name = "CERTIFICATETYPE")
	String certificatetype;

	@Column(name = "CONFIGVALUE")
	String configvalue;
	
	
	@Column(name = "DESCRIPTION")
	String description;
	
	
	@Column(name = "CREATEDBY")
	BigDecimal createdby;
	
	@Column(name = "CREATEDON")
	Date createdon;
	
	
	@Column(name = "STATUSID")
	BigDecimal statusid;
	
	@Column(name = "APPID")
	BigDecimal appid;
	
	@Column(name = "SRNO")
	BigDecimal srno;
	
	
	@Transient
	String appname;
	
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

	public String getCertificatetype() {
		return certificatetype;
	}

	public void setCertificatetype(String certificatetype) {
		this.certificatetype = certificatetype;
	}

	public String getConfigvalue() {
		return configvalue;
	}

	public void setConfigvalue(String configvalue) {
		this.configvalue = configvalue;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public BigDecimal getStatusid() {
		return statusid;
	}

	public void setStatusid(BigDecimal statusid) {
		this.statusid = statusid;
	}

	public BigDecimal getAppid() {
		return appid;
	}

	public void setAppid(BigDecimal appid) {
		this.appid = appid;
	}

	public BigDecimal getSrno() {
		return srno;
	}

	public void setSrno(BigDecimal srno) {
		this.srno = srno;
	}

	public String getAppname() {
		return appname;
	}

	public void setAppname(String appname) {
		this.appname = appname;
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
