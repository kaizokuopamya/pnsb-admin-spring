package com.itl.pns.bean;

import java.math.BigInteger;
import java.util.Date;

public class AdminPortalUserActivityLogs {

	private int ID;

	private String channelName;
	private String channelRequest;
	private String eventName;
	private String category;
	private String action;
	private String properties;
	private String IP;
	private String X_FORWARDEDIP;
	private String Lat;
	private String Lon;
	private String Browser;
	private String Device;
	private String OS;
	private int CREATEDBY;
	private String CREATEDBYNAME;
	private Date CREATEDON;
	private int UPDATEDBY;
	private String UPDATEDBYNAME;
	private Date UPDATEDON;
	private String authorization;
	private BigInteger userLoginDetailsId;

	public BigInteger getUserLoginDetailsId() {
		return userLoginDetailsId;
	}

	public void setUserLoginDetailsId(BigInteger userLoginDetailsId) {
		this.userLoginDetailsId = userLoginDetailsId;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getChannelRequest() {
		return channelRequest;
	}

	public void setChannelRequest(String channelRequest) {
		this.channelRequest = channelRequest;
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

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getProperties() {
		return properties;
	}

	public void setProperties(String properties) {
		this.properties = properties;
	}

	public String getIP() {
		return IP;
	}

	public void setIP(String iP) {
		IP = iP;
	}

	public String getX_FORWARDEDIP() {
		return X_FORWARDEDIP;
	}

	public void setX_FORWARDEDIP(String x_FORWARDEDIP) {
		X_FORWARDEDIP = x_FORWARDEDIP;
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

	public int getCREATEDBY() {
		return CREATEDBY;
	}

	public void setCREATEDBY(int cREATEDBY) {
		CREATEDBY = cREATEDBY;
	}

	public String getCREATEDBYNAME() {
		return CREATEDBYNAME;
	}

	public void setCREATEDBYNAME(String cREATEDBYNAME) {
		CREATEDBYNAME = cREATEDBYNAME;
	}

	public Date getCREATEDON() {
		return CREATEDON;
	}

	public void setCREATEDON(Date cREATEDON) {
		CREATEDON = cREATEDON;
	}

	public int getUPDATEDBY() {
		return UPDATEDBY;
	}

	public void setUPDATEDBY(int uPDATEDBY) {
		UPDATEDBY = uPDATEDBY;
	}

	public String getUPDATEDBYNAME() {
		return UPDATEDBYNAME;
	}

	public void setUPDATEDBYNAME(String uPDATEDBYNAME) {
		UPDATEDBYNAME = uPDATEDBYNAME;
	}

	public Date getUPDATEDON() {
		return UPDATEDON;
	}

	public void setUPDATEDON(Date uPDATEDON) {
		UPDATEDON = uPDATEDON;
	}

	public String getAuthorization() {
		return authorization;
	}

	public void setAuthorization(String authorization) {
		this.authorization = authorization;
	}

}
