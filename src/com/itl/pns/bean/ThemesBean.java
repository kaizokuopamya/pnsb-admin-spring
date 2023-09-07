package com.itl.pns.bean;

import java.math.BigDecimal;
import java.util.Date;

public class ThemesBean {

	  
	private BigDecimal ID;
	
	private String DESCRIPTION;
	
	private String DETAILS;
	
	private BigDecimal statusid;
	
	private BigDecimal createdby;
	
	private Date createdon;
	
	private BigDecimal updatedby;
	
	private Date updatedon;

	public BigDecimal getID() {
		return ID;
	}

	public void setID(BigDecimal iD) {
		ID = iD;
	}

	public String getDESCRIPTION() {
		return DESCRIPTION;
	}

	public void setDESCRIPTION(String dESCRIPTION) {
		DESCRIPTION = dESCRIPTION;
	}

	public String getDETAILS() {
		return DETAILS;
	}

	public void setDETAILS(String dETAILS) {
		DETAILS = dETAILS;
	}



	public BigDecimal getStatusid() {
		return statusid;
	}

	public void setStatusid(BigDecimal statusid) {
		this.statusid = statusid;
	}

	public BigDecimal getCreatedby() {
		return createdby;
	}

	public void setCreatedby(BigDecimal createdby) {
		this.createdby = createdby;
	}

	public Date getCreatedon() {
		return createdon;
	}

	public void setCreatedon(Date createdon) {
		this.createdon = createdon;
	}

	public BigDecimal getUpdatedby() {
		return updatedby;
	}

	public void setUpdatedby(BigDecimal updatedby) {
		this.updatedby = updatedby;
	}

	public Date getUpdatedon() {
		return updatedon;
	}

	public void setUpdatedon(Date updatedon) {
		this.updatedon = updatedon;
	}

	
	
	
}
