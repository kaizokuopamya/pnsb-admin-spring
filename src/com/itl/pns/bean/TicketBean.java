package com.itl.pns.bean;

import java.math.BigDecimal;
import java.sql.Clob;
import java.util.Date;

public class TicketBean {

	BigDecimal ID;

	BigDecimal CHANNELID;

	BigDecimal CUSTOMERID;

	BigDecimal PRODUCTID;

	String CATEGORYNAME;

	String DESCRIPTION;

	String EMAIL;

	String RESOLUTION;

	BigDecimal APPID;

	BigDecimal CREATEDBY;

	Date CREATEDON;

	BigDecimal UPDATEDBY;

	Date UPDATEDON;

	String THIRDPARTYREFERENCENO;

	BigDecimal STATUSID;

	String CUSTOMERNAME;

	String SHORTNAME;

	String STATUSNAME;

	String MOBILE;

	String EMAILOFCUST;

	Clob BASE64;

	String DOCNAME;

	String BASEIMAGE;

	BigDecimal DEVICEID;

	String MOBILEOFCUST;

	String DOB;

	String ASSIGNTO;

	String CIF;

	private BigDecimal user_ID;

	private BigDecimal role_ID;

	private BigDecimal subMenu_ID;

	private String remark;

	private String activityName;

	private BigDecimal userAction;

	private String productName;

	private String action;

	private String roleName;

	private String createdByName;

	public BigDecimal getID() {
		return ID;
	}

	public void setID(BigDecimal iD) {
		ID = iD;
	}

	public BigDecimal getCHANNELID() {
		return CHANNELID;
	}

	public void setCHANNELID(BigDecimal cHANNELID) {
		CHANNELID = cHANNELID;
	}

	public BigDecimal getCUSTOMERID() {
		return CUSTOMERID;
	}

	public void setCUSTOMERID(BigDecimal cUSTOMERID) {
		CUSTOMERID = cUSTOMERID;
	}

	public BigDecimal getPRODUCTID() {
		return PRODUCTID;
	}

	public void setPRODUCTID(BigDecimal pRODUCTID) {
		PRODUCTID = pRODUCTID;
	}

	public String getCATEGORYNAME() {
		return CATEGORYNAME;
	}

	public void setCATEGORYNAME(String cATEGORYNAME) {
		CATEGORYNAME = cATEGORYNAME;
	}

	public String getDESCRIPTION() {
		return DESCRIPTION;
	}

	public void setDESCRIPTION(String dESCRIPTION) {
		DESCRIPTION = dESCRIPTION;
	}

	public String getEMAIL() {
		return EMAIL;
	}

	public void setEMAIL(String eMAIL) {
		EMAIL = eMAIL;
	}

	public String getRESOLUTION() {
		return RESOLUTION;
	}

	public void setRESOLUTION(String rESOLUTION) {
		RESOLUTION = rESOLUTION;
	}

	public BigDecimal getAPPID() {
		return APPID;
	}

	public void setAPPID(BigDecimal aPPID) {
		APPID = aPPID;
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

	public BigDecimal getUPDATEDBY() {
		return UPDATEDBY;
	}

	public void setUPDATEDBY(BigDecimal uPDATEDBY) {
		UPDATEDBY = uPDATEDBY;
	}

	public Date getUPDATEDON() {
		return UPDATEDON;
	}

	public void setUPDATEDON(Date uPDATEDON) {
		UPDATEDON = uPDATEDON;
	}

	public String getTHIRDPARTYREFERENCENO() {
		return THIRDPARTYREFERENCENO;
	}

	public void setTHIRDPARTYREFERENCENO(String tHIRDPARTYREFERENCENO) {
		THIRDPARTYREFERENCENO = tHIRDPARTYREFERENCENO;
	}

	public BigDecimal getSTATUSID() {
		return STATUSID;
	}

	public void setSTATUSID(BigDecimal sTATUSID) {
		STATUSID = sTATUSID;
	}

	public String getCUSTOMERNAME() {
		return CUSTOMERNAME;
	}

	public void setCUSTOMERNAME(String cUSTOMERNAME) {
		CUSTOMERNAME = cUSTOMERNAME;
	}

	public String getSHORTNAME() {
		return SHORTNAME;
	}

	public void setSHORTNAME(String sHORTNAME) {
		SHORTNAME = sHORTNAME;
	}

	public String getSTATUSNAME() {
		return STATUSNAME;
	}

	public void setSTATUSNAME(String sTATUSNAME) {
		STATUSNAME = sTATUSNAME;
	}

	public String getMOBILE() {
		return MOBILE;
	}

	public void setMOBILE(String mOBILE) {
		MOBILE = mOBILE;
	}

	public String getEMAILOFCUST() {
		return EMAILOFCUST;
	}

	public void setEMAILOFCUST(String eMAILOFCUST) {
		EMAILOFCUST = eMAILOFCUST;
	}

	public Clob getBASE64() {
		return BASE64;
	}

	public void setBASE64(Clob bASE64) {
		BASE64 = bASE64;
	}

	public String getDOCNAME() {
		return DOCNAME;
	}

	public void setDOCNAME(String dOCNAME) {
		DOCNAME = dOCNAME;
	}

	public String getBASEIMAGE() {
		return BASEIMAGE;
	}

	public void setBASEIMAGE(String bASEIMAGE) {
		BASEIMAGE = bASEIMAGE;
	}

	public BigDecimal getDEVICEID() {
		return DEVICEID;
	}

	public void setDEVICEID(BigDecimal dEVICEID) {
		DEVICEID = dEVICEID;
	}

	public String getMOBILEOFCUST() {
		return MOBILEOFCUST;
	}

	public void setMOBILEOFCUST(String mOBILEOFCUST) {
		MOBILEOFCUST = mOBILEOFCUST;
	}

	public String getDOB() {
		return DOB;
	}

	public void setDOB(String dOB) {
		DOB = dOB;
	}

	public String getASSIGNTO() {
		return ASSIGNTO;
	}

	public void setASSIGNTO(String aSSIGNTO) {
		ASSIGNTO = aSSIGNTO;
	}

	public String getCIF() {
		return CIF;
	}

	public void setCIF(String cIF) {
		CIF = cIF;
	}

	public BigDecimal getUser_ID() {
		return user_ID;
	}

	public void setUser_ID(BigDecimal user_ID) {
		this.user_ID = user_ID;
	}

	public BigDecimal getRole_ID() {
		return role_ID;
	}

	public void setRole_ID(BigDecimal role_ID) {
		this.role_ID = role_ID;
	}

	public BigDecimal getSubMenu_ID() {
		return subMenu_ID;
	}

	public void setSubMenu_ID(BigDecimal subMenu_ID) {
		this.subMenu_ID = subMenu_ID;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public BigDecimal getUserAction() {
		return userAction;
	}

	public void setUserAction(BigDecimal userAction) {
		this.userAction = userAction;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	@Override
	public String toString() {
		return "TicketBean [ID=" + ID + ", CHANNELID=" + CHANNELID + ", CUSTOMERID=" + CUSTOMERID + ", PRODUCTID=" + PRODUCTID + ", CATEGORYNAME=" + CATEGORYNAME + ", DESCRIPTION=" + DESCRIPTION + ", EMAIL=" + EMAIL + ", RESOLUTION=" + RESOLUTION + ", APPID=" + APPID + ", CREATEDBY=" + CREATEDBY + ", CREATEDON=" + CREATEDON + ", UPDATEDBY=" + UPDATEDBY + ", UPDATEDON=" + UPDATEDON + ", THIRDPARTYREFERENCENO="
				+ THIRDPARTYREFERENCENO + ", STATUSID=" + STATUSID + ", CUSTOMERNAME=" + CUSTOMERNAME + ", SHORTNAME=" + SHORTNAME + ", STATUSNAME=" + STATUSNAME + ", MOBILE=" + MOBILE + ", EMAILOFCUST=" + EMAILOFCUST + ", BASE64=" + BASE64 + ", DOCNAME=" + DOCNAME + ", BASEIMAGE=" + BASEIMAGE + ", DEVICEID=" + DEVICEID + ", MOBILEOFCUST=" + MOBILEOFCUST + ", DOB=" + DOB + ", ASSIGNTO=" + ASSIGNTO + ", CIF=" + CIF
				+ ", user_ID=" + user_ID + ", role_ID=" + role_ID + ", subMenu_ID=" + subMenu_ID + ", remark=" + remark + ", activityName=" + activityName + ", userAction=" + userAction + ", productName=" + productName + ", action=" + action + ", roleName=" + roleName + ", createdByName=" + createdByName + "]";
	}

}
