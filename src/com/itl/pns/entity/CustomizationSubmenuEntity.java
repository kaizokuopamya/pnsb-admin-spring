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
@Table(name = "CUSTOMIZATIONSUBMENUS")
public class CustomizationSubmenuEntity {

	@javax.persistence.Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customizationsubmenus_ID_SEQ")
	@SequenceGenerator(name = "customizationsubmenus_ID_SEQ", sequenceName = "customizationsubmenus_ID_SEQ", allocationSize = 1)
	BigInteger id;

	@Column(name = "SUBMENUNAME")
	String submenuName;

	@Column(name = "MENULOGO")
	String menuLogo;

	@Column(name = "JSONKEY")
	String jsonKey;

	@Column(name = "APPID")
	private BigInteger appId;

	@Column(name = "STATUSID")
	private BigInteger statusId;

	@Column(name = "CREATEDBY")
	private BigInteger createdby;

	@Column(name = "CREATEDON")
	private Date createdOn;

	@Column(name = "RIGHTS")
	private String rights;
	
	@Column(name = "PAGEURL")
	private String pageurl;
	
	@Column(name = "MENUIMAGE")
	private String menuimage;
	
	@Transient
	private String moduleName;
	
	@Transient
	private String statusName;

	@Transient
	private BigInteger submenuid;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getSubmenuName() {
		return submenuName;
	}

	public void setSubmenuName(String submenuName) {
		this.submenuName = submenuName;
	}

	public String getMenuLogo() {
		return menuLogo;
	}

	public void setMenuLogo(String menuLogo) {
		this.menuLogo = menuLogo;
	}

	public String getJsonKey() {
		return jsonKey;
	}

	public void setJsonKey(String jsonKey) {
		this.jsonKey = jsonKey;
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

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public BigInteger getSubmenuid() {
		return submenuid;
	}

	public void setSubmenuid(BigInteger submenuid) {
		this.submenuid = submenuid;
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

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getMenuimage() {
		return menuimage;
	}

	public void setMenuimage(String menuimage) {
		this.menuimage = menuimage;
	}
	
	

}
