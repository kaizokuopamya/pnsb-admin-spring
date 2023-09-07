package com.itl.pns.entity;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "NOTIFICATIONS")
public class Notifications {

	@javax.persistence.Id
	@Column(name = "ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
	@SequenceGenerator(name = "id_Sequence", sequenceName = "notifications_id_SEQ", allocationSize=1)
	private BigInteger id;
	
	@Column(name = "CUSTOMERID")
	private BigInteger customerId;
	
	@Column(name = "NOTIFICATIONMSG")
	private String notificationMsg;  
	
	@Column(name = "APPID")
	private BigInteger appId;
	
	@Column(name = "ACTIVITYID")
	private BigInteger activityId;
	
	@Column(name = "RRN")
	private String rrn;
	
	@Column(name = "CREATEDON")
	Date createdOn;
	
	@Column(name = "CREATEDBY")
	BigInteger createdBy;
	
	@Column(name = "STATUSID")
	private BigInteger statusId;
	
	@Column(name = "LASTRESENTON")
	public Date lastResentOn;
	
	@Column(name = "RESENDBY")
	private int resendBy;

	@Column(name = "TYPE")
	private String type;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public BigInteger getCustomerId() {
		return customerId;
	}

	public void setCustomerId(BigInteger customerId) {
		this.customerId = customerId;
	}

	public String getNotificationMsg() {
		return notificationMsg;
	}

	public void setNotificationMsg(String notificationMsg) {
		this.notificationMsg = notificationMsg;
	}

	public BigInteger getAppId() {
		return appId;
	}

	public void setAppId(BigInteger appId) {
		this.appId = appId;
	}

	public BigInteger getActivityId() {
		return activityId;
	}

	public void setActivityId(BigInteger activityId) {
		this.activityId = activityId;
	}

	public String getRrn() {
		return rrn;
	}

	public void setRrn(String rrn) {
		this.rrn = rrn;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public BigInteger getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(BigInteger createdBy) {
		this.createdBy = createdBy;
	}

	public BigInteger getStatusId() {
		return statusId;
	}

	public void setStatusId(BigInteger statusId) {
		this.statusId = statusId;
	}

	public Date getLastResentOn() {
		return lastResentOn;
	}

	public void setLastResentOn(Date lastResentOn) {
		this.lastResentOn = lastResentOn;
	}

	public int getResendBy() {
		return resendBy;
	}

	public void setResendBy(int resendBy) {
		this.resendBy = resendBy;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
}
