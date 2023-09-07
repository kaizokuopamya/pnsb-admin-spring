package com.itl.pns.bean;

import java.math.BigDecimal;
import java.sql.Clob;
import java.util.Date;

public class TransactionBean {

	private BigDecimal id;

	private Date DATE_OF_TXN;

	private BigDecimal SENTCIF;

	private BigDecimal RECCIF;

	private char REQ_STATUS;

	private BigDecimal TXN_AMOUNT;

	private Clob REMARK;

	private String RemarkString;

	private BigDecimal USERDEVICEID;

	private char REQTYPE;

	private String RRNNO;

	private String CBSREFNO;

	private char READSTATUS;

	private char PAYSTATUS;

	private BigDecimal ACTIVITYID;

	private String BILLER;

	private BigDecimal STATUSID;

	private Date CREATEDON;

	private BigDecimal CREATEDBY;

	private BigDecimal APPID;

	private String STATUS;

	private String APPNAME;

	private String ACTIVITYCODE;

	private String MOBILE;

	private String SENDERCUST;

	private String CUSTOMERNAME;

	private String TRANSACTIONTYPE;

	private String FROMACCOUNT;

	private String TOACCOUNT;

	private String CIF;

	private String RECEIVERNM;

	private String SENDERNM;

	public String getSENDERNM() {
		return SENDERNM;
	}

	public void setSENDERNM(String sENDERNM) {
		SENDERNM = sENDERNM;
	}

	public String getRECEIVERNM() {
		return RECEIVERNM;
	}

	public void setRECEIVERNM(String rECEIVERNM) {
		RECEIVERNM = rECEIVERNM;
	}

	public String getCIF() {
		return CIF;
	}

	public void setCIF(String cIF) {
		CIF = cIF;
	}

	public String getFROMACCOUNT() {
		return FROMACCOUNT;
	}

	public void setFROMACCOUNT(String fROMACCOUNT) {
		FROMACCOUNT = fROMACCOUNT;
	}

	public String getTOACCOUNT() {
		return TOACCOUNT;
	}

	public void setTOACCOUNT(String tOACCOUNT) {
		TOACCOUNT = tOACCOUNT;
	}

	public BigDecimal getID() {
		return id;
	}

	public void setID(BigDecimal iD) {
		id = iD;
	}

	public Date getDATE_OF_TXN() {
		return DATE_OF_TXN;
	}

	public void setDATE_OF_TXN(Date dATE_OF_TXN) {
		DATE_OF_TXN = dATE_OF_TXN;
	}

	public BigDecimal getSENTCIF() {
		return SENTCIF;
	}

	public void setSENTCIF(BigDecimal sENTCIF) {
		SENTCIF = sENTCIF;
	}

	public BigDecimal getRECCIF() {
		return RECCIF;
	}

	public void setRECCIF(BigDecimal rECCIF) {
		RECCIF = rECCIF;
	}

	public char getREQ_STATUS() {
		return REQ_STATUS;
	}

	public void setREQ_STATUS(char rEQ_STATUS) {
		REQ_STATUS = rEQ_STATUS;
	}

	public BigDecimal getTXN_AMOUNT() {
		return TXN_AMOUNT;
	}

	public void setTXN_AMOUNT(BigDecimal tXN_AMOUNT) {
		TXN_AMOUNT = tXN_AMOUNT;
	}

	public Clob getREMARK() {
		return REMARK;
	}

	public void setREMARK(Clob rEMARK) {
		REMARK = rEMARK;
	}

	public BigDecimal getUSERDEVICEID() {
		return USERDEVICEID;
	}

	public void setUSERDEVICEID(BigDecimal uSERDEVICEID) {
		USERDEVICEID = uSERDEVICEID;
	}

	public char getREQTYPE() {
		return REQTYPE;
	}

	public void setREQTYPE(char rEQTYPE) {
		REQTYPE = rEQTYPE;
	}

	public String getRRNNO() {
		return RRNNO;
	}

	public void setRRNNO(String rRNNO) {
		RRNNO = rRNNO;
	}

	public String getCBSREFNO() {
		return CBSREFNO;
	}

	public void setCBSREFNO(String cBSREFNO) {
		CBSREFNO = cBSREFNO;
	}

	public char getREADSTATUS() {
		return READSTATUS;
	}

	public void setREADSTATUS(char rEADSTATUS) {
		READSTATUS = rEADSTATUS;
	}

	public char getPAYSTATUS() {
		return PAYSTATUS;
	}

	public void setPAYSTATUS(char pAYSTATUS) {
		PAYSTATUS = pAYSTATUS;
	}

	public BigDecimal getACTIVITYID() {
		return ACTIVITYID;
	}

	public void setACTIVITYID(BigDecimal aCTIVITYID) {
		ACTIVITYID = aCTIVITYID;
	}

	public String getBILLER() {
		return BILLER;
	}

	public void setBILLER(String bILLER) {
		BILLER = bILLER;
	}

	public BigDecimal getSTATUSID() {
		return STATUSID;
	}

	public void setSTATUSID(BigDecimal sTATUSID) {
		STATUSID = sTATUSID;
	}

	public Date getCREATEDON() {
		return CREATEDON;
	}

	public void setCREATEDON(Date cREATEDON) {
		CREATEDON = cREATEDON;
	}

	public BigDecimal getCREATEDBY() {
		return CREATEDBY;
	}

	public void setCREATEDBY(BigDecimal cREATEDBY) {
		CREATEDBY = cREATEDBY;
	}

	public BigDecimal getAPPID() {
		return APPID;
	}

	public void setAPPID(BigDecimal aPPID) {
		APPID = aPPID;
	}

	public String getSTATUS() {
		return STATUS;
	}

	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}

	public String getAPPNAME() {
		return APPNAME;
	}

	public void setAPPNAME(String aPPNAME) {
		APPNAME = aPPNAME;
	}

	public String getACTIVITYCODE() {
		return ACTIVITYCODE;
	}

	public void setACTIVITYCODE(String aCTIVITYCODE) {
		ACTIVITYCODE = aCTIVITYCODE;
	}

	public String getMOBILE() {
		return MOBILE;
	}

	public void setMOBILE(String mOBILE) {
		MOBILE = mOBILE;
	}

	public String getSENDERCUST() {
		return SENDERCUST;
	}

	public void setSENDERCUST(String sENDERCUST) {
		SENDERCUST = sENDERCUST;
	}

	public String getCUSTOMERNAME() {
		return CUSTOMERNAME;
	}

	public void setCUSTOMERNAME(String cUSTOMERNAME) {
		CUSTOMERNAME = cUSTOMERNAME;
	}

	public String getTRANSACTIONTYPE() {
		return TRANSACTIONTYPE;
	}

	public void setTRANSACTIONTYPE(String tRANSACTIONTYPE) {
		TRANSACTIONTYPE = tRANSACTIONTYPE;
	}

	public String getRemarkString() {
		return RemarkString;
	}

	public void setRemarkString(String remarkString) {
		RemarkString = remarkString;
	}

}
