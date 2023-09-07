package com.itl.pns.corp.entity;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="CORP_MENU_MAPPING")
public class CorpMenuMapping {
	
	@javax.persistence.Id
	@Column(name = "ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "corp_menu_mapping_ID_SEQ")
	@SequenceGenerator(name = "corp_menu_mapping_ID_SEQ", sequenceName = "corp_menu_mapping_ID_SEQ", allocationSize=1)
	BigDecimal id;

	@Column(name = "MENUID")
	BigDecimal menuId;

	@Column(name = "STATUSID")
	BigDecimal statusId;
	
	@Column(name = "CREATEDON")
	Date createdon;
	
	@Column(name = "UPDATEDON")
	Date updatedon;
	
	@Column(name = "CREATEDBY")
	BigDecimal createdby;
	
	@Column(name = "UPDATEDBY")
	BigDecimal updatedby;
	
	@Column(name = "ROLEID")
	BigDecimal roleid;
	
	@Column(name = "CORPORATECOMPID")
	BigDecimal corporatecompid;

	@Transient
	private String menuName;
	
	
	@Transient
	private String userType;
	
	
	@Transient
	private List<CorpCompanyMenuMappingEntity> corpoCompMenusList;
	
	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public BigDecimal getMenuId() {
		return menuId;
	}

	public void setMenuId(BigDecimal menuId) {
		this.menuId = menuId;
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

	public BigDecimal getRoleid() {
		return roleid;
	}

	public void setRoleid(BigDecimal roleid) {
		this.roleid = roleid;
	}

	public BigDecimal getCorporatecompid() {
		return corporatecompid;
	}

	public void setCorporatecompid(BigDecimal corporatecompid) {
		this.corporatecompid = corporatecompid;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public List<CorpCompanyMenuMappingEntity> getCorpoCompMenusList() {
		return corpoCompMenusList;
	}

	public void setCorpoCompMenusList(List<CorpCompanyMenuMappingEntity> corpoCompMenusList) {
		this.corpoCompMenusList = corpoCompMenusList;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}


}

