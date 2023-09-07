package com.itl.pns.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class FraudReportingBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BigDecimal ID;
	private String CIFNUMBER;
	private BigDecimal CUSTID;
	private String TRANSACTIONID;
	private String TRANSACTIONDATE;
	private String REMARKS;
	private String BANKREMARKS;
	private BigDecimal CREATEDBY;
	private Date CREATEDON;
	private BigDecimal STATUSID;
	private Date UPDATEDON;
	private BigDecimal UPDATEDBY;
	private BigDecimal APPID;
	private String USERTYPE;
	private String FRAUDREASON;
	private String ACCOUNTNUMBER;
	private String BRANCHCODE;
	
	public BigDecimal getID() {
		return ID;
	}
	public void setID(BigDecimal iD) {
		ID = iD;
	}
	public String getCIFNUMBER() {
		return CIFNUMBER;
	}
	public void setCIFNUMBER(String cIFNUMBER) {
		CIFNUMBER = cIFNUMBER;
	}
	public BigDecimal getCUSTID() {
		return CUSTID;
	}
	public void setCUSTID(BigDecimal cUSTID) {
		CUSTID = cUSTID;
	}
	public String getTRANSACTIONID() {
		return TRANSACTIONID;
	}
	public void setTRANSACTIONID(String tRANSACTIONID) {
		TRANSACTIONID = tRANSACTIONID;
	}
	public String getTRANSACTIONDATE() {
		return TRANSACTIONDATE;
	}
	public void setTRANSACTIONDATE(String tRANSACTIONDATE) {
		TRANSACTIONDATE = tRANSACTIONDATE;
	}
	public String getREMARKS() {
		return REMARKS;
	}
	public void setREMARKS(String rEMARKS) {
		REMARKS = rEMARKS;
	}
	public String getBANKREMARKS() {
		return BANKREMARKS;
	}
	public void setBANKREMARKS(String bANKREMARKS) {
		BANKREMARKS = bANKREMARKS;
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
	public Date getUPDATEDON() {
		return UPDATEDON;
	}
	public void setUPDATEDON(Date uPDATEDON) {
		UPDATEDON = uPDATEDON;
	}
	public BigDecimal getUPDATEDBY() {
		return UPDATEDBY;
	}
	public void setUPDATEDBY(BigDecimal uPDATEDBY) {
		UPDATEDBY = uPDATEDBY;
	}
	public BigDecimal getAPPID() {
		return APPID;
	}
	public void setAPPID(BigDecimal aPPID) {
		APPID = aPPID;
	}
	public String getUSERTYPE() {
		return USERTYPE;
	}
	public void setUSERTYPE(String uSERTYPE) {
		USERTYPE = uSERTYPE;
	}
	public String getFRAUDREASON() {
		return FRAUDREASON;
	}
	public void setFRAUDREASON(String fRAUDREASON) {
		FRAUDREASON = fRAUDREASON;
	}
	public String getACCOUNTNUMBER() {
		return ACCOUNTNUMBER;
	}
	public void setACCOUNTNUMBER(String aCCOUNTNUMBER) {
		ACCOUNTNUMBER = aCCOUNTNUMBER;
	}
	public String getBRANCHCODE() {
		return BRANCHCODE;
	}
	public void setBRANCHCODE(String bRANCHCODE) {
		BRANCHCODE = bRANCHCODE;
	}
	
	

	
}
