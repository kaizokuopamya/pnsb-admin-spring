package com.itl.pns.bean;

import java.math.BigDecimal;
import java.util.Date;

public class AdapterAuditLogsBean {

	private BigDecimal ID;

	private String MESSAGE;

	private String MSG_TYPE;

	public Date CREATED_ON;

	private String ADAPTER_IP;

	private String ADAPTER_CHANNEL;

	private BigDecimal CREATED_BY;

	private BigDecimal MOBILE_NO;

	private String CHANNEL_REF_NO;

	private String RRN;

	private String createdByName;

	public BigDecimal getId() {
		return ID;
	}

	public void setId(BigDecimal ID) {
		this.ID = ID;
	}

	public String getMESSAGE() {
		return MESSAGE;
	}

	public void setMESSAGE(String mESSAGE) {
		MESSAGE = mESSAGE;
	}

	public String getMSG_TYPE() {
		return MSG_TYPE;
	}

	public void setMSG_TYPE(String mSG_TYPE) {
		MSG_TYPE = mSG_TYPE;
	}

	public Date getCREATED_ON() {
		return CREATED_ON;
	}

	public void setCREATED_ON(Date cREATED_ON) {
		CREATED_ON = cREATED_ON;
	}

	public String getADAPTER_IP() {
		return ADAPTER_IP;
	}

	public void setADAPTER_IP(String aDAPTER_IP) {
		ADAPTER_IP = aDAPTER_IP;
	}

	public String getADAPTER_CHANNEL() {
		return ADAPTER_CHANNEL;
	}

	public void setADAPTER_CHANNEL(String aDAPTER_CHANNEL) {
		ADAPTER_CHANNEL = aDAPTER_CHANNEL;
	}

	public BigDecimal getCREATED_BY() {
		return CREATED_BY;
	}

	public void setCREATED_BY(BigDecimal cREATED_BY) {
		CREATED_BY = cREATED_BY;
	}

	public BigDecimal getMOBILE_NO() {
		return MOBILE_NO;
	}

	public void setMOBILE_NO(BigDecimal mOBILE_NO) {
		MOBILE_NO = mOBILE_NO;
	}

	public String getCHANNEL_REF_NO() {
		return CHANNEL_REF_NO;
	}

	public void setCHANNEL_REF_NO(String cHANNEL_REF_NO) {
		CHANNEL_REF_NO = cHANNEL_REF_NO;
	}

	public String getRRN() {
		return RRN;
	}

	public void setRRN(String rRN) {
		RRN = rRN;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

}
