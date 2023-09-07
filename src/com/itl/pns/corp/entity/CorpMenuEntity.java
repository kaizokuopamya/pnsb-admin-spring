package com.itl.pns.corp.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author shubham.lokhande
 *
 */
@Entity
@Table(name = "CORP_MENU")
public class CorpMenuEntity {

	@javax.persistence.Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "corp_menu_Id_SEQ")
	@SequenceGenerator(name = "corp_menu_Id_SEQ", sequenceName = "corp_menu_Id_SEQ", allocationSize = 1)
	private BigDecimal id;

	@Column(name = "MENUNAME")
	private String menuName;

	@Column(name = "CREATEDON")
	private Date createdon;

	@Column(name = "UPDATEDON")
	private Date updatedon;

	@Column(name = "CREATEDBY")
	private BigDecimal createdby;

	@Column(name = "UPDATEDBY")
	private BigDecimal updatedby;

	@Column(name = "STATUSID")
	private BigDecimal status;

	@Column(name = "MENULOGO")
	private String menuLogo;

	@Column(name = "MENULINK")
	private String menuLink;

	@Transient
	private String statusName;

	@Transient
	private String action;

	@Transient
	private String roleName;

	@Transient
	private String createdByName;

	@Transient
	private BigDecimal user_ID;

	@Transient
	private BigDecimal subMenu_ID;

	@Transient
	private BigDecimal role_ID;

	@Transient
	private String remark;

	@Transient
	private String activityName;

	@Transient
	private BigDecimal userAction;

	@Transient
	private BigDecimal statusId;

	@Transient
	private BigDecimal constitutionCode;

	@Transient
	private String approvalLevel;

	@Transient
	private String levelMaster;

	public String getLevelMaster() {
		return levelMaster;
	}

	public void setLevelMaster(String levelMaster) {
		this.levelMaster = levelMaster;
	}

	public String getApprovalLevel() {
		return approvalLevel;
	}

	public void setApprovalLevel(String approvalLevel) {
		this.approvalLevel = approvalLevel;
	}

	public BigDecimal getConstitutionCode() {
		return constitutionCode;
	}

	public void setConstitutionCode(BigDecimal constitutionCode) {
		this.constitutionCode = constitutionCode;
	}

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
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

	public BigDecimal getStatus() {
		return status;
	}

	public void setStatus(BigDecimal status) {
		this.status = status;
	}

	public String getMenuLogo() {
		return menuLogo;
	}

	public void setMenuLogo(String menuLogo) {
		this.menuLogo = menuLogo;
	}

	public String getMenuLink() {
		return menuLink;
	}

	public void setMenuLink(String menuLink) {
		this.menuLink = menuLink;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public BigDecimal getRole_ID() {
		return role_ID;
	}

	public void setRole_ID(BigDecimal role_ID) {
		this.role_ID = role_ID;
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

	public BigDecimal getUser_ID() {
		return user_ID;
	}

	public void setUser_ID(BigDecimal user_ID) {
		this.user_ID = user_ID;
	}

	public BigDecimal getSubMenu_ID() {
		return subMenu_ID;
	}

	public void setSubMenu_ID(BigDecimal subMenu_ID) {
		this.subMenu_ID = subMenu_ID;
	}

	public BigDecimal getStatusId() {
		return statusId;
	}

	public void setStatusId(BigDecimal statusId) {
		this.statusId = statusId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	@Override
	public String toString() {
		return "CorpMenuEntity [id=" + id + ", menuName=" + menuName + ", createdon=" + createdon + ", updatedon="
				+ updatedon + ", createdby=" + createdby + ", updatedby=" + updatedby + ", status=" + status
				+ ", menuLogo=" + menuLogo + ", menuLink=" + menuLink + ", statusName=" + statusName + ", action="
				+ action + ", roleName=" + roleName + ", createdByName=" + createdByName + ", user_ID=" + user_ID
				+ ", subMenu_ID=" + subMenu_ID + ", role_ID=" + role_ID + ", remark=" + remark + ", activityName="
				+ activityName + ", userAction=" + userAction + ", statusId=" + statusId + ", constitutionCode="
				+ constitutionCode + ", approvalLevel=" + approvalLevel + ", levelMaster=" + levelMaster + "]";
	}

}
