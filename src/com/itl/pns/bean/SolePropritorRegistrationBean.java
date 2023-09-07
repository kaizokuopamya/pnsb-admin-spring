package com.itl.pns.bean;

import java.math.BigDecimal;
import java.sql.Date;

public class SolePropritorRegistrationBean {

	private Date CREATEDON;
	private String COMPANYCODE;
	private String COMPANYNAME;
	private String CIF;
	private String SHORTNAME;
	private String EMAIL_ID;
	private BigDecimal STATUSID;
	private Character IS_CORPORATE;

	public Character getIS_CORPORATE() {
		return IS_CORPORATE;
	}

	public void setIS_CORPORATE(Character iS_CORPORATE) {
		IS_CORPORATE = iS_CORPORATE;
	}

	public Date getCREATEDON() {
		return CREATEDON;
	}

	public void setCREATEDON(Date cREATEDON) {
		CREATEDON = cREATEDON;
	}

	public String getCOMPANYCODE() {
		return COMPANYCODE;
	}

	public void setCOMPANYCODE(String cOMPANYCODE) {
		COMPANYCODE = cOMPANYCODE;
	}

	public String getCOMPANYNAME() {
		return COMPANYNAME;
	}

	public void setCOMPANYNAME(String cOMPANYNAME) {
		COMPANYNAME = cOMPANYNAME;
	}

	public String getCIF() {
		return CIF;
	}

	public void setCIF(String cIF) {
		CIF = cIF;
	}

	public String getSHORTNAME() {
		return SHORTNAME;
	}

	public void setSHORTNAME(String sHORTNAME) {
		SHORTNAME = sHORTNAME;
	}

	public String getEMAIL_ID() {
		return EMAIL_ID;
	}

	public void setEMAIL_ID(String eMAIL_ID) {
		EMAIL_ID = eMAIL_ID;
	}

	public BigDecimal getSTATUSID() {
		return STATUSID;
	}

	public void setSTATUSID(BigDecimal sTATUSID) {
		STATUSID = sTATUSID;
	}

}
