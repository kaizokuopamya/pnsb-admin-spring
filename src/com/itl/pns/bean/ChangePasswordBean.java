package com.itl.pns.bean;

import java.math.BigDecimal;

public class ChangePasswordBean {
	private BigDecimal ID;

	private String USERID;
	private String OLDPASSWORD;
	private String NEWPASSWORD;
	private String EMAILID;
	public BigDecimal getID() {
		return ID;
	}
	public void setID(BigDecimal iD) {
		ID = iD;
	}
	public String getUSERID() {
		return USERID;
	}
	public void setUSERID(String uSERID) {
		USERID = uSERID;
	}
	public String getOLDPASSWORD() {
		return OLDPASSWORD;
	}
	public void setOLDPASSWORD(String oLDPASSWORD) {
		OLDPASSWORD = oLDPASSWORD;
	}
	public String getNEWPASSWORD() {
		return NEWPASSWORD;
	}
	public void setNEWPASSWORD(String nEWPASSWORD) {
		NEWPASSWORD = nEWPASSWORD;
	}
	public String getEMAILID() {
		return EMAILID;
	}
	public void setEMAILID(String eMAILID) {
		EMAILID = eMAILID;
	}
	
	
	
	
	

}
