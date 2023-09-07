package com.itl.pns.bean;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Blob;
import java.util.Date;

public class ImpsCustomerDetails {
	

	BigInteger id;
	String mbl_account_no;
	String mbl_mobile_no;
	String mbl_mascode;
	BigDecimal mbl_tran_limit;
	String mbl_reg_channel;
	char mbl_charges_flag;
	char mbl_del_flag;
	Date mbl_rcre_date	;
	String mbl_rcre_user;
	Date mbl_modified_date;
	String mbl_bene_name;
	String mbl_solid;
	String mbl_req_stan	;
	char mbl_ac_type;
	Date mbl_pingen_date;
	BigDecimal mbl_wrongpin_count;
	String mbl_cust_type;
	String mbl_m_pin;
	Blob secure_data;
	String bdk;
	String mbl_aadhar_number;
	String cust_id;
	String user_id;
	String joint_acc;
	String opmode;
	String account_type_code;
	BigInteger impsCustId;
	
	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
		this.id = id;
	}
	public String getMbl_account_no() {
		return mbl_account_no;
	}
	public void setMbl_account_no(String mbl_account_no) {
		this.mbl_account_no = mbl_account_no;
	}
	public String getMbl_mobile_no() {
		return mbl_mobile_no;
	}
	public void setMbl_mobile_no(String mbl_mobile_no) {
		this.mbl_mobile_no = mbl_mobile_no;
	}
	public String getMbl_mascode() {
		return mbl_mascode;
	}
	public void setMbl_mascode(String mbl_mascode) {
		this.mbl_mascode = mbl_mascode;
	}
	public BigDecimal getMbl_tran_limit() {
		return mbl_tran_limit;
	}
	public void setMbl_tran_limit(BigDecimal mbl_tran_limit) {
		this.mbl_tran_limit = mbl_tran_limit;
	}
	public String getMbl_reg_channel() {
		return mbl_reg_channel;
	}
	public void setMbl_reg_channel(String mbl_reg_channel) {
		this.mbl_reg_channel = mbl_reg_channel;
	}
	public char getMbl_charges_flag() {
		return mbl_charges_flag;
	}
	public void setMbl_charges_flag(char mbl_charges_flag) {
		this.mbl_charges_flag = mbl_charges_flag;
	}
	public char getMbl_del_flag() {
		return mbl_del_flag;
	}
	public void setMbl_del_flag(char mbl_del_flag) {
		this.mbl_del_flag = mbl_del_flag;
	}
	public Date getMbl_rcre_date() {
		return mbl_rcre_date;
	}
	public void setMbl_rcre_date(Date mbl_rcre_date) {
		this.mbl_rcre_date = mbl_rcre_date;
	}
	public String getMbl_rcre_user() {
		return mbl_rcre_user;
	}
	public void setMbl_rcre_user(String mbl_rcre_user) {
		this.mbl_rcre_user = mbl_rcre_user;
	}
	public Date getMbl_modified_date() {
		return mbl_modified_date;
	}
	public void setMbl_modified_date(Date mbl_modified_date) {
		this.mbl_modified_date = mbl_modified_date;
	}
	public String getMbl_bene_name() {
		return mbl_bene_name;
	}
	public void setMbl_bene_name(String mbl_bene_name) {
		this.mbl_bene_name = mbl_bene_name;
	}
	public String getMbl_solid() {
		return mbl_solid;
	}
	public void setMbl_solid(String mbl_solid) {
		this.mbl_solid = mbl_solid;
	}
	public String getMbl_req_stan() {
		return mbl_req_stan;
	}
	public void setMbl_req_stan(String mbl_req_stan) {
		this.mbl_req_stan = mbl_req_stan;
	}
	public char getMbl_ac_type() {
		return mbl_ac_type;
	}
	public void setMbl_ac_type(char mbl_ac_type) {
		this.mbl_ac_type = mbl_ac_type;
	}
	public Date getMbl_pingen_date() {
		return mbl_pingen_date;
	}
	public void setMbl_pingen_date(Date mbl_pingen_date) {
		this.mbl_pingen_date = mbl_pingen_date;
	}
	public BigDecimal getMbl_wrongpin_count() {
		return mbl_wrongpin_count;
	}
	public void setMbl_wrongpin_count(BigDecimal mbl_wrongpin_count) {
		this.mbl_wrongpin_count = mbl_wrongpin_count;
	}
	public String getMbl_cust_type() {
		return mbl_cust_type;
	}
	public void setMbl_cust_type(String mbl_cust_type) {
		this.mbl_cust_type = mbl_cust_type;
	}
	public String getMbl_m_pin() {
		return mbl_m_pin;
	}
	public void setMbl_m_pin(String mbl_m_pin) {
		this.mbl_m_pin = mbl_m_pin;
	}
	public Blob getSecure_data() {
		return secure_data;
	}
	public void setSecure_data(Blob secure_data) {
		this.secure_data = secure_data;
	}
	public String getBdk() {
		return bdk;
	}
	public void setBdk(String bdk) {
		this.bdk = bdk;
	}
	public String getMbl_aadhar_number() {
		return mbl_aadhar_number;
	}
	public void setMbl_aadhar_number(String mbl_aadhar_number) {
		this.mbl_aadhar_number = mbl_aadhar_number;
	}
	public String getCust_id() {
		return cust_id;
	}
	public void setCust_id(String cust_id) {
		this.cust_id = cust_id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getJoint_acc() {
		return joint_acc;
	}
	public void setJoint_acc(String joint_acc) {
		this.joint_acc = joint_acc;
	}
	public String getOpmode() {
		return opmode;
	}
	public void setOpmode(String opmode) {
		this.opmode = opmode;
	}
	public String getAccount_type_code() {
		return account_type_code;
	}
	public void setAccount_type_code(String account_type_code) {
		this.account_type_code = account_type_code;
	}
	public BigInteger getImpsCustId() {
		return impsCustId;
	}
	public void setImpsCustId(BigInteger impsCustId) {
		this.impsCustId = impsCustId;
	}
	
	
}
