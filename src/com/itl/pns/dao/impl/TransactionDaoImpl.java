package com.itl.pns.dao.impl;

import java.io.IOException;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.stereotype.Service;

import com.itl.pns.bean.DateBean;
import com.itl.pns.bean.TransactionBean;
import com.itl.pns.corp.entity.CorpTransactionsEntity;
import com.itl.pns.dao.TransactionDao;

import com.itl.pns.util.EncryptDeryptUtility;
import com.itl.pns.util.EncryptorDecryptor;

@Service
@Transactional
public class TransactionDaoImpl implements TransactionDao {

	private static final Logger logger = LogManager.getLogger(TransactionDaoImpl.class);

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public List<TransactionBean> getTransactions(DateBean datebean) {
		String sqlQuery = "";
		List<TransactionBean> list = null;
		try {

			sqlQuery = "SELECT C.CUSTOMERNAME , A.* FROM (SELECT  T.*,A.SHORTNAME AS APPNAME,S.name AS STATUS,AT.ACTIVITYCODE,C.CUSTOMERNAME AS SENDERCUST,C.MOBILE "
					+ "FROM TRANSACTIONS T ,APPMASTER A ,ACTIVITYMASTER AT,CUSTOMERS C,STATUSMASTER S  WHERE A.ID=T.APPID AND AT.ID=T.ACTIVITYID "
					+ "AND C.ID= T.SENTCIF AND S.ID=T.STATUSID)A,CUSTOMERS C  WHERE A.RECCIF = C.ID AND (A.CREATEDON)  BETWEEN  TO_DATE('"
					+ datebean.getFromdate() + "','yyyy-mm-dd'" + ") AND TO_DATE('" + datebean.getTodate()
					+ "','yyyy-mm-dd'" + ")+1 ORDER  BY A.CREATEDON DESC";
			list = getSession().createSQLQuery(sqlQuery)
					.setResultTransformer(Transformers.aliasToBean(TransactionBean.class)).list();

			for (TransactionBean cm : list) {
				try {

					if (null != cm.getMOBILE() && cm.getMOBILE().contains("=")) {
						cm.setMOBILE(EncryptorDecryptor.decryptData(cm.getMOBILE()));
					}

					String image1 = EncryptDeryptUtility.clobStringConversion(cm.getREMARK());
					cm.setRemarkString(image1);
					cm.setREMARK(null);

				} catch (IOException e) {
					logger.error("Exception Occured ", e);

				} catch (Exception e) {
					logger.error("Exception Occured ", e);
				}
			}

		} catch (Exception e) {

			logger.info("Exception:", e);
		}

		return list;
	}

	@Override
	public List<TransactionBean> getTransactionsType() {
		List<TransactionBean> list = null;
		try {

		} catch (Exception e) {
			logger.error("Exception Occured", e);
		}
		return list;
	}

	@Override
	public List<CorpTransactionsEntity> getCorpTransactions(DateBean datebean) {
		String sqlQuery = "";
		List<CorpTransactionsEntity> list = null;
		try {

			sqlQuery = "select ct.id, ct.CORP_COMP_ID as corp_comp_id, ct.AMOUNT as amount,ct.REMARKS as remarks, "
					+ "ct.PAYMENT_TYPE as payment_type, ct.CREATEDBY as createdBy,ct.CREATEDON as createdOn,"
					+ " ct.STATUSID as statusId, ct.APPID as appId, ct.TO_BENEFICIARY_ACCOUNT as to_beneficary_account,"
					+ " ct.TRNREFNO as trnRefNo, ct.FROM_ACCOUNT as from_account,s.name as statusName, a.shortname as appName ,ct.CREDITCURRENCY as creditCurrency,"
					+ " ct.DEBITCURRENCY as debitCurrency,ct.UPDATEDBY as updatedBy, ct.UPDATEON as updateOn "
					+ " from CORP_TRANSACTIONS ct  inner join statusMaster s on s.id =ct.STATUSID inner join appmaster a on a.id=ct.appid "
					+ " where (ct.CREATEDON)  BETWEEN TO_DATE('" + datebean.getFromdate() + "','yyyy-mm-dd'" + ")"
					+ "	 AND  TO_DATE('" + datebean.getTodate() + "','yyyy-mm-dd'" + ")+1 ORDER  BY ct.CREATEDON DESC";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id", StandardBasicTypes.BIG_INTEGER)
					.addScalar("corp_comp_id", StandardBasicTypes.BIG_INTEGER)
					.addScalar("amount", StandardBasicTypes.INTEGER).addScalar("remarks", StandardBasicTypes.STRING)
					.addScalar("payment_type", StandardBasicTypes.STRING)
					.addScalar("createdBy", StandardBasicTypes.INTEGER).addScalar("createdOn", StandardBasicTypes.DATE)
					.addScalar("statusId", StandardBasicTypes.BIG_INTEGER)
					.addScalar("appId", StandardBasicTypes.BIG_INTEGER)
					.addScalar("to_beneficary_account", StandardBasicTypes.STRING)
					.addScalar("trnRefNo", StandardBasicTypes.STRING)
					.addScalar("from_account", StandardBasicTypes.STRING)
					.addScalar("statusName", StandardBasicTypes.STRING).addScalar("appName", StandardBasicTypes.STRING)
					.addScalar("creditCurrency", StandardBasicTypes.STRING)
					.addScalar("debitCurrency", StandardBasicTypes.STRING)
					.addScalar("updatedBy", StandardBasicTypes.BIG_INTEGER)
					.addScalar("updateOn", StandardBasicTypes.DATE)
					.setResultTransformer(Transformers.aliasToBean(CorpTransactionsEntity.class)).list();
		} catch (Exception e) {
			logger.error("Exception Occured", e);
		}
		return list;
	}

	@Override
	public List<TransactionBean> getTransactionsDetails(DateBean datebean) {
		String sqlQuery = "";
		List<TransactionBean> list = null;
		try {

			sqlQuery = "select s.CUSTOMERNAME ,s.CIF ,t.BILLER,t.CREATEDON,t.TXN_AMOUNT"
					+ " from transactions t inner join customers s on t.SENTCIF=s.id where t.STATUSID=3 AND t.DATE_OF_TXN BETWEEN  TO_DATE('"
					+ datebean.getFromdate() + "','yyyy-mm-dd'" + ") AND TO_DATE('" + datebean.getTodate()
					+ "','yyyy-mm-dd'" + ") +1 ORDER  BY t.CREATEDON DESC";

			list = getSession().createSQLQuery(sqlQuery).addScalar("CUSTOMERNAME", StandardBasicTypes.STRING)
					.addScalar("CIF", StandardBasicTypes.STRING).addScalar("BILLER", StandardBasicTypes.STRING)
					.addScalar("CREATEDON", StandardBasicTypes.DATE)
					.addScalar("TXN_AMOUNT", StandardBasicTypes.BIG_DECIMAL)

					.setResultTransformer(Transformers.aliasToBean(TransactionBean.class)).list();

		} catch (Exception e) {

			e.printStackTrace();
			logger.info("Exception:", e);
		}

		return list;
	}

}
