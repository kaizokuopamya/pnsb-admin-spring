package com.itl.pns.bean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class RequestParamBean {

	String id1;
	String id2;
	String id3;
	String id4;
	String id5;
	String id6;
	String roleTypeId;
	String deviceId;
	String branchCode;
	BigDecimal corpCompId;
	List<Integer> statusList;
	Date date1;
	Date date2;

	public List<Integer> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<Integer> statusList) {
		this.statusList = statusList;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public BigDecimal getCorpCompId() {
		return corpCompId;
	}

	public void setCorpCompId(BigDecimal corpCompId) {
		this.corpCompId = corpCompId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getId1() {
		return id1;
	}

	public void setId1(String id1) {
		this.id1 = id1;
	}

	public String getId2() {
		return id2;
	}

	public void setId2(String id2) {
		this.id2 = id2;
	}

	public String getId3() {
		return id3;
	}

	public void setId3(String id3) {
		this.id3 = id3;
	}

	public String getId4() {
		return id4;
	}

	public void setId4(String id4) {
		this.id4 = id4;
	}

	public String getId5() {
		return id5;
	}

	public void setId5(String id5) {
		this.id5 = id5;
	}

	public String getId6() {
		return id6;
	}

	public void setId6(String id6) {
		this.id6 = id6;
	}

	public String getRoleTypeId() {
		return roleTypeId;
	}

	public void setRoleTypeId(String roleTypeId) {
		this.roleTypeId = roleTypeId;
	}
	
	public Date getDate1() {
		return date1;
	}

	public void setDate1(Date date1) {
		this.date1 = date1;
	}

	public Date getDate2() {
		return date2;
	}

	public void setDate2(Date date2) {
		this.date2 = date2;
	}

	@Override
	public String toString() {
		return "RequestParamBean [id1=" + id1 + ", id2=" + id2 + ", id3=" + id3 + ", id4=" + id4 + ", id5=" + id5
				+ ", id6=" + id6 + ", roleTypeId=" + roleTypeId + ", deviceId=" + deviceId + ", branchCode="
				+ branchCode + ", corpCompId=" + corpCompId + ", statusList=" + statusList + ", date1=" + date1
				+ ", date2=" + date2 + "]";
	}

	
}
