package com.itl.pns.bean;

import java.math.BigDecimal;
import java.util.Date;

public class LanguageJsonBean {

	private BigDecimal id;

	private String ENGLISHTEXT;

	private String LANGUAGECODE;

	private String LANGUAGETEXT;

	private String ISACTIVE;

	private Date CREATEDON;

	private BigDecimal CREATEDBY;

	private String SHORTNAME;

	private BigDecimal APPID;

	private BigDecimal STATUSID;

	String LANGUAGECODEDESC;

	String remark;

	BigDecimal userAction;

	public String getLANGUAGECODEDESC() {
		return LANGUAGECODEDESC;
	}

	public void setLANGUAGECODEDESC(String lANGUAGECODEDESC) {
		LANGUAGECODEDESC = lANGUAGECODEDESC;
	}

	public BigDecimal getID() {
		return id;
	}

	public void setID(BigDecimal iD) {
		id = iD;
	}

	public String getENGLISHTEXT() {
		return ENGLISHTEXT;
	}

	public void setENGLISHTEXT(String eNGLISHTEXT) {
		ENGLISHTEXT = eNGLISHTEXT;
	}

	public String getLANGUAGECODE() {
		return LANGUAGECODE;
	}

	public void setLANGUAGECODE(String lANGUAGECODE) {
		LANGUAGECODE = lANGUAGECODE;
	}

	public String getLANGUAGETEXT() {
		return LANGUAGETEXT;
	}

	public void setLANGUAGETEXT(String lANGUAGETEXT) {
		LANGUAGETEXT = lANGUAGETEXT;
	}

	public String getISACTIVE() {
		return ISACTIVE;
	}

	public void setISACTIVE(String iSACTIVE) {
		ISACTIVE = iSACTIVE;
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

	public String getSHORTNAME() {
		return SHORTNAME;
	}

	public void setSHORTNAME(String sHORTNAME) {
		SHORTNAME = sHORTNAME;
	}

	public BigDecimal getAPPID() {
		return APPID;
	}

	public void setAPPID(BigDecimal aPPID) {
		APPID = aPPID;
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

	public BigDecimal getSTATUSID() {
		return STATUSID;
	}

	public void setSTATUSID(BigDecimal sTATUSID) {
		STATUSID = sTATUSID;
	}

}
