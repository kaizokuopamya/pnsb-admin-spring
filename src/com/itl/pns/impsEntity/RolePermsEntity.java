package com.itl.pns.impsEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="ROLE_PERMS")
public class RolePermsEntity {


	@javax.persistence.Id
	@Column(name = "ROLE")
    private String role;
	
	@Column(name = "NAME")
    private String name;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

}
