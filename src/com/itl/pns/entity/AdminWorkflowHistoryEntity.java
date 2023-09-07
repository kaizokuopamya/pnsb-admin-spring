package com.itl.pns.entity;

import java.math.BigDecimal;
import java.sql.Clob;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "ADMINWORKFLOWHISTORY")
public class AdminWorkflowHistoryEntity {

	@javax.persistence.Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "adminworkflowhistory_ID_SEQ")
	@SequenceGenerator(name = "adminworkflowhistory_ID_SEQ", sequenceName = "adminworkflowhistory_ID_SEQ", allocationSize = 1)
	private BigDecimal id;

	@Column(name = "PAGEID")
	private BigDecimal pageid;

	@Column(name = "REFERENCEID")
	private BigDecimal referenceid;

	@Column(name = "CREATEDBY")
	private BigDecimal createdby;

	@Column(name = "REMARKS")
	private String remarks;

	@Column(name = "CREATEDON")
	Date createdon;

	@Column(name = "CREATEDROLE")
	private BigDecimal createdrole;

	@Column(name = "CONTENT")
	private String content;

	@Column(name = "BRANCH_CODE")
	private String branchCode;

	@Transient
	private String createdByName;

	@Transient
	private Clob contentClob;

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getCreatedon() {
		return createdon;
	}

	public void setCreatedon(Date createdon) {
		this.createdon = createdon;
	}

	public BigDecimal getPageid() {
		return pageid;
	}

	public void setPageid(BigDecimal pageid) {
		this.pageid = pageid;
	}

	public BigDecimal getReferenceid() {
		return referenceid;
	}

	public void setReferenceid(BigDecimal referenceid) {
		this.referenceid = referenceid;
	}

	public BigDecimal getCreatedby() {
		return createdby;
	}

	public void setCreatedby(BigDecimal createdby) {
		this.createdby = createdby;
	}

	public BigDecimal getCreatedrole() {
		return createdrole;
	}

	public void setCreatedrole(BigDecimal createdrole) {
		this.createdrole = createdrole;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Clob getContentClob() {
		return contentClob;
	}

	public void setContentClob(Clob contentClob) {
		this.contentClob = contentClob;
	}

}
