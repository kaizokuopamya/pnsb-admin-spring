package com.itl.pns.entity;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "SUBMENU")
public class SubMenuEntity {

	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "PSBSUBMENU_ID_SEQ")
	@SequenceGenerator(name = "PSBSUBMENU_ID_SEQ", sequenceName = "PSBSUBMENU_ID_SEQ", allocationSize=1)
	int id;

	

	@Column(name = "MENU_NAME")
	String menuName;

	@Column(name = "MENU_DESC")
	String menudesc;

	@Column(name = "STATUSID")
	BigInteger statusId;

	@Column(name = "MENU_LINK")
	String menuLink;

	@Column(name = "MENU_LOGO")
	String menuLogo;

	@ManyToOne
	@JoinColumn(name = "MAIN_MENUID")
	MainMenu mainMenuid;
	
	@Column(name = "CREATEDBY")
	int createdby;

	@Transient
	private String menuId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenudesc() {
		return menudesc;
	}

	public void setMenudesc(String menudesc) {
		this.menudesc = menudesc;
	}

	public String getMenuLink() {
		return menuLink;
	}

	public void setMenuLink(String menuLink) {
		this.menuLink = menuLink;
	}

	public String getMenuLogo() {
		return menuLogo;
	}

	public void setMenuLogo(String menuLogo) {
		this.menuLogo = menuLogo;
	}

	public MainMenu getMainMenuid() {
		return mainMenuid;
	}

	public void setMainMenuid(MainMenu mainMenuid) {
		this.mainMenuid = mainMenuid;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public BigInteger getStatusId() {
		return statusId;
	}

	public void setStatusId(BigInteger statusId) {
		this.statusId = statusId;
	}

	public int getCreatedby() {
		return createdby;
	}

	public void setCreatedby(int createdby) {
		this.createdby = createdby;
	}
	
	

}
