package com.itl.pns.dao.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.sql.rowset.serial.SerialException;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.dao.SurveyDao;
import com.itl.pns.entity.CustomerSurveyEntity;
import com.itl.pns.entity.SurveyAnsMasterEntity;
import com.itl.pns.entity.SurveyMasterEntity;
import com.itl.pns.entity.SurveyQueMasterEntity;
import com.itl.pns.util.AdminWorkFlowReqUtility;
import com.itl.pns.util.EmailUtil;

@Repository
@Transactional
public class SurveyDaoImpl implements SurveyDao{

	static Logger LOGGER = Logger.getLogger(SurveyDaoImpl.class);
	  
	@Autowired
	EmailUtil util;

	
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	@Autowired
	AdminWorkFlowReqUtility adminWorkFlowReqUtility;
	
	protected Session getSession() {
		if (sessionFactory.getCurrentSession() != null)
			return sessionFactory.getCurrentSession();
		else
			return sessionFactory.openSession();
	}
	
	
	@Override
	public List<SurveyMasterEntity> getActiveSurveyDetails() {
		List<SurveyMasterEntity> list = null;
		try {
			String sqlQuery = "select sm.id,CAST(sm.surveyName AS VARCHAR(1000)) AS surveyName , sm.surveyStart as surveyStart, sm.surveyEnd as surveyEnd, sm.createdby as createdby,"
					+ "sm.createdon as createdon, sm.statusId as statusId,s.name as statusname, um.USERID as createdByName from SURVEYMASTER_PRD sm "
					+ " inner join statusmaster s on s.id= sm.STATUSID inner join user_master um on sm.createdby = um.id order by sm.id desc";

			list = getSession().createSQLQuery(sqlQuery)
					.addScalar("id")
					.addScalar("surveyName")
					.addScalar("surveyStart")
					.addScalar("surveyEnd")
					.addScalar("createdby")
					.addScalar("createdon")
					.addScalar("statusId")
					.addScalar("statusname")
					.addScalar("createdByName")
					.setResultTransformer(Transformers.aliasToBean(SurveyMasterEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}
	@Override
	public List<SurveyQueMasterEntity> getSurveyQue() {
		List<SurveyQueMasterEntity> list = null;
		try {
			String sqlQuery = "select sq.id , sq.surveyId as surveyId, sq.surveyQue as surveyQue,sq.createdby as createdby,sq.createdon as createdon, "
					+ "sq.statusId as statusId,s.name as statusname from SURVEYQUEMASTER_PRD sq inner join statusmaster s on s.id= sq.STATUSID order by sq.id desc";

			list = getSession().createSQLQuery(sqlQuery)
					.addScalar("id")
					.addScalar("surveyId")
					.addScalar("surveyQue")
					.addScalar("createdby")
					.addScalar("createdon")
					.addScalar("statusId")
					.addScalar("statusname")
					.setResultTransformer(Transformers.aliasToBean(SurveyQueMasterEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}
	@Override
	public List<SurveyAnsMasterEntity> getSurveyAns() {
		List<SurveyAnsMasterEntity> list = null;
		try {
			String sqlQuery = "select sa.id, sa.surveyId as surveyId, sa.surveyQueId as surveyQueId, sa.surveyAns1 as surveyAns1, sa.surveyAns2 as surveyAns2, "
					+ "sa.surveyAns3 as surveyAns3, sa.surveyAns4 as surveyAns4,sa.createdby as createdby,sa.createdon as createdon,"
					+ "sa.statusId as statusId,s.name as statusname from SURVEYANSMASTER_PRD sa inner join statusmaster s on s.id= sa.STATUSID order by sa.id DESC";

			list = getSession().createSQLQuery(sqlQuery)
					.addScalar("id")
					.addScalar("surveyId")
					.addScalar("surveyQueId")
					.addScalar("surveyAns1")
					.addScalar("surveyAns2")
					.addScalar("surveyAns3")
					.addScalar("surveyAns4")
					.addScalar("createdby")
					.addScalar("createdon")
					.addScalar("statusId")
					.addScalar("statusname")
					.setResultTransformer(Transformers.aliasToBean(SurveyAnsMasterEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}
	
	@Override
	public boolean saveCustAnsOfSurvey(CustomerSurveyEntity custAnsData) {
		Session session = sessionFactory.getCurrentSession();
		try{
		custAnsData.setCreatedon(new Date());
		session.save(custAnsData);
		return true;
		}catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}
	
	@Override
	public List<CustomerSurveyEntity> getCustSurveyDetails() {
		List<CustomerSurveyEntity> list = null;
		try {
			String sqlQuery = "select cs.id,cs.customerId as customerId,cs.RRN as RRN, cs.surveyId as surveyId,cs.surveyAnsId as surveyAnsId,cs.surveyQue "
					+ "as surveyQue,cs.surveyFeedback as surveyFeedback,"
					+ "cs.surveyRatings as surveyRatings,cs.surveyStart as surveyStart,cs.surveyEnd as surveyEnd,cs.createdby as createdby,"
					+ "cs.createdon as createdon,cs.statusId as statusId from CUSTSURVEY cs ";
			list = getSession().createSQLQuery(sqlQuery)
					.addScalar("id")
					.addScalar("customerId")
					.addScalar("RRN")
					.addScalar("surveyId")
					.addScalar("surveyAnsId")
					.addScalar("surveyQue")
					.addScalar("surveyFeedback")
					.addScalar("surveyRatings")
					.addScalar("surveyStart")
					.addScalar("surveyEnd")
					.addScalar("createdby")
					.addScalar("createdon")
					.addScalar("statusId")
					.setResultTransformer(Transformers.aliasToBean(CustomerSurveyEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}
	
	
	@Override
	public boolean addSurveMasterDetails(List<SurveyMasterEntity> surveyMasterData) {
		Session session = sessionFactory.getCurrentSession();
		try{
			for (SurveyMasterEntity surveyMasterDetails : surveyMasterData) {
				
				surveyMasterDetails.setCreatedon(new Date());
				session.save(surveyMasterDetails);

			}
			
						return true;
		}catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}
	@Override
	public boolean addSurveyQue(SurveyQueMasterEntity surveyQue) {
		Session session = sessionFactory.getCurrentSession();
		
		try {
			surveyQue.setCreatedon(new Date());
			session.save(surveyQue);
			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}
	
	
	@Override
	public boolean addSurveyAns(SurveyAnsMasterEntity surveyAns) {
		Session session = sessionFactory.getCurrentSession();
		try {
			surveyAns.setCreatedon(new Date());
			session.save(surveyAns);
			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}


	@Override
	public List<SurveyMasterEntity> getSurveyMasterDetailsById(int id) {
		List<SurveyMasterEntity> list = null;
		try {
			String sqlQuery = "select sm.id,CAST(sm.surveyName AS VARCHAR(1000)) AS surveyName, sm.surveyStart as surveyStart, sm.surveyEnd as surveyEnd, sm.createdby as createdby,"
					+ "sm.createdon as createdon, sm.statusId as statusId,s.name as statusname from SURVEYMASTER_PRD "
					+ " sm inner join statusmaster s on s.id= sm.STATUSID "
					+ " where sm.id=:id order by sm.id desc";

			list = getSession().createSQLQuery(sqlQuery)
					.addScalar("id")
					.addScalar("surveyName")
					.addScalar("surveyStart")
					.addScalar("surveyEnd")
					.addScalar("createdby")
					.addScalar("createdon")
					.addScalar("statusId")
					.addScalar("statusname").setParameter("id", id)
					.setResultTransformer(Transformers.aliasToBean(SurveyMasterEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}


	@Override
	public List<SurveyQueMasterEntity> getSurveyQueBySurveyId(int surveyId) {
		List<SurveyQueMasterEntity> list = null;
		try {
			String sqlQuery = "select sq.id , sq.surveyId as surveyId, sq.surveyQue as surveyQue,sq.createdby as createdby,sq.createdon as createdon,um.USERID as createdByName, "
					+ "sq.statusId as statusId,s.name as statusname from SURVEYQUEMASTER_PRD sq inner join statusmaster s on s.id= sq.STATUSID "
					+ " left join user_master um on sq.createdby = um.id "
					+ "where sq.surveyId=:surveyId order by sq.id desc";

			list = getSession().createSQLQuery(sqlQuery)
					.addScalar("id")
					.addScalar("surveyId")
					.addScalar("surveyQue")
					.addScalar("createdby")
					.addScalar("createdon")
					.addScalar("statusId")
					.addScalar("createdByName")
					.addScalar("statusname").setParameter("surveyId", surveyId)
					.setResultTransformer(Transformers.aliasToBean(SurveyQueMasterEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}


	@Override
	public List<SurveyAnsMasterEntity> getSurveyAnsByQueSUrveyId(SurveyAnsMasterEntity surveyAns) {
		List<SurveyAnsMasterEntity> list = null;
		try {
			String sqlQuery = "select sa.id, sa.surveyId as surveyId, sa.surveyQueId as surveyQueId, sa.surveyAns1 as surveyAns1, sa.surveyAns2 as surveyAns2, "
					+ "sa.surveyAns3 as surveyAns3, sa.surveyAns4 as surveyAns4,sa.createdby as createdby,sa.createdon as createdon,"
					+ "sa.statusId as statusId,s.name as statusname,sq.surveyque as surveyQue from SURVEYANSMASTER_PRD sa inner join surveyqueMaster_PRD sq on sq.id= sa.surveyQueId "
					+ "inner join statusmaster s on s.id= sa.STATUSID where sa.surveyQueId=:surveyQueId and  sa.surveyId=:surveyId";

			list = getSession().createSQLQuery(sqlQuery)
					.addScalar("id")
					.addScalar("surveyId")
					.addScalar("surveyQueId")
					.addScalar("surveyAns1")
					.addScalar("surveyAns2")
					.addScalar("surveyAns3")
					.addScalar("surveyAns4")
					.addScalar("createdby")
					.addScalar("createdon")
					.addScalar("statusId")
					.addScalar("surveyQue")
					.addScalar("statusname").setParameter("surveyQueId", surveyAns.getSurveyQueId()).setParameter("surveyId", surveyAns.getSurveyId())
					.setResultTransformer(Transformers.aliasToBean(SurveyAnsMasterEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}


	@Override
	public boolean updateSurveMasterDetails(SurveyMasterEntity surveyMasterData) {
		Session session = sessionFactory.getCurrentSession();
		
		try{
			

			try {
				surveyMasterData.setSurveyNameClob(new javax.sql.rowset.serial.SerialClob(surveyMasterData.getSurveyName().toCharArray()));
			} catch (SerialException e) {
			} catch (SQLException e) {
				e.printStackTrace();
			}
		session.update(surveyMasterData);
		return true;
		}catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}


	@Override
	public boolean updateSurveyQue(SurveyQueMasterEntity surveyQue) {
		Session session = sessionFactory.getCurrentSession();

		try{			
			session.update(surveyQue);
			return true;
		}catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}


	@Override
	public boolean updateSurveyAns(SurveyAnsMasterEntity surveyAns) {
		Session session = sessionFactory.getCurrentSession();

		try{
			session.update(surveyAns);
			return true;
		}catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}


	@Override
	public boolean deleteSurvey(int surveyId) {
		boolean success=true;
		try{
		String sqlQuery= "delete from surveyMaster_prd where id=:id";
		
		getSession().createSQLQuery(sqlQuery).setParameter("id",surveyId).executeUpdate();
		
		}
		catch(Exception e){
			LOGGER.info("Exception:", e);
			return false;
		}
	
		return success;
	}


	@Override
	public boolean deleteSurveyQue(int surveyId) {
		boolean success=true;
		try{ 
		String sqlQuery= "delete from surveyqueMaster_prd sq where sq.surveyid=:id";
		
		getSession().createSQLQuery(sqlQuery).setParameter("id",surveyId).executeUpdate();
		
		}
		catch(Exception e){
			LOGGER.info("Exception:", e);
			return false;
		}
	
		return success;
	}


	@Override
	public boolean deleteSurveyAns(int surveyId) {
		boolean success=true;
		try{
		String sqlQuery= "delete from surveyansMaster_prd sa where sa.surveyid=:id";
		
		getSession().createSQLQuery(sqlQuery).setParameter("id",surveyId).executeUpdate();
		
		}
		catch(Exception e){
			LOGGER.info("Exception:", e);
			return false;
		}
	
		return success;
	}


	@Override
	@SuppressWarnings("rawtypes")
	public ResponseMessageBean chechSurvey(List<SurveyMasterEntity> surveyData) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		try {
			for (SurveyMasterEntity surveyDetails : surveyData) {
				String sqlNameExit = "SELECT count(*) FROM SURVEYMASTER_PRD WHERE Lower(SURVEYNAME) =:surveyName";
				List isNameExit = getSession().createSQLQuery(sqlNameExit)
						.setParameter("surveyName", surveyDetails.getSurveyName().toLowerCase()).list();

				if (!(isNameExit.get(0).toString().equalsIgnoreCase("0"))) {
					rmb.setResponseCode("451");
					rmb.setResponseMessage("Name Already Exist");
				} else {
					rmb.setResponseCode("200");
					rmb.setResponseMessage("Both not exist");
				}
			}
		}
		catch(Exception e){
			LOGGER.info("Exception:", e);
		}
		
		return rmb;
	}
	

}
