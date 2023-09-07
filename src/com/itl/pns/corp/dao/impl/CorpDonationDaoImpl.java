package com.itl.pns.corp.dao.impl;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.itl.pns.corp.dao.CorpDonationDao;
import com.itl.pns.entity.Donation;


@Repository("CorpDonationDAO")
@Transactional
public class CorpDonationDaoImpl implements CorpDonationDao {

	
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;
	  
	   private static final Logger logger = LogManager.getLogger(CorpDonationDaoImpl.class);
	
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Donation> getCorpDonations() {
		logger.info("in getDonations()");
		List<Donation> list=null;
		try{
		String sqlQuery=  "   select d.id,d.account_number as accountNumber,d.createdon ,d.name ,d.category as category,d.statusid as statusId,s.name as statusName,d.createdby as createdby,"
				+ " um.USERID as createdByName,d.bankingType from donation_prd d"
				 +"  inner join statusmaster s  on d.statusid=s.id inner join user_master um on d.createdby = um.id where d.bankingType='CORPORATE' order by d.createdon desc";
	
		 list = getSession().createSQLQuery(sqlQuery)
				    .addScalar("id")
					.addScalar("accountNumber")
					.addScalar("name")
					.addScalar("category")
					.addScalar("createdon")
					.addScalar("statusId")
					.addScalar("statusName")
					.addScalar("createdby")
					.addScalar("createdByName")
					.addScalar("bankingType")
					.setResultTransformer(Transformers.aliasToBean(Donation.class)).list();
		}
		catch(Exception e){
			logger.info("Exception:", e);
		}
		return list;
	
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Donation> getCorpDonationById(int id) {
		logger.info("in getDonations()");
		List<Donation> list=null;
		try{
		String sqlQuery=  " select d.id,d.account_number as accountNumber ,d.name ,d.category as category,d.createdon,d.statusid as statusId,d.bankingType, s.name as statusName, um.USERID as createdByName ,aw.remark,aw.userAction "
		          		  +"  from donation_prd d "
	          			  +"  inner join statusmaster s  on d.statusid=s.id "
		          		  +"  inner join user_master um on d.createdby = um.id left join ADMINWORKFLOWREQUEST aw on aw.activityrefno=:id and aw.tablename='DONATION_PRD' "
		                  +"  where d.id=:id";
					
		 list = getSession().createSQLQuery(sqlQuery)
				    .addScalar("id")
					.addScalar("accountNumber")
					.addScalar("name")
					.addScalar("category")
					.addScalar("createdon")
					.addScalar("statusId")
					.addScalar("createdByName")
					.addScalar("remark")
					.addScalar("userAction")
					.addScalar("bankingType")
					.addScalar("statusName").setParameter("id", id)
					.setResultTransformer(Transformers.aliasToBean(Donation.class)).list();
		}
		catch(Exception e){
			logger.info("Exception:", e);
		}
		return list;
	
	}

}

