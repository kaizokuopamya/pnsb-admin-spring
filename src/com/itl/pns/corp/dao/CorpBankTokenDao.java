package com.itl.pns.corp.dao;

import java.util.List;

import com.itl.pns.entity.BankTokenEntity;

public interface CorpBankTokenDao {

	List<BankTokenEntity> getBankTokenRequestForCorp(BankTokenEntity bankTokenEntity);

	List<BankTokenEntity> getBankTokenByIdForCorp(int id);

	public boolean rejectBankTokenForCorp(BankTokenEntity bankToken);
	
	List<BankTokenEntity> getBankTokenByIdForRetail(int id);

}
