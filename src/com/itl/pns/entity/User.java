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

@Entity
@Table(name = "USER_MASTER")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
	@SequenceGenerator(name = "id_Sequence", sequenceName = "user_master_ID_SEQ", allocationSize = 1)
	@Column(name = "ID")
	private BigInteger id;

	@Column(name = "USERID")
	private String userid;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "CREATEDBY")
	private String createdBy;

	@Column(name = "CREATEDON")
	private Date createdOn;

	@Column(name = "UPDATEBY")
	private String updatedBy;

	@Column(name = "UPDATEON")
	private Date updatedOn;

	@Column(name = "ISDELETED")
	private char isdeleted;

	@Column(name = "WRONG_ATTEMPTS")
	private BigDecimal wrong_attempts;

	@Column(name = "BRANCHCODE")
	private String branchCode;

	@Column(name = "BRANCH_NAME")
	private String BRANCH_NAME;

	@Transient
	private String otpExpiryTime;

	@Transient
	private BigInteger userLoginDetailsId;

	public BigInteger getUserLoginDetailsId() {
		return userLoginDetailsId;
	}

	public void setUserLoginDetailsId(BigInteger userLoginDetailsId) {
		this.userLoginDetailsId = userLoginDetailsId;
	}

	public String getOtpExpiryTime() {
		return otpExpiryTime;
	}

	public void setOtpExpiryTime(String otpExpiryTime) {
		this.otpExpiryTime = otpExpiryTime;
	}

	public String getBRANCH_NAME() {
		return BRANCH_NAME;
	}

	public void setBRANCH_NAME(String bRANCH_NAME) {
		BRANCH_NAME = bRANCH_NAME;
	}

	@Column(nullable = true, name = "REPORTINGBRANCH")
	private String reportingBranch;

	/*
	 * @Column(name="ISACTIVE") private String isActive;
	 * 
	 * 
	 * 
	 * /*@Column(name="ENCPASSWORD") private String encpassword;
	 */

	/*
	 * @Column(name="TEMPLATE_DATA") private String templateData;
	 */

	@Column(name = "THUMBNAIL")
	private String thumbnail;

	@Column(name = "STATUSID")
	private BigInteger statusId;

	@Transient
	String thumbnailString;

	@Transient
	String biometricflag;

	@Transient
	String otp;

	@Column(name = "USERLASTLOGIN")
	private Date userlastlogin;

	/*
	 * @Column(name="USERTYPE") private String userType;
	 */

	@Column(name = "LOGINTYPE")
	private String loginType;

	@Column(name = "REMARK")
	private String remark;

	@Transient
	String base;

	@Transient
	private String latitude;

	@Transient
	private String longitude;

	@Transient
	private String clientIp;

	@Transient
	private String lanId;

	@Transient
	private String proxyId;

	@Transient
	private String hostName;

	@Transient
	private String userAgent;

	@Transient
	private String browser;

	@Transient
	private String os;

	@Transient
	private String resendOtpAttempt;

	@Transient
	private String PHONENUMBER;

	@Transient
	private String EMAIL;

	@Transient
	private String plainPassword;

	@Transient
	private String zonalcode;

	@Transient
	private String otpValidationString;

	public String getPlainPassword() {
		return plainPassword;
	}

	public void setPlainPassword(String plainPassword) {
		this.plainPassword = plainPassword;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	/*
	 * public String getIsActive() { return isActive; }
	 * 
	 * public void setIsActive(String isActive) { this.isActive = isActive; }
	 * 
	 * 
	 * /* public String getEncpassword() { return encpassword; }
	 * 
	 * public void setEncpassword(String encpassword) { this.encpassword =
	 * encpassword; }
	 */

	/*
	 * public String getTemplateData() { return templateData; }
	 * 
	 * public void setTemplateData(String templateData) { this.templateData =
	 * templateData; }
	 * 
	 */

	public String getBiometricflag() {
		return biometricflag;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public void setBiometricflag(String biometricflag) {
		this.biometricflag = biometricflag;
	}

	public Date getUserlastlogin() {
		return userlastlogin;
	}

	public void setUserlastlogin(Date userlastlogin) {
		this.userlastlogin = userlastlogin;
	}

	public String getThumbnailString() {
		return thumbnailString;
	}

	public void setThumbnailString(String thumbnailString) {
		this.thumbnailString = thumbnailString;
	}

	/*
	 * public String getUserType() { return userType; }
	 * 
	 * public void setUserType(String userType) { this.userType = userType; }
	 */

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public BigInteger getStatusId() {
		return statusId;
	}

	public void setStatusId(BigInteger statusId) {
		this.statusId = statusId;
	}

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
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

	public char getIsdeleted() {
		return isdeleted;
	}

	public void setIsdeleted(char isdeleted) {
		this.isdeleted = isdeleted;
	}

	public BigDecimal getWrong_attempts() {
		return wrong_attempts;
	}

	public void setWrong_attempts(BigDecimal wrong_attempts) {
		this.wrong_attempts = wrong_attempts;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public String getResendOtpAttempt() {
		return resendOtpAttempt;
	}

	public void setResendOtpAttempt(String resendOtpAttempt) {
		this.resendOtpAttempt = resendOtpAttempt;
	}

	public String getPHONENUMBER() {
		return PHONENUMBER;
	}

	public void setPHONENUMBER(String pHONENUMBER) {
		PHONENUMBER = pHONENUMBER;
	}

	public String getEMAIL() {
		return EMAIL;
	}

	public void setEMAIL(String eMAIL) {
		EMAIL = eMAIL;
	}

	public String getReportingBranch() {
		return reportingBranch;
	}

	public void setReportingBranch(String reportingBranch) {
		this.reportingBranch = reportingBranch;
	}

	public String getZonalcode() {
		return zonalcode;
	}

	public void setZonalcode(String zonalcode) {
		this.zonalcode = zonalcode;
	}

	public String getOtpValidationString() {
		return otpValidationString;
	}

	public void setOtpValidationString(String otpValidationString) {
		this.otpValidationString = otpValidationString;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
