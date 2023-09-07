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

@Entity
@Table(name = "CORPACTIVITYSETTINGMASTER")
public class CorpActivitySettingMasterEntity {  

	@javax.persistence.Id
	@Column(name = "ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "corpactivitysettingmaster__SEQ")
	@SequenceGenerator(name = "corpactivitysettingmaster__SEQ", sequenceName = "corpactivitysettingmaster__SEQ", allocationSize=1)
	BigDecimal id;
	
	@Column(name = "ACTIVITYID")
	BigDecimal activityId;
	
	@Column(name = "COMPANYID")
	BigDecimal companyId;
	
	
	@Column(name = "CREATEDBY")
	BigDecimal createdBy;
	
	@Column(name = "CREATEDON")
	Date createdOn;
	
	@Column(name = "STATUSID")
	BigDecimal statusId;
	
	@Column(name = "MAKER")
	String maker;

	@Column(name = "CHECKER")
	String checker;
	
	@Column(name = "APPROVER")
	String approver;
	
	@Transient
	private String statusName;   

	@Transient
	private String activityName;

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

	public BigDecimal getCompanyId() {
		return companyId;
	}

	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
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

	public String getMaker() {
		return maker;
	}

	public void setMaker(String maker) {
		this.maker = maker;
	}

	public String getChecker() {
		return checker;
	}

	public void setChecker(String checker) {
		this.checker = checker;
	}

	public String getApprover() {
		return approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	} 
	
	
	
}
