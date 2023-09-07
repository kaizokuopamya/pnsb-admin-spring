package com.itl.pns.controller;

import java.util.Date;
import java.util.List;

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

import com.itl.pns.bean.ConfigMasterBean;
import com.itl.pns.bean.CountryStateCityBean;
import com.itl.pns.bean.LocationsBean;
import com.itl.pns.bean.RequestParamBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.constant.ApplicationConstants;
import com.itl.pns.entity.ActivitySettingMasterEntity;
import com.itl.pns.entity.LocationsEntity;
import com.itl.pns.service.LocationService;
import com.itl.pns.util.AdminWorkFlowReqUtility;

@RestController
@RequestMapping("locations")
public class LocationsController {

	static Logger LOGGER = Logger.getLogger(LocationsController.class);
	@Autowired
	LocationService locationService;

	@Autowired
	AdminWorkFlowReqUtility adminWorkFlowReqUtility;

	/**
	 * getLocation Details
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getLocations", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getLocations() {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
		List<LocationsEntity> getLocations = locationService.getLocations();
		
			if (!ObjectUtils.isEmpty(getLocations)) {
				res.setResponseCode("200");
				res.setResult(getLocations);
			} else {
				res.setResponseCode("202");
			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	/**
	 * getLocation types
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getLocationsTypes", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getLocationsTypes() {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
		List<LocationsEntity> getLocations = locationService.getLocationsTypes();
		
			if (!ObjectUtils.isEmpty(getLocations)) {
				res.setResponseCode("200");
				res.setResult(getLocations);
			} else {
				res.setResponseCode("202");
			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	/**
	 * gets Location by Id
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/getLocationById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getLocationById(@RequestBody RequestParamBean requestBean) {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
		List<LocationsEntity> location = locationService.getLocationById(Integer.parseInt(requestBean.getId1()));
		
			if (!ObjectUtils.isEmpty(location)) {
				res.setResponseCode("200");
				res.setResult(location);
			} else {
				res.setResponseCode("202");
			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	/**
	 * Adds new location details.
	 * 
	 * @param locations
	 * @return
	 */
	@RequestMapping(value = "/saveLocationDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> saveLocationDetails(@RequestBody LocationsEntity locations) {
		int userStatus = locations.getStatusId().intValue();
		boolean isDataRefresh = false;
		ResponseMessageBean res = new ResponseMessageBean();
		List<CountryStateCityBean> obj = adminWorkFlowReqUtility.getCountryStateCityNameById(
				locations.getCountryId().toBigInteger(), locations.getStateId().toBigInteger(),
				locations.getCityId().toBigInteger());
		locations.setCountryName(obj.get(0).getCountryName());
		locations.setStateName(obj.get(0).getStateName());
		locations.setCityName(obj.get(0).getCityName());
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(locations.getRole_ID().intValue());
		locations.setRoleName(roleName);
		locations.setStatusName(adminWorkFlowReqUtility.getStatusNameByStatusId(locations.getStatusId().intValue()));
		locations.setUpdatedon(new Date());
		locations.setAppName(adminWorkFlowReqUtility.getAppNameByAppId(locations.getAppId().intValue()));
		locations.setCreatedByName(
				adminWorkFlowReqUtility.getCreatedByNameByCreatedId(locations.getCreatedby().intValue()));
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(locations.getActivityName());
		try {
			locationService.saveLocationDetails(locations);

			isDataRefresh = adminWorkFlowReqUtility.refreshCacheData("LocationsReader");
			res.setResponseCode("200");
			locationService.saveLocationToAdminWorkFlow(locations, userStatus);
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

				res.setResponseMessage("Request Submitted To Admin Checker For Approval");
			} else {
				res.setResponseMessage("Location Has Been Added Successfully");
			}

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			res.setResponseCode("500");
			res.setResponseMessage("Error Occured While Updating The Record");
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	/**
	 * Update Location details by Id
	 * 
	 * @param locations
	 * @return
	 */
	@RequestMapping(value = "/updateLocationDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateLocationDetails(@RequestBody LocationsEntity locations) {
		ResponseMessageBean res = new ResponseMessageBean();
		boolean isDataRefresh = false;
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(locations.getRole_ID().intValue());
		locations.setStatusName(adminWorkFlowReqUtility.getStatusNameByStatusId(locations.getStatusId().intValue()));
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(locations.getActivityName());
		List<CountryStateCityBean> obj = adminWorkFlowReqUtility.getCountryStateCityNameById(
				locations.getCountryId().toBigInteger(), locations.getStateId().toBigInteger(),
				locations.getCityId().toBigInteger());
		locations.setUpdatedon(new Date());
		locations.setCountryName(obj.get(0).getCountryName());
		locations.setStateName(obj.get(0).getStateName());
		locations.setCityName(obj.get(0).getCityName());
		locations.setUpdatedon(new Date());
		locations.setRoleName(roleName);
		locations.setAppName(adminWorkFlowReqUtility.getAppNameByAppId(locations.getAppId().intValue()));
		locations.setCreatedByName(
				adminWorkFlowReqUtility.getCreatedByNameByCreatedId(locations.getCreatedby().intValue()));
		try {
			locationService.updateLocationDetails(locations);
			isDataRefresh = adminWorkFlowReqUtility.refreshCacheData("LocationsReader");
			res.setResponseCode("200");

			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				res.setResponseMessage("Request Submitted To Admin Checker For Approval");
			} else {
				res.setResponseMessage("Location Has Been Updated Successfully");
			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			res.setResponseCode("500");
			res.setResponseMessage("Error Occured While Updating The Record");
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	/**
	 * Get all state names
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getStateNames", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getStateNames(@RequestBody RequestParamBean requestBean) {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
		List<LocationsBean> getState = locationService.getStateNames(Integer.parseInt(requestBean.getId1()));
		
			if (!ObjectUtils.isEmpty(getState)) {
				res.setResponseCode("200");
				res.setResult(getState);
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No States Mapped");
			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	/**
	 * get all city names
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getCityNames", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getCityNames(@RequestBody RequestParamBean requestBean) {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
		List<LocationsBean> getCity = locationService.getCityNames(Integer.parseInt(requestBean.getId1()),
				Integer.parseInt(requestBean.getId2()));
		
			if (!ObjectUtils.isEmpty(getCity)) {
				res.setResponseCode("200");
				res.setResult(getCity);
				res.setResponseMessage("Success");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Cities Mapped");
			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	/**
	 * get all Country names
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getCountryNames", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getCountryNames() {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
		List<LocationsBean> getCountry = locationService.getCountryNames();
		
			if (!ObjectUtils.isEmpty(getCountry)) {
				res.setResponseCode("200");
				res.setResult(getCountry);
			} else {
				res.setResponseCode("202");
			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

}
