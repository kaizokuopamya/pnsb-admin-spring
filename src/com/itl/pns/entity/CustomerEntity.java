package com.itl.pns.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import oracle.sql.BLOB;

@Entity
@Table(name = "CUSTOMERS")
public class CustomerEntity {
	@javax.persistence.Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CUSTOMERSEntity_Sequence")
	@SequenceGenerator(name = "CUSTOMERSEntity_Sequence", sequenceName = "customers_id_SEQ", allocationSize = 1)
	BigDecimal Id;

	@Column(name = "CIF")
	String cif;

	@Column(nullable = true, name = "SALUTATION")
	String salutation;

	@Column(name = "CUSTOMERNAME")
	String customername;

	@Column(nullable = true, name = "USERNAME")
	String username;

	@Column(nullable = true, name = "MPIN")
	String mpin;

	@Column(nullable = true, name = "USERPASSWORD")
	String userpassword;

	@Column(nullable = true, name = "IMAGE")
	BLOB image;

	@Column(nullable = true, name = "EMAIL")
	String email;

	@Column(name = "MOBILE")
	String mobile;

	@Column(nullable = true, name = "ISMPINLOCKED")
	char ismpinlocked;

	@Column(nullable = true, name = "MOBILELASTLOGGEDON")
	Date mobilelastloggedon;

	@Column(nullable = true, name = "WEBLASTLOGGENON")
	Date weblastloggenon;

	@Column(nullable = true, name = "ISMPINENABLED")
	Character ismpinenabled;

	@Column(nullable = true, name = "APPID")
	Integer appid;

	@Column(nullable = true, name = "PREFEREDLANGUAGE")
	String preferedlanguage;

	@Column(nullable = true, name = "FINGUREUNLOCKENABLED")
	String fingureunlockenabled;

	@Column(nullable = true, name = "CREATEDBY")
	Integer createdby;

	@Column(nullable = true, name = "CREATEDON")
	Date createdon;

	@Column(nullable = true, name = "UPDATEDBY")
	Integer updatedby;

	@Column(nullable = true, name = "UPDATEDON")
	Date updatedon;

	@Column(nullable = true, name = "DOB")
	String dob;

	@Column(nullable = true, name = "GENDER")
	String gender;

	@Column(nullable = true, name = "WRONGATTEMPTSMPIN")
	Integer wrongattemptsmpin;

	@Column(nullable = true, name = "WRONGATTEMPTSPWD")
	Integer wrongattemptspwd;

	@Column(nullable = true, name = "WRONGATTEMPTSTPIN")
	Integer wrongattemptstpin;

	@Column(nullable = true, name = "STATUSID")
	Integer statusid;

	@Column(nullable = true, name = "FRID")
	String frid;

	@Column(nullable = true, name = "TPIN")
	String tpin;

	@Column(nullable = true, name = "ISTPINLOCKED")
	Character istpinlocked;

	@Column(nullable = true, name = "ISBIOMETRICENABLED")
	String isbiometricenabled;

	@Column(nullable = true, name = "ISSMSENABLED")
	String issmsenabled;

	@Column(nullable = true, name = "ISEMAILENABLED")
	String isemailenabled;

	@Column(nullable = true, name = "ISPUSHNOTIFICATIONENABLED")
	String isPushNotificationEnabled;

	@Column(nullable = true, name = "BASE64IMAGE")
	String base64image;

	@Column(nullable = true, name = "ASSIGNTO")
	String assignto;

	@Column(nullable = true, name = "ISMOBILEENABLED")
	Character ismobileenabled;

	@Column(nullable = true, name = "ISWEBENABLED")
	Character iswebenabled;

	@Column(nullable = true, name = "SSA_ACTIVE")
	Character ssa_active;

	@Column(nullable = true, name = "SSA_ACCOUNT_NUMBER")
	String ssa_account_number;

	@Column(nullable = true, name = "LOCALTRFLIMITS")
	int localtrflimits;

	@Column(nullable = true, name = "UTILITYLIMIT")
	int utilitylimit;

	@Column(nullable = true, name = "INTERNATIONALLIMIT")
	int internationallimit;

	@Column(nullable = true, name = "CARDLIMIT")
	int cardlimit;

	@Column(nullable = true, name = "MOBREGSTATUS")
	public int MOBREGSTATUS;

	@Column(nullable = true, name = "IBREGSTATUS")
	public int IBREGSTATUS;

	@Transient
	int resetLastMobileLoggedIn;

	@Column(nullable = true, name = "REFEREALCODE")
	String referealcode;

	@Column(nullable = true, name = "ISBLOCKED_UPI")
	Character isBlocked_upi;

	@Column(nullable = true, name = "ISUPIENABLED")
	String isUPIEnabled;

	@Column(nullable = true, name = "ISUPIREGISTERED")
	String isUPIRegistered;

	@Column(nullable = true, name = "MPINLOCKEDON")
	String MPINLOCKEDON;

	@Transient
	private String roleName;

	@Transient
	private String action;

	@Transient
	private BigDecimal user_ID;

	@Transient
	private BigDecimal role_ID;

	@Transient
	private BigDecimal subMenu_ID;

	@Transient
	private String remark;

	@Transient
	private String activityName;

	@Transient
	private BigDecimal userAction;

	@Transient
	private String statusName;

	@Transient
	private String appName;

	@Transient
	String createdByName;

	@Transient
	private String wrongAttemptsPara;
	
	@Transient
	private String pushnotificationtoken;

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public BigDecimal getId() {
		return Id;
	}

	public void setId(BigDecimal id) {
		Id = id;
	}

	public String getCif() {
		return cif;
	}

	public void setCif(String cif) {
		this.cif = cif;
	}

	public String getSalutation() {
		return salutation;
	}

	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}

	public String getCustomername() {
		return customername;
	}

	public void setCustomername(String customername) {
		this.customername = customername;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMpin() {
		return mpin;
	}

	public void setMpin(String mpin) {
		this.mpin = mpin;
	}

	public String getUserpassword() {
		return userpassword;
	}

	public void setUserpassword(String userpassword) {
		this.userpassword = userpassword;
	}

	public BLOB getImage() {
		return image;
	}

	public void setImage(BLOB image) {
		this.image = image;
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

	public char getIsmpinlocked() {
		return ismpinlocked;
	}

	public void setIsmpinlocked(char ismpinlocked) {
		this.ismpinlocked = ismpinlocked;
	}

	public Date getMobilelastloggedon() {
		return mobilelastloggedon;
	}

	public void setMobilelastloggedon(Date mobilelastloggedon) {
		this.mobilelastloggedon = mobilelastloggedon;
	}

	public Date getWeblastloggenon() {
		return weblastloggenon;
	}

	public void setWeblastloggenon(Date weblastloggenon) {
		this.weblastloggenon = weblastloggenon;
	}

	public Character getIsmpinenabled() {
		return ismpinenabled;
	}

	public void setIsmpinenabled(Character ismpinenabled) {
		this.ismpinenabled = ismpinenabled;
	}

	public Integer getAppid() {
		return appid;
	}

	public void setAppid(Integer appid) {
		this.appid = appid;
	}

	public String getPreferedlanguage() {
		return preferedlanguage;
	}

	public void setPreferedlanguage(String preferedlanguage) {
		this.preferedlanguage = preferedlanguage;
	}

	public String getFingureunlockenabled() {
		return fingureunlockenabled;
	}

	public void setFingureunlockenabled(String fingureunlockenabled) {
		this.fingureunlockenabled = fingureunlockenabled;
	}

	public Integer getCreatedby() {
		return createdby;
	}

	public void setCreatedby(Integer createdby) {
		this.createdby = createdby;
	}

	public Date getCreatedon() {
		return createdon;
	}

	public void setCreatedon(Date createdon) {
		this.createdon = createdon;
	}

	public Integer getUpdatedby() {
		return updatedby;
	}

	public void setUpdatedby(Integer updatedby) {
		this.updatedby = updatedby;
	}

	public Date getUpdatedon() {
		return updatedon;
	}

	public void setUpdatedon(Date updatedon) {
		this.updatedon = updatedon;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Integer getWrongattemptsmpin() {
		return wrongattemptsmpin;
	}

	public void setWrongattemptsmpin(Integer wrongattemptsmpin) {
		this.wrongattemptsmpin = wrongattemptsmpin;
	}

	public Integer getWrongattemptspwd() {
		return wrongattemptspwd;
	}

	public void setWrongattemptspwd(Integer wrongattemptspwd) {
		this.wrongattemptspwd = wrongattemptspwd;
	}

	public Integer getStatusid() {
		return statusid;
	}

	public void setStatusid(Integer statusid) {
		this.statusid = statusid;
	}

	public String getFrid() {
		return frid;
	}

	public void setFrid(String frid) {
		this.frid = frid;
	}

	public String getTpin() {
		return tpin;
	}

	public void setTpin(String tpin) {
		this.tpin = tpin;
	}

	public Character getIstpinlocked() {
		return istpinlocked;
	}

	public void setIstpinlocked(Character istpinlocked) {
		this.istpinlocked = istpinlocked;
	}

	public String getIsbiometricenabled() {
		return isbiometricenabled;
	}

	public void setIsbiometricenabled(String isbiometricenabled) {
		this.isbiometricenabled = isbiometricenabled;
	}

	public String getIssmsenabled() {
		return issmsenabled;
	}

	public void setIssmsenabled(String issmsenabled) {
		this.issmsenabled = issmsenabled;
	}

	public String getIsemailenabled() {
		return isemailenabled;
	}

	public void setIsemailenabled(String isemailenabled) {
		this.isemailenabled = isemailenabled;
	}

	public String getIsPushNotificationEnabled() {
		return isPushNotificationEnabled;
	}

	public void setIsPushNotificationEnabled(String isPushNotificationEnabled) {
		this.isPushNotificationEnabled = isPushNotificationEnabled;
	}

	public String getBase64image() {
		return base64image;
	}

	public void setBase64image(String base64image) {
		this.base64image = base64image;
	}

	public String getAssignto() {
		return assignto;
	}

	public void setAssignto(String assignto) {
		this.assignto = assignto;
	}

	public Character getIsmobileenabled() {
		return ismobileenabled;
	}

	public void setIsmobileenabled(Character ismobileenabled) {
		this.ismobileenabled = ismobileenabled;
	}

	public Character getIswebenabled() {
		return iswebenabled;
	}

	public void setIswebenabled(Character iswebenabled) {
		this.iswebenabled = iswebenabled;
	}

	public Character getSsa_active() {
		return ssa_active;
	}

	public void setSsa_active(Character ssa_active) {
		this.ssa_active = ssa_active;
	}

	public String getSsa_account_number() {
		return ssa_account_number;
	}

	public void setSsa_account_number(String ssa_account_number) {
		this.ssa_account_number = ssa_account_number;
	}

	public int getLocaltrflimits() {
		return localtrflimits;
	}

	public void setLocaltrflimits(int localtrflimits) {
		this.localtrflimits = localtrflimits;
	}

	public int getUtilitylimit() {
		return utilitylimit;
	}

	public void setUtilitylimit(int utilitylimit) {
		this.utilitylimit = utilitylimit;
	}

	public int getInternationallimit() {
		return internationallimit;
	}

	public void setInternationallimit(int internationallimit) {
		this.internationallimit = internationallimit;
	}

	public int getCardlimit() {
		return cardlimit;
	}

	public void setCardlimit(int cardlimit) {
		this.cardlimit = cardlimit;
	}

	public int getResetLastMobileLoggedIn() {
		return resetLastMobileLoggedIn;
	}

	public void setResetLastMobileLoggedIn(int resetLastMobileLoggedIn) {
		this.resetLastMobileLoggedIn = resetLastMobileLoggedIn;
	}

	public String getReferealcode() {
		return referealcode;
	}

	public void setReferealcode(String referealcode) {
		this.referealcode = referealcode;
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

	public int getMOBREGSTATUS() {
		return MOBREGSTATUS;
	}

	public void setMOBREGSTATUS(int mOBREGSTATUS) {
		MOBREGSTATUS = mOBREGSTATUS;
	}

	public int getIBREGSTATUS() {
		return IBREGSTATUS;
	}

	public void setIBREGSTATUS(int iBREGSTATUS) {
		IBREGSTATUS = iBREGSTATUS;
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

	public Character getIsBlocked_upi() {
		return isBlocked_upi;
	}

	public void setIsBlocked_upi(Character isBlocked_upi) {
		this.isBlocked_upi = isBlocked_upi;
	}

	public String getIsUPIEnabled() {
		return isUPIEnabled;
	}

	public void setIsUPIEnabled(String isUPIEnabled) {
		this.isUPIEnabled = isUPIEnabled;
	}

	public String getIsUPIRegistered() {
		return isUPIRegistered;
	}

	public void setIsUPIRegistered(String isUPIRegistered) {
		this.isUPIRegistered = isUPIRegistered;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public String getWrongAttemptsPara() {
		return wrongAttemptsPara;
	}

	public void setWrongAttemptsPara(String wrongAttemptsPara) {
		this.wrongAttemptsPara = wrongAttemptsPara;
	}

	public Integer getWrongattemptstpin() {
		return wrongattemptstpin;
	}

	public void setWrongattemptstpin(Integer wrongattemptstpin) {
		this.wrongattemptstpin = wrongattemptstpin;
	}

	public String getMPINLOCKEDON() {
		return MPINLOCKEDON;
	}

	public void setMPINLOCKEDON(String mPINLOCKEDON) {
		MPINLOCKEDON = mPINLOCKEDON;
	}

	public String getPushnotificationtoken() {
		return pushnotificationtoken;
	}

	public void setPushnotificationtoken(String pushnotificationtoken) {
		this.pushnotificationtoken = pushnotificationtoken;
	}
	
	

	
}