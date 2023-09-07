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
import javax.persistence.Transient;

@Entity
@Table(name = "APPMASTER")
public class AppMasterEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APPMASTER_SEQ")
	@SequenceGenerator(name = "APPMASTER_SEQ", sequenceName = " APPMASTER_SEQ", allocationSize = 1)
	public BigDecimal id;

	@Column(name = "SHORTNAME")
	String shortName;

	@Column(name = "CHANNELID")
	int channelId;

	@Column(name = "CREATEDBY")
	BigDecimal createdby;

	@Column(name = "CREATEDON")
	Date createdOn;

	@Column(name = "STATUSID")
	int statusId;

	@Transient
	String createdByName;

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public int getChannelId() {
		return channelId;
	}

	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}

	public BigDecimal getCreatedby() {
		return createdby;
	}

	public void setCreatedby(BigDecimal createdby) {
		this.createdby = createdby;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

}
