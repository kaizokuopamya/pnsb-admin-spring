package com.itl.pns.bean;

import java.math.BigDecimal;
import java.util.List;

import com.itl.pns.entity.BulkUsersCreationEntity;

public class BulkUserUploadBean {
	
	List<BulkUsersCreationEntity> bulkUsersCreation;
	
	private int successCount;
	
	private int errorCount;
	
	private String excelName;
	
	private String excelDate;
	
	private String excelStatus;
	
	private String excelRemark;
	
	private BigDecimal excelId;
	
	private int totalCount;
	

	public List<BulkUsersCreationEntity> getBulkUsersCreation() {
		return bulkUsersCreation;
	}

	public void setBulkUsersCreation(List<BulkUsersCreationEntity> bulkUsersCreation) {
		this.bulkUsersCreation = bulkUsersCreation;
	}

	public int getSuccessCount() {
		return successCount;
	}

	public void setSuccessCount(int successCount) {
		this.successCount = successCount;
	}

	public int getErrorCount() {
		return errorCount;
	}

	public void setErrorCount(int errorCount) {
		this.errorCount = errorCount;
	}

	public String getExcelName() {
		return excelName;
	}

	public void setExcelName(String excelName) {
		this.excelName = excelName;
	}

	public String getExcelDate() {
		return excelDate;
	}

	public void setExcelDate(String excelDate) {
		this.excelDate = excelDate;
	}

	public String getExcelStatus() {
		return excelStatus;
	}

	public void setExcelStatus(String excelStatus) {
		this.excelStatus = excelStatus;
	}

	public String getExcelRemark() {
		return excelRemark;
	}

	public void setExcelRemark(String excelRemark) {
		this.excelRemark = excelRemark;
	}

	public BigDecimal getExcelId() {
		return excelId;
	}

	public void setExcelId(BigDecimal excelId) {
		this.excelId = excelId;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	
	
	

}
