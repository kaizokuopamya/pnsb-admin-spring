package com.itl.pns.bean;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "SFTP_FILE_STATUS")
public class SftpFileStatuses {

	@Column(name = "ID")
	BigDecimal ID;

	@Column(name = "FILENAME")
	String FILENAME;

	@Column(name = "CREATEDON")
	Date CREATEDON;

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

	public String getFILENAME() {
		return FILENAME;
	}

	public void setFILENAME(String fILENAME) {
		FILENAME = fILENAME;
	}

	public Date getCREATEDON() {
		return CREATEDON;
	}

	public void setCREATEDON(Date cREATEDON) {
		CREATEDON = cREATEDON;
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

	public SftpFileStatuses(BigDecimal iD, String fILENAME, Date cREATEDON, Date fROMDATE, Date tODATE) {
		super();
		ID = iD;
		FILENAME = fILENAME;
		CREATEDON = cREATEDON;
		FROMDATE = fROMDATE;
		TODATE = tODATE;
	}

	public SftpFileStatuses() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "SFTPFILESTATUS [ID=" + ID + ", FILENAME=" + FILENAME + ", CREATEDON=" + CREATEDON + ", FROMDATE="
				+ FROMDATE + ", TODATE=" + TODATE + "]";
	}
}
