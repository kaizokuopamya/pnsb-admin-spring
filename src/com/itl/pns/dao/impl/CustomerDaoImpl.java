package com.itl.pns.dao.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itl.pns.bean.CountryRestrictionBean;
import com.itl.pns.bean.DateBean;
import com.itl.pns.bean.EmailRequestBean;
import com.itl.pns.bean.FacilityStatusBean;
import com.itl.pns.bean.RegistrationDetailsBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.bean.WalletPointsBean;
import com.itl.pns.constant.ApplicationConstants;
import com.itl.pns.dao.CustomerDao;
import com.itl.pns.entity.ActivitySettingMasterEntity;
import com.itl.pns.entity.AdminWorkFlowRequestEntity;
import com.itl.pns.entity.ChannelMasterEntity;
import com.itl.pns.entity.CustomerEntity;
import com.itl.pns.entity.CustomerOtherInfoEntity;
import com.itl.pns.entity.OfferDetailsEntity;
import com.itl.pns.entity.RMMASTER;
import com.itl.pns.entity.SecurityQuestionMaster;
import com.itl.pns.repository.CustomerRepository;
import com.itl.pns.service.MasterConfigService;
import com.itl.pns.service.impl.RestServiceCall;
import com.itl.pns.util.AdminEmailUtil;
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
public class CustomerDaoImpl implements CustomerDao {

	static Logger LOGGER = Logger.getLogger(AdminWorkFlowReqUtility.class);

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	@Autowired
	EmailUtil util;

	@Autowired
	MasterConfigService masterConfigService;

	@Autowired
	CustomerRepository registrationRepository;

	@Autowired
	AdminWorkFlowReqUtility adminWorkFlowReqUtility;

	@Autowired
	AdminEmailUtil adminEmaiUtil;

	@Autowired
	RestServiceCall rest;

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public List<CountryRestrictionBean> getcountryRestriction(CountryRestrictionBean bean) {
		List<CountryRestrictionBean> list = new ArrayList<CountryRestrictionBean>();
		try {
			if (null != bean.getCUSTID()) {
				LOGGER.info("inside CUST");

				String sql = "SELECT CU.ID,CU.STATUSID,C.MOBILE AS MOBILENO ,CO.NAME AS COUNTRYNAME,C.CUSTOMERNAME,C.EMAIL ,S.NAME AS STATUS  FROM COUNTRYMASTER CO INNER JOIN CUSTCOUNTRYMAPPTING CU ON CO.ID=CU.COUNTRYID "
						+ "INNER JOIN STATUSMASTER S ON S.ID=CU.STATUSID INNER JOIN CUSTOMERS C ON C.ID=CU.CUSTOMERID WHERE CU.CUSTOMERID=:custid";

				list = getSession().createSQLQuery(sql).setParameter("custid", bean.getCUSTID())
						.setResultTransformer(Transformers.aliasToBean(CountryRestrictionBean.class)).list();
			} else if (null != bean.getMOBILENO()) {
				LOGGER.info("inside MOBILE");

				String sql = "SELECT CU.ID ,CU.STATUSID,C.MOBILE AS MOBILENO,CO.NAME AS COUNTRYNAME,C.CUSTOMERNAME,C.EMAIL ,S.NAME AS STATUS  FROM COUNTRYMASTER CO INNER JOIN CUSTCOUNTRYMAPPTING CU ON CO.ID=CU.COUNTRYID "
						+ "INNER JOIN STATUSMASTER S ON S.ID=CU.STATUSID INNER JOIN CUSTOMERS C ON C.ID=CU.CUSTOMERID WHERE C.MOBILE=:mobile";
				list = getSession().createSQLQuery(sql).setParameter("mobile", bean.getMOBILENO())
						.setResultTransformer(Transformers.aliasToBean(CountryRestrictionBean.class)).list();
			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public Boolean updateCountryRestrictionStatus(CountryRestrictionBean countryRestrictionBean) {
		String sql = "update custcountrymappting set statusid=:status where id=:id";

		int res = getSession().createSQLQuery(sql).setParameter("status", countryRestrictionBean.getSTATUSID())
				.setParameter("id", countryRestrictionBean.getID()).executeUpdate();
		if (res > 0)
			return true;
		else
			return false;
	}

	@Override
	public List<RegistrationDetailsBean> getCustomerDetails(DateBean dateBean) {
		List<RegistrationDetailsBean> list = null;
		String sqlQuery = "";
		try {
			sqlQuery = "select c.SSA_ACTIVE,c.SSA_ACCOUNT_NUMBER,c.ID,c.CIF,c.ISPASSWORDLOCKED,c.CUSTOMERNAME,c.USERNAME,c.MPIN,c.USERPASSWORD,c.EMAIL,c.MOBILE,"
					+ "c.ISMPINLOCKED,c.MOBILELASTLOGGEDON,c.WEBLASTLOGGENON,c.ISMPINENABLED,c.APPID,c.PREFEREDLANGUAGE,c.FINGUREUNLOCKENABLED,"
					+ "c.CREATEDON,c.UPDATEDBY,c.UPDATEDON,c.DOB,c.GENDER,c.ISUPIENABLED,c.ISBLOCKED_UPI,c.ISUPIREGISTERED,c.IBREGSTATUS, "
					+ "c.WRONGATTEMPTSMPIN,c.WRONGATTEMPTSPWD,c.STATUSID,c.FRID,c.TPIN,c.ISTPINLOCKED,c.ISBIOMETRICENABLED,c.ISSMSENABLED,c.ISEMAILENABLED,c.ISPUSHNOTIFICATIONENABLED,c.ISMOBILEENABLED,c.ISWEBENABLED,s.name as STATUSNAME,a.shortname  as APPNAME from customers c  inner join statusmaster s "
					+ " on s.Id=c.statusid inner join appmaster a on a.id=c.appid "
					+ " where c.createdon between TO_DATE('" + dateBean.getFromdate() + "','yyyy-mm-dd') and TO_DATE('"
					+ dateBean.getTodate() + "','yyyy-mm-dd')+1 order by c.createdon desc";
			list = getSession().createSQLQuery(sqlQuery)
					.setResultTransformer(Transformers.aliasToBean(RegistrationDetailsBean.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return list;
	}

	@Override
	public List<RegistrationDetailsBean> getCustomerDetailsById(int id) {
		List<RegistrationDetailsBean> list = null;
		try {
			String sqlQuery = "select c.SSA_ACCOUNT_NUMBER,c.ID,c.CIF,c.ISPASSWORDLOCKED,c.CUSTOMERNAME,c.USERNAME,c.MPIN,c.USERPASSWORD,c.EMAIL,c.MOBILE,c.ISMPINLOCKED,c.MOBILELASTLOGGEDON,c.WEBLASTLOGGENON,c.ISMPINENABLED,c.APPID,"
					+ "c.PREFEREDLANGUAGE,c.FINGUREUNLOCKENABLED,c.CREATEDON,c.UPDATEDBY,c.UPDATEDON,c.DOB,c.GENDER,c.WRONGATTEMPTSMPIN,c.WRONGATTEMPTSTPIN,"
					+ "c.WRONGATTEMPTSPWD,c.STATUSID,c.FRID,c.TPIN,c.ISTPINLOCKED,c.ISBIOMETRICENABLED,c.ISSMSENABLED,C.ISUPIENABLED,C.ISBLOCKED_UPI,C.ISUPIREGISTERED,"
					+ "c.ISEMAILENABLED,c.ISPUSHNOTIFICATIONENABLED,c.ISMOBILEENABLED,c.ISWEBENABLED ,s.name as STATUSNAME, a.shortname as APPNAME, aw.remark  "
					+ "\"remark\"" + " " + ", aw.userAction  " + "\"userAction\"" + " "
					+ ",c.IBREGSTATUS  from customers c  "
					+ "inner join statusmaster s on s.Id=c.statusid  inner join appmaster a on a.id=c.appid left join ADMINWORKFLOWREQUEST aw on aw.activityrefno=:id and aw.tablename='CUSTOMERS' where c.ID=:id";

			list = getSession().createSQLQuery(sqlQuery).setParameter("id", id)
					.setResultTransformer(Transformers.aliasToBean(RegistrationDetailsBean.class)).list();

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return list;
	}

	@Override
	public List<OfferDetailsEntity> getOfferDetails(int statusid) {
		List<OfferDetailsEntity> list = null;
		String sqlQuery = null;
		try {
			if (statusid != 22) {
				sqlQuery = "select ofr.ID as id,ofr.weblink as weblink,ofr.servicetype as serviceType,ofr.BASE64IMAGESMALL as baseImageSmall ,ofr.BASE64IMAGELARGE as baseImageLarge,ofr.IMAGEDESCRIPTIONLARGE as imgdescLarge,ofr.IMAGEDESCRIPTIONSMALL as imgdescSmall,"
						+ " ofr.IMAGECAPTION as imgcaption,ofr.createdon as createdon,ofr.createdby as createdby,um.USERID as createdByName,ofr.updatedon as updatedon,ofr.updatedby as updatedby,"
						+ " ofr.seqnumber,ofr.statusId as statusId,ofr.latitude,ofr.longitude,ofr.validfrom as validFrom,ofr.validto as validTo,ofr.appid,s.name as statusname,a.shortname as productname "
						+ " from OFFERSDETAILS_PRD ofr "
						+ " inner join statusmaster s on ofr.statusid=s.id inner join appmaster a on a.id=ofr.appid inner join user_master um on ofr.createdby = um.id where ofr.statusid=:statusid  order by ofr.createdon desc";

				list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("weblink")
						.addScalar("serviceType").addScalar("baseImageLarge", StandardBasicTypes.STRING)
						.addScalar("baseImageSmall", StandardBasicTypes.STRING)
						.addScalar("imgdescLarge", StandardBasicTypes.STRING)
						.addScalar("imgdescSmall", StandardBasicTypes.STRING).addScalar("imgcaption")
						.addScalar("statusName").addScalar("statusId").addScalar("appId").addScalar("updatedby")
						.addScalar("updatedon").addScalar("createdby").addScalar("createdon").addScalar("latitude")
						.addScalar("longitude").addScalar("validFrom").addScalar("validTo").addScalar("seqNumber")
						.addScalar("productName").addScalar("createdByName").setParameter("statusid", statusid)
						.setResultTransformer(Transformers.aliasToBean(OfferDetailsEntity.class)).list();

			} else {
				sqlQuery = "select ofr.ID as id,ofr.weblink as weblink,ofr.servicetype as serviceType,ofr.BASE64IMAGESMALL as baseImageSmall ,ofr.BASE64IMAGELARGE as baseImageLarge,ofr.IMAGEDESCRIPTIONLARGE as imgdescLarge,ofr.IMAGEDESCRIPTIONSMALL as imgdescSmall,"
						+ " ofr.IMAGECAPTION as imgcaption,ofr.createdon as createdon,ofr.createdby as createdby,um.USERID as createdByName,ofr.updatedon as updatedon,ofr.updatedby as updatedby,"
						+ " ofr.seqnumber,ofr.statusId as statusId,ofr.latitude,ofr.longitude,ofr.validfrom as validFrom,ofr.validto as validTo,ofr.appid,s.name as statusname,a.shortname as productname "
						+ " from OFFERSDETAILS_PRD ofr "
						+ " inner join statusmaster s on ofr.statusid=s.id inner join appmaster a on a.id=ofr.appid inner join user_master um on ofr.createdby = um.id  order by ofr.createdon desc";

				list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("weblink")
						.addScalar("serviceType").addScalar("baseImageLarge", StandardBasicTypes.STRING)
						.addScalar("baseImageSmall", StandardBasicTypes.STRING)
						.addScalar("imgdescLarge", StandardBasicTypes.STRING)
						.addScalar("imgdescSmall", StandardBasicTypes.STRING).addScalar("imgcaption")
						.addScalar("statusName").addScalar("statusId").addScalar("appId").addScalar("updatedby")
						.addScalar("updatedon").addScalar("createdby").addScalar("createdon").addScalar("latitude")
						.addScalar("longitude").addScalar("validFrom").addScalar("validTo").addScalar("seqNumber")
						.addScalar("productName").addScalar("createdByName")
						.setResultTransformer(Transformers.aliasToBean(OfferDetailsEntity.class)).list();

			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;

	}

	@Override
	public List<WalletPointsBean> getWalletPointsData() {
		List<WalletPointsBean> list = null;
		try {
			String sqlQuery = "   SELECT CW.MOBILENO AS MOBILE, CW.AMOUNT,CW.POINTSEARNED AS POINTS, CW.REMARKS,CW.CREATEDON,S.name AS STATUS, um.USERID as createdByName, CW.CREATEDBY, "
					+ "CUST.CUSTOMERNAME , WC.CONFIGTYPE FROM CUSTOMERWALLETPOINTS CW INNER JOIN STATUSMASTER S  ON S.ID=CW.STATUSID "
					+ "INNER JOIN CUSTOMERS CUST ON CUST.ID =CW.CUSTOMERID  INNER JOIN  WALLETPOINTSCONFIGURATION WC ON WC.ID=CW.POINTTYPE inner join user_master um on CW.createdby = um.id";

			list = getSession().createSQLQuery(sqlQuery)
					.setResultTransformer(Transformers.aliasToBean(WalletPointsBean.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return list;
	}

	@Override
	public List<WalletPointsBean> getWalletPoints() {
		List<WalletPointsBean> list = null;
		try {
			String sqlQuery = "SELECT W.* ,S.name AS STATUS,A.SHORTNAME, um.USERID as createdByName FROM WALLETPOINTSCONFIGURATION W INNER "
					+ "JOIN APPMASTER A ON A.ID=W.APPID INNER JOIN STATUSMASTER S ON S.ID=W.STATUSID inner join user_master um on W.createdby = um.id ORDER BY W.ID DESC";
			list = getSession().createSQLQuery(sqlQuery)
					.setResultTransformer(Transformers.aliasToBean(WalletPointsBean.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return list;
	}

	@Override
	public List<WalletPointsBean> getWalletPointsById(int id) {
		List<WalletPointsBean> list = null;
		try {
			String sqlQuery = "SELECT W.* ,S.name AS STATUS,A.SHORTNAME , aw.remark,aw.userAction FROM WALLETPOINTSCONFIGURATION W INNER "
					+ "JOIN APPMASTER A ON A.ID=W.APPID INNER JOIN STATUSMASTER S ON S.ID=W.STATUSID "
					+ " left join ADMINWORKFLOWREQUEST aw on aw.activityrefno=W.id and aw.tablename='WALLETPOINTSCONFIGURATION' where W.id=:id";
			list = getSession().createSQLQuery(sqlQuery).setParameter("id", id)
					.setResultTransformer(Transformers.aliasToBean(WalletPointsBean.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return list;
	}

	@Override
	public List<OfferDetailsEntity> getOfferDetailsByid(int id) {
		List<OfferDetailsEntity> list = null;
		try {
			String sqlQuery = "select ofr.ID as id,ofr.weblink as weblink,ofr.servicetype as serviceType,ofr.base64ImageSmall as baseImageSmall,ofr.base64ImageLarge as baseImageLarge,ofr.IMAGEDESCRIPTIONLARGE as imgdescLarge,ofr.IMAGEDESCRIPTIONSMALL as imgdescSmall,"
					+ " ofr.IMAGECAPTION as imgcaption,ofr.createdon as createdon,ofr.createdby as createdby,ofr.updatedon as updatedon,ofr.updatedby as updatedby,"
					+ " ofr.seqnumber,ofr.statusId as statusId,ofr.latitude,ofr.longitude,ofr.validfrom as validFrom,ofr.validto as validTo,ofr.appid,s.name as statusname,a.shortname as productname, "
					+ " aw.remark,aw.userAction from OFFERSDETAILS_PRD ofr "
					+ " inner join statusmaster s on ofr.statusid=s.id inner join appmaster a on a.id=ofr.appid "
					+ "left join ADMINWORKFLOWREQUEST aw on aw.activityrefno=ofr.ID and aw.tablename='OFFERSDETAILS_PRD'  where ofr.id=:id  ";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("weblink").addScalar("serviceType")
					.addScalar("baseImageLarge", StandardBasicTypes.STRING)
					.addScalar("baseImageSmall", StandardBasicTypes.STRING)
					.addScalar("imgdescLarge", StandardBasicTypes.STRING)
					.addScalar("imgdescSmall", StandardBasicTypes.STRING).addScalar("imgcaption")
					.addScalar("statusName").addScalar("statusId").addScalar("appId").addScalar("updatedby")
					.addScalar("updatedon").addScalar("createdby").addScalar("createdon").addScalar("latitude")
					.addScalar("longitude").addScalar("validFrom").addScalar("validTo").addScalar("seqNumber")
					.addScalar("productName").addScalar("remark").addScalar("userAction").setParameter("id", id)
					.setResultTransformer(Transformers.aliasToBean(OfferDetailsEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return list;

	}

	@Override
	@SuppressWarnings("unchecked")
	public List<SecurityQuestionMaster> getSecurityQuestions() {
		List<SecurityQuestionMaster> list = null;
		try {
			String sqlQuery = "select qm.id as id ,qm.appid,qm.createdon,qm.question as question,s.name as statusname,qm.statusId , a.shortname as appname,qm.createdby as createdby,  um.USERID as createdByName"
					+ " from SECURITYQUESTIONMASTER_PRD qm inner join statusmaster s on s.id= qm.STATUSID   "
					+ "inner join appmaster a on a.id=qm.appid inner join user_master um on qm.createdby = um.id ORDER BY qm.id DESC";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("appid").addScalar("statusid")
					.addScalar("question").addScalar("statusname").addScalar("appname").addScalar("createdon")
					.addScalar("createdby").addScalar("createdByName")
					.setResultTransformer(Transformers.aliasToBean(SecurityQuestionMaster.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public List<SecurityQuestionMaster> getSecurityQuestionById(int id) {
		List<SecurityQuestionMaster> list = null;
		try {
			String sqlQuery = "select qm.id as id,qm.appid as appid,qm.createdon,qm.question as question,s.name as statusname,qm.statusId as statusid,"
					+ "a.shortname as appname, aw.remark, aw.userAction,qm.createdby as createdby "
					+ " from SECURITYQUESTIONMASTER_PRD qm inner join statusmaster s on s.id= qm.STATUSID   "
					+ "inner join appmaster a on a.id=qm.appid left join ADMINWORKFLOWREQUEST aw on aw.activityrefno=:id and aw.tablename = 'SECURITYQUESTIONMASTER_PRD' where qm.id =:id";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("appid").addScalar("statusid")
					.addScalar("createdby").addScalar("question").addScalar("statusname").addScalar("appname")
					.addScalar("createdon").addScalar("remark").addScalar("userAction").setParameter("id", id)
					.setResultTransformer(Transformers.aliasToBean(SecurityQuestionMaster.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public List<RegistrationDetailsBean> getCustByCifMobileName(RegistrationDetailsBean detailsBean) {
		List<RegistrationDetailsBean> list = null;
		try {
			String sqlQuery = "";

			sqlQuery = "select  c.SSA_ACTIVE,c.SSA_ACCOUNT_NUMBER,c.ID,c.CIF,c.ISPASSWORDLOCKED,c.CUSTOMERNAME,c.USERNAME,c.MPIN,c.USERPASSWORD, "
					+ "c.EMAIL,c.MOBILE,c.ISMPINLOCKED,c.MOBILELASTLOGGEDON,c.WEBLASTLOGGENON,c.ISMPINENABLED,c.APPID,c.PREFEREDLANGUAGE, "
					+ "c.FINGUREUNLOCKENABLED,c.CREATEDON,c.UPDATEDBY,c.UPDATEDON,c.DOB,c.GENDER,c.WRONGATTEMPTSMPIN, c.IBREGSTATUS,"
					+ "c.WRONGATTEMPTSPWD,c.STATUSID,c.FRID,c.TPIN,c.ISTPINLOCKED,c.ISBIOMETRICENABLED,c.ISSMSENABLED,s.name as STATUSNAME,a.shortname as APPNAME ,"
					+ "c.ISEMAILENABLED,c.ISPUSHNOTIFICATIONENABLED,c.ISMOBILEENABLED,c.ISWEBENABLED,s1.name  "
					+ "\"ibRegStatusName\"" + " " + " from customers c inner join appmaster a on a.id= c.appid  "
					+ "inner join statusmaster s on s.id = c.statusid inner join statusmaster s1 on s1.Id=c.IBREGSTATUS where ";

			if (detailsBean.getMOBILE() != null) {
				String mob = EncryptorDecryptor.encryptData(detailsBean.getMOBILE());
				sqlQuery = sqlQuery.concat(" " + "c.mobile Like('%" + mob + "%')");
			}
			if (detailsBean.getCIF() != null) {
				sqlQuery = sqlQuery.concat(" " + "c.CIF Like('%" + detailsBean.getCIF() + "%')");
			} else if (detailsBean.getID() != null) {
				sqlQuery = sqlQuery.concat(" " + "c.id Like('%" + detailsBean.getID() + "%')");
			} else if (detailsBean.getEMAIL() != null) {
				sqlQuery = sqlQuery
						.concat(" " + "Lower(c.email) Like('%" + detailsBean.getEMAIL().toLowerCase() + "%')");
			} else if (detailsBean.getCUSTOMERNAME() != null) {
				sqlQuery = sqlQuery.concat(
						" " + "Lower(c.CUSTOMERNAME) Like('%" + detailsBean.getCUSTOMERNAME().toLowerCase() + "%')");
			} else if (detailsBean.getFromdate() != null && detailsBean.getTodate() != null) {
				sqlQuery = sqlQuery.concat(" " + " c.createdon between TO_DATE('" + detailsBean.getFromdate()
						+ "','yyyy-mm-dd') and TO_DATE('" + detailsBean.getTodate() + "','yyyy-mm-dd')+1");
			} else if (detailsBean.getSTATUSNAME() != null) {
				if (detailsBean.getSTATUSNAME().equalsIgnoreCase("PENDING")) {
					sqlQuery = sqlQuery
							.concat(" " + " c.statusid in(21,24,25,30,52,53,55,83,88,89,90,91,92,95,96,102) ");
				}
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
	public boolean saveRMMasterData(RMMASTER rmMaster) {
		Session session = sessionFactory.getCurrentSession();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(rmMaster.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		int userStatus = rmMaster.getStatusId().intValue();
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(rmMaster.getActivityName());
		try {
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				rmMaster.setStatusId(BigDecimal.valueOf(statusId)); // 97- ADMIN_CHECKER_PENDIN
			}

			rmMaster.setCreatedon(new Date());
			rmMaster.setUpdatedon(new Date());
			session.save(rmMaster);

			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				List<RMMASTER> list = getRMMasterData();
				ObjectMapper mapper = new ObjectMapper();
				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();

				adminData.setCreatedByUserId(rmMaster.getUser_ID());
				adminData.setCreatedByRoleId(rmMaster.getRole_ID());
				adminData.setPageId(rmMaster.getSubMenu_ID()); // set submenuId as pageid
				adminData.setCreatedBy(rmMaster.getCreatedby());
				adminData.setContent(mapper.writeValueAsString(rmMaster));
				adminData.setActivityId(rmMaster.getSubMenu_ID()); // set submenuId as activityid
				adminData.setStatusId(BigDecimal.valueOf(statusId)); // 97- ADMIN_CHECKER_PENDIN
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6)); // 6- checker roleid
				adminData.setRemark(rmMaster.getRemark());
				adminData.setActivityName(rmMaster.getActivityName());
				adminData.setActivityRefNo(list.get(0).getId());
				adminData.setTableName("RMMASTER_PRD");
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);

				// Save data to admin work flow history
				adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(rmMaster.getSubMenu_ID(), list.get(0).getId(),
						rmMaster.getCreatedby(), rmMaster.getRemark(), rmMaster.getRole_ID(),
						mapper.writeValueAsString(rmMaster));
			}

			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}

	}

	@Override
	public List<RMMASTER> getRMMasterData() {
		List<RMMASTER> list = null;
		try {
			String sqlQuery = "select rm.id ,rm.rmname as rmName, rm.rmid as rmId , rm.appid as appId , rm.createdby as createdby,um.USERID as createdByName ,rm.createdon as createdon,"
					+ "rm.updatedby as updatedby , rm.updatedon as updatedon , rm.statusid  as statusId ,s.name as statusname , a.shortname as appname from RMMASTER_PRD rm "
					+ "inner join statusmaster s on s.id= rm.STATUSID inner join appmaster a on a.id= rm.appid inner join user_master um on rm.createdby = um.id order by rm.createdon desc";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("rmId").addScalar("appId")
					.addScalar("statusId").addScalar("rmName").addScalar("statusname").addScalar("createdon")
					.addScalar("createdby").addScalar("updatedon").addScalar("updatedby").addScalar("appname")
					.addScalar("createdByName").setResultTransformer(Transformers.aliasToBean(RMMASTER.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public boolean updateRMMasterData(RMMASTER rmMaster) {
		Session session = sessionFactory.getCurrentSession();
		int userStatus = rmMaster.getStatusId().intValue();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(rmMaster.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(rmMaster.getActivityName());
		try {
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				rmMaster.setStatusId(BigDecimal.valueOf(statusId)); // 97- ADMIN_CHECKER_PENDIN
			}

			rmMaster.setUpdatedon(new Date());
			session.update(rmMaster);

			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				List<AdminWorkFlowRequestEntity> AdminDataList = adminWorkFlowReqUtility
						.getAdminWorkFlowDataByActivityRefNo(rmMaster.getId().intValue(), rmMaster.getSubMenu_ID());

				ObjectMapper mapper = new ObjectMapper();
				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
				rmMaster.setUpdatedon(new Date());
				adminData.setCreatedOn(rmMaster.getCreatedon());
				adminData.setCreatedByUserId(rmMaster.getUser_ID());
				adminData.setCreatedByRoleId(rmMaster.getRole_ID());
				adminData.setPageId(rmMaster.getSubMenu_ID()); // set submenuId as pageid
				adminData.setCreatedBy(rmMaster.getCreatedby());
				adminData.setContent(mapper.writeValueAsString(rmMaster));
				adminData.setActivityId(rmMaster.getSubMenu_ID()); // set submenuId as activityid
				adminData.setStatusId(BigDecimal.valueOf(statusId)); // 97- ADMIN_CHECKER_PENDIN
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6)); // 6- checker roleid
				adminData.setRemark(rmMaster.getRemark());
				adminData.setActivityName(rmMaster.getActivityName());
				adminData.setActivityRefNo(rmMaster.getId());
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminData.setTableName("RMMASTER_PRD");
				if (ObjectUtils.isEmpty(AdminDataList)) {
					adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);
				} else if (!ObjectUtils.isEmpty(AdminDataList)) {
					adminData.setId(AdminDataList.get(0).getId());
					adminWorkFlowReqUtility.updateAdminWorkFlowRequest(adminData);

				}

				// Save data to admin work flow history
				adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(rmMaster.getSubMenu_ID(), rmMaster.getId(),
						rmMaster.getCreatedby(), rmMaster.getRemark(), rmMaster.getRole_ID(),
						mapper.writeValueAsString(rmMaster));
			} else {

				// if record is present in admin work flow then update status
				adminWorkFlowReqUtility.getCheckerDataByctivityRefId(rmMaster.getId().toBigInteger(),
						BigInteger.valueOf(userStatus), rmMaster.getSubMenu_ID());
			}

			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}

	}

	@Override
	public List<RMMASTER> getRMMasterDataById(int id) {
		List<RMMASTER> list = null;
		try {
			String sqlQuery = "select rm.id ,rm.rmname as rmName, rm.rmid as rmId , rm.appid as appId , rm.createdby as createdby ,rm.createdon as createdon,"
					+ "rm.updatedby as updatedby , rm.updatedon as updatedon , rm.statusid  as statusId ,s.name as statusname , a.shortname as appname, "
					+ "aw.remark, aw.userAction from RMMASTER_PRD rm "
					+ "inner join statusmaster s on s.id= rm.STATUSID inner join appmaster a on a.id= rm.appid left join ADMINWORKFLOWREQUEST  aw on aw.activityrefno=rm.id and aw.tablename='RMMASTER_PRD' "
					+ " where rm.id =:id";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("rmId").addScalar("appId")
					.addScalar("statusId").addScalar("rmName").addScalar("statusname").addScalar("createdon")
					.addScalar("remark").addScalar("userAction").addScalar("createdby").addScalar("updatedon")
					.addScalar("updatedby").addScalar("appname").setParameter("id", id)
					.setResultTransformer(Transformers.aliasToBean(RMMASTER.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public boolean deletetRMMasterById(RMMASTER rmMaster) {
		boolean success = true;
		int userStatus = rmMaster.getStatusId().intValue();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(rmMaster.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		int deletedStatusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.DELETED);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq("RMMASTERDELETE"); // changed table name here
		try {
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				rmMaster.setStatusId(BigDecimal.valueOf(statusId)); // 97- ADMIN_CHECKER_PENDIN
			} else {
				rmMaster.setStatusId(BigDecimal.valueOf(deletedStatusId)); // 97- ADMIN_CHECKER_PENDIN
			}

			String sqlQuery = "update RMMASTER_PRD set statusid=:stsId , updatedon=curdate() where id=:id";
			getSession().createSQLQuery(sqlQuery).setParameter("stsId", rmMaster.getStatusId())
					.setParameter("id", rmMaster.getId()).executeUpdate();

			rmMaster.setUpdatedon(new Date());
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				List<AdminWorkFlowRequestEntity> AdminDataList = adminWorkFlowReqUtility
						.getAdminWorkFlowDataByActivityRefNo(rmMaster.getId().intValue(), rmMaster.getSubMenu_ID());

				ObjectMapper mapper = new ObjectMapper();
				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();

				adminData.setCreatedOn(rmMaster.getCreatedon());
				adminData.setCreatedByUserId(rmMaster.getUser_ID());
				adminData.setCreatedByRoleId(rmMaster.getRole_ID());
				adminData.setPageId(rmMaster.getSubMenu_ID()); // set submenuId as pageid
				adminData.setCreatedBy(rmMaster.getCreatedby());
				adminData.setContent(mapper.writeValueAsString(rmMaster));
				adminData.setActivityId(rmMaster.getSubMenu_ID()); // set submenuId as activityid
				adminData.setStatusId(BigDecimal.valueOf(statusId)); // 97- ADMIN_CHECKER_PENDIN
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6)); // 6- checker roleid
				adminData.setRemark(rmMaster.getRemark());
				adminData.setActivityName("RMMASTERDELETE");
				adminData.setActivityRefNo(rmMaster.getId());
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminData.setTableName("RMMASTER_PRD");
				if (ObjectUtils.isEmpty(AdminDataList)) {
					adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);
				} else if (!ObjectUtils.isEmpty(AdminDataList)) {
					adminData.setId(BigDecimal.valueOf(AdminDataList.get(0).getId().intValue()));
					adminWorkFlowReqUtility.updateAdminWorkFlowRequest(adminData);

				}

				// Save data to admin work flow history
				adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(rmMaster.getSubMenu_ID(), rmMaster.getId(),
						rmMaster.getCreatedby(), rmMaster.getRemark(), rmMaster.getRole_ID(),
						mapper.writeValueAsString(rmMaster));
			} else {

				// if record is present in admin work flow then update status
				adminWorkFlowReqUtility.getCheckerDataByctivityRefId(rmMaster.getId().toBigInteger(),
						BigInteger.valueOf(userStatus), rmMaster.getSubMenu_ID());
			}

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}

		return success;
	}

	@Override
	public List<ChannelMasterEntity> getChannelList() {
		List<ChannelMasterEntity> list = null;
		try {
			String sqlQuery = "select cm.id, cm.shortname as shortName, cm.statusId as statusId, cm.createdby as createdby, um.USERID as createdByName  from channelmaster cm inner join user_master um on cm.createdby = um.id";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("shortName").addScalar("statusId")
					.addScalar("createdby").addScalar("createdByName")
					.setResultTransformer(Transformers.aliasToBean(ChannelMasterEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public List<RegistrationDetailsBean> getCustomerType() {

		List<RegistrationDetailsBean> list = null;

		return list;
	}

	@Override
	public boolean saveCustOtherInfo(CustomerOtherInfoEntity custOtherInfo) {
		Session session = sessionFactory.getCurrentSession();
		int userStatus = custOtherInfo.getStatusId().intValue();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(custOtherInfo.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(custOtherInfo.getActivityName());

		try {
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				custOtherInfo.setStatusId(BigDecimal.valueOf(statusId)); // 97- ADMIN_CHECKER_PENDIN
			}

			custOtherInfo.setCreatedOn(new Date());
			custOtherInfo.setUpdatedOn(new Date());
			session.save(custOtherInfo);

			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

				List<CustomerOtherInfoEntity> list = getCustOtherInfo();
				ObjectMapper mapper = new ObjectMapper();

				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
				adminData.setAppId(custOtherInfo.getAppId());
				adminData.setCreatedByUserId(custOtherInfo.getUser_ID());
				adminData.setCreatedByRoleId(custOtherInfo.getRole_ID());
				adminData.setPageId(custOtherInfo.getSubMenu_ID()); // set submenuId as pageid
				adminData.setCreatedBy(custOtherInfo.getCreatedBy());
				adminData.setContent(mapper.writeValueAsString(custOtherInfo));
				adminData.setActivityId(custOtherInfo.getSubMenu_ID()); // set submenuId as activityid
				adminData.setStatusId(BigDecimal.valueOf(statusId)); // 97- ADMIN_CHECKER_PENDIN
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6)); // 6- checker roleid
				adminData.setRemark(custOtherInfo.getRemark());
				adminData.setActivityName(custOtherInfo.getActivityName());
				adminData.setActivityRefNo(list.get(0).getId());
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminData.setTableName("CUSTOMEROTHERINFO_PRD");
				adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);

				// Save data to admin work flow history
				adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(custOtherInfo.getSubMenu_ID(), list.get(0).getId(),
						custOtherInfo.getCreatedBy(), custOtherInfo.getRemark(), custOtherInfo.getRole_ID(),
						mapper.writeValueAsString(custOtherInfo));
			}
			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}

	@Override
	public List<CustomerOtherInfoEntity> getCustOtherInfo() {
		List<CustomerOtherInfoEntity> list = null;
		try {

			String sqlQuery = "select ci.id, ci.customerId as customerId, ci.employerName as employerName,ci.employerAddress as employerAddress,"
					+ "ci.employerNumber as employerNumber,"
					+ "ci.gstNumber as gstNumber, ci.createdBy as createdBy ,ci.createdOn as createdOn, um.USERID as createdByName, "
					+ "ci.statusId as statusId,ci.appId as appId, ci.updatedBy as updatedBy,"
					+ "ci.updatedOn as updatedOn,s.name as statusname,a.shortname  as appname ,c.customername as customername , c.mobile as mobile, c.cif as cif "
					+ "from CUSTOMEROTHERINFO_PRD ci inner join statusmaster s on s.Id = ci.statusid inner join user_master um on ci.createdby = um.id "
					+ "inner join appmaster  a on a.Id=ci.appid join CUSTOMERS c on c.Id = ci.customerid order by ci.createdOn desc";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("customerId")
					.addScalar("employerName").addScalar("employerNumber").addScalar("employerAddress")
					.addScalar("customername").addScalar("mobile").addScalar("cif").addScalar("gstNumber")
					.addScalar("createdOn").addScalar("createdBy").addScalar("updatedOn").addScalar("createdByName")
					.addScalar("statusname").addScalar("updatedBy").addScalar("statusId").addScalar("appId")
					.addScalar("appname").setResultTransformer(Transformers.aliasToBean(CustomerOtherInfoEntity.class))
					.list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public boolean updateCustOtherInfo(CustomerOtherInfoEntity custOtherInfo) {
		Session session = sessionFactory.getCurrentSession();
		int userStatus = custOtherInfo.getStatusId().intValue();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(custOtherInfo.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(custOtherInfo.getActivityName());

		try {

			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				custOtherInfo.setStatusId(BigDecimal.valueOf(statusId)); // 97- ADMIN_CHECKER_PENDIN
			}
			// save to cust other info table
			custOtherInfo.setUpdatedOn(new Date());
			session.update(custOtherInfo);

			// save to adminWorkFlowRequest tabele
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

				List<AdminWorkFlowRequestEntity> AdminDataList = adminWorkFlowReqUtility
						.getAdminWorkFlowDataByActivityRefNo(custOtherInfo.getId().intValue(),
								custOtherInfo.getSubMenu_ID());

				ObjectMapper mapper = new ObjectMapper();
				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
				adminData.setAppId(custOtherInfo.getAppId());
				adminData.setCreatedOn(custOtherInfo.getCreatedOn());
				adminData.setCreatedByUserId(custOtherInfo.getUser_ID());
				adminData.setCreatedByRoleId(custOtherInfo.getRole_ID());
				adminData.setPageId(custOtherInfo.getSubMenu_ID()); // set submenuId as pageid
				adminData.setCreatedBy(custOtherInfo.getCreatedBy());
				adminData.setContent(mapper.writeValueAsString(custOtherInfo));
				adminData.setActivityId(custOtherInfo.getSubMenu_ID()); // set submenuId as activityid
				adminData.setStatusId(BigDecimal.valueOf(statusId)); // 97- ADMIN_CHECKER_PENDIN
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6)); // 6- checker roleid
				adminData.setRemark(custOtherInfo.getRemark());
				adminData.setActivityName(custOtherInfo.getActivityName());
				adminData.setActivityRefNo(custOtherInfo.getId());
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminData.setTableName("CUSTOMEROTHERINFO_PRD");
				if (ObjectUtils.isEmpty(AdminDataList)) {
					adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);
				} else if (!ObjectUtils.isEmpty(AdminDataList)) {
					adminData.setId(BigDecimal.valueOf(AdminDataList.get(0).getId().intValue()));
					adminWorkFlowReqUtility.updateAdminWorkFlowRequest(adminData);

				}

				// Save data to admin work flow history
				adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(custOtherInfo.getSubMenu_ID(), custOtherInfo.getId(),
						custOtherInfo.getCreatedBy(), custOtherInfo.getRemark(), custOtherInfo.getRole_ID(),
						mapper.writeValueAsString(custOtherInfo));
			} else {
				// if record is present in admin work flow then update status
				adminWorkFlowReqUtility.getCheckerDataByctivityRefId(custOtherInfo.getId().toBigInteger(),
						BigInteger.valueOf(userStatus), custOtherInfo.getSubMenu_ID());
			}

			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}

	}

	@Override
	public List<CustomerOtherInfoEntity> getCustOtherInfoById(int id) {
		List<CustomerOtherInfoEntity> list = null;

		try {

			String sqlQuery = "select ci.id, ci.customerId as customerId, ci.employerName as employerName,ci.employerAddress as employerAddress,"
					+ "ci.employerNumber as employerNumber,"
					+ "ci.gstNumber as gstNumber, ci.createdBy as createdBy ,ci.createdOn as createdOn, "
					+ "ci.statusId as statusId,ci.appId as appId, ci.updatedBy as updatedBy,"
					+ "ci.updatedOn as updatedOn,s.name as statusname,a.shortname  as appname,"
					+ " c.customername as customername , c.mobile as mobile, c.cif as cif,aw.remark,aw.userAction "
					+ "from CUSTOMEROTHERINFO_PRD ci inner join statusmaster s on s.Id = ci.statusid inner join CUSTOMERS c on c.Id = ci.customerid "
					+ "inner join appmaster  a on a.Id=ci.appid left join ADMINWORKFLOWREQUEST aw on aw.activityrefno=:id and aw.tablename='CUSTOMEROTHERINFO_PRD'"
					+ " where ci.id=:id order by ci.createdOn desc";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("customerId")
					.addScalar("employerName").addScalar("employerNumber").addScalar("employerAddress")
					.addScalar("gstNumber").addScalar("createdOn").addScalar("createdBy").addScalar("updatedOn")
					.addScalar("statusname").addScalar("updatedBy").addScalar("statusId").addScalar("customername")
					.addScalar("cif").addScalar("mobile").addScalar("appId").addScalar("appname")
					.addScalar("userAction").addScalar("remark").setParameter("id", id)
					.setResultTransformer(Transformers.aliasToBean(CustomerOtherInfoEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public List<CustomerOtherInfoEntity> getCustOtherInfoByCustId(int custId) {
		List<CustomerOtherInfoEntity> list = null;
		try {
			String sqlQuery = "select ci.id, ci.customerId as customerId, ci.employerName as employerName,ci.employerAddress as employerAddress,"
					+ "ci.employerNumber as employerNumber,"
					+ "ci.gstNumber as gstNumber, ci.createdBy as createdBy ,ci.createdOn as createdOn, "
					+ "ci.statusId as statusId,ci.appId as appId, ci.updatedBy as updatedBy,"
					+ "ci.updatedOn as updatedOn,s.name as statusname,a.shortname  as appname ,c.customername as customername , c.mobile as mobile, c.cif as cif "
					+ "from CUSTOMEROTHERINFO_PRD ci inner join statusmaster s on s.Id = ci.statusid inner join CUSTOMERS c on c.Id = ci.customerid "
					+ "inner join appmaster  a on a.Id=ci.appid where ci.customerid=:custId";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("customerId")
					.addScalar("employerName").addScalar("employerNumber").addScalar("employerAddress")
					.addScalar("gstNumber").addScalar("createdOn").addScalar("createdBy").addScalar("updatedOn")
					.addScalar("statusname").addScalar("updatedBy").addScalar("statusId").addScalar("customername")
					.addScalar("cif").addScalar("mobile").addScalar("appId").addScalar("appname")
					.setParameter("custId", custId)
					.setResultTransformer(Transformers.aliasToBean(CustomerOtherInfoEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public ResponseMessageBean isRMNameExist(RMMASTER rmMasterData) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		try {
			String sqlNameExit = "SELECT count(*) FROM RMMASTER_PRD WHERE RMID =:rmid AND STATUSID=3";

			String sqlRMNameExit = "SELECT count(*) FROM RMMASTER_PRD WHERE LOWER(RMNAME) =:rmName AND STATUSID=3";

			List isNameExit = getSession().createSQLQuery(sqlNameExit).setParameter("rmid", rmMasterData.getRmId())
					.list();

			List isRMNameExit = getSession().createSQLQuery(sqlRMNameExit)
					.setParameter("rmName", rmMasterData.getRmName().toLowerCase()).list();

			if (!(isNameExit.get(0).toString().equalsIgnoreCase("0"))) {
				rmb.setResponseCode("500");
				rmb.setResponseMessage("RM ID Already Exist");

			} else if (!(isRMNameExit.get(0).toString().equalsIgnoreCase("0"))) {
				rmb.setResponseCode("500");
				rmb.setResponseMessage("RM Name Already Exist");
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
	public ResponseMessageBean isUpdateRMNameExist(RMMASTER rmMasterData) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		try {
			String sqlNameExit = "SELECT count(*) FROM RMMASTER_PRD WHERE RMID =:rmid  AND  ID!=:id AND STATUSID=3";

			String sqlRMNameExit = "SELECT count(*) FROM RMMASTER_PRD WHERE LOWER(RMNAME) =:rmName  AND  ID!=:id AND STATUSID=3";

			List isNameExit = getSession().createSQLQuery(sqlNameExit).setParameter("id", rmMasterData.getId())
					.setParameter("rmid", rmMasterData.getRmId()).list();

			List isRMNameExit = getSession().createSQLQuery(sqlRMNameExit).setParameter("id", rmMasterData.getId())
					.setParameter("rmName", rmMasterData.getRmName().toLowerCase()).list();

			if (!(isNameExit.get(0).toString().equalsIgnoreCase("0"))) {
				rmb.setResponseCode("500");
				rmb.setResponseMessage("RM ID Already Exist");
			} else if (!(isRMNameExit.get(0).toString().equalsIgnoreCase("0"))) {
				rmb.setResponseCode("500");
				rmb.setResponseMessage("RM Name Already Exist");
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
	public boolean sendEmailWithAttachment(EmailRequestBean emailRequestBean) {

		try {
			if (emailRequestBean.getApi_name().equalsIgnoreCase("masterconfig/getAllFacilityStatus")) {
				return sendEmailForFacility(emailRequestBean);
			}

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return true;
	}

	public boolean sendEmailForFacility(EmailRequestBean emailRequestBean) {

		List<FacilityStatusBean> list = masterConfigService.getAllFacilityStatus();

		// Create a Workbook
		Workbook workbook = new XSSFWorkbook();

		CreationHelper createHelper = workbook.getCreationHelper();

		// Create a Sheet
		Sheet sheet = workbook.createSheet("FacilityDetails");

		// Create a Font for styling header cells
		Font headerFont = workbook.createFont();

		headerFont.setFontHeightInPoints((short) 14);
		headerFont.setColor(IndexedColors.BLUE.getIndex());

		// Create a CellStyle with the font
		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);

		// Create a Row
		Row headerRow = sheet.createRow(0);

		// Create cells
		int i = 0;
		for (String data : emailRequestBean.getColumanNames()) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(data.toUpperCase());
			cell.setCellStyle(headerCellStyle);
			i++;
		}

		// Create Cell Style for formatting Date
		CellStyle dateCellStyle = workbook.createCellStyle();
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

		// Create Other rows and cells with employees data
		int rowNum = 1;
		for (FacilityStatusBean facilityData : list) {
			Row row = sheet.createRow(rowNum++);

			Cell createdOnCell = row.createCell(0);
			createdOnCell.setCellValue(facilityData.getCREATEDON());
			createdOnCell.setCellStyle(dateCellStyle);

			row.createCell(1).setCellValue(facilityData.getDISPLAYNAME());

			row.createCell(2).setCellValue(facilityData.getENCRYPTIONTYPE());

			row.createCell(3).setCellValue(facilityData.getFT_NFT());

			row.createCell(4).setCellValue(facilityData.getLIMITS().toString());

			row.createCell(5).setCellValue(facilityData.getSHORTNAME());

			row.createCell(6).setCellValue(facilityData.getSTATUS());

		}

		/* Resize all columns to fit the content size */
		int k = 0;
		for (String data : emailRequestBean.getColumanNames()) {
			sheet.autoSizeColumn(k);
			k++;
		}

		// TODO need to add file path from app.properties
		File files = new File("D:/FacilityDetails.xlsx");

		try {
			// Write the output to a file
			FileOutputStream fileOut = new FileOutputStream(files);
			workbook.write(fileOut);
			fileOut.close();

			util.sendBulkEmail(emailRequestBean.getEmailList(), files.toString());
			files.delete();

		} catch (IOException e) {
			LOGGER.info("Exception:", e);
		}
		return true;
	}

	@Override
	public List<RegistrationDetailsBean> getAllCustomers() {
		List<RegistrationDetailsBean> list = null;
		String sqlQuery = "";
		try {
			sqlQuery = "select c.SSA_ACTIVE,c.SSA_ACCOUNT_NUMBER,c.ID,c.CIF,c.ISPASSWORDLOCKED,c.CUSTOMERNAME,c.USERNAME,c.MPIN,c.USERPASSWORD,c.EMAIL,c.MOBILE,c.ISMPINLOCKED,c.MOBILELASTLOGGEDON,c.WEBLASTLOGGENON,c.ISMPINENABLED,c.APPID,c.PREFEREDLANGUAGE,c.FINGUREUNLOCKENABLED,um.USERID  "
					+ "\"createdByName\"" + " " + ",c.CREATEDON,c.UPDATEDBY,c.UPDATEDON,c.DOB,c.GENDER, "
					+ "c.WRONGATTEMPTSMPIN,c.WRONGATTEMPTSPWD,c.STATUSID,c.FRID,c.TPIN,c.ISTPINLOCKED,c.ISBIOMETRICENABLED,c.ISSMSENABLED,c.ISEMAILENABLED,c.ISPUSHNOTIFICATIONENABLED,c.ISMOBILEENABLED,c.ISWEBENABLED,s.name as STATUSNAME,a.shortname  as APPNAME ,c.IBREGSTATUS,s1.name  "
					+ "\"ibRegStatusName\"" + " " + " from customers c  inner join statusmaster s "
					+ " on s.Id=c.statusid inner join statusmaster s1 on s1.Id=c.IBREGSTATUS  inner join appmaster a on a.id=c.appid left join user_master um on c.createdby = um.id  order by c.createdon desc";

			list = getSession().createSQLQuery(sqlQuery)
					.setResultTransformer(Transformers.aliasToBean(RegistrationDetailsBean.class)).list();

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return list;
	}

	@Override
	public boolean saveBulkCustomers(List<CustomerEntity> custDataList) {
		Session session = sessionFactory.getCurrentSession();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(custDataList.get(0).getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(custDataList.get(0).getActivityName());

		String apName = adminWorkFlowReqUtility.getAppNameByAppId(custDataList.get(0).getAppid().intValue());

		try {

			for (CustomerEntity custData : custDataList) {
				custData.setAction("ADD");
				custData.setRoleName(roleName);
				custData.setCreatedByName(
						adminWorkFlowReqUtility.getCreatedByNameByCreatedId(custData.getUser_ID().intValue()));
				custData.setAppName(apName);

				String encryptpass = EncryptDeryptUtility.md5(custData.getUserpassword());
				custData.setUserpassword(encryptpass);

				if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
						&& (ApplicationConstants.YESVALUE)
								.equals(Character.toString(activityMasterData.get(0).getChecker()))
						&& (ApplicationConstants.YESVALUE)
								.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
					custData.setStatusid(statusId); // 97- ADMIN_CHECKER_PENDIN
				} else {
					custData.setStatusid(3);
				}

				custData.setStatusName(adminWorkFlowReqUtility.getStatusNameByStatusId(custData.getStatusid()));
				CustomerEntity bean = registrationRepository.findBymobile(custData.getMobile());
				if (ObjectUtils.isEmpty(bean)) {
					custData.setCreatedon(new Date());
					custData.setUpdatedon(new Date());
					session.save(custData);

					if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
							&& (ApplicationConstants.YESVALUE)
									.equals(Character.toString(activityMasterData.get(0).getChecker()))
							&& (ApplicationConstants.YESVALUE)
									.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
						List<RegistrationDetailsBean> list = getAllCustomers();
						if (custData.getSubMenu_ID().intValue() == 1411) {
							custData.setSubMenu_ID(BigDecimal.valueOf(9));
						}
						ObjectMapper mapper = new ObjectMapper();
						AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
						adminData.setAppId(BigDecimal.valueOf(custData.getAppid()));
						adminData.setCreatedByUserId(custData.getUser_ID());
						adminData.setCreatedByRoleId(custData.getRole_ID());
						adminData.setPageId(custData.getSubMenu_ID()); // set submenuId as pageid
						adminData.setCreatedBy(custData.getUser_ID());
						adminData.setContent(mapper.writeValueAsString(custData));
						adminData.setActivityId(custData.getSubMenu_ID()); // set submenuId as activityid
						adminData.setStatusId(BigDecimal.valueOf(statusId)); // 97- ADMIN_CHECKER_PENDIN
						adminData.setPendingWithRoleId(BigDecimal.valueOf(6)); // 6- checker roleid
						adminData.setRemark(custData.getRemark());
						adminData.setActivityName(custData.getActivityName());
						adminData.setActivityRefNo(list.get(0).getID());
						adminData.setTableName("CUSTOMERS");
						adminData.setUserAction(BigDecimal.valueOf(3));
						adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);

						// Save data to admin work flow history
						adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(custData.getSubMenu_ID(),
								list.get(0).getID(), custData.getUser_ID(), custData.getRemark(), custData.getRole_ID(),
								mapper.writeValueAsString(custData));
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
	public List<RegistrationDetailsBean> getAllPendingCustomers() {
		String sqlQuery = "";

		sqlQuery = "select c.SSA_ACTIVE,c.SSA_ACCOUNT_NUMBER,c.ID,c.CIF,c.ISPASSWORDLOCKED,c.CUSTOMERNAME,c.USERNAME,c.MPIN,c.USERPASSWORD,c.EMAIL,c.MOBILE,c.ISMPINLOCKED,c.MOBILELASTLOGGEDON,c.WEBLASTLOGGENON,c.ISMPINENABLED,c.APPID,c.PREFEREDLANGUAGE,c.FINGUREUNLOCKENABLED,um.USERID as createdByName,c.CREATEDON,c.UPDATEDBY,c.UPDATEDON,c.DOB,c.GENDER, "
				+ "c.WRONGATTEMPTSMPIN,c.WRONGATTEMPTSPWD,c.STATUSID,c.FRID,c.TPIN,c.ISTPINLOCKED,c.ISBIOMETRICENABLED,c.ISSMSENABLED,c.ISEMAILENABLED,c.ISPUSHNOTIFICATIONENABLED,c.ISMOBILEENABLED,c.ISWEBENABLED,s.name as STATUSNAME,a.shortname  as APPNAME from customers c  inner join statusmaster s "
				+ " on s.Id=c.statusid inner join appmaster a on a.id=c.appid inner join user_master um on c.createdby = um.id where c.STATUSID in (21,24,25,30,52,53,55,83,88,89,90,91,92,95,96,102)  order by c.createdon desc";

		List<RegistrationDetailsBean> list = getSession().createSQLQuery(sqlQuery)
				.setResultTransformer(Transformers.aliasToBean(RegistrationDetailsBean.class)).list();
		return list;
	}

	@Override
	public ResponseMessageBean checkQuestionExist(SecurityQuestionMaster securityQuestionMaster) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		try {
			String sqlNameExit = "SELECT count(*) FROM SECURITYQUESTIONMASTER_PRD WHERE Lower(QUESTION) =:question AND appId=:appId ";
			List isReportNameExit = getSession().createSQLQuery(sqlNameExit)
					.setParameter("appId", securityQuestionMaster.getAppid())
					.setParameter("question", securityQuestionMaster.getQuestion().toLowerCase()).list();

			if (!(isReportNameExit.get(0).toString().equalsIgnoreCase("0"))) {
				rmb.setResponseCode("500");
				rmb.setResponseMessage("Security Question Already Exist For Same Channel");
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
	public ResponseMessageBean checkUpdateQuestionExist(SecurityQuestionMaster securityQuestionMaster) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		try {
			String sqlNameExit = "SELECT count(*) FROM SECURITYQUESTIONMASTER_PRD WHERE Lower(QUESTION) =:question  AND id !=:id  AND appId=:appId ";
			List isReportNameExit = getSession().createSQLQuery(sqlNameExit)
					.setParameter("id", securityQuestionMaster.getId())
					.setParameter("appId", securityQuestionMaster.getAppid())
					.setParameter("question", securityQuestionMaster.getQuestion().toLowerCase()).list();

			if (!(isReportNameExit.get(0).toString().equalsIgnoreCase("0"))) {
				rmb.setResponseCode("500");
				rmb.setResponseMessage("Security Question Already Exist For Same Channel");
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
	public int updateCustomerData(CustomerEntity registrationDetails) {
		int res = 0;
		try {
			String sql = "update CUSTOMERS set ISMPINENABLED=:ismpinenabled,STATUSID=:statusId,ISTPINLOCKED=:istpinlocked ,PREFEREDLANGUAGE=:preferedlanguage,"
					+ " ISBIOMETRICENABLED =:isbiometricenabled, ISWEBENABLED =:iswebenabled,ISMOBILEENABLED =:isbiometricenabled,UPDATEDON=:updatedOn,"
					+ " ISUPIENABLED=:isUPIEnabled,ISBLOCKED_UPI=:isBlocked_upi,ISUPIREGISTERED=:isUPIRegistered,IBREGSTATUS=:ibregStatus where id=:id";
			res = getSession().createSQLQuery(sql).setParameter("ismpinenabled", registrationDetails.getIsmpinenabled())
					.setParameter("statusId", registrationDetails.getStatusid())
					.setParameter("istpinlocked", registrationDetails.getIstpinlocked())
					.setParameter("preferedlanguage", registrationDetails.getPreferedlanguage())
					.setParameter("isbiometricenabled", registrationDetails.getIsbiometricenabled())
					.setParameter("iswebenabled", registrationDetails.getIswebenabled())
					.setParameter("isbiometricenabled", registrationDetails.getIsbiometricenabled())
					.setParameter("updatedOn", new Date())
					.setParameter("isUPIEnabled", registrationDetails.getIsUPIEnabled())
					.setParameter("isBlocked_upi", registrationDetails.getIsBlocked_upi())
					.setParameter("isUPIRegistered", registrationDetails.getIsUPIRegistered())
					.setParameter("id", registrationDetails.getId())
					.setParameter("ibregStatus", registrationDetails.getIBREGSTATUS()).executeUpdate();

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return res;
	}

	@Override
	public boolean updateWorngAAttempsOfCustomers(CustomerEntity customer) {
		int res = 0;
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		String mbl = EncryptorDecryptor.encryptData(customer.getMobile());

		try {
			if (customer.getWrongAttemptsPara().equalsIgnoreCase("PASSWORD")) {
				String sql = "update CUSTOMERS set WRONGATTEMPTSPWD=0 ,ISPASSWORDLOCKED='N',IBLOCKEDON=null where MOBILE=:mobile";
				res = getSession().createSQLQuery(sql).setParameter("mobile", mbl).executeUpdate();
			} else if (customer.getWrongAttemptsPara().equalsIgnoreCase("MPIN")) {
				String sql = "update CUSTOMERS set WRONGATTEMPTSMPIN=0,ISMPINLOCKED='N',MPINLOCKEDON = null where MOBILE=:mobile";
				res = getSession().createSQLQuery(sql).setParameter("mobile", mbl).executeUpdate();
			} else if (customer.getWrongAttemptsPara().equalsIgnoreCase("TPIN")) {
				String sql = "update CUSTOMERS set Wrongattemptstpin=0,ISTPINLOCKED='N',Tpinlockedon=null where MOBILE=:mobile";
				res = getSession().createSQLQuery(sql).setParameter("mobile", mbl).executeUpdate();
			}
			tx.commit();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		if (res == 1)
			return true;
		else

			return false;
	}

	public List<RegistrationDetailsBean> getCustomerByMobile(String mobileNo) {
		List<RegistrationDetailsBean> list = null;
		String encMobile = "";
		String sqlQuery = "";
		if (mobileNo.contains("=")) {
			encMobile = mobileNo;
			System.out.println("Decrypted mobile:" + EncryptorDecryptor.decryptData(mobileNo));
		} else {
			encMobile = EncryptorDecryptor.encryptData(mobileNo);

		}

		try {

			sqlQuery = "select ID as ID from customers where MOBILE=:mobileNo";

			list = getSession().createSQLQuery(sqlQuery).setParameter("mobileNo", encMobile)
					.setResultTransformer(Transformers.aliasToBean(RegistrationDetailsBean.class)).list();

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return list;
	}

	@Override
	public boolean sendCustomizeEmailToBulkUsers(EmailRequestBean emailBean) {
		NotifcationEngineImpl notificationEng = new NotifcationEngineImpl();
		boolean isSent = false;
		try {

			for (String emailObj : emailBean.getEmailList()) {

				Map<String, String> notifcaion = new HashMap<String, String>();
				notifcaion.put(ApplicationConstants.CUSTOMER_ID, emailObj);
				notifcaion.put(ApplicationConstants.APP_NAME, "RMOB");
				notifcaion.put(ApplicationConstants.RRN, String.valueOf(System.currentTimeMillis()));

				notifcaion.put(ApplicationConstants.FILE_PATH, "");
				notifcaion.put(ApplicationConstants.EMAILCONTENT, emailBean.getEmailMsgBody().replace("\n", ""));
				notifcaion.put(ApplicationConstants.NOTFICATION_EMAILID, emailObj);
				isSent = notificationEng.sendEmail(notifcaion);
			}

			isSent = true;
			// adminEmaiUtil.sendBulkEmailToCustomer(emailBean.getEmailList(),emailBean.getEmailMsgBody());
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
		return true;
	}

	@Override
	public List<RegistrationDetailsBean> getAllCustomerDetails() {

		String sqlQuery = "";
		List<RegistrationDetailsBean> list = null;
		try {
			sqlQuery = "select c.SSA_ACTIVE,c.SSA_ACCOUNT_NUMBER,c.ID,c.CIF,c.ISPASSWORDLOCKED,c.CUSTOMERNAME,c.USERNAME,c.MPIN,c.USERPASSWORD,c.EMAIL,c.MOBILE,c.ISMPINLOCKED,c.MOBILELASTLOGGEDON,c.WEBLASTLOGGENON,c.ISMPINENABLED,c.APPID,c.PREFEREDLANGUAGE,c.FINGUREUNLOCKENABLED,um.USERID as createdByName,c.CREATEDON,c.UPDATEDBY,c.UPDATEDON,c.DOB,c.GENDER, "
					+ "c.WRONGATTEMPTSMPIN,c.WRONGATTEMPTSPWD,c.STATUSID,c.FRID,c.TPIN,c.ISTPINLOCKED,c.ISBIOMETRICENABLED,c.ISSMSENABLED,c.ISEMAILENABLED,c.ISPUSHNOTIFICATIONENABLED,c.ISMOBILEENABLED,c.ISWEBENABLED,s.name as STATUSNAME,a.shortname  as APPNAME from customers c  inner join statusmaster s "
					+ " on s.Id=c.statusid inner join appmaster a on a.id=c.appid inner join user_master um on c.createdby = um.id where c.STATUSID in (21,24,25,30,52,53,55,83,88,89,90,91,92,95,96,102)  order by c.createdon desc";

			list = getSession().createSQLQuery(sqlQuery)
					.setResultTransformer(Transformers.aliasToBean(RegistrationDetailsBean.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);

		}
		return list;
	}

	@Override
	public ResponseMessageBean customerValidation(String mobile, String cif, String accountNumber, String emailId,
			String referenceNumber, String entityId) {
		ResponseMessageBean respMsg = new ResponseMessageBean();
		String res;
		try {
			res = rest.restcustomerValidation(mobile, cif, accountNumber, emailId, referenceNumber, entityId);

			respMsg.setResult(res);
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return respMsg;
	}

	@Override
	public List<CustomerEntity> getRegistrationDetails(DateBean datebean) {
		String sqlQuery = "";
		List<CustomerEntity> list = null;
		try {
			sqlQuery = "select c.customername,c.cif,c.mobile,c.createdon,c.statusid,s.name as statusName "
					+ "from customers c inner join statusmaster s on s.id=c.statusid where c.appid in (4,5) and c.statusid=3 and c.createdon BETWEEN  TO_DATE('"
					+ datebean.getFromdate() + " ','yyyy-mm-dd'" + ") AND TO_DATE('" + datebean.getTodate()
					+ "','yyyy-mm-dd'" + ") +1 ORDER  BY c.createdon desc";

			list = getSession().createSQLQuery(sqlQuery).addScalar("customername", StandardBasicTypes.STRING)
					.addScalar("cif", StandardBasicTypes.STRING).addScalar("mobile", StandardBasicTypes.STRING)
					.addScalar("createdon", StandardBasicTypes.DATE).addScalar("statusid", StandardBasicTypes.INTEGER)
					.addScalar("statusName", StandardBasicTypes.STRING)
					.setResultTransformer(Transformers.aliasToBean(CustomerEntity.class)).list();

		} catch (Exception e) {
			LOGGER.info("Exception:", e);

		}
		return list;
	}

	@Override
	public List<CustomerEntity> getRetailCustomerDetails(DateBean dateBean) {
		String sqlQuery = "";
		List<CustomerEntity> list = null;
		try {

			sqlQuery = "SELECT CIF,CREATEDON,CUSTOMERNAME,MOBILE,EMAIL FROM CUSTOMERS WHERE USERNAME<>mobile AND STATUSID=3 AND trunc(createdon) between TO_DATE('"
					+ dateBean.getFromdate() + "','yyyy-mm-dd'" + ")" + " AND  TO_DATE('" + dateBean.getTodate()
					+ "','yyyy-mm-dd'" + ")" + " order by createdon";
			list = getSession().createSQLQuery(sqlQuery).addScalar("cif", StandardBasicTypes.STRING)
					.addScalar("createdon", StandardBasicTypes.DATE)
					.addScalar("customername", StandardBasicTypes.STRING).addScalar("mobile", StandardBasicTypes.STRING)
					.addScalar("email", StandardBasicTypes.STRING)
					.setResultTransformer(Transformers.aliasToBean(CustomerEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return list;
	}

	@Override
	public ResponseMessageBean linkDLinkAccounts(String mobile, String accountNumberData, String referenceNumber,
			String entityId) {
		ResponseMessageBean respMsg = new ResponseMessageBean();
		String res;
		try {
			res = rest.linkDLinkAccounts(mobile, accountNumberData, referenceNumber, entityId);

			respMsg.setResult(res);
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return respMsg;
	}
}
