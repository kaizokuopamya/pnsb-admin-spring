package com.itl.pns.bean;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "MONTHLY_REPORT_DATA")
public class MonthlyReportData {
	
	@Column(name = "ID")
	private BigDecimal id;
	
	@Column(name = "APPID")
	private BigDecimal appId;
	
	@Column(name="NUMBEROFRECORDS")
	private BigDecimal numberOfRecords;
	
	@Column(name = "SERVICE_NAME")
	private String service_Name;
	
	@Column(name = "CREATEDON")
	private Timestamp createdOn;
	
	@Column(name = "TXNAMOUNT")
	private BigDecimal txnAmount;
	
	@Column(name = "FROMDATE")
	private Timestamp fromDate;
	
	@Column(name = "TODATE")
	private Timestamp toDate;
	
	@Transient
	private String date1;

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public BigDecimal getAppId() {
		return appId;
	}

	public void setAppId(BigDecimal appId) {
		this.appId = appId;
	}

	public BigDecimal getNumberOfRecords() {
		return numberOfRecords;
	}

	public void setNumberOfRecords(BigDecimal numberOfRecords) {
		this.numberOfRecords = numberOfRecords;
	}

	public String getService_Name() {
		return service_Name;
	}

	public void setService_Name(String service_Name) {
		this.service_Name = service_Name;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public BigDecimal getTxnAmount() {
		return txnAmount;
	}

	public void setTxnAmount(BigDecimal txnAmount) {
		this.txnAmount = txnAmount;
	}

	public Timestamp getFromDate() {
		return fromDate;
	}

	public void setFromDate(Timestamp fromDate) {
		this.fromDate = fromDate;
	}

	public Timestamp getToDate() {
		return toDate;
	}

	public void setToDate(Timestamp toDate) {
		this.toDate = toDate;
	}

	
	public MonthlyReportData() {
		super();
	}

	public String getDate1() {
		return date1;
	}

	public void setDate1(String date1) {
		this.date1 = date1;
	}

	public MonthlyReportData(BigDecimal id, BigDecimal appId, BigDecimal numberOfRecords, String service_Name,
			Timestamp createdOn, BigDecimal txnAmount, Timestamp fromDate, Timestamp toDate, String date) {
		super();
		this.id = id;
		this.appId = appId;
		this.numberOfRecords = numberOfRecords;
		this.service_Name = service_Name;
		this.createdOn = createdOn;
		this.txnAmount = txnAmount;
		this.fromDate = fromDate;
		this.toDate = toDate;
	}

	@Override
	public String toString() {
		return "MonthlyReportData [id=" + id + ", appId=" + appId + ", numberOfRecords=" + numberOfRecords
				+ ", service_Name=" + service_Name + ", createdOn=" + createdOn + ", txnAmount=" + txnAmount
				+ ", fromDate=" + fromDate + ", toDate=" + toDate + ", date1=" + date1 + "]";
	}
}
