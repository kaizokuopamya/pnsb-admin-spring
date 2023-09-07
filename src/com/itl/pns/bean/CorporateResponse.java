package com.itl.pns.bean;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class CorporateResponse {
	private String companyName;
	private BigDecimal corpId;
	private String companyCode;
	private Map<String, String> tempPassword;
	private Collection<BigDecimal> newUsers;
	private BigDecimal createdByUpdateBy;
	private boolean isPendingApprove;
	private boolean isApproved;
	private Map<BigDecimal, List> userIdMap;
	private BigDecimal oldCorpId;

	public Map<BigDecimal, List> getUserIdMap() {
		return userIdMap;
	}

	public void setUserIdMap(Map<BigDecimal, List> userIdMap) {
		this.userIdMap = userIdMap;
	}

	public BigDecimal getOldCorpId() {
		return oldCorpId;
	}

	public void setOldCorpId(BigDecimal oldCorpId) {
		this.oldCorpId = oldCorpId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public BigDecimal getCorpId() {
		return corpId;
	}

	public void setCorpId(BigDecimal corpId) {
		this.corpId = corpId;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public Map<String, String> getTempPassword() {
		return tempPassword;
	}

	public void setTempPassword(Map<String, String> tempPassword) {
		this.tempPassword = tempPassword;
	}

	public Collection<BigDecimal> getNewUsers() {
		return newUsers;
	}

	public void setNewUsers(Collection<BigDecimal> newUsers) {
		this.newUsers = newUsers;
	}

	public BigDecimal getCreatedByUpdateBy() {
		return createdByUpdateBy;
	}

	public void setCreatedByUpdateBy(BigDecimal createdByUpdateBy) {
		this.createdByUpdateBy = createdByUpdateBy;
	}

	public boolean isPendingApprove() {
		return isPendingApprove;
	}

	public void setPendingApprove(boolean isPendingApprove) {
		this.isPendingApprove = isPendingApprove;
	}

	public boolean isApproved() {
		return isApproved;
	}

	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}

}
