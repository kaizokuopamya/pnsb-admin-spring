package com.itl.pns.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
@Table(name = "THEMES")
public class ThemesEntity {

	@javax.persistence.Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "themes_id_SEQ")
	@SequenceGenerator(name = "themes_id_SEQ", sequenceName = "themes_id_SEQ", allocationSize = 1)
	private BigDecimal id;

	@Column(name = "THEMENAME")
	private String themeName;

	@Column(name = "THEMESIDEBARCOLOR")
	private String themeSideBarColor;

	@Column(name = "THEMESIDEBARBACKGROUND")
	private String themeSideBarBackground;

	@Column(name = "FROMDATE")
	private Date fromDate;

	@Column(name = "THEMEMENUOPTION")
	private String themeMenuOptions;

	@Column(name = "TODATE")
	private Date toDate;

	@Column(name = "ForcedToAll")
	private char forcedToAll;

	@Column(name = "statusid")
	private BigDecimal statusid;

	@Column(name = "createdby")
	private BigDecimal createdby;

	@Column(name = "createdon")
	private Date createdon;

	@Column(name = "updatedby")
	private BigDecimal updatedby;

	@Column(name = "updatedon")
	private Date updatedon;
	
	@Transient
	List<ThemesSubEntity> themesSubEntity;

	public BigDecimal getId() {
		return id;
	}

	@Transient
	private String createdByName;

	@Transient
	private String statusName;

	@Transient
	private BigDecimal user_ID;

	@Transient
	private BigDecimal role_ID;

	@Transient
	private BigDecimal subMenu_ID;

	@Transient
	private String remark;

	@Transient
	private String activityName;

	@Transient
	private BigDecimal userAction;

	@Transient
	private String action;

	@Transient
	private String roleName;

	@Transient
	private String fromDateStr;

	@Transient
	private String toDateStr;

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getThemeName() {
		return themeName;
	}

	public void setThemeName(String themeName) {
		this.themeName = themeName;
	}

	public String getThemeSideBarColor() {
		return themeSideBarColor;
	}

	public void setThemeSideBarColor(String themeSideBarColor) {
		this.themeSideBarColor = themeSideBarColor;
	}

	public String getThemeSideBarBackground() {
		return themeSideBarBackground;
	}

	public void setThemeSideBarBackground(String themeSideBarBackground) {
		this.themeSideBarBackground = themeSideBarBackground;
	}

	public String getThemeMenuOptions() {
		return themeMenuOptions;
	}

	public void setThemeMenuOptions(String themeMenuOptions) {
		this.themeMenuOptions = themeMenuOptions;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public char getForcedToAll() {
		return forcedToAll;
	}

	public void setForcedToAll(char forcedToAll) {
		this.forcedToAll = forcedToAll;
	}

	public BigDecimal getStatusid() {
		return statusid;
	}

	public void setStatusid(BigDecimal statusid) {
		this.statusid = statusid;
	}

	public BigDecimal getCreatedby() {
		return createdby;
	}

	public void setCreatedby(BigDecimal createdby) {
		this.createdby = createdby;
	}

	public Date getCreatedon() {
		return createdon;
	}

	public void setCreatedon(Date createdon) {
		this.createdon = createdon;
	}

	public BigDecimal getUpdatedby() {
		return updatedby;
	}

	public void setUpdatedby(BigDecimal updatedby) {
		this.updatedby = updatedby;
	}

	public Date getUpdatedon() {
		return updatedon;
	}

	public void setUpdatedon(Date updatedon) {
		this.updatedon = updatedon;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public BigDecimal getUser_ID() {
		return user_ID;
	}

	public void setUser_ID(BigDecimal user_ID) {
		this.user_ID = user_ID;
	}

	public BigDecimal getRole_ID() {
		return role_ID;
	}

	public void setRole_ID(BigDecimal role_ID) {
		this.role_ID = role_ID;
	}

	public BigDecimal getSubMenu_ID() {
		return subMenu_ID;
	}

	public void setSubMenu_ID(BigDecimal subMenu_ID) {
		this.subMenu_ID = subMenu_ID;
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

	public String getFromDateStr() {
		return fromDateStr;
	}

	public void setFromDateStr(String fromDateStr) {
		this.fromDateStr = fromDateStr;
	}

	public String getToDateStr() {
		return toDateStr;
	}

	public void setToDateStr(String toDateStr) {
		this.toDateStr = toDateStr;
	}

	public List<ThemesSubEntity> getThemesSubEntity() {
		return themesSubEntity;
	}

	public void setThemesSubEntity(List<ThemesSubEntity> themesSubEntity) {
		this.themesSubEntity = themesSubEntity;
	}

	
}
