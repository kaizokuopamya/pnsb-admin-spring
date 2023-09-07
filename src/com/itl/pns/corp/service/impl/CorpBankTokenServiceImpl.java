package com.itl.pns.corp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itl.pns.corp.dao.CorpBankTokenDao;
import com.itl.pns.corp.service.CorpBankTokenService;
import com.itl.pns.entity.BankTokenEntity;

@Service
public class CorpBankTokenServiceImpl implements CorpBankTokenService {

	@Autowired
	CorpBankTokenDao corpBankTokenDao;

	@Override
	public List<BankTokenEntity> getBankTokenRequestForCorp(BankTokenEntity bankTokenEntity) {
		return corpBankTokenDao.getBankTokenRequestForCorp(bankTokenEntity);
	}

	@Override
	public List<BankTokenEntity> getBankTokenByIdForCorp(int id) {
		return corpBankTokenDao.getBankTokenByIdForCorp(id);
	}

	@Override
	public boolean rejectBankTokenForCorp(BankTokenEntity bankToken) {
		return corpBankTokenDao.rejectBankTokenForCorp(bankToken);
	}
}
