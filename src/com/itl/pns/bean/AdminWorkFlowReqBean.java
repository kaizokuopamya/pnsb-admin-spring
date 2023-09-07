package com.itl.pns.bean;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author shubham.lokhande
 *
 */
public class AdminWorkFlowReqBean {

	private BigInteger id;
	
	private BigDecimal activityRefNo;
	
	private BigDecimal user_ID;
	

	private BigDecimal role_ID;

	private BigDecimal subMenu_ID;

	private String remark;

	private String activityName;

	private BigInteger userAction;
	
	

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public BigDecimal getActivityRefNo() {
		return activityRefNo;
	}

	public void setActivityRefNo(BigDecimal activityRefNo) {
		this.activityRefNo = activityRefNo;
	}

	public BigDecimal getUser_ID() {
		return user_ID;
	}

	public void setUser_ID(BigDecimal user_ID) {
		this.user_ID = user_ID;
	}

	public BigDecimal getRole_ID() {
		return role_ID;
	}

	public void setRole_ID(BigDecimal role_ID) {
		this.role_ID = role_ID;
	}

	public BigDecimal getSubMenu_ID() {
		return subMenu_ID;
	}

	public void setSubMenu_ID(BigDecimal subMenu_ID) {
		this.subMenu_ID = subMenu_ID;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public BigInteger getUserAction() {
		return userAction;
	}

	public void setUserAction(BigInteger userAction) {
		this.userAction = userAction;
	}

}
