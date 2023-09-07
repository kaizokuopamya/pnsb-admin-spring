package com.itl.pns.bean;

import java.math.BigDecimal;
import java.util.Date;

public class CorpMenuAccountBean {

	private BigDecimal id;

	private BigDecimal corpReqId;

	private BigDecimal menuReqId;

	private String accountNo;

	BigDecimal statusId;

	private Date createdon;

	private Date updatedon;

	private BigDecimal updatedby;

	private String menuName;

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public BigDecimal getCorpReqId() {
		return corpReqId;
	}

	public void setCorpReqId(BigDecimal corpReqId) {
		this.corpReqId = corpReqId;
	}

	public BigDecimal getMenuReqId() {
		return menuReqId;
	}

	public void setMenuReqId(BigDecimal menuReqId) {
		this.menuReqId = menuReqId;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public BigDecimal getStatusId() {
		return statusId;
	}

	public void setStatusId(BigDecimal statusId) {
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

	public BigDecimal getUpdatedby() {
		return updatedby;
	}

	public void setUpdatedby(BigDecimal updatedby) {
		this.updatedby = updatedby;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

}
