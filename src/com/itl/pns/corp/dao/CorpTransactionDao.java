package com.itl.pns.corp.dao;

import com.itl.pns.bean.DateBean;
import com.itl.pns.corp.entity.CorpTransactionsEntity;
import com.itl.pns.corp.entity.CorporateTransactionLogs;
import java.util.List;

public interface CorpTransactionDao {
  List<CorpTransactionsEntity> getCorporateTransactions();
  
  List<CorporateTransactionLogs> getCorpTransactionLogs();
  
  List<CorporateTransactionLogs> getCorpTransLogsByDate(DateBean paramDateBean);
}
