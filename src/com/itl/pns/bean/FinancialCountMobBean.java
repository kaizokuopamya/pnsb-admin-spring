package com.itl.pns.bean;

import java.math.BigDecimal;
import java.math.BigInteger;

public class FinancialCountMobBean {

	private int ID;
	private Double COUNTDAILY;
	private String SHORTNAME;
	private String STATUS;
	private Integer statusid;
	private BigInteger customerid;
	private String fromdate;
	private String todate;
	private Double TotalCount;
	private BigDecimal total_txn_amount;
	private String biller;

	public BigInteger getCustomerid() {
		return customerid;
	}

	public void setCustomerid(BigInteger customerid) {
		this.customerid = customerid;
	}

	public String getBiller() {
		return biller;
	}

	public void setBiller(String biller) {
		this.biller = biller;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public BigDecimal getTotal_txn_amount() {
		return total_txn_amount;
	}

	public void setTotal_txn_amount(BigDecimal total_txn_amount) {
		this.total_txn_amount = total_txn_amount;
	}

	public Double getCOUNTDAILY() {
		return COUNTDAILY;
	}

	public void setCOUNTDAILY(Double cOUNTDAILY) {
		COUNTDAILY = cOUNTDAILY;
	}

	public String getSHORTNAME() {
		return SHORTNAME;
	}

	public void setSHORTNAME(String sHORTNAME) {
		SHORTNAME = sHORTNAME;
	}

	public String getSTATUS() {
		return STATUS;
	}

	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}

	public Integer getStatusid() {
		return statusid;
	}

	public void setStatusid(Integer statusid) {
		this.statusid = statusid;
	}

	public String getFromdate() {
		return fromdate;
	}

	public void setFromdate(String fromdate) {
		this.fromdate = fromdate;
	}

	public String getTodate() {
		return todate;
	}

	public void setTodate(String todate) {
		this.todate = todate;
	}

	public Double getTotalCount() {
		return TotalCount;
	}

	public void setTotalCount(Double totalCount) {
		TotalCount = totalCount;
	}

}
