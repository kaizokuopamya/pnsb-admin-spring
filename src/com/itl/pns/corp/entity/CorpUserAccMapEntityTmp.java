package com.itl.pns.corp.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "CORPUSER_ACC_MAP_TMP")
public class CorpUserAccMapEntityTmp {

	@javax.persistence.Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
	@SequenceGenerator(name = "id_Sequence", sequenceName = "corpuser_acc_map_id_SEQ", allocationSize = 1)
	private BigDecimal id;

	@Column(name = "Corpcompid")
	private BigDecimal corpCompId;

	@Column(name = "Accountno")
	private String accountNo;

	@Column(name = "Corpuserid")
	private BigDecimal corpUserId;

	@Column(name = "StatusId")
	BigDecimal statusId;

	@Column(name = "Createdon")
	private Date createdon;

	@Column(name = "Updatedon")
	private Date updatedon;

	@Column(name = "UPDATEDBY")
	private BigDecimal updatedby;

	@Column(name = "UserRRN")
	private String userRrn;

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public BigDecimal getCorpCompId() {
		return corpCompId;
	}

	public void setCorpCompId(BigDecimal corpCompId) {
		this.corpCompId = corpCompId;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public BigDecimal getCorpUserId() {
		return corpUserId;
	}

	public void setCorpUserId(BigDecimal corpUserId) {
		this.corpUserId = corpUserId;
	}

	public BigDecimal getStatusId() {
		return statusId;
	}

	public void setStatusId(BigDecimal statusId) {
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

	public BigDecimal getUpdatedby() {
		return updatedby;
	}

	public void setUpdatedby(BigDecimal updatedby) {
		this.updatedby = updatedby;
	}

	public String getUserRrn() {
		return userRrn;
	}

	public void setUserRrn(String userRrn) {
		this.userRrn = userRrn;
	}

}
