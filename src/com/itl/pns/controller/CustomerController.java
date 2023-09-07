package com.itl.pns.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.serial.SerialException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.itl.pns.bean.CountryRestrictionBean;
import com.itl.pns.bean.DateBean;
import com.itl.pns.bean.DeviceMasterDetailsBean;
import com.itl.pns.bean.EmailRequestBean;
import com.itl.pns.bean.RegistrationDetailsBean;
import com.itl.pns.bean.RequestParamBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.bean.TicketBean;
import com.itl.pns.bean.TimestampDateBean;
import com.itl.pns.bean.WalletPointsBean;
import com.itl.pns.constant.ApplicationConstants;
import com.itl.pns.corp.entity.CorpCompanyMasterNewEntity;
import com.itl.pns.corp.entity.CorpUserNewEntity;
import com.itl.pns.dao.CustomerDao;
import com.itl.pns.entity.ActivitySettingMasterEntity;
import com.itl.pns.entity.ChannelMasterEntity;
import com.itl.pns.entity.CustomerEntity;
import com.itl.pns.entity.CustomerOtherInfoEntity;
import com.itl.pns.entity.OfferDetailsEntity;
import com.itl.pns.entity.RMMASTER;
import com.itl.pns.entity.SecurityQuestionMaster;
import com.itl.pns.entity.UserDeviceDetails;
import com.itl.pns.entity.WalletPoints;
import com.itl.pns.repository.CorpCompanyMasterRepository;
import com.itl.pns.repository.CorpUsersRepository;
import com.itl.pns.service.CustomerService;
import com.itl.pns.service.IOfferDetailsService;
import com.itl.pns.service.impl.RestServiceCall;
import com.itl.pns.util.AdminWorkFlowReqUtility;
import com.itl.pns.util.EmailUtil;
import com.itl.pns.util.EncryptorDecryptor;

@RestController
@RequestMapping("customer")
public class CustomerController {

	private static final Logger logger = Logger.getLogger(CustomerController.class);

	@Autowired
	private final CorpCompanyMasterRepository corpCompanyMasterRepository;

	@Autowired
	private final CorpUsersRepository corpUsersRepository;

	@Autowired
	public CustomerController(CorpCompanyMasterRepository corpCompanyMasterRepository,
			CorpUsersRepository corpUsersRepository) {
		super();
		this.corpCompanyMasterRepository = corpCompanyMasterRepository;
		this.corpUsersRepository = corpUsersRepository;
	}

	@Autowired
	AdminWorkFlowReqUtility adminWorkFlowReqUtility;

	@Autowired
	EmailUtil util;

	@Autowired
	CustomerService customerService;

	@Autowired
	private IOfferDetailsService offerDetails;

	@Autowired
	CustomerDao customerDao;

	@Autowired
	RestServiceCall rest;

	@Value("${bot.image.folder}")
	private String botImageFolder;

	@RequestMapping(value = "/countryRestriction", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getcountryRestriction(@RequestBody CountryRestrictionBean bean) {
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			if (!ObjectUtils.isEmpty(bean)) {
				List<CountryRestrictionBean> list = customerService.getcountryRestriction(bean);

				if (!ObjectUtils.isEmpty(list)) {
					response.setResponseCode("200");
					for (CountryRestrictionBean record : list) {
						if (record.getMOBILENO().contains("=")) {
							record.setMOBILENO(EncryptorDecryptor.decryptData(record.getMOBILENO()));
						}
						if (record.getEMAIL().contains("=")) {
							record.setEMAIL(EncryptorDecryptor.decryptData(record.getEMAIL()));
						}
					}
					response.setResponseMessage("Restricted Country List");
					response.setResult(list);
				} else {
					response.setResponseCode("202");
					response.setResponseMessage("No Records Found");
				}
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@RequestMapping(value = "/updateCountryRestrictionStatus", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> updateCountryRestrictionStatus(
			@RequestBody CountryRestrictionBean countryRestrictionBean) {
		logger.info("update country ");
		ResponseMessageBean response = new ResponseMessageBean();
		Boolean res = false;
		try {
			if (!ObjectUtils.isEmpty(countryRestrictionBean)) {
				res = customerService.updateCountryRestrictionStatus(countryRestrictionBean);
			}

			if (res) {
				response.setResponseCode("200");
				response.setResponseMessage("Status Updated Successfully");
			} else {
				response.setResponseCode("202");
				response.setResponseMessage("Error While Updating Status");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/getCustomerDetails", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getCustomerDetails(@RequestBody DateBean datebean) {
		logger.info("Inside getCustomerDetails");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<RegistrationDetailsBean> registrationList = new ArrayList<RegistrationDetailsBean>();
			registrationList = customerService.getCustomerDetails(datebean);

			if (!ObjectUtils.isEmpty(registrationList)) {
				res.setResponseCode("200");
				for (RegistrationDetailsBean record : registrationList) {
					if (record.getDOB() != null && record.getDOB().contains("=")) {
						record.setDOB(EncryptorDecryptor.decryptData(record.getDOB()));
					}
					if (record.getMOBILE() != null && record.getMOBILE().contains("=")) {
						record.setMOBILE(EncryptorDecryptor.decryptData(record.getMOBILE()));
					}
					if (record.getEMAIL() != null && record.getEMAIL().contains("=")) {
						record.setEMAIL(EncryptorDecryptor.decryptData(record.getEMAIL()));
					}
					if (record.getUSERNAME() != null && record.getUSERNAME().contains("=")) {
						record.setUSERNAME(EncryptorDecryptor.decryptData(record.getUSERNAME()));
					}
					if (record.getCIF() != null && record.getCIF().contains("=")) {
						record.setCIF(EncryptorDecryptor.decryptData(record.getCIF()));
					}
				}
				res.setResponseMessage("Success");
				res.setResult(registrationList);
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/getCustomerDetailsById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getCustomerDetailsById(@RequestBody RequestParamBean requestBean) {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<RegistrationDetailsBean> registration = customerService
					.getCustomerDetailsById(Integer.parseInt(requestBean.getId1()));

			if (null != registration) {
				for (RegistrationDetailsBean record : registration) {
					if (record.getDOB() != null && record.getDOB().contains("=")) {
						record.setDOB(EncryptorDecryptor.decryptData(record.getDOB()));
					}
					if (record.getMOBILE() != null && record.getMOBILE().contains("=")) {
						record.setMOBILE(EncryptorDecryptor.decryptData(record.getMOBILE()));
					}
					if (record.getEMAIL() != null && record.getEMAIL().contains("=")) {
						record.setEMAIL(EncryptorDecryptor.decryptData(record.getEMAIL()));
					}
					if (record.getUSERNAME() != null && record.getUSERNAME().contains("=")) {
						record.setUSERNAME(EncryptorDecryptor.decryptData(record.getUSERNAME()));
					}
					if (record.getCIF() != null && record.getCIF().contains("=")) {
						record.setCIF(EncryptorDecryptor.decryptData(record.getCIF()));
					}
				}
				res.setResponseCode("200");
				res.setResult(registration);
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

	@RequestMapping(value = "/addOfferDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> addOfferDetails(@RequestBody OfferDetailsEntity offerDetail)
			throws SerialException, SQLException {
		logger.info("add offer details ");
		boolean isAdded = false;
		int userStatus = offerDetail.getStatusId().intValue();
		ResponseMessageBean responsecode = new ResponseMessageBean();
		offerDetail
				.setStatusName(adminWorkFlowReqUtility.getStatusNameByStatusId(offerDetail.getStatusId().intValue()));
		offerDetail.setProductName(adminWorkFlowReqUtility.getAppNameByAppId(offerDetail.getAppId().intValue()));
		offerDetail.setCreatedByName(
				adminWorkFlowReqUtility.getCreatedByNameByCreatedId(offerDetail.getCreatedby().intValue()));

		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(offerDetail.getRole_ID().intValue());
		offerDetail.setRoleName(roleName);
		offerDetail.setAction("ADD");
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(offerDetail.getActivityName());
		ResponseMessageBean res = new ResponseMessageBean();

		try {

			responsecode = offerDetails.isSeqnoExit(offerDetail);

			if (responsecode.getResponseCode().equalsIgnoreCase("200")) {
				isAdded = offerDetails.insertOfferDetails(offerDetail);

				if (isAdded) {

					String image1 = (offerDetail.getBase64ImageLarge());
					// String image1 = offerDetail.getBase64ImageLarge();
					offerDetail.setBaseImageLarge(image1);
					offerDetail.setBase64ImageLarge(null);

					String image2 = (offerDetail.getBase64ImageSmall());
					// String image2 =offerDetail.getBase64ImageSmall();
					offerDetail.setBaseImageSmall(image2);
					offerDetail.setBase64ImageSmall(null);
					offerDetails.addOfferToAdminWorkFlow(offerDetail, userStatus);

					res.setResponseCode("200");
					if ((roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
							|| roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER))
							&& (ApplicationConstants.YESVALUE)
									.equals(Character.toString(activityMasterData.get(0).getChecker()))
							&& (ApplicationConstants.YESVALUE)
									.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

						if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)) {
							res.setResponseMessage("Request Submitted To Admin Checker For Approval");
						} else {
							res.setResponseMessage("Request Submitted To Corporate Checker For Approval");
						}

					} else {
						res.setResponseMessage("Details Saved Successfully");
					}

				} else {
					res.setResponseCode("500");
					res.setResponseMessage(" Error While Adding Offers Details");
				}
			} else {
				res.setResponseCode("500");
				res.setResponseMessage(responsecode.getResponseMessage());
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateOfferDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateuploadOffer(@RequestBody OfferDetailsEntity offerDetail) {
		logger.info("update offer details ");
		int userStatus = offerDetail.getStatusId().intValue();
		offerDetail
				.setStatusName(adminWorkFlowReqUtility.getStatusNameByStatusId(offerDetail.getStatusId().intValue()));
		offerDetail.setProductName(adminWorkFlowReqUtility.getAppNameByAppId(offerDetail.getAppId().intValue()));

		ResponseMessageBean responsecode = new ResponseMessageBean();
		ResponseMessageBean res = new ResponseMessageBean();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(offerDetail.getRole_ID().intValue());
		offerDetail.setRoleName(roleName);
		offerDetail.setAction("EDIT");
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(offerDetail.getActivityName());

		try {

			responsecode = offerDetails.updateCheckSeqno(offerDetail);

			if (responsecode.getResponseCode().equalsIgnoreCase("200")) {
				boolean isupdated = offerDetails.updateuploadOffer(offerDetail);

				if (isupdated) {

					String image1 = (offerDetail.getBase64ImageLarge());
					// String image1 = offerDetail.getBase64ImageLarge();
					offerDetail.setBaseImageLarge(image1);
					offerDetail.setBase64ImageLarge(null);

					String image2 = (offerDetail.getBase64ImageSmall());
					// String image2 =offerDetail.getBase64ImageSmall();
					offerDetail.setBaseImageSmall(image2);
					offerDetail.setBase64ImageSmall(null);
					offerDetails.updateuploadOfferToAdminWorkFlow(offerDetail, userStatus);

					res.setResponseCode("200");
					if ((roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
							|| roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER))
							&& (ApplicationConstants.YESVALUE)
									.equals(Character.toString(activityMasterData.get(0).getChecker()))
							&& (ApplicationConstants.YESVALUE)
									.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

						if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)) {
							res.setResponseMessage("Request Submitted To Admin Checker For Approval");
						} else {
							res.setResponseMessage("Request Submitted To Corporate Checker For Approval");
						}

					} else {
						res.setResponseMessage("Offer Details Has Been Updated");
					}

				} else {
					res.setResponseCode("500");
					res.setResponseMessage(" Error While Updaing Offers  Details");
				}
			} else {
				res.setResponseCode("500");
				res.setResponseMessage(responsecode.getResponseMessage());
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateCustomerDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateRegistrationDetails(
			@RequestBody CustomerEntity registrationDetails) {
		logger.info("update customer details ");

		ResponseMessageBean res = new ResponseMessageBean();
		List<RegistrationDetailsBean> list = adminWorkFlowReqUtility
				.getCustDataById(registrationDetails.getId().toBigInteger());
		registrationDetails.setCif(list.get(0).getCIF());
		registrationDetails.setCustomername(list.get(0).getCUSTOMERNAME());
		registrationDetails.setUsername(list.get(0).getUSERNAME());

		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(registrationDetails.getRole_ID().intValue());
		registrationDetails.setStatusName(
				adminWorkFlowReqUtility.getStatusNameByStatusId(registrationDetails.getStatusid().intValue()));
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(registrationDetails.getActivityName());
		registrationDetails.setRoleName(roleName);
		registrationDetails.setAction("EDIT");

		Boolean response = customerService.updateRegistrationDetails(registrationDetails);
		try {
			if (response) {
				res.setResponseCode("200");
				if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
						&& (ApplicationConstants.YESVALUE)
								.equals(Character.toString(activityMasterData.get(0).getChecker()))
						&& (ApplicationConstants.YESVALUE)
								.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
					res.setResponseMessage("Request Submitted To Admin Checker For Approval");
				} else {
					res.setResponseMessage("Customer Details Has Been Updated Successfully");
				}

			} else {
				res.setResponseCode("202");
				res.setResponseMessage("Error Occured While Updating The Record");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/getOfferDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getOfferDetails(@RequestBody RequestParamBean requestBean) {
		logger.info("get offfer details ");
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			List<OfferDetailsEntity> list = customerService.getOfferDetails(Integer.parseInt(requestBean.getId1()));

			if (!list.isEmpty()) {
				response.setResponseCode("200");
				response.setResult(list);
				response.setResponseMessage("Success");
			} else {
				response.setResponseCode("202");
				response.setResponseMessage("No Record Found");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/getOfferDetailsByid", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getOfferDetailsByid(@RequestBody RequestParamBean requestBean) {
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			List<OfferDetailsEntity> list = customerService.getOfferDetailsByid(Integer.parseInt(requestBean.getId1()));

			if (!list.isEmpty()) {
				response.setResponseCode("200");
				response.setResult(list);
				response.setResponseMessage("Success");
			} else {
				response.setResponseCode("202");
				response.setResponseMessage("No Record Found");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/getDeviceMasterDetails", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getDeviceMasterDetails() {
		logger.info("get device master details ");
		ResponseMessageBean response = new ResponseMessageBean();

		List<DeviceMasterDetailsBean> deviceDetailsList = offerDetails.getDeviceMasterDetails();
		if (!ObjectUtils.isEmpty(deviceDetailsList)) {
			response.setResponseCode("200");
			for (DeviceMasterDetailsBean record : deviceDetailsList) {
				if (record.getMobileNumber() != null) {
					if (record.getMobileNumber().contains("=")) {
						record.setMobileNumber(EncryptorDecryptor.decryptData(record.getMobileNumber()));
					}
					if (record.getCustomerName().contains("=")) {
						record.setCustomerName(EncryptorDecryptor.decryptData(record.getCustomerName()));
					}

				}
			}
			response.setResult(deviceDetailsList);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.setResponseCode("202");
			return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
		}

	}

	@RequestMapping(value = "/updateDeviceDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateDeviceDetails(@RequestBody UserDeviceDetails deviceDetails) {
		logger.info("update device details ");
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(deviceDetails.getRole_ID().intValue());
		deviceDetails.setStatusName(adminWorkFlowReqUtility.getStatusNameByStatusId(deviceDetails.getStatusId()));
		deviceDetails.setRoleName(roleName);
		deviceDetails.setCreatedByName(
				adminWorkFlowReqUtility.getCreatedByNameByCreatedId(deviceDetails.getUser_ID().intValue()));

		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(deviceDetails.getActivityName());

		ResponseMessageBean res = new ResponseMessageBean();
		offerDetails.updateDeviceDetails(deviceDetails);
		try {
			res.setResponseCode("200");
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

				res.setResponseMessage("Request Submitted To Admin Checker For Approval");
			} else {
				res.setResponseMessage("Details Has Been Updated successfully");
			}

		} catch (Exception e) {
			logger.info("Exception:", e);
			res.setResponseCode("500");
			res.setResponseMessage("Error occured while updating the record");
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/getDeviceMasterDetailsById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getDeviceMasterDetailsById(@RequestBody RequestParamBean requestBean) {
		logger.info("update device master details by id ");
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			List<DeviceMasterDetailsBean> devicDetailsById = offerDetails
					.getDeviceMasterDetailsById(Integer.parseInt(requestBean.getId1()));

			if (!devicDetailsById.isEmpty()) {
				response.setResponseCode("200");

				for (DeviceMasterDetailsBean record : devicDetailsById) {
					if (!ObjectUtils.isEmpty(record.getMobileNumber())) {
						if (record.getMobileNumber().contains("=")) {
							record.setMobileNumber(EncryptorDecryptor.decryptData(record.getMobileNumber()));
						}
						if (record.getCustomerName().contains("=")) {
							record.setCustomerName(EncryptorDecryptor.decryptData(record.getCustomerName()));
						}
					}
				}
				response.setResult(devicDetailsById);
				response.setResponseMessage("Success");
			} else {
				response.setResponseCode("202");
				response.setResponseMessage("No Record Found");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@RequestMapping(value = "/getDeviceMasterDetailsByCustId", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getDeviceMasterDetailsByCustId(
			@RequestBody DeviceMasterDetailsBean requestBean) {
		logger.info("get device master details by id ");
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			List<DeviceMasterDetailsBean> devicDetailsById = offerDetails
					.getDeviceMasterDetailsByCustId(requestBean.getCustomerId().toBigInteger());

			if (!devicDetailsById.isEmpty()) {
				response.setResponseCode("200");
				for (DeviceMasterDetailsBean record : devicDetailsById) {
					if (!ObjectUtils.isEmpty(record.getMobileNumber())) {
						if (record.getMobileNumber().contains("=")) {
							record.setMobileNumber(EncryptorDecryptor.decryptData(record.getMobileNumber()));
						}
					}
					if (!ObjectUtils.isEmpty(record.getDeviceuuid())) {
						if (record.getDeviceuuid().contains("=")) {
							record.setDeviceuuid(EncryptorDecryptor.decryptData(record.getDeviceuuid()));
						}
					}

				}
				response.setResult(devicDetailsById);
				response.setResponseMessage("Success");
			} else {
				response.setResponseCode("202");
				response.setResponseMessage("No Record Found");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	// service for customerKycDocument-Image on custId
	@RequestMapping(value = "/getKycImage", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getKycImage(@RequestBody RequestParamBean requestBean) {
		logger.info("get kyc image ");
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			List<TicketBean> kycImg = offerDetails.getKycImage(Integer.parseInt(requestBean.getId1()));

			if (!kycImg.isEmpty()) {
				response.setResponseCode("200");
				response.setResult(kycImg);
				response.setResponseMessage("Success");
			} else {
				response.setResponseCode("202");
				response.setResponseMessage("No Record Found");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// get data by Custid from customerWalletPoints
	@RequestMapping(value = "/getCustWalletsPoints", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getCustWalletsPoints() {
		logger.info("get cust wallet points by customer id ");
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			List<WalletPointsBean> walletPoints = customerService.getWalletPointsData();

			if (!walletPoints.isEmpty()) {
				response.setResponseCode("200");
				response.setResult(walletPoints);
				response.setResponseMessage("Success");
			} else {
				response.setResponseCode("202");
				response.setResponseMessage("No Record Found");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
			return null;

		}
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	// add wallet points to WalletConfiguration table
	@RequestMapping(value = "/addWalletPoints", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> addWalletPoints(@RequestBody WalletPoints walletPoints) {
		int userStatus = walletPoints.getStatusId();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(walletPoints.getRole_ID().intValue());
		walletPoints.setStatusName(adminWorkFlowReqUtility.getStatusNameByStatusId(walletPoints.getStatusId()));
		walletPoints.setAppName(adminWorkFlowReqUtility.getAppNameByAppId(walletPoints.getAppId()));
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(walletPoints.getActivityName());
		walletPoints.setRoleName(roleName);
		walletPoints.setAction("ADD");

		Boolean isAdded = customerService.addWalletPoints(walletPoints);

		ResponseMessageBean res = new ResponseMessageBean();
		try {
			if (isAdded) {

				customerService.addRewardPointToAdminWorkFlow(walletPoints, userStatus);
				res.setResponseCode("200");
				if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
						&& (ApplicationConstants.YESVALUE)
								.equals(Character.toString(activityMasterData.get(0).getChecker()))
						&& (ApplicationConstants.YESVALUE)
								.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
					res.setResponseMessage("Request Submitted To Admin Checker For Approval");
				} else {
					res.setResponseMessage(" Wallet Points Details Have Been Added");
				}

			} else {
				res.setResponseCode("500");
				res.setResponseMessage(" Error While Adding Wallet Points");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
			return null;

		}
		return new ResponseEntity<>(res, HttpStatus.OK);

	}

	// update record of WalletConfiguration Table
	@RequestMapping(value = "/updateWalletPoints", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateWalletPoints(@RequestBody WalletPoints walletPoints) {
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(walletPoints.getRole_ID().intValue());
		walletPoints.setStatusName(adminWorkFlowReqUtility.getStatusNameByStatusId(walletPoints.getStatusId()));
		walletPoints.setAppName(adminWorkFlowReqUtility.getAppNameByAppId(walletPoints.getAppId()));
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(walletPoints.getActivityName());
		walletPoints.setRoleName(roleName);
		walletPoints.setAction("EDIT");
		boolean isupdated = customerService.updateWalletPoints(walletPoints);
		ResponseMessageBean res = new ResponseMessageBean();
		try {

			if (isupdated) {
				res.setResponseCode("200");
				if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
						&& (ApplicationConstants.YESVALUE)
								.equals(Character.toString(activityMasterData.get(0).getChecker()))
						&& (ApplicationConstants.YESVALUE)
								.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

					res.setResponseMessage("Request Submitted To Admin Checker For Approval");
				} else {
					res.setResponseMessage("Wallet Points Details Have Been Updated");
				}

			} else {
				res.setResponseCode("500");
				res.setResponseMessage(" Error While Updaing Wallet Points Details");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
			return null;

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	// get wallet points from WalletConfiguration Table
	@RequestMapping(value = "/getWalletPoints", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getWalletPoints() {
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			List<WalletPointsBean> walletPointsList = customerService.getWalletPoints();

			if (!walletPointsList.isEmpty()) {
				response.setResponseCode("200");
				response.setResult(walletPointsList);
				response.setResponseMessage("Success");
			} else {
				response.setResponseCode("202");
				response.setResponseMessage("No Record Found");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
			return null;

		}
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	// get wallet points by id from WalletConfiguration Table
	@RequestMapping(value = "/getWalletPointsById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getWalletPointsById(@RequestBody RequestParamBean requestBean) {
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			List<WalletPointsBean> walletPoints = customerService
					.getWalletPointsById(Integer.parseInt(requestBean.getId1()));

			if (!walletPoints.isEmpty()) {
				response.setResponseCode("200");
				response.setResult(walletPoints);
				response.setResponseMessage("Success");
			} else {
				response.setResponseCode("202");
				response.setResponseMessage("No record found");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
			return null;

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/getSecurityQuestions", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getSecurityQuestions() {
		logger.info("get Security questions ");
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			List<SecurityQuestionMaster> questions = customerService.getSecurityQuestions();

			if (!questions.isEmpty()) {
				response.setResponseCode("200");
				response.setResult(questions);
				response.setResponseMessage("Success");
			} else {
				response.setResponseCode("202");
				response.setResponseMessage("No Record Found");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
			return null;

		}
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@RequestMapping(value = "/getSecurityQuestionById", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getSecurityQuestionById(@RequestBody RequestParamBean requestBean) {
		logger.info("get Security questions by ID");
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			List<SecurityQuestionMaster> questions = customerService
					.getSecurityQuestionById(Integer.parseInt(requestBean.getId1()));

			if (!questions.isEmpty()) {
				response.setResponseCode("200");
				response.setResult(questions);
				response.setResponseMessage("Success");
			} else {
				response.setResponseCode("202");
				response.setResponseMessage("No Record Found");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
			return null;

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/addSecurityQuestions", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> addSecurityQuestions(
			@RequestBody SecurityQuestionMaster securityQuestionMaster) {

		int userStatus = securityQuestionMaster.getStatusid().intValue();
		securityQuestionMaster.setStatusname(
				adminWorkFlowReqUtility.getStatusNameByStatusId(securityQuestionMaster.getStatusid().intValue()));
		securityQuestionMaster
				.setAppname(adminWorkFlowReqUtility.getAppNameByAppId((securityQuestionMaster.getAppid().intValue())));
		ResponseMessageBean response = new ResponseMessageBean();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(securityQuestionMaster.getRole_ID().intValue());
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(securityQuestionMaster.getActivityName());
		securityQuestionMaster.setCreatedByName(
				adminWorkFlowReqUtility.getCreatedByNameByCreatedId(securityQuestionMaster.getCreatedby().intValue()));
		securityQuestionMaster.setAction("ADD");
		securityQuestionMaster.setRoleName(roleName);
		ResponseMessageBean responsecode = new ResponseMessageBean();
		responsecode = customerDao.checkQuestionExist(securityQuestionMaster);
		if (responsecode.getResponseCode().equalsIgnoreCase("200")) {

			boolean isAdded = customerService.addSecurityQuestions(securityQuestionMaster);

			if (isAdded) {
				customerService.addSecurityQuestionsToAdminWorkFLow(securityQuestionMaster, userStatus);
				response.setResponseCode("200");
				if ((roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
						|| roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER))
						&& (ApplicationConstants.YESVALUE)
								.equals(Character.toString(activityMasterData.get(0).getChecker()))
						&& (ApplicationConstants.YESVALUE)
								.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
					response.setResponseMessage("Request Submitted To Admin Checker For Approval");
				} else {
					response.setResponseMessage("Security Question Added Successfully");
				}

			} else {
				response.setResponseCode("202");
				response.setResponseMessage("No Record Added");
			}
		} else {
			response.setResponseCode("202");
			response.setResponseMessage(responsecode.getResponseMessage());
		}
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@RequestMapping(value = "/updateSecurityQuestions", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateSecurityQuestions(
			@RequestBody SecurityQuestionMaster securityQuestionMaster) {
		ResponseMessageBean response = new ResponseMessageBean();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(securityQuestionMaster.getRole_ID().intValue());
		securityQuestionMaster.setStatusname(
				adminWorkFlowReqUtility.getStatusNameByStatusId(securityQuestionMaster.getStatusid().intValue()));
		securityQuestionMaster
				.setAppname(adminWorkFlowReqUtility.getAppNameByAppId((securityQuestionMaster.getAppid().intValue())));
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(securityQuestionMaster.getActivityName());
		securityQuestionMaster.setAction("EDIT");
		securityQuestionMaster.setRoleName(roleName);

		ResponseMessageBean responsecode = new ResponseMessageBean();
		responsecode = customerDao.checkUpdateQuestionExist(securityQuestionMaster);
		try {
			if (responsecode.getResponseCode().equalsIgnoreCase("200")) {

				boolean isupdated = customerService.updateSecurityQuestions(securityQuestionMaster);

				if (isupdated) {
					response.setResponseCode("200");
					if ((roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
							|| roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER))
							&& (ApplicationConstants.YESVALUE)
									.equals(Character.toString(activityMasterData.get(0).getChecker()))
							&& (ApplicationConstants.YESVALUE)
									.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
						response.setResponseMessage("Request Submitted To Admin Checker For Approval");
					} else {
						response.setResponseMessage("Security Question Updated Successfully");
					}

				} else {
					response.setResponseCode("202");
					response.setResponseMessage("Records Updating Failed");
				}
			} else {
				response.setResponseCode("202");
				response.setResponseMessage(responsecode.getResponseMessage());
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@RequestMapping(value = "/resetCustPassword", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> resetCustPass(@RequestBody CustomerEntity user) {
		ResponseMessageBean bean = new ResponseMessageBean();
		boolean response = false;
		try {
			String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(user.getRole_ID().intValue());
			user.setStatusName(adminWorkFlowReqUtility.getStatusNameByStatusId(user.getStatusid()));
			user.setAppName(adminWorkFlowReqUtility.getAppNameByAppId(user.getAppid()));
			List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
					.isMakerCheckerPresentForReq(user.getActivityName());
			response = customerService.resetCustPass(user);

			if (response) {
				bean.setResponseCode("200");
				if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
						&& (ApplicationConstants.YESVALUE)
								.equals(Character.toString(activityMasterData.get(0).getChecker()))
						&& (ApplicationConstants.YESVALUE)
								.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
					bean.setResponseMessage("Request Submitted To Admin Checker For Approval");
				} else {
					bean.setResponseMessage("Password Sent To Your Email");
				}

			} else {
				bean.setResponseCode("500");
				bean.setResponseMessage("Error Occured While Reseting Password");

			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}
		return new ResponseEntity<>(bean, HttpStatus.OK);
	}

	@RequestMapping(value = "/getCustByCifMobileName", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getCustByCifMobileName(
			@RequestBody RegistrationDetailsBean detailsBean) {
		logger.info("Inside getCustomerDetails");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<RegistrationDetailsBean> registrationList = new ArrayList<RegistrationDetailsBean>();
			registrationList = customerService.getCustByCifMobileName(detailsBean);

			if (!ObjectUtils.isEmpty(registrationList)) {
				res.setResponseCode("200");
				for (RegistrationDetailsBean record : registrationList) {
					if (record.getDOB() != null && record.getDOB().contains("=")) {
						record.setDOB(EncryptorDecryptor.decryptData(record.getDOB()));
					}
					if (record.getMOBILE() != null && record.getMOBILE().contains("=")) {
						record.setMOBILE(EncryptorDecryptor.decryptData(record.getMOBILE()));
					}
					if (record.getEMAIL() != null && record.getEMAIL().contains("=")) {
						record.setEMAIL(EncryptorDecryptor.decryptData(record.getEMAIL()));
					}
					if (record.getUSERNAME() != null && record.getUSERNAME().contains("=")) {
						record.setUSERNAME(EncryptorDecryptor.decryptData(record.getUSERNAME()));
					}

					if (record.getCIF() != null && record.getCIF().contains("=")) {
						record.setCIF(EncryptorDecryptor.decryptData(record.getCIF()));
					}
				}
				res.setResult(registrationList);
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/saveRMMasterData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> saveRMMasterData(@RequestBody RMMASTER rmMasterData) {
		ResponseMessageBean res = new ResponseMessageBean();
		ResponseMessageBean responsecode = new ResponseMessageBean();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(rmMasterData.getRole_ID().intValue());
		rmMasterData
				.setStatusname(adminWorkFlowReqUtility.getStatusNameByStatusId(rmMasterData.getStatusId().intValue()));
		rmMasterData.setAppname(adminWorkFlowReqUtility.getAppNameByAppId(rmMasterData.getAppId().intValue()));
		rmMasterData.setRoleName(roleName);
		rmMasterData.setProductName(adminWorkFlowReqUtility.getAppNameByAppId(rmMasterData.getAppId().intValue()));
		rmMasterData.setCreatedByName(
				adminWorkFlowReqUtility.getCreatedByNameByCreatedId(rmMasterData.getCreatedby().intValue()));
		rmMasterData.setAction("ADD");

		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(rmMasterData.getActivityName());
		try {
			if (!ObjectUtils.isEmpty(rmMasterData)) {
				responsecode = customerService.isRMNameExist(rmMasterData);
				if (responsecode.getResponseCode().equalsIgnoreCase("200")) {
					boolean isAdded = customerService.saveRMMasterData(rmMasterData);
					if (isAdded) {
						res.setResponseCode("200");
						if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getChecker()))
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
							res.setResponseMessage("Request Submitted To Admin Checker For Approval");
						} else {
							res.setResponseMessage("RM Master Details Has Been Added Successfully");
						}

					} else {

						res.setResponseCode("500");
						res.setResponseMessage("Error Ocured While Adding The Record");
					}
				} else {
					res.setResponseCode("500");
					res.setResponseMessage(responsecode.getResponseMessage());
				}

			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/getRMMasterData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getRMMasterData() {
		logger.info("get RMMaster Data ");
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			List<RMMASTER> rmMasterData = customerService.getRMMasterData();

			if (!rmMasterData.isEmpty()) {
				response.setResponseCode("200");
				response.setResult(rmMasterData);
				response.setResponseMessage("Success");
			} else {
				response.setResponseCode("202");
				response.setResponseMessage("No Record Found");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@RequestMapping(value = "/updateRMMasterData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateRMMasterData(@RequestBody RMMASTER rmMasterData) {
		ResponseMessageBean res = new ResponseMessageBean();
		ResponseMessageBean responsecode = new ResponseMessageBean();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(rmMasterData.getRole_ID().intValue());
		rmMasterData
				.setStatusname(adminWorkFlowReqUtility.getStatusNameByStatusId(rmMasterData.getStatusId().intValue()));
		rmMasterData.setAppname(adminWorkFlowReqUtility.getAppNameByAppId(rmMasterData.getAppId().intValue()));
		rmMasterData.setRoleName(roleName);
		rmMasterData.setProductName(adminWorkFlowReqUtility.getAppNameByAppId(rmMasterData.getAppId().intValue()));
		rmMasterData.setCreatedByName(
				adminWorkFlowReqUtility.getCreatedByNameByCreatedId(rmMasterData.getCreatedby().intValue()));
		rmMasterData.setAction("EDIT");
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(rmMasterData.getActivityName());
		try {
			if (!ObjectUtils.isEmpty(rmMasterData)) {
				responsecode = customerService.isUpdateRMNameExist(rmMasterData);
				if (responsecode.getResponseCode().equalsIgnoreCase("200")) {
					boolean updated = customerService.updateRMMasterData(rmMasterData);
					if (updated) {
						res.setResponseCode("200");
						if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getChecker()))
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
							res.setResponseMessage("Request Submitted To Admin Checker For Approval");
						} else {
							res.setResponseMessage("RM Master Details Has Been Updated Successfully");
						}

					} else {

						res.setResponseCode("500");
						res.setResponseMessage("Error Ocured While Updating The Record");
					}
				} else {
					res.setResponseCode("500");
					res.setResponseMessage(responsecode.getResponseMessage());
				}

			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/getRMMasterDataById", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getRMMasterDataById(@RequestBody RequestParamBean requestBean) {
		logger.info("get RMMaster Data by ID");
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			List<RMMASTER> rmMasterData = customerService.getRMMasterDataById(Integer.parseInt(requestBean.getId1()));

			if (!rmMasterData.isEmpty()) {
				response.setResponseCode("200");
				response.setResult(rmMasterData);
				response.setResponseMessage("Success");
			} else {
				response.setResponseCode("202");
				response.setResponseMessage("No Record Found");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/deletetRMMasterById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> deletetRMMasterById(@RequestBody RMMASTER rmMasterData) {
		ResponseMessageBean bean = new ResponseMessageBean();
		try {
			String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(rmMasterData.getRole_ID().intValue());
			rmMasterData.setStatusname(
					adminWorkFlowReqUtility.getStatusNameByStatusId(rmMasterData.getStatusId().intValue()));
			rmMasterData.setAppname(adminWorkFlowReqUtility.getAppNameByAppId(rmMasterData.getAppId().intValue()));
			rmMasterData.setRoleName(roleName);
			rmMasterData.setProductName(adminWorkFlowReqUtility.getAppNameByAppId(rmMasterData.getAppId().intValue()));
			rmMasterData.setCreatedByName(
					adminWorkFlowReqUtility.getCreatedByNameByCreatedId(rmMasterData.getCreatedby().intValue()));
			rmMasterData.setAction("DELETE");
			List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
					.isMakerCheckerPresentForReq("RMMASTERDELETE");
			Boolean response = customerService.deletetRMMasterById(rmMasterData);

			if (response) {
				bean.setResponseCode("200");
				if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
						&& (ApplicationConstants.YESVALUE)
								.equals(Character.toString(activityMasterData.get(0).getChecker()))
						&& (ApplicationConstants.YESVALUE)
								.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
					bean.setResponseMessage("Request Submitted To Admin Checker For Approval");
				} else {
					bean.setResponseMessage("Deleted Successfully");
				}
			} else {
				bean.setResponseCode("202");
				bean.setResponseMessage("Error Ocured While Deleting The Record");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}
		return new ResponseEntity<>(bean, HttpStatus.OK);
	}

	@RequestMapping(value = "/getChannelList", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getChannelList() {
		logger.info("get Channnnel List ");
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			List<ChannelMasterEntity> channelData = customerService.getChannelList();

			if (!ObjectUtils.isEmpty(channelData)) {
				response.setResponseCode("200");
				response.setResult(channelData);
				response.setResponseMessage("Success");
			} else {
				response.setResponseCode("202");
				response.setResponseMessage("No Record Found");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@RequestMapping(value = "/getCustomerType", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getCustomerType() {
		logger.info("get Customer Type ");
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			List<String> custType = new ArrayList<>();
			custType.add("NRO");
			custType.add("NRE");

			if (!custType.isEmpty()) {
				response.setResponseCode("200");
				response.setResult(custType);
				response.setResponseMessage("Success");
			} else {
				response.setResponseCode("202");
				response.setResponseMessage("No Record Found");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@RequestMapping(value = "/saveCustOtherInfo", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> saveCustOtherInfo(@RequestBody CustomerOtherInfoEntity custOtherInfo) {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(custOtherInfo.getRole_ID().intValue());
			custOtherInfo.setStatusname(
					adminWorkFlowReqUtility.getStatusNameByStatusId(custOtherInfo.getStatusId().intValue()));
			custOtherInfo.setAppname(adminWorkFlowReqUtility.getAppNameByAppId(custOtherInfo.getAppId().intValue()));
			List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
					.isMakerCheckerPresentForReq(custOtherInfo.getActivityName());
			custOtherInfo.setCreatedByName(
					adminWorkFlowReqUtility.getCreatedByNameByCreatedId(custOtherInfo.getCreatedBy().intValue()));
			custOtherInfo.setRoleName(roleName);
			custOtherInfo.setAction("ADD");

			boolean isAdded = customerService.saveCustOtherInfo(custOtherInfo);

			if (isAdded) {
				res.setResponseCode("200");
				if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
						&& (ApplicationConstants.YESVALUE)
								.equals(Character.toString(activityMasterData.get(0).getChecker()))
						&& (ApplicationConstants.YESVALUE)
								.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
					res.setResponseMessage("Request Submitted To Admin Checker For Approval");
				} else {
					res.setResponseMessage("Details Has Been Saved Successfully");
				}

			} else {
				res.setResponseCode("500");
				res.setResponseMessage("Error Ocured While Saving The Record");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/getCustOtherInfo", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getCustOtherInfo() {
		logger.info("get customer Other  Info ");
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			List<CustomerOtherInfoEntity> custOtherInfo = customerService.getCustOtherInfo();

			if (!custOtherInfo.isEmpty()) {
				response.setResponseCode("200");
				for (CustomerOtherInfoEntity record : custOtherInfo) {

					if (record.getMobile() != null && record.getMobile().contains("=")) {
						record.setMobile(EncryptorDecryptor.decryptData(record.getMobile()));
					}

					if (record.getCif() != null && record.getCif().contains("=")) {
						record.setCif(EncryptorDecryptor.decryptData(record.getCif()));
					}

					if (record.getCustomername() != null && record.getCustomername().contains("=")) {
						record.setCustomername(EncryptorDecryptor.decryptData(record.getCustomername()));

					}
				}
				response.setResult(custOtherInfo);
				response.setResponseMessage("Success");
			} else {
				response.setResponseCode("202");
				response.setResponseMessage("No Record Found");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@RequestMapping(value = "/updateCustOtherInfo", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateCustOtherInfo(@RequestBody CustomerOtherInfoEntity custOtherInfo) {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(custOtherInfo.getRole_ID().intValue());
			custOtherInfo.setStatusname(
					adminWorkFlowReqUtility.getStatusNameByStatusId(custOtherInfo.getStatusId().intValue()));
			custOtherInfo.setAppname(adminWorkFlowReqUtility.getAppNameByAppId(custOtherInfo.getAppId().intValue()));
			List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
					.isMakerCheckerPresentForReq(custOtherInfo.getActivityName());
			custOtherInfo.setCreatedByName(
					adminWorkFlowReqUtility.getCreatedByNameByCreatedId(custOtherInfo.getCreatedBy().intValue()));
			custOtherInfo.setRoleName(roleName);
			custOtherInfo.setAction("EDIT");

			boolean updated = customerService.updateCustOtherInfo(custOtherInfo);

			if (updated) {
				res.setResponseCode("200");
				if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
						&& (ApplicationConstants.YESVALUE)
								.equals(Character.toString(activityMasterData.get(0).getChecker()))
						&& (ApplicationConstants.YESVALUE)
								.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
					res.setResponseMessage("Request Submitted To Admin Checker For Approval");
				} else {
					res.setResponseMessage("Customer Details Has Been Updated Successfully");
				}

			} else {

				res.setResponseCode("500");
				res.setResponseMessage("Error Ocured While Updating The Record");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/getCustOtherInfoById", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getCustOtherInfoById(@RequestBody RequestParamBean requestBean) {
		logger.info("get customer other Info by ID");
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			List<CustomerOtherInfoEntity> custOtherInfo = customerService
					.getCustOtherInfoById(Integer.parseInt(requestBean.getId1()));
			if (!custOtherInfo.isEmpty()) {
				response.setResponseCode("200");
				for (CustomerOtherInfoEntity record : custOtherInfo) {
					if (record.getMobile() != null && record.getMobile().contains("=")) {
						record.setMobile(EncryptorDecryptor.decryptData(record.getMobile()));
					}

					if (record.getCustomername() != null && record.getCustomername().contains("=")) {
						record.setCustomername(EncryptorDecryptor.decryptData(record.getCustomername()));
					}

					if (record.getCif() != null && record.getCif().contains("=")) {
						record.setCif(EncryptorDecryptor.decryptData(record.getCif()));
					}
				}
				response.setResult(custOtherInfo);
				response.setResponseMessage("Success");
			} else {
				response.setResponseCode("202");
				response.setResponseMessage("No Record Found");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/getCustOtherInfoByCustId", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getCustOtherInfoByCustId(@RequestBody RequestParamBean requestBean) {
		logger.info("get customer other Info by ID");
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			List<CustomerOtherInfoEntity> custOtherInfo = customerService
					.getCustOtherInfoByCustId(Integer.parseInt(requestBean.getId1()));

			if (!custOtherInfo.isEmpty()) {
				response.setResponseCode("200");
				for (CustomerOtherInfoEntity record : custOtherInfo) {
					if (record.getMobile() != null && record.getMobile().contains("=")) {
						record.setMobile(EncryptorDecryptor.decryptData(record.getMobile()));
					}

					if (record.getCustomername() != null && record.getCustomername().contains("=")) {
						record.setCustomername(EncryptorDecryptor.decryptData(record.getCustomername()));
					}

					if (record.getCif() != null && record.getCif().contains("=")) {
						record.setCif(EncryptorDecryptor.decryptData(record.getCif()));
					}
				}
				response.setResult(custOtherInfo);
				response.setResponseMessage("Success");
			} else {
				response.setResponseCode("202");
				response.setResponseMessage("No Record Found");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/sendEmailWithAttachment", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> sendEmailWithAttachment(@RequestBody EmailRequestBean emailRequestBean)
			throws IOException {
		logger.info("get customer other Info by ID");
		ResponseMessageBean response = new ResponseMessageBean();
		boolean isEmailSent = false;
		try {
			isEmailSent = customerService.sendEmailWithAttachment(emailRequestBean);
			if (isEmailSent) {
				response.setResponseCode("200");

				response.setResponseMessage("Email Send Successfully");
			} else {
				response.setResponseCode("202");
				response.setResponseMessage("Error Occured While Sending Email");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/getAllCustomers", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getAllCustomers() {
		logger.info("Inside getAllCustomers");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<RegistrationDetailsBean> registrationList = new ArrayList<RegistrationDetailsBean>();
			registrationList = customerService.getAllCustomers();

			if (!ObjectUtils.isEmpty(registrationList)) {
				res.setResponseCode("200");
				for (RegistrationDetailsBean record : registrationList) {
					if (record.getDOB() != null && record.getDOB().contains("=")) {
						record.setDOB(EncryptorDecryptor.decryptData(record.getDOB()));
					}
					if (record.getMOBILE() != null && record.getMOBILE().contains("=")) {
						record.setMOBILE(EncryptorDecryptor.decryptData(record.getMOBILE()));
					}
					if (record.getEMAIL() != null && record.getEMAIL().contains("=")) {
						record.setEMAIL(EncryptorDecryptor.decryptData(record.getEMAIL()));
					}
					if (record.getUSERNAME() != null && record.getUSERNAME().contains("=")) {
						record.setUSERNAME(EncryptorDecryptor.decryptData(record.getUSERNAME()));
					}
					if (record.getCIF() != null && record.getCIF().contains("=")) {
						record.setCIF(EncryptorDecryptor.decryptData(record.getCIF()));
					}
				}
				res.setResult(registrationList);
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/saveBulkCustomers", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> saveBulkCustomers(@RequestBody List<CustomerEntity> custDataList) {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(custDataList.get(0).getRole_ID().intValue());
			List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
					.isMakerCheckerPresentForReq(custDataList.get(0).getActivityName());
			boolean isAdded = customerService.saveBulkCustomers(custDataList);

			if (isAdded) {
				res.setResponseCode("200");
				if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
						&& (ApplicationConstants.YESVALUE)
								.equals(Character.toString(activityMasterData.get(0).getChecker()))
						&& (ApplicationConstants.YESVALUE)
								.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
					res.setResponseMessage("Request Submitted To Admin Checker For Approval");
				} else {
					res.setResponseMessage("Details Has Been Saved Successfully");
				}

			} else {
				res.setResponseCode("500");
				res.setResponseMessage("Error Ocured While Saving The Record");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}

		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateWorngAttempsOfCustomers", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateWorngAAttempsOfCustomers(@RequestBody CustomerEntity customer) {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			boolean updated = customerService.updateWorngAAttempsOfCustomers(customer);

			if (updated) {
				res.setResponseCode("200");
				res.setResponseMessage("Customer Details Has Been Updated Successfully");

			} else {
				res.setResponseCode("500");
				res.setResponseMessage("Error Ocured While Updating The Record");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/sendCustomizeEmailToBulkUsers", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> sendCustomizeEmailToBulkUsers(@RequestBody EmailRequestBean emailBean) {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			boolean updated = customerDao.sendCustomizeEmailToBulkUsers(emailBean);

			if (updated) {
				res.setResponseCode("200");
				res.setResponseMessage("Email Send Successfully");

			} else {
				res.setResponseCode("500");
				res.setResponseMessage("Error Ocured While Updating The Record");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/getAllCustomerDetails", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getAllCustomerDetails() {
		logger.info("Inside getAllCustomers");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<RegistrationDetailsBean> customerDetails = new ArrayList<RegistrationDetailsBean>();
			customerDetails = customerService.getAllCustomerDetails();

			if (!ObjectUtils.isEmpty(customerDetails)) {
				res.setResponseCode("200");
				for (RegistrationDetailsBean customer : customerDetails) {

					if (customer.getMOBILE() != null && customer.getMOBILE().contains("=")) {
						customer.setMOBILE(EncryptorDecryptor.decryptData(customer.getMOBILE()));
					}
					if (customer.getEMAIL() != null && customer.getEMAIL().contains("=")) {
						customer.setEMAIL(EncryptorDecryptor.decryptData(customer.getEMAIL()));
					}
					if (customer.getCIF() != null && customer.getCIF().contains("=")) {
						customer.setCIF(EncryptorDecryptor.decryptData(customer.getCIF()));
					}
				}
				res.setResult(customerDetails);
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/customerValidation", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> customerValidation(@RequestBody RequestParamBean requestBean) {
		logger.info("customer validation");
		ResponseMessageBean response = new ResponseMessageBean();
		Boolean res = false;
		try {
			ResponseMessageBean req = customerService.customerValidation(requestBean.getId1(), requestBean.getId2(),
					requestBean.getId3(), requestBean.getId4(), requestBean.getId5(), requestBean.getId6());
			if (!ObjectUtils.isEmpty(req)) {
				response.setResponseCode("200");
				response.setResult(req);
				response.setResponseMessage("Customer Validation Successful");
			} else {
				response.setResponseCode("202");
				response.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/getRegistrationDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getRegistrationDetails(@RequestBody DateBean datebean) {
		logger.info("Registration Details");
		ResponseMessageBean response = new ResponseMessageBean();
		Boolean res = false;
		try {
			List<CustomerEntity> customerDetails = new ArrayList<CustomerEntity>();
			customerDetails = customerService.getRegistrationDetails(datebean);

			for (CustomerEntity custObj : customerDetails) {

				if (custObj.getCustomername() != null && custObj.getCustomername().contains("=")) {
					custObj.setCustomername(EncryptorDecryptor.decryptData(custObj.getCustomername()));

				}
				if (custObj.getCif() != null && custObj.getCif().contains("=")) {
					custObj.setCif(EncryptorDecryptor.decryptData(custObj.getCif()));

				}
				if (custObj.getMobile() != null && custObj.getMobile().contains("=")) {
					custObj.setMobile((EncryptorDecryptor.decryptData(custObj.getMobile())));

				}
			}
			if (!ObjectUtils.isEmpty(customerDetails)) {
				response.setResponseCode("200");
				response.setResult(customerDetails);
				response.setResponseMessage("Registration Details Successful");
			} else {
				response.setResponseCode("202");
				response.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// sneha code

	@RequestMapping(value = "/getRetailCustomerDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getRetailCustomerDetails(@RequestBody DateBean dateBean) {
		logger.info("In getCustomerDetails -> getCustomerDetails()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<CustomerEntity> customerentity = customerService.getRetailCustomerDetails(dateBean);
			for (CustomerEntity custObj : customerentity) {
				if (custObj.getCustomername() != null && custObj.getCustomername().contains("=")) {
					custObj.setCustomername(EncryptorDecryptor.decryptData(custObj.getCustomername()));
				}
				if (custObj.getCif() != null && custObj.getCif().contains("=")) {
					custObj.setCif(EncryptorDecryptor.decryptData(custObj.getCif()));
				}
				if (custObj.getMobile() != null && custObj.getMobile().contains("=")) {
					custObj.setMobile((EncryptorDecryptor.decryptData(custObj.getMobile())));
				}
				if (custObj.getEmail() != null && custObj.getEmail().contains("=")) {
					custObj.setEmail((EncryptorDecryptor.decryptData(custObj.getEmail())));
				}
			}
			if (!ObjectUtils.isEmpty(customerentity)) {
				res.setResponseCode("200");
				res.setResult(customerentity);
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

//vishal
	@PostMapping("/getTabledatabytablename")
	public ResponseEntity<ResponseMessageBean> getTabledatabytablename(
			@RequestBody TimestampDateBean timestampDateBean) {
		ResponseMessageBean responseMessageBean = new ResponseMessageBean();
		if (timestampDateBean.getTable_name().equalsIgnoreCase("CORP_COMPANY_MASTER")) {
			List<CorpCompanyMasterNewEntity> paymentagg = corpCompanyMasterRepository
					.findAllByCreatedOnBetween(timestampDateBean.getFromdate(), timestampDateBean.getTodate());
			if (!paymentagg.isEmpty()) {
				responseMessageBean.setResponseCode("200");
				for (CorpCompanyMasterNewEntity record : paymentagg) {
					if (record.getCompanyCode() != null && record.getCompanyCode().contains("=")) {
						record.setCompanyCode(EncryptorDecryptor.decryptData(record.getCompanyCode()));
					}
					if (record.getCompanyName() != null && record.getCompanyName().contains("=")) {
						record.setCompanyName(EncryptorDecryptor.decryptData(record.getCompanyName()));
					}
					if (record.getLogo() != null && record.getLogo().contains("=")) {
						record.setLogo(EncryptorDecryptor.decryptData(record.getLogo()));
					}
					if (record.getCif() != null && record.getCif().contains("=")) {
						record.setCif(EncryptorDecryptor.decryptData(record.getCif()));
					}
					if (record.getPancardNo() != null && record.getPancardNo().contains("=")) {
						record.setPancardNo(EncryptorDecryptor.decryptData(record.getPancardNo()));
					}
					if (record.getCompanyInfo() != null && record.getCompanyInfo().contains("=")) {
						record.setCompanyInfo(EncryptorDecryptor.decryptData(record.getCompanyInfo()));
					}

				}
				responseMessageBean.setResult(paymentagg);
				return new ResponseEntity<>(responseMessageBean, HttpStatus.OK);

			}
		}
		if (timestampDateBean.getTable_name().equalsIgnoreCase("CORP_USERS")) {
			List<CorpUserNewEntity> paymentagg = corpUsersRepository
					.findAllByCreatedonBetween(timestampDateBean.getFromdate(), timestampDateBean.getTodate());
			if (!paymentagg.isEmpty()) {
				responseMessageBean.setResponseCode("200");
				for (CorpUserNewEntity record : paymentagg) {
					if (record.getUser_disp_name() != null && record.getUser_disp_name().contains("=")) {
						record.setUser_disp_name(EncryptorDecryptor.decryptData(record.getUser_disp_name()));
					}
					if (record.getUser_name() != null && record.getUser_name().contains("=")) {
						record.setUser_name(EncryptorDecryptor.decryptData(record.getUser_name()));
					}

					if (record.getEmail_id() != null && record.getEmail_id().contains("=")) {
						record.setEmail_id(EncryptorDecryptor.decryptData(record.getEmail_id()));
					}
					if (record.getWork_phone() != null && record.getWork_phone().contains("=")) {
						record.setWork_phone(EncryptorDecryptor.decryptData(record.getWork_phone()));
					}
					if (record.getPersonal_Phone() != null && record.getPersonal_Phone().contains("=")) {
						record.setPersonal_Phone(EncryptorDecryptor.decryptData(record.getPersonal_Phone()));
					}
					if (record.getNationalId() != null && record.getNationalId().contains("=")) {
						record.setNationalId(EncryptorDecryptor.decryptData(record.getNationalId()));
					}
					if (record.getPassport() != null && record.getPassport().contains("=")) {
						record.setPassport(EncryptorDecryptor.decryptData(record.getPassport()));
					}

					if (record.getPancardNumber() != null && record.getPancardNumber().contains("=")) {
						record.setPancardNumber(EncryptorDecryptor.decryptData(record.getPancardNumber()));
					}
					if (record.getAadharCardNo() != null && record.getAadharCardNo().contains("=")) {
						record.setAadharCardNo(EncryptorDecryptor.decryptData(record.getAadharCardNo()));
					}
					if (record.getDob() != null && record.getDob().contains("=")) {
						record.setDob(EncryptorDecryptor.decryptData(record.getDob()));
					}

				}
				responseMessageBean.setResult(paymentagg);
				return new ResponseEntity<>(responseMessageBean, HttpStatus.OK);
			}
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PostMapping(value = "/serviceTypeList", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> serviceTypeList() {
		ResponseMessageBean responseMessageBean = new ResponseMessageBean();
		List<String> serviceTypeList = null;
		try {
			serviceTypeList = offerDetails.serviceTypeList();
		} catch (Exception e) {
			logger.error("Exception while fetch service type list");
		}

		if (!ObjectUtils.isEmpty(serviceTypeList)) {
			responseMessageBean.setResponseCode("200");
			responseMessageBean.setResult(serviceTypeList);
			return new ResponseEntity<>(responseMessageBean, HttpStatus.OK);
		} else {
			responseMessageBean.setResponseCode("202");
			return new ResponseEntity<>(responseMessageBean, HttpStatus.ACCEPTED);
		}

	}

}
