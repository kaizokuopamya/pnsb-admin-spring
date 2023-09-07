package com.itl.pns.bean;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class LinkRolesBean {

	private BigInteger ID;

	private Short IS_ACTIVE;

	private BigDecimal MENU_ID;

	private BigDecimal ROLE_ID;

	private BigDecimal PRIVILEGE_ID;

	private String UPDATED_BY;

	private Date UPDATED_ON;

	private String CREATED_BY;

	private Date CREATED_ON;

	private String MENU_NAME;

	private String PRIVILEGENAME;

	public String getMENU_NAME() {
		return MENU_NAME;
	}

	public void setMENU_NAME(String mENU_NAME) {
		MENU_NAME = mENU_NAME;
	}

	public BigInteger getID() {
		return ID;
	}

	public void setID(BigInteger iD) {
		ID = iD;
	}

	public Short getIS_ACTIVE() {
		return IS_ACTIVE;
	}

	public void setIS_ACTIVE(Short iS_ACTIVE) {
		IS_ACTIVE = iS_ACTIVE;
	}

	public BigDecimal getMENU_ID() {
		return MENU_ID;
	}

	public void setMENU_ID(BigDecimal mENU_ID) {
		MENU_ID = mENU_ID;
	}

	public BigDecimal getROLE_ID() {
		return ROLE_ID;
	}

	public void setROLE_ID(BigDecimal rOLE_ID) {
		ROLE_ID = rOLE_ID;
	}

	public BigDecimal getPRIVILEGE_ID() {
		return PRIVILEGE_ID;
	}

	public void setPRIVILEGE_ID(BigDecimal pRIVILEGE_ID) {
		PRIVILEGE_ID = pRIVILEGE_ID;
	}

	public String getUPDATED_BY() {
		return UPDATED_BY;
	}

	public void setUPDATED_BY(String uPDATED_BY) {
		UPDATED_BY = uPDATED_BY;
	}

	public Date getUPDATED_ON() {
		return UPDATED_ON;
	}

	public void setUPDATED_ON(Date uPDATED_ON) {
		UPDATED_ON = uPDATED_ON;
	}

	public String getCREATED_BY() {
		return CREATED_BY;
	}

	public void setCREATED_BY(String cREATED_BY) {
		CREATED_BY = cREATED_BY;
	}

	public Date getCREATED_ON() {
		return CREATED_ON;
	}

	public void setCREATED_ON(Date cREATED_ON) {
		CREATED_ON = cREATED_ON;
	}

	public String getPRIVILEGENAME() {
		return PRIVILEGENAME;
	}

	public void setPRIVILEGENAME(String pRIVILEGENAME) {
		PRIVILEGENAME = pRIVILEGENAME;
	}

}
