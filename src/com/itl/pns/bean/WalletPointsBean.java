package com.itl.pns.bean;

import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;

public class WalletPointsBean {
	private BigDecimal id;
	private String CONFIGTYPE;
	private BigDecimal AMOUNT;
	private BigDecimal POINTS;
	private Timestamp FROMDATE;
	private Timestamp TODATE;
	private Date CREATEDON;
	private BigDecimal CREATEDBY;
	private BigDecimal STATUSID;
	private BigDecimal APPID;
	private String CUSTOMERNAME;
	private String STATUS;
	private String SHORTNAME;
	private String createdByName;
	String RRN;
	String DOCNAME;
	String MOBILE;
	String REMARKS;

	Clob CERTIFICATEOFINCORPORATIONIMG;
	String CERTIFICATEOFINCORPORATION;
	Clob ASSOCIATIONANDARTICLESIMG;
	String ASSOCIATIONANDARTICLES;
	Clob RESOLUTIONOFTHEBOARDIMG;
	String RESOLUTIONOFTHEBOARD;
	Clob POWEROFATTORNEYGRANTEDIMG;
	String POWEROFATTORNEYGRANTED;
	String REGISTEREDOFFICEPROOF;
	Clob REGISTEREDOFFICEIMGF;
	Clob PANCARDIMG;
	String PANCARD;
	Clob AADHAARCARDIMG;
	String AADHAARCARD;
	Clob IDENTIFICATIONPROOFIMG;
	String IDENTIFICATIONPROOF;

	private String remark;

	private BigDecimal userAction;

	public BigDecimal getID() {
		return id;
	}

	public void setID(BigDecimal iD) {
		id = iD;
	}

	public String getCONFIGTYPE() {
		return CONFIGTYPE;
	}

	public void setCONFIGTYPE(String cONFIGTYPE) {
		CONFIGTYPE = cONFIGTYPE;
	}

	public BigDecimal getAMOUNT() {
		return AMOUNT;
	}

	public void setAMOUNT(BigDecimal aMOUNT) {
		AMOUNT = aMOUNT;
	}

	public BigDecimal getPOINTS() {
		return POINTS;
	}

	public void setPOINTS(BigDecimal pOINTS) {
		POINTS = pOINTS;
	}

	public Timestamp getFROMDATE() {
		return FROMDATE;
	}

	public void setFROMDATE(Timestamp fROMDATE) {
		FROMDATE = fROMDATE;
	}

	public Timestamp getTODATE() {
		return TODATE;
	}

	public void setTODATE(Timestamp tODATE) {
		TODATE = tODATE;
	}

	public Date getCREATEDON() {
		return CREATEDON;
	}

	public void setCREATEDON(Date cREATEDON) {
		CREATEDON = cREATEDON;
	}

	public BigDecimal getCREATEDBY() {
		return CREATEDBY;
	}

	public void setCREATEDBY(BigDecimal cREATEDBY) {
		CREATEDBY = cREATEDBY;
	}

	public BigDecimal getSTATUSID() {
		return STATUSID;
	}

	public void setSTATUSID(BigDecimal sTATUSID) {
		STATUSID = sTATUSID;
	}

	public BigDecimal getAPPID() {
		return APPID;
	}

	public void setAPPID(BigDecimal aPPID) {
		APPID = aPPID;
	}

	public String getCUSTOMERNAME() {
		return CUSTOMERNAME;
	}

	public void setCUSTOMERNAME(String cUSTOMERNAME) {
		CUSTOMERNAME = cUSTOMERNAME;
	}

	public String getSTATUS() {
		return STATUS;
	}

	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}

	public String getSHORTNAME() {
		return SHORTNAME;
	}

	public void setSHORTNAME(String sHORTNAME) {
		SHORTNAME = sHORTNAME;
	}

	public String getRRN() {
		return RRN;
	}

	public void setRRN(String rRN) {
		RRN = rRN;
	}

	public String getDOCNAME() {
		return DOCNAME;
	}

	public void setDOCNAME(String dOCNAME) {
		DOCNAME = dOCNAME;
	}

	public Clob getCERTIFICATEOFINCORPORATIONIMG() {
		return CERTIFICATEOFINCORPORATIONIMG;
	}

	public void setCERTIFICATEOFINCORPORATIONIMG(Clob cERTIFICATEOFINCORPORATIONIMG) {
		CERTIFICATEOFINCORPORATIONIMG = cERTIFICATEOFINCORPORATIONIMG;
	}

	public String getCERTIFICATEOFINCORPORATION() {
		return CERTIFICATEOFINCORPORATION;
	}

	public void setCERTIFICATEOFINCORPORATION(String cERTIFICATEOFINCORPORATION) {
		CERTIFICATEOFINCORPORATION = cERTIFICATEOFINCORPORATION;
	}

	public Clob getASSOCIATIONANDARTICLESIMG() {
		return ASSOCIATIONANDARTICLESIMG;
	}

	public void setASSOCIATIONANDARTICLESIMG(Clob aSSOCIATIONANDARTICLESIMG) {
		ASSOCIATIONANDARTICLESIMG = aSSOCIATIONANDARTICLESIMG;
	}

	public String getASSOCIATIONANDARTICLES() {
		return ASSOCIATIONANDARTICLES;
	}

	public void setASSOCIATIONANDARTICLES(String aSSOCIATIONANDARTICLES) {
		ASSOCIATIONANDARTICLES = aSSOCIATIONANDARTICLES;
	}

	public Clob getRESOLUTIONOFTHEBOARDIMG() {
		return RESOLUTIONOFTHEBOARDIMG;
	}

	public void setRESOLUTIONOFTHEBOARDIMG(Clob rESOLUTIONOFTHEBOARDIMG) {
		RESOLUTIONOFTHEBOARDIMG = rESOLUTIONOFTHEBOARDIMG;
	}

	public String getRESOLUTIONOFTHEBOARD() {
		return RESOLUTIONOFTHEBOARD;
	}

	public void setRESOLUTIONOFTHEBOARD(String rESOLUTIONOFTHEBOARD) {
		RESOLUTIONOFTHEBOARD = rESOLUTIONOFTHEBOARD;
	}

	public Clob getPOWEROFATTORNEYGRANTEDIMG() {
		return POWEROFATTORNEYGRANTEDIMG;
	}

	public void setPOWEROFATTORNEYGRANTEDIMG(Clob pOWEROFATTORNEYGRANTEDIMG) {
		POWEROFATTORNEYGRANTEDIMG = pOWEROFATTORNEYGRANTEDIMG;
	}

	public String getPOWEROFATTORNEYGRANTED() {
		return POWEROFATTORNEYGRANTED;
	}

	public void setPOWEROFATTORNEYGRANTED(String pOWEROFATTORNEYGRANTED) {
		POWEROFATTORNEYGRANTED = pOWEROFATTORNEYGRANTED;
	}

	public String getREGISTEREDOFFICEPROOF() {
		return REGISTEREDOFFICEPROOF;
	}

	public void setREGISTEREDOFFICEPROOF(String rEGISTEREDOFFICEPROOF) {
		REGISTEREDOFFICEPROOF = rEGISTEREDOFFICEPROOF;
	}

	public Clob getREGISTEREDOFFICEIMGF() {
		return REGISTEREDOFFICEIMGF;
	}

	public void setREGISTEREDOFFICEIMGF(Clob rEGISTEREDOFFICEIMGF) {
		REGISTEREDOFFICEIMGF = rEGISTEREDOFFICEIMGF;
	}

	public Clob getPANCARDIMG() {
		return PANCARDIMG;
	}

	public void setPANCARDIMG(Clob pANCARDIMG) {
		PANCARDIMG = pANCARDIMG;
	}

	public String getPANCARD() {
		return PANCARD;
	}

	public void setPANCARD(String pANCARD) {
		PANCARD = pANCARD;
	}

	public Clob getAADHAARCARDIMG() {
		return AADHAARCARDIMG;
	}

	public void setAADHAARCARDIMG(Clob aADHAARCARDIMG) {
		AADHAARCARDIMG = aADHAARCARDIMG;
	}

	public String getAADHAARCARD() {
		return AADHAARCARD;
	}

	public void setAADHAARCARD(String aADHAARCARD) {
		AADHAARCARD = aADHAARCARD;
	}

	public Clob getIDENTIFICATIONPROOFIMG() {
		return IDENTIFICATIONPROOFIMG;
	}

	public void setIDENTIFICATIONPROOFIMG(Clob iDENTIFICATIONPROOFIMG) {
		IDENTIFICATIONPROOFIMG = iDENTIFICATIONPROOFIMG;
	}

	public String getIDENTIFICATIONPROOF() {
		return IDENTIFICATIONPROOF;
	}

	public void setIDENTIFICATIONPROOF(String iDENTIFICATIONPROOF) {
		IDENTIFICATIONPROOF = iDENTIFICATIONPROOF;
	}

	public String getMOBILE() {
		return MOBILE;
	}

	public void setMOBILE(String mOBILE) {
		MOBILE = mOBILE;
	}

	public String getREMARKS() {
		return REMARKS;
	}

	public void setREMARKS(String rEMARKS) {
		REMARKS = rEMARKS;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public BigDecimal getUserAction() {
		return userAction;
	}

	public void setUserAction(BigDecimal userAction) {
		this.userAction = userAction;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
