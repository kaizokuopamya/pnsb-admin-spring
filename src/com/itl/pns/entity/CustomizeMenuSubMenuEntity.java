package com.itl.pns.entity;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "CUSTOMIZE_MENU_SUBMENU")
public class CustomizeMenuSubMenuEntity {

	@javax.persistence.Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customizationmenusubmenu_I_SEQ")
	@SequenceGenerator(name = "customizationmenusubmenu_I_SEQ", sequenceName = "customizationmenusubmenu_I_SEQ", allocationSize = 1)
	int id;

	@ManyToOne
	@JoinColumn(name = "CUSTOMIZEMENUID")
	CustomizeMenus customizeMenuId;

	@ManyToOne
	@JoinColumn(name = "CUSTOMIZESUBMENUID")
	CustomizeSubmenuEntity customizeSubmenuId;

	@Column(name = "BANKINGTYPE")
	String bankingType;

	@ManyToOne
	@JoinColumn(name = "STATUSID")
	StatusMasterEntity statusId;

	@Column(name = "CREATEDON")
	Date createdon;

	@Column(name = "UPDATEDON")
	Date updatedon;

	@Column(name = "CREATEDBY")
	int createdby;

	@Column(name = "UPDATEDBY")
	int updatedby;

	@Column(name = "APPID")
	BigInteger appId;

	@Transient
	private String menuname;

	@Transient
	private BigInteger CustMenuId;

	@Transient
	private String CustMenuName;

	@Transient
	private BigInteger isActive;

	@Transient
	private BigInteger custMenuId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public CustomizeMenus getCustomizeMenuId() {
		return customizeMenuId;
	}

	public void setCustomizeMenuId(CustomizeMenus customizeMenuId) {
		this.customizeMenuId = customizeMenuId;
	}

	public CustomizeSubmenuEntity getCustomizeSubmenuId() {
		return customizeSubmenuId;
	}

	public void setCustomizeSubmenuId(CustomizeSubmenuEntity customizeSubmenuId) {
		this.customizeSubmenuId = customizeSubmenuId;
	}

	public String getBankingType() {
		return bankingType;
	}

	public void setBankingType(String bankingType) {
		this.bankingType = bankingType;
	}

	public StatusMasterEntity getStatusId() {
		return statusId;
	}

	public void setStatusId(StatusMasterEntity statusId) {
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

	public int getCreatedby() {
		return createdby;
	}

	public void setCreatedby(int createdby) {
		this.createdby = createdby;
	}

	public int getUpdatedby() {
		return updatedby;
	}

	public void setUpdatedby(int updatedby) {
		this.updatedby = updatedby;
	}

	public String getMenuname() {
		return menuname;
	}

	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}

	public BigInteger getIsActive() {
		return isActive;
	}

	public void setIsActive(BigInteger isActive) {
		this.isActive = isActive;
	}

	public BigInteger getCustMenuId() {
		return CustMenuId;
	}

	public void setCustMenuId(BigInteger custMenuId) {
		CustMenuId = custMenuId;
	}

	public String getCustMenuName() {
		return CustMenuName;
	}

	public void setCustMenuName(String custMenuName) {
		CustMenuName = custMenuName;
	}

	public BigInteger getAppId() {
		return appId;
	}

	public void setAppId(BigInteger appId) {
		this.appId = appId;
	}

	@Override
	public String toString() {
		return "CustomizeMenuSubMenuEntity [id=" + id + ", customizeMenuId=" + customizeMenuId + ", customizeSubmenuId=" + customizeSubmenuId + ", bankingType=" + bankingType + ", statusId=" + statusId + ", createdon=" + createdon + ", updatedon=" + updatedon + ", createdby=" + createdby + ", updatedby=" + updatedby + ", appId=" + appId + ", menuname=" + menuname + ", CustMenuId=" + CustMenuId + ", CustMenuName="
				+ CustMenuName + ", isActive=" + isActive + ", custMenuId=" + custMenuId + ", getId()=" + getId() + ", getCustomizeMenuId()=" + getCustomizeMenuId() + ", getCustomizeSubmenuId()=" + getCustomizeSubmenuId() + ", getBankingType()=" + getBankingType() + ", getStatusId()=" + getStatusId() + ", getCreatedon()=" + getCreatedon() + ", getUpdatedon()=" + getUpdatedon() + ", getCreatedby()=" + getCreatedby()
				+ ", getUpdatedby()=" + getUpdatedby() + ", getMenuname()=" + getMenuname() + ", getIsActive()=" + getIsActive() + ", getCustMenuId()=" + getCustMenuId() + ", getCustMenuName()=" + getCustMenuName() + ", getAppId()=" + getAppId() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

}
