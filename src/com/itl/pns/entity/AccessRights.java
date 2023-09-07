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
@Table(name = "LINK_ROLES")
public class AccessRights {
	
	@javax.persistence.Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
	@SequenceGenerator(name = "id_Sequence", sequenceName = "link_roles_id_SEQ", allocationSize=1)
	private BigDecimal id;
	
	@Column(name = "MENU_ID")
    private BigDecimal menuId;
	
	@Column(name = "ROLE_ID")
    private BigDecimal roleId;
	
	@Column(name = "PRIVILEGE_ID")
    private BigDecimal privilegeId;
	
	@Column(name = "CREATED_BY")
    private BigDecimal createdBy;
	
	@Column(name = "UPDATED_BY")
    private BigDecimal updatedby;
	
	@Column(name = "UPDATED_ON")
    private Date updatedOn;
	
	@Column(name = "CREATED_ON")
    private Date createdOn;
	
	@Column(name = "STATUSID")
	private BigDecimal statusId;


	
	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	
	public BigDecimal getMenuId() {
		return menuId;
	}

	public void setMenuId(BigDecimal menuId) {
		this.menuId = menuId;
	}

	public BigDecimal getRoleId() {
		return roleId;
	}

	public void setRoleId(BigDecimal roleId) {
		this.roleId = roleId;
	}

	public BigDecimal getPrivilegeId() {
		return privilegeId;
	}

	public void setPrivilegeId(BigDecimal privilegeId) {
		this.privilegeId = privilegeId;
	}


	public BigDecimal getUpdatedby() {
		return updatedby;
	}

	public void setUpdatedby(BigDecimal updatedby) {
		this.updatedby = updatedby;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public BigDecimal getStatusId() {
		return statusId;
	}

	public void setStatusId(BigDecimal statusId) {
		this.statusId = statusId;
	}

	public BigDecimal getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(BigDecimal createdBy) {
		this.createdBy = createdBy;
	}

	
	
	

}
