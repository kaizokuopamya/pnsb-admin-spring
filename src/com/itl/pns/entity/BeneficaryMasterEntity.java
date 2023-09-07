package com.itl.pns.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "BENEFICIARYMASTER")
public class BeneficaryMasterEntity {

	@javax.persistence.Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "beneficiarymaster_id_SEQ")
	@SequenceGenerator(name = "beneficiarymaster_id_SEQ", sequenceName = "beneficiarymaster_id_SEQ", allocationSize = 1)
	private BigDecimal id;
	

	@Column(name = "BENEFICIARYTYPE")
	private BigDecimal beneficiarytype;

	@Column(name = "BENEFICIARYNAME")
	private String beneficiaryname;

	@Column(name = "BANKCODE")
	private String bankcode;

	@Column(name = "BEN_MOBILENO")
	private String ben_mobileno;
	


	@Column(name = "BEN_ACCOUNTNUMBER")
	private String ben_accountnumber;

	@Column(name = "BEN_NICKNAME")
	private String ben_nickname;

	@Column(name = "CREATEDON")
	public Date createdon;

	@Column(name = "CREATEDBY")
	private BigDecimal createdby;

	@Column(name = "UPDATEDON")
	public Date updatedon;

	@Column(name = "UPDATEDBY")
	private BigDecimal updatedby;
      
	



	@Column(name = "CURRENCY")
	private String currency;
	
	@Column(name = "CITYID")
	private BigDecimal cityid;
	
	@Column(name = "BRANCHNAME")
	private String branchname;
	
	@Column(name = "MAXAMOUNT")
	private BigDecimal maxamount;
	
	
	@Column(name = "IFSCCODE")
	private String ifsccode;
	
	
	@Column(name = "SWIFTCODE")
	private String swiftcode;
	

	
	@Column(name = "CUSTOMERID")
	private BigDecimal customerid;
	
	@Column(name = "STATUSID")
	private BigDecimal statusid;

	@Column(name = "VPA")
	private String vpa;

	
	@Column(name = "MMID")
	private String mmid;
	
	@Column(name = "BENE_CREDIT_CARD_NO")
	private String bene_credit_card_no;
	
	@Column(name = "BANK_NAME")
	private String bank_name;
	
	@Column(name = "CORPCOMPID")
	private BigDecimal corpcompid;
	
	
	@Column(name = "CIFNUMBER")
	private String cifnumber;
	
    @Transient
    private String statusName;
    
    @Transient
    private String createdDate;

    @Transient
    private String createdByName;
    
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
	private String roleName;


	public BigDecimal getId() {
		return id;
	}


	public void setId(BigDecimal id) {
		this.id = id;
	}


	public BigDecimal getBeneficiarytype() {
		return beneficiarytype;
	}


	public void setBeneficiarytype(BigDecimal beneficiarytype) {
		this.beneficiarytype = beneficiarytype;
	}


	public String getBeneficiaryname() {
		return beneficiaryname;
	}


	public void setBeneficiaryname(String beneficiaryname) {
		this.beneficiaryname = beneficiaryname;
	}


	public String getBankcode() {
		return bankcode;
	}


	public void setBankcode(String bankcode) {
		this.bankcode = bankcode;
	}


	public String getBen_mobileno() {
		return ben_mobileno;
	}


	public void setBen_mobileno(String ben_mobileno) {
		this.ben_mobileno = ben_mobileno;
	}


	public String getBen_accountnumber() {
		return ben_accountnumber;
	}


	public void setBen_accountnumber(String ben_accountnumber) {
		this.ben_accountnumber = ben_accountnumber;
	}


	public String getBen_nickname() {
		return ben_nickname;
	}


	public void setBen_nickname(String ben_nickname) {
		this.ben_nickname = ben_nickname;
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


	public String getCurrency() {
		return currency;
	}


	public void setCurrency(String currency) {
		this.currency = currency;
	}


	public BigDecimal getCityid() {
		return cityid;
	}


	public void setCityid(BigDecimal cityid) {
		this.cityid = cityid;
	}


	public String getBranchname() {
		return branchname;
	}


	public void setBranchname(String branchname) {
		this.branchname = branchname;
	}


	public BigDecimal getMaxamount() {
		return maxamount;
	}


	public void setMaxamount(BigDecimal maxamount) {
		this.maxamount = maxamount;
	}


	public String getIfsccode() {
		return ifsccode;
	}


	public void setIfsccode(String ifsccode) {
		this.ifsccode = ifsccode;
	}


	public String getSwiftcode() {
		return swiftcode;
	}


	public void setSwiftcode(String swiftcode) {
		this.swiftcode = swiftcode;
	}


	public BigDecimal getCustomerid() {
		return customerid;
	}


	public void setCustomerid(BigDecimal customerid) {
		this.customerid = customerid;
	}


	public BigDecimal getStatusid() {
		return statusid;
	}


	public void setStatusid(BigDecimal statusid) {
		this.statusid = statusid;
	}


	public String getVpa() {
		return vpa;
	}


	public void setVpa(String vpa) {
		this.vpa = vpa;
	}


	public String getMmid() {
		return mmid;
	}


	public void setMmid(String mmid) {
		this.mmid = mmid;
	}


	public String getBene_credit_card_no() {
		return bene_credit_card_no;
	}


	public void setBene_credit_card_no(String bene_credit_card_no) {
		this.bene_credit_card_no = bene_credit_card_no;
	}


	public String getBank_name() {
		return bank_name;
	}


	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}


	public BigDecimal getCorpcompid() {
		return corpcompid;
	}


	public void setCorpcompid(BigDecimal corpcompid) {
		this.corpcompid = corpcompid;
	}


	public String getCifnumber() {
		return cifnumber;
	}


	public void setCifnumber(String cifnumber) {
		this.cifnumber = cifnumber;
	}


	public String getStatusName() {
		return statusName;
	}


	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}


	public String getCreatedDate() {
		return createdDate;
	}


	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
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


	public String getRoleName() {
		return roleName;
	}


	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	
	

}