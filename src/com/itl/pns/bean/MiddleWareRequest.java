package com.itl.pns.bean;

import java.util.HashMap;
import java.util.Map;

public class MiddleWareRequest {

	String entityId;
	String deviceId;
	String sessionId;
	int activityId;
	int appId;
	int custId;
	int otpLength;
	String secretKey;
	String SecretKeyType;
	String subActionId;
	String RRN;
	Map<String, String> map = new HashMap<String, String>();
	int deviceDbId;
	int requestFromApp;
	String thirdPartyRefNo;
	String prefered_Language;
	public String getEntityId() {
		return entityId;
	}
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public int getActivityId() {
		return activityId;
	}
	public void setActivityId(int activityId) {
		this.activityId = activityId;
	}
	public int getAppId() {
		return appId;
	}
	public void setAppId(int appId) {
		this.appId = appId;
	}
	public int getCustId() {
		return custId;
	}
	public void setCustId(int custId) {
		this.custId = custId;
	}
	public int getOtpLength() {
		return otpLength;
	}
	public void setOtpLength(int otpLength) {
		this.otpLength = otpLength;
	}
	public String getSecretKey() {
		return secretKey;
	}
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
	public String getSecretKeyType() {
		return SecretKeyType;
	}
	public void setSecretKeyType(String secretKeyType) {
		SecretKeyType = secretKeyType;
	}
	public String getSubActionId() {
		return subActionId;
	}
	public void setSubActionId(String subActionId) {
		this.subActionId = subActionId;
	}
	public String getRRN() {
		return RRN;
	}
	public void setRRN(String rRN) {
		RRN = rRN;
	}
	public Map<String, String> getMap() {
		return map;
	}
	public void setMap(Map<String, String> map) {
		this.map = map;
	}
	public int getDeviceDbId() {
		return deviceDbId;
	}
	public void setDeviceDbId(int deviceDbId) {
		this.deviceDbId = deviceDbId;
	}
	public int getRequestFromApp() {
		return requestFromApp;
	}
	public void setRequestFromApp(int requestFromApp) {
		this.requestFromApp = requestFromApp;
	}
	public String getThirdPartyRefNo() {
		return thirdPartyRefNo;
	}
	public void setThirdPartyRefNo(String thirdPartyRefNo) {
		this.thirdPartyRefNo = thirdPartyRefNo;
	}
	public String getPrefered_Language() {
		return prefered_Language;
	}
	public void setPrefered_Language(String prefered_Language) {
		this.prefered_Language = prefered_Language;
	}
	
	
}
