package com.itl.pns.corp.service.impl;

import com.itl.pns.bean.DateBean;
import com.itl.pns.corp.dao.CorpTransactionDao;
import com.itl.pns.corp.entity.CorpTransactionsEntity;
import com.itl.pns.corp.entity.CorporateTransactionLogs;
import com.itl.pns.corp.service.CorpTransactionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CorpTranServiceImpl implements CorpTransactionService {
  @Autowired
  private CorpTransactionDao corptrandao;
  
  public List<CorpTransactionsEntity> getCorporateTransactions() {
    return this.corptrandao.getCorporateTransactions();
  }
  
  public List<CorporateTransactionLogs> getCorpTransactionLogs() {
    return this.corptrandao.getCorpTransactionLogs();
  }
  
  public List<CorporateTransactionLogs> getCorpTransLogsByDate(DateBean datebean) {
    return this.corptrandao.getCorpTransLogsByDate(datebean);
  }
}
