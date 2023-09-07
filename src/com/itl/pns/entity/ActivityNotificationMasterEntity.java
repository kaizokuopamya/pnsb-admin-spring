package com.itl.pns.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Date;


@Entity
@Table(name = "ACTIVITY_NOTIFICATION_MASTER")
public class ActivityNotificationMasterEntity {

	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "ACTIVITYNOTIFICATION_ID_SEQ")
	@SequenceGenerator(name = "ACTIVITYNOTIFICATION_ID_SEQ", sequenceName = "ACTIVITYNOTIFICATION_ID_SEQ", allocationSize=1)
	long id;

	@ManyToOne
	@JoinColumn(name = "ACTIVITYID")
	ActivityMaster activityid;
	
	@ManyToOne
	@JoinColumn(name = "STATUSID")
	StatusMasterEntity statusid;
	
	
	@Column(name = "SMS")
	char sms;
	
	@Column(name = "EMAIL")
	char email;
	
	@Column(name = "PUSH")
	char push;
	
	
	@Column(name = "CREATEDON")
	Date createdon;
	
	@Column(name = "UPDATEDON")
	Date updatedon;
	
	@Column(name = "CREATEDBY")
	int createdby;
	
	@Column(name = "UPDATEDBY")
	int updatedby;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


	public ActivityMaster getActivityid() {
		return activityid;
	}

	public void setActivityid(ActivityMaster activityid) {
		this.activityid = activityid;
	}

	public StatusMasterEntity getStatusid() {
		return statusid;
	}

	public void setStatusid(StatusMasterEntity statusid) {
		this.statusid = statusid;
	}

	public char getSms() {
		return sms;
	}

	public void setSms(char sms) {
		this.sms = sms;
	}

	public char getEmail() {
		return email;
	}

	public void setEmail(char email) {
		this.email = email;
	}

	public char getPush() {
		return push;
	}

	public void setPush(char push) {
		this.push = push;
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

	public int getCreatedby() {
		return createdby;
	}

	public void setCreatedby(int createdby) {
		this.createdby = createdby;
	}

	public int getUpdatedby() {
		return updatedby;
	}

	public void setUpdatedby(int updatedby) {
		this.updatedby = updatedby;
	}
	
	
	
}
