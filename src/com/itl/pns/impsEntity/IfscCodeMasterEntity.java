package com.itl.pns.impsEntity;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
@Entity
@Table(name="IFSC_CODE_MASTER")
public class IfscCodeMasterEntity {


		@javax.persistence.Id
		@Column(name = "ID")
		@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "SEQUENCE_IFSC_CODE_MASTER")
		@SequenceGenerator(name = "SEQUENCE_IFSC_CODE_MASTER", sequenceName = "SEQUENCE_IFSC_CODE_MASTER", allocationSize=1)
		private BigDecimal id;
		
		@Column(name = "ifsc_code")
	    private String ifsc_code;

		@Column(name = "nbin")
	    private String nbin;

		@Column(name = "bank_name")
	    private String bank_name;

		@Column(name = "created_by")
	    private String created_by;

		@Column(name = "created_on")
	    private Date created_on;
		
	
		@Column(name = "short_code")
	    private String short_code;
		
		@Column(name = "use_customized_ifsc")
	    private char use_customized_ifsc;
		
		@Column(name = "bank_type")
	    private String bank_type;

		
		@Column(name = "member_type")
	    private String member_type;
		
		
		@Column(name = "is_live")
	    private char is_live;


		public BigDecimal getId() {
			return id;
		}


		public void setId(BigDecimal id) {
			this.id = id;
		}


		public String getIfsc_code() {
			return ifsc_code;
		}


		public void setIfsc_code(String ifsc_code) {
			this.ifsc_code = ifsc_code;
		}


		public String getNbin() {
			return nbin;
		}


		public void setNbin(String nbin) {
			this.nbin = nbin;
		}


		public String getBank_name() {
			return bank_name;
		}


		public void setBank_name(String bank_name) {
			this.bank_name = bank_name;
		}


		public String getCreated_by() {
			return created_by;
		}


		public void setCreated_by(String created_by) {
			this.created_by = created_by;
		}


		public Date getCreated_on() {
			return created_on;
		}


		public void setCreated_on(Date created_on) {
			this.created_on = created_on;
		}


		public String getShort_code() {
			return short_code;
		}


		public void setShort_code(String short_code) {
			this.short_code = short_code;
		}


		public char getUse_customized_ifsc() {
			return use_customized_ifsc;
		}


		public void setUse_customized_ifsc(char use_customized_ifsc) {
			this.use_customized_ifsc = use_customized_ifsc;
		}


		public String getBank_type() {
			return bank_type;
		}


		public void setBank_type(String bank_type) {
			this.bank_type = bank_type;
		}


		

		public String getMember_type() {
			return member_type;
		}


		public void setMember_type(String member_type) {
			this.member_type = member_type;
		}


		public char getIs_live() {
			return is_live;
		}


		public void setIs_live(char is_live) {
			this.is_live = is_live;
		}
		
		
}
