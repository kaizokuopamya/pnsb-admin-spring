package com.itl.pns.dao.impl;

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

import com.itl.pns.dao.RoleTypesDao;
import com.itl.pns.entity.Role;
import com.itl.pns.entity.RoleTypesEntity;
import com.itl.pns.util.AdminWorkFlowReqUtility;

@Repository
@Transactional
public class RoleTypesDaoImpls implements RoleTypesDao {

	private static final Logger logger = LogManager.getLogger(RoleTypesDaoImpls.class);
	
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
	public List<RoleTypesEntity> getRoleTypeList() {
		List<RoleTypesEntity> list = null;
		try {
			String sqlQuery = "select r.id,r.name,r.statusId,r.appId ,r.createdBy,r.createdOn,r.updatedBy ," 
					+ "r.updatedOn from role_types r  where  r.statusId=3 order by r.createdOn desc";
			list = getSession().createSQLQuery(sqlQuery)
					.addScalar("id").addScalar("name").addScalar("statusId").addScalar("appId").addScalar("createdBy")
					.addScalar("createdOn").addScalar("updatedBy").addScalar("updatedOn")
					.setResultTransformer(Transformers.aliasToBean(RoleTypesEntity.class)).list();
		} catch (Exception e) {
			logger.error("Exception Occured", e);
		}
		return list;
	}

	@Override
	public List<RoleTypesEntity> getRoleTypeById(int id) {
		List<RoleTypesEntity> list = null;
		try {
			String sqlQuery = "select r.id,r.shortName,r.statusId,r.appId ,r.createdBy,r.createdOn,r.updatedBy ," 
					+ "r.updatedOn from role_types r  where  r.statusId=3 and r.id=:id order by r.createdOn desc";
			list = getSession().createSQLQuery(sqlQuery)
					.addScalar("id").addScalar("name").addScalar("statusId").addScalar("appId").addScalar("createdBy")
					.addScalar("createdOn").addScalar("updatedBy").addScalar("updatedOn").setParameter("id", id).setResultTransformer(Transformers.aliasToBean(RoleTypesEntity.class)).list();
		} catch (Exception e) {
			logger.error("Exception Occured", e);
		}
		return list;
	}



}
