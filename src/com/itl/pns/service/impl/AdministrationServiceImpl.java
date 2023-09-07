package com.itl.pns.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import com.itl.pns.bean.ActivityMasterBean;
import com.itl.pns.bean.AdminUserActivityLogs;
import com.itl.pns.bean.ChangePasswordBean;
import com.itl.pns.bean.DateBean;
import com.itl.pns.bean.FraudReportingBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.bean.UserAddEditRequestBean;
import com.itl.pns.bean.UserDetailsBean;
import com.itl.pns.dao.AdministrationDao;
import com.itl.pns.entity.AccountOpeningDetailsEntity;
import com.itl.pns.entity.AccountTypesEntity;
import com.itl.pns.entity.ActivitySettingMasterEntity;
import com.itl.pns.entity.AdminWorkflowHistoryEntity;
import com.itl.pns.entity.CbsBranchList;
import com.itl.pns.entity.OmniLimitMasterEntity;
import com.itl.pns.entity.Role;
import com.itl.pns.entity.RoleTypesEntity;
import com.itl.pns.entity.User;
import com.itl.pns.entity.UserDetails;
import com.itl.pns.repository.AccountOpeningDetailsRepository;
import com.itl.pns.repository.CbsBranchListRepository;
import com.itl.pns.repository.RoleRepository;
import com.itl.pns.repository.RoleTypeRepository;
import com.itl.pns.repository.UserDetailsRepository;
import com.itl.pns.repository.UserMasterRepository;
import com.itl.pns.service.AdministrationService;
import com.itl.pns.util.AdminEmailUtil;
import com.itl.pns.util.AdminWorkFlowReqUtility;
import com.itl.pns.util.EmailUtil;
import com.itl.pns.util.PasswordSecurityUtil;
import com.itl.pns.util.RandomNumberGenerator;

@Service
@Qualifier("UserService")
@Transactional
public class AdministrationServiceImpl implements AdministrationService {

	static Logger LOGGER = Logger.getLogger(AdministrationServiceImpl.class);
	@Autowired
	AdministrationDao userDao;

	@Autowired
	UserMasterRepository userMasterRepository;

	@Autowired
	EmailUtil email;

	@Autowired
	AdminEmailUtil adminEmailUtil;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	private UserDetailsRepository userDetailsRepository;

	@Autowired
	AdminWorkFlowReqUtility adminWorkFlowReqUtility;

	@Autowired
	AccountOpeningDetailsRepository accountOpeningDetailsRepository;

	@Autowired
	CbsBranchListRepository cbsBranchListRepository;

	@Autowired
	AdministrationDao adminDao;

	@Autowired
	RoleTypeRepository roleTypeRepository;

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	@Value("${pwdRestLink}")
	private String pwdRestLink;

	@Value("${ADMIN_TEMP_PASSWORD}")
	private String adminTempPass;

	@Value("${NOTICATION_ENGINE_URL_EMAIL}")
	private String notificationEngineUrlEmail;

	@Autowired
	RestTemplate restTemplate;

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public List<UserDetailsBean> getUserList() {
		return userDao.getUserList();
	}

	@Override
	public List<UserDetailsBean> getAllReporteesUsers(String userCode) {
		return userDao.getAllReporteesUsers(userCode);
	}

	@Override
	public List<UserDetailsBean> getAllBranchUsersList(String userCode, String branchCode) {
		return userDao.getAllBranchUsersList(userCode, branchCode);
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRED)
	public boolean deleteUser(BigInteger id) {
		try {
			User usernew = userMasterRepository.findByid(BigInteger.valueOf((id).intValue()));
			usernew.setIsdeleted('Y');
			/* usernew.setIsActive("N"); */
			// usernew.setStatusId(BigInteger.valueOf(0));
			userMasterRepository.save(usernew);
			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRED)
	public Boolean createUser(UserAddEditRequestBean userAddEditReqBean) {
		LOGGER.info("AdministrationController->AdminstartionService->createUser----------Start");
		RandomNumberGenerator randomNumberGenerator = new RandomNumberGenerator();

		try {
			String randomNumber = randomNumberGenerator.generateRandomString();
			// String randomNumber = adminTempPass;
//			String encryptpass = PasswordSecurityUtil.toHexString(PasswordSecurityUtil.getSHA(randomNumber));			
			String encryptpass = "4EGbSiJL8tzKRxx71xCslg==";
			LOGGER.info("user auto genrated plain text password---->" + randomNumber);
			LOGGER.info("User auto generated sha2 password--->" + encryptpass);
			Date date = new Date();
			User userM = new User();

			userM.setUserid(userAddEditReqBean.getUSERID().toLowerCase());
			userM.setPassword(encryptpass);
			userM.setStatusId(BigInteger.valueOf(36));
			userM.setCreatedBy(userAddEditReqBean.getCREATEDBY().toString());
			userM.setCreatedOn(date);
			// userM.setUpdatedBy(userAddEditReqBean.getUPDATEBY());
			// userM.setUpdatedOn(date);
			userM.setLoginType(userAddEditReqBean.getLOGINTYPE());
			userM.setThumbnail(userAddEditReqBean.getTHUMBNAIL());
			userM.setIsdeleted('N');
			userM.setWrong_attempts(new BigDecimal(0));
			/*
			 * if(userAddEditReqBean.getUsersType().equalsIgnoreCase(ApplicationConstants.
			 * HEAD_OFFICE)) { userM.setBranchCode(userAddEditReqBean.getReportingBranch());
			 * //HEAD OFFICE userM.setReportingBranch(""); }else
			 * if(userAddEditReqBean.getUsersType().equalsIgnoreCase(ApplicationConstants.
			 * ZONAL_OFFICE)) { userM.setBranchCode(userAddEditReqBean.getZonalcode());
			 * //HEAD OFFICE
			 * userM.setReportingBranch(userAddEditReqBean.getReportingBranch()); //ZONAL
			 * OFFICE }else { userM.setBranchCode(userAddEditReqBean.getBranchcode());
			 * //BRANCH OFFICE userM.setReportingBranch(userAddEditReqBean.getZonalcode());
			 * //ZONAL OFFICE }
			 */

			userM.setBranchCode(userAddEditReqBean.getBranchcode()); // BRANCH OFFICE
			userM.setReportingBranch(userAddEditReqBean.getZonalcode());
			User userid = userMasterRepository.save(userM);
			if (userid != null) {
				/*
				 * Integer id = userid.getId().intValue();
				 * email.setUpandSendEmail(userAddEditReqBean.getEMAIL(), randomNumber);
				 * userAddEditReqBean.setUSERMASTERID(BigInteger.valueOf(id));
				 * addUserDetails(userAddEditReqBean);
				 */
				Integer id = userid.getId().intValue();
				EmailUtil eu = new EmailUtil();
				Map<String, String> notifcaion = new HashMap<String, String>();
				notifcaion.put("userId", userAddEditReqBean.getUSERID());
				notifcaion.put("appName", "entityId");
				notifcaion.put("RRN", "1234");
				notifcaion.put("CreateUser", "CreateUser");
				notifcaion.put("filepath", "");
				notifcaion.put("emailContent", "Dear customer your username is: " + userAddEditReqBean.getUSERID()
						+ " Your New Password is:" + randomNumber);
				notifcaion.put("emailId", userAddEditReqBean.getEMAIL());
				notifcaion.put("deviceTokenId", ""); // Need to check
				notifcaion.put("subject", "User credentials for Login"); // Need to check
				boolean notifcaionSent = eu.sendEmail(notifcaion);
				userAddEditReqBean.setUSERMASTERID(BigInteger.valueOf(id));
				addUserDetails(userAddEditReqBean);

			}

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
		LOGGER.info("AdministrationController->AdminstartionService->createUser----------End");
		return true;
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRED)
	public void addUserDetails(UserAddEditRequestBean userAddEditReqBean) {
		Date date = new Date();
		UserDetails userDetails = new UserDetails();
		// userDetails.setIsActive("Y");
		try {
			userDetails.setUserMasterId(userAddEditReqBean.getUSERMASTERID().intValue());
			userDetails.setRoleid(userAddEditReqBean.getROLEID().intValue());
			userDetails.setEmail(userAddEditReqBean.getEMAIL());
			// userDetails.setIsDeleted('N');
			userDetails.setMobileNumber(userAddEditReqBean.getMobileNumber());
			userDetails.setCreatedOn(date);
			userDetails.setStatusId(BigInteger.valueOf(36));
			userDetails.setPhonenumber(userAddEditReqBean.getPHONENUMBER());
			// userDetails.setUpdatedBy(userAddEditReqBean.getUPDATEBY());
			userDetails.setFirstName(userAddEditReqBean.getFIRSTNAME());
			userDetails.setLastName(userAddEditReqBean.getLASTNAME());
			userDetails.setCreatedBy(userAddEditReqBean.getCREATEDBY());
			// userDetails.setUpdatedOn(date);

			userDetailsRepository.save(userDetails);
		} catch (Exception e) {
			LOGGER.info("Exception:", e);

		}

	}

	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	@Override
	public User editUser(UserAddEditRequestBean userAddEditReqBean) {

		Date du = new Date();
		User user = userMasterRepository.findByid((userAddEditReqBean.getUSERMASTERID()));
		user.setUpdatedBy(userAddEditReqBean.getUPDATEBY());
		user.setUpdatedOn(du);
		user.setIsdeleted('N');
		user.setId(userAddEditReqBean.getUSERMASTERID());
		user.setThumbnail(userAddEditReqBean.getThumbnailImage());
		user.setStatusId(BigInteger.valueOf(36));
		/*
		 * if(userAddEditReqBean.getBranchcode() != null) {
		 * 
		 * if(userAddEditReqBean.getUsersType().equalsIgnoreCase(ApplicationConstants.
		 * HEAD_OFFICE)) { user.setBranchCode(userAddEditReqBean.getReportingBranch());
		 * //HEAD OFFICE user.setReportingBranch(""); }else
		 * if(userAddEditReqBean.getUsersType().equalsIgnoreCase(ApplicationConstants.
		 * ZONAL_OFFICE)) { user.setBranchCode(userAddEditReqBean.getZonalcode());
		 * //HEAD OFFICE
		 * user.setReportingBranch(userAddEditReqBean.getReportingBranch()); //ZONAL
		 * OFFICE }else { user.setBranchCode(userAddEditReqBean.getBranchcode());
		 * //BRANCH OFFICE user.setReportingBranch(userAddEditReqBean.getZonalcode());
		 * //ZONAL OFFICE }
		 * 
		 * }else{ user.setBranchCode(userAddEditReqBean.getUserbranch());
		 * user.setReportingBranch(userAddEditReqBean.getUserreportingbranch()); }
		 */
		user.setBranchCode(userAddEditReqBean.getBranchcode());
		user.setReportingBranch(userAddEditReqBean.getZonalcode());
		userMasterRepository.save(user);
		return user;

	}

	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	@Override
	public ResponseMessageBean editUserDetails(UserAddEditRequestBean userAddEditReqBean) {
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			Date du = new Date();
			UserDetails userDetails1 = userDetailsRepository
					.findByuserMasterId(userAddEditReqBean.getUSERMASTERID().intValue());
			// UserDetails ud =
			// userDetailsRepository.findByemail(userAddEditReqBean.getEMAIL());
//			if (ud != null && userAddEditReqBean.getUSERMASTERID().intValue() != ud.getUserMasterId()) {
//				response.setResponseCode("202");
//				response.setResponseMessage("Email Already Exist");
//				return response;
//			} 

			userDetails1.setEmail(userAddEditReqBean.getEMAIL());
			userDetails1.setPhonenumber(userAddEditReqBean.getPHONENUMBER());
			userDetails1.setFirstName(userAddEditReqBean.getFIRSTNAME());
			userDetails1.setLastName(userAddEditReqBean.getLASTNAME());
			userDetails1.setRoleid(userAddEditReqBean.getROLEID().intValue());
			userDetails1.setUpdatedOn(du);
			userDetails1.setUserMasterId(userAddEditReqBean.getUSERMASTERID().intValue());
			userDetails1.setCreatedBy(userAddEditReqBean.getCREATEDBY());
			userDetails1.setUpdatedBy(userAddEditReqBean.getUPDATEBY());
			userDetails1.setStatusId(BigInteger.valueOf(36));

			userDetailsRepository.save(userDetails1);
			response.setResponseCode("200");
			response.setResponseMessage("User Modified Successfully!");
		} catch (Exception e) {
			LOGGER.info("Exception:", e);

		}
		return response;

	}

	@Override
	public ResponseMessageBean checkUser(UserAddEditRequestBean userAddEditReqBean) {
		return userDao.checkUser(userAddEditReqBean);
	}

	@Override
	public List<UserDetailsBean> getUserDetails(int id) {
		return userDao.getUserDetails(id);
	}

	@Override
	public boolean updateUserStatus(UserAddEditRequestBean userAddEditReqBean) {
		Boolean response = false;
		response = userDao.updateUserStatus(userAddEditReqBean);
		if (response != true) {
			response = userDao.updateUserStatusDetail(userAddEditReqBean);
		}

		return response;
	}

	@Override
	public boolean updateBranchStatus(String userCode, String oldBranchCode, String newBranchCode, int roleId,
			String mobileNumber, String emailId) {
		return userDao.updateBranchStatus(userCode, oldBranchCode, newBranchCode, roleId, mobileNumber, emailId);
	}

	@Override
	public List<User> getTemplateByUserId(String userId) {
		List<User> template = userMasterRepository.findByuserid(userId);
		return template;
	}

	@Override
	public List<Role> getAllRoles() {
		return userDao.getAllRoles();
	}

	@Override
	public List<Role> getActiveRoles(BigInteger roleType) {
		return userDao.getActiveRoles(roleType);
	}

	@Override
	public List<Role> getRoleDetails(BigInteger id) {

		return userDao.getRoleDetails(id);
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	public Boolean modifyRoleDetails(BigInteger id) {
		try {
			Role role = roleRepository.findByid(new BigDecimal(id));
			role.setStatusId(BigDecimal.valueOf(10));
			roleRepository.save(role);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	public boolean addRoleDetails(Role role) {
		Date date = new Date();
		try {
			Role roleNew = roleRepository.findByCodeAndNameAndStatusId(role.getCode(), role.getName(),
					role.getStatusId());
			if (null != roleNew) {
				return false;
			} else {

				// create Role Type
				RoleTypesEntity roleType = new RoleTypesEntity();
				roleType.setAppId(new BigDecimal(5));
				roleType.setCreatedBy(role.getCreatedBy());
				roleType.setCreatedOn(date);
				roleType.setName(role.getName());
				roleType.setStatusId(role.getStatusId());
				roleType = roleTypeRepository.save(roleType);

				//create Role
				role.setCreatedOn(date);
				role.setUpdatedOn(date);
				role.setRoleType(roleType.getId());
				roleRepository.save(role);

				return true;
			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	public Boolean editRoleDetails(Role role) {
		Date date = new Date();
		try {
			Role roleNew = roleRepository.getOne(role.getId());
			roleNew.setCode(role.getCode());

			roleNew.setUpdatedOn(date);

			roleNew.setName(role.getName());
			roleNew.setDescription(role.getDescription());
			roleNew.setUpdatedBy(role.getUpdatedBy());
//			roleNew.setRoleType(role.getRoleType());
			roleRepository.save(roleNew);
			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}

	@Override
	public boolean updateRolesStatus(Role role) {
		return userDao.updateRolesStatus(role);
	}

	@Override
	public ResponseMessageBean getRoleByRoleCodeAndName(String code, String displayName) {
		return userDao.getRoleByRoleCodeAndName(code, displayName);
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	public boolean resetUserPass(UserDetails user) {
		RandomNumberGenerator object = new RandomNumberGenerator();
		String newpassword = object.generateRandomString();
		// String encryptpass = EncryptDeryptUtility.md5(newpassword);
		try {
			String encryptpass = PasswordSecurityUtil.toHexString(PasswordSecurityUtil.getSHA(newpassword));
			System.out.println(encryptpass);

			User oneUser = userMasterRepository.findByid(BigInteger.valueOf(user.getUserMasterId()));
			oneUser.setPassword(encryptpass);
			userMasterRepository.save(oneUser);
			// email.setUpandSendEmail(user.getEmail(), newpassword);
			String userToken = adminEmailUtil.generateEncSessionId();
			String resetLink = pwdRestLink;
			resetLink = resetLink.concat(userToken);
			System.out.println("link:------->" + resetLink);

			// save to admin user cred session table
			adminDao.saveToUserCredentialsSession(user.getEmail(), user.getMobileNumber(), userToken,
					new BigDecimal(user.getUserMasterId()), oneUser.getUserid());

			// send email with link
			adminEmailUtil.sendEmailWithLink(user.getEmail(), resetLink);

			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}

	@Override
	public boolean forgetPassword(ChangePasswordBean chanegebean) {
		return userDao.forgetPassword(chanegebean);
	}

	@Override
	public boolean logoutByUserId(String userid) {
		return userDao.logoutByUserId(userid);

	}

	@Override
	public boolean changePassword(ChangePasswordBean chanegebean) {
		return userDao.changePassword(chanegebean);
	}

	@Override
	public boolean addAccountType(AccountTypesEntity accountTypeData) {
		return userDao.addAccountType(accountTypeData);
	}

	@Override
	public boolean updateAccountType(AccountTypesEntity accountTypeData) {
		return userDao.updateAccountType(accountTypeData);
	}

	@Override
	public List<AccountTypesEntity> getAccountTypeById(int id) {
		return userDao.getAccountTypeById(id);
	}

	@Override
	public List<AccountTypesEntity> getAllAccountTypes() {
		return userDao.getAllAccountTypes();
	}

	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	@Override
	public User editUserImage(User userData) {
		Date du = new Date();
		User user = userMasterRepository.findByid(BigInteger.valueOf(userData.getId().intValue()));
		user.setUpdatedBy(userData.getUpdatedBy());
		user.setUpdatedOn(du);

		user.setThumbnail(userData.getThumbnail());
		userMasterRepository.save(user);
		return user;
	}

	@Override
	public Boolean addActivitySetting(ActivitySettingMasterEntity activity) {
		return userDao.addActivitySetting(activity);

	}

	@Override
	public Boolean updateActivitySettingDetails(ActivitySettingMasterEntity activity) {
		return userDao.updateActivitySettingDetails(activity);

	}

	@Override
	public List<AdminUserActivityLogs> getAdminUserActivityLogsDetails() {

		return userDao.getAdminUserActivityLogsDetails();
	}

	@Override
	public List<AdminUserActivityLogs> getAdminPortalUserActivityLogsDetails() {

		return userDao.getAdminPortalUserActivityLogsDetails();
	}

	@Override
	public List<AdminWorkflowHistoryEntity> getAdminWorkflowHistoryDetails() {

		return userDao.getAdminWorkflowHistoryDetails();
	}

	@Override
	public List<AdminWorkflowHistoryEntity> getAdminWorkflowHistoryDetailsById(int refId, int pageId,
			BigDecimal createdBy) {
		return userDao.getAdminWorkflowHistoryDetailsById(refId, pageId, createdBy);
	}

	@Override
	public List<ActivitySettingMasterEntity> getAllActivitySetting() {
		return userDao.getAllActivitySetting();
	}

	@Override
	public List<ActivitySettingMasterEntity> getActivitySettingById(int id) {
		return userDao.getActivitySettingById(id);
	}

	@Override
	public List<ActivitySettingMasterEntity> getAllActivitySettingByAppId(int appId) {
		return userDao.getAllActivitySettingByAppId(appId);
	}

	@Override
	public List<ActivitySettingMasterEntity> getAllActivitySettingForAdmin() {
		return userDao.getAllActivitySettingForAdmin();
	}

	@Override
	public List<ActivitySettingMasterEntity> getAllActivitySettingForAdminByAppID(int appId) {
		return userDao.getAllActivitySettingForAdminByAppID(appId);
	}

	@Override
	public boolean addOmniLimitMaster(OmniLimitMasterEntity omniData) {
		return userDao.addOmniLimitMaster(omniData);
	}

	@Override
	public boolean updateOmniLimitMaster(OmniLimitMasterEntity omniData) {
		return userDao.updateOmniLimitMaster(omniData);
	}

	@Override
	public List<OmniLimitMasterEntity> getOmniLimitMasterById(int id) {
		return userDao.getOmniLimitMasterById(id);
	}

	@Override
	public List<OmniLimitMasterEntity> getAllOmniLimitMaster() {
		return userDao.getAllOmniLimitMaster();
	}

	@Override
	public List<AdminUserActivityLogs> getAdminPortalUserActivityLogsDetailsByDate(DateBean datebean) {
		return userDao.getAdminPortalUserActivityLogsDetailsByDate(datebean);
	}

	@Override
	public List<ActivityMasterBean> getDistictActivityMaster() {
		return userDao.getActivityMaster();
	}

	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	@Override
	public List<User> getHeadOffice() {

		List<User> template = userMasterRepository.getHeadOffice();
		return template;
	}

	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	@Override
	public List<User> getZonalOffice(String id1) {

		List<User> template = userMasterRepository.getZonalOffice(id1);
		return template;
	}

	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	@Override
	public List<User> getBranchOffice(String id1) {

		List<User> template = userMasterRepository.getBranchOffice(id1);
		return template;
	}

	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	@Override
	public List<AccountOpeningDetailsEntity> getAccountOpeningDetailsCif(String cifNo) {
		List<AccountOpeningDetailsEntity> template = accountOpeningDetailsRepository.getAccountOpeningDetailsCif(cifNo);
		return template;
	}

	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	@Override
	public List<AccountOpeningDetailsEntity> getAccountOpeningDetailsMobileNo(String id1) {
		List<AccountOpeningDetailsEntity> template = accountOpeningDetailsRepository
				.getAccountOpeningDetailsMobileNo(id1);
		return template;
	}

	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	@Override
	public List<AccountOpeningDetailsEntity> getAccountOpeningDetailsAccNo(String accNo) {
		List<AccountOpeningDetailsEntity> template = accountOpeningDetailsRepository
				.getAccountOpeningDetailsAccNo(accNo);
		return template;
	}

	@Override
	public List<FraudReportingBean> getFraudRepotingDetails(String date1, String date2) {
		return userDao.getFraudRepotingDetails(date1, date2);
	}

	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	@Override
	public List<Object[]> getCbsZonalOffice(String id1) {
		List<Object[]> template = cbsBranchListRepository.getZonalffice(id1);
		return template;
	}

	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	@Override
	public List<CbsBranchList> getCbsBranchOffice() {
		List<CbsBranchList> template = cbsBranchListRepository.getBranchOffice();
		return template;
	}

	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	@Override
	public Boolean getBranchAvailability(String id1) {
		int count = 0;
		try {
			count = cbsBranchListRepository.getBranchAvailability(id1);

		} catch (Exception e) {
			LOGGER.info("Exception:", e);

		}

		if (count == 1) {
			return true;
		} else {
			return false;
		}
	}

	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	@Override
	public List<CbsBranchList> getCbsBranchData(String id1) {
		List<CbsBranchList> template = cbsBranchListRepository.getCbsBranchData(id1);
		return template;
	}

	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	@Override
	public String getCbsBranchName(String branchCode) {
		String template = cbsBranchListRepository.getCbsBranchName(branchCode);
		return template;
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	public List<Object[]> getAllMakerUsers() {
		List<Object[]> usernew = userMasterRepository.getAllMakerUsers();
		return usernew;
	}

	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	@Override
	public User rejectApprovedUserMaster(String userId, String flag, String remark, String users) {
		Date du = new Date();
		String create = new SimpleDateFormat("dd-MMM-yy hh.mm.ss.SSSSSSSSS a").format(du);
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yy hh.mm.ss.SSSSSSSSS a");
		Date date1 = null;
		try {
			date1 = formatter.parse(create);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		User user = userMasterRepository.getUserMakerDetails(BigInteger.valueOf(Integer.valueOf(userId)));
		user.setUpdatedOn(date1);
		user.setUpdatedBy(users);
		if (!ObjectUtils.isEmpty(remark)) {
			user.setStatusId(BigInteger.valueOf(35));
			user.setRemark(remark);
		} else {
			user.setStatusId(BigInteger.valueOf(3));
			user.setRemark("");
		}
//		user.setIsdeleted('N');
//		user.setId(userAddEditReqBean.getUSERMASTERID());
//		user.setThumbnail(userAddEditReqBean.getThumbnailImage());
//		user.setBranchCode(userAddEditReqBean.getUserbranch());
//		user.setReportingBranch(userAddEditReqBean.getUserreportingbranch()); 
		userMasterRepository.save(user);
		return user;

	}

	@Override
	public ResponseMessageBean rejectApprovedUserDetails(String userId, String flag, String remark, String users) {
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			Date du = new Date();
			UserDetails userDetails1 = userDetailsRepository.findByuserMasterId(Integer.valueOf(userId));
			userDetails1.setUpdatedOn(du);
			userDetails1.setUpdatedBy(users);
			if (!ObjectUtils.isEmpty(remark)) {
				userDetails1.setStatusId(BigInteger.valueOf(35));
			} else {
				userDetails1.setStatusId(BigInteger.valueOf(3));
			}
			userDetailsRepository.save(userDetails1);
			response.setResponseCode("200");
			if (!ObjectUtils.isEmpty(remark)) {
				response.setResponseMessage("User Rejected Successfully!");
			} else {
				response.setResponseMessage("User Created Successfully!");
			}

		} catch (Exception e) {
			LOGGER.info("Exception:", e);

		}
		return response;

	}

	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	@Override
	public List<UserDetailsBean> getZomnalUserList(String id1) {
		return userDao.getZomnalUserList(id1);
	}

	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	@Override
	public List<Object[]> getAccountOpeningDetails(String id1) {
		List<Object[]> template = accountOpeningDetailsRepository.getAccountOpeningDetails(id1);
		return template;
	}

	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	@Override
	public List<CbsBranchList> getCbsBranchOfficeZonal(String id1) {
		List<CbsBranchList> template = cbsBranchListRepository.getCbsBranchOfficeZonal(id1);
		return template;
	}

	@Override
	public ResponseMessageBean checkUsers(User user) {
		return userDao.checkUsers(user);
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	public List<Object[]> getAllMakerZonalUsers(String id1) {
		List<Object[]> usernew = userMasterRepository.getAllMakerZonalUsers(id1);
		return usernew;
	}

	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	@Override
	public String getCbsZonalName(String branchCode) {
		String template = cbsBranchListRepository.getCbsZonalName(branchCode);
		return template;
	}
	/*
	 * @SuppressWarnings("unchecked") public boolean sendEmail(Map<String, String>
	 * notificationData) { try { System.out.println("notificationData Email--->" +
	 * notificationData.toString()); notificationData.put("notificationType",
	 * "EMAIL"); String notificationEngineUrl = notificationEngineUrlEmail;
	 * Map<String, String> response = null; HttpHeaders headers = new HttpHeaders();
	 * headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	 * HttpEntity<Map<String, String>> entity = new HttpEntity<Map<String,
	 * String>>(notificationData, headers); response =
	 * restTemplate.exchange(notificationEngineUrl, HttpMethod.POST, entity,
	 * Map.class).getBody(); if (null != response &&
	 * response.get("responseCode").equals("200")) { return true; } else { return
	 * false; } } catch (Exception e) { LOGGER.error(e); return false; } }
	 */

	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	@Override
	public List<Object[]> getZonalDetails() {
		List<Object[]> template = cbsBranchListRepository.getZonalDetails();
		return template;
	}

}
