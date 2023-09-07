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
@Table(name = "MESSAGES_MASTER")
public class MessageMasterEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MESSAGES_MASTER_SEQ")
	@SequenceGenerator(name = "MESSAGES_MASTER_SEQ", sequenceName = "MESSAGES_MASTER_SEQ", allocationSize = 1)
	private BigDecimal id;

	@Column(name = "SERVICETYPE")
	private String serviceType;

	@Column(name = "ENTITYID")
	private String entityId;

	@Column(name = "ENDPOINT")
	private String endPoint;

	@Column(name = "MESSAGETYPE")
	private String messagetype;

	@Column(name = "SMSMESSAGE")
	private String smsMessage;

	@Column(name = "SUBJECT")
	private String subject;

	@Column(name = "EMAILMESSAGE")
	private String emailMessage;

	@Column(name = "PUSHMESSAGE")
	private String pushMessage;

	@Column(name = "INAPPMESSAGE")
	private String inAppMessage;

	@Column(name = "OTHERMESSAG1")
	private String otherMessag1;

	@Column(name = "OTHERMESSAG2")
	private String otherMessag2;

	@Column(name = "OTHERMESSAG3")
	private String otherMessag3;

	@Column(name = "OTHERMESSAG4")
	private String otherMessag4;

	@Column(name = "CREATEDBY")
	private BigDecimal createdBy;

	@Column(name = "CREATEDON")
	private Date createdOn;

	@Column(name = "STATUSID")
	private BigDecimal statusId;

	@Transient
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}

	public String getMessagetype() {
		return messagetype;
	}

	public void setMessagetype(String messagetype) {
		this.messagetype = messagetype;
	}

	public String getSmsMessage() {
		return smsMessage;
	}

	public void setSmsMessage(String smsMessage) {
		this.smsMessage = smsMessage;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getEmailMessage() {
		return emailMessage;
	}

	public void setEmailMessage(String emailMessage) {
		this.emailMessage = emailMessage;
	}

	public String getPushMessage() {
		return pushMessage;
	}

	public void setPushMessage(String pushMessage) {
		this.pushMessage = pushMessage;
	}

	public String getInAppMessage() {
		return inAppMessage;
	}

	public void setInAppMessage(String inAppMessage) {
		this.inAppMessage = inAppMessage;
	}

	public String getOtherMessag1() {
		return otherMessag1;
	}

	public void setOtherMessag1(String otherMessag1) {
		this.otherMessag1 = otherMessag1;
	}

	public String getOtherMessag2() {
		return otherMessag2;
	}

	public void setOtherMessag2(String otherMessag2) {
		this.otherMessag2 = otherMessag2;
	}

	public String getOtherMessag3() {
		return otherMessag3;
	}

	public void setOtherMessag3(String otherMessag3) {
		this.otherMessag3 = otherMessag3;
	}

	public String getOtherMessag4() {
		return otherMessag4;
	}

	public void setOtherMessag4(String otherMessag4) {
		this.otherMessag4 = otherMessag4;
	}

	public BigDecimal getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(BigDecimal createdBy) {
		this.createdBy = createdBy;
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

}
