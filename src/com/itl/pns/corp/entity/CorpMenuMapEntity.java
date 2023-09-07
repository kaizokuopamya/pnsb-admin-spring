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
@Table(name = "CORP_MENU_MAP")
public class CorpMenuMapEntity {

	public CorpMenuMapEntity() {
		super();
	}

	public CorpMenuMapEntity(CorpMenuMapEntity corpMenuMapEntity) {
		super();
		this.corpId = corpMenuMapEntity.corpId;
		this.corpMenuId = corpMenuMapEntity.corpMenuId;
		this.corpSubMenuId = corpMenuMapEntity.corpSubMenuId;
		this.statusId = corpMenuMapEntity.statusId;
		this.createdon = corpMenuMapEntity.createdon;
		this.updatedby = corpMenuMapEntity.updatedby;
	}

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
	@SequenceGenerator(name = "id_Sequence", sequenceName = "corp_menu_map_id_SEQ", allocationSize = 1)
	private BigDecimal id;

	@Column(name = "Corpid")
	private BigDecimal corpId;

	@Column(name = "CorpMenuid")
	private BigDecimal corpMenuId;

	@Column(name = "CorpSubMenuId")
	private BigDecimal corpSubMenuId;

	@Column(name = "Statusid")
	BigDecimal statusId;

	@Column(name = "Createdon", updatable = false)
	private Date createdon;

	@Column(name = "Updatedon")
	private Date updatedon;

	@Column(name = "Updatedby")
	private BigDecimal updatedby;

	@Transient
	private String menuName;

	@Transient
	private String subMenuName;

	@Transient
	private String approvalLevel;

	@Transient
	private String levelMaster;

	@Transient
	private BigDecimal[] corpSubMenuIdArray;

	public BigDecimal[] getCorpSubMenuIdArray() {
		return corpSubMenuIdArray;
	}

	public void setCorpSubMenuIdArray(BigDecimal[] corpSubMenuIdArray) {
		this.corpSubMenuIdArray = corpSubMenuIdArray;
	}

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

	public BigDecimal getCorpId() {
		return corpId;
	}

	public void setCorpId(BigDecimal corpId) {
		this.corpId = corpId;
	}

	public BigDecimal getCorpMenuId() {
		return corpMenuId;
	}

	public void setCorpMenuId(BigDecimal corpMenuId) {
		this.corpMenuId = corpMenuId;
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

	public String getApprovalLevel() {
		return approvalLevel;
	}

	public void setApprovalLevel(String approvalLevel) {
		this.approvalLevel = approvalLevel;
	}

	public String getLevelMaster() {
		return levelMaster;
	}

	public void setLevelMaster(String levelMaster) {
		this.levelMaster = levelMaster;
	}

	public BigDecimal getCorpSubMenuId() {
		return corpSubMenuId;
	}

	public void setCorpSubMenuId(BigDecimal corpSubMenuId) {
		this.corpSubMenuId = corpSubMenuId;
	}

	@Override
	public String toString() {
		return "CorpMenuMapEntity [id=" + id + ", corpId=" + corpId + ", corpMenuId=" + corpMenuId + ", corpSubMenuId="
				+ corpSubMenuId + ", statusId=" + statusId + ", createdon=" + createdon + ", updatedon=" + updatedon
				+ ", updatedby=" + updatedby + ", menuName=" + menuName + ", approvalLevel=" + approvalLevel
				+ ", levelMaster=" + levelMaster + "]";
	}

}
