package com.itl.pns.dao.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.itl.pns.bean.RegistrationDetailsBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.constant.ApplicationConstants;
import com.itl.pns.dao.NotificationDao;
import com.itl.pns.entity.CustomerEntity;
import com.itl.pns.entity.NotificationMaster;
import com.itl.pns.entity.NotificationTemplateMaster;
import com.itl.pns.entity.NotificationsEntity;
import com.itl.pns.entity.UserDeviceDetails;
import com.itl.pns.repository.CustomerRepository;
import com.itl.pns.repository.DeviceMasterRepository;
import com.itl.pns.repository.NotificationEntityRepository;
import com.itl.pns.util.EmailUtil;
import com.itl.pns.util.EncryptorDecryptor;
import com.itl.pns.util.Utils;

@Repository("notificationDao")
@Transactional
public class NotificationDaoImpl implements NotificationDao {

	static Logger LOGGER = Logger.getLogger(NotificationDaoImpl.class);
	@Autowired
	EmailUtil util;

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	@Value("${jdbc.url}")
	private String dbConnection;

	@Value("${jdbc.username}")
	private String dbUserName;

	@Value("${jdbc.password}")
	private String dbPassword;

	@Autowired
	private NotificationEntityRepository notificationRepository;

	@Autowired
	private DeviceMasterRepository deviceMasterRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	EmailUtil emailUtil;

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public List<NotificationMaster> getNotificationMasterList() {
		List<NotificationMaster> list = null;
		try {
			String sqlQuery = "select nm.id,nm.appid as channelId,nm.name as shortName,"
					+ " nm.createdOn as createdOn,nm.createdBy as createdBy ,nm.statusid as statusId from Notificationmaster nm order by nm.id desc";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("statusId").addScalar("shortName")
					.addScalar("createdOn").addScalar("channelId").addScalar("createdBy")
					.setResultTransformer(Transformers.aliasToBean(NotificationMaster.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return list;
	}

	@Override
	public List<NotificationMaster> getNotificationMasterById(int id) {
		List<NotificationMaster> list = null;
		try {
			String sqlQuery = "select nm.id,nm.appid as channelId,nm.name as shortName,"
					+ " nm.createdOn as createdOn,nm.createdBy as createdBy ,nm.statusid as statusId from Notificationmaster nm where nm.id=:id";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("statusId").addScalar("shortName")
					.addScalar("createdOn").addScalar("channelId").addScalar("createdBy").setParameter("id", id)
					.setResultTransformer(Transformers.aliasToBean(NotificationMaster.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return list;
	}

	@Override
	public List<NotificationTemplateMaster> getNotificationTempData() {
		List<NotificationTemplateMaster> list = null;
		try {
			String sqlQuery = "select tm.id, tm.appid as channelId,tm.notificationid as notificationId,tm.shortName as shortName,tm.contents as contents,"
					+ "tm.targetTitle1 as targetTitle1,tm.targetTitle2 as targetTitle2,targetTitle3 as targetTitle3,targetTitle4 as targetTitle4,"
					+ "tm.targetAction1 as targetAction1,targetAction2 as targetAction2,targetAction3 as targetAction3,targetAction4 as targetAction4,"
					+ "tm.LANG_CODE as languagecode,tm.statusId as statusId,tm.createdby as createdby,tm.createdon as createdon,s.name as statusname "
					+ "from notificationTemplateMaster tm inner join statusmaster s on s.id= tm.STATUSID order by tm.id desc";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("channelId")
					.addScalar("notificationId").addScalar("shortName").addScalar("contents").addScalar("targetTitle1")
					.addScalar("targetTitle2").addScalar("targetTitle3").addScalar("targetTitle4")
					.addScalar("targetAction1").addScalar("targetAction2").addScalar("targetAction3")
					.addScalar("targetAction4").addScalar("languagecode").addScalar("statusId").addScalar("createdby")
					.addScalar("createdon").addScalar("statusname")
					.setResultTransformer(Transformers.aliasToBean(NotificationTemplateMaster.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return list;
	}

	@Override
	public boolean deletetNotificationTemp(BigInteger id) {
		boolean success = true;
		try {
			String sqlQuery = "delete from MESSAGES_MASTER where id=:id";

			getSession().createSQLQuery(sqlQuery).setParameter("id", id).executeUpdate();

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}

		return success;
	}

	@Override
	public List<NotificationTemplateMaster> getNotificationTempDataById(BigInteger id) {
		List<NotificationTemplateMaster> list = null;
		try {
			String sqlQuery = "select tm.id, tm.appid as channelId,tm.notificationid as notificationId,tm.shortName as shortName,tm.contents as contents,"
					+ "tm.targetTitle1 as targetTitle1,tm.targetTitle2 as targetTitle2,targetTitle3 as targetTitle3,targetTitle4 as targetTitle4,"
					+ "tm.targetAction1 as targetAction1,targetAction2 as targetAction2,targetAction3 as targetAction3,targetAction4 as targetAction4,"
					+ "tm.LANG_CODE as languagecode,tm.statusId as statusId,tm.createdby as createdby,tm.createdon as createdon,s.name as statusname "
					+ "from notificationTemplateMaster tm inner join statusmaster s on s.id= tm.STATUSID  where tm.id=:id ";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("channelId")
					.addScalar("notificationId").addScalar("shortName").addScalar("contents").addScalar("targetTitle1")
					.addScalar("targetTitle2").addScalar("targetTitle3").addScalar("targetTitle4")
					.addScalar("targetAction1").addScalar("targetAction2").addScalar("targetAction3")
					.addScalar("targetAction4").addScalar("languagecode").addScalar("statusId").addScalar("createdby")
					.addScalar("createdon").addScalar("statusname").setParameter("id", id)
					.setResultTransformer(Transformers.aliasToBean(NotificationTemplateMaster.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return list;
	}

	@Override
	public List<RegistrationDetailsBean> getCustDetails(RegistrationDetailsBean detailsBean) {
		List<RegistrationDetailsBean> list = null;

		try {
			String sqlQuery = "";

			sqlQuery = "select c.SSA_ACTIVE,c.SSA_ACCOUNT_NUMBER,c.ID,c.CIF,c.ISPASSWORDLOCKED,c.CUSTOMERNAME,c.USERNAME,c.MPIN,"
					+ "c.USERPASSWORD,c.EMAIL,c.MOBILE,c.ISMPINLOCKED,c.MOBILELASTLOGGEDON,c.WEBLASTLOGGENON,c.ISMPINENABLED,"
					+ "c.APPID,c.PREFEREDLANGUAGE,c.FINGUREUNLOCKENABLED,c.CREATEDON,c.UPDATEDBY,c.UPDATEDON,c.DOB,c.GENDER,"
					+ "c.WRONGATTEMPTSMPIN,c.WRONGATTEMPTSPWD,c.STATUSID,c.FRID,c.TPIN,c.ISTPINLOCKED,"
					+ "c.ISBIOMETRICENABLED,c.ISSMSENABLED,c.ISEMAILENABLED,c.ISPUSHNOTIFICATIONENABLED,c.ISMOBILEENABLED,c.ISWEBENABLED,"
					+ "s.name as STATUSNAME,a.shortname  as APPNAME from customers c  inner join statusmaster s "
					+ " on s.Id=c.statusid inner join appmaster a on a.id=c.appid " + " where ";

			if (detailsBean.getMOBILE() != null) {
				sqlQuery = sqlQuery.concat(
						" " + "c.mobile Like('%" + detailsBean.getMOBILE() + "%')" + "order by c.CREATEDON desc");
			} else if (detailsBean.getID() != null) {
				sqlQuery = sqlQuery
						.concat(" " + "c.id Like('%" + detailsBean.getID() + "%')" + "order by c.CREATEDON desc");
			} else if (detailsBean.getEMAIL() != null) {
				sqlQuery = sqlQuery.concat(" " + "Lower(c.email) Like('%" + detailsBean.getEMAIL().toLowerCase() + "%')"
						+ "order by c.CREATEDON desc");

			} else if (detailsBean.getCUSTOMERNAME() != null) {
				sqlQuery = sqlQuery.concat(" " + "Lower(c.CUSTOMERNAME) Like('%"
						+ detailsBean.getCUSTOMERNAME().toLowerCase() + "%')" + "order by c.CREATEDON desc");
			} else if (detailsBean.getFromdate() != null && detailsBean.getTodate() != null) {
				sqlQuery = sqlQuery.concat(" " + " c.createdon between TO_DATE('" + detailsBean.getFromdate()
						+ "','yyyy-mm-dd'" + ") AND TO_DATE('" + detailsBean.getTodate() + "','yyyy-mm-dd'"
						+ ")+1 order by c.CREATEDON desc");
			}
			System.out.println(sqlQuery);

			list = getSession().createSQLQuery(sqlQuery)
					.setResultTransformer(Transformers.aliasToBean(RegistrationDetailsBean.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return list;
	}

	@Override
	public boolean saveNotificationTempData(NotificationTemplateMaster notificationTempData) {
		Session session = sessionFactory.getCurrentSession();
		try {
			notificationTempData.setCreatedon(new Date());
			session.save(notificationTempData);
			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}

	}

	@Override
	public boolean updateNotificationTempData(NotificationTemplateMaster notificationTempData) {
		Session session = sessionFactory.getCurrentSession();
		try {
			// notificationTempData.setCreatedon(new Date());
			session.update(notificationTempData);
			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}

	}

	@Override
	public List<NotificationsEntity> getNotificationList(BigDecimal appId, String fromDate, String toDate) {
		List<NotificationsEntity> list = null;
		String sqlQuery = "";
		try {
			if (new BigDecimal(4).equals(appId) || new BigDecimal(5).equals(appId)) {
				sqlQuery = "select n.id, n.customerId as customerId, n.notificationMsg as notificationMsg,n.appId as appId,n.activityId as activityId,"
						+ "n.NOTIFICATION_TYPE as type,n.resendBy as resendBy,n.lastResentOn as lastResentOn,"
						+ "n.statusId as statusId, n.createdOn as createdOn, n.createdBy as createdBy,"
						+ "n.rrn as rrn,c.customerName as customerName,c.cif as customerCif,c.mobile as mobile, c.email, nm.name as typeName "
						+ "from NOTIFICATIONS n inner join CUSTOMERS c on c.id = n.CUST_ID inner join notificationmaster nm on nm.id= n.NOTIFICATION_TYPE "
						+ "where n.appId in(4,5) and n.createdOn between to_date('" + fromDate
						+ "','YYYY-MM-DD') and to_date('" + toDate + "','YYYY-MM-DD')+1 order by n.createdOn desc";
			} else {
				sqlQuery = "select n.id, n.customerId as customerId, n.notificationMsg as notificationMsg,n.appId as appId,n.activityId as activityId,"
						+ "n.NOTIFICATION_TYPE as type,n.resendBy as resendBy,n.lastResentOn as lastResentOn,"
						+ "n.statusId as statusId, n.createdOn as createdOn, n.createdBy as createdBy,"
						+ "n.rrn as rrn,c.first_name as customerName,ccm.cif as customerCif,c.PERSONAL_PHONE as mobile, c.EMAIL_ID as email, nm.name as typeName "
						+ "from NOTIFICATIONS n inner join corp_users c on c.id = n.CUST_ID inner join corp_company_master ccm on ccm.id=c.corp_comp_id inner join notificationmaster nm on nm.id= n.NOTIFICATION_TYPE "
						+ "where n.appId in(2,147) and n.createdOn between to_date('" + fromDate
						+ "','YYYY-MM-DD') and to_date('" + toDate + "','YYYY-MM-DD')+1 order by n.createdOn desc";
			}
//			else {
//				sqlQuery = "select n.id, n.customerId as customerId, n.notificationMsg as notificationMsg,n.appId as appId,n.activityId as activityId,"
//						+ "n.NOTIFICATION_TYPE as type,n.resendBy as resendBy,n.lastResentOn as lastResentOn,"
//						+ "n.statusId as statusId, n.createdOn as createdOn, n.createdBy as createdBy,"
//						+ "n.rrn as rrn,c.first_name as customerName,ccm.cif as customerCif,c.PERSONAL_PHONE as mobile, c.EMAIL_ID as email, nm.name as typeName "
//						+ "from NOTIFICATIONS n inner join corp_users c on c.id = n.CUST_ID inner join corp_company_master ccm on ccm.id=c.corp_comp_id inner join notificationmaster nm on nm.id= n.NOTIFICATION_TYPE "
//						+ "where n.appId=147 and (ccm.is_corporate='S' or ccm.is_corporate is null) and n.createdOn between to_date('"
//						+ fromDate + "','DD-MM-YYYY') and to_date('" + toDate
//						+ "','DD-MM-YYYY')+1 order by n.createdOn desc";
//			}

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("customerId")
					.addScalar("notificationMsg").addScalar("appId").addScalar("activityId").addScalar("type")
					.addScalar("resendBy").addScalar("lastResentOn").addScalar("statusId").addScalar("createdOn")
					.addScalar("createdBy").addScalar("rrn").addScalar("customerName").addScalar("customerCif")
					.addScalar("typeName").addScalar("mobile").addScalar("email")
					.setResultTransformer(Transformers.aliasToBean(NotificationsEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return list;
	}

	@Override
	public boolean sendNotificationTocust(NotificationsEntity notification) {
		boolean isSent = false;
		NotifcationEngineImpl notificationEng = new NotifcationEngineImpl();
		try {
			if ("SMS".equalsIgnoreCase(notification.getTypeName())) {
				emailUtil.sendSMSNotification(notification.getMobile(), notification.getNotificationMsg());
				updateLastResendOn(notification);
				isSent = true;

			} else if ("EMAIL".equalsIgnoreCase(notification.getTypeName())) {
				Map<String, String> notifcaion = new HashMap<String, String>();
				notifcaion.put(ApplicationConstants.CUSTOMER_ID, notification.getEmail());
				notifcaion.put(ApplicationConstants.CONTENT, notification.getNotificationMsg());
				notifcaion.put(ApplicationConstants.APP_NAME, "RMOB");
				notifcaion.put(ApplicationConstants.RRN, String.valueOf(System.currentTimeMillis()));
				notifcaion.put(ApplicationConstants.ACTIVITY_NAME, "");
				notifcaion.put(ApplicationConstants.FILE_PATH, "");
				notifcaion.put("userId", "");
				notifcaion.put("appName", "entityId");
				notifcaion.put("RRN", String.valueOf(System.currentTimeMillis()));
				notifcaion.put("CreateUser", "CreateUser");
				notifcaion.put("filepath", "");
				notifcaion.put("emailContent", notification.getNotificationMsg());
				notifcaion.put("emailId", notification.getEmail());
				notifcaion.put("deviceTokenId", ""); // Need to check
				notifcaion.put("subject", "OTP for login"); // Need to check

				EmailUtil eu = new EmailUtil();
				eu.sendEmail(notifcaion);
				updateLastResendOn(notification);
				isSent = true;

			} else if ("PUSH".equalsIgnoreCase(notification.getTypeName())) {
				List<BigDecimal> appIdList = new ArrayList<>();
				appIdList.add(notification.getAppId());
				UserDeviceDetails userDeviceData = deviceMasterRepository.findByCustomerIdAndStatusIdAndAppId(
						notification.getCustId().intValue(), ApplicationConstants.ACTIVE_STATUS, appIdList);
				if (null != userDeviceData && null != userDeviceData.getPushNotificationToken()
						&& userDeviceData.getPushNotificationToken().trim().length() > 20) {
					Map<String, String> notifcaion = new HashMap<String, String>();

					notifcaion.put(ApplicationConstants.APP_NAME, "RIB");
					notifcaion.put(ApplicationConstants.FILE_PATH, "");
					notifcaion.put(ApplicationConstants.CUSTOMER_ID, notification.getMobile());
					notifcaion.put(ApplicationConstants.ACTIVITY_NAME, "");
					notifcaion.put(ApplicationConstants.NOTIFICATION_TYPE, ApplicationConstants.NOTIFICATION_TYPE_PUSH);
					notifcaion.put(ApplicationConstants.CONTENT, notification.getNotificationMsg());
					notifcaion.put(ApplicationConstants.RRN, new Long(Utils.randomNumber()).toString());
					notifcaion.put(ApplicationConstants.REQUEST_KEY_DEVICEID, ""); // Need
					notifcaion.put(ApplicationConstants.PUSH_TOKEN, userDeviceData.getPushNotificationToken()); // Need
//					notifcaion.put(ApplicationConstants.PUSH_TOKEN, userDeviceData.getPushNotificationToken()); // Need
					// Need
					// to
					notificationEng.sendPushNotification(notifcaion);
					updateLastResendOn(notification);

				}

			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
		return isSent;

	}

	@Override
	public boolean addNotificationDetails(List<NotificationsEntity> notification) {
		Session session = sessionFactory.getCurrentSession();
		try {
			for (NotificationsEntity notificationList : notification) {
				notificationList.setCreatedOn(new Date());
				session.save(notificationList);

			}

			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}

	@Override
	public boolean updateNotificationDetails(NotificationsEntity notificationData) {
		Session session = sessionFactory.getCurrentSession();
		try {
			DateFormat df = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
			Date dateobj = new Date();
			System.out.println(df.format(dateobj));
			notificationData.setLastResentOn(new Date());
			session.update(notificationData);
			return true;

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}

	@Override
	@SuppressWarnings("rawtypes")
	public ResponseMessageBean chechNotificatioExit(NotificationTemplateMaster notificationTempData) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		String sqlNameExist = "select count(*) from NOTIFICATIONTEMPLATEMASTER  WHERE Lower(SHORTNAME) =:notiMsg  AND  appid=:channelId ";

		List notiNameExist = getSession().createSQLQuery(sqlNameExist)
				.setParameter("channelId", notificationTempData.getAppId())
				.setParameter("notiMsg", notificationTempData.getShortName().toLowerCase()).list();

		if (!(notiNameExist.get(0).toString().equalsIgnoreCase("0"))) {
			rmb.setResponseCode("500");
			rmb.setResponseMessage("Name Already Exist For Same Channel");
		} else {
			rmb.setResponseCode("200");
			rmb.setResponseMessage("Both Not Exist");
		}
		return rmb;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public ResponseMessageBean updateNotificatioExit(NotificationTemplateMaster notificationTempData) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		/*
		 * String sqlNameExist =
		 * "select count(*) from NOTIFICATIONTEMPLATEMASTER  WHERE Lower(SHORTNAME) =:notiMsg  AND  CHANNELID=:channelId  AND "
		 * ; String
		 * sqlNotiIdExist="select count(*) from NOTIFICATIONTEMPLATEMASTER where notiId=:notiId"
		 * ;
		 * 
		 * List notiNameExist =
		 * getSession().createSQLQuery(sqlNameExist).setParameter("channelId",
		 * notificationTempData.getChannelId()).setParameter("notiMsg",
		 * notificationTempData.getShortName().toLowerCase()).list(); List notiIdExist =
		 * getSession().createSQLQuery(sqlNotiIdExist).setParameter("notiId",
		 * notificationTempData.getNotificationId()).list();
		 * 
		 * if (!(notiNameExist.get(0).toString().equalsIgnoreCase("0"))) {
		 * rmb.setResponseCode("451"); rmb.setResponseMessage("User ID Already Exist");
		 * } else if (!(notiIdExist.get(0).toString().equalsIgnoreCase("0"))) {
		 * rmb.setResponseCode("452"); rmb.setResponseMessage("Email ID Already Exist");
		 * }else { rmb.setResponseCode("200"); rmb.setResponseMessage("Both not exist");
		 * }
		 */
		return rmb;
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	public ResponseMessageBean sendNotificationToAll(NotificationsEntity notification) {
		ResponseMessageBean rmb = new ResponseMessageBean();

		String content = notification.getNotificationMsg();
		List<NotificationsEntity> notificationList = new ArrayList<>();
		List<CustomerEntity> customerDataList = new ArrayList<>();
		List<CustomerEntity> customerDataList1 = new ArrayList<>();
		List<NotificationsEntity> notificationList1 = new ArrayList<>();
		NotifcationEngineImpl notificationEng = new NotifcationEngineImpl();
		String sqlQuery = null;
		try {
			if (notification.getSendNotificationTo().equalsIgnoreCase("All")) {
				if ("Retail".equalsIgnoreCase(notification.getChannel())) {
					if ("PUSH".equalsIgnoreCase(notification.getTypeName())) {
						List<Integer> customerIdList = new ArrayList<>();
						List<BigDecimal> appList = new ArrayList<>();
						CustomerEntity customerEntity = null;
						appList.add(new BigDecimal(4));
						appList.add(new BigDecimal(5));
						// get all active customer list
						List<Object[]> customers = customerRepository.findByStatusId(3);

						for (Object[] customer : customers) {
							if (!ObjectUtils.isEmpty(customer[0])) {
								customerIdList.add(Integer.valueOf(customer[0].toString()));
							}
						}
						List<UserDeviceDetails> newDeviceMasterList = new ArrayList<>();
						List<UserDeviceDetails> deviceMasterList = null;
						if (!ObjectUtils.isEmpty(customerIdList) && customerIdList.size() > 1000) {
							// get all customer device list if device count is more than 1000 then get
							// record 1000-1000 record
							List<List<Integer>> ogrininalList = subList(customerIdList);
							for (List<Integer> list : ogrininalList) {
								deviceMasterList = deviceMasterRepository.findByCustomerIdAndStatusIdAndAppId(
										ApplicationConstants.ACTIVE_STATUS, list, appList);
								newDeviceMasterList.addAll(deviceMasterList);
							}
						} else {
							// get all customer device list
							deviceMasterList = deviceMasterRepository.findByCustomerIdAndStatusIdAndAppId(
									ApplicationConstants.ACTIVE_STATUS, customerIdList, appList);
							newDeviceMasterList.addAll(deviceMasterList);
						}
						Map<Integer, String> pushNotificationMap = new HashMap<>();
						for (UserDeviceDetails deviceMaster : newDeviceMasterList) {
							if (customerIdList.contains(deviceMaster.getCustomerId())) {
								if (!ObjectUtils.isEmpty(deviceMaster.customerId)
										&& !ObjectUtils.isEmpty(deviceMaster.getPushNotificationToken())
										&& !"NA".equalsIgnoreCase(deviceMaster.getPushNotificationToken())) {
									pushNotificationMap.put(deviceMaster.customerId,
											deviceMaster.getPushNotificationToken());
								}
							}
						}

						for (Object[] customer : customers) {
							customerEntity = new CustomerEntity();
							if (!ObjectUtils.isEmpty(customer[0])) {
								customerEntity.setId(new BigDecimal(Integer.valueOf(customer[0].toString())));
							}
							if (!ObjectUtils.isEmpty(customer[1])) {
								customerEntity.setAppid(Integer.valueOf(customer[1].toString()));
							}
							if (!ObjectUtils.isEmpty(customer[2])) {
								customerEntity.setMobile(customer[2].toString());
							}
							if (!ObjectUtils.isEmpty(pushNotificationMap)) {
								customerEntity.setPushnotificationtoken(
										pushNotificationMap.get(Integer.valueOf(customer[0].toString())));
							}
							if (!ObjectUtils.isEmpty(customerEntity)) {
								customerDataList.add(customerEntity);
							}

						}

					} else if ("EMAIL".equalsIgnoreCase(notification.getTypeName())
							|| "SMS".equalsIgnoreCase(notification.getTypeName())) {

						sqlQuery = "select c.EMAIL as email,c.MOBILE as mobile,c.id as Id,c.appid as appid from customers c"
								+ " where c.STATUSID =3 and c.EMAIL is not null ";

						customerDataList = getSession().createSQLQuery(sqlQuery).addScalar("email").addScalar("mobile")
								.addScalar("Id").addScalar("appid", StandardBasicTypes.INTEGER)
								.setResultTransformer(Transformers.aliasToBean(CustomerEntity.class)).list();
					}
				} else if ("Corporate".equalsIgnoreCase(notification.getChannel())) {

					if ("PUSH".equalsIgnoreCase(notification.getTypeName())) {
						sqlQuery = "select cu.email_id as email, cu.personal_phone as mobile,udm.PUSHNOTIFICATIONTOKEN as pushNotificationToken, "
								+ "cu.id as Id,cu.appid as appid from USERDEVICESMASTER udm "
								+ "left join corp_users cu on cu.id=udm.customerid "
								+ "left join corp_company_master ccm on ccm.id=cu.corp_comp_id "
								+ "where ccm.statusid=3 and ccm.og_status=3 and cu.statusid=3 and ccm.IS_CORPORATE<>'G' "
								+ "and cu.og_status=3 and udm.statusid=3 and udm.APPID in(2,147) "
								+ "and to_char(udm.PUSHNOTIFICATIONTOKEN) is not null and to_char(udm.PUSHNOTIFICATIONTOKEN)<>'NA'";

						List<CustomerEntity> pushNotificationList = getSession().createSQLQuery(sqlQuery)
								.addScalar("email").addScalar("mobile").addScalar("pushNotificationToken")
								.addScalar("Id").addScalar("appid", StandardBasicTypes.INTEGER)
								.setResultTransformer(Transformers.aliasToBean(CustomerEntity.class)).list();
						List<BigDecimal> idList = new ArrayList<>();
						for (CustomerEntity customerEntity : pushNotificationList) {
							if (!ObjectUtils.isEmpty(idList) && !idList.contains(customerEntity.getId())) {
								idList.add(customerEntity.getId());
								customerDataList.add(customerEntity);
							}
						}

					} else if ("EMAIL".equalsIgnoreCase(notification.getTypeName())) {
						sqlQuery = " select a.email,a.mobile,a.Id,a.appid from"
								+ " (select cu.email_id as email, cu.personal_phone as mobile,cu.id as Id,cu.appid as appid"
								+ " from corp_company_master ccm left outer join corp_users cu on cu.corp_comp_id= ccm.id"
								+ " where ccm.statusid=3 and cu.statusid=3 and cu.OG_STATUS=3)a" + " INNER JOIN"
								+ " (select max(cu.id) as id,cu.email_id as email" + " from corp_company_master ccm"
								+ " left outer join corp_users cu on cu.corp_comp_id= ccm.id"
								+ " where ccm.statusid=3 and cu.statusid=3 and cu.OG_STATUS=3"
								+ " group by cu.email_id)b ON a.id = b.id";

						customerDataList = getSession().createSQLQuery(sqlQuery).addScalar("email").addScalar("mobile")
								.addScalar("Id").addScalar("appid", StandardBasicTypes.INTEGER)
								.setResultTransformer(Transformers.aliasToBean(CustomerEntity.class)).list();

					} else if ("SMS".equalsIgnoreCase(notification.getTypeName())) {
						sqlQuery = " select a.email,a.mobile,a.Id,a.appid from"
								+ " (select cu.email_id as email, cu.personal_phone as mobile,cu.id as Id,cu.appid as appid"
								+ " from corp_company_master ccm left outer join corp_users cu on cu.corp_comp_id= ccm.id"
								+ " where ccm.statusid=3 and cu.statusid=3 and cu.OG_STATUS=3)a" + " INNER JOIN"
								+ " (select max(cu.id) as id,cu.personal_phone as mobile"
								+ " from corp_company_master ccm"
								+ " left outer join corp_users cu on cu.corp_comp_id= ccm.id"
								+ " where ccm.statusid=3 and cu.statusid=3 and cu.OG_STATUS=3"
								+ " group by cu.personal_phone)b ON a.id = b.id";

						customerDataList = getSession().createSQLQuery(sqlQuery).addScalar("email").addScalar("mobile")
								.addScalar("Id").addScalar("appid", StandardBasicTypes.INTEGER)
								.setResultTransformer(Transformers.aliasToBean(CustomerEntity.class)).list();
					}
				}

				if ("SMS".equalsIgnoreCase(notification.getTypeName())) {
					for (CustomerEntity customer : customerDataList) {
						if (customer.getMobile() != null && !customer.getMobile().isEmpty()) {
							NotificationsEntity list = new NotificationsEntity();
							list.setCustomerId(EncryptorDecryptor.decryptData(customer.getMobile()));
							list.setCreatedOn(new Date());
							list.setType(new BigDecimal(2));
							list.setAppId(new BigDecimal(customer.getAppid()));
							list.setNotificationMsg(content);
							list.setStatusId(new BigDecimal(0));
							list.setCustId(customer.getId());
							list.setResendBy(notification.getCreatedBy());
							list.setCreatedBy(notification.getCreatedBy());
							list.setRrn(new Long(Utils.randomNumber()).toString());
							list.setActivityId(notification.getCreatedBy());
							notificationList.add(list);
						}
						notificationRepository.save(notificationList);
					}

					List<NotificationsEntity> moobList = notificationRepository.getMobileList();
					if (moobList != null && !moobList.isEmpty()) {
						int i;
						for (i = 0; i < moobList.size(); i++) {
							if (!ObjectUtils.isEmpty(moobList.get(0).getCustomerId())) {
								emailUtil.sendSMSNotification(moobList.get(0).getCustomerId(),
										moobList.get(0).getNotificationMsg());
								notificationRepository.updateMobId(moobList.get(0).getId());
							}
						}

					}
					rmb.setResponseCode("200");
					rmb.setResponseMessage("Message send successfully");
					return rmb;
				} else if ("EMAIL".equalsIgnoreCase(notification.getTypeName())) {
					for (CustomerEntity customer : customerDataList) {
						if (customer.getEmail() != null && !customer.getEmail().isEmpty()) {
							NotificationsEntity list = new NotificationsEntity();
							list.setCustomerId(EncryptorDecryptor.decryptData(customer.getEmail()));
							list.setCreatedOn(new Date());
							list.setType(new BigDecimal(3));
							list.setAppId(new BigDecimal(customer.getAppid()));
							list.setNotificationMsg(content);
							list.setStatusId(new BigDecimal(0));
							list.setCustId(customer.getId());
							list.setCreatedBy(notification.getCreatedBy());
							list.setRrn(new Long(Utils.randomNumber()).toString());
							list.setActivityId(notification.getCreatedBy());
							list.setResendBy(notification.getCreatedBy());
							notificationList.add(list);
						}
					}
					notificationRepository.save(notificationList);
					List<NotificationsEntity> emailList = notificationRepository.getEmailList();
					if (emailList != null && !emailList.isEmpty()) {
						EmailUtil eu = new EmailUtil();
						int i;
						int length = emailList.size();
						for (i = 0; i < length; i++) {
							if (!ObjectUtils.isEmpty(emailList.get(i).getCustomerId())) {
								Map<String, String> notifcaion = new HashMap<String, String>();
								notifcaion.put("emailContent", emailList.get(i).getNotificationMsg());
								notifcaion.put("emailId", emailList.get(i).getCustomerId());
								notifcaion.put("subject", "PSB : Notification"); // Need to check
								eu.sendEmail(notifcaion);
								notificationRepository.updateEmailId(emailList.get(i).getId());
							}
						}
					}

					rmb.setResponseCode("200");
					rmb.setResponseMessage("Email send successfully");
					return rmb;
				} else if ("PUSH".equalsIgnoreCase(notification.getTypeName())) {
					for (CustomerEntity customer : customerDataList) {
						if (customer.getPushnotificationtoken() != null
								&& !customer.getPushnotificationtoken().isEmpty()) {
							NotificationsEntity list = new NotificationsEntity();
							list.setCustomerId(EncryptorDecryptor.decryptData(customer.getMobile()));
							list.setCreatedOn(new Date());
							list.setType(new BigDecimal(5));
							list.setAppId(new BigDecimal(customer.getAppid()));
							list.setNotificationMsg(content);
							list.setStatusId(new BigDecimal(0));
							list.setCustId(customer.getId());
							list.setCreatedBy(notification.getCreatedBy());
							list.setRrn(new Long(Utils.randomNumber()).toString());
							list.setActivityId(notification.getCreatedBy());
							list.setResendBy(notification.getCreatedBy());
							notificationList.add(list);

						}
						notificationRepository.save(notificationList);
					}

					List<NotificationsEntity> pushList = notificationRepository.getPushList();
					if (pushList != null && !pushList.isEmpty()) {
						int i;
						for (i = 0; i < pushList.size(); i++) {
							UserDeviceDetails udd = deviceMasterRepository.getUserDeviceDetails(
									pushList.get(i).getCustId().intValue(), pushList.get(i).getAppId());
							if (udd != null && !ObjectUtils.isEmpty(udd)) {
								if (!ObjectUtils.isEmpty(udd.getPushNotificationToken())) {
									Map<String, String> notifcaion = new HashMap<String, String>();
									notifcaion.put(ApplicationConstants.APP_NAME, "RIB");
									notifcaion.put(ApplicationConstants.FILE_PATH, "");
									notifcaion.put(ApplicationConstants.CUSTOMER_ID, pushList.get(i).getCustomerId());
									notifcaion.put(ApplicationConstants.ACTIVITY_NAME, "");
									notifcaion.put(ApplicationConstants.NOTIFICATION_TYPE,
											ApplicationConstants.NOTIFICATION_TYPE_PUSH);
									notifcaion.put(ApplicationConstants.CONTENT, content);
									notifcaion.put(ApplicationConstants.RRN, new Long(Utils.randomNumber()).toString());
									notifcaion.put(ApplicationConstants.REQUEST_KEY_DEVICEID, ""); // Need
									notifcaion.put(ApplicationConstants.PUSH_TOKEN,
											"fhhpEEoqQqKkTEuzxsBWmk:APA91bHXz9C988vwlj8piv9BoKjztdpitXj0-gitpSUs0illz7OUC45y-djohXAJ2L5nQWVBhToIzNWuQ-Fbj-gf3NvL1oAgJs6bJivNYXqxcyGtRbzIROJsPM1qmAq0deJIHCDaCCro"); // Need
									notificationRepository.updatePushId(pushList.get(i).getId());

								}
							}
						}

					}
					rmb.setResponseCode("200");
					rmb.setResponseMessage("Push message send successfully");
					return rmb;
				} else if ("INAPP".equalsIgnoreCase(notification.getTypeName())) {
					NotificationsEntity list = new NotificationsEntity();
					list.setCustomerId(notification.getSendNotificationTo());
					list.setCreatedOn(new Date());
					list.setType(new BigDecimal(4));
					if ("Corporate".equalsIgnoreCase(notification.getChannel())) {
						list.setAppId(new BigDecimal(2));
					} else {
						list.setAppId(new BigDecimal(4));
					}

					list.setNotificationMsg(content);
					list.setStatusId(new BigDecimal(3));
					list.setCreatedBy(notification.getCreatedBy());
					list.setRrn(new Long(Utils.randomNumber()).toString());
					list.setActivityId(notification.getCreatedBy());
					list.setResendBy(notification.getCreatedBy());
					list.setCustId(notification.getCreatedBy());
					notificationList.add(list);
					notificationRepository.save(notificationList);
					rmb.setResponseCode("200");
					rmb.setResponseMessage("InApp message send successfully");
//					}
					return rmb;
				}

			} else if (notification.getSendNotificationTo().equalsIgnoreCase("Mobile")) {
				String mob = EncryptorDecryptor.encryptData(notification.getMobile());
				if ("Retail".equalsIgnoreCase(notification.getChannel())) {
					if ("PUSH".equalsIgnoreCase(notification.getTypeName())) {
						List<BigDecimal> appList = new ArrayList<>();
						appList.add(new BigDecimal(4));
						appList.add(new BigDecimal(5));
						CustomerEntity customers = customerRepository.findByMobileNoAndStatusid(mob, 3);
						UserDeviceDetails deviceMaster = deviceMasterRepository.findByCustomerIdAndStatusIdAndAppId(
								customers.getId().intValue(), customers.getStatusid().intValue(), appList);

						customers.setPushnotificationtoken(deviceMaster.getPushNotificationToken());
						customerDataList1.add(customers);

					} else if ("INAPP".equalsIgnoreCase(notification.getTypeName())) {

						sqlQuery = "select c.EMAIL as email,c.MOBILE as mobile,c.id as Id,c.appid as appid  from customers c"
								+ " where c.STATUSID =3 AND c.MOBILE =:mobileN";
						customerDataList1 = getSession().createSQLQuery(sqlQuery).addScalar("email").addScalar("mobile")
								.addScalar("Id").addScalar("appid", StandardBasicTypes.INTEGER)
								.setParameter("mobileN", mob)
								.setResultTransformer(Transformers.aliasToBean(CustomerEntity.class)).list();

					} else {

						sqlQuery = "select c.EMAIL as email,c.MOBILE as mobile,c.id as Id,c.appid as appid  from customers c"
								+ " where c.STATUSID =3 AND c.MOBILE =:mobileN";
						customerDataList1 = getSession().createSQLQuery(sqlQuery).addScalar("email").addScalar("mobile")
								.addScalar("Id").addScalar("appid", StandardBasicTypes.INTEGER)
								.setParameter("mobileN", mob)
								.setResultTransformer(Transformers.aliasToBean(CustomerEntity.class)).list();

					}
				} else if ("Corporate".equalsIgnoreCase(notification.getChannel())) {
					if ("PUSH".equalsIgnoreCase(notification.getTypeName())) {
						sqlQuery = "select cu.email_id as email, cu.personal_phone as mobile,udm.PUSHNOTIFICATIONTOKEN as pushNotificationToken, "
								+ "cu.id as Id,cu.appid as appid from USERDEVICESMASTER udm "
								+ "left join corp_users cu on cu.id=udm.customerid "
								+ "left join corp_company_master ccm on ccm.id=cu.corp_comp_id "
								+ "where ccm.statusid=3 and ccm.og_status=3 and cu.statusid=3 and cu.personal_phone=:mobileNo and ccm.IS_CORPORATE<>'G' "
								+ "and cu.og_status=3 and udm.statusid=3 and udm.APPID in(2,147) "
								+ "and to_char(udm.PUSHNOTIFICATIONTOKEN) is not null and to_char(udm.PUSHNOTIFICATIONTOKEN)<>'NA'";

						customerDataList1 = getSession().createSQLQuery(sqlQuery).addScalar("email").addScalar("mobile")
								.addScalar("pushNotificationToken").addScalar("Id")
								.addScalar("appid", StandardBasicTypes.INTEGER).setParameter("mobileNo", mob)
								.setResultTransformer(Transformers.aliasToBean(CustomerEntity.class)).list();
					} else if ("INAPP".equalsIgnoreCase(notification.getTypeName())) {
						sqlQuery = "select cu.email_id as email, cu.personal_phone as mobile,cu.id as Id,cu.appid as appid from corp_company_master ccm left outer join corp_users cu on cu.corp_comp_id= ccm.id "
								+ " where ccm.statusid=3 and ccm.OG_STATUS=3 and ccm.IS_CORPORATE<>'G' and cu.statusid=3 and cu.OG_STATUS=3 AND cu.personal_phone =:mobileNo";

						customerDataList1 = getSession().createSQLQuery(sqlQuery).addScalar("email").addScalar("mobile")
								.addScalar("Id").addScalar("appid", StandardBasicTypes.INTEGER)
								.setParameter("mobileNo", mob)
								.setResultTransformer(Transformers.aliasToBean(CustomerEntity.class)).list();

					} else {
						if ("EMAIL".equalsIgnoreCase(notification.getTypeName())) {
							sqlQuery = " select a.email,a.mobile,a.Id,a.appid from"
									+ " (select cu.email_id as email, cu.personal_phone as mobile,cu.id as Id,cu.appid as appid"
									+ " from corp_company_master ccm left outer join corp_users cu on cu.corp_comp_id= ccm.id"
									+ " where ccm.statusid=3 and cu.statusid=3 and cu.OG_STATUS=3)a" + " INNER JOIN"
									+ " (select max(cu.id) as id,cu.email_id as email" + " from corp_company_master ccm"
									+ " left outer join corp_users cu on cu.corp_comp_id= ccm.id"
									+ " where ccm.statusid=3 and cu.statusid=3 and cu.OG_STATUS=3 AND cu.personal_phone =:mobileNo"
									+ " group by cu.email_id)b ON a.id = b.id";
						} else if ("SMS".equalsIgnoreCase(notification.getTypeName())) {
							sqlQuery = " select a.email,a.mobile,a.Id,a.appid from"
									+ " (select cu.email_id as email, cu.personal_phone as mobile,cu.id as Id,cu.appid as appid"
									+ " from corp_company_master ccm left outer join corp_users cu on cu.corp_comp_id= ccm.id"
									+ " where ccm.statusid=3 and cu.statusid=3 and cu.OG_STATUS=3)a" + " INNER JOIN"
									+ " (select max(cu.id) as id,cu.personal_phone as mobile"
									+ " from corp_company_master ccm"
									+ " left outer join corp_users cu on cu.corp_comp_id= ccm.id"
									+ " where ccm.statusid=3 and cu.statusid=3 and cu.OG_STATUS=3 AND cu.personal_phone =:mobileNo"
									+ " group by cu.personal_phone)b ON a.id = b.id";
						}

						customerDataList1 = getSession().createSQLQuery(sqlQuery).addScalar("email").addScalar("mobile")
								.addScalar("Id").addScalar("appid", StandardBasicTypes.INTEGER)
								.setParameter("mobileNo", mob)
								.setResultTransformer(Transformers.aliasToBean(CustomerEntity.class)).list();
					}
				}

				if (!ObjectUtils.isEmpty(customerDataList1)) {
					if ("SMS".equalsIgnoreCase(notification.getTypeName())) {
						for (CustomerEntity customer : customerDataList1) {
							if (customer.getMobile() != null && !customer.getMobile().isEmpty()) {
								NotificationsEntity list = new NotificationsEntity();
								list.setCustomerId(EncryptorDecryptor.decryptData(customer.getMobile()));
								list.setCreatedOn(new Date());
								list.setType(new BigDecimal(2));
								list.setAppId(new BigDecimal(customer.getAppid()));
								list.setNotificationMsg(content);
								list.setStatusId(new BigDecimal(0));
								list.setCustId(customer.getId());
								list.setCreatedBy(notification.getCreatedBy());
								list.setRrn(new Long(Utils.randomNumber()).toString());
								list.setActivityId(notification.getCreatedBy());
								list.setResendBy(notification.getCreatedBy());
								notificationList.add(list);
								// notificationRepository.save(list);
							}
							notificationList1 = notificationRepository.save(notificationList);
						}

						NotificationsEntity smsDetailsById = notificationRepository
								.getSmsDetailsById(notificationList1.get(0).getId());
						if (!ObjectUtils.isEmpty(smsDetailsById)) {
							String mNum = smsDetailsById.getCustomerId();
							emailUtil.sendSMSNotification(mNum, content);
							notificationRepository.updateMobId(smsDetailsById.getId());
							rmb.setResponseCode("200");
							rmb.setResponseMessage("SMS send successfully");
							return rmb;
						}
					} else if ("PUSH".equalsIgnoreCase(notification.getTypeName())) {
						for (CustomerEntity customer : customerDataList1) {
							if (customer.getPushnotificationtoken() != null
									&& !customer.getPushnotificationtoken().isEmpty()) {
								NotificationsEntity list = new NotificationsEntity();
								list.setCustomerId(EncryptorDecryptor.decryptData(customer.getMobile()));
								list.setCreatedOn(new Date());
								list.setType(new BigDecimal(5));
								list.setAppId(new BigDecimal(customer.getAppid()));
								list.setNotificationMsg(content);
								list.setStatusId(new BigDecimal(0));
								list.setCustId(customer.getId());
								list.setCreatedBy(notification.getCreatedBy());
								list.setRrn(new Long(Utils.randomNumber()).toString());
								list.setActivityId(notification.getCreatedBy());
								list.setResendBy(notification.getCreatedBy());
								notificationList.add(list);
								// notificationRepository.save(list);
							}
							notificationList1 = notificationRepository.save(notificationList);
						}

						NotificationsEntity smsDetailsById = notificationRepository
								.getPushDetailsById(notificationList1.get(0).getId());
						if (!ObjectUtils.isEmpty(smsDetailsById)) {
							UserDeviceDetails udd1 = deviceMasterRepository.getUserDeviceDetails(
									smsDetailsById.getCustId().intValue(), smsDetailsById.getAppId());
							if (udd1 != null && !ObjectUtils.isEmpty(udd1)) {
								if (!ObjectUtils.isEmpty(udd1.getPushNotificationToken())) {
									Map<String, String> notifcaion = new HashMap<String, String>();
									notifcaion.put(ApplicationConstants.APP_NAME, "RIB");
									notifcaion.put(ApplicationConstants.FILE_PATH, "");
									notifcaion.put(ApplicationConstants.CUSTOMER_ID,
											notificationList1.get(0).getCustomerId());
									notifcaion.put(ApplicationConstants.ACTIVITY_NAME, content);
									notifcaion.put(ApplicationConstants.NOTIFICATION_TYPE,
											ApplicationConstants.NOTIFICATION_TYPE_PUSH);
									notifcaion.put(ApplicationConstants.CONTENT, content);
									notifcaion.put(ApplicationConstants.RRN, new Long(Utils.randomNumber()).toString());
									notifcaion.put(ApplicationConstants.REQUEST_KEY_DEVICEID, ""); // Need
									notifcaion.put(ApplicationConstants.PUSH_TOKEN,
											"fhhpEEoqQqKkTEuzxsBWmk:APA91bHXz9C988vwlj8piv9BoKjztdpitXj0-gitpSUs0illz7OUC45y-djohXAJ2L5nQWVBhToIzNWuQ-Fbj-gf3NvL1oAgJs6bJivNYXqxcyGtRbzIROJsPM1qmAq0deJIHCDaCCro"); // Need
									// to
									rmb = notificationEng.sendPushNotification(notifcaion);

									notificationRepository.updatePushId(smsDetailsById.getId());
									rmb.setResponseCode("200");
									rmb.setResponseMessage("Push message send successfully");
									return rmb;

								}
							}
						}

					} else if ("EMAIL".equalsIgnoreCase(notification.getTypeName())) {
						for (CustomerEntity customer : customerDataList1) {
							if (customer.getMobile() != null && !customer.getMobile().isEmpty()) {
								NotificationsEntity list = new NotificationsEntity();
								list.setCustomerId(EncryptorDecryptor.decryptData(customer.getEmail()));
								list.setCreatedOn(new Date());
								list.setType(new BigDecimal(3));
								list.setAppId(new BigDecimal(customer.getAppid()));
								list.setNotificationMsg(content);
								list.setStatusId(new BigDecimal(0));
								list.setCustId(customer.getId());
								list.setCreatedBy(notification.getCreatedBy());
								list.setRrn(new Long(Utils.randomNumber()).toString());
								list.setActivityId(notification.getCreatedBy());
								list.setResendBy(notification.getCreatedBy());
								notificationList.add(list);
							}
							notificationList1 = notificationRepository.save(notificationList);
						}

						NotificationsEntity emailDetailsById = notificationRepository
								.getEmailDetailsById(notificationList1.get(0).getId());
						if (!ObjectUtils.isEmpty(emailDetailsById)) {
							EmailUtil eu = new EmailUtil();
							if (!ObjectUtils.isEmpty(emailDetailsById.getCustomerId())) {
								Map<String, String> notifcaion = new HashMap<String, String>();
								notifcaion.put("emailContent", emailDetailsById.getNotificationMsg());
								notifcaion.put("emailId", emailDetailsById.getCustomerId());
								notifcaion.put("subject", "PSB : Notification"); // Need to check
								eu.sendEmail(notifcaion);
							}
							notificationRepository.updateEmailId(emailDetailsById.getId());
							rmb.setResponseCode("200");
							rmb.setResponseMessage("Email send successfully");
							return rmb;

						}
						rmb.setResponseCode("202");
						rmb.setResponseMessage("Mobile/Email does not exist");
						return rmb;

					} else if ("INAPP".equalsIgnoreCase(notification.getTypeName())) {
						for (CustomerEntity customer : customerDataList1) {
							NotificationsEntity list = new NotificationsEntity();
							list.setCustomerId(EncryptorDecryptor.decryptData(customer.getMobile()));
							list.setCreatedOn(new Date());
							list.setType(new BigDecimal(4));
							list.setAppId(new BigDecimal(customer.getAppid()));
							list.setNotificationMsg(content);
							list.setStatusId(new BigDecimal(3));
							list.setCustId(customer.getId());
							list.setCreatedBy(notification.getCreatedBy());
							list.setRrn(new Long(Utils.randomNumber()).toString());
							list.setActivityId(notification.getCreatedBy());
							list.setResendBy(notification.getCreatedBy());
							notificationList.add(list);
							notificationRepository.save(notificationList);
						}
						rmb.setResponseCode("200");
						rmb.setResponseMessage("Inapp message send successfully");
						return rmb;
					}
				} else {
					rmb.setResponseCode("202");
					rmb.setResponseMessage("Mobile number does not exist");
					return rmb;
				}
			}

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			rmb.setResponseCode("202");
			rmb.setResponseMessage("Notification sending failed");
		}
		return rmb;
	}

	private List<List<Integer>> subList(List<Integer> customerIdList) {
		List<List<Integer>> finalList = new ArrayList<>();

		int startIndex = 0;
		int endIndex = 999;
		int cutOffSize = 1000;
		int listSize = customerIdList.size();

		for (int i = startIndex; i < customerIdList.size(); i += listSize) {
			if (listSize > 0 && listSize > 1000) {
				finalList.add(customerIdList.subList(startIndex, endIndex + 1));
				listSize = listSize - cutOffSize;
				startIndex = endIndex + 1;
				if (listSize < cutOffSize) {
					endIndex = endIndex + listSize + 1;
				} else {
					endIndex = endIndex + cutOffSize;
				}
			} else {
				finalList.add(customerIdList.subList(startIndex, endIndex));
				break;
			}
		}
		return finalList;
	}

	public boolean updateLastResendOn(NotificationsEntity notificationData) {

		try {
			String sql = "update NOTIFICATIONS set LASTRESENTON=:curDate where  id=:notiId";
			int resp = getSession().createSQLQuery(sql).setParameter("notiId", notificationData.getId())
					.setParameter("curDate", new Date()).executeUpdate();
			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;

		}
	}

	public int getTotalAttamptCount(NotificationsEntity notificationData, int timeInterval) {
		LOGGER.info("Notification Type: " + notificationData.getType());
		LOGGER.info("customerId: " + notificationData.getCustomerId());
		try {
			Connection connection = null;
			connection = DriverManager.getConnection(dbConnection, dbUserName, dbPassword);
			Statement stmt = connection.createStatement();
			ResultSet rs1 = null;
//			int limit = Integer.parseInt(timeInterval);
			int countResult = 1;
			String query1 = "Select COUNT(*) as totalCount from notifications where notification_type = 'xDeviceId' and customerid = 'XcustId' and createdon > sysdate - interval '"
					+ timeInterval + "' minute";
			if (null != notificationData.getCustomerId() && notificationData.getCustomerId().length() == 10)
				query1 = query1.replace("xDeviceId", String.valueOf(notificationData.getType())).replace("XcustId",
						notificationData.getCustomerId());
			else
				query1 = query1.replace("xDeviceId", String.valueOf(notificationData.getType())).replace("XcustId",
						notificationData.getCustomerId());
			LOGGER.info("query1---->" + query1);
			rs1 = stmt.executeQuery(query1);
			while (rs1.next()) {
				countResult = Integer.parseInt(rs1.getString("totalCount").toString());
			}
			LOGGER.info("totalcountResult---->" + countResult);
//			int countResult = timeInterval + 1;
//			int notificationCount = notificationRepository.notificationCount(notificationData.getType(),
//					notificationData.getCustomerId(),timeInterval);
//			LOGGER.info("Notification count By Repository: " + notificationCount);
			return countResult;

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return 0;

	}

	public void saveNotificationAttampt(NotificationsEntity notifications) {
		Session session = sessionFactory.getCurrentSession();
		try {
			session.save(notifications);
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
	}

}
