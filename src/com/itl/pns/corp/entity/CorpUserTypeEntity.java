
package com.itl.pns.corp.entity;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author shubham.lokhande
 *
 */
@Entity
@Table(name = "CORP_USER_TYPES")
public class CorpUserTypeEntity {

	@javax.persistence.Id
	@Column(name = "ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "corp_user_types_ID_SEQ")
	@SequenceGenerator(name = "corp_user_types_ID_SEQ", sequenceName = "corp_user_types_ID_SEQ", allocationSize=1)
	private BigInteger id;

	@Column(name = "USER_TYPE")
	private String USER_TYPE;

	@Column(name = "DESCRIPTION")
	private String DESCRIPTION;

	@Column(name = "CREATEDBY")
	private BigInteger CREATEDBY;

	@Column(name = "CREATEDON")
	private Date CREATEDON;

	@Column(name = "STATUSID")
	private BigInteger STATUSID;

	@Column(name = "APPID")
	private BigInteger APPID;

	@Column(name = "RULE_SEQ")
	private BigInteger RULE_SEQ;

	@Transient
	String statusname;
	
	@Transient
	String authType;
	
	
	@Transient
	private String productName;

	@Transient  
	private String action;

	@Transient  
	private String roleName;

	@Transient  
	private String createdByName;

	@Transient
	private BigDecimal role_ID;

	@Transient
	private String remark;

	@Transient
	private String activityName;

	@Transient
	private BigInteger userAction;
	

	@Transient
	private BigDecimal user_ID;

	@Transient
	private BigDecimal subMenu_ID;
	
	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getUSER_TYPE() {
		return USER_TYPE;
	}

	public void setUSER_TYPE(String uSER_TYPE) {
		USER_TYPE = uSER_TYPE;
	}

	public String getDESCRIPTION() {
		return DESCRIPTION;
	}

	public void setDESCRIPTION(String dESCRIPTION) {
		DESCRIPTION = dESCRIPTION;
	}

	public BigInteger getCREATEDBY() {
		return CREATEDBY;
	}

	public void setCREATEDBY(BigInteger cREATEDBY) {
		CREATEDBY = cREATEDBY;
	}

	public Date getCREATEDON() {
		return CREATEDON;
	}

	public void setCREATEDON(Date cREATEDON) {
		CREATEDON = cREATEDON;
	}

	public BigInteger getSTATUSID() {
		return STATUSID;
	}

	public void setSTATUSID(BigInteger sTATUSID) {
		STATUSID = sTATUSID;
	}

	public BigInteger getAPPID() {
		return APPID;
	}

	public void setAPPID(BigInteger aPPID) {
		APPID = aPPID;
	}

	public BigInteger getRULE_SEQ() {
		return RULE_SEQ;
	}

	public void setRULE_SEQ(BigInteger rULE_SEQ) {
		RULE_SEQ = rULE_SEQ;
	}

	public String getStatusname() {
		return statusname;
	}

	public void setStatusname(String statusname) {
		this.statusname = statusname;
	}

	

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public BigDecimal getRole_ID() {
		return role_ID;
	}

	public void setRole_ID(BigDecimal role_ID) {
		this.role_ID = role_ID;
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

	public BigDecimal getUser_ID() {
		return user_ID;
	}

	public void setUser_ID(BigDecimal user_ID) {
		this.user_ID = user_ID;
	}

	public BigDecimal getSubMenu_ID() {
		return subMenu_ID;
	}

	public void setSubMenu_ID(BigDecimal subMenu_ID) {
		this.subMenu_ID = subMenu_ID;
	}

	public String getAuthType() {
		return authType;
	}

	public void setAuthType(String authType) {
		this.authType = authType;
	}

}
