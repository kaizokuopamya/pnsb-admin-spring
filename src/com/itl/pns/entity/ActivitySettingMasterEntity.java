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

@Entity
@Table(name = "ACTIVITYSETTINGMASTER")
public class ActivitySettingMasterEntity {
	
	@javax.persistence.Id
	@Column(name = "ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
	@SequenceGenerator(name = "id_Sequence", sequenceName = "activitysettingmaster_ID_SEQ", allocationSize=1)
	BigDecimal id;
	
	@Column(name = "ACTIVITYID")
	BigDecimal activityId;
	
	@Column(name = "TPINALLOWD")
	char tpinAllowd;
	
	@Column(name = "OTPALLOWED")
	char otpAllowed;
	
	@Column(name = "CREATEDBY")
	BigDecimal createdBy;
	
	@Column(name = "CREATEDON")
	Date createdOn;
	
	@Column(name = "STATUSID")
	BigDecimal statusId;
	
	@Column(name = "MAKER")
	char maker;

	@Column(name = "CHECKER")
	char checker;
	
	@Transient
	private String statusName;   
	
	@Transient
	private BigDecimal appid; 

	@Transient
	private String appName; 

	@Transient
	private String activityName; 
	
	@Column(name = "APPROVER")
	char approver;
	
	@Column(name = "ISMULTILEVEL")
	char isMultiLevel;
	
	@Transient
	private String activitySelected;
	
	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public BigDecimal getActivityId() {
		return activityId;
	}

	public void setActivityId(BigDecimal activityId) {
		this.activityId = activityId;
	}

	public char getTpinAllowd() {
		return tpinAllowd;
	}

	public void setTpinAllowd(char tpinAllowd) {
		this.tpinAllowd = tpinAllowd;
	}

	public char getOtpAllowed() {
		return otpAllowed;
	}

	public void setOtpAllowed(char otpAllowed) {
		this.otpAllowed = otpAllowed;
	}

	public BigDecimal getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(BigDecimal createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public BigDecimal getStatusId() {
		return statusId;
	}

	public void setStatusId(BigDecimal statusId) {
		this.statusId = statusId;
	}



	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	

	public String getActivitySelected() {
		return activitySelected;
	}

	public void setActivitySelected(String activitySelected) {
		this.activitySelected = activitySelected;
	}

	public BigDecimal getAppid() {
		return appid;
	}

	public void setAppid(BigDecimal appid) {
		this.appid = appid;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public char getMaker() {
		return maker;
	}

	public void setMaker(char maker) {
		this.maker = maker;
	}

	public char getChecker() {
		return checker;
	}

	public void setChecker(char checker) {
		this.checker = checker;
	}

	public char getApprover() {
		return approver;
	}

	public void setApprover(char approver) {
		this.approver = approver;
	}

	public char getIsMultiLevel() {
		return isMultiLevel;
	}

	public void setIsMultiLevel(char isMultiLevel) {
		this.isMultiLevel = isMultiLevel;
	}

	@Override
	public String toString() {
		return "ActivitySettingMasterEntity [id=" + id + ", activityId=" + activityId + ", tpinAllowd=" + tpinAllowd
				+ ", otpAllowed=" + otpAllowed + ", createdBy=" + createdBy + ", createdOn=" + createdOn + ", statusId="
				+ statusId + ", maker=" + maker + ", checker=" + checker + ", statusName=" + statusName + ", appid="
				+ appid + ", appName=" + appName + ", activityName=" + activityName + ", approver=" + approver
				+ ", isMultiLevel=" + isMultiLevel + ", activitySelected=" + activitySelected + "]";
	}


}
