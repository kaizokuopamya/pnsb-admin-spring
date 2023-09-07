//package com.itl.pns.bean;
//
//import java.math.BigDecimal;
//import java.util.List;
//
//import javax.validation.constraints.NotNull;
//
//import com.itl.pns.corp.entity.CorpCompanyMasterEntityTmp;
//import com.itl.pns.corp.entity.CorpUserEntityTmp;
//
//public class CorpDataBeanTmp {
//
//	private CorpCompanyMasterEntityTmp corpCompMasterData;
//	private CorpCompanyMasterEntityTmp corpCompData;
//	private CorpMenuAccBeanTmp corpMenuAccData;
//	private List<CorpUserEntityTmp> corpUserMasterData;
//	private CorpUserMemuAccMapBeanTmp corpUserMenuAccMapData;
//	private List<CorpUserEntityTmp> corpUserData;
//	private BigDecimal corpCompId;
//	private String reqStatus;
//	private BigDecimal corpCompReqId;
//	private String remark;
//	@NotNull(message = "statusId is required")
//	private BigDecimal statusId;
//	private String userTypes;
//	private String adminTypes;
//	private String branchCode;
//	private BigDecimal createdByUpdatedBy;
//
//	public BigDecimal getCreatedByUpdatedBy() {
//		return createdByUpdatedBy;
//	}
//
//	public void setCreatedByUpdatedBy(BigDecimal createdByUpdatedBy) {
//		this.createdByUpdatedBy = createdByUpdatedBy;
//	}
//
//	public String getAdminTypes() {
//		return adminTypes;
//	}
//
//	public void setAdminTypes(String adminTypes) {
//		this.adminTypes = adminTypes;
//	}
//
//	public String getBranchCode() {
//		return branchCode;
//	}
//
//	public void setBranchCode(String branchCode) {
//		this.branchCode = branchCode;
//	}
//
//	public String getUserTypes() {
//		return userTypes;
//	}
//
//	public void setUserTypes(String userTypes) {
//		this.userTypes = userTypes;
//	}
//
//	public BigDecimal getStatusId() {
//		return statusId;
//	}
//
//	public void setStatusId(BigDecimal statusId) {
//		this.statusId = statusId;
//	}
//
//	public List<CorpUserEntityTmp> getCorpUserData() {
//		return corpUserData;
//	}
//
//	public void setCorpUserData(List<CorpUserEntityTmp> corpUserData) {
//		this.corpUserData = corpUserData;
//	}
//
//	public CorpCompanyMasterEntityTmp getCorpCompMasterData() {
//		return corpCompMasterData;
//	}
//
//	public void setCorpCompMasterData(CorpCompanyMasterEntityTmp corpCompMasterData) {
//		this.corpCompMasterData = corpCompMasterData;
//	}
//
//	public CorpCompanyMasterEntityTmp getCorpCompData() {
//		return corpCompData;
//	}
//
//	public void setCorpCompData(CorpCompanyMasterEntityTmp corpCompData) {
//		this.corpCompData = corpCompData;
//	}
//
//	public CorpMenuAccBeanTmp getCorpMenuAccData() {
//		return corpMenuAccData;
//	}
//
//	public void setCorpMenuAccData(CorpMenuAccBeanTmp corpMenuAccData) {
//		this.corpMenuAccData = corpMenuAccData;
//	}
//
//	public List<CorpUserEntityTmp> getCorpUserMasterData() {
//		return corpUserMasterData;
//	}
//
//	public void setCorpUserMasterData(List<CorpUserEntityTmp> corpUserMasterData) {
//		this.corpUserMasterData = corpUserMasterData;
//	}
//
//	public CorpUserMemuAccMapBeanTmp getCorpUserMenuAccMapData() {
//		return corpUserMenuAccMapData;
//	}
//
//	public void setCorpUserMenuAccMapData(CorpUserMemuAccMapBeanTmp corpUserMenuAccMapData) {
//		this.corpUserMenuAccMapData = corpUserMenuAccMapData;
//	}
//
//	public BigDecimal getCorpCompId() {
//		return corpCompId;
//	}
//
//	public void setCorpCompId(BigDecimal corpCompId) {
//		this.corpCompId = corpCompId;
//	}
//
//	public String getReqStatus() {
//		return reqStatus;
//	}
//
//	public void setReqStatus(String reqStatus) {
//		this.reqStatus = reqStatus;
//	}
//
//	public BigDecimal getCorpCompReqId() {
//		return corpCompReqId;
//	}
//
//	public void setCorpCompReqId(BigDecimal corpCompReqId) {
//		this.corpCompReqId = corpCompReqId;
//	}
//
//	public String getRemark() {
//		return remark;
//	}
//
//	public void setRemark(String remark) {
//		this.remark = remark;
//	}
//
//	@Override
//	public String toString() {
//		return "CorpDataBean [corpCompMasterData=" + corpCompMasterData + ", corpCompData=" + corpCompData
//				+ ", corpUserMenuAccMapData=" + corpUserMenuAccMapData + ", corpUserData=" + corpUserData
//				+ ", corpCompId=" + corpCompId + ", reqStatus=" + reqStatus + ", corpCompReqId=" + corpCompReqId
//				+ ", remark=" + remark + "]";
//	}
//
//}
