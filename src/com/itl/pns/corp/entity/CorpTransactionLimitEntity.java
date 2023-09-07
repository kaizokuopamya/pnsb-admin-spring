package com.itl.pns.corp.entity;

import java.math.BigDecimal;
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
@Table(name = "CORP_TRANSCATION_LIMITS")
public class CorpTransactionLimitEntity {

	@javax.persistence.Id
	@Column(name = "ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "corp_transcation_limits_ID_SEQ")
	@SequenceGenerator(name = "corp_transcation_limits_ID_SEQ", sequenceName = "corp_transcation_limits_ID_SEQ", allocationSize=1)
	private BigDecimal id;
	
	
	@Column(name = "CORPCOMPID")
	private BigDecimal corpCompId;
	
	
	@Column(name = "MINAMOUNT")
	private BigDecimal minAmount;
	
	@Column(name = "MAXAMOUNT")
	private BigDecimal maxAmount;
	
	@Column(name = "CURRENCY")
	private String currency;
	
	@Column(name = "CREATEDBY")
	private int createdBy;
	
	@Column(name = "CREATEDON")
	private Date createdOn;

	@Column(name = "STATUSID")
	private BigDecimal statusId;
	
	@Column(name = "APPID")
	private BigDecimal appId;
	
	@Transient
	private String type;

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



	public BigDecimal getMinAmount() {
		return minAmount;
	}

	public void setMinAmount(BigDecimal minAmount) {
		this.minAmount = minAmount;
	}

	public BigDecimal getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(BigDecimal maxAmount) {
		this.maxAmount = maxAmount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public BigDecimal getStatusId() {
		return statusId;
	}

	public void setStatusId(BigDecimal statusId) {
		this.statusId = statusId;
	}

	public BigDecimal getAppId() {
		return appId;
	}

	public void setAppId(BigDecimal appId) {
		this.appId = appId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	


}
