package com.itl.pns.entity;

import java.math.BigDecimal;
import java.sql.Clob;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
@Entity
@Table(name="SURVEYMASTER_PRD")
public class SurveyMasterEntity {

	@javax.persistence.Id
	@Column(name = "ID") 	
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "surveymaster_prd_id_SEQ")
	@SequenceGenerator(name = "surveymaster_prd_id_SEQ", sequenceName = "surveymaster_prd_id_SEQ", allocationSize=1)
	private BigDecimal id;
	
	@Column(name = "SURVEYNAME")
	private Clob surveyNameClob;

	
	@Column(name = "SURVEYSTART")
	private Date surveyStart;
	
	@Column(name = "SURVEYEND")
	private Date surveyEnd;

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
	
	@Transient
	private String surveyName;;
	 
	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}



	public Date getSurveyStart() {
		return surveyStart;
	}

	public void setSurveyStart(Date surveyStart) {
		this.surveyStart = surveyStart;
	}

	public Date getSurveyEnd() {
		return surveyEnd;
	}

	public void setSurveyEnd(Date surveyEnd) {
		this.surveyEnd = surveyEnd;
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

	public void setSurveyNameClob(Clob surveyNameClob) {
		this.surveyNameClob = surveyNameClob;
	}


	public String getSurveyName() {
		return surveyName;
	}

	public void setSurveyName(String surveyName) {
		this.surveyName = surveyName;
	}	 
	
}
