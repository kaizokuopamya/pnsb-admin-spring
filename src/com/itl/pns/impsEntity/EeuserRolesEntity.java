package com.itl.pns.impsEntity;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="EEUSER_ROLES")
public class EeuserRolesEntity {

	@javax.persistence.Id
	@Column(name = "EEUSER")
	private BigInteger eeuser;
	
	@Column(name = "ROLE")
    private BigInteger role;
	
	@Transient
	private BigInteger roleId;
	
	@Transient
	private String name;

	public BigInteger getEeuser() {
		return eeuser;
	}

	public void setEeuser(BigInteger eeuser) {
		this.eeuser = eeuser;
	}

	public BigInteger getRole() {
		return role;
	}

	public void setRole(BigInteger role) {
		this.role = role;
	}

	public BigInteger getRoleId() {
		return roleId;
	}

	public void setRoleId(BigInteger roleId) {
		this.roleId = roleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
