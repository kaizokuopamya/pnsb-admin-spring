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
import javax.persistence.Transient;

@Entity
@Table(name="CORP_MENU_REQ_TMP")
public class CorpMenuReqEntityTmp {

	@javax.persistence.Id
	@Column(name = "ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
	@SequenceGenerator(name = "id_Sequence", sequenceName = "corp_menu_req_id_SEQ", allocationSize=1)
	private BigDecimal id;
	
	@Column(name = "Corpreqid")
	private BigDecimal corpReqId;
	
	@Column(name = "Menureqid")
	private BigDecimal menuReqId;
	
	@Column(name = "Statusid")
	BigDecimal statusId;
	
	
	@Column(name = "Createdon")
	private Date createdon;

	@Column(name = "Updatedon")
	private Date updatedon;


	@Column(name = "Updatedby")
	private BigDecimal updatedby;


	@Transient
	private String menuName;


	public BigDecimal getId() {
		return id;
	}


	public void setId(BigDecimal id) {
		this.id = id;
	}


	public BigDecimal getCorpReqId() {
		return corpReqId;
	}


	public void setCorpReqId(BigDecimal corpReqId) {
		this.corpReqId = corpReqId;
	}


	public BigDecimal getMenuReqId() {
		return menuReqId;
	}


	public void setMenuReqId(BigDecimal menuReqId) {
		this.menuReqId = menuReqId;
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


	public String getMenuName() {
		return menuName;
	}


	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}


	
	
}
