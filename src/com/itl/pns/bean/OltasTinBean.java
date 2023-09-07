package com.itl.pns.bean;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name="OLTAS_TIN")
public class OltasTinBean {

	@Column(name="ID")
	BigDecimal ID;
	
	@Column(name="CUSTID")
	BigDecimal CUSTID;
	
	@Column(name="CHALLANTYPE")
	String CHALLANTYPE;
	
	@Column(name="FROMACCOUNT")
	String FROMACCOUNT;
	
	@Column(name="BANKNAMEURL")
	String BANKNAMEURL;
	
	@Column(name="MAJORHEAD")
	String MAJORHEAD;
	
	@Column(name="RADIOINDEX")
	String RADIOINDEX;
	
	@Column(name="BANKURL")
	String BANKURL;
	
	@Column(name="CUSTNAME")
	String CUSTNAME;
	
	@Column(name="TRANSACTIONDATE")
	Timestamp TRANSACTIONDATE;
	
	@Column(name="ADD_STATE")
	String ADD_STATE;
	
	@Column(name="BANKNAME_C")
	String BANKNAME_C;
	
	@Column(name="ADD_LINE1")
	String ADD_LINE1;
	
	@Column(name="ADD_LINE2")
	String ADD_LINE2;
	
	@Column(name="ADD_LINE3")
	String ADD_LINE3;
	
	@Column(name="ADD_LINE4")
	String ADD_LINE4;
	
	@Column(name="ADD_LINE5")
	String ADD_LINE5;
	
	@Column(name="ZAOCODE")
	String ZAOCODE;
	
	@Column(name="VALID")
	String VALID;
	
	@Column(name="FINANCIALYEAR")
	String FINANCIALYEAR;
	
	@Column(name="PAN")
	String PAN;
	
	@Column(name="CHALLANNO")
	String CHALLANNO;
	
	@Column(name="MINORHEAD")
	String MINORHEAD;
	
	@Column(name="ADD_PIN")
	String ADD_PIN;
	
	@Column(name="MINORINDEX")
	String MINORINDEX;
	
	@Column(name="R2")
	String R2;
	
	@Column(name="FLAG_VAR")
	String FLAG_VAR;
	
	@Column(name="TRAN_ID")
	String TRAN_ID;
	
	@Column(name="RRN")
	String RRN;
	
	@Column(name="RECORD_USED")
	String RECORD_USED;
	
	@Column(name="CREATEDBY")
	BigDecimal CREATEDBY;
	
	@Column(name="CREATEDON")
	Timestamp CREATEDON;
	
	@Column(name="STATUSID")
	BigDecimal STATUSID;
	
	@Column(name="BASICTAX")
	BigDecimal BASICTAX;
	
	@Column(name="EDUCESS")
	BigDecimal EDUCESS;
	
	@Column(name="SURCHARGE")
	BigDecimal SURCHARGE;
	
	@Column(name="PAYMENTCODE1")
	String PAYMENTCODE1;
	
	@Column(name="PAYMENTCODE2")
	String PAYMENTCODE2;
	
	@Column(name="PAYMENTCODE3")
	String PAYMENTCODE3;
	
	@Column(name="INTEREST")
	BigDecimal INTEREST;
	
	@Column(name="PENALTY")
	BigDecimal PENALTY;
	
	@Column(name="OTHER")
	BigDecimal OTHER;
	
	@Column(name="FEE234E")
	BigDecimal FEE234E;
	
	@Column(name="FEE26QB")
	BigDecimal FEE26QB;
	
	@Column(name="TOTALAMT")
	BigDecimal TOTALAMT;
	
	@Column(name="SCROLLNO")
	String SCROLLNO;
	
	@Column(name="PAYMENTMODE")
	String PAYMENTMODE;
	
	@Column(name="CHQCREDITDATE")
	String CHQCREDITDATE;
	
	@Column(name="BRSCROLLDATE")
	String BRSCROLLDATE;
	
	@Column(name="NODALSCROLLNO")
	String NODALSCROLLNO;
	
	@Column(name="NODALSCROLLDT")
	String NODALSCROLLDT;
	
	@Column(name="FORMNO")
	String FORMNO;
	
	@Column(name="CBFLAG")
	Character CBFLAG;
	
	@Column(name="CIN")
	BigDecimal CIN;
	
	@Column(name="APPID")
	BigDecimal APPID;
	
	@Column(name="NATUREPAYMENT")
	String NATUREPAYMENT;
	
	@Column(name="CIF")
	String CIF;
	
	@Transient
	Date FROMDATE;
	
	@Transient
	Date TODATE;

	public OltasTinBean() {
		super();
	}

	public OltasTinBean(BigDecimal iD, BigDecimal cUSTID, String cHALLANTYPE, String fROMACCOUNT, String bANKNAMEURL,
			String mAJORHEAD, String rADIOINDEX, String bANKURL, String cUSTNAME, Timestamp tRANSACTIONDATE,
			String aDD_STATE, String bANKNAME_C, String aDD_LINE1, String aDD_LINE2, String aDD_LINE3, String aDD_LINE4,
			String aDD_LINE5, String zAOCODE, String vALID, String fINANCIALYEAR, String pAN, String cHALLANNO,
			String mINORHEAD, String aDD_PIN, String mINORINDEX, String r2, String fLAG_VAR, String tRAN_ID, String rRN,
			String rECORD_USED, BigDecimal cREATEDBY, Timestamp cREATEDON, BigDecimal sTATUSID, BigDecimal bASICTAX, BigDecimal eDUCESS, BigDecimal sURCHARGE,
			String pAYMENTCODE1, String pAYMENTCODE2, String pAYMENTCODE3, BigDecimal iNTEREST, BigDecimal pENALTY, BigDecimal oTHER,
			BigDecimal fEE234E, BigDecimal fEE26QB, BigDecimal tOTALAMT, String sCROLLNO, String pAYMENTMODE, String cHQCREDITDATE,
			String bRSCROLLDATE, String nODALSCROLLNO, String nODALSCROLLDT, String fORMNO, Character cBFLAG, BigDecimal cIN,
			BigDecimal aPPID, String nATUREPAYMENT, String cIF, Date fROMDATE, Date tODATE) {
		super();
		ID = iD;
		CUSTID = cUSTID;
		CHALLANTYPE = cHALLANTYPE;
		FROMACCOUNT = fROMACCOUNT;
		BANKNAMEURL = bANKNAMEURL;
		MAJORHEAD = mAJORHEAD;
		RADIOINDEX = rADIOINDEX;
		BANKURL = bANKURL;
		CUSTNAME = cUSTNAME;
		TRANSACTIONDATE = tRANSACTIONDATE;
		ADD_STATE = aDD_STATE;
		BANKNAME_C = bANKNAME_C;
		ADD_LINE1 = aDD_LINE1;
		ADD_LINE2 = aDD_LINE2;
		ADD_LINE3 = aDD_LINE3;
		ADD_LINE4 = aDD_LINE4;
		ADD_LINE5 = aDD_LINE5;
		ZAOCODE = zAOCODE;
		VALID = vALID;
		FINANCIALYEAR = fINANCIALYEAR;
		PAN = pAN;
		CHALLANNO = cHALLANNO;
		MINORHEAD = mINORHEAD;
		ADD_PIN = aDD_PIN;
		MINORINDEX = mINORINDEX;
		R2 = r2;
		FLAG_VAR = fLAG_VAR;
		TRAN_ID = tRAN_ID;
		RRN = rRN;
		RECORD_USED = rECORD_USED;
		CREATEDBY = cREATEDBY;
		CREATEDON = cREATEDON;
		STATUSID = sTATUSID;
		BASICTAX = bASICTAX;
		EDUCESS = eDUCESS;
		SURCHARGE = sURCHARGE;
		PAYMENTCODE1 = pAYMENTCODE1;
		PAYMENTCODE2 = pAYMENTCODE2;
		PAYMENTCODE3 = pAYMENTCODE3;
		INTEREST = iNTEREST;
		PENALTY = pENALTY;
		OTHER = oTHER;
		FEE234E = fEE234E;
		FEE26QB = fEE26QB;
		TOTALAMT = tOTALAMT;
		SCROLLNO = sCROLLNO;
		PAYMENTMODE = pAYMENTMODE;
		CHQCREDITDATE = cHQCREDITDATE;
		BRSCROLLDATE = bRSCROLLDATE;
		NODALSCROLLNO = nODALSCROLLNO;
		NODALSCROLLDT = nODALSCROLLDT;
		FORMNO = fORMNO;
		CBFLAG = cBFLAG;
		CIN = cIN;
		APPID = aPPID;
		NATUREPAYMENT = nATUREPAYMENT;
		CIF = cIF;
		FROMDATE = fROMDATE;
		TODATE = tODATE;
	}

	public BigDecimal getID() {
		return ID;
	}

	public void setID(BigDecimal iD) {
		ID = iD;
	}

	public BigDecimal getCUSTID() {
		return CUSTID;
	}

	public void setCUSTID(BigDecimal cUSTID) {
		CUSTID = cUSTID;
	}

	public String getCHALLANTYPE() {
		return CHALLANTYPE;
	}

	public void setCHALLANTYPE(String cHALLANTYPE) {
		CHALLANTYPE = cHALLANTYPE;
	}

	public String getFROMACCOUNT() {
		return FROMACCOUNT;
	}

	public void setFROMACCOUNT(String fROMACCOUNT) {
		FROMACCOUNT = fROMACCOUNT;
	}

	public String getBANKNAMEURL() {
		return BANKNAMEURL;
	}

	public void setBANKNAMEURL(String bANKNAMEURL) {
		BANKNAMEURL = bANKNAMEURL;
	}

	public String getMAJORHEAD() {
		return MAJORHEAD;
	}

	public void setMAJORHEAD(String mAJORHEAD) {
		MAJORHEAD = mAJORHEAD;
	}

	public String getRADIOINDEX() {
		return RADIOINDEX;
	}

	public void setRADIOINDEX(String rADIOINDEX) {
		RADIOINDEX = rADIOINDEX;
	}

	public String getBANKURL() {
		return BANKURL;
	}

	public void setBANKURL(String bANKURL) {
		BANKURL = bANKURL;
	}

	public String getCUSTNAME() {
		return CUSTNAME;
	}

	public void setCUSTNAME(String cUSTNAME) {
		CUSTNAME = cUSTNAME;
	}

	public Timestamp getTRANSACTIONDATE() {
		return TRANSACTIONDATE;
	}

	public void setTRANSACTIONDATE(Timestamp tRANSACTIONDATE) {
		TRANSACTIONDATE = tRANSACTIONDATE;
	}


	public String getADD_STATE() {
		return ADD_STATE;
	}

	public void setADD_STATE(String aDD_STATE) {
		ADD_STATE = aDD_STATE;
	}

	public String getBANKNAME_C() {
		return BANKNAME_C;
	}

	public void setBANKNAME_C(String bANKNAME_C) {
		BANKNAME_C = bANKNAME_C;
	}

	public String getADD_LINE1() {
		return ADD_LINE1;
	}

	public void setADD_LINE1(String aDD_LINE1) {
		ADD_LINE1 = aDD_LINE1;
	}

	public String getADD_LINE2() {
		return ADD_LINE2;
	}

	public void setADD_LINE2(String aDD_LINE2) {
		ADD_LINE2 = aDD_LINE2;
	}

	public String getADD_LINE3() {
		return ADD_LINE3;
	}

	public void setADD_LINE3(String aDD_LINE3) {
		ADD_LINE3 = aDD_LINE3;
	}

	public String getADD_LINE4() {
		return ADD_LINE4;
	}

	public void setADD_LINE4(String aDD_LINE4) {
		ADD_LINE4 = aDD_LINE4;
	}

	public String getADD_LINE5() {
		return ADD_LINE5;
	}

	public void setADD_LINE5(String aDD_LINE5) {
		ADD_LINE5 = aDD_LINE5;
	}

	public String getZAOCODE() {
		return ZAOCODE;
	}

	public void setZAOCODE(String zAOCODE) {
		ZAOCODE = zAOCODE;
	}

	public String getVALID() {
		return VALID;
	}

	public void setVALID(String vALID) {
		VALID = vALID;
	}

	public String getFINANCIALYEAR() {
		return FINANCIALYEAR;
	}

	public void setFINANCIALYEAR(String fINANCIALYEAR) {
		FINANCIALYEAR = fINANCIALYEAR;
	}

	public String getPAN() {
		return PAN;
	}

	public void setPAN(String pAN) {
		PAN = pAN;
	}

	public String getCHALLANNO() {
		return CHALLANNO;
	}

	public void setCHALLANNO(String cHALLANNO) {
		CHALLANNO = cHALLANNO;
	}

	public String getMINORHEAD() {
		return MINORHEAD;
	}

	public void setMINORHEAD(String mINORHEAD) {
		MINORHEAD = mINORHEAD;
	}

	public String getADD_PIN() {
		return ADD_PIN;
	}

	public void setADD_PIN(String aDD_PIN) {
		ADD_PIN = aDD_PIN;
	}

	public String getMINORINDEX() {
		return MINORINDEX;
	}

	public void setMINORINDEX(String mINORINDEX) {
		MINORINDEX = mINORINDEX;
	}

	public String getR2() {
		return R2;
	}

	public void setR2(String r2) {
		R2 = r2;
	}

	public String getFLAG_VAR() {
		return FLAG_VAR;
	}

	public void setFLAG_VAR(String fLAG_VAR) {
		FLAG_VAR = fLAG_VAR;
	}

	public String getTRAN_ID() {
		return TRAN_ID;
	}

	public void setTRAN_ID(String tRAN_ID) {
		TRAN_ID = tRAN_ID;
	}

	public String getRRN() {
		return RRN;
	}

	public void setRRN(String rRN) {
		RRN = rRN;
	}

	public String getRECORD_USED() {
		return RECORD_USED;
	}

	public void setRECORD_USED(String rECORD_USED) {
		RECORD_USED = rECORD_USED;
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

	public BigDecimal getBASICTAX() {
		return BASICTAX;
	}

	public void setBASICTAX(BigDecimal bASICTAX) {
		BASICTAX = bASICTAX;
	}

	public BigDecimal getEDUCESS() {
		return EDUCESS;
	}

	public void setEDUCESS(BigDecimal eDUCESS) {
		EDUCESS = eDUCESS;
	}

	public BigDecimal getSURCHARGE() {
		return SURCHARGE;
	}

	public void setSURCHARGE(BigDecimal sURCHARGE) {
		SURCHARGE = sURCHARGE;
	}

	public String getPAYMENTCODE1() {
		return PAYMENTCODE1;
	}

	public void setPAYMENTCODE1(String pAYMENTCODE1) {
		PAYMENTCODE1 = pAYMENTCODE1;
	}

	public String getPAYMENTCODE2() {
		return PAYMENTCODE2;
	}

	public void setPAYMENTCODE2(String pAYMENTCODE2) {
		PAYMENTCODE2 = pAYMENTCODE2;
	}

	public String getPAYMENTCODE3() {
		return PAYMENTCODE3;
	}

	public void setPAYMENTCODE3(String pAYMENTCODE3) {
		PAYMENTCODE3 = pAYMENTCODE3;
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

	public BigDecimal getOTHER() {
		return OTHER;
	}

	public void setOTHER(BigDecimal oTHER) {
		OTHER = oTHER;
	}

	public BigDecimal getFEE234E() {
		return FEE234E;
	}

	public void setFEE234E(BigDecimal fEE234E) {
		FEE234E = fEE234E;
	}

	public BigDecimal getFEE26QB() {
		return FEE26QB;
	}

	public void setFEE26QB(BigDecimal fEE26QB) {
		FEE26QB = fEE26QB;
	}

	public BigDecimal getTOTALAMT() {
		return TOTALAMT;
	}

	public void setTOTALAMT(BigDecimal tOTALAMT) {
		TOTALAMT = tOTALAMT;
	}

	public String getSCROLLNO() {
		return SCROLLNO;
	}

	public void setSCROLLNO(String sCROLLNO) {
		SCROLLNO = sCROLLNO;
	}

	public String getPAYMENTMODE() {
		return PAYMENTMODE;
	}

	public void setPAYMENTMODE(String pAYMENTMODE) {
		PAYMENTMODE = pAYMENTMODE;
	}

	public String getCHQCREDITDATE() {
		return CHQCREDITDATE;
	}

	public void setCHQCREDITDATE(String cHQCREDITDATE) {
		CHQCREDITDATE = cHQCREDITDATE;
	}

	public String getBRSCROLLDATE() {
		return BRSCROLLDATE;
	}

	public void setBRSCROLLDATE(String bRSCROLLDATE) {
		BRSCROLLDATE = bRSCROLLDATE;
	}

	public String getNODALSCROLLNO() {
		return NODALSCROLLNO;
	}

	public void setNODALSCROLLNO(String nODALSCROLLNO) {
		NODALSCROLLNO = nODALSCROLLNO;
	}

	public String getNODALSCROLLDT() {
		return NODALSCROLLDT;
	}

	public void setNODALSCROLLDT(String nODALSCROLLDT) {
		NODALSCROLLDT = nODALSCROLLDT;
	}
	
	public String getNATUREPYMENT() {
		return NATUREPAYMENT;
	}

	public void setNATUREPYMENT(String nATUREPAYMENT) {
		NATUREPAYMENT = nATUREPAYMENT;
	}

	public String getCIF() {
		return CIF;
	}

	public void setCIF(String cIF) {
		CIF = cIF;
	}

	public String getFORMNO() {
		return FORMNO;
	}

	public void setFORMNO(String fORMNO) {
		FORMNO = fORMNO;
	}

	public Character getCBFLAG() {
		return CBFLAG;
	}

	public void setCBFLAG(Character cBFLAG) {
		CBFLAG = cBFLAG;
	}

	public BigDecimal getCIN() {
		return CIN;
	}

	public void setCIN(BigDecimal cIN) {
		CIN = cIN;
	}

	public BigDecimal getAPPID() {
		return APPID;
	}

	public void setAPPID(BigDecimal aPPID) {
		APPID = aPPID;
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

	@Override
	public String toString() {
		return "OltasTinBean [ID=" + ID + ", CUSTID=" + CUSTID + ", CHALLANTYPE=" + CHALLANTYPE + ", FROMACCOUNT="
				+ FROMACCOUNT + ", BANKNAMEURL=" + BANKNAMEURL + ", MAJORHEAD=" + MAJORHEAD + ", RADIOINDEX="
				+ RADIOINDEX + ", BANKURL=" + BANKURL + ", CUSTNAME=" + CUSTNAME + ", TRANSACTIONDATE="
				+ TRANSACTIONDATE + ", ADD_STATE=" + ADD_STATE + ", BANKNAME_C=" + BANKNAME_C + ", ADD_LINE1="
				+ ADD_LINE1 + ", ADD_LINE2=" + ADD_LINE2 + ", ADD_LINE3=" + ADD_LINE3 + ", ADD_LINE4=" + ADD_LINE4
				+ ", ADD_LINE5=" + ADD_LINE5 + ", ZAOCODE=" + ZAOCODE + ", VALID=" + VALID + ", FINANCIALYEAR="
				+ FINANCIALYEAR + ", PAN=" + PAN + ", CHALLANNO=" + CHALLANNO + ", MINORHEAD=" + MINORHEAD
				+ ", ADD_PIN=" + ADD_PIN + ", MINORINDEX=" + MINORINDEX + ", R2=" + R2 + ", FLAG_VAR=" + FLAG_VAR
				+ ", TRAN_ID=" + TRAN_ID + ", RRN=" + RRN + ", RECORD_USED=" + RECORD_USED + ", CREATEDBY=" + CREATEDBY
				+ ", CREATEDON=" + CREATEDON + ", STATUSID=" + STATUSID + ", BASICTAX=" + BASICTAX + ", EDUCESS="
				+ EDUCESS + ", SURCHARGE=" + SURCHARGE + ", PAYMENTCODE1=" + PAYMENTCODE1 + ", PAYMENTCODE2="
				+ PAYMENTCODE2 + ", PAYMENTCODE3=" + PAYMENTCODE3 + ", INTEREST=" + INTEREST + ", PENALTY=" + PENALTY
				+ ", OTHER=" + OTHER + ", FEE234E=" + FEE234E + ", FEE26QB=" + FEE26QB + ", TOTALAMT=" + TOTALAMT
				+ ", SCROLLNO=" + SCROLLNO + ", PAYMENTMODE=" + PAYMENTMODE + ", CHQCREDITDATE=" + CHQCREDITDATE
				+ ", BRSCROLLDATE=" + BRSCROLLDATE + ", NODALSCROLLNO=" + NODALSCROLLNO + ", NODALSCROLLDT="
				+ NODALSCROLLDT + ", FORMNO=" + FORMNO + ", CBFLAG=" + CBFLAG + ", CIN=" + CIN + ", APPID=" + APPID
				+ ", NATUREPAYMENT=" + NATUREPAYMENT + ", CIF=" + CIF + ", FROMDATE=" + FROMDATE + ", TODATE=" + TODATE
				+ "]";
	}
}
