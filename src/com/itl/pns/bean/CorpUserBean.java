package com.itl.pns.bean;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.util.List;
import java.util.Map;

public class CorpUserBean {

	private BigInteger transLimitUserId;
	private BigInteger userId;;
	private String corpUSerName;
	private Date CREATEDON;
	private BigInteger STATUSID;
	private BigInteger COUNT;
	private BigDecimal corpCompId;
	private Map<String, BigDecimal> userNameList;
	private BigDecimal corp_status;
	private List<String> userNameListNew;
	private Character IS_CORPORATE;
	private String cif;

	public String getCif() {
		return cif;
	}

	public void setCif(String cif) {
		this.cif = cif;
	}

	public Character getIS_CORPORATE() {
		return IS_CORPORATE;
	}

	public void setIS_CORPORATE(Character iS_CORPORATE) {
		IS_CORPORATE = iS_CORPORATE;
	}

	public List<String> getUserNameListNew() {
		return userNameListNew;
	}

	public void setUserNameListNew(List<String> userNameListNew) {
		this.userNameListNew = userNameListNew;
	}

	public BigDecimal getCorpCompId() {
		return corpCompId;
	}

	public void setCorpCompId(BigDecimal corpCompId) {
		this.corpCompId = corpCompId;
	}

	public Map<String, BigDecimal> getUserNameList() {
		return userNameList;
	}

	public void setUserNameList(Map<String, BigDecimal> userNameList) {
		this.userNameList = userNameList;
	}

	public BigDecimal getCorp_status() {
		return corp_status;
	}

	public void setCorp_status(BigDecimal corp_status) {
		this.corp_status = corp_status;
	}

	public Date getCREATEDON() {
		return CREATEDON;
	}

	public void setCREATEDON(Date cREATEDON) {
		CREATEDON = cREATEDON;
	}

	public BigInteger getSTATUSID() {
		return STATUSID;
	}

	public void setSTATUSID(BigInteger sTATUSID) {
		STATUSID = sTATUSID;
	}

	public BigInteger getCOUNT() {
		return COUNT;
	}

	public void setCOUNT(BigInteger cOUNT) {
		COUNT = cOUNT;
	}

	public BigInteger getTransLimitUserId() {
		return transLimitUserId;
	}

	public void setTransLimitUserId(BigInteger transLimitUserId) {
		this.transLimitUserId = transLimitUserId;
	}

	public BigInteger getUserId() {
		return userId;
	}

	public void setUserId(BigInteger userId) {
		this.userId = userId;
	}

	public String getCorpUSerName() {
		return corpUSerName;
	}

	public void setCorpUSerName(String corpUSerName) {
		this.corpUSerName = corpUSerName;
	}

}
