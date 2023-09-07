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

/**
 * @author shubham.lokhande
 *
 */
@Entity
@Table(name = "CORP_APPROVER_MASTER")
public class CorpApproverMasterEntity {

	@javax.persistence.Id
	@Column(name = "ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "corp_approver_master_ID_SEQ")
	@SequenceGenerator(name = "corp_approver_master_ID_SEQ", sequenceName = "corp_approver_master_ID_SEQ", allocationSize=1)
	private BigDecimal id;
	
	
	@Column(name = "APPROVALTYPE")
	private String approverType;
	
	@Column(name = "CREATEDBY")
	private BigDecimal createdBy;
	
	@Column(name = "CREATEDON")
	private Date createdOn;

	@Column(name = "STATUSID")
	private BigDecimal statusId;
	
	@Column(name = "APPID")
	private BigDecimal appId;

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getApproverType() {
		return approverType;
	}

	public void setApproverType(String approverType) {
		this.approverType = approverType;
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

	public BigDecimal getAppId() {
		return appId;
	}

	public void setAppId(BigDecimal appId) {
		this.appId = appId;
	}
	
	

}
