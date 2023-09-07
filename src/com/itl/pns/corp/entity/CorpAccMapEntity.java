package com.itl.pns.corp.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "CORP_ACC_MAP")
public class CorpAccMapEntity {

	public CorpAccMapEntity() {
		super();
	}

	public CorpAccMapEntity(CorpAccMapEntity corpAccMapEntity) {
		this.corpId = corpAccMapEntity.corpId;
		this.accountNo = corpAccMapEntity.accountNo;
		this.statusId = corpAccMapEntity.statusId;
		this.createdon = corpAccMapEntity.createdon;
		this.updatedby = corpAccMapEntity.updatedby;
	}

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
	@SequenceGenerator(name = "id_Sequence", sequenceName = "corp_acc_map_id_SEQ", allocationSize = 1)
	private BigDecimal id;

	@Column(name = "Corpid")
	private BigDecimal corpId;

	@Column(name = "Accountno")
	private String accountNo;

	@Column(name = "StatusId")
	BigDecimal statusId;

	@Column(name = "Createdon")
	private Date createdon;

	@Column(name = "Updatedon")
	private Date updatedon;

	@Column(name = "UPDATEDBY")
	private BigDecimal updatedby;

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public BigDecimal getCorpId() {
		return corpId;
	}

	public void setCorpId(BigDecimal corpId) {
		this.corpId = corpId;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
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

	@Override
	public String toString() {
		return "CorpAccMapEntity [id=" + id + ", corpId=" + corpId + ", accountNo=" + accountNo + ", statusId="
				+ statusId + ", createdon=" + createdon + ", updatedon=" + updatedon + ", updatedby=" + updatedby + "]";
	}

}
