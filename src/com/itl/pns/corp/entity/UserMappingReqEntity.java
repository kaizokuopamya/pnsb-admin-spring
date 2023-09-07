package com.itl.pns.corp.entity;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author shubham.lokhande
 *
 */
@Entity
@Table(name = "USER_MAPPING_REQ")
public class UserMappingReqEntity {

	@javax.persistence.Id
	@Column(name = "ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
	@SequenceGenerator(name = "id_Sequence", sequenceName = "user_mapping_req_id_SEQ", allocationSize=1)
	private BigInteger id;
	
	@Column(name = "COMPREQID")
	private BigInteger compReqId;
	
	@Column(name = "USERNAME")
	private String userName;  
	
	@Column(name = "DESIGNATION")
	private String designation;  
	
	@Column(name = "ACCOUNT")
	private String account; 
	
	@Column(name = "MAPPRDMENU")
	private String mappedMenu;  

	@Column(name = "RRN")
	private String rrn;  

	@Column(name = "STATUSID")
	private BigInteger statusId;

	@Column(name = "CREATEDON")
	private Date createdOn;

	@Column(name = "UPDATEDON")
	private Date updatedOn;

	@Column(name = "UPDATEDBY")
	private BigInteger updatedBy;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public BigInteger getCompReqId() {
		return compReqId;
	}

	public void setCompReqId(BigInteger compReqId) {
		this.compReqId = compReqId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getMappedMenu() {
		return mappedMenu;
	}

	public void setMappedMenu(String mappedMenu) {
		this.mappedMenu = mappedMenu;
	}

	public String getRrn() {
		return rrn;
	}

	public void setRrn(String rrn) {
		this.rrn = rrn;
	}

	public BigInteger getStatusId() {
		return statusId;
	}

	public void setStatusId(BigInteger statusId) {
		this.statusId = statusId;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public BigInteger getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(BigInteger updatedBy) {
		this.updatedBy = updatedBy;
	}
	
	
}
