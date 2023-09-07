package com.itl.pns.corp.controller;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.itl.pns.bean.RequestParamBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.constant.ApplicationConstants;
import com.itl.pns.corp.dao.CorpMakerCheckerDao;
import com.itl.pns.corp.entity.CorpActivitySettingMasterEntity;
import com.itl.pns.corp.entity.CorpTempSalProcessEntity;
import com.itl.pns.corp.entity.DesignationHierarchyMasterEntity;
import com.itl.pns.corp.service.CorpMakerCheckerService;
import com.itl.pns.entity.ActivitySettingMasterEntity;
import com.itl.pns.entity.CustomerEntity;
import com.itl.pns.util.AdminWorkFlowReqUtility;

@RestController
@RequestMapping("corpMakerChecker")
public class CorpMakerCheckerController {

	private static final Logger logger = LogManager.getLogger(CorpMakerCheckerController.class);

	@Autowired
	CorpMakerCheckerService corpMakerCheckerService;

	@Autowired
	CorpMakerCheckerDao corpMakerCheckerDao;

	@Autowired
	AdminWorkFlowReqUtility adminWorkFlowReqUtility;

	@RequestMapping(value = "/getAllActivitySettingForCorp", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getAllActivitySettingForCorp() {
		logger.info("In Adminstration Controller -> getAllActivitySettingForCorp()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<ActivitySettingMasterEntity> activitySettingData = corpMakerCheckerService
					.getAllActivitySettingForCorp();

			if (null != activitySettingData) {
				res.setResponseCode("200");
				res.setResult(activitySettingData);
				res.setResponseMessage("Success");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);

	}

	@RequestMapping(value = "/getCorpActivitiesAndMapping", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getCorpActivitiesAndMapping(@RequestBody RequestParamBean requestBean) {
		logger.info("In Corporate Controller -> getCorpActivitiesAndMapping()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<ActivitySettingMasterEntity> activitySettingList = corpMakerCheckerService
					.getAllActivitySettingForCorp();

			List<CorpActivitySettingMasterEntity> corpActivitySettingList = corpMakerCheckerService
					.getCorpActivitiesAndMapping(Integer.parseInt(requestBean.getId1()));
			for (ActivitySettingMasterEntity activitySettingData : activitySettingList) {
				activitySettingData.setActivitySelected("NO");

				for (CorpActivitySettingMasterEntity corpActivityData : corpActivitySettingList) {

					if (activitySettingData.getActivityId().intValue() == corpActivityData.getActivityId().intValue()) {
						activitySettingData.setActivitySelected("YES");
						activitySettingData.setMaker(corpActivityData.getMaker().charAt(0));
						activitySettingData.setChecker(corpActivityData.getChecker().charAt(0));
						activitySettingData.setApprover(corpActivityData.getApprover().charAt(0));

					}
				}

			}

			if (!ObjectUtils.isEmpty(activitySettingList)) {
				res.setResponseCode("200");
				res.setResult(activitySettingList);
				res.setResponseMessage("Success");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);

	}

	@RequestMapping(value = "/saveCorpActivities", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> saveCorpActivities(
			@RequestBody List<CorpActivitySettingMasterEntity> corpActivityData) {
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			corpMakerCheckerService.saveCorpActivities(corpActivityData);
			response.setResponseCode("200");
			response.setResponseMessage("Corporate Activities has been updated successfully");
		} catch (Exception e) {
			response.setResponseCode("500");
			response.setResponseMessage("Error occured while updating the record");
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "bulkSalaryUpload", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> bulkSalaryUpload(
			@RequestBody List<CorpTempSalProcessEntity> corpSalData) {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(corpSalData.get(0).getRole_ID().intValue());
			List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
					.isMakerCheckerPresentForReq(corpSalData.get(0).getActivityName());
			boolean isAdded = corpMakerCheckerService.bulkSalaryUpload(corpSalData);

			if (isAdded) {
				res.setResponseCode("200");
				if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)
						&& (ApplicationConstants.YESVALUE)
								.equals(Character.toString(activityMasterData.get(0).getChecker()))
						&& (ApplicationConstants.YESVALUE)
								.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
					res.setResponseMessage("Request Submitted To Admin Checker For Approval");
				} else {
					res.setResponseMessage("File Uploaded Successfully");
				}

			} else {
				res.setResponseCode("500");
				res.setResponseMessage("Error Ocured While Saving The Record");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}

		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/addDesignationHierarchy", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> addDesignationHierarchy(
			@RequestBody DesignationHierarchyMasterEntity designationData) {
		logger.info("In Corporate Controller -> addDesignationHierarchy()");
		ResponseMessageBean response = new ResponseMessageBean();
		Boolean res = false;

		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(designationData.getRole_ID().intValue());
		designationData.setRoleName(roleName);
		designationData.setStatusName(
				adminWorkFlowReqUtility.getStatusNameByStatusId(designationData.getStatusId().intValue()));
		designationData.setCreatedByName(
				adminWorkFlowReqUtility.getCreatedByNameByCreatedId(designationData.getCreatedBy().intValue()));
		designationData.setAction("ADD");
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(designationData.getActivityName());
		ResponseMessageBean responsecode = new ResponseMessageBean();
		try {
			if (!ObjectUtils.isEmpty(designationData)) {
				responsecode = corpMakerCheckerDao.checkDesignationAndLevelExits(designationData);
				if (responsecode.getResponseCode().equalsIgnoreCase("200")) {
					res = corpMakerCheckerService.addDesignationHierarchy(designationData);
					if (res) {
						response.setResponseCode("200");
						if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getChecker()))
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

							response.setResponseMessage("Request Submitted To Corporate Checker For Approval");
						} else {
							response.setResponseMessage("Designation Hierarchy Saved Successfully");
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
			e.printStackTrace();
			response.setResponseCode("500");
			response.setResponseMessage("Error While  Adding Records!");
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateDesignationHierarchy", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateDesignationHierarchy(
			@RequestBody DesignationHierarchyMasterEntity designationData) {
		logger.info("In Corporate Controller -> updateDesignationHierarchy()");
		ResponseMessageBean response = new ResponseMessageBean();
		Boolean res = false;
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(designationData.getRole_ID().intValue());
		designationData.setRoleName(roleName);
		designationData.setStatusName(
				adminWorkFlowReqUtility.getStatusNameByStatusId(designationData.getStatusId().intValue()));

		designationData.setCreatedByName(
				adminWorkFlowReqUtility.getCreatedByNameByCreatedId(designationData.getCreatedBy().intValue()));
		designationData.setAction("EDIT");

		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(designationData.getActivityName());
		ResponseMessageBean responsecode = new ResponseMessageBean();
		try {
			if (!ObjectUtils.isEmpty(designationData)) {
				responsecode = corpMakerCheckerDao.checkUpdateDesignationAndLevelExits(designationData);
				if (responsecode.getResponseCode().equalsIgnoreCase("200")) {
					res = corpMakerCheckerService.updateDesignationHierarchy(designationData);
					response.setResponseCode("200");
					if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)
							&& (ApplicationConstants.YESVALUE)
									.equals(Character.toString(activityMasterData.get(0).getChecker()))
							&& (ApplicationConstants.YESVALUE)
									.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

						response.setResponseMessage("Request Submitted To Corporate Checker For Approval");
					} else {
						response.setResponseMessage("Designation Hierarchy Updated Successfully");
					}

				}
			} else {
				response.setResponseCode("202");
				response.setResponseMessage(responsecode.getResponseMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponseCode("500");
			response.setResponseMessage("Error While Updating Records!");
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/getDesignationHierarchyById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getDesignationHierarchyById(@RequestBody RequestParamBean requestBean) {
		logger.info("In Corporate Controller -> getDesignationHierarchyById()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<DesignationHierarchyMasterEntity> designationData = corpMakerCheckerService
					.getDesignationHierarchyById(Integer.parseInt(requestBean.getId1()));

			if (null != designationData) {
				res.setResponseCode("200");
				res.setResult(designationData);
				res.setResponseMessage("Success");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);

	}

	@RequestMapping(value = "/getAllDesignationHierarchy", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getAllDesignationHierarchy() {
		logger.info("In Corporate Controller -> getAllDesignationHierarchy()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<DesignationHierarchyMasterEntity> designationData = corpMakerCheckerService
					.getAllDesignationHierarchy();

			if (null != designationData) {
				res.setResponseCode("200");
				res.setResult(designationData);
				res.setResponseMessage("Success");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);

	}

	@RequestMapping(value = "/getDesignationHierarchyByCompId", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getDesignationHierarchyByCompId(
			@RequestBody RequestParamBean requestBean) {
		logger.info("In Corporate Controller -> getCorpMenuById()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<DesignationHierarchyMasterEntity> designationData = corpMakerCheckerService
					.getDesignationHierarchyByCompId(Integer.parseInt(requestBean.getId1()));

			if (null != designationData) {
				res.setResponseCode("200");
				res.setResult(designationData);
				res.setResponseMessage("Success");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);

	}

	@RequestMapping(value = "/getAuthTypeByCompIdAndDesignationId", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getDesignationHierarchyByCompId(
			@RequestBody DesignationHierarchyMasterEntity corpReq) {
		logger.info("In Corporate Controller -> getAuthTypeByCompIdAndDesignationId()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<DesignationHierarchyMasterEntity> designationData = corpMakerCheckerService
					.getAuthTypeByCompIdAndDesignationId(corpReq);

			if (null != designationData) {
				res.setResponseCode("200");
				res.setResult(designationData);
				res.setResponseMessage("Success");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);

	}

	// static cal ...CBS cal
	@RequestMapping(value = "/getCorpFreezeAcountDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
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
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);

	}

	// static cal ...CBS cal--- by acc no
	@RequestMapping(value = "/getCorpFreezeAcountDetailsByAccNo", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getCorpFreezeAcountDetailsByAccNo(
			@RequestBody RequestParamBean requestBean) {
		logger.info("In Adminstration Controller -> getCorpFreezeAcountDetailsByAccNo()");
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
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);

	}

	// cbs call -to freeze or unfreeze account
	@RequestMapping(value = "/updateCorpFreezeAccount", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateCorpFreezeAccount(@RequestBody CustomerEntity CustomerData) {
		logger.info("In Adminstration Controller -> updateCorpFreezeAccount()");
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
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);

	}

	@RequestMapping(value = "/saveCorpUserLevels", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> saveCorpUserLevels(
			@RequestBody List<CorpActivitySettingMasterEntity> corpActivityData) {
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			corpMakerCheckerService.saveCorpUserLevels(corpActivityData);
			response.setResponseCode("200");
			response.setResponseMessage("User Levels has been updated successfully");
		} catch (Exception e) {
			response.setResponseCode("500");
			response.setResponseMessage("Error occured while updating the record");
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateCorpUserLevels", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> updateCorpUserLevels(
			@RequestBody List<CorpActivitySettingMasterEntity> corpActivityData) {
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			corpMakerCheckerService.updateCorpUserLevels(corpActivityData);
			response.setResponseCode("200");
			response.setResponseMessage("User Levels has been updated successfully");
		} catch (Exception e) {
			response.setResponseCode("500");
			response.setResponseMessage("Error occured while updating the record");
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
