package com.itl.pns.bean;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "TIN2")
public class Tin2 {
	
	@Column(name="ID")
	long ID;
	
	@Column(name="MERCHANTID")
	String MERCHANTID;
	
	@Column(name="TXNID")
	String TXNID;
	
	@Column(name="PAN")
	String PAN;
	
	@Column(name="CRN")
	String CRN;
	
	@Column(name="AY")
	String AY;
	
	@Column(name="TOTAL_AMT")
	int TOTAL_AMT;
	
	@Column(name="BASIC_TAX")
	int BASIC_TAX;
	
	@Column(name="SUR_CHARGE")
	int SUR_CHARGE;
	
	@Column(name="EDU_CESS")
	int EDU_CESS;
	
	@Column(name="INTEREST")
	int INTEREST;
	
	@Column(name="PENALTY")
	int PENALTY;
	
	@Column(name="OTHERS")
	int OTHERS;
	
	@Column(name="MAJOR_HEAD")
	String MAJOR_HEAD;
	
	@Column(name="MINOR_HEAD")
	String MINOR_HEAD;
	
	@Column(name="PAYMENT_MODE")
	String PAYMENT_MODE;
	
	@Column(name="INSTRUMENT_TY")
	String INSTRUMENT_TY;
	
	@Column(name="REQUEST_TIM")
	String REQUEST_TIM;
	
	@Column(name="CRN_DATE_TIME")
	String CRN_DATE_TIME;
	
	@Column(name="RETURNURL")
	String RETURNURL;
	
	@Column(name="CHLN_VALID_TILL_DT")
	String CHLN_VALID_TILL_DT;
	
	@Column(name="CREATEDBY")
	int CREATEDBY;
	
	@Column(name="CREATEDON")
	Date CREATEDON;
	
	@Column(name="VERIFYRESPONDEDON")
	Date VERIFYRESPONDEDON;
	
	@Column(name="STATUSID")
	int STATUSID;
	
	@Column(name="BANKTXNID")
	String BANKTXNID;
	
	@Transient
	Date FROMDATE;
	@Transient
	Date TODATE;
	
	public Tin2() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Tin2(long iD, String mERCHANTID, String tXNID, String pAN, String cRN, String aY, int tOTAL_AMT,
			int bASIC_TAX, int sUR_CHARGE, int eDU_CESS, int iNTEREST, int pENALTY, int oTHERS, String mAJOR_HEAD,
			String mINOR_HEAD, String pAYMENT_MODE, String iNSTRUMENT_TY, String rEQUEST_TIM, String cRN_DATE_TIME,
			String rETURNURL, String cHLN_VALID_TILL_DT, int cREATEDBY, Date cREATEDON, Date vERIFYRESPONDEDON,
			int sTATUSID, String bANKTXNID, Date fROMDATE, Date tODATE) {
		super();
		ID = iD;
		MERCHANTID = mERCHANTID;
		TXNID = tXNID;
		PAN = pAN;
		CRN = cRN;
		AY = aY;
		TOTAL_AMT = tOTAL_AMT;
		BASIC_TAX = bASIC_TAX;
		SUR_CHARGE = sUR_CHARGE;
		EDU_CESS = eDU_CESS;
		INTEREST = iNTEREST;
		PENALTY = pENALTY;
		OTHERS = oTHERS;
		MAJOR_HEAD = mAJOR_HEAD;
		MINOR_HEAD = mINOR_HEAD;
		PAYMENT_MODE = pAYMENT_MODE;
		INSTRUMENT_TY = iNSTRUMENT_TY;
		REQUEST_TIM = rEQUEST_TIM;
		CRN_DATE_TIME = cRN_DATE_TIME;
		RETURNURL = rETURNURL;
		CHLN_VALID_TILL_DT = cHLN_VALID_TILL_DT;
		CREATEDBY = cREATEDBY;
		CREATEDON = cREATEDON;
		VERIFYRESPONDEDON = vERIFYRESPONDEDON;
		STATUSID = sTATUSID;
		BANKTXNID = bANKTXNID;
		FROMDATE = fROMDATE;
		TODATE = tODATE;
	}
	public long getID() {
		return ID;
	}
	public void setID(long iD) {
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
	public String getAY() {
		return AY;
	}
	public void setAY(String aY) {
		AY = aY;
	}
	public int getTOTAL_AMT() {
		return TOTAL_AMT;
	}
	public void setTOTAL_AMT(int tOTAL_AMT) {
		TOTAL_AMT = tOTAL_AMT;
	}
	public int getBASIC_TAX() {
		return BASIC_TAX;
	}
	public void setBASIC_TAX(int bASIC_TAX) {
		BASIC_TAX = bASIC_TAX;
	}
	public int getSUR_CHARGE() {
		return SUR_CHARGE;
	}
	public void setSUR_CHARGE(int sUR_CHARGE) {
		SUR_CHARGE = sUR_CHARGE;
	}
	public int getEDU_CESS() {
		return EDU_CESS;
	}
	public void setEDU_CESS(int eDU_CESS) {
		EDU_CESS = eDU_CESS;
	}
	public int getINTEREST() {
		return INTEREST;
	}
	public void setINTEREST(int iNTEREST) {
		INTEREST = iNTEREST;
	}
	public int getPENALTY() {
		return PENALTY;
	}
	public void setPENALTY(int pENALTY) {
		PENALTY = pENALTY;
	}
	public int getOTHERS() {
		return OTHERS;
	}
	public void setOTHERS(int oTHERS) {
		OTHERS = oTHERS;
	}
	public String getMAJOR_HEAD() {
		return MAJOR_HEAD;
	}
	public void setMAJOR_HEAD(String mAJOR_HEAD) {
		MAJOR_HEAD = mAJOR_HEAD;
	}
	public String getMINOR_HEAD() {
		return MINOR_HEAD;
	}
	public void setMINOR_HEAD(String mINOR_HEAD) {
		MINOR_HEAD = mINOR_HEAD;
	}
	public String getPAYMENT_MODE() {
		return PAYMENT_MODE;
	}
	public void setPAYMENT_MODE(String pAYMENT_MODE) {
		PAYMENT_MODE = pAYMENT_MODE;
	}
	public String getINSTRUMENT_TY() {
		return INSTRUMENT_TY;
	}
	public void setINSTRUMENT_TY(String iNSTRUMENT_TY) {
		INSTRUMENT_TY = iNSTRUMENT_TY;
	}
	public String getREQUEST_TIM() {
		return REQUEST_TIM;
	}
	public void setREQUEST_TIM(String rEQUEST_TIM) {
		REQUEST_TIM = rEQUEST_TIM;
	}
	public String getCRN_DATE_TIME() {
		return CRN_DATE_TIME;
	}
	public void setCRN_DATE_TIME(String cRN_DATE_TIME) {
		CRN_DATE_TIME = cRN_DATE_TIME;
	}
	public String getRETURNURL() {
		return RETURNURL;
	}
	public void setRETURNURL(String rETURNURL) {
		RETURNURL = rETURNURL;
	}
	public String getCHLN_VALID_TILL_DT() {
		return CHLN_VALID_TILL_DT;
	}
	public void setCHLN_VALID_TILL_DT(String cHLN_VALID_TILL_DT) {
		CHLN_VALID_TILL_DT = cHLN_VALID_TILL_DT;
	}
	public int getCREATEDBY() {
		return CREATEDBY;
	}
	public void setCREATEDBY(int cREATEDBY) {
		CREATEDBY = cREATEDBY;
	}
	public Date getCREATEDON() {
		return CREATEDON;
	}
	public void setCREATEDON(Date cREATEDON) {
		CREATEDON = cREATEDON;
	}
	public Date getVERIFYRESPONDEDON() {
		return VERIFYRESPONDEDON;
	}
	public void setVERIFYRESPONDEDON(Date vERIFYRESPONDEDON) {
		VERIFYRESPONDEDON = vERIFYRESPONDEDON;
	}
	public int getSTATUSID() {
		return STATUSID;
	}
	public void setSTATUSID(int sTATUSID) {
		STATUSID = sTATUSID;
	}
	public String getBANKTXNID() {
		return BANKTXNID;
	}
	public void setBANKTXNID(String bANKTXNID) {
		BANKTXNID = bANKTXNID;
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
		return "TIN2 [ID=" + ID + ", MERCHANTID=" + MERCHANTID + ", TXNID=" + TXNID + ", PAN=" + PAN + ", CRN=" + CRN
				+ ", AY=" + AY + ", TOTAL_AMT=" + TOTAL_AMT + ", BASIC_TAX=" + BASIC_TAX + ", SUR_CHARGE=" + SUR_CHARGE
				+ ", EDU_CESS=" + EDU_CESS + ", INTEREST=" + INTEREST + ", PENALTY=" + PENALTY + ", OTHERS=" + OTHERS
				+ ", MAJOR_HEAD=" + MAJOR_HEAD + ", MINOR_HEAD=" + MINOR_HEAD + ", PAYMENT_MODE=" + PAYMENT_MODE
				+ ", INSTRUMENT_TY=" + INSTRUMENT_TY + ", REQUEST_TIM=" + REQUEST_TIM + ", CRN_DATE_TIME="
				+ CRN_DATE_TIME + ", RETURNURL=" + RETURNURL + ", CHLN_VALID_TILL_DT=" + CHLN_VALID_TILL_DT
				+ ", CREATEDBY=" + CREATEDBY + ", CREATEDON=" + CREATEDON + ", VERIFYRESPONDEDON=" + VERIFYRESPONDEDON
				+ ", STATUSID=" + STATUSID + ", BANKTXNID=" + BANKTXNID + ", FROMDATE=" + FROMDATE + ", TODATE="
				+ TODATE + "]";
	}
}
