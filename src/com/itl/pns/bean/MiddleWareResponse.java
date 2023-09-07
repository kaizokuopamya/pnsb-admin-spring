package com.itl.pns.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MiddleWareResponse {

	String entityId;
	String deviceId;
	String sessionId;
	String actionId;
	String subActionId;
	String RRN;
	String responseCode;
	String encryptionKey;
	String responseDescription;
	DataSet set;
	Map<String, String> responseParameter = new HashMap<String, String>();
	List<DataSet> listofDataset;
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
	public String getActionId() {
		return actionId;
	}
	public void setActionId(String actionId) {
		this.actionId = actionId;
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
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public String getEncryptionKey() {
		return encryptionKey;
	}
	public void setEncryptionKey(String encryptionKey) {
		this.encryptionKey = encryptionKey;
	}
	public String getResponseDescription() {
		return responseDescription;
	}
	public void setResponseDescription(String responseDescription) {
		this.responseDescription = responseDescription;
	}
	public Map<String, String> getResponseParameter() {
		return responseParameter;
	}
	public void setResponseParameter(Map<String, String> responseParameter) {
		this.responseParameter = responseParameter;
	}
	public DataSet getSet() {
		return set;
	}
	public void setSet(DataSet set) {
		this.set = set;
	}
	public List<DataSet> getListofDataset() {
		return listofDataset;
	}
	public void setListofDataset(List<DataSet> listofDataset) {
		this.listofDataset = listofDataset;
	}
	
	
}
