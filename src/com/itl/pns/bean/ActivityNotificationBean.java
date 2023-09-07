package com.itl.pns.bean;

import java.util.Date;

public class ActivityNotificationBean {

	private String ID;
	private String ACTIVITYCODE;
	private String DISPLAYNAME;
	private String ACTIVITYID;
	private String SMS;
	private String EMAIL;
	private String PUSH;
	private String ACTINOTIID;
	private Date CREATEDON;
	
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
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
	public String getACTIVITYID() {
		return ACTIVITYID;
	}
	public void setACTIVITYID(String aCTIVITYID) {
		ACTIVITYID = aCTIVITYID;
	}
	public String getSMS() {
		return SMS;
	}
	public void setSMS(String sMS) {
		SMS = sMS;
	}
	public String getEMAIL() {
		return EMAIL;
	}
	public void setEMAIL(String eMAIL) {
		EMAIL = eMAIL;
	}
	public String getPUSH() {
		return PUSH;
	}
	public void setPUSH(String pUSH) {
		PUSH = pUSH;
	}
	public String getACTINOTIID() {
		return ACTINOTIID;
	}
	public void setACTINOTIID(String aCTINOTIID) {
		ACTINOTIID = aCTINOTIID;
	}
	public Date getCREATEDON() {
		return CREATEDON;
	}
	public void setCREATEDON(Date cREATEDON) {
		CREATEDON = cREATEDON;
	}
	
	
}
