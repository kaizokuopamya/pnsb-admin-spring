package com.itl.pns.corp.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author shubham.lokhande
 *
 */
@Entity
@Table(name = "CORP_COMPANY_MASTER")
public class CorpCompanyMasterNewEntity {

	@javax.persistence.Id
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

	@Column(name = "CREATEDBY")
	private BigDecimal createdBy;

	@Column(name = "CREATEDON")
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

	@Column(name = "APPROVALLEVEL")
	private String approvalLevel;

	@Column(name = "LEVELMASTER")
	private BigDecimal levelMaster;

	@Column(name = "APPID")
	private Long appId;

	@Column(name = "MAX_LIMIT")
	private BigDecimal maxLimit;

	@Column(name = "ADDRESS")
	private String address;

	@Column(name = "ADMIN_TYPES")
	private String adminTypes;

	@Column(name = "IS_CORPORATE")
	private Character isCorporate;

	@Column(name = "BRANCHCODE")
	private String branchCode;

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

	public String getPancardNo() {
		return pancardNo;
	}

	public void setPancardNo(String pancardNo) {
		this.pancardNo = pancardNo;
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

	public String getApprovalLevel() {
		return approvalLevel;
	}

	public void setApprovalLevel(String approvalLevel) {
		this.approvalLevel = approvalLevel;
	}

	public BigDecimal getLevelMaster() {
		return levelMaster;
	}

	public void setLevelMaster(BigDecimal levelMaster) {
		this.levelMaster = levelMaster;
	}

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public BigDecimal getMaxLimit() {
		return maxLimit;
	}

	public void setMaxLimit(BigDecimal maxLimit) {
		this.maxLimit = maxLimit;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAdminTypes() {
		return adminTypes;
	}

	public void setAdminTypes(String adminTypes) {
		this.adminTypes = adminTypes;
	}

	public Character getIsCorporate() {
		return isCorporate;
	}

	public void setIsCorporate(Character isCorporate) {
		this.isCorporate = isCorporate;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

}
