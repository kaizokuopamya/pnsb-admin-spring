package com.itl.pns.dao.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;

import javax.json.Json;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonObject;
import com.itl.pns.bean.CorpTransLimitBean;
import com.itl.pns.constant.ApplicationConstants;
import com.itl.pns.corp.dao.CorporateDao;
import com.itl.pns.dao.AdminWorkFlowDao;
import com.itl.pns.dao.AdministrationDao;
import com.itl.pns.dao.TicketsDao;
import com.itl.pns.entity.ActivitySettingMasterEntity;
import com.itl.pns.entity.AdminWorkFlowRequestEntity;
import com.itl.pns.entity.BankTokenEntity;
import com.itl.pns.util.AdminWorkFlowReqUtility;
import com.itl.pns.util.EncryptDeryptUtility;

/**
 * @author shubham.lokhande
 *
 */
@Repository
@Transactional
public class AdminWorkFlowDaoImpl implements AdminWorkFlowDao {

	static Logger LOGGER = Logger.getLogger(AdminWorkFlowDaoImpl.class);

	@Autowired
	AdminWorkFlowReqUtility adminWorkFlowReqUtility;

	@Autowired
	AdministrationDao userDao;

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	@Autowired
	CorporateDao corpDao;

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public List<AdminWorkFlowRequestEntity> getAllDataForChecker() {
		List<AdminWorkFlowRequestEntity> list = null;
		int statusId = adminWorkFlowReqUtility.getStatusIdByName("ADMIN CHECKER PENDING");
		int approverStatusId = adminWorkFlowReqUtility.getStatusIdByName("ADMIN APPROVER PENDING");
		try {
			String sqlQuery = "select aw.id,aw.appId as appId,aw.createdByUserId,aw.pendingWithUserId,aw.createdByRoleId,aw.pendingWithRoleId,aw.pageId,"
					+ "aw.activityId,aw.activityName,aw.activityRefNo,u.first_Name as created_By, aw.createdOn, u.first_Name as updated_By, aw.updatedOn,aw.content as contentClob,aw.statusId,aw.userAction,"
					+ "aw.remark,s.name as statusName from adminWorkFlowRequest aw inner join statusmaster s on s.id = aw.statusId  inner join user_details u on (u.user_master_id=aw.CREATEDBY or u.user_master_id=aw.updatedBy) "
					+ "where aw.statusId in('" + statusId + "','" + approverStatusId + "') "
					+ " order by aw.updatedOn desc ";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("appId").addScalar("createdByUserId")
					.addScalar("pendingWithUserId").addScalar("createdByRoleId").addScalar("pendingWithRoleId")
					.addScalar("pageId").addScalar("activityId").addScalar("activityName").addScalar("activityRefNo")
					.addScalar("createdOn").addScalar("updatedOn").addScalar("created_By").addScalar("updated_By")
					.addScalar("contentClob").addScalar("statusId").addScalar("userAction").addScalar("remark")
					.addScalar("statusName")
					.setResultTransformer(Transformers.aliasToBean(AdminWorkFlowRequestEntity.class)).list();

			for (AdminWorkFlowRequestEntity cm : list) {
				try {
					String image1 = EncryptDeryptUtility.clobStringConversion(cm.getContentClob());
					cm.setContent(image1);
					cm.setContentClob(null);

				} catch (IOException e) {
					LOGGER.error("Exception Occured ", e);
				} catch (SQLException e1) {
					LOGGER.error("Exception Occured ", e1);
				}

			}

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public List<AdminWorkFlowRequestEntity> getCheckerDataById(int id) {
		List<AdminWorkFlowRequestEntity> list = null;
		try {
			String sqlQuery = "select aw.id,aw.appId as appId,aw.createdByUserId,aw.pendingWithUserId,aw.createdByRoleId,aw.pendingWithRoleId,aw.pageId,"
					+ "aw.activityId,aw.activityName,aw.activityRefNo,aw.createdOn,aw.updatedOn,aw.createdBy,aw.updatedBy,aw.content as contentClob,aw.statusId,"
					+ "aw.remark,s.name as statusName from adminWorkFlowRequest aw inner join statusmaster s on s.id = aw.statusId "
					+ "where aw.id=:id  and aw.statusId=:statusId";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("appId").addScalar("createdByUserId")
					.addScalar("pendingWithUserId").addScalar("createdByRoleId").addScalar("pendingWithRoleId")
					.addScalar("pageId").addScalar("activityId").addScalar("activityName").addScalar("activityRefNo")
					.addScalar("createdOn").addScalar("updatedOn").addScalar("createdBy").addScalar("updatedBy")
					.addScalar("contentClob").addScalar("statusId").addScalar("remark").addScalar("statusName")
					.setParameter("id", id)
					.setResultTransformer(Transformers.aliasToBean(AdminWorkFlowRequestEntity.class)).list();

			for (AdminWorkFlowRequestEntity cm : list) {
				try {
					String image1 = EncryptDeryptUtility.clobStringConversion(cm.getContentClob());
					cm.setContent(image1);
					cm.setContentClob(null);

				} catch (IOException e) {
					LOGGER.error("Exception Occured ", e);
				} catch (SQLException e1) {
					LOGGER.error("Exception Occured ", e1);
				}
			}
		}

		catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public List<AdminWorkFlowRequestEntity> getAllDataForMaker() {
		List<AdminWorkFlowRequestEntity> list = null;
		int checkerStatusId = adminWorkFlowReqUtility.getStatusIdByName("ADMIN CHECKER PENDING");
		int makerStatusId = adminWorkFlowReqUtility.getStatusIdByName("ADMIN MAKER PENDING");
		int approverStatusId = adminWorkFlowReqUtility.getStatusIdByName("ADMIN APPROVER PENDING");
		try {
			String sqlQuery = "select aw.id,aw.appId as appId,aw.createdByUserId,aw.pendingWithUserId,aw.createdByRoleId,aw.pendingWithRoleId,aw.pageId,"
					+ "aw.activityId,aw.activityName,aw.activityRefNo,aw.createdOn,aw.updatedOn,aw.createdBy,aw.updatedBy,aw.content as contentClob,aw.statusId,aw.tableName,"
					+ "aw.remark,s.name as statusName from adminWorkFlowRequest aw inner join statusmaster s on s.id = aw.statusId  "
					+ "where aw.statusId in('" + checkerStatusId + "','" + makerStatusId + "','" + approverStatusId
					+ "') " + "  order by aw.updatedOn desc ";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("appId").addScalar("createdByUserId")
					.addScalar("pendingWithUserId").addScalar("createdByRoleId").addScalar("pendingWithRoleId")
					.addScalar("pageId").addScalar("activityId").addScalar("activityName").addScalar("activityRefNo")
					.addScalar("createdOn").addScalar("updatedOn").addScalar("createdBy").addScalar("updatedBy")
					.addScalar("contentClob").addScalar("statusId").addScalar("tableName").addScalar("remark")
					.addScalar("statusName")
					.setResultTransformer(Transformers.aliasToBean(AdminWorkFlowRequestEntity.class)).list();

			for (AdminWorkFlowRequestEntity cm : list) {
				try {
					String image1 = EncryptDeryptUtility.clobStringConversion(cm.getContentClob());
					cm.setContent(image1);
					cm.setContentClob(null);

				} catch (IOException e) {
					LOGGER.error("Exception Occured ", e);
				} catch (SQLException e1) {
					LOGGER.error("Exception Occured ", e1);
				}

			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public List<AdminWorkFlowRequestEntity> getMakerDataById(int id) {
		List<AdminWorkFlowRequestEntity> list = null;
		int statusId = adminWorkFlowReqUtility.getStatusIdByName("ADMIN CHECKER PENDING");
		try {
			String sqlQuery = "select aw.id,aw.appId as appId,aw.createdByUserId,aw.pendingWithUserId,aw.createdByRoleId,aw.pendingWithRoleId,aw.pageId,"
					+ "aw.activityId,aw.activityName,aw.activityRefNo,aw.createdOn,aw.updatedOn,aw.createdBy,aw.updatedBy,aw.content as contentClob,aw.statusId,"
					+ "aw.remark,s.name as statusName from adminWorkFlowRequest aw inner join statusmaster s on s.id = aw.statusId "
					+ "where aw.id=:id  and aw.statusId !=:statusId";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("appId").addScalar("createdByUserId")
					.addScalar("pendingWithUserId").addScalar("createdByRoleId").addScalar("pendingWithRoleId")
					.addScalar("pageId").addScalar("activityId").addScalar("activityName").addScalar("activityRefNo")
					.addScalar("createdOn").addScalar("updatedOn").addScalar("createdBy").addScalar("updatedBy")
					.addScalar("contentClob").addScalar("statusId").addScalar("remark").addScalar("statusName")
					.setParameter("id", id).setParameter("statusId", statusId)
					.setResultTransformer(Transformers.aliasToBean(AdminWorkFlowRequestEntity.class)).list();

			for (AdminWorkFlowRequestEntity cm : list) {
				try {
					String image1 = EncryptDeryptUtility.clobStringConversion(cm.getContentClob());
					cm.setContent(image1);
					cm.setContentClob(null);

				} catch (IOException e) {
					LOGGER.error("Exception Occured ", e);
				} catch (SQLException e1) {
					LOGGER.error("Exception Occured ", e1);
				}

			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public Boolean updateStatusByChecker(AdminWorkFlowRequestEntity adminWorkFlowData) {
		int res = 0;
		int makerPendingSts = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_MAKER_PENDING);
		int approverPendingSts = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_APPROVER_PENDING);
		String sql = null;
		boolean isDeleteAction = adminWorkFlowReqUtility.checkIsDeleteAction(adminWorkFlowData);
		String actiName = adminWorkFlowReqUtility.getActivityNameByAdmWrkFlowId(adminWorkFlowData);
		List<BankTokenEntity> tokendata = null;
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(adminWorkFlowData.getActivityName());
		if (ApplicationConstants.YESVALUE.equals(Character.toString(activityMasterData.get(0).getApprover()))) { // if
																													// approved
																													// by
																													// checker
																													// and
																													// approver
																													// present
			if (adminWorkFlowData.getStatusId().intValue() == 7 && !isDeleteAction) { // if approved and not
																						// isDeleteAction then
				adminWorkFlowData.setStatusId(BigDecimal.valueOf(approverPendingSts));
			} else if (adminWorkFlowData.getStatusId().intValue() == 6) {
				adminWorkFlowData.setStatusId(BigDecimal.valueOf(makerPendingSts)); // if rejected then set status as
																					// Admin Maker Pending - 96
			} else if (isDeleteAction && adminWorkFlowData.getStatusId().intValue() == 7) {
				adminWorkFlowData.setStatusId(BigDecimal.valueOf(approverPendingSts));
			}
		} else { // if approved by checker and approver not present

			if (adminWorkFlowData.getStatusId().intValue() == 7 && !isDeleteAction) { // if approved and not
																						// isDeleteAction then
				if (actiName.equalsIgnoreCase("CUSTOMERRESETPASSWOEDEDIT")) {
					adminWorkFlowReqUtility.resetCustPassByChecker(adminWorkFlowData);
					adminWorkFlowData.setStatusId(BigDecimal.valueOf(6));
				}
				if (actiName.equalsIgnoreCase("bankTokenEdit")) {
					tokendata = getBankTokenById(adminWorkFlowData.getActivityRefNo().intValue());
					JSONObject json = new JSONObject(adminWorkFlowData.getContent());
					tokendata.get(0).setAppName(json.get("appName").toString());
					adminWorkFlowReqUtility.generateBankToken(tokendata.get(0));
				} else {
					adminWorkFlowData.setStatusId(adminWorkFlowData.getUserAction());
				}
			} else if (adminWorkFlowData.getStatusId().intValue() == 6) {
				adminWorkFlowData.setStatusId(BigDecimal.valueOf(makerPendingSts)); // if rejected then set status as
																					// Admin Maker Pending - 96
			} else if (isDeleteAction && adminWorkFlowData.getStatusId().intValue() == 7) {
				adminWorkFlowData.setStatusId(BigDecimal.valueOf(10));
			} else if (adminWorkFlowData.getStatusId().intValue() == 36) {
				tokendata = getBankTokenById(adminWorkFlowData.getActivityRefNo().intValue());
				JSONObject json = new JSONObject(adminWorkFlowData.getContent());
				tokendata.get(0).setAppName(json.get("appName").toString());
				adminWorkFlowReqUtility.generateBankToken(tokendata.get(0));

			}
		}

		try {
			String tableName = adminWorkFlowReqUtility.getTableName(adminWorkFlowData);
			sql = "update " + tableName + " set ";
			if (tableName.equals("CORP_MENU") || tableName.equals("ACCOUNT_TYPE_MASTER")) {
				sql = sql.concat(" " + " statusid=:status where id=:id ");
			} else {
				if (tableName.equals("CORP_ACC_USER_TYPE") || tableName.equals("CORP_COMP_MENU_MAPPING")) {
					sql = sql.concat(" " + " statusid=:status where id=:id ");
				} else {
					sql = sql.concat(" " + "statusid=:status where id=:id ");
				}

			}
			if (actiName.equalsIgnoreCase("bankTokenEdit")) {
				String sql1 = "update BANKTOKEN set statusid=:status where id=:id";
				res = getSession().createSQLQuery(sql1).setParameter("status", 30).setParameter("id", tokendata.get(0).getId())
						.executeUpdate();
			}else {
			
			res = getSession().createSQLQuery(sql).setParameter("status", adminWorkFlowData.getStatusId())
					.setParameter("id", adminWorkFlowData.getActivityRefNo()).executeUpdate();
			}
			res = adminWorkFlowReqUtility.updateAdminReqFlowStatus(adminWorkFlowData);

			adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(adminWorkFlowData.getPageId(),
					adminWorkFlowData.getActivityRefNo(), adminWorkFlowData.getCreatedBy(),
					adminWorkFlowData.getRemark(), new BigDecimal(6), adminWorkFlowData.getContent());

			refreshCacheDataByTableName(tableName);

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		if (res > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Boolean updateRequestByMaker(AdminWorkFlowRequestEntity adminWorkFlowData) {
		int res = 0;

		try {
			String tableName = adminWorkFlowReqUtility.getTableName(adminWorkFlowData);
			String sql = "update " + tableName + " set statusid=:status where id=:id";
			res = getSession().createSQLQuery(sql).setParameter("status", 10)
					.setParameter("id", adminWorkFlowData.getActivityRefNo()).executeUpdate();
			res = adminWorkFlowReqUtility.updateAdminReqFlowStatus(adminWorkFlowData);

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		if (res > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Boolean updateStatusListByChecker(List<AdminWorkFlowRequestEntity> adminWorkFlowData) {
		int res = 0;
		String sql = null;
		int makerPendingSts = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_MAKER_PENDING);
		int approverPendingSts = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_APPROVER_PENDING);
		int detleteSts = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.DELETED);
		List<ActivitySettingMasterEntity> activityMasterData = null;
		try {

			for (AdminWorkFlowRequestEntity dataList : adminWorkFlowData) {

				String tableName = adminWorkFlowReqUtility.getTableName(dataList);
				boolean isDeleteAction = adminWorkFlowReqUtility.checkIsDeleteAction(dataList);
				String actiName = adminWorkFlowReqUtility.getActivityNameByAdmWrkFlowId(dataList);
				activityMasterData = adminWorkFlowReqUtility.isMakerCheckerPresentForReq(dataList.getActivityName());
				if (ApplicationConstants.YESVALUE.equals(Character.toString(activityMasterData.get(0).getApprover()))) {
					if (("APPROVED").equals(dataList.getType()) && !isDeleteAction) {
						dataList.setStatusId(BigDecimal.valueOf(approverPendingSts));
					} else if (("APPROVED").equals(dataList.getType()) && isDeleteAction) {
						dataList.setStatusId(BigDecimal.valueOf(detleteSts));
					} else if (("REJECTED").equals(dataList.getType())) {
						dataList.setStatusId(BigDecimal.valueOf(makerPendingSts));
					}
				} else {
					if (("APPROVED").equals(dataList.getType()) && !isDeleteAction) {
						if (actiName.equalsIgnoreCase("CUSTOMERRESETPASSWOEDEDIT")) {
							adminWorkFlowReqUtility.resetCustPassByChecker(dataList);
							dataList.setStatusId(BigDecimal.valueOf(6));
						}
						if (actiName.equalsIgnoreCase("bankTokenEdit")) {
							List<BankTokenEntity> tokendata = getBankTokenById(
									adminWorkFlowData.get(0).getActivityRefNo().intValue());
							JSONObject json = new JSONObject(adminWorkFlowData.get(0).getContent());
							tokendata.get(0).setAppName(json.get("appName").toString());
							adminWorkFlowReqUtility.generateBankToken(tokendata.get(0));
						} else {
							dataList.setStatusId(dataList.getUserAction());
						}
					} else if (("APPROVED").equals(dataList.getType()) && isDeleteAction) {
						dataList.setStatusId(BigDecimal.valueOf(detleteSts));
					} else if (("REJECTED").equals(dataList.getType())) {
						dataList.setStatusId(BigDecimal.valueOf(makerPendingSts));
					} else if (!("APPROVED").equals(dataList.getType()) && !isDeleteAction) {
						List<BankTokenEntity> tokendata = getBankTokenById(
								adminWorkFlowData.get(0).getActivityRefNo().intValue());
						JSONObject json = new JSONObject(adminWorkFlowData.get(0).getContent());
						tokendata.get(0).setAppName(json.get("appName").toString());
						adminWorkFlowReqUtility.generateBankToken(tokendata.get(0));
					}
				}
				sql = "update " + tableName + " set ";

				if (tableName.equals("CORP_MENU") || tableName.equals("ACCOUNT_TYPE_MASTER")) {
					sql = sql.concat(" " + " statusid=:status where id=:id ");
				} else {
					if (tableName.equals("CORP_ACC_USER_TYPE") || tableName.equals("CORP_COMP_MENU_MAPPING")) {
						sql = sql.concat(" " + " statusid=:status where id=:id ");
					} else {
						sql = sql.concat(" " + "statusid=:status where id=:id ");
					}

				}

				res = getSession().createSQLQuery(sql).setParameter("status", dataList.getStatusId())
						.setParameter("id", dataList.getActivityRefNo()).executeUpdate();

				res = adminWorkFlowReqUtility.updateAdminReqFlowStatus(dataList);

				adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(dataList.getPageId(), dataList.getActivityRefNo(),
						dataList.getCreatedBy(), dataList.getRemark(), new BigDecimal(6), dataList.getContent());

				refreshCacheDataByTableName(tableName);
			}

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		if (res > 0) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public List<AdminWorkFlowRequestEntity> getAllDataForApprover() {
		List<AdminWorkFlowRequestEntity> list = null;
		int statusId = adminWorkFlowReqUtility.getStatusIdByName("ADMIN APPROVER PENDING");
		try {
			String sqlQuery = "select aw.id,aw.appId as appId,aw.createdByUserId,aw.pendingWithUserId,aw.createdByRoleId,aw.pendingWithRoleId,aw.pageId,"
					+ "aw.activityId,aw.activityName,aw.activityRefNo,aw.createdOn,aw.updatedOn,aw.createdBy,aw.updatedBy,aw.content as contentClob,aw.statusId,aw.userAction,"
					+ "aw.remark,s.name as statusName from adminWorkFlowRequest aw inner join statusmaster s on s.id = aw.statusId  where aw.statusId=:statusId "
					+ " order by aw.updatedOn desc ";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("appId").addScalar("createdByUserId")
					.addScalar("pendingWithUserId").addScalar("createdByRoleId").addScalar("pendingWithRoleId")
					.addScalar("pageId").addScalar("activityId").addScalar("activityName").addScalar("activityRefNo")
					.addScalar("createdOn").addScalar("updatedOn").addScalar("createdBy").addScalar("updatedBy")
					.addScalar("contentClob").addScalar("statusId").addScalar("userAction").addScalar("remark")
					.addScalar("statusName").setParameter("statusId", statusId)
					.setResultTransformer(Transformers.aliasToBean(AdminWorkFlowRequestEntity.class)).list();

			for (AdminWorkFlowRequestEntity cm : list) {
				try {
					String image1 = EncryptDeryptUtility.clobStringConversion(cm.getContentClob());
					cm.setContent(image1);
					cm.setContentClob(null);

				} catch (IOException e) {
					LOGGER.error("Exception Occured ", e);
				} catch (SQLException e1) {
					LOGGER.error("Exception Occured ", e1);
				}

			}

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public List<AdminWorkFlowRequestEntity> getApproverDataById(int id) {
		List<AdminWorkFlowRequestEntity> list = null;
		int statusId = adminWorkFlowReqUtility.getStatusIdByName("ADMIN APPROVER PENDING");
		try {
			String sqlQuery = "select aw.id,aw.appId as appId,aw.createdByUserId,aw.pendingWithUserId,aw.createdByRoleId,aw.pendingWithRoleId,aw.pageId,"
					+ "aw.activityId,aw.activityName,aw.activityRefNo,aw.createdOn,aw.updatedOn,aw.createdBy,aw.updatedBy,aw.content as contentClob,aw.statusId,"
					+ "aw.remark,s.name as statusName from adminWorkFlowRequest aw inner join statusmaster s on s.id = aw.statusId "
					+ "where aw.id=:id  and aw.statusId=:statusId";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("appId").addScalar("createdByUserId")
					.addScalar("pendingWithUserId").addScalar("createdByRoleId").addScalar("pendingWithRoleId")
					.addScalar("pageId").addScalar("activityId").addScalar("activityName").addScalar("activityRefNo")
					.addScalar("createdOn").addScalar("updatedOn").addScalar("createdBy").addScalar("updatedBy")
					.addScalar("contentClob").addScalar("statusId").addScalar("remark").addScalar("statusName")
					.setParameter("id", id).setParameter("statusId", statusId)
					.setResultTransformer(Transformers.aliasToBean(AdminWorkFlowRequestEntity.class)).list();
			for (AdminWorkFlowRequestEntity cm : list) {
				try {
					String image1 = EncryptDeryptUtility.clobStringConversion(cm.getContentClob());
					cm.setContent(image1);
					cm.setContentClob(null);

				} catch (IOException e) {
					LOGGER.error("Exception Occured ", e);
				} catch (SQLException e1) {
					LOGGER.error("Exception Occured ", e1);
				}

			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;

	}

	@Override
	public Boolean updateStatusByApprover(AdminWorkFlowRequestEntity adminWorkFlowData) {
		int res = 0;
		int stsByApprover = adminWorkFlowData.getStatusId().intValue();
		int checkerPendingSts = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		String sql = null;
		boolean isDeleteAction = adminWorkFlowReqUtility.checkIsDeleteAction(adminWorkFlowData);
		String actiName = adminWorkFlowReqUtility.getActivityNameByAdmWrkFlowId(adminWorkFlowData);
		if (adminWorkFlowData.getStatusId().intValue() == 7 && !isDeleteAction) { // if approved then set status as
																					// active -3
			if (actiName.equalsIgnoreCase("CUSTOMERRESETPASSWOEDEDIT")
					|| actiName.equalsIgnoreCase("BANKTOKENGENERATION")) {
				if (actiName.equalsIgnoreCase("CUSTOMERRESETPASSWOEDEDIT")) {
					adminWorkFlowReqUtility.resetCustPassByChecker(adminWorkFlowData);
					adminWorkFlowData.setStatusId(BigDecimal.valueOf(6));
				}
				if (actiName.equalsIgnoreCase("BANKTOKENGENERATION")) {
					adminWorkFlowReqUtility.generateCustomerTokenReq(adminWorkFlowData);
					adminWorkFlowData.setStatusId(BigDecimal.valueOf(87));
				}

			} else {
				adminWorkFlowData.setStatusId(adminWorkFlowData.getUserAction());
			}
		} else if (adminWorkFlowData.getStatusId().intValue() == 6) {
			adminWorkFlowData.setStatusId(BigDecimal.valueOf(checkerPendingSts)); // if rejected then set status as
																					// Admin Checker Pending - 97
		} else if (isDeleteAction && adminWorkFlowData.getStatusId().intValue() == 7) {
			adminWorkFlowData.setStatusId(BigDecimal.valueOf(10));
		}

		try {
			String tableName = adminWorkFlowReqUtility.getTableName(adminWorkFlowData);
			sql = "update " + tableName + " set ";
			if (tableName.equals("CORP_MENU") || tableName.equals("ACCOUNT_TYPE_MASTER")) {
				sql = sql.concat(" " + " statusid=:status where id=:id ");
			} else {
				if (tableName.equals("CORP_ACC_USER_TYPE") || tableName.equals("CORP_COMP_MENU_MAPPING")) {
					sql = sql.concat(" " + " statusid=:status where id=:id ");
				} else {
					sql = sql.concat(" " + "statusid=:status where id=:id ");
				}
			}
			res = getSession().createSQLQuery(sql).setParameter("status", adminWorkFlowData.getStatusId())
					.setParameter("id", adminWorkFlowData.getActivityRefNo()).executeUpdate();
			res = adminWorkFlowReqUtility.updateAdminReqFlowStatus(adminWorkFlowData);
			adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(adminWorkFlowData.getPageId(),
					adminWorkFlowData.getActivityRefNo(), adminWorkFlowData.getCreatedBy(),
					adminWorkFlowData.getRemark(), new BigDecimal(6), adminWorkFlowData.getContent());

			refreshCacheDataByTableName(tableName);

			if (stsByApprover == 7) {
				adminWorkFlowReqUtility.updateAdimWorkFlowRequest(adminWorkFlowData);
			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		if (res > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Boolean updateStatusListByApprover(List<AdminWorkFlowRequestEntity> adminWorkFlowData) {
		int res = 0;
		String sql = null;
		int makerPendingSts = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		int detleteSts = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.DELETED);
		try {

			for (AdminWorkFlowRequestEntity dataList : adminWorkFlowData) {
				String tableName = adminWorkFlowReqUtility.getTableName(dataList);
				boolean isDeleteAction = adminWorkFlowReqUtility.checkIsDeleteAction(dataList);
				String actiName = adminWorkFlowReqUtility.getActivityNameByAdmWrkFlowId(dataList);
				if (("APPROVED").equals(dataList.getType()) && !isDeleteAction) {
					if (actiName.equalsIgnoreCase("CUSTOMERRESETPASSWOEDEDIT")) {
						adminWorkFlowReqUtility.resetCustPassByChecker(dataList);
						dataList.setStatusId(BigDecimal.valueOf(6));
					} else {
						dataList.setStatusId(dataList.getUserAction());
					}
				} else if (("APPROVED").equals(dataList.getType()) && isDeleteAction) {
					dataList.setStatusId(BigDecimal.valueOf(detleteSts));
				} else if (("REJECTED").equals(dataList.getType())) {
					dataList.setStatusId(BigDecimal.valueOf(makerPendingSts));
				}
				sql = "update " + tableName + " set ";

				if (tableName.equals("CORP_MENU") || tableName.equals("ACCOUNT_TYPE_MASTER")) {
					sql = sql.concat(" " + " statusid=:status where id=:id ");
				} else {
					if (tableName.equals("CORP_ACC_USER_TYPE") || tableName.equals("CORP_COMP_MENU_MAPPING")) {
						sql = sql.concat(" " + " statusid=:status where id=:id ");
					} else {
						sql = sql.concat(" " + "statusid=:status where id=:id ");
					}
				}

				res = getSession().createSQLQuery(sql).setParameter("status", dataList.getStatusId())
						.setParameter("id", dataList.getActivityRefNo()).executeUpdate();

				res = adminWorkFlowReqUtility.updateAdminReqFlowStatus(dataList);

				adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(dataList.getPageId(), dataList.getActivityRefNo(),
						dataList.getCreatedBy(), dataList.getRemark(), new BigDecimal(6), dataList.getContent());

				refreshCacheDataByTableName(tableName);

				if (("APPROVED").equals(dataList.getType())) {
					adminWorkFlowReqUtility.updateAdimWorkFlowRequest(dataList);
				}

			}

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		if (res > 0) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public List<AdminWorkFlowRequestEntity> getAllDataForCorpChecker() {
		List<AdminWorkFlowRequestEntity> list = null;
		int statusId = adminWorkFlowReqUtility.getStatusIdByName("CORP_CHECKER_PENDING");
		int approverStatusId = adminWorkFlowReqUtility.getStatusIdByName("CORP_APPROVER_PENDING");
		try {
			String sqlQuery = "select aw.id,aw.appId as appId,aw.createdByUserId,aw.pendingWithUserId,aw.createdByRoleId,aw.pendingWithRoleId,aw.pageId,"
					+ "aw.activityId,aw.activityName,aw.activityRefNo,aw.createdOn,aw.updatedOn,aw.createdBy,aw.updatedBy,aw.content,aw.statusId,aw.userAction,"
					+ "aw.remark,s.name as statusName from adminWorkFlowRequest aw inner join statusmaster s on s.id = aw.statusId  "
					+ "where aw.statusId in('" + statusId + "','" + approverStatusId + "') "
					+ " order by aw.updatedOn desc ";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("appId").addScalar("createdByUserId")
					.addScalar("pendingWithUserId").addScalar("createdByRoleId").addScalar("pendingWithRoleId")
					.addScalar("pageId").addScalar("activityId").addScalar("activityName").addScalar("activityRefNo")
					.addScalar("createdOn").addScalar("updatedOn").addScalar("createdBy").addScalar("updatedBy")
					.addScalar("content").addScalar("userAction").addScalar("statusId").addScalar("remark")
					.addScalar("statusName")
					.setResultTransformer(Transformers.aliasToBean(AdminWorkFlowRequestEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public List<AdminWorkFlowRequestEntity> getAllCorpDataForMaker() {
		List<AdminWorkFlowRequestEntity> list = null;
		int checkerStatusId = adminWorkFlowReqUtility.getStatusIdByName("CORP_CHECKER_PENDING");
		int makerStatusId = adminWorkFlowReqUtility.getStatusIdByName("CORP_MAKER_PENDING");
		int approverStatusId = adminWorkFlowReqUtility.getStatusIdByName("CORP_APPROVER_PENDING");
		try {
			String sqlQuery = "select aw.id,aw.appId as appId,aw.createdByUserId,aw.pendingWithUserId,aw.createdByRoleId,aw.pendingWithRoleId,aw.pageId,"
					+ "aw.activityId,aw.activityName,aw.activityRefNo,aw.createdOn,aw.updatedOn,aw.createdBy,aw.updatedBy,aw.content,aw.statusId,aw.tableName,"
					+ "aw.remark,s.name as statusName from adminWorkFlowRequest aw inner join statusmaster s on s.id = aw.statusId  "
					+ "where aw.statusId in('" + checkerStatusId + "','" + makerStatusId + "','" + approverStatusId
					+ "') " + "  order by aw.updatedOn desc ";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("appId").addScalar("createdByUserId")
					.addScalar("pendingWithUserId").addScalar("createdByRoleId").addScalar("pendingWithRoleId")
					.addScalar("pageId").addScalar("activityId").addScalar("activityName").addScalar("activityRefNo")
					.addScalar("createdOn").addScalar("updatedOn").addScalar("createdBy").addScalar("updatedBy")
					.addScalar("content").addScalar("tableName").addScalar("statusId").addScalar("remark")
					.addScalar("statusName")
					.setResultTransformer(Transformers.aliasToBean(AdminWorkFlowRequestEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public List<AdminWorkFlowRequestEntity> getAllCorpDataForApprover() {
		List<AdminWorkFlowRequestEntity> list = null;
		int statusId = adminWorkFlowReqUtility.getStatusIdByName("CORP_APPROVER_PENDING");
		try {
			String sqlQuery = "select aw.id,aw.appId as appId,aw.createdByUserId,aw.pendingWithUserId,aw.createdByRoleId,aw.pendingWithRoleId,aw.pageId,"
					+ "aw.activityId,aw.activityName,aw.activityRefNo,aw.createdOn,aw.updatedOn,aw.createdBy,aw.updatedBy,aw.content,aw.statusId,aw.userAction,"
					+ "aw.remark,s.name as statusName from adminWorkFlowRequest aw inner join statusmaster s on s.id = aw.statusId  where aw.statusId=:statusId "
					+ " order by aw.updatedOn desc ";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("appId").addScalar("createdByUserId")
					.addScalar("pendingWithUserId").addScalar("createdByRoleId").addScalar("pendingWithRoleId")
					.addScalar("pageId").addScalar("activityId").addScalar("activityName").addScalar("activityRefNo")
					.addScalar("createdOn").addScalar("updatedOn").addScalar("createdBy").addScalar("updatedBy")
					.addScalar("content").addScalar("userAction").addScalar("statusId").addScalar("remark")
					.addScalar("statusName").setParameter("statusId", statusId)
					.setResultTransformer(Transformers.aliasToBean(AdminWorkFlowRequestEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public Boolean updateStatusByCorpChecker(AdminWorkFlowRequestEntity adminWorkFlowData) {
		int res = 0;
		int makerPendingSts = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.CORP_MAKER_PENDING);
		int approverPendingSts = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.CORP_APPROVER_PENDING);
		String sql = null;
		boolean isDeleteAction = adminWorkFlowReqUtility.checkIsDeleteAction(adminWorkFlowData);
		String actiName = adminWorkFlowReqUtility.getActivityNameByAdmWrkFlowId(adminWorkFlowData);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(adminWorkFlowData.getActivityName());
		if (ApplicationConstants.YESVALUE.equals(Character.toString(activityMasterData.get(0).getApprover()))) { // if
																													// approved
																													// by
																													// checker
																													// and
																													// approver
																													// present
			if (adminWorkFlowData.getStatusId().intValue() == 7 && !isDeleteAction) { // if approved and not
																						// isDeleteAction then
				adminWorkFlowData.setStatusId(BigDecimal.valueOf(approverPendingSts));
			} else if (adminWorkFlowData.getStatusId().intValue() == 6) {
				adminWorkFlowData.setStatusId(BigDecimal.valueOf(makerPendingSts)); // if rejected then set status as
																					// Admin Maker Pending - 96
			} else if (isDeleteAction && adminWorkFlowData.getStatusId().intValue() == 7) {
				adminWorkFlowData.setStatusId(BigDecimal.valueOf(approverPendingSts));
			}
		} else { // if approved by checker and approver not present

			if (adminWorkFlowData.getStatusId().intValue() == 7 && !isDeleteAction) { // if approved and not
																						// isDeleteAction then
				if (actiName.equalsIgnoreCase("CUSTOMERRESETPASSWOEDEDIT")) {
					adminWorkFlowReqUtility.resetCustPassByChecker(adminWorkFlowData);
					adminWorkFlowData.setStatusId(BigDecimal.valueOf(6));
				} else {
					adminWorkFlowData.setStatusId(adminWorkFlowData.getUserAction());
				}
			} else if (adminWorkFlowData.getStatusId().intValue() == 6) {
				adminWorkFlowData.setStatusId(BigDecimal.valueOf(makerPendingSts)); // if rejected then set status as
																					// Admin Maker Pending - 96
			} else if (isDeleteAction && adminWorkFlowData.getStatusId().intValue() == 7) {
				adminWorkFlowData.setStatusId(BigDecimal.valueOf(10));
			}
		}

		try {

			if (!("corpSetLimitEdit").equals(actiName) && !("corpSetLimitAdd").equals(actiName)) {
				String tableName = adminWorkFlowReqUtility.getTableName(adminWorkFlowData);
				sql = "update " + tableName + " set ";
				if (tableName.equals("CORP_MENU") || tableName.equals("ACCOUNT_TYPE_MASTER")) {
					sql = sql.concat(" " + " statusid=:status where id=:id ");
				} else {
					if (tableName.equals("CORP_ACC_USER_TYPE") || tableName.equals("CORP_COMP_MENU_MAPPING")) {
						sql = sql.concat(" " + " statusid=:status where id=:id ");
					} else {
						sql = sql.concat(" " + "statusid=:status where id=:id ");
					}

				}
				res = getSession().createSQLQuery(sql).setParameter("status", adminWorkFlowData.getStatusId())
						.setParameter("id", adminWorkFlowData.getActivityRefNo()).executeUpdate();

				res = adminWorkFlowReqUtility.updateAdminReqFlowStatus(adminWorkFlowData);
				adminWorkFlowReqUtility.saveToAdminWorkFlowHistory((adminWorkFlowData.getPageId()),
						adminWorkFlowData.getActivityRefNo(), adminWorkFlowData.getCreatedBy(),
						adminWorkFlowData.getRemark(), new BigDecimal(6), adminWorkFlowData.getContent());

			} else if (("corpSetLimitEdit").equals(actiName) || ("corpSetLimitAdd").equals(actiName)) {

				res = adminWorkFlowReqUtility.updateAdminReqFlowStatus(adminWorkFlowData);
				adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(adminWorkFlowData.getPageId(),
						adminWorkFlowData.getActivityRefNo(), adminWorkFlowData.getCreatedBy(),
						adminWorkFlowData.getRemark(), new BigDecimal(6), adminWorkFlowData.getContent());
			}

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		if (res > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Boolean updateRequestByCorpMaker(AdminWorkFlowRequestEntity adminWorkFlowData) {
		int res = 0;

		try {
			String tableName = adminWorkFlowReqUtility.getTableName(adminWorkFlowData);
			String sql = "update " + tableName + " set statusid=:status where id=:id";
			res = getSession().createSQLQuery(sql).setParameter("status", adminWorkFlowData.getStatusId())
					.setParameter("id", adminWorkFlowData.getActivityRefNo()).executeUpdate();
			res = adminWorkFlowReqUtility.updateAdminReqFlowStatus(adminWorkFlowData);

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		if (res > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Boolean updateStatusListByCorpChecker(List<AdminWorkFlowRequestEntity> adminWorkFlowData) {
		int res = 0;
		String sql = null;
		int makerPendingSts = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.CORP_MAKER_PENDING);
		int approverPendingSts = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.CORP_APPROVER_PENDING);
		int detleteSts = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.DELETED);
		List<ActivitySettingMasterEntity> activityMasterData = null;
		try {

			for (AdminWorkFlowRequestEntity dataList : adminWorkFlowData) {

				String tableName = adminWorkFlowReqUtility.getTableName(dataList);
				boolean isDeleteAction = adminWorkFlowReqUtility.checkIsDeleteAction(dataList);
				String actiName = adminWorkFlowReqUtility.getActivityNameByAdmWrkFlowId(dataList);
				activityMasterData = adminWorkFlowReqUtility.isMakerCheckerPresentForReq(dataList.getActivityName());
				if (ApplicationConstants.YESVALUE.equals(Character.toString(activityMasterData.get(0).getApprover()))) {
					if (("APPROVED").equals(dataList.getType()) && !isDeleteAction) {
						dataList.setStatusId(BigDecimal.valueOf(approverPendingSts));
					} else if (("APPROVED").equals(dataList.getType()) && isDeleteAction) {
						dataList.setStatusId(BigDecimal.valueOf(detleteSts));
					} else if (("REJECTED").equals(dataList.getType())) {
						dataList.setStatusId(BigDecimal.valueOf(makerPendingSts));
					}
				} else {
					if (("APPROVED").equals(dataList.getType()) && !isDeleteAction) {
						if (actiName.equalsIgnoreCase("CUSTOMERRESETPASSWOEDEDIT")) {
							adminWorkFlowReqUtility.resetCustPassByChecker(dataList);
							dataList.setStatusId(BigDecimal.valueOf(6));
						} else {
							dataList.setStatusId(dataList.getUserAction());
						}
					} else if (("APPROVED").equals(dataList.getType()) && isDeleteAction) {
						dataList.setStatusId(BigDecimal.valueOf(detleteSts));
					} else if (("REJECTED").equals(dataList.getType())) {
						dataList.setStatusId(BigDecimal.valueOf(makerPendingSts));
					}
				}

				if (!("corpSetLimitEdit").equals(actiName) && !("corpSetLimitAdd").equals(actiName)) {
					sql = "update " + tableName + " set ";

					if (tableName.equals("CORP_MENU") || tableName.equals("ACCOUNT_TYPE_MASTER")) {
						sql = sql.concat(" " + " statusid=:status where id=:id ");
					} else {
						if (tableName.equals("CORP_ACC_USER_TYPE") || tableName.equals("CORP_COMP_MENU_MAPPING")) {
							sql = sql.concat(" " + " statusid=:status where id=:id ");
						} else {
							sql = sql.concat(" " + "statusid=:status where id=:id ");
						}

					}

					res = getSession().createSQLQuery(sql).setParameter("status", dataList.getStatusId())
							.setParameter("id", dataList.getActivityRefNo()).executeUpdate();

					res = adminWorkFlowReqUtility.updateAdminReqFlowStatus(dataList);

					adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(dataList.getPageId(),
							(dataList.getActivityRefNo()), dataList.getCreatedBy(), dataList.getRemark(),
							new BigDecimal(6), dataList.getContent());

				} else if (("corpSetLimitEdit").equals(actiName) || ("corpSetLimitAdd").equals(actiName)) {

					res = adminWorkFlowReqUtility.updateAdminReqFlowStatus(dataList);

					adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(dataList.getPageId(),
							dataList.getActivityRefNo(), dataList.getCreatedBy(), dataList.getRemark(),
							new BigDecimal(6), dataList.getContent());
				}

			}

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		if (res > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Boolean updateStatusByCorpApprover(AdminWorkFlowRequestEntity adminWorkFlowData) {
		int res = 0;
		int stsByApprover = adminWorkFlowData.getStatusId().intValue();
		int checkerPendingSts = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.CORP_CHECKER_PENDING);
		String sql = null;
		boolean isDeleteAction = adminWorkFlowReqUtility.checkIsDeleteAction(adminWorkFlowData);
		String actiName = adminWorkFlowReqUtility.getActivityNameByAdmWrkFlowId(adminWorkFlowData);
		if (adminWorkFlowData.getStatusId().intValue() == 7 && !isDeleteAction) { // if approved then set status as
																					// active -3
			if (actiName.equalsIgnoreCase("CUSTOMERRESETPASSWOEDEDIT")
					|| actiName.equalsIgnoreCase("BANKTOKENGENERATION")) {
				if (actiName.equalsIgnoreCase("CUSTOMERRESETPASSWOEDEDIT")) {
					adminWorkFlowReqUtility.resetCustPassByChecker(adminWorkFlowData);
					adminWorkFlowData.setStatusId(BigDecimal.valueOf(6));
				}
				if (actiName.equalsIgnoreCase("BANKTOKENGENERATION")) {
					adminWorkFlowReqUtility.generateCustomerTokenReq(adminWorkFlowData);
					adminWorkFlowData.setStatusId(BigDecimal.valueOf(87));
				}

			} else {
				adminWorkFlowData.setStatusId(adminWorkFlowData.getUserAction());
			}
		} else if (adminWorkFlowData.getStatusId().intValue() == 6) {
			adminWorkFlowData.setStatusId(BigDecimal.valueOf(checkerPendingSts)); // if rejected then set status as
																					// Admin Checker Pending - 97
		} else if (isDeleteAction && adminWorkFlowData.getStatusId().intValue() == 7) {
			adminWorkFlowData.setStatusId(BigDecimal.valueOf(10));
		}

		try {

			if (!("corpSetLimitEdit").equals(actiName) && !("corpSetLimitAdd").equals(actiName)) {
				String tableName = adminWorkFlowReqUtility.getTableName(adminWorkFlowData);
				sql = "update " + tableName + " set ";
				if (tableName.equals("CORP_MENU") || tableName.equals("ACCOUNT_TYPE_MASTER")) {
					sql = sql.concat(" " + " statusid=:status where id=:id ");
				} else {
					if (tableName.equals("CORP_ACC_USER_TYPE") || tableName.equals("CORP_COMP_MENU_MAPPING")) {
						sql = sql.concat(" " + " statusid=:status where id=:id ");
					} else {
						sql = sql.concat(" " + "statusid=:status where id=:id ");
					}
				}
				res = getSession().createSQLQuery(sql).setParameter("status", adminWorkFlowData.getStatusId())
						.setParameter("id", adminWorkFlowData.getActivityRefNo()).executeUpdate();
				res = adminWorkFlowReqUtility.updateAdminReqFlowStatus(adminWorkFlowData);
				adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(adminWorkFlowData.getPageId(),
						adminWorkFlowData.getActivityRefNo(), adminWorkFlowData.getCreatedBy(),
						adminWorkFlowData.getRemark(), new BigDecimal(6), adminWorkFlowData.getContent());
			} else if (("corpSetLimitEdit").equals(actiName) || ("corpSetLimitAdd").equals(actiName)) {

				res = adminWorkFlowReqUtility.updateAdminReqFlowStatus(adminWorkFlowData);
				adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(adminWorkFlowData.getPageId(),
						adminWorkFlowData.getActivityRefNo(), adminWorkFlowData.getCreatedBy(),
						adminWorkFlowData.getRemark(), new BigDecimal(6), adminWorkFlowData.getContent());

				if (adminWorkFlowData.getStatusId().intValue() == 3) {
					CorpTransLimitBean allTransData = corpDao
							.getTrasLimitDataByAdminWorkFlowId(adminWorkFlowData.getId().longValue(), "");
					System.out.println("*******************");
					System.out.println(allTransData);
					corpDao.setCorpTransactionsLimitByApprover(allTransData);

				}

			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		if (stsByApprover == 7) {
			adminWorkFlowReqUtility.updateAdimWorkFlowRequest(adminWorkFlowData);
		}
		if (res > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Boolean updateStatusListByCorpApprover(List<AdminWorkFlowRequestEntity> adminWorkFlowData) {
		int res = 0;
		String sql = null;
		int makerPendingSts = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.CORP_MAKER_PENDING);
		int detleteSts = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.DELETED);
		try {

			for (AdminWorkFlowRequestEntity dataList : adminWorkFlowData) {
				String tableName = adminWorkFlowReqUtility.getTableName(dataList);
				boolean isDeleteAction = adminWorkFlowReqUtility.checkIsDeleteAction(dataList);
				String actiName = adminWorkFlowReqUtility.getActivityNameByAdmWrkFlowId(dataList);
				if (("APPROVED").equals(dataList.getType()) && !isDeleteAction) {
					if (actiName.equalsIgnoreCase("CUSTOMERRESETPASSWOEDEDIT")) {
						adminWorkFlowReqUtility.resetCustPassByChecker(dataList);
						dataList.setStatusId(BigDecimal.valueOf(6));
					} else {
						dataList.setStatusId(dataList.getUserAction());
					}
				} else if (("APPROVED").equals(dataList.getType()) && isDeleteAction) {
					dataList.setStatusId(BigDecimal.valueOf(detleteSts));
				} else if (("REJECTED").equals(dataList.getType())) {
					dataList.setStatusId(BigDecimal.valueOf(makerPendingSts));
				}

				if (!("corpSetLimitEdit").equals(actiName) && !("corpSetLimitAdd").equals(actiName)) {
					sql = "update " + tableName + " set ";

					if (tableName.equals("CORP_MENU") || tableName.equals("ACCOUNT_TYPE_MASTER")) {
						sql = sql.concat(" " + " statusid=:status where id=:id ");
					} else {
						if (tableName.equals("CORP_ACC_USER_TYPE") || tableName.equals("CORP_COMP_MENU_MAPPING")) {
							sql = sql.concat(" " + " statusid=:status where id=:id ");
						} else {
							sql = sql.concat(" " + "statusid=:status where id=:id ");
						}
					}

					res = getSession().createSQLQuery(sql).setParameter("status", dataList.getStatusId())
							.setParameter("id", dataList.getActivityRefNo()).executeUpdate();

					res = adminWorkFlowReqUtility.updateAdminReqFlowStatus(dataList);
					adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(dataList.getPageId(),
							dataList.getActivityRefNo(), dataList.getCreatedBy(), dataList.getRemark(),
							new BigDecimal(6), dataList.getContent());
				} else if (("corpSetLimitEdit").equals(actiName) || ("corpSetLimitAdd").equals(actiName)) {

					res = adminWorkFlowReqUtility.updateAdminReqFlowStatus(dataList);

					adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(dataList.getPageId(),
							dataList.getActivityRefNo(), dataList.getCreatedBy(), dataList.getRemark(),
							new BigDecimal(6), dataList.getContent());
					if (("APPROVED").equals(dataList.getType())) {
						CorpTransLimitBean allTransData = corpDao
								.getTrasLimitDataByAdminWorkFlowIdFoeUpdate(dataList.getId().longValue(), "");
						System.out.println("*******************");
						System.out.println(allTransData);
					}

				}
				if (("APPROVED").equals(dataList.getType())) {
					adminWorkFlowReqUtility.updateAdimWorkFlowRequest(dataList);
				}
			}

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		if (res > 0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean refreshCacheDataByTableName(String tableName) {
		boolean isDataRefresh = false;
		try {
			if (tableName.equals("CONFIGURATIONMASTER")) {
				isDataRefresh = adminWorkFlowReqUtility.refreshCacheData("ConfigurationMasterReader");
			} else if (tableName.equals("LOCATIONS")) {
				isDataRefresh = adminWorkFlowReqUtility.refreshCacheData("LocationsReader");
			} else if (tableName.equals("ACTIVITYMASTER")) {
				isDataRefresh = adminWorkFlowReqUtility.refreshCacheData("ActivityMasterReader");
			} else if (tableName.equals("CITIESMASTER")) {
				isDataRefresh = adminWorkFlowReqUtility.refreshCacheData("CitiesMasterReader");
			} else if (tableName.equals("STATEMASTER")) {
				isDataRefresh = adminWorkFlowReqUtility.refreshCacheData("StateMasterReader");
			} else if (tableName.equals("MESSAGECODEMASTER")) {
				isDataRefresh = adminWorkFlowReqUtility.refreshCacheData("MessageCodeMasterReader");
			} else if (tableName.equals("COUNTRYMASTER")) {
				isDataRefresh = adminWorkFlowReqUtility.refreshCacheData("CountryMasterReader");
			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return isDataRefresh;
		}

		return isDataRefresh;
	}

	public List<BankTokenEntity> getBankTokenById(int id) {
		List<BankTokenEntity> list = null;
		int pendingStts = adminWorkFlowReqUtility.getStatusIdByName("PENDING");
		try {

			String sqlquery = "select bk.id, bk.customerId as customerId,bk.accountNumber, bk.statusId , bk.referencenumber , bk.appId,bk.reqInitiatedFor,  "
					+ " 'Pending' as statusName, c.CUSTOMERNAME as customername, c.mobile as mobile from BANKTOKEN bk "
					+ " inner join customers c on c.id=bk.customerId where bk.statusid =:pendingStts and bk.id=:id  ";

			list = getSession().createSQLQuery(sqlquery).addScalar("id").addScalar("customerId")
					.addScalar("accountNumber").addScalar("statusId").addScalar("referencenumber").addScalar("appId")
					.addScalar("reqInitiatedFor").addScalar("statusName").addScalar("customername").addScalar("mobile")
					.setParameter("id", id).setParameter("pendingStts", pendingStts)
					.setResultTransformer(Transformers.aliasToBean(BankTokenEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return list;
	}

}
