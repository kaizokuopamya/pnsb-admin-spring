package com.itl.pns.dao.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import com.itl.pns.bean.ActivityMasterBean;
import com.itl.pns.bean.AdminPortalUserActivityLogs;
import com.itl.pns.bean.AdminUserActivityLogs;
import com.itl.pns.bean.ChangePasswordBean;
import com.itl.pns.bean.DateBean;
import com.itl.pns.bean.FraudReportingBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.bean.UserAddEditRequestBean;
import com.itl.pns.bean.UserDetailsBean;
import com.itl.pns.constant.ApplicationConstants;
import com.itl.pns.dao.AdministrationDao;
import com.itl.pns.dao.MasterConfigDao;
import com.itl.pns.entity.AccountTypesEntity;
import com.itl.pns.entity.ActivityMaster;
import com.itl.pns.entity.ActivitySettingMasterEntity;
import com.itl.pns.entity.AdminWorkFlowRequestEntity;
import com.itl.pns.entity.AdminWorkflowHistoryEntity;
import com.itl.pns.entity.ConfigMasterEntity;
import com.itl.pns.entity.CustNotificationCategoriesEntity;
import com.itl.pns.entity.CustomerSurveyEntity;
import com.itl.pns.entity.OmniLimitMasterEntity;
import com.itl.pns.entity.RMMASTER;

import com.itl.pns.entity.Role;
import com.itl.pns.entity.User;
import com.itl.pns.entity.UserCredentialsSessionEntity;
import com.itl.pns.entity.UserDetails;
import com.itl.pns.util.AdminEmailUtil;
import com.itl.pns.util.AdminWorkFlowReqUtility;
import com.itl.pns.util.CommonCbsCall;
import com.itl.pns.util.EmailUtil;
import com.itl.pns.util.EncryptDeryptUtility;
import com.itl.pns.util.EncryptorDecryptor;
import com.itl.pns.util.GlobalPropertyReader;
import com.itl.pns.util.PasswordSecurityUtil;
import com.itl.pns.util.RandomNumberGenerator;

@Repository("IUserDAO")
@Transactional
public class AdministrationDaoImpl implements AdministrationDao {

	@Autowired
	EmailUtil util;

	@Autowired
	AdminEmailUtil adminEmailUtil;

	@Autowired
	CommonCbsCall commonCbsCall;

	private static final Logger logger = LogManager.getLogger(AdministrationDaoImpl.class);

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	@Autowired
	AdminWorkFlowReqUtility adminWorkFlowReqUtility;

	@Autowired
	MasterConfigDao masterConfigDao;

	protected Session getSession() {
		if (sessionFactory.getCurrentSession() != null)
			return sessionFactory.getCurrentSession();
		else
			return sessionFactory.openSession();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserDetailsBean> getUserList() {
		StringBuilder qry = new StringBuilder();

		qry.append(
				"SELECT UM.ID AS USERID1, UM.USERID,UD.FIRST_NAME,UD.LAST_NAME,UD.EMAIL,UD.PHONENUMBER,R.CODE,R.NAME AS ROLE,UM.LOGINTYPE,UM.REMARK,"
						+ "	R.DESCRIPTION,UM.STATUSID,UM.ID AS USER_ID, UD.ID AS USER_DETAIL_ID, R.ID AS ROLE_ID, R.ROLETYPE,RT.NAME   "
						+ "\"roleTypeName\"" + " " + "," + "	R.STATUSID AS ROLESTATUS, UM.USERID  "
						+ "\"createdByName\"" + " "
						+ " FROM USER_MASTER UM INNER JOIN USER_DETAILS UD ON UM.ID= UD.USER_MASTER_ID "
						+ "	INNER JOIN ROLES R ON UD.ROLE_ID=R.ID Inner JOIN ROLE_TYPES RT ON R.ROLETYPE = RT.ID  WHERE UM.ISDELETED='N' AND UM.STATUSID <> 36 ORDER BY UM.ID DESC");

		return getSession().createSQLQuery(qry.toString())
				.setResultTransformer(Transformers.aliasToBean(UserDetailsBean.class)).list();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserDetailsBean> getAllReporteesUsers(String userCode) {
		StringBuilder qry = new StringBuilder();
		qry.append(
				"select distinct branchcode,cbl.BRANCH_NAME FROM USER_MASTER UM inner join cbs_branch_list cbl on cbl.BRANCH_CODE=um.branchcode\r\n"
						+ "WHERE REPORTINGBRANCH IN (SELECT BRANCHCODE FROM USER_MASTER WHERE USERID=:userCode)");

		return getSession().createSQLQuery(qry.toString()).setParameter("userCode", userCode)
				.setResultTransformer(Transformers.aliasToBean(UserDetailsBean.class)).list();

	}

	@Override
	public boolean updateUserStatus(UserAddEditRequestBean userAddEditReqBean) {
		boolean isUpdated = false;
		logger.info("Update user master status");
		try {
			String sqlQuery = "update user_master set updateby=:updatedby ,updateon=:currDate,statusId=:statusId where id=:id";
			int result = getSession().createSQLQuery(sqlQuery.toString())
					.setParameter("updatedby", userAddEditReqBean.getUpdatedBy())
					.setParameter("statusId", userAddEditReqBean.getStatusId()).setParameter("currDate", new Date())
					.setParameter("id", userAddEditReqBean.getUSERMASTERID()).executeUpdate();
			if (result > 0) {
				isUpdated = true;
			}
			return isUpdated;
		} catch (Exception e) {
			logger.error("Exception Occured.", e);
			return isUpdated;
		}
	}

	@Override
	public boolean updateBranchStatus(String userCode, String oldBranchCode, String newBranchCode, int roleId,
			String mobileNumber, String emailId) {
		boolean isUpdated = false;
		logger.info("Update user Branch " + oldBranchCode + " to " + newBranchCode + " for userid " + userCode);
		try {
			String sqlQuery = "update user_master set branchcode=:branchcode where userid=:userCode and statusid=3";
			int result = getSession().createSQLQuery(sqlQuery.toString()).setParameter("branchcode", newBranchCode)
					.setParameter("userCode", userCode).executeUpdate();
			if (result > 0) {

				String sqlGetUserId = "select id from user_master where userid=:userid";
				List lstGetUserList = getSession().createSQLQuery(sqlGetUserId).setParameter("userid", userCode).list();

				String sqlQueryDetails = "update USER_DETAILS set ROLE_ID=:roleId,PHONENUMBER=:mobNumber,EMAIL=:emailAddress where USER_MASTER_ID=:userCode and statusid=3";
				int resultDetails = getSession().createSQLQuery(sqlQueryDetails.toString())
						.setParameter("roleId", roleId).setParameter("mobNumber", mobileNumber)
						.setParameter("emailAddress", emailId).setParameter("userCode", Integer.parseInt(lstGetUserList.get(0).toString())).executeUpdate();
				if (resultDetails > 0) {
					isUpdated = true;
				}
			}

			return isUpdated;
		} catch (Exception e) {
			logger.error("Exception Occured.", e);
			return isUpdated;
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public ResponseMessageBean checkUser(UserAddEditRequestBean userAddEditReqBean) {
		logger.info("AdministrationController->AdminstartionDAO->checkUser----------Start");
		ResponseMessageBean rmb = new ResponseMessageBean();
		try {
			// String sqlEmailExist = "select count(*) from user_details where
			// email=:email";
			String sqlEmailExist = "select count(*) from user_details INNER JOIN user_master ON user_details.USER_MASTER_ID = user_master.ID where email=:email and isdeleted='N'";
			String sqlUserExist = "select count(*) from user_master where userid=:userid and isdeleted='N'";

			System.out.println(sqlEmailExist);
			System.out.println(sqlUserExist);

			List lsUserExist = getSession().createSQLQuery(sqlUserExist)
					.setParameter("userid", userAddEditReqBean.getUSERID().toLowerCase()).list();

			List lsEmailExist = getSession().createSQLQuery(sqlEmailExist)
					.setParameter("email", userAddEditReqBean.getEMAIL()).list();

			if (!(lsUserExist.get(0).toString().equalsIgnoreCase("0"))) {
				rmb.setResponseCode("500");
				rmb.setResponseMessage("User ID Already Exist");
			} else if (!(lsEmailExist.get(0).toString().equalsIgnoreCase("0"))) {
				rmb.setResponseCode("500");
				rmb.setResponseMessage("Email ID Already Exist");
			} else {
				rmb.setResponseCode("200");
				rmb.setResponseMessage("Both not exist");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}
		logger.info("AdministrationController->AdminstartionDAO->checkUser----------End");
		return rmb;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserDetailsBean> getUserDetails(int id) {
		List<UserDetailsBean> list = null;
		try {

			String sql = "SELECT UM.USERID ,UM.CREATEDBY,UM.THUMBNAIL ,UM.LOGINTYPE,UD.FIRST_NAME,UD.LAST_NAME,UD.EMAIL,UD.PHONENUMBER,R.CODE,"
					+ "R.NAME AS ROLE,UM.ID AS USER_ID, UD.ID AS USER_DETAIL_ID, R.ID AS ROLE_ID,R.ROLETYPE, R.STATUSID"
					+ " AS ROLESTATUS, UM.REPORTINGBRANCH, UM.BRANCHCODE,CB.BRANCH_NAME, CB.BRANCHZONE"
					+ " FROM USER_MASTER UM"
					+ " INNER JOIN USER_DETAILS UD ON UM.ID= UD.USER_MASTER_ID"
					+ " INNER JOIN ROLES R ON UD.ROLE_ID=R.ID "
					+ " INNER JOIN CBS_BRANCH_LIST CB ON UM.BRANCHCODE = CB.BRANCH_CODE and  UM.REPORTINGBRANCH = CB.ZONECODE WHERE UM.ID=:id";

			list = getSession().createSQLQuery(sql).setParameter("id", id)
					.setResultTransformer(Transformers.aliasToBean(UserDetailsBean.class)).list();
			for (UserDetailsBean cm : list) {
				try {
					String image1 = EncryptDeryptUtility.clobStringConversion(cm.getTHUMBNAIL());
					cm.setTHUMBNAILSTRING(image1);
					cm.setTHUMBNAIL(null);

				} catch (IOException e) {
					logger.error("Exception Occured ", e);
				} catch (SQLException e1) {
					logger.error("Exception Occured ", e1);
				}
			}
		} catch (Exception e) {

			logger.info("Exception:", e);
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Role> getAllRoles() {
		List<Role> list = null;
		try {
			String sqlQuery = "select r.id,r.statusId,r.name,r.code ,r.description,r.createdby,r.updateby as updatedBy, r.roleType,RT.name as roleTypeName from roles r left join ROLE_TYPES RT on  r.roleType=RT.ID "
					+ "where  r.statusId in(3,0) order by r.id desc";
			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("name").addScalar("code")
					.addScalar("description").addScalar("createdBy").addScalar("roleTypeName").addScalar("statusId")
					.addScalar("updatedBy").addScalar("roleType")
					.setResultTransformer(Transformers.aliasToBean(Role.class)).list();
		} catch (Exception e) {
			logger.error("Exception Occured", e);
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Role> getRolesByDisplayName(Role role) {
		List<Role> list = null;
		try {
			String sqlQuery = "select r.id,r.name as name,r.code,r.statusId,r.description,r.createdby,r.updateby as updatedBy from roles r  "
					+ " where r.name =:name ";
			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("name").addScalar("code")
					.addScalar("description").addScalar("createdBy").addScalar("statusId").addScalar("updatedBy")
					.setParameter("name", role.getName()).setResultTransformer(Transformers.aliasToBean(Role.class))
					.list();
		} catch (Exception e) {
			logger.error("Exception Occured", e);
		}
		return list;
	}

	@Override
	public List<Role> getActiveRoles(BigInteger roleType) {
		List<Role> list = null;
		try {
			if (roleType.intValue() == 1) {
				String sqlQuery = "select r.id,r.name as name ,r.code,r.ROLETYPE as roleType from roles r  where  r.statusId=:statusId ORDER BY name ASC";
				list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("name").addScalar("code")
						.addScalar("roleType").setParameter("statusId", ApplicationConstants.ACTIVE_SATUS)
						.setResultTransformer(Transformers.aliasToBean(Role.class)).list();
			} else {
				String sqlQuery = "select r.id,r.name as name ,r.code,r.ROLETYPE as roleType from roles r  where  r.statusId=:statusId and r.ROLETYPE != 1 ORDER BY name ASC";
				list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("name").addScalar("code")
						.addScalar("roleType").setParameter("statusId", ApplicationConstants.ACTIVE_SATUS)
						.setResultTransformer(Transformers.aliasToBean(Role.class)).list();
			}
		} catch (Exception e) {
			logger.error("Exception Occured", e);
		}
		return list;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public ResponseMessageBean getRoleByRoleCodeAndName(String code, String displayName) {

		ResponseMessageBean rmb = new ResponseMessageBean();
		String sqlRoleCodeExist = "select count(*) from roles where Lower(code)=:code and statusid=3";
		String sqlRoleNameExist = "select count(*) from roles where Lower(name)=:displayName and statusid=3";

		List lsRoleCodeExis = getSession().createSQLQuery(sqlRoleCodeExist).setParameter("code", code.toLowerCase())
				.list();
		List lsRoleNameExist = getSession().createSQLQuery(sqlRoleNameExist)
				.setParameter("displayName", displayName.toLowerCase()).list();
		if (!(lsRoleNameExist.get(0).toString().equalsIgnoreCase("0"))) {
			rmb.setResponseCode("500");
			rmb.setResponseMessage("Role Already Exist");
		} else if (!(lsRoleCodeExis.get(0).toString().equalsIgnoreCase("0"))) {
			rmb.setResponseCode("500");
			rmb.setResponseMessage("Role Code Already Exist");
		} else {
			rmb.setResponseCode("200");
			rmb.setResponseMessage("Both not exist");
		}
		return rmb;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public ResponseMessageBean checkIsRoleExist(String displayName) {

		ResponseMessageBean rmb = new ResponseMessageBean();
		String sqlRoleNameExist = "select count(*) from roles where Lower(name)=:displayName and statusid=3";

		List lsRoleNameExist = getSession().createSQLQuery(sqlRoleNameExist)
				.setParameter("displayName", displayName.toLowerCase()).list();
		if (!(lsRoleNameExist.get(0).toString().equalsIgnoreCase("0"))) {
			rmb.setResponseCode("500");
			rmb.setResponseMessage("Role Already Exist");
		} else {
			rmb.setResponseCode("200");
			rmb.setResponseMessage("Both not exist");
		}
		return rmb;
	}

	@Override
	public boolean updateRolesStatus(Role role) {
		boolean isUpdated = true;
		try {
			String sql = "update roles set updateby=:updatedby,updateon=:currDate,statusId=:stsId where id=:id";
			getSession().createSQLQuery(sql.toString()).setParameter("updatedby", role.getUpdatedBy())
					.setParameter("stsId", role.getStatusId()).setParameter("currDate", new Date())
					.setParameter("id", role.getId()).executeUpdate();
			String str = "select user_master_id from user_details where role_id=:roleid";
			List results = getSession().createSQLQuery(str).setParameter("roleid", role.getId()).list();
			String str1 = "";
			Iterator<Integer> iterator = results.iterator();
			while (iterator.hasNext()) {
				if (role.getStatusId().intValue() == 0) {
					str1 = "update user_master set statusId=0 where id=:id";
				} else {
					str1 = "update user_master set statusId=3 where id=:id";
				}
				getSession().createSQLQuery(str1).setParameter("id", iterator.next()).executeUpdate();
			}

		} catch (Exception e) {

			logger.info("Exception:", e);
			return isUpdated;
		}
		return isUpdated;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Role> getRoleDetails(BigInteger id) {
		String sqlQuery = "select id as id, code as code,name as name ,statusId as statusId,"
				+ " description as description,createdby as createdBy,updateby as updatedBy, roleType from roles where id=:id";

		List<Role> list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("code").addScalar("name")
				.addScalar("statusId").addScalar("description").addScalar("createdBy").addScalar("updatedBy")
				.addScalar("roleType").setParameter("id", id).setResultTransformer(Transformers.aliasToBean(Role.class))
				.list();

		return list;
	}

	@Override
	public boolean forgetPassword(ChangePasswordBean changebean) {
		boolean ismailsend = false;
		String mailid = changebean.getEMAILID();
		try {
			String qry = "select um.id from user_master um inner join user_details ud on um.id = ud.user_master_id where"
					+ " um.userid=:userid and ud.email=:email";
			System.out.println("##########1");
			List<ChangePasswordBean> list = getSession().createSQLQuery(qry.toString())
					.setParameter("userid", changebean.getUSERID()).setParameter("email", changebean.getEMAILID())
					.setResultTransformer(Transformers.aliasToBean(ChangePasswordBean.class)).list();
			System.out.println("##########2");
			if (!list.isEmpty()) {
				BigInteger id = list.get(0).getID().toBigInteger();
				RandomNumberGenerator object = new RandomNumberGenerator();
				String newpassword = object.generateRandomString();
				ismailsend = true;
				util.setUpandSendEmail(mailid, newpassword);
				// String encryptpass = EncryptDeryptUtility.md5(newpassword);
				String encryptpass = PasswordSecurityUtil.toHexString(PasswordSecurityUtil.getSHA(newpassword));

				String sql = " update user_master set password=:password where id=:id";
				int in = getSession().createSQLQuery(sql).setParameter("password", encryptpass).setParameter("id", id)
						.executeUpdate();
				if (in > 0) {
					ismailsend = true;
					logger.info("Password sent successfully");
				} else
					ismailsend = false;
			}
		} catch (Exception e) {
			logger.debug(" Error ", e);
			System.out.println("##########Exception" + e);
			ismailsend = false;
		}
		return ismailsend;
	}

	public boolean logoutByUserId(String userid) {
		String sql = "update user_token set status=1 where user_id=(select id from user_master where userid=:userid)";
		int executeUpdate = getSession().createSQLQuery(sql).setParameter("userid", userid).executeUpdate();
		if (executeUpdate > 0) {
			return true;
		} else
			return false;
	}

	@Override
	public boolean changePassword(ChangePasswordBean changebean) {
		boolean isCahnaged = false;
		try {
			String qry = " select password from user_master where userid=:userid";
			List list = getSession().createSQLQuery(qry).setParameter("userid", changebean.getUSERID()).list();
			logger.info("Password " + list.get(0).toString());
			String oldpassword = list.get(0).toString();
			// String userInputPass = EncryptDeryptUtility.md5(changebean.getOLDPASSWORD());
			String userInputPass = PasswordSecurityUtil
					.toHexString(PasswordSecurityUtil.getSHA(changebean.getOLDPASSWORD()));

			if (!oldpassword.equals(userInputPass)) {
				isCahnaged = false;
			} else {
				// String encryptpass = EncryptDeryptUtility.md5(changebean.getNEWPASSWORD());
				String encryptpass = PasswordSecurityUtil
						.toHexString(PasswordSecurityUtil.getSHA(changebean.getNEWPASSWORD()));

				String sql = " update user_master set password=:password where userid=:userid";
				int i = getSession().createSQLQuery(sql).setParameter("password", encryptpass)
						.setParameter("userid", changebean.getUSERID()).executeUpdate();
				if (i > 0) {
					isCahnaged = true;
					logger.info("Password changed succesffully");
				} else
					isCahnaged = false;
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return isCahnaged;
	}

	@Override
	public boolean addAccountType(AccountTypesEntity accountTypeData) {
		Session session = sessionFactory.getCurrentSession();
		int userStatus = accountTypeData.getStatusid().intValue();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(accountTypeData.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.CORP_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(accountTypeData.getActivityName());

		try {
			if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				accountTypeData.setStatusid(BigDecimal.valueOf(statusId)); // 97-
																			// ADMIN_CHECKER_PENDIN
			}

			accountTypeData.setCreatedon(new Date());
			session.save(accountTypeData);

			if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				List<AccountTypesEntity> list = getAllAccountTypes();
				ObjectMapper mapper = new ObjectMapper();

				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();

				adminData.setCreatedByUserId(accountTypeData.getUser_ID());
				adminData.setCreatedByRoleId(accountTypeData.getRole_ID());
				adminData.setPageId(accountTypeData.getSubMenu_ID()); // set
																		// submenuId
																		// as
																		// pageid
				adminData.setCreatedBy((accountTypeData.getCreatedby()));
				adminData.setContent(mapper.writeValueAsString(accountTypeData));
				adminData.setActivityId(accountTypeData.getSubMenu_ID()); // set
																			// submenuId
																			// as
																			// activityid
				adminData.setStatusId(BigDecimal.valueOf(statusId)); // 97-
																		// ADMIN_CHECKER_PENDIN
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6)); // 6-
																		// checker
																		// roleid
				adminData.setRemark(accountTypeData.getRemark());
				adminData.setActivityName(accountTypeData.getActivityName());
				adminData.setActivityRefNo(list.get(0).getId());
				adminData.setTableName("ACCOUNT_TYPE_MASTER_PRD");
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);

				// Save data to admin work flow history
				adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(accountTypeData.getSubMenu_ID(), list.get(0).getId(),
						accountTypeData.getCreatedby(), accountTypeData.getRemark(), accountTypeData.getRole_ID(),
						mapper.writeValueAsString(accountTypeData));
			}
			return true;
		} catch (Exception e) {
			logger.info("Exception:", e);
			return false;
		}
	}

	@Override
	public boolean updateAccountType(AccountTypesEntity accountTypeData) {
		Session session = sessionFactory.getCurrentSession();
		int userStatus = accountTypeData.getStatusid().intValue();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(accountTypeData.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.CORP_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(accountTypeData.getActivityName());

		try {
			if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				accountTypeData.setStatusid(BigDecimal.valueOf(statusId)); // 97-
																			// ADMIN_CHECKER_PENDIN
			}
			session.update(accountTypeData);
			if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

				ObjectMapper mapper = new ObjectMapper();

				List<AdminWorkFlowRequestEntity> AdminDataList = adminWorkFlowReqUtility
						.getAdminWorkFlowDataByActivityRefNo(accountTypeData.getId().intValue(),
								accountTypeData.getSubMenu_ID());

				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();

				adminData.setCreatedOn(accountTypeData.getCreatedon());
				adminData.setCreatedByUserId(accountTypeData.getUser_ID());
				adminData.setCreatedByRoleId(accountTypeData.getRole_ID());
				adminData.setPageId(accountTypeData.getSubMenu_ID()); // set
																		// submenuId
																		// as
																		// pageid
				adminData.setCreatedBy((accountTypeData.getCreatedby()));
				adminData.setContent(mapper.writeValueAsString(accountTypeData));
				adminData.setActivityId(accountTypeData.getSubMenu_ID()); // set
																			// submenuId
																			// as
																			// activityid
				adminData.setStatusId(BigDecimal.valueOf(statusId)); // 97-
																		// ADMIN_CHECKER_PENDIN
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6)); // 6-
																		// checker
																		// roleid
				adminData.setRemark(accountTypeData.getRemark());
				adminData.setActivityName(accountTypeData.getActivityName());
				adminData.setActivityRefNo(accountTypeData.getId());
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminData.setTableName("ACCOUNT_TYPE_MASTER_PRD");

				if (ObjectUtils.isEmpty(AdminDataList)) {
					adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);
				} else if (!ObjectUtils.isEmpty(AdminDataList)) {
					adminData.setId(BigDecimal.valueOf(AdminDataList.get(0).getId().intValue()));
					adminWorkFlowReqUtility.updateAdminWorkFlowRequest(adminData);
				}

				// Save data to admin work flow history
				adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(accountTypeData.getSubMenu_ID(),
						accountTypeData.getId(), accountTypeData.getCreatedby(), accountTypeData.getRemark(),
						accountTypeData.getRole_ID(), mapper.writeValueAsString(accountTypeData));

			} else {

				// if record is present in admin work flow then update status
				adminWorkFlowReqUtility.getCheckerDataByctivityRefId(accountTypeData.getId().toBigInteger(),
						BigInteger.valueOf(userStatus), accountTypeData.getSubMenu_ID());
			}
			return true;
		} catch (Exception e) {
			logger.info("Exception:", e);
			return false;
		}
	}

	@Override
	public List<AccountTypesEntity> getAccountTypeById(int id) {
		List<AccountTypesEntity> list = null;
		try {
			String sqlQuery = "select  ac.ID as id , ac.ACCOUNT_TYPE as accountType, ac.ACCOUNT_CODE as accountCode, "
					+ " ac.CREATEDBY as createdby, ac.CREATEDON as createdon, "
					+ "ac.STATUSID as statusid ,s.name as statusname,um.userid as createdByName ,aw.remark,aw.userAction   "
					+ "  from ACCOUNT_TYPE_MASTER_PRD ac "
					+ " left join ADMINWORKFLOWREQUEST aw on aw.activityrefno=:id and aw.tablename='ACCOUNT_TYPE_MASTER' "
					+ " inner join STATUSMASTER s  on s.id=ac.STATUSID  inner join USER_MASTER um on um.id=ac.CREATEDBY  where ac.id=:id ";
			list = getSession().createSQLQuery(sqlQuery).addScalar("id", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("accountType").addScalar("accountCode")
					.addScalar("createdby", StandardBasicTypes.BIG_DECIMAL).addScalar("createdon")
					.addScalar("statusid", StandardBasicTypes.BIG_DECIMAL).addScalar("statusname")
					.addScalar("createdByName").addScalar("remark")
					.addScalar("userAction", StandardBasicTypes.BIG_DECIMAL).setParameter("id", id)
					.setResultTransformer(Transformers.aliasToBean(AccountTypesEntity.class)).list();
		} catch (Exception e) {
			logger.info("Exception:", e);
		}

		return list;
	}

	@Override
	public Boolean addActivitySetting(ActivitySettingMasterEntity activity) {

		Session session = sessionFactory.getCurrentSession();
		try {
			activity.setCreatedOn(new Date());
			session.save(activity);
			return true;
		} catch (Exception e) {
			logger.info("Exception:", e);
			return false;
		}

	}

	@Override
	public List<AccountTypesEntity> getAllAccountTypes() {
		List<AccountTypesEntity> list = null;
		try {
			String sqlQuery = "select  ac.ID as id , ac.ACCOUNT_TYPE as accountType, ac.ACCOUNT_CODE as accountCode, "
					+ " ac.CREATEDBY as createdby, ac.CREATEDON as createdon, "
					+ "ac.STATUSID as statusid ,s.name as statusname,um.userid as createdByName    "
					+ "  from ACCOUNT_TYPE_MASTER_PRD ac inner join STATUSMASTER s "
					+ "on s.id=ac.STATUSID  inner join USER_MASTER um on um.id=ac.CREATEDBY order by ac.CREATEDON desc";
			list = getSession().createSQLQuery(sqlQuery).addScalar("id", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("accountType").addScalar("accountCode")
					.addScalar("createdby", StandardBasicTypes.BIG_DECIMAL).addScalar("createdon")
					.addScalar("statusid", StandardBasicTypes.BIG_DECIMAL).addScalar("statusname")
					.addScalar("createdByName").setResultTransformer(Transformers.aliasToBean(AccountTypesEntity.class))
					.list();
		} catch (Exception e) {
			logger.info("Exception:", e);
		}

		return list;
	}

	@Override
	public Boolean updateActivitySettingDetails(ActivitySettingMasterEntity activity) {
		Session session = sessionFactory.getCurrentSession();
		try {

			session.update(activity);
			return true;
		} catch (Exception e) {
			logger.info("Exception:", e);
			return false;
		}

	}

	@Override
	public List<AdminUserActivityLogs> getAdminUserActivityLogsDetails() {
		List<AdminUserActivityLogs> list = null;
		try {
			String sqlQuery = "select  a.ID , a.USERID , a.authorization , a.appID , a.channels ,  a.channelRequest ,"
					+ " a.eventName , a.category , a.eventTimestamp , a.level, a.operationId , a.operationName , a.properties,  "
					+ " a.IP , a.Lat , a.Lon , a.Browser , a.Device , a.OS , a.CREATEDBY , a.CREATEDON , a.STATUSID , a.CHANNELID ,"

					+ " a.UPDATEDBY , a.UPDATEDON, um.USERID as userName  from admin_user_activity_logs a inner join user_master um on a.USERID = um.id order by a.id desc";

			list = getSession().createSQLQuery(sqlQuery)
					.setResultTransformer(Transformers.aliasToBean(AdminUserActivityLogs.class)).list();
		} catch (Exception e) {
			logger.info("Exception:", e);

		}

		return list;
	}

	@Override
	public List<AdminUserActivityLogs> getAdminPortalUserActivityLogsDetails() {
		List<AdminUserActivityLogs> list = null;
		try {
			String sqlQuery = "select  a.ID , a.channelName, a.channelRequest, a.eventName, a.category, a.action, a.properties, a.IP, a.X_FORWARDEDIP,"
					+ "		a.Lat, a.Lon, a.Browser, a.Device, a.OS, a.CREATEDBY ,  a. CREATEDBYNAME, a.CREATEDON, a.UPDATEDBY, a.UPDATEDON,"
					+ "     (select rts.name from role_types rts where rts.id=(select distinct (rt.roleType) from roles rt where rt.id=(select distinct(role_id) from user_details where user_master_id= a.UPDATEDBY))) as roleName,"
					+ "		a.authorization from adminportal_user_activity_logs a order by a.ID desc";

			list = getSession().createSQLQuery(sqlQuery)
					.setResultTransformer(Transformers.aliasToBean(AdminUserActivityLogs.class)).list();
		} catch (Exception e) {
			logger.info("Exception:", e);

		}
		return list;
	}

	@Override
	public List<AdminUserActivityLogs> getAdminPortalUserActivityLogsDetailsByDate(DateBean datebean) {

		String sqlQuery = "select  a.ID , a.channelName, a.channelRequest as channelrequestClob, a.eventName, a.category, a.action, a.properties as propertiesClob, a.IP, a.X_FORWARDEDIP,"
				+ " a.Lat, a.Lon, a.Browser, a.Device, a.OS, a.CREATEDBY ,  a. CREATEDBYNAME, a.CREATEDON, a.UPDATEDBY, a.UPDATEDON,"
				+ " a.authorization from adminportal_user_activity_logs a  where " + " (a.createdon) BETWEEN TO_DATE('"
				+ datebean.getFromdate() + "','yyyy-mm-dd'" + ") AND TO_DATE('" + datebean.getTodate()
				+ "','yyyy-mm-dd'" + ")+1 order by a.id desc";

		List<AdminUserActivityLogs> list = null;
		try {
			/*
			 * String sqlQuery =
			 * "select  a.ID , a.channelName, a.channelrequest, a.eventName, a.category, a.action, a.PROPERTIES as propertiesClob, a.IP, a.X_FORWARDEDIP,"
			 * +
			 * "		a.Lat, a.Lon, a.Browser, a.Device, a.OS, a.CREATEDBY ,  a. CREATEDBYNAME, a.CREATEDON, a.UPDATEDBY, a.UPDATEDON,"
			 * +
			 * "     (select rts.name from role_types rts where rts.id=(select distinct (rt.roleType) from roles rt where rt.id=(select distinct(role_id) from user_details where user_master_id= a.UPDATEDBY))) as roleName,"
			 * + "		a.authorization from adminportal_user_activity_logs a where " +
			 * " (a.createdon) BETWEEN  TO_DATE('" + datebean.getFromdate() +
			 * "','yyyy-mm-dd'" + ") AND TO_DATE('" + datebean.getTodate() +
			 * "','yyyy-mm-dd'" + ") order by a.id desc";
			 */

			list = getSession().createSQLQuery(sqlQuery).addScalar("channelName").addScalar("channelrequestClob")
					.addScalar("eventName").addScalar("category").addScalar("action").addScalar("propertiesClob")
					.addScalar("IP").addScalar("X_FORWARDEDIP").addScalar("Lat").addScalar("Lon").addScalar("Browser")
					.addScalar("Device").addScalar("OS").addScalar("CREATEDBY").addScalar("CREATEDBYNAME")
					.addScalar("CREATEDON").addScalar("UPDATEDBY").addScalar("UPDATEDON")
					.setResultTransformer(Transformers.aliasToBean(AdminUserActivityLogs.class)).list();

			for (AdminUserActivityLogs cm : list) {
				try {
					String image1 = EncryptDeryptUtility.clobStringConversion(cm.getPropertiesClob());
					cm.setProperties(image1);
					cm.setPropertiesClob(null);

					String image2 = EncryptDeryptUtility.clobStringConversion(cm.getChannelrequestClob());
					cm.setChannelRequest(image2);
					cm.setChannelrequestClob(null);

				} catch (IOException e) {
					logger.error("Exception Occured ", e);
				} catch (SQLException e1) {
					logger.error("Exception Occured ", e1);
				}
			}

		} catch (Exception e) {

			logger.info("Exception:", e);
		}
		return list;

	}

	@Override
	public List<ActivitySettingMasterEntity> getAllActivitySetting() {

		List<ActivitySettingMasterEntity> list = null;
		try {
			String sqlQuery = "select am.id,am.activityId,am.tpinAllowd,am.otpAllowed,am.createdBy,am.createdOn,am.statusId,am.maker,am.approver,"
					+ " am.checker,s.name as statusName,ac.ACTIVITYCODE as activityName , ac.appid as appid , amm.shortname as appName from ACTIVITYSETTINGMASTER am "
					+ "  inner join statusmaster s on s.id=statusId "
					+ " inner join  ACTIVITYMASTER ac on  ac.id = am.activityId inner join appmaster amm on amm.id=ac.appid "
					+ " where ac.FT_NFT !='ADM' order by am.id desc";
			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("activityId").addScalar("tpinAllowd")
					.addScalar("createdBy").addScalar("createdOn").addScalar("statusId").addScalar("maker")
					.addScalar("approver").addScalar("checker").addScalar("statusName").addScalar("activityName")
					.addScalar("appid").addScalar("appName")
					.setResultTransformer(Transformers.aliasToBean(ActivitySettingMasterEntity.class)).list();

		} catch (Exception e) {
			logger.info("Exception:", e);
		}

		return list;
	}

	@Override
	public List<ActivitySettingMasterEntity> getActivitySettingById(int id) {
		List<ActivitySettingMasterEntity> list = null;
		try {
			String sqlQuery = "select am.id,am.activityId,am.tpinAllowd,am.otpAllowed,am.createdBy,am.createdOn,am.statusId,am.maker,am.approver,"
					+ " am.checker,s.name as statusName,ac.ACTIVITYCODE as activityName, ac.appid as appid  from ACTIVITYSETTINGMASTER am "
					+ " inner join statusmaster s on s.id=statusId inner join  ACTIVITYMASTER ac on  ac.id = am.activityId  where am.id=:id";
			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("activityId").addScalar("tpinAllowd")
					.addScalar("otpAllowed").addScalar("createdBy").addScalar("createdOn").addScalar("statusId")
					.addScalar("maker").addScalar("approver").addScalar("checker").addScalar("statusName")
					.addScalar("activityName").addScalar("appid").setParameter("id", id)
					.setResultTransformer(Transformers.aliasToBean(ActivitySettingMasterEntity.class)).list();

		} catch (Exception e) {
			logger.info("Exception:", e);
		}

		return list;
	}

	@Override
	public List<ActivitySettingMasterEntity> getAllActivitySettingByAppId(int appId) {
		List<ActivitySettingMasterEntity> list = null;
		try {
			String sqlQuery = "select am.id,am.activityId,am.tpinAllowd,am.otpAllowed,am.createdBy,am.createdOn,am.statusId,am.maker,am.approver,"
					+ " am.checker,s.name as statusName,ac.ACTIVITYCODE as activityName from ACTIVITYSETTINGMASTER am "
					+ " inner join statusmaster s on s.id=statusId inner join  ACTIVITYMASTER ac on  ac.id = am.activityId  where ac.appId=:appId";
			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("activityId").addScalar("tpinAllowd")
					.addScalar("otpAllowed").addScalar("createdBy").addScalar("createdOn").addScalar("statusId")
					.addScalar("maker").addScalar("approver").addScalar("checker").addScalar("statusName")
					.addScalar("activityName").setParameter("appId", appId)
					.setResultTransformer(Transformers.aliasToBean(ActivitySettingMasterEntity.class)).list();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public List<ActivitySettingMasterEntity> getAllActivitySettingForAdminByAppID(int appId) {
		List<ActivitySettingMasterEntity> list = null;
		try {
			String sqlQuery = "select am.id,am.activityId,am.createdBy,am.createdOn,am.statusId,am.maker,am.approver,"
					+ " am.checker,s.name as statusName,ac.ACTIVITYCODE as activityName from ACTIVITYSETTINGMASTER am "
					+ " inner join statusmaster s on s.id=statusId inner join  ACTIVITYMASTER ac on  ac.id = am.activityId where ac.FT_NFT ='ADM' and ac.appId=:appId order by am.id desc";
			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("activityId").addScalar("createdBy")
					.addScalar("createdOn").addScalar("statusId").addScalar("maker").addScalar("approver")
					.addScalar("checker").addScalar("statusName").addScalar("activityName").setParameter("appId", appId)
					.setResultTransformer(Transformers.aliasToBean(ActivitySettingMasterEntity.class)).list();

		} catch (Exception e) {
			logger.info("Exception:", e);
		}

		return list;
	}

	@Override
	public List<ActivitySettingMasterEntity> getAllActivitySettingForAdmin() {
		List<ActivitySettingMasterEntity> list = null;
		try {
			String sqlQuery = "select am.id,am.activityId,am.createdBy,am.createdOn,am.statusId,am.maker,am.approver,"
					+ " am.checker,s.name as statusName,ac.ACTIVITYCODE as activityName ,ac.appid as appid ,amm.shortname as appName  from ACTIVITYSETTINGMASTER am "
					+ " inner join statusmaster s on s.id=statusId  "
					+ " inner join  ACTIVITYMASTER ac on  ac.id = am.activityId inner join appmaster amm on amm.id=ac.appid"
					+ "   where ac.FT_NFT ='ADM' order by am.id desc";
			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("activityId").addScalar("createdBy")
					.addScalar("createdOn").addScalar("statusId").addScalar("maker").addScalar("approver")
					.addScalar("checker").addScalar("statusName").addScalar("activityName").addScalar("appid")
					.addScalar("appName")
					.setResultTransformer(Transformers.aliasToBean(ActivitySettingMasterEntity.class)).list();

		} catch (Exception e) {
			logger.info("Exception:", e);
		}

		return list;
	}

	@Override
	public List<AdminWorkflowHistoryEntity> getAdminWorkflowHistoryDetails() {
		List<AdminWorkflowHistoryEntity> list = null;
		try {
			String sqlQuery = "select a.id as id,a.createdrole as createdrole, a.createdby as createdby, a.createdon as createdon,a.pageid  as pageid, "
					+ "a.referenceid  as referenceid, a.remarks as remarks, um.USERID as createdByName from "
					+ " adminworkflowhistory a left join user_master um on a.createdby = um.id order by a.id desc ";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("createdrole").addScalar("createdby")
					.addScalar("createdon").addScalar("pageid").addScalar("referenceid").addScalar("remarks")
					.addScalar("createdByName")
					.setResultTransformer(Transformers.aliasToBean(AdminWorkflowHistoryEntity.class)).list();
		} catch (Exception e) {
			logger.info("Exception:", e);
		}

		return list;
	}

	@Override
	public List<AdminWorkflowHistoryEntity> getAdminWorkflowHistoryDetailsById(int refId, int pageId, BigDecimal createdBy) {
		List<AdminWorkflowHistoryEntity> list = null;

		try {
			String sqlQuery = "select a.id as id,a.createdrole as createdrole, a.createdby as createdby, a.createdon as createdon,a.pageid  as pageid, "
					+ "a.referenceid  as referenceid, a.remarks as remarks, um.USERID as createdByName, a.CONTENT as contentClob from "
					+ " adminworkflowhistory a left join user_master um on a.createdby = um.id where a.referenceid=:refId and a.pageid =:pageId and a.createdby=:createdby  order by a.id desc";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("createdrole").addScalar("createdby")
					.addScalar("createdon").addScalar("pageid").addScalar("referenceid").addScalar("remarks")
					.addScalar("createdByName").addScalar("contentClob").setParameter("refId", refId)
					.setParameter("pageId", pageId).setParameter("createdby", createdBy)
					.setResultTransformer(Transformers.aliasToBean(AdminWorkflowHistoryEntity.class)).list();

			for (AdminWorkflowHistoryEntity cm : list) {
				try {
					String image1 = EncryptDeryptUtility.clobStringConversion(cm.getContentClob());
					cm.setContent(image1);
					cm.setContentClob(null);

				} catch (IOException e) {
					logger.error("Exception Occured ", e);
				} catch (SQLException e1) {
					logger.error("Exception Occured ", e1);
				}

			}

		} catch (Exception e) {
			logger.info("Exception:", e);

		}
		return list;

	}

	@Override
	public boolean addOmniLimitMaster(OmniLimitMasterEntity omniData) {
		Session session = sessionFactory.getCurrentSession();
		int userStatus = omniData.getStatusId().intValue();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(omniData.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(omniData.getActivityName());

		try {
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				omniData.setStatusId(BigDecimal.valueOf(statusId)); // 97-
																	// ADMIN_CHECKER_PENDIN
			}
			omniData.setCreatedOn(new Date());
			omniData.setUpdatedOn(new Date());
			session.save(omniData);

			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				List<OmniLimitMasterEntity> list = getAllOmniLimitMaster();
				ObjectMapper mapper = new ObjectMapper();

				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
				adminData.setAppId(omniData.getAppId());
				adminData.setCreatedByUserId(omniData.getUser_ID());
				adminData.setCreatedByRoleId(omniData.getRole_ID());
				adminData.setPageId(omniData.getSubMenu_ID()); // set submenuId
																// as pageid
				adminData.setCreatedBy(omniData.getCreatedBy());
				adminData.setContent(mapper.writeValueAsString(omniData));
				adminData.setActivityId(omniData.getSubMenu_ID()); // set
																	// submenuId
																	// as
																	// activityid
				adminData.setStatusId(BigDecimal.valueOf(statusId)); // 97-
																		// ADMIN_CHECKER_PENDIN
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6)); // 6-
																		// checker
																		// roleid
				adminData.setRemark(omniData.getRemark());
				adminData.setActivityName(omniData.getActivityName());
				adminData.setActivityRefNo(list.get(0).getId());
				adminData.setTableName("OMNIGLOBALLIMITMASTER");
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);

				// Save data to admin work flow history
				adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(omniData.getSubMenu_ID(), list.get(0).getId(),
						omniData.getCreatedBy(), omniData.getRemark(), omniData.getRole_ID(),
						mapper.writeValueAsString(omniData));
			}

			return true;
		} catch (Exception e) {
			logger.info("Exception:", e);
			return false;
		}
	}

	@Override
	public boolean updateOmniLimitMaster(OmniLimitMasterEntity omniData) {
		Session session = sessionFactory.getCurrentSession();
		int userStatus = omniData.getStatusId().intValue();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(omniData.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(omniData.getActivityName());

		try {
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				omniData.setStatusId(BigDecimal.valueOf(statusId)); // 97-
																	// ADMIN_CHECKER_PENDIN
			}

			omniData.setUpdatedOn(new Date());
			session.update(omniData);

			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

				ObjectMapper mapper = new ObjectMapper();

				List<AdminWorkFlowRequestEntity> AdminDataList = adminWorkFlowReqUtility
						.getAdminWorkFlowDataByActivityRefNo(omniData.getId().intValue(), omniData.getSubMenu_ID());

				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
				adminData.setAppId(omniData.getAppId());
				adminData.setCreatedOn(omniData.getCreatedOn());
				adminData.setCreatedByUserId(omniData.getUser_ID());
				adminData.setCreatedByRoleId(omniData.getRole_ID());
				adminData.setPageId(omniData.getSubMenu_ID()); // set submenuId
																// as pageid
				adminData.setCreatedBy(omniData.getCreatedBy());
				adminData.setContent(mapper.writeValueAsString(omniData));
				adminData.setActivityId(omniData.getSubMenu_ID()); // set
																	// submenuId
																	// as
																	// activityid
				adminData.setStatusId(BigDecimal.valueOf(statusId)); // 97-
																		// ADMIN_CHECKER_PENDIN
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6)); // 6-
																		// checker
																		// roleid
				adminData.setRemark(omniData.getRemark());
				adminData.setActivityName(omniData.getActivityName());
				adminData.setActivityRefNo(omniData.getId());
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminData.setTableName("OMNIGLOBALLIMITMASTER");

				if (ObjectUtils.isEmpty(AdminDataList)) {
					adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);
				} else if (!ObjectUtils.isEmpty(AdminDataList)) {
					adminData.setId(BigDecimal.valueOf(AdminDataList.get(0).getId().intValue()));
					adminWorkFlowReqUtility.updateAdminWorkFlowRequest(adminData);

					// Save data to admin work flow history
					adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(omniData.getSubMenu_ID(), omniData.getId(),
							omniData.getCreatedBy(), omniData.getRemark(), omniData.getRole_ID(),
							mapper.writeValueAsString(omniData));
				}

			} else {

				// if record is present in admin work flow then update status
				adminWorkFlowReqUtility.getCheckerDataByctivityRefId(omniData.getId().toBigInteger(),
						BigInteger.valueOf(userStatus), omniData.getSubMenu_ID());
			}
			return true;
		} catch (Exception e) {
			logger.info("Exception:", e);
			return false;
		}
	}

	@Override
	public List<OmniLimitMasterEntity> getOmniLimitMasterById(int id) {
		List<OmniLimitMasterEntity> list = null;
		try {
			String sqlQuery = "select om.id,om.appId as appId, om.LIMIT_NAME as limitName, om.FREQUENCY as frequency,"
					+ "om.MIN_VALUE as minimumValue, om.MAX_VALUE as maximumValue, om.CREATED_BY as createdBy, om.CREATED_ON as createdOn,"
					+ " om.UPDATED_BY as updatedBy,om.UPDATED_ON as updatedOn, om.STATUSID as statusId ,s.name as statusName,"
					+ " a.shortname as apppName, aw.remark,aw.userAction from OMNIGLOBALLIMITMASTER om inner join statusMaster s on s.id=om.STATUSID"
					+ " left join ADMINWORKFLOWREQUEST aw on aw.activityrefno=:id and aw.tablename='OMNIGLOBALLIMITMASTER'  "
					+ " inner join appmaster a on  a.id=om.appId  where om.id=:id";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("appId").addScalar("limitName")
					.addScalar("frequency").addScalar("minimumValue").addScalar("maximumValue").addScalar("createdBy")
					.addScalar("createdOn").addScalar("updatedBy").addScalar("updatedOn").addScalar("statusId")
					.addScalar("statusName").addScalar("apppName").addScalar("remark").addScalar("userAction")
					.setParameter("id", id).setResultTransformer(Transformers.aliasToBean(OmniLimitMasterEntity.class))
					.list();
		} catch (Exception e) {
			logger.info("Exception:", e);
		}

		return list;
	}

	@Override
	public List<OmniLimitMasterEntity> getAllOmniLimitMaster() {
		List<OmniLimitMasterEntity> list = null;
		try {
			String sqlQuery = "select om.id,om.appId as appId, om.LIMIT_NAME as limitName, om.FREQUENCY as frequency,"
					+ "om.MIN_VALUE as minimumValue, om.MAX_VALUE as maximumValue, om.CREATED_BY as createdBy, om.CREATED_ON as createdOn,"
					+ " om.UPDATED_BY as updatedBy,om.UPDATED_ON as updatedOn, om.STATUSID as statusId ,s.name as statusName,"
					+ " a.shortname as apppName,um.USERID as createdByName from OMNIGLOBALLIMITMASTER om inner join statusMaster s on s.id=om.STATUSID "
					+ " inner join user_master um on om.CREATED_BY = um.id "
					+ " inner join appmaster a on  a.id=om.appID order by om.id desc";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("appId").addScalar("limitName")
					.addScalar("frequency").addScalar("minimumValue").addScalar("maximumValue").addScalar("createdBy")
					.addScalar("createdOn").addScalar("updatedBy").addScalar("updatedOn").addScalar("statusId")
					.addScalar("statusName").addScalar("apppName").addScalar("createdByName")
					.setResultTransformer(Transformers.aliasToBean(OmniLimitMasterEntity.class)).list();
		} catch (Exception e) {
			logger.info("Exception:", e);
		}

		return list;
	}

	@Override
	public ResponseMessageBean isLimitExist(OmniLimitMasterEntity omniData) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		try {
			String sqlChannelNameExit = "SELECT count(*) FROM OMNIGLOBALLIMITMASTER WHERE Lower(LIMIT_NAME) =:limitName AND  APPID=:appId ";
			List isChannelNameExit = getSession().createSQLQuery(sqlChannelNameExit)
					.setParameter("appId", omniData.getAppId())
					.setParameter("limitName", omniData.getLimitName().toLowerCase()).list();

			if (!(isChannelNameExit.get(0).toString().equalsIgnoreCase("0"))) {
				rmb.setResponseCode("500");
				rmb.setResponseMessage("Limit Name Already Exist For Same Channel");
			} else {
				rmb.setResponseCode("200");
				rmb.setResponseMessage("Both not exist");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return rmb;
	}

	@Override
	public ResponseMessageBean isUpdateLimitExist(OmniLimitMasterEntity omniData) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		try {
			String sqlChannelNameExit = "SELECT count(*) FROM OMNIGLOBALLIMITMASTER WHERE Lower(LIMIT_NAME) =:limitName AND  ID !=:id AND  APPID=:appId  ";
			List isChannelNameExit = getSession().createSQLQuery(sqlChannelNameExit)
					.setParameter("appId", omniData.getAppId()).setParameter("id", omniData.getId())
					.setParameter("limitName", omniData.getLimitName().toLowerCase()).list();

			if (!(isChannelNameExit.get(0).toString().equalsIgnoreCase("0"))) {
				rmb.setResponseCode("500");
				rmb.setResponseMessage("Limit Name Already Exist For Same Channel");
			} else {
				rmb.setResponseCode("200");
				rmb.setResponseMessage("Both not exist");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return rmb;
	}

	@Override
	public List<ActivityMasterBean> getActivityMaster() {
		List<ActivityMasterBean> list = null;
		try {
			String sqlQuery = "select distinct activitycode, displayname from activitymaster where statusid=3";

			list = getSession().createSQLQuery(sqlQuery).addScalar("activitycode")
					.addScalar("displayname").setResultTransformer(Transformers.aliasToBean(ActivityMasterBean.class))
					.list();
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return list;
	}

	@Override
	public boolean saveToUserCredentialsSession(String email, String mobile, String userToken, BigDecimal userMasterId,
			String userName) {
		Session session = sessionFactory.getCurrentSession();
		try {
			UserCredentialsSessionEntity obj = new UserCredentialsSessionEntity();
			obj.setEMAIL(email);
			obj.setMOBILE(mobile);
			// obj.setOTP(otp);
			obj.setUSERTOKEN(userToken);
			obj.setSTATUSID(new BigDecimal(3));
			obj.setCREATEDON(new Date());
			obj.setUPDATEDON(new Date());
			obj.setUSERMASTERID(userMasterId);
			obj.setUSERNAME(userName);

			session.save(obj);

		} catch (Exception e) {
			logger.info("Exception:", e);
			return false;
		}

		return true;
	}

	@Override
	public ResponseMessageBean validatePwdRestLink(UserCredentialsSessionEntity user) {
		SimpleDateFormat obj = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
		String strDate = obj.format(new Date());
		long minuteDifference = 0;
		ResponseMessageBean rmb = new ResponseMessageBean();
		List<ConfigMasterEntity> configData = masterConfigDao.getConfigByConfigKey("PASSWORD_RESET_LINK");
		int configLinkHour = Integer.parseInt(configData.get(0).getConfigValue());

		try {
			String decUserToken = EncryptorDecryptor.decryptData(user.getUSERTOKEN());

			minuteDifference = adminEmailUtil.findTimeDifference(decUserToken, strDate);

			List<UserCredentialsSessionEntity> userCredList = getCustCredSessionDetails(user);

			if (ObjectUtils.isEmpty(userCredList)) {
				rmb.setResponseCode("500");
				rmb.setResponseMessage("Oops! This Is An Invalid Password Reset Link.");

			} else {
				if (userCredList.get(0).getSTATUSID().equals("0")) {
					rmb.setResponseCode("500");
					rmb.setResponseMessage("This Link Is Already Used");

				} else {

					if ((minuteDifference / 60) > configLinkHour) {
						rmb.setResponseCode("500");
						rmb.setResponseMessage("Link Is Expired...!!");
					} else {
						rmb.setResponseCode("200");
						rmb.setResponseMessage("Success");
						rmb.setResult(userCredList);
					}
				}
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}

		return rmb;
	}

	public List<UserCredentialsSessionEntity> getCustCredSessionDetails(UserCredentialsSessionEntity user) {
		List<UserCredentialsSessionEntity> list = null;
		try {
			String sqlQuery = "select cs.ID, cs.USERTOKEN , cs.OTP, CS.EMAIl, cs.MOBILE , cs.USERMASTERID, cs.USERNAME ,cs.STATUSID "
					+ "from USER_CREDCHANGE_SESSION cs where cs.USERTOKEN=:usrToken order by cs.ID desc";

			list = getSession().createSQLQuery(sqlQuery).setParameter("usrToken", user.getUSERTOKEN())
					.setResultTransformer(Transformers.aliasToBean(UserCredentialsSessionEntity.class)).list();
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return list;
	}

	@Override
	public boolean generateOTPForForgetPwd(UserCredentialsSessionEntity user) {
		int res = 0;

		try {
			String otp = RandomNumberGenerator.generateActivationCode();

			util.sendSMSNotification(user.getMOBILE(), "Please use otp" + " " + otp + " " + "to change your password");

			String sql = "update USER_CREDCHANGE_SESSION set otp=:otp where  USERTOKEN=:userToken and MOBILE=:mobile";
			res = getSession().createSQLQuery(sql).setParameter("userToken", user.getUSERTOKEN())
					.setParameter("otp", otp).setParameter("mobile", user.getMOBILE()).executeUpdate();

		} catch (Exception e) {
			logger.info("Exception:", e);
		}

		if (res == 1)
			return true;
		else
			return false;
	}

	@Override
	public ResponseMessageBean validateOtpAndChangePwd(UserCredentialsSessionEntity user) {
		ResponseMessageBean resp = new ResponseMessageBean();

		try {
			List<UserCredentialsSessionEntity> userCredList = getCustCredSessionDetails(user);
			if (!ObjectUtils.isEmpty(userCredList)) {
				if (userCredList.get(0).getOTP().equals(user.getOTP())) {
					updateUserPassword(user);
					updateUserTokenStatus(user);
					resp.setResponseCode("200");
					resp.setResponseMessage("Password Updated Sucessfully");
				} else {
					resp.setResponseCode("500");
					resp.setResponseMessage("Invalid Otp...!!");
				}
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}

		return resp;
	}

	public boolean updateUserPassword(UserCredentialsSessionEntity user) {
		int res = 0;
		// String encryptpass = EncryptDeryptUtility.md5(user.getUserNewPassword());

		try {
			String encryptpass = PasswordSecurityUtil
					.toHexString(PasswordSecurityUtil.getSHA(user.getUserNewPassword()));
			System.out.println(encryptpass);
			String sql = "update USER_MASTER set PASSWORD=:newPwd where id=:userMstId";
			res = getSession().createSQLQuery(sql).setParameter("newPwd", encryptpass)
					.setParameter("userMstId", user.getUSERMASTERID().intValue()).executeUpdate();

		} catch (Exception e) {
			logger.info("Exception:", e);
		}

		if (res == 1)
			return true;
		else
			return false;
	}

	public boolean updateUserTokenStatus(UserCredentialsSessionEntity user) {
		int res = 0;
		try {
			String sql = "update USER_CREDCHANGE_SESSION set statusid=0 where USERTOKEN=:userToken ";
			res = getSession().createSQLQuery(sql).setParameter("userToken", user.getUSERTOKEN()).executeUpdate();

		} catch (Exception e) {
			logger.info("Exception:", e);
		}

		if (res == 1)
			return true;
		else
			return false;
	}

	public Map<String, String> cbsFreezeAccount(Map<String, String> map) {
		try {
			/*
			 * APPMASTER appMaster = AppMasterReader.getInstance()
			 * .getValue(map.get(ApplicationConstants.ENTITY_ID).replaceAll("\"", ""));
			 */
			Map<String, String> reqdata = new HashMap<String, String>();
			Map<String, Object> finalRequest = new HashMap<String, Object>();
			reqdata.put(ApplicationConstants.MOBILE_NO, map.get(ApplicationConstants.MOBILE_NO_ORG));
			reqdata.put(ApplicationConstants.CHANNEL, "RMOB"); // need to be change and send the actual channel
			reqdata.put(ApplicationConstants.ACCOUNTNUMBER, map.get(ApplicationConstants.ACCOUNTNUMBER));
			reqdata.put(ApplicationConstants.FREEZEACCTCODE, map.get(ApplicationConstants.FREEZEACCTCODE));
			reqdata.put(ApplicationConstants.REFERENCENUMBER, map.get(ApplicationConstants.REFERENCENUMBER));
			finalRequest.put(ApplicationConstants.SOURCE_IP,
					GlobalPropertyReader.getInstance().getValue(map.get(ApplicationConstants.ENTITY_ID) + "_SOURCEIP"));
			finalRequest.put(ApplicationConstants.CHANNEL, map.get(ApplicationConstants.ENTITY_ID)); // Need to change
			finalRequest.put(ApplicationConstants.CHANNEL_REF, map.get(ApplicationConstants.RRN)); // Need to change

			Map<String, String> responseData = new HashMap<String, String>();
			reqdata.put(ApplicationConstants.PROCESS_CODE, ApplicationConstants.SERVICE_FREEZE_ACCOUNT);
			reqdata.put(ApplicationConstants.SERVICE_NAME, "FreezeAccount");
			finalRequest.put(ApplicationConstants.REQUEST_MAP, reqdata);

			responseData = commonCbsCall.getCBSResposne(ApplicationConstants.SERVICE_FREEZE_ACCOUNT, finalRequest);
			return responseData;

		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Exception: ", e);
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserDetailsBean> getAllBranchUsersList(String userCode, String branchCode) {
		StringBuilder qry = new StringBuilder();

		qry.append(
				"SELECT UM.ID AS USERID1, UM.USERID,UD.FIRST_NAME,UD.LAST_NAME,UD.EMAIL,UD.PHONENUMBER,R.CODE,R.NAME AS ROLE,UM.LOGINTYPE,"
						+ "	R.DESCRIPTION,UM.STATUSID,UM.ID AS USER_ID, UD.ID AS USER_DETAIL_ID, R.ID AS ROLE_ID, R.ROLETYPE,RT.NAME   "
						+ "\"roleTypeName\"" + " " + "," + "	R.STATUSID AS ROLESTATUS, UM.USERID  "
						+ "\"createdByName\"" + " "
						+ " ,UM.BRANCHCODE FROM USER_MASTER UM INNER JOIN USER_DETAILS UD ON UM.ID= UD.USER_MASTER_ID "
						+ "	INNER JOIN ROLES R ON UD.ROLE_ID=R.ID Inner JOIN ROLE_TYPES RT ON R.ROLETYPE = RT.ID  WHERE UM.ISDELETED='N' AND UM.BRANCHCODE=:branchCode ORDER BY UM.ID DESC");

		return getSession().createSQLQuery(qry.toString()).setParameter("branchCode", branchCode)
				.setResultTransformer(Transformers.aliasToBean(UserDetailsBean.class)).list();

	}

	@Override
	public boolean updateUserStatusDetail(UserAddEditRequestBean userAddEditReqBean) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateUserStatusDetail(String userCode, String oldBranchCode, String newBranchCode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<FraudReportingBean> getFraudRepotingDetails(String date1, String date2) {

		StringBuilder qry = new StringBuilder();

		qry.append( "SELECT d.ID as id ,d.CIFNUMBER as cifNumber,d.CUSTID as custId,d.TRANSACTIONID as transactionId,d.TRANSACTIONDATE as transactiondate, " +
					"d.REMARKS as remarks,d.BANKREMARKS as bankRemarks,d.CREATEDBY as createdBy," +
					"d.CREATEDON as createdOn,d.STATUSID as statusId,d.UPDATEDON as updatedOn,d.UPDATEDBY as updatedBy,d.APPID as appId," + 
					"d.USERTYPE as userType,d.FRAUDREASON as fraudReason,d.ACCOUNT_NUMBER as accountNumber,d.BRANCH_CODE as branchCode " +
					"FROM FRAUD_REPORTING d " +
					"WHERE d.CREATEDON >= :date1 and d.CREATEDON <= :date2 ");
		
		   return getSession().createSQLQuery(qry.toString()).setParameter("date1", date1).setParameter("date2", date2)
				.setResultTransformer(Transformers.aliasToBean(FraudReportingBean.class)).list();						
	
	}

	@Override
	public List<UserDetailsBean> getZomnalUserList(String  id1) {
		StringBuilder qry = new StringBuilder();

		qry.append(
				"SELECT UM.ID AS USERID1, UM.USERID,UD.FIRST_NAME,UD.LAST_NAME,UD.EMAIL,UD.PHONENUMBER,R.CODE,R.NAME AS ROLE,UM.LOGINTYPE,UM.REMARK,"
						+ "	R.DESCRIPTION,UM.STATUSID,UM.ID AS USER_ID, UD.ID AS USER_DETAIL_ID, R.ID AS ROLE_ID, R.ROLETYPE,RT.NAME   "
						+ "\"roleTypeName\"" + " " + "," + "	R.STATUSID AS ROLESTATUS, UM.USERID  "
						+ "\"createdByName\"" + " "
						+ " FROM USER_MASTER UM INNER JOIN USER_DETAILS UD ON UM.ID= UD.USER_MASTER_ID "
						+ "	INNER JOIN ROLES R ON UD.ROLE_ID=R.ID "
						+ " Inner JOIN ROLE_TYPES RT ON R.ROLETYPE = RT.ID "
						+ " Inner JOIN CBS_BRANCH_LIST CBL ON UM.BRANCHCODE = CBL.BRANCH_CODE"
						+ " WHERE UM.ISDELETED='N' AND UM.STATUSID <> 36 AND CBL.ZONECODE =:id1 ORDER BY UM.ID DESC");

		return getSession().createSQLQuery(qry.toString()).setParameter("id1", id1)
				.setResultTransformer(Transformers.aliasToBean(UserDetailsBean.class)).list();

	}

	@Override
	public ResponseMessageBean checkUsers(User user) {
		logger.info("AdministrationController->AdminstartionDAO->checkUsers----------Start");
		ResponseMessageBean rmb = new ResponseMessageBean();
		try {
			// String sqlEmailExist = "select count(*) from user_details where
			// email=:email";
			String sqlEmailExist = "select count(*) from user_details INNER JOIN user_master ON user_details.USER_MASTER_ID = user_master.ID where email=:email AND user_master.STATUSID <> 36 and isdeleted='N'";
			String sqlUserExist = "select count(*) from user_master where userid=:userid AND STATUSID <> 36 and isdeleted='N'";

			System.out.println(sqlEmailExist);
			System.out.println(sqlUserExist);

			List lsUserExist = getSession().createSQLQuery(sqlUserExist)
					.setParameter("userid", user.getUserid().toLowerCase()).list();

			List lsEmailExist = getSession().createSQLQuery(sqlEmailExist)
					.setParameter("email", user.getEMAIL()).list();

			if (!(lsUserExist.get(0).toString().equalsIgnoreCase("0"))) {
				rmb.setResponseCode("500");
				rmb.setResponseMessage("User ID Already Exist");
			} else if (!(lsEmailExist.get(0).toString().equalsIgnoreCase("0"))) {
				rmb.setResponseCode("500");
				rmb.setResponseMessage("Email ID Already Exist");
			} else {
				rmb.setResponseCode("200");
				rmb.setResponseMessage("Both not exist");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}
		logger.info("AdministrationController->AdminstartionDAO->checkUsers----------End");
		return rmb;
	}

}
