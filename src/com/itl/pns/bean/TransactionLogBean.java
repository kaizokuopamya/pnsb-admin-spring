package com.itl.pns.bean;

import java.math.BigDecimal;
import java.util.Date;

public class TransactionLogBean {
	


	private int count;
	private String date;
	
	private BigDecimal ID;
	private BigDecimal CREATEDBY;
	private String RRN;
	private String REQ_RES;
	private String DISPLAYNAME;
	private String SERVICEREFNO;
	private String MESSAGE1;
	private BigDecimal CUSTOMERID;
	private String CUSTOMERNAME;
	private String MESSAGE2;
	private String MESSAGE3;
	private String MOBILE;
	private String THIRDPARTYREFNO;
	private Date CREATEDON;
	private String SHORTNAME;
	private BigDecimal AMOUNT;
	private String createdByName;
	private String STATUS;

	public BigDecimal getAMOUNT() {
		return AMOUNT;
	}

	public void setAMOUNT(BigDecimal aMOUNT) {
		AMOUNT = aMOUNT;
	}

	public String getSHORTNAME() {
		return SHORTNAME;
	}

	public void setSHORTNAME(String sHORTNAME) {
		SHORTNAME = sHORTNAME;
	}

	public Date getCREATEDON() {
		return CREATEDON;
	}

	public void setCREATEDON(Date cREATEDON) {
		CREATEDON = cREATEDON;
	}

	public String getTHIRDPARTYREFNO() {
		return THIRDPARTYREFNO;
	}

	public void setTHIRDPARTYREFNO(String tHIRDPARTYREFNO) {
		THIRDPARTYREFNO = tHIRDPARTYREFNO;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public BigDecimal getID() {
		return ID;
	}

	public void setID(BigDecimal iD) {
		ID = iD;
	}

	public String getRRN() {
		return RRN;
	}

	public void setRRN(String rRN) {
		RRN = rRN;
	}

	public String getREQ_RES() {
		return REQ_RES;
	}

	public void setREQ_RES(String rEQ_RES) {
		REQ_RES = rEQ_RES;
	}

	public String getDISPLAYNAME() {
		return DISPLAYNAME;
	}

	public void setDISPLAYNAME(String dISPLAYNAME) {
		DISPLAYNAME = dISPLAYNAME;
	}

	public String getSERVICEREFNO() {
		return SERVICEREFNO;
	}

	public void setSERVICEREFNO(String sERVICEREFNO) {
		SERVICEREFNO = sERVICEREFNO;
	}

	public String getMESSAGE1() {
		return MESSAGE1;
	}

	public void setMESSAGE1(String mESSAGE1) {
		MESSAGE1 = mESSAGE1;
	}

	public BigDecimal getCUSTOMERID() {
		return CUSTOMERID;
	}

	public void setCUSTOMERID(BigDecimal cUSTOMERID) {
		CUSTOMERID = cUSTOMERID;
	}

	public String getMESSAGE2() {
		return MESSAGE2;
	}

	public void setMESSAGE2(String mESSAGE2) {
		MESSAGE2 = mESSAGE2;
	}

	public String getMESSAGE3() {
		return MESSAGE3;
	}

	public void setMESSAGE3(String mESSAGE3) {
		MESSAGE3 = mESSAGE3;
	}

	public String getCUSTOMERNAME() {
		return CUSTOMERNAME;
	}

	public void setCUSTOMERNAME(String cUSTOMERNAME) {
		CUSTOMERNAME = cUSTOMERNAME;
	}

	public String getMOBILE() {
		return MOBILE;
	}

	public void setMOBILE(String mOBILE) {
		MOBILE = mOBILE;
	}

	public String getSTATUS() {
		return STATUS;
	}

	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}

	public BigDecimal getCREATEDBY() {
		return CREATEDBY;
	}

	public void setCREATEDBY(BigDecimal cREATEDBY) {
		CREATEDBY = cREATEDBY;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

}
