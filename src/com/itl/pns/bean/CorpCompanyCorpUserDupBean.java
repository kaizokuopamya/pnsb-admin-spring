package com.itl.pns.bean;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;

/**
 * @author ashish.yadav
 *
 */
public class CorpCompanyCorpUserDupBean {

	private BigDecimal id;

	private BigDecimal userId;

	private String companyCode;

	private String companyName;

	private String shortName;

	private String companyInfo;

	private Timestamp establishmentOn;

	private String logo;

	private BigDecimal statusId;

	private BigDecimal createdBy;

	private Timestamp createdOn;

	private String cif;

	private BigDecimal makerLimit;

	private BigDecimal checkerLimit;

	private String phoneNo;

	private String rrn;

	private String coi;

	private String moa;

	private String otherDoc;

	private String corporateType;

	private String pancardNo;

	private Timestamp updatedOn;

	private BigDecimal updatedBy;

	private String approvalLevel;

	private BigDecimal levelMaster;

	private long appId;

	private BigDecimal maxLimit;

	private String address;

	private String adminTypes;

	private char isCorporate;

	private String branchCode;

	BigDecimal corp_comp_id;

	String user_disp_name;

	String user_name;

	String user_pwd;

	BigDecimal userCreatedBy;

	Timestamp userCreatedOn;

	BigDecimal userStatusId;

	BigDecimal userAppId;

	Date lastLoginTime;

	BigDecimal user_type;

	String first_name;

	String last_name;

	String email_id;

	BigDecimal country;

	String work_phone;

	String personal_Phone;

	String nationalId;

	String passport;

	private String aadharCardNo;

	String boardResolution;

	String user_image;

	String tpin;

	String passportNumber;

	String nationalIdNumber;

	String tpin_status;

	String rights;

	String pkiStatus;

	BigDecimal tpin_wrong_attempt;

	BigDecimal city;

	BigDecimal wrong_pwd_attempt;

	String pwd_status;

	String userOtherDoc;

	String certificate_incorporation;

	BigDecimal state;

	BigDecimal designation;

	Date last_tpin_wrong_attempt;

	Date last_pwd_wrong_attempt;

	String mpin;

	BigDecimal mpin_wrong_attempt;

	Date last_mpin_wrong_attempt;

	String pancard;

	String pancardNumber;

	BigDecimal userUpdatedBy;

	BigDecimal transMaxLimit;

	Timestamp userUpdatedOn;

	BigDecimal mobRegStatus;

	BigDecimal ibRegStatus;

	private BigDecimal parentRoleId;

	private BigDecimal parentId;

	private String dob;

	private String parentUserName;

	private BigDecimal corpRoleId;

	private String userRrn;

	private String parentRrn;

	private String remark;

	private BigDecimal wrongAttemptSoftToken;

	private String userLoginId;

	private Integer makerId;

	private Integer checkerId;

	private boolean makerValidated;

	private boolean checkerValidated;

	private String userRole;

	public String getUserLoginId() {
		return userLoginId;
	}

	public void setUserLoginId(String userLoginId) {
		this.userLoginId = userLoginId;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public Integer getMakerId() {
		return makerId;
	}

	public void setMakerId(Integer makerId) {
		this.makerId = makerId;
	}

	public Integer getCheckerId() {
		return checkerId;
	}

	public void setCheckerId(Integer checkerId) {
		this.checkerId = checkerId;
	}

	public boolean isMakerValidated() {
		return makerValidated;
	}

	public void setMakerValidated(boolean makerValidated) {
		this.makerValidated = makerValidated;
	}

	public boolean isCheckerValidated() {
		return checkerValidated;
	}

	public void setCheckerValidated(boolean checkerValidated) {
		this.checkerValidated = checkerValidated;
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

	public String getUser_pwd() {
		return user_pwd;
	}

	public void setUser_pwd(String user_pwd) {
		this.user_pwd = user_pwd;
	}

	public BigDecimal getUserCreatedBy() {
		return userCreatedBy;
	}

	public void setUserCreatedBy(BigDecimal userCreatedBy) {
		this.userCreatedBy = userCreatedBy;
	}

	public Timestamp getUserCreatedOn() {
		return userCreatedOn;
	}

	public void setUserCreatedOn(Timestamp userCreatedOn) {
		this.userCreatedOn = userCreatedOn;
	}

	public String getOtherDoc() {
		return otherDoc;
	}

	public void setOtherDoc(String otherDoc) {
		this.otherDoc = otherDoc;
	}

	public BigDecimal getUserStatusId() {
		return userStatusId;
	}

	public void setUserStatusId(BigDecimal userStatusId) {
		this.userStatusId = userStatusId;
	}

	public BigDecimal getUserAppId() {
		return userAppId;
	}

	public void setUserAppId(BigDecimal userAppId) {
		this.userAppId = userAppId;
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

	public String getTpin() {
		return tpin;
	}

	public void setTpin(String tpin) {
		this.tpin = tpin;
	}

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

	public String getMpin() {
		return mpin;
	}

	public void setMpin(String mpin) {
		this.mpin = mpin;
	}

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

	public BigDecimal getUserUpdatedBy() {
		return userUpdatedBy;
	}

	public void setUserUpdatedBy(BigDecimal userUpdatedBy) {
		this.userUpdatedBy = userUpdatedBy;
	}

	public BigDecimal getTransMaxLimit() {
		return transMaxLimit;
	}

	public void setTransMaxLimit(BigDecimal transMaxLimit) {
		this.transMaxLimit = transMaxLimit;
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

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getAdminTypes() {
		return adminTypes;
	}

	public void setAdminTypes(String adminTypes) {
		this.adminTypes = adminTypes;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public BigDecimal getMaxLimit() {
		return maxLimit;
	}

	public void setMaxLimit(BigDecimal maxLimit) {
		this.maxLimit = maxLimit;
	}

	public BigDecimal getLevelMaster() {
		return levelMaster;
	}

	public void setLevelMaster(BigDecimal levelMaster) {
		this.levelMaster = levelMaster;
	}

	public long getAppId() {
		return appId;
	}

	public void setAppId(long appId) {
		this.appId = appId;
	}

	public String getPancardNo() {
		return pancardNo;
	}

	public void setPancardNo(String pancardNo) {
		this.pancardNo = pancardNo;
	}

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getCompanyInfo() {
		return companyInfo;
	}

	public void setCompanyInfo(String companyInfo) {
		this.companyInfo = companyInfo;
	}

	public Timestamp getEstablishmentOn() {
		return establishmentOn;
	}

	public void setEstablishmentOn(Timestamp establishmentOn) {
		this.establishmentOn = establishmentOn;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public BigDecimal getStatusId() {
		return statusId;
	}

	public void setStatusId(BigDecimal statusId) {
		this.statusId = statusId;
	}

	public BigDecimal getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(BigDecimal createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public String getCif() {
		return cif;
	}

	public void setCif(String cif) {
		this.cif = cif;
	}

	public BigDecimal getMakerLimit() {
		return makerLimit;
	}

	public void setMakerLimit(BigDecimal makerLimit) {
		this.makerLimit = makerLimit;
	}

	public BigDecimal getCheckerLimit() {
		return checkerLimit;
	}

	public void setCheckerLimit(BigDecimal checkerLimit) {
		this.checkerLimit = checkerLimit;
	}

	public String getApprovalLevel() {
		return approvalLevel;
	}

	public void setApprovalLevel(String approvalLevel) {
		this.approvalLevel = approvalLevel;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getCoi() {
		return coi;
	}

	public void setCoi(String coi) {
		this.coi = coi;
	}

	public String getMoa() {
		return moa;
	}

	public void setMoa(String moa) {
		this.moa = moa;
	}

	public String getCorporateType() {
		return corporateType;
	}

	public void setCorporateType(String corporateType) {
		this.corporateType = corporateType;
	}

	public BigDecimal getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(BigDecimal updatedBy) {
		this.updatedBy = updatedBy;
	}

	public char getIsCorporate() {
		return isCorporate;
	}

	public void setIsCorporate(char isCorporate) {
		this.isCorporate = isCorporate;
	}

	public BigDecimal getUserId() {
		return userId;
	}

	public void setUserId(BigDecimal userId) {
		this.userId = userId;
	}

	public String getRrn() {
		return rrn;
	}

	public void setRrn(String rrn) {
		this.rrn = rrn;
	}

	public Timestamp getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getUserOtherDoc() {
		return userOtherDoc;
	}

	public void setUserOtherDoc(String userOtherDoc) {
		this.userOtherDoc = userOtherDoc;
	}

	public Timestamp getUserUpdatedOn() {
		return userUpdatedOn;
	}

	public void setUserUpdatedOn(Timestamp userUpdatedOn) {
		this.userUpdatedOn = userUpdatedOn;
	}

	public String getUserRrn() {
		return userRrn;
	}

	public void setUserRrn(String userRrn) {
		this.userRrn = userRrn;
	}

	@Override
	public String toString() {
		return "CorpCompanyCorpUserDupBean [id=" + id + ", userId=" + userId + ", companyCode=" + companyCode
				+ ", companyName=" + companyName + ", shortName=" + shortName + ", companyInfo=" + companyInfo
				+ ", establishmentOn=" + establishmentOn + ", logo=" + logo + ", statusId=" + statusId + ", createdBy="
				+ createdBy + ", createdOn=" + createdOn + ", cif=" + cif + ", makerLimit=" + makerLimit
				+ ", checkerLimit=" + checkerLimit + ", phoneNo=" + phoneNo + ", rrn=" + rrn + ", coi=" + coi + ", moa="
				+ moa + ", otherDoc=" + otherDoc + ", corporateType=" + corporateType + ", pancardNo=" + pancardNo
				+ ", updatedOn=" + updatedOn + ", updatedBy=" + updatedBy + ", approvalLevel=" + approvalLevel
				+ ", levelMaster=" + levelMaster + ", appId=" + appId + ", maxLimit=" + maxLimit + ", address="
				+ address + ", adminTypes=" + adminTypes + ", isCorporate=" + isCorporate + ", branchCode=" + branchCode
				+ ", corp_comp_id=" + corp_comp_id + ", user_disp_name=" + user_disp_name + ", user_name=" + user_name
				+ ", user_pwd=" + user_pwd + ", userCreatedBy=" + userCreatedBy + ", userCreatedOn=" + userCreatedOn
				+ ", userStatusId=" + userStatusId + ", userAppId=" + userAppId + ", lastLoginTime=" + lastLoginTime
				+ ", user_type=" + user_type + ", first_name=" + first_name + ", last_name=" + last_name + ", email_id="
				+ email_id + ", country=" + country + ", work_phone=" + work_phone + ", personal_Phone="
				+ personal_Phone + ", nationalId=" + nationalId + ", passport=" + passport + ", aadharCardNo="
				+ aadharCardNo + ", boardResolution=" + boardResolution + ", user_image=" + user_image + ", tpin="
				+ tpin + ", passportNumber=" + passportNumber + ", nationalIdNumber=" + nationalIdNumber
				+ ", tpin_status=" + tpin_status + ", rights=" + rights + ", pkiStatus=" + pkiStatus
				+ ", tpin_wrong_attempt=" + tpin_wrong_attempt + ", city=" + city + ", wrong_pwd_attempt="
				+ wrong_pwd_attempt + ", pwd_status=" + pwd_status + ", userOtherDoc=" + userOtherDoc
				+ ", certificate_incorporation=" + certificate_incorporation + ", state=" + state + ", designation="
				+ designation + ", last_tpin_wrong_attempt=" + last_tpin_wrong_attempt + ", last_pwd_wrong_attempt="
				+ last_pwd_wrong_attempt + ", mpin=" + mpin + ", mpin_wrong_attempt=" + mpin_wrong_attempt
				+ ", last_mpin_wrong_attempt=" + last_mpin_wrong_attempt + ", pancard=" + pancard + ", pancardNumber="
				+ pancardNumber + ", userUpdatedBy=" + userUpdatedBy + ", transMaxLimit=" + transMaxLimit
				+ ", userUpdatedOn=" + userUpdatedOn + ", mobRegStatus=" + mobRegStatus + ", ibRegStatus=" + ibRegStatus
				+ ", parentRoleId=" + parentRoleId + ", parentId=" + parentId + ", dob=" + dob + ", parentUserName="
				+ parentUserName + ", corpRoleId=" + corpRoleId + ", userRrn=" + userRrn + ", parentRrn=" + parentRrn
				+ ", remark=" + remark + ", wrongAttemptSoftToken=" + wrongAttemptSoftToken + ", userLoginId="
				+ userLoginId + ", makerId=" + makerId + ", checkerId=" + checkerId + ", makerValidated="
				+ makerValidated + ", checkerValidated=" + checkerValidated + ", userRole=" + userRole + "]";
	}

}
