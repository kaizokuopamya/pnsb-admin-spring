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

/**
 * @author shubham.lokhande
 *
 */
@Entity
@Table(name = "OFFERSDETAILS_PRD")
public class OfferDetailsEntity {

	@javax.persistence.Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "offersdetails_prd_id_SEQ")
	@SequenceGenerator(name = "offersdetails_prd_id_SEQ", sequenceName = "offersdetails_prd_id_SEQ", allocationSize = 1)
	private BigDecimal id;

	@Column(name = "BASE64IMAGESMALL")
	private String base64ImageSmall;

	@Column(name = "BASE64IMAGELARGE")
	private String base64ImageLarge;

	@Column(name = "IMAGEDESCRIPTIONLARGE")
	private String imgdescLarge;

	@Column(name = "IMAGEDESCRIPTIONSMALL")
	private String imgdescSmall;

	@Column(name = "CREATEDBY")
	private BigDecimal createdby;

	@Column(name = "CREATEDON")
	private Date createdon;

	@Column(name = "UPDATEDBY")
	private BigDecimal updatedby;

	@Column(name = "UPDATEDON")
	private Date updatedon;

	@Column(name = "STATUSID")
	private BigDecimal statusId;

	@Column(name = "IMAGECAPTION")
	private String imgcaption;

	@Column(name = "APPID")
	private BigDecimal appId;

	@Column(name = "SEQNUMBER")
	private BigDecimal seqNumber;

	@Column(name = "VALIDFROM")
	private Date validFrom;

	@Column(name = "VALIDTO")
	private Date validTo;

	@Column(name = "LATITUDE")
	private String latitude;

	@Column(name = "LONGITUDE")
	private String longitude;

	@Column(name = "WEBLINK")
	private String weblink;

	@Column(name = "SERVICETYPE")
	private String serviceType;

	@Transient
	private String baseImageLarge;

	@Transient
	private String baseImageSmall;

	@Transient
	private String statusName;

	@Transient
	private String productName;

	@Transient
	private String createdByName;

	@Transient
	private BigDecimal user_ID;

	@Transient
	private BigDecimal role_ID;

	@Transient
	private BigDecimal subMenu_ID;

	@Transient
	private String remark;

	@Transient
	private String activityName;

	@Transient
	private BigDecimal userAction;

	@Transient
	private String roleName;

	@Transient
	private String action;

	public String getBaseImageLarge() {
		return baseImageLarge;
	}

	public void setBaseImageLarge(String baseImageLarge) {
		this.baseImageLarge = baseImageLarge;
	}

	public String getBaseImageSmall() {
		return baseImageSmall;
	}

	public void setBaseImageSmall(String baseImageSmall) {
		this.baseImageSmall = baseImageSmall;
	}

	public String getLatitude() {
		return latitude;
	}

	public String getWeblink() {
		return weblink;
	}

	public void setWeblink(String weblink) {
		this.weblink = weblink;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public Date getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}

	public Date getValidTo() {
		return validTo;
	}

	public void setValidTo(Date validTo) {
		this.validTo = validTo;
	}

	public String getBase64ImageSmall() {
		return base64ImageSmall;
	}

	public void setBase64ImageSmall(String base64ImageSmall) {
		this.base64ImageSmall = base64ImageSmall;
	}

	public String getBase64ImageLarge() {
		return base64ImageLarge;
	}

	public void setBase64ImageLarge(String base64ImageLarge) {
		this.base64ImageLarge = base64ImageLarge;
	}

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getImgdescLarge() {
		return imgdescLarge;
	}

	public void setImgdescLarge(String imgdescLarge) {
		this.imgdescLarge = imgdescLarge;
	}

	public String getImgdescSmall() {
		return imgdescSmall;
	}

	public void setImgdescSmall(String imgdescSmall) {
		this.imgdescSmall = imgdescSmall;
	}

	public BigDecimal getCreatedby() {
		return createdby;
	}

	public void setCreatedby(BigDecimal createdby) {
		this.createdby = createdby;
	}

	public BigDecimal getUpdatedby() {
		return updatedby;
	}

	public void setUpdatedby(BigDecimal updatedby) {
		this.updatedby = updatedby;
	}

	public BigDecimal getStatusId() {
		return statusId;
	}

	public void setStatusId(BigDecimal statusId) {
		this.statusId = statusId;
	}

	public BigDecimal getAppId() {
		return appId;
	}

	public void setAppId(BigDecimal appId) {
		this.appId = appId;
	}

	public BigDecimal getSeqNumber() {
		return seqNumber;
	}

	public void setSeqNumber(BigDecimal seqNumber) {
		this.seqNumber = seqNumber;
	}

	public Date getCreatedon() {
		return createdon;
	}

	public void setCreatedon(Date createdon) {
		this.createdon = createdon;
	}

	public Date getUpdatedon() {
		return updatedon;
	}

	public void setUpdatedon(Date updatedon) {
		this.updatedon = updatedon;
	}

	public String getImgcaption() {
		return imgcaption;
	}

	public void setImgcaption(String imgcaption) {
		this.imgcaption = imgcaption;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public BigDecimal getUserAction() {
		return userAction;
	}

	public void setUserAction(BigDecimal userAction) {
		this.userAction = userAction;
	}

	public BigDecimal getUser_ID() {
		return user_ID;
	}

	public void setUser_ID(BigDecimal user_ID) {
		this.user_ID = user_ID;
	}

	public BigDecimal getRole_ID() {
		return role_ID;
	}

	public void setRole_ID(BigDecimal role_ID) {
		this.role_ID = role_ID;
	}

	public BigDecimal getSubMenu_ID() {
		return subMenu_ID;
	}

	public void setSubMenu_ID(BigDecimal subMenu_ID) {
		this.subMenu_ID = subMenu_ID;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

}
