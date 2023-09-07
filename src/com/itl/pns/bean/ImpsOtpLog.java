package com.itl.pns.bean;

import java.math.BigInteger;
import java.util.Date;

public class ImpsOtpLog extends ImpsCustomerDetails {

	
	BigInteger id;
	String secured_otp;
	String otp_reference_number;
	BigInteger status;
	BigInteger creation_txn_id;
	Date created_on;
	Date expire_on;
	BigInteger used_txn_id;
	Date used_on;
	BigInteger customer_id;
	BigInteger channel_id;
	
	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
		this.id = id;
	}
	public String getSecured_otp() {
		return secured_otp;
	}
	public void setSecured_otp(String secured_otp) {
		this.secured_otp = secured_otp;
	}
	public String getOtp_reference_number() {
		return otp_reference_number;
	}
	public void setOtp_reference_number(String otp_reference_number) {
		this.otp_reference_number = otp_reference_number;
	}
	public BigInteger getStatus() {
		return status;
	}
	public void setStatus(BigInteger status) {
		this.status = status;
	}
	public BigInteger getCreation_txn_id() {
		return creation_txn_id;
	}
	public void setCreation_txn_id(BigInteger creation_txn_id) {
		this.creation_txn_id = creation_txn_id;
	}
	public Date getCreated_on() {
		return created_on;
	}
	public void setCreated_on(Date created_on) {
		this.created_on = created_on;
	}
	public Date getExpire_on() {
		return expire_on;
	}
	public void setExpire_on(Date expire_on) {
		this.expire_on = expire_on;
	}
	public BigInteger getUsed_txn_id() {
		return used_txn_id;
	}
	public void setUsed_txn_id(BigInteger used_txn_id) {
		this.used_txn_id = used_txn_id;
	}
	public Date getUsed_on() {
		return used_on;
	}
	public void setUsed_on(Date used_on) {
		this.used_on = used_on;
	}
	public BigInteger getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(BigInteger customer_id) {
		this.customer_id = customer_id;
	}
	public BigInteger getChannel_id() {
		return channel_id;
	}
	public void setChannel_id(BigInteger channel_id) {
		this.channel_id = channel_id;
	}
	
	
}
