package com.itl.pns.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "USER_CREDCHANGE_SESSION")
public class UserCredentialsSessionEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_CRED_SESSIONID_SEQ")
	@SequenceGenerator(name = "USER_CRED_SESSIONID_SEQ", sequenceName = "USER_CRED_SESSIONID_SEQ", allocationSize = 1)
	@Column(name = "ID")
	private BigDecimal ID;

	@Column(name = "USERTOKEN")
	private String USERTOKEN;

	@Column(name = "OTP")
	private String OTP;

	@Column(name = "CREATEDON")
	private Date CREATEDON;

	@Column(name = "UPDATEDON")
	private Date UPDATEDON;

	@Column(name = "EMAIL")
	private String EMAIL;

	@Column(name = "MOBILE")
	private String MOBILE;

	@Column(name = "STATUSID")
	private BigDecimal STATUSID;

	@Column(name = "USERMASTERID")
	private BigDecimal USERMASTERID;

	@Column(name = "USERNAME")
	private String USERNAME;

	@Transient
	private String userNewPassword;

	public BigDecimal getID() {
		return ID;
	}

	public void setID(BigDecimal iD) {
		ID = iD;
	}

	public String getUSERTOKEN() {
		return USERTOKEN;
	}

	public void setUSERTOKEN(String uSERTOKEN) {
		USERTOKEN = uSERTOKEN;
	}

	public String getOTP() {
		return OTP;
	}

	public void setOTP(String oTP) {
		OTP = oTP;
	}

	public Date getCREATEDON() {
		return CREATEDON;
	}

	public void setCREATEDON(Date cREATEDON) {
		CREATEDON = cREATEDON;
	}

	public Date getUPDATEDON() {
		return UPDATEDON;
	}

	public void setUPDATEDON(Date uPDATEDON) {
		UPDATEDON = uPDATEDON;
	}

	public String getEMAIL() {
		return EMAIL;
	}

	public void setEMAIL(String eMAIL) {
		EMAIL = eMAIL;
	}

	public String getMOBILE() {
		return MOBILE;
	}

	public void setMOBILE(String mOBILE) {
		MOBILE = mOBILE;
	}

	public BigDecimal getSTATUSID() {
		return STATUSID;
	}

	public void setSTATUSID(BigDecimal sTATUSID) {
		STATUSID = sTATUSID;
	}

	public BigDecimal getUSERMASTERID() {
		return USERMASTERID;
	}

	public void setUSERMASTERID(BigDecimal uSERMASTERID) {
		USERMASTERID = uSERMASTERID;
	}

	public String getUSERNAME() {
		return USERNAME;
	}

	public void setUSERNAME(String uSERNAME) {
		USERNAME = uSERNAME;
	}

	public String getUserNewPassword() {
		return userNewPassword;
	}

	public void setUserNewPassword(String userNewPassword) {
		this.userNewPassword = userNewPassword;
	}

}
