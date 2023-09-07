package com.itl.pns.corp.entity;

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
@Table(name = "CORPUSER_MENU_MAP")
public class CorpUserMenuMapEntity {

	public CorpUserMenuMapEntity() {
		super();
	}

	public CorpUserMenuMapEntity(CorpUserMenuMapEntity corpUserMenuMapEntity) {
		this.corpCompId = corpUserMenuMapEntity.corpCompId;
		this.corpMenuId = corpUserMenuMapEntity.corpMenuId;
		this.corpSubMenuId = corpUserMenuMapEntity.corpSubMenuId;
		this.corpUserId = corpUserMenuMapEntity.corpUserId;
		this.statusId = corpUserMenuMapEntity.statusId;
		this.createdon = corpUserMenuMapEntity.createdon;
		this.updatedby = corpUserMenuMapEntity.updatedby;
	}

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
	@SequenceGenerator(name = "id_Sequence", sequenceName = "corpuser_menu_map_id_SEQ", allocationSize = 1)
	private BigDecimal id;

	@Column(name = "CorpCompId")
	private BigDecimal corpCompId;

	@Column(name = "CorpMenuId")
	private BigDecimal corpMenuId;

	@Column(name = "CorpSubMenuId")
	private BigDecimal corpSubMenuId;

	@Column(name = "CorpUserId")
	private BigDecimal corpUserId;

	@Column(name = "StatusId")
	BigDecimal statusId;

	@Column(name = "CreatedOn")
	private Date createdon;

	@Column(name = "UpdatedOn")
	private Date updatedon;

	@Column(name = "UpdatedBy")
	private BigDecimal updatedby;

	@Column(name = "UserRRN")
	private String userRrn;

	@Transient
	private String menuName;

	@Transient
	private String subMenuName;

	public String getSubMenuName() {
		return subMenuName;
	}

	public void setSubMenuName(String subMenuName) {
		this.subMenuName = subMenuName;
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

	public BigDecimal getCorpSubMenuId() {
		return corpSubMenuId;
	}

	public void setCorpSubMenuId(BigDecimal corpSubMenuId) {
		this.corpSubMenuId = corpSubMenuId;
	}

}
