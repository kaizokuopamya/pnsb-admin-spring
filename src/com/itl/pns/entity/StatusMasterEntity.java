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
@Table(name="STATUSMASTER")
public class StatusMasterEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "statusmaster_id_SEQ")
	@SequenceGenerator(name = "statusmaster_id_SEQ", sequenceName = "statusmaster_id_SEQ", allocationSize=1)
	public BigDecimal id;
		
	 @Column(name = "NAME")
	 String name;
	
	 @Column(name = "CREATEDON")
	 Date createdOn;
	 
	 @Column(name = "CREATEDBY")
	 BigDecimal createdBy;
	
	 @Column(name = "ISACTIVE")
	 String isActive;
	
	 @Column(name = "CODE")
	 String code;
	 
	 @Transient
	 String createdByName;

	 @Transient
	 String shortName;
	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	
	
	

}
