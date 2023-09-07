package com.itl.pns.bean;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="TIN_MULTILVL_CALLBACK_LOGS")
public class TinMultilevelCallBackLogs {

	@Column(name="ID")
	BigDecimal ID;
	
	@Column(name="TXNID")
	String TXNID;
	
	@Column(name="CRN")
	String CRN;
	
	@Column(name="BANKTXNID")
    String BANKTXNID;
	
	@Column(name="REQUEST")
     String REQUEST;
	
	@Column(name="RESPONSE")
	String RESPONSE;
	
	@Column(name="CREATEDON")
	Timestamp CREATEDON;
	
	@Column(name="UPDATEDON")
	Timestamp UPDATEDON;
	
	@Column(name="SYNC_STATUS")
	String SYNC_STATUS;
	
	@Transient
	Date FROMDATE;
	
	@Transient
	Date TODATE;

	public BigDecimal getID() {
		return ID;
	}

	public void setID(BigDecimal iD) {
		ID = iD;
	}

	public String getTXNID() {
		return TXNID;
	}

	public void setTXNID(String tXNID) {
		TXNID = tXNID;
	}

	public String getCRN() {
		return CRN;
	}

	public void setCRN(String cRN) {
		CRN = cRN;
	}

	public String getBANKTXNID() {
		return BANKTXNID;
	}

	public void setBANKTXNID(String bANKTXNID) {
		BANKTXNID = bANKTXNID;
	}

	public String getREQUEST() {
		return REQUEST;
	}

	public void setREQUEST(String rEQUEST) {
		REQUEST = rEQUEST;
	}

	public String getRESPONSE() {
		return RESPONSE;
	}

	public void setRESPONSE(String rESPONSE) {
		RESPONSE = rESPONSE;
	}

	public Timestamp getCREATEDON() {
		return CREATEDON;
	}

	public void setCREATEDON(Timestamp cREATEDON) {
		CREATEDON = cREATEDON;
	}

	public Timestamp getUPDATEDON() {
		return UPDATEDON;
	}

	public void setUPDATEDON(Timestamp uPDATEDON) {
		UPDATEDON = uPDATEDON;
	}

	public String getSYNC_STATUS() {
		return SYNC_STATUS;
	}

	public void setSYNC_STATUS(String sYNC_STATUS) {
		SYNC_STATUS = sYNC_STATUS;
	}

	public Date getFROMDATE() {
		return FROMDATE;
	}

	public void setFROMDATE(Date fROMDATE) {
		FROMDATE = fROMDATE;
	}

	public Date getTODATE() {
		return TODATE;
	}

	public void setTODATE(Date tODATE) {
		TODATE = tODATE;
	}

	@Override
	public String toString() {
		return "TINMULTILVLCALLBACKLOGS [ID=" + ID + ", TXNID=" + TXNID + ", CRN=" + CRN
				+ ", BANKTXNID=" + BANKTXNID + ", REQUEST=" + REQUEST + ", RESPONSE=" + RESPONSE + ", CREATEDON="
				+ CREATEDON + ", UPDATEDON=" + UPDATEDON + ", SYNC_STATUS=" + SYNC_STATUS + ", FROMDATE=" + FROMDATE
				+ ", TODATE=" + TODATE + "]";
	}

	public TinMultilevelCallBackLogs(BigDecimal iD, String tXNID,String cRN, String bANKTXNID, String rEQUEST,
			String rESPONSE, Timestamp cREATEDON, Timestamp uPDATEDON, String sYNC_STATUS, Date fROMDATE, Date tODATE) {
		super();
		ID = iD;
		TXNID = tXNID;
		CRN = cRN;
		BANKTXNID = bANKTXNID;
		REQUEST = rEQUEST;
		RESPONSE = rESPONSE;
		CREATEDON = cREATEDON;
		UPDATEDON = uPDATEDON;
		SYNC_STATUS = sYNC_STATUS;
		FROMDATE = fROMDATE;
		TODATE = tODATE;
	}

	public TinMultilevelCallBackLogs() {
		super();
	}
}
