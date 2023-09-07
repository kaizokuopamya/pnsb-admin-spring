package com.itl.pns.bean;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;

public class Payaggregatorbean {

	private BigInteger ID;
	private String MERCHANTNAME;
	private String APPTYPE;
	private String MERCHANTCODE;
	private String PGREFNO;
	private BigDecimal TXNAMOUNT;
	private String CHECKSUM;
	private String RETURNURL;
	private String BANKTXNID;
	private String FULLURL;
	private String ACCOUNTNO;
	private BigInteger CUSTOMERID;
	private String VERIFYURL;
	private Date VERIFYRESPONDEDON;
	private BigInteger CREATEDBY;
	private Date CREATEDON;
	private BigInteger STATUSID;
	private String REMARKS;
	private String CONSUMER_CODE;
	private String PAYEEACCOUNT;
	private String CUSTOMERNAME;
	private String MOBILE;
	private Date FROMDATE;
	private Date TODATE;

	public BigInteger getID() {
		return ID;
	}

	public void setID(BigInteger iD) {
		ID = iD;
	}

	public String getMERCHANTNAME() {
		return MERCHANTNAME;
	}

	public void setMERCHANTNAME(String mERCHANTNAME) {
		MERCHANTNAME = mERCHANTNAME;
	}

	public String getAPPTYPE() {
		return APPTYPE;
	}

	public void setAPPTYPE(String aPPTYPE) {
		APPTYPE = aPPTYPE;
	}

	public String getMERCHANTCODE() {
		return MERCHANTCODE;
	}

	public void setMERCHANTCODE(String mERCHANTCODE) {
		MERCHANTCODE = mERCHANTCODE;
	}

	public String getPGREFNO() {
		return PGREFNO;
	}

	public void setPGREFNO(String pGREFNO) {
		PGREFNO = pGREFNO;
	}

	public BigDecimal getTXNAMOUNT() {
		return TXNAMOUNT;
	}

	public void setTXNAMOUNT(BigDecimal tXNAMOUNT) {
		TXNAMOUNT = tXNAMOUNT;
	}

	public String getCHECKSUM() {
		return CHECKSUM;
	}

	public void setCHECKSUM(String cHECKSUM) {
		CHECKSUM = cHECKSUM;
	}

	public String getRETURNURL() {
		return RETURNURL;
	}

	public void setRETURNURL(String rETURNURL) {
		RETURNURL = rETURNURL;
	}

	public String getBANKTXNID() {
		return BANKTXNID;
	}

	public void setBANKTXNID(String bANKTXNID) {
		BANKTXNID = bANKTXNID;
	}

	public String getFULLURL() {
		return FULLURL;
	}

	public void setFULLURL(String fULLURL) {
		FULLURL = fULLURL;
	}

	public String getACCOUNTNO() {
		return ACCOUNTNO;
	}

	public void setACCOUNTNO(String aCCOUNTNO) {
		ACCOUNTNO = aCCOUNTNO;
	}

	public BigInteger getCUSTOMERID() {
		return CUSTOMERID;
	}

	public void setCUSTOMERID(BigInteger cUSTOMERID) {
		CUSTOMERID = cUSTOMERID;
	}

	public String getVERIFYURL() {
		return VERIFYURL;
	}

	public void setVERIFYURL(String vERIFYURL) {
		VERIFYURL = vERIFYURL;
	}

	public Date getVERIFYRESPONDEDON() {
		return VERIFYRESPONDEDON;
	}

	public void setVERIFYRESPONDEDON(Date vERIFYRESPONDEDON) {
		VERIFYRESPONDEDON = vERIFYRESPONDEDON;
	}

	public BigInteger getCREATEDBY() {
		return CREATEDBY;
	}

	public void setCREATEDBY(BigInteger cREATEDBY) {
		CREATEDBY = cREATEDBY;
	}

	public Date getCREATEDON() {
		return CREATEDON;
	}

	public void setCREATEDON(Date cREATEDON) {
		CREATEDON = cREATEDON;
	}

	public BigInteger getSTATUSID() {
		return STATUSID;
	}

	public void setSTATUSID(BigInteger sTATUSID) {
		STATUSID = sTATUSID;
	}

	public String getREMARKS() {
		return REMARKS;
	}

	public void setREMARKS(String rEMARKS) {
		REMARKS = rEMARKS;
	}

	public String getCONSUMER_CODE() {
		return CONSUMER_CODE;
	}

	public void setCONSUMER_CODE(String cONSUMER_CODE) {
		CONSUMER_CODE = cONSUMER_CODE;
	}

	public String getPAYEEACCOUNT() {
		return PAYEEACCOUNT;
	}

	public void setPAYEEACCOUNT(String pAYEEACCOUNT) {
		PAYEEACCOUNT = pAYEEACCOUNT;
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

	public Date getFROMDATE() {
		return FROMDATE;
	}

	public void setFROMDATE(Date fROMDATE) {
		FROMDATE = fROMDATE;
	}

	public Date getTODATE() {
		return TODATE;
	}

	public void setTODATE(Date tODATE) {
		TODATE = tODATE;
	}

}
