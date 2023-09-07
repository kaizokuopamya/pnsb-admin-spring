package com.itl.pns.entity;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author shubham.lokhande
 *
 */
@Entity
@Table(name = "ADMINUSRSESSIONS")
public class AdminUserSessionsEntity {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
	@SequenceGenerator(name = "id_Sequence", sequenceName = "adminusrsessions_ID_SEQ", allocationSize = 1)
	BigDecimal id;

	@Column(name = "SESSIONTOKEN")
	private String sessionToken;

	@Column(name = "DEVICETOKEN")
	private String deviceToken;

	@Column(name = "STARTTIME")
	private Date startTime;

	@Column(name = "ENDTIME")
	private Date endTime;

	@Column(name = "ENDBY")
	private BigDecimal endBy;

	@Column(name = "USERID")
	private BigDecimal userId;

	@Column(name = "CLIENTIP")
	private String clientIp;

	@Column(name = "LANID")
	private String lanId;

	@Column(name = "PROXYID")
	private String proxyId;

	@Column(name = "HOSTNAME")
	private String hostName;

	@Column(name = "USERAGENT")
	private String userAgent;

	@Column(name = "BROWSER")
	private String browser;

	@Column(name = "OS")
	private String os;

	@Column(name = "LATITUDE")
	private String latitude;

	@Column(name = "LONGITUDE")
	private String longitude;

	@Column(name = "CREATEDBY")
	private BigDecimal cretedBy;

	@Column(name = "CREATEDON")
	private Date createdOn;

	@Column(name = "UPDATEDBY")
	private BigDecimal updatedBy;

	@Column(name = "UPDATEDON")
	private Date updatedOn;

	@Column(name = "STATUSID")
	private BigDecimal statusId;

	@Column(name = "OTP")
	private String otp;

	@Column(name = "WRONGATTEMPTSOTP")
	private String wrongattemptsotp;

	@Column(name = "RESENDOTPATTEMPT")
	private String resendotpattempt;

	@Column(name = "BRANCHCODE")
	private String branchCode;

	@Column(name = "USER_LOGIN_DETAILS_ID")
	private BigInteger userLoginDetailsId;

	@Transient
	private String userName;

	public BigInteger getUserLoginDetailsId() {
		return userLoginDetailsId;
	}

	public void setUserLoginDetailsId(BigInteger userLoginDetailsId) {
		this.userLoginDetailsId = userLoginDetailsId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public BigDecimal getEndBy() {
		return endBy;
	}

	public void setEndBy(BigDecimal endBy) {
		this.endBy = endBy;
	}

	public BigDecimal getUserId() {
		return userId;
	}

	public void setUserId(BigDecimal userId) {
		this.userId = userId;
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

	public BigDecimal getCretedBy() {
		return cretedBy;
	}

	public void setCretedBy(BigDecimal cretedBy) {
		this.cretedBy = cretedBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public BigDecimal getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(BigDecimal updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public BigDecimal getStatusId() {
		return statusId;
	}

	public void setStatusId(BigDecimal statusId) {
		this.statusId = statusId;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public BigDecimal getId() {
		return id;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public String getWrongattemptsotp() {
		return wrongattemptsotp;
	}

	public void setWrongattemptsotp(String wrongattemptsotp) {
		this.wrongattemptsotp = wrongattemptsotp;
	}

	public String getResendotpattempt() {
		return resendotpattempt;
	}

	public void setResendotpattempt(String resendotpattempt) {
		this.resendotpattempt = resendotpattempt;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

}
