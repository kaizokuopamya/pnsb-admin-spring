package com.itl.pns.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "CUSTOMIZE_SUBMENU")
public class CustomizeSubmenuEntity {

	@javax.persistence.Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
	@SequenceGenerator(name = "id_Sequence", sequenceName = "", allocationSize = 1)
	int id;

	@Column(name = "MENU_NAME")
	String menuName;

	@Column(name = "MENU_DESC")
	String menudesc;

	@Column(name = "IS_ACTIVE")
	int isActive;

	@Column(name = "MENU_LINK")
	String menuLink;

	@Column(name = "MENU_LOGO")
	String menuLogo;

	@ManyToOne
	@JoinColumn(name = "CUSTOMIZE_MENUID")
	CustomizeMenus customizeMenuid;

	@Column(name = "JSON_KEY")
	String json_key;

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

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
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

	public CustomizeMenus getCustomizeMenuid() {
		return customizeMenuid;
	}

	public void setCustomizeMenuid(CustomizeMenus customizeMenuid) {
		this.customizeMenuid = customizeMenuid;
	}

	public String getJson_key() {
		return json_key;
	}

	public void setJson_key(String json_key) {
		this.json_key = json_key;
	}

}
