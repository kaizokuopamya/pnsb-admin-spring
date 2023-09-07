package com.itl.pns.entity;

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
@Table(name = "MASKINGRULES")
public class MaskingRulesEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
	@SequenceGenerator(name = "id_Sequence", sequenceName = "maskingrules_id_SEQ", allocationSize = 1)
	private BigDecimal id;

	@Column(name = "STATUSID")
	private BigDecimal statusid;

	@Column(name = "RULENAMEDESC")
	private String rulenamedesc;

	@Column(name = "MASKLASTDIGITS")
	private BigDecimal masklastdigits;

	@Column(name = "MASKFIRSTDIGITS")
	private BigDecimal maskfirstdigits;

	@Column(name = "MASKCHAR")
	private char maskchar;

	@Column(name = "MASKALL_YN")
	private char maskall_yn;

	@Column(name = "CREATEDON")
	private Date createdon;

	@Column(name = "CREATEDBY")
	private BigDecimal createdby;

	@Column(name = "FIELDNAME")
	private String fieldname;

	@Column(name = "APPID")
	private BigDecimal appid;

	@Transient
	private String statusname;

	@Transient
	private String appname;

	@Transient
	private String createdByName;

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

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public BigDecimal getStatusid() {
		return statusid;
	}

	public void setStatusid(BigDecimal statusid) {
		this.statusid = statusid;
	}

	public String getRulenamedesc() {
		return rulenamedesc;
	}

	public void setRulenamedesc(String rulenamedesc) {
		this.rulenamedesc = rulenamedesc;
	}

	public BigDecimal getMasklastdigits() {
		return masklastdigits;
	}

	public void setMasklastdigits(BigDecimal masklastdigits) {
		this.masklastdigits = masklastdigits;
	}

	public BigDecimal getMaskfirstdigits() {
		return maskfirstdigits;
	}

	public void setMaskfirstdigits(BigDecimal maskfirstdigits) {
		this.maskfirstdigits = maskfirstdigits;
	}

	public char getMaskchar() {
		return maskchar;
	}

	public void setMaskchar(char maskchar) {
		this.maskchar = maskchar;
	}

	public char getMaskall_yn() {
		return maskall_yn;
	}

	public void setMaskall_yn(char maskall_yn) {
		this.maskall_yn = maskall_yn;
	}

	public Date getCreatedon() {
		return createdon;
	}

	public void setCreatedon(Date createdon) {
		this.createdon = createdon;
	}

	public BigDecimal getCreatedby() {
		return createdby;
	}

	public void setCreatedby(BigDecimal createdby) {
		this.createdby = createdby;
	}

	public String getFieldname() {
		return fieldname;
	}

	public void setFieldname(String fieldname) {
		this.fieldname = fieldname;
	}

	public BigDecimal getAppid() {
		return appid;
	}

	public void setAppid(BigDecimal appid) {
		this.appid = appid;
	}

	public String getStatusname() {
		return statusname;
	}

	public void setStatusname(String statusname) {
		this.statusname = statusname;
	}

	public String getAppname() {
		return appname;
	}

	public void setAppname(String appname) {
		this.appname = appname;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
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

}
