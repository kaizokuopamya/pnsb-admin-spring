package com.itl.pns.corp.entity;

import java.math.BigDecimal;
import java.sql.Date;

public class PgRequestEntity {
	
	private BigDecimal id;   
	
	private String uuid; 
	
	private String merchantName; 
	
	private String encData; 
	
	private Date createdOn; 
	
	private BigDecimal statusId;
	
	private Date fromdate;

	private Date todate;


	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getEncData() {
		return encData;
	}

	public void setEncData(String encData) {
		this.encData = encData;
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

	@Override
	public String toString() {
		return "PgRequestEntity [id=" + id + ", uuid=" + uuid + ", merchantName=" + merchantName + ", encData="
				+ encData + ", createdOn=" + createdOn + ", statusId=" + statusId + ", fromdate=" + fromdate
				+ ", todate=" + todate + "]";
	}
	
	

}
