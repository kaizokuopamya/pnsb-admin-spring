package com.itl.pns.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "THEMES_SUBMENU")
public class ThemesSubEntity {

	@javax.persistence.Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "THEMES_SUBMENU_ID_SEQ")
	@SequenceGenerator(name = "THEMES_SUBMENU_ID_SEQ", sequenceName = "THEMES_SUBMENU_ID_SEQ", allocationSize = 1)
	private BigDecimal id;
	
	@Column(name = "THEMES_ID")
	private BigDecimal themes_id;

	@Column(name = "THEMENAME")
	private String themename;

	@Column(name = "THEMES_SUBNAME")
	private String themes_subname;

	@Column(name = "TEXTCOLOR") 
	private String textcolor;

	@Column(name = "BARBACKGROUNDCOLOR")
	private String barbackgroundcolor;

	@Column(name = "THEMEMENUOPTION")
	private String thememenuoption;

	@Column(name = "STATUSID")
	private BigDecimal statusid;

	@Column(name = "CREATEDBY")
	private BigDecimal createdby;

	@Column(name = "CREATEDON")
	private Date createdon;

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

	public BigDecimal getThemes_id() {
		return themes_id;
	}

	public void setThemes_id(BigDecimal themes_id) {
		this.themes_id = themes_id;
	}

	public String getThemename() {
		return themename;
	}

	public void setThemename(String themename) {
		this.themename = themename;
	}

	public String getThemes_subname() {
		return themes_subname;
	}

	public void setThemes_subname(String themes_subname) {
		this.themes_subname = themes_subname;
	}

	public String getTextcolor() {
		return textcolor;
	}

	public void setTextcolor(String textcolor) {
		this.textcolor = textcolor;
	}

	public String getBarbackgroundcolor() {
		return barbackgroundcolor;
	}

	public void setBarbackgroundcolor(String barbackgroundcolor) {
		this.barbackgroundcolor = barbackgroundcolor;
	}

	public String getThememenuoption() {
		return thememenuoption;
	}

	public void setThememenuoption(String thememenuoption) {
		this.thememenuoption = thememenuoption;
	}

	public BigDecimal getStatusid() {
		return statusid;
	}

	public void setStatusid(BigDecimal statusid) {
		this.statusid = statusid;
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
