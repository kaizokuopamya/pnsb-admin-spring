package com.itl.pns.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "CBS_BRANCH_LIST")
public class CbsBranchList {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
	@SequenceGenerator(name = "id_Sequence", sequenceName = "cbs_branchlist_ID_SEQ", allocationSize=1)
	@Column(name = "ID")                
	private BigDecimal id; 

	@Column(name = "BRANCHZONE")	
	private String branchzone;

	@Column(name = "STATEID")
	private String stateid;

	@Column(name = "STATE_NAME")
	private String stateName;			

	@Column(name = "STATE_CODE")	
	private String stateCode;			

	@Column(name = "CITY_ID")	
	private String cityId;				

	@Column(name = "CITY_NAME")
	private String cityName;			

	@Column(name = "CITY_CODE")
	private String cityCode;			

	@Column(name = "SUBDISTRICT_NAME")
	private String subdistrictName;		

	@Column(name = "POPULATION_GROUP_NAME")
	private String populationGroupName;		

	@Column(name = "BRANCHTIER")		
	private String branchtier;			

	@Column(name = "BRANCH_CODE")		
	private String branchCode;			

	@Column(name = "BRANCH_NAME")			
	private String branchName;			

	@Column(name = "BANK_NAME")
	private String bankName;			

	@Column(name = "BANKING_CHANNEL_NAME")	
	private String bankingChannelName;		

	@Column(name = "UCN_PART1_CODE")	
	private String ucnPart1Code;			

	@Column(name = "UCN_PART2_CODE")
	private String ucnPart2Code;			

	@Column(name = "IFSC_CODE")	
	private String ifscCode;			

	@Column(name = "MICR_CODE")
	private String micrCode;			

	@Column(name = "CBS_CODE")
	private String cbsCode;			

	@Column(name = "LATITUDE")
	private String latitude;			

	@Column(name = "LONGITUD")
	private String longitud;			

	@Column(name = "LICENSE_NUMBER")
	private String licenseNumber;			

	@Column(name = "LICENSE_START_DATE")
	private String licenseStartDate;		

	@Column(name = "ADDRESS_1")	
	private String address1;			

	@Column(name = "ADDRESS_2")
	private String address2;			

	@Column(name = "PINCODE")
	private String pincode;			

	@Column(name = "POST_OFFICE")
	private String postOffice;			

	@Column(name = "BRANCH_TELEPHONE_NUMBER")
	private String branhTelephoneNumber;		

	@Column(name = "BRANCH_EMAIL_ADDRESS")	
	private String branchEmailAddress;		

	@Column(name = "BRANCH_MOBILE_NUMBER")	
	private String branchMobileNumber;		

	@Column(name = "BNKCHL_OPEN_DATE")	
	private String bnkchlOpenDate;		

	@Column(name = "BNKCHL_ACTUAL_OPEN_DATE")
	private String bnkchlActualOpenDate;		

	@Column(name = "AD_CATEGORY")		
	private String adCategory;			

	@Column(name = "LINK_OFFICE")		
	private String linkOffice;			

	@Column(name = "BANKING_CHANNEL_TYPE")	
	private String bankingChannelType;		

	@Column(name = "BASE_BRANCH_UCN_PART1_CODE")   
	private String baseBranchUcnPart1Code;      

	@Column(name = "BNKCHL_WORKING_TYPE_NAME")	
	private String bnkchlWorkingTypeName;	

	@Column(name = "BNKCHL_TYPE_NAME")		
	private String bnkchlTypeName;		

	@Column(name = "ATMID")		
	private String atmid;				

	@Column(name = "SERVICES_OFFERED")	
	private String servicesOffered;	

	@Column(name = "WORKING_HOURS")	
	private String workingHours;

	@Column(name = "CREATEDBY")		
	private BigDecimal createdby;			

	@Column(name = "CREATEDON")		
	private Date createdon;			

	@Column(name = "STATUSID")	
	private BigDecimal statusid;			

	@Column(name = "COUNTRYID")		
	private String countryid;			

	@Column(name = "ZONECODE")		
	private String zonecode;

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getBranchzone() {
		return branchzone;
	}

	public void setBranchzone(String branchzone) {
		this.branchzone = branchzone;
	}

	public String getStateid() {
		return stateid;
	}

	public void setStateid(String stateid) {
		this.stateid = stateid;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getSubdistrictName() {
		return subdistrictName;
	}

	public void setSubdistrictName(String subdistrictName) {
		this.subdistrictName = subdistrictName;
	}

	public String getPopulationGroupName() {
		return populationGroupName;
	}

	public void setPopulationGroupName(String populationGroupName) {
		this.populationGroupName = populationGroupName;
	}

	public String getBranchtier() {
		return branchtier;
	}

	public void setBranchtier(String branchtier) {
		this.branchtier = branchtier;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankingChannelName() {
		return bankingChannelName;
	}

	public void setBankingChannelName(String bankingChannelName) {
		this.bankingChannelName = bankingChannelName;
	}

	public String getUcnPart1Code() {
		return ucnPart1Code;
	}

	public void setUcnPart1Code(String ucnPart1Code) {
		this.ucnPart1Code = ucnPart1Code;
	}

	public String getUcnPart2Code() {
		return ucnPart2Code;
	}

	public void setUcnPart2Code(String ucnPart2Code) {
		this.ucnPart2Code = ucnPart2Code;
	}

	public String getIfscCode() {
		return ifscCode;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	public String getMicrCode() {
		return micrCode;
	}

	public void setMicrCode(String micrCode) {
		this.micrCode = micrCode;
	}

	public String getCbsCode() {
		return cbsCode;
	}

	public void setCbsCode(String cbsCode) {
		this.cbsCode = cbsCode;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitud() {
		return longitud;
	}

	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}

	public String getLicenseNumber() {
		return licenseNumber;
	}

	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	public String getLicenseStartDate() {
		return licenseStartDate;
	}

	public void setLicenseStartDate(String licenseStartDate) {
		this.licenseStartDate = licenseStartDate;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getPostOffice() {
		return postOffice;
	}

	public void setPostOffice(String postOffice) {
		this.postOffice = postOffice;
	}

	public String getBranhTelephoneNumber() {
		return branhTelephoneNumber;
	}

	public void setBranhTelephoneNumber(String branhTelephoneNumber) {
		this.branhTelephoneNumber = branhTelephoneNumber;
	}

	public String getBranchEmailAddress() {
		return branchEmailAddress;
	}

	public void setBranchEmailAddress(String branchEmailAddress) {
		this.branchEmailAddress = branchEmailAddress;
	}

	public String getBranchMobileNumber() {
		return branchMobileNumber;
	}

	public void setBranchMobileNumber(String branchMobileNumber) {
		this.branchMobileNumber = branchMobileNumber;
	}

	public String getBnkchlOpenDate() {
		return bnkchlOpenDate;
	}

	public void setBnkchlOpenDate(String bnkchlOpenDate) {
		this.bnkchlOpenDate = bnkchlOpenDate;
	}

	public String getBnkchlActualOpenDate() {
		return bnkchlActualOpenDate;
	}

	public void setBnkchlActualOpenDate(String bnkchlActualOpenDate) {
		this.bnkchlActualOpenDate = bnkchlActualOpenDate;
	}

	public String getAdCategory() {
		return adCategory;
	}

	public void setAdCategory(String adCategory) {
		this.adCategory = adCategory;
	}

	public String getLinkOffice() {
		return linkOffice;
	}

	public void setLinkOffice(String linkOffice) {
		this.linkOffice = linkOffice;
	}

	public String getBankingChannelType() {
		return bankingChannelType;
	}

	public void setBankingChannelType(String bankingChannelType) {
		this.bankingChannelType = bankingChannelType;
	}

	public String getBaseBranchUcnPart1Code() {
		return baseBranchUcnPart1Code;
	}

	public void setBaseBranchUcnPart1Code(String baseBranchUcnPart1Code) {
		this.baseBranchUcnPart1Code = baseBranchUcnPart1Code;
	}

	public String getBnkchlWorkingTypeName() {
		return bnkchlWorkingTypeName;
	}

	public void setBnkchlWorkingTypeName(String bnkchlWorkingTypeName) {
		this.bnkchlWorkingTypeName = bnkchlWorkingTypeName;
	}

	public String getBnkchlTypeName() {
		return bnkchlTypeName;
	}

	public void setBnkchlTypeName(String bnkchlTypeName) {
		this.bnkchlTypeName = bnkchlTypeName;
	}

	public String getAtmid() {
		return atmid;
	}

	public void setAtmid(String atmid) {
		this.atmid = atmid;
	}

	public String getServicesOffered() {
		return servicesOffered;
	}

	public void setServicesOffered(String servicesOffered) {
		this.servicesOffered = servicesOffered;
	}

	public String getWorkingHours() {
		return workingHours;
	}

	public void setWorkingHours(String workingHours) {
		this.workingHours = workingHours;
	}

	public BigDecimal getCreatedby() {
		return createdby;
	}

	public void setCreatedby(BigDecimal createdby) {
		this.createdby = createdby;
	}

	public Date getCreatedon() {
		return createdon;
	}

	public void setCreatedon(Date createdon) {
		this.createdon = createdon;
	}

	public BigDecimal getStatusid() {
		return statusid;
	}

	public void setStatusid(BigDecimal statusid) {
		this.statusid = statusid;
	}

	public String getCountryid() {
		return countryid;
	}

	public void setCountryid(String countryid) {
		this.countryid = countryid;
	}

	public String getZonecode() {
		return zonecode;
	}

	public void setZonecode(String zonecode) {
		this.zonecode = zonecode;
	}

	@Override
	public String toString() {
		return "CbsBranchList [id=" + id + ", branchzone=" + branchzone + ", stateid=" + stateid + ", stateName="
				+ stateName + ", stateCode=" + stateCode + ", cityId=" + cityId + ", cityName=" + cityName
				+ ", cityCode=" + cityCode + ", subdistrictName=" + subdistrictName + ", populationGroupName="
				+ populationGroupName + ", branchtier=" + branchtier + ", branchCode=" + branchCode + ", branchName="
				+ branchName + ", bankName=" + bankName + ", bankingChannelName=" + bankingChannelName
				+ ", ucnPart1Code=" + ucnPart1Code + ", ucnPart2Code=" + ucnPart2Code + ", ifscCode=" + ifscCode
				+ ", micrCode=" + micrCode + ", cbsCode=" + cbsCode + ", latitude=" + latitude + ", longitud="
				+ longitud + ", licenseNumber=" + licenseNumber + ", licenseStartDate=" + licenseStartDate
				+ ", address1=" + address1 + ", address2=" + address2 + ", pincode=" + pincode + ", postOffice="
				+ postOffice + ", branhTelephoneNumber=" + branhTelephoneNumber + ", branchEmailAddress="
				+ branchEmailAddress + ", branchMobileNumber=" + branchMobileNumber + ", bnkchlOpenDate="
				+ bnkchlOpenDate + ", bnkchlActualOpenDate=" + bnkchlActualOpenDate + ", adCategory=" + adCategory
				+ ", linkOffice=" + linkOffice + ", bankingChannelType=" + bankingChannelType
				+ ", baseBranchUcnPart1Code=" + baseBranchUcnPart1Code + ", bnkchlWorkingTypeName="
				+ bnkchlWorkingTypeName + ", bnkchlTypeName=" + bnkchlTypeName + ", atmid=" + atmid
				+ ", servicesOffered=" + servicesOffered + ", workingHours=" + workingHours + ", createdby=" + createdby
				+ ", createdon=" + createdon + ", statusid=" + statusid + ", countryid=" + countryid + ", zonecode="
				+ zonecode + "]";
	}			
	
	
	

}
