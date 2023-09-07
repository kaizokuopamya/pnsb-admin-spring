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
@Table(name = "BULK_USERS_EXCEL_DETAILS")
public class BulkUsersExcelDetailsEntity {
	@javax.persistence.Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BULK_USERS_EXCEL_DETAILS_ID_SEQ")
	@SequenceGenerator(name = "BULK_USERS_EXCEL_DETAILS_ID_SEQ", sequenceName = "BULK_USERS_EXCEL_DETAILS_ID_SEQ", allocationSize = 1, initialValue =1)
	private BigDecimal id;
	
	@Column(name = "EXCEL_NAME")
	private String excelname;
	
	@Column(name = "CREATEDBY")
	private Integer createdby;
	
	@Column(name = "UPDATEBY")
	private Integer updateby;
	
	@Column(name = "CREATEDON")
	private Date createdon;
	
	@Column(name = "UPDATEON")
	private Date updateon;
	
	@Column(name = "STATUS")
	private Integer status;
	
	@Column(name = "REMARK")
	private String remark;
	
	@Column(name = "SUCCESS_COUNT")
	private String successCount;
	
	@Column(name = "ERROR_COUNT")
	private String errorCount;
	
	@Column(name = "TOTAL_COUNT")
	private String totalCount;
	
	@Column(name = "BRANCHCODE")
	private String branchcode;
	
	
	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getExcelname() {
		return excelname;
	}

	public void setExcelname(String excelname) {
		this.excelname = excelname;
	}

	public Integer getCreatedby() {
		return createdby;
	}

	public void setCreatedby(Integer createdby) {
		this.createdby = createdby;
	}

	public Integer getUpdateby() {
		return updateby;
	}

	public void setUpdateby(Integer updateby) {
		this.updateby = updateby;
	}

	public Date getCreatedon() {
		return createdon;
	}

	public void setCreatedon(Date createdon) {
		this.createdon = createdon;
	}

	public Date getUpdateon() {
		return updateon;
	}

	public void setUpdateon(Date updateon) {
		this.updateon = updateon;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSuccessCount() {
		return successCount;
	}

	public void setSuccessCount(String successCount) {
		this.successCount = successCount;
	}

	public String getErrorCount() {
		return errorCount;
	}

	public void setErrorCount(String errorCount) {
		this.errorCount = errorCount;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public String getBranchcode() {
		return branchcode;
	}

	public void setBranchcode(String branchcode) {
		this.branchcode = branchcode;
	}
	
	
	
}
