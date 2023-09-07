package com.itl.pns.bean;


import java.math.BigDecimal;
import java.sql.Date;

public class MerchantDetailsBean {
	
	private BigDecimal id;
	private String merchantCode;
	private String merchantName;
	private String merchantKey;
	private String merchantAccountNo;
	private BigDecimal statusId;
	private String glSubHead;
	private String checksumKey;
	private Date createdOn;
	private Date updatedOn;
	private BigDecimal createdBy;
	private BigDecimal updatedBy;
	private Date fromdate;
	private Date todate;
	
	public BigDecimal getId() {
		return id;
	}
	public void setId(BigDecimal id) {
		this.id = id;
	}
	public String getMerchantCode() {
		return merchantCode;
	}
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getMerchantKey() {
		return merchantKey;
	}
	public void setMerchantKey(String merchantKey) {
		this.merchantKey = merchantKey;
	}
	public String getMerchantAccountNo() {
		return merchantAccountNo;
	}
	public void setMerchantAccountNo(String merchantAccountNo) {
		this.merchantAccountNo = merchantAccountNo;
	}
	public BigDecimal getStatusId() {
		return statusId;
	}
	public void setStatusId(BigDecimal statusId) {
		this.statusId = statusId;
	}
	public String getGlSubHead() {
		return glSubHead;
	}
	public void setGlSubHead(String glSubHead) {
		this.glSubHead = glSubHead;
	}
	public String getChecksumKey() {
		return checksumKey;
	}
	public void setChecksumKey(String checksumKey) {
		this.checksumKey = checksumKey;
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
	public Date getFromdate() {
		return fromdate;
	}
	public void setFromdate(Date fromdate) {
		this.fromdate = fromdate;
	}
	public Date getTodate() {
		return todate;
	}
	public void setTodate(Date todate) {
		this.todate = todate;
	}
	
	


}
	
	
	