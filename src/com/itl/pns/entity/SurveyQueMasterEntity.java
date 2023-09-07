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
@Table(name = "SURVEYQUEMASTER_PRD")
public class SurveyQueMasterEntity {

	@javax.persistence.Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "surveyquemaster_prd_id_SEQ")
	@SequenceGenerator(name = "surveyquemaster_prd_id_SEQ", sequenceName = "surveyquemaster_prd_id_SEQ", allocationSize = 1)
	private BigDecimal id;

	@Column(name = "SURVEYID")
	private BigDecimal surveyId;

	@Column(name = "SURVEYQUE")
	private String surveyQue;

	@Column(name = "CREATEDBY")
	private BigDecimal createdby;

	@Column(name = "CREATEDON")
	private Date createdon;

	@Column(name = "STATUSID")
	private BigDecimal statusId;

	@Transient
	private String statusname;

	@Transient
	private String createdByName;

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public BigDecimal getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(BigDecimal surveyId) {
		this.surveyId = surveyId;
	}

	public String getSurveyQue() {
		return surveyQue;
	}

	public void setSurveyQue(String surveyQue) {
		this.surveyQue = surveyQue;
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

	public BigDecimal getStatusId() {
		return statusId;
	}

	public void setStatusId(BigDecimal statusId) {
		this.statusId = statusId;
	}

	public String getStatusname() {
		return statusname;
	}

	public void setStatusname(String statusname) {
		this.statusname = statusname;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

}
