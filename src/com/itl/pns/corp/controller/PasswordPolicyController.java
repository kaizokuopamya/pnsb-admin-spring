package com.itl.pns.corp.controller;

import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itl.pns.bean.RequestParamBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.constant.ApplicationConstants;
import com.itl.pns.corp.entity.ForcePasswordPolicyEntity;
import com.itl.pns.corp.service.PasswordPolicyService;
import com.itl.pns.dao.MasterConfigDao;
import com.itl.pns.entity.ActivitySettingMasterEntity;
import com.itl.pns.entity.ConfigMasterEntity;
import com.itl.pns.repository.ForcePwdPolicyEntityRepository;
import com.itl.pns.util.AdminWorkFlowReqUtility;
import com.itl.pns.util.Utils;

@RestController
@RequestMapping("passwordPolicy")
public class PasswordPolicyController {

	private static final Logger logger = LogManager.getLogger(PasswordPolicyController.class);

	@Autowired
	private ForcePwdPolicyEntityRepository forcePwdPolicyEntityRepository;

	@Autowired
	private MasterConfigDao masterConfigDao;

	@Autowired
	private AdminWorkFlowReqUtility adminWorkFlowReqUtility;

	@Autowired
	private PasswordPolicyService passwordPolicyService;

//	@Autowired
//	private AdminWorkFlow adminWorkFlowReqUtility;

	@PostMapping(value = "/getRBIMaxMinExpDaysList")
	public ResponseEntity<ResponseMessageBean> getRBIMaxMinExpDaysList(@RequestBody RequestParamBean requestParamBean) {
		logger.info("Inside Force Password Policy List..");
		ResponseMessageBean responseMessageBean = new ResponseMessageBean();
		List<ConfigMasterEntity> configData = null;
		try {
			configData = masterConfigDao.getConfigByConfigKey(ApplicationConstants.RBI_PASS_EXP_DAYS_MAX);
			if (ObjectUtils.isEmpty(configData)) {
				responseMessageBean.setResponseCode("202");
				responseMessageBean.setResponseMessage("No record found");
			} else {
				responseMessageBean.setResponseCode("200");
				responseMessageBean.setResponseMessage("Fetch password max and min value successfully");
			}
		} catch (Exception e) {
			logger.error("Error while password max and min", e);
			responseMessageBean.setResponseCode("202");
			responseMessageBean.setResponseMessage("Failed to password max and min value");
		}
		responseMessageBean.setResult(configData);
		return new ResponseEntity<>(responseMessageBean, HttpStatus.OK);
	}

	@PostMapping(value = "/addForcePwdPolicy")
	public ResponseEntity<ResponseMessageBean> addForcePwd(@RequestBody ForcePasswordPolicyEntity forcePasswordPolicy,
			HttpServletRequest httpRequest) {
		logger.info("Inside Add Force Password Policy");
		ResponseMessageBean responseMessageBean = new ResponseMessageBean();
		forcePasswordPolicy.setCreatedBy(Utils.getUpdatedBy(httpRequest).intValue());
		forcePasswordPolicy.setCreatedOn(new Timestamp(System.currentTimeMillis()));
		forcePasswordPolicy.setUpdatedBy(Utils.getUpdatedBy(httpRequest).intValue());
		forcePasswordPolicy.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
		forcePasswordPolicy.setStatusId(ApplicationConstants.ACTIVE_STATUS);
		try {
			forcePwdPolicyEntityRepository.save(forcePasswordPolicy);
			responseMessageBean.setResponseCode("200");
			responseMessageBean.setResponseMessage("Force password policy added successfully");
		} catch (Exception e) {
			logger.error("Error while saving force password policy", e);
			responseMessageBean.setResponseCode("202");
			responseMessageBean.setResponseMessage("Failed to save force password policy");
		}

		return new ResponseEntity<>(responseMessageBean, HttpStatus.OK);
	}

	@PostMapping(value = "/updateForcePwdPolicy")
	public ResponseEntity<ResponseMessageBean> updateForcePwd(
			@RequestBody ForcePasswordPolicyEntity forcePasswordPolicy, HttpServletRequest httpRequest) {
		logger.info("Inside Add Force Password Policy");
		ResponseMessageBean responseMessageBean = new ResponseMessageBean();
		forcePasswordPolicy.setUpdatedBy(Utils.getUpdatedBy(httpRequest).intValue());
		forcePasswordPolicy.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
		forcePasswordPolicy.setStatusId(ApplicationConstants.ACTIVE_STATUS);
		try {
			forcePwdPolicyEntityRepository.save(forcePasswordPolicy);
			responseMessageBean.setResponseCode("200");
			responseMessageBean.setResponseMessage("Force password policy updated successfully");
		} catch (Exception e) {
			logger.error("Error while updaing force password policy", e);
			responseMessageBean.setResponseCode("202");
			responseMessageBean.setResponseMessage("Failed to update force password policy");
		}

		return new ResponseEntity<>(responseMessageBean, HttpStatus.OK);
	}

	@PostMapping(value = "/forcePwdPolicyList")
	public ResponseEntity<ResponseMessageBean> forcePwdList(@RequestBody RequestParamBean requestParamBean) {
		logger.info("Inside Force Password Policy List..");
		ResponseMessageBean responseMessageBean = new ResponseMessageBean();
		List<ForcePasswordPolicyEntity> forcePasswordPolicyList = null;
		try {
			forcePasswordPolicyList = forcePwdPolicyEntityRepository.findAllByStatusId(3);
			if (ObjectUtils.isEmpty(forcePasswordPolicyList)) {
				responseMessageBean.setResponseCode("202");
				responseMessageBean.setResponseMessage("No record found");
			} else {
				responseMessageBean.setResponseCode("200");
				responseMessageBean.setResponseMessage("Fetch password policy list successfully");
			}

		} catch (Exception e) {
			logger.error("Error while fetching force password policy List", e);
			responseMessageBean.setResponseCode("202");
			responseMessageBean.setResponseMessage("Failed to fetch password policy list");
		}

		responseMessageBean.setResult(forcePasswordPolicyList);
		return new ResponseEntity<>(responseMessageBean, HttpStatus.OK);
	}

	@PostMapping(value = "/getForcePwdPolicy")
	public ResponseEntity<ResponseMessageBean> getForcePwd(@RequestBody RequestParamBean requestParamBean) {
		logger.info("Inside Force Password Policy List..");
		ResponseMessageBean responseMessageBean = new ResponseMessageBean();
		ForcePasswordPolicyEntity forcePasswordPolicy = null;
		try {
			forcePasswordPolicy = forcePwdPolicyEntityRepository.findOne(Long.valueOf(requestParamBean.getId1()));
			if (ObjectUtils.isEmpty(forcePasswordPolicy)) {
				responseMessageBean.setResponseCode("202");
				responseMessageBean.setResponseMessage("No record found");
			} else {
				responseMessageBean.setResponseCode("200");
				responseMessageBean.setResponseMessage("Fetch password policy successfully");
			}
		} catch (Exception e) {
			logger.error("Error while fetcing force password policy", e);
			responseMessageBean.setResponseCode("202");
			responseMessageBean.setResponseMessage("Failed to fetch password policy");
		}
		responseMessageBean.setResult(forcePasswordPolicy);
		return new ResponseEntity<>(responseMessageBean, HttpStatus.OK);
	}

//	@PostMapping(value = "/addForcePwdPolicy")
//	public ResponseEntity<ResponseMessageBean> addForcePwd1(@RequestBody ForcePasswordPolicyEntity forcePasswordPolicy,
//			HttpServletRequest httpRequest) {
//		logger.info("Inside Add Force Password Policy");
//		ResponseMessageBean responseMessageBean = new ResponseMessageBean();
//		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(forcePasswordPolicy.getRole_ID().intValue());
//
//		forcePasswordPolicy.setRoleName(roleName);
//		forcePasswordPolicy.setStatusName(
//				adminWorkFlowReqUtility.getStatusNameByStatusId(forcePasswordPolicy.getStatusId().intValue()));
//		forcePasswordPolicy
//				.setProductName(adminWorkFlowReqUtility.getAppNameByAppId(forcePasswordPolicy.getAppId().intValue()));
//		forcePasswordPolicy.setCreatedByName(
//				adminWorkFlowReqUtility.getCreatedByNameByCreatedId(forcePasswordPolicy.getCreatedby()));
//		forcePasswordPolicy.setAction("ADD");
//
//		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
//				.isMakerCheckerPresentForReq(forcePasswordPolicy.getActivityName());
//
//		if (!ObjectUtils.isEmpty(forcePasswordPolicy)) {
//			forcePasswordPolicy = passwordPolicyService.saveForcePassword(forcePasswordPolicy);
//			if (!ObjectUtils.isEmpty(forcePasswordPolicy)) {
//				responseMessageBean.setResponseCode("200");
//				if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
//						&& (ApplicationConstants.YESVALUE)
//								.equals(Character.toString(activityMasterData.get(0).getChecker()))
//						&& (ApplicationConstants.YESVALUE)
//								.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
//					responseMessageBean.setResponseMessage("Request Submitted To Checker For Approval");
//				} else {
//					responseMessageBean.setResponseMessage("Details Saved Successfully");
//				}
//
//			} else {
//				responseMessageBean.setResponseCode("202");
//				responseMessageBean.setResponseMessage("Error While Saving Records");
//			}
//		}
//
//		return new ResponseEntity<>(responseMessageBean, HttpStatus.OK);
//	}

//	@PostMapping(value = "/updateForcePwdPolicy")
//	public ResponseEntity<ResponseMessageBean> updateForcePwd1(
//			@RequestBody ForcePasswordPolicyEntity forcePasswordPolicy, HttpServletRequest httpRequest) {
//		logger.info("Inside Add Force Password Policy");
//		ResponseMessageBean responseMessageBean = new ResponseMessageBean();
//		forcePasswordPolicy.setUpdatedBy(Utils.getUpdatedBy(httpRequest).intValue());
//		forcePasswordPolicy.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
//		forcePasswordPolicy.setStatusId(ApplicationConstants.ACTIVE_STATUS);
//		try {
//			forcePwdPolicyEntityRepository.save(forcePasswordPolicy);
//			responseMessageBean.setResponseCode("200");
//			responseMessageBean.setResponseMessage("Force password policy updated successfully");
//		} catch (Exception e) {
//			logger.error("Error while updaing force password policy", e);
//			responseMessageBean.setResponseCode("202");
//			responseMessageBean.setResponseMessage("Failed to update force password policy");
//		}
//
//		return new ResponseEntity<>(responseMessageBean, HttpStatus.OK);
//	}

}
