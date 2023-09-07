package com.itl.pns.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.itl.pns.service.MessageReportService;
import com.itl.pns.dao.MessageReportDao;
import com.itl.pns.entity.MessageReport;

@Repository
@Transactional
public class MessageReportDaoImpl implements MessageReportDao {
	
	static Logger LOGGER = Logger.getLogger(MessageReportDaoImpl.class);
	
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;
	
	@Autowired
	MessageReportService messageReportService;
	
	protected Session getSession() {
		if (sessionFactory.getCurrentSession() != null)
			return sessionFactory.getCurrentSession();
		else
			return sessionFactory.openSession();
	}
	
	@Override
	public List<MessageReport> getChannelDetails() {
		String sqlQuery = "";
		List<MessageReport> list = null;
		try {
			sqlQuery = "select am.id,am.SHORTNAME as shortName from APPMASTER am where am.statusId=3";
			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("shortName").setResultTransformer(Transformers.aliasToBean(MessageReport.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return list;
	}

	@Override
	public List<MessageReport> getMessageReportDetails(MessageReport messageReport){
		String sqlQuery="";
		List<MessageReport> list = null;
		try {
			sqlQuery = "select t.statusid, count(REQ_RES) as message from TRANSACTIONLOGS t inner join APPMASTER am on t.appid=am.id WHERE t.createdon between TO_DATE('"+messageReport.getFromdate()+"','yyyy-mm-dd'" + ") and TO_DATE('"+messageReport.getTodate()+"','yyyy-mm-dd'" + ") and am.id=:id  GROUP BY t.statusid";
			list =  getSession().createSQLQuery(sqlQuery).addScalar("message").setParameter("id",messageReport.getId()).setResultTransformer(Transformers.aliasToBean(MessageReport.class)).list();
			
		}catch(Exception e) {
			LOGGER.info("Exception:",e);
		}
		return list;
		
	}

}
