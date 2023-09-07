package com.itl.pns.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.itl.pns.bean.ChangePasswordBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.bean.UserDetailsBean;
import com.itl.pns.dao.LoginDao;
import com.itl.pns.entity.User;
import com.itl.pns.repository.UserMasterRepository;
import com.itl.pns.service.LoginService;

@Service
@Qualifier("LoginService")
@Transactional
public class LoginServiceImpl implements LoginService {

	static Logger LOGGER = Logger.getLogger(LoginServiceImpl.class);

	@Autowired
	LoginDao loginDao;

	@Autowired
	UserMasterRepository userMasterRepository;

	@Override
	public UserDetailsBean getUserID(String userid) {
		UserDetailsBean userDetail = null;
		try {
			userDetail = loginDao.getUserID(userid);
			if (!ObjectUtils.isEmpty(userDetail)) {
				List<User> userLastLogin = userMasterRepository.findByuserid(userDetail.getUSERID().toLowerCase());
				userLastLogin.get(0).setUserlastlogin(new Date());
				userMasterRepository.save(userLastLogin);
			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return userDetail;
	}

	@Override
	public boolean changePassword(ChangePasswordBean chanegebean) {
		return loginDao.changePassword(chanegebean);
	}

	@Override
	public boolean forgetPassword(ChangePasswordBean chanegebean) {
		return loginDao.forgetPassword(chanegebean);
	}

	@Override
	public boolean logout(String token) {
		return loginDao.logout(token);
	}

	@Override
	public boolean logoutByUserId(String userid) {
		return loginDao.logoutByUserId(userid);

	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	public boolean updateStatus(String userid) {
		return loginDao.updateStatus(userid);

	}

	@Override
	public boolean updateStatusActive(String userid) {
		return loginDao.updateStatusActive(userid);
	}

	@Override
	public User ckeckLogin(User user) {
		try {
			user = loginDao.ckeckLogin(user);
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return user;
	}
	
//	@Override
//	public ResponseMessageBean ckeckLogin(User user) {
//		ResponseMessageBean response = new ResponseMessageBean();
//		try {
//			User userMaster = loginDao.ckeckLogin(user);
//
//			if (!ObjectUtils.isEmpty(userMaster)) {
//				// if(userMaster.getPassword().equals(EncryptDeryptUtility.md5(user.getPassword()))){
////				if (userMaster.getPassword().equals(PasswordSecurityUtil.toHexString(PasswordSecurityUtil.getSHA(user.getPassword())))) {
//					if (userMaster.getPassword().equals(user.getPassword())) {
//					response.setResponseCode("200");
//					response.setResponseMessage("Login Successfully");
//				} else {
//					response.setResponseCode("202");
//					response.setResponseMessage("Invalid Credentials");
//				}
//			} else {
//				response.setResponseCode("202");
//				response.setResponseMessage("Invalid Credentials");
//			}
//
//		} catch (Exception e) {
//			LOGGER.info("Exception:", e);
//		}
//		return response;
//
//	}
	
	
	
	@Override
	public List<UserDetailsBean> getUserLoginTypes() {
		return loginDao.getUserLoginTypes();
	}

}
