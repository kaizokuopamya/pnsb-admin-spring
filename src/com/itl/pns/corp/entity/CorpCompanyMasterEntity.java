package com.itl.pns.corp.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author shubham.lokhande
 *
 */
@Entity
@Table(name = "CORP_COMPANY_MASTER")
public class CorpCompanyMasterEntity {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "corp_company_master_id_SEQ")
	@SequenceGenerator(name = "corp_company_master_id_SEQ", sequenceName = "corp_company_master_id_SEQ", allocationSize = 1)
	private BigDecimal id;

	@Column(name = "COMPANYCODE")
	private String companyCode;

	@Column(name = "COMPANYNAME")
	private String companyName;

	@Column(name = "SHORTNAME")
	private String shortName;

	@Column(name = "COMPANYINFO")
	private String companyInfo;

	@Column(name = "ESTABLISHMENTON")
	private Timestamp establishmentOn;

	@Column(name = "LOGO")
	private String logo;

	@Column(name = "STATUSID")
	private BigDecimal statusId;

	@Column(name = "CREATEDBY", updatable = false)
	private BigDecimal createdBy;

	@Column(name = "CREATEDON", updatable = false)
	private Timestamp createdOn;

	@Column(name = "CIF")
	private String cif;

	@Column(name = "MAKER_LIMIT")
	private BigDecimal makerLimit;

	@Column(name = "CHECKER_LIMIT")
	private BigDecimal checkerLimit;

	@Column(name = "PHONENO")
	private String phoneNo;

	@Column(name = "RRN")
	private String rrn;

	@Column(name = "COI")
	private String coi;

	@Column(name = "MOA")
	private String moa;

	@Column(name = "OTHERDOC")
	private String otherDoc;

	@Column(name = "CORPORATETYPE")
	private String corporateType;

	@Column(name = "PANCARDNO")
	private String pancardNo;

	@Column(name = "UPDATEDON")
	private Timestamp updatedOn;

	@Column(name = "UPDATEDBY")
	private BigDecimal updatedBy;

	@Column(name = "ApprovalLevel")
	private String approvalLevel;

	@Column(name = "LevelMaster")
	private BigDecimal levelMaster;

	@Column(name = "APPID")
	private long appId;

	@Column(name = "MAX_LIMIT")
	private BigDecimal maxLimit;

	@Column(name = "ADDRESS")
	private String address;

	@Column(name = "ADMIN_TYPES")
	private String adminTypes;

	@Column(name = "IS_CORPORATE")
	private char isCorporate;

	@Column(name = "BRANCHCODE")
	private String branchCode;

	@Column(name = "OG_STATUS")
	private BigDecimal ogstatus;

	@Transient
	String loginId;

	@Transient
	String loginType;

	@Transient
	String appname;

	@Transient
	private String logoImage;

	@Transient
	private String statusName;

	@Transient
	private String productName;

	@Transient
	private String action;

	@Transient
	private String roleName;

	@Transient
	private String createdByName;

	@Transient
	private BigDecimal role_ID;

	@Transient
	private String remark;

	@Transient
	private String activityName;

	@Transient
	private BigDecimal userAction;

	@Transient
	private BigDecimal user_ID;

	@Transient
	private BigDecimal subMenu_ID;

	@Transient
	private char userTypes;

	@Transient
	private int multiUser;

	@Transient
	List<Integer> statusList;

	@Transient
	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAdminTypes() {
		return adminTypes;
	}

	public void setAdminTypes(String adminTypes) {
		this.adminTypes = adminTypes;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<Integer> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<Integer> statusList) {
		this.statusList = statusList;
	}

	public BigDecimal getMaxLimit() {
		return maxLimit;
	}

	public void setMaxLimit(BigDecimal maxLimit) {
		this.maxLimit = maxLimit;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	public char getUserTypes() {
		return userTypes;
	}

	public void setUserTypes(char userTypes) {
		this.userTypes = userTypes;
	}

	public int getMultiUser() {
		return multiUser;
	}

	public void setMultiUser(int multiUser) {
		this.multiUser = multiUser;
	}

	public BigDecimal getLevelMaster() {
		return levelMaster;
	}

	public void setLevelMaster(BigDecimal levelMaster) {
		this.levelMaster = levelMaster;
	}

	public long getAppId() {
		return appId;
	}

	public void setAppId(long appId) {
		this.appId = appId;
	}

	public String getPancardNo() {
		return pancardNo;
	}

	public void setPancardNo(String pancardNo) {
		this.pancardNo = pancardNo;
	}

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getCompanyInfo() {
		return companyInfo;
	}

	public void setCompanyInfo(String companyInfo) {
		this.companyInfo = companyInfo;
	}

	public Timestamp getEstablishmentOn() {
		return establishmentOn;
	}

	public void setEstablishmentOn(Timestamp establishmentOn) {
		this.establishmentOn = establishmentOn;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public BigDecimal getStatusId() {
		return statusId;
	}

	public void setStatusId(BigDecimal statusId) {
		this.statusId = statusId;
	}

	public BigDecimal getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(BigDecimal createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public String getCif() {
		return cif;
	}

	public void setCif(String cif) {
		this.cif = cif;
	}

	public BigDecimal getMakerLimit() {
		return makerLimit;
	}

	public void setMakerLimit(BigDecimal makerLimit) {
		this.makerLimit = makerLimit;
	}

	public BigDecimal getCheckerLimit() {
		return checkerLimit;
	}

	public void setCheckerLimit(BigDecimal checkerLimit) {
		this.checkerLimit = checkerLimit;
	}

	public String getAppname() {
		return appname;
	}

	public void setAppname(String appname) {
		this.appname = appname;
	}

	public String getLogoImage() {
		return logoImage;
	}

	public void setLogoImage(String logoImage) {
		this.logoImage = logoImage;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
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

	public String getApprovalLevel() {
		return approvalLevel;
	}

	public void setApprovalLevel(String approvalLevel) {
		this.approvalLevel = approvalLevel;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getRrn() {
		return rrn;
	}

	public void setRrn(String rrn) {
		this.rrn = rrn;
	}

	public String getCoi() {
		return coi;
	}

	public void setCoi(String coi) {
		this.coi = coi;
	}

	public String getMoa() {
		return moa;
	}

	public void setMoa(String moa) {
		this.moa = moa;
	}

	public String getOtherDoc() {
		return otherDoc;
	}

	public void setOtherDoc(String otherDoc) {
		this.otherDoc = otherDoc;
	}

	public String getCorporateType() {
		return corporateType;
	}

	public void setCorporateType(String corporateType) {
		this.corporateType = corporateType;
	}

	public Timestamp getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
	}

	public BigDecimal getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(BigDecimal updatedBy) {
		this.updatedBy = updatedBy;
	}

	public char getIsCorporate() {
		return isCorporate;
	}

	public void setIsCorporate(char isCorporate) {
		this.isCorporate = isCorporate;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public BigDecimal getOgstatus() {
		return ogstatus;
	}

	public void setOgstatus(BigDecimal ogstatus) {
		this.ogstatus = ogstatus;
	}

	@Override
	public String toString() {
		return "CorpCompanyMasterEntity [id=" + id + ", companyCode=" + companyCode + ", companyName=" + companyName
				+ ", shortName=" + shortName + ", companyInfo=" + companyInfo + ", establishmentOn=" + establishmentOn
				+ ", logo=" + logo + ", statusId=" + statusId + ", createdBy=" + createdBy + ", createdOn=" + createdOn
				+ ", cif=" + cif + ", makerLimit=" + makerLimit + ", checkerLimit=" + checkerLimit + ", phoneNo="
				+ phoneNo + ", rrn=" + rrn + ", coi=" + coi + ", moa=" + moa + ", otherDoc=" + otherDoc
				+ ", corporateType=" + corporateType + ", pancardNo=" + pancardNo + ", updatedOn=" + updatedOn
				+ ", updatedBy=" + updatedBy + ", approvalLevel=" + approvalLevel + ", levelMaster=" + levelMaster
				+ ", appId=" + appId + ", maxLimit=" + maxLimit + ", address=" + address + ", adminTypes=" + adminTypes
				+ ", isCorporate=" + isCorporate + ", branchCode=" + branchCode + ", ogstatus=" + ogstatus
				+ ", loginId=" + loginId + ", loginType=" + loginType + ", appname=" + appname + ", logoImage="
				+ logoImage + ", statusName=" + statusName + ", productName=" + productName + ", action=" + action
				+ ", roleName=" + roleName + ", createdByName=" + createdByName + ", role_ID=" + role_ID + ", remark="
				+ remark + ", activityName=" + activityName + ", userAction=" + userAction + ", user_ID=" + user_ID
				+ ", subMenu_ID=" + subMenu_ID + ", userTypes=" + userTypes + ", multiUser=" + multiUser
				+ ", statusList=" + statusList + ", userId=" + userId + "]";
	}

}
