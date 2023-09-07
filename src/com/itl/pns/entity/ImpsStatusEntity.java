package com.itl.pns.entity;

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
@Table(name = "STATUS")
public class ImpsStatusEntity {

	@javax.persistence.Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
	@SequenceGenerator(name = "id_Sequence", sequenceName = "", allocationSize = 1)
	private String id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "STATE")
	private String state;

	@Column(name = "DETAIL")
	private String detail;

	@Column(name = "GROUP_NAME")
	private String groupName;

	@Column(name = "LAST_TICK")
	private Date lastTick;

	@Column(name = "TIMEOUT")
	private BigDecimal timeOut;

	@Column(name = "TIMEOUT_STATE")
	private String timeoutState;

	@Column(name = "COMMAND")
	private String command;

	@Column(name = "VALID_COMMANDS")
	private String validCommands;

	@Column(name = "EXPIRED")
	private char expired;

	@Column(name = "MAX_EVENTS")
	private BigDecimal maxEvents;

	@Column(name = "TAGS")
	private String tags;

	@Column(name = "ENABLE_EMAIL")
	private char enableEmail;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Date getLastTick() {
		return lastTick;
	}

	public void setLastTick(Date lastTick) {
		this.lastTick = lastTick;
	}

	public BigDecimal getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(BigDecimal timeOut) {
		this.timeOut = timeOut;
	}

	public String getTimeoutState() {
		return timeoutState;
	}

	public void setTimeoutState(String timeoutState) {
		this.timeoutState = timeoutState;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getValidCommands() {
		return validCommands;
	}

	public void setValidCommands(String validCommands) {
		this.validCommands = validCommands;
	}

	public char getExpired() {
		return expired;
	}

	public void setExpired(char expired) {
		this.expired = expired;
	}

	public BigDecimal getMaxEvents() {
		return maxEvents;
	}

	public void setMaxEvents(BigDecimal maxEvents) {
		this.maxEvents = maxEvents;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public char getEnableEmail() {
		return enableEmail;
	}

	public void setEnableEmail(char enableEmail) {
		this.enableEmail = enableEmail;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

}
