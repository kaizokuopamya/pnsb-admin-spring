package com.itl.pns.bean;

import java.math.BigInteger;
import java.util.Date;

public class ConfigMasterBean {

	private BigInteger ID;

	private String CONFIG_KEY;

	private String CONFIG_VALUE;

	private String DESCRIPTION;

	private BigInteger STATUSID;

	private BigInteger CREATEDBY;

	private Date CREATEDON;

	private BigInteger APPID;

	private String SHORTNAME;

	private String STATUSNAME;

	public BigInteger getID() {
		return ID;
	}

	public void setID(BigInteger iD) {
		ID = iD;
	}

	public String getCONFIG_KEY() {
		return CONFIG_KEY;
	}

	public void setCONFIG_KEY(String cONFIG_KEY) {
		CONFIG_KEY = cONFIG_KEY;
	}

	public String getCONFIG_VALUE() {
		return CONFIG_VALUE;
	}

	public void setCONFIG_VALUE(String cONFIG_VALUE) {
		CONFIG_VALUE = cONFIG_VALUE;
	}

	public String getDESCRIPTION() {
		return DESCRIPTION;
	}

	public void setDESCRIPTION(String dESCRIPTION) {
		DESCRIPTION = dESCRIPTION;
	}

	public BigInteger getSTATUSID() {
		return STATUSID;
	}

	public void setSTATUSID(BigInteger sTATUSID) {
		STATUSID = sTATUSID;
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

	public BigInteger getAPPID() {
		return APPID;
	}

	public void setAPPID(BigInteger aPPID) {
		APPID = aPPID;
	}

	public String getSHORTNAME() {
		return SHORTNAME;
	}

	public void setSHORTNAME(String sHORTNAME) {
		SHORTNAME = sHORTNAME;
	}

	public String getSTATUSNAME() {
		return STATUSNAME;
	}

	public void setSTATUSNAME(String sTATUSNAME) {
		STATUSNAME = sTATUSNAME;
	}

}
