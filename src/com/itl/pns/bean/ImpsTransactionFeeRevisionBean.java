package com.itl.pns.bean;

import java.math.BigInteger;

public class ImpsTransactionFeeRevisionBean {

    char deleted  ;      
    BigInteger	fee_setup ;
    
	public char getDeleted() {
		return deleted;
	}
	public void setDeleted(char deleted) {
		this.deleted = deleted;
	}
	public BigInteger getFee_setup() {
		return fee_setup;
	}
	public void setFee_setup(BigInteger fee_setup) {
		this.fee_setup = fee_setup;
	}
    
    
    		
}
