package com.itl.pns.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "PAYMENTAGGREGATORS")
public class PaymentagreegatorsEntity {

	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PAYMENTAGGREGATORS_SEQ")
	@SequenceGenerator(name = "PAYMENTAGGREGATORS_SEQ", sequenceName = "PAYMENTAGGREGATORS_SEQ", allocationSize = 1)
	@Id
	@Column(name = "ID")
	private Long id;

	@Column(name = "MERCHANTNAME")
	private String merchantname;

	@Column(name = "APPTYPE")
	private String appType;

	@Column(name = "MERCHANTCODE")
	private String merchantCode;

	@Column(name = "PGREFNO")
	private String paymentRefNo;

	@Column(name = "TXNAMOUNT")
	private Long TransactionAmount;

	@Column(name = "CHECKSUM")
	private String checkSum;

	@Column(name = "RETURNURL")
	private String returnUrl;

	@Column(name = "BANKTXNID")
	private String bankTransactionId;

	@Column(name = "FULLURL")
	private String fullUrl;

	@Column(name = "ACCOUNTNO")
	private String accNo;

	@Column(name = "CUSTOMERID")
	private String customerId;

	@Column(name = "VERIFYURL")
	private String verifyUrl;

	@Column(name = "VERIFYRESPONDEDON")
	private Timestamp verifyRespondedon;

	@Column(name = "CREATEDBY")
	private String createdBy;

	@Column(name = "CREATEDON")
	private Timestamp createdon;

	@Column(name = "STATUSID")
	private Long statusId;

	@Column(name = "REMARKS")
	private String remarks;

	@Column(name = "CONSUMER_CODE")
	private String consumerCode;

	@Column(name = "PAYEEACCOUNT")
	private String payeeAccount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMerchantname() {
		return merchantname;
	}

	public void setMerchantname(String merchantname) {
		this.merchantname = merchantname;
	}

	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getPaymentRefNo() {
		return paymentRefNo;
	}

	public void setPaymentRefNo(String paymentRefNo) {
		this.paymentRefNo = paymentRefNo;
	}

	public Long getTransactionAmount() {
		return TransactionAmount;
	}

	public void setTransactionAmount(Long transactionAmount) {
		TransactionAmount = transactionAmount;
	}

	public String getCheckSum() {
		return checkSum;
	}

	public void setCheckSum(String checkSum) {
		this.checkSum = checkSum;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public String getBankTransactionId() {
		return bankTransactionId;
	}

	public void setBankTransactionId(String bankTransactionId) {
		this.bankTransactionId = bankTransactionId;
	}

	public String getFullUrl() {
		return fullUrl;
	}

	public void setFullUrl(String fullUrl) {
		this.fullUrl = fullUrl;
	}

	public String getAccNo() {
		return accNo;
	}

	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getVerifyUrl() {
		return verifyUrl;
	}

	public void setVerifyUrl(String verifyUrl) {
		this.verifyUrl = verifyUrl;
	}

	public Timestamp getVerifyRespondedon() {
		return verifyRespondedon;
	}

	public void setVerifyRespondedon(Timestamp verifyRespondedon) {
		this.verifyRespondedon = verifyRespondedon;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedon() {
		return createdon;
	}

	public void setCreatedon(Timestamp createdon) {
		this.createdon = createdon;
	}

	public Long getStatusId() {
		return statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getConsumerCode() {
		return consumerCode;
	}

	public void setConsumerCode(String consumerCode) {
		this.consumerCode = consumerCode;
	}

	public String getPayeeAccount() {
		return payeeAccount;
	}

	public void setPayeeAccount(String payeeAccount) {
		this.payeeAccount = payeeAccount;
	}

}
