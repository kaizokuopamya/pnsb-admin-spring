package com.itl.pns.dao;

import java.util.List;

import com.itl.pns.bean.DateBean;
import com.itl.pns.bean.TransactionBean;
import com.itl.pns.corp.entity.CorpTransactionsEntity;

public interface TransactionDao {

	List<TransactionBean> getTransactions(DateBean datebean);
	
	List<CorpTransactionsEntity> getCorpTransactions(DateBean datebean);
	
	public List<TransactionBean> getTransactionsType(); 
	
	List<TransactionBean> getTransactionsDetails(DateBean datebean);


}
