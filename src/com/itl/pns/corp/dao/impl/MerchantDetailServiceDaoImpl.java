package com.itl.pns.corp.dao.impl;

import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.itl.pns.bean.MerchantDetailsBean;
import com.itl.pns.corp.dao.MerchantDetailServiceDao;

import com.itl.pns.corp.service.MerchantDetailService;


@Repository
@Transactional
public class MerchantDetailServiceDaoImpl implements MerchantDetailServiceDao {
	
	static Logger LOGGER = Logger.getLogger(MerchantDetailServiceDaoImpl.class);
	
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;
	
	@Autowired
	MerchantDetailService merchantDetailService;

	protected Session getSession() {
		if (sessionFactory.getCurrentSession() != null)
			return sessionFactory.getCurrentSession();
		else
			return sessionFactory.openSession();
	}

	@Override
	public List<MerchantDetailsBean> getMerchantDetail(MerchantDetailsBean merchantDetailsBean) {
		String sqlQuery = "";
		List<MerchantDetailsBean> list = null;
		try {
			sqlQuery = sqlQuery = " select m.id as id,m.merchantcode as merchantCode,m.merchantname as merchantName,"
					+ "m.merchantkey as merchantKey,m.merchantaccountno as merchantAccountNo,m.statusid as statusId,"
					+ "m.createdon as createdOn,m.createdby as createdBy,m.updatedon as updatedOn,m.updatedby as updatedBy,"
					+ "m.glsubhead as glSubHead,m.checksumkey as checksumKey from MERCHANT_DETAILS m where merchantname=:merchantName"
					+ " and trunc(CREATEDON) between TO_DATE('" + merchantDetailsBean.getFromdate() + "','yyyy-mm-dd'"
					+ ") and TO_DATE('" + merchantDetailsBean.getTodate() + "','yyyy-mm-dd'" + ")";
			list = getSession().createSQLQuery(sqlQuery).addScalar("id", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("merchantCode", StandardBasicTypes.STRING)
					.addScalar("merchantName", StandardBasicTypes.STRING)
					.addScalar("merchantKey", StandardBasicTypes.STRING)
					.addScalar("merchantAccountNo", StandardBasicTypes.STRING)
					.addScalar("statusId", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("createdOn", StandardBasicTypes.DATE)
					.addScalar("createdBy", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("updatedOn", StandardBasicTypes.DATE)
					.addScalar("updatedBy", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("glSubHead", StandardBasicTypes.STRING)
					.addScalar("checksumKey", StandardBasicTypes.STRING)
					.setParameter("merchantName", merchantDetailsBean.getMerchantName())
					.setResultTransformer(Transformers.aliasToBean(MerchantDetailsBean.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return list;
	}
}
