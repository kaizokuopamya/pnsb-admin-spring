package com.itl.pns.bean;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name="TINSERVICEDATA")
public class TinServicesData {

	@Column(name="ID")
	private BigDecimal ID;
	
	@Column(name="MERCHANTID")
	private String MERCHANTID;
	
	@Column(name="TXNID")
	private String TXNID;
	
	@Column(name="PAN")
	private String PAN;
	
	@Column(name="CRN")
	private String CRN;
	
	@Column(name="ASSESSMENTYEAR")
	private String ASSESSMENTYEAR;
	
	@Column(name="TOTALAMOUNT")
	private BigDecimal TOTALAMOUNT;
	
	@Column(name="BASICTAX")
	private BigDecimal BASICTAX;
	
	@Column(name="SURCHARGE")
	private BigDecimal SURCHARGE;
	
	@Column(name="EDUCATIONALCESS")
	private BigDecimal EDUCATIONALCESS;
	
	@Column(name="INTEREST")
	private BigDecimal INTEREST;
	
	@Column(name="PENALTY")
	private BigDecimal PENALTY;
	
	@Column(name="OTHERS")
	private BigDecimal OTHERS;
	
	@Column(name="MAJORHEAD")
	private String MAJORHEAD;
	
	@Column(name="MINORHEAD")
	private String MINORHEAD;
	
	@Column(name="PAYMENTMODE")
	private String PAYMENTMODE;
	
	@Column(name="INSTRUMENTTYPE")
	private String INSTRUMENTTYPE;
	
	@Column(name="REQUESTTIME")
	private String REQUESTTIME;
	
	@Column(name="CRNDATETIME")
	private String CRNDATETIME;
	
	@Column(name="RETURNURL")
	private String RETURNURL;
	
	@Column(name="BANKTXNID")
	private String BANKTXNID;
	
	@Column(name="CHALLANVALIDTILLDATE")
	private String CHALLANVALIDTILLDATE;
	
	@Column(name="PAYMENTDATETIME")
	private Timestamp PAYMENTDATETIME;
	
	@Column(name="CREATEDBY")
	private BigDecimal CREATEDBY;
	
	@Column(name="CREATEDON")
	private Timestamp CREATEDON;	
	
	@Column(name="STATUSID")
	private BigDecimal STATUSID;
	
	@Column(name="UPDATEDBY")
	private BigDecimal UPDATEDBY;
	
	@Column(name="UPDATEDON")
	private Timestamp UPDATEDON;
	
	@Transient
	private Date FROMDATE;
	
	@Transient
	private Date TODATE;
	
	@Column(name="CUSTID")
	BigDecimal CUSTID;
	
	@Column(name="FROMACCOUNT")
	private String FROMACCOUNT;
	
	@Column(name="CIF")
	String CIF;
	
	@Column(name="APPID")
	BigDecimal APPID;
	
	@Column(name="TINERRORCODE")
	String TINERRORCODE;
	
	@Column(name="LUGG_FILE_SYNC_STATUS")
	String LUGG_FILE_SYNC_STATUS;
	
	@Column(name="SYNC_STATUS_WITH_PRAKALP")
	String SYNC_STATUS_WITH_PRAKALP;

	@Column(name="VALIDATECRNRESPONSE")
	String VALIDATECRNRESPONSE;
	
	public String getVALIDATECRNRESPONSE() {
		return VALIDATECRNRESPONSE;
	}

	public void setVALIDATECRNRESPONSE(String vALIDATECRNRESPONSE) {
		VALIDATECRNRESPONSE = vALIDATECRNRESPONSE;
	}

	public BigDecimal getID() {
		return ID;
	}

	public void setID(BigDecimal iD) {
		ID = iD;
	}

	public String getMERCHANTID() {
		return MERCHANTID;
	}

	public void setMERCHANTID(String mERCHANTID) {
		MERCHANTID = mERCHANTID;
	}

	public String getTXNID() {
		return TXNID;
	}

	public void setTXNID(String tXNID) {
		TXNID = tXNID;
	}

	public String getPAN() {
		return PAN;
	}

	public void setPAN(String pAN) {
		PAN = pAN;
	}

	public String getCRN() {
		return CRN;
	}

	public void setCRN(String cRN) {
		CRN = cRN;
	}

	public String getASSESSMENTYEAR() {
		return ASSESSMENTYEAR;
	}

	public void setASSESSMENTYEAR(String aSSESSMENTYEAR) {
		ASSESSMENTYEAR = aSSESSMENTYEAR;
	}

	public BigDecimal getTOTALAMOUNT() {
		return TOTALAMOUNT;
	}

	public void setTOTALAMOUNT(BigDecimal tOTALAMOUNT) {
		TOTALAMOUNT = tOTALAMOUNT;
	}

	public BigDecimal getBASICTAX() {
		return BASICTAX;
	}

	public void setBASICTAX(BigDecimal bASICTAX) {
		BASICTAX = bASICTAX;
	}

	public BigDecimal getSURCHARGE() {
		return SURCHARGE;
	}

	public void setSURCHARGE(BigDecimal sURCHARGE) {
		SURCHARGE = sURCHARGE;
	}

	public BigDecimal getEDUCATIONALCESS() {
		return EDUCATIONALCESS;
	}

	public void setEDUCATIONALCESS(BigDecimal eDUCATIONALCESS) {
		EDUCATIONALCESS = eDUCATIONALCESS;
	}

	public BigDecimal getINTEREST() {
		return INTEREST;
	}

	public void setINTEREST(BigDecimal iNTEREST) {
		INTEREST = iNTEREST;
	}

	public BigDecimal getPENALTY() {
		return PENALTY;
	}

	public void setPENALTY(BigDecimal pENALTY) {
		PENALTY = pENALTY;
	}

	public BigDecimal getOTHERS() {
		return OTHERS;
	}

	public void setOTHERS(BigDecimal oTHERS) {
		OTHERS = oTHERS;
	}

	public String getMAJORHEAD() {
		return MAJORHEAD;
	}

	public void setMAJORHEAD(String mAJORHEAD) {
		MAJORHEAD = mAJORHEAD;
	}

	public String getMINORHEAD() {
		return MINORHEAD;
	}

	public void setMINORHEAD(String mINORHEAD) {
		MINORHEAD = mINORHEAD;
	}

	public String getPAYMENTMODE() {
		return PAYMENTMODE;
	}

	public void setPAYMENTMODE(String pAYMENTMODE) {
		PAYMENTMODE = pAYMENTMODE;
	}

	public String getINSTRUMENTTYPE() {
		return INSTRUMENTTYPE;
	}

	public void setINSTRUMENTTYPE(String iNSTRUMENTTYPE) {
		INSTRUMENTTYPE = iNSTRUMENTTYPE;
	}

	public String getREQUESTTIME() {
		return REQUESTTIME;
	}

	public void setREQUESTTIME(String rEQUESTTIME) {
		REQUESTTIME = rEQUESTTIME;
	}

	public String getCRNDATETIME() {
		return CRNDATETIME;
	}

	public void setCRNDATETIME(String cRNDATETIME) {
		CRNDATETIME = cRNDATETIME;
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

	public String getCHALLANVALIDTILLDATE() {
		return CHALLANVALIDTILLDATE;
	}

	public void setCHALLANVALIDTILLDATE(String cHALLANVALIDTILLDATE) {
		CHALLANVALIDTILLDATE = cHALLANVALIDTILLDATE;
	}

	public Timestamp getPAYMENTDATETIME() {
		return PAYMENTDATETIME;
	}

	public void setPAYMENTDATETIME(Timestamp pAYMENTDATETIME) {
		PAYMENTDATETIME = pAYMENTDATETIME;
	}

	public BigDecimal getCREATEDBY() {
		return CREATEDBY;
	}

	public void setCREATEDBY(BigDecimal cREATEDBY) {
		CREATEDBY = cREATEDBY;
	}

	public Timestamp getCREATEDON() {
		return CREATEDON;
	}

	public void setCREATEDON(Timestamp cREATEDON) {
		CREATEDON = cREATEDON;
	}

	public BigDecimal getSTATUSID() {
		return STATUSID;
	}

	public void setSTATUSID(BigDecimal sTATUSID) {
		STATUSID = sTATUSID;
	}

	public BigDecimal getUPDATEDBY() {
		return UPDATEDBY;
	}

	public void setUPDATEDBY(BigDecimal uPDATEDBY) {
		UPDATEDBY = uPDATEDBY;
	}

	public Timestamp getUPDATEDON() {
		return UPDATEDON;
	}

	public void setUPDATEDON(Timestamp uPDATEDON) {
		UPDATEDON = uPDATEDON;
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

	public BigDecimal getCUSTID() {
		return CUSTID;
	}

	public void setCUSTID(BigDecimal cUSTID) {
		CUSTID = cUSTID;
	}

	public String getFROMACCOUNT() {
		return FROMACCOUNT;
	}

	public void setFROMACCOUNT(String fROMACCOUNT) {
		FROMACCOUNT = fROMACCOUNT;
	}

	public String getCIF() {
		return CIF;
	}

	public void setCIF(String cIF) {
		CIF = cIF;
	}

	public BigDecimal getAPPID() {
		return APPID;
	}

	public void setAPPID(BigDecimal aPPID) {
		APPID = aPPID;
	}

	public String getTINERRORCODE() {
		return TINERRORCODE;
	}

	public void setTINERRORCODE(String tINERRORCODE) {
		TINERRORCODE = tINERRORCODE;
	}

	public String getLUGG_FILE_SYNC_STATUS() {
		return LUGG_FILE_SYNC_STATUS;
	}

	public void setLUGG_FILE_SYNC_STATUS(String lUGG_FILE_SYNC_STATUS) {
		LUGG_FILE_SYNC_STATUS = lUGG_FILE_SYNC_STATUS;
	}

	public String getSYNC_STATUS_WITH_PRAKALP() {
		return SYNC_STATUS_WITH_PRAKALP;
	}

	public void setSYNC_STATUS_WITH_PRAKALP(String sYNC_STATUS_WITH_PRAKALP) {
		SYNC_STATUS_WITH_PRAKALP = sYNC_STATUS_WITH_PRAKALP;
	}

	public TinServicesData(BigDecimal iD, String mERCHANTID, String tXNID, String pAN, String cRN, String aSSESSMENTYEAR,
			BigDecimal tOTALAMOUNT, BigDecimal bASICTAX, BigDecimal sURCHARGE, BigDecimal eDUCATIONALCESS, BigDecimal iNTEREST, BigDecimal pENALTY,
			BigDecimal oTHERS, String mAJORHEAD, String mINORHEAD, String pAYMENTMODE, String iNSTRUMENTTYPE,
			String rEQUESTTIME, String cRNDATETIME, String rETURNURL, String bANKTXNID, String cHALLANVALIDTILLDATE,
			Timestamp pAYMENTDATETIME, BigDecimal cREATEDBY, Timestamp cREATEDON, BigDecimal sTATUSID, BigDecimal uPDATEDBY, Timestamp uPDATEDON,
			Date fROMDATE, Date tODATE, BigDecimal cUSTID, String fROMACCOUNT, String cIF, BigDecimal aPPID, String tINERRORCODE,
			String lUGG_FILE_SYNC_STATUS, String sYNC_STATUS_WITH_PRAKALP, String vALIDATECRNRESPONSE) {
		super();
		ID = iD;
		MERCHANTID = mERCHANTID;
		TXNID = tXNID;
		PAN = pAN;
		CRN = cRN;
		ASSESSMENTYEAR = aSSESSMENTYEAR;
		TOTALAMOUNT = tOTALAMOUNT;
		BASICTAX = bASICTAX;
		SURCHARGE = sURCHARGE;
		EDUCATIONALCESS = eDUCATIONALCESS;
		INTEREST = iNTEREST;
		PENALTY = pENALTY;
		OTHERS = oTHERS;
		MAJORHEAD = mAJORHEAD;
		MINORHEAD = mINORHEAD;
		PAYMENTMODE = pAYMENTMODE;
		INSTRUMENTTYPE = iNSTRUMENTTYPE;
		REQUESTTIME = rEQUESTTIME;
		CRNDATETIME = cRNDATETIME;
		RETURNURL = rETURNURL;
		BANKTXNID = bANKTXNID;
		CHALLANVALIDTILLDATE = cHALLANVALIDTILLDATE;
		PAYMENTDATETIME = pAYMENTDATETIME;
		CREATEDBY = cREATEDBY;
		CREATEDON = cREATEDON;
		STATUSID = sTATUSID;
		UPDATEDBY = uPDATEDBY;
		UPDATEDON = uPDATEDON;
		FROMDATE = fROMDATE;
		TODATE = tODATE;
		CUSTID = cUSTID;
		FROMACCOUNT = fROMACCOUNT;
		CIF = cIF;
		APPID = aPPID;
		TINERRORCODE = tINERRORCODE;
		LUGG_FILE_SYNC_STATUS = lUGG_FILE_SYNC_STATUS;
		SYNC_STATUS_WITH_PRAKALP = sYNC_STATUS_WITH_PRAKALP;
		VALIDATECRNRESPONSE = vALIDATECRNRESPONSE;
	}

	public TinServicesData() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "TINSERVICEDATA [ID=" + ID + ", MERCHANTID=" + MERCHANTID + ", TXNID=" + TXNID + ", PAN=" + PAN
				+ ", CRN=" + CRN + ", TOTALAMOUNT=" + TOTALAMOUNT + ", BASICTAX="
				+ BASICTAX + ", SURCHARGE=" + SURCHARGE + ", EDUCATIONALCESS=" + EDUCATIONALCESS + ", INTEREST="
				+ INTEREST + ", PENALTY=" + PENALTY + ", OTHERS=" + OTHERS + ", MAJORHEAD=" + MAJORHEAD + ", MINORHEAD="
				+ MINORHEAD + ", PAYMENTMODE=" + PAYMENTMODE + ", INSTRUMENTTYPE=" + INSTRUMENTTYPE + ", REQUESTTIME="
				+ REQUESTTIME + ", CRNDATETIME=" + CRNDATETIME + ", RETURNURL=" + RETURNURL + ", BANKTXNID=" + BANKTXNID
				+ ", CHALLANVALIDTILLDATE=" + CHALLANVALIDTILLDATE + ", PAYMENTDATETIME=" + PAYMENTDATETIME
				+ ", CREATEDBY=" + CREATEDBY + ", CREATEDON=" + CREATEDON + ", STATUSID=" + STATUSID + ", UPDATEDBY="
				+ UPDATEDBY + ", UPDATEDON=" + UPDATEDON + ", FROMDATE=" + FROMDATE + ", TODATE=" + TODATE + ", CUSTID="
				+ CUSTID + ", FROMACCOUNT=" + FROMACCOUNT + ", CIF=" + CIF + ", APPID=" + APPID + ", TINERRORCODE="
				+ TINERRORCODE + ", LUGG_FILE_SYNC_STATUS=" + LUGG_FILE_SYNC_STATUS + ", SYNC_STATUS_WITH_PRAKALP="
				+ SYNC_STATUS_WITH_PRAKALP + ", VALIDATECRNRESPONSE=" + VALIDATECRNRESPONSE + "]";
	} 
}
