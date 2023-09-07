package com.itl.pns.dao.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.itl.pns.dao.CBSMessageTemplateDao;
import com.itl.pns.entity.ActivitySettingMasterEntity;
import com.itl.pns.entity.CBSMessageTemplateMasterEntity;
import com.itl.pns.entity.CalculatorMasterEntity;
import com.itl.pns.util.AdminWorkFlowReqUtility;

/**
 * @author shubham.lokhande
 *
 */
@Repository
@Transactional
public class CBSMessageTemplateDaoImpl implements CBSMessageTemplateDao {

	
	static Logger LOGGER = Logger.getLogger(CBSMessageTemplateDaoImpl.class);
	
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;	
	
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	
	@Autowired
	AdminWorkFlowReqUtility adminWorkFlowReqUtility;
	
	@Override
	public boolean addCbsMessageTemplate(CBSMessageTemplateMasterEntity templateData) {
		Session session = sessionFactory.getCurrentSession();
		try {
			templateData.setCreatedon(new Date());
			session.save(templateData);
			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}

	@Override
	public boolean updateCbsMessageTemplate(CBSMessageTemplateMasterEntity templateData) {
		Session session = sessionFactory.getCurrentSession();
		try {
			templateData.setUpdatedon(new Date());
			session.update(templateData);
			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}

	@Override
	public List<CBSMessageTemplateMasterEntity> getAllCbsMessageTemplate() {
	
		List<CBSMessageTemplateMasterEntity> list = null;
		try {
			String sqlQuery = "select am.id as id,am.errorcode as errorcode,am.errormessage as errormessage,am.email as email,am.sms as sms,am.push as push,am.inapp as inapp,"
					+ "am.smstemplate as smstemplate,am.emailtemplate as emailtemplate,am.pushtemplate as pushtemplate,am.inapptemplate as inapptemplate,"
					+ " am.createdby as createdby,am.updatedby as updatedby,am.createdon as createdon,am.updatedon as updatedon,am.statusid as statusid from CBS_MESSAGE_TEMPLATE_MASTER am ";
				
			list = getSession().createSQLQuery(sqlQuery)
					.addScalar("id").addScalar("errorcode").addScalar("errormessage").addScalar("email")
					.addScalar("push").addScalar("inapp").addScalar("sms").addScalar("smstemplate").addScalar("emailtemplate")
					.addScalar("pushtemplate").addScalar("inapptemplate").addScalar("createdby").addScalar("updatedby")
					.addScalar("createdon").addScalar("updatedon").addScalar("statusid")
					.setResultTransformer(Transformers.aliasToBean(CBSMessageTemplateMasterEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	
	@Override
	public List<CBSMessageTemplateMasterEntity> getCbsMessageTemplateById(BigDecimal id) {
		List<CBSMessageTemplateMasterEntity> list = null;
		try {
			String sqlQuery = "select am.id,am.errorcode,am.errormessage,am.email,am.sms,am.push,am.inapp,am.smstemplate,am.emailtemplate,am.pushtemplate,am.inapptemplate,"
					+ " am.createdby,am.updatedby,am.createdon,am.updatedon,am.statusid  from CBS_MESSAGE_TEMPLATE_MASTER am  where am.id=:id";
				
			list = getSession().createSQLQuery(sqlQuery)
					.addScalar("id").addScalar("errorcode").addScalar("errormessage").addScalar("email")
					.addScalar("push").addScalar("inapp").addScalar("sms").addScalar("smstemplate").addScalar("emailtemplate")
					.addScalar("pushtemplate").addScalar("inapptemplate").addScalar("createdby").addScalar("updatedby")
					.addScalar("createdon").addScalar("updatedon").addScalar("statusid").setParameter("id", id)
					.setResultTransformer(Transformers.aliasToBean(CBSMessageTemplateMasterEntity.class)).list();

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	
}
