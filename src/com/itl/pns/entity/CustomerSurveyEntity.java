package com.itl.pns.entity;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "CUSTSURVEY")
public class CustomerSurveyEntity {
	@javax.persistence.Id
	@Column(name = "ID") 
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
	@SequenceGenerator(name = "id_Sequence", sequenceName = "custsurvey_prd_id_SEQ", allocationSize=1)
	private BigInteger id;

	
	@Column(name = "CUSTOMERID")
	private BigInteger customerId;

	@Column(name = "RRN")
	private String RRN;
	
	@Column(name = "SURVEYID")
	private BigInteger surveyId;
	
	@Column(name = "SURVEYANSID")
	private BigInteger surveyAnsId;
	
	@Column(name = "SURVEYQUE")
	private BigInteger surveyQue;
	
	
	@Column(name = "SURVEYFEEDBACK")
	private String surveyFeedback;
	
	@Column(name = "SURVEYRATINGS")
	private String surveyRatings;
	
	
	@Column(name = "SURVEYSTART")
	private Date surveyStart;
	
	@Column(name = "SURVEYEND")
	private Date surveyEnd;

	@Column(name = "CREATEDBY")
	private BigInteger createdby;

	@Column(name = "CREATEDON")
	private Date createdon;

	@Column(name = "STATUSID")
	private BigInteger statusId;

	 @Transient
	    private String statusname;

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

	public String getRRN() {
		return RRN;
	}

	public void setRRN(String rRN) {
		RRN = rRN;
	}

	public BigInteger getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(BigInteger surveyId) {
		this.surveyId = surveyId;
	}

	public BigInteger getSurveyAnsId() {
		return surveyAnsId;
	}

	public void setSurveyAnsId(BigInteger surveyAnsId) {
		this.surveyAnsId = surveyAnsId;
	}

	public BigInteger getSurveyQue() {
		return surveyQue;
	}

	public void setSurveyQue(BigInteger surveyQue) {
		this.surveyQue = surveyQue;
	}

	public String getSurveyFeedback() {
		return surveyFeedback;
	}

	public void setSurveyFeedback(String surveyFeedback) {
		this.surveyFeedback = surveyFeedback;
	}

	public String getSurveyRatings() {
		return surveyRatings;
	}

	public void setSurveyRatings(String surveyRatings) {
		this.surveyRatings = surveyRatings;
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

	public BigInteger getCreatedby() {
		return createdby;
	}

	public void setCreatedby(BigInteger createdby) {
		this.createdby = createdby;
	}

	public Date getCreatedon() {
		return createdon;
	}

	public void setCreatedon(Date createdon) {
		this.createdon = createdon;
	}

	public BigInteger getStatusId() {
		return statusId;
	}

	public void setStatusId(BigInteger statusId) {
		this.statusId = statusId;
	}

	public String getStatusname() {
		return statusname;
	}

	public void setStatusname(String statusname) {
		this.statusname = statusname;
	}
	 
	 
}
