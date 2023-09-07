package com.itl.pns.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "MK_CK_AP_MAPPING")
public class MakerCheckerApproverMappingEntity {

	@javax.persistence.Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Mk_Ck_MAP_ID_SEQ")
	@SequenceGenerator(name = "Mk_Ck_MAP_ID_SEQ", sequenceName = "Mk_Ck_MAP_ID_SEQ", allocationSize = 1)
	private BigDecimal id;

	@Column(name = "MAKER_ID")
	private BigDecimal makerId;

	@Column(name = "CHECKER_ID")
	private BigDecimal checkerId;

	@Column(name = "APPROVER_ID")
	private BigDecimal approverId;

	@Column(name = "MAKER_NAME")
	private String makerName;

	@Column(name = "CHECKER_NAME")
	private String checkerName;

	@Column(name = "APPROVER_NAME")
	private String approverName;

	@Column(name = "MK_CK_AP_TYPE")
	private String makerCheckerApproverType;

	@Column(name = "STATUS_ID")
	private BigDecimal statusId;

	@Column(name = "CREATED_BY")
	private BigDecimal createdBy;

	@Column(name = "CREATED_ON")
	private Timestamp createdOn;

	@Column(name = "UPDATED_BY")
	private BigDecimal updatedBy;

	@Column(name = "UPDATED_ON")
	private Timestamp updatedOn;

	public BigDecimal getApproverId() {
		return approverId;
	}

	public void setApproverId(BigDecimal approverId) {
		this.approverId = approverId;
	}

	public String getMakerName() {
		return makerName;
	}

	public void setMakerName(String makerName) {
		this.makerName = makerName;
	}

	public String getCheckerName() {
		return checkerName;
	}

	public void setCheckerName(String checkerName) {
		this.checkerName = checkerName;
	}

	public String getApproverName() {
		return approverName;
	}

	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}

	public String getMakerCheckerApproverType() {
		return makerCheckerApproverType;
	}

	public void setMakerCheckerApproverType(String makerCheckerApproverType) {
		this.makerCheckerApproverType = makerCheckerApproverType;
	}

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public BigDecimal getMakerId() {
		return makerId;
	}

	public void setMakerId(BigDecimal makerId) {
		this.makerId = makerId;
	}

	public BigDecimal getCheckerId() {
		return checkerId;
	}

	public void setCheckerId(BigDecimal checkerId) {
		this.checkerId = checkerId;
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

	public BigDecimal getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(BigDecimal updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Timestamp getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
	}

}
