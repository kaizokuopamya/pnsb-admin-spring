package com.itl.pns.bean;

import java.sql.Timestamp;

public class TimestampDateBean {
	private Timestamp fromdate;
	private Timestamp todate;
	private String table_name;

	public Timestamp getFromdate() {
		return fromdate;
	}

	public void setFromdate(Timestamp fromdate) {
		this.fromdate = fromdate;
	}

	public Timestamp getTodate() {
		return todate;
	}

	public void setTodate(Timestamp todate) {
		this.todate = todate;
	}

	public String getTable_name() {
		return table_name;
	}

	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}

}
