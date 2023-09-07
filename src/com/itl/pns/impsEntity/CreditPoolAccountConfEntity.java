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
@Table(name="CREDIT_POOL_ACCOUNT_CONF")
public class CreditPoolAccountConfEntity {

	@javax.persistence.Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "SEQ_CREDIT_POOL")
	@SequenceGenerator(name = "SEQ_CREDIT_POOL", sequenceName = "SEQ_CREDIT_POOL", allocationSize=1)
	@Column(name = "ID")
	private BigDecimal id;
		    
    @Column(name = "tran_type")
    private String tran_type;
	
	@Column(name = "channel_id")
    private BigInteger channel_id;
	
    @Column(name = "default_account")
    private String default_account;

    @Column(name = "source_identifier")
    private String source_identifier;
	
    @Column(name = "ismultipleaccount")
    private char ismultipleaccount;

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getTran_type() {
		return tran_type;
	}

	public void setTran_type(String tran_type) {
		this.tran_type = tran_type;
	}

	public BigInteger getChannel_id() {
		return channel_id;
	}

	public void setChannel_id(BigInteger channel_id) {
		this.channel_id = channel_id;
	}

	public String getDefault_account() {
		return default_account;
	}

	public void setDefault_account(String default_account) {
		this.default_account = default_account;
	}

	public String getSource_identifier() {
		return source_identifier;
	}

	public void setSource_identifier(String source_identifier) {
		this.source_identifier = source_identifier;
	}

	public char getIsmultipleaccount() {
		return ismultipleaccount;
	}

	public void setIsmultipleaccount(char ismultipleaccount) {
		this.ismultipleaccount = ismultipleaccount;
	}
	

}
