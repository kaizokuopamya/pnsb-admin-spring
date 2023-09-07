package com.itl.pns.bean;

import java.util.Date;
import java.math.BigInteger;
import java.sql.Clob;

public class CustomerKycDocumentBean {

private BigInteger id;
	
    private BigInteger channelId;
    
    private String channelName;
	
    private BigInteger appId;
    
    private String appName;
	
	private String rrn;
	
    private BigInteger docType;
	
	private String docName;
	
	private Clob base64Image;
	
	private String baseImage;
    
    private BigInteger customerId;
    
    private String customerName;
    
    private String mobile;
    
    private String dob;
    
    private BigInteger activityId;
    
    private String activityName;
	
    private BigInteger createdBy;
	
    private Date createdOn;
    
    private BigInteger updatedBy;
    
    private Date updateOn;
    
    private BigInteger statusId;
    
    private String statusName;
    
	private String docIdentification;
    
	private String documentNumber;
    
	private String cifNumber;
	
	private String comments;
	
	String familybooknoexpdate;
	String nationalidexpdate;
	String passportidexpdate;
	String staypermitnoexpdate;
	String workpermitnoexpdate;
	String regexpdate;
	
	String familybookno;
	String nationalid;
	String passportid;
	String staypermitno;
	String workpermitno;
	String otherdoc;
	String frid;
      Date kycdate;
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getBaseImage() {
		return baseImage;
	}

	public void setBaseImage(String baseImage) {
		this.baseImage = baseImage;
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public BigInteger getChannelId() {
		return channelId;
	}

	public void setChannelId(BigInteger channelId) {
		this.channelId = channelId;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public BigInteger getAppId() {
		return appId;
	}

	public void setAppId(BigInteger appId) {
		this.appId = appId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getRrn() {
		return rrn;
	}

	public void setRrn(String rrn) {
		this.rrn = rrn;
	}

	public BigInteger getDocType() {
		return docType;
	}

	public void setDocType(BigInteger docType) {
		this.docType = docType;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public Clob getBase64Image() {
		return base64Image;
	}

	public void setBase64Image(Clob base64Image) {
		this.base64Image = base64Image;
	}

	public BigInteger getCustomerId() {
		return customerId;
	}

	public void setCustomerId(BigInteger customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public BigInteger getActivityId() {
		return activityId;
	}

	public void setActivityId(BigInteger activityId) {
		this.activityId = activityId;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public BigInteger getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(BigInteger createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public BigInteger getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(BigInteger updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdateOn() {
		return updateOn;
	}

	public void setUpdateOn(Date updateOn) {
		this.updateOn = updateOn;
	}

	public BigInteger getStatusId() {
		return statusId;
	}

	public void setStatusId(BigInteger statusId) {
		this.statusId = statusId;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getDocIdentification() {
		return docIdentification;
	}

	public void setDocIdentification(String docIdentification) {
		this.docIdentification = docIdentification;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public String getCifNumber() {
		return cifNumber;
	}

	public void setCifNumber(String cifNumber) {
		this.cifNumber = cifNumber;
	}

	public String getFamilybooknoexpdate() {
		return familybooknoexpdate;
	}

	public void setFamilybooknoexpdate(String familybooknoexpdate) {
		this.familybooknoexpdate = familybooknoexpdate;
	}

	public String getNationalidexpdate() {
		return nationalidexpdate;
	}

	public void setNationalidexpdate(String nationalidexpdate) {
		this.nationalidexpdate = nationalidexpdate;
	}

	public String getPassportidexpdate() {
		return passportidexpdate;
	}

	public void setPassportidexpdate(String passportidexpdate) {
		this.passportidexpdate = passportidexpdate;
	}

	public String getStaypermitnoexpdate() {
		return staypermitnoexpdate;
	}

	public void setStaypermitnoexpdate(String staypermitnoexpdate) {
		this.staypermitnoexpdate = staypermitnoexpdate;
	}

	public String getWorkpermitnoexpdate() {
		return workpermitnoexpdate;
	}

	public void setWorkpermitnoexpdate(String workpermitnoexpdate) {
		this.workpermitnoexpdate = workpermitnoexpdate;
	}

	public String getRegexpdate() {
		return regexpdate;
	}

	public void setRegexpdate(String regexpdate) {
		this.regexpdate = regexpdate;
	}

	public String getFamilybookno() {
		return familybookno;
	}

	public void setFamilybookno(String familybookno) {
		this.familybookno = familybookno;
	}

	public String getNationalid() {
		return nationalid;
	}

	public void setNationalid(String nationalid) {
		this.nationalid = nationalid;
	}

	public String getPassportid() {
		return passportid;
	}

	public void setPassportid(String passportid) {
		this.passportid = passportid;
	}

	public String getStaypermitno() {
		return staypermitno;
	}

	public void setStaypermitno(String staypermitno) {
		this.staypermitno = staypermitno;
	}

	public String getWorkpermitno() {
		return workpermitno;
	}

	public void setWorkpermitno(String workpermitno) {
		this.workpermitno = workpermitno;
	}

	public String getOtherdoc() {
		return otherdoc;
	}

	public void setOtherdoc(String otherdoc) {
		this.otherdoc = otherdoc;
	}

	public String getFrid() {
		return frid;
	}

	public void setFrid(String frid) {
		this.frid = frid;
	}

	public Date getKycdate() {
		return kycdate;
	}

	public void setKycdate(Date kycdate) {
		this.kycdate = kycdate;
	}

}
