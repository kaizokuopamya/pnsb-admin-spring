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

import com.itl.pns.bean.CountryStateCityBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.constant.ApplicationConstants;
import com.itl.pns.dao.CountryStateCityDao;
import com.itl.pns.entity.ActivitySettingMasterEntity;
import com.itl.pns.entity.CitiesMasterEntity;
import com.itl.pns.entity.CountryMasterEntity;
import com.itl.pns.entity.StateMasterEntity;
import com.itl.pns.service.CountryStateCityService;
import com.itl.pns.util.AdminWorkFlowReqUtility;

/**
 * @author shubham.lokhande
 *
 */
@RestController
@RequestMapping("countryStateCity")
public class CountryStateCityController {

	@Autowired
	CountryStateCityService countryStateCityService;

	@Autowired
	AdminWorkFlowReqUtility adminWorkFlowReqUtility;

	@Autowired
	CountryStateCityDao countryStateCityDao;

	private static final Logger logger = LogManager.getLogger(InvestementProductsController.class);

	@RequestMapping(value = "/getAllCountryDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getAllCountryDetails() {
		logger.info("In CountryStateCityController -> getAllCountryDetails()");
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			List<CountryMasterEntity> resultData = countryStateCityService.getAllCountryDetails();

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

	@RequestMapping(value = "/getCountryById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getCountryById(@RequestBody CountryMasterEntity countryData) {
		logger.info("In CountryStateCityController -> getCountryById()");
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			List<CountryMasterEntity> resultData = countryStateCityService.getCountryById(countryData);

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

	@RequestMapping(value = "/addCountryDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> addCountryDetails(@RequestBody CountryMasterEntity countryData) {
		ResponseMessageBean response = new ResponseMessageBean();

		logger.info("In CountryStateCityController -> addCountryDetails()");
		Boolean res = false;
		countryData.setCreatedByName(
				adminWorkFlowReqUtility.getCreatedByNameByCreatedId(countryData.getCreatedBy().intValue()));
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(countryData.getRole_ID().intValue());
		countryData.setRoleName(roleName);
		countryData.setAction("ADD");
		countryData
				.setStatusName(adminWorkFlowReqUtility.getStatusNameByStatusId(countryData.getStatusId().intValue()));
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(countryData.getActivityName());
		ResponseMessageBean responsecode = new ResponseMessageBean();

		try {
			if (!ObjectUtils.isEmpty(countryData)) {
				responsecode = countryStateCityDao.isCountryExists(countryData);
				if (responsecode.getResponseCode().equalsIgnoreCase("200")) {

					res = countryStateCityService.addCountryDetails(countryData);
					if (res) {
						boolean isDataRefresh = false;
						isDataRefresh = adminWorkFlowReqUtility.refreshCacheData("CountryMasterReader");
						response.setResponseCode("200");
						if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getChecker()))
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

							response.setResponseMessage("Request Submitted To Admin Checker For Approval");
						} else {
							response.setResponseMessage("Country Has Been Saved Successfully");
						}

					} else {
						response.setResponseCode("500");
						response.setResponseMessage("Error While Saving Records");
					}
				} else {
					response.setResponseCode("500");
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

	@RequestMapping(value = "/updateCountryDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateCountryDetails(@RequestBody CountryMasterEntity countryData) {
		ResponseMessageBean response = new ResponseMessageBean();
		logger.info("In CountryStateCityController -> updateCountryDetails()");
		Boolean res = false;

		countryData.setCreatedByName(
				adminWorkFlowReqUtility.getCreatedByNameByCreatedId(countryData.getCreatedBy().intValue()));
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(countryData.getRole_ID().intValue());
		countryData.setRoleName(roleName);
		countryData.setAction("EDIT");
		countryData
				.setStatusName(adminWorkFlowReqUtility.getStatusNameByStatusId(countryData.getStatusId().intValue()));
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(countryData.getActivityName());
		ResponseMessageBean responsecode = new ResponseMessageBean();
		try {
			if (!ObjectUtils.isEmpty(countryData)) {
				responsecode = countryStateCityDao.isUpdateCountryExists(countryData);

				if (responsecode.getResponseCode().equalsIgnoreCase("200")) {
					res = countryStateCityService.updateCountryDetails(countryData);
					if (res) {
						boolean isDataRefresh = false;
						isDataRefresh = adminWorkFlowReqUtility.refreshCacheData("CountryMasterReader");
						response.setResponseCode("200");
						if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getChecker()))
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

							response.setResponseMessage("Request Submitted To Admin Checker For Approval");
						} else {
							response.setResponseMessage("Country Has Been Updated Successfully");
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

	@RequestMapping(value = "/getAllStateDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getAllStateDetails() {
		logger.info("In CountryStateCityController -> getAllStateDetails()");
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			List<StateMasterEntity> resultData = countryStateCityService.getAllStateDetails();

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

	@RequestMapping(value = "/getStateById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getStateById(@RequestBody StateMasterEntity stateData) {
		logger.info("In CountryStateCityController -> getStateById()");
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			List<StateMasterEntity> resultData = countryStateCityService.getStateById(stateData);

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

	@RequestMapping(value = "/addStateDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> addStateDetails(@RequestBody StateMasterEntity stateData) {
		ResponseMessageBean response = new ResponseMessageBean();
		logger.info("In CountryStateCityController -> addStateDetails()");
		Boolean res = false;
		List<CountryStateCityBean> obj = adminWorkFlowReqUtility
				.getCountryNameById(stateData.getCountryId().toBigInteger());
		stateData.setCountryName(obj.get(0).getCountryName());
		stateData.setCreatedByName(
				adminWorkFlowReqUtility.getCreatedByNameByCreatedId(stateData.getCreatedBy().intValue()));
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(stateData.getRole_ID().intValue());
		stateData.setRoleName(roleName);
		stateData.setAction("ADD");
		stateData.setStatusName(adminWorkFlowReqUtility.getStatusNameByStatusId(stateData.getStatusId().intValue()));
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(stateData.getActivityName());
		ResponseMessageBean responsecode = new ResponseMessageBean();

		try {
			if (!ObjectUtils.isEmpty(stateData)) {
				responsecode = countryStateCityDao.isStateExists(stateData);
				if (responsecode.getResponseCode().equalsIgnoreCase("200")) {

					res = countryStateCityService.addStateDetails(stateData);
					if (res) {
						boolean isDataRefresh = false;
						isDataRefresh = adminWorkFlowReqUtility.refreshCacheData("StateMasterReader");
						response.setResponseCode("200");
						if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getChecker()))
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

							response.setResponseMessage("Request Submitted To Admin Checker For Approval");
						} else {
							response.setResponseMessage("State Has Been  Saved Successfully");
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

	@RequestMapping(value = "/updateStateDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateStateDetails(@RequestBody StateMasterEntity stateData) {
		ResponseMessageBean response = new ResponseMessageBean();
		logger.info("In CountryStateCityController -> updateStateDetails()");
		Boolean res = false;
		List<CountryStateCityBean> obj = adminWorkFlowReqUtility
				.getCountryNameById(stateData.getCountryId().toBigInteger());
		stateData.setCountryName(obj.get(0).getCountryName());
		stateData.setCreatedByName(
				adminWorkFlowReqUtility.getCreatedByNameByCreatedId(stateData.getCreatedBy().intValue()));
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(stateData.getRole_ID().intValue());
		stateData.setRoleName(roleName);
		stateData.setAction("EDIT");
		stateData.setStatusName(adminWorkFlowReqUtility.getStatusNameByStatusId(stateData.getStatusId().intValue()));
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(stateData.getActivityName());
		ResponseMessageBean responsecode = new ResponseMessageBean();
		try {
			if (!ObjectUtils.isEmpty(stateData)) {
				responsecode = countryStateCityDao.isUpdateStateExists(stateData);
				if (responsecode.getResponseCode().equalsIgnoreCase("200")) {
					res = countryStateCityService.updateStateDetails(stateData);
					if (res) {
						boolean isDataRefresh = false;
						isDataRefresh = adminWorkFlowReqUtility.refreshCacheData("StateMasterReader");
						response.setResponseCode("200");
						if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getChecker()))
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

							response.setResponseMessage("Request Submitted To Admin Checker For Approval");
						} else {
							response.setResponseMessage("State Has Been  Updated Successfully");
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

	@RequestMapping(value = "/getAllCitiesDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getAllCitiesDetails() {
		logger.info("In CountryStateCityController -> getAllCitiesDetails()");

		ResponseMessageBean response = new ResponseMessageBean();
		try {
			List<CitiesMasterEntity> resultData = countryStateCityService.getAllCitiesDetails();

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

	@RequestMapping(value = "/getCityById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getCityById(@RequestBody CitiesMasterEntity cityData) {
		logger.info("In CountryStateCityController -> getCityById()");
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			List<CitiesMasterEntity> resultData = countryStateCityService.getCityById(cityData);

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

	@RequestMapping(value = "/addCityDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> addCityDetails(@RequestBody CitiesMasterEntity cityData) {
		ResponseMessageBean response = new ResponseMessageBean();
		logger.info("In CountryStateCityController -> addCityDetails()");
		Boolean res = false;
		List<CountryStateCityBean> obj = adminWorkFlowReqUtility
				.getCountryStateNameById(cityData.getCountryId().toBigInteger(), cityData.getStateId().toBigInteger());
		cityData.setCountryName(obj.get(0).getCountryName());
		cityData.setStateName(obj.get(0).getStateName());
		cityData.setCreatedByName(
				adminWorkFlowReqUtility.getCreatedByNameByCreatedId(cityData.getCreatedBy().intValue()));
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(cityData.getRole_ID().intValue());
		cityData.setRoleName(roleName);
		cityData.setAction("ADD");
		cityData.setStatusName(adminWorkFlowReqUtility.getStatusNameByStatusId(cityData.getStatusId().intValue()));
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(cityData.getActivityName());
		ResponseMessageBean responsecode = new ResponseMessageBean();

		try {
			if (!ObjectUtils.isEmpty(cityData)) {
				responsecode = countryStateCityDao.isCityExists(cityData);
				if (responsecode.getResponseCode().equalsIgnoreCase("200")) {

					res = countryStateCityService.addCityDetails(cityData);
					if (res) {
						boolean isDataRefresh = false;
						isDataRefresh = adminWorkFlowReqUtility.refreshCacheData("CitiesMasterReader");
						response.setResponseCode("200");
						if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getChecker()))
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

							response.setResponseMessage("Request Submitted To Admin Checker For Approval");
						} else {
							response.setResponseMessage("City Has Been  Saved Successfully");
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

	@RequestMapping(value = "/updateCityDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateCityDetails(@RequestBody CitiesMasterEntity cityData) {
		ResponseMessageBean response = new ResponseMessageBean();
		logger.info("In CountryStateCityController -> updateCityDetails()");
		Boolean res = false;
		List<CountryStateCityBean> obj = adminWorkFlowReqUtility.getCountryStateCityNameById(
				cityData.getCountryId().toBigInteger(), cityData.getStateId().toBigInteger(),
				cityData.getId().toBigInteger());
		cityData.setCountryName(obj.get(0).getCountryName());
		cityData.setStateName(obj.get(0).getStateName());
		cityData.setCityName(obj.get(0).getCityName());
		cityData.setCreatedByName(
				adminWorkFlowReqUtility.getCreatedByNameByCreatedId(cityData.getCreatedBy().intValue()));
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(cityData.getRole_ID().intValue());
		cityData.setRoleName(roleName);
		cityData.setAction("EDIT");
		cityData.setStatusName(adminWorkFlowReqUtility.getStatusNameByStatusId(cityData.getStatusId().intValue()));
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(cityData.getActivityName());
		ResponseMessageBean responsecode = new ResponseMessageBean();
		try {
			if (!ObjectUtils.isEmpty(cityData)) {
				responsecode = countryStateCityDao.isUpdateCityExists(cityData);
				if (responsecode.getResponseCode().equalsIgnoreCase("200")) {
					res = countryStateCityService.updateCityDetails(cityData);
					if (res) {
						boolean isDataRefresh = false;
						isDataRefresh = adminWorkFlowReqUtility.refreshCacheData("CitiesMasterReader");
						response.setResponseCode("200");
						if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getChecker()))
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

							response.setResponseMessage("Request Submitted To Admin Checker For Approval");
						} else {
							response.setResponseMessage("City Has Been  Updated Successfully");
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
