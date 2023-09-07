package com.itl.pns.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.itl.pns.bean.CountryStateCityBean;
import com.itl.pns.bean.ProductBean;
import com.itl.pns.bean.RegistrationDetailsBean;
import com.itl.pns.corp.entity.CorpUserEntity;
import com.itl.pns.corp.entity.CorpUserTypeEntity;
import com.itl.pns.entity.ActivitySettingMasterEntity;
import com.itl.pns.entity.AdminUserSessionsEntity;
import com.itl.pns.entity.AdminWorkFlowRequestEntity;
import com.itl.pns.entity.AdminWorkflowHistoryEntity;
import com.itl.pns.entity.BankTokenEntity;
import com.itl.pns.entity.ConfigMasterEntity;
import com.itl.pns.entity.CustomerEntity;
import com.itl.pns.entity.CustomerTokenEntity;
import com.itl.pns.entity.User;
import com.itl.pns.repository.CustomerRepository;
import com.itl.pns.service.impl.RestServiceCall;

/**
 * @author shubham.lokhande
 *
 */
@Repository
@Transactional
public class AdminWorkFlowReqUtility {

	static Logger LOGGER = Logger.getLogger(AdminWorkFlowReqUtility.class);
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Autowired
	EmailUtil emailUtil;

	@Autowired
	RestServiceCall rest;

	public boolean addAdminWorkFlowRequest(AdminWorkFlowRequestEntity adminData) {
		Session session = sessionFactory.getCurrentSession();
		try {

			adminData.setCreatedOn(new Date());
			adminData.setUpdatedOn(new Date());
			session.save(adminData);
			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}

	}

	public boolean updateAdminWorkFlowRequest(AdminWorkFlowRequestEntity adminData) {
		Session session = sessionFactory.getCurrentSession();

		try {
			adminData.setUpdatedOn(new Date());
			session.update(adminData);
			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}

	}

	public String getRoleNameByRoleId(int roleTypeId) {
		String roleName = null;
		StringBuilder sqlsb = new StringBuilder();
		try {
			// sqlsb.append("select rt.shortname from role_types rt inner join roles r on
			// rt.id=r.roletype where r.id=:id");
			sqlsb.append(
					"select  rt.name as rolename from roles r inner join role_types rt on rt.id=r.roletype where  r.roletype=:roleTypeId");
			List list = getSession().createSQLQuery(sqlsb.toString()).setParameter("roleTypeId", roleTypeId).list();
			if (list.size() > 0) {
				roleName = list.get(0).toString();

				System.out.println(roleName);
			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);

		}
		return roleName;
	}

	public String getAccountTypeById(int id) {
		String roleName = null;
		StringBuilder sqlsb = new StringBuilder();
		try {
			sqlsb.append("select ACCOUNT_TYPE from ACCOUNT_TYPE_MASTER_PRD where id=:id");
			List list = getSession().createSQLQuery(sqlsb.toString()).setParameter("id", id).list();
			roleName = list.get(0).toString();
			System.out.println(roleName);
		} catch (Exception e) {
			LOGGER.info("Exception:", e);

		}
		return roleName;
	}

	public String getUserTypeById(int id) {
		String roleName = null;
		StringBuilder sqlsb = new StringBuilder();
		try {
			sqlsb.append("select USER_TYPE from CORP_USER_TYPES where id=:id");
			List list = getSession().createSQLQuery(sqlsb.toString()).setParameter("id", id).list();
			roleName = list.get(0).toString();
			System.out.println(roleName);
		} catch (Exception e) {
			LOGGER.info("Exception:", e);

		}
		return roleName;
	}

	public int getStatusIdByName(String statusName) {
		int statusId = 0;
		try {
			String querry = "select id from statusmaster where name=:statusName";
			List list = getSession().createSQLQuery(querry).setParameter("statusName", statusName).list();
			statusId = Integer.parseInt(list.get(0).toString());
			System.out.println(statusId);
		} catch (Exception e) {
			LOGGER.info("Exception:", e);

		}
		return statusId;
	}

	public String getStatusNameByStatusId(int statusId) {
		String statusName = null;
		try {
			String querry = "select name from statusmaster where id=:statusId";
			List list = getSession().createSQLQuery(querry).setParameter("statusId", statusId).list();
			statusName = (list.get(0).toString());
			System.out.println(statusId);
		} catch (Exception e) {
			LOGGER.info("Exception:", e);

		}
		return statusName;
	}

	public String getAppNameByAppId(int appId) {
		String statusName = null;
		try {
			String querry = "select shortname from APPMASTER where id=:appId";
			List list = getSession().createSQLQuery(querry).setParameter("appId", appId).list();
			statusName = (list.get(0).toString());
			System.out.println(appId);
		} catch (Exception e) {
			LOGGER.info("Exception:", e);

		}
		return statusName;
	}

	public String getcalciNameById(int calciId) {
		String statusName = null;
		try {
			String querry = "select calculatorname from CALCULATORMASTER_PRD where id=:calciId";
			List list = getSession().createSQLQuery(querry).setParameter("calciId", calciId).list();
			statusName = (list.get(0).toString());

		} catch (Exception e) {
			LOGGER.info("Exception:", e);

		}
		return statusName;
	}

	public String getCreatedByNameByCreatedId(int createdById) {
		String createdByName = null;
		try {
			String querry = " select userid from USER_MASTER where id=:credtedById";
			List list = getSession().createSQLQuery(querry).setParameter("credtedById", createdById).list();
			createdByName = (list.get(0).toString());
		} catch (Exception e) {
			LOGGER.info("Exception:", e);

		}
		return createdByName;
	}

	public String getTableName(AdminWorkFlowRequestEntity adminWorkFlowData) {
		String tableName = null;
		try {
			String sql = "select tablename from ADMINWORKFLOWREQUEST where id=:id";
			List list = getSession().createSQLQuery(sql).setParameter("id", adminWorkFlowData.getId()).list();
			tableName = list.get(0).toString();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return tableName;

	}

	public int updateAdminReqFlowStatus(AdminWorkFlowRequestEntity adminWorkFlowData) {
		int res = 0;
		try {
			String sql = "update ADMINWORKFLOWREQUEST set statusid=:status,remark=:remark where id=:id";
			res = getSession().createSQLQuery(sql).setParameter("status", adminWorkFlowData.getStatusId())
					.setParameter("remark", adminWorkFlowData.getRemark()).setParameter("id", adminWorkFlowData.getId())
					.executeUpdate();

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return res;
	}

	public List<AdminWorkFlowRequestEntity> getAdminWorkFlowDataByActivityRefNo(int activityRefNo, BigDecimal pageId) {
		List<AdminWorkFlowRequestEntity> list = null;
		try {
			String sqlQuery = "select aw.id,aw.appId as appId,aw.createdByUserId,aw.pendingWithUserId,aw.createdByRoleId,aw.pendingWithRoleId,aw.pageId,"
					+ "aw.activityId,aw.activityName,aw.activityRefNo,aw.createdOn,aw.updatedOn,aw.createdBy,aw.updatedBy,aw.content as contentClob,aw.statusId,"
					+ "aw.remark from adminWorkFlowRequest aw where aw.activityRefNo =:activityRefNo and aw.pageId=:pageId order by aw.id desc";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("appId").addScalar("createdByUserId")
					.addScalar("pendingWithUserId").addScalar("createdByRoleId").addScalar("pendingWithRoleId")
					.addScalar("pageId").addScalar("activityId").addScalar("activityName").addScalar("activityRefNo")
					.addScalar("createdOn").addScalar("updatedOn").addScalar("createdBy").addScalar("updatedBy")
					.addScalar("contentClob").addScalar("statusId").addScalar("remark")
					.setParameter("activityRefNo", activityRefNo).setParameter("pageId", pageId)
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

	public List<AdminWorkFlowRequestEntity> getAdminWorkFlowDataByAdminWorkFlowId(int admnWorkFlowId,
			BigDecimal pageId) {
		List<AdminWorkFlowRequestEntity> list = null;
		try {
			String sqlQuery = "select aw.id,aw.appId as appId,aw.createdByUserId,aw.pendingWithUserId,aw.createdByRoleId,aw.pendingWithRoleId,aw.pageId,"
					+ "aw.activityId,aw.activityName,aw.activityRefNo,aw.createdOn,aw.updatedOn,aw.createdBy,aw.updatedBy,aw.content as contentClob,aw.statusId,"
					+ "aw.remark from adminWorkFlowRequest aw where aw.id =:admnWorkFlowId order by aw.id desc";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("appId").addScalar("createdByUserId")
					.addScalar("pendingWithUserId").addScalar("createdByRoleId").addScalar("pendingWithRoleId")
					.addScalar("pageId").addScalar("activityId").addScalar("activityName").addScalar("activityRefNo")
					.addScalar("createdOn").addScalar("updatedOn").addScalar("createdBy").addScalar("updatedBy")
					.addScalar("contentClob").addScalar("statusId").addScalar("remark")
					.setParameter("admnWorkFlowId", admnWorkFlowId)
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

	public List<ActivitySettingMasterEntity> isMakerCheckerPresentForReq(String activityName) {
		List<ActivitySettingMasterEntity> list = null;
		try {
			long activityId = getActivityIdByActivityCode(activityName);

			String sqlQuery = "select am.maker as maker,am.checker as checker ,am.approver as approver from ACTIVITYSETTINGMASTER am where am.activityId=:activityId";
			list = getSession().createSQLQuery(sqlQuery).addScalar("maker").addScalar("checker").addScalar("approver")
					.setParameter("activityId", activityId)
					.setResultTransformer(Transformers.aliasToBean(ActivitySettingMasterEntity.class)).list();

			return list;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return list;
	}

	public long getActivityIdByActivityCode(String activityCode) {
		long activityId = 0;
		try {
			String sqlQuery = "select am.id from ACTIVITYMASTER am where am.activityCode=:activityCode";
			List list = getSession().createSQLQuery(sqlQuery).setParameter("activityCode", activityCode.toUpperCase())
					.list();
			activityId = Long.parseLong(list.get(0).toString());

			System.out.println(activityId);

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return activityId;
	}

	public boolean saveToAdminWorkFlowHistory(BigDecimal pageId, BigDecimal referanceId, BigDecimal createdBy,
			String remarks, BigDecimal createdRole, String content) {

		Session session = sessionFactory.getCurrentSession();
		try {

			AdminWorkflowHistoryEntity adminworkFlowHistory = new AdminWorkflowHistoryEntity();
			adminworkFlowHistory.setPageid(pageId);
			adminworkFlowHistory.setReferenceid(referanceId);
			adminworkFlowHistory.setCreatedby(createdBy);
			adminworkFlowHistory.setRemarks(remarks);
			adminworkFlowHistory.setCreatedrole(createdRole);
			adminworkFlowHistory.setCreatedon(new Date());
			adminworkFlowHistory.setContent(content);
			session.save(adminworkFlowHistory);

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}

		return true;
	}

	public boolean saveToAdminWorkFlowHistoryContent(BigDecimal pageId, BigDecimal referanceId, BigDecimal createdBy,
			String remarks, BigDecimal createdRole, String content) {

		Session session = sessionFactory.getCurrentSession();
		try {

			AdminWorkflowHistoryEntity adminworkFlowHistory = new AdminWorkflowHistoryEntity();
			adminworkFlowHistory.setPageid(pageId);
			adminworkFlowHistory.setReferenceid(referanceId);
			adminworkFlowHistory.setCreatedby(createdBy);
			adminworkFlowHistory.setRemarks(remarks);
			adminworkFlowHistory.setCreatedrole(createdRole);
			adminworkFlowHistory.setCreatedon(new Timestamp(System.currentTimeMillis()));
			adminworkFlowHistory.setContent(content);
			session.save(adminworkFlowHistory);

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}

		return true;
	}

	public List<AdminWorkFlowRequestEntity> getCheckerDataByctivityRefId(BigInteger actRefId, BigInteger stsId,
			BigDecimal pageId) {
		List<AdminWorkFlowRequestEntity> list = null;
		int res = 0;
		try {
			String sqlQuery = "select aw.id as id,aw.appId as appId,aw.createdByUserId,aw.pendingWithUserId,aw.createdByRoleId,aw.pendingWithRoleId,aw.pageId,"
					+ "aw.activityId,aw.activityName,aw.activityRefNo,aw.createdOn,aw.updatedOn,aw.createdBy,aw.updatedBy,aw.content as contentClob,aw.statusId,"
					+ "aw.remark,s.name as statusName from adminWorkFlowRequest aw inner join statusmaster s on s.id = aw.statusId "
					+ "where aw.activityRefNo=:actRefId and aw.pageId=:pageId";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("appId").addScalar("createdByUserId")
					.addScalar("pendingWithUserId").addScalar("createdByRoleId").addScalar("pendingWithRoleId")
					.addScalar("pageId").addScalar("activityId").addScalar("activityName").addScalar("activityRefNo")
					.addScalar("createdOn").addScalar("updatedOn").addScalar("createdBy").addScalar("updatedBy")
					.addScalar("contentClob").addScalar("statusId").addScalar("remark")
					.setParameter("actRefId", actRefId).setParameter("pageId", pageId)
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

			if (!ObjectUtils.isEmpty(list)) {
				String querry = "update adminWorkFlowRequest aw set aw.statusId=:stsId where aw.id=:id ";

				res = getSession().createSQLQuery(querry).setParameter("stsId", stsId)
						.setParameter("id", list.get(0).getId()).executeUpdate();

			}

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	public boolean checkIsDeleteAction(AdminWorkFlowRequestEntity adminWorkFlowData) {
		String actName = getActivityNameByAdmWrkFlowId(adminWorkFlowData);

		if (actName != null) {
			if (("ADAPTERSRCIPDELETE").equalsIgnoreCase(actName)
					|| ("ADAPTERSRCCHANNELDELETE").equalsIgnoreCase(actName)
					|| ("RMMASTERDELETE").equalsIgnoreCase(actName)) {
				return true;
			}
		}
		return false;

	}

	public String getActivityNameByAdmWrkFlowId(AdminWorkFlowRequestEntity adminWorkFlowData) {
		String activityName = null;
		try {
			String sqlQuery = "select activityName from  ADMINWORKFLOWREQUEST where id=:id";
			List list = getSession().createSQLQuery(sqlQuery).setParameter("id", adminWorkFlowData.getId()).list();
			activityName = (list.get(0).toString());
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return activityName;

	}

	public int getRoleIdByName(String statusName) {
		int statusId = 0;
		try {
			String querry = "select id from roles where displayname=:statusName";
			List list = getSession().createSQLQuery(querry).setParameter("statusName", statusName).list();
			statusId = Integer.parseInt(list.get(0).toString());
			System.out.println(statusId);
		} catch (Exception e) {
			LOGGER.info("Exception:", e);

		}
		return statusId;
	}

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	EmailUtil email;

	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	public boolean resetCustPassByChecker(AdminWorkFlowRequestEntity adminWorkFlowData) {
		RandomNumberGenerator object = new RandomNumberGenerator();
		String newpassword = object.generateRandomString();
		String encryptpass = EncryptDeryptUtility.md5(newpassword);
		System.out.println(encryptpass);
		try {
			CustomerEntity t = customerRepository.getOne(adminWorkFlowData.getActivityRefNo().intValue());
			t.setUserpassword(encryptpass);
			customerRepository.save(t);
			email.setUpandSendEmail(t.getEmail(), newpassword);
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
		return true;
	}

	public String getChannelNameBychannelId(int channelId) {
		String chaanelName = null;
		try {
			String querry = " select CHANNEL from ADAPTER_SRC_CHANNEL where id=:channelId";
			List list = getSession().createSQLQuery(querry).setParameter("channelId", channelId).list();
			chaanelName = (list.get(0).toString());
		} catch (Exception e) {
			LOGGER.info("Exception:", e);

		}
		return chaanelName;
	}

	public static final int zeroPad(String value, int size) {
		String s = "000000000000000000" + value;
		return Integer.parseInt(s.substring(s.length() - size));
	}

	public static String generateActivationCode() {

		String strOtp = "" + ((int) ((Math.random() * (111111 - 999999)) * (-1)));
		System.out.println(strOtp);
		return "1111";
	}

	public boolean generateCustomerTokenReq(AdminWorkFlowRequestEntity adminWorkFlowData) {

		try {
			List<ConfigMasterEntity> Configlist = getomniChannelConfigDetails(3, "OTP_LENGTH");
			String actCodeNew = String.valueOf(
					zeroPad(generateActivationCode(), Integer.parseInt(Configlist.get(0).getConfigValue().toString())));

			String sqlquery = "Select customerId,appId from CUSTOMERTOKEN where id=:id ";
			List<CustomerTokenEntity> CustDataList = getSession().createSQLQuery(sqlquery)
					.setParameter("id", adminWorkFlowData.getActivityRefNo())
					.setResultTransformer(Transformers.aliasToBean(CustomerTokenEntity.class)).list();

			sendTokenNotificationToCust(CustDataList.get(0).customerId.intValue(),
					CustDataList.get(0).getAppId().intValue(), actCodeNew);

			int res = 0;
			String sql = "update CUSTOMERTOKEN set token=:token where id=:id";
			res = getSession().createSQLQuery(sql).setParameter("id", adminWorkFlowData.getActivityRefNo())
					.setParameter("token", actCodeNew).executeUpdate();

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
		return true;

	}

	public List<ConfigMasterEntity> getomniChannelConfigDetails(int appId, String configType) {

		List<ConfigMasterEntity> list = null;
		int activeSts = getStatusIdByName("ACTIVE");
		try {
			String sqlquery = "select CONFIG_VALUE as configValue from CONFIGURATIONMASTER where appId=:appId and CONFIG_KEY=:configKey and statusId=:activeSts";

			list = getSession().createSQLQuery(sqlquery).addScalar("configValue").setParameter("appId", appId)
					.setParameter("configKey", configType).setParameter("activeSts", activeSts)
					.setResultTransformer(Transformers.aliasToBean(ConfigMasterEntity.class)).list();
			System.out.println(list.get(0).getConfigValue());
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return list;

	}

	public List<RegistrationDetailsBean> getCustDataById(BigInteger customerID) {
		List<RegistrationDetailsBean> list = null;
		String sqlQuery = "";
		try {
			sqlQuery = "select  c.SSA_ACTIVE,c.SSA_ACCOUNT_NUMBER,c.ID,c.CIF,c.ISPASSWORDLOCKED,c.CUSTOMERNAME,c.USERNAME,c.MPIN,c.USERPASSWORD,c.MOBREGSTATUS,c.IBREGSTATUS, "
					+ " c.EMAIL,c.MOBILE,c.ISMPINLOCKED,c.MOBILELASTLOGGEDON,c.WEBLASTLOGGENON,c.ISMPINENABLED,c.APPID,c.PREFEREDLANGUAGE, "
					+ "c.FINGUREUNLOCKENABLED,c.CREATEDON,c.UPDATEDBY,c.UPDATEDON,c.DOB,c.GENDER,c.WRONGATTEMPTSMPIN, "
					+ "c.WRONGATTEMPTSPWD,c.STATUSID,c.FRID,c.TPIN,c.ISTPINLOCKED,c.ISBIOMETRICENABLED,c.ISSMSENABLED,s.name as STATUSNAME,a.shortname as APPNAME ,"
					+ "c.ISEMAILENABLED,c.ISPUSHNOTIFICATIONENABLED,c.ISMOBILEENABLED,c.ISWEBENABLED from customers c inner join appmaster a on a.id= c.appid  "
					+ "inner join statusmaster s on s.id = c.statusid where c.id =:customerID ";

			list = getSession().createSQLQuery(sqlQuery).setParameter("customerID", customerID)
					.setResultTransformer(Transformers.aliasToBean(RegistrationDetailsBean.class)).list();

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return list;
	}

	public List<RegistrationDetailsBean> getCustByMobileNo(String mobNo) {
		List<RegistrationDetailsBean> list = null;
		String sqlQuery = "";
		try {
			sqlQuery = "select  c.SSA_ACTIVE,c.SSA_ACCOUNT_NUMBER,c.ID,c.CIF,c.ISPASSWORDLOCKED,c.CUSTOMERNAME,c.USERNAME,c.MPIN,c.USERPASSWORD,c.MOBREGSTATUS,c.IBREGSTATUS, "
					+ " c.EMAIL,c.MOBILE,c.ISMPINLOCKED,c.MOBILELASTLOGGEDON,c.WEBLASTLOGGENON,c.ISMPINENABLED,c.APPID,c.PREFEREDLANGUAGE, "
					+ "c.FINGUREUNLOCKENABLED,c.CREATEDON,c.UPDATEDBY,c.UPDATEDON,c.DOB,c.GENDER,c.WRONGATTEMPTSMPIN, "
					+ "c.WRONGATTEMPTSPWD,c.STATUSID,c.FRID,c.TPIN,c.ISTPINLOCKED,c.ISBIOMETRICENABLED,c.ISSMSENABLED,s.name as STATUSNAME,a.shortname as APPNAME ,"
					+ "c.ISEMAILENABLED,c.ISPUSHNOTIFICATIONENABLED,c.ISMOBILEENABLED,c.ISWEBENABLED from customers c inner join appmaster a on a.id= c.appid  "
					+ "inner join statusmaster s on s.id = c.statusid where c.MOBILE =:mobNo ";

			list = getSession().createSQLQuery(sqlQuery).setParameter("mobNo", mobNo)
					.setResultTransformer(Transformers.aliasToBean(RegistrationDetailsBean.class)).list();

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return list;
	}

	public boolean sendTokenNotificationToCust(int customerId, int appId, String actCodeNw) {
		String regStatus = null;
		try {
			List<RegistrationDetailsBean> customers = getCustDataById(BigInteger.valueOf(customerId));

			List<ConfigMasterEntity> lstSMS_SEND = getomniChannelConfigDetails(appId, "SMS_SEND");
			List<ConfigMasterEntity> lstEMAIL_SEND = getomniChannelConfigDetails(appId, "EMAIL_SEND");

			if (null != lstSMS_SEND && lstSMS_SEND.get(0).getConfigValue().equalsIgnoreCase("Y")) {
				mwSMSService.sendSMS(customers.get(0).getMOBILE(),
						"KIYA.AI - Please used OTP " + actCodeNw + " to proceed with PSB application");
			}

			if (null != customers.get(0).getEMAIL() && null != lstEMAIL_SEND
					&& lstEMAIL_SEND.get(0).getConfigValue().equalsIgnoreCase("Y")) {
				emailUtil.setUpandSendEmailWithBody(customers.get(0).getEMAIL(), "KIYA.AI OTP",
						"KIYA.AI - Please used OTP " + actCodeNw + " to proceed with PSB application");
			}

			if (customers.get(0).getIBREGSTATUS().intValue() == 88) {
				regStatus = "IBREGSTATUS";
			}
			if (customers.get(0).getMOBREGSTATUS().intValue() == 88) {
				regStatus = "MOBREGSTATUS";
			}
			if (regStatus != null) {

				String sql = "update Customers set " + regStatus + "=:status where  id=:custId";
				getSession().createSQLQuery(sql).setParameter("status", 89)
						.setParameter("custId", customers.get(0).getID().intValue()).executeUpdate();

			}

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
		return true;
	}

	public int updateCustomerIBMobStatus(String regStatus, int custId) {
		int res = 0;

		try {
			String sql = "update Customers set " + regStatus + "=:status where  id=:custId";
			res = getSession().createSQLQuery(sql).setParameter("status", 89).setParameter("custId", custId)
					.executeUpdate();

			return res;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return res;

		}
	}

	public String getApproverMasterName(int id) {
		String approverType = null;
		try {
			String sqlQuery = "  select  ch.APPROVALTYPE as approverType from CORP_APPROVER_MASTER ch"
					+ "	where ch.id =:id";

			List list = getSession().createSQLQuery(sqlQuery).setParameter("id", id).list();
			approverType = (list.get(0).toString());
		} catch (Exception e) {
			LOGGER.info("Exception:", e);

		}
		return approverType;
	}

	public String getHierarchialMasterName(int id) {
		String hierarchy = null;
		try {
			String sqlQuery = " select ch.HIERARCHY as hierarchy from CORP_HIERARCHY_MASTER ch" + "	where ch.id =:id ";
			List list = getSession().createSQLQuery(sqlQuery).setParameter("id", id).list();
			hierarchy = (list.get(0).toString());
		} catch (Exception e) {
			LOGGER.info("Exception:", e);

		}
		return hierarchy;
	}

	public int stringToIntValue(String string) {
		int val = 0;
		if (!ObjectUtils.isEmpty(string)) {
			val = Integer.parseInt(string);
		}
		return val;
	}

	public List<CountryStateCityBean> getCountryStateCityNameById(BigInteger countryId, BigInteger stateId,
			BigInteger cityId) {
		List<CountryStateCityBean> list = null;
		String sqlQuery = "";
		try {
			sqlQuery = "select c.name as countryName, s.shortname as stateName  , t.CITYNAME as cityName"
					+ " from COUNTRYMASTER c, STATEMASTER s ,CITIESMASTER t where c.id=:countryId and s.id=:stateId and t.id=:cityId ";

			list = getSession().createSQLQuery(sqlQuery).addScalar("countryName").addScalar("stateName")
					.addScalar("cityName").setParameter("countryId", countryId).setParameter("stateId", stateId)
					.setParameter("cityId", cityId)
					.setResultTransformer(Transformers.aliasToBean(CountryStateCityBean.class)).list();

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return list;
	}

	public List<CountryStateCityBean> getCountryNameById(BigInteger countryId) {
		List<CountryStateCityBean> list = null;
		String sqlQuery = "";
		try {
			sqlQuery = "select c.name as countryName " + " from COUNTRYMASTER c where c.id=:countryId";

			list = getSession().createSQLQuery(sqlQuery).addScalar("countryName").setParameter("countryId", countryId)
					.setResultTransformer(Transformers.aliasToBean(CountryStateCityBean.class)).list();

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return list;
	}

	public List<CountryStateCityBean> getCountryStateNameById(BigInteger countryId, BigInteger stateId) {
		List<CountryStateCityBean> list = null;
		String sqlQuery = "";
		try {
			sqlQuery = "select c.name as countryName, s.shortname as stateName "
					+ " from COUNTRYMASTER c, STATEMASTER s  where c.id=:countryId and s.id=:stateId ";

			list = getSession().createSQLQuery(sqlQuery).addScalar("countryName").addScalar("stateName")
					.setParameter("countryId", countryId).setParameter("stateId", stateId)
					.setResultTransformer(Transformers.aliasToBean(CountryStateCityBean.class)).list();

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return list;
	}

	public String getSessionIdFromDeviceId(String deviceId) {
		List<AdminUserSessionsEntity> list = null;

		try {
			String sqlQuery = "select SESSIONTOKEN as sessionToken,USERID as userId,STATUSID as statusId from ADMINUSRSESSIONS where DEVICETOKEN=:deviceToken AND STATUSID=3";

			list = getSession().createSQLQuery(sqlQuery).setParameter("deviceToken", deviceId)
					.setResultTransformer(Transformers.aliasToBean(AdminUserSessionsEntity.class)).list();

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		if (!ObjectUtils.isEmpty(list)) {
			return list.get(0).getSessionToken() + "~~~" + list.get(0).getUserId() + "~~~" + list.get(0).getStatusId();
		} else {
			return "NA~~~NA~~~NA";
		}

	}

	public boolean refreshCacheData(String cacheInstanceName) {
		int res = 0;
		String resStatus = "";
		String resp = "";
		String result = "";

		try {

			resp = rest.refreshCache(cacheInstanceName);
			System.out.println(resp);
			if (resp.equalsIgnoreCase("error")) {

				return false;
			}

			JSONObject json1 = new JSONObject(resp);
			JSONObject json2 = json1.getJSONObject("responseParameter");
			resStatus = json2.getString("opstatus");
			result = json2.getString("Result");
			System.out.println(result);

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
		return true;
	}

	public int updateAdimWorkFlowRequest(AdminWorkFlowRequestEntity adminData) {
		int res = 0;

		try {
			String sql = "update adminworkflowrequest set useraction=null where  id=:adminWorkFlowId";
			res = getSession().createSQLQuery(sql).setParameter("adminWorkFlowId", adminData.getId()).executeUpdate();

			return res;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return res;

		}
	}

	public List<ProductBean> getProductCategoryType(BigInteger id) {

		String sqlQuery = "select PRODUCTNAME as productName from  productcategorymaster  WHERE id=:id";

		List<ProductBean> list = getSession().createSQLQuery(sqlQuery).addScalar("productName").setParameter("id", id)
				.setResultTransformer(Transformers.aliasToBean(ProductBean.class)).list();
		return list;
	}

	public CorpUserEntity encryptCorpUserData(CorpUserEntity corpUser) {

		String nonEncUserName = corpUser.getUser_name().toLowerCase();
		String encUserName = EncryptorDecryptor.encryptData(nonEncUserName);
		corpUser.setUser_name(encUserName);

		String nonEncEmail = corpUser.getEmail_id();
		String encEmail = EncryptorDecryptor.encryptData(nonEncEmail);
		corpUser.setEmail_id(encEmail);

		String nonEncMobile = corpUser.getPersonal_Phone();
		String encMobile = EncryptorDecryptor.encryptData(nonEncMobile);
		corpUser.setPersonal_Phone(encMobile);

		String nonEncBirthDate = corpUser.getDob();
		String encBirthDate = EncryptorDecryptor.encryptData(nonEncBirthDate);
		corpUser.setDob(encBirthDate);

		String nonEncPanCardNo = corpUser.getPancardNumber();
		String encPanCardNo = EncryptorDecryptor.encryptData(nonEncPanCardNo);
		corpUser.setPancardNumber(encPanCardNo);

		String nonEncNationaldNo = corpUser.getAadharCardNo();
		String encNationalIdNo = EncryptorDecryptor.encryptData(nonEncNationaldNo);
		corpUser.setAadharCardNo(encNationalIdNo);

		if (!ObjectUtils.isEmpty(corpUser.getPassport())) {
			String nonEncPassportNo = corpUser.getPassport();
			String encPassportNo = EncryptorDecryptor.encryptData(nonEncPassportNo);
			corpUser.setPassport(encPassportNo);
		}

		String displayName = EncryptorDecryptor.encryptData(corpUser.getFirst_name() + " " + corpUser.getLast_name());
		corpUser.setUser_disp_name(displayName);

		return corpUser;

	}

	public List<CorpUserTypeEntity> getIdFromUserTypeName(String userType) {

		String sqlQuery = "select ct.id as  as productName from  CORP_USER_TYPES ct  WHERE USER_TYPE=:userType";

		List<CorpUserTypeEntity> list = getSession().createSQLQuery(sqlQuery).setParameter("userType", userType)
				.setResultTransformer(Transformers.aliasToBean(CorpUserTypeEntity.class)).list();
		return list;
	}

	public String getCorpRoleNameByRoleId(int roleId) {
		String roleName = null;
		if (roleId == 1) {
			roleName = "Regulator";
		} else if (roleId == 2) {
			roleName = "Admin";
		} else if (roleId == 3) {
			roleName = "Maker";
		} else if (roleId == 4) {
			roleName = "Checker";
		} else if (roleId == 5) {
			roleName = "Approver";
		}
		return roleName;
	}

	public List<AdminUserSessionsEntity> getAdminUserSessionByUserId(String userId) {
		List<AdminUserSessionsEntity> list = null;

		try {
			String sqlQuery = "select otp as otp, startTime from ADMINUSRSESSIONS where USERID=:userId AND STATUSID=3";

			list = getSession().createSQLQuery(sqlQuery).addScalar("otp").addScalar("startTime")
					.setParameter("userId", userId)
					.setResultTransformer(Transformers.aliasToBean(AdminUserSessionsEntity.class)).list();

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return list;

	}

	public boolean upadteResetOtp(User user) {
		int res = 0;

		try {
			String sql = "update ADMINUSRSESSIONS set otp=:otp  where USERID=:userId AND STATUSID=3";
			res = getSession().createSQLQuery(sql).setParameter("otp", user.getOtp())
					.setParameter("userId", user.getUserid()).executeUpdate();

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;

		}

		if (res > 0) {
			return true;
		} else {
			return false;
		}

	}

	public int updateWrongAttempts(User user) {
		int res = 0;

		try {
			String sql = "update ADMINUSRSESSIONS set  wrongattemptsotp=:wrongattemptsotp where USERID=:userId and statusid=3";
			res = getSession().createSQLQuery(sql).setParameter("wrongattemptsotp", user.getWrong_attempts())
					.setParameter("userId", user.getUserid()).executeUpdate();

			return res;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return res;

		}
	}

	public int updateResendOtpAttempts(User user) {
		int res = 0;

		try {
			String sql = "update ADMINUSRSESSIONS set RESENDOTPATTEMPT=:resendOtpAttempt where USERID=:userId and statusid=3";
			res = getSession().createSQLQuery(sql).setParameter("resendOtpAttempt", user.getResendOtpAttempt())
					.setParameter("userId", user.getId()).executeUpdate();

			return res;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return res;

		}
	}

	public List<AdminUserSessionsEntity> getWrongAttemptByUserId(String userId) {
		List<AdminUserSessionsEntity> list = null;

		try {
			String sqlQuery = "select wrongattemptsotp as wrongattemptsotp from ADMINUSRSESSIONS where USERID=:userId  and statusid=3";

			list = getSession().createSQLQuery(sqlQuery).addScalar("wrongattemptsotp").setParameter("userId", userId)
					.setResultTransformer(Transformers.aliasToBean(AdminUserSessionsEntity.class)).list();

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return list;

	}

	public List<AdminUserSessionsEntity> getResendOtpAttemptByUserId(String userId) {
		List<AdminUserSessionsEntity> list = null;

		try {
			String sqlQuery = "select max(RESENDOTPATTEMPT) as resendotpattempt from ADMINUSRSESSIONS where USERID=:userId and statusid=3";

			list = getSession().createSQLQuery(sqlQuery).addScalar("resendotpattempt").setParameter("userId", userId)
					.setResultTransformer(Transformers.aliasToBean(AdminUserSessionsEntity.class)).list();

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return list;

	}

	public boolean generateBankToken(BankTokenEntity bankToken) {
		int res = 0;
		String resStatus = "";
		String resp = "";
		String result = "";
		resp = rest.generateTokenService(bankToken.getMobile(), bankToken.getAppName(),
				bankToken.getReferencenumber().toString(), bankToken.getTypeOfRequest());
		System.out.println(resp);
		if (resp.equalsIgnoreCase("error")) {

			return false;
		}

		JSONObject json1 = new JSONObject(resp);
		JSONObject json2 = json1.getJSONObject("responseParameter");
		resStatus = json2.getString("opstatus");
		/*
		 * result=json2.getString("Result"); System.out.println(result);
		 */
		if (resStatus.equalsIgnoreCase("00") || resStatus.equalsIgnoreCase("04") || resStatus.equalsIgnoreCase("01")) {
			String sql = "update BANKTOKEN set statusid=:status where id=:id";
			res = getSession().createSQLQuery(sql).setParameter("status", 30).setParameter("id", bankToken.getId())
					.executeUpdate();
			res = updateCustomerStatus(bankToken);
		} else {

			return false;
		}
		return true;
	}

	public int updateCustomerStatus(BankTokenEntity bankToken) {
		int res = 0;
		String encMobileNo = EncryptorDecryptor.encryptData(bankToken.getMobile());

		try {
			if (bankToken.getReqInitiatedFor().intValue() == 4) {
				String sql = "update Customers set IBREGSTATUS=:status where  mobile=:mobile";
				res = getSession().createSQLQuery(sql).setParameter("status", 30).setParameter("mobile", encMobileNo)
						.executeUpdate();
			} else {
				String sql = "update Customers set MOBREGSTATUS=:status where  mobile=:mobile";
				res = getSession().createSQLQuery(sql).setParameter("status", 30).setParameter("mobile", encMobileNo)
						.executeUpdate();
			}

			return res;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return res;

		}

	}

}
