package com.itl.pns.corp.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author shubham.lokhande
 *
 */
@Entity
@Table(name = "CORP_USERS")
public class CorpUserNewEntity {

	@javax.persistence.Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "corp_users_ID_SEQ")
	@SequenceGenerator(name = "corp_users_ID_SEQ", sequenceName = "corp_users_ID_SEQ", allocationSize = 1)
	BigDecimal id;

	@Column(name = "CORP_COMP_ID")
	BigDecimal corp_comp_id;

	@Column(name = "USER_DISP_NAME")
	String user_disp_name;

	@Column(name = "USER_NAME")
	String user_name;

	@Column(name = "CREATEDBY")
	BigDecimal createdby;

	@Column(name = "CREATEDON")
	Timestamp createdon;

	@Column(name = "STATUSID")
	BigDecimal statusid;

	@Column(name = "APPID")
	BigDecimal appid;

	@Column(name = "LASTLOGINTIME")
	Date lastLoginTime;

	@Column(name = "USER_TYPE")
	BigDecimal user_type;

	@Column(name = "FIRST_NAME")
	String first_name;

	@Column(name = "LAST_NAME")
	String last_name;

	@Column(name = "EMAIL_ID")
	String email_id;

	@Column(name = "COUNTRY")
	BigDecimal country;

	@Column(name = "WORK_PHONE")
	String work_phone;

	@Column(name = "PERSONAL_PHONE")
	String personal_Phone;

	@Column(name = "NATIONALID")
	String nationalId;

	@Column(name = "PASSPORT")
	String passport;

	@Column(name = "AADHARCARDNO")
	private String aadharCardNo;

	@Column(name = "BOARDRESOLUTION")
	String boardResolution;

	@Column(name = "USER_IMAGE")
	String user_image;

	/*
	 * @Column(name = "TPIN") String tpin;
	 */

	@Column(name = "PASSPORTNUMBER")
	String passportNumber;

	@Column(name = "NATIONALIDNUMBER")
	String nationalIdNumber;

	@Column(name = "TPIN_STATUS")
	String tpin_status;

	@Column(name = "RIGHTS")
	String rights;

	@Column(name = "PKISTATUS")
	String pkiStatus;

	@Column(name = "TPIN_WRONG_ATTEMPT")
	BigDecimal tpin_wrong_attempt;

	@Column(name = "CITY")
	BigDecimal city;

	@Column(name = "WRONG_PWD_ATTEMPT")
	BigDecimal wrong_pwd_attempt;

	@Column(name = "PWD_STATUS")
	String pwd_status;

	@Column(name = "OTHERDOC")
	String otherDoc;

	@Column(name = "CERTIFICATE_INCORPORATION")
	String certificate_incorporation;

	@Column(name = "STATE")
	BigDecimal state;

	@Column(name = "DESIGNATION")
	BigDecimal designation;

	@Column(name = "LAST_TPIN_WRONG_ATTEMPT")
	Date last_tpin_wrong_attempt;

	@Column(name = "LAST_PWD_WRONG_ATTEMPT")
	Date last_pwd_wrong_attempt;

	/*
	 * @Column(name = "MPIN") String mpin;
	 */

	@Column(name = "MPIN_WRONG_ATTEMPT")
	BigDecimal mpin_wrong_attempt;

	@Column(name = "LAST_MPIN_WRONG_ATTEMPT")
	Date last_mpin_wrong_attempt;

	@Column(name = "PANCARD")
	String pancard;

	@Column(name = "PANCARDNUMBER")
	String pancardNumber;

	@Column(name = "UPDATEDBY")
	BigDecimal updatedby;

	@Column(name = "MAXLIMIT")
	BigDecimal transMaxLimit;

	@Column(name = "UPDATEDON")
	Date updatedOn;

	@Column(name = "MOBREGSTATUS")
	BigDecimal mobRegStatus;

	@Column(name = "IBREGSTATUS")
	BigDecimal ibRegStatus;

	@Column(name = "PARENTROLEID")
	private BigDecimal parentRoleId;

	@Column(name = "PARENTID")
	private BigDecimal parentId;

	@Column(name = "DOB")
	private String dob;

	@Column(name = "PARENTUSERNAME")
	private String parentUserName;

	@Column(name = "CORPROLEID")
	private BigDecimal corpRoleId;

	@Column(name = "RRN")
	private String rrn;

	@Column(name = "PARENTRRN")
	private String parentRrn;

	@Column(name = "REMARKS")
	private String remark;

	@Column(name = "WRONGATTEMPTSSOFTTOKEN")
	private BigDecimal wrongAttemptSoftToken;

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public BigDecimal getCorp_comp_id() {
		return corp_comp_id;
	}

	public void setCorp_comp_id(BigDecimal corp_comp_id) {
		this.corp_comp_id = corp_comp_id;
	}

	public String getUser_disp_name() {
		return user_disp_name;
	}

	public void setUser_disp_name(String user_disp_name) {
		this.user_disp_name = user_disp_name;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	/*
	 * public String getUser_pwd() { return user_pwd; }
	 * 
	 * public void setUser_pwd(String user_pwd) { this.user_pwd = user_pwd; }
	 */
	public BigDecimal getCreatedby() {
		return createdby;
	}

	public void setCreatedby(BigDecimal createdby) {
		this.createdby = createdby;
	}

	public Timestamp getCreatedon() {
		return createdon;
	}

	public void setCreatedon(Timestamp createdon) {
		this.createdon = createdon;
	}

	public BigDecimal getStatusid() {
		return statusid;
	}

	public void setStatusid(BigDecimal statusid) {
		this.statusid = statusid;
	}

	public BigDecimal getAppid() {
		return appid;
	}

	public void setAppid(BigDecimal appid) {
		this.appid = appid;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public BigDecimal getUser_type() {
		return user_type;
	}

	public void setUser_type(BigDecimal user_type) {
		this.user_type = user_type;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getEmail_id() {
		return email_id;
	}

	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}

	public BigDecimal getCountry() {
		return country;
	}

	public void setCountry(BigDecimal country) {
		this.country = country;
	}

	public String getWork_phone() {
		return work_phone;
	}

	public void setWork_phone(String work_phone) {
		this.work_phone = work_phone;
	}

	public String getPersonal_Phone() {
		return personal_Phone;
	}

	public void setPersonal_Phone(String personal_Phone) {
		this.personal_Phone = personal_Phone;
	}

	public String getNationalId() {
		return nationalId;
	}

	public void setNationalId(String nationalId) {
		this.nationalId = nationalId;
	}

	public String getPassport() {
		return passport;
	}

	public void setPassport(String passport) {
		this.passport = passport;
	}

	public String getAadharCardNo() {
		return aadharCardNo;
	}

	public void setAadharCardNo(String aadharCardNo) {
		this.aadharCardNo = aadharCardNo;
	}

	public String getBoardResolution() {
		return boardResolution;
	}

	public void setBoardResolution(String boardResolution) {
		this.boardResolution = boardResolution;
	}

	public String getUser_image() {
		return user_image;
	}

	public void setUser_image(String user_image) {
		this.user_image = user_image;
	}

	/*
	 * public String getTpin() { return tpin; }
	 * 
	 * public void setTpin(String tpin) { this.tpin = tpin; }
	 */

	public String getPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}

	public String getNationalIdNumber() {
		return nationalIdNumber;
	}

	public void setNationalIdNumber(String nationalIdNumber) {
		this.nationalIdNumber = nationalIdNumber;
	}

	public String getTpin_status() {
		return tpin_status;
	}

	public void setTpin_status(String tpin_status) {
		this.tpin_status = tpin_status;
	}

	public String getRights() {
		return rights;
	}

	public void setRights(String rights) {
		this.rights = rights;
	}

	public String getPkiStatus() {
		return pkiStatus;
	}

	public void setPkiStatus(String pkiStatus) {
		this.pkiStatus = pkiStatus;
	}

	public BigDecimal getTpin_wrong_attempt() {
		return tpin_wrong_attempt;
	}

	public void setTpin_wrong_attempt(BigDecimal tpin_wrong_attempt) {
		this.tpin_wrong_attempt = tpin_wrong_attempt;
	}

	public BigDecimal getCity() {
		return city;
	}

	public void setCity(BigDecimal city) {
		this.city = city;
	}

	public BigDecimal getWrong_pwd_attempt() {
		return wrong_pwd_attempt;
	}

	public void setWrong_pwd_attempt(BigDecimal wrong_pwd_attempt) {
		this.wrong_pwd_attempt = wrong_pwd_attempt;
	}

	public String getPwd_status() {
		return pwd_status;
	}

	public void setPwd_status(String pwd_status) {
		this.pwd_status = pwd_status;
	}

	public String getOtherDoc() {
		return otherDoc;
	}

	public void setOtherDoc(String otherDoc) {
		this.otherDoc = otherDoc;
	}

	public String getCertificate_incorporation() {
		return certificate_incorporation;
	}

	public void setCertificate_incorporation(String certificate_incorporation) {
		this.certificate_incorporation = certificate_incorporation;
	}

	public BigDecimal getState() {
		return state;
	}

	public void setState(BigDecimal state) {
		this.state = state;
	}

	public BigDecimal getDesignation() {
		return designation;
	}

	public void setDesignation(BigDecimal designation) {
		this.designation = designation;
	}

	public Date getLast_tpin_wrong_attempt() {
		return last_tpin_wrong_attempt;
	}

	public void setLast_tpin_wrong_attempt(Date last_tpin_wrong_attempt) {
		this.last_tpin_wrong_attempt = last_tpin_wrong_attempt;
	}

	public Date getLast_pwd_wrong_attempt() {
		return last_pwd_wrong_attempt;
	}

	public void setLast_pwd_wrong_attempt(Date last_pwd_wrong_attempt) {
		this.last_pwd_wrong_attempt = last_pwd_wrong_attempt;
	}

	/*
	 * public String getMpin() { return mpin; }
	 * 
	 * public void setMpin(String mpin) { this.mpin = mpin; }
	 */

	public BigDecimal getMpin_wrong_attempt() {
		return mpin_wrong_attempt;
	}

	public void setMpin_wrong_attempt(BigDecimal mpin_wrong_attempt) {
		this.mpin_wrong_attempt = mpin_wrong_attempt;
	}

	public Date getLast_mpin_wrong_attempt() {
		return last_mpin_wrong_attempt;
	}

	public void setLast_mpin_wrong_attempt(Date last_mpin_wrong_attempt) {
		this.last_mpin_wrong_attempt = last_mpin_wrong_attempt;
	}

	public String getPancard() {
		return pancard;
	}

	public void setPancard(String pancard) {
		this.pancard = pancard;
	}

	public String getPancardNumber() {
		return pancardNumber;
	}

	public void setPancardNumber(String pancardNumber) {
		this.pancardNumber = pancardNumber;
	}

	public BigDecimal getUpdatedby() {
		return updatedby;
	}

	public void setUpdatedby(BigDecimal updatedby) {
		this.updatedby = updatedby;
	}

	public BigDecimal getTransMaxLimit() {
		return transMaxLimit;
	}

	public void setTransMaxLimit(BigDecimal transMaxLimit) {
		this.transMaxLimit = transMaxLimit;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public BigDecimal getMobRegStatus() {
		return mobRegStatus;
	}

	public void setMobRegStatus(BigDecimal mobRegStatus) {
		this.mobRegStatus = mobRegStatus;
	}

	public BigDecimal getIbRegStatus() {
		return ibRegStatus;
	}

	public void setIbRegStatus(BigDecimal ibRegStatus) {
		this.ibRegStatus = ibRegStatus;
	}

	public BigDecimal getParentRoleId() {
		return parentRoleId;
	}

	public void setParentRoleId(BigDecimal parentRoleId) {
		this.parentRoleId = parentRoleId;
	}

	public BigDecimal getParentId() {
		return parentId;
	}

	public void setParentId(BigDecimal parentId) {
		this.parentId = parentId;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getParentUserName() {
		return parentUserName;
	}

	public void setParentUserName(String parentUserName) {
		this.parentUserName = parentUserName;
	}

	public BigDecimal getCorpRoleId() {
		return corpRoleId;
	}

	public void setCorpRoleId(BigDecimal corpRoleId) {
		this.corpRoleId = corpRoleId;
	}

	public String getRrn() {
		return rrn;
	}

	public void setRrn(String rrn) {
		this.rrn = rrn;
	}

	public String getParentRrn() {
		return parentRrn;
	}

	public void setParentRrn(String parentRrn) {
		this.parentRrn = parentRrn;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getWrongAttemptSoftToken() {
		return wrongAttemptSoftToken;
	}

	public void setWrongAttemptSoftToken(BigDecimal wrongAttemptSoftToken) {
		this.wrongAttemptSoftToken = wrongAttemptSoftToken;
	}

}
