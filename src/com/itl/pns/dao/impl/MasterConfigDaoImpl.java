package com.itl.pns.dao.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itl.pns.bean.DateBean;
import com.itl.pns.bean.FacilityStatusBean;
import com.itl.pns.bean.LanguageJsonBean;
import com.itl.pns.bean.ProductBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.bean.TransactionLogBean;
import com.itl.pns.bean.UserAccountLeadsBean;
import com.itl.pns.constant.ApplicationConstants;
import com.itl.pns.dao.MasterConfigDao;
import com.itl.pns.entity.AccountSchemeMasterEntity;
import com.itl.pns.entity.ActivityMaster;
import com.itl.pns.entity.ActivitySettingMasterEntity;
import com.itl.pns.entity.AdminWorkFlowRequestEntity;
import com.itl.pns.entity.AppMasterEntity;
import com.itl.pns.entity.CertificateConfigEntity;
import com.itl.pns.entity.ConfigMasterEntity;
import com.itl.pns.entity.LanguageJson;
import com.itl.pns.entity.MaskingRulesEntity;
import com.itl.pns.entity.OfferDetailsEntity;
import com.itl.pns.entity.Product;
import com.itl.pns.entity.SchedularTransMasterEntity;
import com.itl.pns.entity.StatusMasterEntity;
import com.itl.pns.util.AdminWorkFlowReqUtility;
import com.itl.pns.util.EncryptDeryptUtility;
import com.itl.pns.util.EncryptorDecryptor;

@Repository
@Transactional
public class MasterConfigDaoImpl implements MasterConfigDao {

	static Logger LOGGER = Logger.getLogger(MasterConfigDaoImpl.class);

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	@Autowired
	AdminWorkFlowReqUtility adminWorkFlowReqUtility;

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public List<LanguageJson> getLanguageJson() {

		/*
		 * String
		 * sqlQuery="select t.id as id,t.ENGLISHTEXT as englishtext ,t.LANGUAGECODE as languagecode,t.LANGUAGETEXT as languagetext,"
		 * +
		 * "t.ISACTIVE as isactive ,t.CREATEDON as createdon,t.APPID ,a.shortname  as appName,t.LANGUAGECODEDESC as languagecodedesc,s.shortname as statusName,t.statusId ,"
		 * +
		 * " t.CREATEDBY as createdby, um.USERID as createdByName from languagejson t inner join statusmaster s on  s.id=t.statusId  inner join user_master um "
		 * +
		 * " on t.CREATEDBY = um.id,appmaster a  where t.appid=a.id and a.statusid=3 order by t.CREATEDON desc "
		 * ;
		 */

		String sqlQuery = "select t.id as id,t.ENGLISHTEXT as englishtext ,t.LANGUAGECODE as languagecode,t.LANGUAGETEXT as languagetext,"
				+ "	t.CREATEDON as createdon,t.LANGUAGECODEDESC as languagecodedesc,s.name as statusName,t.statusId ,"
				+ "			 t.CREATEDBY as createdby, um.USERID as createdByName from languagejson_new t inner join statusmaster s on  s.id=t.statusId  "
				+ "inner join user_master um " + " on t.CREATEDBY = um.id  order by t.id desc";

		List<LanguageJson> list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("englishtext")
				.addScalar("languagecode").addScalar("statusName").addScalar("statusId").addScalar("languagetext")
				.addScalar("createdon").addScalar("languagecodedesc").addScalar("createdby").addScalar("createdByName")
				.setResultTransformer(Transformers.aliasToBean(LanguageJson.class)).list();

		for (LanguageJson cm : list) {
			try {
				String decLangText = EncryptorDecryptor.decryptDataForLangJson(cm.getLanguagetext());
				String szUT8 = new String(decLangText.getBytes(), "UTF-8").replace("\ufffd?", "").replace("\ufffd", "");
				System.out.println(szUT8);
				cm.setLanguagetext(szUT8);

			} catch (Exception e) {
				LOGGER.info("Exception:", e);
			}

		}
		return list;
	}

	@Override
	public List<LanguageJsonBean> getLanguageJsonById(long id) {
		List<LanguageJsonBean> list = null;
		try {
			String sqlQuery = "SELECT T.LANGUAGETEXT as LANGUAGETEXT ,T.id, T.CREATEDON,T.CREATEDBY,T.STATUSID,T.LANGUAGECODEDESC,T.LANGUAGECODE,T.ENGLISHTEXT,"
					+ "aw.remark " + "\"remark\"" + " " + ",aw.userAction " + "\"userAction\"" + " "
					+ " FROM LANGUAGEJSON_NEW T   "
					+ " left join ADMINWORKFLOWREQUEST aw on aw.activityrefno=:id and aw.tablename='LANGUAGEJSON_NEW' WHERE T.ID=:id";
			list = getSession().createSQLQuery(sqlQuery).setParameter("id", id)
					.setResultTransformer(Transformers.aliasToBean(LanguageJsonBean.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		for (LanguageJsonBean cm : list) {
			try {

				String decLangText = EncryptorDecryptor.decryptDataForLangJson(cm.getLANGUAGETEXT());
				String szUT8 = new String(decLangText.getBytes(), "UTF-8").replace("\ufffd?", "").replace("\ufffd", "");
				System.out.println(szUT8);
				cm.setLANGUAGETEXT(szUT8);

				String langCodeDec = EncryptorDecryptor.decryptDataForLangJson(cm.getLANGUAGECODEDESC());
				String stringLangDec = new String(langCodeDec.getBytes(), "UTF-8").replace("\ufffd?", "")
						.replace("\ufffd", "");
				System.out.println(stringLangDec);
				cm.setLANGUAGECODEDESC(stringLangDec);

				/*
				 * String decLangText =
				 * EncryptorDecryptor.decryptDataForLangJson(cm.getLANGUAGETEXT());
				 * EncryptDeryptUtility encDecObj = new EncryptDeryptUtility(); String encNonand
				 * =encDecObj.encryptNonAndroid(decLangText, "@MrN$2Qi8R");
				 * System.out.println("Non And Enc:"+encNonand); cm.setLANGUAGETEXT(encNonand);
				 */

			} catch (Exception e) {
				LOGGER.info("Exception:", e);
			}

		}
		return list;
	}

	@Override
	public List<ConfigMasterEntity> findAllConfiguration() {
		List<ConfigMasterEntity> leasinglist = null;
		try {
			String sqlQuery = " SELECT t.id,t.createdOn as createdOn,t.CONFIG_KEY as configKey,t.DESCRIPTION as description,"
					+ "t.CONFIG_VALUE as configValue, a.shortname  as appname,s.name as statusname,t.CREATEDBY as createdBy, "
					+ "um.USERID as createdByName from configurationmaster t  inner join user_master um on t.createdby = um.id ,"
					+ "statusmaster s,  appmaster a where t.appid=a.id and t.statusid=s.id order by t.id desc ";
			leasinglist = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("createdOn")
					.addScalar("configKey").addScalar("description").addScalar("configValue").addScalar("appname")
					.addScalar("statusname").addScalar("createdBy").addScalar("createdByName")
					.setResultTransformer(Transformers.aliasToBean(ConfigMasterEntity.class)).list();

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return leasinglist;
	}

	@Override
	public List<ConfigMasterEntity> getConfigById(BigInteger aid) {
		List<ConfigMasterEntity> list = null;
		try {
			String sqlQuery = " SELECT t.id,t.statusid as statusId,t.createdby,t.CONFIG_KEY as configKey,t.DESCRIPTION as description,t.CONFIG_VALUE as configValue,"
					+ " a.shortname  as appname,s.name as statusname ,t.appId  ,aw.remark,aw.userAction, um.userid as createdByName, t.createdBy as createdBy  from "
					+ "  configurationmaster t  left join user_master um on um.id=t.createdby "
					+ "  inner join statusmaster s on s.id=t.statusId inner join  appmaster a on a.id=t.appid"
					+ " left join ADMINWORKFLOWREQUEST aw on aw.activityrefno=:id and aw.tablename='CONFIGURATIONMASTER'   where t.appid=a.id and t.statusid=s.id and t.id=:id";
			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("statusId").addScalar("appId")
					.addScalar("configKey").addScalar("description").addScalar("configValue").addScalar("appname")
					.addScalar("statusname").addScalar("remark").addScalar("createdByName").addScalar("createdBy")
					.addScalar("userAction").setParameter("id", aid)
					.setResultTransformer(Transformers.aliasToBean(ConfigMasterEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return list;
	}

	@Override
	public List<ConfigMasterEntity> getConfigByConfigKey(String configKey) {

		List<ConfigMasterEntity> list = null;
		try {
			String sqlQuery = " SELECT t.id,t.statusid as statusId,t.CONFIG_KEY as configKey,t.DESCRIPTION as description,t.CONFIG_VALUE as configValue"
					+ "  from configurationmaster t where t.CONFIG_KEY=:configKey ";
			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("statusId").addScalar("configKey")
					.addScalar("description").addScalar("configValue").setParameter("configKey", configKey)
					.setResultTransformer(Transformers.aliasToBean(ConfigMasterEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return list;
	}

	@Override
	public List<StatusMasterEntity> getStatus() {
		List<StatusMasterEntity> list = null;
		try {
			String sqlQuery = "select sm.id, sm.name  as shortName, sm.createdby as createdBy, um.USERID as createdByName from  "
					+ "statusmaster sm inner join user_master um on sm.createdby = um.id  where sm.isActive='Y'  order by sm.name  ";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("shortName").addScalar("createdBy")
					.addScalar("createdByName").setResultTransformer(Transformers.aliasToBean(StatusMasterEntity.class))
					.list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return list;
	}

	@Override
	public List<AppMasterEntity> getAppMasterList() {
		List<AppMasterEntity> list = null;
		try {
			String sqlQuery = "select am.id, am.shortname as shortName,am.createdby as createdby, um.USERID as createdByName "
					+ "from  appmaster am inner join user_master um on am.createdby = um.id where am.statusid=3 ";
			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("shortName").addScalar("createdby")
					.addScalar("createdByName").setResultTransformer(Transformers.aliasToBean(AppMasterEntity.class))
					.list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OfferDetailsEntity> getOfferDetails() {

		List<OfferDetailsEntity> list = null;
		try {
			String sqlQuery = "select ofr.id as id,ofr.weblink as weblink,ofr.servicetype as serviceType,ofr.BASE64IMAGESMALL as baseImageSmall,ofr.BASE64IMAGELARGE as baseImageLarge,ofr.IMAGEDESCRIPTIONLARGE as imgdescLarge,ofr.IMAGEDESCRIPTIONSMALL as imgdescSmall,"
					+ " ofr.IMAGECAPTION as imgcaption,ofr.seqnumber,ofr.latitude,ofr.longitude,ofr.validfrom as validFrom,ofr.validto as validTo,ofr.appid,s.name as statusname,a.shortname as productname "
					+ " from OFFERSDETAILS_PRD ofr "
					+ " inner join statusmaster s on ofr.statusid=s.id inner join appmaster a on a.id=ofr.appid  order by ofr.seqnumber ";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("weblink").addScalar("serviceType")
					.addScalar("baseImageSmall", StandardBasicTypes.STRING)
					.addScalar("baseImageLarge", StandardBasicTypes.STRING).addScalar("imgdescLarge")
					.addScalar("imgdescSmall").addScalar("imgcaption").addScalar("statusName").addScalar("appId")
					.addScalar("latitude").addScalar("longitude").addScalar("validFrom").addScalar("validTo")
					.addScalar("seqNumber").addScalar("productName")
					.setResultTransformer(Transformers.aliasToBean(OfferDetailsEntity.class)).list();

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return list;

	}

	@Override
	public List<TransactionLogBean> getTransactionLog() {
		List<TransactionLogBean> leasinglist = null;
		try {
			String sqlQuery = "select t.id ,t.amount,t.servicerefno ,t.rrn ,t.message1 ,app.shortname ,p.shortname as status,t.req_res, "
					+ "t.customerid ,t.thirdpartyrefno,t.createdon , a.displayname,c.customername,c.mobile  from activitymaster a "
					+ "inner join transactionlogs t on a.id=t.activityid inner join customers c on c.id=t.customerid inner join appmaster app "
					+ "on app.id=t.appid  inner join statusmaster p on p.id=t.statusid order by t.id desc";

			leasinglist = getSession().createSQLQuery(sqlQuery)
					.setResultTransformer(Transformers.aliasToBean(TransactionLogBean.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return leasinglist;
	}

	@Override
	public List<TransactionLogBean> getTransactionLogByDate(DateBean datebean) {
		List<TransactionLogBean> list = null;
		String sqlQuery = "";
		try {
			sqlQuery = "SELECT T.ID ,T.AMOUNT,T.SERVICEREFNO ,T.RRN ,T.MESSAGE1 ,APP.SHORTNAME ,P.NAME AS STATUS,T.REQ_RES, "
					+ "T.CUSTOMERID ,T.THIRDPARTYREFNO,T.CREATEDON , A.DISPLAYNAME,C.CUSTOMERNAME,C.MOBILE  FROM ACTIVITYMASTER A "
					+ "INNER JOIN TRANSACTIONLOGS T ON A.ID=T.ACTIVITYID INNER JOIN CUSTOMERS C ON C.ID=T.CUSTOMERID INNER JOIN APPMASTER APP "
					+ "ON APP.ID=T.APPID  INNER JOIN STATUSMASTER P ON P.ID=T.STATUSID "
					+ " AND (T.CREATEDON) BETWEEN TO_DATE('" + datebean.getFromdate() + "','yyyy-mm-dd'" + ")"
					+ "	 AND  TO_DATE('" + datebean.getTodate() + "','yyyy-mm-dd'"
					+ ")+1 AND T.REQ_RES='REQ' ORDER BY T.ID DESC";

			list = getSession().createSQLQuery(sqlQuery).addScalar("ID", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("AMOUNT", StandardBasicTypes.BIG_DECIMAL).addScalar("SERVICEREFNO").addScalar("RRN")
					.addScalar("MESSAGE1", StandardBasicTypes.STRING).addScalar("SHORTNAME").addScalar("STATUS")
					.addScalar("REQ_RES").addScalar("CUSTOMERID", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("THIRDPARTYREFNO").addScalar("CREATEDON").addScalar("DISPLAYNAME").addScalar("MOBILE")
					.setResultTransformer(Transformers.aliasToBean(TransactionLogBean.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductBean> getProducts() {

		List<ProductBean> list = null;
		try {
			String sqlQuery = "select p.id,p.productname as productName ,p.description as description ,p.createdon as createdon,"
					+ "  p.appid as appId,a.shortname as appName ,p.PRODTYPE,p.statusid as statusId,s.name as statusName,"
					+ "p.createdby as createdby, um.USERID as createdByName , pc.productname as productTypeName "
					+ "  from productmaster p inner join productcategorymaster pc on pc.id=p.PRODTYPE "
					+ "  inner join appmaster a  on p.appid=a.id inner join user_master um on p.createdby = um.id"
					+ "  inner join statusmaster s  on p.statusid=s.id  order by p.id desc";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("productName")
					.addScalar("description").addScalar("appId").addScalar("createdon").addScalar("appName")
					.addScalar("statusId").addScalar("statusName").addScalar("PRODTYPE").addScalar("createdby")
					.addScalar("createdByName").addScalar("productTypeName")
					.setResultTransformer(Transformers.aliasToBean(ProductBean.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return list;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductBean> getProductById(int id) {

		List<ProductBean> list = null;
		try {
			String sqlQuery = "  select p.id,p.productname as productName ,p.description as description ,p.createdon as createdon,"
					+ "  p.appid as appId,a.shortname as appName ,p.PRODTYPE,p.statusid as statusId,s.name as statusName, aw.remark, aw.userAction "
					+ "  ,pc.productname as productTypeName from productmaster p inner join productcategorymaster pc on pc.id=p.PRODTYPE "
					+ "  inner join appmaster a  on p.appid=a.id left join ADMINWORKFLOWREQUEST  aw on aw.activityrefno=p.id"
					+ "  inner join statusmaster s  on p.statusid=s.id"
					+ " inner join productcategorymaster pc on pc.id=p.PRODTYPE " + "  where p.id='" + id + "'";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("productName")
					.addScalar("description").addScalar("appId").addScalar("appName").addScalar("createdon")
					.addScalar("statusId").addScalar("statusName").addScalar("PRODTYPE").addScalar("remark")
					.addScalar("userAction").addScalar("productTypeName")
					.setResultTransformer(Transformers.aliasToBean(ProductBean.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return list;

	}

	@Override
	public List<ProductBean> getProductType() {
		List<ProductBean> list = null;
		try {
			String sqlQuery = "select PRODUCTNAME as productName, id from  productcategorymaster  WHERE statusid=3";

			list = getSession().createSQLQuery(sqlQuery).addScalar("productName").addScalar("id")
					.setResultTransformer(Transformers.aliasToBean(ProductBean.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return list;
	}

	@Override
	public List<FacilityStatusBean> getAllFacilityStatusById(int id) {
		try {
			String bankMasterQuery = "select t.*,a.SHORTNAME,s.name as STATUS ,aw.remark,aw.userAction from activitymaster t inner join appmaster a on "
					+ "a.id=t.appid inner join statusmaster s on s.id=t.statusid left join ADMINWORKFLOWREQUEST aw on aw.activityrefno=:id and aw.tablename='ACTIVITYMASTER'"
					+ " where t.id=:id ";
			List<FacilityStatusBean> list = getSession().createSQLQuery(bankMasterQuery).addScalar("id")
					.addScalar("ACTIVITYCODE").addScalar("LIMITS").addScalar("ENCRYPTIONTYPE").addScalar("DISPLAYNAME")
					.addScalar("STATUSID").addScalar("SHORTNAME").addScalar("STATUS").addScalar("FT_NFT")
					.addScalar("APPID").addScalar("CREATEDON").addScalar("CREATEDBY").addScalar("remark")
					.addScalar("userAction").setParameter("id", id)
					.setResultTransformer(Transformers.aliasToBean(FacilityStatusBean.class)).list();
			return list;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return null;
		}

	}

	@Override
	public List<FacilityStatusBean> getAllFacilityStatus() {
		try {
			String bankMasterQuery = "select t.*,a.SHORTNAME,s.name as STATUS, um.USERID as createdByName   "
					+ "from activitymaster t inner join appmaster a on a.id=t.appid inner join statusmaster s "
					+ "on s.id=t.statusid inner join user_master um on t.createdby = um.id  order by t.id desc";
			List<FacilityStatusBean> list = getSession().createSQLQuery(bankMasterQuery).addScalar("id")
					.addScalar("ACTIVITYCODE").addScalar("LIMITS").addScalar("ENCRYPTIONTYPE").addScalar("DISPLAYNAME")
					.addScalar("STATUSID").addScalar("SHORTNAME").addScalar("STATUS").addScalar("createdByName")
					.addScalar("FT_NFT").addScalar("APPID").addScalar("CREATEDON").addScalar("CREATEDBY")
					.setResultTransformer(Transformers.aliasToBean(FacilityStatusBean.class)).list();
			return list;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return null;
		}

	}

	@Override
	public List<MaskingRulesEntity> findMaskingRulesList() {
		List<MaskingRulesEntity> list = null;
		try {
			String maskingRules = "select m.id as id,m.fieldname as fieldname,m.rulenamedesc as rulenamedesc,m.appid as appid,m.statusid as statusid,m.maskall_yn as maskall_yn,m.maskchar as maskchar,m.createdon as createdon,"
					+ " m.maskfirstdigits as maskfirstdigits ,m.masklastdigits as masklastdigits,m.createdby as createdby, s.name as statusname, a.shortname as appname, um.USERID as createdByName  from maskingrules m inner join"
					+ " statusmaster s on m.statusid=s.id inner join appmaster a on m.appid=a.id inner join user_master um on m.createdby = um.id order by m.id desc";
			list = getSession().createSQLQuery(maskingRules).addScalar("fieldname").addScalar("rulenamedesc")
					.addScalar("id").addScalar("appid").addScalar("statusid").addScalar("maskall_yn")
					.addScalar("maskchar").addScalar("createdon").addScalar("maskfirstdigits")
					.addScalar("masklastdigits").addScalar("statusname").addScalar("appname").addScalar("createdby")
					.addScalar("createdByName").setResultTransformer(Transformers.aliasToBean(MaskingRulesEntity.class))
					.list();

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return null;
		}
		return list;
	}

	@Override
	public List<MaskingRulesEntity> getMaskingRulesId(int id) {
		List<MaskingRulesEntity> list = null;
		try {
			String maskingRules = "select m.id as id,m.fieldname as fieldname,m.rulenamedesc as rulenamedesc,m.appid as appid,m.statusid as statusid,"
					+ "m.maskall_yn as maskall_yn,m.maskchar as maskchar,m.createdon as createdon,"
					+ " m.maskfirstdigits as maskfirstdigits ,m.masklastdigits as masklastdigits,s.name as statusname, a.shortname as appname, aw.remark, aw.userAction "
					+ " from maskingrules m inner join "
					+ " statusmaster s on m.statusid=s.id inner join appmaster a on m.appid=a.id left join ADMINWORKFLOWREQUEST  aw on aw.activityrefno=m.id  and aw.tablename='MASKINGRULES' "
					+ " where m.id=:id";

			list = getSession().createSQLQuery(maskingRules).addScalar("id").addScalar("fieldname")
					.addScalar("rulenamedesc").addScalar("appid").addScalar("statusid").addScalar("maskall_yn")
					.addScalar("maskchar").addScalar("createdon").addScalar("maskfirstdigits")
					.addScalar("masklastdigits").addScalar("statusname").addScalar("appname").addScalar("remark")
					.addScalar("userAction").setParameter("id", id)
					.setResultTransformer(Transformers.aliasToBean(MaskingRulesEntity.class)).list();

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return null;
		}
		return list;
	}

	@Override
	public boolean saveProductDetails(Product product) {
		Session session = sessionFactory.getCurrentSession();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(product.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		int userStatus = product.getStatusId();
		int productId = 0;
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(product.getActivityName());

		try {
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				product.setStatusId(statusId);
			}

			productId = (Integer) session.save(product);

			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				// List<ProductBean> list= getProducts();
				ObjectMapper mapper = new ObjectMapper();
				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();

				adminData.setCreatedByUserId(product.getUser_ID());
				adminData.setCreatedByRoleId(product.getRole_ID());
				adminData.setPageId(product.getSubMenu_ID()); // set submenuId as pageid
				adminData.setCreatedBy(BigDecimal.valueOf(product.getCreatedby()));
				adminData.setContent(mapper.writeValueAsString(product));
				adminData.setActivityId(product.getSubMenu_ID()); // set submenuId as activityid
				adminData.setStatusId(BigDecimal.valueOf(statusId)); // 97- ADMIN_CHECKER_PENDIN
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6)); // 6- checker roleid
				adminData.setRemark(product.getRemark());
				adminData.setActivityName(product.getActivityName());
				adminData.setActivityRefNo(BigDecimal.valueOf(productId));
				adminData.setTableName("PRODUCTMASTER");
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);

				// Save data to admin work flow history
				adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(product.getSubMenu_ID(),
						BigDecimal.valueOf(productId), new BigDecimal(product.getCreatedby()), product.getRemark(),
						product.getRole_ID(), mapper.writeValueAsString(product));
			}

			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}

	@Override
	public ResponseMessageBean checkUpdateProduct(Product product) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		try {
			String sqlChannelNameExit = "SELECT count(*) FROM PRODUCTMASTER WHERE Lower(PRODUCTNAME) =:name AND  ID!=:id  and appid=:appId";
			List isChannelNameExit = getSession().createSQLQuery(sqlChannelNameExit).setParameter("id", product.getId())
					.setParameter("appId", product.getAppId())
					.setParameter("name", product.getProductName().toLowerCase()).list();

			if (!(isChannelNameExit.get(0).toString().equalsIgnoreCase("0"))) {
				rmb.setResponseCode("451");
				rmb.setResponseMessage("Product Name Already Exist For Same Channel");
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
	public ResponseMessageBean checkProduct(Product product) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		try {
			String sqlChannelNameExit = "SELECT count(*) FROM PRODUCTMASTER WHERE Lower(PRODUCTNAME) =:name and appid=:appId";
			List isChannelNameExit = getSession().createSQLQuery(sqlChannelNameExit)
					.setParameter("appId", product.getAppId())
					.setParameter("name", product.getProductName().toLowerCase()).list();

			if (!(isChannelNameExit.get(0).toString().equalsIgnoreCase("0"))) {
				rmb.setResponseCode("451");
				rmb.setResponseMessage("Product Already Exist For Same Channel");
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
	public List<UserAccountLeadsBean> getCustAccountDetailsById(int id) {
		List<UserAccountLeadsBean> list = null;
		try {
			String sqlQuery = "select *from USERACCOUNTLEADS where id=:id";
			list = getSession().createSQLQuery(sqlQuery).setParameter("id", id)
					.setResultTransformer(Transformers.aliasToBean(UserAccountLeadsBean.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;

	}

	@Override
	public List<UserAccountLeadsBean> getCustAllAccountDetails() {
		List<UserAccountLeadsBean> list = null;
		try {
			String sqlQuery = "select *from USERACCOUNTLEADS ";
			list = getSession().createSQLQuery(sqlQuery)
					.setResultTransformer(Transformers.aliasToBean(UserAccountLeadsBean.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public List<LanguageJson> getDistinctLanguageJsonCode() {

		String sqlQuery = "select distinct languagecode, languagecodedesc from  languagejson_new order by LANGUAGECODEDESC";

		List<LanguageJson> list = getSession().createSQLQuery(sqlQuery).addScalar("languagecode")
				.addScalar("languagecodedesc").setResultTransformer(Transformers.aliasToBean(LanguageJson.class))
				.list();

		for (LanguageJson cm : list) {
			try {

				String decLangDescText = EncryptorDecryptor.decryptDataForLangJson(cm.getLanguagecodedesc());
				String szUT8Desc = new String(decLangDescText.getBytes(), "UTF-8").replace("\ufffd?", "")
						.replace("\ufffd", "");
				cm.setLanguagecodedesc(szUT8Desc);

			} catch (Exception e) {
				LOGGER.info("Exception:", e);
			}
		}

		return list;
	}

	@Override
	public ResponseMessageBean isEnglsihTextExist(LanguageJson languagejson) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		try {
			String sqlNameExit = "SELECT count(*) FROM LANGUAGEJSON_NEW WHERE Lower(ENGLISHTEXT) =:englishText AND LANGUAGECODE =:langCode";

			List isNameExit = getSession().createSQLQuery(sqlNameExit)
					.setParameter("langCode", languagejson.getLanguagecode())
					.setParameter("englishText", languagejson.getEnglishtext().toLowerCase()).list();

			if (!(isNameExit.get(0).toString().equalsIgnoreCase("0"))) {
				rmb.setResponseCode("451");
				rmb.setResponseMessage("English Text Already Exist For Same Language Code");
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
	public ResponseMessageBean isUpdateEnglsihTextExist(LanguageJson languagejson) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		try {
			String sqlNameExit = "SELECT count(*) FROM LANGUAGEJSON_NEW WHERE Lower(ENGLISHTEXT) =:englishText AND id !=:id AND LANGUAGECODE =:langCode";

			List isNameExit = getSession().createSQLQuery(sqlNameExit).setParameter("id", languagejson.getId())
					.setParameter("langCode", languagejson.getLanguagecode())
					.setParameter("englishText", languagejson.getEnglishtext().toLowerCase()).list();

			if (!(isNameExit.get(0).toString().equalsIgnoreCase("0"))) {
				rmb.setResponseCode("451");
				rmb.setResponseMessage("English Text Already Exist For Same Language Code");
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
	public ResponseMessageBean isFacilityStatusExist(ActivityMaster activitymaster) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		try {
			String sqlNameExit = "SELECT count(*) FROM ACTIVITYMASTER WHERE Lower(DISPLAYNAME) =:displayName AND APPID=:appId";

			String sqlActiNameExit = "SELECT count(*) FROM ACTIVITYMASTER WHERE Lower(ACTIVITYCODE) =:activityCode AND APPID=:appId ";

			List isNameExit = getSession().createSQLQuery(sqlNameExit).setParameter("appId", activitymaster.getAppid())
					.setParameter("displayName", activitymaster.getDisplayname().toLowerCase()).list();

			List isActiNameExit = getSession().createSQLQuery(sqlActiNameExit)
					.setParameter("appId", activitymaster.getAppid())
					.setParameter("activityCode", activitymaster.getActivitycode().toLowerCase()).list();

			if (!(isNameExit.get(0).toString().equalsIgnoreCase("0"))) {
				rmb.setResponseCode("500");
				rmb.setResponseMessage("Display Name Already Exist For Same Channel");
			} else if (!(isActiNameExit.get(0).toString().equalsIgnoreCase("0"))) {
				rmb.setResponseCode("500");
				rmb.setResponseMessage("Activity Code Already Exist For Same Channel");
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
	public ResponseMessageBean isUpdateFacilityStatusExist(ActivityMaster activitymaster) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		try {
			String sqlNameExit = "SELECT count(*) FROM ACTIVITYMASTER WHERE Lower(DISPLAYNAME) =:displayName AND id !=:id AND APPID=:appId";

			List isNameExit = getSession().createSQLQuery(sqlNameExit).setParameter("id", activitymaster.getId())
					.setParameter("appId", activitymaster.getAppid())
					.setParameter("displayName", activitymaster.getDisplayname().toLowerCase()).list();

			if (!(isNameExit.get(0).toString().equalsIgnoreCase("0"))) {
				rmb.setResponseCode("500");
				rmb.setResponseMessage("Display Name Already Exist For Same Channel");
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
	public List<LanguageJson> getLanguageJsonByLangCode(LanguageJson languagejson) {
		List<LanguageJson> list = null;
		try {
			String sqlQuery = "select t.id as id, englishtext as englishtext ,t.LANGUAGECODE as languagecode,t.LANGUAGETEXT as languagetext,"
					+ "	t.CREATEDON as createdon,t.LANGUAGECODEDESC as languagecodedesc,s.name as statusName,t.statusId ,"
					+ "			 t.CREATEDBY as createdby, um.USERID as createdByName from LANGUAGEJSON_NEW t inner join statusmaster s on  s.id=t.statusId  inner join user_master um "
					+ " on t.CREATEDBY = um.id  where t.LANGUAGECODE=:langCode order by t.id desc ";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("englishtext")
					.addScalar("languagecode").addScalar("statusName").addScalar("statusId")
					.addScalar("languagetext", StandardBasicTypes.STRING).addScalar("createdon")
					.addScalar("languagecodedesc").addScalar("createdby").addScalar("createdByName")
					.setParameter("langCode", languagejson.getLanguagecode())
					.setResultTransformer(Transformers.aliasToBean(LanguageJson.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		for (LanguageJson cm : list) {
			String szUT8 = null;
			try {
				String decLangText = EncryptorDecryptor.decryptDataForLangJson(cm.getLanguagetext());
				szUT8 = new String(decLangText.getBytes(), "UTF-8").replace("\ufffd?", "").replace("\ufffd", "");
				System.out.println(szUT8);
				cm.setLanguagetext(szUT8);
				LOGGER.info(szUT8);
				System.out.println("Id is->" + cm.id);

			} catch (Exception e) {
				LOGGER.info("************Error in english text-->" + cm.getEnglishtext());
				LOGGER.info("************Enc of rnglish text-->" + szUT8);
				LOGGER.info("Exception:", e);

			}

		}

		return list;

	}

	@Override
	public List<LanguageJson> getLanguageJsonByLangText(LanguageJson languagejson) {

		String sqlQuery = "select t.id as id, englishtext as englishtext ,t.LANGUAGECODE as languagecode,t.LANGUAGETEXT as languagetext,"
				+ "	t.CREATEDON as createdon,t.LANGUAGECODEDESC as languagecodedesc,s.name as statusName,t.statusId ,"
				+ "			 t.CREATEDBY as createdby, um.USERID as createdByName from LANGUAGEJSON_NEW t inner join statusmaster s on  s.id=t.statusId  inner join user_master um "
				+ " on t.CREATEDBY = um.id  where t.englishtext=:engText";

		List<LanguageJson> list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("englishtext")
				.addScalar("languagecode").addScalar("statusName").addScalar("statusId")
				.addScalar("languagetext", StandardBasicTypes.STRING).addScalar("createdon")
				.addScalar("languagecodedesc").addScalar("createdby").addScalar("createdByName")
				.setParameter("engText", languagejson.getEnglishtext())
				.setResultTransformer(Transformers.aliasToBean(LanguageJson.class)).list();

		for (LanguageJson cm : list) {
			try {

				String decLangText = EncryptorDecryptor.decryptDataForLangJson(cm.getLanguagetext());
				String szUT8 = new String(decLangText.getBytes(), "UTF-8").replace("\ufffd?", "").replace("\ufffd", "");
				System.out.println(szUT8);
				cm.setLanguagetext(szUT8);

				String decLangDescText = EncryptorDecryptor.decryptDataForLangJson(cm.getLanguagecodedesc());
				String szUT8Desc = new String(decLangDescText.getBytes(), "UTF-8").replace("\ufffd?", "")
						.replace("\ufffd", "");
				System.out.println(szUT8);
				cm.setLanguagecodedesc(szUT8Desc);

				/*
				 * String decLangText =
				 * EncryptorDecryptor.decryptDataForLangJson(cm.getLanguagetext());
				 * EncryptDeryptUtility encDecObj = new EncryptDeryptUtility(); String encNonand
				 * =encDecObj.encryptNonAndroid(decLangText, "@MrN$2Qi8R");
				 */
				// System.out.println("Non And Enc:"+encNonand);
				/*
				 * String decNonAnd=encDecObj.decryptNonAndroid(encNonand, "laN@Jv8k#Omnip$b") ;
				 * System.out.println("dec dta:"+decNonAnd);
				 */
				// cm.setLanguagetext(encNonand);

			} catch (Exception e) {
				LOGGER.info("Exception:", e);
			}

		}
		return list;

	}

	@Override
	public ResponseMessageBean checkConfigKeyExist(ConfigMasterEntity configMasterEntity) {

		ResponseMessageBean rmb = new ResponseMessageBean();
		try {
			String sqlNameExist = " SELECT count(*) FROM CONFIGURATIONMASTER WHERE Lower(CONFIG_KEY) =:configKey ";

			List nameExit = getSession().createSQLQuery(sqlNameExist)
					.setParameter("configKey", configMasterEntity.getConfigKey().toLowerCase()).list();

			if (!(nameExit.get(0).toString().equalsIgnoreCase("0"))) {
				rmb.setResponseCode("500");
				rmb.setResponseMessage("Config Key Already Exist");
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
	public ResponseMessageBean checkUpdateConfigKeyExist(ConfigMasterEntity configMasterEntity) {

		ResponseMessageBean rmb = new ResponseMessageBean();
		try {
			String sqlNameExist = " SELECT count(*) FROM CONFIGURATIONMASTER WHERE Lower(CONFIG_KEY) =:configKey AND ID !=:id";

			List nameExit = getSession().createSQLQuery(sqlNameExist).setParameter("id", configMasterEntity.getId())
					.setParameter("configKey", configMasterEntity.getConfigKey().toLowerCase()).list();

			if (!(nameExit.get(0).toString().equalsIgnoreCase("0"))) {
				rmb.setResponseCode("500");
				rmb.setResponseMessage("Config Key Already Exist");
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
	public boolean updateLanguageJsonList(List<LanguageJson> languagejson) {
		Session session = sessionFactory.getCurrentSession();

		try {
			for (LanguageJson langJson : languagejson) {

				if (langJson.getId() != null) {
					String decReq = EncryptDeryptUtility.decryptNonAndroid(langJson.getLanguagetext(), "@MrN$2Qi8R");
					String encLanText = EncryptorDecryptor.encryptDataForLangJson(decReq);
					langJson.setLanguagetext(encLanText);

					String decLangCode = EncryptDeryptUtility.decryptNonAndroid(langJson.getLanguagecodedesc(),
							"@MrN$2Qi8R");
					String encLangCode = EncryptorDecryptor.encryptDataForLangJson(decLangCode);
					langJson.setLanguagecodedesc(encLangCode);

					session.update(langJson);
				} else {
					String decReq = EncryptDeryptUtility.decryptNonAndroid(langJson.getLanguagetext(), "@MrN$2Qi8R");
					String encLanText = EncryptorDecryptor.encryptDataForLangJson(decReq);
					langJson.setLanguagetext(encLanText);

					String decLangCode = EncryptDeryptUtility.decryptNonAndroid(langJson.getLanguagecodedesc(),
							"@MrN$2Qi8R");
					String encLangCode = EncryptorDecryptor.encryptDataForLangJson(decLangCode);
					langJson.setLanguagecodedesc(encLangCode);

					langJson.setCreatedon(new Date());
					session.save(langJson);
				}

			}

			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}

	@Override
	public List<CertificateConfigEntity> getCertificateConfigMaster() {
		List<CertificateConfigEntity> list = null;
		try {
			String sqlQuery = "	select cc.id as id, cc.CERTIFICATETYPE as certificatetype, cc.CONFIGVALUE as configvalue, cc.DESCRIPTION as description, cc.CREATEDBY as createdby,"
					+ "cc.CREATEDON as createdon, cc.STATUSID as statusid,cc.APPID as appid, cc.SRNO as srno ,s.name as statusname,a.shortname as appname, um.USERID as createdbyname "
					+ " from CERTIFICATE_CONFIGS cc inner join statusmaster s on s.Id=cc.statusid  "
					+ "inner join appmaster a on a.id=cc.appid inner join user_master um on cc.createdby = um.id order by cc.id desc";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("certificatetype")
					.addScalar("configvalue").addScalar("description").addScalar("createdby").addScalar("createdon")
					.addScalar("statusname").addScalar("statusid").addScalar("appid").addScalar("srno")
					.addScalar("appname").addScalar("createdbyname")
					.setResultTransformer(Transformers.aliasToBean(CertificateConfigEntity.class)).list();

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public List<CertificateConfigEntity> getCertificateConfigById(int id) {
		List<CertificateConfigEntity> list = null;
		try {
			String sqlQuery = "	select cc.id as id, cc.CERTIFICATETYPE as certificatetype, cc.CONFIGVALUE as configvalue, cc.DESCRIPTION as description, cc.CREATEDBY as createdby,"
					+ "cc.CREATEDON as createdon, cc.STATUSID as statusid,cc.APPID as appid, cc.SRNO as srno ,s.name as statusname,a.shortname as appname, um.USERID as createdbyname, "
					+ " aw.remark,aw.userAction from CERTIFICATE_CONFIGS cc inner join statusmaster s on s.Id=cc.statusid  "
					+ "inner join appmaster a on a.id=cc.appid inner join user_master um on cc.createdby = um.id "
					+ "left join ADMINWORKFLOWREQUEST aw on aw.activityrefno=cc.id and aw.tablename='CERTIFICATE_CONFIGS' where cc.id=:id ";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("certificatetype")
					.addScalar("configvalue").addScalar("description").addScalar("createdby").addScalar("createdon")
					.addScalar("statusname").addScalar("statusid").addScalar("appid").addScalar("srno")
					.addScalar("appname").addScalar("createdbyname").addScalar("remark").addScalar("userAction")
					.setParameter("id", id)
					.setResultTransformer(Transformers.aliasToBean(CertificateConfigEntity.class)).list();

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public boolean addCertificateConfigMaster(CertificateConfigEntity categoryMasterData) {
		Session session = sessionFactory.getCurrentSession();
		int userStatus = categoryMasterData.getStatusid().intValue();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(categoryMasterData.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(categoryMasterData.getActivityName());

		try {
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				categoryMasterData.setStatusid(BigDecimal.valueOf(statusId)); // 97- ADMIN_CHECKER_PENDIN
			}
			categoryMasterData.setCreatedon(new Date());
			BigDecimal certConfigId = (BigDecimal) session.save(categoryMasterData);

			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

				ObjectMapper mapper = new ObjectMapper();

				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
				adminData.setAppId(categoryMasterData.getAppid());
				adminData.setCreatedByUserId(categoryMasterData.getUser_ID());
				adminData.setCreatedByRoleId(categoryMasterData.getRole_ID());
				adminData.setPageId(categoryMasterData.getSubMenu_ID()); // set submenuId as pageid
				adminData.setCreatedBy(categoryMasterData.getCreatedby());
				adminData.setContent(mapper.writeValueAsString(categoryMasterData));
				adminData.setActivityId(categoryMasterData.getSubMenu_ID()); // set submenuId as activityid
				adminData.setStatusId(BigDecimal.valueOf(statusId)); // 97- ADMIN_CHECKER_PENDIN
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6)); // 6- checker roleid
				adminData.setRemark(categoryMasterData.getRemark());
				adminData.setActivityName(categoryMasterData.getActivityName());
				adminData.setActivityRefNo(certConfigId);
				adminData.setTableName("CERTIFICATE_CONFIGS");
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);

				// Save data to admin work flow history
				adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(categoryMasterData.getSubMenu_ID(), certConfigId,
						categoryMasterData.getCreatedby(), categoryMasterData.getRemark(),
						categoryMasterData.getRole_ID(), mapper.writeValueAsString(categoryMasterData));
			}

			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}

	@Override
	public boolean updateCertificateConfigMaster(CertificateConfigEntity categoryMasterData) {
		Session session = sessionFactory.getCurrentSession();
		int userStatus = categoryMasterData.getStatusid().intValue();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(categoryMasterData.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(categoryMasterData.getActivityName());

		try {
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				categoryMasterData.setStatusid(BigDecimal.valueOf(statusId));
			}

			session.update(categoryMasterData);

			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

				ObjectMapper mapper = new ObjectMapper();

				List<AdminWorkFlowRequestEntity> AdminDataList = adminWorkFlowReqUtility
						.getAdminWorkFlowDataByActivityRefNo(categoryMasterData.getId().intValue(),
								categoryMasterData.getSubMenu_ID());

				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
				adminData.setAppId(categoryMasterData.getAppid());
				adminData.setCreatedOn(categoryMasterData.getCreatedon());
				adminData.setCreatedByUserId(categoryMasterData.getUser_ID());
				adminData.setCreatedByRoleId(categoryMasterData.getRole_ID());
				adminData.setPageId(categoryMasterData.getSubMenu_ID()); // set submenuId as pageid
				adminData.setCreatedBy(categoryMasterData.getCreatedby());
				adminData.setContent(mapper.writeValueAsString(categoryMasterData));
				adminData.setActivityId(categoryMasterData.getSubMenu_ID()); // set submenuId as activityid
				adminData.setStatusId(BigDecimal.valueOf(statusId)); // 97- ADMIN_CHECKER_PENDIN
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6)); // 6- checker roleid
				adminData.setRemark(categoryMasterData.getRemark());
				adminData.setActivityName(categoryMasterData.getActivityName());
				adminData.setActivityRefNo(categoryMasterData.getId());
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminData.setTableName("CERTIFICATE_CONFIGS");

				if (ObjectUtils.isEmpty(AdminDataList)) {
					adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);
				} else if (!ObjectUtils.isEmpty(AdminDataList)) {
					adminData.setId(BigDecimal.valueOf(AdminDataList.get(0).getId().intValue()));
					adminWorkFlowReqUtility.updateAdminWorkFlowRequest(adminData);

					// Save data to admin work flow history
					adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(categoryMasterData.getSubMenu_ID(),
							categoryMasterData.getId(), categoryMasterData.getCreatedby(),
							categoryMasterData.getRemark(), categoryMasterData.getRole_ID(),
							mapper.writeValueAsString(categoryMasterData));
				}

			} else {

				// if record is present in admin work flow then update status
				adminWorkFlowReqUtility.getCheckerDataByctivityRefId(categoryMasterData.getId().toBigInteger(),
						BigInteger.valueOf(userStatus), categoryMasterData.getSubMenu_ID());
			}
			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}

	@Override
	public ResponseMessageBean checkConfigKeyExist(CertificateConfigEntity configMasterEntity) {

		ResponseMessageBean rmb = new ResponseMessageBean();
		try {
			String sqlNameExist = " SELECT count(*) FROM CERTIFICATE_CONFIGS WHERE Lower(configvalue) =:configKey ";

			List nameExit = getSession().createSQLQuery(sqlNameExist)
					.setParameter("configKey", configMasterEntity.getConfigvalue().toLowerCase()).list();

			if (!(nameExit.get(0).toString().equalsIgnoreCase("0"))) {
				rmb.setResponseCode("500");
				rmb.setResponseMessage("Config Key Already Exist");
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
	public boolean updateAccountSchemeMaster(AccountSchemeMasterEntity categoryMasterData) {
		Session session = sessionFactory.getCurrentSession();
		int userStatus = categoryMasterData.getStatusid().intValue();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(categoryMasterData.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(categoryMasterData.getActivityName());

		try {
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				categoryMasterData.setStatusid(BigDecimal.valueOf(statusId));
			}

			session.update(categoryMasterData);

			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

				ObjectMapper mapper = new ObjectMapper();

				List<AdminWorkFlowRequestEntity> AdminDataList = adminWorkFlowReqUtility
						.getAdminWorkFlowDataByActivityRefNo(categoryMasterData.getId().intValue(),
								categoryMasterData.getSubMenu_ID());

				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
				adminData.setCreatedOn(categoryMasterData.getCreatedon());
				adminData.setCreatedByUserId(categoryMasterData.getUser_ID());
				adminData.setCreatedByRoleId(categoryMasterData.getRole_ID());
				adminData.setPageId(categoryMasterData.getSubMenu_ID()); // set submenuId as pageid
				adminData.setCreatedBy(categoryMasterData.getCreatedby());
				adminData.setContent(mapper.writeValueAsString(categoryMasterData));
				adminData.setActivityId(categoryMasterData.getSubMenu_ID()); // set submenuId as activityid
				adminData.setStatusId(BigDecimal.valueOf(statusId)); // 97- ADMIN_CHECKER_PENDIN
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6)); // 6- checker roleid
				adminData.setRemark(categoryMasterData.getRemark());
				adminData.setActivityName(categoryMasterData.getActivityName());
				adminData.setActivityRefNo(categoryMasterData.getId());
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminData.setTableName("ACCOUNTSCHEMEMASTER");

				if (ObjectUtils.isEmpty(AdminDataList)) {
					adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);
				} else if (!ObjectUtils.isEmpty(AdminDataList)) {
					adminData.setId(BigDecimal.valueOf(AdminDataList.get(0).getId().intValue()));
					adminWorkFlowReqUtility.updateAdminWorkFlowRequest(adminData);

					// Save data to admin work flow history
					adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(categoryMasterData.getSubMenu_ID(),
							categoryMasterData.getId(), categoryMasterData.getCreatedby(),
							categoryMasterData.getRemark(), categoryMasterData.getRole_ID(),
							mapper.writeValueAsString(categoryMasterData));
				}

			} else {

				// if record is present in admin work flow then update status
				adminWorkFlowReqUtility.getCheckerDataByctivityRefId(categoryMasterData.getId().toBigInteger(),
						BigInteger.valueOf(userStatus), categoryMasterData.getSubMenu_ID());
			}
			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}

	@Override
	public List<AccountSchemeMasterEntity> getAccountSchemeMasterById(int id) {
		List<AccountSchemeMasterEntity> list = null;
		try {
			String sqlQuery = "	select cc.id as id ,cc.schemetype as schemetype,cc.schemecode as schemecode,cc.schemedescription as schemedescription,cc.schememapping as schememapping ,cc.CREATEDBY as createdby,"
					+ "cc.CREATEDON as createdon, cc.STATUSID as statusid, s.name as statusname,cc.updatedon as updatedon, cc.updatedby as updatedby, um.USERID as createdbyname, "
					+ " aw.remark,aw.userAction from ACCOUNTSCHEMEMASTER cc inner join statusmaster s on s.Id=cc.statusid  "
					+ "  inner join user_master um on cc.createdby = um.id "
					+ "left join ADMINWORKFLOWREQUEST aw on aw.activityrefno=cc.id and aw.tablename='ACCOUNTSCHEMEMASTER' where cc.id=:id ";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("schemetype").addScalar("schemecode")
					.addScalar("schememapping").addScalar("schemedescription").addScalar("createdby")
					.addScalar("createdon").addScalar("updatedon").addScalar("updatedby").addScalar("statusname")
					.addScalar("statusid").addScalar("createdbyname").addScalar("remark").addScalar("userAction")
					.setParameter("id", id)
					.setResultTransformer(Transformers.aliasToBean(AccountSchemeMasterEntity.class)).list();

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public List<AccountSchemeMasterEntity> getAccountSchemeMaster() {
		List<AccountSchemeMasterEntity> list = null;
		try {
			String sqlQuery = "select cc.id as id ,cc.schemetype as schemetype,cc.schemecode as schemecode ,cc.schemedescription as schemedescription,cc.schememapping as schememapping,cc.CREATEDBY as createdby,"
					+ "cc.CREATEDON as createdon, cc.STATUSID as statusid, s.name as statusname,cc.updatedon as updatedon, cc.updatedby as updatedby, um.USERID as createdbyname "
					+ " from ACCOUNTSCHEMEMASTER cc inner join statusmaster s on s.Id=cc.statusid  "
					+ " inner join user_master um on cc.createdby = um.id order by cc.id desc";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("schemetype").addScalar("schemecode")
					.addScalar("schemedescription").addScalar("schememapping").addScalar("createdby")
					.addScalar("createdon").addScalar("updatedby").addScalar("updatedon").addScalar("statusname")
					.addScalar("statusid").addScalar("createdbyname")
					.setResultTransformer(Transformers.aliasToBean(AccountSchemeMasterEntity.class)).list();

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public boolean addAccountSchemeMaster(AccountSchemeMasterEntity categoryMasterData) {
		Session session = sessionFactory.getCurrentSession();
		int userStatus = categoryMasterData.getStatusid().intValue();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(categoryMasterData.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(categoryMasterData.getActivityName());

		try {
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				categoryMasterData.setStatusid(BigDecimal.valueOf(statusId)); // 97- ADMIN_CHECKER_PENDIN
			}
			categoryMasterData.setCreatedon(new Date());
			BigDecimal certConfigId = (BigDecimal) session.save(categoryMasterData);

			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

				ObjectMapper mapper = new ObjectMapper();

				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
				adminData.setCreatedByUserId(categoryMasterData.getUser_ID());
				adminData.setCreatedByRoleId(categoryMasterData.getRole_ID());
				adminData.setPageId(categoryMasterData.getSubMenu_ID()); // set submenuId as pageid
				adminData.setCreatedBy(categoryMasterData.getCreatedby());
				adminData.setContent(mapper.writeValueAsString(categoryMasterData));
				adminData.setActivityId(categoryMasterData.getSubMenu_ID()); // set submenuId as activityid
				adminData.setStatusId(BigDecimal.valueOf(statusId)); // 97- ADMIN_CHECKER_PENDIN
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6)); // 6- checker roleid
				adminData.setRemark(categoryMasterData.getRemark());
				adminData.setActivityName(categoryMasterData.getActivityName());
				adminData.setActivityRefNo(certConfigId);
				adminData.setTableName("ACCOUNTSCHEMEMASTER");
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);

				// Save data to admin work flow history
				adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(categoryMasterData.getSubMenu_ID(), certConfigId,
						categoryMasterData.getCreatedby(), categoryMasterData.getRemark(),
						categoryMasterData.getRole_ID(), mapper.writeValueAsString(categoryMasterData));
			}

			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}

	@Override
	public boolean addSchedulatTransMaster(SchedularTransMasterEntity schedularData) {
		Session session = sessionFactory.getCurrentSession();
		try {

			schedularData.setCREATEDON(new Date());
			session.save(schedularData);
			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}

	@Override
	public boolean updateSchedulatTransMaster(SchedularTransMasterEntity schedularData) {
		Session session = sessionFactory.getCurrentSession();
		try {

			schedularData.setUPDATEDON(new Date());
			session.save(schedularData);
			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}

	@Override
	public List<SchedularTransMasterEntity> getSchedulatTransMaster() {
		List<SchedularTransMasterEntity> list = null;
		try {
			String sqlQuery = "	select sm.ID as id,sm.CUSTOMERID as CUSTOMERID,sm.FROMACCNUMBER as FROMACCNUMBER,sm.TOACCNUMBER as TOACCNUMBER,"
					+ "	sm.PAYMENTSTARTDATE as PAYMENTSTARTDATE, sm.PAYMENTENDDATE as PAYMENTENDDATE,sm.PAYMENTFREQUENCY as PAYMENTFREQUENCY,"
					+ "	sm.PAYMENTFREQTYPE as PAYMENTFREQTYPE,sm.EMIAMOUNT as EMIAMOUNT,sm.NUMOFINSTALLMENT as NUMOFINSTALLMENT,sm.SITYPE as SITYPE,"
					+ "	sm.STATUSID as STATUSID,sm.CREATEDBY as CREATEDBY,sm.CREATEDON as CREATEDON, sm.UPDATEDBY as UPDATEDBY,sm.UPDATEDON as UPDATEDON,"
					+ "	sm.RRN as RRN, s.name as statusname, um.USERID as createdbyname from SCHEDULARTRANSMASTER sm inner join statusmaster s on s.Id=sm.statusid  "
					+ " inner join user_master um on sm.createdby = um.id order by sm.ID desc";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("CUSTOMERID", StandardBasicTypes.LONG)
					.addScalar("FROMACCNUMBER", StandardBasicTypes.STRING)
					.addScalar("TOACCNUMBER", StandardBasicTypes.STRING)
					.addScalar("PAYMENTSTARTDATE", StandardBasicTypes.DATE)
					.addScalar("PAYMENTENDDATE", StandardBasicTypes.DATE)
					.addScalar("PAYMENTFREQUENCY", StandardBasicTypes.STRING)
					.addScalar("PAYMENTFREQTYPE", StandardBasicTypes.STRING)
					.addScalar("EMIAMOUNT", StandardBasicTypes.LONG)
					.addScalar("NUMOFINSTALLMENT", StandardBasicTypes.LONG)
					.addScalar("SITYPE", StandardBasicTypes.STRING).addScalar("STATUSID", StandardBasicTypes.INTEGER)
					.addScalar("CREATEDBY", StandardBasicTypes.INTEGER).addScalar("CREATEDON", StandardBasicTypes.DATE)
					.addScalar("UPDATEDBY", StandardBasicTypes.INTEGER).addScalar("UPDATEDON", StandardBasicTypes.DATE)
					.addScalar("RRN", StandardBasicTypes.STRING).addScalar("statusname", StandardBasicTypes.STRING)
					.setResultTransformer(Transformers.aliasToBean(SchedularTransMasterEntity.class)).list();

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public List<SchedularTransMasterEntity> getSchedulatTransMasterById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SchedularTransMasterEntity> getScheduleTransactionDetails() {
		List<SchedularTransMasterEntity> list = null;
		try {
			String sqlQuery = "	select sm.ID as id,sm.CUSTOMERID as CUSTOMERID,sm.FROMACCNUMBER as FROMACCNUMBER,sm.TOACCNUMBER as TOACCNUMBER,"
					+ "	sm.PAYMENTSTARTDATE as PAYMENTSTARTDATE, sm.PAYMENTENDDATE as PAYMENTENDDATE,sm.PAYMENTFREQUENCY as PAYMENTFREQUENCY,"
					+ "	sm.PAYMENTFREQTYPE as PAYMENTFREQTYPE,sm.EMIAMOUNT as EMIAMOUNT,sm.NUMOFINSTALLMENT as NUMOFINSTALLMENT,sm.SITYPE as SITYPE,"
					+ "	sm.STATUSID as STATUSID,sm.CREATEDBY as CREATEDBY,sm.CREATEDON as CREATEDON, sm.UPDATEDBY as UPDATEDBY,sm.UPDATEDON as UPDATEDON,"
					+ "	sm.RRN as RRN, s.name as statusname, um.USERID as createdbyname from SCHEDULARTRANSMASTER sm inner join statusmaster s on s.Id=sm.statusid  "
					+ " inner join user_master um on sm.createdby = um.id order by sm.id desc";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("CUSTOMERID", StandardBasicTypes.LONG)
					.addScalar("FROMACCNUMBER", StandardBasicTypes.STRING)
					.addScalar("TOACCNUMBER", StandardBasicTypes.STRING)
					.addScalar("PAYMENTSTARTDATE", StandardBasicTypes.DATE)
					.addScalar("PAYMENTENDDATE", StandardBasicTypes.DATE)
					.addScalar("PAYMENTFREQUENCY", StandardBasicTypes.STRING)
					.addScalar("PAYMENTFREQTYPE", StandardBasicTypes.STRING)
					.addScalar("EMIAMOUNT", StandardBasicTypes.LONG)
					.addScalar("NUMOFINSTALLMENT", StandardBasicTypes.LONG)
					.addScalar("SITYPE", StandardBasicTypes.STRING).addScalar("STATUSID", StandardBasicTypes.INTEGER)
					.addScalar("CREATEDBY", StandardBasicTypes.INTEGER).addScalar("CREATEDON", StandardBasicTypes.DATE)
					.addScalar("UPDATEDBY", StandardBasicTypes.INTEGER).addScalar("UPDATEDON", StandardBasicTypes.DATE)
					.addScalar("RRN", StandardBasicTypes.STRING).addScalar("statusname", StandardBasicTypes.STRING)
					.setResultTransformer(Transformers.aliasToBean(SchedularTransMasterEntity.class)).list();

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public List<AppMasterEntity> getAppListForLimitMaster() {
		List<AppMasterEntity> list = null;
		try {
			String sqlQuery = "select am.id, am.shortname as shortName,am.createdby as createdby, um.USERID as createdByName from  appmaster am inner join user_master um on am.createdby = um.id where am.statusid=3 and am.id in (0,2,5)";
			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("shortName").addScalar("createdby")
					.addScalar("createdByName").setResultTransformer(Transformers.aliasToBean(AppMasterEntity.class))
					.list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return list;
	}
	
}
