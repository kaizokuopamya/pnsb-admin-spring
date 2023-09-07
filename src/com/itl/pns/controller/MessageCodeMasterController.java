package com.itl.pns.controller;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.constant.ApplicationConstants;
import com.itl.pns.entity.ActivitySettingMasterEntity;
import com.itl.pns.entity.MessageCodeMasterEntity;
import com.itl.pns.service.MessageCodeMasterService;
import com.itl.pns.util.AdminWorkFlowReqUtility;

@RestController
@RequestMapping("mesageCodeMaster")
public class MessageCodeMasterController {

	private static final Logger logger = LogManager.getLogger(MessageCodeMasterController.class);

	@Autowired
	MessageCodeMasterService msgCodeMaster;

	@Autowired
	AdminWorkFlowReqUtility adminWorkFlowReqUtility;

	@RequestMapping(value = "/getMessageCodeMasterDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getMessageCodeMasterDetails() {
		ResponseMessageBean response = new ResponseMessageBean();
		logger.info("In MessageCodeMasterController -> getMessageCodeMasterDetails()");
		try {
		List<MessageCodeMasterEntity> resultData = msgCodeMaster.getMessageCodeMasterDetails();
	
			if (!ObjectUtils.isEmpty(resultData)) {
				response.setResponseCode("200");
				response.setResult(resultData);
				response.setResponseMessage("success");
			} else {
				response.setResponseCode("202");
				response.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/getMessageCodeMasterDetailsById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getMessageCodeMasterDetailsById(
			@RequestBody MessageCodeMasterEntity messageCodeData) {
		ResponseMessageBean response = new ResponseMessageBean();
		logger.info("In MessageCodeMasterController -> getMessageCodeMasterDetailsById()");
		try {
		List<MessageCodeMasterEntity> resultData = msgCodeMaster.getMessageCodeMasterDetailsById(messageCodeData);

		if (!ObjectUtils.isEmpty(resultData)) {
			response.setResponseCode("200");
			response.setResult(resultData);
			response.setResponseMessage("success");
		} else {
			response.setResponseCode("202");
			response.setResponseMessage("No Records Found");
		}} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/addMessageCode", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> addMessageCode(@RequestBody MessageCodeMasterEntity messageCodeData) {
		ResponseMessageBean response = new ResponseMessageBean();
		logger.info("In MessageCodeMasterController -> addMessageCode()");
		Boolean res = false;
		messageCodeData.setCreatedByName(
				adminWorkFlowReqUtility.getCreatedByNameByCreatedId(messageCodeData.getCreatedby().intValue()));
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(messageCodeData.getRole_ID().intValue());
		messageCodeData.setRoleName(roleName);
		messageCodeData.setAction("ADD");
		messageCodeData.setStatusName(
				adminWorkFlowReqUtility.getStatusNameByStatusId(messageCodeData.getStatusId().intValue()));
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(messageCodeData.getActivityName());
		ResponseMessageBean responsecode = new ResponseMessageBean();
		responsecode.setResponseCode("200");
		try {
			if (!ObjectUtils.isEmpty(messageCodeData)) {
				// responsecode =
				// investementProductDao.isInvestementProductExist(messageCodeData);
				if (responsecode.getResponseCode().equalsIgnoreCase("200")) {

					res = msgCodeMaster.addMessageCode(messageCodeData);
					if (res) {
						boolean isDataRefresh = false;
						isDataRefresh = adminWorkFlowReqUtility.refreshCacheData("MessageCodeMasterReader");
						response.setResponseCode("200");
						if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getChecker()))
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

							response.setResponseMessage("Request Submitted To Admin Checker For Approval");
						} else {
							response.setResponseMessage("Message Code Details Saved Successfully");
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

	@RequestMapping(value = "/updateMessageCode", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateMessageCode(@RequestBody MessageCodeMasterEntity messageCodeData) {
		ResponseMessageBean response = new ResponseMessageBean();
		logger.info("In MessageCodeMasterController -> updateMessageCode()");
		Boolean res = false;
		messageCodeData.setCreatedByName(
				adminWorkFlowReqUtility.getCreatedByNameByCreatedId(messageCodeData.getCreatedby().intValue()));
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(messageCodeData.getRole_ID().intValue());
		messageCodeData.setRoleName(roleName);
		messageCodeData.setAction("EDIT");
		messageCodeData.setStatusName(
				adminWorkFlowReqUtility.getStatusNameByStatusId(messageCodeData.getStatusId().intValue()));
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(messageCodeData.getActivityName());
		ResponseMessageBean responsecode = new ResponseMessageBean();
		responsecode.setResponseCode("200");
		try {
			if (!ObjectUtils.isEmpty(messageCodeData)) {
				// responsecode =
				// investementProductDao.isUpdateInvestementProductExist(messageCodeData);
				if (responsecode.getResponseCode().equalsIgnoreCase("200")) {
					res = msgCodeMaster.updateMessageCode(messageCodeData);
					if (res) {
						boolean isDataRefresh = false;
						isDataRefresh = adminWorkFlowReqUtility.refreshCacheData("MessageCodeMasterReader");
						response.setResponseCode("200");
						if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getChecker()))
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

							response.setResponseMessage("Request Submitted To Admin Checker For Approval");
						} else {
							response.setResponseMessage("Message Code Details Updated Successfully");
						}

					} else {
						response.setResponseCode("202");
						response.setResponseMessage("Error While Updating Records");
					}
				} else {
					response.setResponseCode("500");
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
}
