package com.itl.pns.corp.controller;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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

import com.itl.pns.bean.CorpTransLimitBean;
import com.itl.pns.bean.RequestParamBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.constant.ApplicationConstants;
import com.itl.pns.corp.dao.CorpMakerCheckerDao;
import com.itl.pns.corp.dao.CorporateDao;
import com.itl.pns.corp.entity.CorpAccUserTypeEntity;
import com.itl.pns.corp.entity.CorpApproverMasterEntity;
import com.itl.pns.corp.entity.CorpCompanyMasterEntity;
import com.itl.pns.corp.entity.CorpCompanyMenuMappingEntity;
import com.itl.pns.corp.entity.CorpHierarchyMaster;
import com.itl.pns.corp.entity.CorpLevelMasterEntity;
import com.itl.pns.corp.entity.CorpMenuEntity;
import com.itl.pns.corp.entity.CorpMenuMapping;
import com.itl.pns.corp.entity.CorpTransactionLimitEntity;
import com.itl.pns.corp.entity.CorpUserEntity;
import com.itl.pns.corp.entity.CorpUserTypeEntity;
import com.itl.pns.corp.service.CorporateService;
import com.itl.pns.dao.MasterConfigDao;
import com.itl.pns.entity.ActivitySettingMasterEntity;
import com.itl.pns.entity.ConfigMasterEntity;
import com.itl.pns.repository.ConfigRepository;
import com.itl.pns.util.AdminWorkFlowReqUtility;
import com.itl.pns.util.EmailUtil;
import com.itl.pns.util.EncryptorDecryptor;
import com.itl.pns.util.PdfGenerator;
import com.itl.pns.util.RandomNumberGenerator;
import com.itl.pns.util.Utils;

/**
 * @author shubham.lokhande
 *
 */
@RestController
@RequestMapping("corporate")
public class CorporateController {

	private static final Logger logger = LogManager.getLogger(CorporateController.class);

	@Value("${bot.image.folder}")
	private String botImageFolder;

	@Autowired
	CorporateService corporateService;

	@Autowired
	CorpMakerCheckerDao corpMakerCheckerDao;

	@Autowired
	AdminWorkFlowReqUtility adminWorkFlowReqUtility;

	@Autowired
	CorporateDao corpDao;

	@Autowired
	EmailUtil emailUtil;

	@Autowired
	MasterConfigDao masterConfigDao;

	@Autowired
	private PdfGenerator pdfGenerator;

	@Autowired
	private ConfigRepository configRepository;

	@Value("${PDF_FILE_PATH}")
	private String pdfFilePath;

	@RequestMapping(value = "/addCorpMenu", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> addCorpMenu(@RequestBody CorpMenuEntity corpMenuData) {
		logger.info("In Corporate Controller -> addCorpMenu()");
		ResponseMessageBean response = new ResponseMessageBean();
		Boolean res = false;
		corpMenuData.setStatusId(corpMenuData.getStatus());
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(corpMenuData.getRole_ID().intValue());
		corpMenuData.setRoleName(roleName);
		corpMenuData
				.setStatusName(adminWorkFlowReqUtility.getStatusNameByStatusId(corpMenuData.getStatus().intValue()));

		corpMenuData.setCreatedByName(
				adminWorkFlowReqUtility.getCreatedByNameByCreatedId(corpMenuData.getCreatedby().intValue()));
		corpMenuData.setAction("ADD");
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(corpMenuData.getActivityName());
		ResponseMessageBean responsecode = new ResponseMessageBean();
		try {
			if (!ObjectUtils.isEmpty(corpMenuData)) {
				responsecode = corpMakerCheckerDao.checkCorpMenuNameExit(corpMenuData);
				if (responsecode.getResponseCode().equalsIgnoreCase("200")) {
					res = corporateService.addCorpMenu(corpMenuData);
					if (res) {
						response.setResponseCode("200");
						if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getChecker()))
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
							response.setResponseMessage("Request Submitted To Corporate Checker For Approval");
						} else {
							response.setResponseMessage("Corporate Menu Saved Successfully");
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

	@RequestMapping(value = "/updateCorpMenu", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateCorpMenu(@RequestBody CorpMenuEntity corpMenuData) {
		logger.info("In Corporate Controller -> updateCorpMenu()");
		ResponseMessageBean response = new ResponseMessageBean();
		Boolean res = false;
		corpMenuData.setStatusId(corpMenuData.getStatus());
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(corpMenuData.getRole_ID().intValue());
		corpMenuData.setRoleName(roleName);
		corpMenuData
				.setStatusName(adminWorkFlowReqUtility.getStatusNameByStatusId(corpMenuData.getStatus().intValue()));

		corpMenuData.setCreatedByName(
				adminWorkFlowReqUtility.getCreatedByNameByCreatedId(corpMenuData.getCreatedby().intValue()));
		corpMenuData.setAction("EDIT");

		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(corpMenuData.getActivityName());
		ResponseMessageBean responsecode = new ResponseMessageBean();
		try {
			if (!ObjectUtils.isEmpty(corpMenuData)) {
				responsecode = corpMakerCheckerDao.checkUpdateCorpMenuNameExit(corpMenuData);
				if (responsecode.getResponseCode().equalsIgnoreCase("200")) {
					res = corporateService.updateCorpMenu(corpMenuData);
					response.setResponseCode("200");
					if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)
							&& (ApplicationConstants.YESVALUE)
									.equals(Character.toString(activityMasterData.get(0).getChecker()))
							&& (ApplicationConstants.YESVALUE)
									.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
						response.setResponseMessage("Request Submitted To Corporate Checker For Approval");
					} else {
						response.setResponseMessage("Corporate Menu Updated Successfully");
					}

				}
			} else {
				response.setResponseCode("202");
				response.setResponseMessage(responsecode.getResponseMessage());
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
			response.setResponseCode("500");
			response.setResponseMessage("Error While Updating Records!");
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/getCorpMenuById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getCorpMenuById(@RequestBody RequestParamBean requestBean) {
		logger.info("In Corporate Controller -> getCorpMenuById()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<CorpMenuEntity> corpMenuDataList = corporateService
					.getCorpMenuById(Integer.parseInt(requestBean.getId1()));

			if (null != corpMenuDataList) {
				res.setResponseCode("200");
				res.setResult(corpMenuDataList);
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

	@RequestMapping(value = "/getAllCorpMenus", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getAllCorpMenus(@RequestBody CorpMenuEntity corpMenuEntity,
			HttpServletRequest httpRequest) {
		logger.info("UserActivity :: View coporate all menu List : userId : " + Utils.getUpdatedBy(httpRequest));
		ResponseMessageBean res = new ResponseMessageBean();
		logger.info("userId : " + Utils.getUpdatedBy(httpRequest) + ",  View coporate all menu List Request.."
				+ corpMenuEntity.toString());
		try {
			List<CorpMenuEntity> corpMenuDataList = corporateService.getAllCorpMenus();

			List<ConfigMasterEntity> configMasterList = configRepository.findByConfigKey(ApplicationConstants.PFMS);
			List<BigDecimal> consitutionCodeList = new ArrayList<>();
			configMasterList.forEach(s -> consitutionCodeList.add(new BigDecimal(s.getConfigValue())));

			if (null != corpMenuDataList) {
				Iterator<CorpMenuEntity> it = corpMenuDataList.iterator();
				while (it.hasNext()) {
					CorpMenuEntity corpEntity = it.next();
					if ("S".equals(corpMenuEntity.getApprovalLevel())
							&& ApplicationConstants.PFMS.equals(corpEntity.getMenuName())
							|| "1".equals(corpMenuEntity.getLevelMaster())
									&& ApplicationConstants.PFMS.equals(corpEntity.getMenuName())
							|| !consitutionCodeList.contains(corpMenuEntity.getConstitutionCode())
									&& ApplicationConstants.PFMS.equals(corpEntity.getMenuName())) {
						it.remove();
						break;
					}
				}

				res.setResponseCode("200");
				res.setResult(corpMenuDataList);
				res.setResponseMessage("Success");
				logger.info("userId : " + Utils.getUpdatedBy(httpRequest) + ", view all menu Successfully"
						+ corpMenuEntity.toString());
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
				logger.info("userId : " + Utils.getUpdatedBy(httpRequest)
						+ ", View coporate all menu List: No Records Found");
			}
		} catch (Exception e) {
			logger.info("userId : " + Utils.getUpdatedBy(httpRequest)+ ", Error to view coporate all menu List: No Records Found");
		}
		return new ResponseEntity<>(res, HttpStatus.OK);

	}

	@RequestMapping(value = "/addCorpCompanyDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> addCorpCompanyDetails(
			@RequestBody CorpCompanyMasterEntity corpCompanyData) {
		logger.info("In Corporate Controller -> addCorpCompanyDetails()");
		ResponseMessageBean response = new ResponseMessageBean();
		corpCompanyData.setStatusName(
				adminWorkFlowReqUtility.getStatusNameByStatusId(corpCompanyData.getStatusId().intValue()));
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(corpCompanyData.getRole_ID().intValue());
		corpCompanyData.setRoleName(roleName);
		corpCompanyData.setStatusName(
				adminWorkFlowReqUtility.getStatusNameByStatusId(corpCompanyData.getStatusId().intValue()));
		corpCompanyData.setCreatedByName(
				adminWorkFlowReqUtility.getCreatedByNameByCreatedId(corpCompanyData.getCreatedBy().intValue()));
		corpCompanyData.setAction("ADD");
		corpCompanyData.setAppname("Corporate");
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(corpCompanyData.getActivityName());
		corpCompanyData.setLogo(corpCompanyData.getLogoImage());
		corpCompanyData.setLogoImage(corpCompanyData.getLogoImage());
		Boolean res = false;

		/*
		 * String splitArray[] = corpCompanyData.getLogoImage().split("#"); File file1 =
		 * new File(botImageFolder + splitArray[0]);
		 */
		try {

			/*
			 * byte[] fileContent = FileUtils.readFileToByteArray(file1); String
			 * encodedStringSmall = Base64.getEncoder().encodeToString(fileContent);
			 * 
			 * corpCompanyData.setLogo(encodedStringSmall);
			 * corpCompanyData.setLogoImage(encodedStringSmall);
			 */

			ResponseMessageBean responsecode = new ResponseMessageBean();

			if (!ObjectUtils.isEmpty(corpCompanyData)) {
				responsecode = corpMakerCheckerDao.checkCompanyExit(corpCompanyData);

				if (corpCompanyData.getCif() != null) {
					corpCompanyData.setCif(EncryptorDecryptor.encryptData(corpCompanyData.getCif()));
				}
				if (corpCompanyData.getCompanyCode() != null) {
					corpCompanyData.setCompanyCode(EncryptorDecryptor.encryptData(corpCompanyData.getCompanyCode()));
				}

				if (responsecode.getResponseCode().equalsIgnoreCase("200")) {
					res = corporateService.addCorpCompanyDetails(corpCompanyData);
					if (res) {
						response.setResponseCode("200");

						if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getChecker()))
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

							response.setResponseMessage("Request Submitted To Corporate Checker For Approval");
						} else {
							response.setResponseMessage("Company Details Saved Successfully");
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

	@RequestMapping(value = "/updateCorpCompanyDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateCorpCompanyDetails(
			@RequestBody CorpCompanyMasterEntity corpCompanyData) {
		logger.info("In Corporate Controller -> updateCorpCompanyDetails()");
		ResponseMessageBean response = new ResponseMessageBean();

		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(corpCompanyData.getRole_ID().intValue());
		corpCompanyData.setRoleName(roleName);
		corpCompanyData.setStatusName(
				adminWorkFlowReqUtility.getStatusNameByStatusId(corpCompanyData.getStatusId().intValue()));
		corpCompanyData.setAppname("Corporate");
		corpCompanyData.setStatusName(
				adminWorkFlowReqUtility.getStatusNameByStatusId(corpCompanyData.getStatusId().intValue()));
		corpCompanyData.setCreatedByName(
				adminWorkFlowReqUtility.getCreatedByNameByCreatedId(corpCompanyData.getCreatedBy().intValue()));
		corpCompanyData.setAction("EDIT");
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(corpCompanyData.getActivityName());
		Boolean res = false;
		corpCompanyData.setLogo(corpCompanyData.getLogoImage());
		corpCompanyData.setLogoImage(corpCompanyData.getLogoImage());

		/*
		 * String splitArray[] = corpCompanyData.getLogoImage().split("#"); File file1 =
		 * new File(botImageFolder + splitArray[0]);
		 */
		ResponseMessageBean responsecode = new ResponseMessageBean();
		try {

			/*
			 * byte[] fileContent = FileUtils.readFileToByteArray(file1); String
			 * encodedStringSmall = Base64.getEncoder().encodeToString(fileContent);
			 * 
			 * corpCompanyData.setLogo(encodedStringSmall);
			 * corpCompanyData.setLogoImage(encodedStringSmall);
			 */
			if (!ObjectUtils.isEmpty(corpCompanyData)) {
				responsecode = corpMakerCheckerDao.checkUpdateCompanyExit(corpCompanyData);
				if (responsecode.getResponseCode().equalsIgnoreCase("200")) {

					if (corpCompanyData.getCif() != null) {
						corpCompanyData.setCif(EncryptorDecryptor.encryptData(corpCompanyData.getCif()));
					}
					if (corpCompanyData.getCompanyCode() != null) {
						corpCompanyData
								.setCompanyCode(EncryptorDecryptor.encryptData(corpCompanyData.getCompanyCode()));
					}
					res = corporateService.updateCorpCompanyDetails(corpCompanyData);
					if (res) {
						response.setResponseCode("200");
						if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getChecker()))
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

							response.setResponseMessage("Request Submitted To Corporate Checker For Approval");
						} else {
							response.setResponseMessage("Company Details Updated Successfully");
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

	@RequestMapping(value = "/getCorpCompanyDetailsByID", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getCorpCompanyDetailsByID(@RequestBody RequestParamBean requestBean) {
		logger.info("In Corporate Controller -> getCorpCompanyDetailsByID()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<CorpCompanyMasterEntity> corpCompanyMasterList = corporateService
					.getCorpCompanyDetailsByID(Integer.parseInt(requestBean.getId1()));

			if (!ObjectUtils.isEmpty(corpCompanyMasterList)) {

				for (CorpCompanyMasterEntity compObg : corpCompanyMasterList) {
					if (compObg.getCif() != null && compObg.getCif().contains("=")) {
						compObg.setCif(EncryptorDecryptor.decryptData(compObg.getCif()));
					}
					if (compObg.getCompanyCode() != null && compObg.getCompanyCode().contains("=")) {
						compObg.setCompanyCode(EncryptorDecryptor.decryptData(compObg.getCompanyCode()));
					}

				}

				res.setResponseCode("200");
				res.setResult(corpCompanyMasterList);
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

	@RequestMapping(value = "/getAllCorpCompanyDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getAllCorpCompanyDetails() {
		logger.info("In Corporate Controller -> getAllCorpCompanyDetails()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<CorpCompanyMasterEntity> corpCompanyMasterList = corporateService.getAllCorpCompanyDetails();

			if (null != corpCompanyMasterList) {

				for (CorpCompanyMasterEntity compObg : corpCompanyMasterList) {
					if (compObg.getCif() != null && compObg.getCif().contains("=")) {
						compObg.setCif(EncryptorDecryptor.decryptData(compObg.getCif()));
					}
					if (compObg.getCompanyCode() != null && compObg.getCompanyCode().contains("=")) {
						compObg.setCompanyCode(EncryptorDecryptor.decryptData(compObg.getCompanyCode()));
					}

					if (compObg.getCompanyName() != null && compObg.getCompanyName().contains("=")) {
						compObg.setCompanyName(EncryptorDecryptor.decryptData(compObg.getCompanyName()));
					}

				}

				res.setResponseCode("200");
				res.setResult(corpCompanyMasterList);
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

	@RequestMapping(value = "/getCorpUserTypes", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getCorpUserTypes() {
		logger.info("In Corporate Controller -> getCorpUserTypes()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<CorpUserTypeEntity> corpUserTypeList = corporateService.getCorpUserTypes();

			if (null != corpUserTypeList) {
				res.setResponseCode("200");
				res.setResult(corpUserTypeList);
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

	@RequestMapping(value = "/getCorpUserTypesById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getCorpUserTypesById(@RequestBody RequestParamBean requestBean) {
		logger.info("In Corporate Controller -> getCorpUserTypesById()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<CorpUserTypeEntity> corpUserTypeList = corporateService
					.getCorpUserTypesById(Integer.parseInt(requestBean.getId1()));

			if (null != corpUserTypeList) {
				res.setResponseCode("200");
				res.setResult(corpUserTypeList);
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

	@RequestMapping(value = "/addCorpUserTypes", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> addCorpUserTypes(@RequestBody CorpUserTypeEntity corpUserTypeData) {
		logger.info("In Corporate Controller -> addCorpUserTypes()");
		ResponseMessageBean response = new ResponseMessageBean();
		corpUserTypeData.setSTATUSID(BigInteger.valueOf(3));
		corpUserTypeData.setAPPID(BigInteger.valueOf(2));
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(corpUserTypeData.getRole_ID().intValue());
		corpUserTypeData.setRoleName(roleName);
		corpUserTypeData.setStatusname(
				adminWorkFlowReqUtility.getStatusNameByStatusId(corpUserTypeData.getSTATUSID().intValue()));
		corpUserTypeData
				.setProductName(adminWorkFlowReqUtility.getAppNameByAppId(corpUserTypeData.getAPPID().intValue()));
		corpUserTypeData.setCreatedByName(
				adminWorkFlowReqUtility.getCreatedByNameByCreatedId(corpUserTypeData.getCREATEDBY().intValue()));
		corpUserTypeData.setAction("ADD");
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(corpUserTypeData.getActivityName());
		Boolean res = false;
		ResponseMessageBean responsecode = new ResponseMessageBean();

		try {
			if (!ObjectUtils.isEmpty(corpUserTypeData)) {
				responsecode = corpMakerCheckerDao.checkUserTypeExit(corpUserTypeData);
				if (responsecode.getResponseCode().equalsIgnoreCase("200")) {
					res = corporateService.addCorpUserTypes(corpUserTypeData);
					if (res) {
						response.setResponseCode("200");
						if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getChecker()))
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

							response.setResponseMessage("Request Submitted To Corporate Checker For Approval");
						} else {
							response.setResponseMessage("Corporate User Added Successfully");
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

	@RequestMapping(value = "/updateCorpUserTypes", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateCorpUserTypes(@RequestBody CorpUserTypeEntity corpUserTypeData) {
		logger.info("In Corporate Controller -> updateCorpUserTypes()");
		ResponseMessageBean response = new ResponseMessageBean();
		Boolean res = false;
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(corpUserTypeData.getRole_ID().intValue());
		corpUserTypeData.setRoleName(roleName);
		corpUserTypeData.setStatusname(
				adminWorkFlowReqUtility.getStatusNameByStatusId(corpUserTypeData.getSTATUSID().intValue()));
		corpUserTypeData.setProductName(adminWorkFlowReqUtility.getAppNameByAppId(2));
		corpUserTypeData.setCreatedByName(
				adminWorkFlowReqUtility.getCreatedByNameByCreatedId(corpUserTypeData.getCREATEDBY().intValue()));
		corpUserTypeData.setAction("EDIT");
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(corpUserTypeData.getActivityName());
		ResponseMessageBean responsecode = new ResponseMessageBean();
		try {
			if (!ObjectUtils.isEmpty(corpUserTypeData)) {
				responsecode = corpMakerCheckerDao.checkUpdateUserTypeExit(corpUserTypeData);
				if (responsecode.getResponseCode().equalsIgnoreCase("200")) {
					res = corporateService.updateCorpUserTypes(corpUserTypeData);
					if (res) {
						response.setResponseCode("200");
						if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getChecker()))
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

							response.setResponseMessage("Request Submitted To Corporate Checker For Approval");
						} else {
							response.setResponseMessage("Corporate User Updated Successfully");
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

	@RequestMapping(value = "/addCorpUserTypeAccount", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> addCorpUserTypeAccount(
			@RequestBody CorpAccUserTypeEntity corpUserAccData) {
		logger.info("In Corporate Controller -> addCorpUserTypeAccount()");
		ResponseMessageBean response = new ResponseMessageBean();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(corpUserAccData.getRole_ID().intValue());
		corpUserAccData.setStatusid(BigDecimal.valueOf(3));
		corpUserAccData.setRoleName(roleName);
		corpUserAccData.setStatusName(
				adminWorkFlowReqUtility.getStatusNameByStatusId(corpUserAccData.getStatusid().intValue()));
		corpUserAccData.setProductName("Corporate");
		corpUserAccData.setCreatedByName(
				adminWorkFlowReqUtility.getCreatedByNameByCreatedId(corpUserAccData.getCreatedby().intValue()));
		corpUserAccData.setAccountType(
				adminWorkFlowReqUtility.getAccountTypeById(corpUserAccData.getAccountTypeId().intValue()));
		corpUserAccData.setUserType(corpUserAccData.getCreatedByName());
		corpUserAccData.setAction("ADD");
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(corpUserAccData.getActivityName());
		Boolean res = false;
		ResponseMessageBean responsecode = new ResponseMessageBean();

		try {
			if (!ObjectUtils.isEmpty(corpUserAccData)) {

				responsecode = corpDao.checkUserAccountExist(corpUserAccData);
				if (responsecode.getResponseCode().equalsIgnoreCase("200")) {

					res = corporateService.addCorpUserTypeAccount(corpUserAccData);
					if (res) {
						response.setResponseCode("200");
						if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getChecker()))
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

							response.setResponseMessage("Request Submitted To Corporate Checker For Approval");
						} else {
							response.setResponseMessage("User Type Account Saved Successfully");
						}
					} else {
						response.setResponseCode("202");
						response.setResponseMessage("Error While Saving Records");
					}
				} else {
					response.setResponseCode(responsecode.getResponseCode());
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

	@RequestMapping(value = "/updateCorpUserTypeAccount", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateCorpUserTypeAccount(
			@RequestBody CorpAccUserTypeEntity corpUserAccData) {
		logger.info("In Corporate Controller -> updateCorpUserTypeAccount()");
		ResponseMessageBean response = new ResponseMessageBean();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(corpUserAccData.getRole_ID().intValue());
		corpUserAccData.setRoleName(roleName);
		corpUserAccData.setStatusName(
				adminWorkFlowReqUtility.getStatusNameByStatusId(corpUserAccData.getStatusid().intValue()));
		corpUserAccData.setProductName("Corporate");
		corpUserAccData.setCreatedByName(
				adminWorkFlowReqUtility.getCreatedByNameByCreatedId(corpUserAccData.getCreatedby().intValue()));
		corpUserAccData.setAction("EDIT");
		corpUserAccData.setAccountType(
				adminWorkFlowReqUtility.getAccountTypeById(corpUserAccData.getAccountTypeId().intValue()));
		corpUserAccData.setUserType(corpUserAccData.getCreatedByName());
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(corpUserAccData.getActivityName());
		Boolean res = false;
		ResponseMessageBean responsecode = new ResponseMessageBean();

		try {
			if (!ObjectUtils.isEmpty(corpUserAccData)) {
				responsecode = corpDao.checkUpdateUserAccountExist(corpUserAccData);
				if (responsecode.getResponseCode().equalsIgnoreCase("200")) {

					res = corporateService.updateCorpUserTypeAccount(corpUserAccData);
					if (res) {
						response.setResponseCode("200");
						if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getChecker()))
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
							response.setResponseMessage("Request Submitted To Corporate Checker For Approval");
						} else {
							response.setResponseMessage("User Type Account Updated Successfully");
						}
					} else {
						response.setResponseCode("202");
						response.setResponseMessage("Error While Updating Records");
					}
				} else {
					response.setResponseCode(responsecode.getResponseCode());
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

	@RequestMapping(value = "/getCorpUserTypeAccountById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getCorpUserTypeAccountById(@RequestBody RequestParamBean requestBean) {
		logger.info("In Corporate Controller -> getCorpUserTypeAccountById()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<CorpAccUserTypeEntity> corpAccUserAccList = corporateService
					.getCorpUserTypeAccountById(Integer.parseInt(requestBean.getId1()));

			if (null != corpAccUserAccList) {
				res.setResponseCode("200");
				res.setResult(corpAccUserAccList);
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

	@RequestMapping(value = "/getAllCorpUserTypeAccount", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getAllCorpUserTypeAccount() {
		logger.info("In Corporate Controller -> getAllCorpUserTypeAccount()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<CorpAccUserTypeEntity> corpAccUserAccList = corporateService.getAllCorpUserTypeAccount();

			if (!ObjectUtils.isEmpty(corpAccUserAccList)) {
				res.setResponseCode("200");
				res.setResult(corpAccUserAccList);
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

	@RequestMapping(value = "/getCorpCompanyMenuListById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getCorpCompanyMenuListById(@RequestBody RequestParamBean requestBean) {
		logger.info("In Corporate Controller -> getCorpCompanyMenuListById()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<CorpCompanyMenuMappingEntity> corpCompanyMenuMAppingList = corporateService
					.getCorpCompanyMenuListById(Integer.parseInt(requestBean.getId1()));
			if (null != corpCompanyMenuMAppingList) {
				res.setResponseCode("200");
				res.setResult(corpCompanyMenuMAppingList);
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

	@RequestMapping(value = "/addCorpCompanyMenu", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> addCorpCompanyMenu(
			@RequestBody CorpCompanyMenuMappingEntity corpCompMenuData) {
		logger.info("In Corporate Controller -> addCorpCompanyMenu()");
		ResponseMessageBean response = new ResponseMessageBean();

		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(corpCompMenuData.getRole_ID().intValue());
		corpCompMenuData.setRoleName(roleName);
		corpCompMenuData.setStatusName(
				adminWorkFlowReqUtility.getStatusNameByStatusId(corpCompMenuData.getStatusId().intValue()));
		corpCompMenuData.setCreatedByName(
				adminWorkFlowReqUtility.getCreatedByNameByCreatedId(corpCompMenuData.getCreatedby().intValue()));
		corpCompMenuData.setAction("ADD");
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(corpCompMenuData.getActivityName());
		ResponseMessageBean responsecode = new ResponseMessageBean();
		Boolean res = false;
		try {
			if (!ObjectUtils.isEmpty(corpCompMenuData)) {
				responsecode = corpMakerCheckerDao.checkCorpMenuExitForCompany(corpCompMenuData);
				if (responsecode.getResponseCode().equalsIgnoreCase("200")) {
					res = corporateService.addCorpCompanyMenu(corpCompMenuData);
					if (res) {
						response.setResponseCode("200");
						if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getChecker()))
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

							response.setResponseMessage("Request Submitted To Corporate Checker For Approval");
						} else {
							response.setResponseMessage("Company Menu Saved Successfully");
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

	@RequestMapping(value = "/updateCorpCompanyMenu", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateCorpCompanyMenu(
			@RequestBody CorpCompanyMenuMappingEntity corpCompMenuData) {
		logger.info("In Corporate Controller -> updateCorpCompanyMenu()");
		ResponseMessageBean response = new ResponseMessageBean();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(corpCompMenuData.getRole_ID().intValue());
		corpCompMenuData.setRoleName(roleName);
		corpCompMenuData.setStatusName(
				adminWorkFlowReqUtility.getStatusNameByStatusId(corpCompMenuData.getStatusId().intValue()));
		corpCompMenuData.setCreatedByName(
				adminWorkFlowReqUtility.getCreatedByNameByCreatedId(corpCompMenuData.getCreatedby().intValue()));
		corpCompMenuData.setAction("EDIT");
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(corpCompMenuData.getActivityName());
		Boolean res = false;
		ResponseMessageBean responsecode = new ResponseMessageBean();
		try {
			if (!ObjectUtils.isEmpty(corpCompMenuData)) {
				responsecode = corpMakerCheckerDao.checkUpdateCorpMenuExitForCompany(corpCompMenuData);
				if (responsecode.getResponseCode().equalsIgnoreCase("200")) {

					res = corporateService.updateCorpCompanyMenu(corpCompMenuData);
					if (res) {
						response.setResponseCode("200");
						if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getChecker()))
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

							response.setResponseMessage("Request Submitted To Corporate Checker For Approval");
						} else {
							response.setResponseMessage("Company Menu Updated Successfully");
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

	@RequestMapping(value = "/getAllCorpCompanyMenu", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getAllCorpCompanyMenu() {
		logger.info("In Corporate Controller -> getAllCorpCompanyMenu()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<CorpCompanyMenuMappingEntity> corpCompMenu = corporateService.getAllCorpCompanyMenu();

			if (null != corpCompMenu) {
				res.setResponseCode("200");
				res.setResult(corpCompMenu);
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

	@RequestMapping(value = "/getAllCorpCompanyMenuByCompId", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getAllCorpCompanyMenuByCompId(
			@RequestBody CorpCompanyMenuMappingEntity compReqData) {
		logger.info("In Corporate Controller -> getAllCorpCompanyMenuByCompId()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<CorpCompanyMenuMappingEntity> corpCompMenu = corporateService
					.getAllCorpCompanyMenuByCompId(compReqData);

			if (!ObjectUtils.isEmpty(corpCompMenu)) {
				res.setResponseCode("200");
				res.setResult(corpCompMenu);
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

	@RequestMapping(value = "/saveCorpMenuRights", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> saveCorpMenuRights(@RequestBody List<CorpMenuMapping> corpMenuMapping) {
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			/*
			 * BigInteger userTypeId =
			 * adminWorkFlowReqUtility.getIdFromUserTypeName(corpMenuMapping.get(0).
			 * getUserType()).get(0).getId();
			 */

			corporateService.saveCorpMenuRights(corpMenuMapping);
			response.setResponseCode("200");
			response.setResponseMessage("Menu Rights has been updated successfully");
		} catch (Exception e) {
			logger.info("Exception:", e);
			response.setResponseCode("500");
			response.setResponseMessage("Error occured while updating the record");
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/getCorpMenuRightsForRoleId", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getCorpMenuRightsForRoleId(@RequestBody RequestParamBean requestBean) {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<CorpMenuMapping> linkMenuRoles = corporateService.getCorpMenuRightsForRoleId(
					Integer.parseInt(requestBean.getId1()), Integer.parseInt(requestBean.getId2()));

			if (!ObjectUtils.isEmpty(linkMenuRoles)) {
				res.setResponseCode("200");
				res.setResult(linkMenuRoles);
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

	@RequestMapping(value = "/getCorpCompanyMenuListAndMapping", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getCorpCompanyMenuListAndMApping(
			@RequestBody RequestParamBean requestBean) {
		logger.info("In Corporate Controller -> getCorpCompanyMenuListAndMApping()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			/*
			 * BigInteger userTypeId =
			 * adminWorkFlowReqUtility.getIdFromUserTypeName(requestBean.getId3()).get(0).
			 * getId();
			 */

			List<CorpCompanyMenuMappingEntity> corpCompanyMenuMAppingList = corporateService
					.getCorpCompanyMenuListByCompanyId(Integer.parseInt(requestBean.getId1()));
			List<CorpMenuMapping> linkMenuRoles = corporateService.getCorpMenuRightsForRoleId(
					Integer.parseInt(requestBean.getId1()), Integer.parseInt(requestBean.getId2()));
			int i = 0;
			for (CorpCompanyMenuMappingEntity corpCompanyMap : corpCompanyMenuMAppingList) {
				corpCompanyMap.setMenuSelected("NO");
				corpCompanyMenuMAppingList.get(i).setStatusId(BigDecimal.valueOf(0));

				for (CorpMenuMapping corpMenu : linkMenuRoles) {
					corpCompanyMap.setRole_ID(corpMenu.getRoleid());
					corpCompanyMap.setCompanyId(corpMenu.getCorporatecompid());
					if (corpCompanyMap.getCorpMenuId().intValue() == corpMenu.getMenuId().intValue()) {
						corpCompanyMap.setMenuSelected("YES");
						corpCompanyMap.setStatusId(corpMenu.getStatusId());
						break;

					}
				}
				i++;
			}

			if (!ObjectUtils.isEmpty(corpCompanyMenuMAppingList)) {
				res.setResponseCode("200");
				res.setResult(corpCompanyMenuMAppingList);
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

	@RequestMapping(value = "/addCorpUser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> addCorpUser(@RequestBody CorpUserEntity corpUserData) {
		logger.info("In Corporate Controller -> addCorpUser()");
		ResponseMessageBean response = new ResponseMessageBean();
		ResponseMessageBean responsecode = new ResponseMessageBean();
		Boolean res = false;
		File file = null;
		List<Map<String, String>> record = new ArrayList<Map<String, String>>();
		Map<String, String> map = new HashMap<String, String>();
		corpUserData.setStatusid(BigDecimal.valueOf(3));
		corpUserData.setAppid(BigDecimal.valueOf(2));
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(corpUserData.getRole_ID().intValue());
		corpUserData.setRoleName(roleName);
		corpUserData
				.setStatusName(adminWorkFlowReqUtility.getStatusNameByStatusId(corpUserData.getStatusid().intValue()));
		corpUserData.setCreatedByName(
				adminWorkFlowReqUtility.getCreatedByNameByCreatedId(corpUserData.getCreatedby().intValue()));
		corpUserData.setAction("ADD");
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(corpUserData.getActivityName());
		try {
			if (!ObjectUtils.isEmpty(corpUserData)) {

				RandomNumberGenerator randomNumberGenerator = new RandomNumberGenerator();
				String randomNumber = randomNumberGenerator.generateRandomString();
				String encryptpass = EncryptorDecryptor.encryptData(randomNumber);
				corpUserData.setUser_pwd(encryptpass);

				String nonEncUserName = corpUserData.getTempUserName();
				String encrypUserName = EncryptorDecryptor.encryptData(nonEncUserName);
				corpUserData.setUser_name(encrypUserName);

				String nonEncEmail = corpUserData.getEmail_id();
				String encEmail = EncryptorDecryptor.encryptData(nonEncEmail);
				corpUserData.setEmail_id(encEmail);

				String nonEncWorkPhone = corpUserData.getWork_phone();
				String encWorkPhone = EncryptorDecryptor.encryptData(nonEncWorkPhone);
				corpUserData.setWork_phone(encWorkPhone);

				String nonEncMobile = corpUserData.getPersonal_Phone();
				String encMobile = EncryptorDecryptor.encryptData(nonEncMobile);
				corpUserData.setPersonal_Phone(encMobile);

				String nonEncPanCardNo = corpUserData.getPancardNumber();
				String encPanCardNo = EncryptorDecryptor.encryptData(nonEncPanCardNo);
				corpUserData.setPancardNumber(encPanCardNo);

				responsecode = corporateService.checkIsUserExist(corpUserData);

				if (responsecode.getResponseCode().equalsIgnoreCase("200")) {
					res = corporateService.addCorpUser(corpUserData);
					if (res) {
						response.setResponseCode("200");
						if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getChecker()))
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

							response.setResponseMessage("Request Submitted To Corporate Checker For Approval");
						} else {

							String otp = RandomNumberGenerator.generateActivationCode();
							String encPwd = (nonEncPanCardNo).concat(otp);
							logger.info("Password for pdf:" + encPwd);
							map.put("User Name", nonEncUserName);
							map.put("Password", randomNumber);

							List<String> generalRec = new ArrayList<>();
							List<ConfigMasterEntity> configData = masterConfigDao
									.getConfigByConfigKey("CORP_PORTAL_URL_LINK");
							generalRec.add(corpUserData.getCompanyName());
							generalRec.add(corpUserData.getRoleName());
							generalRec.add(configData.get(0).getDescription());
							record.add(map);
							file = pdfGenerator.generatePDF(corpUserData.getCompanyName() + "_UserCredentials.pdf",
									"PSB: User Credentials", record, encPwd, encPwd, generalRec, pdfFilePath);

							List<String> toEmail = new ArrayList<>();
							List<String> ccEmail = new ArrayList<>();
							List<String> bccEmail = new ArrayList<>();
							List<File> files = new ArrayList<>();
							toEmail.add(nonEncEmail);
							files.add(file);
							Date subjectDate = new Date();
							String subject = "PSB: Notification " + subjectDate;
//							if (emailUtil.sendEmailWithAttachment(toEmail, ccEmail, bccEmail, files,
//									"Note: Please enter your pancard number and otp(sent on your mobile number) to view login credentials.",
//									subject)) {
//								file.delete();
//							}
//							emailUtil.sendSMSNotification(nonEncMobile, "Please enter otp" + " " + otp + " "
//									+ "along with your pancard number as password to view PDF sent on your email");

							response.setResponseMessage("Corporate User Saved Successfully");
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
			logger.error("Exception:", e);
			response.setResponseCode("500");
			response.setResponseMessage("Error While  Adding Records!");
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateCorpUser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateCorpUser(@RequestBody CorpUserEntity corpUserData) {
		logger.info("In Corporate Controller -> updateCorpUser()");
		ResponseMessageBean response = new ResponseMessageBean();
		Boolean res = false;
		ResponseMessageBean responsecode = new ResponseMessageBean();
		corpUserData.setAppid(BigDecimal.valueOf(2));
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(corpUserData.getRole_ID().intValue());
		corpUserData.setRoleName(roleName);
		corpUserData
				.setStatusName(adminWorkFlowReqUtility.getStatusNameByStatusId(corpUserData.getStatusid().intValue()));
		corpUserData.setCreatedByName(
				adminWorkFlowReqUtility.getCreatedByNameByCreatedId(corpUserData.getCreatedby().intValue()));
		corpUserData.setAction("EDIT");
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(corpUserData.getActivityName());
		try {
			if (!ObjectUtils.isEmpty(corpUserData)) {

				corpUserData.setUser_name(EncryptorDecryptor.encryptData(corpUserData.getTempUserName()));
				corpUserData.setEmail_id(EncryptorDecryptor.encryptData(corpUserData.getEmail_id()));

				responsecode = corpDao.checkUpdateUserExist(corpUserData);

				if (responsecode.getResponseCode().equalsIgnoreCase("200")) {

					res = corporateService.updateCorpUser(corpUserData);
					response.setResponseCode("200");
					if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)
							&& (ApplicationConstants.YESVALUE)
									.equals(Character.toString(activityMasterData.get(0).getChecker()))
							&& (ApplicationConstants.YESVALUE)
									.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
						response.setResponseMessage("Request Submitted To Corporate Checker For Approval");
					} else {
						response.setResponseMessage("Corporate User  Updated Successfully");
					}
				} else {
					response.setResponseCode("202");
					response.setResponseMessage(responsecode.getResponseMessage());
				}

			}
		} catch (Exception e) {
			logger.error("Exception:", e);
			response.setResponseCode("500");
			response.setResponseMessage("Error While Updating Records!");
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/getCorpUserById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getCorpUserById(@RequestBody RequestParamBean requestBean) {
		logger.info("In Corporate Controller -> getCorpUserById()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<CorpUserEntity> corpUserData = corporateService
					.getCorpUserById(Integer.parseInt(requestBean.getId1()));

			if (null != corpUserData) {

				for (CorpUserEntity corpUser : corpUserData) {

					if (corpUser.getUser_disp_name() != null && corpUser.getUser_disp_name().contains("=")) {
						corpUser.setUser_disp_name(EncryptorDecryptor.decryptData(corpUser.getUser_disp_name()));
					}

					if (corpUser.getPersonal_Phone() != null && corpUser.getPersonal_Phone().contains("=")) {
						corpUser.setPersonal_Phone(EncryptorDecryptor.decryptData(corpUser.getPersonal_Phone()));
					}
					res.setResponseCode("200");
					res.setResult(corpUserData);
					res.setResponseMessage("Success");
				}
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);

	}

	@RequestMapping(value = "/getAllCorpUsers", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getAllCorpUsers() {
		logger.info("In Corporate Controller -> getAllCorpUsers()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<CorpUserEntity> corpUserData = corporateService.getAllCorpUsers();

			if (null != corpUserData) {

				for (CorpUserEntity corpUser : corpUserData) {

					if (corpUser.getUser_disp_name() != null && corpUser.getUser_disp_name().contains("=")) {
						corpUser.setUser_disp_name(EncryptorDecryptor.decryptData(corpUser.getUser_disp_name()));
					}

				}
				res.setResponseCode("200");
				res.setResult(corpUserData);
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

	@RequestMapping(value = "/getCorpUsersByCompId", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getCorpUsersByCompId(@RequestBody RequestParamBean requestBean) {
		logger.info("In Corporate Controller -> getCorpUsersByCompId()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<CorpUserEntity> corpUserData = corporateService
					.getCorpUsersByCompId(Integer.parseInt(requestBean.getId1()));
			if (null != corpUserData) {
				res.setResponseCode("200");
				res.setResult(corpUserData);
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

	@RequestMapping(value = "/getCorpLevels", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getCorpLevels(@RequestBody RequestParamBean requestBean) {
		logger.info("In Corporate Controller -> getCorpLevels()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<CorpCompanyMasterEntity> corpCompanyList = corporateService
					.getCorpCompanyDetailsByID(Integer.parseInt(requestBean.getId1()));
			if (!ObjectUtils.isEmpty(corpCompanyList)) {
				List<CorpLevelMasterEntity> corpUserData = corporateService.getCorpLevels();
				if (null != corpUserData) {
					res.setResponseCode("200");
					res.setResult(corpUserData);
					res.setResponseMessage("Success");
				} else {
					res.setResponseCode("202");
					res.setResponseMessage("No Records Found");
				}

			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/getAllCorpLevels", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getAllCorpLevels() {
		logger.info("In Corporate Controller -> getAllCorpLevels()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<CorpLevelMasterEntity> corpUserData = corporateService.getCorpLevels();
			if (null != corpUserData) {
				res.setResponseCode("200");
				res.setResult(corpUserData);
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

	@RequestMapping(value = "/getCorpHierarchyList", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getCorpHierarchyList(@RequestBody RequestParamBean requestBean) {
		logger.info("In Corporate Controller -> getCorpHierarchyList()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<CorpCompanyMasterEntity> corpCompanyList = corporateService
					.getCorpCompanyDetailsByID(Integer.parseInt(requestBean.getId1()));
			if (!ObjectUtils.isEmpty(corpCompanyList)) {
				List<CorpHierarchyMaster> corpUserData = corporateService.getCorpHierarchyList();
				if (null != corpUserData) {
					res.setResponseCode("200");
					res.setResult(corpUserData);
					res.setResponseMessage("Success");
				} else {
					res.setResponseCode("202");
					res.setResponseMessage("No Records Found");
				}

			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/getCorpApproverTypeList", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getCorpApproverTypeList(@RequestBody RequestParamBean requestBean) {
		logger.info("In Corporate Controller -> getCorpApproverTypeList()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<CorpCompanyMasterEntity> corpCompanyList = corporateService
					.getCorpCompanyDetailsByID(Integer.parseInt(requestBean.getId1()));
			if (!ObjectUtils.isEmpty(corpCompanyList)) {
				List<CorpApproverMasterEntity> corpApproverList = corporateService.getCorpApproverTypeList();
				if (null != corpApproverList) {
					res.setResponseCode("200");
					res.setResult(corpApproverList);
					res.setResponseMessage("Success");
				} else {
					res.setResponseCode("202");
					res.setResponseMessage("No Records Found");
				}

			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/setCorpTransactionsLimit", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> setCorpTransactionsLimit(@RequestBody CorpTransLimitBean corpTransData) {
		logger.info("In Corporate Controller -> setCorpTransactionsLimit()");
		ResponseMessageBean response = new ResponseMessageBean();
		corpTransData.setStatusId(BigDecimal.valueOf(3));
		corpTransData.setAppId(BigDecimal.valueOf(2));
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(corpTransData.getRole_ID().intValue());
		corpTransData.setRoleName(roleName);
		corpTransData
				.setStatusName(adminWorkFlowReqUtility.getStatusNameByStatusId(corpTransData.getStatusId().intValue()));
		corpTransData.setProductName(adminWorkFlowReqUtility.getAppNameByAppId(corpTransData.getAppId().intValue()));
		corpTransData
				.setCreatedByName(adminWorkFlowReqUtility.getCreatedByNameByCreatedId(corpTransData.getCreatedBy()));
		corpTransData.setAction("ADD");
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(corpTransData.getActivityName());
		Boolean res = false;
		try {
			if (ObjectUtils.isEmpty(corpTransData.getCorpLimitData().get(0).getMaxAmount())) {
				corpTransData.getCorpLimitData().get(0).setMaxAmount(new BigDecimal(0));
				corpTransData.getCorpLimitData().get(0).setMinAmount(new BigDecimal(0));
			}

			if (!ObjectUtils.isEmpty(corpTransData)) {
				List<CorpTransactionLimitEntity> isLimiExist = corpDao.getCorpTransLimitData(
						corpTransData.getCorpCompId().intValue(),
						corpTransData.getCorpLimitData().get(0).getMinAmount().intValue(),
						corpTransData.getCorpLimitData().get(0).getMaxAmount().intValue(), corpTransData.getCurrency(),
						corpTransData.getAccountNumber());
				if (ObjectUtils.isEmpty(isLimiExist)) {
					res = corporateService.setCorpTransactionsLimit(corpTransData);
					if (res) {
						response.setResponseCode("200");
						if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getChecker()))
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
							response.setResponseMessage("Request Submitted To Corporate Checker For Approval");
						} else {
							response.setResponseMessage("Transaction Limit Submitted Successfully");
						}
					} else {
						response.setResponseCode("202");
						response.setResponseMessage("Error While Saving Records");
					}
				} else {
					response.setResponseCode("202");
					response.setResponseMessage("Limit Already Exists");
				}

			}
		} catch (Exception e) {
			logger.error("Exception:", e);
			response.setResponseCode("500");
			response.setResponseMessage("Error While  Adding Records!");
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateCorpTransactionsLimit", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateCorpTransactionsLimit(
			@RequestBody CorpTransLimitBean corpTransData) {
		logger.info("In Corporate Controller -> updateCorpTransactionsLimit()");
		ResponseMessageBean response = new ResponseMessageBean();
		corpTransData.setStatusId(BigDecimal.valueOf(3));
		corpTransData.setAppId(BigDecimal.valueOf(2));
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(corpTransData.getRole_ID().intValue());
		corpTransData.setRoleName(roleName);
		corpTransData
				.setStatusName(adminWorkFlowReqUtility.getStatusNameByStatusId(corpTransData.getStatusId().intValue()));
		corpTransData.setProductName(adminWorkFlowReqUtility.getAppNameByAppId(corpTransData.getAppId().intValue()));
		corpTransData
				.setCreatedByName(adminWorkFlowReqUtility.getCreatedByNameByCreatedId(corpTransData.getCreatedBy()));
		corpTransData.setAction("EDIT");
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(corpTransData.getActivityName());
		Boolean res = false;
		try {

			if (!ObjectUtils.isEmpty(corpTransData)) {
				List<CorpTransactionLimitEntity> isLimiExist = corpDao.checkCorpTransLimitDataPresent(
						corpTransData.getCorpCompId().intValue(),
						corpTransData.getCorpLimitData().get(0).getMinAmount().intValue(),
						corpTransData.getCorpLimitData().get(0).getMaxAmount().intValue(), corpTransData.getCurrency(),
						corpTransData.getCorpLimitData().get(0).getTransLimitId().intValue(),
						corpTransData.getAccountNumber());
				if (ObjectUtils.isEmpty(isLimiExist)) {
					if (corpTransData.getCorpLimitData().get(0).getTransLimitId().intValue() != 0) {
						res = corporateService.updateCorpTransactionsLimit(corpTransData);
					}
					res = corpDao.updateCorpTransactionsLimit(corpTransData);
					if (res) {
						response.setResponseCode("200");
						if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getChecker()))
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

							response.setResponseMessage("Request Submitted To Corporate Checker For Approval");
						} else {
							response.setResponseMessage("Transaction Limit Updated Successfully");
						}
					} else {
						response.setResponseCode("202");
						response.setResponseMessage("Error While Saving Records");
					}

				} else {
					response.setResponseCode("202");
					response.setResponseMessage("Limit Already Exist For Same Company");
				}
			}
		} catch (Exception e) {
			logger.error("Exception:", e);
			response.setResponseCode("500");
			response.setResponseMessage("Error While  Adding Records!");
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/getAllTransationByAccNoAndCompId", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getAllTransationByAccNoAndCompId(
			@RequestBody RequestParamBean requestBean) {
		logger.info("In Corporate Controller -> accNo()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<CorpCompanyMasterEntity> corpCompanyList = corporateService
					.getCorpCompanyDetailsByID(Integer.parseInt(requestBean.getId2()));
			if (!ObjectUtils.isEmpty(corpCompanyList)) {
				CorpTransLimitBean corpTransData = corporateService
						.getAllTransationByAccNoAndCompId(requestBean.getId1(), Integer.parseInt(requestBean.getId2()));
				if (!ObjectUtils.isEmpty(corpTransData.getCorpLimitListData())) {
					res.setResponseCode("200");
					res.setResult(corpTransData);
					res.setResponseMessage("Success");
				} else {
					res.setResponseCode("202");
					res.setResponseMessage("No Records Found");
				}

			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/getTranByAccNoAndCompIdAndTransId", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getTranByAccNoAndCompIdAndTransId(
			@RequestBody RequestParamBean requestBean) {
		logger.info("In Corporate Controller -> accNo()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<CorpCompanyMasterEntity> corpCompanyList = corporateService
					.getCorpCompanyDetailsByID(Integer.parseInt(requestBean.getId2()));
			if (!ObjectUtils.isEmpty(corpCompanyList)) {
				CorpTransLimitBean corpTransData = null;
				if (Integer.parseInt(requestBean.getId4()) != 0) {

					corpTransData = corpDao.getTrasLimitDataByAdminWorkFlowId(Integer.parseInt(requestBean.getId4()),
							requestBean.getId1());

				} else if (Integer.parseInt(requestBean.getId4()) == 0) {
					corpTransData = corporateService.getTranByAccNoAndCompIdAndTransId(requestBean.getId1(),
							Integer.parseInt(requestBean.getId2()), Integer.parseInt(requestBean.getId3()));
				}

				if (!ObjectUtils.isEmpty(corpTransData.getCorpLimitListData())) {
					res.setResponseCode("200");
					res.setResult(corpTransData);
					res.setResponseMessage("Success");
				} else {
					res.setResponseCode("202");
					res.setResponseMessage("No Records Found");
				}

			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/getDynamicRolesByCompId", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getDynamicRolesByCompId(@RequestBody RequestParamBean requestBean) {
		logger.info("In Corporate Controller -> getDynamicRolesByCompId()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<CorpUserTypeEntity> corpCompanyList = corporateService
					.getDynamicRolesByCompId(Integer.parseInt(requestBean.getId1()));
			if (!ObjectUtils.isEmpty(corpCompanyList)) {
				res.setResponseCode("200");
				res.setResult(corpCompanyList);
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

	@RequestMapping(value = "/getFirstCorpUserById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getFirstCorpUserById(@RequestBody RequestParamBean requestBean) {
		logger.info("In Corporate Controller -> getFirstCorpUserById()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<CorpUserEntity> corpUserData = corporateService
					.getFirstCorpUserById(new BigInteger(requestBean.getId1()));

			if (null != corpUserData) {
				res.setResponseCode("200");
				res.setResult(corpUserData);
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

	@RequestMapping(value = "/getFirstAllCorpUsers", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getFirstAllCorpUsers() {
		logger.info("In Corporate Controller -> getFirstAllCorpUsers()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<CorpUserEntity> corpUserData = corporateService.getFirstAllCorpUsers();

			if (null != corpUserData) {
				res.setResponseCode("200");
				res.setResult(corpUserData);
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

	@RequestMapping(value = "/updateFirstCorpUsers", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateFirstCorpUsers(@RequestBody CorpUserEntity corpUserData) {
		logger.info("In Corporate Controller -> updateFirstCorpUsers()");
		ResponseMessageBean res = new ResponseMessageBean();
		boolean isUpdate = false;
		try {
			isUpdate = corporateService.updateFirstCorpUsers(corpUserData);

			if (null != corpUserData) {
				res.setResponseCode("200");
				res.setResult(corpUserData);
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

	@RequestMapping(value = "/getAccountsByCif", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getAccountsByCif(@RequestBody RequestParamBean requestBean) {
		logger.info("In Corporate Controller -> getAccountsByCif()");
		ResponseMessageBean res = new ResponseMessageBean();

//		List<String> accounts=new ArrayList<>();
		try {
			ResponseMessageBean corpCompanyList = corporateService.getAccountsByCif(requestBean.getId1(),
					requestBean.getId2(), requestBean.getId3());

//			JSONObject result = new JSONObject(corpCompanyList.getResult().toString());
//			JSONObject set=(JSONObject) result.get("set");
//			JSONArray records=(JSONArray) set.get("records");
//			Iterator itr2 = records.iterator();
//	        
//	        while (itr2.hasNext()) 
//	        {
//	        	JSONObject record=(JSONObject)itr2.next();          
//	                if(record.get("Status").equals("Active")) {
//	                	accounts.add(record.get("accountNo").toString());
//	                }
//	        }
			if (!ObjectUtils.isEmpty(corpCompanyList)) {
				res.setResponseCode("200");
				res.setResult(corpCompanyList);
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

	@RequestMapping(value = "/checkEmailExist", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> checkEmailExist(@RequestBody RequestParamBean requestBean) {
		logger.info("In Corporate Controller -> checkEmailExist()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			res = corporateService.checkEmailExist(EncryptorDecryptor.encryptData(requestBean.getId1()));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);

	}
}
