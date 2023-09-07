package com.itl.pns.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "EASE5_QUICK_SUBMENU_ACCESS")
public class Ease5QuickSubMenuAccessEntity {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EASE5_QUICK_SUBMENU_ACCESS_SEQ")
	@SequenceGenerator(name = "EASE5_QUICK_SUBMENU_ACCESS_SEQ", sequenceName = "EASE5_QUICK_SUBMENU_ACCESS_SEQ", allocationSize = 1)
	private Integer id;

	@Column(name = "QUICK_MAIN_MENUID")
	private BigDecimal quickMainMenuId;

	@Column(name = "OPTIONNAME")
	private String optionName;

	@Column(name = "PAGENAME")
	private String pageName;

	@Column(name = "APPREDIRECTIONLINK")
	private String appRedirectionLink;

	@Column(name = "IMAGELINK")
	private String imageLink;

	@Column(name = "OPTIONTYPE")
	private String optionType;

	@Column(name = "REDIRECTIONTYPE")
	private String redirectionType;

	@Column(name = "REDIRECTIONURL")
	private String redirectionUrl;

	@Column(name = "STATUSID")
	private BigDecimal statusId;

	@Column(name = "SEQNUMBER")
	private BigDecimal seqNumber;

	@Column(name = "APPID")
	private BigDecimal appId;

	@Column(name = "CREATEDBY")
	private BigDecimal createdBy;

	@Column(name = "CREATEDON")
	private Date createdOn;

	@Transient
	private String isActiveAll;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigDecimal getQuickMainMenuId() {
		return quickMainMenuId;
	}

	public void setQuickMainMenuId(BigDecimal quickMainMenuId) {
		this.quickMainMenuId = quickMainMenuId;
	}

	public String getOptionName() {
		return optionName;
	}

	public void setOptionName(String optionName) {
		this.optionName = optionName;
	}

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public String getAppRedirectionLink() {
		return appRedirectionLink;
	}

	public void setAppRedirectionLink(String appRedirectionLink) {
		this.appRedirectionLink = appRedirectionLink;
	}

	public String getImageLink() {
		return imageLink;
	}

	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}

	public String getOptionType() {
		return optionType;
	}

	public void setOptionType(String optionType) {
		this.optionType = optionType;
	}

	public String getRedirectionType() {
		return redirectionType;
	}

	public void setRedirectionType(String redirectionType) {
		this.redirectionType = redirectionType;
	}

	public String getRedirectionUrl() {
		return redirectionUrl;
	}

	public void setRedirectionUrl(String redirectionUrl) {
		this.redirectionUrl = redirectionUrl;
	}

	public BigDecimal getStatusId() {
		return statusId;
	}

	public void setStatusId(BigDecimal statusId) {
		this.statusId = statusId;
	}

	public BigDecimal getSeqNumber() {
		return seqNumber;
	}

	public void setSeqNumber(BigDecimal seqNumber) {
		this.seqNumber = seqNumber;
	}

	public BigDecimal getAppId() {
		return appId;
	}

	public void setAppId(BigDecimal appId) {
		this.appId = appId;
	}

	public BigDecimal getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(BigDecimal createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getIsActiveAll() {
		return isActiveAll;
	}

	public void setIsActiveAll(String isActiveAll) {
		this.isActiveAll = isActiveAll;
	}

}
