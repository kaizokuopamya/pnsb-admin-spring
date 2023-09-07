package com.itl.pns.bean;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Blob;
import java.sql.Date;
import java.sql.Timestamp;

public class TransLogBean {

	BigInteger id;
	BigInteger trasId;
	String subclass;
	BigInteger local_id;
	String node;
	String ss;
	String ds;
	String acquirer;
	String forwarding;
	String mid;
	String tid;
	String hash;
	String pan;
	String kid;
	Blob secure_data;
	String ss_stan;
	String ss_rrn;
	String ds_rrn;
	String ds_stan;
	String ca_name;
	String ca_city;
	String ca_region;
	String ca_postal_code;
	String ca_country;
	String mcc;
	String function_code;
	String ss_transport_data;
	String ds_transport_data;
	Blob pos_data_code;
	String response_code;
	String approval_number;
	String display_message;
	BigInteger reversal_count;
	BigInteger reversal_id;
	BigDecimal completion_count;
	BigInteger completion_id;
	BigDecimal void_count;
	BigInteger void_id;
	BigDecimal notification_count;
	String original_mti;
	Date tdate;
	Date transmission_date;
	Timestamp local_transaction_date;
	Date capture_date;
	Date settlement_date;
	BigInteger batch_number;
	BigInteger source_batch_number;
	BigInteger destination_batch_number;
	String itc;
	String src_acct_type;
	String dest_acct_type;
	String irc;
	String currency_code;
	BigDecimal amount;
	BigDecimal additional_amount;
	BigDecimal amount_cardholder_billing;
	BigDecimal acquirer_fee;
	BigDecimal issuer_fee;
	String returned_balances;
	String from_account;
	String to_account;
	String mini_statement;
	String smw_command;
	String smw_params;
	String smw_cmd_response;
	String smw_ext_response;
	BigDecimal avlbl_bal_from;
	BigDecimal legder_bal_from;
	BigDecimal avlbl_bal_to;
	BigDecimal legder_bal_to;
	String rc;
	String extrc;
	BigDecimal duration;
	BigInteger outstanding;
	String ss_rc;
	String ds_rc;
	String ss_pcode;
	String ds_pcode;
	String host_command;
	String host_cmd_response;
	String host_ext_response;
	BigInteger cardproduct;
	BigInteger refid;
	BigInteger notification;
	BigDecimal trasAmt;

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getSubclass() {
		return subclass;
	}

	public void setSubclass(String subclass) {
		this.subclass = subclass;
	}

	public BigInteger getLocal_id() {
		return local_id;
	}

	public void setLocal_id(BigInteger local_id) {
		this.local_id = local_id;
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public String getSs() {
		return ss;
	}

	public void setSs(String ss) {
		this.ss = ss;
	}

	public String getDs() {
		return ds;
	}

	public void setDs(String ds) {
		this.ds = ds;
	}

	public String getAcquirer() {
		return acquirer;
	}

	public void setAcquirer(String acquirer) {
		this.acquirer = acquirer;
	}

	public String getForwarding() {
		return forwarding;
	}

	public void setForwarding(String forwarding) {
		this.forwarding = forwarding;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public String getKid() {
		return kid;
	}

	public void setKid(String kid) {
		this.kid = kid;
	}

	public Blob getSecure_data() {
		return secure_data;
	}

	public void setSecure_data(Blob secure_data) {
		this.secure_data = secure_data;
	}

	public String getSs_stan() {
		return ss_stan;
	}

	public void setSs_stan(String ss_stan) {
		this.ss_stan = ss_stan;
	}

	public String getSs_rrn() {
		return ss_rrn;
	}

	public void setSs_rrn(String ss_rrn) {
		this.ss_rrn = ss_rrn;
	}

	public String getDs_rrn() {
		return ds_rrn;
	}

	public void setDs_rrn(String ds_rrn) {
		this.ds_rrn = ds_rrn;
	}

	public String getDs_stan() {
		return ds_stan;
	}

	public void setDs_stan(String ds_stan) {
		this.ds_stan = ds_stan;
	}

	public String getCa_name() {
		return ca_name;
	}

	public void setCa_name(String ca_name) {
		this.ca_name = ca_name;
	}

	public String getCa_city() {
		return ca_city;
	}

	public void setCa_city(String ca_city) {
		this.ca_city = ca_city;
	}

	public String getCa_region() {
		return ca_region;
	}

	public void setCa_region(String ca_region) {
		this.ca_region = ca_region;
	}

	public String getCa_postal_code() {
		return ca_postal_code;
	}

	public void setCa_postal_code(String ca_postal_code) {
		this.ca_postal_code = ca_postal_code;
	}

	public String getCa_country() {
		return ca_country;
	}

	public void setCa_country(String ca_country) {
		this.ca_country = ca_country;
	}

	public String getMcc() {
		return mcc;
	}

	public void setMcc(String mcc) {
		this.mcc = mcc;
	}

	public String getFunction_code() {
		return function_code;
	}

	public void setFunction_code(String function_code) {
		this.function_code = function_code;
	}

	public String getSs_transport_data() {
		return ss_transport_data;
	}

	public void setSs_transport_data(String ss_transport_data) {
		this.ss_transport_data = ss_transport_data;
	}

	public String getDs_transport_data() {
		return ds_transport_data;
	}

	public void setDs_transport_data(String ds_transport_data) {
		this.ds_transport_data = ds_transport_data;
	}

	public Blob getPos_data_code() {
		return pos_data_code;
	}

	public void setPos_data_code(Blob pos_data_code) {
		this.pos_data_code = pos_data_code;
	}

	public String getResponse_code() {
		return response_code;
	}

	public void setResponse_code(String response_code) {
		this.response_code = response_code;
	}

	public String getApproval_number() {
		return approval_number;
	}

	public void setApproval_number(String approval_number) {
		this.approval_number = approval_number;
	}

	public String getDisplay_message() {
		return display_message;
	}

	public void setDisplay_message(String display_message) {
		this.display_message = display_message;
	}

	public BigInteger getReversal_count() {
		return reversal_count;
	}

	public void setReversal_count(BigInteger reversal_count) {
		this.reversal_count = reversal_count;
	}

	public BigInteger getReversal_id() {
		return reversal_id;
	}

	public void setReversal_id(BigInteger reversal_id) {
		this.reversal_id = reversal_id;
	}

	public BigDecimal getCompletion_count() {
		return completion_count;
	}

	public void setCompletion_count(BigDecimal completion_count) {
		this.completion_count = completion_count;
	}

	public BigInteger getCompletion_id() {
		return completion_id;
	}

	public void setCompletion_id(BigInteger completion_id) {
		this.completion_id = completion_id;
	}

	public BigDecimal getVoid_count() {
		return void_count;
	}

	public void setVoid_count(BigDecimal void_count) {
		this.void_count = void_count;
	}

	public BigInteger getVoid_id() {
		return void_id;
	}

	public void setVoid_id(BigInteger void_id) {
		this.void_id = void_id;
	}

	public BigDecimal getNotification_count() {
		return notification_count;
	}

	public void setNotification_count(BigDecimal notification_count) {
		this.notification_count = notification_count;
	}

	public String getOriginal_mti() {
		return original_mti;
	}

	public void setOriginal_mti(String original_mti) {
		this.original_mti = original_mti;
	}

	public Date getTdate() {
		return tdate;
	}

	public void setTdate(Date tdate) {
		this.tdate = tdate;
	}

	public Date getTransmission_date() {
		return transmission_date;
	}

	public void setTransmission_date(Date transmission_date) {
		this.transmission_date = transmission_date;
	}

	public Timestamp getLocal_transaction_date() {
		return local_transaction_date;
	}

	public void setLocal_transaction_date(Timestamp local_transaction_date) {
		this.local_transaction_date = local_transaction_date;
	}

	public Date getCapture_date() {
		return capture_date;
	}

	public void setCapture_date(Date capture_date) {
		this.capture_date = capture_date;
	}

	public Date getSettlement_date() {
		return settlement_date;
	}

	public void setSettlement_date(Date settlement_date) {
		this.settlement_date = settlement_date;
	}

	public BigInteger getBatch_number() {
		return batch_number;
	}

	public void setBatch_number(BigInteger batch_number) {
		this.batch_number = batch_number;
	}

	public BigInteger getSource_batch_number() {
		return source_batch_number;
	}

	public void setSource_batch_number(BigInteger source_batch_number) {
		this.source_batch_number = source_batch_number;
	}

	public BigInteger getDestination_batch_number() {
		return destination_batch_number;
	}

	public void setDestination_batch_number(BigInteger destination_batch_number) {
		this.destination_batch_number = destination_batch_number;
	}

	public String getItc() {
		return itc;
	}

	public void setItc(String itc) {
		this.itc = itc;
	}

	public String getSrc_acct_type() {
		return src_acct_type;
	}

	public void setSrc_acct_type(String src_acct_type) {
		this.src_acct_type = src_acct_type;
	}

	public String getDest_acct_type() {
		return dest_acct_type;
	}

	public void setDest_acct_type(String dest_acct_type) {
		this.dest_acct_type = dest_acct_type;
	}

	public String getIrc() {
		return irc;
	}

	public void setIrc(String irc) {
		this.irc = irc;
	}

	public String getCurrency_code() {
		return currency_code;
	}

	public void setCurrency_code(String currency_code) {
		this.currency_code = currency_code;
	}

	public BigDecimal getAdditional_amount() {
		return additional_amount;
	}

	public void setAdditional_amount(BigDecimal additional_amount) {
		this.additional_amount = additional_amount;
	}

	public BigDecimal getAmount_cardholder_billing() {
		return amount_cardholder_billing;
	}

	public void setAmount_cardholder_billing(BigDecimal amount_cardholder_billing) {
		this.amount_cardholder_billing = amount_cardholder_billing;
	}

	public BigDecimal getAcquirer_fee() {
		return acquirer_fee;
	}

	public void setAcquirer_fee(BigDecimal acquirer_fee) {
		this.acquirer_fee = acquirer_fee;
	}

	public BigDecimal getIssuer_fee() {
		return issuer_fee;
	}

	public void setIssuer_fee(BigDecimal issuer_fee) {
		this.issuer_fee = issuer_fee;
	}

	public String getReturned_balances() {
		return returned_balances;
	}

	public void setReturned_balances(String returned_balances) {
		this.returned_balances = returned_balances;
	}

	public String getFrom_account() {
		return from_account;
	}

	public void setFrom_account(String from_account) {
		this.from_account = from_account;
	}

	public String getTo_account() {
		return to_account;
	}

	public void setTo_account(String to_account) {
		this.to_account = to_account;
	}

	public String getMini_statement() {
		return mini_statement;
	}

	public void setMini_statement(String mini_statement) {
		this.mini_statement = mini_statement;
	}

	public String getSmw_command() {
		return smw_command;
	}

	public void setSmw_command(String smw_command) {
		this.smw_command = smw_command;
	}

	public String getSmw_params() {
		return smw_params;
	}

	public void setSmw_params(String smw_params) {
		this.smw_params = smw_params;
	}

	public String getSmw_cmd_response() {
		return smw_cmd_response;
	}

	public void setSmw_cmd_response(String smw_cmd_response) {
		this.smw_cmd_response = smw_cmd_response;
	}

	public String getSmw_ext_response() {
		return smw_ext_response;
	}

	public void setSmw_ext_response(String smw_ext_response) {
		this.smw_ext_response = smw_ext_response;
	}

	public BigDecimal getAvlbl_bal_from() {
		return avlbl_bal_from;
	}

	public void setAvlbl_bal_from(BigDecimal avlbl_bal_from) {
		this.avlbl_bal_from = avlbl_bal_from;
	}

	public BigDecimal getLegder_bal_from() {
		return legder_bal_from;
	}

	public void setLegder_bal_from(BigDecimal legder_bal_from) {
		this.legder_bal_from = legder_bal_from;
	}

	public BigDecimal getAvlbl_bal_to() {
		return avlbl_bal_to;
	}

	public void setAvlbl_bal_to(BigDecimal avlbl_bal_to) {
		this.avlbl_bal_to = avlbl_bal_to;
	}

	public BigDecimal getLegder_bal_to() {
		return legder_bal_to;
	}

	public void setLegder_bal_to(BigDecimal legder_bal_to) {
		this.legder_bal_to = legder_bal_to;
	}

	public String getRc() {
		return rc;
	}

	public void setRc(String rc) {
		this.rc = rc;
	}

	public String getExtrc() {
		return extrc;
	}

	public void setExtrc(String extrc) {
		this.extrc = extrc;
	}

	public BigDecimal getDuration() {
		return duration;
	}

	public void setDuration(BigDecimal duration) {
		this.duration = duration;
	}

	public BigInteger getOutstanding() {
		return outstanding;
	}

	public void setOutstanding(BigInteger outstanding) {
		this.outstanding = outstanding;
	}

	public String getSs_rc() {
		return ss_rc;
	}

	public void setSs_rc(String ss_rc) {
		this.ss_rc = ss_rc;
	}

	public String getDs_rc() {
		return ds_rc;
	}

	public void setDs_rc(String ds_rc) {
		this.ds_rc = ds_rc;
	}

	public String getSs_pcode() {
		return ss_pcode;
	}

	public void setSs_pcode(String ss_pcode) {
		this.ss_pcode = ss_pcode;
	}

	public String getDs_pcode() {
		return ds_pcode;
	}

	public void setDs_pcode(String ds_pcode) {
		this.ds_pcode = ds_pcode;
	}

	public String getHost_command() {
		return host_command;
	}

	public void setHost_command(String host_command) {
		this.host_command = host_command;
	}

	public String getHost_cmd_response() {
		return host_cmd_response;
	}

	public void setHost_cmd_response(String host_cmd_response) {
		this.host_cmd_response = host_cmd_response;
	}

	public String getHost_ext_response() {
		return host_ext_response;
	}

	public void setHost_ext_response(String host_ext_response) {
		this.host_ext_response = host_ext_response;
	}

	public BigInteger getCardproduct() {
		return cardproduct;
	}

	public void setCardproduct(BigInteger cardproduct) {
		this.cardproduct = cardproduct;
	}

	public BigInteger getRefid() {
		return refid;
	}

	public void setRefid(BigInteger refid) {
		this.refid = refid;
	}

	public BigInteger getNotification() {
		return notification;
	}

	public void setNotification(BigInteger notification) {
		this.notification = notification;
	}

	public BigDecimal getTrasAmt() {
		return trasAmt;
	}

	public void setTrasAmt(BigDecimal trasAmt) {
		this.trasAmt = trasAmt;
	}

	public BigInteger getTrasId() {
		return trasId;
	}

	public void setTrasId(BigInteger trasId) {
		this.trasId = trasId;
	}

	@Override
	public String toString() {
		return "TransLogBean [ss=" + ss + ", pan=" + pan + ", approval_number=" + approval_number
				+ ", local_transaction_date=" + local_transaction_date + ", itc=" + itc + ", irc=" + irc + ", amount="
				+ amount + ", duration=" + duration + "]";
	}

}