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

import com.itl.pns.dao.ActivityLogReportDao;
import com.itl.pns.entity.ActivityLogReport;
import com.itl.pns.service.ActivityLogReportService;

@Repository
@Transactional
public class ActivityLogReportDaoImpl implements ActivityLogReportDao {

	static Logger LOGGER = Logger.getLogger(MessageReportDaoImpl.class);

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	@Autowired
	ActivityLogReportService activityLogReportService;

	protected Session getSession() {
		if (sessionFactory.getCurrentSession() != null)
			return sessionFactory.getCurrentSession();
		else
			return sessionFactory.openSession();
	}

	@Override
	public List<ActivityLogReport> getActivityLogReport(ActivityLogReport activityLogReport) {
		String sqlQuery = "";
		List<ActivityLogReport> list = null;
		try {
			sqlQuery = "select au.eventname as eventName,au.category as category,au.action as action,au.createdbyname as createdByName,au.updatedbyname as updatedByName,au.createdon as createdOn,au.updatedon as updatedOn from ADMINPORTAL_USER_ACTIVITY_LOGS au WHERE au.createdon BETWEEN TO_DATE('"+activityLogReport.getFromdate() + "','yyyy-mm-dd'" + ") AND TO_DATE('"+activityLogReport.getTodate() + "','yyyy-mm-dd'" + ")+1 order by updatedOn desc";
			list = getSession().createSQLQuery(sqlQuery).addScalar("eventName").addScalar("category")
					.addScalar("action").addScalar("createdByName").addScalar("updatedByName").addScalar("createdOn")
					.addScalar("updatedOn").setResultTransformer(Transformers.aliasToBean(ActivityLogReport.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return list;
	}

}
