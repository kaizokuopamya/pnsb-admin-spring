package com.itl.pns.controller;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.constant.ApplicationConstants;
import com.itl.pns.corp.entity.MerchantEntity;
import com.itl.pns.entity.ActivitySettingMasterEntity;
import com.itl.pns.service.MerchantService;
import com.itl.pns.util.AdminWorkFlowReqUtility;

@RestController
@RequestMapping("merchant")
public class MerchantController {

	private static final Logger logger = LogManager.getLogger(MerchantController.class);

	@Autowired
	private MerchantService merchantservice;

	@Autowired
	AdminWorkFlowReqUtility adminWorkFlowReqUtility;

	@RequestMapping(value = "/merchantList", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getMerchantDetails() {
		logger.info("In Merchant Controller -> MerchantList()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<MerchantEntity> merchantDetailsList = merchantservice.getAllMerchantDetails();
			if (!ObjectUtils.isEmpty(merchantDetailsList)) {
				res.setResponseCode("200");
				res.setResult(merchantDetailsList);
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

	@RequestMapping(value = "/addMerchant", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> addMerchantDetails(@RequestBody MerchantEntity merchantEntity) {
		logger.info("In Merchant Controller -> addMerchant()");
		ResponseMessageBean response = new ResponseMessageBean();
		merchantEntity.setCreatedByName(
				adminWorkFlowReqUtility.getCreatedByNameByCreatedId((merchantEntity.getCreatedBy().intValue())));
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(merchantEntity.getRole_ID().intValue());
		merchantEntity.setRoleName(roleName);
		merchantEntity.setAction("ADD");
		merchantEntity.setStatusName(
				adminWorkFlowReqUtility.getStatusNameByStatusId(merchantEntity.getStatusId().intValue()));
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(merchantEntity.getActivityName());

		Boolean res = false;
		try {
			if (!ObjectUtils.isEmpty(merchantEntity)) {
				res = merchantservice.add(merchantEntity);
				if (res) {
					response.setResponseCode("200");
					if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
							&& (ApplicationConstants.YESVALUE)
									.equals(Character.toString(activityMasterData.get(0).getChecker()))
							&& (ApplicationConstants.YESVALUE)
									.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

						response.setResponseMessage("Request Submitted To Admin Checker For Approval");
					} else {
						response.setResponseMessage("Notification Category Saved Successfully");
					}

				} else {
					response.setResponseCode("202");
					response.setResponseMessage("Error While Saving Records");
				}

			}
		} catch (Exception e) {
			logger.info("Exception:", e);
			response.setResponseCode("500");
			response.setResponseMessage("Error While  Adding Records!");
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/updateMerchant")
	public ResponseEntity<ResponseMessageBean> updateMerchantDetails(@RequestBody MerchantEntity merchantEntity) {
		logger.info("In Merchant Controller -> updateMerchant()");
		ResponseMessageBean response = new ResponseMessageBean();
		Boolean res = false;
		merchantEntity.setCreatedByName(
				adminWorkFlowReqUtility.getCreatedByNameByCreatedId(merchantEntity.getCreatedBy().intValue()));
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(merchantEntity.getRole_ID().intValue());
		merchantEntity.setRoleName(roleName);
		merchantEntity.setAction("EDIT");
		merchantEntity.setStatusName(
				adminWorkFlowReqUtility.getStatusNameByStatusId(merchantEntity.getStatusId().intValue()));
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(merchantEntity.getActivityName());
		ResponseMessageBean responsecode = new ResponseMessageBean();
		try {
			if (!ObjectUtils.isEmpty(merchantEntity)) {
				responsecode = merchantservice.checkUpdateMerchantExist(merchantEntity);
				if (responsecode.getResponseCode().equalsIgnoreCase("200")) {
					res = merchantservice.update(merchantEntity);
					if (res) {
						response.setResponseCode("200");
						if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getChecker()))
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

							response.setResponseMessage("Request Submitted To Admin Checker For Approval");
						} else {
							response.setResponseMessage("merchant Details Updated Successfully");
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

	@RequestMapping(value = "/getMerchantById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getCountryById(@RequestBody MerchantEntity merchantData) {
		logger.info("In MerchantController -> getMerchantById()");
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			MerchantEntity resultData = merchantservice.findByMerchantId(merchantData);

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

}
