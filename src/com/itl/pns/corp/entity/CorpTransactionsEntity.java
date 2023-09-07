package com.itl.pns.corp.entity;

import java.math.BigInteger;
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
@Table(name = "CORP_TRANSACTIONS")
public class CorpTransactionsEntity {

	@javax.persistence.Id
	@Column(name = "ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
	@SequenceGenerator(name = "id_Sequence", sequenceName = "corp_transactions_ID_SEQ", allocationSize=1)
	private BigInteger id;

	@Column(name = "CORP_COMP_ID")
	private BigInteger corp_comp_id;

	@Column(name = "AMOUNT")
	private int amount;

	@Column(name = "REMARKS")
	private String remarks;

	@Column(name = "PAYMENT_TYPE")
	private String payment_type;

	@Column(name = "CREATEDBY")
	private int createdBy;

	@Column(name = "CREATEDON")
	private Date createdOn;

	@Column(name = "STATUSID")
	private BigInteger statusId;

	@Column(name = "APPID")
	private BigInteger appId;

	@Column(name = "TO_BENEFICIARY_ACCOUNT")
	private String to_beneficary_account;

	@Column(name = "TRNREFNO")
	private String trnRefNo;

	@Column(name = "FROM_ACCOUNT")
	private String from_account;

	@Column(name = "CREDITCURRENCY")
	private String creditCurrency;

	@Column(name = "DEBITCURRENCY")
	private String debitCurrency;

	@Column(name = "UPDATEDBY")
	private BigInteger updatedBy;

	@Column(name = "UPDATEON")
	private Date updateOn;
	
	@Transient
	private String statusName;   
	
	@Transient
	private String appName;   

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public BigInteger getCorp_comp_id() {
		return corp_comp_id;
	}

	public void setCorp_comp_id(BigInteger corp_comp_id) {
		this.corp_comp_id = corp_comp_id;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getPayment_type() {
		return payment_type;
	}

	public void setPayment_type(String payment_type) {
		this.payment_type = payment_type;
	}

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public BigInteger getStatusId() {
		return statusId;
	}

	public void setStatusId(BigInteger statusId) {
		this.statusId = statusId;
	}

	public BigInteger getAppId() {
		return appId;
	}

	public void setAppId(BigInteger appId) {
		this.appId = appId;
	}

	public String getTo_beneficary_account() {
		return to_beneficary_account;
	}

	public void setTo_beneficary_account(String to_beneficary_account) {
		this.to_beneficary_account = to_beneficary_account;
	}

	public String getTrnRefNo() {
		return trnRefNo;
	}

	public void setTrnRefNo(String trnRefNo) {
		this.trnRefNo = trnRefNo;
	}

	public String getFrom_account() {
		return from_account;
	}

	public void setFrom_account(String from_account) {
		this.from_account = from_account;
	}

	public String getCreditCurrency() {
		return creditCurrency;
	}

	public void setCreditCurrency(String creditCurrency) {
		this.creditCurrency = creditCurrency;
	}

	public String getDebitCurrency() {
		return debitCurrency;
	}

	public void setDebitCurrency(String debitCurrency) {
		this.debitCurrency = debitCurrency;
	}

	public BigInteger getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(BigInteger updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdateOn() {
		return updateOn;
	}

	public void setUpdateOn(Date updateOn) {
		this.updateOn = updateOn;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	
}
