package com.itl.pns.entity;

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
@Table(name = "ACTIVITYMASTER")
public class ActivityMaster {

		@Id
		@Column(name = "ID")
		@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "activitymaster_id_SEQ")
		@SequenceGenerator(name = "activitymaster_id_SEQ", sequenceName = "activitymaster_id_SEQ", allocationSize=1)
		int id;
	
		@Column(name = "ACTIVITYCODE")
		String activitycode;
		
		@Column(name = "DISPLAYNAME")
		String displayname;
		
		@Column(name = "LIMITS")
		int limits;
		
		@Column(name = "ENCRYPTIONTYPE")
        char encryptiontype;
		
		@Column(name = "CREATEDBY")
		int createdby;
		
		@Column(name = "CREATEDON")
		Date createdon;
		
		@Column(name = "STATUSID")
		int statusid;
		
		@Column(name = "APPID")
		int appid;
		
		@Column(name = "FT_NFT")
		String ft_nft;

		
		@Transient
		private BigDecimal user_ID;
		
		@Transient
		private BigDecimal role_ID;
		
		@Transient
		private BigDecimal subMenu_ID;
		
		@Transient
		private String remark;
		
		@Transient
		private String activityName;
		
		@Transient
		private BigInteger userAction;
		
		@Transient
		private String statusName;
		
		@Transient
		private String appName;
		
	    @Transient
	    private String createdByName;
	    
		@Transient
	    private String productName;

		@Transient  
		private String action;

	 @Transient  
		private String roleName;
		
	
		public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

		public String getActivitycode() {
			return activitycode;
		}

		public void setActivitycode(String activitycode) {
			this.activitycode = activitycode;
		}

		public String getDisplayname() {
			return displayname;
		}

		public void setDisplayname(String displayname) {
			this.displayname = displayname;
		}

		public int getLimits() {
			return limits;
		}

		public void setLimits(int limits) {
			this.limits = limits;
		}

		public char getEncryptiontype() {
			return encryptiontype;
		}

		public void setEncryptiontype(char encryptiontype) {
			this.encryptiontype = encryptiontype;
		}

		public int getCreatedby() {
			return createdby;
		}

		public void setCreatedby(int createdby) {
			this.createdby = createdby;
		}

		public Date getCreatedon() {
			return createdon;
		}

		public void setCreatedon(Date createdon) {
			this.createdon = createdon;
		}

		public int getStatusid() {
			return statusid;
		}

		public void setStatusid(int statusid) {
			this.statusid = statusid;
		}

		public int getAppid() {
			return appid;
		}

		public void setAppid(int appid) {
			this.appid = appid;
		}

		public String getFt_nft() {
			return ft_nft;
		}

		public void setFt_nft(String ft_nft) {
			this.ft_nft = ft_nft;
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

		public String getStatusName() {
			return statusName;
		}

		public void setStatusName(String statusName) {
			this.statusName = statusName;
		}

		public String getAppName() {
			return appName;
		}

		public void setAppName(String appName) {
			this.appName = appName;
		}

		public String getCreatedByName() {
			return createdByName;
		}

		public void setCreatedByName(String createdByName) {
			this.createdByName = createdByName;
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
		
		
}
