package com.itl.pns.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="SCHEDULARTRANSDETAILS")
public class SchedulareTransDetailsEntity {

	@javax.persistence.Id
	@Column(name = "ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "SCHEDULAR_TRANSID_SEQ")
	@SequenceGenerator(name = "SCHEDULAR_TRANSID_SEQ", sequenceName = "SCHEDULAR_TRANSID_SEQ", allocationSize=1)
	private BigDecimal id;
	
	@Column(name="SCEDULARTRANSID")
	private BigDecimal SCEDULARTRANSID;
	
	@Column(name="SCEDULEDSTARTDATE")
	private Date SCEDULEDSTARTDATE;
	
	@Column(name="SCEDULEDENDDATE")
	private Date SCEDULEDENDDATE;
	
	@Column(name="CBSREFFERENCENUMBER")
	private String CBSREFFERENCENUMBER;
	
	@Column(name="REMARK")
	private String REMARK;
	
	@Column(name="STATUSID")
	private int STATUSID;
	
	@Column(name="CREATEDON")
	private Date CREATEDON;
	
	@Column(name="CREATEDBY")
	private int CREATEDBY;
	
	@Column(name="UPDATEDON")
	private Date UPDATEDON;
	
	@Column(name="UPDATEDBY")
	private int UPDATEDBY;

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public BigDecimal getSCEDULARTRANSID() {
		return SCEDULARTRANSID;
	}

	public void setSCEDULARTRANSID(BigDecimal sCEDULARTRANSID) {
		SCEDULARTRANSID = sCEDULARTRANSID;
	}

	public Date getSCEDULEDSTARTDATE() {
		return SCEDULEDSTARTDATE;
	}

	public void setSCEDULEDSTARTDATE(Date sCEDULEDSTARTDATE) {
		SCEDULEDSTARTDATE = sCEDULEDSTARTDATE;
	}

	public Date getSCEDULEDENDDATE() {
		return SCEDULEDENDDATE;
	}

	public void setSCEDULEDENDDATE(Date sCEDULEDENDDATE) {
		SCEDULEDENDDATE = sCEDULEDENDDATE;
	}

	public String getCBSREFFERENCENUMBER() {
		return CBSREFFERENCENUMBER;
	}

	public void setCBSREFFERENCENUMBER(String cBSREFFERENCENUMBER) {
		CBSREFFERENCENUMBER = cBSREFFERENCENUMBER;
	}

	public String getREMARK() {
		return REMARK;
	}

	public void setREMARK(String rEMARK) {
		REMARK = rEMARK;
	}

	public int getSTATUSID() {
		return STATUSID;
	}

	public void setSTATUSID(int sTATUSID) {
		STATUSID = sTATUSID;
	}

	public Date getCREATEDON() {
		return CREATEDON;
	}

	public void setCREATEDON(Date cREATEDON) {
		CREATEDON = cREATEDON;
	}

	public int getCREATEDBY() {
		return CREATEDBY;
	}

	public void setCREATEDBY(int cREATEDBY) {
		CREATEDBY = cREATEDBY;
	}

	public Date getUPDATEDON() {
		return UPDATEDON;
	}

	public void setUPDATEDON(Date uPDATEDON) {
		UPDATEDON = uPDATEDON;
	}

	public int getUPDATEDBY() {
		return UPDATEDBY;
	}

	public void setUPDATEDBY(int uPDATEDBY) {
		UPDATEDBY = uPDATEDBY;
	}
	
	
	
}
