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
@Table(name = "THEMESIDEBARBACKGROUND")
public class ThemeSideBarBackgroundEntity {

	@javax.persistence.Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "themesidebarbackground_ID_SEQ")
	@SequenceGenerator(name = "themesidebarbackground_ID_SEQ", sequenceName = "themesidebarbackground_ID_SEQ", allocationSize = 1)
	private BigDecimal id;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "DETAILS")
	private String details;

	@Column(name = "statusid")
	private BigDecimal statusid;

	@Column(name = "createdby")
	private BigDecimal createdby;

	@Column(name = "createdon")
	private Date createdon;

	@Column(name = "updatedby")
	private BigDecimal updatedby;

	@Column(name = "updatedon")
	private Date updatedon;

	public BigDecimal getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public BigDecimal getStatusid() {
		return statusid;
	}

	public void setStatusid(BigDecimal statusid) {
		this.statusid = statusid;
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

	public BigDecimal getUpdatedby() {
		return updatedby;
	}

	public void setUpdatedby(BigDecimal updatedby) {
		this.updatedby = updatedby;
	}

	public Date getUpdatedon() {
		return updatedon;
	}

	public void setUpdatedon(Date updatedon) {
		this.updatedon = updatedon;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

}
