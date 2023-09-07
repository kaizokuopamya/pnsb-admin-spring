package com.itl.pns.impsEntity;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

@Entity
@Table(name="REPORT_CATEGORY")
public class ReportCatrgoryEntity {

	@javax.persistence.Id
	@Column(name = "ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private BigInteger id;
	
	@Column(name = "TITLE")
    private String title;
	

	@Column(name = "show_as_submenu")
    private char show_as_submenu;
	

	@Column(name = "system_category")
    private char system_category;


	public BigInteger getId() {
		return id;
	}


	public void setId(BigInteger id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public char getShow_as_submenu() {
		return show_as_submenu;
	}


	public void setShow_as_submenu(char show_as_submenu) {
		this.show_as_submenu = show_as_submenu;
	}


	public char getSystem_category() {
		return system_category;
	}


	public void setSystem_category(char system_category) {
		this.system_category = system_category;
	}


	
}
