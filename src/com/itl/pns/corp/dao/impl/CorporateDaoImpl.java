package com.itl.pns.corp.dao.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itl.pns.bean.CorpLimitDataBean;
import com.itl.pns.bean.CorpTransLimitBean;
import com.itl.pns.bean.CorpUserBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.constant.ApplicationConstants;
import com.itl.pns.corp.dao.CorporateDao;
import com.itl.pns.corp.entity.CorpAccUserTypeEntity;
import com.itl.pns.corp.entity.CorpAcctTransLimitUsersEntity;
import com.itl.pns.corp.entity.CorpAcctTransMasterEntity;
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
import com.itl.pns.entity.ActivitySettingMasterEntity;
import com.itl.pns.entity.AdminWorkFlowRequestEntity;
import com.itl.pns.service.impl.RestServiceCall;
import com.itl.pns.util.AdminWorkFlowReqUtility;
import com.itl.pns.util.EmailUtil;
import com.itl.pns.util.EncryptDeryptUtility;
import com.itl.pns.util.EncryptorDecryptor;

/**
 * @author shubham.lokhande
 *
 */

@Repository
@Transactional
@Service
public class CorporateDaoImpl implements CorporateDao {

	static Logger LOGGER = Logger.getLogger(CorporateDaoImpl.class);
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	@Autowired
	AdminWorkFlowReqUtility adminWorkFlowReqUtility;

	@Autowired
	EmailUtil util;

	@Autowired
	RestServiceCall rest;

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public boolean addCorpMenu(CorpMenuEntity corpMenuData) {
		Session session = sessionFactory.getCurrentSession();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(corpMenuData.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.CORP_CHECKER_PENDING);
		int userStatus = corpMenuData.getStatus().intValue();
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(corpMenuData.getActivityName());

		try {
			if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				corpMenuData.setStatus(BigDecimal.valueOf(statusId));
			}

			corpMenuData.setCreatedon(new Date());
			corpMenuData.setUpdatedon(new Date());
			session.save(corpMenuData);

			if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				List<CorpMenuEntity> list = getAllCorpMenus();
				ObjectMapper mapper = new ObjectMapper();
				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
				adminData.setAppId(BigDecimal.valueOf(2));
				adminData.setCreatedByUserId(corpMenuData.getUser_ID());
				adminData.setCreatedByRoleId(corpMenuData.getRole_ID());
				adminData.setPageId(corpMenuData.getSubMenu_ID());
				adminData.setCreatedBy(corpMenuData.getCreatedby());
				adminData.setContent(mapper.writeValueAsString(corpMenuData));
				adminData.setActivityId(corpMenuData.getSubMenu_ID());
				adminData.setStatusId(BigDecimal.valueOf(statusId));
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6));
				adminData.setRemark(corpMenuData.getRemark());
				adminData.setActivityName(corpMenuData.getActivityName());
				adminData.setActivityRefNo(list.get(0).getId());
				adminData.setTableName("CORP_MENU");
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);

				// Save data to admin work flow history
				adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(corpMenuData.getSubMenu_ID(), list.get(0).getId(),
						BigDecimal.valueOf(corpMenuData.getCreatedby().intValue()), corpMenuData.getRemark(),
						corpMenuData.getRole_ID(), mapper.writeValueAsString(corpMenuData));
			}

			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}

	@Override
	public boolean updateCorpMenu(CorpMenuEntity corpMenuData) {
		Session session = sessionFactory.getCurrentSession();
		int userStatus = corpMenuData.getStatus().intValue();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(corpMenuData.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.CORP_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(corpMenuData.getActivityName());
		try {
			if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				corpMenuData.setStatus(BigDecimal.valueOf(statusId));
			}
			corpMenuData.setUpdatedon(new Date());
			session.update(corpMenuData);

			if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

				ObjectMapper mapper = new ObjectMapper();

				List<AdminWorkFlowRequestEntity> AdminDataList = adminWorkFlowReqUtility
						.getAdminWorkFlowDataByActivityRefNo(corpMenuData.getId().intValue(),
								corpMenuData.getSubMenu_ID());

				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
				adminData.setAppId(BigDecimal.valueOf(2));
				adminData.setCreatedOn(corpMenuData.getCreatedon());
				adminData.setCreatedByUserId(corpMenuData.getUser_ID());
				adminData.setCreatedByRoleId(corpMenuData.getRole_ID());
				adminData.setPageId(corpMenuData.getSubMenu_ID());
				adminData.setCreatedBy(corpMenuData.getCreatedby());
				adminData.setContent(mapper.writeValueAsString(corpMenuData));
				adminData.setActivityId(corpMenuData.getSubMenu_ID());
				adminData.setStatusId(BigDecimal.valueOf(statusId));
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6));
				adminData.setRemark(corpMenuData.getRemark());
				adminData.setActivityName(corpMenuData.getActivityName());
				adminData.setActivityRefNo(corpMenuData.getId());
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminData.setTableName("CORP_MENU");

				if (ObjectUtils.isEmpty(AdminDataList)) {
					adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);
				} else if (!ObjectUtils.isEmpty(AdminDataList)) {
					adminData.setId(BigDecimal.valueOf(AdminDataList.get(0).getId().intValue()));
					adminWorkFlowReqUtility.updateAdminWorkFlowRequest(adminData);

				}
				// Save data to admin work flow history
				adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(corpMenuData.getSubMenu_ID(), corpMenuData.getId(),
						corpMenuData.getCreatedby(), corpMenuData.getRemark(), corpMenuData.getRole_ID(),
						mapper.writeValueAsString(corpMenuData));
			} else {
				// if record is present in admin work flow then update status
				adminWorkFlowReqUtility.getCheckerDataByctivityRefId(corpMenuData.getId().toBigInteger(),
						BigInteger.valueOf(userStatus), corpMenuData.getSubMenu_ID());
			}
			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}

	@Override
	public List<CorpMenuEntity> getCorpMenuById(int id) {
		List<CorpMenuEntity> list = null;
		try {
			String sqlQuery = "select cm.id , cm.menuName as menuname, cm.CREATEDON as createdon, cm.UPDATEDON as updatedon , cm.CREATEDBY as createdby,"
					+ " cm.UPDATEDBY as updatedby, cm.statusId as status, cm.MENULOGO as menuLogo,cm. MENULINK as  menuLink,s.name as statusName ,aw.remark,aw.userAction "
					+ " from CORP_MENU cm  inner join statusmaster s on s.id=cm.statusId "
					+ "left join ADMINWORKFLOWREQUEST aw on aw.activityrefno=:id and aw.tablename='CORP_MENU'  where cm.id =:id";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("menuname", StandardBasicTypes.STRING).addScalar("createdon", StandardBasicTypes.DATE)
					.addScalar("updatedon", StandardBasicTypes.DATE)
					.addScalar("createdby", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("updatedby", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("status", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("statusName", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("menuLogo", StandardBasicTypes.STRING).addScalar("menuLink", StandardBasicTypes.STRING)
					.addScalar("userAction", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("remark", StandardBasicTypes.STRING).setParameter("id", id)
					.setResultTransformer(Transformers.aliasToBean(CorpMenuEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public List<CorpMenuEntity> getAllCorpMenus() {
		List<CorpMenuEntity> list = null;
		try {
			String sqlQuery = "select cm.id , cm.menuname as menuName, cm.CREATEDON as createdon, cm.UPDATEDON as updatedon , cm.CREATEDBY as createdby,"
					+ " cm.UPDATEDBY as updatedby, cm.statusId as status, cm.MENULOGO as menuLogo,cm. MENULINK as  menuLink ,s.name as statusName from CORP_MENU cm "
					+ " inner join statusmaster s on s.id=cm.statusId where cm.statusid=3";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("menuName").addScalar("createdon").addScalar("updatedon")
					.addScalar("createdby", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("updatedby", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("status", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("menuLogo", StandardBasicTypes.STRING).addScalar("menuLink").addScalar("statusName")
					.setResultTransformer(Transformers.aliasToBean(CorpMenuEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public boolean addCorpCompanyDetails(CorpCompanyMasterEntity corpCompanyData) {
		Session session = sessionFactory.getCurrentSession();
		int userStatus = corpCompanyData.getStatusId().intValue();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(corpCompanyData.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.CORP_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(corpCompanyData.getActivityName());
		try {
			if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				corpCompanyData.setStatusId(BigDecimal.valueOf(statusId)); // 97-
																			// ADMIN_CHECKER_PENDIN
			}

			corpCompanyData.setCreatedOn(new Timestamp(System.currentTimeMillis()));
			session.save(corpCompanyData);

			if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				List<CorpCompanyMasterEntity> list = getAllCorpCompanyDetails();
				ObjectMapper mapper = new ObjectMapper();

				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
				adminData.setAppId(BigDecimal.valueOf(2));
				adminData.setCreatedByUserId(corpCompanyData.getUser_ID());
				adminData.setCreatedByRoleId(corpCompanyData.getRole_ID());
				adminData.setPageId(corpCompanyData.getSubMenu_ID());
				adminData.setCreatedBy(corpCompanyData.getCreatedBy());
				adminData.setContent(mapper.writeValueAsString(corpCompanyData));
				adminData.setActivityId(corpCompanyData.getSubMenu_ID());
				adminData.setStatusId(BigDecimal.valueOf(statusId));
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6));
				adminData.setRemark(corpCompanyData.getRemark());
				adminData.setActivityName(corpCompanyData.getActivityName());
				adminData.setActivityRefNo(list.get(0).getId());
				adminData.setTableName("CORP_COMPANY_MASTER");
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);

				// Save data to admin work flow history
				adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(corpCompanyData.getSubMenu_ID(), list.get(0).getId(),
						corpCompanyData.getCreatedBy(), corpCompanyData.getRemark(), corpCompanyData.getRole_ID(),
						mapper.writeValueAsString(corpCompanyData));
			}

			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}

	@Override
	public boolean updateCorpCompanyDetails(CorpCompanyMasterEntity corpCompanyData) {
		Session session = sessionFactory.getCurrentSession();
		int userStatus = corpCompanyData.getStatusId().intValue();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(corpCompanyData.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.CORP_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(corpCompanyData.getActivityName());
		try {
			if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				corpCompanyData.setStatusId(BigDecimal.valueOf(statusId));
			}
			session.update(corpCompanyData);

			if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

				ObjectMapper mapper = new ObjectMapper();

				List<AdminWorkFlowRequestEntity> AdminDataList = adminWorkFlowReqUtility
						.getAdminWorkFlowDataByActivityRefNo(corpCompanyData.getId().intValue(),
								corpCompanyData.getSubMenu_ID());

				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
				adminData.setCreatedOn(corpCompanyData.getCreatedOn());
				adminData.setCreatedByUserId(corpCompanyData.getUser_ID());
				adminData.setCreatedByRoleId(corpCompanyData.getRole_ID());
				adminData.setPageId(corpCompanyData.getSubMenu_ID());
				adminData.setCreatedBy(corpCompanyData.getCreatedBy());
				adminData.setContent(mapper.writeValueAsString(corpCompanyData));
				adminData.setActivityId(corpCompanyData.getSubMenu_ID());
				adminData.setStatusId(BigDecimal.valueOf(statusId));
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6));
				adminData.setRemark(corpCompanyData.getRemark());
				adminData.setActivityName(corpCompanyData.getActivityName());
				adminData.setActivityRefNo(corpCompanyData.getId());
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminData.setTableName("CORP_COMPANY_MASTER");

				if (ObjectUtils.isEmpty(AdminDataList)) {
					adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);
				} else if (!ObjectUtils.isEmpty(AdminDataList)) {
					adminData.setId(BigDecimal.valueOf(AdminDataList.get(0).getId().intValue()));
					adminWorkFlowReqUtility.updateAdminWorkFlowRequest(adminData);

				}

				// Save data to admin work flow history
				adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(corpCompanyData.getSubMenu_ID(),
						corpCompanyData.getId(), corpCompanyData.getCreatedBy(), corpCompanyData.getRemark(),
						corpCompanyData.getRole_ID(), mapper.writeValueAsString(corpCompanyData));

			} else {

				// if record is present in admin work flow then update status
				adminWorkFlowReqUtility.getCheckerDataByctivityRefId(corpCompanyData.getId().toBigInteger(),
						BigInteger.valueOf(userStatus), corpCompanyData.getSubMenu_ID());
			}
			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}

	@Override
	public List<CorpCompanyMasterEntity> getCorpCompanyDetailsByID(int id) {
		List<CorpCompanyMasterEntity> list = null;
		try {
			String sqlQuery = "select cc.id,cc.COMPANYCODE as companyCode,cc.COMPANYNAME as companyName, cc.SHORTNAME as shortName, cc.COMPANYINFO as companyInfo, cc.ESTABLISHMENTON as establishmentOn,"
					+ " cc.LOGO as logoImage, cc.STATUSID as statusId, cc.CREATEDBY as createdBy, cc.CREATEDON as createdOn, cc.CIF as cif,cc.approvalLevel ,"
					+ " cc.MAKER_LIMIT as makerLimit, cc.CHECKER_LIMIT as checkerLimit, s.name as statusName , aw.remark,aw.userAction from CORP_COMPANY_MASTER cc "
					+ " left join ADMINWORKFLOWREQUEST aw on aw.activityrefno=:id and aw.tablename='CORP_COMPANY_MASTER' "
					+ " inner join statusmaster s on s.id = cc.STATUSID  where cc.id=:id";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("companyCode").addScalar("companyName").addScalar("shortName")
					.addScalar("companyInfo", StandardBasicTypes.STRING)
					.addScalar("logoImage", StandardBasicTypes.STRING)
					.addScalar("statusId", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("createdBy", StandardBasicTypes.BIG_DECIMAL).addScalar("createdOn")
					.addScalar("userAction").addScalar("remark").addScalar("cif")
					.addScalar("makerLimit", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("checkerLimit", StandardBasicTypes.BIG_DECIMAL).addScalar("establishmentOn")
					.addScalar("approvalLevel").addScalar("statusName").setParameter("id", id)
					.setResultTransformer(Transformers.aliasToBean(CorpCompanyMasterEntity.class)).list();

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CorpCompanyMasterEntity> getAllCorpCompanyDetails() {
		List<CorpCompanyMasterEntity> list = null;
		try {
			String sqlQuery = "select cc.id,cc.COMPANYCODE as companyCode,cc.COMPANYNAME as companyName, cc.SHORTNAME as shortName, cc.COMPANYINFO as companyInfo, cc.ESTABLISHMENTON as establishmentOn,"
					+ " cc.LOGO as logoImage, cc.STATUSID as statusId, cc.CREATEDBY as createdBy, cc.CREATEDON as createdOn,cc.CIF as cif,"
					+ " cc.MAKER_LIMIT as makerLimit, cc.CHECKER_LIMIT as checkerLimit, s.name as statusName, cc.approvalLevel from CORP_COMPANY_MASTER cc "
					+ " inner join statusmaster s on s.id = cc.STATUSID  order by cc.id desc";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("companyCode").addScalar("companyName").addScalar("shortName")
					.addScalar("companyInfo", StandardBasicTypes.STRING)
					.addScalar("logoImage", StandardBasicTypes.STRING)
					.addScalar("statusId", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("createdBy", StandardBasicTypes.BIG_DECIMAL).addScalar("createdOn")
					.addScalar("establishmentOn").addScalar("cif")
					.addScalar("makerLimit", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("checkerLimit", StandardBasicTypes.BIG_DECIMAL).addScalar("statusName")
					.addScalar("approvalLevel")
					.setResultTransformer(Transformers.aliasToBean(CorpCompanyMasterEntity.class)).list();

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public List<CorpUserTypeEntity> getCorpUserTypes() {
		List<CorpUserTypeEntity> list = null;
		try {
			String sqlQuery = "select cu.id ,cu.USER_TYPE as USER_TYPE, cu.DESCRIPTION as DESCRIPTION, cu.CREATEDBY as CREATEDBY,cu.CREATEDON as CREATEDON,"
					+ "cu.STATUSID as STATUSID, cu.APPID as APPID, cu.RULE_SEQ as RULE_SEQ , s.name as statusName from CORP_USER_TYPES cu inner "
					+ "join statusmaster s on s.id= cu.STATUSID order by  cu.id desc ";
			list = getSession().createSQLQuery(sqlQuery).addScalar("id", StandardBasicTypes.BIG_INTEGER)
					.addScalar("USER_TYPE", StandardBasicTypes.STRING)
					.addScalar("DESCRIPTION", StandardBasicTypes.STRING)
					.addScalar("CREATEDBY", StandardBasicTypes.BIG_INTEGER).addScalar("CREATEDON")
					.addScalar("STATUSID", StandardBasicTypes.BIG_INTEGER)
					.addScalar("APPID", StandardBasicTypes.BIG_INTEGER)
					.addScalar("RULE_SEQ", StandardBasicTypes.BIG_INTEGER).addScalar("statusname")
					.setResultTransformer(Transformers.aliasToBean(CorpUserTypeEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public boolean addCorpUserTypes(CorpUserTypeEntity corpUserTypeData) {
		Session session = sessionFactory.getCurrentSession();
		int userStatus = corpUserTypeData.getSTATUSID().intValue();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(corpUserTypeData.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.CORP_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(corpUserTypeData.getActivityName());
		try {
			if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				corpUserTypeData.setSTATUSID(BigInteger.valueOf(statusId)); // 97-
																			// ADMIN_CHECKER_PENDIN
			}

			corpUserTypeData.setCREATEDON(new Date());
			session.save(corpUserTypeData);

			if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				List<CorpUserTypeEntity> list = getCorpUserTypes();
				ObjectMapper mapper = new ObjectMapper();

				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
				adminData.setAppId(BigDecimal.valueOf(2));
				adminData.setCreatedByUserId(corpUserTypeData.getUser_ID());
				adminData.setCreatedByRoleId(corpUserTypeData.getRole_ID());
				adminData.setPageId(corpUserTypeData.getSubMenu_ID());
				adminData.setCreatedBy(new BigDecimal(corpUserTypeData.getCREATEDBY()));
				adminData.setContent(mapper.writeValueAsString(corpUserTypeData));
				adminData.setActivityId(corpUserTypeData.getSubMenu_ID());
				adminData.setStatusId(BigDecimal.valueOf(statusId));
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6));
				adminData.setRemark(corpUserTypeData.getRemark());
				adminData.setActivityName(corpUserTypeData.getActivityName());
				adminData.setActivityRefNo(new BigDecimal(list.get(0).getId()));
				adminData.setTableName("CORP_USER_TYPES");
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);

				// Save data to admin work flow history
				adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(corpUserTypeData.getSubMenu_ID(),
						new BigDecimal(list.get(0).getId()), new BigDecimal(corpUserTypeData.getCREATEDBY()),
						corpUserTypeData.getRemark(), corpUserTypeData.getRole_ID(),
						mapper.writeValueAsString(corpUserTypeData));
			}

			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}

	@Override
	public boolean updateCorpUserTypes(CorpUserTypeEntity corpUserTypeData) {
		Session session = sessionFactory.getCurrentSession();
		int userStatus = corpUserTypeData.getSTATUSID().intValue();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(corpUserTypeData.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.CORP_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(corpUserTypeData.getActivityName());
		try {
			if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				corpUserTypeData.setSTATUSID(BigInteger.valueOf(statusId));
			}

			corpUserTypeData.setAPPID(BigInteger.valueOf(2));
			session.update(corpUserTypeData);

			if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

				ObjectMapper mapper = new ObjectMapper();

				List<AdminWorkFlowRequestEntity> AdminDataList = adminWorkFlowReqUtility
						.getAdminWorkFlowDataByActivityRefNo(corpUserTypeData.getId().intValue(),
								corpUserTypeData.getSubMenu_ID());

				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
				adminData.setAppId(BigDecimal.valueOf(2));
				adminData.setCreatedOn(corpUserTypeData.getCREATEDON());
				adminData.setCreatedByUserId(corpUserTypeData.getUser_ID());
				adminData.setCreatedByRoleId(corpUserTypeData.getRole_ID());
				adminData.setPageId(corpUserTypeData.getSubMenu_ID());
				adminData.setCreatedBy(new BigDecimal(corpUserTypeData.getCREATEDBY()));
				adminData.setContent(mapper.writeValueAsString(corpUserTypeData));
				adminData.setActivityId(corpUserTypeData.getSubMenu_ID());
				adminData.setStatusId(BigDecimal.valueOf(statusId));
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6));
				adminData.setRemark(corpUserTypeData.getRemark());
				adminData.setActivityName(corpUserTypeData.getActivityName());
				adminData.setActivityRefNo(new BigDecimal(corpUserTypeData.getId()));
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminData.setTableName("CORP_USER_TYPES");

				if (ObjectUtils.isEmpty(AdminDataList)) {
					adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);
				} else if (!ObjectUtils.isEmpty(AdminDataList)) {
					adminData.setId(BigDecimal.valueOf(AdminDataList.get(0).getId().intValue()));
					adminWorkFlowReqUtility.updateAdminWorkFlowRequest(adminData);

				}

				// Save data to admin work flow history
				adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(corpUserTypeData.getSubMenu_ID(),
						new BigDecimal(corpUserTypeData.getId()), new BigDecimal(corpUserTypeData.getCREATEDBY()),
						corpUserTypeData.getRemark(), corpUserTypeData.getRole_ID(),
						mapper.writeValueAsString(corpUserTypeData));

			} else {

				// if record is present in admin work flow then update status
				adminWorkFlowReqUtility.getCheckerDataByctivityRefId(corpUserTypeData.getId(),
						BigInteger.valueOf(userStatus), corpUserTypeData.getSubMenu_ID());
			}
			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}

	@Override
	public List<CorpUserTypeEntity> getCorpUserTypesById(int id) {
		List<CorpUserTypeEntity> list = null;
		try {
			String sqlQuery = "select cu.id ,cu.USER_TYPE as USER_TYPE, cu.DESCRIPTION as DESCRIPTION, cu.CREATEDBY as CREATEDBY,cu.CREATEDON as CREATEDON,"
					+ "cu.STATUSID as STATUSID, cu.APPID as APPID, cu.RULE_SEQ as RULE_SEQ , s.name as statusName, aw.remark,aw.userAction from CORP_USER_TYPES cu inner "
					+ "join statusmaster s on s.id= cu.STATUSID "
					+ "	left join ADMINWORKFLOWREQUEST aw on aw.activityrefno=:id and aw.tablename='CORP_USER_TYPES'  where cu.id =:id ";
			list = getSession().createSQLQuery(sqlQuery).addScalar("id", StandardBasicTypes.BIG_INTEGER)
					.addScalar("USER_TYPE", StandardBasicTypes.STRING)
					.addScalar("DESCRIPTION", StandardBasicTypes.STRING)
					.addScalar("CREATEDBY", StandardBasicTypes.BIG_INTEGER).addScalar("CREATEDON")
					.addScalar("STATUSID", StandardBasicTypes.BIG_INTEGER)
					.addScalar("APPID", StandardBasicTypes.BIG_INTEGER).addScalar("userAction").addScalar("remark")
					.addScalar("RULE_SEQ", StandardBasicTypes.BIG_INTEGER).addScalar("statusname")
					.setParameter("id", id).setResultTransformer(Transformers.aliasToBean(CorpUserTypeEntity.class))
					.list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public boolean addCorpUserTypeAccount(CorpAccUserTypeEntity corpUserAccData) {
		Session session = sessionFactory.getCurrentSession();
		int userStatus = corpUserAccData.getStatusid().intValue();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(corpUserAccData.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.CORP_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(corpUserAccData.getActivityName());
		try {
			if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				corpUserAccData.setStatusid(BigDecimal.valueOf(statusId));
			}

			corpUserAccData.setCreatedon(new Date());
			session.save(corpUserAccData);

			if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				List<CorpAccUserTypeEntity> list = getAllCorpUserTypeAccount();
				ObjectMapper mapper = new ObjectMapper();

				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
				adminData.setAppId(BigDecimal.valueOf(2));
				adminData.setCreatedByUserId(corpUserAccData.getUser_ID());
				adminData.setCreatedByRoleId(corpUserAccData.getRole_ID());
				adminData.setPageId(corpUserAccData.getSubMenu_ID());
				adminData.setCreatedBy(corpUserAccData.getCreatedby());
				adminData.setContent(mapper.writeValueAsString(corpUserAccData));
				adminData.setActivityId(corpUserAccData.getSubMenu_ID());
				adminData.setStatusId(BigDecimal.valueOf(statusId));
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6));
				adminData.setRemark(corpUserAccData.getRemark());
				adminData.setActivityName(corpUserAccData.getActivityName());
				adminData.setActivityRefNo(list.get(0).getId());
				adminData.setTableName("CORP_ACC_USER_TYPE");
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);

				// Save data to admin work flow history
				adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(corpUserAccData.getSubMenu_ID(), list.get(0).getId(),
						corpUserAccData.getCreatedby(), corpUserAccData.getRemark(), corpUserAccData.getRole_ID(),
						mapper.writeValueAsString(corpUserAccData));
			}

			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}

	@Override
	public boolean updateCorpUserTypeAccount(CorpAccUserTypeEntity corpUserAccData) {
		Session session = sessionFactory.getCurrentSession();
		int userStatus = corpUserAccData.getStatusid().intValue();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(corpUserAccData.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.CORP_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(corpUserAccData.getActivityName());
		try {
			if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				corpUserAccData.setStatusid(BigDecimal.valueOf(statusId));
			}

			session.update(corpUserAccData);

			if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

				ObjectMapper mapper = new ObjectMapper();

				List<AdminWorkFlowRequestEntity> AdminDataList = adminWorkFlowReqUtility
						.getAdminWorkFlowDataByActivityRefNo(corpUserAccData.getId().intValue(),
								corpUserAccData.getSubMenu_ID());

				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
				adminData.setAppId(BigDecimal.valueOf(2));
				adminData.setCreatedOn(corpUserAccData.getCreatedon());
				adminData.setCreatedByUserId(corpUserAccData.getUser_ID());
				adminData.setCreatedByRoleId(corpUserAccData.getRole_ID());
				adminData.setPageId(corpUserAccData.getSubMenu_ID());
				adminData.setCreatedBy(corpUserAccData.getCreatedby());
				adminData.setContent(mapper.writeValueAsString(corpUserAccData));
				adminData.setActivityId(corpUserAccData.getSubMenu_ID());
				adminData.setStatusId(BigDecimal.valueOf(statusId));
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6));
				adminData.setRemark(corpUserAccData.getRemark());
				adminData.setActivityName(corpUserAccData.getActivityName());
				adminData.setActivityRefNo(corpUserAccData.getId());
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminData.setTableName("CORP_ACC_USER_TYPE");

				if (ObjectUtils.isEmpty(AdminDataList)) {
					adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);
				} else if (!ObjectUtils.isEmpty(AdminDataList)) {
					adminData.setId(BigDecimal.valueOf(AdminDataList.get(0).getId().intValue()));
					adminWorkFlowReqUtility.updateAdminWorkFlowRequest(adminData);

				}
				// Save data to admin work flow history
				adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(corpUserAccData.getSubMenu_ID(),
						corpUserAccData.getId(), corpUserAccData.getCreatedby(), corpUserAccData.getRemark(),
						corpUserAccData.getRole_ID(), mapper.writeValueAsString(corpUserAccData));

			} else {

				// if record is present in admin work flow then update status
				adminWorkFlowReqUtility.getCheckerDataByctivityRefId(corpUserAccData.getId().toBigInteger(),
						BigInteger.valueOf(userStatus), corpUserAccData.getSubMenu_ID());
			}
			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}

	@Override
	public List<CorpAccUserTypeEntity> getAllCorpUserTypeAccount() {
		List<CorpAccUserTypeEntity> list = null;
		try {
			String sqlQuery = "select ca.id, ca.account_type_id as accountTypeId, ca.corp_user_type_id as corpUserTypeId,"
					+ "ca.createdby ,ca.createdon,ca.statusId as statusid,s.name as statusName,"
					+ " cu.user_type as userType, am.account_type as accountType from corp_Acc_user_type ca inner join CORP_USER_TYPES cu "
					+ " on cu.id=ca.corp_user_type_id  inner join account_type_master_prd am on am.id=ca.account_type_id "
					+ " inner join STATUSMASTER s on s.id=ca.statusId order by ca.createdon desc";
			list = getSession().createSQLQuery(sqlQuery).addScalar("id", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("accountTypeId", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("corpUserTypeId", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("createdby", StandardBasicTypes.BIG_DECIMAL).addScalar("createdon")
					.addScalar("statusid", StandardBasicTypes.BIG_DECIMAL).addScalar("statusName").addScalar("userType")
					.addScalar("accountType")
					.setResultTransformer(Transformers.aliasToBean(CorpAccUserTypeEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return list;

	}

	@Override
	public List<CorpAccUserTypeEntity> getCorpUserTypeAccountById(int id) {
		List<CorpAccUserTypeEntity> list = null;
		try {
			String sqlQuery = "select ca.id, ca.account_type_id as accountTypeId, ca.corp_user_type_id as corpUserTypeId,"
					+ "ca.createdby ,ca.createdon,ca.statusId as statusid,s.name as statusName,"
					+ " cu.user_type as userType, am.account_type as accountType,aw.remark,aw.userAction from corp_Acc_user_type ca inner join CORP_USER_TYPES cu "
					+ " on cu.id=ca.corp_user_type_id  inner join account_type_master_prd am on am.id=ca.account_type_id "
					+ " left join ADMINWORKFLOWREQUEST aw on aw.activityrefno=:id and aw.tablename='CORP_ACC_USER_TYPE'  "
					+ " inner join STATUSMASTER s on s.id=ca.statusId where ca.id=:id";
			list = getSession().createSQLQuery(sqlQuery).addScalar("id", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("accountTypeId", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("corpUserTypeId", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("createdby", StandardBasicTypes.BIG_DECIMAL).addScalar("createdon")
					.addScalar("statusid", StandardBasicTypes.BIG_DECIMAL).addScalar("statusName").addScalar("userType")
					.addScalar("accountType").addScalar("remark")
					.addScalar("userAction", StandardBasicTypes.BIG_DECIMAL).setParameter("id", id)
					.setResultTransformer(Transformers.aliasToBean(CorpAccUserTypeEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return list;

	}

	@Override
	public Boolean addCorpCompanyMenu(CorpCompanyMenuMappingEntity corpCompMenuMappingData) {
		Session session = sessionFactory.getCurrentSession();
		int userStatus = corpCompMenuMappingData.getStatusId().intValue();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(corpCompMenuMappingData.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.CORP_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(corpCompMenuMappingData.getActivityName());

		try {
			if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				corpCompMenuMappingData.setStatusId(BigDecimal.valueOf(statusId)); // 97-
																					// ADMIN_CHECKER_PENDIN
			}
			corpCompMenuMappingData.setCreatedon(new Date());
			corpCompMenuMappingData.setUpdatedon(new Date());
			session.save(corpCompMenuMappingData);

			if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				List<CorpCompanyMenuMappingEntity> list = getAllCorpCompanyMenu();
				corpCompMenuMappingData.setCompanyName(list.get(0).getCompanyName());
				corpCompMenuMappingData.setMenuName(list.get(0).getMenuName());
				ObjectMapper mapper = new ObjectMapper();

				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
				adminData.setAppId(BigDecimal.valueOf(2));
				adminData.setCreatedByUserId(corpCompMenuMappingData.getUser_ID());
				adminData.setCreatedByRoleId(corpCompMenuMappingData.getRole_ID());
				adminData.setPageId(corpCompMenuMappingData.getSubMenu_ID());
				adminData.setCreatedBy(corpCompMenuMappingData.getCreatedby());
				adminData.setContent(mapper.writeValueAsString(corpCompMenuMappingData));
				adminData.setActivityId(corpCompMenuMappingData.getSubMenu_ID());
				adminData.setStatusId(BigDecimal.valueOf(statusId));
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6));
				adminData.setRemark(corpCompMenuMappingData.getRemark());
				adminData.setActivityName(corpCompMenuMappingData.getActivityName());
				adminData.setActivityRefNo(list.get(0).getId());
				adminData.setTableName("CORP_COMP_MENU_MAPPING");
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);

				// Save data to admin work flow history
				adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(corpCompMenuMappingData.getSubMenu_ID(),
						list.get(0).getId(), corpCompMenuMappingData.getCreatedby(),
						corpCompMenuMappingData.getRemark(), corpCompMenuMappingData.getRole_ID(),
						mapper.writeValueAsString(corpCompMenuMappingData));
			}

			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}

	@Override
	public List<CorpCompanyMenuMappingEntity> getCorpCompanyMenuListById(int id) {
		List<CorpCompanyMenuMappingEntity> list = null;
		try {
			String sqlQuery = "select cm.id as id, cm.company_Id as companyId, cm.createdon as createdon, cm.updatedon as updatedon , cm.createdby as createdby, um.userid as createdByName, "
					+ " cm.updatedby as updatedby, cm.statusId as statusId, cm.corp_Menu_Id as corpMenuId,c.menuname  as menuName , ccm.companyname as companyName,aw.remark,aw.userAction from CORP_COMP_MENU_MAPPING  cm "
					+ " inner join CORP_COMPANY_MASTER ccm on ccm.id=cm.company_Id "
					+ " left join ADMINWORKFLOWREQUEST aw on aw.activityrefno= cm.id and aw.tablename='CORP_COMP_MENU_MAPPING'  "
					+ "inner join CORP_MENU c on c.id=cm.corp_Menu_Id  inner join USER_MASTER  um on um.id = cm.createdby where cm.id =:id";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("companyId", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("createdby", StandardBasicTypes.BIG_DECIMAL).addScalar("createdon")
					.addScalar("updatedon").addScalar("updatedby", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("statusId", StandardBasicTypes.BIG_DECIMAL).addScalar("createdByName")
					.addScalar("corpMenuId", StandardBasicTypes.BIG_DECIMAL).addScalar("menuName")
					.addScalar("companyName").addScalar("remark")
					.addScalar("userAction", StandardBasicTypes.BIG_DECIMAL).setParameter("id", id)
					.setResultTransformer(Transformers.aliasToBean(CorpCompanyMenuMappingEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public List<CorpCompanyMenuMappingEntity> getCorpCompanyMenuListByCompanyId(int Compid) {
		List<CorpCompanyMenuMappingEntity> list = null;
		try {
			String sqlQuery = "select cm.id as id, cm.company_Id as companyId, cm.createdon as createdon, cm.updatedon as updatedon , cm.createdby as createdby, um.userid as createdByName, "
					+ " cm.updatedby as updatedby, cm.statusId as statusId, cm.corp_Menu_Id as corpMenuId,c.menuname  menuName  ,aw.remark,aw.userAction from CORP_COMP_MENU_MAPPING cm "
					+ " left join ADMINWORKFLOWREQUEST aw on aw.activityrefno= cm.id and aw.tablename='CORP_COMP_MENU_MAPPING'  "
					+ "inner join CORP_MENU c on c.id=cm.corp_Menu_Id  inner join USER_MASTER  um on um.id = cm.createdby where cm.company_Id =:Compid and cm.statusId =3";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("companyId", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("createdby", StandardBasicTypes.BIG_DECIMAL).addScalar("createdon")
					.addScalar("updatedon").addScalar("updatedby", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("statusId", StandardBasicTypes.BIG_DECIMAL).addScalar("createdByName")
					.addScalar("corpMenuId", StandardBasicTypes.BIG_DECIMAL).addScalar("menuName").addScalar("remark")
					.addScalar("userAction", StandardBasicTypes.BIG_DECIMAL).setParameter("Compid", Compid)
					.setResultTransformer(Transformers.aliasToBean(CorpCompanyMenuMappingEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public List<CorpMenuMapping> getCorpMenuRightsForRoleId(int companyId, int roleId) {
		List<CorpMenuMapping> list = null;
		try {
			String sqlQuery = "select cm.id,cm.MENUID as menuId,cm.statusId as statusId,cm.createdon as createdon ,cm.updatedon as updatedon,"
					+ "cm.createdby as createdby,cm.updatedby as updatedby,"
					+ "cm.roleid as roleid,cm.corporatecompid as corporatecompid,c.menuname as menuName from "
					+ " CORP_MENU_MAPPING cm inner join CORP_MENU c on c.id=cm.menuId "
					+ "where cm.roleid=:roleId and cm.corporatecompid=:companyId";
			list = getSession().createSQLQuery(sqlQuery).addScalar("id", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("menuId", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("createdby", StandardBasicTypes.BIG_DECIMAL).addScalar("createdon")
					.addScalar("updatedon").addScalar("updatedby", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("statusId", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("roleid", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("corporatecompid", StandardBasicTypes.BIG_DECIMAL).addScalar("menuName")
					.setParameter("companyId", companyId).setParameter("roleId", roleId)
					.setResultTransformer(Transformers.aliasToBean(CorpMenuMapping.class)).list();

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public boolean addCorpUser(CorpUserEntity corpUserData) {

		Session session = sessionFactory.getCurrentSession();
		int userStatus = corpUserData.getStatusid().intValue();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(corpUserData.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.CORP_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(corpUserData.getActivityName());

		try {
			if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				corpUserData.setStatusid(BigDecimal.valueOf(statusId));
			}

			corpUserData.setCreatedon(new Timestamp(System.currentTimeMillis()));
			session.save(corpUserData);

			if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				List<CorpUserEntity> list = getAllCorpUsers();
				ObjectMapper mapper = new ObjectMapper();

				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
				adminData.setAppId(BigDecimal.valueOf(2));
				adminData.setCreatedByUserId(corpUserData.getUser_ID());
				adminData.setCreatedByRoleId(corpUserData.getRole_ID());
				adminData.setPageId(corpUserData.getSubMenu_ID());
				adminData.setCreatedBy(corpUserData.getCreatedby());
				adminData.setContent(mapper.writeValueAsString(corpUserData));
				adminData.setActivityId(corpUserData.getSubMenu_ID());
				adminData.setStatusId(BigDecimal.valueOf(statusId));
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6));
				adminData.setRemark(corpUserData.getRemark());
				adminData.setActivityName(corpUserData.getActivityName());
				adminData.setActivityRefNo(list.get(0).getId());
				adminData.setTableName("CORP_USERS");
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);

				// Save data to admin work flow history
				adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(corpUserData.getSubMenu_ID(), list.get(0).getId(),
						corpUserData.getCreatedby(), corpUserData.getRemark(), corpUserData.getRole_ID(),
						mapper.writeValueAsString(corpUserData));
			}

			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}

	@Override
	public boolean updateCorpUser(CorpUserEntity corpUserData) {
		Session session = sessionFactory.getCurrentSession();
		int userStatus = corpUserData.getStatusid().intValue();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(corpUserData.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.CORP_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(corpUserData.getActivityName());
		try {
			if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				corpUserData.setStatusid(BigDecimal.valueOf(statusId));
			}

			List<CorpUserEntity> corpUserObj = getCorpUserById(corpUserData.getId().intValue());
			corpUserData.setUser_pwd(corpUserObj.get(0).getUser_pwd());
			corpUserData.setEmail_id(corpUserObj.get(0).getEmail_id());
			corpUserData.setWork_phone(EncryptorDecryptor.encryptData(corpUserData.getWork_phone()));
			corpUserData.setPersonal_Phone(EncryptorDecryptor.encryptData(corpUserData.getPersonal_Phone()));
			session.update(corpUserData);

			if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

				ObjectMapper mapper = new ObjectMapper();

				List<AdminWorkFlowRequestEntity> AdminDataList = adminWorkFlowReqUtility
						.getAdminWorkFlowDataByActivityRefNo(corpUserData.getId().intValue(),
								corpUserData.getSubMenu_ID());

				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
				adminData.setAppId(corpUserData.getAppid());
				adminData.setCreatedOn(corpUserData.getCreatedon());
				adminData.setCreatedByUserId(corpUserData.getUser_ID());
				adminData.setCreatedByRoleId(corpUserData.getRole_ID());
				adminData.setPageId(corpUserData.getSubMenu_ID());
				adminData.setCreatedBy(corpUserData.getCreatedby());
				adminData.setContent(mapper.writeValueAsString(corpUserData));
				adminData.setActivityId(corpUserData.getSubMenu_ID());
				adminData.setStatusId(BigDecimal.valueOf(statusId));
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6));
				adminData.setRemark(corpUserData.getRemark());
				adminData.setActivityName(corpUserData.getActivityName());
				adminData.setActivityRefNo(corpUserData.getId());
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminData.setTableName("CORP_USERS");

				if (ObjectUtils.isEmpty(AdminDataList)) {
					adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);
				} else if (!ObjectUtils.isEmpty(AdminDataList)) {
					adminData.setId(BigDecimal.valueOf(AdminDataList.get(0).getId().intValue()));
					adminWorkFlowReqUtility.updateAdminWorkFlowRequest(adminData);

				}
				// Save data to admin work flow history
				adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(corpUserData.getSubMenu_ID(), corpUserData.getId(),
						corpUserData.getCreatedby(), corpUserData.getRemark(), corpUserData.getRole_ID(),
						mapper.writeValueAsString(corpUserData));

			} else {

				// if record is present in admin work flow then update status
				adminWorkFlowReqUtility.getCheckerDataByctivityRefId(corpUserData.getId().toBigInteger(),
						BigInteger.valueOf(userStatus), corpUserData.getSubMenu_ID());
			}
			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}

	@Override
	public List<CorpUserEntity> getCorpUserById(int id) {
		List<CorpUserEntity> list = null;
		try {
			String sqlQuery = "select cu.id,cu.designation,ccm.id AS designationId,cu.corp_comp_id,cu.user_disp_name,cu.user_name,cu.user_pwd,cu.createdby,cu.createdon,cu.statusid,"
					+ "cu.appid,cu.lastLoginTime,cu.user_type,cu.first_name,cu.last_name,cu.email_id,cu.country,cu.work_phone,cu.personal_Phone,"
					+ "cu.nationalId,cu.passport,cu.boardResolution,cu.user_image,cu.tpin,cu.passportNumber,cu.nationalIdNumber,cu.pancardNumber as pancardNumber,"
					+ "cu.tpin_status,cu.tpin_wrong_attempt,cu.city,cu.wrong_pwd_attempt,cu.pwd_status,cu.otherDoc,cu.certificate_incorporation,"
					+ "cu.state , ccm.Companyname as companyName, ut.user_type as userType ,aw.remark,aw.userAction ,cm.name as countryName, "
					+ " ci.CITYNAME as cityName ,sm.shortname as stateName,dh.HIERARCHYLEVEL as hierarchyLevel ,dh.DESIGNATIONNAME as designationName from CORP_USERS cu "
					+ "left join CORP_COMPANY_MASTER ccm on ccm.id= cu.corp_comp_id left join CORP_USER_TYPES ut on ut.id=cu.user_type "
					+ " left join ADMINWORKFLOWREQUEST aw on aw.activityrefno=:id and aw.tablename='CORP_USERS'  "
					+ " left join DESIGNATION_HIERARCHY_MASTER dh on dh.COMPANYID=cu.corp_comp_id AND dh.id =cu.designation "
					+ "inner join COUNTRYMASTER cm on cm.id=cu.country inner join statemaster sm on sm.id=cu.state inner join  citiesmaster ci on ci.id=cu.city  "
					+ "where cu.id=:id ";
			list = getSession().createSQLQuery(sqlQuery).addScalar("id", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("designation", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("designationId", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("corp_comp_id", StandardBasicTypes.BIG_DECIMAL).addScalar("user_disp_name")
					.addScalar("user_name").addScalar("user_pwd", StandardBasicTypes.STRING)
					.addScalar("createdby", StandardBasicTypes.BIG_DECIMAL).addScalar("createdon")
					.addScalar("statusid", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("appid", StandardBasicTypes.BIG_DECIMAL).addScalar("lastLoginTime")
					.addScalar("user_type", StandardBasicTypes.BIG_DECIMAL).addScalar("first_name")
					.addScalar("last_name").addScalar("email_id").addScalar("country", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("work_phone").addScalar("personal_Phone")
					.addScalar("nationalId", StandardBasicTypes.STRING).addScalar("passport", StandardBasicTypes.STRING)
					.addScalar("boardResolution", StandardBasicTypes.STRING)
					.addScalar("user_image", StandardBasicTypes.STRING).addScalar("tpin", StandardBasicTypes.STRING)
					.addScalar("passportNumber").addScalar("nationalIdNumber").addScalar("pancardNumber")
					.addScalar("tpin_status").addScalar("tpin_wrong_attempt", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("city", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("wrong_pwd_attempt", StandardBasicTypes.BIG_DECIMAL).addScalar("pwd_status")
					.addScalar("otherDoc", StandardBasicTypes.STRING)
					.addScalar("certificate_incorporation", StandardBasicTypes.STRING)
					.addScalar("state", StandardBasicTypes.BIG_DECIMAL).addScalar("companyName").addScalar("userType")
					.addScalar("remark").addScalar("userAction", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("countryName").addScalar("cityName").addScalar("stateName").addScalar("hierarchyLevel")
					.addScalar("designationName").setParameter("id", id)
					.setResultTransformer(Transformers.aliasToBean(CorpUserEntity.class)).list();

			for (CorpUserEntity corpData : list) {
				if (corpData.getWork_phone().contains("=")) {
					corpData.setWork_phone(EncryptorDecryptor.decryptData(corpData.getWork_phone()));
				}
				if (corpData.getPersonal_Phone().contains("=")) {
					corpData.setPersonal_Phone(EncryptorDecryptor.decryptData(corpData.getPersonal_Phone()));
				}
				if (corpData.getEmail_id().contains("=")) {
					corpData.setEmail_id(EncryptorDecryptor.decryptData(corpData.getEmail_id()));
				}
				if (corpData.getPancardNumber().contains("=")) {
					corpData.setPancardNumber(EncryptorDecryptor.decryptData(corpData.getPancardNumber()));
				}
			}

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public List<CorpUserEntity> getAllCorpUsers() {
		List<CorpUserEntity> list = null;
		try {
			String sqlQuery = "select cu.id,cu.designation,cu.corp_comp_id,cu.user_disp_name,cu.user_name,cu.user_pwd,cu.createdby,cu.createdon,cu.statusid,"
					+ "cu.appid,cu.lastLoginTime,cu.user_type,cu.first_name,cu.last_name,cu.email_id,cu.country,cu.work_phone,cu.personal_Phone,"
					+ "cu.nationalId,cu.passport,cu.boardResolution,cu.user_image,cu.tpin,cu.passportNumber,cu.nationalIdNumber,cu.pancardNumber as pancardNumber,"
					+ "cu.tpin_status,cu.tpin_wrong_attempt,cu.city,cu.wrong_pwd_attempt,cu.pwd_status,cu.otherDoc,cu.certificate_incorporation,"
					+ "cu.state , ccm.Companyname as companyName, ut.user_type as userType,aw.remark,aw.userAction,s.name as statusName,dh.DESIGNATIONNAME as designationName  from CORP_USERS cu "
					+ "left join CORP_COMPANY_MASTER ccm on ccm.id= cu.corp_comp_id left join CORP_USER_TYPES ut on ut.id=cu.user_type "
					+ " left join DESIGNATION_HIERARCHY_MASTER dh on dh.id= cu.designation "
					+ "inner join statusmaster s on s.id = cu.statusid "
					+ "left join ADMINWORKFLOWREQUEST aw on aw.activityrefno=cu.id and aw.tablename='CORP_USERS' "
					+ " order by cu.id desc ";
			list = getSession().createSQLQuery(sqlQuery).addScalar("id", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("designation", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("corp_comp_id", StandardBasicTypes.BIG_DECIMAL).addScalar("user_disp_name")
					.addScalar("user_name").addScalar("user_pwd", StandardBasicTypes.STRING)
					.addScalar("createdby", StandardBasicTypes.BIG_DECIMAL).addScalar("createdon")
					.addScalar("statusid", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("appid", StandardBasicTypes.BIG_DECIMAL).addScalar("lastLoginTime")
					.addScalar("user_type", StandardBasicTypes.BIG_DECIMAL).addScalar("first_name")
					.addScalar("last_name").addScalar("email_id").addScalar("country", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("work_phone").addScalar("personal_Phone")
					.addScalar("nationalId", StandardBasicTypes.STRING).addScalar("passport", StandardBasicTypes.STRING)
					.addScalar("boardResolution", StandardBasicTypes.STRING)
					.addScalar("user_image", StandardBasicTypes.STRING).addScalar("tpin", StandardBasicTypes.STRING)
					.addScalar("passportNumber").addScalar("nationalIdNumber").addScalar("pancardNumber")
					.addScalar("tpin_status").addScalar("tpin_wrong_attempt", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("city", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("wrong_pwd_attempt", StandardBasicTypes.BIG_DECIMAL).addScalar("pwd_status")
					.addScalar("otherDoc", StandardBasicTypes.STRING)
					.addScalar("certificate_incorporation", StandardBasicTypes.STRING)
					.addScalar("state", StandardBasicTypes.BIG_DECIMAL).addScalar("companyName").addScalar("userType")
					.addScalar("remark").addScalar("userAction", StandardBasicTypes.BIG_DECIMAL).addScalar("statusName")
					.addScalar("designationName").setResultTransformer(Transformers.aliasToBean(CorpUserEntity.class))
					.list();

			try {
				for (CorpUserEntity corpData : list) {
					corpData.setWork_phone(EncryptorDecryptor.decryptData(corpData.getWork_phone()));
					corpData.setPersonal_Phone(EncryptorDecryptor.decryptData(corpData.getPersonal_Phone()));
					corpData.setEmail_id(EncryptorDecryptor.decryptData(corpData.getEmail_id()));
					corpData.setPancardNumber(EncryptorDecryptor.decryptData(corpData.getPancardNumber()));

					/*
					 * if (corpData.getWork_phone().contains("=")) {
					 * 
					 * } if (corpData.getPersonal_Phone().contains("=")) {
					 * 
					 * } if (corpData.getEmail_id().contains("=")) {
					 * 
					 * } if (corpData.getPancardNumber().contains("=")) {
					 */

				}
			} catch (Exception e) {
				LOGGER.info("Exception:", e);
			}

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public Boolean updateCorpCompanyMenu(CorpCompanyMenuMappingEntity corpCompMenuData) {
		Session session = sessionFactory.getCurrentSession();
		int userStatus = corpCompMenuData.getStatusId().intValue();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(corpCompMenuData.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.CORP_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(corpCompMenuData.getActivityName());
		try {
			if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				corpCompMenuData.setStatusId(BigDecimal.valueOf(statusId));
			}
			corpCompMenuData.setUpdatedon(new Date());
			session.update(corpCompMenuData);

			if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

				ObjectMapper mapper = new ObjectMapper();

				List<CorpCompanyMenuMappingEntity> list = getCorpCompanyMenuListById(
						corpCompMenuData.getId().intValue());
				corpCompMenuData.setCompanyName(list.get(0).getCompanyName());
				corpCompMenuData.setMenuName(list.get(0).getMenuName());
				List<AdminWorkFlowRequestEntity> AdminDataList = adminWorkFlowReqUtility
						.getAdminWorkFlowDataByActivityRefNo(corpCompMenuData.getId().intValue(),
								corpCompMenuData.getSubMenu_ID());

				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
				adminData.setAppId(BigDecimal.valueOf(2));
				adminData.setCreatedOn(corpCompMenuData.getCreatedon());
				adminData.setCreatedByUserId(corpCompMenuData.getUser_ID());
				adminData.setCreatedByRoleId(corpCompMenuData.getRole_ID());
				adminData.setPageId(corpCompMenuData.getSubMenu_ID());
				adminData.setCreatedBy(corpCompMenuData.getCreatedby());
				adminData.setContent(mapper.writeValueAsString(corpCompMenuData));
				adminData.setActivityId(corpCompMenuData.getSubMenu_ID());
				adminData.setStatusId(BigDecimal.valueOf(statusId));
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6));
				adminData.setRemark(corpCompMenuData.getRemark());
				adminData.setActivityName(corpCompMenuData.getActivityName());
				adminData.setActivityRefNo(corpCompMenuData.getId());
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminData.setTableName("CORP_COMP_MENU_MAPPING");

				if (ObjectUtils.isEmpty(AdminDataList)) {
					adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);
				} else if (!ObjectUtils.isEmpty(AdminDataList)) {
					adminData.setId(BigDecimal.valueOf(AdminDataList.get(0).getId().intValue()));
					adminWorkFlowReqUtility.updateAdminWorkFlowRequest(adminData);

				}
				// Save data to admin work flow history
				adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(corpCompMenuData.getSubMenu_ID(),
						corpCompMenuData.getId(), corpCompMenuData.getCreatedby(), corpCompMenuData.getRemark(),
						corpCompMenuData.getRole_ID(), mapper.writeValueAsString(corpCompMenuData));

			} else {

				// if record is present in admin work flow then update status
				adminWorkFlowReqUtility.getCheckerDataByctivityRefId(corpCompMenuData.getId().toBigInteger(),
						BigInteger.valueOf(userStatus), corpCompMenuData.getSubMenu_ID());
			}
			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}

	@Override
	public List<CorpCompanyMenuMappingEntity> getAllCorpCompanyMenu() {
		List<CorpCompanyMenuMappingEntity> list = null;
		try {
			String sqlQuery = "select cu.id ,cu.company_Id as companyId, cu.corp_Menu_Id as corpMenuId, cu.createdby as createdby,"
					+ "cu.createdon as createdon, cu.statusId as statusId, cu.updatedon as updatedon, cu.updatedby as updatedby, cm.companyname as companyName, cmm.menuName as menuName, "
					+ "s.name as statusName,um.USERID as createdByName from CORP_COMP_MENU_MAPPING cu inner join CORP_COMPANY_MASTER cm on cm.id=cu.company_Id"
					+ " left join user_master um on cu.createdby = um.id "
					+ "inner join statusmaster s on s.id=cu.statusId "
					+ " inner join CORP_MENU cmm on cmm.id=cu.corp_Menu_Id order by cu.id desc ";
			list = getSession().createSQLQuery(sqlQuery).addScalar("id", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("companyId", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("corpMenuId", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("createdby", StandardBasicTypes.BIG_DECIMAL).addScalar("createdon")
					.addScalar("statusId", StandardBasicTypes.BIG_DECIMAL).addScalar("updatedon")
					.addScalar("updatedby", StandardBasicTypes.BIG_DECIMAL).addScalar("companyName")
					.addScalar("menuName").addScalar("statusName").addScalar("createdByName")
					.setResultTransformer(Transformers.aliasToBean(CorpCompanyMenuMappingEntity.class)).list();

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public List<CorpCompanyMenuMappingEntity> getAllCorpCompanyMenuByCompId(CorpCompanyMenuMappingEntity commReqData) {
		List<CorpCompanyMenuMappingEntity> list = null;
		try {
			String sqlQuery = "select cu.id ,cu.company_Id as companyId, cu.corp_Menu_Id as corpMenuId, cu.createdby as createdby,"
					+ "cu.createdon as createdon, cu.statusId as statusId, cu.updatedon as updatedon, cu.updatedby as updatedby, cm.companyname as companyName, cmm.menuName as menuName, "
					+ "s.name as statusName,um.USERID as createdByName from CORP_COMP_MENU_MAPPING cu inner join CORP_COMPANY_MASTER cm on cm.id=cu.company_Id"
					+ " left join user_master um on cu.createdby = um.id "
					+ "left join statusmaster s on s.id=cu.statusId "
					+ " left join CORP_MENU cmm on cmm.id=cu.corp_Menu_Id where cu.company_Id=:compId order by cu.id desc ";
			list = getSession().createSQLQuery(sqlQuery).addScalar("id", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("companyId", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("corpMenuId", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("createdby", StandardBasicTypes.BIG_DECIMAL).addScalar("createdon")
					.addScalar("statusId", StandardBasicTypes.BIG_DECIMAL).addScalar("updatedon")
					.addScalar("updatedby", StandardBasicTypes.BIG_DECIMAL).addScalar("companyName")
					.addScalar("menuName").addScalar("statusName").addScalar("createdByName")
					.setParameter("compId", commReqData.getCompanyId())
					.setResultTransformer(Transformers.aliasToBean(CorpCompanyMenuMappingEntity.class)).list();

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public ResponseMessageBean checkIsUserExist(CorpUserEntity corpUserData) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		// String encrypUserName =
		// EncryptDeryptUtility.md5(corpUserData.getTempUserName());
		try {
			String sqlEmailExist = " SELECT count(*) FROM CORP_USERS WHERE Lower(EMAIL_ID) =:email AND CORP_COMP_ID=:compId";

			String sqlUserNameExist = " SELECT count(*) FROM CORP_USERS WHERE Lower(USER_NAME) =:encrypUserName ";

			List corpEmailExit = getSession().createSQLQuery(sqlEmailExist)
					.setParameter("compId", corpUserData.getCorp_comp_id())
					.setParameter("email", corpUserData.getEmail_id().toLowerCase()).list();

			List corpUserNameExit = getSession().createSQLQuery(sqlUserNameExist)
					.setParameter("encrypUserName", corpUserData.getUser_name().toLowerCase()).list();

			if (!(corpEmailExit.get(0).toString().equalsIgnoreCase("0"))) {
				rmb.setResponseCode("451");
				rmb.setResponseMessage("Email Is Already Exist");
			} else if (!(corpUserNameExit.get(0).toString().equalsIgnoreCase("0"))) {
				rmb.setResponseCode("451");
				rmb.setResponseMessage("User Name Is Already Exist");
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
	public ResponseMessageBean checkUpdateUserExist(CorpUserEntity corpUserData) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		String encrypUserName = EncryptDeryptUtility.md5(corpUserData.getTempUserName());
		try {
			String sqlEmailExist = " SELECT count(*) FROM CORP_USERS WHERE Lower(EMAIL_ID) =:email AND CORP_COMP_ID=:compId AND id !=:id";

			String sqlUserNameExist = " SELECT count(*) FROM CORP_USERS WHERE Lower(USER_NAME) =:encrypUserName AND id !=:id ";

			List corpEmailExit = getSession().createSQLQuery(sqlEmailExist)
					.setParameter("compId", corpUserData.getCorp_comp_id()).setParameter("id", corpUserData.getId())
					.setParameter("email", corpUserData.getEmail_id().toLowerCase()).list();

			List corpUserNameExit = getSession().createSQLQuery(sqlUserNameExist)
					.setParameter("id", corpUserData.getId())
					.setParameter("encrypUserName", corpUserData.getUser_name().toLowerCase()).list();

			if (!(corpEmailExit.get(0).toString().equalsIgnoreCase("0"))) {
				rmb.setResponseCode("451");
				rmb.setResponseMessage("Email Is Already Exist");
			} else if (!(corpUserNameExit.get(0).toString().equalsIgnoreCase("0"))) {
				rmb.setResponseCode("451");
				rmb.setResponseMessage("User Name Is Already  Exist");
			} else {
				rmb.setResponseCode("200");
				rmb.setResponseMessage("Both Not Exist");
			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return rmb;
	}

	public CorpUserEntity getCorpUserDetails(int id) {
		CorpUserEntity list = null;
		try {
			String sqlQuery = "select cu.id,cu.designation,cu.corp_comp_id,cu.user_disp_name,cu.user_name,cu.user_pwd,cu.createdby,cu.createdon,cu.statusid,"
					+ "cu.appid,cu.lastLoginTime,cu.user_type,cu.first_name,cu.last_name,cu.email_id,cu.country,cu.work_phone,cu.personal_Phone,"
					+ "cu.nationalId,cu.passport,cu.boardResolution,cu.user_image,cu.tpin,cu.passportNumber,cu.nationalIdNumber,"
					+ "cu.tpin_status,cu.tpin_wrong_attempt,cu.city,cu.wrong_pwd_attempt,cu.pwd_status,cu.otherDoc,cu.certificate_incorporation,"
					+ "cu.state , ccm.Companyname as companyName, ut.user_type as userType from CORP_USERS cu "
					+ "inner join CORP_COMPANY_MASTER ccm on ccm.id= cu.corp_comp_id left join CORP_USER_TYPES ut on ut.id=cu.user_type "
					+ "where cu.id=:id ";
			list = (CorpUserEntity) getSession().createSQLQuery(sqlQuery)
					.addScalar("id", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("designation", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("corp_comp_id", StandardBasicTypes.BIG_DECIMAL).addScalar("user_disp_name")
					.addScalar("user_name").addScalar("user_pwd", StandardBasicTypes.STRING)
					.addScalar("createdby", StandardBasicTypes.BIG_DECIMAL).addScalar("createdon")
					.addScalar("statusid", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("appid", StandardBasicTypes.BIG_DECIMAL).addScalar("lastLoginTime")
					.addScalar("user_type", StandardBasicTypes.BIG_DECIMAL).addScalar("first_name")
					.addScalar("last_name").addScalar("email_id").addScalar("country", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("work_phone").addScalar("personal_Phone")
					.addScalar("nationalId", StandardBasicTypes.STRING).addScalar("passport", StandardBasicTypes.STRING)
					.addScalar("boardResolution", StandardBasicTypes.STRING)
					.addScalar("user_image", StandardBasicTypes.STRING).addScalar("tpin").addScalar("passportNumber")
					.addScalar("nationalIdNumber").addScalar("tpin_status")
					.addScalar("tpin_wrong_attempt", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("city", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("wrong_pwd_attempt", StandardBasicTypes.BIG_DECIMAL).addScalar("pwd_status")
					.addScalar("otherDoc", StandardBasicTypes.STRING)
					.addScalar("certificate_incorporation", StandardBasicTypes.STRING)
					.addScalar("state", StandardBasicTypes.BIG_DECIMAL).addScalar("companyName").addScalar("userType")
					.setParameter("id", id).setResultTransformer(Transformers.aliasToBean(CorpUserEntity.class));

			if (list.getWork_phone().contains("=") && list.getPersonal_Phone().contains("=")
					&& list.getEmail_id().contains("=")) {
				list.setEmail_id(EncryptorDecryptor.decryptData(list.getEmail_id()));
				list.setWork_phone(EncryptorDecryptor.decryptData(list.getWork_phone()));
				list.setPersonal_Phone(EncryptorDecryptor.decryptData(list.getPersonal_Phone()));
			}

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public List<CorpLevelMasterEntity> getCorpLevels() {
		List<CorpLevelMasterEntity> list = null;
		try {

			String sqlQuery = " select cl.ID as id , cl.LEVELID as levelId, cl.CREATEDBY as createdBy , cl.CREATEDON as createdOn, cl.STATUSID as statusId, cl.APPID as appId,"
					+ "	cl.APPROVERLEVEL as approverLevel from CORP_LEVELS_MASTER cl ";
			list = getSession().createSQLQuery(sqlQuery).addScalar("id", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("levelId", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("createdBy", StandardBasicTypes.BIG_DECIMAL).addScalar("createdOn")
					.addScalar("statusId", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("appId", StandardBasicTypes.BIG_DECIMAL).addScalar("approverLevel")
					.setResultTransformer(Transformers.aliasToBean(CorpLevelMasterEntity.class)).list();

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public List<CorpUserEntity> getCorpUsersByCompId(int compId) {
		List<CorpUserEntity> list = null;
		try {

			String sqlQuery = "select cu.id,cu.designation,cu.corp_comp_id,cu.user_disp_name,cu.user_name,cu.user_pwd,cu.createdby,cu.createdon,cu.statusid,"
					+ "cu.appid,cu.lastLoginTime,cu.user_type,cu.first_name,cu.last_name,cu.email_id,"
					+ "cu.certificate_incorporation,"
					+ "cu.state , ccm.Companyname as companyName, ut.user_type as userType from CORP_USERS cu "
					+ "inner join CORP_COMPANY_MASTER ccm on ccm.id= cu.corp_comp_id left join CORP_USER_TYPES ut on ut.id=cu.user_type "
					+ "where cu.corp_comp_id=:compId";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("designation", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("corp_comp_id", StandardBasicTypes.BIG_DECIMAL).addScalar("user_disp_name")
					.addScalar("user_name").addScalar("user_pwd", StandardBasicTypes.STRING)
					.addScalar("createdby", StandardBasicTypes.BIG_DECIMAL).addScalar("createdon")
					.addScalar("statusid", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("appid", StandardBasicTypes.BIG_DECIMAL).addScalar("lastLoginTime")
					.addScalar("user_type", StandardBasicTypes.BIG_DECIMAL).addScalar("first_name")
					.addScalar("last_name").addScalar("email_id")
					.addScalar("certificate_incorporation", StandardBasicTypes.STRING)
					.addScalar("state", StandardBasicTypes.BIG_DECIMAL).addScalar("companyName").addScalar("userType")
					.setParameter("compId", compId).setResultTransformer(Transformers.aliasToBean(CorpUserEntity.class))
					.list();

			for (CorpUserEntity corpData : list) {
				if (corpData.getWork_phone().contains("=") && corpData.getPersonal_Phone().contains("=")
						&& corpData.getEmail_id().contains("=")) {
					corpData.setEmail_id(EncryptorDecryptor.decryptData(corpData.getEmail_id()));
					corpData.setWork_phone(EncryptorDecryptor.decryptData(corpData.getWork_phone()));
					corpData.setPersonal_Phone(EncryptorDecryptor.decryptData(corpData.getPersonal_Phone()));
				}
			}

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public List<CorpHierarchyMaster> getCorpHierarchyList() {
		List<CorpHierarchyMaster> list = null;
		try {
			String sqlQuery = " select ch.id as id , ch.HIERARCHY as hierarchy, ch.CREATEDBY as createdBy, ch.STATUSID as statusId, ch.APPID as appId from CORP_HIERARCHY_MASTER ch"
					+ "	where ch.STATUSID = 3 ";
			list = getSession().createSQLQuery(sqlQuery).addScalar("id", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("hierarchy").addScalar("createdBy", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("statusId", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("appId", StandardBasicTypes.BIG_DECIMAL)
					.setResultTransformer(Transformers.aliasToBean(CorpHierarchyMaster.class)).list();

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public List<CorpApproverMasterEntity> getCorpApproverTypeList() {
		List<CorpApproverMasterEntity> list = null;
		try {
			String sqlQuery = "  select ch.id as id , ch.APPROVALTYPE as approverType, ch.CREATEDBY as createdBy, ch.STATUSID as statusId, ch.APPID as appId from CORP_APPROVER_MASTER ch"
					+ "	where ch.STATUSID = 3 ";
			list = getSession().createSQLQuery(sqlQuery).addScalar("id", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("approverType").addScalar("createdBy", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("statusId", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("appId", StandardBasicTypes.BIG_DECIMAL)
					.setResultTransformer(Transformers.aliasToBean(CorpApproverMasterEntity.class)).list();

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public boolean setCorpTransactionsLimit(CorpTransLimitBean corpTransData) {
		int userStatus = corpTransData.getStatusId().intValue();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(corpTransData.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.CORP_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(corpTransData.getActivityName());

		try {
			if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				corpTransData.setStatusId(BigDecimal.valueOf(statusId));
				if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)
						&& (ApplicationConstants.YESVALUE)
								.equals(Character.toString(activityMasterData.get(0).getChecker()))
						&& (ApplicationConstants.YESVALUE)
								.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

					for (CorpLimitDataBean corpLimitDatas : corpTransData.getCorpLimitData()) {
						corpLimitDatas.setApproverMasterName(adminWorkFlowReqUtility
								.getApproverMasterName(corpLimitDatas.getApproverMasterId().intValue()));
						corpLimitDatas.setHierarchyMasterName(adminWorkFlowReqUtility
								.getHierarchialMasterName(corpLimitDatas.getHierarchyMasterId().intValue()));
					}
					ObjectMapper mapper = new ObjectMapper();

					AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
					adminData.setAppId(BigDecimal.valueOf(2));
					adminData.setCreatedByUserId(corpTransData.getUser_ID());
					adminData.setCreatedByRoleId(corpTransData.getRole_ID());
					adminData.setPageId(corpTransData.getSubMenu_ID());
					adminData.setCreatedBy(BigDecimal.valueOf(corpTransData.getCreatedBy()));
					adminData.setContent(mapper.writeValueAsString(corpTransData));
					adminData.setActivityId(corpTransData.getSubMenu_ID());
					adminData.setStatusId(BigDecimal.valueOf(statusId));
					adminData.setPendingWithRoleId(BigDecimal.valueOf(6));
					adminData.setRemark(corpTransData.getRemark());
					adminData.setActivityName(corpTransData.getActivityName());
					adminData.setActivityRefNo(null);
					adminData.setTableName("CORP_TRANSCATION_LIMITS");
					adminData.setUserAction(BigDecimal.valueOf(userStatus));
					adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);

					// Save data to admin work flow history
					adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(corpTransData.getSubMenu_ID(), null,
							new BigDecimal(corpTransData.getCreatedBy()), corpTransData.getRemark(),
							corpTransData.getRole_ID(), mapper.writeValueAsString(corpTransData));
				}
			} else {

				for (CorpLimitDataBean corpLimitDatas : corpTransData.getCorpLimitData()) {
					// save data to corp trasaction limit
					CorpTransactionLimitEntity corpTransDataObj = new CorpTransactionLimitEntity();
					corpTransDataObj.setCorpCompId(corpTransData.getCorpCompId());
					corpTransDataObj.setMinAmount(corpLimitDatas.getMinAmount());
					corpTransDataObj.setMaxAmount(corpLimitDatas.getMaxAmount());
					corpTransDataObj.setCurrency(corpTransData.getCurrency());
					corpTransDataObj.setCreatedBy(corpTransData.getCreatedBy());
					corpTransDataObj.setStatusId(corpTransData.getStatusId());
					corpTransDataObj.setAppId(corpTransData.getAppId());
					corpTransData.setApproverLimitId(corpTransData.getAppId());

					int tranLimitId = saveCorpTransLimit(corpTransDataObj);

					// save data to CORP_ACCT_TRANS_MASTER
					CorpAcctTransMasterEntity corpAcctTransMasterObj = new CorpAcctTransMasterEntity();
					corpAcctTransMasterObj.setCorporateCompId(corpTransData.getCorpCompId());
					corpAcctTransMasterObj.setAccountNumber(corpTransData.getAccountNumber());
					corpAcctTransMasterObj.setTranLimitId(BigDecimal.valueOf(tranLimitId));
					corpAcctTransMasterObj.setApproverLevelId(corpLimitDatas.getApproverLevelId());
					corpAcctTransMasterObj.setApproverMasterId(corpLimitDatas.getApproverMasterId());
					corpAcctTransMasterObj.setHierarchyMasterId(corpLimitDatas.getHierarchyMasterId());
					corpAcctTransMasterObj.setCreatedBy(BigDecimal.valueOf(corpTransData.getCreatedBy()));
					corpAcctTransMasterObj.setStatusId(corpTransData.getStatusId());
					corpAcctTransMasterObj.setAppId(corpTransData.getAppId());

					saveCorpAcctTransMaster(corpAcctTransMasterObj);
					corpTransData.setCatmId(getAllCorpTransLimitMasterData().get(0).getId());

					// save data to CORP_ACCT_TRANS_LIMIT_USERS
					for (BigDecimal corpUserIdData : corpLimitDatas.getCorpUserId()) {
						CorpAcctTransLimitUsersEntity corpAcctTransLimitUserObj = new CorpAcctTransLimitUsersEntity();
						corpAcctTransLimitUserObj.setCorporateCompId(corpTransData.getCorpCompId());
						corpAcctTransLimitUserObj.setCatmId(corpTransData.getCatmId());
						corpAcctTransLimitUserObj.setCorpUserId(corpUserIdData);
						corpAcctTransLimitUserObj.setStatusId(corpTransData.getStatusId());
						corpAcctTransLimitUserObj.setAppId(corpTransData.getAppId());
						corpAcctTransLimitUserObj.setCreatedBy(BigDecimal.valueOf(corpTransData.getCreatedBy()));
						saveCorpAcctTransLimitUser(corpAcctTransLimitUserObj);

					}
				}

			}

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
		return true;
	}

	@Override
	public boolean updateCorpTransactionsLimit(CorpTransLimitBean corpTransData) {
		int userStatus = corpTransData.getStatusId().intValue();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(corpTransData.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.CORP_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(corpTransData.getActivityName());

		try {
			if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				corpTransData.setStatusId(BigDecimal.valueOf(statusId)); // 97-
																			// ADMIN_CHECKER_PENDIN

				if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)
						&& (ApplicationConstants.YESVALUE)
								.equals(Character.toString(activityMasterData.get(0).getChecker()))
						&& (ApplicationConstants.YESVALUE)
								.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

					for (CorpLimitDataBean corpLimitDatas : corpTransData.getCorpLimitData()) {
						corpLimitDatas.setApproverMasterName(adminWorkFlowReqUtility
								.getApproverMasterName(corpLimitDatas.getApproverMasterId().intValue()));
						corpLimitDatas.setHierarchyMasterName(adminWorkFlowReqUtility
								.getHierarchialMasterName(corpLimitDatas.getHierarchyMasterId().intValue()));
					}

					ObjectMapper mapper = new ObjectMapper();

					List<AdminWorkFlowRequestEntity> AdminDataList = adminWorkFlowReqUtility
							.getAdminWorkFlowDataByAdminWorkFlowId(corpTransData.getAdminWorkFlowId().intValue(),
									corpTransData.getSubMenu_ID());

					AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
					adminData.setId(corpTransData.getAdminWorkFlowId());
					adminData.setAppId(corpTransData.getAppId());
					adminData.setCreatedOn(corpTransData.getCreatedOn());
					adminData.setCreatedByUserId(corpTransData.getUser_ID());
					adminData.setCreatedByRoleId(corpTransData.getRole_ID());
					adminData.setPageId(corpTransData.getSubMenu_ID());
					adminData.setCreatedBy(BigDecimal.valueOf(corpTransData.getCreatedBy()));
					adminData.setContent(mapper.writeValueAsString(corpTransData));
					adminData.setActivityId(corpTransData.getSubMenu_ID());
					adminData.setStatusId(BigDecimal.valueOf(statusId));
					adminData.setPendingWithRoleId(BigDecimal.valueOf(6));
					adminData.setRemark(corpTransData.getRemark());
					adminData.setActivityName(corpTransData.getActivityName());
					adminData.setUserAction(BigDecimal.valueOf(userStatus));
					adminData.setTableName("CORP_TRANSCATION_LIMITS");

					if (ObjectUtils.isEmpty(AdminDataList)) {
						adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);
					} else if (!ObjectUtils.isEmpty(AdminDataList)) {
						adminData.setId(BigDecimal.valueOf(AdminDataList.get(0).getId().intValue()));
						adminWorkFlowReqUtility.updateAdminWorkFlowRequest(adminData);
					}

					// Save data to admin work flow history
					adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(corpTransData.getSubMenu_ID(),
							new BigDecimal(corpTransData.getAdminWorkFlowId().intValue()),
							new BigDecimal(corpTransData.getCreatedBy()), corpTransData.getRemark(),
							corpTransData.getRole_ID(), mapper.writeValueAsString(corpTransData));

				} else {

					// if record is present in admin work flow then update status

					adminWorkFlowReqUtility.getCheckerDataByctivityRefId(corpTransData.getId().toBigInteger(),
							BigInteger.valueOf(userStatus), corpTransData.getSubMenu_ID());
				}
			} else {

				for (CorpLimitDataBean corpLimitDatas : corpTransData.getCorpLimitData()) {
					// save data to corp trasaction limit
					CorpTransactionLimitEntity corpTransDataObj = new CorpTransactionLimitEntity();
					corpTransDataObj.setCorpCompId(corpTransData.getCorpCompId());
					corpTransDataObj.setMinAmount(corpLimitDatas.getMinAmount());
					corpTransDataObj.setMaxAmount(corpLimitDatas.getMaxAmount());
					corpTransDataObj.setCurrency(corpTransData.getCurrency());
					corpTransDataObj.setCreatedBy(corpTransData.getCreatedBy());
					corpTransDataObj.setStatusId(corpTransData.getStatusId());
					corpTransDataObj.setAppId(corpTransData.getAppId());
					corpTransDataObj.setId(corpLimitDatas.getTransLimitId());
					corpTransData.setApproverLimitId(corpTransData.getAppId());

					int tranLimitId = saveCorpTransLimit(corpTransDataObj);

					// save data to CORP_ACCT_TRANS_MASTER
					CorpAcctTransMasterEntity corpAcctTransMasterObj = new CorpAcctTransMasterEntity();

					corpAcctTransMasterObj.setCorporateCompId(corpTransData.getCorpCompId());
					corpAcctTransMasterObj.setAccountNumber(corpTransData.getAccountNumber());
					corpAcctTransMasterObj.setTranLimitId(BigDecimal.valueOf(tranLimitId));
					corpAcctTransMasterObj.setApproverLevelId(corpLimitDatas.getApproverLevelId());
					corpAcctTransMasterObj.setApproverMasterId(corpLimitDatas.getApproverMasterId());
					corpAcctTransMasterObj.setHierarchyMasterId(corpLimitDatas.getHierarchyMasterId());
					corpAcctTransMasterObj.setCreatedBy(BigDecimal.valueOf(corpTransData.getCreatedBy()));
					corpAcctTransMasterObj.setStatusId(corpTransData.getStatusId());
					corpAcctTransMasterObj.setId(corpLimitDatas.getTransMasterId());
					corpAcctTransMasterObj.setAppId(corpTransData.getAppId());

					saveCorpAcctTransMaster(corpAcctTransMasterObj);
					corpTransData.setCatmId(getAllCorpTransLimitMasterData().get(0).getId());

					// save data to CORP_ACCT_TRANS_LIMIT_USERS
					for (CorpUserBean corpUserIdData : corpLimitDatas.getCorpUserData()) {

						CorpAcctTransLimitUsersEntity corpAcctTransLimitUserObj = new CorpAcctTransLimitUsersEntity();
						corpAcctTransLimitUserObj.setCorporateCompId(corpTransData.getCorpCompId());
						corpAcctTransLimitUserObj.setCatmId(corpTransData.getCatmId());
						corpAcctTransLimitUserObj.setCorpUserId(new BigDecimal(corpUserIdData.getUserId()));
						corpAcctTransLimitUserObj.setId(new BigDecimal(corpUserIdData.getTransLimitUserId()));
						corpAcctTransLimitUserObj.setStatusId(corpTransData.getStatusId());
						corpAcctTransLimitUserObj.setAppId(corpTransData.getAppId());
						corpAcctTransLimitUserObj.setCreatedBy(BigDecimal.valueOf(corpTransData.getCreatedBy()));
						saveCorpAcctTransLimitUser(corpAcctTransLimitUserObj);

					}
				}

			}
			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}

	@Override
	public List<CorpTransactionLimitEntity> getCorpTransLimitData(int compId, int minAmt, int maxAmt, String currency,
			String accNo) {
		List<CorpTransactionLimitEntity> list = null;
		try {
			String sqlQuery = " select ct.id as id ,ct. CORPCOMPID as corpCompId from CORP_TRANSCATION_LIMITS ct where ct.CORPCOMPID =:compId "
					+ "and ct.MINAMOUNT=:minAmt and ct.MAXAMOUNT=:maxAmt"
					+ " and ct.CURRENCY =:currency and ct.id in((select ca.TRAN_LIMIT_ID from corp_acct_trans_master ca where ca.ACCOUNT_NUMBER=:accNo)) ";
			list = getSession().createSQLQuery(sqlQuery).setParameter("compId", compId).setParameter("minAmt", minAmt)
					.setParameter("maxAmt", maxAmt).setParameter("currency", currency).setParameter("accNo", accNo)
					.setResultTransformer(Transformers.aliasToBean(CorpTransactionLimitEntity.class)).list();

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	public List<CorpTransactionLimitEntity> isCorpTransLimitDataExit(int compId, int minAmt, int maxAmt,
			String currency) {
		List<CorpTransactionLimitEntity> list = null;
		try {
			String sqlQuery = " select ct.id as id ,ct. CORPCOMPID as corpCompId from CORP_TRANSCATION_LIMITS ct where ct.CORPCOMPID =:compId "
					+ "and ct.MINAMOUNT=:minAmt and ct.MAXAMOUNT=:maxAmt" + " and ct.CURRENCY =:currency ";
			list = getSession().createSQLQuery(sqlQuery).setParameter("compId", compId).setParameter("minAmt", minAmt)
					.setParameter("maxAmt", maxAmt).setParameter("currency", currency)
					.setResultTransformer(Transformers.aliasToBean(CorpTransactionLimitEntity.class)).list();

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public List<CorpTransactionLimitEntity> checkCorpTransLimitDataPresent(int compId, int minAmt, int maxAmt,
			String currency, int transId, String accNo) {
		List<CorpTransactionLimitEntity> list = null;
		try {
			String sqlQuery = " select ct.id as id ,ct. CORPCOMPID as corpCompId from CORP_TRANSCATION_LIMITS ct where ct.CORPCOMPID =:compId "
					+ "and ct.MINAMOUNT=:minAmt and ct.MAXAMOUNT=:maxAmt"
					+ " and ct.CURRENCY =:currency and ct.id !=:transId and ct.id in((select ca.TRAN_LIMIT_ID from corp_acct_trans_master ca where ca.ACCOUNT_NUMBER=:accNo))";
			list = getSession().createSQLQuery(sqlQuery).setParameter("compId", compId).setParameter("minAmt", minAmt)
					.setParameter("maxAmt", maxAmt).setParameter("currency", currency).setParameter("transId", transId)
					.setParameter("accNo", accNo)
					.setResultTransformer(Transformers.aliasToBean(CorpTransactionLimitEntity.class)).list();

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	public List<CorpTransactionLimitEntity> getAllCorpTransLimitData() {
		List<CorpTransactionLimitEntity> list = null;
		try {
			String sqlQuery = "select ct.id as id from CORP_TRANSCATION_LIMITS ct order by ct.id desc";
			list = getSession().createSQLQuery(sqlQuery).addScalar("id")
					.setResultTransformer(Transformers.aliasToBean(CorpTransactionLimitEntity.class)).list();

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	public List<CorpAcctTransMasterEntity> getAllCorpTransLimitMasterData() {
		List<CorpAcctTransMasterEntity> list = null;
		try {
			String sqlQuery = "select ct.id as id from CORP_ACCT_TRANS_MASTER ct order by ct.id desc";
			list = getSession().createSQLQuery(sqlQuery).addScalar("id")
					.setResultTransformer(Transformers.aliasToBean(CorpAcctTransMasterEntity.class)).list();

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	public int saveCorpTransLimit(CorpTransactionLimitEntity corpTransDataList) {
		int tranLimitId = 0;
		Session session = sessionFactory.getCurrentSession();
		try {
			// check if limit id already there for given amount
			List<CorpTransactionLimitEntity> corptransactionlimitsList = isCorpTransLimitDataExit(
					corpTransDataList.getCorpCompId().intValue(), corpTransDataList.getMinAmount().intValue(),
					corpTransDataList.getMaxAmount().intValue(), corpTransDataList.getCurrency());
			if (ObjectUtils.isEmpty(corptransactionlimitsList)) {
				corpTransDataList.setCreatedOn(new Date());
				session.save(corpTransDataList);
				tranLimitId = getAllCorpTransLimitData().get(0).getId().intValue();
			} else {
				tranLimitId = corptransactionlimitsList.get(0).getId().intValue();
			}

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return tranLimitId;
	}

	public boolean saveCorpAcctTransMaster(CorpAcctTransMasterEntity corpAcctTransMasterObj) {

		Session session = sessionFactory.getCurrentSession();
		try {
			corpAcctTransMasterObj.setCreatedOn(new Date());
			session.save(corpAcctTransMasterObj);

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
		return true;
	}

	public boolean saveCorpAcctTransLimitUser(CorpAcctTransLimitUsersEntity corpAcctTransLimitUserObj) {

		Session session = sessionFactory.getCurrentSession();
		try {
			corpAcctTransLimitUserObj.setCreatedon(new Date());
			session.save(corpAcctTransLimitUserObj);

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
		return true;
	}

	public int updateCorpTransLimit(CorpTransactionLimitEntity corpTransDataList, String AccNo) {
		int tranLimitId = 0;
		Session session = sessionFactory.getCurrentSession();
		try {
			// check if limit id already there for given amount
			List<CorpTransactionLimitEntity> corptransactionlimitsList = getCorpTransLimitData(
					corpTransDataList.getCorpCompId().intValue(), corpTransDataList.getMinAmount().intValue(),
					corpTransDataList.getMaxAmount().intValue(), corpTransDataList.getCurrency(), AccNo);
			if (ObjectUtils.isEmpty(corptransactionlimitsList)) {
				corpTransDataList.setCreatedOn(new Date());
				session.update(corpTransDataList);

			} else {
				tranLimitId = corptransactionlimitsList.get(0).getId().intValue();
			}

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return tranLimitId;
	}

	@Override
	public CorpTransLimitBean getAllTransationByAccNoAndCompId(String accNo, int compId) {
		List<CorpTransactionLimitEntity> corpTransList = null;
		List<List<CorpLimitDataBean>> corpLimitDataList = new ArrayList<>();
		CorpTransLimitBean allTransData = new CorpTransLimitBean();
		try {
			String sqlQuery = " select cl.id,cl.CORPCOMPID as corpCompId ,cl.MINAMOUNT as minAmount ,cl.MAXAMOUNT as maxAmount, cl.CURRENCY as currency ,"
					+ "cl. STATUSID as statusId ,cl.APPID as appId from CORP_TRANSCATION_LIMITS cl "
					+ " where cl.id in(select ca.tran_limit_id from  corp_acct_trans_master ca where ca.CORPORATE_COMP_ID=:compId and ca.ACCOUNT_NUMBER=:accNo and ca.statusId=3) and cl.statusid=3 ";

			corpTransList = getSession().createSQLQuery(sqlQuery).addScalar("id", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("corpCompId", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("minAmount", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("maxAmount", StandardBasicTypes.BIG_DECIMAL).addScalar("currency")
					.addScalar("statusId", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("appId", StandardBasicTypes.BIG_DECIMAL).setParameter("compId", compId)
					.setParameter("accNo", accNo)
					.setResultTransformer(Transformers.aliasToBean(CorpTransactionLimitEntity.class)).list();

			List<CorpAcctTransMasterEntity> corpTransMasterList = getAllTranMasterData(accNo, compId);
			List<CorpAcctTransLimitUsersEntity> corpTransUserList = getCorpTranUser(accNo, compId);

			for (CorpTransactionLimitEntity aa : corpTransList) {
				List<CorpLimitDataBean> corpLimitDataObjLst = new ArrayList<>();
				for (CorpAcctTransMasterEntity corpTrasnMasterObj : corpTransMasterList) {
					if (aa.getId().intValue() == corpTrasnMasterObj.getTranLimitId().intValue()) {
						CorpLimitDataBean corpLimitDataObj = new CorpLimitDataBean();
						corpLimitDataObj.setTransLimitId(aa.getId());
						corpLimitDataObj.setTransMasterId(corpTrasnMasterObj.getId());

						corpLimitDataObj.setApproverLevelId(corpTrasnMasterObj.getApproverLevelId());
						corpLimitDataObj.setApproverMasterId(corpTrasnMasterObj.getApproverMasterId());
						corpLimitDataObj.setHierarchyMasterId(corpTrasnMasterObj.getHierarchyMasterId());
						corpLimitDataObj.setMaxAmount(aa.getMaxAmount());
						corpLimitDataObj.setMinAmount(aa.getMinAmount());
						corpLimitDataObj.setApproverMasterName(corpTrasnMasterObj.getApproverMasterName());
						corpLimitDataObj.setHierarchyMasterName(corpTrasnMasterObj.getHierarchyMasterName());
						List<CorpUserBean> corpUserDataBean = new ArrayList<>();
						for (CorpAcctTransLimitUsersEntity corpTransUserObj : corpTransUserList) {
							CorpUserBean corpUserDataIds = new CorpUserBean();
							if (corpTransUserObj.getCatmId().intValue() == corpTrasnMasterObj.getId().intValue()) {

								corpUserDataIds.setTransLimitUserId(corpTransUserObj.getId().toBigInteger());
								corpUserDataIds.setUserId(corpTransUserObj.getCorpUserId().toBigInteger());
								corpUserDataIds.setCorpUSerName(
										corpTransUserObj.getFirstName() + " " + corpTransUserObj.getLastName());
								corpUserDataBean.add(corpUserDataIds);
							}
						}

						corpLimitDataObj.setCorpUserData(corpUserDataBean);

						corpLimitDataObjLst.add(corpLimitDataObj);

					}
				}
				corpLimitDataList.add(corpLimitDataObjLst);
				allTransData.setCorpLimitListData(corpLimitDataList);
				allTransData.setAccountNumber(accNo);

			}

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return allTransData;

	}

	public List<CorpAcctTransMasterEntity> getAllTranMasterData(String accNo, int compId) {
		List<CorpAcctTransMasterEntity> corpTransMasterList = null;
		try {
			String sqlQuery = "select ca.ID as id ,ca.CORPORATE_COMP_ID as corporateCompId ,ca.ACCOUNT_NUMBER as accountNumber,ca.tran_limit_id as tranLimitId"
					+ " ,ca.APPROVER_MASTER_ID as approverMasterId,ca.APPROVER_LEVEL_ID as approverLevelId,ca.HIERARCHY_MASTER_ID as hierarchyMasterId,ca.STATUSID as statusId"
					+ " ,ca.APPID as appId,cm.APPROVALTYPE as  approverMasterName, ch. HIERARCHY as hierarchyMasterName "
					+ "  from  corp_acct_trans_master ca "
					+ " inner join CORP_APPROVER_MASTER cm on cm.id=ca.APPROVER_MASTER_ID  inner join CORP_HIERARCHY_MASTER ch on ch.id= ca.HIERARCHY_MASTER_ID "
					+ " where ca.CORPORATE_COMP_ID=:compId and ca.ACCOUNT_NUMBER=:accNo and ca.statusid=3";

			corpTransMasterList = getSession().createSQLQuery(sqlQuery).addScalar("id", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("corporateCompId").addScalar("accountNumber", StandardBasicTypes.STRING)
					.addScalar("tranLimitId", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("approverMasterId", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("approverLevelId", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("hierarchyMasterId", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("statusId", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("appId", StandardBasicTypes.BIG_DECIMAL).addScalar("approverMasterName")
					.addScalar("hierarchyMasterName").setParameter("compId", compId).setParameter("accNo", accNo)
					.setResultTransformer(Transformers.aliasToBean(CorpAcctTransMasterEntity.class)).list();

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return corpTransMasterList;

	}

	public List<CorpAcctTransLimitUsersEntity> getCorpTranUser(String accNo, int compId) {
		List<CorpAcctTransLimitUsersEntity> corpTransUserList = null;
		try {
			String sqlQuery = "select ct.id, ct.CORPORATE_COMP_ID as corporateCompId ,ct.CATM_ID as catmId ,ct.CORP_USER_ID as corpUserId ,"
					+ "cu.first_name as firstName, cu.last_name as lastName from corp_acct_trans_limit_users ct"
					+ "   inner join  corp_users cu on cu.id = ct.CORP_USER_ID "
					+ "where ct.CATM_ID in(select ca.ID from  corp_acct_trans_master ca where ca.CORPORATE_COMP_ID=:compId and ca.ACCOUNT_NUMBER=:accNo and ca.statusid=3)and ct.statusid=3";

			corpTransUserList = getSession().createSQLQuery(sqlQuery).addScalar("id", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("corporateCompId", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("catmId", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("corpUserId", StandardBasicTypes.BIG_DECIMAL).addScalar("firstName")
					.addScalar("lastName").setParameter("compId", compId).setParameter("accNo", accNo)
					.setResultTransformer(Transformers.aliasToBean(CorpAcctTransLimitUsersEntity.class)).list();

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return corpTransUserList;

	}

	@Override
	public CorpTransLimitBean getTranByAccNoAndCompIdAndTransId(String accNo, int compId, long transId) {
		List<CorpTransactionLimitEntity> corpTransList = null;
		List<List<CorpLimitDataBean>> corpLimitDataList = new ArrayList<>();
		CorpTransLimitBean allTransData = new CorpTransLimitBean();
		try {
			String sqlQuery = " select cl.id,cl.CORPCOMPID as corpCompId ,cl.MINAMOUNT as minAmount ,cl.MAXAMOUNT as maxAmount, cl.CURRENCY as currency ,"
					+ "cl. STATUSID as statusId ,cl.APPID as appId from CORP_TRANSCATION_LIMITS cl "
					+ " where cl.id=:transId and cl.statusid=3 ";

			corpTransList = getSession().createSQLQuery(sqlQuery).addScalar("id", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("corpCompId", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("minAmount", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("maxAmount", StandardBasicTypes.BIG_DECIMAL).addScalar("currency")
					.addScalar("statusId", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("appId", StandardBasicTypes.BIG_DECIMAL).setParameter("transId", transId)
					.setResultTransformer(Transformers.aliasToBean(CorpTransactionLimitEntity.class)).list();

			List<CorpAcctTransMasterEntity> corpTransMasterList = getAllTranMasterDataByTransId(accNo, compId, transId);
			List<CorpAcctTransLimitUsersEntity> corpTransUserList = getCorpTranUser(accNo, compId);

			for (CorpTransactionLimitEntity aa : corpTransList) {
				List<CorpLimitDataBean> corpLimitDataObjLst = new ArrayList<>();
				for (CorpAcctTransMasterEntity corpTrasnMasterObj : corpTransMasterList) {
					if (aa.getId().intValue() == corpTrasnMasterObj.getTranLimitId().intValue()) {
						CorpLimitDataBean corpLimitDataObj = new CorpLimitDataBean();
						corpLimitDataObj.setTransLimitId(aa.getId());
						corpLimitDataObj.setTransMasterId(corpTrasnMasterObj.getId());

						corpLimitDataObj.setApproverLevelId(corpTrasnMasterObj.getApproverLevelId());
						corpLimitDataObj.setApproverMasterId(corpTrasnMasterObj.getApproverMasterId());
						corpLimitDataObj.setHierarchyMasterId(corpTrasnMasterObj.getHierarchyMasterId());
						corpLimitDataObj.setMaxAmount(aa.getMaxAmount());
						corpLimitDataObj.setMinAmount(aa.getMinAmount());
						corpLimitDataObj.setApproverMasterName(corpTrasnMasterObj.getApproverMasterName());
						corpLimitDataObj.setHierarchyMasterName(corpTrasnMasterObj.getHierarchyMasterName());

						List<CorpUserBean> corpUserDataBean = new ArrayList<>();
						for (CorpAcctTransLimitUsersEntity corpTransUserObj : corpTransUserList) {
							CorpUserBean corpUserDataIds = new CorpUserBean();
							if (corpTransUserObj.getCatmId().intValue() == corpTrasnMasterObj.getId().intValue()) {

								corpUserDataIds.setTransLimitUserId(corpTransUserObj.getId().toBigInteger());
								corpUserDataIds.setUserId(corpTransUserObj.getCorpUserId().toBigInteger());
								corpUserDataIds.setCorpUSerName(
										corpTransUserObj.getFirstName() + " " + corpTransUserObj.getLastName());
								corpUserDataBean.add(corpUserDataIds);
							}
						}

						corpLimitDataObj.setCorpUserData(corpUserDataBean);
						corpLimitDataObjLst.add(corpLimitDataObj);

					}
				}
				corpLimitDataList.add(corpLimitDataObjLst);
				allTransData.setCorpLimitListData(corpLimitDataList);
				allTransData.setAccountNumber(accNo);

			}

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return allTransData;
	}

	public List<CorpAcctTransMasterEntity> getAllTranMasterDataByTransId(String accNo, int compId, long transId) {
		List<CorpAcctTransMasterEntity> corpTransMasterList = null;
		try {
			String sqlQuery = "select ca.ID as id ,ca.CORPORATE_COMP_ID as corporateCompId ,ca.ACCOUNT_NUMBER as accountNumber,ca.tran_limit_id as tranLimitId"
					+ " ,ca.APPROVER_MASTER_ID as approverMasterId,ca.APPROVER_LEVEL_ID as approverLevelId,ca.HIERARCHY_MASTER_ID as hierarchyMasterId,ca.STATUSID as statusId"
					+ " ,ca.APPID as appId,cm.APPROVALTYPE as  approverMasterName, ch. HIERARCHY as hierarchyMasterName "
					+ "  from  corp_acct_trans_master ca "
					+ " inner join CORP_APPROVER_MASTER cm on cm.id=ca.APPROVER_MASTER_ID  inner join CORP_HIERARCHY_MASTER ch on ch.id= ca.HIERARCHY_MASTER_ID "
					+ " where ca.CORPORATE_COMP_ID=:compId and ca.ACCOUNT_NUMBER=:accNo and ca.tran_limit_id=:transId  and ca.statusid=3";

			corpTransMasterList = getSession().createSQLQuery(sqlQuery).addScalar("id", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("corporateCompId", StandardBasicTypes.BIG_DECIMAL).addScalar("accountNumber")
					.addScalar("tranLimitId", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("approverMasterId", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("approverLevelId", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("hierarchyMasterId", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("statusId", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("appId", StandardBasicTypes.BIG_DECIMAL).addScalar("approverMasterName")
					.addScalar("hierarchyMasterName").setParameter("compId", compId).setParameter("accNo", accNo)
					.setParameter("transId", transId)
					.setResultTransformer(Transformers.aliasToBean(CorpAcctTransMasterEntity.class)).list();

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return corpTransMasterList;

	}

	public List<CorpAcctTransLimitUsersEntity> getCorpTranUserByTransId(String accNo, int compId, long transId) {
		List<CorpAcctTransLimitUsersEntity> corpTransUserList = null;
		try {
			String sqlQuery = "select ct.id, ct.CORPORATE_COMP_ID as corporateCompId ,ct.CATM_ID as catmId ,ct.CORP_USER_ID as corpUserId ,"
					+ "cu.first_name as firstName, cu.last_name as lastName from corp_acct_trans_limit_users ct"
					+ "   left join  corp_users cu on cu.id = ct.CORP_USER_ID "
					+ "where ct.CATM_ID in(select ca.ID from  corp_acct_trans_master ca where ca.CORPORATE_COMP_ID=:compId and ca.ACCOUNT_NUMBER=:accNo and ca.tran_limit_id=:transId)";

			corpTransUserList = getSession().createSQLQuery(sqlQuery).addScalar("id", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("corporateCompId", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("catmId", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("corpUserId", StandardBasicTypes.BIG_DECIMAL).addScalar("firstName")
					.addScalar("lastName").addScalar("hierarchyMasterName").setParameter("compId", compId)
					.setParameter("accNo", accNo).setParameter("transId", transId)
					.setResultTransformer(Transformers.aliasToBean(CorpAcctTransLimitUsersEntity.class)).list();

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return corpTransUserList;

	}

	@Override
	public CorpTransLimitBean getTrasLimitDataByAdminWorkFlowId(long adminWorkFlowId, String accNo) {
		List<AdminWorkFlowRequestEntity> contentList = null;
		List<List<CorpLimitDataBean>> corpLimitDataList = new ArrayList<>();
		CorpTransLimitBean allTransData = new CorpTransLimitBean();

		try {
			String sqlQuery = "select aw.id,aw.channelId,aw.createdByUserId,aw.pendingWithUserId,aw.createdByRoleId,aw.pendingWithRoleId,aw.pageId,"
					+ "aw.activityId,aw.activityName,aw.activityRefNo,aw.createdOn,aw.updatedOn,aw.createdBy,aw.updatedBy,aw.content,aw.statusId,aw.userAction,"
					+ "aw.remark from adminWorkFlowRequest aw where aw.id=:adminWorkFlowId" + " ";

			contentList = getSession().createSQLQuery(sqlQuery).addScalar("id", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("channelId", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("createdByUserId", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("pendingWithUserId", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("pageId", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("activityId", StandardBasicTypes.BIG_DECIMAL).addScalar("activityName")
					.addScalar("activityRefNo", StandardBasicTypes.BIG_DECIMAL).addScalar("createdOn")
					.addScalar("updatedOn").addScalar("createdBy", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("updatedBy", StandardBasicTypes.BIG_DECIMAL).addScalar("content")
					.addScalar("statusId", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("userAction", StandardBasicTypes.BIG_DECIMAL).addScalar("remark")
					.setParameter("adminWorkFlowId", adminWorkFlowId)
					.setResultTransformer(Transformers.aliasToBean(AdminWorkFlowRequestEntity.class)).list();

			if (("corpSetLimitEdit").equals(contentList.get(0).getActivityName())) {
				allTransData = getTrasLimitDataByAdminWorkFlowIdFoeUpdate(adminWorkFlowId, accNo);

			} else {
				System.out.println(contentList.get(0).getContent());
				String string = contentList.get(0).getContent();
				JSONObject json = new JSONObject(string);
				JSONArray corpLimitData = json.getJSONArray("corpLimitData");
				accNo = json.get("accountNumber").toString();
				allTransData.setCurrency(json.get("currency").toString());
				allTransData.setCorpCompId(new BigDecimal(json.get("corpCompId").toString()));
				List<CorpLimitDataBean> corpLimitDataObjLst = new ArrayList<>();
				for (int i = 0; i < corpLimitData.length(); i++) {
					JSONObject object = corpLimitData.getJSONObject(i);
					CorpLimitDataBean corpLimitDataObj = new CorpLimitDataBean();

					corpLimitDataObj.setApproverLevelId(new BigDecimal(object.get("approverLevelId").toString()));
					corpLimitDataObj.setApproverMasterId(new BigDecimal(object.get("approverMasterId").toString()));
					corpLimitDataObj.setHierarchyMasterId(new BigDecimal(object.get("hierarchyMasterId").toString()));
					corpLimitDataObj.setMaxAmount(new BigDecimal(object.get("maxAmount").toString()));
					corpLimitDataObj.setMinAmount(new BigDecimal(object.get("minAmount").toString()));
					corpLimitDataObj.setApproverMasterName(object.get("approverMasterName").toString());
					corpLimitDataObj.setHierarchyMasterName(object.get("hierarchyMasterName").toString());

					List<CorpUserBean> corpUserDataBean = new ArrayList<>();
					JSONArray arrObj = (JSONArray) object.get("corpUserId");
					for (int j = 0; j < arrObj.length(); j++) {
						CorpUserBean corpUserDataIds = new CorpUserBean();
						corpUserDataIds.setUserId(new BigInteger(arrObj.get(j).toString()));
						List<CorpUserEntity> userList = getCorpUserById(Integer.parseInt(arrObj.get(j).toString()));

						corpUserDataIds.setCorpUSerName(
								userList.get(0).getFirst_name() + " " + userList.get(0).getLast_name());
						corpUserDataBean.add(corpUserDataIds);

					}

					corpLimitDataObj.setCorpUserData(corpUserDataBean);

					corpLimitDataObjLst.add(corpLimitDataObj);

				}
				corpLimitDataList.add(corpLimitDataObjLst);
				allTransData.setCorpLimitListData(corpLimitDataList);
				allTransData.setAccountNumber(accNo);

			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return allTransData;
	}

	@Override
	public CorpTransLimitBean getTrasLimitDataByAdminWorkFlowIdFoeUpdate(long adminWorkFlowId, String accNo) {
		List<AdminWorkFlowRequestEntity> contentList = null;
		List<List<CorpLimitDataBean>> corpLimitDataList = new ArrayList<>();
		CorpTransLimitBean allTransData = new CorpTransLimitBean();

		try {
			String sqlQuery = "select aw.id,aw.channelId,aw.createdByUserId,aw.pendingWithUserId,aw.createdByRoleId,aw.pendingWithRoleId,aw.pageId,"
					+ "aw.activityId,aw.activityName,aw.activityRefNo,aw.createdOn,aw.updatedOn,aw.createdBy,aw.updatedBy,aw.content,aw.statusId,aw.userAction,"
					+ "aw.remark from adminWorkFlowRequest aw where aw.id=:adminWorkFlowId" + " ";

			contentList = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("channelId")
					.addScalar("createdByUserId").addScalar("pendingWithUserId").addScalar("createdByRoleId")
					.addScalar("pendingWithRoleId").addScalar("pageId").addScalar("activityId")
					.addScalar("activityName").addScalar("activityRefNo").addScalar("createdOn").addScalar("updatedOn")
					.addScalar("createdBy").addScalar("updatedBy").addScalar("content").addScalar("statusId")
					.addScalar("userAction").addScalar("remark").setParameter("adminWorkFlowId", adminWorkFlowId)
					.setResultTransformer(Transformers.aliasToBean(AdminWorkFlowRequestEntity.class)).list();
			System.out.println(contentList.get(0).getContent());
			String string = contentList.get(0).getContent();
			JSONObject json = new JSONObject(string);
			accNo = json.get("accountNumber").toString();
			allTransData.setCurrency(json.get("currency").toString());
			allTransData.setCorpCompId(new BigDecimal(json.get("corpCompId").toString()));
			JSONArray corpLimitData = json.getJSONArray("corpLimitData");

			List<CorpLimitDataBean> corpLimitDataObjLst = new ArrayList<>();
			for (int i = 0; i < corpLimitData.length(); i++) {
				JSONObject object = corpLimitData.getJSONObject(i);
				CorpLimitDataBean corpLimitDataObj = new CorpLimitDataBean();

				corpLimitDataObj.setApproverLevelId(new BigDecimal(object.get("approverLevelId").toString()));
				corpLimitDataObj.setApproverMasterId(new BigDecimal(object.get("approverMasterId").toString()));
				corpLimitDataObj.setHierarchyMasterId(new BigDecimal(object.get("hierarchyMasterId").toString()));
				corpLimitDataObj.setMaxAmount(new BigDecimal(object.get("maxAmount").toString()));
				corpLimitDataObj.setMinAmount(new BigDecimal(object.get("minAmount").toString()));
				corpLimitDataObj.setApproverMasterName(object.get("approverMasterName").toString());
				corpLimitDataObj.setHierarchyMasterName(object.get("hierarchyMasterName").toString());

				List<CorpUserBean> corpUserDataBean = new ArrayList<>();
				JSONArray arrObj = (JSONArray) object.get("corpUserData");
				for (int j = 0; j < arrObj.length(); j++) {
					JSONObject userJsonObj = arrObj.getJSONObject(j);
					CorpUserBean corpUserDataIds = new CorpUserBean();

					corpUserDataIds.setUserId(new BigInteger(userJsonObj.get("userId").toString()));
					List<CorpUserEntity> userList = getCorpUserById(
							Integer.parseInt(userJsonObj.get("userId").toString()));
					corpUserDataIds
							.setCorpUSerName(userList.get(0).getFirst_name() + " " + userList.get(0).getLast_name());
					corpUserDataBean.add(corpUserDataIds);

				}

				corpLimitDataObj.setCorpUserData(corpUserDataBean);

				corpLimitDataObjLst.add(corpLimitDataObj);

			}
			corpLimitDataList.add(corpLimitDataObjLst);
			allTransData.setCorpLimitListData(corpLimitDataList);
			allTransData.setAccountNumber(accNo);

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return allTransData;
	}

	@Override
	public boolean setCorpTransactionsLimitByApprover(CorpTransLimitBean corpTransData) {

		corpTransData.setStatusId(BigDecimal.valueOf(3));
		corpTransData.setAppId(BigDecimal.valueOf(2));

		try {

			for (CorpLimitDataBean corpLimitDatas : corpTransData.getCorpLimitListData().get(0)) {
				// save data to corp trasaction limit
				CorpTransactionLimitEntity corpTransDataObj = new CorpTransactionLimitEntity();
				corpTransDataObj.setCorpCompId(corpTransData.getCorpCompId());
				corpTransDataObj.setMinAmount(corpLimitDatas.getMinAmount());
				corpTransDataObj.setMaxAmount(corpLimitDatas.getMaxAmount());
				corpTransDataObj.setCurrency(corpTransData.getCurrency());
				corpTransDataObj.setCreatedBy(corpTransData.getCreatedBy());
				corpTransDataObj.setStatusId(corpTransData.getStatusId());
				corpTransDataObj.setAppId(corpTransData.getAppId());
				corpTransData.setApproverLimitId(corpTransData.getAppId());

				int tranLimitId = saveCorpTransLimit(corpTransDataObj);

				// save data to CORP_ACCT_TRANS_MASTER
				CorpAcctTransMasterEntity corpAcctTransMasterObj = new CorpAcctTransMasterEntity();
				corpAcctTransMasterObj.setCorporateCompId(corpTransData.getCorpCompId());
				corpAcctTransMasterObj.setAccountNumber(corpTransData.getAccountNumber());
				corpAcctTransMasterObj.setTranLimitId(BigDecimal.valueOf(tranLimitId));
				corpAcctTransMasterObj.setApproverLevelId(corpLimitDatas.getApproverLevelId());
				corpAcctTransMasterObj.setApproverMasterId(corpLimitDatas.getApproverMasterId());
				corpAcctTransMasterObj.setHierarchyMasterId(corpLimitDatas.getHierarchyMasterId());
				corpAcctTransMasterObj.setCreatedBy(BigDecimal.valueOf(corpTransData.getCreatedBy()));
				corpAcctTransMasterObj.setStatusId(corpTransData.getStatusId());
				corpAcctTransMasterObj.setAppId(corpTransData.getAppId());

				saveCorpAcctTransMaster(corpAcctTransMasterObj);
				corpTransData.setCatmId(getAllCorpTransLimitMasterData().get(0).getId());

				if (corpLimitDatas.getCorpUserId() != null) {
					// save data to CORP_ACCT_TRANS_LIMIT_USERS
					for (BigDecimal corpUserIdData : corpLimitDatas.getCorpUserId()) {
						CorpAcctTransLimitUsersEntity corpAcctTransLimitUserObj = new CorpAcctTransLimitUsersEntity();
						corpAcctTransLimitUserObj.setCorporateCompId(corpTransData.getCorpCompId());
						corpAcctTransLimitUserObj.setCatmId(corpTransData.getCatmId());
						corpAcctTransLimitUserObj.setCorpUserId(corpUserIdData);
						corpAcctTransLimitUserObj.setStatusId(corpTransData.getStatusId());
						corpAcctTransLimitUserObj.setAppId(corpTransData.getAppId());
						corpAcctTransLimitUserObj.setCreatedBy(BigDecimal.valueOf(corpTransData.getCreatedBy()));
						saveCorpAcctTransLimitUser(corpAcctTransLimitUserObj);

					}
				} else if (corpLimitDatas.getCorpUserData() != null) {
					for (CorpUserBean corpUserIdData : corpLimitDatas.getCorpUserData()) {

						CorpAcctTransLimitUsersEntity corpAcctTransLimitUserObj = new CorpAcctTransLimitUsersEntity();
						corpAcctTransLimitUserObj.setCorporateCompId(corpTransData.getCorpCompId());
						corpAcctTransLimitUserObj.setCatmId(corpTransData.getCatmId());
						corpAcctTransLimitUserObj.setCorpUserId(new BigDecimal(corpUserIdData.getUserId()));
						corpAcctTransLimitUserObj.setId(new BigDecimal(corpUserIdData.getTransLimitUserId()));
						corpAcctTransLimitUserObj.setStatusId(corpTransData.getStatusId());
						corpAcctTransLimitUserObj.setAppId(corpTransData.getAppId());
						corpAcctTransLimitUserObj.setCreatedBy(BigDecimal.valueOf(corpTransData.getCreatedBy()));
						saveCorpAcctTransLimitUser(corpAcctTransLimitUserObj);

					}
				}
			}

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
		return true;
	}

	public List<CorpUserTypeEntity> getDynamicRolesByCompId(int compId) {
		List<CorpUserTypeEntity> corpRolesList = null;
		try {
			// String sqlQuery = "select distinct ut.USER_TYPE,ut.id from corp_user_types ut
			// inner join corp_users cu on ut.id = cu.USER_TYPE where cu.CORP_COMP_ID
			// =:compId";

			String sqlQuery = "select distinct ut.authtype as USER_TYPE from DESIGNATION_HIERARCHY_MASTER ut where ut.companyid =:compId";
			corpRolesList = getSession().createSQLQuery(sqlQuery).setParameter("compId", compId)
					.setResultTransformer(Transformers.aliasToBean(CorpUserTypeEntity.class)).list();

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return corpRolesList;

	}

	@Override
	public ResponseMessageBean checkUserAccountExist(CorpAccUserTypeEntity corpUserAccData) {

		ResponseMessageBean rmb = new ResponseMessageBean();
		try {
			String sqlNameExist = " SELECT count(*) FROM CORP_ACC_USER_TYPE WHERE ACCOUNT_TYPE_ID =:accountTypeId AND  CORP_USER_TYPE_ID=:corpUserTypeId  ";

			List nameExit = getSession().createSQLQuery(sqlNameExist)
					.setParameter("accountTypeId", corpUserAccData.getAccountTypeId())
					.setParameter("corpUserTypeId", corpUserAccData.getCorpUserTypeId()).list();

			if (!(nameExit.get(0).toString().equalsIgnoreCase("0"))) {
				rmb.setResponseCode("500");
				rmb.setResponseMessage("Account Type Is Already Mapped  For Selected User Type");
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
	public ResponseMessageBean checkUpdateUserAccountExist(CorpAccUserTypeEntity corpUserAccData) {

		ResponseMessageBean rmb = new ResponseMessageBean();
		try {
			String sqlNameExist = " SELECT count(*) FROM CORP_ACC_USER_TYPE WHERE ACCOUNT_TYPE_ID =:accountTypeId AND  CORP_USER_TYPE_ID=:corpUserTypeId  AND ID !=:id ";

			List nameExit = getSession().createSQLQuery(sqlNameExist)
					.setParameter("accountTypeId", corpUserAccData.getAccountTypeId())
					.setParameter("id", corpUserAccData.getId())
					.setParameter("corpUserTypeId", corpUserAccData.getCorpUserTypeId()).list();

			if (!(nameExit.get(0).toString().equalsIgnoreCase("0"))) {
				rmb.setResponseCode("500");
				rmb.setResponseMessage("Account Type Is Already Mapped  For Selected User Type");
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
	public List<CorpUserEntity> getFirstCorpUserById(BigInteger id) {
		List<CorpUserEntity> list = null;
		try {
			String sqlQuery = "select cu.id,cu.designation,cu.corp_comp_id,cu.user_disp_name,cu.user_name,cu.user_pwd,cu.createdby,cu.createdon,cu.statusid,"
					+ "cu.appid,cu.user_type,cu.first_name,cu.last_name,cu.email_id,cu.country,cu.work_phone,cu.personal_Phone,"
					+ "cu.nationalId,cu.passport,cu.boardResolution,cu.user_image,cu.tpin,cu.passportNumber,cu.nationalIdNumber,cu.city,"
					+ "cu.state , ccm.Companyname as companyName, ut.user_type as userType,dh.DESIGNATIONNAME as designationName  from CORP_USERS cu "
					+ "left join CORP_COMPANY_MASTER ccm on ccm.id= cu.corp_comp_id left join CORP_USER_TYPES ut on ut.id=cu.user_type "
					+ " left join DESIGNATION_HIERARCHY_MASTER dh on dh.id= cu.designation "
					+ "inner join statusmaster s on s.id = cu.statusid where cu.statusId=15 and  cu.id=:id";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("designation", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("corp_comp_id", StandardBasicTypes.BIG_DECIMAL).addScalar("user_disp_name")
					.addScalar("user_name").addScalar("user_pwd", StandardBasicTypes.STRING)
					.addScalar("createdby", StandardBasicTypes.BIG_DECIMAL).addScalar("createdon")
					.addScalar("statusid", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("appid", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("user_type", StandardBasicTypes.BIG_DECIMAL).addScalar("first_name")
					.addScalar("last_name").addScalar("email_id").addScalar("country", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("work_phone").addScalar("personal_Phone").addScalar("nationalId")
					.addScalar("passport", StandardBasicTypes.STRING)
					.addScalar("boardResolution", StandardBasicTypes.STRING)
					.addScalar("user_image", StandardBasicTypes.STRING).addScalar("tpin").addScalar("passportNumber")
					.addScalar("nationalIdNumber").addScalar("city", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("state", StandardBasicTypes.BIG_DECIMAL).addScalar("companyName").addScalar("userType")
					.addScalar("designationName").setParameter("id", id)
					.setResultTransformer(Transformers.aliasToBean(CorpUserEntity.class)).list();

			for (CorpUserEntity corpData : list) {
				if (corpData.getWork_phone().contains("=") && corpData.getPersonal_Phone().contains("=")
						&& corpData.getEmail_id().contains("=")) {
					corpData.setEmail_id(EncryptorDecryptor.decryptData(corpData.getEmail_id()));
					corpData.setWork_phone(EncryptorDecryptor.decryptData(corpData.getWork_phone()));
					corpData.setPersonal_Phone(EncryptorDecryptor.decryptData(corpData.getPersonal_Phone()));
				}
			}

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public List<CorpUserEntity> getFirstAllCorpUsers() {
		List<CorpUserEntity> list = null;
		try {
			String sqlQuery = "select cu.id,cu.designation,cu.corp_comp_id,cu.user_disp_name,cu.user_name,cu.user_pwd,cu.createdby,cu.createdon,cu.statusid,"
					+ "cu.appid,cu.user_type,cu.first_name,cu.last_name,cu.email_id,cu.country,cu.work_phone,cu.personal_Phone,"
					+ "cu.nationalId,cu.passport,cu.boardResolution,cu.user_image,cu.tpin,cu.passportNumber,cu.nationalIdNumber,cu.city,"
					+ "cu.state , ccm.Companyname as companyName, ut.user_type as userType,dh.DESIGNATIONNAME as designationName  from CORP_USERS cu "
					+ "left join CORP_COMPANY_MASTER ccm on ccm.id= cu.corp_comp_id left join CORP_USER_TYPES ut on ut.id=cu.user_type "
					+ " left join DESIGNATION_HIERARCHY_MASTER dh on dh.id= cu.designation "
					+ "inner join statusmaster s on s.id = cu.statusid where cu.statusId=15 " + " order by cu.id desc ";
			list = getSession().createSQLQuery(sqlQuery).addScalar("id", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("designation", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("corp_comp_id", StandardBasicTypes.BIG_DECIMAL).addScalar("user_disp_name")
					.addScalar("user_name").addScalar("user_pwd", StandardBasicTypes.STRING)
					.addScalar("createdby", StandardBasicTypes.BIG_DECIMAL).addScalar("createdon")
					.addScalar("statusid", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("appid", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("user_type", StandardBasicTypes.BIG_DECIMAL).addScalar("first_name")
					.addScalar("last_name").addScalar("email_id").addScalar("country", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("work_phone").addScalar("personal_Phone").addScalar("nationalId")
					.addScalar("passport", StandardBasicTypes.STRING)
					.addScalar("boardResolution", StandardBasicTypes.STRING)
					.addScalar("user_image", StandardBasicTypes.STRING).addScalar("tpin").addScalar("passportNumber")
					.addScalar("nationalIdNumber").addScalar("city", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("state", StandardBasicTypes.BIG_DECIMAL).addScalar("companyName").addScalar("userType")
					.addScalar("designationName").setResultTransformer(Transformers.aliasToBean(CorpUserEntity.class))
					.list();

			for (CorpUserEntity corpData : list) {
				if (corpData.getWork_phone().contains("=") && corpData.getPersonal_Phone().contains("=")
						&& corpData.getEmail_id().contains("=")) {
					corpData.setEmail_id(EncryptorDecryptor.decryptData(corpData.getEmail_id()));
					corpData.setWork_phone(EncryptorDecryptor.decryptData(corpData.getWork_phone()));
					corpData.setPersonal_Phone(EncryptorDecryptor.decryptData(corpData.getPersonal_Phone()));
				}
			}

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public boolean updateFirstCorpUsers(CorpUserEntity corpUserData) {
		util.setUpandSendEmail(corpUserData.getEmail_id(), "ABC");
		return false;
	}

	@Override
	public ResponseMessageBean getAccountsByCif(String cif, String rrn, String referenceNumber) {
		ResponseMessageBean respMsg = new ResponseMessageBean();
		String res;
		try {
			res = rest.restFectchAccByCif(cif, rrn, referenceNumber);
			respMsg.setResult(res);
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return respMsg;
	}

	@Override
	public ResponseMessageBean checkEmailExistByEmailId(String email) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		try {
			String sqlNameExist = "select count(*) from corp_users_master where email=:email";

			List emailE = getSession().createSQLQuery(sqlNameExist).setParameter("email", email).list();

			if (!(emailE.get(0).toString().equalsIgnoreCase("0"))) {
				rmb.setResponseCode("202");
				rmb.setResponseMessage("Email is already exist");
			} else {
				rmb.setResponseCode("200");
				rmb.setResponseMessage("Email not exist ");
			}

		} catch (Exception e) {
			LOGGER.info("Exception:", e);

			e.printStackTrace();
		}
		return rmb;
	}

}
