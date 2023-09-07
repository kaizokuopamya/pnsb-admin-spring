package com.itl.pns.bean;

import java.math.BigDecimal;

public class MenuBean {

	private BigDecimal id;
	private String menuName;
	BigDecimal menuid;
	private String submenuName;
	BigDecimal submenuid;
	private String menuDescription;
	private String menuUrl;
	private String menuLogo;
	private BigDecimal isActive;
	private BigDecimal statusId;

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuDescription() {
		return menuDescription;
	}

	public void setMenuDescription(String menuDescription) {
		this.menuDescription = menuDescription;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public String getMenuLogo() {
		return menuLogo;
	}

	public void setMenuLogo(String menuLogo) {
		this.menuLogo = menuLogo;
	}

	public BigDecimal getIsActive() {
		return isActive;
	}

	public void setIsActive(BigDecimal isActive) {
		this.isActive = isActive;
	}

	public BigDecimal getMenuid() {
		return menuid;
	}

	public void setMenuid(BigDecimal menuid) {
		this.menuid = menuid;
	}

	public String getSubmenuName() {
		return submenuName;
	}

	public void setSubmenuName(String submenuName) {
		this.submenuName = submenuName;
	}

	public BigDecimal getSubmenuId() {
		return submenuid;
	}

	public void setSubmenuId(BigDecimal submenuId) {
		this.submenuid = submenuId;
	}

	public BigDecimal getStatusId() {
		return statusId;
	}

	public void setStatusId(BigDecimal statusId) {
		this.statusId = statusId;
	}

	@Override
	public String toString() {
		return "MenuBean [id=" + id + ", menuName=" + menuName + ", menuid=" + menuid + ", submenuName=" + submenuName
				+ ", submenuid=" + submenuid + ", menuDescription=" + menuDescription + ", menuUrl=" + menuUrl
				+ ", menuLogo=" + menuLogo + ", isActive=" + isActive + ", statusId=" + statusId + "]";
	}

}
