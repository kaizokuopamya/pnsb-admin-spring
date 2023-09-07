package com.itl.pns.corp.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "CORP_USERS_MASTER")
public class CorpUserMasterEntity {

	@javax.persistence.Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
	@SequenceGenerator(name = "id_Sequence", sequenceName = "corp_users_master_id_SEQ", allocationSize = 1)
	private BigDecimal id;

	@Column(name = "Corpid")
	private BigDecimal corpid;

	@Column(name = "Username")
	private String userName;

	@Column(name = "Userpassword")
	private String userPassword;

	@Column(name = "Firstname")
	private String firstName;

	@Column(name = "Lastname")
	private String lastName;

	@Column(name = "Email")
	private String email;

	@Column(name = "Mobile")
	private String mobile;

	@Column(name = "Dob")
	private String dob;

	@Column(name = "Pancardno")
	private String pancardNo;

	@Column(name = "RRN")
	private String rrn;

	@Column(name = "Corproleid")
	private BigDecimal corpRoleId;

	@Column(name = "Aadharcard")
	private String aadharCard;

	@Column(name = "Passport")
	private String passport;

	@Column(name = "Passportno")
	private String passportNo;

	@Column(name = "Boardresolution")
	private String boardResolution;

	@Column(name = "Userimage")
	private String userImage;

	@Column(name = "Aadharcardno")
	private String aadharCardNo;

	@Column(name = "Otherdoc")
	private String otherDoc;

	@Column(name = "Certificateincorporation")
	private String certificateIncorporation;

	@Column(name = "State")
	private BigDecimal state;

	@Column(name = "Designation")
	private BigDecimal designation;

	@Column(name = "Parentid")
	private BigDecimal parentId;

	@Column(name = "Authtype")
	private String authType;

	@Column(name = "StatusId")
	private BigDecimal statusId;

	@Column(name = "Createdon")
	private Date createdon;

	@Column(name = "Updatedon")
	private Date updatedon;

	@Column(name = "UPDATEDBY")
	private BigDecimal updatedby;

	@Column(name = "ParentRRN")
	private String parentRrn;

	@Column(name = "Parentroleid")
	private BigDecimal parentRoleId;

	@Column(name = "TOKEN")
	private String token;

	@Column(name = "Parentusername")
	private String parentUserName;

	@Transient
	List<CorpUserMenuMapEntity> corpUserMenuData;

	@Transient
	List<CorpUserAccMapEntity> corpUserAccData;

	@Transient
	CorpUserMenuMapEntity userMenusList;

	@Transient
	CorpUserAccMapEntity userAccountList;

	@Transient
	String parentRoleName;

	@Transient
	String corpRoleName;

	@Transient
	String menuList;

	@Transient
	String accountList;

	@Transient
	private String companyName;

	@Transient
	String loginId;

	@Transient
	String loginType;

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

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public BigDecimal getCorpid() {
		return corpid;
	}

	public void setCorpid(BigDecimal corpid) {
		this.corpid = corpid;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getPancardNo() {
		return pancardNo;
	}

	public void setPancardNo(String pancardNo) {
		this.pancardNo = pancardNo;
	}

	public String getRrn() {
		return rrn;
	}

	public void setRrn(String rrn) {
		this.rrn = rrn;
	}

	public BigDecimal getCorpRoleId() {
		return corpRoleId;
	}

	public void setCorpRoleId(BigDecimal corpRoleId) {
		this.corpRoleId = corpRoleId;
	}

	public String getPassport() {
		return passport;
	}

	public void setPassport(String passport) {
		this.passport = passport;
	}

	public String getPassportNo() {
		return passportNo;
	}

	public void setPassportNo(String passportNo) {
		this.passportNo = passportNo;
	}

	public String getBoardResolution() {
		return boardResolution;
	}

	public void setBoardResolution(String boardResolution) {
		this.boardResolution = boardResolution;
	}

	public String getUserImage() {
		return userImage;
	}

	public void setUserImage(String userImage) {
		this.userImage = userImage;
	}

	public String getAadharCard() {
		return aadharCard;
	}

	public void setAadharCard(String aadharCard) {
		this.aadharCard = aadharCard;
	}

	public String getAadharCardNo() {
		return aadharCardNo;
	}

	public void setAadharCardNo(String aadharCardNo) {
		this.aadharCardNo = aadharCardNo;
	}

	public String getOtherDoc() {
		return otherDoc;
	}

	public void setOtherDoc(String otherDoc) {
		this.otherDoc = otherDoc;
	}

	public String getCertificateIncorporation() {
		return certificateIncorporation;
	}

	public void setCertificateIncorporation(String certificateIncorporation) {
		this.certificateIncorporation = certificateIncorporation;
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

	public BigDecimal getParentId() {
		return parentId;
	}

	public void setParentId(BigDecimal parentId) {
		this.parentId = parentId;
	}

	public String getAuthType() {
		return authType;
	}

	public void setAuthType(String authType) {
		this.authType = authType;
	}

	public BigDecimal getStatusId() {
		return statusId;
	}

	public void setStatusId(BigDecimal statusId) {
		this.statusId = statusId;
	}

	public Date getCreatedon() {
		return createdon;
	}

	public void setCreatedon(Date createdon) {
		this.createdon = createdon;
	}

	public Date getUpdatedon() {
		return updatedon;
	}

	public void setUpdatedon(Date updatedon) {
		this.updatedon = updatedon;
	}

	public BigDecimal getUpdatedby() {
		return updatedby;
	}

	public void setUpdatedby(BigDecimal updatedby) {
		this.updatedby = updatedby;
	}

	public String getParentRrn() {
		return parentRrn;
	}

	public void setParentRrn(String parentRrn) {
		this.parentRrn = parentRrn;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public BigDecimal getParentRoleId() {
		return parentRoleId;
	}

	public void setParentRoleId(BigDecimal parentRoleId) {
		this.parentRoleId = parentRoleId;
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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getParentUserName() {
		return parentUserName;
	}

	public void setParentUserName(String parentUserName) {
		this.parentUserName = parentUserName;
	}

	public CorpUserMenuMapEntity getUserMenusList() {
		return userMenusList;
	}

	public void setUserMenusList(CorpUserMenuMapEntity userMenusList) {
		this.userMenusList = userMenusList;
	}

	public CorpUserAccMapEntity getUserAccountList() {
		return userAccountList;
	}

	public void setUserAccountList(CorpUserAccMapEntity userAccountList) {
		this.userAccountList = userAccountList;
	}

}
