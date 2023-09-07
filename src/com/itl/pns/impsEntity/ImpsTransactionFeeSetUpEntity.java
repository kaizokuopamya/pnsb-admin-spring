package com.itl.pns.impsEntity;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

@Entity
@Table(name="TRANSACTION_FEE_SETUP")
public class ImpsTransactionFeeSetUpEntity {

	@javax.persistence.Id
	@Column(name = "ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	BigInteger id;
	
	@Column(name = "transaction_type")
	BigInteger transaction_type	;
	
	@Column(name = "transaction_direction")
	BigInteger transaction_direction;
	
	@Column(name = "apply_fee")
	char apply_fee;
	
	@Column(name = "description")
	String description;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public BigInteger getTransaction_type() {
		return transaction_type;
	}

	public void setTransaction_type(BigInteger transaction_type) {
		this.transaction_type = transaction_type;
	}

	public BigInteger getTransaction_direction() {
		return transaction_direction;
	}

	public void setTransaction_direction(BigInteger transaction_direction) {
		this.transaction_direction = transaction_direction;
	}

	public char getApply_fee() {
		return apply_fee;
	}

	public void setApply_fee(char apply_fee) {
		this.apply_fee = apply_fee;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
}
