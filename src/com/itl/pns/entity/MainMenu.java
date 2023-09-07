package com.itl.pns.entity;

import java.math.BigDecimal;
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
@Table(name="MAIN_MENU")
public class MainMenu {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "main_menu_id_SEQ")
	@SequenceGenerator(name = "main_menu_id_SEQ", sequenceName = "main_menu_id_SEQ", allocationSize = 1)
	int id;
	
	@Column(name = "MENUNAME")
	String menuname;
	
	@Column(name = "STATUSID")
	int statusId;
	
	@Column(name = "CREATEDON")
	Date createdon;
	
	@Column(name = "UPDATEDON")
	Date updatedon;
	
	@Column(name = "CREATEDBY")
	int createdby;
	
	@Column(name = "UPDATEDBY")
	int updatedby;
	
	@Column(name = "MENULOGO")
	String menuLogo;
		
	@Transient
	private BigDecimal user_ID;
	
	@Transient
	private BigDecimal role_ID;
	
	@Transient
	private BigDecimal subMenu_ID;
	
	@Transient
	private String remark;
	
	@Transient
	private String activityName;
	
	@Transient
	private BigInteger userAction;
	
	@Transient
	private String statusName;
	
	@Transient  
	private String roleName;
	
	@Transient  
	private String createdByName;
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getMenuname() {
		return menuname;
	}

	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}



	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
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

	public String getMenuLogo() {
		return menuLogo;
	}

	public void setMenuLogo(String menuLogo) {
		this.menuLogo = menuLogo;
	}


	public BigDecimal getUser_ID() {
		return user_ID;
	}

	public void setUser_ID(BigDecimal user_ID) {
		this.user_ID = user_ID;
	}

	public BigDecimal getRole_ID() {
		return role_ID;
	}

	public void setRole_ID(BigDecimal role_ID) {
		this.role_ID = role_ID;
	}

	public BigDecimal getSubMenu_ID() {
		return subMenu_ID;
	}

	public void setSubMenu_ID(BigDecimal subMenu_ID) {
		this.subMenu_ID = subMenu_ID;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public BigInteger getUserAction() {
		return userAction;
	}

	public void setUserAction(BigInteger userAction) {
		this.userAction = userAction;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	
	
}
