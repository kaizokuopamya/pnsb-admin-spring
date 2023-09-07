package com.itl.pns.controller;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
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

import com.itl.pns.bean.ActivityMasterBean;
import com.itl.pns.bean.AdminUserActivityLogs;
import com.itl.pns.bean.ChangePasswordBean;
import com.itl.pns.bean.DataSet;
import com.itl.pns.bean.DateBean;
import com.itl.pns.bean.FraudReportingBean;
import com.itl.pns.bean.MiddleWareRequest;
import com.itl.pns.bean.MiddleWareResponse;
import com.itl.pns.bean.RequestParamBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.bean.UserAddEditRequestBean;
import com.itl.pns.bean.UserDetailsBean;
import com.itl.pns.constant.ApplicationConstants;
import com.itl.pns.corp.dao.CorpMakerCheckerDao;
import com.itl.pns.dao.AdministrationDao;
import com.itl.pns.entity.AccountOpeningDetailsEntity;
import com.itl.pns.entity.AccountTypesEntity;
import com.itl.pns.entity.ActivitySettingMasterEntity;
import com.itl.pns.entity.AdminWorkflowHistoryEntity;
import com.itl.pns.entity.CustomerEntity;
import com.itl.pns.entity.OmniLimitMasterEntity;
import com.itl.pns.entity.Role;
import com.itl.pns.entity.User;
import com.itl.pns.entity.UserCredentialsSessionEntity;
import com.itl.pns.entity.UserDetails;
import com.itl.pns.repository.LanguageMasterRepository;
import com.itl.pns.service.AdministrationService;
import com.itl.pns.util.AdminWorkFlowReqUtility;
import com.itl.pns.util.EncryptorDecryptor;
import com.itl.pns.util.Utils;

@RestController
@RequestMapping("admin")
public class AdministrationController {

	private static final Logger logger = LogManager.getLogger(AdministrationController.class);

	@Value("${bot.image.folder}")
	private String botImageFolder;

	@Autowired
	AdminWorkFlowReqUtility adminWorkFlowReqUtility;

	@Autowired
	CorpMakerCheckerDao corpMakerCheckerDao;

	@Autowired
	private AdministrationService administrationService;

	@Autowired
	private AdministrationDao adminDao;

	@Autowired
	private LanguageMasterRepository languageMasterRepository;

	@RequestMapping(value = "/getAllUsers", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> listAllUsers() {

		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<UserDetailsBean> users = administrationService.getUserList();

			if (!users.isEmpty()) {
				res.setResponseCode("200");
				res.setResult(users);
			} else {
				res.setResponseCode("202");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/deleteUsersDetails", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> deleteUser(@RequestBody RequestParamBean requestBean) {
		ResponseMessageBean bean = new ResponseMessageBean();
		try {
			Boolean response = administrationService.deleteUser(new BigInteger(requestBean.getId1()));

			if (response) {
				bean.setResponseCode("200");
				bean.setResponseMessage("Deleted Successfully");
			} else {
				bean.setResponseCode("202");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}
		return new ResponseEntity<>(bean, HttpStatus.OK);
	}

	@RequestMapping(value = "/getUserDetailsEdit", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getUserDetailsForEdit(@RequestBody RequestParamBean requestBean) {
		ResponseMessageBean bean = new ResponseMessageBean();
		try {
			List<UserDetailsBean> listUser = administrationService
					.getUserDetails(Integer.parseInt(requestBean.getId1()));

			if (null != listUser) {
				bean.setResponseCode("200");
				bean.setResult(listUser);
			} else {
				bean.setResponseCode("202");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}
		return new ResponseEntity<>(bean, HttpStatus.OK);
	}

	@RequestMapping(value = "/addUserDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> addUserDetails(@RequestBody UserAddEditRequestBean useraddeditreqbean) {
		logger.info("AdministrationController->addUserDetails----------Start");
		ResponseMessageBean responsecode = new ResponseMessageBean();
		Boolean response = false;
		Boolean res = false;
		try {
			useraddeditreqbean.setLOGINTYPE(useraddeditreqbean.getLOGINTYPE());
			ResponseMessageBean responseRole = null;

			if (useraddeditreqbean.getROLENAME() != null) {
				Role role = new Role();
				role.setName(useraddeditreqbean.getROLENAME());
				role.setCreatedBy(new BigDecimal(useraddeditreqbean.getCREATEDBY()));
				String str = useraddeditreqbean.getROLENAME();
				role.setDescription(useraddeditreqbean.getROLENAME());
				role.setCode(str.replaceAll("\\s", ""));
				role.setUpdatedBy(useraddeditreqbean.getCREATEDBY().toString());
				role.setStatusId(BigDecimal.valueOf(3));
				useraddeditreqbean.setLOGINTYPE(useraddeditreqbean.getLOGINTYPE());
				responseRole = adminDao.checkIsRoleExist(role.getName());
				useraddeditreqbean.setThumbnailImage(useraddeditreqbean.getTHUMBNAIL());

				if (responseRole.getResponseCode().equalsIgnoreCase("200")) {
					res = administrationService.addRoleDetails(role);
					List<Role> roles = adminDao.getRolesByDisplayName(role);
					useraddeditreqbean.setROLEID(roles.get(0).getId().toBigInteger());
				} else {
					List<Role> roles = adminDao.getRolesByDisplayName(role);
					useraddeditreqbean.setROLEID(roles.get(0).getId().toBigInteger());
				}
			}

			if (!ObjectUtils.isEmpty(useraddeditreqbean)) {
				responsecode = administrationService.checkUser(useraddeditreqbean);// Check if user already exists

				if (responsecode.getResponseCode().equalsIgnoreCase("200")) {

					response = administrationService.createUser(useraddeditreqbean);
					if (response) {
						responsecode.setResponseCode("200");
						responsecode.setResponseMessage("User Added Successfully!");
					} else {
						responsecode.setResponseCode("500");
						responsecode.setResponseMessage("Error While Adding User!");
					}
				}
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
			responsecode.setResponseCode("500");
			responsecode.setResponseMessage("Error While Adding User!");
		}
		logger.info("AdministrationController->addUserDetails------End");
		return new ResponseEntity<>(responsecode, HttpStatus.OK);
	}

	@RequestMapping(value = "/editUserDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> editUserDetails(@RequestBody UserAddEditRequestBean useraddeditreqbean) {
		ResponseMessageBean response = null;
		try {
			useraddeditreqbean.setThumbnailImage(useraddeditreqbean.getTHUMBNAIL());
			// User user = new User();

			User usr = administrationService.editUser(useraddeditreqbean);
			useraddeditreqbean.setUSERMASTERID(usr.getId());
			response = administrationService.editUserDetails(useraddeditreqbean);
		} catch (Exception e) {
			logger.info("Exception:", e);
			response = new ResponseMessageBean();
			response.setResponseCode("202");
			response.setResponseMessage("Error Occured!");
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateUserStatus", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateTemplateStatus(
			@RequestBody UserAddEditRequestBean useraddeditreqbean) {
		useraddeditreqbean.setUpdatedBy(new BigDecimal(useraddeditreqbean.getUSERID()));
		boolean isUpdated = administrationService.updateUserStatus(useraddeditreqbean);
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			if (isUpdated) {
				response.setResponseCode("200");
				response.setResponseMessage("User Status Updated Successfully!");
			} else {
				response.setResponseCode("202");
				response.setResponseMessage("Error Occured!");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/getAllRoles", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getAllRoles() {
		ResponseMessageBean res = new ResponseMessageBean();
		List<Role> roles = administrationService.getAllRoles();
		try {
			if (!roles.isEmpty()) {
				res.setResponseCode("200");
				res.setResult(roles);
			} else {
				res.setResponseCode("202");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/getActiveRoles", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getActiveRoles(@RequestBody RequestParamBean requestBean) {
		ResponseMessageBean res = new ResponseMessageBean();
		List<Role> roles = administrationService
				.getActiveRoles(BigInteger.valueOf(Long.valueOf(requestBean.getRoleTypeId())));

		logger.info(roles);
		try {
			if (!roles.isEmpty()) {
				res.setResponseCode("200");
				res.setResponseMessage("Success");
				res.setResult(roles);

			} else {
				res.setResponseCode("202");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}

		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/addRole", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> addRoleDetails(@RequestBody Role role, HttpServletRequest httpRequest) {
		ResponseMessageBean response = null;
		Boolean res = false;
		try {
			if (!ObjectUtils.isEmpty(role)) {

				response = administrationService.getRoleByRoleCodeAndName(role.getCode(), role.getName());
				if (response.getResponseCode().equalsIgnoreCase("200")) {
					role.setCreatedBy(Utils.getUpdatedBy(httpRequest));
					res = administrationService.addRoleDetails(role);
					if (res) {
						response = new ResponseMessageBean();
						response.setResponseCode("200");
						response.setResponseMessage("Role Created Successfully!");
					} else {
						response = new ResponseMessageBean();
						response.setResponseCode("202");
						response.setResponseMessage("Role Creation Failed");
					}
				}
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
			response.setResponseCode("500");
			response.setResponseMessage("Error While Adding Role!");
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/editRolesDetails", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> editRole(@RequestBody Role role) {
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			if (role.getId() != null) {
				Boolean res = administrationService.editRoleDetails(role);
				if (res) {
					response.setResponseCode("200");
					response.setResponseMessage("Role Modified Successfully!");
				} else {
					response.setResponseCode("202");
					response.setResponseMessage("Role Modification Failed");
				}

			}
		} catch (Exception e) {
			logger.info("Exception:", e);
			response.setResponseCode("500");
			response.setResponseMessage("Error Occured!");
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/getRoleDetailsById", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getRole(@RequestBody Role role) {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<Role> roleDetails = administrationService.getRoleDetails(role.getId().toBigInteger());

			if (null != roleDetails) {
				res.setResponseCode("200");
				res.setResult(roleDetails);
			} else {
				res.setResponseCode("202");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}

		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/deleteRolesDetails", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> deleteRole(@RequestBody Role role) {
		Boolean res = false;
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			BigInteger roleid = role.getId().toBigInteger();
			res = administrationService.modifyRoleDetails(roleid);

			if (res) {
				response.setResponseCode("200");
				response.setResponseMessage("Role Deleted Successfully!");
			} else {
				response.setResponseCode("202");
				response.setResponseMessage("Error Occured While Deleting role");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateRolesStatus", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateTemplateStatus(@RequestBody Role role) {
		boolean isUpdated = administrationService.updateRolesStatus(role);
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			if (isUpdated) {
				response.setResponseCode("200");
				response.setResponseMessage("Role Status Updated Successfully!");
			} else {
				response.setResponseCode("202");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/resetUserPassword", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> resetUserPass(@RequestBody UserDetails user) {
		boolean response = false;
		ResponseMessageBean bean = new ResponseMessageBean();
		try {
			response = administrationService.resetUserPass(user);

			if (response) {
				bean.setResponseCode("200");
				bean.setResponseMessage("Password Sent To Your Email");
			} else {
				bean.setResponseCode("500");
				bean.setResponseMessage("Error occured while Reseting Password");

			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}

		return new ResponseEntity<>(bean, HttpStatus.OK);
	}

	@RequestMapping(value = "/forgetPassword", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> forgetPassword(@RequestBody ChangePasswordBean chanegebean) {
		boolean ismailsend;
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			ismailsend = administrationService.forgetPassword(chanegebean);

			if (ismailsend) {
				administrationService.logoutByUserId(chanegebean.getUSERID());
				response.setResponseCode("201");
				response.setResponseMessage("New Password Sent To Registered Mail ID");
			} else {
				response.setResponseCode("500");
				response.setResponseMessage("Username And Email Id  Does Not Match");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> changePassword(@RequestBody ChangePasswordBean chanegebean) {
		boolean ischanged;
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			if (null == chanegebean || chanegebean.getNEWPASSWORD() == null || null == chanegebean.getOLDPASSWORD()
					|| null == chanegebean.getUSERID()) {
				response.setResponseCode("500");
				response.setResponseMessage("Kindly Enter Valid Userid and Password ");

				return new ResponseEntity<>(response, HttpStatus.OK);
			}
			ischanged = administrationService.changePassword(chanegebean);
			if (ischanged) {
				administrationService.logoutByUserId(chanegebean.getUSERID());
				response.setResponseCode("201");
				response.setResponseMessage("Password Changed Successfully, Please Login With New Password");
			} else {
				response.setResponseCode("500");
				response.setResponseMessage("Kindly Enter Valid Old Password ");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/addAccountType", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> addAccountType(@RequestBody AccountTypesEntity accountTypeData) {
		logger.info("In Adminstration Controller -> addAccountType()");
		ResponseMessageBean response = new ResponseMessageBean();
		accountTypeData.setStatusid(BigDecimal.valueOf(3));
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(accountTypeData.getRole_ID().intValue());
		accountTypeData.setRoleName(roleName);
		accountTypeData.setStatusname(
				adminWorkFlowReqUtility.getStatusNameByStatusId(accountTypeData.getStatusid().intValue()));
		accountTypeData.setCreatedByName(
				adminWorkFlowReqUtility.getCreatedByNameByCreatedId(accountTypeData.getCreatedby().intValue()));
		accountTypeData.setAction("ADD");
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(accountTypeData.getActivityName());
		Boolean res = false;
		ResponseMessageBean responsecode = new ResponseMessageBean();
		try {
			if (!ObjectUtils.isEmpty(accountTypeData)) {
				responsecode = corpMakerCheckerDao.checkAccountTypeExit(accountTypeData);
				if (responsecode.getResponseCode().equalsIgnoreCase("200")) {
					res = administrationService.addAccountType(accountTypeData);
					if (res) {
						response.setResponseCode("200");
						if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getChecker()))
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

							response.setResponseMessage("Request Submitted To Corporate Checker For Approval");
						} else {
							response.setResponseMessage("Details Saved Successfully");
						}
					} else {
						response.setResponseCode("202");
						response.setResponseMessage("Error While Saving Records");
					}
				} else {
					response.setResponseCode("202");
					response.setResponseMessage(responsecode.getResponseMessage());
				}

			}
		} catch (Exception e) {
			logger.info("Exception:", e);
			response.setResponseCode("500");
			response.setResponseMessage("Error While  Adding Records!");
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateAccountType", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateAccountType(@RequestBody AccountTypesEntity accountTypeData) {
		logger.info("In Adminstration Controller -> updateAccountType()");
		ResponseMessageBean response = new ResponseMessageBean();
		Boolean res = false;

		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(accountTypeData.getRole_ID().intValue());
		accountTypeData.setRoleName(roleName);
		accountTypeData.setStatusname(
				adminWorkFlowReqUtility.getStatusNameByStatusId(accountTypeData.getStatusid().intValue()));
		accountTypeData.setCreatedByName(
				adminWorkFlowReqUtility.getCreatedByNameByCreatedId(accountTypeData.getCreatedby().intValue()));
		accountTypeData.setAction("EDIT");
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(accountTypeData.getActivityName());
		ResponseMessageBean responsecode = new ResponseMessageBean();
		try {
			if (!ObjectUtils.isEmpty(accountTypeData)) {
				responsecode = corpMakerCheckerDao.checkUpdateAccountTypeExit(accountTypeData);
				if (responsecode.getResponseCode().equalsIgnoreCase("200")) {

					res = administrationService.updateAccountType(accountTypeData);
					if (res) {
						response.setResponseCode("200");
						if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getChecker()))
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

							response.setResponseMessage("Request Submitted To Corporate Checker For Approval");
						} else {
							response.setResponseMessage("Details Updated Successfully");
						}
					} else {
						response.setResponseCode("202");
						response.setResponseMessage("Error While Updating Records");
					}
				} else {
					response.setResponseCode("202");
					response.setResponseMessage(responsecode.getResponseMessage());
				}

			}
		} catch (Exception e) {
			logger.info("Exception:", e);
			response.setResponseCode("500");
			response.setResponseMessage("Error While Updating Records!");
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/getAccountTypeById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getAccountTypeById(@RequestBody RequestParamBean requestBean) {
		logger.info("In Adminstration Controller -> getAccountTypeById()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<AccountTypesEntity> accountTypeList = administrationService
					.getAccountTypeById(Integer.parseInt(requestBean.getId1()));

			if (null != accountTypeList) {
				res.setResponseCode("200");
				res.setResult(accountTypeList);
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

	@RequestMapping(value = "/getAllAccountTypes", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getAllAccountTypes() {
		logger.info("In Adminstration Controller -> getAllAccountTypes()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<AccountTypesEntity> accountTypeList = administrationService.getAllAccountTypes();

			if (null != accountTypeList) {
				res.setResponseCode("200");
				res.setResult(accountTypeList);
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

	@RequestMapping(value = "/editUserImage", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> editUserImage(@RequestBody User user) {

		ResponseMessageBean response = new ResponseMessageBean();

		try {

			user.setThumbnail(user.getThumbnailString());
			administrationService.editUserImage(user);
			response.setResponseCode("200");
			response.setResponseMessage("Profile Image Updated Successfully!");

		} catch (Exception e) {
			logger.info("Exception:", e);
			response.setResponseCode("202");
			response.setResponseMessage("Error Occured While Updating Image");
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/addActivitySetting", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> addActivitySetting(@RequestBody ActivitySettingMasterEntity activity) {
		logger.info("In addActivitySetting Master  Details -> addActivitySetting()");
		ResponseMessageBean response = null;
		boolean isDataRefresh = false;
		Boolean res = false;
		try {
			if (!ObjectUtils.isEmpty(activity)) {
				res = administrationService.addActivitySetting(activity);
				if (res) {

					isDataRefresh = adminWorkFlowReqUtility.refreshCacheData("ActivitySettingMasterReader");
					response = new ResponseMessageBean();
					response.setResponseCode("200");
					response.setResponseMessage("Activity Setting added Successfully!");
				} else {
					response = new ResponseMessageBean();
					response.setResponseCode("202");
					response.setResponseMessage("Failed to add activity setting");
				}
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
			response.setResponseCode("500");
			response.setResponseMessage("Error While Adding Activity Setting!");
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateActivitySetting", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateActivitySetting(
			@RequestBody ActivitySettingMasterEntity activity) {
		logger.info("In updateActivitySetting Master  Details -> updateActivitySetting()");
		boolean isDataRefresh = false;
		ResponseMessageBean response = new ResponseMessageBean();
		ResponseMessageBean responsecode = new ResponseMessageBean();
		Boolean res = false;
		try {
			if (!ObjectUtils.isEmpty(activity)) {
				res = administrationService.updateActivitySettingDetails(activity);
				if (res) {

					isDataRefresh = adminWorkFlowReqUtility.refreshCacheData("ActivitySettingMasterReader");
					response.setResponseCode("200");
					response.setResponseMessage("Activity Setting Updated Successfully");
				} else {
					response.setResponseCode("202");
					response.setResponseMessage("Error While Updating Records");
				}

			}
		} catch (Exception e) {
			logger.info("Exception:", e);
			responsecode.setResponseCode("500");
			responsecode.setResponseMessage("Error While Updating Records!");
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/getAllActivitySetting", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getAllActivitySetting() {
		logger.info("In Adminstration Controller -> getAllActivitySetting()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<ActivitySettingMasterEntity> activitySettingData = administrationService.getAllActivitySetting();

			if (null != activitySettingData) {
				res.setResponseCode("200");
				res.setResult(activitySettingData);
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

	@RequestMapping(value = "/getAllActivitySettingByAppId", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getAllActivitySettingByAppId(@RequestBody RequestParamBean requestBean) {
		logger.info("In Adminstration Controller -> getAllActivitySettingByAppId()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<ActivitySettingMasterEntity> activitySettingData = administrationService
					.getAllActivitySettingByAppId(Integer.parseInt(requestBean.getId1()));

			if (null != activitySettingData) {
				res.setResponseCode("200");
				res.setResult(activitySettingData);
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

	@RequestMapping(value = "/getActivitySettingById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getActivitySettingById(@RequestBody RequestParamBean requestBean) {
		logger.info("In Adminstration Controller -> getActivitySettingById()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<ActivitySettingMasterEntity> activitySettingData = administrationService
					.getActivitySettingById(Integer.parseInt(requestBean.getId1()));

			if (null != activitySettingData) {
				res.setResponseCode("200");
				res.setResult(activitySettingData);
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

	@RequestMapping(value = "/getAdminUserActivityLogs", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getAdminUserActivityLogs() {
		logger.info("In getAdminUserActivityLogs  Details -> getAdminUserActivityLogs()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<AdminUserActivityLogs> activityLogDetails = administrationService.getAdminUserActivityLogsDetails();

			if (null != activityLogDetails) {
				res.setResponseCode("200");
				res.setResult(activityLogDetails);
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

	@RequestMapping(value = "/getAdminPortalUserActivityLogs", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getAdminPortalUserActivityLogs() {
		logger.info("In getAdminPortalUserActivityLogs  Details -> getAdminPortalUserActivityLogs()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<AdminUserActivityLogs> activityLogDetails = administrationService
					.getAdminPortalUserActivityLogsDetails();

			if (null != activityLogDetails) {
				res.setResponseCode("200");
				res.setResult(activityLogDetails);
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

	@RequestMapping(value = "/getAdminWorkflowHistoryDetails", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getAdminWorkflowHistory() {
		logger.info("In getAdminWorkflowHistoryDetails  Details -> getAdminWorkflowHistoryDetails()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<AdminWorkflowHistoryEntity> activityLogDetails = administrationService
					.getAdminWorkflowHistoryDetails();

			if (!ObjectUtils.isEmpty(activityLogDetails)) {
				res.setResponseCode("200");
				res.setResult(activityLogDetails);
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

	@RequestMapping(value = "/getAdminWorkflowHistoryDetailsById", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getAdminWorkflowHistoryDetailsById(
			@RequestBody RequestParamBean requestBean, HttpServletRequest httpRequest) {

		logger.info(requestBean.toString());
		logger.info("In getAdminWorkflowHistoryDetails  Details -> getAdminWorkflowHistoryDetailsById()");
		ResponseMessageBean res = new ResponseMessageBean();
		logger.info("ReferenceId: " + requestBean.getId1() + " and PageId: " + requestBean.getId2());
		List<AdminWorkflowHistoryEntity> activityLogDetails = administrationService.getAdminWorkflowHistoryDetailsById(
				Integer.parseInt(requestBean.getId1()), Integer.parseInt(requestBean.getId2()),
				Utils.getUpdatedBy(httpRequest));
		try {
			if (!ObjectUtils.isEmpty(activityLogDetails)) {
				res.setResponseCode("200");
				res.setResult(activityLogDetails);
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

	@RequestMapping(value = "/getAllActivitySettingForAdmin", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getAllActivitySettingForAdmin() {
		logger.info("In Adminstration Controller -> getAllActivitySettingForAdmin()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<ActivitySettingMasterEntity> activitySettingData = administrationService
					.getAllActivitySettingForAdmin();

			if (null != activitySettingData) {
				res.setResponseCode("200");
				res.setResult(activitySettingData);
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

	@RequestMapping(value = "/getAllActivitySettingForAdminByAppID", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getAllActivitySettingForAdminByAppID(
			@RequestBody RequestParamBean requestBean) {
		logger.info("In Adminstration Controller -> getAllActivitySettingForAdminByAppID()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<ActivitySettingMasterEntity> activitySettingData = administrationService
					.getAllActivitySettingForAdminByAppID(Integer.parseInt(requestBean.getId1()));

			if (null != activitySettingData) {
				res.setResponseCode("200");
				res.setResult(activitySettingData);
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

	@RequestMapping(value = "/getFreezeAcountDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getFreezeAcountDetails() {
		logger.info("In Adminstration Controller -> getFreezeAcountDetails()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<ActivitySettingMasterEntity> activitySettingData = null;

			if (null != activitySettingData) {
				res.setResponseCode("200");
				res.setResult(activitySettingData);
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

	// static cal ...CBS cal--- by acc no
	@RequestMapping(value = "/getFreezeAcountDetailsByAccNo", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getFreezeAcountDetailsByAccNo(
			@RequestBody RequestParamBean requestBean) {
		logger.info("In Adminstration Controller -> getFreezeAcountDetailsByAccNo()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<ActivitySettingMasterEntity> activitySettingData = null;

			if (null != activitySettingData) {
				res.setResponseCode("200");
				res.setResult(activitySettingData);
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

	// cbs call -to freeze or unfreeze account
	@RequestMapping(value = "/updateFreezeAccount", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateFreezeAcount(@RequestBody CustomerEntity CustomerData) {
		logger.info("In Adminstration Controller -> updateFreezeAcount()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			if (!ObjectUtils.isEmpty(CustomerData)) {
				res.setResponseCode("200");
				res.setResult(CustomerData);
				res.setResponseMessage("Account Unfreeze Scuuessfully");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);

	}

	@RequestMapping(value = "/addOmniLimitMaster", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> addOmniLimitMaster(@RequestBody OmniLimitMasterEntity omniData) {
		logger.info("In Adminstration Controller -> addOmniLimitMaster()");
		ResponseMessageBean response = new ResponseMessageBean();
		omniData.setCreatedByName(
				adminWorkFlowReqUtility.getCreatedByNameByCreatedId(omniData.getCreatedBy().intValue()));
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(omniData.getRole_ID().intValue());
		omniData.setRoleName(roleName);
		omniData.setAction("ADD");
		omniData.setStatusName(adminWorkFlowReqUtility.getStatusNameByStatusId(omniData.getStatusId().intValue()));
		omniData.setApppName(adminWorkFlowReqUtility.getAppNameByAppId(omniData.getAppId().intValue()));
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(omniData.getActivityName());

		Boolean res = false;
		try {
			if (!ObjectUtils.isEmpty(omniData)) {
				ResponseMessageBean responsecode = new ResponseMessageBean();

				responsecode = adminDao.isLimitExist(omniData);
				if (responsecode.getResponseCode().equalsIgnoreCase("200")) {
					res = administrationService.addOmniLimitMaster(omniData);
					if (res) {
						response.setResponseCode("200");
						if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getChecker()))
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

							response.setResponseMessage("Request Submitted To Admin Checker For Approval");
						} else {
							response.setResponseMessage("Omni Limit Details Saved Successfully");
						}

					} else {
						response.setResponseCode("202");
						response.setResponseMessage("Error While Saving Records");
					}
				} else {
					response.setResponseCode("202");
					response.setResponseMessage(responsecode.getResponseMessage());
				}

			}
		} catch (Exception e) {
			logger.info("Exception:", e);
			response.setResponseCode("500");
			response.setResponseMessage("Error While  Adding Records!");
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateOmniLimitMaster", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateOmniLimitMaster(@RequestBody OmniLimitMasterEntity omniData) {
		logger.info("In Adminstration Controller -> updateOmniLimitMaster()");
		ResponseMessageBean response = new ResponseMessageBean();
		ResponseMessageBean responsecode = new ResponseMessageBean();
		omniData.setCreatedByName(
				adminWorkFlowReqUtility.getCreatedByNameByCreatedId(omniData.getCreatedBy().intValue()));
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(omniData.getRole_ID().intValue());
		omniData.setRoleName(roleName);
		omniData.setAction("ADD");
		omniData.setStatusName(adminWorkFlowReqUtility.getStatusNameByStatusId(omniData.getStatusId().intValue()));
		omniData.setApppName(adminWorkFlowReqUtility.getAppNameByAppId(omniData.getAppId().intValue()));
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(omniData.getActivityName());

		Boolean res = false;
		try {
			if (!ObjectUtils.isEmpty(omniData)) {

				responsecode = adminDao.isUpdateLimitExist(omniData);
				if (responsecode.getResponseCode().equalsIgnoreCase("200")) {

					res = administrationService.updateOmniLimitMaster(omniData);
					if (res) {
						response.setResponseCode("200");
						if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getChecker()))
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

							response.setResponseMessage("Request Submitted To Admin Checker For Approval");
						} else {
							response.setResponseMessage("Omni Limit Details Updated Successfully");
						}

					} else {
						response.setResponseCode("202");
						response.setResponseMessage("Error While Updating Records");
					}
				} else {
					response.setResponseCode("202");
					response.setResponseMessage(responsecode.getResponseMessage());
				}

			}
		} catch (Exception e) {
			logger.info("Exception:", e);
			response.setResponseCode("500");
			response.setResponseMessage("Error While Updating Records!");
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/getOmniLimitMasterById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getOmniLimitMasterById(@RequestBody RequestParamBean requestBean) {
		logger.info("In Adminstration Controller -> getOmniLimitMasterById()");
		ResponseMessageBean res = new ResponseMessageBean();
		List<OmniLimitMasterEntity> omniData = administrationService
				.getOmniLimitMasterById(Integer.parseInt(requestBean.getId1()));
		try {
			if (null != omniData) {
				res.setResponseCode("200");
				res.setResult(omniData);
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

	@RequestMapping(value = "/getAllOmniLimitMaster", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getAllOmniLimitMaster() {
		logger.info("In Category Controller -> getAllOmniLimitMaster()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<OmniLimitMasterEntity> omniData = administrationService.getAllOmniLimitMaster();

			if (null != omniData) {
				res.setResponseCode("200");
				res.setResult(omniData);
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

	@RequestMapping(value = "/getAdminPortalUserActivityLogsDetailsByDate", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getAdminPortalUserActivityLogsDetailsByDate(
			@RequestBody DateBean datebean) {

		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<AdminUserActivityLogs> list = administrationService
					.getAdminPortalUserActivityLogsDetailsByDate(datebean);

			if (!ObjectUtils.isEmpty(list)) {
				res.setResponseCode("200");
				res.setResult(list);
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

	@RequestMapping(value = "/getDistinctActivityMasterRecords", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getDistinctActivityMasterRecords() {

		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<ActivityMasterBean> list = administrationService.getDistictActivityMaster();

			if (null != list) {
				res.setResponseCode("200");
				res.setResult(list);
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

	@RequestMapping(value = "/validatePwdRestLink", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> validatePwdRestLink(@RequestBody UserCredentialsSessionEntity user) {
		boolean response = false;
		ResponseMessageBean bean = new ResponseMessageBean();
		ResponseMessageBean respCode = new ResponseMessageBean();
		try {
			respCode = adminDao.validatePwdRestLink(user);

			if (!ObjectUtils.isEmpty(respCode)) {
				bean.setResponseCode(respCode.getResponseCode());
				bean.setResponseMessage(respCode.getResponseMessage());
				bean.setResult(respCode.getResult());
			} else {
				bean.setResponseCode("500");
				bean.setResponseMessage("Error occured while Reseting Password");

			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(bean, HttpStatus.OK);
	}

	@RequestMapping(value = "/generateOTPForForgetPwd", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> generateOTPForForgetPwd(@RequestBody UserCredentialsSessionEntity user) {
		logger.info("In Adminstration Controller -> generateOTPForForgetPwd()");
		ResponseMessageBean res = new ResponseMessageBean();
		boolean otpsend = false;
		try {
			otpsend = adminDao.generateOTPForForgetPwd(user);

			if (otpsend) {
				res.setResponseCode("200");
				res.setResponseMessage("Otp Send To Your Mobile Number");
			} else {
				res.setResponseCode("500");
				res.setResponseMessage("Error While Sending Otp");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);

	}

	@RequestMapping(value = "/validateOtpAndChangePwd", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> validateOtpAndChangePwd(@RequestBody UserCredentialsSessionEntity user) {
		boolean response = false;
		ResponseMessageBean bean = new ResponseMessageBean();
		ResponseMessageBean respCode = new ResponseMessageBean();
		try {
			respCode = adminDao.validateOtpAndChangePwd(user);
			if (!ObjectUtils.isEmpty(respCode)) {
				bean.setResponseCode(respCode.getResponseCode());
				bean.setResponseMessage(respCode.getResponseMessage());
			} else {
				bean.setResponseCode("500");
				bean.setResponseMessage("Error occured while Reseting Password");

			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(bean, HttpStatus.OK);
	}

	@RequestMapping(value = "/FREEZACCOUNT", method = RequestMethod.POST)
	public MiddleWareResponse freezAccount(@RequestBody MiddleWareRequest transactionPOJO) {
		// LOGGER.debug("freezAccount - Freez Customer Account");

		String Opstatus = "";
		Map<String, String> map = transactionPOJO.getMap();
		MiddleWareResponse result = new MiddleWareResponse();
		List<String> requiredParam = new ArrayList<String>();
		List<String> errorParam = new ArrayList<String>();
		requiredParam.add(ApplicationConstants.ENTITY_ID);
		requiredParam.add(ApplicationConstants.ACCOUNTNUMBER);
		requiredParam.add(ApplicationConstants.FREEZEACCTCODE);
		requiredParam.add(ApplicationConstants.MOBILE_NO_ORG);
		requiredParam.add(ApplicationConstants.REFERENCENUMBER);
		requiredParam.add(ApplicationConstants.RRN);
		errorParam = Utils.feildValidation(requiredParam, transactionPOJO.getMap());
		if (!errorParam.isEmpty()) {
			result.getResponseParameter().put(ApplicationConstants.RESULT,
					"Missing parameters" + errorParam.toString());
			result.getResponseParameter().put(ApplicationConstants.OPSTATUS, "08");
		} else {
			Map<String, String> cbsResponse = new HashMap<String, String>();

			cbsResponse = adminDao.cbsFreezeAccount(transactionPOJO.getMap());

			if (cbsResponse != null && cbsResponse.get("responseCode").equalsIgnoreCase("000")) {
				List<Map<String, String>> recordsList = new ArrayList<Map<String, String>>();

				recordsList.add(cbsResponse);

				DataSet set = new DataSet("FreezeAccountDetails");
				set.setRecords(recordsList);
				Opstatus = "00";
				result.setSet(set);
			} else {
				Opstatus = "01";
			}

		}

		return result;
	}

	@RequestMapping(value = "/GETREPORTEESUSERS", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> listAllReporteesUsers(@RequestBody RequestParamBean requestBean) {

		ResponseMessageBean res = new ResponseMessageBean();
		try {
			logger.info(requestBean.getId1());
			List<UserDetailsBean> users = administrationService.getAllReporteesUsers(requestBean.getId1());

			if (!users.isEmpty()) {
				res.setResponseCode("200");
				res.setResult(users);
			} else {
				res.setResponseCode("202");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/GETALLBRANCHUSERS", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> listAllBranchUsersList(@RequestBody RequestParamBean requestBean) {

		ResponseMessageBean res = new ResponseMessageBean();
		try {
			logger.info(requestBean.toString());
			List<UserDetailsBean> users = administrationService.getAllBranchUsersList(requestBean.getId1(),
					requestBean.getId2());

			if (!users.isEmpty()) {
				res.setResponseCode("200");
				res.setResult(users);
			} else {
				res.setResponseCode("202");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateBranchCode", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateBranchStatus(@RequestBody RequestParamBean requestBean) {

		boolean isUpdated = administrationService.updateBranchStatus(requestBean.getId1(), requestBean.getId2(),
				requestBean.getId3(), Integer.parseInt(requestBean.getId4()), requestBean.getId5(),
				requestBean.getId6());
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			if (isUpdated) {
				response.setResponseCode("200");
				response.setResponseMessage("User Branch Updated Successfully!");
			} else {
				response.setResponseCode("202");
				response.setResponseMessage("Error Occured!");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/getHeadOffice", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getHeadOffice() {
		logger.info("In Adminstration Controller -> getHeadOffice()");
		ResponseMessageBean res = new ResponseMessageBean();
		List<User> headOffice = administrationService.getHeadOffice();

		// logger.info(headOffice);
		try {
			if (!headOffice.isEmpty()) {
				res.setResponseCode("200");
				res.setResponseMessage("Success");
				res.setResult(headOffice);

			} else {
				res.setResponseCode("202");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}

		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/getZonalOffice", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getZonalOff(@RequestBody RequestParamBean requestBean) {
		logger.info("In Adminstration Controller -> getZonalOffice()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<User> zonalOffice = administrationService.getZonalOffice(requestBean.getId1());
			// logger.info(zonalOffice);

			if (null != zonalOffice) {
				res.setResponseCode("200");
				res.setResult(zonalOffice);
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

	@RequestMapping(value = "/getBranchOffice", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getBranchOffice(@RequestBody RequestParamBean requestBean) {
		logger.info("In Adminstration Controller -> getBranchOffice()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<User> branchOffice = administrationService.getBranchOffice(requestBean.getId1());
			// logger.info(branchOffice);

			if (null != branchOffice) {
				res.setResponseCode("200");
				res.setResult(branchOffice);
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

	@RequestMapping(value = "/getAccountOpeningDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getAccountOpeningDetails(@RequestBody RequestParamBean requestBean) {
		logger.info("getAccountOpeningDetails() :: Branch Code");
		logger.info("Branch Code :- " + requestBean.getId1());
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<Object[]> accountOpeningDetails = administrationService.getAccountOpeningDetails(requestBean.getId1());

			List<AccountOpeningDetailsEntity> accountOpeningList = new ArrayList<>();
			if (null != accountOpeningDetails) {
				for (Object[] ab : accountOpeningDetails) {
					if (!ObjectUtils.isEmpty(ab[2])) {
						AccountOpeningDetailsEntity obj = new AccountOpeningDetailsEntity();
						String cbsBranchName = administrationService.getCbsBranchName(ab[0].toString());
						logger.info("AccountNumber: " + ab[1]);
						obj.setBranchname(cbsBranchName);
						if (!ObjectUtils.isEmpty(ab[1])) {
							logger.info("account Number Length: " + ab[1].toString().length());
							if (14 == ab[1].toString().length()) {
								obj.setAccountnumber(ab[1].toString());
							} else {
								obj.setAccountnumber(EncryptorDecryptor.decryptData(ab[1].toString()));
							}
						}

						if (!ObjectUtils.isEmpty(ab[2])) {
							logger.info("cif Length: " + ab[2].toString().length());
							if (9 == ab[2].toString().length()) {
								obj.setCif(ab[2].toString());
							} else {
								obj.setCif(EncryptorDecryptor.decryptData(ab[2].toString()));
							}
						}

						if (!ObjectUtils.isEmpty(ab[3]))
							obj.setId(BigDecimal.valueOf(Long.valueOf(ab[3].toString())));

						if (!ObjectUtils.isEmpty(ab[4]))
							obj.setFirstName(ab[4].toString());

						if (!ObjectUtils.isEmpty(ab[5]))
							obj.setMiddleName(ab[5].toString());

						if (!ObjectUtils.isEmpty(ab[6]))
							obj.setLastName(ab[6].toString());

						if (!ObjectUtils.isEmpty(ab[7]))
							obj.setMobileNumber(ab[7].toString());

						if (!ObjectUtils.isEmpty(ab[8]))
							obj.setEmail(ab[8].toString());

						if (!ObjectUtils.isEmpty(ab[9]))
							obj.setPanNumber(ab[9].toString());

						if (!ObjectUtils.isEmpty(ab[10]))
							obj.setAadharNumber(ab[10].toString());

						if (!ObjectUtils.isEmpty(ab[11]))
							obj.setUpdatedon(Timestamp.valueOf((ab[11]).toString()));

						obj.setBranchCode(requestBean.getId1());
						accountOpeningList.add(obj);
					}
				}
			}

			if (null != accountOpeningList && !accountOpeningList.isEmpty()) {
				res.setResponseCode("200");
				res.setResult(accountOpeningList);
				res.setResponseMessage("Success");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
			logger.info("In Adminstration Controller -> getAccountOpeningDetails() :: End");
		} catch (Exception e) {
			logger.info("Exception:", e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/viewAccountOpeningDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> viewAccountOpeningDetails(@RequestBody RequestParamBean requestBean) {
		logger.info("In Adminstration Controller -> viewAccountOpeningDetails() :: Start");
		ResponseMessageBean res = new ResponseMessageBean();
		List<AccountOpeningDetailsEntity> accountOpeningDetails = null;

		List<AccountOpeningDetailsEntity> accountOpeningList = new ArrayList<>();
		try {
			int i = requestBean.getId1().length();
			logger.info("Length :- " + i);
			if (9 == i) {
				logger.info("In Adminstration Controller -> getAccountOpeningDetails() :: Cif Number");
				String cifNo = EncryptorDecryptor.encryptData(requestBean.getId1());
				logger.info("cif Number :- " + cifNo);
				accountOpeningDetails = administrationService.getAccountOpeningDetailsCif(cifNo);
			} else if (10 == i) {
				logger.info("getAccountOpeningDetails() :: Mobile Number");
				logger.info("Mobile Number :- " + requestBean.getId1());
				accountOpeningDetails = administrationService.getAccountOpeningDetailsMobileNo(requestBean.getId1());
			} else if (14 == i) {
				logger.info("getAccountOpeningDetails() :: Account Number");
				String accNo = EncryptorDecryptor.encryptData(requestBean.getId1());
				logger.info("Account Number :- " + accNo);
				accountOpeningDetails = administrationService.getAccountOpeningDetailsAccNo(accNo);
			}

			if (!accountOpeningDetails.isEmpty()) {
				for (AccountOpeningDetailsEntity ab : accountOpeningDetails) {

					AccountOpeningDetailsEntity obj = new AccountOpeningDetailsEntity();
					String cbsBranchName = administrationService.getCbsBranchName(ab.getBranchCode());
					obj.setBranchname(cbsBranchName);

					if (!ObjectUtils.isEmpty(ab.getAccountnumber())) {
						obj.setAccountnumber(EncryptorDecryptor.decryptData(ab.getAccountnumber()));
					}

					if (!ObjectUtils.isEmpty(ab.getCif())) {
						obj.setCif(EncryptorDecryptor.decryptData(ab.getCif()));
					}

					obj.setId(ab.getId());
					obj.setFirstName(ab.getFirstName());
					obj.setLastName(ab.getLastName());
					obj.setMobileNumber(ab.getMobileNumber());
					obj.setEmail(ab.getEmail());
					obj.setPanNumber(ab.getPanNumber());
					obj.setAadharNumber(ab.getAadharNumber());
					obj.setGender(ab.getGender());
					obj.setDob(ab.getDob());
					obj.setPermanentAddressLine1(ab.getPermanentAddressLine1());
					obj.setPermanentAddressLine2(ab.getPermanentAddressLine2());
					obj.setPermanentAddressCity(ab.getPermanentAddressCity());
					obj.setPermanentAddressState(ab.getPermanentAddressState());
					obj.setPermanentAddressPin(ab.getPermanentAddressPin());
					obj.setNationality(ab.getNationality());
					obj.setMaritalStatus(ab.getMaritalStatus());
					obj.setCommunity(ab.getCommunity());
					obj.setCategory(ab.getCategory());
					obj.setFatherName(ab.getFatherName());
					obj.setMotherName(ab.getMotherName());
					obj.setCommunicationAddressLine1(ab.getCommunicationAddressLine1());
					obj.setCommunicationAddressLine2(ab.getCommunicationAddressLine2());
					obj.setCommunicationAddressCity(ab.getCommunicationAddressCity());
					obj.setCommunicationAddressState(ab.getCommunicationAddressState());
					obj.setCommunicationAddressPin(ab.getCommunicationAddressPin());
					obj.setOccupation(ab.getOccupation());
					obj.setAnnualIncome(ab.getAnnualIncome());
					obj.setDbtBenefitNew(ab.getDbtBenefitNew());
					obj.setDbtBenefitAccount(ab.getDbtBenefitAccount());
					obj.setDbtBenefirAccountReplace(ab.getDbtBenefirAccountReplace());
					obj.setBranchCode(ab.getBranchCode());
					obj.setNomineeName(ab.getNomineeName());
					obj.setNomineeAddressLine1(ab.getNomineeAddressLine1());
					obj.setNomineeAddressLine2(ab.getNomineeAddressLine2());
					obj.setNomineeAddressCity(ab.getNomineeAddressCity());
					obj.setNomineeAddressState(ab.getNomineeAddressState());
					obj.setNomineeAddressPin(ab.getNomineeAddressPin());
					obj.setNomineeRelation(ab.getNomineeRelation());
					obj.setNomineeDob(ab.getNomineeDob());
					obj.setGuardianName(ab.getGuardianName());
					obj.setGuardianAddressLine1(ab.getGuardianAddressLine1());
					obj.setGuardianAddressLine2(ab.getGuardianAddressLine2());
					obj.setGuardianAddressCity(ab.getGuardianAddressCity());
					obj.setGuardianAddressState(ab.getGuardianAddressState());
					obj.setGuardianAddressPin(ab.getGuardianAddressPin());
					obj.setGuardianType(ab.getGuardianType());
					obj.setUpiId(ab.getUpiId());
					obj.setAccountType(ab.getAccountType());
					obj.setConcentFacta(ab.getConcentFacta());
					obj.setConcentTandc(ab.getConcentTandc());
					obj.setConcentAuthBankComm(ab.getConcentAuthBankComm());
					obj.setConcentAadhar(ab.getConcentAadhar());
					obj.setStatusid(ab.getStatusid());
					obj.setRrn(ab.getRrn());
					obj.setCreatedon(ab.getCreatedon());
					obj.setUpdatedon(ab.getUpdatedon());
					obj.setCreatedby(ab.getCreatedby());
					obj.setUpdatedby(ab.getUpdatedby());
					obj.setLastdraftpage(ab.getLastdraftpage());
					// obj.setAccountnumber(ab.getAccountnumber());
					obj.setCifnumber(ab.getCifnumber());
					obj.setAmountdeposited(ab.getAmountdeposited());
					obj.setVideokycflag(ab.getVideokycflag());
					obj.setBranchcity(ab.getBranchcity());
					obj.setBranchstate(ab.getBranchstate());
					obj.setBranchpincode(ab.getBranchpincode());
					obj.setBranchsearchtype(ab.getBranchsearchtype());
					obj.setAccountopentype(ab.getAccountopentype());
					obj.setFatcadeclaration(ab.getFatcadeclaration());
					obj.setNomineeaddsameaspermanent(ab.getNomineeaddsameaspermanent());
					obj.setCommaddsameaspermanent(ab.getCommaddsameaspermanent());
					obj.setAadharlinkdbt1(ab.getAadharlinkdbt1());
					obj.setAadharlinkdbt2(ab.getAadharlinkdbt2());
					obj.setDonotwantmoninee(ab.getDonotwantmoninee());
					obj.setSmsemailpermission(ab.getSmsemailpermission());
					// obj.setCif(ab.getCif());
					obj.setAccount(ab.getAccount());
					obj.setAccountopenflag(ab.getAccountopenflag());
					// obj.setBranchname(ab.getBranchname());
					obj.setMaritalStatusNm(ab.getMaritalStatusNm());
					obj.setCommunityNm(ab.getCommunityNm());
					obj.setCategoryNm(ab.getCategoryNm());
					obj.setPermanentAddressCityNm(ab.getPermanentAddressCityNm());
					obj.setPermanentAddressStateNm(ab.getPermanentAddressStateNm());
					obj.setCommunicationAddressCityNm(ab.getCommunicationAddressCityNm());
					obj.setCommunicationAddressStateNm(ab.getCommunicationAddressStateNm());
					obj.setNomineeAddressCityNm(ab.getNomineeAddressCityNm());
					obj.setNomineeAddressStateNm(ab.getNomineeAddressStateNm());
					obj.setNomineeRelationNm(ab.getNomineeRelationNm());
					obj.setGuardianAddressCityNm(ab.getGuardianAddressCityNm());
					obj.setGuardianAddressStateNm(ab.getGuardianAddressStateNm());
					obj.setGuardianTypeNm(ab.getGuardianTypeNm());
					obj.setBranchcityNm(ab.getBranchcityNm());
					obj.setBranchstateNm(ab.getBranchstateNm());
					obj.setOccupationNm(ab.getOccupationNm());
					obj.setAnnualincomeNm(ab.getAnnualincomeNm());
					obj.setBranchaddressNm(ab.getBranchaddressNm());
					obj.setNomineerelnameNm(ab.getNomineerelnameNm());
					obj.setMiddleName(ab.getMiddleName());
					obj.setAadharimage(ab.getAadharimage());
					obj.setAadharpdf(ab.getAadharpdf());
					obj.setAadharfilename(ab.getAadharfilename());
					accountOpeningList.add(obj);
				}
			}

			if (null != accountOpeningList && !accountOpeningList.isEmpty()) {
				res.setResponseCode("200");
				res.setResult(accountOpeningList);
				res.setResponseMessage("Success");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
			logger.info("In Adminstration Controller -> getAccountOpeningDetails() :: End");
		} catch (Exception e) {
			logger.info("Exception:", e);

		}

		return new ResponseEntity<>(res, HttpStatus.OK);

	}

	@RequestMapping(value = "/getFraudRepotingDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getFraudRepotingDetails(@RequestBody RequestParamBean requestBean) {
		logger.info("In Adminstration Controller -> getFraudRepotingDetails() :: ");
		ResponseMessageBean res = new ResponseMessageBean();
		List<FraudReportingBean> fraudReporting = null;
		try {
			DateFormat sdf1 = new SimpleDateFormat("dd-MMM-yy");
			String date1 = sdf1.format(requestBean.getDate1());
			String date2 = sdf1.format(requestBean.getDate2());

			fraudReporting = administrationService.getFraudRepotingDetails(date1, date2);

			for (FraudReportingBean fraudReportingObj : fraudReporting) {

				if (null != fraudReportingObj.getCIFNUMBER()) {
					fraudReportingObj.setCIFNUMBER(EncryptorDecryptor.decryptData(fraudReportingObj.getCIFNUMBER()));
				}
			}

			if (null != fraudReporting && !fraudReporting.isEmpty()) {
				res.setResponseCode("200");
				res.setResult(fraudReporting);
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

	@RequestMapping(value = "/languageCodeList", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> languageCodeList(@RequestBody RequestParamBean requestBean) {
		logger.info("In Adminstration Controller -> languageMasterList() :: ");
		ResponseMessageBean res = new ResponseMessageBean();
		List<Object> languageMasterEntityList = null;
		try {
			languageMasterEntityList = languageMasterRepository.languageCodeList();
			if (null != languageMasterEntityList && !languageMasterEntityList.isEmpty()) {
				res.setResponseCode("200");
				res.setResult(languageMasterEntityList);
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

}
