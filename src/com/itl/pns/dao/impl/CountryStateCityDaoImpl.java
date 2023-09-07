package com.itl.pns.dao.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.constant.ApplicationConstants;
import com.itl.pns.dao.AdminWorkFlowDao;
import com.itl.pns.dao.CountryStateCityDao;
import com.itl.pns.entity.ActivitySettingMasterEntity;
import com.itl.pns.entity.AdminWorkFlowRequestEntity;
import com.itl.pns.entity.CitiesMasterEntity;
import com.itl.pns.entity.CountryMasterEntity;
import com.itl.pns.entity.StateMasterEntity;
import com.itl.pns.util.AdminWorkFlowReqUtility;

@Repository
@Transactional
public class CountryStateCityDaoImpl implements CountryStateCityDao {


	static Logger LOGGER = Logger.getLogger(CountryStateCityDaoImpl.class);

	@Autowired
	AdminWorkFlowDao adminWorkFlowDao;

	@Autowired
	AdminWorkFlowReqUtility adminWorkFlowReqUtility;

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public List<CountryMasterEntity> getAllCountryDetails() {
		List<CountryMasterEntity> list = null;

		try {
			String sqlQuerry = "select cm.id as id, cm.appId as appId, cm.name as name, cm.createdBy as createdBy, cm.createdOn as createdOn, cm.statusId as statusId, "
					+ " s.name as statusName , a.shortname as appName , um.userid as createdByName from "
					+ "	COUNTRYMASTER cm inner join statusmaster s on  s.id = cm.statusid inner join appmaster a on a.id=cm.appid "
					+ " inner join user_master um on um.id= cm.createdBy order by cm.id desc";

			list = getSession().createSQLQuery(sqlQuerry)
					.addScalar("id").addScalar("appId").addScalar("name").addScalar("createdBy").addScalar("createdOn").addScalar("statusId")
					.addScalar("statusName").addScalar("appName").addScalar("createdByName")		
					.setResultTransformer(Transformers.aliasToBean(CountryMasterEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public List<CountryMasterEntity> getCountryById(CountryMasterEntity countryData) {
		List<CountryMasterEntity> list = null;

		try {

			String sqlQuerry = "select cm.id as id, cm.appId as appId, cm.name as name, cm.createdBy as createdBy, cm.createdOn as createdOn, cm.statusId as statusId, "
					+ " s.name as statusName , a.shortname as appName , um.userid as createdByName, aw.remark,aw.userAction from "
					+ "	COUNTRYMASTER cm inner join statusmaster s on  s.id = cm.statusid inner join appmaster a on a.id=cm.appid "
					+ " inner join user_master um on um.id= cm.createdBy left join ADMINWORKFLOWREQUEST aw on aw.activityrefno=:id and aw.tablename='COUNTRYMASTER' where cm.id=:id";
			
			list = getSession().createSQLQuery(sqlQuerry)
					.addScalar("id").addScalar("appId").addScalar("name").addScalar("createdBy").addScalar("createdOn").addScalar("statusId")
					.addScalar("statusName").addScalar("appName").addScalar("createdByName").addScalar("remark").addScalar("userAction")		
					.setParameter("id", countryData.getId())
					.setResultTransformer(Transformers.aliasToBean(CountryMasterEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public boolean addCountryDetails(CountryMasterEntity countryData) {
		Session session = sessionFactory.getCurrentSession();
		int userStatus = countryData.getStatusId().intValue();
		BigDecimal countryId = new BigDecimal(0);
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(countryData.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(countryData.getActivityName());

		try {
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				countryData.setStatusId(BigDecimal.valueOf(statusId)); // 97-
																					// ADMIN_CHECKER_PENDIN
			}
			countryData.setCreatedOn(new Date());
			countryId =(BigDecimal)session.save(countryData);

			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
			//	List<CountryMasterEntity> list = getAllCountryDetails();
				ObjectMapper mapper = new ObjectMapper();

				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
				adminData.setCreatedByUserId(countryData.getUser_ID());
				adminData.setCreatedByRoleId(countryData.getRole_ID());
				adminData.setPageId(countryData.getSubMenu_ID()); // set
																							// submenuId
																							// as
																							// pageid
				adminData.setCreatedBy(countryData.getCreatedBy());
				adminData.setContent(mapper.writeValueAsString(countryData));
				adminData.setActivityId(countryData.getSubMenu_ID()); // set
																								// submenuId
																								// as
																								// activityid
				adminData.setStatusId(BigDecimal.valueOf(statusId)); // 97-
																		// ADMIN_CHECKER_PENDIN
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6)); // 6-
																		// checker
																		// roleid
				adminData.setRemark(countryData.getRemark());
				adminData.setActivityName(countryData.getActivityName());
				adminData.setActivityRefNo(countryId);
				adminData.setTableName("COUNTRYMASTER");
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);

				// Save data to admin work flow history
				adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(countryData.getSubMenu_ID(),
						countryId, countryData.getCreatedBy(),
						countryData.getRemark(), countryData.getRole_ID(),
						mapper.writeValueAsString(countryData));
			}

			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}

	@Override
	public boolean updateCountryDetails(CountryMasterEntity countryData) {
		Session session = sessionFactory.getCurrentSession();
		int userStatus = countryData.getStatusId().intValue();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(countryData.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(countryData.getActivityName());

		try {
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				countryData.setStatusId(BigDecimal.valueOf(statusId));
			}

			countryData.setCreatedOn(new Date());
			session.update(countryData);

			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

				ObjectMapper mapper = new ObjectMapper();

				List<AdminWorkFlowRequestEntity> AdminDataList = adminWorkFlowReqUtility
						.getAdminWorkFlowDataByActivityRefNo(countryData.getId().intValue(),
								countryData.getSubMenu_ID());

				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
				adminData.setCreatedOn(countryData.getCreatedOn());
				adminData.setCreatedByUserId(countryData.getUser_ID());
				adminData.setCreatedByRoleId(countryData.getRole_ID());
				adminData.setPageId(countryData.getSubMenu_ID()); // set
																							// submenuId
																							// as
																							// pageid
				adminData.setCreatedBy(countryData.getCreatedBy());
				adminData.setContent(mapper.writeValueAsString(countryData));
				adminData.setActivityId(countryData.getSubMenu_ID()); // set
																								// submenuId
																								// as
																								// activityid
				adminData.setStatusId(BigDecimal.valueOf(statusId)); // 97-
																		// ADMIN_CHECKER_PENDIN
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6)); // 6-
																		// checker
																		// roleid
				adminData.setRemark(countryData.getRemark());
				adminData.setActivityName(countryData.getActivityName());
				adminData.setActivityRefNo(countryData.getId());
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminData.setTableName("COUNTRYMASTER");

				if (ObjectUtils.isEmpty(AdminDataList)) {
					adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);
				} else if (!ObjectUtils.isEmpty(AdminDataList)) {
					adminData.setId(BigDecimal.valueOf(AdminDataList.get(0).getId().intValue()));
					adminWorkFlowReqUtility.updateAdminWorkFlowRequest(adminData);
				}
					// Save data to admin work flow history
					adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(countryData.getSubMenu_ID(),
							countryData.getId(),
						countryData.getCreatedBy(), countryData.getRemark(),
							countryData.getRole_ID(), mapper.writeValueAsString(countryData));
				

			} else {

				// if record is present in admin work flow then update status
				adminWorkFlowReqUtility.getCheckerDataByctivityRefId(countryData.getId().toBigInteger(),
						BigInteger.valueOf(userStatus), countryData.getSubMenu_ID());
			}
			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}
	

	@Override
	public List<StateMasterEntity> getAllStateDetails() {
		List<StateMasterEntity> list = null;

		try {
			String sqlQuerry = "select sm.id as id, sm.countryId as countryId, sm.shortName as shortName,sm.createdBy as createdBy,"
					+ " sm.createdOn as createdOn,sm.statusId as statusId,s.name as statusName , um.userid as createdByName, cm.name as countryName "
					+ "	 from STATEMASTER sm inner join statusmaster s on s.id=sm.statusId "
					+ "  inner join user_master um on um.id= sm.createdBy "
					+ " inner join COUNTRYMASTER cm on cm.id=sm.countryId "
					+ "  order by sm.id desc";

			list = getSession().createSQLQuery(sqlQuerry)
					.addScalar("id").addScalar("countryId").addScalar("shortName").addScalar("createdBy").addScalar("createdOn").addScalar("statusId")
					.addScalar("statusName").addScalar("countryName").addScalar("createdByName")		
					.setResultTransformer(Transformers.aliasToBean(StateMasterEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public List<StateMasterEntity> getStateById(StateMasterEntity stateData) {
		List<StateMasterEntity> list = null;

		try {
			
			String sqlQuerry = "select sm.id as id, sm.countryId as countryId, sm.shortName as shortName,sm.createdBy as createdBy,"
					+ " sm.createdOn as createdOn,sm.statusId as statusId,s.name as statusName , um.userid as createdByName,cm.name as countryName,aw.remark,aw.userAction "
					+ "	 from STATEMASTER sm inner join statusmaster s on s.id=sm.statusId "
					+ "  inner join user_master um on um.id= sm.createdBy "
					+ " inner join COUNTRYMASTER cm on cm.id=sm.countryId left join ADMINWORKFLOWREQUEST aw on aw.activityrefno=sm.id and aw.tablename='STATEMASTER' "
					+ "  where sm.id=:id ";
			

			list = getSession().createSQLQuery(sqlQuerry)
					.addScalar("id").addScalar("countryId").addScalar("shortName").addScalar("createdBy").addScalar("createdOn").addScalar("statusId")
					.addScalar("statusName").addScalar("countryName").addScalar("createdByName").addScalar("remark").addScalar("userAction")			
					.setParameter("id",stateData.getId())
					.setResultTransformer(Transformers.aliasToBean(StateMasterEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public boolean addStateDetails(StateMasterEntity stateData) {
		Session session = sessionFactory.getCurrentSession();
		int userStatus = stateData.getStatusId().intValue();
		BigDecimal stateId = new BigDecimal(0);
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(stateData.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(stateData.getActivityName());

		try {
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				stateData.setStatusId(BigDecimal.valueOf(statusId)); // 97-
																					// ADMIN_CHECKER_PENDIN
			}
			stateData.setCreatedOn(new Date());
			stateId=(BigDecimal)session.save(stateData);

			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
			//	List<StateMasterEntity> list = getAllStateDetails();
				ObjectMapper mapper = new ObjectMapper();

				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
				adminData.setCreatedByUserId(stateData.getUser_ID());
				adminData.setCreatedByRoleId(stateData.getRole_ID());
				adminData.setPageId(stateData.getSubMenu_ID()); // set
																							// submenuId
																							// as
																							// pageid
				adminData.setCreatedBy(stateData.getCreatedBy());
				adminData.setContent(mapper.writeValueAsString(stateData));
				adminData.setActivityId(stateData.getSubMenu_ID()); // set
																								// submenuId
																								// as
																								// activityid
				adminData.setStatusId(BigDecimal.valueOf(statusId)); // 97-
																		// ADMIN_CHECKER_PENDIN
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6)); // 6-
																		// checker
																		// roleid
				adminData.setRemark(stateData.getRemark());
				adminData.setActivityName(stateData.getActivityName());
				adminData.setActivityRefNo(stateId);
				adminData.setTableName("STATEMASTER");
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);

				// Save data to admin work flow history
				adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(stateData.getSubMenu_ID(),
						stateId,stateData.getCreatedBy(),
						stateData.getRemark(), stateData.getRole_ID(),
						mapper.writeValueAsString(stateData));
			}

			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}

	@Override
	public boolean updateStateDetails(StateMasterEntity stateData) {
		Session session = sessionFactory.getCurrentSession();
		int userStatus = stateData.getStatusId().intValue();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(stateData.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(stateData.getActivityName());

		try {
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				stateData.setStatusId(BigDecimal.valueOf(statusId));
			}

			stateData.setCreatedOn(new Date());
			session.update(stateData);

			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

				ObjectMapper mapper = new ObjectMapper();

				List<AdminWorkFlowRequestEntity> AdminDataList = adminWorkFlowReqUtility
						.getAdminWorkFlowDataByActivityRefNo(stateData.getId().intValue(),
								stateData.getSubMenu_ID());

				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
				adminData.setCreatedOn(stateData.getCreatedOn());
				adminData.setCreatedByUserId(stateData.getUser_ID());
				adminData.setCreatedByRoleId(stateData.getRole_ID());
				adminData.setPageId(stateData.getSubMenu_ID()); // set
																							// submenuId
																							// as
																							// pageid
				adminData.setCreatedBy(stateData.getCreatedBy());
				adminData.setContent(mapper.writeValueAsString(stateData));
				adminData.setActivityId(stateData.getSubMenu_ID()); // set
																								// submenuId
																								// as
																								// activityid
				adminData.setStatusId(BigDecimal.valueOf(statusId)); // 97-
																		// ADMIN_CHECKER_PENDIN
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6)); // 6-
																		// checker
																		// roleid
				adminData.setRemark(stateData.getRemark());
				adminData.setActivityName(stateData.getActivityName());
				adminData.setActivityRefNo(stateData.getId());
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminData.setTableName("STATEMASTER");

				if (ObjectUtils.isEmpty(AdminDataList)) {
					adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);
				} else if (!ObjectUtils.isEmpty(AdminDataList)) {
					adminData.setId(BigDecimal.valueOf(AdminDataList.get(0).getId().intValue()));
					adminWorkFlowReqUtility.updateAdminWorkFlowRequest(adminData);
				}
					// Save data to admin work flow history
					adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(stateData.getSubMenu_ID(),
							stateData.getId(),
							stateData.getCreatedBy(), stateData.getRemark(),
							stateData.getRole_ID(), mapper.writeValueAsString(stateData));
				

			} else {

				// if record is present in admin work flow then update status
				adminWorkFlowReqUtility.getCheckerDataByctivityRefId(stateData.getId().toBigInteger(),
						BigInteger.valueOf(userStatus), stateData.getSubMenu_ID());
			}
			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}

	@Override
	public List<CitiesMasterEntity> getAllCitiesDetails() {
		List<CitiesMasterEntity> list = null;

		try {
			String sqlQuerry = " select cm.id as id, cm.countryId as countryId , cm.stateId as stateId, cm.cityName as cityName, cm.createdBy as createdBy, cm.createdOn as createdOn,"
					+ "cm.statusId as statusId, s.name as statusName, um.userid as createdByName,cn.name as countryName, sm.SHORTNAME as stateName  from CITIESMASTER cm inner join statusmaster s on s.id=cm.statusId "
					+ " inner join user_master um on um.id=cm.createdby "
					+ " inner join COUNTRYMASTER cn on cn.id=cm.countryId inner join statemaster sm on sm.id=cm.stateId order by id desc";

			list = getSession().createSQLQuery(sqlQuerry)
					.addScalar("id").addScalar("countryId").addScalar("stateId").addScalar("cityName").addScalar("createdBy").addScalar("createdOn").addScalar("statusId")
					.addScalar("statusName").addScalar("countryName").addScalar("createdByName").addScalar("stateName")	
					.setResultTransformer(Transformers.aliasToBean(CitiesMasterEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public List<CitiesMasterEntity> getCityById(CitiesMasterEntity cityData) {
		List<CitiesMasterEntity> list = null;

		try {
			
			String sqlQuerry = " select cm.id as id, cm.countryId as countryId , cm.stateId as stateId, cm.cityName as cityName, cm.createdBy as createdBy, cm.createdOn as createdOn,"
					+ "cm.statusId as statusId, s.name as statusName, um.userid as createdByName,cn.name as countryName, sm.SHORTNAME as stateName,aw.remark,aw.userAction from CITIESMASTER cm inner join statusmaster s on s.id=cm.statusId "
					+ " inner join user_master um on um.id=cm.createdby left join ADMINWORKFLOWREQUEST aw on aw.activityrefno=cm.id and aw.tablename='CITIESMASTER' "
					+ " inner join COUNTRYMASTER cn on cn.id=cm.countryId inner join statemaster sm on sm.id=cm.stateId where cm.id=:id";
			
			list = getSession().createSQLQuery(sqlQuerry)
					.addScalar("id").addScalar("countryId").addScalar("stateId").addScalar("cityName").addScalar("createdBy").addScalar("createdOn").addScalar("statusId")
					.addScalar("statusName").addScalar("countryName").addScalar("createdByName").addScalar("stateName")	.addScalar("remark").addScalar("userAction")		
					.setParameter("id",cityData.getId())
					.setResultTransformer(Transformers.aliasToBean(CitiesMasterEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public boolean addCityDetails(CitiesMasterEntity cityData) {
		Session session = sessionFactory.getCurrentSession();
		int userStatus = cityData.getStatusId().intValue();
		BigDecimal cityId = new BigDecimal(0);
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(cityData.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(cityData.getActivityName());

		try {
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				cityData.setStatusId(BigDecimal.valueOf(statusId)); // 97-
																					// ADMIN_CHECKER_PENDIN
			}
			cityData.setCreatedOn(new Date());
			cityId =(BigDecimal)session.save(cityData);

			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
			//	List<CitiesMasterEntity> list = getAllCitiesDetails();
				ObjectMapper mapper = new ObjectMapper();

				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
				adminData.setCreatedByUserId(cityData.getUser_ID());
				adminData.setCreatedByRoleId(cityData.getRole_ID());
				adminData.setPageId(cityData.getSubMenu_ID()); // set
																							// submenuId
																							// as
																							// pageid
				adminData.setCreatedBy(cityData.getCreatedBy());
				adminData.setContent(mapper.writeValueAsString(cityData));
				adminData.setActivityId(cityData.getSubMenu_ID()); // set
																								// submenuId
																								// as
																								// activityid
				adminData.setStatusId(BigDecimal.valueOf(statusId)); // 97-
																		// ADMIN_CHECKER_PENDIN
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6)); // 6-
																		// checker
																		// roleid
				adminData.setRemark(cityData.getRemark());
				adminData.setActivityName(cityData.getActivityName());
				adminData.setActivityRefNo(cityId);
				adminData.setTableName("CITIESMASTER");
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);

				// Save data to admin work flow history
				adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(cityData.getSubMenu_ID(),
						cityId,cityData.getCreatedBy(),
						cityData.getRemark(), cityData.getRole_ID(),
						mapper.writeValueAsString(cityData));
			}

			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
		
	}

	@Override
	public boolean updateCityDetails(CitiesMasterEntity cityData) {
		Session session = sessionFactory.getCurrentSession();
		int userStatus = cityData.getStatusId().intValue();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(cityData.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(cityData.getActivityName());

		try {
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				cityData.setStatusId(BigDecimal.valueOf(statusId));
			}

			cityData.setCreatedOn(new Date());
			session.update(cityData);

			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

				ObjectMapper mapper = new ObjectMapper();

				List<AdminWorkFlowRequestEntity> AdminDataList = adminWorkFlowReqUtility
						.getAdminWorkFlowDataByActivityRefNo(cityData.getId().intValue(),
								cityData.getSubMenu_ID());

				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
				adminData.setCreatedOn(cityData.getCreatedOn());
				adminData.setCreatedByUserId(cityData.getUser_ID());
				adminData.setCreatedByRoleId(cityData.getRole_ID());
				adminData.setPageId(cityData.getSubMenu_ID()); // set
																							// submenuId
																							// as
																							// pageid
				adminData.setCreatedBy(cityData.getCreatedBy());
				adminData.setContent(mapper.writeValueAsString(cityData));
				adminData.setActivityId(cityData.getSubMenu_ID()); // set
																								// submenuId
																								// as
																								// activityid
				adminData.setStatusId(BigDecimal.valueOf(statusId)); // 97-
																		// ADMIN_CHECKER_PENDIN
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6)); // 6-
																		// checker
																		// roleid
				adminData.setRemark(cityData.getRemark());
				adminData.setActivityName(cityData.getActivityName());
				adminData.setActivityRefNo(cityData.getId());
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminData.setTableName("CITIESMASTER");

				if (ObjectUtils.isEmpty(AdminDataList)) {
					adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);
				} else if (!ObjectUtils.isEmpty(AdminDataList)) {
					adminData.setId(BigDecimal.valueOf(AdminDataList.get(0).getId().intValue()));
					adminWorkFlowReqUtility.updateAdminWorkFlowRequest(adminData);
				}
					// Save data to admin work flow history
					adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(cityData.getSubMenu_ID(),
							cityData.getId(),
							cityData.getCreatedBy(), cityData.getRemark(),
							cityData.getRole_ID(), mapper.writeValueAsString(cityData));
				

			} else {

				// if record is present in admin work flow then update status
				adminWorkFlowReqUtility.getCheckerDataByctivityRefId(cityData.getId().toBigInteger(),
						BigInteger.valueOf(userStatus), cityData.getSubMenu_ID());
			}
			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	
	}

	@Override
	public ResponseMessageBean isCountryExists(CountryMasterEntity countryData) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		try {
			String sqlNameExist = " SELECT count(*) FROM COUNTRYMASTER WHERE Lower(NAME) =:countryName ";
		
			List nameExit = getSession().createSQLQuery(sqlNameExist)
					.setParameter("countryName", countryData.getName().toLowerCase()).list();
			
			
			if (!(nameExit.get(0).toString().equalsIgnoreCase("0"))) {
				rmb.setResponseCode("500");
				rmb.setResponseMessage("Country Is Already Exist ");
			} else {
				rmb.setResponseCode("200");
				rmb.setResponseMessage("Both Not Exist");
			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return rmb;
	}

	@Override
	public ResponseMessageBean isUpdateCountryExists(CountryMasterEntity countryData) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		try {
			String sqlNameExist = " SELECT count(*) FROM COUNTRYMASTER WHERE Lower(NAME) =:countryName AND id !=:id ";
		
			List nameExit = getSession().createSQLQuery(sqlNameExist).setParameter("id", countryData.getId())
					.setParameter("countryName", countryData.getName().toLowerCase()).list();
			
			
			if (!(nameExit.get(0).toString().equalsIgnoreCase("0"))) {
				rmb.setResponseCode("500");
				rmb.setResponseMessage("Country Is Already Exist ");
			} else {
				rmb.setResponseCode("200");
				rmb.setResponseMessage("Both Not Exist");
			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return rmb;
	}

	@Override
	public ResponseMessageBean isStateExists(StateMasterEntity stateData) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		try {
			String sqlNameExist = " SELECT count(*) FROM STATEMASTER WHERE Lower(SHORTNAME) =:stateName ";
		
			List nameExit = getSession().createSQLQuery(sqlNameExist)
					.setParameter("stateName", stateData.getShortName().toLowerCase()).list();
			
			
			if (!(nameExit.get(0).toString().equalsIgnoreCase("0"))) {
				rmb.setResponseCode("500");
				rmb.setResponseMessage("State Is Already Exist ");
			} else {
				rmb.setResponseCode("200");
				rmb.setResponseMessage("Both Not Exist");
			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return rmb;
	}

	@Override
	public ResponseMessageBean isUpdateStateExists(StateMasterEntity stateData) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		try {
			String sqlNameExist = " SELECT count(*) FROM STATEMASTER WHERE Lower(SHORTNAME) =:stateName AND id !=:id ";
		
			List nameExit = getSession().createSQLQuery(sqlNameExist).setParameter("id", stateData.getId())
					.setParameter("stateName", stateData.getShortName().toLowerCase()).list();
			
			
			if (!(nameExit.get(0).toString().equalsIgnoreCase("0"))) {
				rmb.setResponseCode("500");
				rmb.setResponseMessage("State Is Already Exist ");
			} else {
				rmb.setResponseCode("200");
				rmb.setResponseMessage("Both Not Exist");
			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return rmb;
	}

	@Override
	public ResponseMessageBean isCityExists(CitiesMasterEntity cityData) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		try {
			String sqlNameExist = " SELECT count(*) FROM CITIESMASTER WHERE Lower(CITYNAME) =:cityName ";
		
			List nameExit = getSession().createSQLQuery(sqlNameExist)
					.setParameter("cityName", cityData.getCityName().toLowerCase()).list();
			
			
			if (!(nameExit.get(0).toString().equalsIgnoreCase("0"))) {
				rmb.setResponseCode("500");
				rmb.setResponseMessage("City Is Already Exist ");
			} else {
				rmb.setResponseCode("200");
				rmb.setResponseMessage("Both Not Exist");
			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return rmb;
	}

	@Override
	public ResponseMessageBean isUpdateCityExists(CitiesMasterEntity cityData) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		try {
			String sqlNameExist = " SELECT count(*) FROM CITIESMASTER WHERE Lower(CITYNAME) =:cityName AND id !=:id ";
		
			List nameExit = getSession().createSQLQuery(sqlNameExist).setParameter("id", cityData.getId())
					.setParameter("cityName", cityData.getCityName().toLowerCase()).list();
			
			
			if (!(nameExit.get(0).toString().equalsIgnoreCase("0"))) {
				rmb.setResponseCode("500");
				rmb.setResponseMessage("City Is Already Exist ");
			} else {
				rmb.setResponseCode("200");
				rmb.setResponseMessage("Both Not Exist");
			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return rmb;
	}
	


	/*@Override
	public ResponseMessageBean isInvestementProductExist(InvestementProductsEntity countryData) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		try {
			String sqlNameExist = " SELECT count(*) FROM INVESTMENT_PRODUCTS WHERE Lower(PRODUCTNAME) =:productName and statusid=3";
			String sqlLinkNameExist = " SELECT count(*) FROM INVESTMENT_PRODUCTS WHERE Lower(PRODUCTLINK) =:productLink and statusid=3";

			List nameExit = getSession().createSQLQuery(sqlNameExist)
					.setParameter("productName", countryData.getProductName().toLowerCase()).list();
			
			List LinkExit = getSession().createSQLQuery(sqlLinkNameExist)
					.setParameter("productLink", countryData.getProductLink().toLowerCase()).list();
			
			if (!(nameExit.get(0).toString().equalsIgnoreCase("0"))) {
				rmb.setResponseCode("500");
				rmb.setResponseMessage("Prooduct Name Is Already Exist ");
			} else if (!(LinkExit.get(0).toString().equalsIgnoreCase("0"))) {
				rmb.setResponseCode("500");
				rmb.setResponseMessage("Prooduct Link Is Already Exist ");
			} else {

				rmb.setResponseCode("200");
				rmb.setResponseMessage("Both Not Exist");
			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return rmb;
	}

	@Override
	public ResponseMessageBean isUpdateInvestementProductExist(InvestementProductsEntity countryData) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		try {
			String sqlNameExist = " SELECT count(*) FROM INVESTMENT_PRODUCTS WHERE Lower(PRODUCTNAME) =:productName AND id !=:id and statusid=3";
			String sqlLinkNameExist = " SELECT count(*) FROM INVESTMENT_PRODUCTS WHERE Lower(PRODUCTLINK) =:productLink AND id !=:id and statusid=3";

			List nameExit = getSession().createSQLQuery(sqlNameExist).setParameter("id", countryData.getId())
					.setParameter("productName", countryData.getProductName().toLowerCase()).list();
			
			List LinkExit = getSession().createSQLQuery(sqlLinkNameExist).setParameter("id", countryData.getId())
					.setParameter("productLink", countryData.getProductLink().toLowerCase()).list();


			if (!(nameExit.get(0).toString().equalsIgnoreCase("0"))) {
				rmb.setResponseCode("500");
				rmb.setResponseMessage("Prooduct Name Is Already Exist ");
			} else if (!(LinkExit.get(0).toString().equalsIgnoreCase("0"))) {
				rmb.setResponseCode("500");
				rmb.setResponseMessage("Prooduct Link Is Already Exist ");
			} else {

				rmb.setResponseCode("200");
				rmb.setResponseMessage("Both Not Exist");
			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return rmb;
	}
	*/
}
