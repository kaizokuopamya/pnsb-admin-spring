package com.itl.pns.bean;

import java.math.BigInteger;

public class CountryRestrictionBean {
	
	BigInteger ID;
	
	String COUNTRYNAME;
	
	String STATUS;
	
	BigInteger CUSTID;
	
	String MOBILENO;
	
	BigInteger STATUSID;
	
	String CUSTOMERNAME;
	
	String EMAIL;

	public String getCOUNTRYNAME() {
		return COUNTRYNAME;
	}

	public void setCOUNTRYNAME(String cOUNTRYNAME) {
		COUNTRYNAME = cOUNTRYNAME;
	}

	public String getSTATUS() {
		return STATUS;
	}

	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}

	public BigInteger getID() {
		return ID;
	}

	public void setID(BigInteger iD) {
		ID = iD;
	}

	public BigInteger getSTATUSID() {
		return STATUSID;
	}

	public void setSTATUSID(BigInteger sTATUSID) {
		STATUSID = sTATUSID;
	}

	

	public BigInteger getCUSTID() {
		return CUSTID;
	}

	public void setCUSTID(BigInteger cUSTID) {
		CUSTID = cUSTID;
	}

	public String getMOBILENO() {
		return MOBILENO;
	}

	public void setMOBILENO(String mOBILENO) {
		MOBILENO = mOBILENO;
	}

	public String getCUSTOMERNAME() {
		return CUSTOMERNAME;
	}

	public void setCUSTOMERNAME(String cUSTOMERNAME) {
		CUSTOMERNAME = cUSTOMERNAME;
	}

	public String getEMAIL() {
		return EMAIL;
	}

	public void setEMAIL(String eMAIL) {
		EMAIL = eMAIL;
	}
	
	
	

	
}
