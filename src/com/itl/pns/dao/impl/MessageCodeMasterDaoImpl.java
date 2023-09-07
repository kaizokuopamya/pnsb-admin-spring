package com.itl.pns.dao.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itl.pns.constant.ApplicationConstants;
import com.itl.pns.dao.MessageCodeMasterDao;
import com.itl.pns.entity.ActivitySettingMasterEntity;
import com.itl.pns.entity.AdminWorkFlowRequestEntity;
import com.itl.pns.entity.InvestementProductsEntity;
import com.itl.pns.entity.MessageCodeMasterEntity;
import com.itl.pns.util.AdminWorkFlowReqUtility;

@Repository
@Transactional
public class MessageCodeMasterDaoImpl implements MessageCodeMasterDao {

	static Logger LOGGER = Logger.getLogger(MessageCodeMasterDaoImpl.class);
	
	@Autowired
	AdminWorkFlowReqUtility adminWorkFlowReqUtility;
	
	@Autowired
	@Qualifier("sessionFactory")
	
	
	private SessionFactory sessionFactory;
	private static final Logger logger = LogManager.getLogger(MessageCodeMasterDaoImpl.class);

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public List<MessageCodeMasterEntity> getMessageCodeMasterDetails() {
		List<MessageCodeMasterEntity> list = null;

		try {
			String sqlQuerry = "select mc.id, mc.SHORTNAME as shortName, mc.ACTIVITYID as activityId,mc.ERRORCODE as errorCode,mc.STATUSID as statusId, mc.APPID as appId,"
					+ "	mc.I18NCODE as i18nCode, mc.DESCRIPTION as description, mc.CREATEDBY as createdby, mc.CREATEDON as createdon, mc.UPDATEDBY as updatedby,am.ACTIVITYCODE as activityCodeName,"
					+ "   mc.UPDATEDON as updatedon, s.name as statusName, um.userid as createdByName ,aa.shortname as appName from MESSAGECODEMASTER mc inner join statusmaster s on s.id= mc.statusid inner join user_master um on um.id=mc.createdby "
					+ " inner join activitymaster am on am.id=mc.activityId  inner join appmaster aa on aa.id=mc.appId order by mc.id desc";

			list = getSession().createSQLQuery(sqlQuerry)
					.addScalar("id").addScalar("shortName").addScalar("activityId").addScalar("errorCode").addScalar("statusId").addScalar("i18nCode").addScalar("appId").addScalar("appName")
					.addScalar("description").addScalar("createdby").addScalar("createdon").addScalar("activityCodeName").addScalar("updatedby").addScalar("updatedon").addScalar("statusName").addScalar("createdByName")
					.setResultTransformer(Transformers.aliasToBean(MessageCodeMasterEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public List<MessageCodeMasterEntity> getMessageCodeMasterDetailsById(MessageCodeMasterEntity messageCodeData) {
		List<MessageCodeMasterEntity> list = null;

		try {
			String sqlQuerry = "select mc.id, mc.SHORTNAME as shortName, mc.ACTIVITYID as activityId,mc.ERRORCODE as errorCode,mc.STATUSID as statusId, mc.APPID as appId,"
					+ "	mc.I18NCODE as i18nCode, mc.DESCRIPTION as description, mc.CREATEDBY as createdby, mc.CREATEDON as createdon, mc.UPDATEDBY as updatedby,am.ACTIVITYCODE as activityCodeName,aa.shortname as appName ,"
					+ "   mc.UPDATEDON as updatedon,s.name as statusName, um.userid as createdByName,aw.remark,aw.userAction from MESSAGECODEMASTER mc inner join activitymaster am on am.id=mc.activityId "
					+ "inner join statusmaster s on s.id= mc.statusid inner join user_master um on um.id=mc.createdby inner join appmaster aa on aa.id=mc.appId  "
					+ " left join ADMINWORKFLOWREQUEST aw on aw.activityrefno=mc.id and aw.tablename='MESSAGECODEMASTER' where mc.id =:id";

			list = getSession().createSQLQuery(sqlQuerry)
					.addScalar("id").addScalar("shortName").addScalar("activityId").addScalar("errorCode").addScalar("appId").addScalar("statusId").addScalar("i18nCode").addScalar("appName")
					.addScalar("description").addScalar("createdby").addScalar("createdon").addScalar("updatedby").addScalar("updatedon").addScalar("statusName")
					.addScalar("createdByName").addScalar("activityCodeName").addScalar("remark").addScalar("userAction")
					.setParameter("id", messageCodeData.getId())
					.setResultTransformer(Transformers.aliasToBean(MessageCodeMasterEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public boolean addMessageCode(MessageCodeMasterEntity messageCodeData) {
		Session session = sessionFactory.getCurrentSession();
		int userStatus = messageCodeData.getStatusId().intValue();
		BigDecimal messageCodeId = new BigDecimal(0);
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(messageCodeData.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(messageCodeData.getActivityName());

		try {
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				messageCodeData.setStatusId(BigDecimal.valueOf(statusId)); // 97-
																					// ADMIN_CHECKER_PENDIN
			}
			messageCodeData.setCreatedon(new Date());
			messageCodeData.setUpdatedon(new Date());
			messageCodeId =(BigDecimal)session.save(messageCodeData);

			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
			//	List<MessageCodeMasterEntity> list = getMessageCodeMasterDetails();
				ObjectMapper mapper = new ObjectMapper();

				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
				adminData.setCreatedByUserId(messageCodeData.getUser_ID());
				adminData.setCreatedByRoleId(messageCodeData.getRole_ID());
				adminData.setPageId(messageCodeData.getSubMenu_ID()); // set
																							// submenuId
																							// as
																							// pageid
				adminData.setCreatedBy(messageCodeData.getCreatedby());
				adminData.setContent(mapper.writeValueAsString(messageCodeData));
				adminData.setActivityId(messageCodeData.getSubMenu_ID()); // set
																								// submenuId
																								// as
																								// activityid
				adminData.setStatusId(BigDecimal.valueOf(statusId)); // 97-
																		// ADMIN_CHECKER_PENDIN
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6)); // 6-
																		// checker
																		// roleid
				adminData.setRemark(messageCodeData.getRemark());
				adminData.setActivityName(messageCodeData.getActivityName());
				adminData.setActivityRefNo(messageCodeId);
				adminData.setTableName("MESSAGECODEMASTER");
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);

				// Save data to admin work flow history
				adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(messageCodeData.getSubMenu_ID(),
						messageCodeId,messageCodeData.getCreatedby(),
						messageCodeData.getRemark(), messageCodeData.getRole_ID(),
						mapper.writeValueAsString(messageCodeData));
			}

			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}

	@Override
	public boolean updateMessageCode(MessageCodeMasterEntity messageCodeData) {
		Session session = sessionFactory.getCurrentSession();
		int userStatus = messageCodeData.getStatusId().intValue();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(messageCodeData.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(messageCodeData.getActivityName());

		try {
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				messageCodeData.setStatusId(BigDecimal.valueOf(statusId));
			}

			messageCodeData.setUpdatedon(new Date());
			session.update(messageCodeData);

			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

				ObjectMapper mapper = new ObjectMapper();

				List<AdminWorkFlowRequestEntity> AdminDataList = adminWorkFlowReqUtility
						.getAdminWorkFlowDataByActivityRefNo(messageCodeData.getId().intValue(),
								messageCodeData.getSubMenu_ID());

				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
				adminData.setCreatedOn(messageCodeData.getCreatedon());
				adminData.setCreatedByUserId(messageCodeData.getUser_ID());
				adminData.setCreatedByRoleId(messageCodeData.getRole_ID());
				adminData.setPageId(messageCodeData.getSubMenu_ID()); // set
																							// submenuId
																							// as
																							// pageid
				adminData.setCreatedBy(messageCodeData.getCreatedby());
				adminData.setContent(mapper.writeValueAsString(messageCodeData));
				adminData.setActivityId(messageCodeData.getSubMenu_ID()); // set
																								// submenuId
																								// as
																								// activityid
				adminData.setStatusId(BigDecimal.valueOf(statusId)); // 97-
																		// ADMIN_CHECKER_PENDIN
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6)); // 6-
																		// checker
																		// roleid
				adminData.setRemark(messageCodeData.getRemark());
				adminData.setActivityName(messageCodeData.getActivityName());
				adminData.setActivityRefNo(messageCodeData.getId());
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminData.setTableName("MESSAGECODEMASTER");

				if (ObjectUtils.isEmpty(AdminDataList)) {
					adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);
				} else if (!ObjectUtils.isEmpty(AdminDataList)) {
					adminData.setId(BigDecimal.valueOf(AdminDataList.get(0).getId().intValue()));
					adminWorkFlowReqUtility.updateAdminWorkFlowRequest(adminData);
				}
					// Save data to admin work flow history
					adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(messageCodeData.getSubMenu_ID(),
							messageCodeData.getId(),
							messageCodeData.getCreatedby(), messageCodeData.getRemark(),
							messageCodeData.getRole_ID(), mapper.writeValueAsString(messageCodeData));
				

			} else {

				// if record is present in admin work flow then update status
				adminWorkFlowReqUtility.getCheckerDataByctivityRefId(messageCodeData.getId().toBigInteger(),
						BigInteger.valueOf(userStatus), messageCodeData.getSubMenu_ID());
			}
			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}

}
