package com.itl.pns.corp.entity;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 
 * @author ashish.yadav
 *
 */
@Entity
@Table(name = "CORP_SUBMENU")
public class CorpSubMenuEntity {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "corp_submenu_Id_SEQ")
	@SequenceGenerator(name = "corp_submenu_Id_SEQ", sequenceName = "corp_submenu_Id_SEQ", allocationSize = 1)
	private Long id;

	@Column(name = "MENUNAME")
	private String subMenuName;

	@Column(name = "CREATEDON")
	private Timestamp createdOn;

	@Column(name = "UPDATEDON")
	private Timestamp updatedOn;

	@Column(name = "CREATEDBY")
	private Long createdBy;

	@Column(name = "UPDATEDBY")
	private Long updatedBy;

	@Column(name = "STATUSID")
	private Long status;

	@Column(name = "MENULOGO")
	private String subMenuLogo;

	@Column(name = "MENULINK")
	private String subMenuLink;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSubMenuName() {
		return subMenuName;
	}

	public void setSubMenuName(String subMenuName) {
		this.subMenuName = subMenuName;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getSubMenuLogo() {
		return subMenuLogo;
	}

	public void setSubMenuLogo(String subMenuLogo) {
		this.subMenuLogo = subMenuLogo;
	}

	public String getSubMenuLink() {
		return subMenuLink;
	}

	public void setSubMenuLink(String subMenuLink) {
		this.subMenuLink = subMenuLink;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public void setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
	}

}
