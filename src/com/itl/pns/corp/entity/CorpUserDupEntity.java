package com.itl.pns.corp.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author shubham.lokhande
 *
 */
@Entity
@Table(name = "CORP_USERS_DUP")
public class CorpUserDupEntity {

	@javax.persistence.Id
	@Column(name = "ID")
	BigDecimal id;

	@Column(name = "CORP_COMP_ID")
	BigDecimal corp_comp_id;

	@Column(name = "USER_DISP_NAME")
	String user_disp_name;

	@Column(name = "USER_NAME")
	String user_name;

	@Column(name = "USER_PWD")
	String user_pwd;

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

	@Column(name = "AadharcardNo")
	private String aadharCardNo;

	@Column(name = "BOARDRESOLUTION")
	String boardResolution;

	@Column(name = "USER_IMAGE")
	String user_image;

	@Column(name = "TPIN")
	String tpin;

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

	@Column(name = "MPIN")
	String mpin;

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

	@Column(name = "Parentroleid")
	private BigDecimal parentRoleId;

	@Column(name = "Parentid")
	private BigDecimal parentId;

	@Column(name = "DOB")
	private String dob;

	@Column(name = "Parentusername")
	private String parentUserName;

	@Column(name = "Corproleid")
	private BigDecimal corpRoleId;

	@Column(name = "RRN")
	private String rrn;

	@Column(name = "ParentRRN")
	private String parentRrn;

	@Column(name = "remarks")
	private String remark;

	@Column(name = "WRONGATTEMPTSSOFTTOKEN")
	private BigDecimal wrongAttemptSoftToken;

	@Column(name = "MAKER_ID")
	private Integer makerId;

	@Column(name = "CHECKER_ID")
	private Integer checkerId;

	@Column(name = "MAKER_VALIDATED")
	private Integer makerValidated;

	@Column(name = "CHECKER_VALIDATED")
	private Integer checkerValidated;

	@Transient
	String passportImg;

	@Transient
	String userStringImage;

	@Transient
	String boardResolutionImg;

	@Transient
	String otherDocImg;

	@Transient
	String certificateIncorporationImg;

	@Transient
	private String statusName;

	@Transient
	private String createdByName;

	@Transient
	private BigDecimal user_ID;

	@Transient
	private BigDecimal role_ID;

	@Transient
	private BigDecimal subMenu_ID;

	@Transient
	private String activityName;

	@Transient
	private BigDecimal userAction;

	@Transient
	private String roleName;

	@Transient
	private String action;

	@Transient
	private String companyName;

	@Transient
	private String userType;

	@Transient
	private String cityName;

	@Transient
	private String stateName;

	@Transient
	private String countryName;

	@Transient
	private BigDecimal hierarchyLevel;

	@Transient
	private BigDecimal designationId;

	@Transient
	private String base;

	@Transient
	private String tempUserName;

	@Transient
	private String designationName;

	@Transient
	String nationalIdImg;

	@Transient
	private String menuList;

	@Transient
	private String accountList;

	@Transient
	List<CorpUserMenuMapEntity> corpUserMenuData;

	@Transient
	List<CorpUserAccMapEntity> corpUserAccData;

	@Transient
	String parentRoleName;

	@Transient
	String corpRoleName;

	@Transient
	String loginId;

	@Transient
	String loginType;

	@Transient
	List<Integer> statusList;

	@Transient
	String temp_pwd;

	@Transient
	List<String> userNameList;

	@Transient
	String branchCode;

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public List<String> getUserNameList() {
		return userNameList;
	}

	public void setUserNameList(List<String> userNameList) {
		this.userNameList = userNameList;
	}

	public BigDecimal getWrongAttemptSoftToken() {
		return wrongAttemptSoftToken;
	}

	public void setWrongAttemptSoftToken(BigDecimal wrongAttemptSoftToken) {
		this.wrongAttemptSoftToken = wrongAttemptSoftToken;
	}

	public String getTemp_pwd() {
		return temp_pwd;
	}

	public void setTemp_pwd(String temp_pwd) {
		this.temp_pwd = temp_pwd;
	}

	public List<Integer> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<Integer> statusList) {
		this.statusList = statusList;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	public String getParentRoleName() {
		return parentRoleName;
	}

	public void setParentRoleName(String parentRoleName) {
		this.parentRoleName = parentRoleName;
	}

	public String getCorpRoleName() {
		return corpRoleName;
	}

	public void setCorpRoleName(String corpRoleName) {
		this.corpRoleName = corpRoleName;
	}

	public List<CorpUserMenuMapEntity> getCorpUserMenuData() {
		return corpUserMenuData;
	}

	public void setCorpUserMenuData(List<CorpUserMenuMapEntity> corpUserMenuData) {
		this.corpUserMenuData = corpUserMenuData;
	}

	public List<CorpUserAccMapEntity> getCorpUserAccData() {
		return corpUserAccData;
	}

	public void setCorpUserAccData(List<CorpUserAccMapEntity> corpUserAccData) {
		this.corpUserAccData = corpUserAccData;
	}

	public String getRrn() {
		return rrn;
	}

	public void setRrn(String rrn) {
		this.rrn = rrn;
	}

	public String getMenuList() {
		return menuList;
	}

	public void setMenuList(String menuList) {
		this.menuList = menuList;
	}

	public String getAccountList() {
		return accountList;
	}

	public void setAccountList(String accountList) {
		this.accountList = accountList;
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

	public String getUser_pwd() {
		return user_pwd;
	}

	public void setUser_pwd(String user_pwd) {
		this.user_pwd = user_pwd;
	}

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

	public String getNationalIdImg() {
		return nationalIdImg;
	}

	public void setNationalIdImg(String nationalIdImg) {
		this.nationalIdImg = nationalIdImg;
	}

	public String getPassport() {
		return passport;
	}

	public void setPassport(String passport) {
		this.passport = passport;
	}

	public String getPassportImg() {
		return passportImg;
	}

	public void setPassportImg(String passportImg) {
		this.passportImg = passportImg;
	}

	public String getBoardResolution() {
		return boardResolution;
	}

	public void setBoardResolution(String boardResolution) {
		this.boardResolution = boardResolution;
	}

	public String getBoardResolutionImg() {
		return boardResolutionImg;
	}

	public void setBoardResolutionImg(String boardResolutionImg) {
		this.boardResolutionImg = boardResolutionImg;
	}

	public String getUser_image() {
		return user_image;
	}

	public void setUser_image(String user_image) {
		this.user_image = user_image;
	}

	public String getUserStringImage() {
		return userStringImage;
	}

	public void setUserStringImage(String userStringImage) {
		this.userStringImage = userStringImage;
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

	public String getOtherDocImg() {
		return otherDocImg;
	}

	public void setOtherDocImg(String otherDocImg) {
		this.otherDocImg = otherDocImg;
	}

	public String getCertificate_incorporation() {
		return certificate_incorporation;
	}

	public void setCertificate_incorporation(String certificate_incorporation) {
		this.certificate_incorporation = certificate_incorporation;
	}

	public String getCertificateIncorporationImg() {
		return certificateIncorporationImg;
	}

	public void setCertificateIncorporationImg(String certificateIncorporationImg) {
		this.certificateIncorporationImg = certificateIncorporationImg;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public BigDecimal getUser_ID() {
		return user_ID;
	}

	public void setUser_ID(BigDecimal user_ID) {
		this.user_ID = user_ID;
	}

	public BigDecimal getRole_ID() {
		return role_ID;
	}

	public void setRole_ID(BigDecimal role_ID) {
		this.role_ID = role_ID;
	}

	public BigDecimal getSubMenu_ID() {
		return subMenu_ID;
	}

	public void setSubMenu_ID(BigDecimal subMenu_ID) {
		this.subMenu_ID = subMenu_ID;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public BigDecimal getUserAction() {
		return userAction;
	}

	public void setUserAction(BigDecimal userAction) {
		this.userAction = userAction;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public BigDecimal getState() {
		return state;
	}

	public void setState(BigDecimal state) {
		this.state = state;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public BigDecimal getDesignation() {
		return designation;
	}

	public void setDesignation(BigDecimal designation) {
		this.designation = designation;
	}

	public BigDecimal getHierarchyLevel() {
		return hierarchyLevel;
	}

	public void setHierarchyLevel(BigDecimal hierarchyLevel) {
		this.hierarchyLevel = hierarchyLevel;
	}

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public BigDecimal getDesignationId() {
		return designationId;
	}

	public void setDesignationId(BigDecimal designationId) {
		this.designationId = designationId;
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

	public String getTempUserName() {
		return tempUserName;
	}

	public void setTempUserName(String tempUserName) {
		this.tempUserName = tempUserName;
	}

	public String getDesignationName() {
		return designationName;
	}

	public void setDesignationName(String designationName) {
		this.designationName = designationName;
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

	public String getAadharCardNo() {
		return aadharCardNo;
	}

	public void setAadharCardNo(String aadharCardNo) {
		this.aadharCardNo = aadharCardNo;
	}

	public String getParentRrn() {
		return parentRrn;
	}

	public void setParentRrn(String parentRrn) {
		this.parentRrn = parentRrn;
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

	public Integer isMakerValidated() {
		return makerValidated;
	}

	public void setMakerValidated(Integer makerValidated) {
		this.makerValidated = makerValidated;
	}

	public Integer isCheckerValidated() {
		return checkerValidated;
	}

	public void setCheckerValidated(Integer checkerValidated) {
		this.checkerValidated = checkerValidated;
	}

	@Override
	public String toString() {
		return "CorpUserDupEntity [id=" + id + ", corp_comp_id=" + corp_comp_id + ", user_disp_name=" + user_disp_name
				+ ", user_name=" + user_name + ", user_pwd=" + user_pwd + ", createdby=" + createdby + ", createdon="
				+ createdon + ", statusid=" + statusid + ", appid=" + appid + ", lastLoginTime=" + lastLoginTime
				+ ", user_type=" + user_type + ", first_name=" + first_name + ", last_name=" + last_name + ", email_id="
				+ email_id + ", country=" + country + ", work_phone=" + work_phone + ", personal_Phone="
				+ personal_Phone + ", nationalId=" + nationalId + ", passport=" + passport + ", aadharCardNo="
				+ aadharCardNo + ", boardResolution=" + boardResolution + ", user_image=" + user_image + ", tpin="
				+ tpin + ", passportNumber=" + passportNumber + ", nationalIdNumber=" + nationalIdNumber
				+ ", tpin_status=" + tpin_status + ", rights=" + rights + ", pkiStatus=" + pkiStatus
				+ ", tpin_wrong_attempt=" + tpin_wrong_attempt + ", city=" + city + ", wrong_pwd_attempt="
				+ wrong_pwd_attempt + ", pwd_status=" + pwd_status + ", otherDoc=" + otherDoc
				+ ", certificate_incorporation=" + certificate_incorporation + ", state=" + state + ", designation="
				+ designation + ", last_tpin_wrong_attempt=" + last_tpin_wrong_attempt + ", last_pwd_wrong_attempt="
				+ last_pwd_wrong_attempt + ", mpin=" + mpin + ", mpin_wrong_attempt=" + mpin_wrong_attempt
				+ ", last_mpin_wrong_attempt=" + last_mpin_wrong_attempt + ", pancard=" + pancard + ", pancardNumber="
				+ pancardNumber + ", updatedby=" + updatedby + ", transMaxLimit=" + transMaxLimit + ", updatedOn="
				+ updatedOn + ", mobRegStatus=" + mobRegStatus + ", ibRegStatus=" + ibRegStatus + ", parentRoleId="
				+ parentRoleId + ", parentId=" + parentId + ", dob=" + dob + ", parentUserName=" + parentUserName
				+ ", corpRoleId=" + corpRoleId + ", rrn=" + rrn + ", parentRrn=" + parentRrn + ", remark=" + remark
				+ ", wrongAttemptSoftToken=" + wrongAttemptSoftToken + ", makerId=" + makerId + ", checkerId="
				+ checkerId + ", makerValidated=" + makerValidated + ", checkerValidated=" + checkerValidated
				+ ", passportImg=" + passportImg + ", userStringImage=" + userStringImage + ", boardResolutionImg="
				+ boardResolutionImg + ", otherDocImg=" + otherDocImg + ", certificateIncorporationImg="
				+ certificateIncorporationImg + ", statusName=" + statusName + ", createdByName=" + createdByName
				+ ", user_ID=" + user_ID + ", role_ID=" + role_ID + ", subMenu_ID=" + subMenu_ID + ", activityName="
				+ activityName + ", userAction=" + userAction + ", roleName=" + roleName + ", action=" + action
				+ ", companyName=" + companyName + ", userType=" + userType + ", cityName=" + cityName + ", stateName="
				+ stateName + ", countryName=" + countryName + ", hierarchyLevel=" + hierarchyLevel + ", designationId="
				+ designationId + ", base=" + base + ", tempUserName=" + tempUserName + ", designationName="
				+ designationName + ", nationalIdImg=" + nationalIdImg + ", menuList=" + menuList + ", accountList="
				+ accountList + ", corpUserMenuData=" + corpUserMenuData + ", corpUserAccData=" + corpUserAccData
				+ ", parentRoleName=" + parentRoleName + ", corpRoleName=" + corpRoleName + ", loginId=" + loginId
				+ ", loginType=" + loginType + ", statusList=" + statusList + ", temp_pwd=" + temp_pwd
				+ ", userNameList=" + userNameList + ", branchCode=" + branchCode + "]";
	}

}
