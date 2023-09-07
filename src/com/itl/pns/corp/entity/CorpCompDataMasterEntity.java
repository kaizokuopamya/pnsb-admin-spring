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

/**
 * @author shubham.lokhande
 *
 */
@Entity
@Table(name = "CORP_COMP_DATA_MASTER")
public class CorpCompDataMasterEntity {

	@javax.persistence.Id
	@Column(name = "ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "corp_comp_data_master_id_SEQ")
	@SequenceGenerator(name = "corp_comp_data_master_id_SEQ", sequenceName = "corp_comp_data_master_id_SEQ", allocationSize=1)
	private BigDecimal id;

	@Column(name = "COMPANYNAME")
	private String companyName;

	@Column(name = "COMPANYCODE")
	private String companyCode;
	
	@Column(name = "COMPANYINFO")
	private String companyInfo;
	
	@Column(name = "CIF")
	private String cif;
	
	@Column(name = "RRN")
	private String rrn;  
	
	@Column(name = "COI")
	private String coi;
	
	@Column(name = "MOA")
	private String moa;
	
	@Column(name = "OTHERDOC")
	private String otherDoc;
	
	@Column(name = "PANCARDNO")
	private String PancardNo;

	@Column(name = "ESTABLISHMENTON")
	private Date establishmentOn;

	@Column(name = "LOGO")
	private String logo;
	
	@Column(name = "CORPORATETYPE")
	private String corporateType;
	
	@Column(name = "STATUSID")
	private BigDecimal statusId;

	@Column(name = "CREATEDON")
	private Date createdOn;

	@Column(name = "UPDATEDON")
	private Date updatedOn;

	@Column(name = "UPDATEDBY")
	private BigDecimal updatedBy;
	
	@Column(name = "PHONENO")
	private String phoneNo;
	
	@Transient
	private String reqApproved;
	
	@Transient
	private String statusName;

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getCompanyInfo() {
		return companyInfo;
	}

	public void setCompanyInfo(String companyInfo) {
		this.companyInfo = companyInfo;
	}

	public String getCif() {
		return cif;
	}

	public void setCif(String cif) {
		this.cif = cif;
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

	public String getPancardNo() {
		return PancardNo;
	}

	public void setPancardNo(String pancardNo) {
		PancardNo = pancardNo;
	}

	public Date getEstablishmentOn() {
		return establishmentOn;
	}

	public void setEstablishmentOn(Date establishmentOn) {
		this.establishmentOn = establishmentOn;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getCorporateType() {
		return corporateType;
	}

	public void setCorporateType(String corporateType) {
		this.corporateType = corporateType;
	}

	public BigDecimal getStatusId() {
		return statusId;
	}

	public void setStatusId(BigDecimal statusId) {
		this.statusId = statusId;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public BigDecimal getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(BigDecimal updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getReqApproved() {
		return reqApproved;
	}

	public void setReqApproved(String reqApproved) {
		this.reqApproved = reqApproved;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	

}
