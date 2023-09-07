package com.itl.pns.bean;

import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties
public class CreateCustResponse {

	@JsonProperty("entityId")
	private String entityId;

	@JsonProperty("deviceId")
	private String deviceId;

	@JsonProperty("actionId")
	private String actionId;

	@JsonProperty("subActionId")
	private String subActionId;

	@JsonProperty("RRN")
	private String RRN;

	@JsonProperty("ResponseParameterObject")
	ResponseParameter ResponseParameterObject;

	@SuppressWarnings("rawtypes")
	Set SetObject;

	@SuppressWarnings("rawtypes")
	@JsonProperty("MapObject")
	Map MapObject;

	// Getter Methods

	@SuppressWarnings("rawtypes")
	public Set getSet() {
		return SetObject;
	}

	@SuppressWarnings("rawtypes")
	public void setSet(Set setObject) {
		this.SetObject = setObject;
	}

	@SuppressWarnings("rawtypes")
	public Map getMap() {
		return MapObject;
	}

	public String getEntityId() {
		return entityId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public String getActionId() {
		return actionId;
	}

	public String getSubActionId() {
		return subActionId;
	}

	public ResponseParameter getResponseParameter() {
		return ResponseParameterObject;
	}

	// Setter Methods

	@SuppressWarnings("rawtypes")
	public void setMap(Map mapObject) {
		this.MapObject = mapObject;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public void setActionId(String actionId) {
		this.actionId = actionId;
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

	public void setResponseParameter(ResponseParameter responseParameterObject) {
		this.ResponseParameterObject = responseParameterObject;
	}

	@Override
	public String toString() {
		return "CreateCustResponse [entityId=" + entityId + ", deviceId=" + deviceId + ", actionId=" + actionId + ", subActionId=" + subActionId + ", RRN=" + RRN + ", ResponseParameterObject=" + ResponseParameterObject + ", SetObject=" + SetObject + ", MapObject=" + MapObject + "]";
	}
}
