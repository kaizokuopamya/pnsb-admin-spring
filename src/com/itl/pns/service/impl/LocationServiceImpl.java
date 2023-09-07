package com.itl.pns.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.sql.rowset.serial.SerialException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itl.pns.bean.LocationsBean;
import com.itl.pns.constant.ApplicationConstants;
import com.itl.pns.dao.LocationDAO;
import com.itl.pns.entity.ActivitySettingMasterEntity;
import com.itl.pns.entity.AdminWorkFlowRequestEntity;
import com.itl.pns.entity.LocationsEntity;
import com.itl.pns.repository.ConfigRepository;
import com.itl.pns.repository.LocationRepository;
import com.itl.pns.service.LocationService;
import com.itl.pns.util.AdminWorkFlowReqUtility;

@Service
@Qualifier("LocationService")
public class LocationServiceImpl implements LocationService {

	static Logger LOGGER = Logger.getLogger(LocationServiceImpl.class);

	@Autowired
	LocationDAO locationdao;

	@Autowired
	AdminWorkFlowReqUtility adminWorkFlowReqUtility;

	@Autowired
	LocationRepository locationRepository;

	@Autowired
	ConfigRepository configRepository;

	@Override
	public List<LocationsEntity> getLocations() {
		List<LocationsEntity> list = locationdao.getLocations();

		return list;

	}

	@Override
	public List<LocationsEntity> getLocationsTypes() {
		List<LocationsEntity> list = locationdao.getLocationsTypes();

		return list;

	}

	@Override
	public List<LocationsEntity> getLocationById(int id) {
		return locationdao.getLocationById(id);
	}

	@Override
	public List<LocationsBean> getStateNames(int countryid) {
		return locationdao.getStateNames(countryid);
	}

	@Override
	public List<LocationsBean> getCityNames(int countryid, int stateid) {
		return locationdao.getCityNames(countryid, stateid);
	}

	@Override
	public List<LocationsBean> getCountryNames() {
		return locationdao.getCountryNames();
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	public void updateLocationDetails(LocationsEntity locations) {

		int userStatus = locations.getStatusId().intValue();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(locations.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(locations.getActivityName());

		try {
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getMaker())))  {
				locations.setStatusId(BigDecimal.valueOf(statusId)); // 97-
																		// ADMIN_CHECKER_PENDIN
			}

			LocationsEntity loc = locationRepository.getOne(locations.getId());
			try {
				loc.setAddressClob(new javax.sql.rowset.serial.SerialClob(locations.getAddress().toCharArray()));
			} catch (SerialException e) {
			} catch (SQLException e) {
				e.printStackTrace();
			}
			loc.setEmailAddress(locations.getEmailAddress());
			loc.setPhoneNumber(locations.getPhoneNumber());
			loc.setDisplayName(locations.getDisplayName());
			loc.setLocationTypeId(locations.getLocationTypeId());
			loc.setCityId(locations.getCityId());
			loc.setStateId(locations.getStateId());
			loc.setCountryId(locations.getCountryId());
			loc.setBranchCode(locations.getBranchCode());
			loc.setLanguageCode(locations.getLanguageCode());
			loc.setPostCode(locations.getPostCode());
			loc.setLatitude(locations.getLatitude());
			loc.setLongitude(locations.getLongitude());
			loc.setStatusId(locations.getStatusId());
			loc.setUpdatedon(new Date());
			loc.setUpdatedby(locations.getUpdatedby());
			locationRepository.save(loc);
			locations.setAddressClob(null);
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getMaker())))  {

				ObjectMapper mapper = new ObjectMapper();

				List<AdminWorkFlowRequestEntity> AdminDataList = adminWorkFlowReqUtility
						.getAdminWorkFlowDataByActivityRefNo(locations.getId().intValue(), locations.getSubMenu_ID());

				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();

				adminData.setCreatedOn(loc.getCreatedon());
				adminData.setCreatedByUserId(locations.getUser_ID());
				adminData.setCreatedByRoleId(locations.getRole_ID());
				adminData.setPageId(locations.getSubMenu_ID()); // set submenuId
																// as pageid
				adminData.setCreatedBy(loc.getCreatedby());
				adminData.setContent(mapper.writeValueAsString(locations));
				adminData.setActivityId(locations.getSubMenu_ID()); // set
																	// submenuId
																	// as
																	// activityid
				adminData.setStatusId(BigDecimal.valueOf(statusId)); // 97-
																		// ADMIN_CHECKER_PENDIN
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6)); // 6-
																		// checker
																		// roleid
				adminData.setRemark(locations.getRemark());
				adminData.setActivityName(locations.getActivityName());
				adminData.setActivityRefNo(locations.getId());
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminData.setTableName("LOCATIONS");

				if (ObjectUtils.isEmpty(AdminDataList)) {
					adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);
				} else if (!ObjectUtils.isEmpty(AdminDataList)) {
					adminData.setId(BigDecimal.valueOf(AdminDataList.get(0).getId().intValue()));
					adminWorkFlowReqUtility.updateAdminWorkFlowRequest(adminData);

				}

				// Save data to admin work flow history
				adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(locations.getSubMenu_ID(),
					locations.getId(),loc.getCreatedby(), locations.getRemark(),
						locations.getRole_ID(),mapper.writeValueAsString(locations));

			} else {
				// if record is present in admin work flow then update status
				adminWorkFlowReqUtility.getCheckerDataByctivityRefId(locations.getId().toBigInteger(), BigInteger.valueOf(userStatus),
						locations.getSubMenu_ID());
			}

		} catch (Exception ex) {
			LOGGER.info("Exception:", ex);
		}

	}

	/**
	 * Saves location details
	 */
	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	public boolean saveLocationDetails(LocationsEntity locations) {
		
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(locations.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(locations.getActivityName());

		try {
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getMaker())))  {
				locations.setStatusId(BigDecimal.valueOf(statusId)); // 97-
																		// ADMIN_CHECKER_PENDIN
			}
			try {
				locations.setAddressClob(new javax.sql.rowset.serial.SerialClob(locations.getAddress().toCharArray()));
			} catch (SerialException e) {
			} catch (SQLException e) {
				e.printStackTrace();
			}
			locations.setCreatedon(new Date());
			locationRepository.save(locations);

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
		return true;
	}

	@Override
	public boolean saveLocationToAdminWorkFlow(LocationsEntity locations, int userStatus) {
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(locations.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(locations.getActivityName());

		try {

			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getMaker())))  {

				List<LocationsEntity> list = getLocations();
				ObjectMapper mapper = new ObjectMapper();

				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();

				adminData.setCreatedByUserId(locations.getUser_ID());
				adminData.setCreatedByRoleId(locations.getRole_ID());
				adminData.setPageId(locations.getSubMenu_ID()); // set submenuId
																// as pageid
				adminData.setCreatedBy(locations.getCreatedby());
				adminData.setContent(mapper.writeValueAsString(locations));
				adminData.setActivityId(locations.getSubMenu_ID()); // set
																	// submenuId
																	// as
																	// activityid
				adminData.setStatusId(BigDecimal.valueOf(statusId)); // 97-
																		// ADMIN_CHECKER_PENDIN
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6)); // 6-
																		// checker
																		// roleid
				adminData.setRemark(locations.getRemark());
				adminData.setActivityName(locations.getActivityName());
				adminData.setActivityRefNo(list.get(0).getId());
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminData.setTableName("LOCATIONS");
				adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);

				// Save data to admin work flow history
				adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(locations.getSubMenu_ID(),
						list.get(0).getId(), locations.getCreatedby(),
						locations.getRemark(), locations.getRole_ID(),mapper.writeValueAsString(locations));
			}

		}

		catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
		return true;
	}

}
