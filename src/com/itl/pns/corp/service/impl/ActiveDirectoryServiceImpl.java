////package com.itl.pns.corp.service.impl;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.naming.NamingException;
//
//import org.apache.log4j.Logger;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//
//import com.itl.pns.corp.service.ActiveDirectoryService;
//import com.itl.psb.ad.ValidateADUser;
//
//
//
//@Service
//@Component
//public class ActiveDirectoryServiceImpl implements ActiveDirectoryService {
//
//	static Logger LOGGER = Logger.getLogger(ActiveDirectoryServiceImpl.class);
//
//	
//	@SuppressWarnings("unchecked")
//	@Override
//	public Map<String, String>  checkUser(String userName) {
//		ValidateADUser adUser = new ValidateADUser();
//		Map<String, String> map = new HashMap<>();
//		try {
//			System.out.println(userName);
//			map = adUser.searchUser(userName);
//			System.out.println(adUser.searchUser(userName));
//			System.out.println(map);
//			
//		} catch (NamingException e) {
//			LOGGER.info("Exception:", e);
//		}
//
//		return map;
//	}
//
//	@Override
//	public boolean validateUser(String userName,String password,String base ) {
//		ValidateADUser adUser = new ValidateADUser();
//     	boolean isValidate = false;
//     	try {
//     		isValidate= adUser.validateADUser(userName, password, base);
//     		System.out.println(isValidate);
//		} catch (Exception e) {
//			LOGGER.info("Exception:", e);
//		}
//
//		return isValidate;
//	}
//
//}
