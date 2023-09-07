package com.itl.pns.bean;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.itl.pns.corp.entity.CorpCompDataMasterEntity;
import com.itl.pns.corp.entity.CorpCompanyMasterEntity;
import com.itl.pns.corp.entity.CorpUserEntity;

public class CorpDataBean {

	private CorpCompanyMasterEntity corpCompMasterData;
	private CorpCompDataMasterEntity corpCompData;
	private CorpMenuAccBeanTmp corpMenuAccData;
	private List<CorpUserEntity> corpUserMasterData;
	private CorpUserMemuAccMapBean corpUserMenuAccMapData;
	private List<CorpUserEntity> corpUserData;
	private BigDecimal corpCompId;
	private String reqStatus;
	private BigDecimal corpCompReqId;
	private String remark;
	@NotNull(message = "statusId is required")
	private BigDecimal statusId;
	private String userTypes;
	private String adminTypes;
	private String branchCode;
	private BigDecimal createdByUpdatedBy;

	public BigDecimal getCreatedByUpdatedBy() {
		return createdByUpdatedBy;
	}

	public void setCreatedByUpdatedBy(BigDecimal createdByUpdatedBy) {
		this.createdByUpdatedBy = createdByUpdatedBy;
	}

	public String getAdminTypes() {
		return adminTypes;
	}

	public void setAdminTypes(String adminTypes) {
		this.adminTypes = adminTypes;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getUserTypes() {
		return userTypes;
	}

	public void setUserTypes(String userTypes) {
		this.userTypes = userTypes;
	}

	public BigDecimal getStatusId() {
		return statusId;
	}

	public void setStatusId(BigDecimal statusId) {
		this.statusId = statusId;
	}

	public List<CorpUserEntity> getCorpUserData() {
		return corpUserData;
	}

	public void setCorpUserData(List<CorpUserEntity> corpUserData) {
		this.corpUserData = corpUserData;
	}

	public CorpCompanyMasterEntity getCorpCompMasterData() {
		return corpCompMasterData;
	}

	public void setCorpCompMasterData(CorpCompanyMasterEntity corpCompMasterData) {
		this.corpCompMasterData = corpCompMasterData;
	}

	public CorpCompDataMasterEntity getCorpCompData() {
		return corpCompData;
	}

	public void setCorpCompData(CorpCompDataMasterEntity corpCompData) {
		this.corpCompData = corpCompData;
	}

	public CorpMenuAccBeanTmp getCorpMenuAccData() {
		return corpMenuAccData;
	}

	public void setCorpMenuAccData(CorpMenuAccBeanTmp corpMenuAccData) {
		this.corpMenuAccData = corpMenuAccData;
	}

	public List<CorpUserEntity> getCorpUserMasterData() {
		return corpUserMasterData;
	}

	public void setCorpUserMasterData(List<CorpUserEntity> corpUserMasterData) {
		this.corpUserMasterData = corpUserMasterData;
	}

	public CorpUserMemuAccMapBean getCorpUserMenuAccMapData() {
		return corpUserMenuAccMapData;
	}

	public void setCorpUserMenuAccMapData(CorpUserMemuAccMapBean corpUserMenuAccMapData) {
		this.corpUserMenuAccMapData = corpUserMenuAccMapData;
	}

	public BigDecimal getCorpCompId() {
		return corpCompId;
	}

	public void setCorpCompId(BigDecimal corpCompId) {
		this.corpCompId = corpCompId;
	}

	public String getReqStatus() {
		return reqStatus;
	}

	public void setReqStatus(String reqStatus) {
		this.reqStatus = reqStatus;
	}

	public BigDecimal getCorpCompReqId() {
		return corpCompReqId;
	}

	public void setCorpCompReqId(BigDecimal corpCompReqId) {
		this.corpCompReqId = corpCompReqId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "CorpDataBean [corpCompMasterData=" + corpCompMasterData + ", corpCompData=" + corpCompData
				+ ", corpMenuAccData=" + corpMenuAccData + ", corpUserMasterData=" + corpUserMasterData
				+ ", corpUserMenuAccMapData=" + corpUserMenuAccMapData + ", corpUserData=" + corpUserData
				+ ", corpCompId=" + corpCompId + ", reqStatus=" + reqStatus + ", corpCompReqId=" + corpCompReqId
				+ ", remark=" + remark + ", statusId=" + statusId + ", userTypes=" + userTypes + ", adminTypes="
				+ adminTypes + ", branchCode=" + branchCode + ", createdByUpdatedBy=" + createdByUpdatedBy + "]";
	}

}
