package com.itl.pns.bean;

import java.math.BigDecimal;
import java.util.Date;

public class CorpUserMenuMapBean {

	private BigDecimal id;

	private BigDecimal corpCompId;

	private BigDecimal corpMenuId;

	private BigDecimal corpSubMenuId;

	private BigDecimal corpUserId;

	BigDecimal statusId;

	private Date createdon;

	private Date updatedon;

	private BigDecimal updatedby;

	private String userRrn;

	private String menuName;

	private String subMenuName;

	public String getSubMenuName() {
		return subMenuName;
	}

	public void setSubMenuName(String subMenuName) {
		this.subMenuName = subMenuName;
	}

	public BigDecimal getCorpSubMenuId() {
		return corpSubMenuId;
	}

	public void setCorpSubMenuId(BigDecimal corpSubMenuId) {
		this.corpSubMenuId = corpSubMenuId;
	}

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public BigDecimal getCorpCompId() {
		return corpCompId;
	}

	public void setCorpCompId(BigDecimal corpCompId) {
		this.corpCompId = corpCompId;
	}

	public BigDecimal getCorpMenuId() {
		return corpMenuId;
	}

	public void setCorpMenuId(BigDecimal corpMenuId) {
		this.corpMenuId = corpMenuId;
	}

	public BigDecimal getCorpUserId() {
		return corpUserId;
	}

	public void setCorpUserId(BigDecimal corpUserId) {
		this.corpUserId = corpUserId;
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

	public BigDecimal getUpdatedby() {
		return updatedby;
	}

	public void setUpdatedby(BigDecimal updatedby) {
		this.updatedby = updatedby;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getUserRrn() {
		return userRrn;
	}

	public void setUserRrn(String userRrn) {
		this.userRrn = userRrn;
	}

}
