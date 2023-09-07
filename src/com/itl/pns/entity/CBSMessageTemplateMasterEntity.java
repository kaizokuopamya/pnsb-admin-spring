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

@Entity
@Table(name = "CBS_MESSAGE_TEMPLATE_MASTER")
public class CBSMessageTemplateMasterEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "MESSAGETEMPLATE_ID_SEQ")
	@SequenceGenerator(name = "MESSAGETEMPLATE_ID_SEQ", sequenceName = "MESSAGETEMPLATE_ID_SEQ", allocationSize=1)
	BigDecimal id;

	@Column(name = "ERRORCODE")
	String errorcode;
	
	
	@Column(name = "ERRORMESSAGE")
	String errormessage;
	
	@Column(name = "SMS")
	char sms;
	
	@Column(name = "EMAIL")
	char email;
	
	@Column(name = "PUSH")
	char push;
	
	
	@Column(name = "INAPP")
	char inapp;
	
	
	@Column(name = "SMSTEMPLATE")
	String smstemplate;
	
	@Column(name = "EMAILTEMPLATE")
	String emailtemplate;
	
	@Column(name = "PUSHTEMPLATE")
	String pushtemplate;
	
	
	@Column(name = "INAPPTEMPLATE")
	String inapptemplate;
	
	
	@Column(name = "STATUSID")
	BigDecimal statusid;
	
	@Column(name = "CREATEDON")
	Date createdon;
	
	@Column(name = "UPDATEDON")
	Date updatedon;
	
	@Column(name = "CREATEDBY")
	BigDecimal createdby;
	
	@Column(name = "UPDATEDBY")
	BigDecimal updatedby;

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getErrorcode() {
		return errorcode;
	}

	public void setErrorcode(String errorcode) {
		this.errorcode = errorcode;
	}

	public String getErrormessage() {
		return errormessage;
	}

	public void setErrormessage(String errormessage) {
		this.errormessage = errormessage;
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

	public char getInapp() {
		return inapp;
	}

	public void setInapp(char inapp) {
		this.inapp = inapp;
	}

	public String getSmstemplate() {
		return smstemplate;
	}

	public void setSmstemplate(String smstemplate) {
		this.smstemplate = smstemplate;
	}

	public String getEmailtemplate() {
		return emailtemplate;
	}

	public void setEmailtemplate(String emailtemplate) {
		this.emailtemplate = emailtemplate;
	}

	public String getPushtemplate() {
		return pushtemplate;
	}

	public void setPushtemplate(String pushtemplate) {
		this.pushtemplate = pushtemplate;
	}

	public String getInapptemplate() {
		return inapptemplate;
	}

	public void setInapptemplate(String inapptemplate) {
		this.inapptemplate = inapptemplate;
	}

	public BigDecimal getStatusid() {
		return statusid;
	}

	public void setStatusid(BigDecimal statusid) {
		this.statusid = statusid;
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

	public BigDecimal getCreatedby() {
		return createdby;
	}

	public void setCreatedby(BigDecimal createdby) {
		this.createdby = createdby;
	}

	public BigDecimal getUpdatedby() {
		return updatedby;
	}

	public void setUpdatedby(BigDecimal updatedby) {
		this.updatedby = updatedby;
	}

	

	
}
