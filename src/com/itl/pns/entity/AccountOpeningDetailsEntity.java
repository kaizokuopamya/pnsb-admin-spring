package com.itl.pns.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "ACCOUNT_OPENING_DETAILS")
public class AccountOpeningDetailsEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
	@SequenceGenerator(name = "id_Sequence", sequenceName = "account_opening_details_ID_SEQ", allocationSize=1)
	@Column(name = "ID") 
	private BigDecimal id;

	@Column(name = "FIRST_NAME") 
	private String firstName;

	@Column(name = "LAST_NAME")
	private String lastName;

	@Column(name = "MOBILE_NUMBER") 
	private String mobileNumber;

	@Column(name = "EMAIL") 
	private String email;

	@Column(name = "PAN_NUMBER") 
	private String panNumber;                    

	@Column(name = "AADHAR_NUMBER")
	private String aadharNumber;               

	@Column(name = "GENDER")  
	private String gender;                        

	@Column(name = "DOB")  
	private String dob;

	@Column(name = "PERMANENT_ADDRESS_LINE1")  
	private String permanentAddressLine1;

	@Column(name = "PERMANENT_ADDRESS_LINE2")  
	private String permanentAddressLine2;       

	@Column(name = "PERMANENT_ADDRESS_CITY")
	private String permanentAddressCity;      

	@Column(name = "PERMANENT_ADDRESS_STATE") 
	private String permanentAddressState;       

	@Column(name = "PERMANENT_ADDRESS_PIN")  
	private String permanentAddressPin;      

	@Column(name = "NATIONALITY")  
	private String nationality;

	@Column(name = "MARITAL_STATUS")
	private String maritalStatus;

	@Column(name = "COMMUNITY")  
	private String community;                  

	@Column(name = "CATEGORY")  
	private String category;                    

	@Column(name = "FATHER_NAME") 
	private String fatherName;                   

	@Column(name = "MOTHER_NAME")  
	private String motherName;                  

	@Column(name = "COMMUNICATION_ADDRESS_LINE1")  
	private String communicationAddressLine1;   

	@Column(name = "COMMUNICATION_ADDRESS_LINE2")  
	private String communicationAddressLine2;  

	@Column(name = "COMMUNICATION_ADDRESS_CITY")  
	private String communicationAddressCity;  

	@Column(name = "COMMUNICATION_ADDRESS_STATE")  
	private String communicationAddressState;   

	@Column(name = "COMMUNICATION_ADDRESS_PIN")  
	private String communicationAddressPin;  

	@Column(name = "OCCUPATION")  
	private String occupation;                    

	@Column(name = "ANNUAL_INCOME")  
	private String annualIncome;                 

	@Column(name = "DBT_BENEFIT_NEW")  
	private char dbtBenefitNew;               

	@Column(name = "DBT_BENEFIT_ACCOUNT")  
	private char dbtBenefitAccount;           

	@Column(name = "DBT_BENEFIR_ACCOUNT_REPLACE") 
	private char dbtBenefirAccountReplace;   

	@Column(name = "BRANCH_CODE")  
	private String branchCode;                   

	@Column(name = "NOMINEE_NAME") 
	private String nomineeName;                  

	@Column(name = "NOMINEE_ADDRESS_LINE1") 
	private String nomineeAddressLine1;         

	@Column(name = "NOMINEE_ADDRESS_LINE2") 
	private String nomineeAddressLine2;         

	@Column(name = "NOMINEE_ADDRESS_CITY")  
	private String nomineeAddressCity;          

	@Column(name = "NOMINEE_ADDRESS_STATE") 
	private String nomineeAddressState;         

	@Column(name = "NOMINEE_ADDRESS_PIN")  
	private String nomineeAddressPin;           

	@Column(name = "NOMINEE_RELATION")  
	private String nomineeRelation;              

	@Column(name = "NOMINEE_DOB")  
	private String nomineeDob;

	@Column(name = "GUARDIAN_NAME") 
	private String guardianName;                 

	@Column(name = "GUARDIAN_ADDRESS_LINE1")  
	private String guardianAddressLine1;        

	@Column(name = "GUARDIAN_ADDRESS_LINE2") 
	private String guardianAddressLine2;        

	@Column(name = "GUARDIAN_ADDRESS_CITY") 
	private String guardianAddressCity;         

	@Column(name = "GUARDIAN_ADDRESS_STATE")  
	private String guardianAddressState;        

	@Column(name = "GUARDIAN_ADDRESS_PIN")  
	private String guardianAddressPin;          

	@Column(name = "GUARDIAN_TYPE")  
	private String guardianType;                 

	@Column(name = "UPI_ID") 
	private String upiId;                        

	@Column(name = "ACCOUNT_TYPE")  
	private String accountType;                  

	@Column(name = "CONCENT_FACTA")  
	private char concentFacta;                 

	@Column(name = "CONCENT_TANDC")  
	private char concentTandc;                 

	@Column(name = "CONCENT_AUTH_BANK_COMM")  
	private char concentAuthBankComm;        

	@Column(name = "CONCENT_AADHAR")  
	private char concentAadhar;                

	@Column(name = "STATUSID")  
	private BigDecimal statusid;                      

	@Column(name = "RRN")  
	private String rrn;                           

	@Column(name = "CREATEDON") 
	private Timestamp createdon;

	@Column(name = "UPDATEDON")  
	private Timestamp updatedon;

	@Column(name = "CREATEDBY")  
	private BigDecimal createdby;                     

	@Column(name = "UPDATEDBY")  
	private BigDecimal updatedby;                     

	@Column(name = "LASTDRAFTPAGE") 
	private String lastdraftpage;                 

	@Column(name = "ACCOUNTNUMBER") 
	private String accountnumber;                 

	@Column(name = "CIFNUMBER") 
	private String cifnumber;                     

	@Column(name = "AMOUNTDEPOSITED") 
	private String amountdeposited;    
	
	@Column(name="VIDEOKYCFLAG",nullable = true)
	private char videokycflag;                  

	@Column(name = "BRANCHCITY")  
	private String branchcity;                    

	@Column(name = "BRANCHSTATE")  
	private String branchstate;                   

	@Column(name = "BRANCHPINCODE")  
	private String branchpincode;

	@Column(name = "BRANCHSEARCHTYPE")  
	private String branchsearchtype;              

	@Column(name = "ACCOUNTOPENTYPE")  
	private String accountopentype;               

	@Column(name = "BANKTERMCONDITION")  
	private char banktermcondition;             

	@Column(name = "FATCADECLARATION")  
	private char fatcadeclaration;              

	@Column(name = "NOMINEEADDSAMEASPERMANENT")  
	private char nomineeaddsameaspermanent;     

	@Column(name = "COMMADDSAMEASPERMANENT")  
	private char commaddsameaspermanent;        

	@Column(name = "AADHARLINKDBT1")  
	private char aadharlinkdbt1;                

	@Column(name = "AADHARLINKDBT2")  
	private char aadharlinkdbt2;                

	@Column(name = "DONOTWANTMONINEE")  
	private char donotwantmoninee;              

	@Column(name = "SMSEMAILPERMISSION")  
	private char smsemailpermission;

	@Column(name = "CIF")  
	private String cif;                           

	@Column(name = "ACCOUNT")  
	private String account;        

	@Column(name = "ACCOUNTOPENFLAG")  
	private String accountopenflag;               

	@Column(name = "BRANCHNAME") 
	private String branchname;                    

	@Column(name = "MARITAL_STATUS_NM")  
	private String maritalStatusNm;             

	@Column(name = "COMMUNITY_NM")  
	private String communityNm;                  

	@Column(name = "CATEGORY_NM")  
	private String categoryNm;                 

	@Column(name = "PERMANENT_ADDRESS_CITY_NM")  
	private String permanentAddressCityNm;
	     
	@Column(name = "PERMANENT_ADDRESS_STATE_NM")  
	private String permanentAddressStateNm;    

	@Column(name = "COMMUNICATION_ADDRESS_CITY_NM")  
	private String communicationAddressCityNm; 

	@Column(name = "COMMUNICATION_ADDRESS_STATE_NM")  
	private String communicationAddressStateNm;

	@Column(name = "NOMINEE_ADDRESS_CITY_NM")  
	private String nomineeAddressCityNm;       

	@Column(name = "NOMINEE_ADDRESS_STATE_NM")  
	private String nomineeAddressStateNm;      

	@Column(name = "NOMINEE_RELATION_NM")  
	private String nomineeRelationNm;           

	@Column(name = "GUARDIAN_ADDRESS_CITY_NM")  
	private String guardianAddressCityNm;      

	@Column(name = "GUARDIAN_ADDRESS_STATE_NM")  
	private String guardianAddressStateNm;     

	@Column(name = "GUARDIAN_TYPE_NM")  
	private String guardianTypeNm;              

	@Column(name = "BRANCHCITY_NM")  
	private String branchcityNm;                 

	@Column(name = "BRANCHSTATE_NM")  
	private String branchstateNm; 

	@Column(name = "OCCUPATION_NM")  
	private String occupationNm;                  

	@Column(name = "ANNUALINCOME_NM")  
	private String annualincomeNm;                

	@Column(name = "BRANCHADDRESS_NM")  
	private String branchaddressNm;               

	@Column(name = "NOMINEERELNAME_NM")  
	private String nomineerelnameNm;              

	@Column(name = "MIDDLE_NAME")  
	private String middleName;                    

	@Column(name = "AADHARIMAGE")                    
	private String aadharimage;
	
	@Column(name = "AADHARPDF")                    
	private String aadharpdf;
	
	@Column(name = "AADHARFILENAME")                    
	private String aadharfilename;
	

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPanNumber() {
		return panNumber;
	}

	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	public String getAadharNumber() {
		return aadharNumber;
	}

	public void setAadharNumber(String aadharNumber) {
		this.aadharNumber = aadharNumber;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getPermanentAddressLine1() {
		return permanentAddressLine1;
	}

	public void setPermanentAddressLine1(String permanentAddressLine1) {
		this.permanentAddressLine1 = permanentAddressLine1;
	}

	public String getPermanentAddressLine2() {
		return permanentAddressLine2;
	}

	public void setPermanentAddressLine2(String permanentAddressLine2) {
		this.permanentAddressLine2 = permanentAddressLine2;
	}

	public String getPermanentAddressCity() {
		return permanentAddressCity;
	}

	public void setPermanentAddressCity(String permanentAddressCity) {
		this.permanentAddressCity = permanentAddressCity;
	}

	public String getPermanentAddressState() {
		return permanentAddressState;
	}

	public void setPermanentAddressState(String permanentAddressState) {
		this.permanentAddressState = permanentAddressState;
	}

	public String getPermanentAddressPin() {
		return permanentAddressPin;
	}

	public void setPermanentAddressPin(String permanentAddressPin) {
		this.permanentAddressPin = permanentAddressPin;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getCommunity() {
		return community;
	}

	public void setCommunity(String community) {
		this.community = community;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getMotherName() {
		return motherName;
	}

	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}

	public String getCommunicationAddressLine1() {
		return communicationAddressLine1;
	}

	public void setCommunicationAddressLine1(String communicationAddressLine1) {
		this.communicationAddressLine1 = communicationAddressLine1;
	}

	public String getCommunicationAddressLine2() {
		return communicationAddressLine2;
	}

	public void setCommunicationAddressLine2(String communicationAddressLine2) {
		this.communicationAddressLine2 = communicationAddressLine2;
	}

	public String getCommunicationAddressCity() {
		return communicationAddressCity;
	}

	public void setCommunicationAddressCity(String communicationAddressCity) {
		this.communicationAddressCity = communicationAddressCity;
	}

	public String getCommunicationAddressState() {
		return communicationAddressState;
	}

	public void setCommunicationAddressState(String communicationAddressState) {
		this.communicationAddressState = communicationAddressState;
	}

	public String getCommunicationAddressPin() {
		return communicationAddressPin;
	}

	public void setCommunicationAddressPin(String communicationAddressPin) {
		this.communicationAddressPin = communicationAddressPin;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getAnnualIncome() {
		return annualIncome;
	}

	public void setAnnualIncome(String annualIncome) {
		this.annualIncome = annualIncome;
	}

	public char getDbtBenefitNew() {
		return dbtBenefitNew;
	}

	public void setDbtBenefitNew(char dbtBenefitNew) {
		this.dbtBenefitNew = dbtBenefitNew;
	}

	public char getDbtBenefitAccount() {
		return dbtBenefitAccount;
	}

	public void setDbtBenefitAccount(char dbtBenefitAccount) {
		this.dbtBenefitAccount = dbtBenefitAccount;
	}

	public char getDbtBenefirAccountReplace() {
		return dbtBenefirAccountReplace;
	}

	public void setDbtBenefirAccountReplace(char dbtBenefirAccountReplace) {
		this.dbtBenefirAccountReplace = dbtBenefirAccountReplace;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getNomineeName() {
		return nomineeName;
	}

	public void setNomineeName(String nomineeName) {
		this.nomineeName = nomineeName;
	}

	public String getNomineeAddressLine1() {
		return nomineeAddressLine1;
	}

	public void setNomineeAddressLine1(String nomineeAddressLine1) {
		this.nomineeAddressLine1 = nomineeAddressLine1;
	}

	public String getNomineeAddressLine2() {
		return nomineeAddressLine2;
	}

	public void setNomineeAddressLine2(String nomineeAddressLine2) {
		this.nomineeAddressLine2 = nomineeAddressLine2;
	}

	public String getNomineeAddressCity() {
		return nomineeAddressCity;
	}

	public void setNomineeAddressCity(String nomineeAddressCity) {
		this.nomineeAddressCity = nomineeAddressCity;
	}

	public String getNomineeAddressState() {
		return nomineeAddressState;
	}

	public void setNomineeAddressState(String nomineeAddressState) {
		this.nomineeAddressState = nomineeAddressState;
	}

	public String getNomineeAddressPin() {
		return nomineeAddressPin;
	}

	public void setNomineeAddressPin(String nomineeAddressPin) {
		this.nomineeAddressPin = nomineeAddressPin;
	}

	public String getNomineeRelation() {
		return nomineeRelation;
	}

	public void setNomineeRelation(String nomineeRelation) {
		this.nomineeRelation = nomineeRelation;
	}

	public String getNomineeDob() {
		return nomineeDob;
	}

	public void setNomineeDob(String nomineeDob) {
		this.nomineeDob = nomineeDob;
	}

	public String getGuardianName() {
		return guardianName;
	}

	public void setGuardianName(String guardianName) {
		this.guardianName = guardianName;
	}

	public String getGuardianAddressLine1() {
		return guardianAddressLine1;
	}

	public void setGuardianAddressLine1(String guardianAddressLine1) {
		this.guardianAddressLine1 = guardianAddressLine1;
	}

	public String getGuardianAddressLine2() {
		return guardianAddressLine2;
	}

	public void setGuardianAddressLine2(String guardianAddressLine2) {
		this.guardianAddressLine2 = guardianAddressLine2;
	}

	public String getGuardianAddressCity() {
		return guardianAddressCity;
	}

	public void setGuardianAddressCity(String guardianAddressCity) {
		this.guardianAddressCity = guardianAddressCity;
	}

	public String getGuardianAddressState() {
		return guardianAddressState;
	}

	public void setGuardianAddressState(String guardianAddressState) {
		this.guardianAddressState = guardianAddressState;
	}

	public String getGuardianAddressPin() {
		return guardianAddressPin;
	}

	public void setGuardianAddressPin(String guardianAddressPin) {
		this.guardianAddressPin = guardianAddressPin;
	}

	public String getGuardianType() {
		return guardianType;
	}

	public void setGuardianType(String guardianType) {
		this.guardianType = guardianType;
	}

	public String getUpiId() {
		return upiId;
	}

	public void setUpiId(String upiId) {
		this.upiId = upiId;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public char getConcentFacta() {
		return concentFacta;
	}

	public void setConcentFacta(char concentFacta) {
		this.concentFacta = concentFacta;
	}

	public char getConcentTandc() {
		return concentTandc;
	}

	public void setConcentTandc(char concentTandc) {
		this.concentTandc = concentTandc;
	}

	public char getConcentAuthBankComm() {
		return concentAuthBankComm;
	}

	public void setConcentAuthBankComm(char concentAuthBankComm) {
		this.concentAuthBankComm = concentAuthBankComm;
	}

	public char getConcentAadhar() {
		return concentAadhar;
	}

	public void setConcentAadhar(char concentAadhar) {
		this.concentAadhar = concentAadhar;
	}

	public BigDecimal getStatusid() {
		return statusid;
	}

	public void setStatusid(BigDecimal statusid) {
		this.statusid = statusid;
	}

	public String getRrn() {
		return rrn;
	}

	public void setRrn(String rrn) {
		this.rrn = rrn;
	}

	public Timestamp getCreatedon() {
		return createdon;
	}

	public void setCreatedon(Timestamp createdon) {
		this.createdon = createdon;
	}

	public Timestamp getUpdatedon() {
		return updatedon;
	}

	public void setUpdatedon(Timestamp updatedon) {
		this.updatedon = updatedon;
	}

	public BigDecimal getCreatedby() {
		return createdby;
	}

	public void setCreatedby(BigDecimal createdby) {
		this.createdby = createdby;
	}

	public BigDecimal getUpdatedby() {
		return updatedby;
	}

	public void setUpdatedby(BigDecimal updatedby) {
		this.updatedby = updatedby;
	}

	public String getLastdraftpage() {
		return lastdraftpage;
	}

	public void setLastdraftpage(String lastdraftpage) {
		this.lastdraftpage = lastdraftpage;
	}

	public String getAccountnumber() {
		return accountnumber;
	}

	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
	}

	public String getCifnumber() {
		return cifnumber;
	}

	public void setCifnumber(String cifnumber) {
		this.cifnumber = cifnumber;
	}

	public String getAmountdeposited() {
		return amountdeposited;
	}

	public void setAmountdeposited(String amountdeposited) {
		this.amountdeposited = amountdeposited;
	}

	public char getVideokycflag() {
		return videokycflag;
	}

	public void setVideokycflag(char videokycflag) {
		this.videokycflag = videokycflag;
	}

	public String getBranchcity() {
		return branchcity;
	}

	public void setBranchcity(String branchcity) {
		this.branchcity = branchcity;
	}

	public String getBranchstate() {
		return branchstate;
	}

	public void setBranchstate(String branchstate) {
		this.branchstate = branchstate;
	}

	public String getBranchpincode() {
		return branchpincode;
	}

	public void setBranchpincode(String branchpincode) {
		this.branchpincode = branchpincode;
	}

	public String getBranchsearchtype() {
		return branchsearchtype;
	}

	public void setBranchsearchtype(String branchsearchtype) {
		this.branchsearchtype = branchsearchtype;
	}

	public String getAccountopentype() {
		return accountopentype;
	}

	public void setAccountopentype(String accountopentype) {
		this.accountopentype = accountopentype;
	}

	public char getBanktermcondition() {
		return banktermcondition;
	}

	public void setBanktermcondition(char banktermcondition) {
		this.banktermcondition = banktermcondition;
	}

	public char getFatcadeclaration() {
		return fatcadeclaration;
	}

	public void setFatcadeclaration(char fatcadeclaration) {
		this.fatcadeclaration = fatcadeclaration;
	}

	public char getNomineeaddsameaspermanent() {
		return nomineeaddsameaspermanent;
	}

	public void setNomineeaddsameaspermanent(char nomineeaddsameaspermanent) {
		this.nomineeaddsameaspermanent = nomineeaddsameaspermanent;
	}

	public char getCommaddsameaspermanent() {
		return commaddsameaspermanent;
	}

	public void setCommaddsameaspermanent(char commaddsameaspermanent) {
		this.commaddsameaspermanent = commaddsameaspermanent;
	}

	public char getAadharlinkdbt1() {
		return aadharlinkdbt1;
	}

	public void setAadharlinkdbt1(char aadharlinkdbt1) {
		this.aadharlinkdbt1 = aadharlinkdbt1;
	}

	public char getAadharlinkdbt2() {
		return aadharlinkdbt2;
	}

	public void setAadharlinkdbt2(char aadharlinkdbt2) {
		this.aadharlinkdbt2 = aadharlinkdbt2;
	}

	public char getDonotwantmoninee() {
		return donotwantmoninee;
	}

	public void setDonotwantmoninee(char donotwantmoninee) {
		this.donotwantmoninee = donotwantmoninee;
	}

	public char getSmsemailpermission() {
		return smsemailpermission;
	}

	public void setSmsemailpermission(char smsemailpermission) {
		this.smsemailpermission = smsemailpermission;
	}

	public String getCif() {
		return cif;
	}

	public void setCif(String cif) {
		this.cif = cif;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getAccountopenflag() {
		return accountopenflag;
	}

	public void setAccountopenflag(String accountopenflag) {
		this.accountopenflag = accountopenflag;
	}

	public String getBranchname() {
		return branchname;
	}

	public void setBranchname(String branchname) {
		this.branchname = branchname;
	}

	public String getMaritalStatusNm() {
		return maritalStatusNm;
	}

	public void setMaritalStatusNm(String maritalStatusNm) {
		this.maritalStatusNm = maritalStatusNm;
	}

	public String getCommunityNm() {
		return communityNm;
	}

	public void setCommunityNm(String communityNm) {
		this.communityNm = communityNm;
	}

	public String getCategoryNm() {
		return categoryNm;
	}

	public void setCategoryNm(String categoryNm) {
		this.categoryNm = categoryNm;
	}

	public String getPermanentAddressCityNm() {
		return permanentAddressCityNm;
	}

	public void setPermanentAddressCityNm(String permanentAddressCityNm) {
		this.permanentAddressCityNm = permanentAddressCityNm;
	}

	public String getPermanentAddressStateNm() {
		return permanentAddressStateNm;
	}

	public void setPermanentAddressStateNm(String permanentAddressStateNm) {
		this.permanentAddressStateNm = permanentAddressStateNm;
	}

	public String getCommunicationAddressCityNm() {
		return communicationAddressCityNm;
	}

	public void setCommunicationAddressCityNm(String communicationAddressCityNm) {
		this.communicationAddressCityNm = communicationAddressCityNm;
	}

	public String getCommunicationAddressStateNm() {
		return communicationAddressStateNm;
	}

	public void setCommunicationAddressStateNm(String communicationAddressStateNm) {
		this.communicationAddressStateNm = communicationAddressStateNm;
	}

	public String getNomineeAddressCityNm() {
		return nomineeAddressCityNm;
	}

	public void setNomineeAddressCityNm(String nomineeAddressCityNm) {
		this.nomineeAddressCityNm = nomineeAddressCityNm;
	}

	public String getNomineeAddressStateNm() {
		return nomineeAddressStateNm;
	}

	public void setNomineeAddressStateNm(String nomineeAddressStateNm) {
		this.nomineeAddressStateNm = nomineeAddressStateNm;
	}

	public String getNomineeRelationNm() {
		return nomineeRelationNm;
	}

	public void setNomineeRelationNm(String nomineeRelationNm) {
		this.nomineeRelationNm = nomineeRelationNm;
	}

	public String getGuardianAddressCityNm() {
		return guardianAddressCityNm;
	}

	public void setGuardianAddressCityNm(String guardianAddressCityNm) {
		this.guardianAddressCityNm = guardianAddressCityNm;
	}

	public String getGuardianAddressStateNm() {
		return guardianAddressStateNm;
	}

	public void setGuardianAddressStateNm(String guardianAddressStateNm) {
		this.guardianAddressStateNm = guardianAddressStateNm;
	}

	public String getGuardianTypeNm() {
		return guardianTypeNm;
	}

	public void setGuardianTypeNm(String guardianTypeNm) {
		this.guardianTypeNm = guardianTypeNm;
	}

	public String getBranchcityNm() {
		return branchcityNm;
	}

	public void setBranchcityNm(String branchcityNm) {
		this.branchcityNm = branchcityNm;
	}

	public String getBranchstateNm() {
		return branchstateNm;
	}

	public void setBranchstateNm(String branchstateNm) {
		this.branchstateNm = branchstateNm;
	}

	public String getOccupationNm() {
		return occupationNm;
	}

	public void setOccupationNm(String occupationNm) {
		this.occupationNm = occupationNm;
	}

	public String getAnnualincomeNm() {
		return annualincomeNm;
	}

	public void setAnnualincomeNm(String annualincomeNm) {
		this.annualincomeNm = annualincomeNm;
	}

	public String getBranchaddressNm() {
		return branchaddressNm;
	}

	public void setBranchaddressNm(String branchaddressNm) {
		this.branchaddressNm = branchaddressNm;
	}

	public String getNomineerelnameNm() {
		return nomineerelnameNm;
	}

	public void setNomineerelnameNm(String nomineerelnameNm) {
		this.nomineerelnameNm = nomineerelnameNm;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getAadharimage() {
		return aadharimage;
	}

	public void setAadharimage(String aadharimage) {
		this.aadharimage = aadharimage;
	}

	public String getAadharpdf() {
		return aadharpdf;
	}

	public void setAadharpdf(String aadharpdf) {
		this.aadharpdf = aadharpdf;
	}

	public String getAadharfilename() {
		return aadharfilename;
	}

	public void setAadharfilename(String aadharfilename) {
		this.aadharfilename = aadharfilename;
	}

	
	


	 

}
