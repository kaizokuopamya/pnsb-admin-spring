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

import com.itl.pns.bean.RegistrationDetailsBean;
import com.itl.pns.bean.RequestParamBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.constant.ApplicationConstants;
import com.itl.pns.dao.CategoryDao;
import com.itl.pns.entity.ActivitySettingMasterEntity;
import com.itl.pns.entity.CustNotificationCategoriesEntity;
import com.itl.pns.entity.NotificationCategoriesMasterEntity;
import com.itl.pns.entity.NotificationsEntity;
import com.itl.pns.service.CategoryService;
import com.itl.pns.util.AdminWorkFlowReqUtility;
import com.itl.pns.util.EncryptorDecryptor;

/**
 * @author shubham.lokhande
 *
 */
@RestController
@RequestMapping("category")
public class CategoryController {

	private static final Logger logger = LogManager.getLogger(CategoryController.class);

	@Autowired
	CategoryService categoryService;

	@Autowired
	AdminWorkFlowReqUtility adminWorkFlowReqUtility;

	@Autowired
	CategoryDao categoryDao;

	@RequestMapping(value = "/addCategoriesMaster", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> addCategoriesMaster(
			@RequestBody NotificationCategoriesMasterEntity categoryMasterData) {
		logger.info("In Category Controller -> addCategoriesMaster()");
		ResponseMessageBean response = new ResponseMessageBean();
		categoryMasterData.setCreatedByName(
				adminWorkFlowReqUtility.getCreatedByNameByCreatedId(categoryMasterData.getCreatedBy().intValue()));
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(categoryMasterData.getRole_ID().intValue());
		categoryMasterData.setRoleName(roleName);
		categoryMasterData.setAction("ADD");
		categoryMasterData.setStatusName(
				adminWorkFlowReqUtility.getStatusNameByStatusId(categoryMasterData.getStatusId().intValue()));
		categoryMasterData
				.setAppName(adminWorkFlowReqUtility.getAppNameByAppId(categoryMasterData.getAppId().intValue()));
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(categoryMasterData.getActivityName());
		ResponseMessageBean responsecode = new ResponseMessageBean();
		Boolean res = false;

		try {
			if (!ObjectUtils.isEmpty(categoryMasterData)) {
				responsecode = categoryDao.checkCategoryExist(categoryMasterData);
				if (responsecode.getResponseCode().equalsIgnoreCase("200")) {

					res = categoryService.addCategoriesMaster(categoryMasterData);
					if (res) {
						response.setResponseCode("200");
						if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getChecker()))
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

							response.setResponseMessage("Request Submitted To Admin Checker For Approval");
						} else {
							response.setResponseMessage("Category Details Saved Successfully");
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

	@RequestMapping(value = "/updateCategoriesMaster", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateCategoriesMaster(
			@RequestBody NotificationCategoriesMasterEntity categoryMasterData) {
		logger.info("In Category Controller -> updateCategoriesMaster()");
		ResponseMessageBean response = new ResponseMessageBean();
		Boolean res = false;
		categoryMasterData.setCreatedByName(
				adminWorkFlowReqUtility.getCreatedByNameByCreatedId(categoryMasterData.getCreatedBy().intValue()));
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(categoryMasterData.getRole_ID().intValue());
		categoryMasterData.setRoleName(roleName);
		categoryMasterData.setAction("EDIT");
		categoryMasterData
				.setAppName(adminWorkFlowReqUtility.getAppNameByAppId(categoryMasterData.getAppId().intValue()));
		categoryMasterData.setStatusName(
				adminWorkFlowReqUtility.getStatusNameByStatusId(categoryMasterData.getStatusId().intValue()));
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(categoryMasterData.getActivityName());
		ResponseMessageBean responsecode = new ResponseMessageBean();
		try {
			if (!ObjectUtils.isEmpty(categoryMasterData)) {
				responsecode = categoryDao.checkUpdateCategoryExist(categoryMasterData);
				if (responsecode.getResponseCode().equalsIgnoreCase("200")) {
					res = categoryService.updateCategoriesMaster(categoryMasterData);
					if (res) {
						response.setResponseCode("200");
						if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getChecker()))
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

							response.setResponseMessage("Request Submitted To Admin Checker For Approval");
						} else {
							response.setResponseMessage("Category Details Updated Successfully");
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

	@RequestMapping(value = "/getCategoriesMasterById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getCategoriesMasterById(@RequestBody RequestParamBean requestBean) {
		logger.info("In Category Controller -> getCategoriesMasterById()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<NotificationCategoriesMasterEntity> categoryMasterData = categoryService
					.getCategoriesMasterById(Integer.parseInt(requestBean.getId1()));

			if (null != categoryMasterData) {
				res.setResponseCode("200");
				res.setResult(categoryMasterData);
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

	@RequestMapping(value = "/getAllCategoriesMaster", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getAllCategoriesMaster() {
		logger.info("In Category Controller -> getAllCategoriesMaster()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<NotificationCategoriesMasterEntity> categoryMasterData = categoryService.getAllCategoriesMaster();

			if (!ObjectUtils.isEmpty(categoryMasterData)) {
				res.setResponseCode("200");
				res.setResult(categoryMasterData);
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

	@RequestMapping(value = "/addCustNotificationCategories", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> addCustNotificationCategories(
			@RequestBody CustNotificationCategoriesEntity categoryMasterData) {
		logger.info("In Category Controller -> addCustNotificationCategories()");
		ResponseMessageBean response = new ResponseMessageBean();
		System.out.println("g**********" + categoryMasterData.getFromTime());
		categoryMasterData.setCreatedByName(
				adminWorkFlowReqUtility.getCreatedByNameByCreatedId((categoryMasterData.getCreatedBy().intValue())));
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(categoryMasterData.getRole_ID().intValue());
		categoryMasterData.setRoleName(roleName);
		categoryMasterData.setAction("ADD");
		categoryMasterData.setStatusName(
				adminWorkFlowReqUtility.getStatusNameByStatusId(categoryMasterData.getStatusId().intValue()));
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(categoryMasterData.getActivityName());

		Boolean res = false;
		try {
			if (!ObjectUtils.isEmpty(categoryMasterData)) {
				res = categoryService.addCustNotificationCategories(categoryMasterData);
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

	@RequestMapping(value = "/updateCustNotificationCategories", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateCustNotificationCategories(
			@RequestBody CustNotificationCategoriesEntity categoryMasterData) {
		logger.info("In Category Controller -> updateCustNotificationCategories()");
		ResponseMessageBean response = new ResponseMessageBean();
		Boolean res = false;
		categoryMasterData.setCreatedByName(
				adminWorkFlowReqUtility.getCreatedByNameByCreatedId(categoryMasterData.getCreatedBy().intValue()));
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(categoryMasterData.getRole_ID().intValue());
		categoryMasterData.setRoleName(roleName);
		categoryMasterData.setAction("EDIT");
		categoryMasterData.setStatusName(
				adminWorkFlowReqUtility.getStatusNameByStatusId(categoryMasterData.getStatusId().intValue()));
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(categoryMasterData.getActivityName());

		try {
			if (!ObjectUtils.isEmpty(categoryMasterData)) {

				res = categoryService.updateCustNotificationCategories(categoryMasterData);
				if (res) {
					response.setResponseCode("200");
					if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
							&& (ApplicationConstants.YESVALUE)
									.equals(Character.toString(activityMasterData.get(0).getChecker()))
							&& (ApplicationConstants.YESVALUE)
									.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

						response.setResponseMessage("Request Submitted To Admin Checker For Approval");
					} else {
						response.setResponseMessage("Notification Ctegory  Updated Successfully");
					}

				} else {
					response.setResponseCode("202");
					response.setResponseMessage("Error While Updating Records");
				}

			}
		} catch (Exception e) {
			logger.info("Exception:", e);
			response.setResponseCode("500");
			response.setResponseMessage("Error While Updating Records!");
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/getCustNotificationCategoriesById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getCustNotificationCategoriesById(
			@RequestBody RequestParamBean requestBean) {
		logger.info("In Category Controller -> getCustNotificationCategoriesById()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<CustNotificationCategoriesEntity> categoryMasterData = categoryService
					.getCustNotificationCategoriesById(Integer.parseInt(requestBean.getId1()));
			for (CustNotificationCategoriesEntity custNotiData : categoryMasterData) {

				List<RegistrationDetailsBean> custData = adminWorkFlowReqUtility
						.getCustDataById(custNotiData.getCustomerId().toBigInteger());
				custNotiData.setCustomerName(custData.get(0).getCUSTOMERNAME());
				custNotiData.setCIF(custData.get(0).getCIF());
			}

			if (null != categoryMasterData) {

				for (CustNotificationCategoriesEntity record : categoryMasterData) {

					if (record.getCustomerName() != null && record.getCustomerName().contains("=")) {
						record.setCustomerName(EncryptorDecryptor.decryptData(record.getCustomerName()));
					}
					if (record.getCIF() != null && record.getCIF().contains("=")) {
						record.setCIF(EncryptorDecryptor.decryptData(record.getCIF()));
					}
				}
				res.setResponseCode("200");
				res.setResult(categoryMasterData);
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

	@RequestMapping(value = "/getAllCustNotificationCategories", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getAllCustNotificationCategories() {
		logger.info("In Category Controller -> getAllCustNotificationCategories()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<CustNotificationCategoriesEntity> categoryMasterData = categoryService
					.getAllCustNotificationCategories();

			if (!ObjectUtils.isEmpty(categoryMasterData)) {

				for (CustNotificationCategoriesEntity record : categoryMasterData) {

					if (record.getCustomerName() != null && record.getCustomerName().contains("=")) {
						record.setCustomerName(EncryptorDecryptor.decryptData(record.getCustomerName()));
					}
				}
				res.setResponseCode("200");
				res.setResult(categoryMasterData);
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
