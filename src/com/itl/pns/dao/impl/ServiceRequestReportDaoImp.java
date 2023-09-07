package com.itl.pns.dao.impl;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.itl.pns.dao.ServiceRequestReportDao;
import com.itl.pns.entity.ServiceRequestEntity;
import com.itl.pns.service.ServiceRequestService;

@Repository
@Transactional
public class ServiceRequestReportDaoImp implements ServiceRequestReportDao {

	static Logger LOGGER = Logger.getLogger(ServiceRequestReportDaoImp.class);

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	@Autowired
	ServiceRequestService serviceRequestService;

	protected Session getSession() {
		if (sessionFactory.getCurrentSession() != null)
			return sessionFactory.getCurrentSession();
		else
			return sessionFactory.openSession();
	}

	@Override
	public List<ServiceRequestEntity> getChannelDetailService() {
		String sqlQuery = "";
		List<ServiceRequestEntity> list = null;
		try {
			sqlQuery = "select am.id,am.SHORTNAME as shortName from APPMASTER am";
			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("shortName")
					.setResultTransformer(Transformers.aliasToBean(ServiceRequestEntity.class)).list();
		} catch (Exception e) {
			LOGGER.error("Exception:", e);
		}
		return list;
		
		
		
	}

	@Override
	public List<ServiceRequestEntity> getServiceRequestReportDetails(ServiceRequestEntity serviceRequestEntity) {
		String sqlQuery = "";
		List<ServiceRequestEntity> list = null;
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");  
//	    String strDate= formatter.format(date); 
		
		
		
		
		try {

	sqlQuery=" select to_char(cast(t.createdon as date),'DD-MM-YYYY') as createdon, t.statusid ,t.req_res, count(t.REQ_RES) as Count_REQ_RES from TRANSACTIONLOGS t inner join appmaster m  on t.appid = m.id  where t.createdon between TO_DATE('"+serviceRequestEntity.getFromdate()+ "','yyyy-mm-dd'" + ") and TO_DATE('"+serviceRequestEntity.getTodate() + "','yyyy-mm-dd'" +") and m.id=:id group by to_char(cast(t.createdon as date),'DD-MM-YYYY'),t.statusid, t.REQ_RES order by to_char(cast(t.createdon as date),'DD-MM-YYYY') asc";
   	list= getSession().createSQLQuery(sqlQuery).addScalar("Count_REQ_RES").addScalar("createdon").addScalar("statusid").addScalar("req_res").setParameter("id", serviceRequestEntity.getId()).setResultTransformer(Transformers.aliasToBean(ServiceRequestEntity.class)).list();
	System.out.println(list);
		
//		list= getSession().createSQLQuery(sqlQuery).addScalar("result").setParameter("id", serviceRequestEntity.getId()).setResultTransformer(Transformers.aliasToBean(ServiceRequestEntity.class)).list();
		System.out.println(list);	
		} catch (Exception e) {
			//LOGGER.error("Exception:", e);
			System.out.println(e);
		}
		return list;

	}

}
