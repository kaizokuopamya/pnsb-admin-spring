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

@Entity
@Table(name = "SUBREPORT_DETAILS")
public class SubReportEntity {

	
	@javax.persistence.Id
	@Column(name = "ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "SUBREPORT_ID_SEQ")
	@SequenceGenerator(name = "SUBREPORT_ID_SEQ", sequenceName = "SUBREPORT_ID_SEQ", allocationSize=1)
	private BigDecimal id;

	@Column(name = "SUBREPORTNAME")
	private String subreportname;

	@Column(name = "SUBREPORTDESCRIPTION")
	private String subreportdescription;

	@Column(name = "REPORTID")
	private BigDecimal reportid;

	@Column(name = "statusid")
	private BigDecimal statusid;

	@Column(name = "createdby")
	private BigDecimal createdby;

	@Column(name = "createdon")
	private Date createdon;

	@Column(name = "updatedby")
	private BigDecimal updatedby;

	@Column(name = "updatedon")
	private Date updatedon;
	
	@Transient
	private String reportName;
	
	@Transient
	private String  createdByName;
	
	@Transient
	private String statusName;

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getSubreportname() {
		return subreportname;
	}

	public void setSubreportname(String subreportname) {
		this.subreportname = subreportname;
	}

	public String getSubreportdescription() {
		return subreportdescription;
	}

	public void setSubreportdescription(String subreportdescription) {
		this.subreportdescription = subreportdescription;
	}

	public BigDecimal getReportid() {
		return reportid;
	}

	public void setReportid(BigDecimal reportid) {
		this.reportid = reportid;
	}

	public BigDecimal getStatusid() {
		return statusid;
	}

	public void setStatusid(BigDecimal statusid) {
		this.statusid = statusid;
	}

	public BigDecimal getCreatedby() {
		return createdby;
	}

	public void setCreatedby(BigDecimal createdby) {
		this.createdby = createdby;
	}

	public Date getCreatedon() {
		return createdon;
	}

	public void setCreatedon(Date createdon) {
		this.createdon = createdon;
	}

	public BigDecimal getUpdatedby() {
		return updatedby;
	}

	public void setUpdatedby(BigDecimal updatedby) {
		this.updatedby = updatedby;
	}

	public Date getUpdatedon() {
		return updatedon;
	}

	public void setUpdatedon(Date updatedon) {
		this.updatedon = updatedon;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	

}
