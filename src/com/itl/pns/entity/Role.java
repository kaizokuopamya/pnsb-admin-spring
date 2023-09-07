package com.itl.pns.entity;

import java.math.BigDecimal;
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
@Table(name="ROLES")
public class Role {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "roles_id_SEQ")
	@SequenceGenerator(name = "roles_id_SEQ", sequenceName = "roles_id_SEQ", allocationSize=1)
    private BigDecimal id;  
	
	@Column(name="CODE")
    private String code;
	
	@Column(name="NAME")
    private String name;
	
	@Column(name="DESCRIPTION")
    private String description;
	
	@Column(name="CREATEDBY")
    private BigDecimal createdBy;
	
	@Column(name="CREATEDON")
    private Date createdOn;
	
	@Column(name="UPDATEBY")
    private String updatedBy;
	
	@Column(name="UPDATEON")
    private Date updatedOn;
	
	@Column(name="STATUSID")
    private BigDecimal statusId;
	
	@Column(name="ROLETYPE")
    private BigDecimal roleType;
	
	@Transient
	private String roleTypeName;
	
	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(BigDecimal createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}


	public BigDecimal getRoleType() {
		return roleType;
	}

	public void setRoleType(BigDecimal roleType) {
		this.roleType = roleType;
	}

	public String getRoleTypeName() {
		return roleTypeName;
	}

	public void setRoleTypeName(String roleTypeName) {
		this.roleTypeName = roleTypeName;
	}

	public BigDecimal getStatusId() {
		return statusId;
	}

	public void setStatusId(BigDecimal statusId) {
		this.statusId = statusId;
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", code=" + code + ", name=" + name + ", description=" + description + ", createdBy="
				+ createdBy + ", createdOn=" + createdOn + ", updatedBy=" + updatedBy + ", updatedOn=" + updatedOn
				+ ", statusId=" + statusId + ", roleType=" + roleType + ", roleTypeName=" + roleTypeName + ", getId()="
				+ getId() + ", getCode()=" + getCode() + ", getName()=" + getName() + ", getDescription()="
				+ getDescription() + ", getCreatedBy()=" + getCreatedBy() + ", getCreatedOn()=" + getCreatedOn()
				+ ", getUpdatedBy()=" + getUpdatedBy() + ", getUpdatedOn()=" + getUpdatedOn() + ", getRoleType()="
				+ getRoleType() + ", getRoleTypeName()=" + getRoleTypeName() + ", getStatusId()=" + getStatusId()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}

	
	

	
}
