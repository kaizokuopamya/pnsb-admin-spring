package com.itl.pns.entity;

import java.math.BigDecimal;
import java.sql.Date;


public class ServiceRequestEntity {

	private BigDecimal id;
	private String shortName;
	private String req_res;
	private String createdon;
	private BigDecimal statusid;
	private BigDecimal Count_REQ_RES;
	private  BigDecimal result;
	private Date Fromdate;
	private Date Todate;
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
	public String getCreatedon() {
		return createdon;
	}
	public void setCreatedon(String createdon) {
		this.createdon = createdon;
	}
	public BigDecimal getStatusid() {
		return statusid;
	}
	public void setStatusid(BigDecimal statusid) {
		this.statusid = statusid;
	}
	public BigDecimal getCount_REQ_RES() {
		return Count_REQ_RES;
	}
	public void setCount_REQ_RES(BigDecimal count_REQ_RES) {
		Count_REQ_RES = count_REQ_RES;
	}
	public BigDecimal getResult() {
		return result;
	}
	public void setResult(BigDecimal result) {
		this.result = result;
	}
	public Date getFromdate() {
		return Fromdate;
	}
	public void setFromdate(Date fromdate) {
		Fromdate = fromdate;
	}
	public Date getTodate() {
		return Todate;
	}
	public void setTodate(Date todate) {
		Todate = todate;
	}
	public ServiceRequestEntity(BigDecimal id, String shortName, String req_res, String createdon, BigDecimal statusid,
			BigDecimal count_REQ_RES, BigDecimal result, Date fromdate, Date todate) {
		super();
		this.id = id;
		this.shortName = shortName;
		this.req_res = req_res;
		this.createdon = createdon;
		this.statusid = statusid;
		Count_REQ_RES = count_REQ_RES;
		this.result = result;
		Fromdate = fromdate;
		Todate = todate;
	}
	public ServiceRequestEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "ServiceRequestEntity [req_res=" + req_res + ", createdon=" + createdon + ", statusid=" + statusid
				+ ", Count_REQ_RES=" + Count_REQ_RES + "]";
	}
	}