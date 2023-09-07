package com.itl.pns.dao.impl;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.itl.pns.dao.CustomerOtpDao;
import com.itl.pns.util.AdminWorkFlowReqUtility;

@Repository
@Transactional
@Service
public class CustomerOtpDaoImpl implements CustomerOtpDao {

	static Logger LOGGER = Logger.getLogger(CategoryDaoImpl.class);
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Autowired
	AdminWorkFlowReqUtility adminWorkFlowReqUtility;

	@Override
	public void getAllCustomerOtp() {
		Session session = sessionFactory.getCurrentSession();
	}

	@Override
	public void getCustomerOtpByCustomerId(String customerOtp) {
		// TODO Auto-generated method stub

	}

}
