package com.itl.pns.entity;

import java.math.BigDecimal;
import java.sql.Date;

public class MessageReport {
	
	private BigDecimal id;
	
	private String shortName;

	private String req_res;

	private Date createdOn;
	
	private int statudId;
	
	private int appId;
	
	private String fromdate;

	private String todate;

	private BigDecimal message;

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getReq_res() {
		return req_res;
	}

	public void setReq_res(String req_res) {
		this.req_res = req_res;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public int getStatudId() {
		return statudId;
	}

	public void setStatudId(int statudId) {
		this.statudId = statudId;
	}

	public int getAppId() {
		return appId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}

	public String getFromdate() {
		return fromdate;
	}

	public void setFromdate(String fromdate) {
		this.fromdate = fromdate;
	}

	public String getTodate() {
		return todate;
	}

	public void setTodate(String todate) {
		this.todate = todate;
	}

	public BigDecimal getMessage() {
		return message;
	}

	public void setMessage(BigDecimal message) {
		this.message = message;
	}

	public MessageReport(BigDecimal id, String shortName, String req_res, Date createdOn, int statudId, int appId,
			String fromdate, String todate, BigDecimal message) {
		super();
		this.id = id;
		this.shortName = shortName;
		this.req_res = req_res;
		this.createdOn = createdOn;
		this.statudId = statudId;
		this.appId = appId;
		this.fromdate = fromdate;
		this.todate = todate;
		this.message = message;
	}

	public MessageReport() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "MessageReport [message=" + message + "]";
	}
}
