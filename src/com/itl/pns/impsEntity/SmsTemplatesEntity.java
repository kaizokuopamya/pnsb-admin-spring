package com.itl.pns.impsEntity;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="SMS_TEMPLATES")
public class SmsTemplatesEntity {

	@javax.persistence.Id
	@Column(name = "ID")
	private BigDecimal id;
	
    @Column(name = "NAME")
	private String name;
	
	@Column(name = "TEMPLATE")
    private String template;
    
    @Column(name = "TYPE")
    private String type;
    
    @Column(name = "TARGET")
    private String target;
    
    @Column(name = "TRAN_TYPE")
	private BigDecimal tran_type;
    
    
    @Column(name = "CDCI_STATUS")
    private String cdci_status;
    
    @Column(name = "NFS_STATUS")
    private String nfs_status;
    
    @Column(name = "NFS_RESPONSE_CODE")
    private String nfs_response_code;

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public BigDecimal getTran_type() {
		return tran_type;
	}

	public void setTran_type(BigDecimal tran_type) {
		this.tran_type = tran_type;
	}

	public String getCdci_status() {
		return cdci_status;
	}

	public void setCdci_status(String cdci_status) {
		this.cdci_status = cdci_status;
	}

	public String getNfs_status() {
		return nfs_status;
	}

	public void setNfs_status(String nfs_status) {
		this.nfs_status = nfs_status;
	}

	public String getNfs_response_code() {
		return nfs_response_code;
	}

	public void setNfs_response_code(String nfs_response_code) {
		this.nfs_response_code = nfs_response_code;
	}
  
    
	
}
