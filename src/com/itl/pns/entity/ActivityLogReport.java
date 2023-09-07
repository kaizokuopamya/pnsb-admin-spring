package com.itl.pns.entity;

import java.util.Date;

public class ActivityLogReport {
	
	private String eventName;
	
	private String category;
	
	private String action;
	
	private String createdByName;
	
	private Date createdOn;
	
	private String updatedByName;
	
	private Date updatedOn;
	
	private String fromdate;

	private String todate;

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getUpdatedByName() {
		return updatedByName;
	}

	public void setUpdatedByName(String updatedByName) {
		this.updatedByName = updatedByName;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updateOn) {
		this.updatedOn = updateOn;
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

	
	public ActivityLogReport(String eventName, String category, String action, String createdByName, Date createdOn,
			String updatedByName, Date updatedOn, String fromdate, String todate) {
		super();
		this.eventName = eventName;
		this.category = category;
		this.action = action;
		this.createdByName = createdByName;
		this.createdOn = createdOn;
		this.updatedByName = updatedByName;
		this.updatedOn = updatedOn;
		this.fromdate = fromdate;
		this.todate = todate;
	}

	public ActivityLogReport() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "ActivityLogReport [eventName=" + eventName + ", category=" + category + ", action=" + action
				+ ", createdByName=" + createdByName + ", createdOn=" + createdOn + ", updatedByName=" + updatedByName
				+ ", updatedOn=" + updatedOn + ", fromdate=" + fromdate + ", todate=" + todate + "]";
	}
}
