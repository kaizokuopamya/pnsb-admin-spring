package com.itl.pns.controller;

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
import com.itl.pns.dao.HolidayDao;
import com.itl.pns.entity.ActivitySettingMasterEntity;
import com.itl.pns.entity.HolidayEntity;
import com.itl.pns.service.HolidayService;
import com.itl.pns.util.AdminWorkFlowReqUtility;

/**
 * @author shubham.lokhande
 *
 */
@RestController
@RequestMapping("holiday")
public class HolidayController {

	private static final Logger logger = LogManager.getLogger(HolidayController.class);

	@Autowired
	HolidayService holidayService;

	@Autowired
	AdminWorkFlowReqUtility adminWorkFlowReqUtility;

	@Autowired
	HolidayDao holidayDao;

	@RequestMapping(value = "/getHolidayDetailsById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getHolidayDetailsById(@RequestBody RequestParamBean requestBean) {
		logger.info("In HolidayController -> getHolidayDetailsById()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
		List<HolidayEntity> holidayData = holidayService.getHolidayDetailsById(Integer.parseInt(requestBean.getId1()));
		
			if (null != holidayData) {
				res.setResponseCode("200");
				res.setResult(holidayData);
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

	@RequestMapping(value = "/getHolidayDetailsByState", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getHolidayDetailsByState(@RequestBody RequestParamBean requestBean) {
		logger.info("In HolidayController -> getHolidayDetailsByState()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
		List<HolidayEntity> holidayData = holidayService.getHolidayDetailsByState(requestBean.getId1());
		
			if (null != holidayData) {
				res.setResponseCode("200");
				res.setResult(holidayData);
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

	@RequestMapping(value = "/getHolidayDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getHolidayDetails() {
		logger.info("In HolidayController -> getHolidayDetails()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
		List<HolidayEntity> holidayData = holidayService.getHolidayDetails();
		
			if (null != holidayData) {
				res.setResponseCode("200");
				res.setResult(holidayData);
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

	@RequestMapping(value = "/addHolidayDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> addHolidayDetails(@RequestBody HolidayEntity holidayData) {
		logger.info("In HolidayController -> addHolidayDetails()");
		ResponseMessageBean response = new ResponseMessageBean();
		ResponseMessageBean responsecode = new ResponseMessageBean();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(holidayData.getRole_ID().intValue());
		holidayData.setRoleName(roleName);
		holidayData
				.setStatusName(adminWorkFlowReqUtility.getStatusNameByStatusId(holidayData.getStatusId().intValue()));
		// holidayData.setProductName(adminWorkFlowReqUtility.getAppNameByAppId(holidayData.getAppId().intValue()));
		holidayData.setCreatedByName(
				adminWorkFlowReqUtility.getCreatedByNameByCreatedId(holidayData.getCreatedBy().intValue()));
		holidayData.setAction("ADD");
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(holidayData.getActivityName());
		Boolean res = false;

		try {
			if (!ObjectUtils.isEmpty(holidayData)) {
				responsecode = holidayDao.isHolidayExist(holidayData);
				if (responsecode.getResponseCode().equalsIgnoreCase("200")) {

					res = holidayService.addHolidayDetails(holidayData);
					if (res) {
						response.setResponseCode("200");
						if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getChecker()))
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

							response.setResponseMessage("Request Submitted To Admin Checker For Approval");
						} else {
							response.setResponseMessage("Holiday Details Saved Successfully");
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

	@RequestMapping(value = "/updateHolidayDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateHolidayDetails(@RequestBody HolidayEntity holidayData) {
		logger.info("In HolidayController -> updateHolidayDetails()");
		ResponseMessageBean response = new ResponseMessageBean();
		ResponseMessageBean responsecode = new ResponseMessageBean();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(holidayData.getRole_ID().intValue());
		holidayData.setRoleName(roleName);
		holidayData.setAction("EDIT");

		holidayData
				.setStatusName(adminWorkFlowReqUtility.getStatusNameByStatusId(holidayData.getStatusId().intValue()));
		// holidayData.setProductName(adminWorkFlowReqUtility.getAppNameByAppId(holidayData.getAppId().intValue()));
		holidayData.setCreatedByName(
				adminWorkFlowReqUtility.getCreatedByNameByCreatedId(holidayData.getCreatedBy().intValue()));
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(holidayData.getActivityName());
		Boolean res = false;
		try {
			if (!ObjectUtils.isEmpty(holidayData)) {
				responsecode = holidayService.updateHolidayExist(holidayData);
				if (responsecode.getResponseCode().equalsIgnoreCase("200")) {
					res = holidayService.updateHolidayDetails(holidayData);
					if (res) {
						response.setResponseCode("200");
						if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getChecker()))
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
							response.setResponseMessage("Request Submitted To Admin Checker For Approval");
						} else {
							response.setResponseMessage("Holiday Details Updated Successfully");
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
			responsecode.setResponseCode("500");
			responsecode.setResponseMessage("Error While Updating Records!");
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/addBulkHolidayDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> addBulkHolidayDetails(@RequestBody List<HolidayEntity> holidayData) {
		logger.info("In HolidayController -> addBulkHolidayDetails()");
		ResponseMessageBean response = new ResponseMessageBean();
		ResponseMessageBean responsecode = new ResponseMessageBean();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(holidayData.get(0).getRole_ID().intValue());
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(holidayData.get(0).getActivityName());
		Boolean res = false;
		try {
			if (!ObjectUtils.isEmpty(holidayData)) {

				res = holidayService.addBulkHolidayDetails(holidayData);
				if (res) {
					response.setResponseCode("200");
					if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
							&& (ApplicationConstants.YESVALUE)
									.equals(Character.toString(activityMasterData.get(0).getChecker()))
							&& (ApplicationConstants.YESVALUE)
									.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

						response.setResponseMessage("Request Submitted To Admin Checker For Approval");
					} else {
						response.setResponseMessage("Holiday Details Saved Successfully");
					}

				} else {
					response.setResponseCode("202");
					response.setResponseMessage("Error While Saving Records");
				}

			}
		} catch (Exception e) {
			logger.info("Exception:", e);
			responsecode.setResponseCode("500");
			responsecode.setResponseMessage("Error While  Adding Records!");
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}