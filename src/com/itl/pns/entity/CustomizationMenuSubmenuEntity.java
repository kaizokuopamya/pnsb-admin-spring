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
@Table(name = "CUSTOMIZATIONMENUSUBMENU")
public class CustomizationMenuSubmenuEntity {

	@javax.persistence.Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customizationmenusubmenu_I_SEQ")
	@SequenceGenerator(name = "customizationmenusubmenu_I_SEQ", sequenceName = "customizationmenusubmenu_I_SEQ", allocationSize = 1)
	int id;

	@Column(name = "CUSTOMIZEMENUID")
	BigInteger customizeMenuId;

	@Column(name = "CUSTOMIZESUBMENUID")
	BigInteger customizeSubmenuId;

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
	private BigInteger isActive;

	@Transient
	private BigInteger custMenuId;

	@Transient
	private BigInteger custSubmenuId;

	@Transient
	private String menuName;

	@Transient
	private String subMenuName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BigInteger getCustomizeMenuId() {
		return customizeMenuId;
	}

	public void setCustomizeMenuId(BigInteger customizeMenuId) {
		this.customizeMenuId = customizeMenuId;
	}

	public BigInteger getCustomizeSubmenuId() {
		return customizeSubmenuId;
	}

	public void setCustomizeSubmenuId(BigInteger customizeSubmenuId) {
		this.customizeSubmenuId = customizeSubmenuId;
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

	public BigInteger getAppId() {
		return appId;
	}

	public void setAppId(BigInteger appId) {
		this.appId = appId;
	}

	public BigInteger getIsActive() {
		return isActive;
	}

	public void setIsActive(BigInteger isActive) {
		this.isActive = isActive;
	}

	public BigInteger getCustMenuId() {
		return custMenuId;
	}

	public void setCustMenuId(BigInteger custMenuId) {
		this.custMenuId = custMenuId;
	}

	public BigInteger getCustSubmenuId() {
		return custSubmenuId;
	}

	public void setCustSubmenuId(BigInteger custSubmenuId) {
		this.custSubmenuId = custSubmenuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getSubMenuName() {
		return subMenuName;
	}

	public void setSubMenuName(String subMenuName) {
		this.subMenuName = subMenuName;
	}

}
