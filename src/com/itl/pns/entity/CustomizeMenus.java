package com.itl.pns.entity;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "CUSTOMIZATIONMENUS")
public class CustomizeMenus {

	@javax.persistence.Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customizationmenus_id_SEQ")
	@SequenceGenerator(name = "customizationmenus_id_SEQ", sequenceName = "customizationmenus_id_SEQ", allocationSize = 1)
	private BigInteger id;

	@Column(name = "MODULENAME")
	private String moduleName;

	@Column(name = "APPID")
	private BigInteger appId;

	@Column(name = "STATUSID")
	private BigInteger statusId;

	@Column(name = "CREATEDBY")
	private BigInteger createdby;

	@Column(name = "CREATEDON")
	private Date createdOn;

	@Column(name = "ROLEID")
	private BigInteger roleId;

	@Column(name = "MENUIMAGE")
	private String menuImage;

	@Column(name = "JSONKEY")
	String json_key;

	@Column(name = "TYPE")
	private String type;

	@Column(name = "RIGHTS")
	String rights;

	@Column(name = "PAGEURL")
	private String pageurl;
	
	
	
	@Transient
	private BigInteger menuid;

	@Transient
	private String appName;

	@Transient
	private String menuName;

	@Transient
	private BigInteger custMenuId;

	@Transient
	private String menuImageString;

	@Transient
	private BigInteger isActive;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public BigInteger getAppId() {
		return appId;
	}

	public void setAppId(BigInteger appId) {
		this.appId = appId;
	}

	public BigInteger getStatusId() {
		return statusId;
	}

	public void setStatusId(BigInteger statusId) {
		this.statusId = statusId;
	}

	public BigInteger getCreatedby() {
		return createdby;
	}

	public void setCreatedby(BigInteger createdby) {
		this.createdby = createdby;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public BigInteger getRoleId() {
		return roleId;
	}

	public void setRoleId(BigInteger roleId) {
		this.roleId = roleId;
	}

	public String getMenuImage() {
		return menuImage;
	}

	public void setMenuImage(String menuImage) {
		this.menuImage = menuImage;
	}

	public String getMenuImageString() {
		return menuImageString;
	}

	public void setMenuImageString(String menuImageString) {
		this.menuImageString = menuImageString;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getJson_key() {
		return json_key;
	}

	public void setJson_key(String json_key) {
		this.json_key = json_key;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public BigInteger getMenuid() {
		return menuid;
	}

	public void setMenuid(BigInteger menuid) {
		this.menuid = menuid;
	}

	public BigInteger getIsActive() {
		return isActive;
	}

	public void setIsActive(BigInteger isActive) {
		this.isActive = isActive;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public BigInteger getCustMenuId() {
		return custMenuId;
	}

	public void setCustMenuId(BigInteger custMenuId) {
		this.custMenuId = custMenuId;
	}

	public String getRights() {
		return rights;
	}

	public void setRights(String rights) {
		this.rights = rights;
	}

	public String getPageurl() {
		return pageurl;
	}

	public void setPageurl(String pageurl) {
		this.pageurl = pageurl;
	}
	
	

}
