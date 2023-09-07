package com.itl.pns.controller;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.itl.pns.bean.ChangePasswordBean;
import com.itl.pns.bean.RequestParamBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.bean.UserDetailsBean;
import com.itl.pns.constant.ApplicationConstants;
import com.itl.pns.dao.LoginDao;
import com.itl.pns.dao.MasterConfigDao;
import com.itl.pns.dao.impl.NotifcationEngineImpl;
import com.itl.pns.entity.AdminUserSessionsEntity;
import com.itl.pns.entity.ConfigMasterEntity;
import com.itl.pns.entity.NotificationsEntity;
import com.itl.pns.entity.Role;
import com.itl.pns.entity.User;
import com.itl.pns.entity.UserLoginDetails;
import com.itl.pns.repository.RoleRepository;
import com.itl.pns.repository.UserLoginDetailsRepository;
import com.itl.pns.service.LoginService;
import com.itl.pns.service.NotificationService;
import com.itl.pns.util.AdminWorkFlowReqUtility;
import com.itl.pns.util.EmailUtil;
import com.itl.pns.util.EncryptDeryptUtility;
import com.itl.pns.util.EncryptorDecryptor;
import com.itl.pns.util.RandomNumberGenerator;
import com.itl.pns.util.Utils;

/**
 * 
 * @author Sareeka Bangar
 * @since 07-06-2017
 * @version 1.0
 *
 */
@RestController
@RequestMapping("login")
public class LoginController {

	private static final Logger logger = LogManager.getLogger(LoginController.class);

	@Autowired
	private LoginService loginService;

	@Autowired
	EmailUtil emailUtil;

	@Autowired
	private LoginDao loginDao;

	@Autowired
	private EmailUtil util;

	@Autowired
	AdminWorkFlowReqUtility adminReqUtility;

	@Autowired
	MasterConfigDao masterConfigDao;

	@Autowired
	NotifcationEngineImpl notificationEng;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private UserLoginDetailsRepository userLoginDetailsRepository;

	@Value("${DATA_ENCR}")
	private String staticEncDecKey;

	@Value("${ADMIN_VER_OMNI_AD}")
	private String adminVerifiationFlow;

//	@Value("${INITIAL_CONTEXT_FACTORY}")
//	private String initialContextFActory;

	@Value("${LDAP_PROVIDER_URL}")
	private String ldapProviderURL;

	@Value("${LDAP_SECURITY_PRINCIPAL}")
	private String ldapSecurityPrincipal;

	@Value("${LDAP_SECURITY_AUTHENTICATION}")
	private String ldapSecurityAuthentication;

	@Value("${TIME_RETRIES}")
	private int timeRetries;

	@Value("${MAX_RETRIES}")
	private int maxRetries;

	@Value("${OTP_EXPIRY_TIME_SECONDS}")
	private int otpExpiryTime;

	@RequestMapping(value = "/getUserLogin", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getUserLogin(@RequestBody User user) {
		logger.info("UserId: " + user.getUserid());
		ResponseMessageBean response = new ResponseMessageBean();
		String password = user.getPassword();
		String userId = user.getUserid();
		UserDetailsBean userDetails = null;
		UserLoginDetails userLoginDetails = null;
		logger.info("UserActivity :: User Login : userId : " + userId);
		try {
			user = loginService.ckeckLogin(user);
			if (!ObjectUtils.isEmpty(user)) {
				userDetails = loginService.getUserID(userId);
				if (!ObjectUtils.isEmpty(userDetails)) {
					logger.info("userId : " + userId + ", User verified");
					response.setResult(userDetails);
					Role role = roleRepository.findByid(userDetails.getROLE_ID());
					if (!ObjectUtils.isEmpty(role)) {
						logger.info("userId : " + userId + ", user Role verified");
						password = EncryptDeryptUtility.decryptNonAndroid(password, staticEncDecKey);
						if (role.getName().equalsIgnoreCase(ApplicationConstants.ADMIN)
								|| role.getName().equalsIgnoreCase(ApplicationConstants.SUPER_ADMIN)
								|| role.getName().equalsIgnoreCase(ApplicationConstants.SUPERADMIN)
								|| role.getName().equalsIgnoreCase(ApplicationConstants.LANGUAGEUSER)) {
							logger.info("Infra User Admin/SuperAdmin......");
							password = EncryptorDecryptor.encryptData(password);
//							logger.info("DB password: " + password + " for UserName: " + userId);
							if (password.equals(user.getPassword())) {
								response.setResponseCode("200");
								response.setResponseMessage("Login Successfully");
								logger.info("userId : " + userId + ", user password Verified");
								userLoginDetails = saveUserLoginDetails(user.getId(), "Password Verified", 'Y');
							} else {
								response.setResponseCode("202");
								response.setResponseMessage("Invalid Credentials");
								logger.info("userId : " + userId + ", Invalid login credentials");
								saveUserLoginDetails(user.getId(), "Failed", 'N');
								return new ResponseEntity<>(response, HttpStatus.OK);
							}
							// comment for infrabots deployment
						} else {
							logger.info("Ldap User......");
							ResponseMessageBean ldapResponse = ldapAuth(userId.toUpperCase(), password);
							boolean result = (boolean) ldapResponse.getResult();
							if (result) {
								response.setResponseCode("200");
								response.setResponseMessage("User details are available in AD");
								logger.info("userId : " + userId + ", LDAP user details verified");
								userLoginDetails = saveUserLoginDetails(user.getId(), "Password Verified", 'Y');
							} else {
								logger.info("userId : " + userId + ", LDAP login failure");
								saveUserLoginDetails(user.getId(), "Failed", 'N');
								return new ResponseEntity<>(ldapResponse, HttpStatus.OK);
							}
						}
						userDetails.setLatitude(user.getLatitude());
						userDetails.setLongitude(user.getLongitude());
						userDetails.setOs(user.getOs());
						userDetails.setClientIp(user.getClientIp());
						userDetails.setBrowser(user.getBrowser());
						List<ConfigMasterEntity> configData = masterConfigDao.getConfigByConfigKey("OTP_LENGTH");

						String mobileOtp = String
								.valueOf(RandomNumberGenerator.onePad(RandomNumberGenerator.generateActivationCode(),
										Integer.parseInt(configData.get(0).getConfigValue())));
						userDetails.setOTP(mobileOtp);
						userDetails.setUserLoginDetailsId(userLoginDetails.getId());
						loginDao.saveToAdminUserSession(userDetails);
						EmailUtil eu = new EmailUtil();

						Map<String, String> notifcaion = new HashMap<String, String>();
						notifcaion.put(ApplicationConstants.CUSTOMER_ID, userDetails.getPHONENUMBER());
						notifcaion.put(ApplicationConstants.CONTENT, "Dear Customer, Your OTP for login is " + mobileOtp
								+ ". Do not share your OTP with anyone. Bank NEVER asks for otp over Call, SMS or Email -Punjab&Sind Bank");
						notifcaion.put(ApplicationConstants.APP_NAME, "entityId");
						notifcaion.put(ApplicationConstants.RRN, "1234");
						notifcaion.put(ApplicationConstants.ACTIVITY_NAME, "");
						notifcaion.put(ApplicationConstants.FILE_PATH, "");
						logger.info("userId : " + userId + ", Mobile: " + userDetails.getPHONENUMBER());

						NotificationsEntity smsNotification = new NotificationsEntity();
						smsNotification.setType(BigDecimal.valueOf(2));
						smsNotification.setCustId(userDetails.getUSER_ID());
						smsNotification.setCustomerId(userDetails.getPHONENUMBER());
						int smsAttamptCount = notificationService.getTotalAttamptCount(smsNotification, timeRetries);
						if (smsAttamptCount < maxRetries) {
							smsNotification.setNotificationMsg(mobileOtp
									+ " is your OTP to login into PSB UnIC Administrator Portal. Do not share your OTP with anyone-P%26SB");								
							smsNotification.setCreatedBy(BigDecimal.valueOf(1));
							smsNotification.setStatusId(BigDecimal.valueOf(3));
							smsNotification.setCreatedOn(new Timestamp(System.currentTimeMillis()));
							smsNotification.setRrn("1234");
							notificationService.saveNotificationAttampt(smsNotification);
							// send otp on SMS
							emailUtil.sendSMSNotification(userDetails.getPHONENUMBER(), mobileOtp
									+ " is your OTP to login into PSB UnIC Administrator Portal. Do not share your OTP with anyone-P%26SB");
						} else {
							logger.info("userId : " + userId + ": SMS max attampt reached.......");
						}
						logger.info("userId : " + userId + ": Sms sending successfully.......");

						// Map<String, String> notifcaion = new HashMap<String, String>();
						notifcaion.put("userId", userDetails.getUSERID());
						notifcaion.put("appName", "entityId");
						notifcaion.put("RRN", "1234");
						notifcaion.put("CreateUser", "CreateUser");
						notifcaion.put("filepath", "");
						notifcaion.put("emailContent", "Dear Customer, Your OTP for login is " + mobileOtp
								+ ". Do not share your OTP with anyone. Bank NEVER asks for otp over Call, SMS or Email -Punjab&Sind Bank");
						notifcaion.put("emailId", userDetails.getEMAIL());
						notifcaion.put("deviceTokenId", ""); // Need to check
						notifcaion.put("subject", "OTP for login"); // Need to check

						NotificationsEntity emailNotification = new NotificationsEntity();
						emailNotification.setType(BigDecimal.valueOf(3));
						emailNotification.setCustId(userDetails.getUSER_ID());
						emailNotification.setCustomerId(userDetails.getEMAIL());
						int emailAttamptCount = notificationService.getTotalAttamptCount(emailNotification,
								timeRetries);
						if (emailAttamptCount < maxRetries) {
							emailNotification.setNotificationMsg(notifcaion.get("emailContent"));
							emailNotification.setCreatedBy(BigDecimal.valueOf(1));
							emailNotification.setStatusId(BigDecimal.valueOf(3));
							emailNotification.setCreatedOn(new Timestamp(System.currentTimeMillis()));
							emailNotification.setRrn("1234");
							notificationService.saveNotificationAttampt(emailNotification);
							eu.sendEmail(notifcaion);
						} else {
							logger.info("userId : " + userId + ", Email max attampt reached.......");
						}
						userDetails.setOtpExpiryTime(otpExpiryTime);
						response.setResult(userDetails);
					} else {
						response.setResponseCode("202");
						response.setResponseMessage(
								"Invalid credentials, Please contact to administrator for further process");
						logger.info("userId : " + userId + ", Invalid credentials......Login failed");
						saveUserLoginDetails(user.getId(), "Failed", 'N');
					}
				} else {
					response.setResponseCode("202");
					response.setResponseMessage(
							"Invalid credentials, Please contact to administrator for further process");
					logger.info("userId : " + userId + ", Invalid credentials......Login failed");
					saveUserLoginDetails(user.getId(), "Failed", 'N');
				}

			} else {
				response.setResponseCode("202");
				response.setResponseMessage("Invalid credentials, Please contact to administrator for further process");
				logger.info("userId : " + userId + ", Invalid credentials......Login failed");
				saveUserLoginDetails(user.getId(), "Failed", 'N');
			}

		} catch (Exception e) {
			logger.error("userId : " + userId + ", Login error." + e);
			saveUserLoginDetails(user.getId(), "Failed", 'N');
		}

		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	private UserLoginDetails saveUserLoginDetails(BigInteger userId, String status, char isPasswordVerified) {
		List<UserLoginDetails> userLoginDetails = userLoginDetailsRepository.findByUserIdAndIsPasswordVerified(userId,
				'Y');
		if (!ObjectUtils.isEmpty(userLoginDetails)) {
			List<UserLoginDetails> userLoginDetailList = new ArrayList<>();
			for (UserLoginDetails userLoginDetail : userLoginDetails) {
				userLoginDetail.setIsPasswordVerified('N');
				userLoginDetail.setIsLogin('N');
				userLoginDetailList.add(userLoginDetail);
			}
			userLoginDetailsRepository.save(userLoginDetailList);
		}
		UserLoginDetails userLoginDeails = new UserLoginDetails();
		userLoginDeails.setUserId(userId);
		userLoginDeails.setStatus(status);
		userLoginDeails.setCreatedOn(new Timestamp(System.currentTimeMillis()));
		userLoginDeails.setIsLogin('N');
		userLoginDeails.setIsPasswordVerified(isPasswordVerified);
		return userLoginDetailsRepository.save(userLoginDeails);

	}

	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> changePassword(@RequestBody ChangePasswordBean chanegebean) {
		logger.info("UserActivity :: Change password : userId : " + chanegebean.getUSERID());
		boolean ischanged;
		ResponseMessageBean response = new ResponseMessageBean();
		if (null == chanegebean || chanegebean.getNEWPASSWORD() == null || null == chanegebean.getOLDPASSWORD()
				|| null == chanegebean.getUSERID()) {
			response.setResponseCode("500");
			response.setResponseMessage("Kindly Enter valid userid and Password ");
			logger.info("userId : " + chanegebean.getUSERID() + ", Kindly Enter valid userid and Password");
			return new ResponseEntity<ResponseMessageBean>(response, HttpStatus.OK);
		}
		ischanged = loginService.changePassword(chanegebean);
		try {
			if (ischanged) {
				loginService.logoutByUserId(chanegebean.getUSERID());
				response.setResponseCode("200");
				logger.info("userId : " + chanegebean.getUSERID() + ", Password Changed Successfully");
				response.setResponseMessage(" Password Changed Successfully, please Login with new Password");

			} else {
				response.setResponseCode("500");
				response.setResponseMessage("Kindly Enter valid Old Password ");
				logger.info("userId : " + chanegebean.getUSERID() + ", Kindly Enter valid Old Password");
			}
		} catch (Exception e) {
			logger.info("userId : " + chanegebean.getUSERID() + ", Error in change password:", e);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/forgetPassword", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> forgetPassword(@RequestBody ChangePasswordBean chanegebean) {
		logger.info("UserActivity :: Change password : userId : " + chanegebean.getUSERID());
		boolean ismailsend;
		ismailsend = loginService.forgetPassword(chanegebean);
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			if (ismailsend) {
				loginService.logoutByUserId(chanegebean.getUSERID());
				response.setResponseCode("200");
				response.setResponseMessage("New Password Sent To Registered Mail ID");
				logger.info("userId : " + chanegebean.getUSERID() + ", New Password Sent To Registered Mail ID:");
			} else {
				response.setResponseCode("500");
				response.setResponseMessage("Username And Email Id Does Not Match ");
				logger.info("userId : " + chanegebean.getUSERID() + ", Username And Email Id Does Not Match");
			}
		} catch (Exception e) {
			logger.info("userId : " + chanegebean.getUSERID() + ", Error in forgot password:", e);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> logout(@RequestBody User user, HttpServletRequest httpRequest) {
		int logOut = 0;
		logger.info("UserActivity :: User logout : userId : " + Utils.getUserId(httpRequest));
		ResponseMessageBean res = new ResponseMessageBean();
		UserLoginDetails userLoginDetails = userLoginDetailsRepository
				.findByUserIdAndIsLogin(new BigInteger(user.getUserid()), 'Y');
		userLoginDetails.setIsLogin('N');
		userLoginDetails.setIsPasswordVerified('N');
		userLoginDetailsRepository.save(userLoginDetails);
		logOut = loginDao.updateAdminSessionStatus(new BigInteger(user.getUserid()));
		try {
			if (logOut != 0) {
				res.setResponseCode("200");
				res.setResponseMessage("Your Session Expired");
				logger.info("userId : " + Utils.getUserId(httpRequest) + ", Logout successfully!");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("Error While Logout");
				logger.info("userId : " + Utils.getUserId(httpRequest) + ", Error While Logout");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);

	}

	@RequestMapping(value = "/activeToken", method = RequestMethod.POST)
	public ResponseEntity<Boolean> activeToken(@RequestBody RequestParamBean requestBean) {

		boolean isupdate = loginService.updateStatusActive(requestBean.getId1());
		return new ResponseEntity<>(isupdate, HttpStatus.OK);

	}

	@RequestMapping(value = "/deactiveToken", method = RequestMethod.POST)
	public ResponseEntity<Boolean> deactiveToken(@RequestBody RequestParamBean requestBean) {

		boolean isupdate = loginService.updateStatus(requestBean.getId1());

		return new ResponseEntity<>(isupdate, HttpStatus.OK);

	}

	@RequestMapping(value = "/getUserLoginTypes", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getUserLoginTypes() {
		logger.info("get getUserLoginTypes  -> getUserLoginTypes()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<UserDetailsBean> userDetails = loginService.getUserLoginTypes();

			if (null != userDetails) {
				res.setResponseCode("200");
				res.setResult(userDetails);
				res.setResponseMessage("Success");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);

	}

	@RequestMapping(value = "/resendOtpActivationCode", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> generateOtpActivationCode(@RequestBody User user,
			HttpServletRequest httpRequest) {
		logger.info("UserActivity :: Resend OTP : userId : " + Utils.getUserId(httpRequest));
		ResponseMessageBean res = new ResponseMessageBean();
		int count = 0;

		UserDetailsBean userDetails = loginService.getUserID(user.getUserid());

		List<ConfigMasterEntity> resendOtpCount = masterConfigDao.getConfigByConfigKey("RESEND_MAX_ATTEMPTS");
		List<ConfigMasterEntity> configData = masterConfigDao.getConfigByConfigKey("OTP_LENGTH");
		List<AdminUserSessionsEntity> resendOtpAttmpResp = adminReqUtility
				.getResendOtpAttemptByUserId(userDetails.getUSER_ID().toString());

		try {
			if (!ObjectUtils.isEmpty(resendOtpAttmpResp.get(0).getResendotpattempt())
					&& (Integer.parseInt(resendOtpAttmpResp.get(0).getResendotpattempt()) > Integer
							.parseInt(resendOtpCount.get(0).getConfigValue()))) {
				res.setResponseCode("500");
				res.setResponseMessage("You Have Exceeded Maximum Resend otp Attempts..!");
				logger.info("userId : " + Utils.getUserId(httpRequest) + ", Maximum Resend otp Attempts Exceeded!");
			} else {
				String mobileOtp = String
						.valueOf(RandomNumberGenerator.onePad(RandomNumberGenerator.generateActivationCode(),
								Integer.parseInt(configData.get(0).getConfigValue())));

				EmailUtil eu = new EmailUtil();

				Map<String, String> notifcaion = new HashMap<String, String>();
				notifcaion.put("userId", user.getUserid());
				notifcaion.put("appName", "entityId");
				notifcaion.put("RRN", "1234");
				notifcaion.put("CreateUser", "CreateUser");
				notifcaion.put("filepath", "");
				notifcaion.put("emailContent", "Dear Customer, Your OTP for login is " + mobileOtp
						+ ". Do not share your OTP with anyone. Bank NEVER asks for otp over Call, SMS or Email -Punjab&Sind Bank");
				notifcaion.put("emailId", userDetails.getEMAIL());
				notifcaion.put("deviceTokenId", ""); // Need to check
				notifcaion.put("subject", "OTP for login"); // Need to check

				NotificationsEntity smsNotification = new NotificationsEntity();
				smsNotification.setType(BigDecimal.valueOf(2));
				smsNotification.setCustomerId(userDetails.getPHONENUMBER());
				int smsAttamptCount = notificationService.getTotalAttamptCount(smsNotification, timeRetries);
				logger.info("Sms Attampt Count: " + smsAttamptCount);
				if (smsAttamptCount < maxRetries) {
					smsNotification.setNotificationMsg(mobileOtp
							+ " is your OTP to login into PSB UnIC Administrator Portal. Do not share your OTP with anyone-P%26SB");
					smsNotification.setCreatedBy(BigDecimal.valueOf(1));
					smsNotification.setStatusId(BigDecimal.valueOf(3));
					smsNotification.setCreatedOn(new Timestamp(System.currentTimeMillis()));
					smsNotification.setRrn("1234");
					notificationService.saveNotificationAttampt(smsNotification);
					// send otp on SMS
					emailUtil.sendSMSNotification(userDetails.getPHONENUMBER(), mobileOtp
							+ " is your OTP to login into PSB UnIC Administrator Portal. Do not share your OTP with anyone-P%26SB");
				} else {
					logger.info("userId : " + Utils.getUserId(httpRequest) + ", SMS max attampt reached");
				}
				NotificationsEntity emailNotification = new NotificationsEntity();
				emailNotification.setType(BigDecimal.valueOf(3));
				emailNotification.setCustomerId(userDetails.getEMAIL());
				int emailAttamptCount = notificationService.getTotalAttamptCount(emailNotification, timeRetries);
				logger.info("Email Attampt Count: " + emailAttamptCount);
				if (emailAttamptCount < maxRetries) {
					emailNotification.setNotificationMsg(notifcaion.get("emailContent"));
					emailNotification.setCreatedBy(BigDecimal.valueOf(1));
					emailNotification.setStatusId(BigDecimal.valueOf(3));
					emailNotification.setCreatedOn(new Timestamp(System.currentTimeMillis()));
					emailNotification.setRrn("1234");
					notificationService.saveNotificationAttampt(emailNotification);
					eu.sendEmail(notifcaion);
				} else {
					logger.info("userId : " + Utils.getUserId(httpRequest) + ", Email max attampt reached");
				}
				if (!ObjectUtils.isEmpty(resendOtpAttmpResp.get(0).getResendotpattempt())) {
					count = Integer.parseInt(resendOtpAttmpResp.get(0).getResendotpattempt());
					count = count + 1;
				} else {
					count = count + 1;
				}

				user.setResendOtpAttempt(String.valueOf(count));
				user.setId(userDetails.getUSER_ID().toBigInteger());
				adminReqUtility.updateResendOtpAttempts(user);
				userDetails.setOtpExpiryTime(otpExpiryTime);
				userDetails.setDeviceToken(httpRequest.getHeader("deviceid"));
				res.setResponseCode("200");
				res.setResult(userDetails);
				logger.info("userId : " + Utils.getUserId(httpRequest) + ", Otp Sent Successfully");
				res.setResponseMessage("Otp Sent Successfully");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}

		return new ResponseEntity<>(res, HttpStatus.OK);

	}

	@RequestMapping(value = "/validateLoginOtp", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> validateLoginOtp(@RequestBody User user,
			HttpServletRequest httpRequest) {

		ResponseMessageBean response = new ResponseMessageBean();

		List<AdminUserSessionsEntity> resp = adminReqUtility.getAdminUserSessionByUserId(user.getUserid());
		Date otpStartTime = resp.get(0).getStartTime();
		Date otpEndTime = new Timestamp(System.currentTimeMillis());
		long seconds = (otpEndTime.getTime() - otpStartTime.getTime()) / 1000;
		if (seconds > otpExpiryTime) {
			response.setResponseCode("500");
			response.setResponseMessage("OTP Expired");
			logger.info("userId : " + Utils.getUserId(httpRequest) + ": Otp Expired");

			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		logger.info("UserActivity :: Validate User OTP : userId : " + Utils.getUserId(httpRequest));
		int count = 0;

		List<ConfigMasterEntity> configData = masterConfigDao.getConfigByConfigKey("OTP_MAX_ATTEMPT");

		List<AdminUserSessionsEntity> wrongAttmpResp = adminReqUtility.getWrongAttemptByUserId(user.getUserid());
		Map<String, String> map = new HashMap<>();
		map.put("OtpValidationString", user.getOtpValidationString());
		try {
			if (!ObjectUtils.isEmpty(wrongAttmpResp.get(0).getWrongattemptsotp())
					&& (Integer.parseInt(wrongAttmpResp.get(0).getWrongattemptsotp()) > Integer
							.parseInt(configData.get(0).getConfigValue()))) {
				response.setResponseCode("500");
				response.setResponseMessage("You Have Exceeded Maximum otp Attempts..!");
				logger.info("userid: " + Utils.getUserId(httpRequest) + ", Maximum otp Attempts Exceeded.....!");
			} else {

				if (resp.get(0).getOtp().equalsIgnoreCase(user.getOtp())) {
					response.setResponseCode("200");
					response.setResult(map);
					response.setResponseMessage("OTP Verified");

					List<UserLoginDetails> userLoginDetails = userLoginDetailsRepository
							.findByUserIdAndIsPasswordVerified(BigInteger.valueOf(Long.parseLong(user.getUserid())),
									'Y');
					if (!ObjectUtils.isEmpty(userLoginDetails)) {
						for (UserLoginDetails userLoginDetail : userLoginDetails) {
							userLoginDetail.setStatus("Success");
							userLoginDetail.setIsLogin('Y');
							userLoginDetailsRepository.save(userLoginDetail);
						}
					}

					logger.info("userid: " + Utils.getUserId(httpRequest) + ": OTP Verified");
					logger.info("userid: " + Utils.getUserId(httpRequest) + ": Login Sucessfully.....");

				} else {
					count = Integer.parseInt(wrongAttmpResp.get(0).getWrongattemptsotp());
					count = count + 1;
					user.setWrong_attempts(new BigDecimal(count));
					adminReqUtility.updateWrongAttempts(user);

					response.setResponseCode("500");
					response.setResponseMessage("Invalid OTP");
					logger.info("userId : " + Utils.getUserId(httpRequest) + ": Invalid OTP");
				}

			}
		} catch (Exception e) {
			logger.error("Otp Validation error : " + Utils.getUserId(httpRequest) + ": Login error." + e);
		}

		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	private ResponseMessageBean ldapAuth(String username, String password) {
		ResponseMessageBean response = new ResponseMessageBean();
		boolean result = true;
		logger.info(password.trim().length());
		if (password.trim().length() < 1) {
			response.setResponseCode("202");
			response.setResult(false);
			response.setResponseMessage("Invalid credentials, Please provide valid credentials to process further");
			return response;
		}
		try {
			// Set up the environment for creating the initial context
			Hashtable<String, String> env = new Hashtable<String, String>();
			env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
			// env.put(Context.PROVIDER_URL, "ldap://172.22.23.16:389");
			env.put(Context.PROVIDER_URL, ldapProviderURL);
			env.put(Context.SECURITY_PRINCIPAL, ldapSecurityPrincipal);
			env.put(Context.SECURITY_AUTHENTICATION, ldapSecurityAuthentication);
			env.put(Context.SECURITY_PRINCIPAL, username + "@psb.org.in"); // we have 2 \\ because it's a escape char
			env.put(Context.SECURITY_CREDENTIALS, password);

			// Create the initial context
			DirContext ctx = new InitialDirContext(env);
			result = ctx != null;
			ctx.getEnvironment();

			if (ctx != null) {
				ctx.close();
			}
			response.setResponseCode("200");
			response.setResult(true);
			response.setResponseMessage("Success");
			// System.out.println(result);
		} catch (AuthenticationException e) {
			logger.info("Error Message: " + e.getMessage());
			response.setResponseCode("202");
			response.setResult(false);
			response.setResponseMessage("Ldap Error: AcceptSecurityContext error code 49");
//			String str="[LDAP: error code 49 - 80090308: LdapErr: DSID-0C09042A, comment: AcceptSecurityContext error, data 52e, v3839]";
			logger.info("userId: " + username + ": Authentication Exception in LDAP" + e);
			return response;
		} catch (Exception e) {
			JSONObject json = new JSONObject(e.getMessage());
			response.setResponseCode("202");
			response.setResult(false);
			response.setResponseMessage("Invalid credentials, Please provide valid credentials to process further");
			logger.info("userId: " + username + ": Exception in LDAP." + e);
			return response;

		}
		return response;
	}

}
