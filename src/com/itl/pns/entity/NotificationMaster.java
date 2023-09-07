package com.itl.pns.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "NOTIFICATIONMASTER")
public class NotificationMaster {

	
	@javax.persistence.Id
	@Column(name = "ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "notificationmaster_id_SEQ")
	@SequenceGenerator(name = "notificationmaster_id_SEQ", sequenceName = "notificationmaster_id_SEQ", allocationSize=1)
	private BigDecimal id;
    
	
	@Column(name = "CHANNELID")
	BigDecimal channelId;
	  
	@Column(name = "SHORTNAME")
	String shortName;
	
	@Column(name = "CREATEDON")
	Date createdOn;
	
	@Column(name = "CREATEDBY")
	BigDecimal createdBy;
	
	@Column(name = "STATUSID")
	private BigDecimal statusId;

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public BigDecimal getChannelId() {
		return channelId;
	}

	public void setChannelId(BigDecimal channelId) {
		this.channelId = channelId;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public BigDecimal getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(BigDecimal createdBy) {
		this.createdBy = createdBy;
	}

	public BigDecimal getStatusId() {
		return statusId;
	}

	public void setStatusId(BigDecimal statusId) {
		this.statusId = statusId;
	}
	
	
	
}
