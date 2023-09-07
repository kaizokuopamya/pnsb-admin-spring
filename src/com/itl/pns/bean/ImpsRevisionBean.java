package com.itl.pns.bean;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class ImpsRevisionBean {

	
	BigDecimal id;
	Date rev_date;
	String info;
	String ref;
	BigDecimal author;
	public BigDecimal getId() {
		return id;
	}
	public void setId(BigDecimal id) {
		this.id = id;
	}
	public Date getRev_date() {
		return rev_date;
	}
	public void setRev_date(Date rev_date) {
		this.rev_date = rev_date;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getRef() {
		return ref;
	}
	public void setRef(String ref) {
		this.ref = ref;
	}
	public BigDecimal getAuthor() {
		return author;
	}
	public void setAuthor(BigDecimal author) {
		this.author = author;
	}
	
	
	
}
