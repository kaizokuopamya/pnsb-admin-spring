package com.itl.pns.impsEntity;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

@Entity
@Table(name="SCHEDULE")
public class ScheduleEntity<email_password> {


	
	@javax.persistence.Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
    private BigInteger id;
	
	@Column(name = "schedule_desc")
    private String schedule_desc;
	
	@Column(name = "task_interval")
    private String task_interval;
	
	@Column(name = "interval_unit")
    private BigInteger interval_unit;
	
	@Column(name = "last_date")
    private Date last_date;
	
	@Column(name = "next_exec_datetime")
    private Date next_exec_datetime;
	
	@Column(name = "execution_count")
    private BigInteger execution_count;
	
	@Column(name = "active")
    private char active;
			
	@Column(name = "delivery_type")
    private String delivery_type;
			
	@Column(name = "email_from")
    private String email_from;
	
	@Column(name = "email_password")
    private String email_password;
		
	@Column(name = "email_to")
    private String email_to;
		
	@Column(name = "email_cc")
    private String email_cc;
			
	@Column(name = "email_content")
    private String email_content;
		
	@Column(name = "ftp_host")
    private String ftp_host;
	
	@Column(name = "ftp_port")
    private BigDecimal ftp_port;
	
	@Column(name = "ftp_user")
    private String ftp_user;

	@Column(name = "ftp_password")
    private String ftp_password;

	@Column(name = "ftp_remote_dir")
    private String ftp_remote_dir;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getSchedule_desc() {
		return schedule_desc;
	}

	public void setSchedule_desc(String schedule_desc) {
		this.schedule_desc = schedule_desc;
	}

	public String getTask_interval() {
		return task_interval;
	}

	public void setTask_interval(String task_interval) {
		this.task_interval = task_interval;
	}

	public BigInteger getInterval_unit() {
		return interval_unit;
	}

	public void setInterval_unit(BigInteger interval_unit) {
		this.interval_unit = interval_unit;
	}

	public Date getLast_date() {
		return last_date;
	}

	public void setLast_date(Date last_date) {
		this.last_date = last_date;
	}

	public Date getNext_exec_datetime() {
		return next_exec_datetime;
	}

	public void setNext_exec_datetime(Date next_exec_datetime) {
		this.next_exec_datetime = next_exec_datetime;
	}

	public BigInteger getExecution_count() {
		return execution_count;
	}

	public void setExecution_count(BigInteger execution_count) {
		this.execution_count = execution_count;
	}

	public char getActive() {
		return active;
	}

	public void setActive(char active) {
		this.active = active;
	}

	public String getDelivery_type() {
		return delivery_type;
	}

	public void setDelivery_type(String delivery_type) {
		this.delivery_type = delivery_type;
	}

	public String getEmail_from() {
		return email_from;
	}

	public void setEmail_from(String email_from) {
		this.email_from = email_from;
	}

	public String getEmail_password() {
		return email_password;
	}

	public void setEmail_password(String email_password) {
		this.email_password = email_password;
	}

	public String getEmail_to() {
		return email_to;
	}

	public void setEmail_to(String email_to) {
		this.email_to = email_to;
	}

	public String getEmail_cc() {
		return email_cc;
	}

	public void setEmail_cc(String email_cc) {
		this.email_cc = email_cc;
	}

	public String getEmail_content() {
		return email_content;
	}

	public void setEmail_content(String email_content) {
		this.email_content = email_content;
	}

	public String getFtp_host() {
		return ftp_host;
	}

	public void setFtp_host(String ftp_host) {
		this.ftp_host = ftp_host;
	}

	public BigDecimal getFtp_port() {
		return ftp_port;
	}

	public void setFtp_port(BigDecimal ftp_port) {
		this.ftp_port = ftp_port;
	}

	public String getFtp_user() {
		return ftp_user;
	}

	public void setFtp_user(String ftp_user) {
		this.ftp_user = ftp_user;
	}

	public String getFtp_password() {
		return ftp_password;
	}

	public void setFtp_password(String ftp_password) {
		this.ftp_password = ftp_password;
	}

	public String getFtp_remote_dir() {
		return ftp_remote_dir;
	}

	public void setFtp_remote_dir(String ftp_remote_dir) {
		this.ftp_remote_dir = ftp_remote_dir;
	}
	
	
	
	
	
}



