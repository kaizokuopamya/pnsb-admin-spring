package com.itl.pns.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.ObjectUtils;

/**
 * @author shubham.lokhande
 *
 */
public class PSBCommonUtility {

	

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;
	

	@Autowired
	AdminWorkFlowReqUtility adminWorkFlowReqUtility;

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public static boolean checkIsNotNull(final Object value) {
		if (ObjectUtils.isEmpty(value)  ) {
			return false;
		} else {
			return true;
		}
	}
	
	
	
	
}
