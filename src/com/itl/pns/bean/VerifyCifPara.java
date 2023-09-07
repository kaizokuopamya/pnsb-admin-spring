package com.itl.pns.bean;

public class VerifyCifPara {

	private String entityId;
	private String cbsType;
	private String mobPlatform;
	private String mobileAppVersion;
	private String clientAppVer;
	private String corpCompId;
	private String latitude;
	private String longitute;
	private String MobileNo;
	private String referenceNumber;
	private String RRN;
	private String userId;
	private String UserID;

	public String getUserID() {
		return UserID;
	}

	public void setUserID(String userID) {
		UserID = userID;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getCbsType() {
		return cbsType;
	}

	public void setCbsType(String cbsType) {
		this.cbsType = cbsType;
	}

	public String getMobPlatform() {
		return mobPlatform;
	}

	public void setMobPlatform(String mobPlatform) {
		this.mobPlatform = mobPlatform;
	}

	public String getMobileAppVersion() {
		return mobileAppVersion;
	}

	public void setMobileAppVersion(String mobileAppVersion) {
		this.mobileAppVersion = mobileAppVersion;
	}

	public String getClientAppVer() {
		return clientAppVer;
	}

	public void setClientAppVer(String clientAppVer) {
		this.clientAppVer = clientAppVer;
	}

	public String getCorpCompId() {
		return corpCompId;
	}

	public void setCorpCompId(String corpCompId) {
		this.corpCompId = corpCompId;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitute() {
		return longitute;
	}

	public void setLongitute(String longitute) {
		this.longitute = longitute;
	}

	public String getMobileNo() {
		return MobileNo;
	}

	public void setMobileNo(String mobileNo) {
		MobileNo = mobileNo;
	}

	public String getReferenceNumber() {
		return referenceNumber;
	}

	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	public String getRRN() {
		return RRN;
	}

	public void setRRN(String rRN) {
		RRN = rRN;
	}

	@Override
	public String toString() {
		return "VerifyCifPara [entityId=" + entityId + ", cbsType=" + cbsType + ", mobPlatform=" + mobPlatform
				+ ", mobileAppVersion=" + mobileAppVersion + ", clientAppVer=" + clientAppVer + ", corpCompId="
				+ corpCompId + ", latitude=" + latitude + ", longitute=" + longitute + ", MobileNo=" + MobileNo
				+ ", referenceNumber=" + referenceNumber + ", RRN=" + RRN + ", userId=" + userId + ", UserID=" + UserID
				+ "]";
	}

}
