package com.itl.pns.bean;

import java.math.BigDecimal;
import java.util.List;

public class CorpLimitDataBean {

	BigDecimal transLimitId;

	BigDecimal transMasterId;

	BigDecimal minAmount;

	BigDecimal maxAmount;

	BigDecimal approverLevelId;

	BigDecimal approverMasterId;

	BigDecimal hierarchyMasterId;

	List<BigDecimal> corpUserId;

	List<CorpUserBean> corpUserData;

	String approverMasterName;

	String hierarchyMasterName;

	public BigDecimal getTransLimitId() {
		return transLimitId;
	}

	public void setTransLimitId(BigDecimal transLimitId) {
		this.transLimitId = transLimitId;
	}

	public BigDecimal getTransMasterId() {
		return transMasterId;
	}

	public void setTransMasterId(BigDecimal transMasterId) {
		this.transMasterId = transMasterId;
	}

	public BigDecimal getMinAmount() {
		return minAmount;
	}

	public void setMinAmount(BigDecimal minAmount) {
		this.minAmount = minAmount;
	}

	public BigDecimal getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(BigDecimal maxAmount) {
		this.maxAmount = maxAmount;
	}

	public BigDecimal getApproverLevelId() {
		return approverLevelId;
	}

	public void setApproverLevelId(BigDecimal approverLevelId) {
		this.approverLevelId = approverLevelId;
	}

	public BigDecimal getApproverMasterId() {
		return approverMasterId;
	}

	public void setApproverMasterId(BigDecimal approverMasterId) {
		this.approverMasterId = approverMasterId;
	}

	public BigDecimal getHierarchyMasterId() {
		return hierarchyMasterId;
	}

	public void setHierarchyMasterId(BigDecimal hierarchyMasterId) {
		this.hierarchyMasterId = hierarchyMasterId;
	}

	public List<BigDecimal> getCorpUserId() {
		return corpUserId;
	}

	public void setCorpUserId(List<BigDecimal> corpUserId) {
		this.corpUserId = corpUserId;
	}

	public List<CorpUserBean> getCorpUserData() {
		return corpUserData;
	}

	public void setCorpUserData(List<CorpUserBean> corpUserData) {
		this.corpUserData = corpUserData;
	}

	public String getApproverMasterName() {
		return approverMasterName;
	}

	public void setApproverMasterName(String approverMasterName) {
		this.approverMasterName = approverMasterName;
	}

	public String getHierarchyMasterName() {
		return hierarchyMasterName;
	}

	public void setHierarchyMasterName(String hierarchyMasterName) {
		this.hierarchyMasterName = hierarchyMasterName;
	}

}
