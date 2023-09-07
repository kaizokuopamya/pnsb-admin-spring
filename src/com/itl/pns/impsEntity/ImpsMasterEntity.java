package com.itl.pns.impsEntity;

import java.math.BigDecimal;
import java.sql.Clob;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "IMPS_MASTER")
public class ImpsMasterEntity {

	@javax.persistence.Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IMPS_MASTER_ID_SEQ")
	@SequenceGenerator(name = "IMPS_MASTER_ID_SEQ", sequenceName = "IMPS_MASTER_ID_SEQ", allocationSize = 1)
	private BigDecimal id;

	@Column(name = "BANK")
	private String bank;

	@Column(name = "IFSC")
	private String ifsc;

	@Column(name = "BRANCH")
	private String branch;

	@Column(name = "CENTER")
	private String center;

	@Column(name = "DISTRICT")
	private String district;

	@Column(name = "STATE")
	private String state;

	@Column(name = "ADDRESS")
	private String address;

	@Column(name = "CONTACT")
	private String contact;

	@Column(name = "IMPS")
	private char imps;

	@Column(name = "RTGS")
	private char rtgs;

	@Column(name = "CITY")
	private String city;

	@Column(name = "NEFT")
	private char neft;

	@Column(name = "MICR")
	private String micr;

	@Column(name = "UPI")
	private char upi;

	@Column(name = "CREATEDBY")
	private BigDecimal createdby;

	@Column(name = "CREATEDON")
	private Date createdon;

	@Column(name = "STATUSID")
	private BigDecimal statusId;

	@Column(name = "UPDATEDBY")
	private BigDecimal updatedby;

	@Column(name = "UPDATEDON")
	private Date updatedon;

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getIfsc() {
		return ifsc;
	}

	public void setIfsc(String ifsc) {
		this.ifsc = ifsc;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getCenter() {
		return center;
	}

	public void setCenter(String center) {
		this.center = center;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public char getImps() {
		return imps;
	}

	public void setImps(char imps) {
		this.imps = imps;
	}

	public char getRtgs() {
		return rtgs;
	}

	public void setRtgs(char rtgs) {
		this.rtgs = rtgs;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public char getNeft() {
		return neft;
	}

	public void setNeft(char neft) {
		this.neft = neft;
	}

	public String getMicr() {
		return micr;
	}

	public void setMicr(String micr) {
		this.micr = micr;
	}

	public char getUpi() {
		return upi;
	}

	public void setUpi(char upi) {
		this.upi = upi;
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

	public BigDecimal getStatusId() {
		return statusId;
	}

	public void setStatusId(BigDecimal statusId) {
		this.statusId = statusId;
	}

	public BigDecimal getUpdatedby() {
		return updatedby;
	}

	public void setUpdatedby(BigDecimal updatedby) {
		this.updatedby = updatedby;
	}

	public Date getUpdatedon() {
		return updatedon;
	}

	public void setUpdatedon(Date updatedon) {
		this.updatedon = updatedon;
	}

}
