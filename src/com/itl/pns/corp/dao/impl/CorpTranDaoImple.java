package com.itl.pns.corp.dao.impl;

import com.itl.pns.bean.DateBean;
import com.itl.pns.corp.dao.CorpTransactionDao;
import com.itl.pns.corp.entity.CorpTransactionsEntity;
import com.itl.pns.corp.entity.CorporateTransactionLogs;
import com.itl.pns.dao.impl.TransactionDaoImpl;
import java.util.List;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class CorpTranDaoImple implements CorpTransactionDao {
  private static final Logger logger = LogManager.getLogger(TransactionDaoImpl.class);
  
  @Autowired
  @Qualifier("sessionFactory")
  private SessionFactory sessionFactory;
  
  protected Session getSession() {
    return this.sessionFactory.getCurrentSession();
  }
  
  public List<CorpTransactionsEntity> getCorporateTransactions() {
    String sqlQuery = "";
    List<CorpTransactionsEntity> list = null;
    try {
      sqlQuery = "select ct.ID as id, ct.CORP_COMP_ID as corp_comp_id, ct.AMOUNT as amount,ct.REMARKS as remarks, ct.PAYMENT_TYPE as payment_type, ct.CREATEDBY as createdBy,ct.CREATEDON as createdOn, ct.STATUSID as statusId, ct.APPID as appId, ct.TO_BENEFICIARY_ACCOUNT as to_beneficary_account, ct.TRNREFNO as trnRefNo, ct.FROM_ACCOUNT as from_account,s.name as statusName, a.shortname as appName ,ct.CREDITCURRENCY as creditCurrency, ct.DEBITCURRENCY as debitCurrency,ct.UPDATEDBY as updatedBy, ct.UPDATEON as updateOn  from CORP_TRANSACTIONS ct  inner join statusMaster s on s.id =ct.STATUSID inner join appmaster a on a.id=ct.appId ORDER  BY ct.CREATEDON DESC";
      list = getSession().createSQLQuery(sqlQuery).addScalar("id", (Type)StandardBasicTypes.BIG_INTEGER).addScalar("corp_comp_id", (Type)StandardBasicTypes.BIG_INTEGER).addScalar("amount", (Type)StandardBasicTypes.INTEGER).addScalar("remarks", (Type)StandardBasicTypes.STRING).addScalar("payment_type", (Type)StandardBasicTypes.STRING).addScalar("createdBy", (Type)StandardBasicTypes.INTEGER).addScalar("createdOn", (Type)StandardBasicTypes.DATE).addScalar("statusId", (Type)StandardBasicTypes.BIG_INTEGER).addScalar("appId", (Type)StandardBasicTypes.BIG_INTEGER).addScalar("to_beneficary_account", (Type)StandardBasicTypes.STRING).addScalar("trnRefNo", (Type)StandardBasicTypes.STRING).addScalar("from_account", (Type)StandardBasicTypes.STRING).addScalar("statusName", (Type)StandardBasicTypes.STRING).addScalar("appName", (Type)StandardBasicTypes.STRING).addScalar("creditCurrency", (Type)StandardBasicTypes.STRING).addScalar("debitCurrency", (Type)StandardBasicTypes.STRING).addScalar("updatedBy", (Type)StandardBasicTypes.BIG_INTEGER).addScalar("updateOn", (Type)StandardBasicTypes.DATE).setResultTransformer(Transformers.aliasToBean(CorpTransactionsEntity.class)).list();
    } catch (Exception e) {
      logger.error("Exception Occured", e);
    } 
    return list;
  }
  
  public List<CorporateTransactionLogs> getCorpTransactionLogs() {
    String sqlQuery = "";
    List<CorporateTransactionLogs> list = null;
    try {
      sqlQuery = "select ct.ID as id, ct.ACTIVITYID as activityId,ct.RRN as rrn, ct.REQ_RES as req_res, ct.MESSAGE1 as message1, ct.MESSAGE2 as message2,ct.AMOUNT as amount, ct.USERDEVICEID as userDeviceId, ct.SERVICEREFNO as serviceRefNo, ct.CUSTOMERID as customerId, ct.CREATEDBY as createdBy, ct.CREATEDON as createdOn,ct.STATUSID as statusId, ct.APPID as appId ,ct.THIRDPARTYREFNO as thirdPartyRefNo, ct.MESSAGE3 as message3,ct.SESSION_ID as session_id  from CORPTRANSACTIONLOGS ct  inner join statusMaster s on s.id =ct.STATUSID inner join appmaster a on a.id=ct.appId ORDER  BY ct.CREATEDON DESC";
      list = getSession().createSQLQuery(sqlQuery).addScalar("id", (Type)StandardBasicTypes.BIG_INTEGER).addScalar("activityId", (Type)StandardBasicTypes.BIG_INTEGER).addScalar("rrn", (Type)StandardBasicTypes.STRING).addScalar("req_res", (Type)StandardBasicTypes.STRING).addScalar("message1", (Type)StandardBasicTypes.STRING).addScalar("message2", (Type)StandardBasicTypes.STRING).addScalar("amount", (Type)StandardBasicTypes.INTEGER).addScalar("userDeviceId", (Type)StandardBasicTypes.BIG_INTEGER).addScalar("serviceRefNo", (Type)StandardBasicTypes.STRING).addScalar("customerId", (Type)StandardBasicTypes.LONG).addScalar("createdBy", (Type)StandardBasicTypes.INTEGER).addScalar("createdOn", (Type)StandardBasicTypes.DATE).addScalar("statusId", (Type)StandardBasicTypes.BIG_INTEGER).addScalar("appId", (Type)StandardBasicTypes.BIG_INTEGER).addScalar("thirdPartyRefNo", (Type)StandardBasicTypes.STRING).addScalar("message3", (Type)StandardBasicTypes.STRING).addScalar("session_id", (Type)StandardBasicTypes.STRING).setResultTransformer(Transformers.aliasToBean(CorporateTransactionLogs.class)).list();
    } catch (Exception e) {
      e.printStackTrace();
      logger.info(e);
    } 
    return list;
  }
  
  public List<CorporateTransactionLogs> getCorpTransLogsByDate(DateBean datebean) {
    String sqlQuery = "";
    List<CorporateTransactionLogs> list = null;
    try {
      sqlQuery = "select ct.ID as id, ct.ACTIVITYID as activityId,ct.RRN as rrn, ct.REQ_RES as req_res, ct.MESSAGE1 as message1, ct.MESSAGE2 as message2,ct.AMOUNT as amount, ct.USERDEVICEID as userDeviceId, ct.SERVICEREFNO as serviceRefNo, ct.CUSTOMERID as customerId, ct.CREATEDBY as createdBy, ct.CREATEDON as createdOn,ct.STATUSID as statusId, ct.APPID as appId ,ct.THIRDPARTYREFNO as thirdPartyRefNo, ct.MESSAGE3 as message3,ct.SESSION_ID as session_id  from CORPTRANSACTIONLOGS ct  inner join statusMaster s on s.id =ct.STATUSID inner join appmaster a on a.id=ct.appId where (ct.CREATEDON)  BETWEEN TO_DATE('" + datebean.getFromdate() + "','yyyy-mm-dd')\t AND  TO_DATE('" + datebean.getTodate() + "','yyyy-mm-dd')+1 ORDER  BY ct.CREATEDON DESC";
      list = getSession().createSQLQuery(sqlQuery).addScalar("id", (Type)StandardBasicTypes.BIG_INTEGER).addScalar("activityId", (Type)StandardBasicTypes.BIG_INTEGER).addScalar("rrn", (Type)StandardBasicTypes.STRING).addScalar("req_res", (Type)StandardBasicTypes.STRING).addScalar("message1", (Type)StandardBasicTypes.STRING).addScalar("message2", (Type)StandardBasicTypes.STRING).addScalar("amount", (Type)StandardBasicTypes.INTEGER).addScalar("userDeviceId", (Type)StandardBasicTypes.BIG_INTEGER).addScalar("serviceRefNo", (Type)StandardBasicTypes.STRING).addScalar("customerId", (Type)StandardBasicTypes.LONG).addScalar("createdBy", (Type)StandardBasicTypes.INTEGER).addScalar("createdOn", (Type)StandardBasicTypes.DATE).addScalar("statusId", (Type)StandardBasicTypes.BIG_INTEGER).addScalar("appId", (Type)StandardBasicTypes.BIG_INTEGER).addScalar("thirdPartyRefNo", (Type)StandardBasicTypes.STRING).addScalar("message3", (Type)StandardBasicTypes.STRING).addScalar("session_id", (Type)StandardBasicTypes.STRING).setResultTransformer(Transformers.aliasToBean(CorporateTransactionLogs.class)).list();
    } catch (Exception e) {
      e.printStackTrace();
      logger.info(e);
    } 
    return list;
  }
}

