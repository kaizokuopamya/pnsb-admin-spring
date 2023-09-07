package com.itl.pns.entity;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "KYC_DOCUMENT")
public class KycDocumentEntity {

	@javax.persistence.Id
	@Column(name = "ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
	@SequenceGenerator(name = "id_Sequence", sequenceName = "", allocationSize=1)
	private BigInteger id;
	
	@Column(name = "KYCFOLDERID")
	private BigInteger kycFolderId;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "UUID")
	private String uuid;
	
	@Column(name = "KYCFOLDERUUID")
	private String kycFolderUuid;
	
	@Column(name = "CREATEDON")
	private Date createdOn;

	@Column(name = "CREATEDBY")
	private BigInteger createdBy;

	@Column(name = "UPDATEDON")
	private Date updatedOn;

	@Column(name = "UPDATEDBY")
	private BigInteger updatedBy;

	@Column(name = "STATUSID")
	private BigInteger statusId;
	
	@Transient
	private String KycFolderName;

	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
		this.id = id;
	}
	public BigInteger getKycFolderId() {
		return kycFolderId;
	}
	public void setKycFolderId(BigInteger kycFolderId) {
		this.kycFolderId = kycFolderId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getKycFolderUuid() {
		return kycFolderUuid;
	}
	public void setKycFolderUuid(String kycFolderUuid) {
		this.kycFolderUuid = kycFolderUuid;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public BigInteger getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(BigInteger createdBy) {
		this.createdBy = createdBy;
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
	public BigInteger getStatusId() {
		return statusId;
	}
	public void setStatusId(BigInteger statusId) {
		this.statusId = statusId;
	}
	public String getKycFolderName() {
		return KycFolderName;
	}
	public void setKycFolderName(String kycFolderName) {
		KycFolderName = kycFolderName;
	}
	

}
