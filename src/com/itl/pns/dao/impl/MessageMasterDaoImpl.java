package com.itl.pns.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.itl.pns.constant.ApplicationConstants;
import com.itl.pns.dao.MessageMasterDao;
import com.itl.pns.entity.MessageMasterEntity;
import com.itl.pns.repository.MessageMasterRepository;

@Repository("messageMasterDao")
@Transactional
public class MessageMasterDaoImpl implements MessageMasterDao {
	static Logger LOGGER = Logger.getLogger(MessageMasterDaoImpl.class);

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Autowired
	private MessageMasterRepository messageMasterRepository;

	@Override
	public List<MessageMasterEntity> messageTemplateList() {
		List<MessageMasterEntity> list = null;
		try {
			String sqlQuery = "select tm.id, tm.serviceType as serviceType,tm.entityId as entityId,tm.endPoint as endPoint,tm.messagetype as messagetype,"
					+ "tm.smsMessage as smsMessage,tm.subject as subject,emailMessage as emailMessage,pushMessage as pushMessage,"
					+ "tm.inAppMessage as inAppMessage,otherMessag1 as otherMessag1,otherMessag2 as otherMessag2,otherMessag3 as otherMessag3,"
					+ "tm.otherMessag4 as otherMessag4,tm.createdBy as createdBy,tm.createdOn as createdOn,s.name as status "
					+ "from MESSAGES_MASTER tm inner join statusmaster s on s.id= tm.statusId order by tm.id desc";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("serviceType").addScalar("entityId")
					.addScalar("endPoint").addScalar("messagetype").addScalar("smsMessage").addScalar("subject")
					.addScalar("emailMessage").addScalar("pushMessage").addScalar("inAppMessage")
					.addScalar("otherMessag1").addScalar("otherMessag2").addScalar("otherMessag3")
					.addScalar("otherMessag4").addScalar("createdBy").addScalar("createdOn").addScalar("status")
					.setResultTransformer(Transformers.aliasToBean(MessageMasterEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return list;
	}

	@Override
	public List<MessageMasterEntity> getmessageTemplate(BigDecimal id) {
		List<MessageMasterEntity> list = null;
		try {
			String sqlQuery = "select tm.id, tm.serviceType as serviceType,tm.entityId as entityId,tm.endPoint as endPoint,tm.messagetype as messagetype,"
					+ "tm.smsMessage as smsMessage,tm.subject as subject,emailMessage as emailMessage,pushMessage as pushMessage,"
					+ "tm.inAppMessage as inAppMessage,otherMessag1 as otherMessag1,otherMessag2 as otherMessag2,otherMessag3 as otherMessag3,"
					+ "tm.otherMessag4 as otherMessag4,tm.createdBy as createdBy,tm.createdOn as createdOn,s.id as statusId, s.name as status "
					+ "from MESSAGES_MASTER tm inner join statusmaster s on s.id= tm.statusId where tm.id=:id order by tm.id desc";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("serviceType").addScalar("entityId")
					.addScalar("endPoint").addScalar("messagetype").addScalar("smsMessage").addScalar("subject")
					.addScalar("emailMessage").addScalar("pushMessage").addScalar("inAppMessage")
					.addScalar("otherMessag1").addScalar("otherMessag2").addScalar("otherMessag3")
					.addScalar("otherMessag4").addScalar("createdBy").addScalar("createdOn").addScalar("statusId")
					.addScalar("status").setParameter("id", id)
					.setResultTransformer(Transformers.aliasToBean(MessageMasterEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return list;
	}

	@Override
	public MessageMasterEntity getmessageTemplateById(BigDecimal id) {
//		return messageMasterRepository.findOne(id);
		return new MessageMasterEntity();
	}

	@Override
	public void saveMessageTemplate(MessageMasterEntity messageMasterEntity) {
		messageMasterRepository.save(messageMasterEntity);

	}

	@Override
	public void updateMessageTemplate(MessageMasterEntity messageMasterEntity) {
		List<MessageMasterEntity> messageMasterEntityList = getmessageTemplate(messageMasterEntity.getId());
		if (!ObjectUtils.isEmpty(messageMasterEntityList)) {
			for (MessageMasterEntity messageMasterEntityNew : messageMasterEntityList) {
				if (!ObjectUtils.isEmpty(messageMasterEntity.getSmsMessage()))
					messageMasterEntityNew.setSmsMessage(messageMasterEntity.getSmsMessage());

				if (!ObjectUtils.isEmpty(messageMasterEntity.getEmailMessage()))
					messageMasterEntityNew.setEmailMessage(messageMasterEntity.getEmailMessage());

				if (!ObjectUtils.isEmpty(messageMasterEntity.getPushMessage()))
					messageMasterEntityNew.setPushMessage(messageMasterEntity.getPushMessage());

				if (!ObjectUtils.isEmpty(messageMasterEntity.getInAppMessage()))
					messageMasterEntityNew.setInAppMessage(messageMasterEntity.getInAppMessage());

				messageMasterEntityNew.setSubject(messageMasterEntity.getSubject());
				messageMasterEntityNew.setOtherMessag1(messageMasterEntity.getOtherMessag2());
				messageMasterEntityNew.setOtherMessag2(messageMasterEntity.getOtherMessag2());
				messageMasterEntityNew.setOtherMessag3(messageMasterEntity.getOtherMessag3());
				messageMasterEntityNew.setOtherMessag4(messageMasterEntity.getOtherMessag4());
				messageMasterEntityNew.setStatusId(messageMasterEntity.getStatusId());
				// update message master;
				messageMasterRepository.update(messageMasterEntityNew.getSmsMessage(),
						messageMasterEntityNew.getSubject(), messageMasterEntityNew.getEmailMessage(),
						messageMasterEntityNew.getPushMessage(), messageMasterEntityNew.getInAppMessage(),
						messageMasterEntityNew.getOtherMessag1(), messageMasterEntityNew.getOtherMessag2(),
						messageMasterEntityNew.getOtherMessag3(), messageMasterEntityNew.getOtherMessag4(),
						messageMasterEntityNew.getStatusId(), messageMasterEntityNew.getId());
			}

		}
	}
}
