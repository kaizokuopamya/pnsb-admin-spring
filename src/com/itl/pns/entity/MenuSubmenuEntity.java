package com.itl.pns.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name="MENU_SUBMENU")
public class MenuSubmenuEntity {

	
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "menu_submenu_id_SEQ")
	@SequenceGenerator(name = "menu_submenu_id_SEQ", sequenceName = "menu_submenu_id_SEQ", allocationSize=1)
	int id;
	
	@ManyToOne
	@JoinColumn(name = "MENUID")
	MainMenu menuId;
	

	@ManyToOne
	@JoinColumn(name = "SUBMENUID")
	SubMenuEntity submenuId;
	
	
	
	@Column(name = "ROLEID")
	int roleid;
	
	
	@ManyToOne
	@JoinColumn(name = "STATUSID")
	StatusMasterEntity statusId;
	
	@Column(name = "CREATEDON")
	Date createdon;
	
	@Column(name = "UPDATEDON")
	Date updatedon;
	
	@Column(name = "CREATEDBY")
	int createdby;
	
	@Column(name = "UPDATEDBY")
	int updatedby;
	


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public MainMenu getMenuId() {
		return menuId;
	}

	public void setMenuId(MainMenu menuId) {
		this.menuId = menuId;
	}

	public SubMenuEntity getSubmenuId() {
		return submenuId;
	}

	public void setSubmenuId(SubMenuEntity submenuId) {
		this.submenuId = submenuId;
	}

	public StatusMasterEntity getStatusId() {
		return statusId;
	}

	public void setStatusId(StatusMasterEntity statusId) {
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

	public int getCreatedby() {
		return createdby;
	}

	public void setCreatedby(int createdby) {
		this.createdby = createdby;
	}

	public int getUpdatedby() {
		return updatedby;
	}

	public void setUpdatedby(int updatedby) {
		this.updatedby = updatedby;
	}

	public int getRoleid() {
		return roleid;
	}

	public void setRoleid(int roleid) {
		this.roleid = roleid;
	}

	@Override
	public String toString() {
		return "MenuSubmenuEntity [id=" + id + ", menuId=" + menuId
				+ ", submenuId=" + submenuId + ", roleid=" + roleid
				+ ", statusId=" + statusId + ", createdon=" + createdon
				+ ", updatedon=" + updatedon + ", createdby=" + createdby
				+ ", updatedby=" + updatedby + "]";
	}



	
	
}
