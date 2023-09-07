package com.itl.pns.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.itl.pns.bean.DateBean;

@Entity
@Table(name = "REPORT_MASTER")
public class ReportMsterEntity {

	@javax.persistence.Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "report_master_ID_SEQ")
	@SequenceGenerator(name = "report_master_ID_SEQ", sequenceName = "report_master_ID_SEQ", allocationSize = 1)
	private BigDecimal id;

	@Column(name = "REPORT_NAME")
	private String reportName;

	@Column(name = "REPORT_DESCRIPTION")
	private String reportDescription;

	@Column(name = "STATUSID")
	private BigDecimal statusId;

	@Column(name = "CREATEDON")
	private Date createdOn;

	@Column(name = "UPDATEDON")
	private Date updatedOn;

	@Column(name = "CREATEDBY")
	private BigDecimal createdBy;

	@Column(name = "UPDATEDBY")
	private BigDecimal updatedBy;
	
	@Transient
	private  DateBean dateBean; 

	@Transient
	private String statusName;
	
	@Transient
	private BigDecimal subid;

	/*@Transient
	private String productName;

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
	private String roleName;

	@Transient
	private String action;*/
	@Transient
	String createdByName;

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getReportDescription() {
		return reportDescription;
	}

	public void setReportDescription(String reportDescription) {
		this.reportDescription = reportDescription;
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

	public BigDecimal getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(BigDecimal createdBy) {
		this.createdBy = createdBy;
	}

	public BigDecimal getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(BigDecimal updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}


	public DateBean getDateBean() {
		return dateBean;
	}

	public void setDateBean(DateBean dateBean) {
		this.dateBean = dateBean;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public BigDecimal getSubid() {
		return subid;
	}

	public void setSubid(BigDecimal subid) {
		this.subid = subid;
	}

	
}
