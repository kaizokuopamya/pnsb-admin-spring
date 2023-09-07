package com.itl.pns.controller;

import java.math.BigInteger;
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

import com.itl.pns.bean.DateBean;
import com.itl.pns.bean.FacilityStatusBean;
import com.itl.pns.bean.LanguageJsonBean;
import com.itl.pns.bean.ProductBean;
import com.itl.pns.bean.RequestParamBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.bean.TransactionLogBean;
import com.itl.pns.bean.UserAccountLeadsBean;
import com.itl.pns.constant.ApplicationConstants;
import com.itl.pns.dao.MasterConfigDao;
import com.itl.pns.entity.AccountSchemeMasterEntity;
import com.itl.pns.entity.ActivityMaster;
import com.itl.pns.entity.ActivitySettingMasterEntity;
import com.itl.pns.entity.AppMasterEntity;
import com.itl.pns.entity.CertificateConfigEntity;
import com.itl.pns.entity.ConfigMasterEntity;
import com.itl.pns.entity.LanguageJson;
import com.itl.pns.entity.LimitMasterEntity;
import com.itl.pns.entity.MaskingRulesEntity;
import com.itl.pns.entity.OfferDetailsEntity;
import com.itl.pns.entity.Product;
import com.itl.pns.entity.SchedularTransMasterEntity;
import com.itl.pns.entity.StatusMasterEntity;
import com.itl.pns.service.MasterConfigService;
import com.itl.pns.util.AdminWorkFlowReqUtility;
import com.itl.pns.util.EncryptDeryptUtility;
import com.itl.pns.util.EncryptorDecryptor;

@RestController
@RequestMapping("masterconfig")
public class MasterConfigController {

	private static final Logger logger = LogManager.getLogger(MasterConfigController.class);

	@Autowired
	MasterConfigService masterConfigService;

	@Autowired
	MasterConfigDao masterConfigDao;

	@Autowired
	AdminWorkFlowReqUtility adminWorkFlowReqUtility;

	@RequestMapping(value = "/getLanguageJson", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getLanguageJson() {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<LanguageJson> languagejsonlist = masterConfigService.getLanguageJson();

			if (!ObjectUtils.isEmpty(languagejsonlist)) {
				res.setResponseCode("200");
				res.setResult(languagejsonlist);
			} else {
				res.setResponseCode("202");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}

		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/addLanguagejson", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> addLanguagejson(@RequestBody LanguageJson languagejson) {

		int userStatus = languagejson.getStatusId().intValue();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(languagejson.getRole_ID().intValue());
		languagejson
				.setStatusName(adminWorkFlowReqUtility.getStatusNameByStatusId(languagejson.getStatusId().intValue()));
		languagejson.setRoleName(roleName);
		languagejson.setCreatedByName(
				adminWorkFlowReqUtility.getCreatedByNameByCreatedId(languagejson.getCreatedby().intValue()));
		languagejson.setAction("ADD");
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(languagejson.getActivityName());
		ResponseMessageBean res = new ResponseMessageBean();
		ResponseMessageBean responsecode = new ResponseMessageBean();

		try {
			String decReq = EncryptDeryptUtility.decryptNonAndroid(languagejson.getLanguagetext(), "@MrN$2Qi8R");
			languagejson.setLanguagetext(decReq);
			String encLanText = EncryptorDecryptor.encryptDataForLangJson(languagejson.getLanguagetext());
			languagejson.setLanguagetext(encLanText);

			String decLangCode = EncryptDeryptUtility.decryptNonAndroid(languagejson.getLanguagecodedesc(),
					"@MrN$2Qi8R");
			String encLangCode = EncryptorDecryptor.encryptDataForLangJson(decLangCode);
			languagejson.setLanguagecodedesc(encLangCode);

		} catch (Exception e) {
			logger.info("Exception:", e);
		}

		responsecode = masterConfigDao.isEnglsihTextExist(languagejson);
		if (responsecode.getResponseCode().equalsIgnoreCase("200")) {

			boolean isAdded = masterConfigService.addLanguagejson(languagejson);

			if (isAdded) {
				masterConfigService.addLanguageJsonToAdminWorkFlow(languagejson, userStatus);
				// List<LanguageJson> list =
				// masterConfigService.getLanguageJson();
				res.setResponseCode("200");
				res.setResult("Success");

				if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
						&& (ApplicationConstants.YESVALUE)
								.equals(Character.toString(activityMasterData.get(0).getChecker()))
						&& (ApplicationConstants.YESVALUE)
								.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
					res.setResponseMessage("Request Submitted To Admin Checker For Approval");
				} else {
					res.setResponseMessage("Language Json Details Added Successfully");
				}

			} else {
				res.setResponseCode("500");
				res.setResponseMessage("Error While Adding Language Json Details");
			}
		} else {
			res.setResponseCode("202");
			res.setResponseMessage(responsecode.getResponseMessage());
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/getLanguageJsonById", method = RequestMethod.POST)
	public ResponseEntity<List<LanguageJsonBean>> getLanguageJsonById(@RequestBody RequestParamBean requestBean) {
		List<LanguageJsonBean> languagejson = masterConfigService
				.getLanguageJsonById(Integer.parseInt(requestBean.getId1()));
		return new ResponseEntity<>(languagejson, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateLanguageJson", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateLanguageJson(@RequestBody LanguageJson languagejson) {
		;
		languagejson
				.setStatusName(adminWorkFlowReqUtility.getStatusNameByStatusId(languagejson.getStatusId().intValue()));

		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(languagejson.getRole_ID().intValue());
		languagejson
				.setStatusName(adminWorkFlowReqUtility.getStatusNameByStatusId(languagejson.getStatusId().intValue()));
		languagejson.setRoleName(roleName);
		languagejson
				.setStatusName(adminWorkFlowReqUtility.getStatusNameByStatusId(languagejson.getStatusId().intValue()));
		languagejson.setCreatedByName(
				adminWorkFlowReqUtility.getCreatedByNameByCreatedId(languagejson.getCreatedby().intValue()));
		languagejson.setAction("EDIT");
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(languagejson.getActivityName());
		ResponseMessageBean res = new ResponseMessageBean();
		ResponseMessageBean responsecode = new ResponseMessageBean();

		String decReq = EncryptDeryptUtility.decryptNonAndroid(languagejson.getLanguagetext(), "@MrN$2Qi8R");

		try {

			languagejson.setLanguagetext(decReq);
			String encLanText = EncryptorDecryptor.encryptDataForLangJson(languagejson.getLanguagetext());
			languagejson.setLanguagetext(encLanText);

		} catch (Exception e) {
			logger.info("Exception:", e);
		}

		responsecode = masterConfigDao.isUpdateEnglsihTextExist(languagejson);
		if (responsecode.getResponseCode().equalsIgnoreCase("200")) {
			boolean isupdated = masterConfigService.updateLanguageJson(languagejson);

			if (isupdated) {
				res.setResponseCode("200");
				if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
						&& (ApplicationConstants.YESVALUE)
								.equals(Character.toString(activityMasterData.get(0).getChecker()))
						&& (ApplicationConstants.YESVALUE)
								.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

					res.setResponseMessage("Request Submitted To Admin Checker For Approval");
				} else {
					res.setResponseMessage(" Data Has Been Updated Successfully");
				}
			} else {
				res.setResponseCode("500");
				res.setResponseMessage(" Error While Updaing  Details");
			}
		} else {
			res.setResponseCode("202");
			res.setResponseMessage(responsecode.getResponseMessage());
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/getConfigMaster", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getConfigMaster() {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<ConfigMasterEntity> list = masterConfigService.getConfigMaster();

			if (!ObjectUtils.isEmpty(list)) {
				res.setResponseCode("200");
				res.setResponseMessage("Configuration List");
				res.setResult(list);
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("Empty List");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/addConfigMaster", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> addConfigMasterDetails(
			@RequestBody ConfigMasterEntity configMasterEntity) {
		logger.info("addConfig : Start");
		ResponseMessageBean res = new ResponseMessageBean();
		int userStatus = configMasterEntity.getStatusId().intValue();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(configMasterEntity.getRole_ID().intValue());
		configMasterEntity
				.setAppname(adminWorkFlowReqUtility.getAppNameByAppId(configMasterEntity.getAppId().intValue()));
		configMasterEntity.setRoleName(roleName);
		configMasterEntity.setStatusname(
				adminWorkFlowReqUtility.getStatusNameByStatusId(configMasterEntity.getStatusId().intValue()));
		configMasterEntity
				.setProductName(adminWorkFlowReqUtility.getAppNameByAppId(configMasterEntity.getAppId().intValue()));
		configMasterEntity.setCreatedByName(
				adminWorkFlowReqUtility.getCreatedByNameByCreatedId(configMasterEntity.getCreatedBy().intValue()));
		configMasterEntity.setAction("ADD");

		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(configMasterEntity.getActivityName());
		ResponseMessageBean responsecode = new ResponseMessageBean();
		responsecode = masterConfigDao.checkConfigKeyExist(configMasterEntity);
		try {
			if (responsecode.getResponseCode().equalsIgnoreCase("200")) {
				boolean isAdded = masterConfigService.addConfigMasterDetails(configMasterEntity);

				boolean isDataRefresh = false;

				if (isAdded) {
					isDataRefresh = adminWorkFlowReqUtility.refreshCacheData("ConfigurationMasterReader");
					masterConfigService.addToadminWorkFlowReq(configMasterEntity, userStatus);
					res.setResponseCode("200");
					if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
							&& (ApplicationConstants.YESVALUE)
									.equals(Character.toString(activityMasterData.get(0).getChecker()))
							&& (ApplicationConstants.YESVALUE)
									.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

						res.setResponseMessage("Request Submitted To Admin Checker For Approval");
					} else {
						res.setResponseMessage(" Config Master Details Has Been Added Successfully");
					}

				} else {
					res.setResponseCode("500");
					res.setResponseMessage(" Error While Adding Config Master Details");
				}
			} else {
				res.setResponseCode(responsecode.getResponseCode());
				res.setResponseMessage(responsecode.getResponseMessage());
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		logger.info("addConfig : End");
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateConfigMaster", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateConfigMaster(@RequestBody ConfigMasterEntity configMasterBean) {
		logger.info("updateConfig : Start");
		boolean isDataRefresh = false;
		ResponseMessageBean responsecode = new ResponseMessageBean();
		ResponseMessageBean res = new ResponseMessageBean();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(configMasterBean.getRole_ID().intValue());
		configMasterBean.setStatusname(
				adminWorkFlowReqUtility.getStatusNameByStatusId(configMasterBean.getStatusId().intValue()));
		configMasterBean.setAppname(adminWorkFlowReqUtility.getAppNameByAppId(configMasterBean.getAppId().intValue()));
		configMasterBean.setRoleName(roleName);
		configMasterBean
				.setProductName(adminWorkFlowReqUtility.getAppNameByAppId(configMasterBean.getAppId().intValue()));
		configMasterBean.setCreatedByName(
				adminWorkFlowReqUtility.getCreatedByNameByCreatedId(configMasterBean.getCreatedBy().intValue()));
		configMasterBean.setAction("EDIT");
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(configMasterBean.getActivityName());
		try {
			responsecode = masterConfigDao.checkUpdateConfigKeyExist(configMasterBean);

			if (responsecode.getResponseCode().equalsIgnoreCase("200")) {

				boolean isupdated = masterConfigService.updateConfigMaster(configMasterBean);

				if (isupdated) {
					isDataRefresh = adminWorkFlowReqUtility.refreshCacheData("ConfigurationMasterReader");
					res.setResponseCode("200");
					if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
							&& (ApplicationConstants.YESVALUE)
									.equals(Character.toString(activityMasterData.get(0).getChecker()))
							&& (ApplicationConstants.YESVALUE)
									.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

						res.setResponseMessage("Request Submitted To Admin Checker For Approval");
					} else {
						res.setResponseMessage("Config Master Details Has Been Updated Successfully");
					}

				} else {
					res.setResponseCode("500");
					res.setResponseMessage(" Error While Updaing Config Master Details");
				}
			} else {
				res.setResponseCode(responsecode.getResponseCode());
				res.setResponseMessage(responsecode.getResponseMessage());
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}

		logger.info("updateConfig : End");
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/getConfigById", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getConfigById(@RequestBody RequestParamBean requestBean) {
		ResponseMessageBean bean = new ResponseMessageBean();
		try {
			List<ConfigMasterEntity> configmasterbean = masterConfigService
					.getConfigById(new BigInteger(requestBean.getId1()));

			if (null != configmasterbean) {
				bean.setResponseCode("200");
				bean.setResult(configmasterbean);
			} else {
				bean.setResponseCode("202");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(bean, HttpStatus.OK);
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getStatusList", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getStatus() {
		ResponseMessageBean res = new ResponseMessageBean();
		List<StatusMasterEntity> statusList = null;
		try {
			statusList = masterConfigService.getStatus();
			if (!ObjectUtils.isEmpty(statusList)) {
				res.setResponseCode("200");
				res.setResult(statusList);
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("Empty List");
			}
		} catch (Exception e) {
			logger.error("Exception while fetching Status List", e);
		}

		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getAppMasterList", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getAppMasterList() {
		ResponseMessageBean res = new ResponseMessageBean();
		List<AppMasterEntity> getTickets = null;
		try {
			logger.info("before calling appmaster list");
			getTickets = masterConfigService.getAppMasterList();
			logger.info("after calling appmaster list: " + getTickets.toString());
			if (!ObjectUtils.isEmpty(getTickets)) {
				res.setResponseCode("200");
				res.setResult(getTickets);
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("Empty List");
			}
		} catch (Exception e) {
			logger.error("Exception while fetching appMaster List", e);
		}

		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/getOfferDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getOfferDetails() {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<OfferDetailsEntity> list = masterConfigService.getOfferDetails();

			if (!ObjectUtils.isEmpty(list)) {
				res.setResponseCode("200");
				res.setResult(list);
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("Empty List");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/getAllTransactionLogByDate", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getAllTransactionLogByDate(@RequestBody DateBean datebean) {
		List<TransactionLogBean> list = masterConfigService.getAllTransactionLogByDate(datebean);
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			if (!list.isEmpty()) {
				res.setResponseCode("200");
				res.setResult(list);
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/saveProductDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> saveProductDetails(@RequestBody Product product) {
		ResponseMessageBean res = new ResponseMessageBean();
		ResponseMessageBean responsecode = new ResponseMessageBean();
		ResponseMessageBean response = new ResponseMessageBean();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(product.getRole_ID().intValue());
		product.setStatusName(adminWorkFlowReqUtility.getStatusNameByStatusId(product.getStatusId()));
		product.setAppName(adminWorkFlowReqUtility.getAppNameByAppId(product.getAppId().intValue()));
		product.setRoleName(roleName);
		product.setStatusName(adminWorkFlowReqUtility.getStatusNameByStatusId(product.getStatusId()));
		product.setCreatedByName(adminWorkFlowReqUtility.getCreatedByNameByCreatedId(product.getCreatedby()));
		product.setProductCategoryName(
				adminWorkFlowReqUtility.getProductCategoryType(product.getProdType()).get(0).getProductName());
		product.setAction("ADD");
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(product.getActivityName());

		try {
			responsecode = masterConfigService.checkProduct(product);
			if (responsecode.getResponseCode().equalsIgnoreCase("200")) {
				masterConfigService.saveProductDetails(product);
				res.setResponseCode("200");

				if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
						&& (ApplicationConstants.YESVALUE)
								.equals(Character.toString(activityMasterData.get(0).getChecker()))
						&& (ApplicationConstants.YESVALUE)
								.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
					res.setResponseMessage("Request Submitted To Admin Checker For Approval");
				} else {
					res.setResponseMessage("Product Details Has Been Saved Successfully");
				}

			} else {
				res.setResponseCode("202");
				res.setResponseMessage(responsecode.getResponseMessage());
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
			res.setResponseCode("500");
			res.setResponseMessage("Error Occured While Updating The Record");
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateProductDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateProductDetails(@RequestBody Product product) {
		ResponseMessageBean res = new ResponseMessageBean();
		ResponseMessageBean responsecode = new ResponseMessageBean();
		product.setStatusName(adminWorkFlowReqUtility.getStatusNameByStatusId(product.getStatusId()));
		product.setAppName(adminWorkFlowReqUtility.getAppNameByAppId(product.getAppId().intValue()));
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(product.getRole_ID().intValue());
		product.setRoleName(roleName);
		product.setCreatedByName(adminWorkFlowReqUtility.getCreatedByNameByCreatedId(product.getCreatedby()));
		product.setAction("EDIT");
		product.setProductCategoryName(
				adminWorkFlowReqUtility.getProductCategoryType(product.getProdType()).get(0).getProductName());
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(product.getActivityName());

		try {
			responsecode = masterConfigService.checkUpdateProduct(product);
			if (responsecode.getResponseCode().equalsIgnoreCase("200")) {
				masterConfigService.updateProductDetails(product);
				res.setResponseCode("200");
				if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
						&& (ApplicationConstants.YESVALUE)
								.equals(Character.toString(activityMasterData.get(0).getChecker()))
						&& (ApplicationConstants.YESVALUE)
								.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
					res.setResponseMessage("Request Submitted To Admin Checker For Approval");
				} else {
					res.setResponseMessage("Product Has Been Updated Successfully");
				}
			} else {
				res.setResponseCode("202");
				res.setResponseMessage(responsecode.getResponseMessage());
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
			res.setResponseCode("500");
			res.setResponseMessage("Error Occured While Updating The Record");
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/getProducts", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getProducts() {
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			List<ProductBean> getProduct = masterConfigService.getProducts();

			if (!getProduct.isEmpty()) {
				response.setResponseCode("200");
				response.setResult(getProduct);
				response.setResponseMessage("Success");
			} else {
				response.setResponseCode("202");
				response.setResponseMessage("No record found");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/getProductById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getProductById(@RequestBody RequestParamBean requestBean) {
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			List<ProductBean> product = masterConfigService.getProductById(Integer.parseInt(requestBean.getId1()));

			if (!product.isEmpty()) {
				response.setResponseCode("200");
				response.setResult(product);
				response.setResponseMessage("Success");
			} else {
				response.setResponseCode("202");
				response.setResponseMessage("No record found");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/getProductType", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getProductType() {
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			List<ProductBean> getProduct = masterConfigService.getProductType();

			if (!getProduct.isEmpty()) {
				response.setResponseCode("200");
				response.setResult(getProduct);
				response.setResponseMessage("Success");
			} else {
				response.setResponseCode("202");
				response.setResponseMessage("No record found");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateFacilityStatus", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ActivityMaster>> updateFacilityStatus(@RequestBody ActivityMaster activitymaster) {
		boolean isDataRefresh = false;
		masterConfigService.updateFacilityStatus(activitymaster);
		isDataRefresh = adminWorkFlowReqUtility.refreshCacheData("ActivityMasterReader");
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/addFacilityStatus", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> addFacilityStatus(@RequestBody ActivityMaster activitymaster) {
		int userStatus = activitymaster.getStatusid();
		boolean isDataRefresh = false;
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(activitymaster.getRole_ID().intValue());
		activitymaster.setStatusName(adminWorkFlowReqUtility.getStatusNameByStatusId(activitymaster.getStatusid()));
		activitymaster.setAppName(adminWorkFlowReqUtility.getAppNameByAppId(activitymaster.getAppid()));
		activitymaster.setRoleName(roleName);
		activitymaster.setProductName(adminWorkFlowReqUtility.getAppNameByAppId(activitymaster.getAppid()));
		activitymaster
				.setCreatedByName(adminWorkFlowReqUtility.getCreatedByNameByCreatedId(activitymaster.getCreatedby()));
		activitymaster.setAction("ADD");
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(activitymaster.getActivityName());
		ResponseMessageBean res = new ResponseMessageBean();
		ResponseMessageBean responsecode = new ResponseMessageBean();
		try {
			responsecode = masterConfigDao.isFacilityStatusExist(activitymaster);
			if (responsecode.getResponseCode().equalsIgnoreCase("200")) {

				boolean isAdded = masterConfigService.addFacilityStatus(activitymaster);

				if (isAdded) {
					isDataRefresh = adminWorkFlowReqUtility.refreshCacheData("ActivityMasterReader");
					res.setResponseCode("200");
					masterConfigService.addFacilityStatusToAdminWorkFlow(activitymaster, userStatus);
					if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
							&& (ApplicationConstants.YESVALUE)
									.equals(Character.toString(activityMasterData.get(0).getChecker()))
							&& (ApplicationConstants.YESVALUE)
									.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

						res.setResponseMessage("Request Submitted To Admin Checker For Approval");
					} else {
						res.setResponseMessage(" Facility Master Has Been Added Successfully");
					}
				} else {
					res.setResponseCode("500");
					res.setResponseMessage(" Error While Adding Facility Status");
				}
			} else {
				res.setResponseCode(responsecode.getResponseCode());
				res.setResponseMessage(responsecode.getResponseMessage());
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/getAllFacilityStatus", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getAllFacilityStatus() {
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			List<FacilityStatusBean> list = masterConfigService.getAllFacilityStatus();

			if (!list.isEmpty()) {
				response.setResponseCode("200");
				response.setResult(list);
				response.setResponseMessage("Success");
			} else {
				response.setResponseCode("202");
				response.setResponseMessage("No record found");
			}

		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@RequestMapping(value = "/getAllFacilityStatusById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getAllFacilityStatusById(@RequestBody RequestParamBean requestBean) {
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			List<FacilityStatusBean> list = masterConfigService
					.getAllFacilityStatusById(Integer.parseInt(requestBean.getId1()));

			if (!list.isEmpty()) {
				response.setResponseCode("200");
				response.setResult(list);
				response.setResponseMessage("Success");
			} else {
				response.setResponseCode("202");
				response.setResponseMessage("No record found");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@RequestMapping(value = "/updateFacilityStatusDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateFacilityStatusDetails(@RequestBody ActivityMaster activitymaster) {
		boolean isDataRefresh = false;
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(activitymaster.getRole_ID().intValue());
		activitymaster.setStatusName(adminWorkFlowReqUtility.getStatusNameByStatusId(activitymaster.getStatusid()));
		activitymaster.setAppName(adminWorkFlowReqUtility.getAppNameByAppId(activitymaster.getAppid()));
		activitymaster.setRoleName(roleName);
		activitymaster.setProductName(adminWorkFlowReqUtility.getAppNameByAppId(activitymaster.getAppid()));
		activitymaster
				.setCreatedByName(adminWorkFlowReqUtility.getCreatedByNameByCreatedId(activitymaster.getCreatedby()));
		activitymaster.setAction("EDIT");
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(activitymaster.getActivityName());

		ResponseMessageBean res = new ResponseMessageBean();
		ResponseMessageBean responsecode = new ResponseMessageBean();
		try {
			responsecode = masterConfigDao.isUpdateFacilityStatusExist(activitymaster);
			if (responsecode.getResponseCode().equalsIgnoreCase("200")) {
				boolean isupdated = masterConfigService.updateFacilityStatusDetails(activitymaster);
				if (isupdated) {
					isDataRefresh = adminWorkFlowReqUtility.refreshCacheData("ActivityMasterReader");
					res.setResponseCode("200");

					if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
							&& (ApplicationConstants.YESVALUE)
									.equals(Character.toString(activityMasterData.get(0).getChecker()))
							&& (ApplicationConstants.YESVALUE)
									.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

						res.setResponseMessage("Request Submitted To Admin Checker For Approval");
					} else {
						res.setResponseMessage("Facility Master Has Been Updated Successfully");
					}
				} else {
					res.setResponseCode("500");
					res.setResponseMessage(" Error While Updaing Facility Master");
				}
			} else {
				res.setResponseCode(responsecode.getResponseCode());
				res.setResponseMessage(responsecode.getResponseMessage());
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/getMaskingRulesList", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getMaskingRulesList() {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<MaskingRulesEntity> maskingRulesEntity = masterConfigService.getMaskingRulesList();

			if (!ObjectUtils.isEmpty(maskingRulesEntity)) {
				res.setResponseCode("200");
				res.setResult(maskingRulesEntity);
			} else {
				res.setResponseCode("202");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}

		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/addMaskingRules", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> addMaskingRules(@RequestBody MaskingRulesEntity maskingRulesEntity) {
		int userStatus = maskingRulesEntity.getStatusid().intValue();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(maskingRulesEntity.getRole_ID().intValue());
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(maskingRulesEntity.getActivityName());
		maskingRulesEntity.setStatusname(
				adminWorkFlowReqUtility.getStatusNameByStatusId(maskingRulesEntity.getStatusid().intValue()));
		maskingRulesEntity
				.setAppname(adminWorkFlowReqUtility.getAppNameByAppId(maskingRulesEntity.getAppid().intValue()));
		maskingRulesEntity.setCreatedByName(
				adminWorkFlowReqUtility.getCreatedByNameByCreatedId(maskingRulesEntity.getCreatedby().intValue()));
		maskingRulesEntity.setRoleName(roleName);
		maskingRulesEntity.setAction("ADD");

		boolean isAdded = masterConfigService.addMaskingRules(maskingRulesEntity);
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			if (isAdded) {
				masterConfigService.addMaskingRulesToAdminWorkFlow(maskingRulesEntity, userStatus);
				res.setResponseCode("200");
				if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
						&& (ApplicationConstants.YESVALUE)
								.equals(Character.toString(activityMasterData.get(0).getChecker()))
						&& (ApplicationConstants.YESVALUE)
								.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
					res.setResponseMessage("Request Submitted To Admin Checker For Approval");
				} else {
					res.setResponseMessage("Masking Rules Has Been Added Successfully");
				}

			} else {
				res.setResponseCode("500");
				res.setResponseMessage(" Error While Adding Masking Rules");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/getMaskingRulesId", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getMaskingRulesId(@RequestBody RequestParamBean requestBean) {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<MaskingRulesEntity> maskingRulesEntity = masterConfigService
					.getMaskingRulesId(Integer.parseInt(requestBean.getId1()));

			if (!ObjectUtils.isEmpty(maskingRulesEntity)) {
				res.setResponseCode("200");
				res.setResult(maskingRulesEntity);
			} else {
				res.setResponseCode("500");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateMaskingRules", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateMaskingRules(@RequestBody MaskingRulesEntity maskingRulesEntity) {
		ResponseMessageBean res = new ResponseMessageBean();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(maskingRulesEntity.getRole_ID().intValue());
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(maskingRulesEntity.getActivityName());
		maskingRulesEntity.setStatusname(
				adminWorkFlowReqUtility.getStatusNameByStatusId(maskingRulesEntity.getStatusid().intValue()));
		maskingRulesEntity
				.setAppname(adminWorkFlowReqUtility.getAppNameByAppId(maskingRulesEntity.getAppid().intValue()));
		maskingRulesEntity.setRoleName(roleName);
		maskingRulesEntity.setAction("EDIT");
		try {
			boolean isupdated = masterConfigService.updateMaskingRules(maskingRulesEntity);
			if (isupdated) {
				res.setResponseCode("200");
				if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
						&& (ApplicationConstants.YESVALUE)
								.equals(Character.toString(activityMasterData.get(0).getChecker()))
						&& (ApplicationConstants.YESVALUE)
								.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
					res.setResponseMessage("Request Submitted To Admin Checker For Approval");
				} else {
					res.setResponseMessage("Data Has Been Updated Successfully");
				}

			} else {
				res.setResponseCode("500");
				res.setResponseMessage(" Error While Updaing  Details");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/getCustAccountDetailsById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getCustAccountDetailsById(@RequestBody RequestParamBean requestBean) {
		logger.info("In Category Controller -> getCustAccountDetailsById()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<UserAccountLeadsBean> userAccountList = masterConfigService
					.getCustAccountDetailsById(Integer.parseInt(requestBean.getId1()));

			if (!ObjectUtils.isEmpty(userAccountList)) {
				res.setResponseCode("200");
				res.setResult(userAccountList);
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

	@RequestMapping(value = "/getCustAllAccountDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getCustAllAccountDetails() {
		logger.info("In Category Controller -> getCustAllAccountDetails()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<UserAccountLeadsBean> userAccountList = masterConfigService.getCustAllAccountDetails();

			if (!ObjectUtils.isEmpty(userAccountList)) {
				res.setResponseCode("200");
				res.setResult(userAccountList);
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

	@RequestMapping(value = "/getDistinctLanguageJsonCode", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getDistinctLanguageJsonCode() {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<LanguageJson> languagejsonlist = masterConfigService.getDistinctLanguageJsonCode();

			if (!ObjectUtils.isEmpty(languagejsonlist)) {
				res.setResponseCode("200");
				res.setResult(languagejsonlist);
			} else {
				res.setResponseCode("202");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}

		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/getLanguageJsonByLangCode", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getLanguageJsonByLangCode(@RequestBody LanguageJson languagejson) {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<LanguageJson> languagejsonlist = masterConfigService.getLanguageJsonByLangCode(languagejson);

			if (!ObjectUtils.isEmpty(languagejsonlist)) {
				res.setResponseCode("200");
				res.setResult(languagejsonlist);
			} else {
				res.setResponseCode("202");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}

		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/getLanguageJsonByLangText", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getLanguageJsonByLangText(@RequestBody LanguageJson languagejson) {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<LanguageJson> languagejsonlist = masterConfigService.getLanguageJsonByLangText(languagejson);

			if (!ObjectUtils.isEmpty(languagejsonlist)) {
				res.setResponseCode("200");
				res.setResult(languagejsonlist);
			} else {
				res.setResponseCode("202");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}

		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateLanguageJsonList", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateLanguageJsonList(@RequestBody List<LanguageJson> languagejson) {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			boolean isupdated = masterConfigService.updateLanguageJsonList(languagejson);

			if (isupdated) {
				res.setResponseCode("200");

				res.setResponseMessage("Language Json Updated Successfully");

			} else {
				res.setResponseCode("500");
				res.setResponseMessage("Error While Updating Language Json");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	/*
	 * @RequestMapping(value = "/getCertificateConfigMaster", method =
	 * RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE) public
	 * ResponseEntity<ResponseMessageBean> getCertificateConfigMaster(@RequestBody
	 * RequestParamBean requestBean) { logger.
	 * info("get calculator MasterDetails by id -> getCertificateConfigMaster()");
	 * ResponseMessageBean res = new ResponseMessageBean();
	 * List<CertificateConfigEntity> list =
	 * masterConfigService.getCertificateConfigMaster(); if
	 * (!ObjectUtils.isEmpty(list)) { res.setResponseCode("200");
	 * res.setResponseMessage("Configuration List"); res.setResult(list); } else {
	 * res.setResponseCode("202"); res.setResponseMessage("Empty List"); } return
	 * new ResponseEntity<>(res, HttpStatus.OK); }
	 */

	@RequestMapping(value = "/addCertificateConfigMaster", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> addCertificateConfigMaster(
			@RequestBody CertificateConfigEntity categoryMasterData) {
		logger.info("addConfig : Start");
		ResponseMessageBean response = new ResponseMessageBean();
		categoryMasterData.setCreatedbyname(
				adminWorkFlowReqUtility.getCreatedByNameByCreatedId(categoryMasterData.getCreatedby().intValue()));
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(categoryMasterData.getRole_ID().intValue());
		categoryMasterData.setRoleName(roleName);
		categoryMasterData.setAction("ADD");
		categoryMasterData.setStatusname(
				adminWorkFlowReqUtility.getStatusNameByStatusId(categoryMasterData.getStatusid().intValue()));
		categoryMasterData
				.setAppname(adminWorkFlowReqUtility.getAppNameByAppId(categoryMasterData.getAppid().intValue()));
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(categoryMasterData.getActivityName());
		ResponseMessageBean responsecode = new ResponseMessageBean();
		Boolean res = false;

		try {
			if (!ObjectUtils.isEmpty(categoryMasterData)) {
				responsecode = masterConfigDao.checkConfigKeyExist(categoryMasterData);
				if (responsecode.getResponseCode().equalsIgnoreCase("200")) {

					res = masterConfigService.addCertificateConfigMaster(categoryMasterData);
					if (res) {
						response.setResponseCode("200");
						if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getChecker()))
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

							response.setResponseMessage("Request Submitted To Admin Checker For Approval");
						} else {
							response.setResponseMessage("Certificate Details Saved Successfully");
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

	@RequestMapping(value = "/updateCertificateConfigMaster", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateCertificateConfigMaster(
			@RequestBody CertificateConfigEntity categoryMasterData) {
		logger.info("In Category Controller -> updateCategoriesMaster()");
		ResponseMessageBean response = new ResponseMessageBean();
		Boolean res = false;
		categoryMasterData.setCreatedbyname(
				adminWorkFlowReqUtility.getCreatedByNameByCreatedId(categoryMasterData.getCreatedby().intValue()));
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(categoryMasterData.getRole_ID().intValue());
		categoryMasterData.setRoleName(roleName);
		categoryMasterData.setAction("EDIT");
		categoryMasterData
				.setAppname(adminWorkFlowReqUtility.getAppNameByAppId(categoryMasterData.getAppid().intValue()));
		categoryMasterData.setStatusname(
				adminWorkFlowReqUtility.getStatusNameByStatusId(categoryMasterData.getStatusid().intValue()));
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(categoryMasterData.getActivityName());
		ResponseMessageBean responsecode = new ResponseMessageBean();
		try {
			if (!ObjectUtils.isEmpty(categoryMasterData)) {
				responsecode.setResponseCode("200");
				if (responsecode.getResponseCode().equalsIgnoreCase("200")) {
					res = masterConfigService.updateCertificateConfigMaster(categoryMasterData);
					if (res) {
						response.setResponseCode("200");
						if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getChecker()))
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

							response.setResponseMessage("Request Submitted To Admin Checker For Approval");
						} else {
							response.setResponseMessage("Certificate Details Updated Successfully");
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

	@RequestMapping(value = "/getCertificateConfigById", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getCertificateConfigById(@RequestBody RequestParamBean requestBean) {
		ResponseMessageBean bean = new ResponseMessageBean();
		try {
			List<CertificateConfigEntity> configmasterbean = masterConfigService
					.getCertificateConfigById(Integer.parseInt(requestBean.getId1()));

			if (null != configmasterbean) {
				bean.setResponseCode("200");
				bean.setResult(configmasterbean);
			} else {
				bean.setResponseCode("202");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(bean, HttpStatus.OK);
	}

	@RequestMapping(value = "/getCertificateConfigMaster", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getCertificateConfigMaster() {
		logger.info("get calculator MasterDetails by id -> getCertificateConfigMaster()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<CertificateConfigEntity> list = masterConfigService.getCertificateConfigMaster();

			if (!ObjectUtils.isEmpty(list)) {
				res.setResponseCode("200");
				res.setResponseMessage("Configuration List");
				res.setResult(list);
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("Empty List");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}

		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/addAccountSchemeMaster", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> addAccountSchemeMaster(
			@RequestBody AccountSchemeMasterEntity categoryMasterData) {
		logger.info("addConfig : Start");
		ResponseMessageBean response = new ResponseMessageBean();
		categoryMasterData.setCreatedbyname(
				adminWorkFlowReqUtility.getCreatedByNameByCreatedId(categoryMasterData.getCreatedby().intValue()));
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(categoryMasterData.getRole_ID().intValue());
		categoryMasterData.setRoleName(roleName);
		categoryMasterData.setAction("ADD");
		categoryMasterData.setStatusname(
				adminWorkFlowReqUtility.getStatusNameByStatusId(categoryMasterData.getStatusid().intValue()));
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(categoryMasterData.getActivityName());
		ResponseMessageBean responsecode = new ResponseMessageBean();
		Boolean res = false;

		try {
			if (!ObjectUtils.isEmpty(categoryMasterData)) {
				responsecode.setResponseCode("200");
				if (responsecode.getResponseCode().equalsIgnoreCase("200")) {

					res = masterConfigService.addAccountSchemeMaster(categoryMasterData);
					if (res) {
						response.setResponseCode("200");
						if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getChecker()))
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

							response.setResponseMessage("Request Submitted To Admin Checker For Approval");
						} else {
							response.setResponseMessage("Account Schema Details Saved Successfully");
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

	@RequestMapping(value = "/updateAccountSchemeMaster", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateAccountSchemeMaster(
			@RequestBody AccountSchemeMasterEntity categoryMasterData) {
		logger.info("In Category Controller -> updateAccountSchemeMaster()");
		ResponseMessageBean response = new ResponseMessageBean();
		Boolean res = false;
		categoryMasterData.setCreatedbyname(
				adminWorkFlowReqUtility.getCreatedByNameByCreatedId(categoryMasterData.getCreatedby().intValue()));
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(categoryMasterData.getRole_ID().intValue());
		categoryMasterData.setRoleName(roleName);
		categoryMasterData.setAction("EDIT");
		categoryMasterData.setStatusname(
				adminWorkFlowReqUtility.getStatusNameByStatusId(categoryMasterData.getStatusid().intValue()));
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(categoryMasterData.getActivityName());
		ResponseMessageBean responsecode = new ResponseMessageBean();
		try {
			if (!ObjectUtils.isEmpty(categoryMasterData)) {
				responsecode.setResponseCode("200");
				if (responsecode.getResponseCode().equalsIgnoreCase("200")) {
					res = masterConfigService.updateAccountSchemeMaster(categoryMasterData);
					if (res) {
						response.setResponseCode("200");
						if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getChecker()))
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

							response.setResponseMessage("Request Submitted To Admin Checker For Approval");
						} else {
							response.setResponseMessage("Account Schema Details Updated Successfully");
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

	@RequestMapping(value = "/getAccountSchemeMasterById", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getAccountSchemeMasterById(@RequestBody RequestParamBean requestBean) {
		ResponseMessageBean bean = new ResponseMessageBean();
		List<AccountSchemeMasterEntity> configmasterbean = masterConfigService
				.getAccountSchemeMasterById(Integer.parseInt(requestBean.getId1()));
		try {
			if (null != configmasterbean) {
				bean.setResponseCode("200");
				bean.setResult(configmasterbean);
			} else {
				bean.setResponseCode("202");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}

		return new ResponseEntity<>(bean, HttpStatus.OK);
	}

	@RequestMapping(value = "/getAccountSchemeMaster", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getAccountSchemeMaster() {
		logger.info("get calculator MasterDetails by id -> getAccountSchemeMaster()");
		ResponseMessageBean res = new ResponseMessageBean();
		List<AccountSchemeMasterEntity> list = masterConfigService.getAccountSchemeMaster();
		try {
			if (!ObjectUtils.isEmpty(list)) {
				res.setResponseCode("200");
				res.setResponseMessage("Configuration List");
				res.setResult(list);
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("Empty List");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}

		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/addSchedulatTransMaster", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> addSchedulatTransMaster(
			@RequestBody SchedularTransMasterEntity schedularData) {
		logger.info("In Master Config Controller -> addSchedulatTransMaster()");
		ResponseMessageBean response = new ResponseMessageBean();

		Boolean res = false;
		try {
			if (!ObjectUtils.isEmpty(schedularData)) {
				res = masterConfigService.addSchedulatTransMaster(schedularData);
				if (res) {
					response.setResponseCode("200");
					response.setResponseMessage("Schedular Trans Details  Saved Successfully");
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

	@RequestMapping(value = "/updateSchedulatTransMaster", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateSchedulatTransMaster(
			@RequestBody SchedularTransMasterEntity schedularData) {
		logger.info("In Master Config Controller -> updateSchedulatTransMaster()");
		ResponseMessageBean response = new ResponseMessageBean();

		Boolean res = false;
		try {
			if (!ObjectUtils.isEmpty(schedularData)) {
				res = masterConfigService.updateSchedulatTransMaster(schedularData);
				if (res) {
					response.setResponseCode("200");
					response.setResponseMessage("Schedular Trans Details Updated Successfully");
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

	@RequestMapping(value = "/getSchedulatTransMaster", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getSchedulatTransMaster() {
		ResponseMessageBean bean = new ResponseMessageBean();

		try {
			List<SchedularTransMasterEntity> configmasterbean = masterConfigService.getSchedulatTransMaster();
			if (null != configmasterbean) {
				bean.setResponseCode("200");
				bean.setResult(configmasterbean);
			} else {
				bean.setResponseCode("202");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}

		return new ResponseEntity<>(bean, HttpStatus.OK);
	}

	@RequestMapping(value = "/getSchedulatTransMasterById", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getSchedulatTransMasterById(@RequestBody RequestParamBean requestBean) {
		ResponseMessageBean bean = new ResponseMessageBean();
		try {
			List<SchedularTransMasterEntity> configmasterbean = masterConfigService
					.getSchedulatTransMasterById(Integer.parseInt(requestBean.getId1()));

			if (null != configmasterbean) {
				bean.setResponseCode("200");
				bean.setResult(configmasterbean);
			} else {
				bean.setResponseCode("202");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}

		return new ResponseEntity<>(bean, HttpStatus.OK);
	}

	@RequestMapping(value = "/getScheduleTransactionDetails", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getScheduleTransactionDetails() {
		ResponseMessageBean bean = new ResponseMessageBean();

		try {
			List<SchedularTransMasterEntity> configmasterbean = masterConfigService.getScheduleTransactionDetails();
			if (null != configmasterbean) {
				bean.setResponseCode("200");
				bean.setResult(configmasterbean);
			} else {
				bean.setResponseCode("202");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}

		return new ResponseEntity<>(bean, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getLimitMasterDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getLimitMasterDetails(@RequestBody RequestParamBean requestBean) {
		logger.info("In Category Controller -> getLimitMasterDetails()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<LimitMasterEntity> limitMasterList = masterConfigService.getLimitMasterDetails(requestBean.getId1());

			if (!ObjectUtils.isEmpty(limitMasterList)) {
				res.setResponseCode("200");
				res.setResult(limitMasterList);
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
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getAppListForLimitMaster", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean>  getAppListForLimitMaster() {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<AppMasterEntity> appMaster = masterConfigService.getAppListForLimitMaster();
			if (!ObjectUtils.isEmpty(appMaster)) {
				res.setResponseCode("200");
				res.setResult(appMaster);
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

	@RequestMapping(value = "/addLimitMasterList", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> addLimitMasterList(
			@RequestBody LimitMasterEntity limitMaster) {
		logger.info("In Master Config Controller -> addLimitMasterList()");
		ResponseMessageBean response = new ResponseMessageBean();

		Boolean res = false;
		try {
			if (!ObjectUtils.isEmpty(limitMaster)) {
				res = masterConfigService.addLimitMasterList(limitMaster);
				if (res) {
					response.setResponseCode("200");
					response.setResponseMessage("Limit Master Details Saved Successfully");
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

	@RequestMapping(value = "/getLimitMasterDetailsById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getLimitMasterDetailsById(@RequestBody RequestParamBean requestBean) {
		logger.info("In Category Controller -> getLimitMasterDetailsById()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<LimitMasterEntity> limitMasterList = masterConfigService.getLimitMasterDetailsById(requestBean.getId1());

			if (!ObjectUtils.isEmpty(limitMasterList)) {
				res.setResponseCode("200");
				res.setResult(limitMasterList);
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
	
	@RequestMapping(value = "/editLimitMaster", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> editLimitMaster(
			@RequestBody LimitMasterEntity limitMaster) {
		logger.info("In Master Config Controller -> editLimitMaster()");
		ResponseMessageBean response = new ResponseMessageBean();

		Boolean res = false;
		try {
			if (!ObjectUtils.isEmpty(limitMaster)) {
				res = masterConfigService.editLimitMaster(limitMaster);
				if (res) {
					response.setResponseCode("200");
					response.setResponseMessage("Limit Master Details Saved Successfully");
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

}
