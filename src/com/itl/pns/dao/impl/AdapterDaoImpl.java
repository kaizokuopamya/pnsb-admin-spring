package com.itl.pns.dao.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itl.pns.bean.AdapterAuditLogsBean;
import com.itl.pns.bean.AdminWorkFlowReqBean;
import com.itl.pns.bean.DateBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.constant.ApplicationConstants;
import com.itl.pns.dao.AdapterDao;
import com.itl.pns.entity.ActivitySettingMasterEntity;
import com.itl.pns.entity.AdapterSrcChannelEntity;
import com.itl.pns.entity.AdapterSrcIPEntity;
import com.itl.pns.entity.AdminWorkFlowRequestEntity;
import com.itl.pns.util.AdminWorkFlowReqUtility;

@Repository
@Transactional
public class AdapterDaoImpl implements AdapterDao {

	static Logger LOGGER = Logger.getLogger(AdminWorkFlowReqUtility.class);

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	@Autowired
	AdminWorkFlowReqUtility adminWorkFlowReqUtility;

	protected Session getSession() {
		if (sessionFactory.getCurrentSession() != null)
			return sessionFactory.getCurrentSession();
		else
			return sessionFactory.openSession();
	}

	@Override
	public boolean updateAdapterSrcChannel(AdapterSrcChannelEntity adapterSrcChanneData) {
		Session session = sessionFactory.getCurrentSession();
		int userStatus = adapterSrcChanneData.getStatusId().intValue();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(adapterSrcChanneData.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(adapterSrcChanneData.getActivityName());
		try {
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				adapterSrcChanneData.setStatusId(BigDecimal.valueOf(statusId)); // 97-
																				// ADMIN_CHECKER_PENDIN
			}

			adapterSrcChanneData.setUpdatedon(new Date());
			session.update(adapterSrcChanneData);
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				List<AdminWorkFlowRequestEntity> AdminDataList = adminWorkFlowReqUtility
						.getAdminWorkFlowDataByActivityRefNo(adapterSrcChanneData.getId().intValue(),
								adapterSrcChanneData.getSubMenu_ID());

				ObjectMapper mapper = new ObjectMapper();
				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();

				adminData.setCreatedOn(adapterSrcChanneData.getCreatedon());
				adminData.setCreatedByUserId(adapterSrcChanneData.getUser_ID());
				adminData.setCreatedByRoleId(adapterSrcChanneData.getRole_ID());
				adminData.setPageId(adapterSrcChanneData.getSubMenu_ID()); // set
																			// submenuId
																			// as
																			// pageid
				// adminData.setCreatedBy(adapterSrcChanneData.getCreatedby());
				adminData.setContent(mapper.writeValueAsString(adapterSrcChanneData));
				adminData.setActivityId(adapterSrcChanneData.getSubMenu_ID()); // set
																				// submenuId
																				// as
																				// activityid
				adminData.setStatusId(BigDecimal.valueOf(statusId)); // 97-
																		// ADMIN_CHECKER_PENDIN
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6)); // 6-
																		// checker
																		// roleid
				adminData.setRemark(adapterSrcChanneData.getRemark());
				adminData.setActivityName(adapterSrcChanneData.getActivityName());
				adminData.setActivityRefNo(adapterSrcChanneData.getId());
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminData.setTableName("ADAPTER_SRC_CHANNEL");
				if (ObjectUtils.isEmpty(AdminDataList)) {
					adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);
				} else if (!ObjectUtils.isEmpty(AdminDataList)) {
					adminData.setId(BigDecimal.valueOf(AdminDataList.get(0).getId().intValue()));
					adminWorkFlowReqUtility.updateAdminWorkFlowRequest(adminData);
				}
				// Save data to admin work flow history
				adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(adapterSrcChanneData.getSubMenu_ID(),
						adapterSrcChanneData.getId(), adapterSrcChanneData.getCreatedby(),
						adapterSrcChanneData.getRemark(), adapterSrcChanneData.getRole_ID(),
						mapper.writeValueAsString(adapterSrcChanneData));

			} else {
				// if record is present in admin work flow then update status
				adminWorkFlowReqUtility.getCheckerDataByctivityRefId(adapterSrcChanneData.getId().toBigInteger(),
						BigInteger.valueOf(userStatus), adapterSrcChanneData.getSubMenu_ID());
			}

			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}

	@Override
	public boolean addAdapterSrcChannel(AdapterSrcChannelEntity adapterSrcChanneData) {

		Session session = sessionFactory.getCurrentSession();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(adapterSrcChanneData.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		int userStatus = adapterSrcChanneData.getStatusId().intValue();
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(adapterSrcChanneData.getActivityName());

		try {
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				adapterSrcChanneData.setStatusId(BigDecimal.valueOf(statusId));
			}

			adapterSrcChanneData.setCreatedon(new Date());
			adapterSrcChanneData.setUpdatedon(new Date());
			session.save(adapterSrcChanneData);

			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				List<AdapterSrcChannelEntity> list = getAdaptrSrcChannel();
				ObjectMapper mapper = new ObjectMapper();
				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();

				adminData.setCreatedByUserId(adapterSrcChanneData.getUser_ID());
				adminData.setCreatedByRoleId(adapterSrcChanneData.getRole_ID());
				adminData.setPageId(adapterSrcChanneData.getSubMenu_ID()); // set
																			// submenuId
																			// as
																			// pageid
				// adminData.setCreatedBy(adapterSrcChanneData.getCreatedby());
				adminData.setContent(mapper.writeValueAsString(adapterSrcChanneData));
				adminData.setActivityId(adapterSrcChanneData.getSubMenu_ID()); // set
																				// submenuId
																				// as
																				// activityid
				adminData.setStatusId(BigDecimal.valueOf(statusId)); // 97-
																		// ADMIN_CHECKER_PENDIN
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6)); // 6-
																		// checker
																		// roleid
				adminData.setRemark(adapterSrcChanneData.getRemark());
				adminData.setActivityName(adapterSrcChanneData.getActivityName());
				adminData.setActivityRefNo(list.get(0).getId());
				adminData.setTableName("ADAPTER_SRC_CHANNEL");
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);

				// Save data to admin work flow history
				adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(adapterSrcChanneData.getSubMenu_ID(),
						list.get(0).getId(), adapterSrcChanneData.getCreatedby(), adapterSrcChanneData.getRemark(),
						adapterSrcChanneData.getRole_ID(), mapper.writeValueAsString(adapterSrcChanneData));
			}

			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}

	@Override
	public boolean deletetAdapterSrcChannel(AdapterSrcChannelEntity adapterSrcChanneData) {
		boolean success = true;
		int userStatus = adapterSrcChanneData.getStatusId().intValue();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(adapterSrcChanneData.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		int deletedStatusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.DELETED);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq("ADAPTERSRCCHANNELDELETE"); // changed
																			// table
																			// name
																			// here

		try {
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				adapterSrcChanneData.setStatusId(BigDecimal.valueOf(statusId)); // 97-
																				// ADMIN_CHECKER_PENDIN
			} else {
				adapterSrcChanneData.setStatusId(BigDecimal.valueOf(deletedStatusId)); // 97-
																						// ADMIN_CHECKER_PENDIN
			}

			String sqlQuery = "update adapter_src_channel set statusid=:stsId where id=:id";
			getSession().createSQLQuery(sqlQuery).setParameter("stsId", adapterSrcChanneData.getStatusId())
					.setParameter("id", adapterSrcChanneData.getId()).executeUpdate();

			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				List<AdminWorkFlowRequestEntity> AdminDataList = adminWorkFlowReqUtility
						.getAdminWorkFlowDataByActivityRefNo(adapterSrcChanneData.getId().intValue(),
								adapterSrcChanneData.getSubMenu_ID());

				ObjectMapper mapper = new ObjectMapper();
				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();

				adminData.setCreatedOn(adapterSrcChanneData.getCreatedon());
				adminData.setCreatedByUserId(adapterSrcChanneData.getUser_ID());
				adminData.setCreatedByRoleId(adapterSrcChanneData.getRole_ID());
				adminData.setPageId(adapterSrcChanneData.getSubMenu_ID()); // set
																			// submenuId
																			// as
																			// pageid
				// adminData.setCreatedBy(adapterSrcChanneData.getCreatedby());
				adminData.setContent(mapper.writeValueAsString(adapterSrcChanneData));
				adminData.setActivityId(adapterSrcChanneData.getSubMenu_ID()); // set
																				// submenuId
																				// as
																				// activityid
				adminData.setStatusId(BigDecimal.valueOf(statusId)); // 97-
																		// ADMIN_CHECKER_PENDIN
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6)); // 6-
																		// checker
																		// roleid
				adminData.setRemark(adapterSrcChanneData.getRemark());
				adminData.setActivityName("ADAPTERSRCCHANNELDELETE");
				adminData.setActivityRefNo(adapterSrcChanneData.getId());
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminData.setTableName("ADAPTER_SRC_CHANNEL");
				if (ObjectUtils.isEmpty(AdminDataList)) {
					adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);
				} else if (!ObjectUtils.isEmpty(AdminDataList)) {
					adminData.setId(BigDecimal.valueOf(AdminDataList.get(0).getId().intValue()));
					adminWorkFlowReqUtility.updateAdminWorkFlowRequest(adminData);
				}
				// Save data to admin work flow history
				adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(adapterSrcChanneData.getSubMenu_ID(),
						adapterSrcChanneData.getId(), adapterSrcChanneData.getCreatedby(),
						adapterSrcChanneData.getRemark(), adapterSrcChanneData.getRole_ID(),
						mapper.writeValueAsString(adapterSrcChanneData));

			} else {
				// if record is present in admin work flow then update status
				adminWorkFlowReqUtility.getCheckerDataByctivityRefId(adapterSrcChanneData.getId().toBigInteger(),
						BigInteger.valueOf(userStatus), adapterSrcChanneData.getSubMenu_ID());
			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}

		return success;
	}

	@Override
	public List<AdapterSrcChannelEntity> getAdaptrSrcChannel() {
		List<AdapterSrcChannelEntity> list = null;
		try {
			String sqlQuery = "  select ac.id,ac.appId, ac.createdby as createdby ,ac.createdon as createdon, ac.updatedby as updatedby,"
					+ "  ac.updatedon as updatedon, um.USERID as createdByName,ac.statusId ,s.name as statusName , a.shortname as appName "
					+ " from adapter_src_channel ac inner join statusmaster s on s.id = ac.statusId "
					+ "inner join appmaster a on a.id=ac.appid  "
					+ " inner join user_master um on ac.createdby = um.id where ac.statusId !=10  order by ac.id desc";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("appName").addScalar("appId")
					.addScalar("createdby").addScalar("createdByName").addScalar("createdon").addScalar("updatedby")
					.addScalar("updatedon").addScalar("statusId").addScalar("statusName")
					.setResultTransformer(Transformers.aliasToBean(AdapterSrcChannelEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return list;
	}

	@Override
	public List<AdapterSrcChannelEntity> getAdaptrSrcChannelById(BigInteger id) {
		List<AdapterSrcChannelEntity> list = null;
		try {
			String sqlQuery = "  select ac.id,ac.appId , ac.createdby as createdby ,ac.createdon as createdon, ac.updatedby as updatedby,"
					+ "aw.remark, aw.userAction, ac.updatedon as updatedon,ac.statusId ,s.name as statusName ,a.shortname as appName"
					+ " from adapter_src_channel ac inner join appmaster a on a.id=ac.appid "
					+ "inner join statusmaster s on s.id = ac.statusId left join ADMINWORKFLOWREQUEST AS aw on aw.activityrefno=ac.id and aw.tablename='ADAPTER_SRC_CHANNEL' "
					+ " where ac.id=:id";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("appName").addScalar("appId")
					.addScalar("createdby").addScalar("createdon").addScalar("updatedby").addScalar("updatedon")
					.addScalar("remark").addScalar("userAction").addScalar("statusId").addScalar("statusName")
					.setParameter("id", id)
					.setResultTransformer(Transformers.aliasToBean(AdapterSrcChannelEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return list;
	}

	@Override
	public boolean addAdapterSrcIP(AdapterSrcIPEntity adapterIpdata) {
		Session session = sessionFactory.getCurrentSession();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(adapterIpdata.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		int userStatus = adapterIpdata.getStatusId().intValue();
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(adapterIpdata.getActivityName());
		try {
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				adapterIpdata.setStatusId(new BigDecimal(statusId));
			}
			adapterIpdata.setCreatedon(new Date());
			adapterIpdata.setUpdatedon(new Date());
			session.save(adapterIpdata);

			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				List<AdapterSrcIPEntity> list = getAdapterSrcIpDetails();
				ObjectMapper mapper = new ObjectMapper();
				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();

				adminData.setCreatedByUserId(adapterIpdata.getUser_ID());
				adminData.setCreatedByRoleId(adapterIpdata.getRole_ID());
				adminData.setPageId(adapterIpdata.getSubMenu_ID()); // set
																	// submenuId
																	// as pageid
				// adminData.setCreatedBy(adapterIpdata.getCreatedby());
				adminData.setContent(mapper.writeValueAsString(adapterIpdata));
				adminData.setActivityId(adapterIpdata.getSubMenu_ID()); // set
																		// submenuId
																		// as
																		// activityid
				adminData.setStatusId(BigDecimal.valueOf(statusId)); // 97-
																		// ADMIN_CHECKER_PENDIN
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6)); // 6-
																		// checker
																		// roleid
				adminData.setRemark(adapterIpdata.getRemark());
				adminData.setActivityName(adapterIpdata.getActivityName());
				adminData.setActivityRefNo(list.get(0).getId());
				adminData.setTableName("ADPATER_SRC_IP");
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);

				// Save data to admin work flow history
				adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(adapterIpdata.getSubMenu_ID(), list.get(0).getId(),
						adapterIpdata.getCreatedby(), adapterIpdata.getRemark(), adapterIpdata.getRole_ID(),
						mapper.writeValueAsString(adapterIpdata));
			}

			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}

	@Override
	public boolean updateAdapterSrcIP(AdapterSrcIPEntity adapterIpdata) {
		Session session = sessionFactory.getCurrentSession();
		int userStatus = adapterIpdata.getStatusId().intValue();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(adapterIpdata.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(adapterIpdata.getActivityName());
		try {
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				adapterIpdata.setStatusId(new BigDecimal(statusId)); // 97-
																		// ADMIN_CHECKER_PENDIN
			}
			adapterIpdata.setUpdatedon(new Date());
			session.update(adapterIpdata);

			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				List<AdminWorkFlowRequestEntity> AdminDataList = adminWorkFlowReqUtility
						.getAdminWorkFlowDataByActivityRefNo(adapterIpdata.getId().intValue(),
								adapterIpdata.getSubMenu_ID());

				ObjectMapper mapper = new ObjectMapper();
				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();

				adminData.setCreatedOn(adapterIpdata.getCreatedon());
				adminData.setCreatedByUserId(adapterIpdata.getUser_ID());
				adminData.setCreatedByRoleId(adapterIpdata.getRole_ID());
				adminData.setPageId(adapterIpdata.getSubMenu_ID()); // set
																	// submenuId
																	// as pageid
				// adminData.setCreatedBy(adapterIpdata.getCreatedby());
				adminData.setContent(mapper.writeValueAsString(adapterIpdata));
				adminData.setActivityId(adapterIpdata.getSubMenu_ID()); // set
																		// submenuId
																		// as
																		// activityid
				adminData.setStatusId(BigDecimal.valueOf(statusId)); // 97-
																		// ADMIN_CHECKER_PENDIN
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6)); // 6-
																		// checker
																		// roleid
				adminData.setRemark(adapterIpdata.getRemark());
				adminData.setActivityName(adapterIpdata.getActivityName());
				adminData.setActivityRefNo(adapterIpdata.getId());
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminData.setTableName("ADPATER_SRC_IP");
				if (ObjectUtils.isEmpty(AdminDataList)) {
					adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);
				} else if (!ObjectUtils.isEmpty(AdminDataList)) {
					adminData.setId(BigDecimal.valueOf(AdminDataList.get(0).getId().intValue()));
					adminWorkFlowReqUtility.updateAdminWorkFlowRequest(adminData);

				}
				// Save data to admin work flow history
				adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(adapterIpdata.getSubMenu_ID(), adapterIpdata.getId(),
						adapterIpdata.getCreatedby(), adapterIpdata.getRemark(), adapterIpdata.getRole_ID(),
						mapper.writeValueAsString(adapterIpdata));

			} else {
				// if record is present in admin work flow then update status
				adminWorkFlowReqUtility.getCheckerDataByctivityRefId(adapterIpdata.getId().toBigInteger(),
						BigInteger.valueOf(userStatus), adapterIpdata.getSubMenu_ID());
			}

			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}

	@Override
	public boolean deleteAdapterSrcIP(AdapterSrcIPEntity adapterIpdata) {
		boolean success = true;
		int userStatus = adapterIpdata.getStatusId().intValue();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(adapterIpdata.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		int deletedStatusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.DELETED);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq("ADAPTERSRCIPDELETE");// change
																	// table
																	// name here

		try {
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				adapterIpdata.setStatusId(new BigDecimal(statusId)); // 97-
																		// ADMIN_CHECKER_PENDIN
			} else {
				adapterIpdata.setStatusId(new BigDecimal(deletedStatusId)); // 23-
																			// DELETE
			}

			String sqlQuery = "update ADPATER_SRC_IP set statusId=:stsId where id=:id";
			getSession().createSQLQuery(sqlQuery).setParameter("stsId", adapterIpdata.getStatusId())
					.setParameter("id", adapterIpdata.getId()).executeUpdate();

			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				List<AdminWorkFlowRequestEntity> AdminDataList = adminWorkFlowReqUtility
						.getAdminWorkFlowDataByActivityRefNo(adapterIpdata.getId().intValue(),
								adapterIpdata.getSubMenu_ID());

				ObjectMapper mapper = new ObjectMapper();
				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();

				adapterIpdata.setUpdatedon(new Date());
				adminData.setCreatedOn(adapterIpdata.getCreatedon());
				adminData.setCreatedByUserId(adapterIpdata.getUser_ID());
				adminData.setCreatedByRoleId(adapterIpdata.getRole_ID());
				adminData.setPageId(adapterIpdata.getSubMenu_ID()); // set
																	// submenuId
																	// as pageid
				// adminData.setCreatedBy(adapterIpdata.getCreatedby());
				adminData.setContent(mapper.writeValueAsString(adapterIpdata));
				adminData.setActivityId(adapterIpdata.getSubMenu_ID()); // set
																		// submenuId
																		// as
																		// activityid
				adminData.setStatusId(BigDecimal.valueOf(statusId)); // 97-
																		// ADMIN_CHECKER_PENDIN
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6)); // 6-
																		// checker
																		// roleid
				adminData.setRemark(adapterIpdata.getRemark());
				adminData.setActivityName("ADAPTERSRCIPDELETE");
				adminData.setActivityRefNo(adapterIpdata.getId());
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminData.setTableName("ADPATER_SRC_IP");
				if (ObjectUtils.isEmpty(AdminDataList)) {
					adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);
				} else if (!ObjectUtils.isEmpty(AdminDataList)) {
					adminData.setId(BigDecimal.valueOf(AdminDataList.get(0).getId().intValue()));
					adminWorkFlowReqUtility.updateAdminWorkFlowRequest(adminData);

				}
				// Save data to admin work flow history
				adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(adapterIpdata.getSubMenu_ID(), adapterIpdata.getId(),
						adapterIpdata.getCreatedby(), adapterIpdata.getRemark(), adapterIpdata.getRole_ID(),
						mapper.writeValueAsString(adapterIpdata));

			} else {
				// if record is present in admin work flow then update status
				adminWorkFlowReqUtility.getCheckerDataByctivityRefId((adapterIpdata.getId().toBigInteger()),
						BigInteger.valueOf(userStatus), adapterIpdata.getSubMenu_ID());
			}

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}

		return success;
	}

	@Override
	public List<AdapterSrcIPEntity> getAdapterSrcIpDetails() {
		List<AdapterSrcIPEntity> list = null;
		try {
			String sqlQuery = "select ac.id,ac.SOURCE_IP as sourceIp ,ac.appId as appId, ac.created_by as createdby ,um.USERID as createdByName,"
					+ "ac.created_on as createdon, ac.updated_by as updatedby,  ac.updated_on as updatedon,a.shortname as appName,"
					+ "ac.statusId,s.name as statusName from ADPATER_SRC_IP ac inner join statusmaster s on s.id = ac.statusId  "
					+ " inner join appmaster a on a.id = ac.appid "
					+ "  inner join user_master um on ac.created_by = um.id  where ac.statusId !=10 order by ac.id desc";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("appName").addScalar("sourceIp")
					.addScalar("appId").addScalar("createdby").addScalar("createdByName").addScalar("createdon")
					.addScalar("updatedby").addScalar("updatedon").addScalar("statusId").addScalar("statusName")
					.setResultTransformer(Transformers.aliasToBean(AdapterSrcIPEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return list;
	}

	@Override
	public List<AdapterSrcIPEntity> getAdapterSrcIpById(BigInteger id) {
		List<AdapterSrcIPEntity> list = null;
		try {

			String sqlQuery = "select ac.id,ac.SOURCE_IP as sourceIp ,ac.appId as appId, ac.created_by as createdby ,"
					+ "ac.created_on as createdon, ac.updated_by as updatedby,  ac.updated_on as updatedon, aw.remark, aw.userAction ,a.shortname as appName,"
					+ " ac.statusId,s.name as statusName  from ADPATER_SRC_IP ac "
					+ " inner join statusmaster s on s.id = ac.statusId  inner join appmaster a on a.id = ac.appid "
					+ "left join ADMINWORKFLOWREQUEST aw on aw.activityrefno=ac.id and aw.tablename='ADPATER_SRC_IP' "
					+ " where ac.id=:id";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("appName").addScalar("sourceIp")
					.addScalar("appId").addScalar("createdby").addScalar("createdon").addScalar("updatedby")
					.addScalar("updatedon").addScalar("remark").addScalar("userAction").addScalar("statusId")
					.addScalar("statusName").setParameter("id", id)
					.setResultTransformer(Transformers.aliasToBean(AdapterSrcIPEntity.class)).list();

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return list;
	}

	@Override
	public List<AdapterAuditLogsBean> getAdapterAuditLogsById(BigInteger id) {
		List<AdapterAuditLogsBean> list = null;
		try {
			String sqlQuery = "select al.id,al.MSG_TYPE,al.CREATED_ON,al.ADAPTER_IP,al.ADAPTER_CHANNEL,al.MESSAGE,al.CHANNEL_REF_NO, al.RRN,"
					+ "al.CREATED_BY,al.MOBILE_NO from adapter_audit_logs al where al.id=:id";
			list = getSession().createSQLQuery(sqlQuery).setParameter("id", id)
					.setResultTransformer(Transformers.aliasToBean(AdapterAuditLogsBean.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return list;
	}

	@Override
	public List<AdapterAuditLogsBean> getAdapterAuditLogs() {
		
		List<AdapterAuditLogsBean> list = null;
		try {
		String sqlQuery = "select al.id,al.MSG_TYPE,al.CREATED_ON,al.ADAPTER_IP,al.ADAPTER_CHANNEL,al.CREATED_BY,al.MESSAGE,al.CHANNEL_REF_NO, al.RRN,"
				+ "al.MOBILE_NO from adapter_audit_logs al";
	 list = getSession().createSQLQuery(sqlQuery)
				.setResultTransformer(Transformers.aliasToBean(AdapterAuditLogsBean.class)).list();
		}catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return list;
	}

	@Override
	public List<AdapterAuditLogsBean> getAdapterAuditLogByDate(DateBean datebean) {
		String sqlQuery = "";

		List<AdapterAuditLogsBean> list = null;
		try {

			sqlQuery = "select al.id as id,al.MSG_TYPE,al.CREATED_ON,al.ADAPTER_IP,al.ADAPTER_CHANNEL,al.CREATED_BY,um.USERID createdByName, al.MESSAGE,al.CHANNEL_REF_NO, al.RRN,"
					+ "al.MOBILE_NO from adapter_audit_logs al inner join user_master um on al.CREATED_BY = um.id"
					+ " where al.CREATED_ON between TO_DATE('" + datebean.getFromdate() + "','yyyy-mm-dd') "
					+ " and  TO_DATE('" + datebean.getTodate() + "','yyyy-mm-dd')+1  " + " order by al.id desc";

			System.out.println(sqlQuery);
			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("MSG_TYPE").addScalar("CREATED_ON")
					.addScalar("ADAPTER_IP").addScalar("ADAPTER_CHANNEL").addScalar("CREATED_BY")
					.addScalar("createdByName").addScalar("MESSAGE", StandardBasicTypes.STRING).addScalar("RRN")
					.addScalar("MOBILE_NO").addScalar("CHANNEL_REF_NO")
					.setResultTransformer(Transformers.aliasToBean(AdapterAuditLogsBean.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return list;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public ResponseMessageBean isChannelNameExist(AdapterSrcChannelEntity adapterSrcChanneData) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		try {
			String sqlChannelNameExit = "SELECT count(*) FROM ADAPTER_SRC_CHANNEL WHERE appId =:channel AND statusId=3 ";
			List isChannelNameExit = getSession().createSQLQuery(sqlChannelNameExit)
					.setParameter("channel", adapterSrcChanneData.getAppId()).list();

			if (!(isChannelNameExit.get(0).toString().equalsIgnoreCase("0"))) {
				rmb.setResponseCode("500");
				rmb.setResponseMessage("Channel Already Exist");
			} else {
				rmb.setResponseCode("200");
				rmb.setResponseMessage("Both not exist");
			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return rmb;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public ResponseMessageBean updateChannelNameExist(AdapterSrcChannelEntity adapterSrcChanneData) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		try {
			String sqlMenuNameExit = "SELECT count(*) FROM ADAPTER_SRC_CHANNEL WHERE appId =:channel  AND  ID!=:id ";
			List isMenuNameExit = getSession().createSQLQuery(sqlMenuNameExit)
					.setParameter("id", adapterSrcChanneData.getId())
					.setParameter("channel", adapterSrcChanneData.getAppId()).list();

			System.out.println(sqlMenuNameExit);
			System.out.println(isMenuNameExit);

			if (!(isMenuNameExit.get(0).toString().equalsIgnoreCase("0"))) {
				rmb.setResponseCode("500");
				rmb.setResponseMessage("Channel Already Exist");
			} else {
				rmb.setResponseCode("200");
				rmb.setResponseMessage("Both not exist");
			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return rmb;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public ResponseMessageBean isIpExitForSameChannel(AdapterSrcIPEntity adapterSrcIpData) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		try {
			String sqlIpExitForChannel = "SELECT count(*) FROM ADPATER_SRC_IP ap where ap.appId =:channelId and ap.source_ip =:ipaddress and statusid =3 ";
			List ipExitForChannel = getSession().createSQLQuery(sqlIpExitForChannel)
					.setParameter("ipaddress", adapterSrcIpData.getSourceIp())
					.setParameter("channelId", adapterSrcIpData.getAppId()).list();

			if (!(ipExitForChannel.get(0).toString().equalsIgnoreCase("0"))) {
				rmb.setResponseCode("500");
				rmb.setResponseMessage("Name Already Exist");
			} else {
				rmb.setResponseCode("200");
				rmb.setResponseMessage("Both not exist");
			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return rmb;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public ResponseMessageBean UpdateIpExitForSameChannel(AdapterSrcIPEntity adapterSrcIpData) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		try {
			String sqlIpExitForChannel = "SELECT count(*) FROM ADPATER_SRC_IP ap where ap.appId =:channelId and ap.source_ip =:ipaddress  AND  ID!=:id AND STATUSID =3 ";
			List ipExitForChannel = getSession().createSQLQuery(sqlIpExitForChannel)
					.setParameter("id", adapterSrcIpData.getId())
					.setParameter("ipaddress", adapterSrcIpData.getSourceIp())
					.setParameter("channelId", adapterSrcIpData.getAppId()).list();
			System.out.println(sqlIpExitForChannel);
			System.out.println(ipExitForChannel);
			if (!(ipExitForChannel.get(0).toString().equalsIgnoreCase("0"))) {
				rmb.setResponseCode("500");
				rmb.setResponseMessage("Name Already Exist");
			} else {
				rmb.setResponseCode("200");
				rmb.setResponseMessage("Both not exist");
			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return rmb;

	}

}
