package com.itl.pns.dao.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.Calendar;
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
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.itl.pns.bean.ChangePasswordBean;
import com.itl.pns.bean.UserDetailsBean;
import com.itl.pns.constant.ApplicationConstants;
import com.itl.pns.dao.LoginDao;
import com.itl.pns.entity.AdminUserSessionsEntity;
import com.itl.pns.entity.User;
import com.itl.pns.util.EmailUtil;
import com.itl.pns.util.EncryptDeryptUtility;
import com.itl.pns.util.EncryptorDecryptor;
import com.itl.pns.util.PasswordSecurityUtil;
import com.itl.pns.util.RandomNumberGenerator;

/**
 * 
 * @author Sareeka Bangar
 * @since 10-5-2017
 * @version 1.0
 */

/**
 * This class is the LoginDaoImpl implementation class for the LoginDao
 * interface which performsadd, delete , modify and get bot details operation on
 * Bots .
 */
@Repository("LoginDao")
@Transactional
public class LoginDaoImpl implements LoginDao {

	static Logger LOGGER = Logger.getLogger(LoginDaoImpl.class);

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Autowired
	EmailUtil util;

	@SuppressWarnings("unchecked")
	@Override
	public UserDetailsBean getUserID(String userid) {
		UserDetailsBean userdata = null;
		StringBuilder qry = new StringBuilder();
		qry.append(
				"select um.userid,r.code,um.userlastlogin , r.name as role, r.description,um.statusid,um.thumbnail as thumbnail,"
						+ "um.id as user_id, ud.first_name,ud.last_name,ud.id as user_detail_id,ud.email as email,ud.phonenumber as phonenumber, "
						+ "r.id as role_id,r.roletype as roletype,um.logintype,"
						+ "rt.name as roleTypeName,um.branchcode  from user_master um inner join user_details ud on um.id= ud.user_master_id inner join "
						+ " roles r on ud.role_id=r.id inner join role_types rt on r.roletype=rt.id where um.statusid=:stsId"
						+ " and r.statusid=:stsId and lower(um.userid)=:userid");

		LOGGER.info(qry);
		LOGGER.info(userid);

		userdata = (UserDetailsBean) getSession().createSQLQuery(qry.toString())
				.setParameter("userid", userid.toLowerCase()).setParameter("stsId", ApplicationConstants.ACTIVE_SATUS)
				.setResultTransformer(Transformers.aliasToBean(UserDetailsBean.class)).uniqueResult();
		if (!ObjectUtils.isEmpty(userdata)) {
			try {
				String img = EncryptDeryptUtility.clobStringConversion(userdata.getTHUMBNAIL());
				userdata.setTHUMBNAILSTRING(img);
				userdata.setTHUMBNAIL(null);
				userdata.setBRANCHCODE(userdata.getBRANCHCODE());
				userdata.setRoleTypeName(userdata.getROLETYPENAME());
			} catch (IOException e) {
				LOGGER.error("userId: " + userid.toLowerCase() + "Error in getUserId: " + e);
			} catch (SQLException e) {
				LOGGER.error("userId: " + userid.toLowerCase() + "Error in getuserId: " + e);
			}
		}
		return userdata;

	}

	@Override
	public boolean changePassword(ChangePasswordBean changebean) {
		boolean isCahnaged = false;
		try {
			String sql = " select password from user_master where userid=:userid";
			List list = getSession().createSQLQuery(sql.toString()).setParameter("userid", changebean.getUSERID())
					.list();
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
				;

				String sqlquery = " update user_master set password=:password where userid=:userid";
				int i = getSession().createSQLQuery(sqlquery).setParameter("password", encryptpass)
						.setParameter("userid", changebean.getUSERID()).executeUpdate();
				if (i > 0) {
					isCahnaged = true;
				} else
					isCahnaged = false;
			}
		} catch (Exception e) {
			LOGGER.error("userId: " + changebean.getUSERID() + "Error in change password: " + e);
		}

		return isCahnaged;
	}

	@Override
	public boolean forgetPassword(ChangePasswordBean changebean) {
		boolean ismailsend = false;
		String mailid = changebean.getEMAILID();
		try {
			String sqlquery = "select um.id as ID from user_master um inner join user_details ud on um.id = ud.user_master_id "
					+ "where um.userid=:userid and ud.email=:emailid ";

			List<ChangePasswordBean> list = getSession().createSQLQuery(sqlquery.toString())
					.setParameter("userid", changebean.getUSERID().toLowerCase())
					.setParameter("emailid", changebean.getEMAILID())
					.setResultTransformer(Transformers.aliasToBean(ChangePasswordBean.class)).list();

			if (!list.isEmpty()) {

				BigInteger i = list.get(0).getID().toBigInteger();
				RandomNumberGenerator object = new RandomNumberGenerator();
				String newpassword = object.generateRandomString();
				// String newpassword = "Test@1234";
				ismailsend = true;
				LOGGER.error("userId: " + changebean.getUSERID() + "Forget password:" + newpassword);
				// String encryptpass = EncryptDeryptUtility.md5("Test@1234");
//				String encryptpass = PasswordSecurityUtil.toHexString(PasswordSecurityUtil.getSHA(newpassword));
				String encryptpass = EncryptorDecryptor.encryptData(newpassword);
				LOGGER.error("userId: " + changebean.getUSERID() + "enc Password: " + encryptpass);
				EmailUtil eu = new EmailUtil();

				Map<String, String> notifcaion = new HashMap<String, String>();
				notifcaion.put("userId", changebean.getUSERID());
				notifcaion.put("appName", "entityId");
				notifcaion.put("RRN", "1234");
				notifcaion.put("CreateUser", "CreateUser");
				notifcaion.put("filepath", "");
				notifcaion.put("emailContent", "Dear Customer, Your new password for login is " + newpassword
						+ ". Do not share your OTP with anyone. Bank NEVER asks for otp over Call, SMS or Email -Punjab&Sind Bank");
				notifcaion.put("emailId", changebean.getEMAILID());
				notifcaion.put("deviceTokenId", ""); // Need to check
				notifcaion.put("subject", "Password Send Successfully!"); // Need to check
				boolean notifcaionSent = eu.sendEmail(notifcaion);

				String sql = " update user_master set password=:password where id=:id";

				int in = getSession().createSQLQuery(sql).setParameter("password", encryptpass).setParameter("id", i)
						.executeUpdate();
				if (in > 0) {
					ismailsend = true;
				} else
					ismailsend = false;
			}

		} catch (Exception e) {
			LOGGER.error("userId: " + changebean.getUSERID() + "Exception in change password: " + e);
			ismailsend = false;
		}

		return ismailsend;
	}

	public boolean logout(String token) {
		int executeUpdate = 0;
		// String sql = "update user_token set status=1 where token=:token";
		// int executeUpdate = getSession().createSQLQuery(sql).setParameter("token",
		// token).executeUpdate();
		if (executeUpdate > 0) {
			return true;
		} else
			return false;
	}

	public boolean logoutByUserId(String userid) {
		int executeUpdate = 0;
		// String sql = "update user_token set status=1 where user_id=(select id from
		// user_master where userid=:userid)";
		// int executeUpdate = getSession().createSQLQuery(sql).setParameter("userid",
		// userid).executeUpdate();
		if (executeUpdate > 0) {
			return true;
		} else
			return false;
	}

	@Override
	public boolean updateStatus(String token) {
		String sql = "update user_token set status=1  where token=:token";
		int executeUpdate = getSession().createSQLQuery(sql).setParameter("token", token).executeUpdate();
		if (executeUpdate > 0) {
			return true;
		} else
			return false;

	}

	@Override
	public boolean updateStatusActive(String token) {
		String sql = "update user_token set status=0  where token=:token";
		int executeUpdate = getSession().createSQLQuery(sql).setParameter("token", token).executeUpdate();
		if (executeUpdate > 0) {
			return true;
		} else
			return false;
	}

	@Override
	public User ckeckLogin(User user) {
		String sql = "select u.id as id, u.password as password from user_master u where userid=:userid and statusid=:statusid";
		User userMaster = (User) getSession().createSQLQuery(sql).addScalar("id", StandardBasicTypes.BIG_INTEGER)
				.addScalar("password").setParameter("userid", user.getUserid().toLowerCase())
				.setParameter("statusid", ApplicationConstants.ACTIVE_SATUS)
				.setResultTransformer(Transformers.aliasToBean(User.class)).uniqueResult();
		return userMaster;
	}

	@Override
	public List<UserDetailsBean> getUserLoginTypes() {
		List<UserDetailsBean> list = null;
		try {
			String sqlQuery = "SELECT DISTINCT(USERTYPE) FROM USER_MASTER";

			list = getSession().createSQLQuery(sqlQuery)

					.setResultTransformer(Transformers.aliasToBean(UserDetailsBean.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public int saveToAdminUserSession(UserDetailsBean user) {
		Session session = getSession();
		try {

			updateAdminSessionStatus(user.getUSER_ID().toBigInteger());
			AdminUserSessionsEntity sessionObj = new AdminUserSessionsEntity();
			sessionObj.setSessionToken(getDeviceId());
			sessionObj.setDeviceToken(getDeviceIdForSession());
			sessionObj.setStartTime(Calendar.getInstance().getTime());
			sessionObj.setEndTime(Calendar.getInstance().getTime());
			sessionObj.setEndBy(user.getUSER_ID());
			sessionObj.setUserId(user.getUSER_ID());
			sessionObj.setLatitude(user.getLatitude());
			sessionObj.setLongitude(user.getLongitude());
			sessionObj.setLatitude(user.getLatitude());
			sessionObj.setLongitude(user.getLongitude());
			sessionObj.setOs(user.getOs());
			sessionObj.setBrowser(user.getBrowser());
			sessionObj.setClientIp(user.getClientIp());
			sessionObj.setCretedBy(user.getUSER_ID());
			sessionObj.setCreatedOn(Calendar.getInstance().getTime());
			sessionObj.setUpdatedBy(user.getUSER_ID());
			sessionObj.setUpdatedOn(Calendar.getInstance().getTime());
			sessionObj.setStatusId(BigDecimal.valueOf(3));
			sessionObj.setOtp(user.getOTP());
			sessionObj.setBranchCode(user.getBRANCHCODE());
			sessionObj.setResendotpattempt("0");
			sessionObj.setWrongattemptsotp("0");
			sessionObj.setUserLoginDetailsId(user.getUserLoginDetailsId());
			session.save(sessionObj);
			user.setSessionToken(sessionObj.getSessionToken());
			user.setDeviceToken(sessionObj.getDeviceToken());
			return 1;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return 0;
		}
	}

	private String getDeviceId() {
		Calendar cal = Calendar.getInstance();
		return ("" + cal.get(Calendar.YEAR)).substring(3) + cal.get(Calendar.DAY_OF_YEAR)
				+ cal.get(Calendar.HOUR_OF_DAY) + cal.get(Calendar.MINUTE) + cal.get(Calendar.SECOND)
				+ cal.get(Calendar.MILLISECOND) + (int) (Math.random() * (99 - 10));
	}

	private String getDeviceIdForSession() {
		Calendar cal = Calendar.getInstance();
		return ("" + cal.get(Calendar.YEAR)).substring(3) + cal.get(Calendar.DAY_OF_YEAR)
				+ cal.get(Calendar.HOUR_OF_DAY) + cal.get(Calendar.MINUTE) + cal.get(Calendar.SECOND)
				+ cal.get(Calendar.MILLISECOND) + (int) (Math.random() * (1080 - 128));
	}

	@Override
	public int updateAdminSessionStatus(BigInteger userId) {
		int res = 0;
		try {
			String sql = "update ADMINUSRSESSIONS set statusId=0, ENDTIME=sysdate where userId=:userId and statusid=3";
			res = getSession().createSQLQuery(sql).setParameter("userId", userId).executeUpdate();
			return res;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return res;

		}
	}

//	@Override
//	public int selectInactiveSessionLastRow(BigInteger userId) {
//		int res = 0;
//		try {
//			String sql = "select * from adminusrsessions where statusid=0 and userid=:userId ORDER BY id DESC FETCH FIRST 1 ROWS ONLY";
//			res = getSession().createSQLQuery(sql).setParameter("userId", userId).executeUpdate();
//			return res;
//		} catch (Exception e) {
//			LOGGER.info("Exception:", e);
//			return res;
//
//		}
//	}

}