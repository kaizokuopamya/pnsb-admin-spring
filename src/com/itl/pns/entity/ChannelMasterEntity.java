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
@Table(name = "CHANNELMASTER")
public class ChannelMasterEntity {

	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
	@SequenceGenerator(name = "id_Sequence", sequenceName = "channelmaster_ID_SEQ", allocationSize=1)
	private BigDecimal id;
	
	@Column(name = "SHORTNAME")
	private String shortName;
	
	@Column(name = "CREATEDBY")
	private BigDecimal createdby;
	
	@Column(name = "CREATEDON")
	private Date createdOn;
	
	
	@Column(name = "STATUSID")
	private BigDecimal statusId;

	@Transient
	private String createdByName;
	
	
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
	
	
	
	
	
}
