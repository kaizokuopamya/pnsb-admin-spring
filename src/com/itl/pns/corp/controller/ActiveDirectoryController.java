//package com.itl.pns.corp.controller;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.util.ObjectUtils;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.itl.pns.bean.ResponseMessageBean;
//import com.itl.pns.bean.UserDetailsBean;
//import com.itl.pns.corp.entity.CorpUserEntity;
//import com.itl.pns.corp.service.ActiveDirectoryService;
//import com.itl.pns.entity.User;
//import com.itl.pns.service.LoginService;
//
//@RestController
//@RequestMapping("activeDirectory")
//public class ActiveDirectoryController {
//
//	static Logger LOGGER = Logger.getLogger(ActiveDirectoryController.class);
//	@Autowired
//	ActiveDirectoryService activeDirectoryService;
//
//	@Autowired
//	private LoginService loginService;
//
//	@RequestMapping(value = "/checkUser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<ResponseMessageBean> checkUser(@RequestBody CorpUserEntity corpUserData) {
//		ResponseMessageBean res = new ResponseMessageBean();
//		try {
//			Map<String, String> map = activeDirectoryService.checkUser(corpUserData.getUser_name());
//			if (!ObjectUtils.isEmpty(map)) {
//				res.setResponseCode("200");
//				res.setResult(map);
//			} else {
//				res.setResponseCode("202");
//				res.setResponseMessage("No Records Found");
//			}
//		} catch (Exception e) {
//			LOGGER.error(e.getMessage(), e);
//
//		}
//		return new ResponseEntity<>(res, HttpStatus.OK);
//	}
//
//	@RequestMapping(value = "/validateADUser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<ResponseMessageBean> validateADUser(@RequestBody User user) {
//		Boolean validateResponse = false;
//		UserDetailsBean userDetails = null;
//		ResponseMessageBean response = new ResponseMessageBean();
//		String mainBase = "cn=" + user.getBase() + ",cn=Users,dc=infraseepz,dc=lan";
//		System.out.println("**********************");
//		System.out.println(mainBase);
//		user.setBase(mainBase);
//		try {
//			validateResponse = activeDirectoryService.validateUser(user.getUserid(), user.getPassword(),
//					user.getBase());
//
//			if (validateResponse) {
//				userDetails = loginService.getUserID(user.getUserid());
//				if (!ObjectUtils.isEmpty(userDetails)) {
//					response.setResponseCode("200");
//					response.setResult(userDetails);
//				} else {
//					response.setResponseCode("500");
//					response.setResponseMessage("User Is Inactive");
//				}
//				if (user.getBiometricflag().equalsIgnoreCase("true")) {
//
//				}
//
//			} else {
//				response.setResponseCode("202");
//				response.setResponseMessage("User Does Not Exist");
//			}
//		} catch (Exception e) {
//			LOGGER.error(e.getMessage(), e);
//
//		}
//
//		return new ResponseEntity<>(response, HttpStatus.OK);
//	}
//
//}
