package com.itl.pns.entity;

import java.math.BigDecimal;
import java.sql.Clob;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name = "LOCATIONS")
public class LocationsEntity {
	
	@javax.persistence.Id
	@Column(name = "ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "locations_id_SEQ")
	@SequenceGenerator(name = "locations_id_SEQ", sequenceName = "locations_id_SEQ", allocationSize=1)
	private BigDecimal id;
    
    @Column(name = "LOCATIONTYPEID")
    private BigDecimal locationTypeId;
    
    @Column(name = "DISPLAYNAME")
    private String displayName;
    
    @Column(name = "COUNTRYID")
    private BigDecimal countryId;
    
    @Column(name = "STATEID")
    private BigDecimal stateId;
    
    @Column(name = "CITYID")
    private BigDecimal cityId;
    
    @Column(name = "ADDRESS")
    private Clob addressClob;
    
    @Column(name = "LATITUDE")
    private String latitude;
    
    @Column(name = "LONGITUDE")
    private String longitude;
    
    @Column(name = "PHONENUMBER")
    private String phoneNumber;
    
    @Column(name = "EMAILADDRESS")
    private String emailAddress;
    
    @Column(name = "POSTCODE")
    private BigDecimal postCode;
    
    @Column(name = "BRANCHCODE")
    private String branchCode;
    
    @Column(name = "CREATEDBY")
    private BigDecimal createdby;
    
    @Column(name = "UPDATEDBY")
    private Integer updatedby;
    
    @Column(name = "STATUSID")
    private BigDecimal statusId;
    
    @Column(name = "LANGUAGECODE")
    private String languageCode;
    
    @Column(name = "CREATEDON")
	public Date createdon;
    
    @Column(name = "UPDATEDON")
	public Date updatedon;
    
    @Column(name = "APPID")
	public BigDecimal appId;
    
    @Transient
	private BigDecimal user_ID;

    @Transient
	String address;
  
	@Transient
	String locationType;
	
	@Transient
	String countryName;
	
	@Transient
	String stateName;
	
	@Transient
	String cityName;
	
	@Transient
	String statusName;
	
	@Transient  
	private String roleName;
	
	@Transient
	String createdByName;
	
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
	String appName;
	 
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



	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}



	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	

	public BigDecimal getCreatedby() {
		return createdby;
	}

	public void setCreatedby(BigDecimal createdby) {
		this.createdby = createdby;
	}

	public Integer getUpdatedby() {
		return updatedby;
	}

	public void setUpdatedby(Integer updatedby) {
		this.updatedby = updatedby;
	}


	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getLocationType() {
		return locationType;
	}

	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public BigDecimal getLocationTypeId() {
		return locationTypeId;
	}

	public void setLocationTypeId(BigDecimal locationTypeId) {
		this.locationTypeId = locationTypeId;
	}

	public BigDecimal getCountryId() {
		return countryId;
	}

	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}

	public BigDecimal getStateId() {
		return stateId;
	}

	public void setStateId(BigDecimal stateId) {
		this.stateId = stateId;
	}

	public BigDecimal getCityId() {
		return cityId;
	}

	public void setCityId(BigDecimal cityId) {
		this.cityId = cityId;
	}

	public BigDecimal getPostCode() {
		return postCode;
	}

	public void setPostCode(BigDecimal postCode) {
		this.postCode = postCode;
	}

	public BigDecimal getStatusId() {
		return statusId;
	}

	public void setStatusId(BigDecimal statusId) {
		this.statusId = statusId;
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

	public BigDecimal getAppId() {
		return appId;
	}

	public void setAppId(BigDecimal appId) {
		this.appId = appId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Clob getAddressClob() {
		return addressClob;
	}

	public void setAddressClob(Clob addressClob) {
		this.addressClob = addressClob;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}



}
