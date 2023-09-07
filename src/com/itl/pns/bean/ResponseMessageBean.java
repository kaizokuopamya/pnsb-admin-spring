package com.itl.pns.bean;

public class ResponseMessageBean {
	
	private String responseCode;
	
	private String responseMessage;
/*	
	String deviceId;
	
	String secretKey;
	
	String SecretKeyType;*/
	
	private Object result;

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "ResponseMessageBean [responseCode=" + responseCode + ", responseMessage=" + responseMessage
				+ ", result=" + result + "]";
	}

	/*public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
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
	*/

	
	
}
