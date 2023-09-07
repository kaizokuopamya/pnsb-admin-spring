package com.itl.pns.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Clob;
import java.util.Date;

public class UserDetailsBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String USERID;
	private String EMAIL;
	private String PHONENUMBER;
	private String CODE;
	private String ROLE;
	private String DESCRIPTION;
	private String ISACTIVE;
	private String ISDELETED;
	private BigDecimal USER_ID;
	private BigDecimal USER_DETAIL_ID;
	private BigDecimal ROLE_ID;
	private BigDecimal ROLETYPE;
	private BigDecimal ROLESTATUS;
	private String TOKEN;
	private Clob THUMBNAIL;
	private String THUMBNAILSTRING;
	private BigDecimal USERID1;
	Date USERLASTLOGIN;
	private String createdByName;
	private String USERTYPE;
	private String roleTypeName;
	private String FIRST_NAME;
	private String LAST_NAME;
	private BigDecimal STATUSID;
	private String LOGINTYPE;
	private BigDecimal CREATEDBY;
	private String latitude;
	private String longitude;
	private BigDecimal appId;
	private String sessionToken;
	private String deviceToken;
	private String clientIp;
	private String lanId;
	private String proxyId;
	private String hostName;
	private String userAgent;
	private String browser;
	private String os;
	private String ROLETYPENAME;
	private String OTP;
	private String MOBILENUMBER;

	private String BRANCHCODE;
	private String BRANCH_NAME;
	private String REPORTINGBRANCH;
	private String zonalcode;
	private String zonecode;
	// private String branchzone;
	private String branchCode;
	private String branchName;
	private String BRANCHZONE;
	private String REMARK;
	private int otpExpiryTime;
	private BigInteger userLoginDetailsId;

	public BigInteger getUserLoginDetailsId() {
		return userLoginDetailsId;
	}

	public void setUserLoginDetailsId(BigInteger userLoginDetailsId) {
		this.userLoginDetailsId = userLoginDetailsId;
	}

	public int getOtpExpiryTime() {
		return otpExpiryTime;
	}

	public void setOtpExpiryTime(int otpExpiryTime) {
		this.otpExpiryTime = otpExpiryTime;
	}

	public Date getUSERLASTLOGIN() {
		return USERLASTLOGIN;
	}

	public void setUSERLASTLOGIN(Date uSERLASTLOGIN) {
		USERLASTLOGIN = uSERLASTLOGIN;
	}

	public String getUSERID() {
		return USERID;
	}

	public void setUSERID(String uSERID) {
		USERID = uSERID;
	}

	public String getBRANCH_NAME() {
		return BRANCH_NAME;
	}

	public void setBRANCH_NAME(String bRANCH_NAME) {
		BRANCH_NAME = bRANCH_NAME;
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

	public String getCODE() {
		return CODE;
	}

	public void setCODE(String cODE) {
		CODE = cODE;
	}

	public String getROLE() {
		return ROLE;
	}

	public void setROLE(String rOLE) {
		ROLE = rOLE;
	}

	public String getDESCRIPTION() {
		return DESCRIPTION;
	}

	public void setDESCRIPTION(String dESCRIPTION) {
		DESCRIPTION = dESCRIPTION;
	}

	public String getISACTIVE() {
		return ISACTIVE;
	}

	public void setISACTIVE(String iSACTIVE) {
		ISACTIVE = iSACTIVE;
	}

	public String getISDELETED() {
		return ISDELETED;
	}

	public void setISDELETED(String iSDELETED) {
		ISDELETED = iSDELETED;
	}

	public BigDecimal getUSER_ID() {
		return USER_ID;
	}

	public void setUSER_ID(BigDecimal uSER_ID) {
		USER_ID = uSER_ID;
	}

	public BigDecimal getUSER_DETAIL_ID() {
		return USER_DETAIL_ID;
	}

	public void setUSER_DETAIL_ID(BigDecimal uSER_DETAIL_ID) {
		USER_DETAIL_ID = uSER_DETAIL_ID;
	}

	public BigDecimal getROLE_ID() {
		return ROLE_ID;
	}

	public void setROLE_ID(BigDecimal rOLE_ID) {
		ROLE_ID = rOLE_ID;
	}

	public BigDecimal getROLESTATUS() {
		return ROLESTATUS;
	}

	public void setROLESTATUS(BigDecimal rOLESTATUS) {
		ROLESTATUS = rOLESTATUS;
	}

	public String getTOKEN() {
		return TOKEN;
	}

	public void setTOKEN(String tOKEN) {
		TOKEN = tOKEN;
	}

	public Clob getTHUMBNAIL() {
		return THUMBNAIL;
	}

	public void setTHUMBNAIL(Clob tHUMBNAIL) {
		THUMBNAIL = tHUMBNAIL;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getTHUMBNAILSTRING() {
		return THUMBNAILSTRING;
	}

	public void setTHUMBNAILSTRING(String tHUMBNAILSTRING) {
		THUMBNAILSTRING = tHUMBNAILSTRING;
	}

	public BigDecimal getUSERID1() {
		return USERID1;
	}

	public void setUSERID1(BigDecimal uSERID1) {
		USERID1 = uSERID1;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public String getUSERTYPE() {
		return USERTYPE;
	}

	public void setUSERTYPE(String uSERTYPE) {
		USERTYPE = uSERTYPE;
	}

	public BigDecimal getROLETYPE() {
		return ROLETYPE;
	}

	public void setROLETYPE(BigDecimal rOLETYPE) {
		ROLETYPE = rOLETYPE;
	}

	public String getFIRST_NAME() {
		return FIRST_NAME;
	}

	public void setFIRST_NAME(String fIRST_NAME) {
		FIRST_NAME = fIRST_NAME;
	}

	public String getLAST_NAME() {
		return LAST_NAME;
	}

	public void setLAST_NAME(String lAST_NAME) {
		LAST_NAME = lAST_NAME;
	}

	public BigDecimal getSTATUSID() {
		return STATUSID;
	}

	public void setSTATUSID(BigDecimal sTATUSID) {
		STATUSID = sTATUSID;
	}

	public String getLOGINTYPE() {
		return LOGINTYPE;
	}

	public void setLOGINTYPE(String lOGINTYPE) {
		LOGINTYPE = lOGINTYPE;
	}

	public BigDecimal getCREATEDBY() {
		return CREATEDBY;
	}

	public void setCREATEDBY(BigDecimal cREATEDBY) {
		CREATEDBY = cREATEDBY;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public BigDecimal getAppId() {
		return appId;
	}

	public void setAppId(BigDecimal appId) {
		this.appId = appId;
	}

	public String getSessionToken() {
		return sessionToken;
	}

	public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}

	public String getDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public String getLanId() {
		return lanId;
	}

	public void setLanId(String lanId) {
		this.lanId = lanId;
	}

	public String getProxyId() {
		return proxyId;
	}

	public void setProxyId(String proxyId) {
		this.proxyId = proxyId;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getROLETYPENAME() {
		return ROLETYPENAME;
	}

	public void setROLETYPENAME(String rOLETYPENAME) {
		ROLETYPENAME = rOLETYPENAME;
	}

	public String getRoleTypeName() {
		return roleTypeName;
	}

	public void setRoleTypeName(String roleTypeName) {
		this.roleTypeName = roleTypeName;
	}

	public String getOTP() {
		return OTP;
	}

	public void setOTP(String oTP) {
		OTP = oTP;
	}

	public String getMOBILENUMBER() {
		return MOBILENUMBER;
	}

	public void setMOBILENUMBER(String mOBILENUMBER) {
		MOBILENUMBER = mOBILENUMBER;
	}

	public String getBRANCHCODE() {
		return BRANCHCODE;
	}

	public void setBRANCHCODE(String bRANCHCODE) {
		BRANCHCODE = bRANCHCODE;
	}

	public String getREPORTINGBRANCH() {
		return REPORTINGBRANCH;
	}

	public void setREPORTINGBRANCH(String rEPORTINGBRANCH) {
		REPORTINGBRANCH = rEPORTINGBRANCH;
	}

	public String getZonalcode() {
		return zonalcode;
	}

	public void setZonalcode(String zonalcode) {
		this.zonalcode = zonalcode;
	}

//	public String getBranchzone() {
//		return branchzone;
//	}
//
//	public void setBranchzone(String branchzone) {
//		this.branchzone = branchzone;
//	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getZonecode() {
		return zonecode;
	}

	public void setZonecode(String zonecode) {
		this.zonecode = zonecode;
	}

	public String getBRANCHZONE() {
		return BRANCHZONE;
	}

	public void setBRANCHZONE(String bRANCHZONE) {
		BRANCHZONE = bRANCHZONE;
	}

	public String getREMARK() {
		return REMARK;
	}

	public void setREMARK(String rEMARK) {
		REMARK = rEMARK;
	}

	@Override
	public String toString() {
		return "UserDetailsBean [USERID=" + USERID + ", EMAIL=" + EMAIL + ", PHONENUMBER=" + PHONENUMBER + ", CODE="
				+ CODE + ", ROLE=" + ROLE + ", DESCRIPTION=" + DESCRIPTION + ", ISACTIVE=" + ISACTIVE + ", ISDELETED="
				+ ISDELETED + ", USER_ID=" + USER_ID + ", USER_DETAIL_ID=" + USER_DETAIL_ID + ", ROLE_ID=" + ROLE_ID
				+ ", ROLETYPE=" + ROLETYPE + ", ROLESTATUS=" + ROLESTATUS + ", TOKEN=" + TOKEN + ", THUMBNAIL="
				+ THUMBNAIL + ", THUMBNAILSTRING=" + THUMBNAILSTRING + ", USERID1=" + USERID1 + ", USERLASTLOGIN="
				+ USERLASTLOGIN + ", createdByName=" + createdByName + ", USERTYPE=" + USERTYPE + ", roleTypeName="
				+ roleTypeName + ", FIRST_NAME=" + FIRST_NAME + ", LAST_NAME=" + LAST_NAME + ", STATUSID=" + STATUSID
				+ ", LOGINTYPE=" + LOGINTYPE + ", CREATEDBY=" + CREATEDBY + ", latitude=" + latitude + ", longitude="
				+ longitude + ", appId=" + appId + ", sessionToken=" + sessionToken + ", deviceToken=" + deviceToken
				+ ", clientIp=" + clientIp + ", lanId=" + lanId + ", proxyId=" + proxyId + ", hostName=" + hostName
				+ ", userAgent=" + userAgent + ", browser=" + browser + ", os=" + os + ", ROLETYPENAME=" + ROLETYPENAME
				+ ", OTP=" + OTP + ", MOBILENUMBER=" + MOBILENUMBER + ", BRANCHCODE=" + BRANCHCODE + ", BRANCH_NAME="
				+ BRANCH_NAME + ", REPORTINGBRANCH=" + REPORTINGBRANCH + ", zonalcode=" + zonalcode + ", zonecode="
				+ zonecode + ", branchCode=" + branchCode + ", branchName=" + branchName + ", BRANCHZONE=" + BRANCHZONE
				+ ", REMARK=" + REMARK + "]";
	}

}
