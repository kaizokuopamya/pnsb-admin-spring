package com.itl.pns.bean;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class UserAddEditRequestBean {

	private BigInteger USERMASTERID;
	private String USERID;
	private String ISACTIVE;
	private BigInteger CREATEDBY;
	private Date CREATEDON;
	private String UPDATEBY;
	private Date UPDATEON;
	private String ENCPASSWORD;
	private BigInteger USERDETAILSID;
	private BigInteger ROLEID;
	private String CODE;
	private String DISPLAYNAME;// user name
	private String FIRSTNAME;
	private String LASTNAME;
	private String EMAIL;
	private String PHONENUMBER;
	private String TEMPLATE;
	private String THUMBNAIL;
	private String ROLENAME;
	private String thumbnailImage;
	private BigInteger statusId;
	private String LOGINTYPE;
	private String mobileNumber;
	private BigDecimal updatedBy;
    private String reportingBranch;
    private String branchcode;
    private String zonalcode;
    private String usersType;
    private String userreportingbranch;
    private String userbranch;
    private String Remark;
    

	public String getUSERID() {
		return USERID;
	}

	public void setUSERID(String uSERID) {
		USERID = uSERID;
	}

	public String getISACTIVE() {
		return ISACTIVE;
	}

	public void setISACTIVE(String iSACTIVE) {
		ISACTIVE = iSACTIVE;
	}

	public BigInteger getCREATEDBY() {
		return CREATEDBY;
	}

	public void setCREATEDBY(BigInteger cREATEDBY) {
		CREATEDBY = cREATEDBY;
	}

	public Date getCREATEDON() {
		return CREATEDON;
	}

	public void setCREATEDON(Date cREATEDON) {
		CREATEDON = cREATEDON;
	}

	public Date getUPDATEON() {
		return UPDATEON;
	}

	public void setUPDATEON(Date uPDATEON) {
		UPDATEON = uPDATEON;
	}

	public String getENCPASSWORD() {
		return ENCPASSWORD;
	}

	public void setENCPASSWORD(String eNCPASSWORD) {
		ENCPASSWORD = eNCPASSWORD;
	}

	public BigInteger getUSERMASTERID() {
		return USERMASTERID;
	}

	public void setUSERMASTERID(BigInteger uSERMASTERID) {
		USERMASTERID = uSERMASTERID;
	}

	public BigInteger getUSERDETAILSID() {
		return USERDETAILSID;
	}

	public void setUSERDETAILSID(BigInteger uSERDETAILSID) {
		USERDETAILSID = uSERDETAILSID;
	}

	public BigInteger getROLEID() {
		return ROLEID;
	}

	public void setROLEID(BigInteger rOLEID) {
		ROLEID = rOLEID;
	}

	public String getCODE() {
		return CODE;
	}

	public void setCODE(String cODE) {
		CODE = cODE;
	}

	public String getDISPLAYNAME() {
		return DISPLAYNAME;
	}

	public void setDISPLAYNAME(String dISPLAYNAME) {
		DISPLAYNAME = dISPLAYNAME;
	}

	public String getFIRSTNAME() {
		return FIRSTNAME;
	}

	public void setFIRSTNAME(String fIRSTNAME) {
		FIRSTNAME = fIRSTNAME;
	}

	public String getLASTNAME() {
		return LASTNAME;
	}

	public void setLASTNAME(String lASTNAME) {
		LASTNAME = lASTNAME;
	}

	public String getEMAIL() {
		return EMAIL;
	}

	public void setEMAIL(String eMAIL) {
		EMAIL = eMAIL;
	}

	public String getPHONENUMBER() {
		return PHONENUMBER;
	}

	public void setPHONENUMBER(String pHONENUMBER) {
		PHONENUMBER = pHONENUMBER;
	}

	public String getTEMPLATE() {
		return TEMPLATE;
	}

	public void setTEMPLATE(String tEMPLATE) {
		TEMPLATE = tEMPLATE;
	}

	public String getTHUMBNAIL() {
		return THUMBNAIL;
	}

	public void setTHUMBNAIL(String tHUMBNAIL) {
		THUMBNAIL = tHUMBNAIL;
	}

	public String getROLENAME() {
		return ROLENAME;
	}

	public void setROLENAME(String rOLENAME) {
		ROLENAME = rOLENAME;
	}

	public String getThumbnailImage() {
		return thumbnailImage;
	}

	public void setThumbnailImage(String thumbnailImage) {
		this.thumbnailImage = thumbnailImage;
	}

	public BigInteger getStatusId() {
		return statusId;
	}

	public void setStatusId(BigInteger statusId) {
		this.statusId = statusId;
	}

	public String getLOGINTYPE() {
		return LOGINTYPE;
	}

	public void setLOGINTYPE(String lOGINTYPE) {
		LOGINTYPE = lOGINTYPE;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getUPDATEBY() {
		return UPDATEBY;
	}

	public void setUPDATEBY(String uPDATEBY) {
		UPDATEBY = uPDATEBY;
	}

	public BigDecimal getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(BigDecimal updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getReportingBranch() {
		return reportingBranch;
	}

	public void setReportingBranch(String reportingBranch) {
		this.reportingBranch = reportingBranch;
	}

	public String getBranchcode() {
		return branchcode;
	}

	public void setBranchcode(String branchcode) {
		this.branchcode = branchcode;
	}

	public String getZonalcode() {
		return zonalcode;
	}

	public void setZonalcode(String zonalcode) {
		this.zonalcode = zonalcode;
	}

	public String getUsersType() {
		return usersType;
	}

	public void setUsersType(String usersType) {
		this.usersType = usersType;
	}

	public String getUserreportingbranch() {
		return userreportingbranch;
	}

	public void setUserreportingbranch(String userreportingbranch) {
		this.userreportingbranch = userreportingbranch;
	}

	public String getUserbranch() {
		return userbranch;
	}

	public void setUserbranch(String userbranch) {
		this.userbranch = userbranch;
	}

	public String getRemark() {
		return Remark;
	}

	public void setRemark(String remark) {
		Remark = remark;
	}

	
	
	

}
