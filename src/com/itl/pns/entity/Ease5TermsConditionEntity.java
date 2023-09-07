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
@Table(name = "EASE5_TERMS_CONDITION")
public class Ease5TermsConditionEntity {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EASE5_TERMS_CONDITION_SEQ")
	@SequenceGenerator(name = "EASE5_TERMS_CONDITION_SEQ", sequenceName = "EASE5_TERMS_CONDITION_SEQ", allocationSize = 1)
	private Integer id;

	@Column(name = "HEADER")
	private String header;

	@Column(name = "TERMS_CONDITION_1")
	private String termsCondition1;

	@Column(name = "TERMS_CONDITION_2")
	private String termsCondition2;

	@Column(name = "TERMS_CONDITION_3")
	private String termsCondition3;

	@Column(name = "TERMS_CONDITION_4")
	private String termsCondition4;

	@Column(name = "TERMS_CONDITION_5")
	private String termsCondition5;

	@Column(name = "TERMS_CONDITION_6")
	private String termsCondition6;

	@Column(name = "TERMS_CONDITION_7")
	private String termsCondition7;

	@Column(name = "TERMS_CONDITION_8")
	private String termsCondition8;

	@Column(name = "REDIRECTIONTYPE")
	private String redirectionType;

	@Column(name = "STATUSID")
	private BigDecimal statusId;

	@Column(name = "CREATEDBY")
	private BigDecimal createdBy;

	@Column(name = "CREATEDON")
	private Date createdOn;

	@Column(name = "APPID")
	private BigDecimal appId;

	@Transient
	private String isActiveAll;

	@Transient
	private String termsCondition;

	public String getTermsCondition() {
		return termsCondition;
	}

	public void setTermsCondition(String termsCondition) {
		this.termsCondition = termsCondition;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getTermsCondition1() {
		return termsCondition1;
	}

	public void setTermsCondition1(String termsCondition1) {
		this.termsCondition1 = termsCondition1;
	}

	public String getTermsCondition2() {
		return termsCondition2;
	}

	public void setTermsCondition2(String termsCondition2) {
		this.termsCondition2 = termsCondition2;
	}

	public String getTermsCondition3() {
		return termsCondition3;
	}

	public void setTermsCondition3(String termsCondition3) {
		this.termsCondition3 = termsCondition3;
	}

	public String getTermsCondition4() {
		return termsCondition4;
	}

	public void setTermsCondition4(String termsCondition4) {
		this.termsCondition4 = termsCondition4;
	}

	public String getTermsCondition5() {
		return termsCondition5;
	}

	public void setTermsCondition5(String termsCondition5) {
		this.termsCondition5 = termsCondition5;
	}

	public String getTermsCondition6() {
		return termsCondition6;
	}

	public void setTermsCondition6(String termsCondition6) {
		this.termsCondition6 = termsCondition6;
	}

	public String getTermsCondition7() {
		return termsCondition7;
	}

	public void setTermsCondition7(String termsCondition7) {
		this.termsCondition7 = termsCondition7;
	}

	public String getTermsCondition8() {
		return termsCondition8;
	}

	public void setTermsCondition8(String termsCondition8) {
		this.termsCondition8 = termsCondition8;
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

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public BigDecimal getAppId() {
		return appId;
	}

	public void setAppId(BigDecimal appId) {
		this.appId = appId;
	}

	public String getIsActiveAll() {
		return isActiveAll;
	}

	public void setIsActiveAll(String isActiveAll) {
		this.isActiveAll = isActiveAll;
	}

	public String getRedirectionType() {
		return redirectionType;
	}

	public void setRedirectionType(String redirectionType) {
		this.redirectionType = redirectionType;
	}

}
