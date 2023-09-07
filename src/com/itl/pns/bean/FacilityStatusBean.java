package com.itl.pns.bean;

import java.math.BigDecimal;
import java.util.Date;

public class FacilityStatusBean {

	BigDecimal id;

	String BANK_SHORTNAME;

	String ACTIVITYCODE;

	String DISPLAYNAME;

	BigDecimal LIMITS;

	char ENCRYPTIONTYPE;

	BigDecimal CREATEDBY;

	Date CREATEDON;

	BigDecimal STATUSID;

	BigDecimal APPID;

	String FT_NFT;

	String SHORTNAME;

	String STATUS;

	String createdByName;

	String remark;

	BigDecimal userAction;

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getBANK_SHORTNAME() {
		return BANK_SHORTNAME;
	}

	public void setBANK_SHORTNAME(String bANK_SHORTNAME) {
		BANK_SHORTNAME = bANK_SHORTNAME;
	}

	public String getACTIVITYCODE() {
		return ACTIVITYCODE;
	}

	public void setACTIVITYCODE(String aCTIVITYCODE) {
		ACTIVITYCODE = aCTIVITYCODE;
	}

	public String getDISPLAYNAME() {
		return DISPLAYNAME;
	}

	public void setDISPLAYNAME(String dISPLAYNAME) {
		DISPLAYNAME = dISPLAYNAME;
	}

	public BigDecimal getLIMITS() {
		return LIMITS;
	}

	public void setLIMITS(BigDecimal lIMITS) {
		LIMITS = lIMITS;
	}

	public char getENCRYPTIONTYPE() {
		return ENCRYPTIONTYPE;
	}

	public void setENCRYPTIONTYPE(char eNCRYPTIONTYPE) {
		ENCRYPTIONTYPE = eNCRYPTIONTYPE;
	}

	public BigDecimal getCREATEDBY() {
		return CREATEDBY;
	}

	public void setCREATEDBY(BigDecimal cREATEDBY) {
		CREATEDBY = cREATEDBY;
	}

	public Date getCREATEDON() {
		return CREATEDON;
	}

	public void setCREATEDON(Date cREATEDON) {
		CREATEDON = cREATEDON;
	}

	public BigDecimal getSTATUSID() {
		return STATUSID;
	}

	public void setSTATUSID(BigDecimal sTATUSID) {
		STATUSID = sTATUSID;
	}

	public BigDecimal getAPPID() {
		return APPID;
	}

	public void setAPPID(BigDecimal aPPID) {
		APPID = aPPID;
	}

	public String getFT_NFT() {
		return FT_NFT;
	}

	public void setFT_NFT(String fT_NFT) {
		FT_NFT = fT_NFT;
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

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getUserAction() {
		return userAction;
	}

	public void setUserAction(BigDecimal userAction) {
		this.userAction = userAction;
	}

}
