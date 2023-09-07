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
@Table(name = "NOTIFICATIONS")
public class NotificationsEntity {

	@javax.persistence.Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notifications_id_SEQ")
	@SequenceGenerator(name = "notifications_id_SEQ", sequenceName = "notifications_id_SEQ", allocationSize = 1)
	private BigDecimal id;

	@Column(name = "CUSTOMERID")
	private String customerId;

	@Column(name = "CUST_ID")
	private BigDecimal custId;

	@Column(name = "NOTIFICATIONMSG")
	private String notificationMsg;

	@Column(name = "APPID")
	private BigDecimal appId;

	@Column(name = "ACTIVITYID")
	private BigDecimal activityId;

	@Column(name = "RRN")
	private String rrn;

	@Column(name = "CREATEDON")
	Date createdOn;

	@Column(name = "CREATEDBY")
	BigDecimal createdBy;

	@Column(name = "STATUSID")
	private BigDecimal statusId;

	@Column(name = "LASTRESENTON")
	public Date lastResentOn;

	@Column(name = "RESENDBY")
	private BigDecimal resendBy;

	@Column(name = "NOTIFICATION_TYPE")
	private BigDecimal type;

	@Transient
	private String channel;

	@Transient
	private String customerName;

	@Transient
	private String mobile;

	@Transient
	private String email;

	@Transient
	private String customerCif;

	@Transient
	private String typeName;

	@Transient
	private NotificationTemplateMaster notificationTemplateMaster;

	@Transient
	private String sendNotificationTo;

	@Transient
	private String deviceTokenId;

	@Transient
	private String fromDate;

	@Transient
	private String toDate;

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public BigDecimal getCustId() {
		return custId;
	}

	public void setCustId(BigDecimal custId) {
		this.custId = custId;
	}

	public String getNotificationMsg() {
		return notificationMsg;
	}

	public void setNotificationMsg(String notificationMsg) {
		this.notificationMsg = notificationMsg;
	}

	public BigDecimal getAppId() {
		return appId;
	}

	public void setAppId(BigDecimal appId) {
		this.appId = appId;
	}

	public BigDecimal getActivityId() {
		return activityId;
	}

	public void setActivityId(BigDecimal activityId) {
		this.activityId = activityId;
	}

	public String getRrn() {
		return rrn;
	}

	public void setRrn(String rrn) {
		this.rrn = rrn;
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

	public BigDecimal getStatusId() {
		return statusId;
	}

	public void setStatusId(BigDecimal statusId) {
		this.statusId = statusId;
	}

	public Date getLastResentOn() {
		return lastResentOn;
	}

	public void setLastResentOn(Date lastResentOn) {
		this.lastResentOn = lastResentOn;
	}

	public BigDecimal getResendBy() {
		return resendBy;
	}

	public void setResendBy(BigDecimal resendBy) {
		this.resendBy = resendBy;
	}

	public BigDecimal getType() {
		return type;
	}

	public void setType(BigDecimal type) {
		this.type = type;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerCif() {
		return customerCif;
	}

	public void setCustomerCif(String customerCif) {
		this.customerCif = customerCif;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public NotificationTemplateMaster getNotificationTemplateMaster() {
		return notificationTemplateMaster;
	}

	public void setNotificationTemplateMaster(NotificationTemplateMaster notificationTemplateMaster) {
		this.notificationTemplateMaster = notificationTemplateMaster;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSendNotificationTo() {
		return sendNotificationTo;
	}

	public void setSendNotificationTo(String sendNotificationTo) {
		this.sendNotificationTo = sendNotificationTo;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getDeviceTokenId() {
		return deviceTokenId;
	}

	public void setDeviceTokenId(String deviceTokenId) {
		this.deviceTokenId = deviceTokenId;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

}
