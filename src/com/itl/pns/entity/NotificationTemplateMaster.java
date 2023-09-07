package com.itl.pns.entity;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;



@Entity
@Table(name="NOTIFICATIONTEMPLATEMASTER")
public class NotificationTemplateMaster {

	@javax.persistence.Id
	@Column(name = "ID") 
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "notificationtemplatemaster_SEQ")
	@SequenceGenerator(name = "notificationtemplatemaster_SEQ", sequenceName = "notificationtemplatemaster_SEQ", allocationSize=1)
	private BigDecimal id;
	
	@Column(name = "APPID")
	BigDecimal appId;

	@Column(name = "NOTIFICATIONID")
	BigDecimal notificationId;

	@Column(name = "SHORTNAME")
	String shortName;
  
	@Column(name = "CONTENTS")
	String contents;
    
	@Column(name = "TARGETTITLE1")
	String targetTitle1;
	
	@Column(name = "TARGETACTION1")
	String targetAction1;
	
	@Column(name = "TARGETTITLE2")
	String targetTitle2;
	
	@Column(name = "TARGETACTION2")
	String targetAction2;
	
	@Column(name = "TARGETTITLE3")
	String targetTitle3;
	
	@Column(name = "TARGETACTION3")
	String targetAction3;
	
	@Column(name = "TARGETTITLE4")
	String targetTitle4;
	
	@Column(name = "TARGETACTION4")
	String targetAction4;
	
	@Column(name = "LANG_CODE")
	public String languagecode;
	
    @Column(name = "STATUSID")
    private BigDecimal statusId;
	
    @Column(name = "CREATEDBY")
    private BigDecimal createdby;
    
    @Column(name = "CREATEDON")
    private Date createdon;
    
    @Transient
    private String statusname;

    @Transient
	BigDecimal channelId;
    
	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}


	public BigDecimal getAppId() {
		return appId;
	}

	public void setAppId(BigDecimal appId) {
		this.appId = appId;
	}

	public BigDecimal getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(BigDecimal notificationId) {
		this.notificationId = notificationId;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getTargetTitle1() {
		return targetTitle1;
	}

	public void setTargetTitle1(String targetTitle1) {
		this.targetTitle1 = targetTitle1;
	}

	public String getTargetAction1() {
		return targetAction1;
	}

	public void setTargetAction1(String targetAction1) {
		this.targetAction1 = targetAction1;
	}

	public String getTargetTitle2() {
		return targetTitle2;
	}

	public void setTargetTitle2(String targetTitle2) {
		this.targetTitle2 = targetTitle2;
	}

	public String getTargetAction2() {
		return targetAction2;
	}

	public void setTargetAction2(String targetAction2) {
		this.targetAction2 = targetAction2;
	}

	public String getTargetTitle3() {
		return targetTitle3;
	}

	public void setTargetTitle3(String targetTitle3) {
		this.targetTitle3 = targetTitle3;
	}

	public String getTargetAction3() {
		return targetAction3;
	}

	public void setTargetAction3(String targetAction3) {
		this.targetAction3 = targetAction3;
	}

	public String getTargetTitle4() {
		return targetTitle4;
	}

	public void setTargetTitle4(String targetTitle4) {
		this.targetTitle4 = targetTitle4;
	}

	public String getTargetAction4() {
		return targetAction4;
	}

	public void setTargetAction4(String targetAction4) {
		this.targetAction4 = targetAction4;
	}

	public String getLanguagecode() {
		return languagecode;
	}

	public void setLanguagecode(String languagecode) {
		this.languagecode = languagecode;
	}

	public BigDecimal getStatusId() {
		return statusId;
	}

	public void setStatusId(BigDecimal statusId) {
		this.statusId = statusId;
	}

	public BigDecimal getCreatedby() {
		return createdby;
	}

	public void setCreatedby(BigDecimal createdby) {
		this.createdby = createdby;
	}

	public Date getCreatedon() {
		return createdon;
	}

	public void setCreatedon(Date createdon) {
		this.createdon = createdon;
	}

	public String getStatusname() {
		return statusname;
	}

	public void setStatusname(String statusname) {
		this.statusname = statusname;
	}

	public BigDecimal getChannelId() {
		return channelId;
	}

	public void setChannelId(BigDecimal channelId) {
		this.channelId = channelId;
	}
    

}
