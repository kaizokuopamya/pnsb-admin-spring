package com.itl.pns.corp.service;

import java.util.List;

import com.itl.pns.entity.BankTokenEntity;

public interface CorpBankTokenService {

	// public boolean generateTokenRequest(TicketBean ticketBean);

	public List<BankTokenEntity> getBankTokenRequestForCorp(BankTokenEntity bankTokenEntity);

	public List<BankTokenEntity> getBankTokenByIdForCorp(int id);

	public boolean rejectBankTokenForCorp(BankTokenEntity bankToken);

}
