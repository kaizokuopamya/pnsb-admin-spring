package com.itl.pns.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseParameter {
	    @JsonProperty("accountno")
		 private String accountno;
	    
	    @JsonProperty("customerId")
		 private String customerId;
	    
	    @JsonProperty("RegistrationSuccess")
		 private String RegistrationSuccess;
	    
	    @JsonProperty("resstatus")
		 private String resstatus;
	    
	    @JsonProperty("opstatus")
		 private String opstatus;
	    
	    @JsonProperty("userType")
		 private String userType;

	    @JsonProperty("sbBalance")
	    private String sbBalance;

	    @JsonProperty("amountTotal")
	    private String amountTotal;

	    @JsonProperty("cust_address")
	    private String cust_address;
		
	    
	    @JsonProperty("currency")
	    private String currency;
	    
	    
	    @JsonProperty("Result")
	    private String Result;
	    
	    public String getCurrency() {
			return currency;
		}

		public void setCurrency(String currency) {
			this.currency = currency;
		}

		public String getSbBalance() {
	    	  return sbBalance;
	    	 }

		 public String getAccountno() {
		  return accountno;
		 }

		 public String getCustomerId() {
		  return customerId;
		 }

		 public String getRegistrationSuccess() {
		  return RegistrationSuccess;
		 }

		 public String getResstatus() {
		  return resstatus;
		 }

		 public String getOpstatus() {
		  return opstatus;
		 }

		 public String getUserType() {
		  return userType;
		 }

		 // Setter Methods 

		 public void setSbBalance(String sbBalance) {
			  this.sbBalance = sbBalance;
			 }

		 public void setAccountno(String accountno) {
		  this.accountno = accountno;
		 }

		 public void setCustomerId(String customerId) {
		  this.customerId = customerId;
		 }

		 public void setRegistrationSuccess(String RegistrationSuccess) {
		  this.RegistrationSuccess = RegistrationSuccess;
		 }

		 public void setResstatus(String resstatus) {
		  this.resstatus = resstatus;
		 }

		 public void setOpstatus(String opstatus) {
		  this.opstatus = opstatus;
		 }

		 public void setUserType(String userType) {
		  this.userType = userType;
		 }

		public String getAmountTotal() {
			return amountTotal;
		}

		public void setAmountTotal(String amountTotal) {
			this.amountTotal = amountTotal;
		}

		public String getCust_address() {
			return cust_address;
		}

		public void setCust_address(String cust_address) {
			this.cust_address = cust_address;
		}

		public String getResult() {
			return Result;
		}

		public void setResult(String result) {
			Result = result;
		}
		

}


