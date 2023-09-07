package com.itl.pns.impsEntity;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="DELIVERY_CHANNELS")
public class DeliveryChannelsEntity {


		@javax.persistence.Id
		@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "SEQ_DELIVERY_CHANNELS")
		@SequenceGenerator(name = "SEQ_DELIVERY_CHANNELS", sequenceName = "SEQ_DELIVERY_CHANNELS", allocationSize=1)
		@Column(name = "ID")
		private BigDecimal id;
			    
	    @Column(name = "NAME")
	    private String name;
		
		@Column(name = "nfs_channel_code")
	    private String nfs_channel_code;
	    
	    @Column(name = "daily_limit_amount")
	    private BigDecimal daily_limit_amount;
	    
	    @Column(name = "monthly_limit_amount")
	    private BigDecimal monthly_limit_amount;
	    
	    @Column(name = "mcc")
	    private String mcc;
	    
	    @Column(name = "pos_entry_mode")
	    private String pos_entry_mode;
	    
	    @Column(name = "pos_condition_code")
	    private String pos_condition_code;
 
	    @Column(name = "check_mpin")
	    private char check_mpin;

	    @Column(name = "cust_authenticated")
	    private char cust_authenticated;

	    @Column(name = "accnum_partial")
	    private char accnum_partial;

	    @Column(name = "otp_limit")
	    private BigDecimal otp_limit;
	    
	    @Column(name = "otp_validity")
	    private BigDecimal otp_validity;

	    @Column(name = "check_remitter_mobile")
	    private char check_remitter_mobile;

		public BigDecimal getId() {
			return id;
		}

		public void setId(BigDecimal id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getNfs_channel_code() {
			return nfs_channel_code;
		}

		public void setNfs_channel_code(String nfs_channel_code) {
			this.nfs_channel_code = nfs_channel_code;
		}

		public BigDecimal getDaily_limit_amount() {
			return daily_limit_amount;
		}

		public void setDaily_limit_amount(BigDecimal daily_limit_amount) {
			this.daily_limit_amount = daily_limit_amount;
		}

		public BigDecimal getMonthly_limit_amount() {
			return monthly_limit_amount;
		}

		public void setMonthly_limit_amount(BigDecimal monthly_limit_amount) {
			this.monthly_limit_amount = monthly_limit_amount;
		}

		public String getMcc() {
			return mcc;
		}

		public void setMcc(String mcc) {
			this.mcc = mcc;
		}

		public String getPos_entry_mode() {
			return pos_entry_mode;
		}

		public void setPos_entry_mode(String pos_entry_mode) {
			this.pos_entry_mode = pos_entry_mode;
		}

		public String getPos_condition_code() {
			return pos_condition_code;
		}

		public void setPos_condition_code(String pos_condition_code) {
			this.pos_condition_code = pos_condition_code;
		}

		public char getCheck_mpin() {
			return check_mpin;
		}

		public void setCheck_mpin(char check_mpin) {
			this.check_mpin = check_mpin;
		}

		public char getCust_authenticated() {
			return cust_authenticated;
		}

		public void setCust_authenticated(char cust_authenticated) {
			this.cust_authenticated = cust_authenticated;
		}

		public char getAccnum_partial() {
			return accnum_partial;
		}

		public void setAccnum_partial(char accnum_partial) {
			this.accnum_partial = accnum_partial;
		}

		public BigDecimal getOtp_limit() {
			return otp_limit;
		}

		public void setOtp_limit(BigDecimal otp_limit) {
			this.otp_limit = otp_limit;
		}

		public BigDecimal getOtp_validity() {
			return otp_validity;
		}

		public void setOtp_validity(BigDecimal otp_validity) {
			this.otp_validity = otp_validity;
		}

		public char getCheck_remitter_mobile() {
			return check_remitter_mobile;
		}

		public void setCheck_remitter_mobile(char check_remitter_mobile) {
			this.check_remitter_mobile = check_remitter_mobile;
		}    
}
