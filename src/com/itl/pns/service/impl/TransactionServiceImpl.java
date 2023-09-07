package com.itl.pns.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itl.pns.bean.DateBean;
import com.itl.pns.bean.TransactionBean;
import com.itl.pns.corp.entity.CorpTransactionsEntity;
import com.itl.pns.dao.TransactionDao;
import com.itl.pns.service.TransactionService;

@Service
public class TransactionServiceImpl  implements TransactionService{
	
	
	@Autowired
	private TransactionDao transactiondaoImpl;

	@Override
	public List<TransactionBean> getTransactions(DateBean datebean) {
		  return transactiondaoImpl.getTransactions(datebean);
	}

	@Override
	public List<TransactionBean> getTransactionsType() {
		  return transactiondaoImpl.getTransactionsType();
		
	}

	@Override
	public List<CorpTransactionsEntity> getCorpTransactions(DateBean datebean) {
		 return transactiondaoImpl.getCorpTransactions(datebean);
	}
	
	
	@Override
	public List<TransactionBean> getTransactionsDetails(DateBean datebean) {
		  return transactiondaoImpl.getTransactionsDetails(datebean);
	}

}
