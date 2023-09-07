package com.itl.pns.bean;

import java.math.BigDecimal;
import java.sql.Clob;
import java.util.Date;

import javax.persistence.Transient;


public class AdminUserActivityLogs {

	private BigDecimal ID;	
	
	private String authorizationClob;
	private String authorization;
	private BigDecimal appID;
	private String channels;
	private Clob channelrequestClob;
	private String channelRequest;
	private String eventName;
	private String category;
	private Date eventTimestamp;
	private String level;
	private String operationId;
	private String operationName;
	private String properties;
	private Clob propertiesClob;
	private String Lat;
	private String Lon;
	private String Browser;
	private String Device;
	private String OS;
	private BigDecimal CREATEDBY;
	private Date CREATEDON;
	private BigDecimal STATUSID;
	private BigDecimal CHANNELID;
	private BigDecimal UPDATEDBY;
	private Date  UPDATEDON;
	private BigDecimal USERID;
	private String IP;
	private String CREATEDBYNAME;
	private String channelName;
	private String  action;
	private String X_FORWARDEDIP;
	private String roleName;
	
 
	public String getX_FORWARDEDIP() {
		return X_FORWARDEDIP;
	}
	public void setX_FORWARDEDIP(String x_FORWARDEDIP) {
		X_FORWARDEDIP = x_FORWARDEDIP;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	@Transient
	private String userName;
	
	
	
	public BigDecimal getID() {
		return ID;
	}
	public void setID(BigDecimal iD) {
		ID = iD;
	}
	public String getAuthorization() {
		return authorization;
	}
	public void setAuthorization(String authorization) {
		this.authorization = authorization;
	}
	public BigDecimal getAppID() {
		return appID;
	}
	public void setAppID(BigDecimal appID) {
		this.appID = appID;
	}
	public String getChannels() {
		return channels;
	}
	public void setChannels(String channels) {
		this.channels = channels;
	}
	
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Date getEventTimestamp() {
		return eventTimestamp;
	}
	public void setEventTimestamp(Date eventTimestamp) {
		this.eventTimestamp = eventTimestamp;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getOperationId() {
		return operationId;
	}
	public void setOperationId(String operationId) {
		this.operationId = operationId;
	}
	public String getOperationName() {
		return operationName;
	}
	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public String getLat() {
		return Lat;
	}
	public void setLat(String lat) {
		Lat = lat;
	}
	public String getLon() {
		return Lon;
	}
	public void setLon(String lon) {
		Lon = lon;
	}
	public String getBrowser() {
		return Browser;
	}
	public void setBrowser(String browser) {
		Browser = browser;
	}
	public String getDevice() {
		return Device;
	}
	public void setDevice(String device) {
		Device = device;
	}
	public String getOS() {
		return OS;
	}
	public void setOS(String oS) {
		OS = oS;
	}
	
	public Date getCREATEDON() {
		return CREATEDON;
	}
	public void setCREATEDON(Date cREATEDON) {
		CREATEDON = cREATEDON;
	}
	public BigDecimal getSTATUSID() {
		return STATUSID;
	}
	public void setSTATUSID(BigDecimal sTATUSID) {
		STATUSID = sTATUSID;
	}
	public BigDecimal getCHANNELID() {
		return CHANNELID;
	}
	public void setCHANNELID(BigDecimal cHANNELID) {
		CHANNELID = cHANNELID;
	}
	
	public BigDecimal getCREATEDBY() {
		return CREATEDBY;
	}
	public void setCREATEDBY(BigDecimal cREATEDBY) {
		CREATEDBY = cREATEDBY;
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
	public BigDecimal getUSERID() {
		return USERID;
	}
	public void setUSERID(BigDecimal uSERID) {
		USERID = uSERID;
	}
	public String getIP() {
		return IP;
	}
	public void setIP(String iP) {
		IP = iP;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCREATEDBYNAME() {
		return CREATEDBYNAME;
	}
	public void setCREATEDBYNAME(String cREATEDBYNAME) {
		CREATEDBYNAME = cREATEDBYNAME;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getChannelRequest() {
		return channelRequest;
	}
	public void setChannelRequest(String channelRequest) {
		this.channelRequest = channelRequest;
	}
	public String getProperties() {
		return properties;
	}
	public void setProperties(String properties) {
		this.properties = properties;
	}
	public Clob getPropertiesClob() {
		return propertiesClob;
	}
	public void setPropertiesClob(Clob propertiesClob) {
		this.propertiesClob = propertiesClob;
	}
	public String getAuthorizationClob() {
		return authorizationClob;
	}
	public void setAuthorizationClob(String authorizationClob) {
		this.authorizationClob = authorizationClob;
	}
	public Clob getChannelrequestClob() {
		return channelrequestClob;
	}
	public void setChannelrequestClob(Clob channelrequestClob) {
		this.channelrequestClob = channelrequestClob;
	}

	
	
}
