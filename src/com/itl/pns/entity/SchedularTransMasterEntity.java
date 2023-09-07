package com.itl.pns.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "SCHEDULARTRANSMASTER")
public class SchedularTransMasterEntity {

	@javax.persistence.Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SCHEDULAR_TRANSID_SEQ")
	@SequenceGenerator(name = "SCHEDULAR_TRANSID_SEQ", sequenceName = "SCHEDULAR_TRANSID_SEQ", allocationSize = 1)
	private BigDecimal id;

	@Column(name = "CUSTOMERID")
	private long CUSTOMERID;

	@Column(name = "FROMACCNUMBER")
	private String FROMACCNUMBER;

	@Column(name = "TOACCNUMBER")
	private String TOACCNUMBER;

	@Column(name = "PAYMENTSTARTDATE")
	private Date PAYMENTSTARTDATE;

	@Column(name = "PAYMENTENDDATE")
	private Date PAYMENTENDDATE;

	@Column(name = "PAYMENTFREQUENCY")
	private String PAYMENTFREQUENCY;

	@Column(name = "PAYMENTFREQTYPE")
	private String PAYMENTFREQTYPE;

	@Column(name = "EMIAMOUNT")
	private long EMIAMOUNT;

	@Column(name = "NUMOFINSTALLMENT")
	private long NUMOFINSTALLMENT;

	@Column(name = "SITYPE")
	private String SITYPE;

	@Column(name = "STATUSID")
	private int STATUSID;

	@Column(name = "CREATEDBY")
	private int CREATEDBY;

	@Column(name = "CREATEDON")
	private Date CREATEDON;

	@Column(name = "UPDATEDBY")
	private int UPDATEDBY;

	@Column(name = "UPDATEDON")
	private Date UPDATEDON;

	@Column(name = "RRN")
	private String RRN;

	@Transient
	String statusname;
	
	@Transient
	String createdbyName;

	public String getStatusName() {
		return statusname;
	}

	public void setStatusName(String statusName) {
		this.statusname = statusName;
	}

	public String getCreatedbyName() {
		return createdbyName;
	}

	public void setCreatedbyName(String createdbyName) {
		this.createdbyName = createdbyName;
	}

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public long getCUSTOMERID() {
		return CUSTOMERID;
	}

	public void setCUSTOMERID(long cUSTOMERID) {
		CUSTOMERID = cUSTOMERID;
	}

	public String getFROMACCNUMBER() {
		return FROMACCNUMBER;
	}

	public void setFROMACCNUMBER(String fROMACCNUMBER) {
		FROMACCNUMBER = fROMACCNUMBER;
	}

	public String getTOACCNUMBER() {
		return TOACCNUMBER;
	}

	public void setTOACCNUMBER(String tOACCNUMBER) {
		TOACCNUMBER = tOACCNUMBER;
	}

	public Date getPAYMENTSTARTDATE() {
		return PAYMENTSTARTDATE;
	}

	public void setPAYMENTSTARTDATE(Date pAYMENTSTARTDATE) {
		PAYMENTSTARTDATE = pAYMENTSTARTDATE;
	}

	public Date getPAYMENTENDDATE() {
		return PAYMENTENDDATE;
	}

	public void setPAYMENTENDDATE(Date pAYMENTENDDATE) {
		PAYMENTENDDATE = pAYMENTENDDATE;
	}

	public String getPAYMENTFREQUENCY() {
		return PAYMENTFREQUENCY;
	}

	public void setPAYMENTFREQUENCY(String pAYMENTFREQUENCY) {
		PAYMENTFREQUENCY = pAYMENTFREQUENCY;
	}

	public String getPAYMENTFREQTYPE() {
		return PAYMENTFREQTYPE;
	}

	public void setPAYMENTFREQTYPE(String pAYMENTFREQTYPE) {
		PAYMENTFREQTYPE = pAYMENTFREQTYPE;
	}

	public long getEMIAMOUNT() {
		return EMIAMOUNT;
	}

	public void setEMIAMOUNT(long eMIAMOUNT) {
		EMIAMOUNT = eMIAMOUNT;
	}

	public long getNUMOFINSTALLMENT() {
		return NUMOFINSTALLMENT;
	}

	public void setNUMOFINSTALLMENT(long nUMOFINSTALLMENT) {
		NUMOFINSTALLMENT = nUMOFINSTALLMENT;
	}

	public String getSITYPE() {
		return SITYPE;
	}

	public void setSITYPE(String sITYPE) {
		SITYPE = sITYPE;
	}

	public int getSTATUSID() {
		return STATUSID;
	}

	public void setSTATUSID(int sTATUSID) {
		STATUSID = sTATUSID;
	}

	public int getCREATEDBY() {
		return CREATEDBY;
	}

	public void setCREATEDBY(int cREATEDBY) {
		CREATEDBY = cREATEDBY;
	}

	public Date getCREATEDON() {
		return CREATEDON;
	}

	public void setCREATEDON(Date cREATEDON) {
		CREATEDON = cREATEDON;
	}

	public int getUPDATEDBY() {
		return UPDATEDBY;
	}

	public void setUPDATEDBY(int uPDATEDBY) {
		UPDATEDBY = uPDATEDBY;
	}

	public Date getUPDATEDON() {
		return UPDATEDON;
	}

	public void setUPDATEDON(Date uPDATEDON) {
		UPDATEDON = uPDATEDON;
	}

	public String getRRN() {
		return RRN;
	}

	public void setRRN(String rRN) {
		RRN = rRN;
	}

}
