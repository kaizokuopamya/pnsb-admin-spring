package com.itl.pns.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "LIMITMASTER")
public class LimitMasterEntity {

	@javax.persistence.Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LIMITMASTER_ID_SEQ")
	@SequenceGenerator(name = "LIMITMASTER_ID_SEQ", sequenceName = "LIMITMASTER_ID_SEQ", allocationSize = 1)
	private BigDecimal id;
	
	@Column(name = "ACTIVITYID")
	private BigDecimal activityId;

	@Column(name = "APPID")
	private BigDecimal appId;

	@Column(name = "LIMIT_NAME")
	private String limitName;
	
	@Column(name = "CUSTOMERID")
	private BigDecimal customerId;

	@Column(name = "FREQUENCY")
	private String frequency;

	@Column(name = "MIN_VAL")
	private BigDecimal minimumValue;

	@Column(name = "MAX_VAL")
	private BigDecimal maximumValue;

	@Column(name = "CREATEDBY")
	private BigDecimal createdBy;

	@Column(name = "CREATEDON")
	private Date createdOn;

	@Column(name = "STATUSID")
	private BigDecimal statusId;

    @Column(name = "TYPE")
	private String type;
	
	@Column(name = "LIMITTYPE_G_C")
	private char limitType;

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public BigDecimal getActivityId() {
		return activityId;
	}

	public void setActivityId(BigDecimal activityId) {
		this.activityId = activityId;
	}

	public BigDecimal getAppId() {
		return appId;
	}

	public void setAppId(BigDecimal appId) {
		this.appId = appId;
	}

	public String getLimitName() {
		return limitName;
	}

	public void setLimitName(String limitName) {
		this.limitName = limitName;
	}

	public BigDecimal getCustomerId() {
		return customerId;
	}

	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public BigDecimal getMinimumValue() {
		return minimumValue;
	}

	public void setMinimumValue(BigDecimal minimumValue) {
		this.minimumValue = minimumValue;
	}

	public BigDecimal getMaximumValue() {
		return maximumValue;
	}

	public void setMaximumValue(BigDecimal maximumValue) {
		this.maximumValue = maximumValue;
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

	public BigDecimal getStatusId() {
		return statusId;
	}

	public void setStatusId(BigDecimal statusId) {
		this.statusId = statusId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public char getLimitType() {
		return limitType;
	}

	public void setLimitType(char limitType) {
		this.limitType = limitType;
	}
	
	
}
