package com.itl.pns.bean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "RECONCILE_BILLDESK")
public class ReconcileBillDeskBean {

	
	@Column(name="ID")
	long ID;
	
	@Column(name="CUSTOMER_ID")
	String CUSTOMER_ID;
	
	@Column(name="MERCHANT_COD")
	String MERCHANT_CODE;
	
	@Column(name="MERCHANT_REF_NO")
	String MERCHANT_REF_NO;
	
	@Column(name="BANK_REF_NO")
	String BANK_REF_NO;
	
	@Column(name="TRAN_DATE")
	String TRAN_DATE;
	
	@Column(name="TRAN_AMOUNT")
	String TRAN_AMOUNT;
	
	@Column(name="DEBIT_ACC_NO")
	String DEBIT_ACC_NO;
	
	@Column(name="RECON_STATUS")
	String RECON_STATUS;
	
	@Column(name="REFUND_TILL_DATE")
	String REFUND_TILL_DATE;
	
	@Column(name="PROCESSING_STATUS")
	String PROCESSING_STATUS;
	
	@Column(name="JNO")
	String JNO;
	
	@Column(name="PAID_STATUS")
	String PAID_STATUS;
	
	@Column(name="NARRATION")
	String NARRATION;
	
	@Column(name="BILL_TYPE")
	String BILL_TYPE;
	
	@Column(name="MOBILE_NO")
	String MOBILE_NO;
	
	@Transient
	String FROMDATE;
	@Transient
	String TODATE;
	
	public long getID() {
		return ID;
	}
	public void setID(long iD) {
		ID = iD;
	}
	
	public String getCUSTOMER_ID() {
		return CUSTOMER_ID;
	}
	public void setCUSTOMER_ID(String cUSTOMER_ID) {
		CUSTOMER_ID = cUSTOMER_ID;
	}
	public String getMERCHANT_CODE() {
		return MERCHANT_CODE;
	}
	public void setMERCHANT_CODE(String mERCHANT_CODE) {
		MERCHANT_CODE = mERCHANT_CODE;
	}
	public String getMERCHANT_REF_NO() {
		return MERCHANT_REF_NO;
	}
	public void setMERCHANT_REF_NO(String mERCHANT_REF_NO) {
		MERCHANT_REF_NO = mERCHANT_REF_NO;
	}
	public String getBANK_REF_NO() {
		return BANK_REF_NO;
	}
	public void setBANK_REF_NO(String bANK_REF_NO) {
		BANK_REF_NO = bANK_REF_NO;
	}
	public String getTRAN_DATE() {
		return TRAN_DATE;
	}
	public void setTRAN_DATE(String tRAN_DATE) {
		TRAN_DATE = tRAN_DATE;
	}
	public String getTRAN_AMOUNT() {
		return TRAN_AMOUNT;
	}
	public void setTRAN_AMOUNT(String tRAN_AMOUNT) {
		TRAN_AMOUNT = tRAN_AMOUNT;
	}
	public String getDEBIT_ACC_NO() {
		return DEBIT_ACC_NO;
	}
	public void setDEBIT_ACC_NO(String dEBIT_ACC_NO) {
		DEBIT_ACC_NO = dEBIT_ACC_NO;
	}
	public String getRECON_STATUS() {
		return RECON_STATUS;
	}
	public void setRECON_STATUS(String rECON_STATUS) {
		RECON_STATUS = rECON_STATUS;
	}
	public String getREFUND_TILL_DATE() {
		return REFUND_TILL_DATE;
	}
	public void setREFUND_TILL_DATE(String rEFUND_TILL_DATE) {
		REFUND_TILL_DATE = rEFUND_TILL_DATE;
	}
	public String getPROCESSING_STATUS() {
		return PROCESSING_STATUS;
	}
	public void setPROCESSING_STATUS(String pROCESSING_STATUS) {
		PROCESSING_STATUS = pROCESSING_STATUS;
	}
	public String getJNO() {
		return JNO;
	}
	public void setJNO(String jNO) {
		JNO = jNO;
	}
	public String getPAID_STATUS() {
		return PAID_STATUS;
	}
	public void setPAID_STATUS(String pAID_STATUS) {
		PAID_STATUS = pAID_STATUS;
	}
	
	public String getBILL_TYPE() {
		return BILL_TYPE;
	}
	public void setBILL_TYPE(String bILL_TYPE) {
		BILL_TYPE = bILL_TYPE;
	}
	public String getMOBILE_NO() {
		return MOBILE_NO;
	}
	public void setMOBILE_NO(String mOBILE_NO) {
		MOBILE_NO = mOBILE_NO;
	}
	public String getFROMDATE() {
		return FROMDATE;
	}
	public void setFROMDATE(String fROMDATE) {
		FROMDATE = fROMDATE;
	}
	public String getTODATE() {
		return TODATE;
	}
	public void setTODATE(String tODATE) {
		TODATE = tODATE;
	}
	
	public String getNARRATION() {
		return NARRATION;
		
		}
	
	public void setNARRATION(Clob nARRATION) {
		NARRATION = getClobString(nARRATION);//nARRATION;
	}
	
	private String getClobString(Clob nARRATION2) {
		StringBuilder sb = new StringBuilder();
		try {
			Reader reader = nARRATION2.getCharacterStream();
			BufferedReader br = new BufferedReader(reader); 
			String line; 
			while(null != (line = br.readLine())) 
			{
				sb.append(line); } br.close(); 
			} 
		catch (SQLException e) { // handle this exception 
		}
		catch (IOException e) { // handle this exception 
		} 
		return sb.toString();
	}
	@Override
	public String toString() {
		return "ReconcileBillDeskBean [ID=" + ID + ", CUSTOMER_ID=" + CUSTOMER_ID + ", MERCHANT_CODE=" + MERCHANT_CODE
				+ ", MERCHANT_REF_NO=" + MERCHANT_REF_NO + ", BANK_REF_NO=" + BANK_REF_NO + ", TRAN_DATE=" + TRAN_DATE
				+ ", TRAN_AMOUNT=" + TRAN_AMOUNT + ", DEBIT_ACC_NO=" + DEBIT_ACC_NO + ", RECON_STATUS=" + RECON_STATUS
				+ ", REFUND_TILL_DATE=" + REFUND_TILL_DATE + ", PROCESSING_STATUS=" + PROCESSING_STATUS + ", JNO=" + JNO
				+ ", PAID_STATUS=" + PAID_STATUS + ", narration=" + NARRATION + ", BILL_TYPE=" + BILL_TYPE
				+ ", MOBILE_NO=" + MOBILE_NO + ", FROMDATE=" + FROMDATE + ", TODATE=" + TODATE + "]";
	}
	
	public ReconcileBillDeskBean(long iD, String cUSTOMER_ID, String mERCHANT_CODE, String mERCHANT_REF_NO,
			String bANK_REF_NO, String tRAN_DATE, String tRAN_AMOUNT, String dEBIT_ACC_NO, String rECON_STATUS,
			String rEFUND_TILL_DATE, String pROCESSING_STATUS, String jNO, String pAID_STATUS, String nARRATION,
			String bILL_TYPE, String mOBILE_NO) {
		super();
		ID = iD;
		CUSTOMER_ID = cUSTOMER_ID;
		MERCHANT_CODE = mERCHANT_CODE;
		MERCHANT_REF_NO = mERCHANT_REF_NO;
		BANK_REF_NO = bANK_REF_NO;
		TRAN_DATE = tRAN_DATE;
		TRAN_AMOUNT = tRAN_AMOUNT;
		DEBIT_ACC_NO = dEBIT_ACC_NO;
		RECON_STATUS = rECON_STATUS;
		REFUND_TILL_DATE = rEFUND_TILL_DATE;
		PROCESSING_STATUS = pROCESSING_STATUS;
		JNO = jNO;
		PAID_STATUS = pAID_STATUS;
		NARRATION = nARRATION;
		BILL_TYPE = bILL_TYPE;
		MOBILE_NO = mOBILE_NO;
	}
	public ReconcileBillDeskBean() {
		super();
	}
}
