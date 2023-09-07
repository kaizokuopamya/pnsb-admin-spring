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
import org.springframework.util.ObjectUtils;

import com.itl.pns.dao.AdminSessionReqDao;
import com.itl.pns.entity.AdminUserSessionsEntity;

@Repository
@Transactional
public class AdminSessionReqDaoImpl implements AdminSessionReqDao {

	static Logger LOGGER = Logger.getLogger(AdminWorkFlowDaoImpl.class);

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public String getSessionIdFromDeviceId(String deviceId) {
		List<AdminUserSessionsEntity> list = null;

		try {
			String sqlQuery = "select ads.SESSIONTOKEN as sessionToken,ads.USERID as userId,ads.STATUSID as statusId, um.userid as userName from ADMINUSRSESSIONS ads\r\n"
					+ "inner join user_master um on um.id=ads.USERID \r\n"
					+ "where ads.DEVICETOKEN=:deviceToken and ads.STATUSID=3 and um.statusid=3";

			list = getSession().createSQLQuery(sqlQuery).addScalar("sessionToken").addScalar("userId")
					.addScalar("statusId").addScalar("userName").setParameter("deviceToken", deviceId)
					.setResultTransformer(Transformers.aliasToBean(AdminUserSessionsEntity.class)).list();

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		if (!ObjectUtils.isEmpty(list)) {
			return list.get(0).getSessionToken() + "~~~" + list.get(0).getUserId() + "~~~" + list.get(0).getStatusId()+"~~~" + list.get(0).getUserName();
		} else {
			return "NA~~~NA~~~NA";
		}

	}

}
