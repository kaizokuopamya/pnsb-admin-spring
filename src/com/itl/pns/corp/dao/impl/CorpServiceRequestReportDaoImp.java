package com.itl.pns.corp.dao.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.itl.pns.corp.dao.CorpServiceRequestReportDao;
import com.itl.pns.corp.entity.CorpServiceRequestEntity;
import com.itl.pns.corp.service.CorpServiceRequestService;
import com.itl.pns.util.AdminWorkFlowReqUtility;

@Repository
@Transactional
public class CorpServiceRequestReportDaoImp implements CorpServiceRequestReportDao {

	static Logger logger = Logger.getLogger(CorpServiceRequestReportDaoImp.class);

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	@Autowired
	private AdminWorkFlowReqUtility adminWorkFlowReqUtility;

	@Autowired
	private CorpServiceRequestService CorpserviceRequestService;

	protected Session getSession() {
		if (sessionFactory.getCurrentSession() != null)
			return sessionFactory.getCurrentSession();
		else
			return sessionFactory.openSession();
	}

	@Override
	public List<CorpServiceRequestEntity> getChannelDetailsService() {
		String sqlQuery = " ";
		List<CorpServiceRequestEntity> list = null;
		try {
			sqlQuery = "select am.id, am.shortname as shortName from APPMASTER am";
			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("shortName")
					.setResultTransformer(Transformers.aliasToBean(CorpServiceRequestEntity.class)).list();
			System.out.println(list);
		} catch (Exception e) {
			logger.error("Exception Occured", e);
		}
		return list;
	}

//	@Override
//	public List<CorpServiceRequestEntity> gettypeDetails( ) {
//		String sqlQuery=" ";
//		List<CorpServiceRequestEntity> list = null;
//		try {
//			 sqlQuery = "SELECT req_res as reqres FROM transactionlogs GROUP BY req_res";
//			list = getSession().createSQLQuery(sqlQuery).addScalar("reqres").setResultTransformer(Transformers.aliasToBean(CorpServiceRequestEntity.class)).list();
//			System.out.println(list);
//		} catch (Exception e) {
//			logger.error("Exception Occured", e);
//		}
//		return list;
//	}
//	

	@Override
	public List<CorpServiceRequestEntity> getServiceRequestReportDetail(
			CorpServiceRequestEntity corpserviceRequestEntity) {
		String sqlQuery = "";
		List<CorpServiceRequestEntity> list = null;
		try {
			sqlQuery = " select to_char(cast(t.createdon as date),'DD-MM-YYYY') as createdon, t.statusid ,t.req_res, count(t.REQ_RES) as Count_REQ_RES from corptransactionlogs t inner join appmaster m  on t.appid = m.id  where t.createdon between TO_DATE('"
					+ corpserviceRequestEntity.getFromdate() + "','yyyy-mm-dd'" + ") and TO_DATE('"
					+ corpserviceRequestEntity.getTodate() + "','yyyy-mm-dd'"
					+ ") and m.id=:id group by to_char(cast(t.createdon as date),'DD-MM-YYYY'),t.statusid, t.REQ_RES order by to_char(cast(t.createdon as date),'DD-MM-YYYY') asc";
			list = getSession().createSQLQuery(sqlQuery).addScalar("Count_REQ_RES").addScalar("createdon")
					.setParameter("id", corpserviceRequestEntity.getId())
					.setResultTransformer(Transformers.aliasToBean(CorpServiceRequestEntity.class)).list();
			System.out.println(list);

		} catch (Exception e) {
			logger.error("Exception Occured", e);
		}
		return list;

	}

	@Override
	public List<CorpServiceRequestEntity> getChannelDetailsActiveService() {
		String sqlQuery = " ";
		List<CorpServiceRequestEntity> list = null;
		try {
			sqlQuery = "select am.id, am.shortname as shortName from APPMASTER am where am.statusid=3";
			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("shortName")
					.setResultTransformer(Transformers.aliasToBean(CorpServiceRequestEntity.class)).list();
			System.out.println(list);
		} catch (Exception e) {
			logger.error("Exception Occured", e);
		}
		return list;
	}

}