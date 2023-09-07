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
import javax.persistence.Transient;

@Entity
@Table(name = "CORPUSER_MENU_REQ")
public class CorpUserMenuReqEntity {

	@javax.persistence.Id
	@Column(name = "ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
	@SequenceGenerator(name = "id_Sequence", sequenceName = "corpuser_menu_req_id_SEQ", allocationSize=1)
	private BigInteger id;
	
	@Column(name = "Corpcompid")
	private BigInteger corpCompId;
	
	@Column(name = "Menureqid")
	private BigInteger menuReqId;
	
	@Column(name = "USERREQID")
	private BigInteger userReqId;
	
	@Column(name = "Statusid")
	BigInteger statusId;
	
	
	@Column(name = "Createdon")
	private Date createdon;

	@Column(name = "Updatedon")
	private Date updatedon;


	@Column(name = "Updatedby")
	private BigInteger updatedby;

	@Column(name = "UserRRN")
	private String userRrn;
	

	@Transient
	private String menuName;


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


	public BigInteger getMenuReqId() {
		return menuReqId;
	}


	public void setMenuReqId(BigInteger menuReqId) {
		this.menuReqId = menuReqId;
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


	public String getMenuName() {
		return menuName;
	}


	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}


	public String getUserRrn() {
		return userRrn;
	}


	public void setUserRrn(String userRrn) {
		this.userRrn = userRrn;
	}


}
