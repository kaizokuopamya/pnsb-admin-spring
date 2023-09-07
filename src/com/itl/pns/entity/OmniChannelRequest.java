package com.itl.pns.entity;

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
@Table(name = "OMNICHANNELREQUEST")
public class OmniChannelRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
	@SequenceGenerator(name = "id_Sequence", sequenceName = "omnichannelrequest_id_SEQ", allocationSize = 1)
	int id;

	@Column(name = "APPID")
	int appId;

	@Column(name = "CUSTOMERID")
	int customerId;

	@Column(name = "ACCOUNTNO")
	String accountno;

	@Column(name = "REFNO")
	String refno;

	@Column(name = "SERVICETYPE")
	String serviceType;

	@Column(name = "CREATEDON")
	Date createdOn;

	@Column(name = "UPDATEDON")
	Date updatedOn;

	@Column(name = "CREATEDBY")
	String createdBy;

	@Column(name = "UPDATEDBY")
	String updatedBy;

	@Column(name = "CONTENT")
	String content;

	@Column(name = "STATUSID")
	int statusId;

	@Column(name = "CHANNELACTION")
	String channelaction;

	@Transient
	private String otpCode;

	@Transient
	private String mobile;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAppId() {
		return appId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getAccountno() {
		return accountno;
	}

	public void setAccountno(String accountno) {
		this.accountno = accountno;
	}

	public String getRefno() {
		return refno;
	}

	public void setRefno(String refno) {
		this.refno = refno;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getStatus() {
		return statusId;
	}

	public void setStatus(int status) {
		this.statusId = status;
	}

	public String getChannelaction() {
		return channelaction;
	}

	public void setChannelaction(String channelaction) {
		this.channelaction = channelaction;
	}

	public String getOtpCode() {
		return otpCode;
	}

	public void setOtpCode(String otpCode) {
		this.otpCode = otpCode;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}
