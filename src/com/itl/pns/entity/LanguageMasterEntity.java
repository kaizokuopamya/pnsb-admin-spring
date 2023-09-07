package com.itl.pns.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "LANGUAGEMASTER")
public class LanguageMasterEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
	@SequenceGenerator(name = "id_Sequence", sequenceName = "language_master_ID_SEQ", allocationSize = 1)
	@Column(name = "ID")
	private BigDecimal id;

	@Column(name = "PREFEREDLANGUAGECODE")
	private String preferedLanguageCode;

	@Lob
	@Column(name = "CODEDETAILS")
	private String codeDetails;

	@Column(name = "STATUSID")
	private String statusId;

	@Column(name = "CREATEDON")
	private Timestamp createdOn;

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getPreferedLanguageCode() {
		return preferedLanguageCode;
	}

	public void setPreferedLanguageCode(String preferedLanguageCode) {
		this.preferedLanguageCode = preferedLanguageCode;
	}

	public String getCodeDetails() {
		return codeDetails;
	}

	public void setCodeDetails(String codeDetails) {
		this.codeDetails = codeDetails;
	}

	public String getStatusId() {
		return statusId;
	}

	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

}
