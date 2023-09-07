package com.itl.pns.bean;

import java.math.BigInteger;

public class ImpsTransactionFeeSetupBean {

	BigInteger id;
	BigInteger transaction_type	;
	BigInteger transaction_direction;
	char apply_fee;
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
