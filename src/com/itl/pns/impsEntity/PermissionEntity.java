package com.itl.pns.impsEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="PERMISSION")
public class PermissionEntity {

	
	@javax.persistence.Id
	@Column(name = "NAME")
    private String name;
	
	@Column(name = "VALUE")
    private String value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
}
