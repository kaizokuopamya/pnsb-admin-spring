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

import com.itl.pns.corp.dao.PgRequestDao;
import com.itl.pns.corp.entity.PgRequestEntity;
import com.itl.pns.entity.MessageReport;


@Repository
@Transactional
public class PgRequestDaoImpl implements PgRequestDao {
	
	static Logger LOGGER = Logger.getLogger(PgRequestDaoImpl.class);
	
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;
	
	protected Session getSession() {
		if (sessionFactory.getCurrentSession() != null)
			return sessionFactory.getCurrentSession();
		else
			return sessionFactory.openSession();
	}

	@Override
	public List<PgRequestEntity> getMerchantDetails(PgRequestEntity pgRequestEntity) {
		String sqlQuery="";
		List<PgRequestEntity> list = null;
		try {
			sqlQuery = "select p.id as id, p.uuid as uuid, p.merchantname as merchantName, p.encdata as encData, p.createdon as createdOn, p.statusid as statusId from PGREQUEST p WHERE trunc(p.createdon) between TO_DATE('"+pgRequestEntity.getFromdate()+"','yyyy-mm-dd'" + ") and TO_DATE('"+pgRequestEntity.getTodate()+"','yyyy-mm-dd'" + ") and merchantname=:merchantName";
			list =  getSession().createSQLQuery(sqlQuery)
					.addScalar("id",StandardBasicTypes.BIG_DECIMAL).addScalar("uuid",StandardBasicTypes.STRING).addScalar("merchantName",StandardBasicTypes.STRING).addScalar("encData",StandardBasicTypes.STRING).addScalar("createdOn",StandardBasicTypes.DATE).addScalar("statusId",StandardBasicTypes.BIG_DECIMAL)
					.setParameter("merchantName",pgRequestEntity.getMerchantName()).setResultTransformer(Transformers.aliasToBean(PgRequestEntity.class)).list();
			
		}catch(Exception e) {
			LOGGER.info("Exception:",e);
		}
		return list;
	}
	
	
}
