package com.itl.pns.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="LANGUAGEJSON_NEW")
public class LanguageJsonNewEntity {
	
	
	     @Id
		@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LANG_JSONID_SWQ")
		@SequenceGenerator(name = "LANG_JSONID_SWQ", sequenceName = "LANG_JSONID_SWQ", allocationSize=1)
	     public BigDecimal id;
		
		@Column(name = "ENGLISHTEXT")
		public String englishtext;
		
		@Column(name = "LANGUAGECODE")
		public String languagecode;
		
		@Column(name = "LANGUAGETEXT")
		public String languagetext;
		
	
		@Column(name = "CREATEDON")
		public Date createdon;
		
		@Column(name = "CREATEDBY")
		public BigDecimal createdby;
		
		
		@Column(name = "LANGUAGECODEDESC")
		String languagecodedesc;
		
		@Column(name = "STATUSID")
		BigDecimal statusId;
		
		
		@Transient
		String createdByName;
		
		
		@Transient
		String statusName;
		
				
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
		private BigDecimal userAction;
		
		@Transient
		String action;
		
		@Transient
		String roleName;
		
		@Transient
	    private String productName;
		

		public BigDecimal getId() {
			return id;
		}

		public void setId(BigDecimal id) {
			this.id = id;
		}

		public String getEnglishtext() {
			return englishtext;
		}

		public void setEnglishtext(String englishtext) {
			this.englishtext = englishtext;
		}

		public String getLanguagecode() {
			return languagecode;
		}

		public void setLanguagecode(String languagecode) {
			this.languagecode = languagecode;
		}

		public String getLanguagetext() {
			return languagetext;
		}

		public void setLanguagetext(String languagetext) {
			this.languagetext = languagetext;
		}


		public Date getCreatedon() {
			return createdon;
		}

		public void setCreatedon(Date createdon) {
			this.createdon = createdon;
		}

		

		public BigDecimal getCreatedby() {
			return createdby;
		}

		public void setCreatedby(BigDecimal createdby) {
			this.createdby = createdby;
		}


		public String getLanguagecodedesc() {
			return languagecodedesc;
		}

		public void setLanguagecodedesc(String languagecodedesc) {
			this.languagecodedesc = languagecodedesc;
		}


		public String getCreatedByName() {
			return createdByName;
		}

		public void setCreatedByName(String createdByName) {
			this.createdByName = createdByName;
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

		public BigDecimal getStatusId() {
			return statusId;
		}

		public void setStatusId(BigDecimal statusId) {
			this.statusId = statusId;
		}

	
		public String getStatusName() {
			return statusName;
		}

		public void setStatusName(String statusName) {
			this.statusName = statusName;
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

		public BigDecimal getUserAction() {
			return userAction;
		}

		public void setUserAction(BigDecimal userAction) {
			this.userAction = userAction;
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

		public String getProductName() {
			return productName;
		}

		public void setProductName(String productName) {
			this.productName = productName;
		}

		


}
