package com.itl.pns.bean;

import java.sql.Date;
public class InvalidLeasingDetailsBean {
	 private int ID;
	 public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getCOMPANY_ID() {
		return COMPANY_ID;
	}
	public void setCOMPANY_ID(String cOMPANY_ID) {
		COMPANY_ID = cOMPANY_ID;
	}
	public String getCUSTOMER_NAME() {
		return CUSTOMER_NAME;
	}
	public void setCUSTOMER_NAME(String cUSTOMER_NAME) {
		CUSTOMER_NAME = cUSTOMER_NAME;
	}
	public String getCONTRACT_NUMBER() {
		return CONTRACT_NUMBER;
	}
	public void setCONTRACT_NUMBER(String cONTRACT_NUMBER) {
		CONTRACT_NUMBER = cONTRACT_NUMBER;
	}
	public String getDUE_AMOUNT() {
		return DUE_AMOUNT;
	}
	public void setDUE_AMOUNT(String dUE_AMOUNT) {
		DUE_AMOUNT = dUE_AMOUNT;
	}
	public String getDUE_DATE() {
		return DUE_DATE;
	}
	public void setDUE_DATE(String dUE_DATE) {
		DUE_DATE = dUE_DATE;
	}
	public Date getUPLOADED_DATE() {
		return UPLOADED_DATE;
	}
	public void setUPLOADED_DATE(Date uPLOADED_DATE) {
		UPLOADED_DATE = uPLOADED_DATE;
	}
	private String COMPANY_ID;
	 private String CUSTOMER_NAME;
	 private String CONTRACT_NUMBER;
	 private String DUE_AMOUNT ;
	 private String DUE_DATE;
	 private Date UPLOADED_DATE;
	 private String remarks;
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
