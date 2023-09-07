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

@Entity
@Table(name = "CORPUSER_ACC_REQ")
public class CorpUserAccReqEntity {

	@javax.persistence.Id
	@Column(name = "ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
	@SequenceGenerator(name = "id_Sequence", sequenceName = "corpuser_acc_req_id_SEQ", allocationSize=1)
	private BigInteger id;
	
	@Column(name = "Corpcompid")
	private BigInteger corpCompId;
	
	@Column(name = "Accountno")
	private String accountNo;
	
	@Column(name = "Userreqid")
	private BigInteger userReqId;
	
	@Column(name = "StatusId")
	BigInteger statusId;
	
	
	@Column(name = "Createdon")
	private Date createdon;

	@Column(name = "Updatedon")
	private Date updatedon;


	@Column(name = "UPDATEDBY")
	private BigInteger updatedby;
	
	@Column(name = "UserRRN")
	private String userRrn;


	public BigInteger getId() {
		return id;
	}


	public void setId(BigInteger id) {
		this.id = id;
	}


	public BigInteger getCorpCompId() {
		return corpCompId;
	}


	public void setCorpCompId(BigInteger corpCompId) {
		this.corpCompId = corpCompId;
	}


	public String getAccountNo() {
		return accountNo;
	}


	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}


	public BigInteger getUserReqId() {
		return userReqId;
	}


	public void setUserReqId(BigInteger userReqId) {
		this.userReqId = userReqId;
	}


	public BigInteger getStatusId() {
		return statusId;
	}


	public void setStatusId(BigInteger statusId) {
		this.statusId = statusId;
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


	public BigInteger getUpdatedby() {
		return updatedby;
	}


	public void setUpdatedby(BigInteger updatedby) {
		this.updatedby = updatedby;
	}


	public String getUserRrn() {
		return userRrn;
	}


	public void setUserRrn(String userRrn) {
		this.userRrn = userRrn;
	}

	
}
