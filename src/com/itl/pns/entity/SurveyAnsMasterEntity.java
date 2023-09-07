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
@Table(name="SURVEYANSMASTER_PRD")
public class SurveyAnsMasterEntity {

	@javax.persistence.Id
	@Column(name = "ID") 
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "surveyansmaster_prd_id_SEQ")
	@SequenceGenerator(name = "surveyansmaster_prd_id_SEQ", sequenceName = "surveyansmaster_prd_id_SEQ", allocationSize=1)
	private BigDecimal id;
	
	
	@Column(name = "SURVEYID")
	private BigDecimal surveyId;
	
	@Column(name = "SURVEYQUEID")
	private BigDecimal surveyQueId;
	
	@Column(name = "SURVEYANS1")
	private String surveyAns1;
	
	@Column(name = "SURVEYANS2")
	private String surveyAns2;
	
	@Column(name = "SURVEYANS3")
	private String surveyAns3;
	
	@Column(name = "SURVEYANS4")
	private String surveyAns4;
	
	@Column(name = "CREATEDBY")
	private BigDecimal createdby;

	@Column(name = "CREATEDON")
	private Date createdon;

	@Column(name = "STATUSID")
	private BigDecimal statusId;

	 @Transient
	    private String statusname;
	 
	 @Transient
	  private String surveyQue;
	 
	 @Transient
	  private BigDecimal surveyAnsId;
	 

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

	public BigDecimal getSurveyQueId() {
		return surveyQueId;
	}

	public void setSurveyQueId(BigDecimal surveyQueId) {
		this.surveyQueId = surveyQueId;
	}

	public String getSurveyAns1() {
		return surveyAns1;
	}

	public void setSurveyAns1(String surveyAns1) {
		this.surveyAns1 = surveyAns1;
	}

	public String getSurveyAns2() {
		return surveyAns2;
	}

	public void setSurveyAns2(String surveyAns2) {
		this.surveyAns2 = surveyAns2;
	}

	public String getSurveyAns3() {
		return surveyAns3;
	}

	public void setSurveyAns3(String surveyAns3) {
		this.surveyAns3 = surveyAns3;
	}

	public String getSurveyAns4() {
		return surveyAns4;
	}

	public void setSurveyAns4(String surveyAns4) {
		this.surveyAns4 = surveyAns4;
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

	public String getSurveyQue() {
		return surveyQue;
	}

	public void setSurveyQue(String surveyQue) {
		this.surveyQue = surveyQue;
	}

	public BigDecimal getSurveyAnsId() {
		return surveyAnsId;
	}

	public void setSurveyAnsId(BigDecimal surveyAnsId) {
		this.surveyAnsId = surveyAnsId;
	}	 
}
