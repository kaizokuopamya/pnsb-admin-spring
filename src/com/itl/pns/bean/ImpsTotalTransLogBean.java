
package com.itl.pns.bean;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;

public class ImpsTotalTransLogBean extends TransLogBean {
	BigInteger id;
	String direction;
	BigDecimal tran_type;
	String bene_nbin;
	String bene_mas;
	String bene_ifsc;
	String bene_account;
	String bene_mobile;
	String bene_name;
	String remitter_mmid;
	String remitter_mobile;
	String remitter_name;
	String product_indicator;
	String remarks;
	String originating_channel;
	String status;
	String original_data;
	String f120_request;
	String f120_response;
	String cdci_req_status;
	String cdci_rc;
	String cdci_rev_status;
	String cdci_rev_rc;
	BigInteger tran_id;
	BigInteger asp_id;
	BigInteger submember_id;
	Date created_date;
	String nfs_status;
	String nfs_response_code;
	String remitter_account;
	BigDecimal remitter_charge;
	BigDecimal remitter_service_tax;
	char remitter_charge_applicable;
	BigDecimal amount;
	String nfs_verify_status;
	String nfs_verify_rc;
	String f120_vm_response;
	BigInteger delivery_channel_id;
	BigInteger cust_id_remitter;
	BigInteger cust_id_bene;
	BigInteger bc_id;
	String bc_retailer_code;
	String bc_trans_ref_no;
	BigInteger merchant_detail_id;
	String merchant_tran_ref;
	String nfs_error_text;
	String merchant_tran_id;
	String pg_tran_id;
	char otp_indicator;
	String bene_aadhar_number;
	BigInteger ipay_tran_id;
	String intermediary_ifsc;
	String nri_institution_name;
	String nri_inst_account_number;
	String originator_name;
	String originator_account_number;
	String originator_address;
	String purpose_code;
	String nri_referenceno;
	String exchange_referenceno;
	String cdci_txn_type;
	Date bc_transaction_time;
	String bc_mer_bene_txn;
	String cdci_ip;
	String cdci_port;
	String user_id;
	String customer_id;
	String debit_pull_account;
	String credit_pull_account;
	String ss_trans_ref_no;
	Date ss_transaction_time;
	String atm_tid;
	String atm_location;
	String instrument_dtls;
	String additonal_info;
	String source_channel;
	String source_payee_name;
	String payer_bank;
	String payee_bank;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public BigDecimal getTran_type() {
		return tran_type;
	}

	public void setTran_type(BigDecimal tran_type) {
		this.tran_type = tran_type;
	}

	public String getBene_nbin() {
		return bene_nbin;
	}

	public void setBene_nbin(String bene_nbin) {
		this.bene_nbin = bene_nbin;
	}

	public String getBene_mas() {
		return bene_mas;
	}

	public void setBene_mas(String bene_mas) {
		this.bene_mas = bene_mas;
	}

	public String getBene_ifsc() {
		return bene_ifsc;
	}

	public void setBene_ifsc(String bene_ifsc) {
		this.bene_ifsc = bene_ifsc;
	}

	public String getBene_account() {
		return bene_account;
	}

	public void setBene_account(String bene_account) {
		this.bene_account = bene_account;
	}

	public String getBene_mobile() {
		return bene_mobile;
	}

	public void setBene_mobile(String bene_mobile) {
		this.bene_mobile = bene_mobile;
	}

	public String getBene_name() {
		return bene_name;
	}

	public void setBene_name(String bene_name) {
		this.bene_name = bene_name;
	}

	public String getRemitter_mmid() {
		return remitter_mmid;
	}

	public void setRemitter_mmid(String remitter_mmid) {
		this.remitter_mmid = remitter_mmid;
	}

	public String getRemitter_mobile() {
		return remitter_mobile;
	}

	public void setRemitter_mobile(String remitter_mobile) {
		this.remitter_mobile = remitter_mobile;
	}

	public String getRemitter_name() {
		return remitter_name;
	}

	public void setRemitter_name(String remitter_name) {
		this.remitter_name = remitter_name;
	}

	public String getProduct_indicator() {
		return product_indicator;
	}

	public void setProduct_indicator(String product_indicator) {
		this.product_indicator = product_indicator;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getOriginating_channel() {
		return originating_channel;
	}

	public void setOriginating_channel(String originating_channel) {
		this.originating_channel = originating_channel;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOriginal_data() {
		return original_data;
	}

	public void setOriginal_data(String original_data) {
		this.original_data = original_data;
	}

	public String getF120_request() {
		return f120_request;
	}

	public void setF120_request(String f120_request) {
		this.f120_request = f120_request;
	}

	public String getF120_response() {
		return f120_response;
	}

	public void setF120_response(String f120_response) {
		this.f120_response = f120_response;
	}

	public String getCdci_req_status() {
		return cdci_req_status;
	}

	public void setCdci_req_status(String cdci_req_status) {
		this.cdci_req_status = cdci_req_status;
	}

	public String getCdci_rc() {
		return cdci_rc;
	}

	public void setCdci_rc(String cdci_rc) {
		this.cdci_rc = cdci_rc;
	}

	public String getCdci_rev_status() {
		return cdci_rev_status;
	}

	public void setCdci_rev_status(String cdci_rev_status) {
		this.cdci_rev_status = cdci_rev_status;
	}

	public String getCdci_rev_rc() {
		return cdci_rev_rc;
	}

	public void setCdci_rev_rc(String cdci_rev_rc) {
		this.cdci_rev_rc = cdci_rev_rc;
	}

	public BigInteger getTran_id() {
		return tran_id;
	}

	public void setTran_id(BigInteger tran_id) {
		this.tran_id = tran_id;
	}

	public BigInteger getAsp_id() {
		return asp_id;
	}

	public void setAsp_id(BigInteger asp_id) {
		this.asp_id = asp_id;
	}

	public BigInteger getSubmember_id() {
		return submember_id;
	}

	public void setSubmember_id(BigInteger submember_id) {
		this.submember_id = submember_id;
	}

	public Date getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}

	public String getNfs_status() {
		return nfs_status;
	}

	public void setNfs_status(String nfs_status) {
		this.nfs_status = nfs_status;
	}

	public String getNfs_response_code() {
		return nfs_response_code;
	}

	public void setNfs_response_code(String nfs_response_code) {
		this.nfs_response_code = nfs_response_code;
	}

	public String getRemitter_account() {
		return remitter_account;
	}

	public void setRemitter_account(String remitter_account) {
		this.remitter_account = remitter_account;
	}

	public BigDecimal getRemitter_charge() {
		return remitter_charge;
	}

	public void setRemitter_charge(BigDecimal remitter_charge) {
		this.remitter_charge = remitter_charge;
	}

	public BigDecimal getRemitter_service_tax() {
		return remitter_service_tax;
	}

	public void setRemitter_service_tax(BigDecimal remitter_service_tax) {
		this.remitter_service_tax = remitter_service_tax;
	}

	public char getRemitter_charge_applicable() {
		return remitter_charge_applicable;
	}

	public void setRemitter_charge_applicable(char remitter_charge_applicable) {
		this.remitter_charge_applicable = remitter_charge_applicable;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getNfs_verify_status() {
		return nfs_verify_status;
	}

	public void setNfs_verify_status(String nfs_verify_status) {
		this.nfs_verify_status = nfs_verify_status;
	}

	public String getNfs_verify_rc() {
		return nfs_verify_rc;
	}

	public void setNfs_verify_rc(String nfs_verify_rc) {
		this.nfs_verify_rc = nfs_verify_rc;
	}

	public String getF120_vm_response() {
		return f120_vm_response;
	}

	public void setF120_vm_response(String f120_vm_response) {
		this.f120_vm_response = f120_vm_response;
	}

	public BigInteger getDelivery_channel_id() {
		return delivery_channel_id;
	}

	public void setDelivery_channel_id(BigInteger delivery_channel_id) {
		this.delivery_channel_id = delivery_channel_id;
	}

	public BigInteger getCust_id_remitter() {
		return cust_id_remitter;
	}

	public void setCust_id_remitter(BigInteger cust_id_remitter) {
		this.cust_id_remitter = cust_id_remitter;
	}

	public BigInteger getCust_id_bene() {
		return cust_id_bene;
	}

	public void setCust_id_bene(BigInteger cust_id_bene) {
		this.cust_id_bene = cust_id_bene;
	}

	public BigInteger getBc_id() {
		return bc_id;
	}

	public void setBc_id(BigInteger bc_id) {
		this.bc_id = bc_id;
	}

	public String getBc_retailer_code() {
		return bc_retailer_code;
	}

	public void setBc_retailer_code(String bc_retailer_code) {
		this.bc_retailer_code = bc_retailer_code;
	}

	public String getBc_trans_ref_no() {
		return bc_trans_ref_no;
	}

	public void setBc_trans_ref_no(String bc_trans_ref_no) {
		this.bc_trans_ref_no = bc_trans_ref_no;
	}

	public BigInteger getMerchant_detail_id() {
		return merchant_detail_id;
	}

	public void setMerchant_detail_id(BigInteger merchant_detail_id) {
		this.merchant_detail_id = merchant_detail_id;
	}

	public String getMerchant_tran_ref() {
		return merchant_tran_ref;
	}

	public void setMerchant_tran_ref(String merchant_tran_ref) {
		this.merchant_tran_ref = merchant_tran_ref;
	}

	public String getNfs_error_text() {
		return nfs_error_text;
	}

	public void setNfs_error_text(String nfs_error_text) {
		this.nfs_error_text = nfs_error_text;
	}

	public String getMerchant_tran_id() {
		return merchant_tran_id;
	}

	public void setMerchant_tran_id(String merchant_tran_id) {
		this.merchant_tran_id = merchant_tran_id;
	}

	public String getPg_tran_id() {
		return pg_tran_id;
	}

	public void setPg_tran_id(String pg_tran_id) {
		this.pg_tran_id = pg_tran_id;
	}

	public char getOtp_indicator() {
		return otp_indicator;
	}

	public void setOtp_indicator(char otp_indicator) {
		this.otp_indicator = otp_indicator;
	}

	public String getBene_aadhar_number() {
		return bene_aadhar_number;
	}

	public void setBene_aadhar_number(String bene_aadhar_number) {
		this.bene_aadhar_number = bene_aadhar_number;
	}

	public BigInteger getIpay_tran_id() {
		return ipay_tran_id;
	}

	public void setIpay_tran_id(BigInteger ipay_tran_id) {
		this.ipay_tran_id = ipay_tran_id;
	}

	public String getIntermediary_ifsc() {
		return intermediary_ifsc;
	}

	public void setIntermediary_ifsc(String intermediary_ifsc) {
		this.intermediary_ifsc = intermediary_ifsc;
	}

	public String getNri_institution_name() {
		return nri_institution_name;
	}

	public void setNri_institution_name(String nri_institution_name) {
		this.nri_institution_name = nri_institution_name;
	}

	public String getNri_inst_account_number() {
		return nri_inst_account_number;
	}

	public void setNri_inst_account_number(String nri_inst_account_number) {
		this.nri_inst_account_number = nri_inst_account_number;
	}

	public String getOriginator_name() {
		return originator_name;
	}

	public void setOriginator_name(String originator_name) {
		this.originator_name = originator_name;
	}

	public String getOriginator_account_number() {
		return originator_account_number;
	}

	public void setOriginator_account_number(String originator_account_number) {
		this.originator_account_number = originator_account_number;
	}

	public String getOriginator_address() {
		return originator_address;
	}

	public void setOriginator_address(String originator_address) {
		this.originator_address = originator_address;
	}

	public String getPurpose_code() {
		return purpose_code;
	}

	public void setPurpose_code(String purpose_code) {
		this.purpose_code = purpose_code;
	}

	public String getNri_referenceno() {
		return nri_referenceno;
	}

	public void setNri_referenceno(String nri_referenceno) {
		this.nri_referenceno = nri_referenceno;
	}

	public String getExchange_referenceno() {
		return exchange_referenceno;
	}

	public void setExchange_referenceno(String exchange_referenceno) {
		this.exchange_referenceno = exchange_referenceno;
	}

	public String getCdci_txn_type() {
		return cdci_txn_type;
	}

	public void setCdci_txn_type(String cdci_txn_type) {
		this.cdci_txn_type = cdci_txn_type;
	}

	public Date getBc_transaction_time() {
		return bc_transaction_time;
	}

	public void setBc_transaction_time(Date bc_transaction_time) {
		this.bc_transaction_time = bc_transaction_time;
	}

	public String getBc_mer_bene_txn() {
		return bc_mer_bene_txn;
	}

	public void setBc_mer_bene_txn(String bc_mer_bene_txn) {
		this.bc_mer_bene_txn = bc_mer_bene_txn;
	}

	public String getCdci_ip() {
		return cdci_ip;
	}

	public void setCdci_ip(String cdci_ip) {
		this.cdci_ip = cdci_ip;
	}

	public String getCdci_port() {
		return cdci_port;
	}

	public void setCdci_port(String cdci_port) {
		this.cdci_port = cdci_port;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}

	public String getDebit_pull_account() {
		return debit_pull_account;
	}

	public void setDebit_pull_account(String debit_pull_account) {
		this.debit_pull_account = debit_pull_account;
	}

	public String getCredit_pull_account() {
		return credit_pull_account;
	}

	public void setCredit_pull_account(String credit_pull_account) {
		this.credit_pull_account = credit_pull_account;
	}

	public String getSs_trans_ref_no() {
		return ss_trans_ref_no;
	}

	public void setSs_trans_ref_no(String ss_trans_ref_no) {
		this.ss_trans_ref_no = ss_trans_ref_no;
	}

	public Date getSs_transaction_time() {
		return ss_transaction_time;
	}

	public void setSs_transaction_time(Date ss_transaction_time) {
		this.ss_transaction_time = ss_transaction_time;
	}

	public String getAtm_tid() {
		return atm_tid;
	}

	public void setAtm_tid(String atm_tid) {
		this.atm_tid = atm_tid;
	}

	public String getAtm_location() {
		return atm_location;
	}

	public void setAtm_location(String atm_location) {
		this.atm_location = atm_location;
	}

	public String getInstrument_dtls() {
		return instrument_dtls;
	}

	public void setInstrument_dtls(String instrument_dtls) {
		this.instrument_dtls = instrument_dtls;
	}

	public String getAdditonal_info() {
		return additonal_info;
	}

	public void setAdditonal_info(String additonal_info) {
		this.additonal_info = additonal_info;
	}

	public String getSource_channel() {
		return source_channel;
	}

	public void setSource_channel(String source_channel) {
		this.source_channel = source_channel;
	}

	public String getSource_payee_name() {
		return source_payee_name;
	}

	public void setSource_payee_name(String source_payee_name) {
		this.source_payee_name = source_payee_name;
	}

	public String getPayer_bank() {
		return payer_bank;
	}

	public void setPayer_bank(String payer_bank) {
		this.payer_bank = payer_bank;
	}

	public String getPayee_bank() {
		return payee_bank;
	}

	public void setPayee_bank(String payee_bank) {
		this.payee_bank = payee_bank;
	}
}
