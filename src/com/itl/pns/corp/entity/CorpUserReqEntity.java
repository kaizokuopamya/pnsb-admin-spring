package com.itl.pns.corp.entity;

import java.math.BigInteger;
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
@Table(name = "CORP_USER_REQ")
public class CorpUserReqEntity {

	@javax.persistence.Id
	@Column(name = "ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
	@SequenceGenerator(name = "id_Sequence", sequenceName = "corp_user_req_id_SEQ", allocationSize=1)
	private BigInteger id;

	@Column(name = "Corpid")    
	private BigInteger corpid;

	@Column(name = "Username")
	private String userName;
	
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
	private BigInteger corpRoleId;
	
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
	private BigInteger state;
	
	@Column(name = "Designation")
	private BigInteger designation;
	
	@Column(name = "Parentid")
	private BigInteger parentId;
	
	@Column(name = "Authtype")
	private String authType;
	
	@Column(name = "StatusId")
	private BigInteger statusId;
	
	@Column(name = "Createdon")
	private Date createdon;

	@Column(name = "Updatedon")
	private Date updatedon;

	@Column(name = "UPDATEDBY")
	private BigInteger updatedby;
	
	@Column(name = "ParentRRN")
	private String parentRrn;
	
	@Column(name = "Parentroleid")
	private BigInteger parentRoleId;
	
	@Column(name = "Parentusername")
	private String parentUserName;
	
	
	@Column(name = "TOKEN")
	private String token;
	
	@Transient
	String parentRoleName;
	
	@Transient
	String corpRoleName;
	
	@Transient
	String companyName;
	
	@Transient
	List<CorpUserMenuReqEntity> corpUserMenuData;
	
	@Transient
	List<CorpUserAccReqEntity> corpUserAccData;
	

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public BigInteger getCorpid() {
		return corpid;
	}

	public void setCorpid(BigInteger corpid) {
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

	public BigInteger getCorpRoleId() {
		return corpRoleId;
	}

	public void setCorpRoleId(BigInteger corpRoleId) {
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

	public BigInteger getState() {
		return state;
	}

	public void setState(BigInteger state) {
		this.state = state;
	}

	public BigInteger getDesignation() {
		return designation;
	}

	public void setDesignation(BigInteger designation) {
		this.designation = designation;
	}

	public BigInteger getParentId() {
		return parentId;
	}

	public void setParentId(BigInteger parentId) {
		this.parentId = parentId;
	}

	public String getAuthType() {
		return authType;
	}

	public void setAuthType(String authType) {
		this.authType = authType;
	}

	public BigInteger getStatusId() {
		return statusId;
	}

	public void setStatusId(BigInteger statusId) {
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

	public BigInteger getUpdatedby() {
		return updatedby;
	}

	public void setUpdatedby(BigInteger updatedby) {
		this.updatedby = updatedby;
	}

	public String getParentRrn() {
		return parentRrn;
	}

	public void setParentRrn(String parentRrn) {
		this.parentRrn = parentRrn;
	}

	public List<CorpUserMenuReqEntity> getCorpUserMenuData() {
		return corpUserMenuData;
	}

	public void setCorpUserMenuData(List<CorpUserMenuReqEntity> corpUserMenuData) {
		this.corpUserMenuData = corpUserMenuData;
	}

	public List<CorpUserAccReqEntity> getCorpUserAccData() {
		return corpUserAccData;
	}

	public void setCorpUserAccData(List<CorpUserAccReqEntity> corpUserAccData) {
		this.corpUserAccData = corpUserAccData;
	}

	public String getCorpRoleName() {
		return corpRoleName;
	}

	public void setCorpRoleName(String corpRoleName) {
		this.corpRoleName = corpRoleName;
	}


	public BigInteger getParentRoleId() {
		return parentRoleId;
	}

	public void setParentRoleId(BigInteger parentRoleId) {
		this.parentRoleId = parentRoleId;
	}

	public String getParentRoleName() {
		return parentRoleName;
	}

	public void setParentRoleName(String parentRoleName) {
		this.parentRoleName = parentRoleName;
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
	

}
