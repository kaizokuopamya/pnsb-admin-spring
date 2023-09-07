
package com.itl.pns.corp.dao.impl;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.jdbc.ReturningWork;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.util.ObjectUtils;

import com.itl.pns.bean.CorpCompanyCorpUserDupBean;
import com.itl.pns.bean.CorpCompanyDataBean;
import com.itl.pns.bean.CorpDataBean;
import com.itl.pns.bean.CorpMenuAccMasterBean;
import com.itl.pns.bean.CorpUserAccMapBean;
import com.itl.pns.bean.CorpUserBean;
import com.itl.pns.bean.CorpUserEntityBean;
import com.itl.pns.bean.CorpUserMenuMapBean;
import com.itl.pns.bean.CorporateResponse;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.bean.VerifyCifPara;
import com.itl.pns.constant.ApplicationConstants;
import com.itl.pns.corp.dao.OfflineCorpUserDao;
import com.itl.pns.corp.entity.CorpAccMapEntity;
import com.itl.pns.corp.entity.CorpAccMapEntityTmp;
import com.itl.pns.corp.entity.CorpCompDataMasterEntity;
import com.itl.pns.corp.entity.CorpCompanyMasterDupEntity;
import com.itl.pns.corp.entity.CorpCompanyMasterEntity;
import com.itl.pns.corp.entity.CorpMenuMapEntity;
import com.itl.pns.corp.entity.CorpUserAccMapEntity;
import com.itl.pns.corp.entity.CorpUserAccMapEntityTmp;
import com.itl.pns.corp.entity.CorpUserDupEntity;
import com.itl.pns.corp.entity.CorpUserEntity;
import com.itl.pns.corp.entity.CorpUserEntityTmp;
import com.itl.pns.corp.entity.CorpUserMenuMapEntity;
import com.itl.pns.corp.entity.CorpUserMenuMapEntityTmp;
import com.itl.pns.corp.repository.CorpAccMapRepository;
import com.itl.pns.corp.repository.CorpCompanyMasterDupRepository;
import com.itl.pns.corp.repository.CorpCompanyMasterRepo;
import com.itl.pns.corp.repository.CorpMenuMapRepository;
import com.itl.pns.corp.repository.CorpUserRepository;
import com.itl.pns.corp.repository.CorpUsersAccMapRepository;
import com.itl.pns.corp.repository.CorpUsersMenuMapRepository;
import com.itl.pns.entity.CorpRoles;
import com.itl.pns.repository.CorpRolesRepository;
import com.itl.pns.service.NotificationService;
import com.itl.pns.service.impl.RestServiceCall;
import com.itl.pns.util.AdminWorkFlowReqUtility;
import com.itl.pns.util.EmailUtil;
import com.itl.pns.util.EncryptorDecryptor;
import com.itl.pns.util.PdfGenerator;
import com.itl.pns.util.RandomNumberGenerator;

@Transactional
@Repository
public class OfflineCorpUserDaoImpl implements OfflineCorpUserDao {

	private static final Logger logger = Logger.getLogger(OfflineCorpUserDaoImpl.class);

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	@Autowired
	private AdminWorkFlowReqUtility adminWorkFlowReqUtility;

	@Autowired
	private EmailUtil emailUtil;

	@Autowired
	private RestServiceCall restServiceCall;

	@Autowired
	private PdfGenerator pdfGenerator;

	@Autowired
	private CorpCompanyMasterRepo corpCompanyMasterRepo;

	@Autowired
	private CorpMenuMapRepository corpMenuMapRepository;

	@Autowired
	private CorpAccMapRepository corpAccMapRepository;

	@Autowired
	private CorpUserRepository corpUserRepository;

	@Autowired
	private CorpUsersMenuMapRepository corpUsersMenuMapRepository;

	@Autowired
	private CorpUsersAccMapRepository corpUsersAccMapRepository;

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private CorpRolesRepository corpRolesRepository;

	@Autowired
	private CorpCompanyMasterDupRepository corpCompanyMasterDupRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Value("${PDF_FILE_PATH}")
	private String pdfFilePath;

	@Value("${TIME_RETRIES}")
	private int timeRetries;

	@Value("${MAX_RETRIES}")
	private int maxRetries;

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public List<CorpCompanyDataBean> getOfflineCorpCompData(List<Integer> statusList, String branchCode) {
		List<CorpCompanyDataBean> list = null;
		try {

			String sqlQuery = "select cc.id ,cc.companyName as companyName,cc.COMPANYINFO as companyInfo,cc.RRN as rrn, cc.is_corporate as isCorporate, cc.branchCode as branchCode, "
					+ "cc.establishmentOn as establishmentOn,cc.pancardNo as pancardNo,cc.phoneNo as phoneNo,	cc.logo as logo,cc.coi as coi, cc.moa as moa,cc.otherDoc as otherDoc,cc.corporateType as corporateType,"
					+ "	cc.statusId as statusId,cc.createdBy as createdBy,cc.createdOn as createdOn , cc.updatedOn as updatedOn, cc.updatedBy as updatedBy,s.name as statusName,"
					+ "	cc.cif as cif,cc.companyCode as companyCode from CORP_COMPANY_MASTER cc  inner join statusmaster s on s.id=cc.statusid where cc.statusid in :statusId and cc.branchCode=:branchCode and cc.is_corporate='G'  order by cc.id desc";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("companyName")
					.addScalar("companyInfo", StandardBasicTypes.STRING).addScalar("rrn")
					.addScalar("isCorporate", StandardBasicTypes.CHARACTER)
					.addScalar("branchCode", StandardBasicTypes.STRING).addScalar("establishmentOn")
					.addScalar("pancardNo").addScalar("phoneNo").addScalar("logo", StandardBasicTypes.STRING)
					.addScalar("coi", StandardBasicTypes.STRING).addScalar("moa", StandardBasicTypes.STRING)
					.addScalar("otherDoc", StandardBasicTypes.STRING).addScalar("corporateType").addScalar("statusId")
					.addScalar("createdBy").addScalar("createdOn").addScalar("updatedOn").addScalar("updatedBy")
					.addScalar("statusName").addScalar("cif").addScalar("companyCode")
					.setParameterList("statusId", statusList).setParameter("branchCode", branchCode)
					.setResultTransformer(Transformers.aliasToBean(CorpCompanyDataBean.class)).list();
		} catch (Exception e) {
			logger.error("Exception:", e);
		}
		return list;
	}

	@Override
	public List<CorpCompanyDataBean> getOfflineCorpCompBlockUnblockDataFromUsers(List<Integer> statusList,
			String branchCode) {
		List<CorpCompanyDataBean> list = null;
		try {

			String sqlQuery = "select distinct cc.id ,cc.companyName as companyName,cc.RRN as rrn, cc.is_corporate as isCorporate, "
					+ "cc.branchCode as branchCode, cc.establishmentOn as establishmentOn,cc.pancardNo as pancardNo,cc.phoneNo as phoneNo, "
					+ "cc.corporateType as corporateType, cc.statusId as statusId, cc.createdOn as createdOn , cc.updatedOn as updatedOn, "
					+ "cc.updatedBy as updatedBy, s.name as statusName, cc.cif as cif,cc.companyCode as companyCode from CORP_COMPANY_MASTER cc "
					+ "inner join statusmaster s on s.id=cc.statusId inner join corp_users cu on cc.id = cu.corp_comp_Id where cc.branchCode=:branchCode "
					+ "and cc.is_corporate='G' and cu.statusid in :statusId";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("companyName").addScalar("rrn")
					.addScalar("isCorporate", StandardBasicTypes.CHARACTER)
					.addScalar("branchCode", StandardBasicTypes.STRING).addScalar("establishmentOn")
					.addScalar("pancardNo").addScalar("phoneNo").addScalar("corporateType").addScalar("statusId")
					.addScalar("createdOn").addScalar("updatedOn").addScalar("updatedBy").addScalar("statusName")
					.addScalar("cif").addScalar("companyCode").setParameterList("statusId", statusList)
					.setParameter("branchCode", branchCode)
					.setResultTransformer(Transformers.aliasToBean(CorpCompanyDataBean.class)).list();
		} catch (Exception e) {
			logger.info("Exception:", e);

			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<CorpCompanyDataBean> getOfflineCorpCompBlockUnblockData(List<Integer> statusList, String branchCode) {
		List<CorpCompanyDataBean> list = null;
		try {

			String sqlQuery = "select cc.id ,cc.companyName as companyName,cc.COMPANYINFO as companyInfo,cc.RRN as rrn, cc.is_corporate as isCorporate, cc.branchCode as branchCode, "
					+ "cc.establishmentOn as establishmentOn,cc.pancardNo as pancardNo,cc.phoneNo as phoneNo,	cc.logo as logo,cc.coi as coi, cc.moa as moa,cc.otherDoc as otherDoc,cc.corporateType as corporateType,"
					+ "	cc.statusId as statusId,cc.createdOn as createdOn , cc.updatedOn as updatedOn, cc.updatedBy as updatedBy,s.name as statusName,"
					+ "	cc.cif as cif,cc.companyCode as companyCode from CORP_COMPANY_MASTER cc inner join statusmaster s on s.id=cc.statusid inner join corp_users u on u.CORP_COMP_ID=cc.id where u.statusid in :statusId and cc.branchCode=:branchCode and cc.is_corporate='G'  order by cc.id desc";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("companyName")
					.addScalar("companyInfo", StandardBasicTypes.STRING).addScalar("rrn")
					.addScalar("isCorporate", StandardBasicTypes.CHARACTER)
					.addScalar("branchCode", StandardBasicTypes.STRING).addScalar("establishmentOn")
					.addScalar("pancardNo").addScalar("phoneNo").addScalar("logo", StandardBasicTypes.STRING)
					.addScalar("coi", StandardBasicTypes.STRING).addScalar("moa", StandardBasicTypes.STRING)
					.addScalar("otherDoc", StandardBasicTypes.STRING).addScalar("corporateType").addScalar("statusId")
					.addScalar("createdOn").addScalar("updatedOn").addScalar("updatedBy").addScalar("statusName")
					.addScalar("cif").addScalar("companyCode").setParameterList("statusId", statusList)
					.setParameter("branchCode", branchCode)
					.setResultTransformer(Transformers.aliasToBean(CorpCompanyDataBean.class)).list();
		} catch (Exception e) {
			logger.info("Exception:", e);

			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<CorpCompanyDataBean> getOfflineCorpCompDataById(CorpCompanyDataBean corpCompMasterData) {
		logger.info("getOfflineCorpCompDataById service request.........id" + corpCompMasterData.getId() + "|satutsId:"
				+ corpCompMasterData.getStatusId());
		List<CorpCompanyDataBean> list = null;
		try {

			String sqlQuery = "select cc.id ,cc.companyName as companyName,cc.COMPANYINFO as companyInfo,cc.rrn as rrn, cc.max_Limit as maxLimit, cc.is_corporate as isCorporate, "
					+ "cc.createdBy,  cc.maker_Limit as makerLimit, cc.checker_Limit as checkerLimit, cc.approvalLevel as approvalLevel, cc.admin_types as adminTypes, "
					+ " cc.establishmentOn as establishmentOn,cc.pancardNo as pancardNo,cc.phoneNo as phoneNo, cc.address as address, "
					+ "	cc.logo as logo,cc.coi as coi, cc.moa as moa,cc.otherDoc as otherDoc,cc.corporateType as corporateType,"
					+ "	cc.statusId as statusId,cc.createdOn as createdOn , cc.updatedOn as updatedOn, cc.updatedBy as updatedBy,"
					+ "	cc.cif as cif,cc.companyCode as companyCode, cc.levelMaster as levelMaster, cc.og_status as ogstatus from CORP_COMPANY_MASTER cc where cc.id=:id and cc.statusid in :statusId and cc.is_corporate='G'";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("companyName")
					.addScalar("companyInfo", StandardBasicTypes.STRING).addScalar("rrn").addScalar("maxLimit")
					.addScalar("isCorporate", StandardBasicTypes.CHARACTER).addScalar("createdBy")
					.addScalar("makerLimit").addScalar("checkerLimit").addScalar("approvalLevel")
					.addScalar("adminTypes").addScalar("establishmentOn").addScalar("pancardNo").addScalar("phoneNo")
					.addScalar("address").addScalar("logo", StandardBasicTypes.STRING)
					.addScalar("coi", StandardBasicTypes.STRING).addScalar("moa", StandardBasicTypes.STRING)
					.addScalar("otherDoc", StandardBasicTypes.STRING).addScalar("corporateType").addScalar("statusId")
					.addScalar("createdOn").addScalar("updatedOn").addScalar("updatedBy").addScalar("cif")
					.addScalar("companyCode").addScalar("levelMaster").addScalar("ogstatus")
					.setParameter("id", corpCompMasterData.getId())
					.setParameterList("statusId", corpCompMasterData.getStatusList())
					.setResultTransformer(Transformers.aliasToBean(CorpCompanyDataBean.class)).list();
		} catch (Exception e) {
			logger.error("Exception:", e);
		}
		return list;
	}

	@Override
	public List<CorpCompanyMasterEntity> getOfflineCorpCompDataByIdNew(CorpCompanyMasterEntity corpCompMasterData) {
		logger.info("getOfflineCorpCompDataByIdTmpNew service request.........id" + corpCompMasterData.getId());
		List<CorpCompanyMasterEntity> list = null;
		try {

			String sqlQuery = "select cc.id ,cc.companyName as companyName,cc.COMPANYINFO as companyInfo,cc.rrn as rrn, cc.max_Limit as maxLimit, cc.is_corporate as isCorporate, cc.branchCode as branchCode, "
					+ "cc.createdBy,  cc.maker_Limit as makerLimit, cc.checker_Limit as checkerLimit, cc.approvalLevel as approvalLevel, cc.admin_types as adminTypes, "
					+ " cc.establishmentOn as establishmentOn,cc.pancardNo as pancardNo,cc.phoneNo as phoneNo, cc.address as address, cc.og_status as ogstatus, "
					+ "	cc.logo as logo,cc.coi as coi, cc.moa as moa,cc.otherDoc as otherDoc,cc.corporateType as corporateType,"
					+ "	cc.statusId as statusId,cc.createdOn as createdOn , cc.updatedOn as updatedOn, cc.updatedBy as updatedBy,"
					+ "	cc.cif as cif,cc.companyCode as companyCode, cc.levelMaster as levelMaster, cc.appId as appId from CORP_COMPANY_MASTER cc "
					+ "where cc.branchCode=:branchCode and cc.id=:id and cc.is_corporate='G' and cc.statusid in :statusId";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("companyName")
					.addScalar("companyInfo", StandardBasicTypes.STRING).addScalar("rrn").addScalar("maxLimit")
					.addScalar("isCorporate", StandardBasicTypes.CHARACTER)
					.addScalar("branchCode", StandardBasicTypes.STRING).addScalar("createdBy").addScalar("makerLimit")
					.addScalar("checkerLimit").addScalar("approvalLevel").addScalar("adminTypes")
					.addScalar("establishmentOn").addScalar("pancardNo").addScalar("phoneNo").addScalar("address")
					.addScalar("logo", StandardBasicTypes.STRING).addScalar("coi", StandardBasicTypes.STRING)
					.addScalar("moa", StandardBasicTypes.STRING).addScalar("otherDoc", StandardBasicTypes.STRING)
					.addScalar("corporateType").addScalar("statusId").addScalar("createdOn").addScalar("updatedOn")
					.addScalar("updatedBy").addScalar("ogstatus").addScalar("cif").addScalar("companyCode")
					.addScalar("levelMaster").addScalar("appId", StandardBasicTypes.LONG)
					.setParameter("id", corpCompMasterData.getId())
					.setParameter("branchCode", corpCompMasterData.getBranchCode())
					.setParameterList("statusId", corpCompMasterData.getStatusList())
					.setResultTransformer(Transformers.aliasToBean(CorpCompanyMasterEntity.class)).list();
		} catch (Exception e) {
			logger.error("Exception:", e);
		}
		return list;
	}

	@Override
	public List<CorpCompanyMasterEntity> getOfflineCorpCompDataByRrn(CorpCompanyMasterEntity corpCompMasterData) {
		logger.info("getOfflineCorpCompDataByRrn service request.........rrn: " + corpCompMasterData.getRrn());
		List<CorpCompanyMasterEntity> list = null;
		try {

			String sqlQuery = "select cc.id ,cc.companyName as companyName,cc.COMPANYINFO as companyInfo,cc.rrn as rrn, cc.is_corporate as isCorporate, "
					+ "cc.establishmentOn as establishmentOn,cc.pancardNo as pancardNo,cc.phoneNo as phoneNo,"
					+ "	cc.logo as logo,cc.coi as coi, cc.moa as moa,cc.otherDoc as otherDoc,cc.corporateType as corporateType,"
					+ "	cc.statusId as statusId,cc.createdOn as createdOn , cc.updatedOn as updatedOn, cc.updatedBy as updatedBy,"
					+ "	cc.cif as cif,cc.companyCode as companyCode from CORP_COMPANY_MASTER cc	where cc.rrn=:rrn and cc.statusid=3 and cc.is_corporate='G'";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("companyName")
					.addScalar("companyInfo", StandardBasicTypes.STRING).addScalar("rrn")
					.addScalar("isCorporate", StandardBasicTypes.CHARACTER).addScalar("establishmentOn")
					.addScalar("pancardNo").addScalar("phoneNo").addScalar("logo", StandardBasicTypes.STRING)
					.addScalar("coi", StandardBasicTypes.STRING).addScalar("moa", StandardBasicTypes.STRING)
					.addScalar("otherDoc", StandardBasicTypes.STRING).addScalar("corporateType").addScalar("statusId")
					.addScalar("createdOn").addScalar("updatedOn").addScalar("updatedBy").addScalar("cif")
					.addScalar("companyCode").setParameter("rrn", corpCompMasterData.getRrn())
					.setResultTransformer(Transformers.aliasToBean(CorpCompanyMasterEntity.class)).list();
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return list;
	}

	@Override
	public List<CorpCompanyDataBean> getOfflineCorpByCompNameCifCorpId(CorpCompanyDataBean corpCompReq) {
		List<CorpCompanyDataBean> list = null;
		try {

			String sqlQuery = "select cc.id ,cc.companyName as companyName,cc.COMPANYINFO as companyInfo,cc.rrn as rrn,"
					+ " cc.establishmentOn as establishmentOn,cc.pancardNo as pancardNo,cc.phoneNo as phoneNo,"
					+ "	cc.logo as logo,cc.coi as coi, cc.moa as moa,cc.otherDoc as otherDoc,cc.corporateType as corporateType,"
					+ "	cc.statusId as statusId,cc.createdOn as createdOn , cc.updatedOn as updatedOn, cc.updatedBy as updatedBy,s.name as statusName,"
					+ "	cc.cif as cif,cc.companyCode as companyCode from CORP_COMPANY_MASTER cc	inner join statusmaster s on s.id=cc.statusid where ";

			if (corpCompReq.getCif() != null) {
				logger.info("getOfflineCorpByCompNameCifCorpId service request.........CIF:" + corpCompReq.getCif()
						+ "|statusId:" + corpCompReq.getStatusList());
				sqlQuery = sqlQuery.concat(" " + "cc.cif=:cif and cc.statusid in :statusId ");
				list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("companyName")
						.addScalar("companyInfo", StandardBasicTypes.STRING).addScalar("rrn")
						.addScalar("establishmentOn").addScalar("pancardNo").addScalar("phoneNo")
						.addScalar("logo", StandardBasicTypes.STRING).addScalar("coi", StandardBasicTypes.STRING)
						.addScalar("moa", StandardBasicTypes.STRING).addScalar("otherDoc", StandardBasicTypes.STRING)
						.addScalar("corporateType").addScalar("statusId").addScalar("createdOn").addScalar("updatedOn")
						.addScalar("updatedBy").addScalar("statusName").addScalar("cif").addScalar("companyCode")
						.setParameter("cif", EncryptorDecryptor.encryptData(corpCompReq.getCif()))
						.setParameterList("statusId", corpCompReq.getStatusList())
						.setResultTransformer(Transformers.aliasToBean(CorpCompanyDataBean.class)).list();
			} else if (corpCompReq.getCompanyCode() != null) {
				logger.info("getOfflineCorpByCompNameCifCorpId service request.........CompanyCode:"
						+ corpCompReq.getCompanyCode() + "|statusId:" + corpCompReq.getStatusList());
				sqlQuery = sqlQuery.concat(" " + "cc.companyCode=:compCode and cc.statusid in :statusId");
				list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("companyName")
						.addScalar("companyInfo", StandardBasicTypes.STRING).addScalar("rrn")
						.addScalar("establishmentOn").addScalar("pancardNo").addScalar("phoneNo")
						.addScalar("logo", StandardBasicTypes.STRING).addScalar("coi", StandardBasicTypes.STRING)
						.addScalar("moa", StandardBasicTypes.STRING).addScalar("otherDoc", StandardBasicTypes.STRING)
						.addScalar("corporateType").addScalar("statusId").addScalar("createdOn").addScalar("updatedOn")
						.addScalar("updatedBy").addScalar("statusName").addScalar("cif").addScalar("companyCode")
						.setParameter("compCode", EncryptorDecryptor.encryptData(corpCompReq.getCompanyCode()))
						.setParameterList("statusId", corpCompReq.getStatusList())
						.setResultTransformer(Transformers.aliasToBean(CorpCompanyDataBean.class)).list();
			} else if (corpCompReq.getCompanyName() != null) {
				logger.info("getOfflineCorpByCompNameCifCorpId service request.........CompanyName:"
						+ corpCompReq.getCompanyName() + "|statusId:" + corpCompReq.getStatusList());
				sqlQuery = sqlQuery.concat(
						" " + "cc.companyName LIKE('%" + EncryptorDecryptor.encryptData(corpCompReq.getCompanyName())
								+ "%') and cc.statusid in :statusId");
				list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("companyName")
						.addScalar("companyInfo", StandardBasicTypes.STRING).addScalar("rrn")
						.addScalar("establishmentOn").addScalar("pancardNo").addScalar("phoneNo")
						.addScalar("logo", StandardBasicTypes.STRING).addScalar("coi", StandardBasicTypes.STRING)
						.addScalar("moa", StandardBasicTypes.STRING).addScalar("otherDoc", StandardBasicTypes.STRING)
						.addScalar("corporateType").addScalar("statusId").addScalar("createdOn").addScalar("updatedOn")
						.addScalar("updatedBy").addScalar("statusName").addScalar("cif").addScalar("companyCode")
						.setParameterList("statusId", corpCompReq.getStatusList())
						.setResultTransformer(Transformers.aliasToBean(CorpCompanyDataBean.class)).list();
			}

		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return list;
	}

	@Override
	public ResponseMessageBean updateCorpUsersMode(CorpCompanyMasterEntity corpCompReq) {
		ResponseMessageBean responseMessageBean = new ResponseMessageBean();
		try {
			Session session = sessionFactory.getCurrentSession();
			if (corpCompReq.getMaxLimit().equals(BigDecimal.valueOf(0))) {
				List<CorpUserEntity> userList = getAllCorpUsersByCompanyId(corpCompReq.getId());
				for (CorpUserEntity corpUserEntity : userList) {
					if (!corpUserEntity.getStatusid().equals(BigDecimal.valueOf(10))
							&& corpUserEntity.getRights().equals("T")) {
						corpUserEntity.setRights("V");
						corpUserEntity.setTransMaxLimit(BigDecimal.valueOf(0));
						session.update(corpUserEntity);
					}
				}
				responseMessageBean.setResponseCode("200");
				responseMessageBean.setResponseCode("Record Updated Successfully");
			}
		} catch (Exception e) {
			responseMessageBean.setResponseCode("202");
			responseMessageBean.setResponseCode("Error while update user mode");
			logger.error("Error while update user mode" + e);
		}

		return responseMessageBean;
	}

	@Override
	public ResponseMessageBean updateCorpCompData(CorpCompanyMasterEntity corpCompReq) {
		ResponseMessageBean responseMessageBean = new ResponseMessageBean();
		logger.info("updateCorpCompData .........");
		Session session = sessionFactory.getCurrentSession();
		try {
			logger.info("CorpCompId: " + corpCompReq.getId());
			corpCompReq.setAdminTypes(corpCompReq.getAdminTypes());
			corpCompReq.setCompanyName(EncryptorDecryptor.encryptData(corpCompReq.getCompanyName()));
			corpCompReq.setCompanyCode(EncryptorDecryptor.encryptData(corpCompReq.getRrn()));
			corpCompReq.setCif(EncryptorDecryptor.encryptData(corpCompReq.getCif()));// Add if statement
			corpCompReq.setPancardNo(EncryptorDecryptor.encryptData(corpCompReq.getPancardNo()));
			logger.info("Offline request, CORP COMPRRN:" + corpCompReq.getRrn());

			corpCompReq.setIsCorporate('G');
			corpCompReq.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
			if (corpCompReq.getLoginId().equalsIgnoreCase("6")
					|| corpCompReq.getLoginType().equalsIgnoreCase("Checker")) {
				corpCompReq.setStatusId(BigDecimal.valueOf(36));
			}
			corpCompReq.setAppId(2l);
			corpCompReq.setUpdatedBy(corpCompReq.getUpdatedBy());
			corpCompReq.setApprovalLevel(corpCompReq.getUserTypes() + "");
			corpCompReq.setMaxLimit(corpCompReq.getMaxLimit());
			corpCompReq.setMakerLimit(corpCompReq.getMaxLimit());
			corpCompReq.setCheckerLimit(corpCompReq.getMaxLimit());
			corpCompReq.setLevelMaster(BigDecimal.valueOf(corpCompReq.getMultiUser()));
			responseMessageBean.setResponseMessage("Company Details Updated Successfully");
			responseMessageBean.setResponseCode("200");
			if (!ObjectUtils.isEmpty(corpCompReq.getAction())
					&& corpCompReq.getAction().equalsIgnoreCase("editActiveCompany")) {
				// check for inactive company against cif
				List<Integer> statusIdList = new ArrayList<>();
				statusIdList.add(0);
				statusIdList.add(35);
				statusIdList.add(36);
				statusIdList.add(53);
				statusIdList.add(54);
				CorpCompanyMasterEntity corpCompanyMasterEntity = corpCompanyMasterRepo
						.findByCifAndStatusid(corpCompReq.getCif(), statusIdList);
				List<BigDecimal> statusList = new ArrayList<BigDecimal>();
				statusList.add(BigDecimal.valueOf(53));
				statusList.add(BigDecimal.valueOf(54));
				if (ObjectUtils.isEmpty(corpCompanyMasterEntity)) {
					List<CorpUserEntity> corpUserEntityList = corpUserRepository
							.findByCorpCompIdAndStatusidIN(corpCompReq.getId(), statusList);
					if (!ObjectUtils.isEmpty(corpUserEntityList)) {
						responseMessageBean.setResponseMessage("Request is Pending to checker for approval");
						responseMessageBean.setResponseCode("202");
					} else {
						// replicate active company, accounts, modules and users for new record
						corpCompReq.setId(replicateCorporateData(corpCompReq));
						corpCompReq.setStatusId(BigDecimal.valueOf(0));
						responseMessageBean.setResult(corpCompReq);
					}
				} else {
					corpCompReq.setId(corpCompanyMasterEntity.getId());
					if (corpCompanyMasterEntity.getStatusId().equals(BigDecimal.valueOf(0))
							|| corpCompanyMasterEntity.getStatusId().equals(BigDecimal.valueOf(35))) {
						responseMessageBean.setResponseMessage("Request already in action pending mode for Action");
						responseMessageBean.setResponseCode("202");
					} else if (corpCompanyMasterEntity.getStatusId().equals(BigDecimal.valueOf(36))
							|| corpCompanyMasterEntity.getStatusId().equals(BigDecimal.valueOf(53))
							|| corpCompanyMasterEntity.getStatusId().equals(BigDecimal.valueOf(54))) {
						responseMessageBean.setResponseMessage("Request is Pending to checker for approval");
						responseMessageBean.setResponseCode("202");
					}
				}
			} else {
				if (ObjectUtils.isEmpty(corpCompReq.getOgstatus())) {
					corpCompReq.setOgstatus(new BigDecimal(0));
				}
				session.saveOrUpdate(corpCompReq);
			}
//			if (corpCompReq.getMaxLimit().equals(BigDecimal.valueOf(0))) {
//				List<CorpUserEntity> userList = getAllCorpUsersByCompanyId(corpCompReq.getId());
//				for (CorpUserEntity corpUserEntity : userList) {
//					if (!corpUserEntity.getStatusid().equals(BigDecimal.valueOf(10))
//							&& corpUserEntity.getRights().equals("T")) {
//						corpUserEntity.setRights("V");
//						corpUserEntity.setTransMaxLimit(BigDecimal.valueOf(0));
//						session.update(corpUserEntity);
//					}
//				}
//			}
//
			logger.info("updateCorpCompData response........." + corpCompReq.toString());
		} catch (Exception e) {
			logger.error("Exception:", e);
			return responseMessageBean;
		}
		return responseMessageBean;
	}

	private BigDecimal replicateCorporateData(CorpCompanyMasterEntity corpCompReq) {
		BigDecimal activeCorpCompId = corpCompReq.getId();
		BigDecimal inactiveCorpCompId = replicateCompanyData(corpCompReq);
		if (!ObjectUtils.isEmpty(inactiveCorpCompId)) {
			replicateCorpMenuData(activeCorpCompId, inactiveCorpCompId, corpCompReq.getUpdatedBy());
			replicateCorpAccData(activeCorpCompId, inactiveCorpCompId, corpCompReq.getUpdatedBy());
			replicateCorpUserData(activeCorpCompId, inactiveCorpCompId, corpCompReq.getUpdatedBy());
		}
		return inactiveCorpCompId;
	}

	private void replicateCorpUserAccData(BigDecimal activeCorpCompId, BigDecimal inactiveCorpCompId,
			BigDecimal createdBy, BigDecimal oldCorpUserId, BigDecimal corpUserId) {
		List<CorpUserAccMapEntity> newCorpUserAccEntities = new ArrayList<>();
		List<CorpUserAccMapEntity> corpUserAccEntities = corpUsersAccMapRepository
				.findByCorpCompIdAndStatusIdAndCorpUserId(activeCorpCompId, BigDecimal.valueOf(3), oldCorpUserId);
		for (CorpUserAccMapEntity corpUserAccEntity : corpUserAccEntities) {
			entityManager.detach(corpUserAccEntity);
			corpUserAccEntity.setCorpCompId(inactiveCorpCompId);
			corpUserAccEntity.setCorpUserId(corpUserId);
			corpUserAccEntity.setUpdatedby(createdBy);
			corpUserAccEntity.setCreatedon(new Date());
			newCorpUserAccEntities.add(new CorpUserAccMapEntity(corpUserAccEntity));
		}
		corpUsersAccMapRepository.save(newCorpUserAccEntities);
	}

	private void replicateCorpUserMenuData(BigDecimal activeCorpCompId, BigDecimal inactiveCorpCompId,
			BigDecimal createdBy, BigDecimal oldCorpUserId, BigDecimal corpUserId) {
		List<CorpUserMenuMapEntity> newCorpUserMenuEntities = new ArrayList<>();
		List<CorpUserMenuMapEntity> corpUserMenuEntities = corpUsersMenuMapRepository
				.findByCorpCompIdAndStatusIdAndCorpUserId(activeCorpCompId, BigDecimal.valueOf(3), oldCorpUserId);
		for (CorpUserMenuMapEntity corpUserMenuEntity : corpUserMenuEntities) {
			entityManager.detach(corpUserMenuEntity);
			corpUserMenuEntity.setCorpCompId(inactiveCorpCompId);
			corpUserMenuEntity.setCorpUserId(corpUserId);
			corpUserMenuEntity.setCreatedon(new Date());
			corpUserMenuEntity.setUpdatedby(createdBy);
			newCorpUserMenuEntities.add(new CorpUserMenuMapEntity(corpUserMenuEntity));
		}
		corpUsersMenuMapRepository.save(newCorpUserMenuEntities);
	}

	private void replicateCorpUserData(BigDecimal activeCorpCompId, BigDecimal inactiveCorpCompId,
			BigDecimal createdBy) {
		List<CorpUserEntity> corpUserEntities = corpUserRepository.findByCorpCompIdAndStatusidNotIn(activeCorpCompId,
				BigDecimal.valueOf(10));
		for (CorpUserEntity corpUserEntity : corpUserEntities) {
			entityManager.detach(corpUserEntity);
			corpUserEntity.setCorp_comp_id(inactiveCorpCompId);
			corpUserEntity.setOgstatus(corpUserEntity.getStatusid());
			corpUserEntity.setStatusid(BigDecimal.valueOf(0));
			corpUserEntity.setMpin("");
			corpUserEntity.setTpin("");
			CorpUserEntity newCorpUserEntity = new CorpUserEntity(corpUserEntity);
			logger.info("status before save in user replica.........." + newCorpUserEntity.getStatusid());
			corpUserRepository.save(newCorpUserEntity);
			replicateCorpUserMenuData(activeCorpCompId, inactiveCorpCompId, createdBy, corpUserEntity.getId(),
					newCorpUserEntity.getId());
			replicateCorpUserAccData(activeCorpCompId, inactiveCorpCompId, createdBy, corpUserEntity.getId(),
					newCorpUserEntity.getId());
		}

	}

	private void replicateCorpAccData(BigDecimal activeCorpCompId, BigDecimal inactiveCorpCompId,
			BigDecimal createdBy) {
		List<CorpAccMapEntity> newCorpAccMapEntities = new ArrayList<>();
		List<CorpAccMapEntity> corpAccMapEntities = corpAccMapRepository.findByCorpIdAndStatusId(activeCorpCompId,
				BigDecimal.valueOf(3));
		for (CorpAccMapEntity corpAccMapEntity : corpAccMapEntities) {
			entityManager.detach(corpAccMapEntity);
			corpAccMapEntity.setCorpId(inactiveCorpCompId);
			corpAccMapEntity.setCreatedon(new Date());
			corpAccMapEntity.setUpdatedby(createdBy);
			newCorpAccMapEntities.add(new CorpAccMapEntity(corpAccMapEntity));
		}
		corpAccMapRepository.save(newCorpAccMapEntities);
	}

	private void replicateCorpMenuData(BigDecimal activeCorpCompId, BigDecimal inactiveCorpCompId,
			BigDecimal createdBy) {
		List<CorpMenuMapEntity> newCorpMenuMapEntities = new ArrayList<>();
		List<CorpMenuMapEntity> corpMenuMapEntities = corpMenuMapRepository.findByCorpIdAndStatusId(activeCorpCompId,
				BigDecimal.valueOf(3));
		for (CorpMenuMapEntity corpMenuMapEntity : corpMenuMapEntities) {
			entityManager.detach(corpMenuMapEntity);
			corpMenuMapEntity.setCorpId(inactiveCorpCompId);
			corpMenuMapEntity.setCreatedon(new Date());
			corpMenuMapEntity.setUpdatedby(createdBy);
			newCorpMenuMapEntities.add(new CorpMenuMapEntity(corpMenuMapEntity));
		}
		corpMenuMapRepository.save(newCorpMenuMapEntities);
	}

	// replicate corp_company_master_data with new id
	private BigDecimal replicateCompanyData(CorpCompanyMasterEntity corpCompReq) {
		CorpCompanyMasterEntity corpCompanyMasterEntity = new CorpCompanyMasterEntity();
		corpCompanyMasterEntity.setAdminTypes(corpCompReq.getAdminTypes());
		corpCompanyMasterEntity.setCompanyName(corpCompReq.getCompanyName());
		corpCompanyMasterEntity.setCompanyCode(EncryptorDecryptor.encryptData(corpCompReq.getRrn()));
		corpCompanyMasterEntity.setCif(corpCompReq.getCif());// Add if statement
		corpCompanyMasterEntity.setCompanyInfo(corpCompReq.getCompanyInfo());
		corpCompanyMasterEntity.setPancardNo(corpCompReq.getPancardNo());
		corpCompanyMasterEntity.setEstablishmentOn(corpCompReq.getEstablishmentOn());
		corpCompanyMasterEntity.setIsCorporate('G');
		corpCompanyMasterEntity.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
		corpCompanyMasterEntity.setCreatedOn(corpCompReq.getCreatedOn());
		corpCompanyMasterEntity.setAppId(2l);
		corpCompanyMasterEntity.setUpdatedBy(corpCompReq.getUpdatedBy());
		corpCompanyMasterEntity.setApprovalLevel(corpCompReq.getUserTypes() + "");
		corpCompanyMasterEntity.setMaxLimit(corpCompReq.getMaxLimit());
		corpCompanyMasterEntity.setMakerLimit(corpCompReq.getMaxLimit());
		corpCompanyMasterEntity.setCheckerLimit(corpCompReq.getMaxLimit());
		corpCompanyMasterEntity.setLevelMaster(BigDecimal.valueOf(corpCompReq.getMultiUser()));
		corpCompanyMasterEntity.setStatusId(BigDecimal.valueOf(0));
		corpCompanyMasterEntity.setCreatedBy(corpCompReq.getCreatedBy());
		corpCompanyMasterEntity.setBranchCode(corpCompReq.getBranchCode());
		corpCompanyMasterEntity.setOgstatus(BigDecimal.valueOf(3));
		corpCompanyMasterEntity.setRrn(corpCompReq.getRrn());
		corpCompanyMasterEntity.setPhoneNo(corpCompReq.getPhoneNo());
		corpCompanyMasterEntity.setCorporateType(corpCompReq.getCorporateType());
		corpCompanyMasterEntity.setAddress(corpCompReq.getAddress());
		corpCompanyMasterRepo.save(corpCompanyMasterEntity);
		return corpCompanyMasterEntity.getId();
	}

	@Override
	public List<CorpMenuMapEntity> getOfflineCorpMenuByCompanyId(CorpMenuMapEntity corpMenuData) {
		logger.info("getOfflineCorpMenuByCompanyId service request........." + corpMenuData.getCorpId());
		List<CorpMenuMapEntity> list = null;
		try {

			String sqlQuery = "	select cm.id as id ,cm.corpId as corpId, cm.corpMenuId as corpMenuId,"
					+ "NVL(cm.corpSubMenuId, 0) as corpSubMenuId, "
					+ "cm.StatusId as statusId, cm.Createdon as createdon,"
					+ "	cm.Updatedon as updatedon,cm.UPDATEDBY as updatedby , cmm.menuname as menuName, NVL(csm.menuname, 'NA') as subMenuName from CORP_MENU_MAP cm "
					+ "	left outer join corp_menu cmm on cmm.id =cm.corpMenuId left outer join corp_submenu csm on csm.id =cm.corpSubMenuId where cm.corpId=:corpId and cm.statusid=3";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("corpMenuId")
					.addScalar("corpSubMenuId").addScalar("corpId").addScalar("statusId").addScalar("createdon")
					.addScalar("updatedon").addScalar("updatedby").addScalar("menuName").addScalar("subMenuName")
					.setParameter("corpId", corpMenuData.getCorpId())
					.setResultTransformer(Transformers.aliasToBean(CorpMenuMapEntity.class)).list();
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return list;
	}

	@Override
	public List<CorpMenuMapEntity> getOfflineCorpMenuByCompanyIdBeam(CorpMenuMapEntity corpMenuMapEntity) {
		logger.info("getOfflineCorpMenuByCompanyIdTmp service request........." + corpMenuMapEntity.getCorpId());
		List<CorpMenuMapEntity> list = null;
		try {
			String sqlQuery = "	select cm.id as id ,cm.corpId as corpId, cm.corpMenuId as corpMenuId,NVL(cm.corpSubMenuId, 0) as corpSubMenuId, cm.StatusId as statusId, cm.Createdon as createdon,"
					+ "	cm.Updatedon as updatedon,cm.UPDATEDBY as updatedby , cmm.menuname as menuName, NVL(csm.menuname, 'NA') as subMenuName from CORP_MENU_MAP cm "
					+ "	left outer join corp_menu cmm on cmm.id =cm.corpMenuId left outer join corp_submenu csm on csm.id =cm.corpSubMenuId where cm.corpId=:corpId and cm.statusid = 3";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("corpMenuId")
					.addScalar("corpSubMenuId").addScalar("corpId").addScalar("statusId").addScalar("createdon")
					.addScalar("updatedon").addScalar("updatedby").addScalar("menuName").addScalar("subMenuName")
					.setParameter("corpId", corpMenuMapEntity.getCorpId())
					.setResultTransformer(Transformers.aliasToBean(CorpMenuMapEntity.class)).list();
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return list;
	}

	@Override
	public List<CorpAccMapEntity> getOfflineCorpAccByCompanyId(CorpAccMapEntity corpAccData) {
		logger.info("getOfflineCorpAccByCompanyId service request........." + corpAccData.getCorpId());
		List<CorpAccMapEntity> list = null;
		try {

			String sqlQuery = "	select cm.id as id ,cm.corpId as corpId, cm.Accountno as accountNo, cm.StatusId as statusId, cm.Createdon as createdon,"
					+ "	cm.Updatedon as updatedon,cm.UPDATEDBY as updatedby  from CORP_ACC_MAP cm where cm.corpId=:corpId and cm.statusid <> 10";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("accountNo").addScalar("corpId")
					.addScalar("statusId").addScalar("createdon").addScalar("updatedon").addScalar("updatedby")
					.setParameter("corpId", corpAccData.getCorpId())
					.setResultTransformer(Transformers.aliasToBean(CorpAccMapEntity.class)).list();
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return list;
	}

	@Override
	public List<CorpAccMapEntity> getAllOfflineCorpAccByCompanyId(CorpAccMapEntity corpAccData) {
		logger.info("getOfflineCorpAccByCompanyId service request........." + corpAccData.getCorpId());
		List<CorpAccMapEntity> list = null;
		try {

			String sqlQuery = "	select cm.id as id ,cm.corpId as corpId, cm.Accountno as accountNo, cm.StatusId as statusId, cm.Createdon as createdon,"
					+ "	cm.Updatedon as updatedon,cm.UPDATEDBY as updatedby  from CORP_ACC_MAP cm where cm.corpId=:corpId";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("accountNo").addScalar("corpId")
					.addScalar("statusId").addScalar("createdon").addScalar("updatedon").addScalar("updatedby")
					.setParameter("corpId", corpAccData.getCorpId())
					.setResultTransformer(Transformers.aliasToBean(CorpAccMapEntity.class)).list();
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return list;
	}

	@Override
	public List<CorpAccMapEntity> getOfflineCorpAccByCompanyIdBean(CorpAccMapEntity corpAccData) {
		logger.info("getOfflineCorpAccByCompanyId service request........." + corpAccData.getCorpId());
		List<CorpAccMapEntity> list = null;
		try {

			String sqlQuery = "	select cm.id as id ,cm.corpId as corpId, cm.Accountno as accountNo, cm.StatusId as statusId, cm.Createdon as createdon,"
					+ "	cm.Updatedon as updatedon,cm.UPDATEDBY as updatedby  from CORP_ACC_MAP cm where cm.corpId=:corpId and cm.statusid = 3";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("accountNo").addScalar("corpId")
					.addScalar("statusId").addScalar("createdon").addScalar("updatedon").addScalar("updatedby")
					.setParameter("corpId", corpAccData.getCorpId())
					.setResultTransformer(Transformers.aliasToBean(CorpAccMapEntity.class)).list();
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return list;
	}

	@Override
	public List<CorpAccMapEntityTmp> getOfflineCorpAccByCompanyIdTmp(CorpAccMapEntityTmp corpAccData) {
		logger.info("getOfflineCorpAccByCompanyIdTmp service request........." + corpAccData.getCorpId());
		List<CorpAccMapEntityTmp> list = null;
		try {

			String sqlQuery = "	select cm.id as id ,cm.corpId as corpId, cm.Accountno as accountNo, cm.StatusId as statusId, cm.Createdon as createdon,"
					+ "	cm.Updatedon as updatedon,cm.UPDATEDBY as updatedby  from CORP_ACC_MAP_TMP cm where cm.corpId=:corpId ";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("accountNo").addScalar("corpId")
					.addScalar("statusId").addScalar("createdon").addScalar("updatedon").addScalar("updatedby")
					.setParameter("corpId", corpAccData.getCorpId())
					.setResultTransformer(Transformers.aliasToBean(CorpAccMapEntityTmp.class)).list();
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return list;
	}

	@Override
	public List<CorpUserMenuMapEntity> getUserMenuByCorpUserId(CorpUserMenuMapEntity corpUserMenuData) {
		logger.info("getUserMenuByCorpUserId service request........." + corpUserMenuData.getCorpUserId());
		List<CorpUserMenuMapEntity> list = null;
		try {

			String sqlQuery = "select cm.id as id, cm.corpCompId as corpCompId,cm.corpMenuId as corpMenuId,NVL(cm.corpSubMenuId, 0) as corpSubMenuId, cm.corpUserId as corpUserId,"
					+ "cm.statusId as statusId, cc.menuName as menuName ,NVL(csm.menuname, 'NA') as subMenuName ,cm.userRrn as userRrn from CORPUSER_MENU_MAP cm left outer join corp_menu cc on cc.id =cm.corpMenuId "
					+ "left outer join corp_submenu csm on csm.id =cm.corpSubMenuId  where cm.corpUserId =:userId and cm.statusid = 3";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("corpCompId").addScalar("corpMenuId")
					.addScalar("corpSubMenuId").addScalar("corpUserId").addScalar("statusId").addScalar("menuName")
					.addScalar("subMenuName").addScalar("userRrn")
					.setParameter("userId", corpUserMenuData.getCorpUserId())
					.setResultTransformer(Transformers.aliasToBean(CorpUserMenuMapEntity.class)).list();
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return list;
	}

	@Override
	public List<CorpUserAccMapEntity> getUserAccountByCorpUserId(CorpUserAccMapEntity corpUserAccData) {
		logger.info("getUserMenuByCorpUserId service request........." + corpUserAccData.getCorpUserId());
		List<CorpUserAccMapEntity> list = null;
		try {

			String sqlQuery = "select ca.id as id, ca.corpCompId as corpCompId, ca.accountNo as accountNo,ca.corpUserId as corpUserId, ca.statusId as statusId, "
					+ " ca.userRrn as userRrn from CORPUSER_ACC_MAP ca where ca.corpUserId =:userId and ca.statusid = 3";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("corpCompId").addScalar("accountNo")
					.addScalar("corpUserId").addScalar("statusId").addScalar("userRrn")
					.setParameter("userId", corpUserAccData.getCorpUserId())
					.setResultTransformer(Transformers.aliasToBean(CorpUserAccMapEntity.class)).list();
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return list;
	}

	@Override
	public List<CorpUserEntity> getAllCorpUsersByCompId(CorpUserEntity corpuserReqData, boolean isDecrypted) {
		logger.info("getAllCorpUsersByCompId service request.........CORP_ID" + corpuserReqData.getCorp_comp_id()
				+ "|statusId:" + corpuserReqData.getStatusList());
		List<CorpUserEntity> list = null;
		try {

			String sqlQuery = "select cu.id,cu.corp_comp_id, cu.user_name,cu.user_pwd,cu.first_name,cu.last_name,cu.email_id,cu.parentId, "
					+ "cu.user_disp_name, cu.createdby, cu.appid, cu.country, cu.work_phone, cu.tpin_wrong_attempt, cu.city, cu.wrong_pwd_attempt, "
					+ "cu.pwd_status, cu.state, cu.mpin_wrong_attempt, cu.rights,cu.MAXLIMIT as transMaxLimit, cu.pkistatus, cu.mobRegStatus, cu.ibRegStatus,"
					+ "	cu.personal_Phone, cu.dob,cu.pancardNumber, cu.rrn as rrn,cu.corpRoleId, cu.aadharCardNo, "
					+ "	cu.passport, cu.boardResolution, cu.user_image,cu.user_type as user_type,  "
					+ "	cu.otherDoc as otherDoc,cu.certificate_incorporation, cu.designation,cu.parentRrn, "
					+ "	cu.statusid, cu.createdon, cu.updatedOn,cu.updatedby as updatedby,cr.name as corpRoleName, cc.name as parentRoleName, "
					+ " cu.parentRoleId as parentRoleId, cu.parentUserName as parentUserName, cu.og_status as ogstatus from CORP_USERS  cu  "
					+ " left join corp_roles cr on cr.id=cu.corpRoleId  left join corp_roles cc on cc.id=cu.parentRoleId where cu.corp_comp_id=:compId AND cu.statusId in :statusId order by cu.id";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("corp_comp_id")
					.addScalar("user_name").addScalar("user_pwd", StandardBasicTypes.STRING).addScalar("first_name")
					.addScalar("last_name").addScalar("email_id").addScalar("parentId").addScalar("user_disp_name")
					.addScalar("createdby").addScalar("appid").addScalar("country").addScalar("work_phone")
					.addScalar("tpin_wrong_attempt").addScalar("city").addScalar("wrong_pwd_attempt")
					.addScalar("pwd_status").addScalar("state").addScalar("mpin_wrong_attempt").addScalar("rights")
					.addScalar("transMaxLimit").addScalar("pkiStatus").addScalar("mobRegStatus")
					.addScalar("ibRegStatus").addScalar("personal_Phone").addScalar("dob").addScalar("pancardNumber")
					.addScalar("rrn").addScalar("corpRoleId").addScalar("aadharCardNo", StandardBasicTypes.STRING)
					.addScalar("passport", StandardBasicTypes.STRING)// .addScalar("passportNo")
					.addScalar("boardResolution", StandardBasicTypes.STRING)
					.addScalar("user_image", StandardBasicTypes.STRING).addScalar("user_type")
					.addScalar("otherDoc", StandardBasicTypes.STRING)
					.addScalar("certificate_incorporation", StandardBasicTypes.STRING).addScalar("designation")
					.addScalar("parentRrn").addScalar("statusid").addScalar("ogstatus").addScalar("createdon")
					.addScalar("updatedOn").addScalar("updatedby").addScalar("corpRoleName").addScalar("parentRoleName")
					.addScalar("parentRoleId").addScalar("parentUserName")
					.setParameter("compId", corpuserReqData.getCorp_comp_id())
					.setParameterList("statusId", corpuserReqData.getStatusList())
					.setResultTransformer(Transformers.aliasToBean(CorpUserEntity.class)).list();
			if (isDecrypted) {
				for (CorpUserEntity corpData : list) {
					corpData.setPersonal_Phone(EncryptorDecryptor.decryptData(corpData.getPersonal_Phone()));
					corpData.setUser_name(EncryptorDecryptor.decryptData(corpData.getUser_name()));
					corpData.setEmail_id(EncryptorDecryptor.decryptData(corpData.getEmail_id()));
					corpData.setDob(EncryptorDecryptor.decryptData(corpData.getDob()));
					corpData.setPancardNumber(EncryptorDecryptor.decryptData(corpData.getPancardNumber()));
					if (!ObjectUtils.isEmpty(corpData.getPassport())) {
						corpData.setPassport(EncryptorDecryptor.decryptData(corpData.getPassport()));
					} else {
						corpData.setPassport("");
					}
					corpData.setAadharCardNo(EncryptorDecryptor.decryptData(corpData.getAadharCardNo()));

				}
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return list;
	}

	@Override
	public List<CorpUserEntityBean> getAllCorpUsersByCompIdBeanStatus(CorpUserEntityBean corpuserReqData,
			boolean isDecrypted) {
		logger.info("getAllCorpUsersByCompId service request.........CORP_ID" + corpuserReqData.getCorp_comp_id()
				+ "|statusId:" + corpuserReqData.getStatusList());
		List<CorpUserEntityBean> list = null;
		try {

			String sqlQuery = "select cu.id,cu.corp_comp_id, cu.user_name,cu.user_pwd,cu.first_name,cu.last_name,cu.email_id,cu.parentId, "
					+ "cu.user_disp_name, cu.createdby, cu.appid, cu.country, cu.work_phone, cu.tpin_wrong_attempt, cu.city, cu.wrong_pwd_attempt, "
					+ "cu.pwd_status, cu.state, cu.mpin_wrong_attempt, cu.rights,cu.MAXLIMIT as transMaxLimit, cu.pkistatus, cu.mobRegStatus, cu.ibRegStatus,"
					+ "	cu.personal_Phone, cu.dob,cu.pancardNumber, cu.rrn as rrn,cu.corpRoleId, cu.aadharCardNo, "
					+ "	cu.passport, cu.boardResolution, cu.user_image,cu.user_type as user_type,  "
					+ "	cu.otherDoc as otherDoc,cu.certificate_incorporation, cu.designation,cu.parentRrn, "
					+ "	cu.statusid, cu.createdon, cu.updatedOn,cu.updatedby as updatedby,cr.name as corpRoleName, cc.name as parentRoleName, "
					+ " cu.parentRoleId as parentRoleId, cu.parentUserName as parentUserName, cu.remarks as remark, cu.og_status as ogstatus, s.name as statusName from CORP_USERS  cu  "
					+ " left join corp_roles cr on cr.id=cu.corpRoleId  left join corp_roles cc on cc.id=cu.parentRoleId "
					+ "inner join STATUSMASTER s on s.id=cu.statusid where cu.corp_comp_id=:compId AND cu.statusId in :statusId order by cu.id";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("corp_comp_id")
					.addScalar("user_name").addScalar("user_pwd", StandardBasicTypes.STRING).addScalar("first_name")
					.addScalar("last_name").addScalar("email_id").addScalar("parentId").addScalar("user_disp_name")
					.addScalar("createdby").addScalar("appid").addScalar("country").addScalar("work_phone")
					.addScalar("tpin_wrong_attempt").addScalar("city").addScalar("wrong_pwd_attempt")
					.addScalar("pwd_status").addScalar("state").addScalar("mpin_wrong_attempt").addScalar("rights")
					.addScalar("transMaxLimit").addScalar("pkiStatus").addScalar("mobRegStatus")
					.addScalar("ibRegStatus").addScalar("personal_Phone").addScalar("dob").addScalar("pancardNumber")
					.addScalar("rrn").addScalar("corpRoleId").addScalar("aadharCardNo", StandardBasicTypes.STRING)
					.addScalar("passport", StandardBasicTypes.STRING)// .addScalar("passportNo")
					.addScalar("boardResolution", StandardBasicTypes.STRING)
					.addScalar("user_image", StandardBasicTypes.STRING).addScalar("user_type")
					.addScalar("otherDoc", StandardBasicTypes.STRING)
					.addScalar("certificate_incorporation", StandardBasicTypes.STRING).addScalar("designation")
					.addScalar("parentRrn").addScalar("statusid").addScalar("createdon").addScalar("updatedOn")
					.addScalar("updatedby").addScalar("corpRoleName").addScalar("parentRoleName")
					.addScalar("parentRoleId").addScalar("parentUserName").addScalar("remark").addScalar("ogstatus")
					.addScalar("statusName").setParameter("compId", corpuserReqData.getCorp_comp_id())
					.setParameterList("statusId", corpuserReqData.getStatusList())
					.setResultTransformer(Transformers.aliasToBean(CorpUserEntityBean.class)).list();
			if (isDecrypted) {
				for (CorpUserEntityBean corpData : list) {
					corpData.setPersonal_Phone(EncryptorDecryptor.decryptData(corpData.getPersonal_Phone()));
					corpData.setUser_name(EncryptorDecryptor.decryptData(corpData.getUser_name()));
					corpData.setEmail_id(EncryptorDecryptor.decryptData(corpData.getEmail_id()));
					corpData.setDob(EncryptorDecryptor.decryptData(corpData.getDob()));
					corpData.setPancardNumber(EncryptorDecryptor.decryptData(corpData.getPancardNumber()));
					if (!ObjectUtils.isEmpty(corpData.getPassport())) {
						corpData.setPassport(EncryptorDecryptor.decryptData(corpData.getPassport()));
					} else {
						corpData.setPassport("");
					}
					corpData.setAadharCardNo(EncryptorDecryptor.decryptData(corpData.getAadharCardNo()));
				}
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return list;
	}

	@Override
	public List<CorpUserEntityBean> getAllCorpUsersByCompIdBean(CorpUserEntityBean corpuserReqData,
			boolean isDecrypted) {
		logger.info("getAllCorpUsersByCompId service request.........CORP_ID" + corpuserReqData.getCorp_comp_id()
				+ "|statusId:" + corpuserReqData.getStatusList());
		List<CorpUserEntityBean> list = null;
		try {

			String sqlQuery = "select cu.id,cu.corp_comp_id, cu.user_name,cu.user_pwd,cu.first_name,cu.last_name,cu.email_id,cu.parentId, "
					+ "cu.user_disp_name, cu.createdby, cu.appid, cu.country, cu.work_phone, cu.tpin_wrong_attempt, cu.city, cu.wrong_pwd_attempt, "
					+ "cu.pwd_status, cu.state, cu.mpin_wrong_attempt, cu.rights,cu.MAXLIMIT as transMaxLimit, cu.pkistatus, cu.mobRegStatus, cu.ibRegStatus,"
					+ "	cu.personal_Phone, cu.dob,cu.pancardNumber, cu.rrn as rrn,cu.corpRoleId, cu.aadharCardNo, "
					+ "	cu.passport, cu.boardResolution, cu.user_image,cu.user_type as user_type,  "
					+ "	cu.otherDoc as otherDoc,cu.certificate_incorporation, cu.designation,cu.parentRrn, "
					+ "	cu.statusid, cu.createdon, cu.updatedOn,cu.updatedby as updatedby,cr.name as corpRoleName, cc.name as parentRoleName, "
					+ " cu.parentRoleId as parentRoleId, cu.parentUserName as parentUserName, cu.remarks as remark, cu.og_status as ogstatus, s.name as statusName from CORP_USERS  cu  "
					+ " left join corp_roles cr on cr.id=cu.corpRoleId  left join corp_roles cc on cc.id=cu.parentRoleId "
					+ "inner join STATUSMASTER s on s.id=cu.og_status where cu.corp_comp_id=:compId AND cu.statusId in :statusId order by cu.id";
			logger.info("getAllCorpUsersByCompId service query: " + sqlQuery);
			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("corp_comp_id")
					.addScalar("user_name").addScalar("user_pwd", StandardBasicTypes.STRING).addScalar("first_name")
					.addScalar("last_name").addScalar("email_id").addScalar("parentId").addScalar("user_disp_name")
					.addScalar("createdby").addScalar("appid").addScalar("country").addScalar("work_phone")
					.addScalar("tpin_wrong_attempt").addScalar("city").addScalar("wrong_pwd_attempt")
					.addScalar("pwd_status").addScalar("state").addScalar("mpin_wrong_attempt").addScalar("rights")
					.addScalar("transMaxLimit").addScalar("pkiStatus").addScalar("mobRegStatus")
					.addScalar("ibRegStatus").addScalar("personal_Phone").addScalar("dob").addScalar("pancardNumber")
					.addScalar("rrn").addScalar("corpRoleId").addScalar("aadharCardNo", StandardBasicTypes.STRING)
					.addScalar("passport", StandardBasicTypes.STRING)// .addScalar("passportNo")
					.addScalar("boardResolution", StandardBasicTypes.STRING)
					.addScalar("user_image", StandardBasicTypes.STRING).addScalar("user_type")
					.addScalar("otherDoc", StandardBasicTypes.STRING)
					.addScalar("certificate_incorporation", StandardBasicTypes.STRING).addScalar("designation")
					.addScalar("parentRrn").addScalar("statusid").addScalar("createdon").addScalar("updatedOn")
					.addScalar("updatedby").addScalar("corpRoleName").addScalar("parentRoleName")
					.addScalar("parentRoleId").addScalar("parentUserName").addScalar("remark").addScalar("ogstatus")
					.addScalar("statusName").setParameter("compId", corpuserReqData.getCorp_comp_id())
					.setParameterList("statusId", corpuserReqData.getStatusList())
					.setResultTransformer(Transformers.aliasToBean(CorpUserEntityBean.class)).list();
			if (isDecrypted) {
				for (CorpUserEntityBean corpData : list) {
					corpData.setPersonal_Phone(EncryptorDecryptor.decryptData(corpData.getPersonal_Phone()));
					corpData.setUser_name(EncryptorDecryptor.decryptData(corpData.getUser_name()));
					corpData.setEmail_id(EncryptorDecryptor.decryptData(corpData.getEmail_id()));
					corpData.setDob(EncryptorDecryptor.decryptData(corpData.getDob()));
					corpData.setPancardNumber(EncryptorDecryptor.decryptData(corpData.getPancardNumber()));
					if (!ObjectUtils.isEmpty(corpData.getPassport())) {
						corpData.setPassport(EncryptorDecryptor.decryptData(corpData.getPassport()));
					} else {
						corpData.setPassport("");
					}
					corpData.setAadharCardNo(EncryptorDecryptor.decryptData(corpData.getAadharCardNo()));
				}
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return list;
	}

	@Override
	public List<CorpUserEntity> getAllCorpUsersByUserName(List<String> userList, BigDecimal corpCompId) {
		logger.info(
				"getAllCorpUsersByUserName service request.........userList" + userList + "|corpCompId:" + corpCompId);
		List<CorpUserEntity> list = null;
		try {

			String sqlQuery = "select cu.id as id, cu.CORP_COMP_ID as corp_comp_id, cu.USER_DISP_NAME as user_disp_name, cu.USER_NAME as user_name, cu.USER_PWD as user_pwd, "
					+ "cu.CREATEDBY as createdby, cu.CREATEDON as createdon, cu.STATUSID as statusid, cu.APPID as appid, cu.LASTLOGINTIME as lastLoginTime, cu.USER_TYPE as user_type,"
					+ "cu.FIRST_NAME as first_name, cu.LAST_NAME as last_name, cu.EMAIL_ID as email_id, cu.COUNTRY as country, cu.WORK_PHONE as work_phone, cu.PERSONAL_PHONE as personal_Phone,"
					+ "cu.NATIONALID as nationalId, cu.PASSPORT as passport, cu.AadharcardNo as aadharCardNo, cu.BOARDRESOLUTION as boardResolution, cu.USER_IMAGE as user_image, cu.TPIN as tpin, "
					+ "cu.PASSPORTNUMBER as passportNumber, cu.NATIONALIDNUMBER as nationalIdNumber, cu.TPIN_STATUS as tpin_status, cu.RIGHTS as rights, cu.PKISTATUS as pkiStatus,"
					+ "cu.TPIN_WRONG_ATTEMPT as tpin_wrong_attempt, cu.CITY as city, cu.WRONG_PWD_ATTEMPT as wrong_pwd_attempt, cu.PWD_STATUS as pwd_status, cu.OTHERDOC as otherDoc, "
					+ "cu.CERTIFICATE_INCORPORATION as certificate_incorporation, cu.STATE as state, cu.DESIGNATION as designation, cu.LAST_TPIN_WRONG_ATTEMPT as last_tpin_wrong_attempt,"
					+ "cu.LAST_PWD_WRONG_ATTEMPT as last_pwd_wrong_attempt, cu.MPIN as mpin, cu.MPIN_WRONG_ATTEMPT as mpin_wrong_attempt, cu.LAST_MPIN_WRONG_ATTEMPT as last_mpin_wrong_attempt,"
					+ "cu.PANCARD as pancard, cu.PANCARDNUMBER as pancardNumber, cu.UPDATEDBY as updatedby, cu.MAXLIMIT as transMaxLimit, cu.UPDATEDON as updatedOn, cu.MOBREGSTATUS as mobRegStatus,"
					+ "cu.IBREGSTATUS as ibRegStatus, cu.Parentroleid as parentRoleId, cu.Parentid as parentId, cu.DOB as dob, cu.Parentusername as parentUserName, cu.Corproleid as corpRoleId,"
					// TODO uncomment code for enable delete functionality
					+ "cu.RRN as rrn, cu.ParentRRN as parentRrn, cu.remarks as remark, cu.WRONGATTEMPTSSOFTTOKEN as wrongAttemptSoftToken, s.NAME statusName,cu.og_status as ogstatus "
					+ "from CORP_USERS cu inner join STATUSMASTER s on s.id=cu.STATUSID left join corp_roles cr on cr.id=cu.corpRoleId left join corp_roles cc on cc.id=cu.parentRoleId where cu.user_name in :userName and cu.corp_comp_id=:corpCompId";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("corp_comp_id")
					.addScalar("user_disp_name").addScalar("user_name").addScalar("user_pwd").addScalar("createdby")
					.addScalar("createdon").addScalar("statusid").addScalar("appid").addScalar("lastLoginTime")
					.addScalar("user_type").addScalar("first_name").addScalar("last_name").addScalar("email_id")
					.addScalar("country").addScalar("work_phone").addScalar("personal_Phone").addScalar("nationalId")
					.addScalar("passport", StandardBasicTypes.STRING).addScalar("aadharCardNo")
					.addScalar("boardResolution").addScalar("user_image").addScalar("tpin", StandardBasicTypes.STRING)
					.addScalar("passportNumber").addScalar("nationalIdNumber").addScalar("tpin_status")
					.addScalar("rights").addScalar("pkiStatus").addScalar("tpin_wrong_attempt").addScalar("city")
					.addScalar("wrong_pwd_attempt").addScalar("pwd_status").addScalar("otherDoc")
					.addScalar("certificate_incorporation").addScalar("state").addScalar("designation")
					.addScalar("last_tpin_wrong_attempt").addScalar("last_pwd_wrong_attempt").addScalar("mpin")
					.addScalar("mpin_wrong_attempt").addScalar("last_mpin_wrong_attempt").addScalar("pancard")
					.addScalar("pancardNumber").addScalar("updatedby").addScalar("transMaxLimit").addScalar("updatedOn")
					.addScalar("mobRegStatus").addScalar("ibRegStatus").addScalar("parentRoleId").addScalar("parentId")
					.addScalar("dob").addScalar("parentUserName").addScalar("corpRoleId").addScalar("rrn")
					.addScalar("remark").addScalar("wrongAttemptSoftToken").addScalar("statusName")
					// TODO uncomment code for enable delete functionality
					.addScalar("ogstatus").setParameterList("userName", userList).setParameter("corpCompId", corpCompId)
					.setResultTransformer(Transformers.aliasToBean(CorpUserEntity.class)).list();
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return list;
	}

	@Override
	public List<CorpUserEntityTmp> getAllCorpUsersByUserNameTmp(List<String> userList, BigDecimal corpCompId) {
		logger.info("getAllCorpUsersByUserNameTmp service request.........userList" + userList + "|corpCompId:"
				+ corpCompId);
		List<CorpUserEntityTmp> list = null;
		try {

			String sqlQuery = "select cu.id as id, cu.CORP_COMP_ID as corp_comp_id, cu.USER_DISP_NAME as user_disp_name, cu.USER_NAME as user_name, cu.USER_PWD as user_pwd, "
					+ "cu.CREATEDBY as createdby, cu.CREATEDON as createdon, cu.STATUSID as statusid, cu.APPID as appid, cu.LASTLOGINTIME as lastLoginTime, cu.USER_TYPE as user_type,"
					+ "cu.FIRST_NAME as first_name, cu.LAST_NAME as last_name, cu.EMAIL_ID as email_id, cu.COUNTRY as country, cu.WORK_PHONE as work_phone, cu.PERSONAL_PHONE as personal_Phone,"
					+ "cu.NATIONALID as nationalId, cu.PASSPORT as passport, cu.AadharcardNo as aadharCardNo, cu.BOARDRESOLUTION as boardResolution, cu.USER_IMAGE as user_image, cu.TPIN as tpin, "
					+ "cu.PASSPORTNUMBER as passportNumber, cu.NATIONALIDNUMBER as nationalIdNumber, cu.TPIN_STATUS as tpin_status, cu.RIGHTS as rights, cu.PKISTATUS as pkiStatus,"
					+ "cu.TPIN_WRONG_ATTEMPT as tpin_wrong_attempt, cu.CITY as city, cu.WRONG_PWD_ATTEMPT as wrong_pwd_attempt, cu.PWD_STATUS as pwd_status, cu.OTHERDOC as otherDoc, "
					+ "cu.CERTIFICATE_INCORPORATION as certificate_incorporation, cu.STATE as state, cu.DESIGNATION as designation, cu.LAST_TPIN_WRONG_ATTEMPT as last_tpin_wrong_attempt,"
					+ "cu.LAST_PWD_WRONG_ATTEMPT as last_pwd_wrong_attempt, cu.MPIN as mpin, cu.MPIN_WRONG_ATTEMPT as mpin_wrong_attempt, cu.LAST_MPIN_WRONG_ATTEMPT as last_mpin_wrong_attempt,"
					+ "cu.PANCARD as pancard, cu.PANCARDNUMBER as pancardNumber, cu.UPDATEDBY as updatedby, cu.MAXLIMIT as transMaxLimit, cu.UPDATEDON as updatedOn, cu.MOBREGSTATUS as mobRegStatus,"
					+ "cu.IBREGSTATUS as ibRegStatus, cu.Parentroleid as parentRoleId, cu.Parentid as parentId, cu.DOB as dob, cu.Parentusername as parentUserName, cu.Corproleid as corpRoleId,"
					+ "cu.RRN as rrn, cu.ParentRRN as parentRrn, cu.remarks as remark, cu.WRONGATTEMPTSSOFTTOKEN as wrongAttemptSoftToken, s.NAME statusName "
					+ "from CORP_USERS_TMP cu inner join STATUSMASTER s on s.id=cu.STATUSID left join corp_roles cr on cr.id=cu.corpRoleId left join corp_roles cc on cc.id=cu.parentRoleId where cu.user_name in :userName and cu.corp_comp_id=:corpCompId";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("corp_comp_id")
					.addScalar("user_disp_name").addScalar("user_name").addScalar("user_pwd").addScalar("createdby")
					.addScalar("createdon").addScalar("statusid").addScalar("appid").addScalar("lastLoginTime")
					.addScalar("user_type").addScalar("first_name").addScalar("last_name").addScalar("email_id")
					.addScalar("country").addScalar("work_phone").addScalar("personal_Phone").addScalar("nationalId")
					.addScalar("passport", StandardBasicTypes.STRING).addScalar("aadharCardNo")
					.addScalar("boardResolution").addScalar("user_image").addScalar("tpin").addScalar("passportNumber")
					.addScalar("nationalIdNumber").addScalar("tpin_status").addScalar("rights").addScalar("pkiStatus")
					.addScalar("tpin_wrong_attempt").addScalar("city").addScalar("wrong_pwd_attempt")
					.addScalar("pwd_status").addScalar("otherDoc").addScalar("certificate_incorporation")
					.addScalar("state").addScalar("designation").addScalar("last_tpin_wrong_attempt")
					.addScalar("last_pwd_wrong_attempt").addScalar("mpin").addScalar("mpin_wrong_attempt")
					.addScalar("last_mpin_wrong_attempt").addScalar("pancard").addScalar("pancardNumber")
					.addScalar("updatedby").addScalar("transMaxLimit").addScalar("updatedOn").addScalar("mobRegStatus")
					.addScalar("ibRegStatus").addScalar("parentRoleId").addScalar("parentId").addScalar("dob")
					.addScalar("parentUserName").addScalar("corpRoleId").addScalar("rrn").addScalar("remark")
					.addScalar("wrongAttemptSoftToken").addScalar("statusName").setParameterList("userName", userList)
					.setParameter("corpCompId", corpCompId)
					.setResultTransformer(Transformers.aliasToBean(CorpUserEntityTmp.class)).list();
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return list;
	}

	@Override
	public List<CorpUserEntity> getAllCorpUsersByCompanyId(BigDecimal corpCompId) {
		logger.info("getAllCorpUsersByCompanyIdTmp service request.........corpCompId" + corpCompId);
		List<CorpUserEntity> list = null;
		try {

			String sqlQuery = "select cu.id as id, cu.CORP_COMP_ID as corp_comp_id, cu.USER_DISP_NAME as user_disp_name, cu.USER_NAME as user_name, cu.USER_PWD as user_pwd, "
					+ "cu.CREATEDBY as createdby, cu.CREATEDON as createdon, cu.STATUSID as statusid, cu.APPID as appid, cu.LASTLOGINTIME as lastLoginTime, cu.USER_TYPE as user_type,"
					+ "cu.FIRST_NAME as first_name, cu.LAST_NAME as last_name, cu.EMAIL_ID as email_id, cu.COUNTRY as country, cu.WORK_PHONE as work_phone, cu.PERSONAL_PHONE as personal_Phone,"
					+ "cu.NATIONALID as nationalId, cu.PASSPORT as passport, cu.AadharcardNo as aadharCardNo, cu.BOARDRESOLUTION as boardResolution, cu.USER_IMAGE as user_image, cu.TPIN as tpin, "
					+ "cu.PASSPORTNUMBER as passportNumber, cu.NATIONALIDNUMBER as nationalIdNumber, cu.TPIN_STATUS as tpin_status, cu.RIGHTS as rights, cu.PKISTATUS as pkiStatus,"
					+ "cu.TPIN_WRONG_ATTEMPT as tpin_wrong_attempt, cu.CITY as city, cu.WRONG_PWD_ATTEMPT as wrong_pwd_attempt, cu.PWD_STATUS as pwd_status, cu.OTHERDOC as otherDoc, "
					+ "cu.CERTIFICATE_INCORPORATION as certificate_incorporation, cu.STATE as state, cu.DESIGNATION as designation, cu.LAST_TPIN_WRONG_ATTEMPT as last_tpin_wrong_attempt,"
					+ "cu.LAST_PWD_WRONG_ATTEMPT as last_pwd_wrong_attempt, cu.MPIN as mpin, cu.MPIN_WRONG_ATTEMPT as mpin_wrong_attempt, cu.LAST_MPIN_WRONG_ATTEMPT as last_mpin_wrong_attempt,"
					+ "cu.PANCARD as pancard, cu.PANCARDNUMBER as pancardNumber, cu.UPDATEDBY as updatedby, cu.MAXLIMIT as transMaxLimit, cu.UPDATEDON as updatedOn, cu.MOBREGSTATUS as mobRegStatus,"
					+ "cu.IBREGSTATUS as ibRegStatus, cu.Parentroleid as parentRoleId, cu.Parentid as parentId, cu.DOB as dob, cu.Parentusername as parentUserName, cu.Corproleid as corpRoleId,"
					+ "cu.RRN as rrn, cu.ParentRRN as parentRrn, cu.remarks as remark, cu.WRONGATTEMPTSSOFTTOKEN as wrongAttemptSoftToken,cu.og_status as ogstatus, s.NAME statusName "
					+ "from CORP_USERS cu inner join STATUSMASTER s on s.id=cu.STATUSID left join corp_roles cr on cr.id=cu.corpRoleId left join corp_roles cc on cc.id=cu.parentRoleId "
					+ "where cu.corp_comp_id=:corpCompId";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("corp_comp_id")
					.addScalar("user_disp_name").addScalar("user_name").addScalar("user_pwd").addScalar("createdby")
					.addScalar("createdon").addScalar("statusid").addScalar("appid").addScalar("lastLoginTime")
					.addScalar("user_type").addScalar("first_name").addScalar("last_name").addScalar("email_id")
					.addScalar("country").addScalar("work_phone").addScalar("personal_Phone").addScalar("nationalId")
					.addScalar("passport", StandardBasicTypes.STRING).addScalar("aadharCardNo")
					.addScalar("boardResolution").addScalar("user_image").addScalar("tpin", StandardBasicTypes.STRING)
					.addScalar("passportNumber").addScalar("nationalIdNumber").addScalar("tpin_status")
					.addScalar("rights").addScalar("pkiStatus").addScalar("tpin_wrong_attempt").addScalar("city")
					.addScalar("wrong_pwd_attempt").addScalar("pwd_status").addScalar("otherDoc")
					.addScalar("certificate_incorporation").addScalar("state").addScalar("designation")
					.addScalar("last_tpin_wrong_attempt").addScalar("last_pwd_wrong_attempt")
					.addScalar("mpin", StandardBasicTypes.STRING).addScalar("mpin_wrong_attempt")
					.addScalar("last_mpin_wrong_attempt").addScalar("pancard").addScalar("pancardNumber")
					.addScalar("updatedby").addScalar("transMaxLimit").addScalar("updatedOn").addScalar("mobRegStatus")
					.addScalar("ibRegStatus").addScalar("parentRoleId").addScalar("parentId").addScalar("dob")
					.addScalar("parentUserName").addScalar("corpRoleId").addScalar("rrn").addScalar("remark")
					.addScalar("wrongAttemptSoftToken").addScalar("ogstatus").addScalar("statusName")
					.setParameter("corpCompId", corpCompId)
					.setResultTransformer(Transformers.aliasToBean(CorpUserEntity.class)).list();
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return list;
	}

	@Override
	public List<CorpUserEntityTmp> getAllCorpUsersByCompIdTemp(CorpUserEntityTmp corpuserReqData, boolean isDecrypted) {
		logger.info("getAllCorpUsersByCompIdTemp service request.........corpCompId" + corpuserReqData.getCorp_comp_id()
				+ "|StatusList" + corpuserReqData.getStatusList());
		List<CorpUserEntityTmp> list = null;
		try {

			String sqlQuery = "select cu.id,cu.corp_comp_id, cu.user_name,cu.user_pwd,cu.first_name,cu.last_name,cu.email_id,cu.parentId, "
					+ "cu.user_disp_name, cu.createdby, cu.appid, cu.country, cu.work_phone, cu.tpin_wrong_attempt, cu.city, cu.wrong_pwd_attempt, "
					+ "cu.pwd_status, cu.state, cu.mpin_wrong_attempt, cu.rights,cu.MAXLIMIT as transMaxLimit, cu.pkistatus, cu.mobRegStatus, cu.ibRegStatus,"
					+ "	cu.personal_Phone, cu.dob,cu.pancardNumber, cu.rrn as rrn,cu.corpRoleId, cu.aadharCardNo, "
					+ "	cu.passport, cu.boardResolution, cu.user_image,cu.user_type as user_type,  "
					+ "	cu.otherDoc as otherDoc,cu.certificate_incorporation, cu.designation,cu.parentRrn, "
					+ "	cu.statusid, cu.createdon, cu.updatedOn,cu.updatedby as updatedby,cr.name as corpRoleName, cc.name as parentRoleName, "
					+ " cu.parentRoleId as parentRoleId, cu.parentUserName as parentUserName from CORP_USERS_TMP  cu  "
					+ " left join corp_roles cr on cr.id=cu.corpRoleId    left join corp_roles cc on cc.id=cu.parentRoleId where cu.corp_comp_id=:compId AND cu.statusId in :statusId order by cu.id";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("corp_comp_id")
					.addScalar("user_name").addScalar("user_pwd", StandardBasicTypes.STRING).addScalar("first_name")
					.addScalar("last_name").addScalar("email_id").addScalar("parentId").addScalar("user_disp_name")
					.addScalar("createdby").addScalar("appid").addScalar("country").addScalar("work_phone")
					.addScalar("tpin_wrong_attempt").addScalar("city").addScalar("wrong_pwd_attempt")
					.addScalar("pwd_status").addScalar("state").addScalar("mpin_wrong_attempt").addScalar("rights")
					.addScalar("transMaxLimit").addScalar("pkiStatus").addScalar("mobRegStatus")
					.addScalar("ibRegStatus").addScalar("personal_Phone").addScalar("dob").addScalar("pancardNumber")
					.addScalar("rrn").addScalar("corpRoleId").addScalar("aadharCardNo", StandardBasicTypes.STRING)
					.addScalar("passport", StandardBasicTypes.STRING)// .addScalar("passportNo")
					.addScalar("boardResolution", StandardBasicTypes.STRING)
					.addScalar("user_image", StandardBasicTypes.STRING).addScalar("user_type")
					.addScalar("otherDoc", StandardBasicTypes.STRING)
					.addScalar("certificate_incorporation", StandardBasicTypes.STRING).addScalar("parentRrn")
					.addScalar("statusid").addScalar("createdon").addScalar("updatedOn").addScalar("updatedby")
					.addScalar("corpRoleName").addScalar("parentRoleName").addScalar("parentRoleId")
					.addScalar("parentUserName").setParameter("compId", corpuserReqData.getCorp_comp_id())
					.setParameterList("statusId", corpuserReqData.getStatusList())
					.setResultTransformer(Transformers.aliasToBean(CorpUserEntityTmp.class)).list();
			if (isDecrypted) {
				for (CorpUserEntityTmp corpData : list) {
					corpData.setPersonal_Phone(EncryptorDecryptor.decryptData(corpData.getPersonal_Phone()));
					corpData.setUser_name(EncryptorDecryptor.decryptData(corpData.getUser_name()));
					corpData.setEmail_id(EncryptorDecryptor.decryptData(corpData.getEmail_id()));
					corpData.setDob(EncryptorDecryptor.decryptData(corpData.getDob()));
					corpData.setPancardNumber(EncryptorDecryptor.decryptData(corpData.getPancardNumber()));
					if (null != corpData.getPassport() && "" != corpData.getPassport()) {
						corpData.setPassport(EncryptorDecryptor.decryptData(corpData.getPassport()));
					} else {
						corpData.setPassport("");
					}
					corpData.setAadharCardNo(EncryptorDecryptor.decryptData(corpData.getAadharCardNo()));

				}
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return list;
	}

	@Override
	public List<CorpUserMenuMapEntity> getUserMenuListByCorpCompId(BigInteger companyId) {
		logger.info("getUserMenuListByCorpCompId service request.........companyId" + companyId);
		List<CorpUserMenuMapEntity> list = null;
		try {

			String sqlQuery = "select cm.id as id, cm.corpCompId as corpCompId,cm.corpMenuId as corpMenuId, cm.corpUserId as corpUserId,"
					+ "cm.statusId as statusId, cc.menuName as menuName, cm.userRrn as userRrn from CORPUSER_MENU_MAP cm inner join corp_menu cc on cc.id =cm.corpMenuId"
					+ "    where cm.Corpcompid =:compId and cm.statusid = 3";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("corpCompId").addScalar("corpMenuId")
					.addScalar("corpUserId").addScalar("statusId").addScalar("menuName").addScalar("userRrn")
					.setParameter("compId", companyId)
					.setResultTransformer(Transformers.aliasToBean(CorpUserMenuMapEntity.class)).list();
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return list;
	}

	public List<CorpUserMenuMapEntityTmp> getUserMenuListByCorpCompIdTmp(BigInteger companyId) {
		logger.info("getUserMenuListByCorpCompIdTmp service request.........companyId" + companyId);
		List<CorpUserMenuMapEntityTmp> list = null;
		try {
			String sqlQuery = "select cm.id as id, cm.corpCompId as corpCompId,cm.corpMenuId as corpMenuId, cm.corpUserId as corpUserId,"
					+ "cm.statusId as statusId, cc.menuName as menuName, cm.userRrn as userRrn from CORPUSER_MENU_MAP_TMP cm inner join corp_menu cc on cc.id =cm.corpMenuId"
					+ "    where cm.Corpcompid =:compId";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("corpCompId").addScalar("corpMenuId")
					.addScalar("corpUserId").addScalar("statusId").addScalar("menuName").addScalar("userRrn")
					.setParameter("compId", companyId)
					.setResultTransformer(Transformers.aliasToBean(CorpUserMenuMapEntityTmp.class)).list();
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return list;
	}

	@Override
	public List<CorpUserMenuMapBean> getUserMenuListByCorpCompIdBeanTmp(BigInteger companyId) {
		logger.info("getUserMenuListByCorpCompIdTmp service request.........companyId" + companyId);
		List<CorpUserMenuMapBean> list = null;
		try {
			String sqlQuery = "select cm.id as id, cm.corpCompId as corpCompId,cm.corpMenuId as corpMenuId,NVL(cm.corpSubMenuId, 0) as corpSubMenuId, cm.corpUserId as corpUserId,"
					+ "cm.statusId as statusId, cc.menuName as menuName,NVL(csm.menuname, 'NA') as subMenuName, cm.userRrn as userRrn from CORPUSER_MENU_MAP_TMP cm left outer join corp_menu cc on cc.id =cm.corpMenuId"
					+ " left outer join corp_submenu csm on csm.id =cm.corpSubMenuId where cm.Corpcompid =:compId";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("corpCompId").addScalar("corpMenuId")
					.addScalar("corpSubMenuId").addScalar("corpUserId").addScalar("statusId").addScalar("menuName")
					.addScalar("subMenuName").addScalar("userRrn").setParameter("compId", companyId)
					.setResultTransformer(Transformers.aliasToBean(CorpUserMenuMapBean.class)).list();
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return list;
	}

	@Override
	public List<CorpUserMenuMapBean> getUserMenuListByCorpCompIdBean(BigInteger companyId) {
		logger.info("getUserMenuListByCorpCompIdTmp service request.........companyId" + companyId);
		List<CorpUserMenuMapBean> list = null;
		try {
			String sqlQuery = "select cm.id as id, cm.corpCompId as corpCompId,cm.corpMenuId as corpMenuId,NVL(cm.corpSubMenuId, 0) as corpSubMenuId, cm.corpUserId as corpUserId,"
					+ "cm.statusId as statusId, cc.menuName as menuName,NVL(csm.menuname, 'NA') as subMenuName, cm.userRrn as userRrn from CORPUSER_MENU_MAP cm left outer join corp_menu cc on cc.id =cm.corpMenuId "
					+ "left outer join corp_submenu csm on csm.id =cm.corpSubMenuId where cm.Corpcompid =:compId and cm.statusid = 3";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("corpCompId").addScalar("corpMenuId")
					.addScalar("corpSubMenuId").addScalar("corpUserId").addScalar("statusId").addScalar("menuName")
					.addScalar("subMenuName").addScalar("userRrn").setParameter("compId", companyId)
					.setResultTransformer(Transformers.aliasToBean(CorpUserMenuMapBean.class)).list();
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return list;
	}

	@Override
	public List<CorpUserMenuMapBean> getUserMenuListByCorpUserIdTmp(BigInteger userId) {
		logger.info("getUserMenuListByCorpUserIdTmp service request.........userId" + userId);
		List<CorpUserMenuMapBean> list = null;
		try {
			String sqlQuery = "select cm.id as id, cm.corpCompId as corpCompId,cm.corpMenuId as corpMenuId,NVL(cm.corpSubMenuId, 0) as corpSubMenuId, cm.corpUserId as corpUserId,"
					+ "cm.statusId as statusId, cc.menuName as menuName,NVL(csm.menuname, 'NA') as subMenuName, cm.userRrn as userRrn from CORPUSER_MENU_MAP_TMP cm left outer join corp_menu cc on cc.id =cm.corpMenuId"
					+ " left outer join join corp_submenu csm on csm.id =cm.corpSubMenuId   where cm.corpUserId =:userId";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("corpCompId").addScalar("corpMenuId")
					.addScalar("corpSubMenuId").addScalar("corpUserId").addScalar("statusId").addScalar("subMenuName")
					.addScalar("userRrn").setParameter("userId", userId)
					.setResultTransformer(Transformers.aliasToBean(CorpUserMenuMapBean.class)).list();
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return list;
	}

	@Override
	public List<CorpUserAccMapEntity> getUserAccountListByCorpCompId(BigInteger companyId) {
		logger.info("getUserAccountListByCorpCompId service request.........CompanyId" + companyId);
		List<CorpUserAccMapEntity> list = null;
		try {

			String sqlQuery = "select ca.id as id, ca.corpCompId as corpCompId, ca.accountNo as accountNo,ca.corpUserId as corpUserId, ca.statusId as statusId, ca.userRrn as userRrn "
					+ "from CORPUSER_ACC_MAP ca where ca.Corpcompid =:compId and ca.statusid = 3";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("corpCompId").addScalar("accountNo")
					.addScalar("corpUserId").addScalar("statusId").addScalar("userRrn")
					.setParameter("compId", companyId)
					.setResultTransformer(Transformers.aliasToBean(CorpUserAccMapEntity.class)).list();
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return list;
	}

	@Override
	public List<CorpUserAccMapEntityTmp> getUserAccountListByCorpCompIdTmp(BigInteger companyId) {
		logger.info("getUserAccountListByCorpCompIdTmp service request.........CompanyId" + companyId);
		List<CorpUserAccMapEntityTmp> list = null;
		try {

			String sqlQuery = "select ca.id as id, ca.corpCompId as corpCompId, ca.accountNo as accountNo,ca.corpUserId as corpUserId, ca.statusId as statusId, ca.userRrn as userRrn "
					+ "from CORPUSER_ACC_MAP_TMP ca where ca.Corpcompid =:compId";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("corpCompId").addScalar("accountNo")
					.addScalar("corpUserId").addScalar("statusId").addScalar("userRrn")
					.setParameter("compId", companyId)
					.setResultTransformer(Transformers.aliasToBean(CorpUserAccMapEntityTmp.class)).list();
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return list;
	}

	@Override
	public List<CorpUserAccMapBean> getUserAccountListByCorpCompIdBeanTmp(BigInteger companyId) {
		logger.info("getUserAccountListByCorpCompIdTmp service request.........CompanyId" + companyId);
		List<CorpUserAccMapBean> list = null;
		try {

			String sqlQuery = "select ca.id as id, ca.corpCompId as corpCompId, ca.accountNo as accountNo,ca.corpUserId as corpUserId, ca.statusId as statusId, ca.userRrn as userRrn "
					+ "from CORPUSER_ACC_MAP_TMP ca where ca.Corpcompid =:compId";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("corpCompId").addScalar("accountNo")
					.addScalar("corpUserId").addScalar("statusId").addScalar("userRrn")
					.setParameter("compId", companyId)
					.setResultTransformer(Transformers.aliasToBean(CorpUserAccMapBean.class)).list();
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return list;
	}

	@Override
	public List<CorpUserAccMapBean> getUserAccountListByCorpCompIdBean(BigInteger companyId) {
		logger.info("getUserAccountListByCorpCompIdTmp service request.........CompanyId" + companyId);
		List<CorpUserAccMapBean> list = null;
		try {

			String sqlQuery = "select ca.id as id, ca.corpCompId as corpCompId, ca.accountNo as accountNo,ca.corpUserId as corpUserId, ca.statusId as statusId, ca.userRrn as userRrn "
					+ "from CORPUSER_ACC_MAP ca where ca.Corpcompid =:compId and ca.statusid = 3";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("corpCompId").addScalar("accountNo")
					.addScalar("corpUserId").addScalar("statusId").addScalar("userRrn")
					.setParameter("compId", companyId)
					.setResultTransformer(Transformers.aliasToBean(CorpUserAccMapBean.class)).list();
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return list;
	}

	@Override
	public ResponseMessageBean saveOfflineCorpSingleUserMasterData(CorpDataBean corpData) {
		logger.info("saveOfflineCorpSingleUserMasterData service request.........");
		ResponseMessageBean responseMessageBean = new ResponseMessageBean();
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		List<Integer> statusList = new ArrayList<>();

		// update company status record Inactive to Pending Checker
		CorpCompanyMasterEntity corpCompanyMasterData = new CorpCompanyMasterEntity();
		if (!ObjectUtils.isEmpty(corpData.getCorpUserMasterData().get(0).getStatusid())) {
			statusList.add(corpData.getCorpUserMasterData().get(0).getStatusid().intValue());
		} else {
			statusList.add(0);
		}
		logger.info("user Status: " + statusList.toArray().toString());
		try {
			for (CorpUserEntity corpUser : corpData.getCorpUserMasterData()) {
				String[] corpUserMenuList = null;
				String[] corpUserAccountList = null;
				corpUser.setCorp_comp_id(corpData.getCorpCompId());
				corpUser.setCreatedon(new Timestamp(System.currentTimeMillis()));
				corpUser.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
				corpUser.setRights(corpUser.getRights());
				corpUser.setPkiStatus(corpUser.getPkiStatus());
				corpUser.setStatusid(BigDecimal.valueOf(0));
				corpUser.setOgstatus(BigDecimal.valueOf(0));
				corpUser.setRights(corpUser.getRights());
				corpUser.setPkiStatus(corpUser.getPkiStatus());
				corpUser.setTransMaxLimit(corpUser.getTransMaxLimit());
				corpUser.setUser_pwd("");
				corpUser.setParentId(new BigDecimal(0));
				corpUser.setParentRoleId((new BigDecimal(0)));
				adminWorkFlowReqUtility.encryptCorpUserData(corpUser);

				corpUserMenuList = corpUser.getMenuList().split(",");
				corpUserAccountList = corpUser.getAccountList().split(",");

				// check is username and email exist and mobile exist
				ResponseMessageBean resp = new ResponseMessageBean();
				resp = isCompanyUserExist(corpUser);
				if (resp.getResponseCode().equalsIgnoreCase("200")) {
					corpUser.setRrn(String.valueOf(System.currentTimeMillis()));

					corpUser = saveCorpUserData(corpUser);

					saveToCorpUsersMenuMap(corpUserMenuList, corpUser.getCorp_comp_id(), corpUser.getId(),
							corpUser.getRrn(), corpUser.getUpdatedby());
					saveToCorpUsersAccMap(corpUserAccountList, corpUser.getCorp_comp_id(), corpUser.getId(),
							corpUser.getRrn(), corpUser.getUpdatedby());
					tx.commit();
				}
				Map<String, Object> tempStatus = validateHierarchy(
						BigDecimal.valueOf(corpData.getCorpUserMasterData().get(0).getCorp_comp_id().intValue()));
				if (!ObjectUtils.isEmpty(tempStatus)) {
					Integer statusCode = (Integer) tempStatus.get("statusCode");
					String statusMsg = tempStatus.get("statusMsg").toString();
					if (statusCode != 0) {
						responseMessageBean.setResponseCode("202");
						responseMessageBean.setResponseMessage(statusMsg);
						return responseMessageBean;
					} else {
						Session newSession = sessionFactory.getCurrentSession();
						Transaction newTx = newSession.beginTransaction();
						// update status of corpUsers
						List<Integer> userStatusList = new ArrayList<>();
						userStatusList.add(0);
						corpUser.setStatusid(BigDecimal.valueOf(36));
						newSession.update(corpUser);

						// UPDATE COMPANY FROM INACTIVE TO VERIFICATION PENDING
						corpCompanyMasterData.setBranchCode(corpData.getBranchCode());
						corpCompanyMasterData.setStatusList(userStatusList);
						corpCompanyMasterData.setId(corpData.getCorpUserMasterData().get(0).getCorp_comp_id());
						List<CorpCompanyMasterEntity> companyData = getOfflineCorpCompDataByIdNew(
								corpCompanyMasterData);
						companyData.get(0).setStatusId(BigDecimal.valueOf(36));
						companyData.get(0).setIsCorporate('G');
						newSession.saveOrUpdate(companyData.get(0));
						newTx.commit();

						responseMessageBean.setResponseMessage("Request Submitted Successfully For The Approver");
						responseMessageBean.setResponseCode("200");
					}
				}
			}
		} catch (Exception e) {
			logger.error("Exception:", e);
			responseMessageBean.setResponseCode("202");
			responseMessageBean.setResponseCode("Invalid Request");
			return responseMessageBean;
		}
		return responseMessageBean;
	}

	@Override
	public ResponseMessageBean saveOfflineCorpUserMasterData(CorpDataBean corpData) {
		logger.info("saveOfflineCorpUserMasterDataTemp service starting.........");
		BigDecimal regulatorId = BigDecimal.valueOf(0);
		BigDecimal userId = BigDecimal.valueOf(0);
		BigDecimal userParentId = BigDecimal.valueOf(0);
		String regulatorRRN = null;
		String userRRN = null;
		Map<String, BigDecimal> parentDataMap = new HashMap<>();
		String regulatorUserName = null;
		List<CorpUserEntity> corpUserList = new ArrayList<>();
		ResponseMessageBean responseMessageBean = new ResponseMessageBean();
		try {
			for (CorpUserEntity corpUser : corpData.getCorpUserMasterData()) {
				String[] corpUserMenuList = {};
				String[] corpUserAccountList = {};
				corpUser.setCreatedby(corpData.getCreatedByUpdatedBy());
				corpUser.setCorp_comp_id(corpData.getCorpCompId());
				corpUser.setCreatedon(new Timestamp(System.currentTimeMillis()));
				corpUser.setOgstatus(BigDecimal.valueOf(0));
				corpUser.setUser_pwd("");
				String nonEncUserName = corpUser.getUser_name().toLowerCase();
				adminWorkFlowReqUtility.encryptCorpUserData(corpUser);
				if (!ObjectUtils.isEmpty(corpUser.getMenuList())) {
					if (corpUser.getMenuList().contains(",")) {
						corpUserMenuList = corpUser.getMenuList().split(",");
					} else {
						corpUserMenuList = new String[] { "" + corpUser.getMenuList() };
					}
				}

				if (!ObjectUtils.isEmpty(corpUser.getAccountList())) {
					if (corpUser.getAccountList().contains(",")) {
						corpUserAccountList = corpUser.getAccountList().split(",");
					} else {
						corpUserAccountList = new String[] { "" + corpUser.getAccountList() };
					}
				}

				if (corpUser.getCorpRoleId().intValue() == 1) {
					corpUser.setRrn(String.valueOf(System.currentTimeMillis()));
					regulatorRRN = corpUser.getRrn();
					userRRN = corpUser.getRrn();
					corpUser.setParentRrn("0");
					corpUser.setParentRoleId(BigDecimal.valueOf(0));
					corpUser.setParentId(BigDecimal.valueOf(0));
					regulatorUserName = nonEncUserName;
					corpUser = saveCorpUserData(corpUser);
					corpUserList.add(corpUser);
					regulatorId = corpUser.getId();
					logger.info("Regulator CorpUserMenuList........." + corpUserMenuList);
					saveToCorpUsersMenuMap(corpUserMenuList, corpUser.getCorp_comp_id(), regulatorId, userRRN,
							corpUser.getCreatedby());
					logger.info("Regulator CorpUserAccountList........." + corpUserAccountList);
					saveToCorpUsersAccMap(corpUserAccountList, corpUser.getCorp_comp_id(), regulatorId, userRRN,
							corpData.getCreatedByUpdatedBy());

				} else if (corpUser.getCorpRoleId().intValue() == 2) {
					ResponseMessageBean response = new ResponseMessageBean();
					if (corpData.getUserTypes().equals("M") && corpData.getAdminTypes().equals("MA")) {
						logger.info("MultiAdmin.........");
						CorpUserEntity corpUserEntity = new CorpUserEntity();
						corpUserEntity.setCorp_comp_id(corpData.getCorpCompId());
						corpUserEntity.setUser_name(corpUser.getParentUserName());
						response = isCompanyUserExist(corpUserEntity);
						if ("500" == response.getResponseCode()) {
							corpUser.setParentId(new BigDecimal(response.getResult().toString()));
						} else {
							corpUser.setParentId(BigDecimal.valueOf(0));
						}
						logger.info("MultiAdmin ParentId........." + corpUser.getParentId());
					} else {
						logger.info("Single Admin.........ParentId" + BigDecimal.valueOf(0));
						corpUser.setParentId(BigDecimal.valueOf(0));
					}
					corpUser.setRrn(String.valueOf(System.currentTimeMillis()));
					userRRN = corpUser.getRrn();
					corpUser.setParentRrn(regulatorRRN);
					corpUser.setParentRoleId(BigDecimal.valueOf(1));
					if (regulatorUserName != null) {
						logger.info("Admin ParentUserName........." + regulatorUserName);
						corpUser.setParentUserName(regulatorUserName);
					}

					corpUser = saveCorpUserData(corpUser);
					corpUserList.add(corpUser);
					userId = corpUser.getId();
					parentDataMap.put(nonEncUserName, userId);
					logger.info("Admin CorpUserMenuList........." + corpUserMenuList);
					saveToCorpUsersMenuMap(corpUserMenuList, corpUser.getCorp_comp_id(), userId, userRRN,
							corpData.getCreatedByUpdatedBy());
					logger.info("Admin CorpUserAccountList........." + corpUserAccountList);
					saveToCorpUsersAccMap(corpUserAccountList, corpUser.getCorp_comp_id(), userId, userRRN,
							corpData.getCreatedByUpdatedBy());

				} else if (corpUser.getCorpRoleId().intValue() == 3 || corpUser.getCorpRoleId().intValue() == 4
						|| corpUser.getCorpRoleId().intValue() == 5 || corpUser.getCorpRoleId().intValue() == 6) {
					logger.info("Maker/Checker/Approver/Operator ........." + corpUser.getCorpRoleId().intValue());
					CorpUserEntity corpUserEntity = new CorpUserEntity();
					corpUserEntity.setCorp_comp_id(corpData.getCorpCompId());
					corpUserEntity.setUser_name(corpUser.getParentUserName());
					ResponseMessageBean response = isCompanyUserExist(corpUserEntity);

					corpUser.setRrn(String.valueOf(System.currentTimeMillis()));
					userRRN = corpUser.getRrn();
					corpUser.setParentRrn("0");
					if ("500" == response.getResponseCode()) {
						corpUser.setParentId(new BigDecimal(response.getResult().toString()));
						corpUser.setParentRoleId(BigDecimal.valueOf(2));
					} else {
						corpUser.setParentId(BigDecimal.valueOf(0));
						corpUser.setParentRoleId(BigDecimal.valueOf(0));
					}
					logger.info("Maker/Checker/Approver/Operator ........." + corpUser.getParentId());
					corpUser = saveCorpUserData(corpUser);
					corpUserList.add(corpUser);
					userParentId = corpUser.getId();
					logger.info("Admin CorpUserMenuList........." + corpUserMenuList);
					saveToCorpUsersMenuMap(corpUserMenuList, corpUser.getCorp_comp_id(), userParentId, userRRN,
							corpData.getCreatedByUpdatedBy());
					logger.info("Admin CorpUserAccountList........." + corpUserAccountList);
					saveToCorpUsersAccMap(corpUserAccountList, corpUser.getCorp_comp_id(), userParentId, userRRN,
							corpData.getCreatedByUpdatedBy());
				}
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
			responseMessageBean.setResponseMessage("Unable to process request, Please contact Adminstrator");
			responseMessageBean.setResponseCode("202");
			responseMessageBean.setResult(false);
			return responseMessageBean;
		}
		responseMessageBean.setResult(true);
		return responseMessageBean;
	}

	private Map<String, Object> validateActiveHierarchy(int corpCompId) {
		logger.info("Validation running............" + corpCompId);
		Map<String, Object> hierarchyStatus = null;
		try {
			hierarchyStatus = getSession().doReturningWork(new ReturningWork<Map<String, Object>>() {

				@Override
				public Map<String, Object> execute(Connection con) throws SQLException {

					CallableStatement callable = con.prepareCall("{call validate_active_hierarchy(?,?,?)}");
					callable.setInt(1, corpCompId);
					callable.registerOutParameter(2, Types.INTEGER);
					callable.registerOutParameter(3, Types.VARCHAR);
					callable.execute();
					Map<String, Object> map = new HashMap<>();
					map.put("statusCode", callable.getInt(2));
					map.put("statusMsg", callable.getString(3));
					return map;
				}
			});

		} catch (Exception e) {
			logger.error("Exception:", e);
		}
		return hierarchyStatus;
	}

	@Override
	@org.springframework.transaction.annotation.Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {
			NullPointerException.class, IndexOutOfBoundsException.class })
	public CorporateResponse approveRejectCompanyUser(CorpDataBean corpData, String companyName) {
		logger.info("approveRejectCompanyUser method Corporate request Data: " + corpData.toString());
		CorporateResponse corporateResponse = new CorporateResponse();
		logger.info("approveRejectCompanyUserTmp service starting............Company: " + companyName);
		boolean isApproved = true;
		boolean isPendingApprove = false;
		BigDecimal oldCorporateHirarchy = BigDecimal.valueOf(0);
		BigDecimal newCorporateHirarchy = BigDecimal.valueOf(0);
		CorpUserEntity corpUserEntity = new CorpUserEntity();
		Map<String, String> tempPasswordMap = new HashMap<>();
		corpUserEntity.setCorp_comp_id(corpData.getCorpCompId());
		List<CorpUserEntity> newCorpUserEntityList = corpUserRepository
				.findByCorpCompIdAndStatusidOrderById(corpUserEntity.getCorp_comp_id(), BigDecimal.valueOf(36));
		logger.info("New Corporate data is Empty: " + newCorpUserEntityList.isEmpty());
		List<CorpUserEntity> oldCorpUserEntityList = null;
		Map<BigDecimal, String> oldIdUserNameMap = new HashMap<>();
		Map<BigDecimal, String> newIdUserNameMap = new HashMap<>();
		String companyCode = "";
		BigDecimal oldCorpCompId = BigDecimal.valueOf(0);
		List<Integer> statusList = new ArrayList<>();
		List<Integer> statusIdList = new ArrayList<>();
		CorpCompanyMasterEntity corpCompanyMasterData = new CorpCompanyMasterEntity();
		boolean isOldUser = false;
		List<CorpCompanyMasterEntity> companyData = new ArrayList<>();
		logger.info("User Request....." + corpData.getCorpUserMasterData());

		try {
			if (("6".equalsIgnoreCase(corpData.getCorpUserMasterData().get(0).getLoginId())
					|| ApplicationConstants.CHECKER
							.equalsIgnoreCase(corpData.getCorpUserMasterData().get(0).getLoginType()))
					&& ApplicationConstants.APPROVE.equalsIgnoreCase(corpData.getReqStatus())) {
				logger.info("inside company approval:");

				logger.info("Branch Code: " + corpData.getBranchCode());
				corpCompanyMasterData.setBranchCode(corpData.getBranchCode());
				corpCompanyMasterData.setId(corpData.getCorpUserMasterData().get(0).getCorp_comp_id());
				statusList.add(36);
				corpCompanyMasterData.setStatusList(statusList);
				companyData = getOfflineCorpCompDataByIdNew(corpCompanyMasterData);
				logger.info("company Data: " + companyData.toString());
				companyCode = EncryptorDecryptor.decryptData(companyData.get(0).getCompanyCode());

				BigDecimal statusId;
				if (BigDecimal.valueOf(8).equals(companyData.get(0).getOgstatus())) {
					logger.info("update company status Pending Checker to Active.........");
					statusId = BigDecimal.valueOf(8);
					corpCompanyMasterRepo.corpCompanyMasterUpdateById(statusId, corpData.getCreatedByUpdatedBy(),
							companyData.get(0).getId());

				} else {
					// update company status Pending Checker to Active
					logger.info("update company status Pending Checker to Active.........");
					newCorporateHirarchy = companyData.get(0).getLevelMaster();
					companyData.get(0).setStatusId(BigDecimal.valueOf(8));
					statusIdList.add(3);
					CorpCompanyMasterEntity corpCompanyMasterEntity = corpCompanyMasterRepo
							.findByCifAndStatusid(companyData.get(0).getCif(), statusIdList);
					if (!ObjectUtils.isEmpty(corpCompanyMasterEntity)) {
						oldCorpCompId = corpCompanyMasterEntity.getId();
						oldCorporateHirarchy = corpCompanyMasterEntity.getLevelMaster();
					}

					for (CorpUserEntity corpUser : newCorpUserEntityList) {
						newIdUserNameMap.put(corpUser.getId(), corpUser.getUser_name());
					}

					CorpUserEntity oldCorpUserEntity = null;
					if (!ObjectUtils.isEmpty(corpData.getCorpUserMasterData()) && oldCorpCompId.intValue() > 0) {
						oldCorpUserEntityList = corpUserRepository.findByCorpCompIdAndStatusidNotIn(oldCorpCompId,
								BigDecimal.valueOf(10));
						for (CorpUserEntity oldCorpUser : oldCorpUserEntityList) {
							logger.info("id: " + oldCorpUser.getId() + ", statusid: " + oldCorpUser.getStatusid()
									+ ", userName: " + oldCorpUser.getUser_name());
							oldIdUserNameMap.put(oldCorpUser.getId(), oldCorpUser.getUser_name());
						}

						for (CorpUserEntity corpEntity : corpData.getCorpUserMasterData()) {
							oldCorpUserEntity = corpUserRepository.findByCorpCompIdAnduserNameAndStatusidNotIn(
									oldCorpCompId, EncryptorDecryptor.encryptData(corpEntity.getUser_name()),
									BigDecimal.valueOf(10));
							if (!ObjectUtils.isEmpty(oldCorpUserEntity)) {
								isOldUser = true;
								logger.info("Old username found: " + corpEntity.getUser_name());
								break;
							}
						}
					}

					BigDecimal corpCompanyStatusId = BigDecimal.valueOf(8);
					if (!ObjectUtils.isEmpty(oldCorpUserEntityList)) {
						corpCompanyMasterRepo.corpCompanyMasterUpdateById(BigDecimal.valueOf(10),
								corpData.getCreatedByUpdatedBy(), companyData.get(0).getId());
						corpCompanyStatusId = companyData.get(0).getOgstatus();
						if (ObjectUtils.isEmpty(corpCompanyStatusId)) {
							corpCompanyStatusId = BigDecimal.valueOf(3);
						}
						if (!isOldUser) {
							corpCompanyStatusId = BigDecimal.valueOf(8);
						}

						corpCompanyMasterRepo.corpCompMasterUpdateById(corpData.getCreatedByUpdatedBy(),
								corpCompanyStatusId, oldCorpCompId, companyData.get(0).getPhoneNo(),
								companyData.get(0).getMakerLimit(), companyData.get(0).getCheckerLimit(),
								companyData.get(0).getMaxLimit(), companyData.get(0).getAdminTypes(),
								companyData.get(0).getLevelMaster(), companyData.get(0).getApprovalLevel());
					} else {
						corpCompanyMasterRepo.corpCompanyMasterUpdateById(corpCompanyStatusId, corpCompanyStatusId,
								corpData.getCreatedByUpdatedBy(), companyData.get(0).getId());
					}

					logger.info("company is Registerd:");
				}

			} else if (("6".equalsIgnoreCase(corpData.getCorpUserMasterData().get(0).getLoginId())
					|| ApplicationConstants.CHECKER
							.equalsIgnoreCase(corpData.getCorpUserMasterData().get(0).getLoginType()))
					&& ApplicationConstants.REJECT.equalsIgnoreCase(corpData.getReqStatus())) {
				logger.info("status....Rejected");
				logger.info("inside company rejection:");
				logger.info("Branch Code: " + corpData.getBranchCode());
				// update company status Pending Checker to Pending Maker
				corpCompanyMasterData.setBranchCode(corpData.getBranchCode());
				corpCompanyMasterData.setId(corpData.getCorpCompId());
				statusList.add(36);
				corpCompanyMasterData.setStatusList(statusList);
				companyData = getOfflineCorpCompDataByIdNew(corpCompanyMasterData);
				logger.info("Company data by status to rejected corporate...." + companyData.toString());
				companyData.get(0).setStatusId(BigDecimal.valueOf(35));
				BigDecimal statusId;
				if (!ObjectUtils.isEmpty(companyData.get(0).getOgstatus())) {
					if (BigDecimal.valueOf(8).equals(companyData.get(0).getOgstatus())) {
						statusId = BigDecimal.valueOf(141);
					} else {
						statusId = BigDecimal.valueOf(35);
					}
					corpCompanyMasterRepo.corpCompanyMasterUpdateById(statusId, corpData.getCreatedByUpdatedBy(),
							companyData.get(0).getId());
				} else {
					corpCompanyMasterRepo.corpCompanyMasterUpdateById(BigDecimal.valueOf(35), BigDecimal.valueOf(0),
							corpData.getCreatedByUpdatedBy(), companyData.get(0).getId());
				}

				logger.info("company creation request is rejectd:");
			}
		} catch (Exception e) {
			logger.error("Error in application: " + ExceptionUtils.getStackTrace(e));
		}
		Map<BigDecimal, List> userIdMap = new HashMap<>();
		int i = 0;
		boolean isCorpMenuAccUpdate = false;
		Set<BigDecimal> deletedUserMenuList = new HashSet<>();
		Set<BigDecimal> deletedUserAccList = new HashSet<>();
		Set<String> deletedUserList = new HashSet<>();
		Set<BigDecimal> oldParentIdSet = new HashSet<>();
		Set<BigDecimal> oldMatchedUserMenuList = new HashSet<>();
		Set<BigDecimal> oldMatchedUserAccList = new HashSet<>();
		Set<String> oldAndNewMatchedUserList = new HashSet<>();
		Collection<BigDecimal> usersForEmail = new HashSet<>();
		boolean isOldCorporateDelete = false;
		for (CorpUserEntity corpUser : newCorpUserEntityList) {
			try {
				if (("6".equalsIgnoreCase(corpData.getCorpUserMasterData().get(0).getLoginId())
						|| ApplicationConstants.CHECKER
								.equalsIgnoreCase(corpData.getCorpUserMasterData().get(0).getLoginType()))
						&& ApplicationConstants.APPROVE.equalsIgnoreCase(corpData.getReqStatus())) {

					BigDecimal statusId;

					if (BigDecimal.valueOf(8).equals(companyData.get(0).getOgstatus())
							&& BigDecimal.valueOf(8).equals(corpUser.getOgstatus())) {
						statusId = BigDecimal.valueOf(8);
						corpUser.setStatusid(statusId);
						corpUserRepository.corpUserUpdateByUserId(statusId, corpData.getRemark(),
								corpData.getCorpUserMasterData().get(i).getUser_pwd(), corpData.getCreatedByUpdatedBy(),
								corpUser.getId());
						// Send Email
						usersForEmail.add(corpUser.getId());
						tempPasswordMap.put(EncryptorDecryptor.decryptData(corpUser.getUser_name()),
								corpData.getCorpUserMasterData().get(i).getTemp_pwd());
//						sendEmailAndSms(companyName, companyData.get(0).getId(), companyCode, tempPasswordMap,
//								usersForEmail, corpData.getCreatedByUpdatedBy());
						isPendingApprove = true;
						isApproved = false;
						corporateResponse.setCorpId(corpUser.getCorp_comp_id());
						corporateResponse.setCompanyCode(companyCode);
						corporateResponse.setTempPassword(tempPasswordMap);
						corporateResponse.setNewUsers(usersForEmail);
						corporateResponse.setCreatedByUpdateBy(corpData.getCreatedByUpdatedBy());
//						tempPasswordMap.clear();
//						usersForEmail.clear();											
//						
					} else {
						boolean isOld = false;
						if (!ObjectUtils.isEmpty(oldCorpUserEntityList)) {
							for (CorpUserEntity oldCorpUser : oldCorpUserEntityList) {
								List<BigDecimal> companyIdList = new ArrayList<>();
								List<BigDecimal> usersIdList = new ArrayList<>();
								companyIdList.add(oldCorpUser.getCorp_comp_id());
								companyIdList.add(corpUser.getCorp_comp_id());
								usersIdList.add(oldCorpUser.getId());
								usersIdList.add(corpUser.getId());
								if (oldCorpUser.getUser_name().equals(corpUser.getUser_name())
										&& BigDecimal.valueOf(102).equals(corpUser.getOgstatus())) {
									logger.info("Deleting deletion pending status records");
									logger.info("Deleting old and new corporate user menu and account...");
									corpUsersAccMapRepository.deleteByCompanyIdAndUserId(companyIdList, usersIdList);
									corpUsersMenuMapRepository.deleteByCompanyIdAndUserId(companyIdList, usersIdList);
									corpUserRepository.deleteByCompanyIdAndUserId(companyIdList, usersIdList);
								} else if (oldCorpUser.getUser_name().equals(corpUser.getUser_name())) {
									String oldParentUserName = oldIdUserNameMap.get(oldCorpUser.getParentId());
									String newParentUserName = newIdUserNameMap.get(corpUser.getParentId());
									logger.info("oldParentId:" + oldCorpUser.getParentId() + ", oldParentUserName: "
											+ oldParentUserName);
									logger.info("newParentId: " + corpUser.getParentId() + ", newParentUserName: "
											+ newParentUserName);
									if (!BigDecimal.valueOf(0).equals(oldCorpUser.getParentId())
											&& !ObjectUtils.isEmpty(oldParentUserName)) {
										oldParentIdSet.add(oldCorpUser.getParentId());
									}
									isOld = true;
									if (BigDecimal.valueOf(8).equals(oldCorpUser.getStatusid())
											&& !BigDecimal.valueOf(102).equals(corpUser.getOgstatus())) {
										tempPasswordMap.put(EncryptorDecryptor.decryptData(corpUser.getUser_name()),
												corpData.getCorpUserMasterData().get(i).getTemp_pwd());
										oldCorpUser.setUser_pwd(corpData.getCorpUserMasterData().get(i).getUser_pwd());
									}
									if (!ObjectUtils.isEmpty(oldParentUserName)
											&& !ObjectUtils.isEmpty(newParentUserName)
											&& !BigDecimal.valueOf(102).equals(corpUser.getOgstatus())) {
										if (!oldParentUserName.equals(newParentUserName)) {
											oldCorpUser.setParentId(corpUser.getParentId());
											oldCorpUser.setParentUserName(
													EncryptorDecryptor.decryptData(newParentUserName));
										}
									}

									if (!oldCorpUser.getStatusid().equals(BigDecimal.valueOf(0))) {
										oldCorpUser.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
									}
									if (!isCorpMenuAccUpdate) {
										logger.info("Deleting old corporate menu and account");
										// update corp Menu Map
										corpMenuMapRepository.delete(oldCorpCompId, BigDecimal.valueOf(3));
										// update corp Acc Map
										corpAccMapRepository.delete(oldCorpCompId, BigDecimal.valueOf(3));
										logger.info("Deleting old user menu and account");
										// update corp Menu Map
										corpMenuMapRepository.update(corpUser.getCorp_comp_id(), oldCorpCompId);
										// update corp Acc Map
										corpAccMapRepository.update(corpUser.getCorp_comp_id(), oldCorpCompId);
										isCorpMenuAccUpdate = true;
										// update corp Menu Map
									}
									corpUsersMenuMapRepository.delete(oldCorpUser.getCorp_comp_id(),
											oldCorpUser.getId(), BigDecimal.valueOf(3));
									corpUsersAccMapRepository.delete(oldCorpUser.getCorp_comp_id(), oldCorpUser.getId(),
											BigDecimal.valueOf(3));
									corpUsersMenuMapRepository.update(oldCorpUser.getCorp_comp_id(),
											oldCorpUser.getId(), corpUser.getCorp_comp_id(), corpUser.getId());
									corpUsersAccMapRepository.update(oldCorpUser.getCorp_comp_id(), oldCorpUser.getId(),
											corpUser.getCorp_comp_id(), corpUser.getId());
									oldMatchedUserMenuList.add(oldCorpUser.getId());
									oldMatchedUserAccList.add(oldCorpUser.getId());
									oldAndNewMatchedUserList.add(oldCorpUser.getUser_name());

									List userDataList = new ArrayList<>();
									userDataList.add(oldCorpCompId);
									userDataList.add(corpUser.getRrn());
									userDataList.add(EncryptorDecryptor.decryptData(corpUser.getPersonal_Phone()));
									userIdMap.put(oldCorpCompId, userDataList);
									corpUserRepository.updateUsers(corpUser.getTransMaxLimit(), corpUser.getEmail_id(),
											corpUser.getPersonal_Phone(), corpUser.getRemark(), corpUser.getRights(),
											corpUser.getPkiStatus(), corpUser.getUpdatedby(), corpUser.getDob(),
											corpUser.getOgstatus(), oldCorpUser.getUser_pwd(),
											oldCorpUser.getParentId(), oldCorpUser.getParentUserName(),
											corpUser.getPancardNumber(), oldCorpUser.getStatusid(),
											oldCorpUser.getCorp_comp_id(), oldCorpUser.getCorp_comp_id(),
											oldCorpUser.getId());
									i++;
								} else {
									deletedUserMenuList.add(oldCorpUser.getId());
									deletedUserAccList.add(oldCorpUser.getId());
									deletedUserList.add(oldCorpUser.getUser_name());
								}
							}
						}

						if (!isOld) {
							logger.info("Start Add new users..........");
							BigDecimal newCorpCompId = corpUser.getCorp_comp_id();
							if (!ObjectUtils.isEmpty(oldCorpUserEntityList)) {
								corpUser.setCorp_comp_id(oldCorpCompId);
							} else {
								oldCorpCompId = companyData.get(0).getId();
							}
							corpUser.setStatusid(BigDecimal.valueOf(8));
							corpUser.setUser_pwd(corpData.getCorpUserMasterData().get(i).getUser_pwd());
							corpUser.setRemark(corpData.getRemark());
							corpUser.setUpdatedby(corpData.getCreatedByUpdatedBy());
							corpUser.setUpdatedOn(new Date());
							corpUser.setOgstatus(BigDecimal.valueOf(8));
							if (!ObjectUtils.isEmpty(oldCorpUserEntityList)
									&& oldCorporateHirarchy.equals(newCorporateHirarchy)) {
								logger.info("UserName: " + corpUser.getUser_name() + "Parent RoleId: "
										+ corpUser.getParentRoleId());
								if (BigDecimal.valueOf(0) != corpUser.getParentId()
										&& BigDecimal.valueOf(1) != corpUser.getParentId()) {
									String newParentUserName = newIdUserNameMap.get(corpUser.getParentId());
									logger.info("NewParent UserName " + newParentUserName);
									for (BigDecimal oldParentId : oldParentIdSet) {
										String oldParentUserName = oldIdUserNameMap.get(oldParentId);
										logger.info("OldParent UserName " + oldParentUserName);
										if (oldParentUserName.equals(newParentUserName)) {
											corpUser.setParentId(oldParentId);
											break;
										}
									}
								}
							}

							if (!ObjectUtils.isEmpty(oldCorpUserEntityList)
									&& !oldCorporateHirarchy.equals(newCorporateHirarchy) && !isOldCorporateDelete
									|| !ObjectUtils.isEmpty(oldCorpUserEntityList)
											&& newCorporateHirarchy.equals(new BigDecimal(0))
											&& oldCorporateHirarchy.equals(new BigDecimal(0))
											&& !isOldCorporateDelete) {
								logger.info("Deleting old corporate menu and account");
								logger.info("old corporate Level: " + oldCorporateHirarchy + ", new corporate level: "
										+ newCorporateHirarchy);
								// update corp Menu Map
								corpMenuMapRepository.delete(oldCorpCompId, BigDecimal.valueOf(3));
								// update corp Acc Map
								corpAccMapRepository.delete(oldCorpCompId, BigDecimal.valueOf(3));
								isOldCorporateDelete = true;
							}

							if (!isCorpMenuAccUpdate && !ObjectUtils.isEmpty(oldCorpUserEntityList)) {
								corpMenuMapRepository.update(companyData.get(0).getId(), oldCorpCompId);
								corpAccMapRepository.update(companyData.get(0).getId(), oldCorpCompId);
								isCorpMenuAccUpdate = true;
							}
							if (!ObjectUtils.isEmpty(oldCorpUserEntityList)) {
								corpUsersMenuMapRepository.updateCorpUserMenu(oldCorpCompId, companyData.get(0).getId(),
										corpUser.getId());
								// update corporate User Acc
								corpUsersAccMapRepository.updateCorpUserAcc(oldCorpCompId, companyData.get(0).getId(),
										corpUser.getId());
							}
							logger.info("userName: " + corpUser.getUser_name() + ", Password is: "
									+ corpUser.getUser_pwd());
							corpUserRepository.updateUsers(corpUser.getTransMaxLimit(), corpUser.getEmail_id(),
									corpUser.getPersonal_Phone(), corpUser.getRemark(), corpUser.getRights(),
									corpUser.getPkiStatus(), corpUser.getUpdatedby(), corpUser.getDob(),
									corpUser.getOgstatus(), corpUser.getUser_pwd(), corpUser.getParentId(),
									corpUser.getParentUserName(), corpUser.getPancardNumber(), corpUser.getStatusid(),
									oldCorpCompId, newCorpCompId, corpUser.getId());
							tempPasswordMap.put(EncryptorDecryptor.decryptData(corpUser.getUser_name()),
									corpData.getCorpUserMasterData().get(i).getTemp_pwd());
							List userDataList = new ArrayList<>();
							userDataList.add(oldCorpCompId);
							userDataList.add(corpUser.getRrn());
							userDataList.add(EncryptorDecryptor.decryptData(corpUser.getPersonal_Phone()));
							userIdMap.put(oldCorpCompId, userDataList);
							i++;
//							}
							logger.info("End Add new users..........");
						}
					}
				} else if ((corpData.getCorpUserMasterData().get(0).getLoginId().equalsIgnoreCase("6") || corpData
						.getCorpUserMasterData().get(0).getLoginType().equalsIgnoreCase(ApplicationConstants.CHECKER))
						&& corpData.getReqStatus().equalsIgnoreCase(ApplicationConstants.REJECT)) {
					logger.info("inside checker rejection:");
					logger.info("Password is: " + corpUser.getUser_pwd());
					BigDecimal statusId;
					if (corpUser.getOgstatus().equals(BigDecimal.valueOf(8))) {
						statusId = BigDecimal.valueOf(141);
					} else {
						statusId = BigDecimal.valueOf(35);
					}
					corpUser.setStatusid(statusId);
					isApproved = false;
					corporateResponse.setApproved(isApproved);
					corpUserRepository.corpUserUpdateByUserId(statusId, corpUser.getWrongAttemptSoftToken(),
							corpData.getRemark(), corpUser.getUser_pwd(), corpData.getCreatedByUpdatedBy(),
							corpUser.getOgstatus(), corpUser.getId());

				}
			} catch (Exception e) {
				logger.error("Error in corporate user approve reject..........." + ExceptionUtils.getStackTrace(e));
			}
		}

		if (isPendingApprove) {
			logger.info("Is Pending Approver: " + isPendingApprove);
			corporateResponse.setPendingApprove(isPendingApprove);
			corporateResponse.setApproved(isApproved);
			return corporateResponse;
		}
		if (!ObjectUtils.isEmpty(oldCorpUserEntityList)) {
			deletedUserMenuList.removeAll(oldMatchedUserMenuList);
			deletedUserAccList.removeAll(oldMatchedUserAccList);
			deletedUserList.removeAll(oldAndNewMatchedUserList);

			for (BigDecimal userId : deletedUserMenuList) {
				corpUsersMenuMapRepository.delete(oldCorpCompId, userId, BigDecimal.valueOf(3));
			}
			for (BigDecimal userId : deletedUserAccList) {
				corpUsersAccMapRepository.delete(oldCorpCompId, userId, BigDecimal.valueOf(3));
			}
			for (String userName : deletedUserList) {
				corpUserRepository.delete(oldCorpCompId, userName, corpData.getCreatedByUpdatedBy());
			}
			for (String userName : oldAndNewMatchedUserList) {
				corpUserRepository.deleteByStatusId(companyData.get(0).getId(), userName,
						corpData.getCreatedByUpdatedBy(), BigDecimal.valueOf(36));
			}
		}
		if (oldCorpCompId.equals(BigDecimal.valueOf(0))) {
			oldCorpCompId = companyData.get(0).getId();
		}
		if (isApproved) {
			corporateResponse.setCompanyName(companyName);
			corporateResponse.setCompanyCode(companyCode);
			corporateResponse.setTempPassword(tempPasswordMap);
			corporateResponse.setCreatedByUpdateBy(corpData.getCreatedByUpdatedBy());
			corporateResponse.setCorpId(oldCorpCompId);
			corporateResponse.setUserIdMap(userIdMap);
		}
		corporateResponse.setPendingApprove(false);
		corporateResponse.setApproved(isApproved);
		return corporateResponse;

	}

	private int deleteCorpUserByCorpCompId(BigDecimal corpCompId, Session session, BigDecimal updatedBy,
			String username) {
		return session.createSQLQuery(
				"UPDATE CORP_USERS SET STATUSID = 10, UPDATEDON = current_timestamp, UPDATEDBY = :updatedBy WHERE CORP_COMP_ID = :id AND USER_NAME=:username AND STATUSID = 3")
				.setBigDecimal("id", corpCompId).setBigDecimal("updatedBy", updatedBy)
				.setParameter("username", username).executeUpdate();
	}

	private BigDecimal getOldCorpCompIdByCif(String cif) {
		List<BigDecimal> list = getSession()
				.createSQLQuery("SELECT ID FROM CORP_COMPANY_MASTER WHERE CIF = :cif AND STATUSID = 3")
				.setParameter("cif", cif).list();
		if (list.isEmpty()) {
			return BigDecimal.valueOf(0);
		}
		return list.get(0);
	}

	private int deleteCorpCompanyByCorpCompId(BigDecimal corpCompId, Session session, BigDecimal updatedBy) {
		return session.createSQLQuery(
				"UPDATE CORP_COMPANY_MASTER SET STATUSID = 10, UPDATEDON = current_timestamp, UPDATEDBY = :updatedBy WHERE ID = :id AND STATUSID = 36")
				.setBigDecimal("id", corpCompId).setBigDecimal("updatedBy", updatedBy).executeUpdate();
	}

	@Override
	public void linkDlinkAccounts(Map<BigDecimal, List> userIdMap, BigDecimal corpCompId) {
		logger.info("Link Dlink Service calling start.......");
		try {
			Map<BigDecimal, List> accountListMap = new HashMap<>();
			CorpAccMapEntity corpAccMapEntity = new CorpAccMapEntity();
			corpAccMapEntity.setCorpId(corpCompId);
			List<CorpAccMapEntity> userAccountList = getAllOfflineCorpAccByCompanyId(corpAccMapEntity);
			for (CorpAccMapEntity corpUserAccMap : userAccountList) {
				List userDataList = accountListMap.get(corpCompId);
				if (accountListMap.containsKey(corpCompId)) {
					String accountData = "";
					if (corpUserAccMap.getStatusId().equals(3)) {
						accountData = userDataList.get(3)
								+ EncryptorDecryptor.decryptData(corpUserAccMap.getAccountNo()) + "|L|~";
					} else {
						accountData = userDataList.get(3)
								+ EncryptorDecryptor.decryptData(corpUserAccMap.getAccountNo()) + "|D|~";
					}
					userDataList.remove(3);
					userDataList.add(accountData);
					accountListMap.put(corpCompId, userDataList);
				} else {
					List userEntityDataList = userIdMap.get(corpUserAccMap.getCorpId());
					String accountNo = "";
					if (corpUserAccMap.getStatusId().equals(3)) {
						accountNo = "~" + EncryptorDecryptor.decryptData(corpUserAccMap.getAccountNo()) + "|L|~";
					} else {
						accountNo = "~" + EncryptorDecryptor.decryptData(corpUserAccMap.getAccountNo()) + "|D|~";
					}
					userEntityDataList.add(accountNo);
					accountListMap.put(corpUserAccMap.getCorpId(), userEntityDataList);
				}

			}
			for (Map.Entry<BigDecimal, List> entry : accountListMap.entrySet()) {
				restServiceCall.linkDLinkAccounts(entry.getValue().get(2).toString(),
						entry.getValue().get(3).toString(), entry.getValue().get(1).toString(),
						entry.getValue().get(0).toString());
			}
		} catch (Exception e) {
			logger.error("Exception in linkDlinkAccounts method calling......" + e);
		}
		logger.info("Link Dlink Service calling End.......");
	}

	private void linkDlinkAccounts1(Map<BigDecimal, List> userIdMap, BigDecimal corpCompId) {
		Map<BigDecimal, List> accountListMap = new HashMap<>();
		CorpAccMapEntity corpAccMapEntity = new CorpAccMapEntity();
		corpAccMapEntity.setCorpId(corpCompId);
		List<CorpUserAccMapEntity> userAccountList = getUserAccountListByCorpCompId(corpCompId.toBigInteger());
		for (CorpUserAccMapEntity corpUserAccMap : userAccountList) {
			if (userIdMap.containsKey(corpUserAccMap.getCorpUserId())) {
				List userDataList = accountListMap.get(corpUserAccMap.getCorpUserId());
				if (accountListMap.containsKey(corpUserAccMap.getCorpUserId())) {
					String accountData = userDataList.get(3)
							+ EncryptorDecryptor.decryptData(corpUserAccMap.getAccountNo()) + "|L|~";
					userDataList.remove(3);
					userDataList.add(accountData);
					accountListMap.put(corpUserAccMap.getCorpUserId(), userDataList);
				} else {
					List userEntityDataList = userIdMap.get(corpUserAccMap.getCorpUserId());
					String accountNo = "~" + EncryptorDecryptor.decryptData(corpUserAccMap.getAccountNo()) + "|L|~";
					userEntityDataList.add(accountNo);
					accountListMap.put(corpUserAccMap.getCorpUserId(), userEntityDataList);
				}

			}
		}
		for (Map.Entry<BigDecimal, List> entry : accountListMap.entrySet()) {
			restServiceCall.linkDLinkAccounts(entry.getValue().get(2).toString(), entry.getValue().get(3).toString(),
					entry.getValue().get(1).toString(), entry.getValue().get(0).toString());
		}
	}

	@Override
	public HashSet<BigDecimal> findCorpUserIdByCompId(BigDecimal corpCompId) {
		logger.info("findCorpUserIdByCompId : " + corpCompId);
		try {
			String sqlCorpName = "SELECT ID FROM CORP_USERS WHERE CORP_COMP_ID = :corpCompId AND STATUSID IN (8)";
			return new HashSet<>(
					getSession().createSQLQuery(sqlCorpName).setParameter("corpCompId", corpCompId).list());
		} catch (Exception e) {
			logger.error("Exception:", e);
		}
		return new HashSet<>(Collections.emptyList());
	}

	public void deleteDataCorpMenuMap(BigDecimal id, Session newSession) {
		logger.info("deleteDataCorpMenuMap..........corpid:" + id);
		try {
			// String sqlCorpName = "DELETE FROM CORP_MENU_MAP WHERE corpId = :id";
			String sqlCorpName = "UPDATE CORP_MENU_MAP SET STATUSID = 10 WHERE corpId = :id";
			newSession.createSQLQuery(sqlCorpName).setParameter("id", id).executeUpdate();
		} catch (Exception e) {
			logger.error("Exception:", e);
		}
	}

	public void deleteDataCorpMenuMapTmpToCorpMenuMap(BigDecimal id, Session newSession) {
		logger.info("deleteDataCorpMenuMapTmpToCorpMenuMap..........id:" + id);
		String corpId = "SELECT id FROM CORP_MENU_MAP_TMP WHERE corpId =:id";
		List corpMenuMapExist = newSession.createSQLQuery(corpId).setParameter("id", id).list();
		if (!(ObjectUtils.isEmpty(corpMenuMapExist))) {
			logger.info("deleteDataCorpCompanyTmpToCorpCompany..........id:" + id);
			try {
				// String sqlCorpName = "DELETE FROM CORP_MENU_MAP_TMP WHERE corpId =:id";
				String sqlCorpName = "UPDATE CORP_MENU_MAP SET STATUSID = 10 WHERE corpId = :id";
				newSession.createSQLQuery(sqlCorpName).setParameter("id", id).executeUpdate();
			} catch (Exception e) {
				logger.error("Exception:", e);
			}
		}
	}

	public void deleteDataCorpAccMap(BigDecimal id, Session newSession) {
		logger.info("deleteDataCorpAccMap........corpcompid: " + id);
		try {
			String sqlCorpName = "UPDATE CORP_ACC_MAP SET STATUSID = 10 WHERE corpId =:id";
			newSession.createSQLQuery(sqlCorpName).setParameter("id", id).executeUpdate();
		} catch (Exception e) {
			logger.error("Exception:", e);
		}
	}

	public void deleteDataCorpUserAccMap(BigDecimal id, Session newSession) {
		logger.info("deleteDataCorpUserAccMap..........corpCompId:" + id);
		try {
			String sqlCorpName = "UPDATE CORPUSER_ACC_MAP SET STATUSID = 10 WHERE CORPCOMPID =:id";
			newSession.createSQLQuery(sqlCorpName).setParameter("id", id).executeUpdate();
		} catch (Exception e) {
			logger.error("Exception:", e);
		}
	}

	public void deleteDataCorpUserMenuMap(BigDecimal id, Session newSession) {
		logger.info("deleteDataCorpUserMenuMap..........corpCompId:" + id);
		try {
			String sqlCorpName = "UPDATE CORPUSER_MENU_MAP SET STATUSID = 10 WHERE corpCompId =:id";
			newSession.createSQLQuery(sqlCorpName).setParameter("id", id).executeUpdate();
		} catch (Exception e) {
			logger.error("Exception:", e);
		}
	}

	@Override
	public boolean sendEmailAndSms(String companyName, BigDecimal corpId, String companyCode,
			Map<String, String> tempPassword, Collection<BigDecimal> newUsers, BigDecimal createdByUpdateBy) {
		// EncryptDeryptUtility encryptDeryptUtility = new EncryptDeryptUtility();
		CorpUserEntity corpUserEntity = new CorpUserEntity();
		corpUserEntity.setCorp_comp_id(corpId);
		List<Integer> statusList = new ArrayList<>();
		statusList.add(8);
		corpUserEntity.setStatusList(statusList);
		List<CorpUserEntity> corpUserList = getAllCorpUsersByCompId(corpUserEntity, true);
		for (CorpUserEntity corpUser : corpUserList) {
			if (newUsers.contains(corpUser.getId())) {
				logger.info("fetch data from DB.... ");
				File file = null;
				List<Map<String, String>> record = null;
				record = new ArrayList<Map<String, String>>();

				String nonEncUserName = corpUser.getUser_name();
				Map<String, String> map = new HashMap<>();
				String nonEncEmail = corpUser.getEmail_id();
				String nonEncMobile = corpUser.getPersonal_Phone();
				String nonEncPanCardNo = corpUser.getPancardNumber();

				// update user status Pending Checker to Pending for Authentication
				logger.info("inside approved checker");
				// Generate password protected credential file in PDF
				String userName = nonEncUserName;
				String otp = RandomNumberGenerator.generateActivationCode();
				String encPwd = (nonEncPanCardNo).concat(otp);
//				logger.info("Password for pdf:" + encPwd);
				map.put("User Name", userName);
				map.put("Password", tempPassword.get(userName));
				logger.info("kit UserName: " + userName + ", Kit Password: " + tempPassword.get(userName));

				List<String> generalRec = new ArrayList<>();
				String userDisplyName = EncryptorDecryptor.decryptData(corpUser.getUser_disp_name());
				generalRec.add(userDisplyName);
				generalRec.add(corpUser.getPersonal_Phone());
				generalRec.add(companyName);
				generalRec.add(userName);
				generalRec.add(companyCode);
				generalRec.add(tempPassword.get(userName));
				generalRec.add(corpUser.getCorpRoleName());
				record.add(map);
//				logger.info("userName: " + userName + " ,TempPassword: " + tempPassword.get(userName));

				String finalCompanyName = new String();
				if (companyName.toString().contains("/")) {
					finalCompanyName = companyName.toString().replaceAll("/", "");
				} else {
					finalCompanyName = companyName;
				}
				String fileName = finalCompanyName + "_UserCredentials.pdf";

				logger.info("start PDF Generation:");
				file = pdfGenerator.generatePDF(fileName, "PSB: User Credentials", record, encPwd, encPwd, generalRec,
						pdfFilePath);
				logger.info("end PDF Generation:");
				List<String> toEmail = new ArrayList<>();
				List<String> ccEmail = new ArrayList<>();
				List<String> bccEmail = new ArrayList<>();
				List<File> files = new ArrayList<>();
				toEmail.add(nonEncEmail);
				files.add(file);
//				Date subjectDate = new Date();
				String subject = "PSB UnIC Biz- Corporate Registration Kit";

//			send mail to user with their credentials file
				String body = "<p style='margin-top:0in;margin-right:0in;margin-bottom:8.0pt;margin-left:0in;line-height:107%;font-size:15px;font-family:\\\"Calibri\\\",sans-serif;text-align:justify;'>Dear <strong>"
						+ userDisplyName
						+ ",</strong></p> <p style='margin-top:0in;margin-right:0in;margin-bottom:8.0pt;margin-left:0in;line-height:107%;font-size:15px;font-family:\\\"Calibri\\\",sans-serif;text-align:justify;'><br>&nbsp;We would like to welcome you as a new user of PSB UnIC Biz. We value your support and contribution to our business, and we believe that your experience with our services will bring you the utmost satisfaction.</p> <p style='margin-top:0in;margin-right:0in;margin-bottom:8.0pt;margin-left:0in;line-height:107%;font-size:15px;font-family:\\\"Calibri\\\",sans-serif;text-align:justify;'>At <strong>Punjab &amp; Sind Bank,&nbsp;</strong>we are committed to deliver responsive and excellent services to all our customers. We are pleased to serve you with our best<strong>&nbsp;</strong>services. Our customer&apos;s satisfaction is the most important part of our business, and we work hard to ensure our customers feel valued and heard. Please find in attachment your &ldquo;Registration Kit&rdquo;&nbsp;</p> <p style='margin-top:0in;margin-right:0in;margin-bottom:8.0pt;margin-left:0in;line-height:107%;font-size:15px;font-family:\\\"Calibri\\\",sans-serif;text-align:justify;'><br>&nbsp;Your Registration Kit is password protected. Please follow the below instructions to open your kit</p> <ul style=\\\"margin-bottom:0in;margin-top:0in;\\\" type=\\\"circle\\\">     <li style='margin-top:0in;margin-right:0in;margin-bottom:8.0pt;margin-left:0in;line-height:107%;font-size:15px;font-family:\\\"Calibri\\\",sans-serif;text-align:justify;'>Click on the attachment provided with this mail</li>     <li style='margin-top:0in;margin-right:0in;margin-bottom:8.0pt;margin-left:0in;line-height:107%;font-size:15px;font-family:\\\"Calibri\\\",sans-serif;text-align:justify;'>You will be prompted for your password</li>     <li style='margin-top:0in;margin-right:0in;margin-bottom:8.0pt;margin-left:0in;line-height:107%;font-size:15px;font-family:\\\"Calibri\\\",sans-serif;text-align:justify;'>The password is your PAN (in capital) + 6-digit OTP received over SMS. For eg. PAN number is: AAAAA1111A &amp; OTP received is 123456, then password to open the registration kit document is: &ldquo;AAAAA1111A123456&rdquo;.</li> </ul> <p style='margin-top:0in;margin-right:0in;margin-bottom:8.0pt;margin-left:0in;line-height:107%;font-size:15px;font-family:\\\"Calibri\\\",sans-serif;text-align:justify;'>We congratulate you to become a part of Punjab &amp; Sind Bank&rsquo;s digital banking journey. We are grateful for the opportunity to serve you with the best service.</p> <p style='margin-top:0in;margin-right:0in;margin-bottom:8.0pt;margin-left:0in;line-height:107%;font-size:15px;font-family:\\\"Calibri\\\",sans-serif;text-align:justify;'><br><strong>Warm Regards,</strong></p> <p style='margin-top:0in;margin-right:0in;margin-bottom:8.0pt;margin-left:0in;line-height:107%;font-size:15px;font-family:\\\"Calibri\\\",sans-serif;text-align:justify;'><strong>Punjab &amp; Sind Bank.</strong></p> <p style='margin-top:0in;margin-right:0in;margin-bottom:8.0pt;margin-left:0in;line-height:107%;font-size:15px;font-family:\\\"Calibri\\\",sans-serif;text-align:justify;'><strong>&nbsp;</strong></p> <p style='margin-top:0in;margin-right:0in;margin-bottom:8.0pt;margin-left:0in;line-height:107%;font-size:15px;font-family:\\\"Calibri\\\",sans-serif;text-align:justify;'>Note: Please do not reply to this email. This is sent from an unattended mail box. For any queries/complaints, please write to us at <a href=\\\"mailto:omni_support@psb.co.in\\\">omni_support@psb.co.in</a>.</p> <p style='margin-top:0in;margin-right:0in;margin-bottom:8.0pt;margin-left:0in;line-height:107%;font-size:15px;font-family:\\\"Calibri\\\",sans-serif;text-align:justify;'>&nbsp;<br>&nbsp;Disclaimer:<br>This email is confidential. If you are not the intended recipient, please notify us immediately on <a href=\\\"mailto:omni_support@psb.co.in\\\">omni_support@psb.co.in</a>; you should not copy, forward, disclose or use it for any purpose either partly or completely. Please delete the email and all copies from your system. Internet communications cannot be guaranteed to be timely, secure, error or virus-free. If you encounter any problems in opening your Welcome Kit, please check if your web/email administrator allows emails with attachments.</p>";
				try {

					// send mail to user with their credentials file
					if (emailUtil.sendEmailWithAttachment(toEmail, ccEmail, bccEmail, files, body, subject)) {
						file.delete();
					}

					// send mail to user with their credentials file
					// WELCOME KIT Message
					emailUtil.sendSMSNotification(nonEncMobile,
//							"Dear User, You have been enrolled for PSB UnIC Biz. Use {#var#} as OTP to validate registration kit shared over email. Complete registration in 3 days-P%26SB");
							"Dear User, You have been successfully registered for PSB UnIC Biz. We have shared a registration kit consisting your temporary credentials  over email. Please complete your registration within 7 days. OTP to validate the kit will be shared separately through SMS -Punjab%26Sind+Bank");
					// Registration OTP
					emailUtil.sendSMSNotification(nonEncMobile, otp
							+ " is your OTP to validate the PSB UnIC Biz registration kit. Do not share your OTP with anyone. Bank NEVER asks for OTP over Call, SMS or Email -Punjab%26Sind+Bank");
//							+ " is your OTP to validate the PSB UnIC Biz registration kit. Do not share your OTP with anyone. Bank NEVER asks for OTP over Call, SMS or Email -P%26S+B");

//					} else {
//						logger.info("SMS max attampt reached");
//					}
//					map.clear();
//					generalRec.clear();
				} catch (Exception e) {
					logger.error("Error in email and sms sending........." + e.getLocalizedMessage());
				}
			}
		}
		return true;

	}

	public boolean saveToCorpUsersMenuMap(String[] newMenuMapId, BigDecimal corpId, BigDecimal userId, String userRRN,
			BigDecimal createdBy) {
		logger.info("updateToCorpUsersMenuMapTmp request:CorpUserMenuList corpId: " + corpId + ", userId:" + userId);
		Session session = sessionFactory.getCurrentSession();
		// delete menus for user
		logger.info("deleteAllRegulatorMenuTmp request" + userId.intValue());

		List<BigDecimal> deleteMenuListIds = null;
		List<BigDecimal> oldMenuMapId = new ArrayList<>();
		List<String> addMenuListIds = new ArrayList<>();
		List<BigDecimal> addMenuFinalList = new ArrayList<>();

		List<CorpUserMenuMapEntity> corpMenulist = getUserMenuListByCompIdAndUserId(userId, corpId);
		if (!ObjectUtils.isEmpty(corpMenulist)) {
			deleteMenuListIds = new ArrayList<>();
			for (CorpUserMenuMapEntity oldMenu : corpMenulist) {
				oldMenuMapId.add(oldMenu.getCorpMenuId());
				deleteMenuListIds.add(oldMenu.getCorpMenuId());
			}
		}
		if (!ObjectUtils.isEmpty(newMenuMapId)) {
			// convert array to arraylist
			addMenuListIds = Arrays.asList(newMenuMapId);
			// string arraylist to Bigdecimal arraylist
			addMenuListIds.forEach(s -> addMenuFinalList.add(new BigDecimal(s)));
		}
		if (!ObjectUtils.isEmpty(deleteMenuListIds)) {
			// delete existing record
			deleteMenuListIds.removeAll(addMenuFinalList);
		}
		if (!ObjectUtils.isEmpty(addMenuFinalList)) {
			// get new record
			addMenuFinalList.removeAll(oldMenuMapId);
		}

		if (!ObjectUtils.isEmpty(deleteMenuListIds)) {
			deleteFromCorpuserByUserId(deleteMenuListIds, userId.toBigInteger());
		}

		try {
			if (!ObjectUtils.isEmpty(addMenuFinalList)) {
				for (BigDecimal corpMenu : addMenuFinalList) {
					CorpUserMenuMapEntity corpUserMenuMap = new CorpUserMenuMapEntity();
					Transaction tx = session.beginTransaction();
					corpUserMenuMap.setCorpMenuId(corpMenu);
					corpUserMenuMap.setCorpUserId(userId);
					corpUserMenuMap.setCorpCompId(corpId);
					corpUserMenuMap.setStatusId(BigDecimal.valueOf(3));
					corpUserMenuMap.setUserRrn(userRRN);
					corpUserMenuMap.setUpdatedon(new Timestamp(System.currentTimeMillis()));
					corpUserMenuMap.setCreatedon(corpUserMenuMap.getUpdatedon());
					if (!ObjectUtils.isEmpty(createdBy)) {
						corpUserMenuMap.setUpdatedby(createdBy);
					} else {
						corpUserMenuMap.setUpdatedby(new BigDecimal(1));
					}
					session.save(corpUserMenuMap);
					tx.commit();
				}
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
			return false;
		}
		return true;
	}

	public boolean updateToCorpUsersMenuMap(String[] newMenuMapId, BigDecimal corpId, BigDecimal userId, String userRRN,
			BigDecimal updatedBy) {
		logger.info("updateToCorpUsersMenuMapTmp request:CorpUserMenuList corpId: " + corpId + ", userId:" + userId);
		Session session = sessionFactory.getCurrentSession();
		// delete menus for user
		logger.info("deleteAllRegulatorMenuTmp request" + userId.intValue());

		List<BigDecimal> deleteMenuListIds = null;
		List<BigDecimal> oldMenuMapId = new ArrayList<>();
		List<String> addMenuListIds = new ArrayList<>();
		List<BigDecimal> addMenuFinalList = new ArrayList<>();

		List<CorpUserMenuMapEntity> corpMenulist = getUserMenuListByCompIdAndUserId(userId, corpId);
		if (!ObjectUtils.isEmpty(corpMenulist)) {
			deleteMenuListIds = new ArrayList<>();
			for (CorpUserMenuMapEntity oldMenu : corpMenulist) {
				oldMenuMapId.add(oldMenu.getCorpMenuId());
				deleteMenuListIds.add(oldMenu.getCorpMenuId());
			}
		}
		// convert array to arraylist
		addMenuListIds = Arrays.asList(newMenuMapId);
		// string arraylist to Bigdecimal arraylist
		addMenuListIds.forEach(s -> addMenuFinalList.add(new BigDecimal(s)));

		if (!ObjectUtils.isEmpty(deleteMenuListIds)) {
			deleteMenuListIds.removeAll(addMenuFinalList);
		}
		// delete existing record

		// get new record
		addMenuFinalList.removeAll(oldMenuMapId);

		if (!ObjectUtils.isEmpty(deleteMenuListIds)) {
			deleteFromCorpuserByUserId(deleteMenuListIds, userId.toBigInteger());
		}

		try {
			if (!ObjectUtils.isEmpty(addMenuFinalList)) {
				for (BigDecimal corpMenu : addMenuFinalList) {
					CorpUserMenuMapEntity corpUserMenuMap = new CorpUserMenuMapEntity();
					Transaction tx = session.beginTransaction();
					corpUserMenuMap.setCorpMenuId(corpMenu);
					corpUserMenuMap.setCorpUserId(userId);
					corpUserMenuMap.setCorpCompId(corpId);
					corpUserMenuMap.setStatusId(BigDecimal.valueOf(3));
					corpUserMenuMap.setUserRrn(userRRN);
					corpUserMenuMap.setUpdatedon(new Timestamp(System.currentTimeMillis()));
					corpUserMenuMap.setCreatedon(corpUserMenuMap.getUpdatedon());
					if (!ObjectUtils.isEmpty(updatedBy)) {
						corpUserMenuMap.setUpdatedby(updatedBy);
					} else {
						corpUserMenuMap.setUpdatedby(new BigDecimal(1));
					}
					session.save(corpUserMenuMap);
					tx.commit();
				}
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
			return false;
		}
		return true;
	}

	public boolean saveToCorpUsersAccMap(String[] newAccMapId, BigDecimal corpId, BigDecimal userId, String userRRN,
			BigDecimal createdBy) {
		Session session = sessionFactory.getCurrentSession();
		logger.info("updateToCorpUsersAccMapTmp request CorpUserAccList" + newAccMapId + ", corpId: " + corpId
				+ ", userId: " + userId);
		// delete accounts for user
		logger.info("deleteAllRegulatorAccountTmp");

		List<String> deleteAccListIds = null;
		List<String> oldAccMapId = new ArrayList<>();
		List<String> addAccListIds = new ArrayList<>();

		List<CorpUserAccMapEntity> corpAcclist = getUserAccListByCompIdAndUserId(userId, corpId);
		if (!ObjectUtils.isEmpty(corpAcclist)) {
			deleteAccListIds = new ArrayList<>();
			for (CorpUserAccMapEntity oldMenu : corpAcclist) {
				oldAccMapId.add(EncryptorDecryptor.decryptData(oldMenu.getAccountNo()));
				deleteAccListIds.add(EncryptorDecryptor.decryptData(oldMenu.getAccountNo()));
			}
		}
		if (!ObjectUtils.isEmpty(newAccMapId)) {
			// convert array to arraylist
			addAccListIds = Arrays.asList(newAccMapId);
		}

		if (!ObjectUtils.isEmpty(deleteAccListIds)) {
			// delete existing record
			deleteAccListIds.removeAll(addAccListIds);
		}

		if (!ObjectUtils.isEmpty(addAccListIds)) {
			// get new record
			addAccListIds.removeAll(oldAccMapId);
		}

		if (!ObjectUtils.isEmpty(deleteAccListIds)) {
			deleteAccFromCorpuserByUserId(deleteAccListIds, userId.toBigInteger());
		}
		if (!ObjectUtils.isEmpty(addAccListIds)) {
			Transaction tx = session.beginTransaction();
			try {
				for (String corpAcc : addAccListIds) {
					CorpUserAccMapEntity corpUserMenuMap = new CorpUserAccMapEntity();
					corpUserMenuMap.setAccountNo(EncryptorDecryptor.encryptData(corpAcc));
					corpUserMenuMap.setCorpUserId(userId);
					corpUserMenuMap.setCorpCompId(corpId);
					corpUserMenuMap.setStatusId(BigDecimal.valueOf(3));
					corpUserMenuMap.setUserRrn(userRRN);
					corpUserMenuMap.setUpdatedon(new Timestamp(System.currentTimeMillis()));
					corpUserMenuMap.setCreatedon(corpUserMenuMap.getUpdatedon());
					if (!ObjectUtils.isEmpty(createdBy)) {
						corpUserMenuMap.setUpdatedby(createdBy);
					} else {
						corpUserMenuMap.setUpdatedby(new BigDecimal(1));
					}
					session.save(corpUserMenuMap);
				}
				tx.commit();
			} catch (Exception e) {
				logger.error("Exception:", e);
				tx.rollback();
				return false;
			}
		}
		return true;
	}

	public boolean updateToCorpUsersAccMap(String[] newAccMapId, BigDecimal corpId, BigDecimal userId, String userRRN,
			BigDecimal updatedBy) {
		Session session = sessionFactory.getCurrentSession();
		logger.info("updateToCorpUsersAccMapTmp request CorpUserAccList" + newAccMapId + ", corpId: " + corpId
				+ ", userId: " + userId);
		// delete accounts for user
		logger.info("deleteAllRegulatorAccountTmp");

		List<String> deleteAccListIds = null;
		List<String> oldAccMapId = new ArrayList<>();
		List<String> addAccListIds = new ArrayList<>();

		List<CorpUserAccMapEntity> corpAcclist = getUserAccListByCompIdAndUserId(userId, corpId);
		if (!ObjectUtils.isEmpty(corpAcclist)) {
			deleteAccListIds = new ArrayList<>();
			for (CorpUserAccMapEntity oldMenu : corpAcclist) {
				oldAccMapId.add(oldMenu.getAccountNo());
				deleteAccListIds.add(oldMenu.getAccountNo());
			}
		}

		// convert array to arraylist
		for (String accountNo : newAccMapId) {
			addAccListIds.add(EncryptorDecryptor.encryptData(accountNo));
		}
		if (!ObjectUtils.isEmpty(deleteAccListIds)) {
			// delete existing record
			deleteAccListIds.removeAll(addAccListIds);
		}

		// get new record
		addAccListIds.removeAll(oldAccMapId);

		if (!ObjectUtils.isEmpty(deleteAccListIds)) {
			deleteAccFromCorpuserByUserId(deleteAccListIds, userId.toBigInteger());
		}
		if (!ObjectUtils.isEmpty(addAccListIds)) {
			Transaction tx = session.beginTransaction();
			try {
				for (String corpAcc : addAccListIds) {
					CorpUserAccMapEntity corpUserMenuMap = new CorpUserAccMapEntity();
					corpUserMenuMap.setAccountNo(corpAcc);
					corpUserMenuMap.setCorpUserId(userId);
					corpUserMenuMap.setCorpCompId(corpId);
					corpUserMenuMap.setStatusId(BigDecimal.valueOf(3));
					corpUserMenuMap.setUserRrn(userRRN);
					corpUserMenuMap.setUpdatedon(new Timestamp(System.currentTimeMillis()));
					corpUserMenuMap.setCreatedon(corpUserMenuMap.getUpdatedon());
					if (!ObjectUtils.isEmpty(updatedBy)) {
						corpUserMenuMap.setUpdatedby(updatedBy);
					} else {
						corpUserMenuMap.setUpdatedby(new BigDecimal(1));
					}
					session.save(corpUserMenuMap);
				}
				tx.commit();
			} catch (Exception e) {
				logger.error("Exception:", e);
				tx.rollback();
				return false;
			}
		}
		return true;
	}

	public List<CorpUserMenuMapEntity> getUserMenuListByCompIdAndUserId(BigDecimal userId, BigDecimal corpId) {
		List<CorpUserMenuMapEntity> list = null;
		try {
			String sqlQuery = "select cm.id as id, cm.corpCompId as corpCompId,cm.corpMenuId as corpMenuId,NVL(cm.corpSubMenuId,0) as corpSubMenuId, cm.corpUserId as corpUserId,"
					+ "cm.statusId as statusId from CORPUSER_MENU_MAP cm"
					+ " where cm.corpUserId =:userId and cm.corpCompId=:compId and cm.statusid = 3";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("corpCompId").addScalar("corpMenuId")
					.addScalar("corpSubMenuId").addScalar("corpUserId").addScalar("statusId")
					.setParameter("userId", userId).setParameter("compId", corpId)
					.setResultTransformer(Transformers.aliasToBean(CorpUserMenuMapEntity.class)).list();
		} catch (Exception e) {
			logger.error("Exception:", e);
		}
		return list;
	}

	public List<CorpUserAccMapEntity> getUserAccListByCompIdAndUserId(BigDecimal userId, BigDecimal corpId) {
		List<CorpUserAccMapEntity> list = null;
		try {
			String sqlQuery = "select cm.id as id, cm.corpCompId as corpCompId,cm.accountNo as accountNo, cm.corpUserId as corpUserId,"
					+ "cm.statusId as statusId from CORPUSER_Acc_MAP cm"
					+ " where cm.corpUserId =:userId and cm.corpCompId=:compId and cm.statusid = 3";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("corpCompId").addScalar("accountNo")
					.addScalar("corpUserId").addScalar("statusId").setParameter("userId", userId)
					.setParameter("compId", corpId)
					.setResultTransformer(Transformers.aliasToBean(CorpUserAccMapEntity.class)).list();
		} catch (Exception e) {
			logger.error("Exception:", e);
		}
		return list;
	}

	@Override
	public boolean saveToCorpCompMasterData(CorpDataBean corpData) {
		Session session = sessionFactory.getCurrentSession();
		Date date = new Date();
		CorpCompDataMasterEntity corpCompMasterObj = corpData.getCorpCompData();
		try {
			if (!ObjectUtils.isEmpty(corpCompMasterObj)) {
				corpCompMasterObj.setRrn(String.valueOf(System.currentTimeMillis()));
				logger.info("Offline request, CORP COMP RRN:" + corpCompMasterObj.getRrn());
				corpCompMasterObj.setUpdatedBy(corpData.getCreatedByUpdatedBy());
				corpCompMasterObj.setCreatedOn(date);
				corpCompMasterObj.setStatusId(BigDecimal.valueOf(3));
				corpCompMasterObj.setReqApproved("M");
				BigDecimal compId = (BigDecimal) session.save(corpCompMasterObj);
				logger.info("Id of save object =" + compId);
				corpData.setCorpCompId(compId);
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
			return false;
		}

		return true;

	}

	@Override
	public boolean updateOfflineCorpMenuMapData(CorpMenuAccMasterBean corpMenuAccData, BigDecimal updatedBy) {
		logger.info("updateOfflineCorpMenuMapData:");
		try {
			ArrayList<BigDecimal> oldSubMenuList = null;
			ArrayList<BigDecimal> deleteSubMenuList = null;

			Map<BigDecimal, ArrayList<BigDecimal>> oldMenuMap = new HashMap<>();
			Map<BigDecimal, ArrayList<BigDecimal>> addMenuMap = new HashMap<>();
			Map<BigDecimal, ArrayList<BigDecimal>> newMenuMap = new HashMap<>();
			Map<BigDecimal, ArrayList<BigDecimal>> deleteMenuMap = new HashMap<>();

			List<CorpMenuMapEntity> corpMenulist = getOfflineCorpMenuByCompanyId(
					corpMenuAccData.getCorpMenuList().get(0));
			if (!ObjectUtils.isEmpty(corpMenulist)) {
				for (CorpMenuMapEntity oldMenu : corpMenulist) {
					if (!ObjectUtils.isEmpty(oldMenu.getCorpSubMenuId())
							&& !oldMenu.getCorpSubMenuId().equals(new BigDecimal(0))) {
						if (ObjectUtils.isEmpty(oldSubMenuList) || !oldMenuMap.containsKey(oldMenu.getCorpMenuId())) {
							oldSubMenuList = new ArrayList<>();
							deleteSubMenuList = new ArrayList<>();
						}
						oldSubMenuList.add(oldMenu.getCorpSubMenuId());
						deleteSubMenuList.add(oldMenu.getCorpSubMenuId());
						oldMenuMap.put(oldMenu.getCorpMenuId(), oldSubMenuList);
						deleteMenuMap.put(oldMenu.getCorpMenuId(), deleteSubMenuList);
					} else {
						oldMenuMap.put(oldMenu.getCorpMenuId(), new ArrayList<>());
						deleteMenuMap.put(oldMenu.getCorpMenuId(), new ArrayList<>());
					}
				}
			}
			
			for (CorpMenuMapEntity newMenu : corpMenuAccData.getCorpMenuList()) {
				if (!ObjectUtils.isEmpty(newMenu.getCorpSubMenuIdArray())) {
					ArrayList<BigDecimal> newMenuList = new ArrayList<>(Arrays.asList(newMenu.getCorpSubMenuIdArray()));
					ArrayList<BigDecimal> addMenuList = new ArrayList<>(Arrays.asList(newMenu.getCorpSubMenuIdArray()));
					newMenuMap.put(newMenu.getCorpMenuId(), newMenuList);
					addMenuMap.put(newMenu.getCorpMenuId(), addMenuList);
				} else {
					newMenuMap.put(newMenu.getCorpMenuId(), new ArrayList<>());
					addMenuMap.put(newMenu.getCorpMenuId(), new ArrayList<>());
				}
				
			}

			Iterator<Map.Entry<BigDecimal, ArrayList<BigDecimal>>> addIterator = addMenuMap.entrySet().iterator();
			while (addIterator.hasNext()) {
				logger.info("Add submenu");
				Map.Entry<BigDecimal, ArrayList<BigDecimal>> entry = addIterator.next();
				if (oldMenuMap.containsKey(entry.getKey())) {
					Iterator<BigDecimal> it = entry.getValue().iterator();
					if (!ObjectUtils.isEmpty(entry.getValue())) {
						while (it.hasNext()) {
							BigDecimal subMenuId = it.next();
							logger.info("SubMenuId: " + subMenuId);
							if (oldMenuMap.get(entry.getKey()).contains(subMenuId)) {
								it.remove();
								if (ObjectUtils.isEmpty(addMenuMap.get(entry.getKey())))
									addIterator.remove();
							}
						}
					} else {
						addIterator.remove();
					}
				}
			}

			if (!ObjectUtils.isEmpty(deleteMenuMap)) {
				// delete records
				Iterator<Map.Entry<BigDecimal, ArrayList<BigDecimal>>> deleteIterator = deleteMenuMap.entrySet()
						.iterator();
				while (deleteIterator.hasNext()) {
					logger.info("delete menu");
					Map.Entry<BigDecimal, ArrayList<BigDecimal>> entry = deleteIterator.next();
					if (newMenuMap.containsKey(entry.getKey())) {
						Iterator<BigDecimal> it = entry.getValue().iterator();
						if (!ObjectUtils.isEmpty(entry.getValue())) {
							while (it.hasNext()) {
								BigDecimal subMenuId = it.next();
								logger.info("SubMenuId: " + subMenuId);
								if (newMenuMap.get(entry.getKey()).contains(subMenuId)
										&& !oldMenuMap.get(entry.getKey()).equals(new BigDecimal(0))) {
									it.remove();
									if (ObjectUtils.isEmpty(deleteMenuMap.get(entry.getKey())))
										deleteIterator.remove();
								}
							}
						} else {
							deleteIterator.remove();
						}
					}

				}
			}
			if (!ObjectUtils.isEmpty(deleteMenuMap)) {
				List<BigDecimal> menuList = new ArrayList<>();
				for (Map.Entry<BigDecimal, ArrayList<BigDecimal>> entry : deleteMenuMap.entrySet()) {
					if (!ObjectUtils.isEmpty(entry.getKey())) {
						if (!ObjectUtils.isEmpty(entry.getValue())) {
							for (BigDecimal subMenuId : entry.getValue()) {
								deleteFromCorpuserMapMenuAndSubMenuById(entry.getKey(), subMenuId,
										corpMenulist.get(0).getCorpId().toBigInteger());
								deleteFromMenuMapAndSubMenuById(entry.getKey(), subMenuId,
										corpMenulist.get(0).getCorpId().toBigInteger());
							}
						} else {
							menuList.add(entry.getKey());
						}
					}
				}
				deleteFromCorpuserMapMenuById(menuList, corpMenulist.get(0).getCorpId().toBigInteger());
				deleteFromMenuMapMenuById(menuList, corpMenulist.get(0).getCorpId().toBigInteger());
			}

			if (!ObjectUtils.isEmpty(addMenuMap)) {
				logger.info("saveToCorpMenuMapTmp:.....");
				saveToCorpMenuMap(corpMenulist.get(0).getCorpId(), updatedBy, addMenuMap);
			}

			List<CorpUserEntity> userData = new ArrayList<>();
			if (!ObjectUtils.isEmpty(corpMenulist)) {
				userData = getUserDetailsByCompAndRoleId(corpMenulist.get(0).getCorpId().toBigInteger());
			}

			if (!ObjectUtils.isEmpty(userData)) {
				Session session = sessionFactory.getCurrentSession();
				for (CorpUserEntity corpUserEntity : userData) {
					for (Map.Entry<BigDecimal, ArrayList<BigDecimal>> entry : addMenuMap.entrySet()) {
						if (BigDecimal.valueOf(0).equals(corpUserEntity.getParentId())
								&& !ObjectUtils.isEmpty(addMenuMap) && addMenuMap.size() > 0
								&& addMenuMap.containsKey(entry.getKey())) {
							logger.info("add new menu " + entry.getKey() + " for user " + corpUserEntity.getId());
							if (!ObjectUtils.isEmpty(entry.getValue())) {
								for (BigDecimal subMenuId : entry.getValue()) {
									CorpUserMenuMapEntity corpMenuObj = new CorpUserMenuMapEntity();
									corpMenuObj.setCorpMenuId(entry.getKey());
									corpMenuObj.setCorpSubMenuId(subMenuId);
									corpMenuObj.setCorpUserId(corpUserEntity.getId());
									corpMenuObj.setCorpCompId(corpMenulist.get(0).getCorpId());
									corpMenuObj.setStatusId(BigDecimal.valueOf(3));
									corpMenuObj.setUserRrn(corpUserEntity.getRrn());
									corpMenuObj.setCreatedon(new Date());
									corpMenuObj.setUpdatedby(updatedBy);
									session.save(corpMenuObj);
								}
							} else {
								CorpUserMenuMapEntity corpMenuObj = new CorpUserMenuMapEntity();
								corpMenuObj.setCorpMenuId(entry.getKey());
								corpMenuObj.setCorpSubMenuId(null);
								corpMenuObj.setCorpUserId(corpUserEntity.getId());
								corpMenuObj.setCorpCompId(corpMenulist.get(0).getCorpId());
								corpMenuObj.setStatusId(BigDecimal.valueOf(3));
								corpMenuObj.setUserRrn(corpUserEntity.getRrn());
								corpMenuObj.setCreatedon(new Date());
								corpMenuObj.setUpdatedby(updatedBy);
								session.save(corpMenuObj);
							}

						}
					}
				}
			}
			return true;
		} catch (Exception e) {
			logger.info("Exception:", e);
			return false;
		}
	}

	@Override
	public boolean updateOfflineCorpAccMapData(CorpMenuAccMasterBean corpMenuAccData, BigDecimal updatedBy) {
		try {

			List<String> deleteAccListIds = null;
			List<String> oldAccMapId = new ArrayList<>();
			List<String> newAccMapId = new ArrayList<>();
			List<String> addAccListIds = new ArrayList<>();

			List<CorpAccMapEntity> corpAcclist = getOfflineCorpAccByCompanyId(corpMenuAccData.getCorpAccList().get(0));
			if (!ObjectUtils.isEmpty(corpAcclist)) {
				deleteAccListIds = new ArrayList<>();
				for (CorpAccMapEntity oldAcc : corpAcclist) {
					oldAccMapId.add(oldAcc.getAccountNo());
					deleteAccListIds.add(oldAcc.getAccountNo());
				}
			}

			for (CorpAccMapEntity newAcc : corpMenuAccData.getCorpAccList()) {
				newAccMapId.add(EncryptorDecryptor.encryptData(newAcc.getAccountNo()));
				addAccListIds.add(EncryptorDecryptor.encryptData(newAcc.getAccountNo()));
			}
			if (!addAccListIds.isEmpty()) {
				// get new record
				addAccListIds.removeAll(oldAccMapId);
				logger.info("new record: " + addAccListIds.toString());
			}

			if (!ObjectUtils.isEmpty(deleteAccListIds)) {
				// delete existing record
				deleteAccListIds.removeAll(newAccMapId);
				logger.info("delete existing record: " + deleteAccListIds.toString());
			}

			if (!ObjectUtils.isEmpty(deleteAccListIds)) {
				deleteFromCorpuserAccMapById(deleteAccListIds, corpAcclist.get(0).getCorpId().toBigInteger());
				deleteFromAccMapById(deleteAccListIds, corpAcclist.get(0).getCorpId().toBigInteger());
			}

			if (!ObjectUtils.isEmpty(addAccListIds)) {
				logger.info("saveToCorpMenuMapTmp:.....");
				saveToCorpAccMap(corpMenuAccData.getCorpAccList(), updatedBy, addAccListIds);
			}

			List<CorpUserEntity> userData = new ArrayList<>();
			if (!ObjectUtils.isEmpty(corpAcclist)) {
				userData = getUserDetailsByCompAndRoleId(corpAcclist.get(0).getCorpId().toBigInteger());
			}

			if (!ObjectUtils.isEmpty(userData)) {
				Session session = sessionFactory.getCurrentSession();
				for (CorpUserEntity corpUserEntity : userData) {
					for (String acc : newAccMapId) {
						if (BigDecimal.valueOf(0).equals(corpUserEntity.getParentId())
								&& !ObjectUtils.isEmpty(addAccListIds) && addAccListIds.size() > 0
								&& addAccListIds.contains(acc)) {
							logger.info("add new account " + acc + " for user " + corpUserEntity.getId());
							CorpUserAccMapEntity corpAcc = new CorpUserAccMapEntity();
							corpAcc.setAccountNo(acc);
							corpAcc.setCorpUserId(corpUserEntity.getId());
							corpAcc.setCorpCompId(corpAcclist.get(0).getCorpId());
							corpAcc.setStatusId(BigDecimal.valueOf(3));
							corpAcc.setUserRrn(corpUserEntity.getRrn());
							corpAcc.setCreatedon(new Date());
							corpAcc.setUpdatedby(updatedBy);
							session.save(corpAcc);
						}
					}

				}
			}
			return true;
		} catch (Exception e) {
			logger.info("Exception:", e);
			return false;
		}

	}

	@Override
	public boolean deleteFromCorpuserMapMenuById(List<BigDecimal> uncommon, BigInteger compId) {
		logger.info("deleteFromCorpuserMapMenuById: menuId: " + uncommon + ",:compId " + compId);
		try {
			String sqlQuery = "UPDATE CORPUSER_MENU_MAP SET STATUSID = 10, updatedon = current_timestamp where Corpcompid = :compId AND Corpmenuid in :menuId";
			getSession().createSQLQuery(sqlQuery).setParameterList("menuId", uncommon).setParameter("compId", compId)
					.executeUpdate();
		} catch (Exception e) {
			logger.error("Exception:", e);
			return false;
		}
		return true;
	}

	@Override
	public boolean deleteFromCorpuserMapMenuAndSubMenuById(BigDecimal menuId, BigDecimal subMenuId, BigInteger compId) {
		logger.info("deleteFromCorpuserMapMenuById: menuId: " + menuId + ",:compId " + compId);
		try {
			String sqlQuery = "UPDATE CORPUSER_MENU_MAP SET STATUSID = 10, updatedon = current_timestamp where Corpcompid = :compId AND Corpmenuid =:menuId AND CorpSubMenuId =:subMenuId";
			getSession().createSQLQuery(sqlQuery).setParameter("menuId", menuId).setParameter("subMenuId", subMenuId)
					.setParameter("compId", compId).executeUpdate();
		} catch (Exception e) {
			logger.error("Exception:", e);
			return false;
		}
		return true;
	}

	public boolean deleteFromCorpuserByUserId(List<BigDecimal> menuId, BigInteger userId) {
		logger.info("deleteFromCorpuserMapMenuById: menuId: " + menuId + ",:compId " + userId);
		try {
			String sqlQuery = "UPDATE CORPUSER_MENU_MAP SET STATUSID = 10, updatedon = current_timestamp where CorpUserid = :userId AND Corpmenuid in :menuId";
			getSession().createSQLQuery(sqlQuery).setParameterList("menuId", menuId).setParameter("userId", userId)
					.executeUpdate();
		} catch (Exception e) {
			logger.error("Exception:", e);
			return false;
		}
		return true;
	}

	public boolean deleteFromMenuMapMenuById(List<BigDecimal> menuId, BigInteger compId) {
		logger.info("deleteFromMenuMapMenuById: id: " + menuId);
		try {
			String sqlQuery = "UPDATE corp_menu_map SET STATUSID = 10, updatedon = current_timestamp where Corpid = :compId AND Corpmenuid in :menuId ";
			getSession().createSQLQuery(sqlQuery).setParameter("compId", compId).setParameterList("menuId", menuId)
					.executeUpdate();
		} catch (Exception e) {
			logger.info("Exception:", e);
			return false;
		}
		return true;
	}

	public boolean deleteFromMenuMapAndSubMenuById(BigDecimal menuId, BigDecimal subMenuId, BigInteger compId) {
		logger.info("deleteFromMenuMapMenuById: id: " + menuId);
		try {
			String sqlQuery = "UPDATE corp_menu_map SET STATUSID = 10, updatedon = current_timestamp where Corpid = :compId AND Corpmenuid =:menuId and CorpSubMenuId =:subMenuId ";
			getSession().createSQLQuery(sqlQuery).setParameter("compId", compId).setParameter("menuId", menuId)
					.setParameter("subMenuId", subMenuId).executeUpdate();
		} catch (Exception e) {
			logger.info("Exception:", e);
			return false;
		}
		return true;
	}

//	public boolean deleteFromMenuMapMenuByCompAndMenuId(BigInteger compId, BigInteger menuId) {
//		logger.info("deleteFromMenuMapMenuByCompAndMenuId: id: " + compId + ",menuId" + menuId);
//		try {
//			String sqlQuery = "UPDATE corp_menu_map SET STATUSID = 10 where Corpid = :compId AND Corpmenuid = :menuId";
//			getSession().createSQLQuery(sqlQuery).setParameter("compId", compId).setParameter("menuId", menuId)
//					.executeUpdate();
//		} catch (Exception e) {
//			logger.info("Exception:", e);
//			return false;
//		}
//		return true;
//	}

	@Override
	public boolean deleteFromCorpuserAccMapById(List<String> accNo, BigInteger compId) {
		logger.info("deleteFromCorpuserAccMapById: accNo: " + accNo + ",compId" + compId);
		try {
			String sqlQuery = "UPDATE CORPUSER_ACC_MAP SET STATUSID = 10, updatedon = current_timestamp where Corpcompid = :compId AND Accountno in :accNo ";
			getSession().createSQLQuery(sqlQuery).setParameter("compId", compId).setParameterList("accNo", accNo)
					.executeUpdate();
		} catch (Exception e) {
			logger.info("Exception:", e);
			return false;
		}
		return true;
	}

	public boolean deleteAccFromCorpuserByUserId(List<String> accNo, BigInteger corpUserId) {
		logger.info("deleteFromCorpuserAccMapById: accNo: " + accNo + ",compId" + corpUserId);
		try {
			String sqlQuery = "UPDATE CORPUSER_ACC_MAP SET STATUSID = 10, updatedon = current_timestamp where corpUserId = :corpUserId AND Accountno in :accNo ";
			getSession().createSQLQuery(sqlQuery).setParameter("corpUserId", corpUserId)
					.setParameterList("accNo", accNo).executeUpdate();
		} catch (Exception e) {
			logger.info("Exception:", e);
			return false;
		}
		return true;
	}

//	public boolean deleteAllRegulatorMenu(Integer userId, Integer compId) {
//		logger.info("deleteAllRegulatorMenu: compId: " + compId + ",User: " + userId);
//		try {
//			String sqlQuery = "UPDATE CORPUSER_MENU_MAP SET STATUSID = 10 where Corpcompid = :compId AND Corpuserid = :userId AND statusid <> 10";
//			getSession().createSQLQuery(sqlQuery).setParameter("userId", userId).setParameter("compId", compId)
//					.executeUpdate();
//		} catch (Exception e) {
//			logger.info("Exception:", e);
//			return false;
//		}
//		return true;
//	}

//	public boolean deleteCorpUsersMenu(Integer userId, Integer compId) {
//		logger.info("deleteAllRegulatorMenu: compId: " + compId + ",User: " + userId);
//		try {
//			String sqlQuery = "UPDATE CORPUSER_MENU_MAP SET STATUSID = 10 where Corpcompid = :compId AND Corpuserid = :userId AND corpmenuid=:deleteCorpUsersMenu";
//			getSession().createSQLQuery(sqlQuery).setParameter("userId", userId).setParameter("compId", compId)
//					.executeUpdate();
//		} catch (Exception e) {
//			logger.info("Exception:", e);
//			return false;
//		}
//		return true;
//	}

//	public boolean deleteAllRegulatorAccount(BigInteger userId, BigInteger compId, Session session) {
//		logger.info("deleteAllRegulatorAccount: User: " + userId + ", User: " + userId);
//		try {
//			String sqlQuery = "UPDATE CORPUSER_ACC_MAP SET STATUSID = 10, updatedon = current_timestamp where Corpcompid = :compId AND Corpuserid = :userId";
//			getSession().createSQLQuery(sqlQuery).setParameter("userId", userId).setParameter("compId", compId)
//					.executeUpdate();
//		} catch (Exception e) {
//			logger.info("Exception:", e);
//			return false;
//		}
//		return true;
//	}

	public boolean deleteFromAccMapById(List<String> accNo, BigInteger compId) {
		logger.info("deleteFromAccMapById: User: " + accNo);
		boolean success = true;
		try {
			String sqlQuery = "UPDATE corp_acc_map SET STATUSID = 10, updatedon = current_timestamp where corpId = :compId AND Accountno in :accNo ";
			getSession().createSQLQuery(sqlQuery).setParameterList("accNo", accNo).setParameter("compId", compId)
					.executeUpdate();
		} catch (Exception e) {
			logger.info("Exception:", e);
			return false;
		}
		return success;
	}

//	public List<CorpUserMenuMapEntity> getUserMenuListByMenuId(BigInteger menuId, BigInteger compId) {
//		logger.info("getUserMenuListByMenuId: menuId: " + menuId + ", compId: " + compId);
//		List<CorpUserMenuMapEntity> list = null;
//		try {
//
//			String sqlQuery = "select cm.id as id, cm.corpCompId as corpCompId,cm.corpMenuId as corpMenuId, cm.corpUserId as corpUserId,"
//					+ "cm.statusId as statusId, cc.menuName as menuName, cm.userRrn as userRrn from CORPUSER_MENU_MAP cm inner join corp_menu cc on cc.id =cm.corpMenuId"
//					+ "    where cm.corpMenuId =:menuId and cm. corpCompId=:compId and cm.statusid = 3";
//			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("corpCompId").addScalar("corpMenuId")
//					.addScalar("corpUserId").addScalar("statusId").addScalar("menuName").addScalar("userRrn")
//					.setParameter("menuId", menuId).setParameter("compId", compId)
//					.setResultTransformer(Transformers.aliasToBean(CorpUserMenuMapEntity.class)).list();
//		} catch (Exception e) {
//			logger.info("Exception:", e);
//		}
//		return list;
//	}

//	public List<CorpUserAccMapEntity> getUserAccountListByAccountNo(String accNo, BigInteger compId) {
//		logger.info("getUserAccountListByAccountNo: accNo: " + accNo + ", compId: " + compId);
//		List<CorpUserAccMapEntity> list = null;
//		try {
//
//			String sqlQuery = "select ca.id as id, ca.corpCompId as corpCompId, ca.accountNo as accountNo,ca.corpUserId as corpUserId, ca.statusId as statusId, ca.userRrn as userRrn "
//					+ "from CORPUSER_ACC_MAP ca where ca.Corpcompid =:accNo and ca.corpCompId=:compId and ca.statusid = 3";
//
//			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("corpCompId").addScalar("accountNo")
//					.addScalar("corpUserId").addScalar("statusId").addScalar("userRrn").setParameter("accountNo", accNo)
//					.setParameter("compId", compId)
//					.setResultTransformer(Transformers.aliasToBean(CorpUserAccMapEntity.class)).list();
//		} catch (Exception e) {
//			logger.info("Exception:", e);
//		}
//		return list;
//	}

//	public boolean saveToCorpMenuMap(List<CorpMenuMapEntity> corpMenuData) {
//		logger.info("saveToCorpMenuMap: ");
//		Session session = sessionFactory.getCurrentSession();
//		Date date = new Date();
//		try {
//			for (CorpMenuMapEntity corpMenu : corpMenuData) {
//				corpMenu.setCreatedon(date);
//				corpMenu.setUpdatedon(date);
//				corpMenu.setStatusId(BigDecimal.valueOf(3));
//				session.save(corpMenu);
//			}
//
//		} catch (Exception e) {
//			logger.info("Exception:", e);
//			return false;
//		}
//		return true;
//	}

	public boolean saveToCorpMenuMap(BigDecimal corpId, BigDecimal updatedBy,
			Map<BigDecimal, ArrayList<BigDecimal>> addMenuMap) {
		logger.info("saveToCorpMenuMapTmp: ");
		Session session = sessionFactory.getCurrentSession();
		try {
			for (Map.Entry<BigDecimal, ArrayList<BigDecimal>> entry : addMenuMap.entrySet()) {
				if (!ObjectUtils.isEmpty(entry.getKey()) && !ObjectUtils.isEmpty(entry.getValue())) {
					for (BigDecimal subMenuId : entry.getValue()) {
						CorpMenuMapEntity corpMenu = new CorpMenuMapEntity();
						corpMenu.setCorpId(corpId);
						corpMenu.setCorpSubMenuId(subMenuId);
						corpMenu.setCorpMenuId(entry.getKey());
						corpMenu.setUpdatedby(updatedBy);
						corpMenu.setStatusId(BigDecimal.valueOf(3));
						corpMenu.setCreatedon(new Date());
						session.save(corpMenu);
					}
				} else {
					CorpMenuMapEntity corpMenu = new CorpMenuMapEntity();
					corpMenu.setCorpId(corpId);
					corpMenu.setCorpSubMenuId(null);
					corpMenu.setCorpMenuId(entry.getKey());
					corpMenu.setCreatedon(new Date());
					corpMenu.setCreatedon(new Date());
					corpMenu.setUpdatedby(updatedBy);
					corpMenu.setStatusId(BigDecimal.valueOf(3));
					session.save(corpMenu);
				}
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
			return false;
		}
		return true;
	}

	public boolean saveToCorpAccMap(List<CorpAccMapEntity> corpAccData, BigDecimal updatedBy,
			List<String> addAccListIds) {
		logger.info("saveToCorpAccMapTmp: ");
		Session session = sessionFactory.getCurrentSession();
		try {
			for (CorpAccMapEntity corpAcc : corpAccData) {
				if (!addAccListIds.contains(EncryptorDecryptor.encryptData(corpAcc.getAccountNo()))) {
					continue;
				}
				corpAcc.setAccountNo(EncryptorDecryptor.encryptData(corpAcc.getAccountNo()));
				corpAcc.setCreatedon(new Date());
				corpAcc.setUpdatedby(updatedBy);
				corpAcc.setStatusId(BigDecimal.valueOf(3));
				session.save(corpAcc);
			}

		} catch (Exception e) {
			logger.info("Exception:", e);
			return false;
		}
		return true;
	}

	@Override
	public boolean updateOfflineCorpUserMenuData(List<CorpUserMenuMapEntity> CorpMenuData) {
		logger.info("updateOfflineCorpUserMenuData: ");
		Session session = sessionFactory.getCurrentSession();
		Date date = new Date();
		boolean isDeleted = false;
		try {
			isDeleted = deleteCorpUserMenu(CorpMenuData.get(0).getCorpCompId().toBigInteger());
			if (isDeleted) {
				for (CorpUserMenuMapEntity corpMenuObj : CorpMenuData) {
					corpMenuObj.setCreatedon(date);
					corpMenuObj.setUpdatedon(date);
					session.save(corpMenuObj);
				}
			}

		} catch (Exception e) {
			logger.info("Exception:", e);
			return false;
		}
		return true;
	}

	@Override
	public boolean updateOfflineCorpUserAccData(List<CorpUserAccMapEntity> CorpAccData) {
		logger.info("updateOfflineCorpUserAccData: ");
		Session session = sessionFactory.getCurrentSession();
		Date date = new Date();
		boolean isDeleted = false;
		try {
			isDeleted = deleteCorpUserAccounts(CorpAccData.get(0).getCorpCompId().toBigInteger());
			if (isDeleted) {
				for (CorpUserAccMapEntity corpAcc : CorpAccData) {
					corpAcc.setUpdatedon(date);
					session.save(corpAcc);
				}
			}

		} catch (Exception e) {
			logger.info("Exception:", e);
			return false;
		}
		return true;
	}

	@Override
	public boolean updateCorpUserData(List<CorpUserEntity> corpUserData) {
		logger.info("updateCorpUserData: ");
		Session session = sessionFactory.getCurrentSession();
		boolean isUserUpdate = false;
		ResponseMessageBean resp = new ResponseMessageBean();
		try {
			for (CorpUserEntity corpUser : corpUserData) {
				corpUser.setUser_pwd(getCorpUserById(corpUser.getId().toBigInteger()).get(0).getUser_pwd());
				adminWorkFlowReqUtility.encryptCorpUserData(corpUser);
				corpUser.setCreatedon(new Timestamp(System.currentTimeMillis()));
				corpUser.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
				session.update(corpUser);
				isUserUpdate = true;
			}

		} catch (Exception e) {
			logger.info("Exception:", e);
			isUserUpdate = false;
		}
		return isUserUpdate;
	}

	@Override
	public boolean deleteCorpUserMenu(BigInteger compId) {
		logger.info("deleteCorpUserMenu: compId:" + compId);
		try {
			String sqlQuery = "UPDATE CORPUSER_MENU_MAP SET STATUSID = 10 where Corpcompid = :compId";
			getSession().createSQLQuery(sqlQuery).setParameter("compId", compId).executeUpdate();
		} catch (Exception e) {
			logger.error("Exception:", e);
			return false;
		}
		return true;
	}

	@Override
	public boolean deleteCorpUserAccounts(BigInteger compId) {
		logger.info("deleteCorpUserAccounts: compId:" + compId);
		try {
			String sqlQuery = "UPDATE CORPUSER_ACC_MAP SET STATUS = 10 where Corpcompid = :compId";
			getSession().createSQLQuery(sqlQuery).setParameter("compId", compId).executeUpdate();
		} catch (Exception e) {
			logger.info("Exception:", e);
			return false;
		}
		return true;
	}

	@Override
	public boolean updateCorpUserMenuMapDetails(List<CorpUserEntity> corpUserData) {
		logger.info("updateCorpUserMenuMapDetails: ");
		String[] CorpUserMenuList = null;
		boolean isDeleted = false;
		try {

			for (CorpUserEntity corpUser : corpUserData) {
				isDeleted = deleteuserMapMenuByCompAndUserId(corpUser.getId().toBigInteger(),
						corpUserData.get(0).getCorp_comp_id().toBigInteger());
				CorpUserMenuList = corpUser.getMenuList().split(",");
				saveToCorpUsersMenuMap(CorpUserMenuList, corpUser.getCorp_comp_id(), corpUser.getId(),
						corpUser.getRrn(), corpUser.getUpdatedby());
			}

		} catch (Exception e) {
			logger.info("Exception:", e);
			return false;
		}
		return true;

	}

	@Override
	public boolean updateCorpUserAccMapDetails(List<CorpUserEntity> corpUserData) {
		logger.info("updateCorpUserAccMapDetails: ");
		String[] corpUserAccountList = null;
		try {
			for (CorpUserEntity corpUser : corpUserData) {
				deleteuserMapAccByCompAndUserId(corpUser.getId().toBigInteger(),
						corpUserData.get(0).getCorp_comp_id().toBigInteger());
				corpUserAccountList = corpUser.getAccountList().split(",");
				saveToCorpUsersAccMap(corpUserAccountList, corpUser.getCorp_comp_id(), corpUser.getId(),
						corpUser.getRrn(), corpUser.getUpdatedby());
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
			return false;
		}
		return true;
	}

	public boolean deleteuserMapMenuByCompAndUserId(BigInteger userId, BigInteger compId) {
		logger.info("deleteuserMapMenuByCompAndUserId: ");
		try {
			String sqlQuery = "UPDATE CORPUSER_MENU_MAP SET STATUSID = 10 where Corpcompid = :compId AND Corpuserid = :userId";
			getSession().createSQLQuery(sqlQuery).setParameter("userId", userId).setParameter("compId", compId)
					.executeUpdate();
		} catch (Exception e) {
			logger.info("Exception:", e);
			return false;
		}
		return true;
	}

	public boolean deleteuserMapAccByCompAndUserId(BigInteger userId, BigInteger compId) {
		logger.info("deleteuserMapAccByCompAndUserId: ");
		try {
			String sqlQuery = "UPDATE CORPUSER_ACC_MAP SET STATUSID = 10 where Corpcompid = :compId AND Corpuserid = :userId";
			getSession().createSQLQuery(sqlQuery).setParameter("userId", userId).setParameter("compId", compId)
					.executeUpdate();
		} catch (Exception e) {
			logger.info("Exception:", e);
			return false;
		}
		return true;
	}

	@Override
	public List<CorpUserEntity> getCorpUserById(BigInteger id) {
		logger.info("getCorpUserById: id" + id);
		List<CorpUserEntity> list = null;
		try {

			String sqlQuery = "select cu.id,cu.corp_comp_id as corpid, cu.user_name as userName,cu.first_name as firstName,cu.last_name as lastName,cu.email_id as email,cu.parentId as parentId,"
					+ "	cu.personal_Phone as mobile,cu.user_pwd as userPassword, cu.dob as dob,cu.pancardNo as pancardNo, cu.rrn as rrn,cu.corpRoleId as corpRoleId, cu.aadharCardNo as aadharCard,"
					+ "	cu.passport as passport, cu.passportNumber as passportNo, cu.boardResolution as boardResolution, cu.user_image as userImage, cu.aadharCardNo as aadharCardNo,"
					+ "	cu.otherDoc as otherDoc,cu.certificate_incorporation as certificateIncorporation, cu.parentRrn as parentRrn, cu.authType as authType,"
					+ "	cu.statusid as statusId, cu.createdon as createdon, cu.updatedOn as updatedon,cu.updatedby as updatedby,cr.corpRoleName as corpRoleName, cc.name as parentRoleName, "
					+ "cu.parentRoleId as parentRoleId, cu.parentUserName as parentUserName, "
					+ "cu.nationalIdNumber as nationalIdNumber, cu.tpin_status as tpin_status, cu.rights as rights, cu.pkiStatus as pkiStatus "
					+ "cu.tpin_wrong_attempt as tpin_wrong_attempt,cu.city as city, cu.wrong_pwd_attempt as wrong_pwd_attempt, cu.pwd_status as pwd_status, "
					+ "cu.state as state, cu.designation as designation, cu.last_pwd_wrong_attempt as last_pwd_wrong_attempt,cu.mpin as mpin, "
					+ "cu.mpin_wrong_attempt as mpin_wrong_attempt,  cu.pancard as pancard,"
					+ "cu.ibRegStatus as ibRegStatus, cu.parentRoleId as parentRoleId,cu.remark as remark, cu.wrongAttemptSoftToken as wrongAttemptSoftToken from CORP_USERS  cu  "
					+ " left join corp_roles cr on cr.id=cu.corpRoleId    left join corp_roles cc on cc.id=cu.parentRoleId where cu.id=:id	";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("corpid").addScalar("userName")
					.addScalar("firstName").addScalar("lastName").addScalar("email").addScalar("parentId")
					.addScalar("mobile").addScalar("userPassword").addScalar("dob").addScalar("pancardNo")
					.addScalar("rrn").addScalar("corpRoleId").addScalar("aadharCard", StandardBasicTypes.STRING)
					.addScalar("passport", StandardBasicTypes.STRING).addScalar("passportNo")
					.addScalar("boardResolution", StandardBasicTypes.STRING)
					.addScalar("userImage", StandardBasicTypes.STRING).addScalar("aadharCardNo")
					.addScalar("otherDoc", StandardBasicTypes.STRING)
					.addScalar("certificateIncorporation", StandardBasicTypes.STRING).addScalar("parentRrn")
					.addScalar("authType").addScalar("statusId").addScalar("createdon").addScalar("updatedon")
					.addScalar("updatedby").addScalar("corpRoleName").addScalar("parentRoleName")
					.addScalar("parentRoleId").addScalar("parentUserName").addScalar("nationalIdNumber")
					.addScalar("tpin_status").addScalar("rights").addScalar("pkiStatus").addScalar("tpin_wrong_attempt")
					.addScalar("city").addScalar("wrong_pwd_attempt").addScalar("pwd_status").addScalar("state")
					.addScalar("designation").addScalar("last_pwd_wrong_attempt").addScalar("mpin")
					.addScalar("mpin_wrong_attempt").addScalar("pancard").addScalar("tpin_status").addScalar("rights")
					.addScalar("pkiStatus").addScalar("tpin_wrong_attempt").addScalar("ibRegStatus")
					.addScalar("parentRoleId").addScalar("remark").addScalar("wrongAttemptSoftToken")
					.setParameter("id", id).setResultTransformer(Transformers.aliasToBean(CorpUserEntity.class)).list();

			for (CorpUserEntity corpData : list) {
				corpData.setPersonal_Phone(EncryptorDecryptor.decryptData(corpData.getPersonal_Phone()));
				corpData.setUser_name(EncryptorDecryptor.decryptData(corpData.getUser_name()));
				corpData.setEmail_id(EncryptorDecryptor.decryptData(corpData.getEmail_id()));
				corpData.setDob(EncryptorDecryptor.decryptData(corpData.getDob()));
				corpData.setPancard(EncryptorDecryptor.decryptData(corpData.getPancard()));
				corpData.setPassportNumber(EncryptorDecryptor.decryptData(corpData.getPassportNumber()));
				corpData.setAadharCardNo(EncryptorDecryptor.decryptData(corpData.getAadharCardNo()));

			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return list;
	}

	@Override
	public boolean updateAddCorpMasterUser(CorpDataBean corpData, String branchCode) {
		logger.info("updateAddCorpMasterUserTmp: branchCode: " + branchCode);
		boolean isSaved = false;
		BigDecimal userId = BigDecimal.valueOf(0);
		String userRRN = null;
		try {
			for (CorpUserEntity corpUser : corpData.getCorpUserMasterData()) {
				String[] CorpUserMenuList = {};
				String[] CorpUserAccountList = {};
				CorpUserEntity corpUserEntity = new CorpUserEntity();
				corpUserEntity.setCorp_comp_id(corpData.getCorpCompId());
				corpUserEntity.setUser_name(corpUser.getParentUserName());
				corpUserEntity.setUpdatedby(corpData.getCreatedByUpdatedBy());
				if (ObjectUtils.isEmpty(corpUser.getOgstatus())) {
					corpUser.setOgstatus(BigDecimal.valueOf(0));
				}
				corpUser.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
				corpUser.setUpdatedby(corpData.getCreatedByUpdatedBy());
				corpUser.setWrongAttemptSoftToken(new BigDecimal(0));
				logger.info(userRRN);
				ResponseMessageBean response = new ResponseMessageBean();
				if (null != corpUser.getParentUserName()) {
					response = isCompanyUserExist(corpUserEntity);
				}
				int corpRoleId = corpUser.getCorpRoleId().intValue();
				corpUser.setRrn(String.valueOf(System.currentTimeMillis()));
				if (corpRoleId == 1) {
					corpUser.setParentRoleId(BigDecimal.valueOf(0));
				} else if (corpRoleId == 2) {
					if (corpData.getUserTypes().equals("M") && corpData.getAdminTypes().equals("MA")) {
						logger.info("update MultiAdmin:");
						corpUser.setParentRoleId(new BigDecimal(corpRoleId));
						corpUser.setParentId(new BigDecimal(response.getResult().toString()));
					} else {
						logger.info("update Single MultiAdmin:");
						corpUser.setParentRoleId(new BigDecimal(0));
						corpUser.setParentId(new BigDecimal(0));
					}
				} else if (corpRoleId == 3 || corpRoleId == 4 || corpRoleId == 5 || corpRoleId == 6) {
					logger.info("update Maker/Checker/Approver/Operator: ");
					if (!ObjectUtils.isEmpty(response.getResult())) {
						corpUser.setParentId(new BigDecimal(response.getResult().toString()));
						corpUser.setParentRoleId(new BigDecimal(2));
					} else {
						corpUser.setParentId(new BigDecimal(0));
						corpUser.setParentRoleId(new BigDecimal(0));
					}
					logger.info("update Maker/Checker/Approver/Operator: ParentRoleId " + corpUser.getParentRoleId());
				}
				if ("500" == response.getResponseCode()) {
					corpUser.setParentId(new BigDecimal(response.getResult().toString()));
				} else {
					corpUser.setParentId(BigDecimal.valueOf(0));
				}

				userRRN = corpUser.getRrn();
				adminWorkFlowReqUtility.encryptCorpUserData(corpUser);
				userId = updateCorpUserData(corpUser);

				if (!ObjectUtils.isEmpty(corpUser.getMenuList())) {
					if (corpUser.getMenuList().contains(",")) {
						CorpUserMenuList = corpUser.getMenuList().split(",");
					} else {
						CorpUserMenuList = new String[] { "" + corpUser.getMenuList() };
					}
				}
				if (!ObjectUtils.isEmpty(corpUser.getAccountList())) {
					if (corpUser.getAccountList().contains(",")) {
						CorpUserAccountList = corpUser.getAccountList().split(",");
					} else {
						CorpUserAccountList = new String[] { "" + corpUser.getAccountList() };
					}
				}
				if (!corpUser.getStatusid().equals(BigDecimal.valueOf(8))) {
					logger.info("update Maker/Checker/Approver/Operator: CorpUserMenuList " + CorpUserMenuList);
					updateToCorpUsersMenuMap(CorpUserMenuList, corpUser.getCorp_comp_id(), userId, userRRN,
							corpUser.getUpdatedby());
					logger.info("update Maker/Checker/Approver/Operator: CorpUserAccountList " + CorpUserAccountList);
					updateToCorpUsersAccMap(CorpUserAccountList, corpUser.getCorp_comp_id(), userId, userRRN,
							corpUser.getUpdatedby());
				}
				isSaved = true;
			}

		} catch (Exception e) {
			logger.info("Exception:", e);
			isSaved = false;
		}
		return isSaved;
	}

	@Override
	public List<CorpUserMenuMapBean> getUserMenuListByCorpUserId(CorpUserMenuMapEntity corpUserMenuData) {
		List<CorpUserMenuMapBean> list = null;
		try {
			logger.info("getUserMenuListByCorpUserId: userId: " + corpUserMenuData.getCorpUserId());
			String sqlQuery = "select cm.id as id, cm.corpCompId as corpCompId,cm.corpMenuId as corpMenuId,NVL(cm.corpSubMenuId,0) as corpSubMenuId, cm.corpUserId as corpUserId,"
					+ "cm.statusId as statusId, cc.menuName as menuName , NVL(csm.menuname, 'NA') as subMenuName, cm.userRrn as userRrn from CORPUSER_MENU_MAP cm inner join corp_menu cc on cc.id =cm.corpMenuId "
					+ "inner join corp_submenu csm on csm.id =cm.corpSubMenuId  where cm.corpUserId =:userId and cm.statusid = 3";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("corpCompId").addScalar("corpMenuId")
					.addScalar("corpSubMenuId").addScalar("corpUserId").addScalar("statusId").addScalar("menuName")
					.addScalar("subMenuName").addScalar("userRrn")
					.setParameter("userId", corpUserMenuData.getCorpUserId())
					.setResultTransformer(Transformers.aliasToBean(CorpUserMenuMapBean.class)).list();
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return list;
	}

	@Override
	public List<CorpUserAccMapBean> getUserAccountListByCorpUserId(CorpUserAccMapEntity corpUserAccMapBean) {
		List<CorpUserAccMapBean> list = null;
		try {
			logger.info("getUserAccountListByCorpUserId: userId: " + corpUserAccMapBean.getCorpUserId());
			String sqlQuery = "select ca.id as id, ca.corpCompId as corpCompId, ca.accountNo as accountNo,ca.corpUserId as corpUserId, ca.statusId as statusId, "
					+ " ca.userRrn as userRrn from CORPUSER_ACC_MAP ca where ca.corpUserId =:userId and ca.statusid = 3";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("corpCompId").addScalar("accountNo")
					.addScalar("corpUserId").addScalar("statusId")
					.setParameter("userId", corpUserAccMapBean.getCorpUserId())
					.setResultTransformer(Transformers.aliasToBean(CorpUserAccMapBean.class)).list();

		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return list;
	}

//	public List<CorpUserEntity> getUserDetailsByUserName(CorpUserEntity userReq) {
//		List<CorpUserEntity> list = null;
//		try {
//			logger.info(
//					"getUserDetailsByUserName userId: " + EncryptorDecryptor.encryptData(userReq.getParentUserName()));
//			String sqlQuery = "select u.id, u.user_uame as userName, u.rrn as rrn  from CORP_USERS u where u.user_name=:userId";
//
//			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("userName").addScalar("rrn")
//					.setParameter("userId", EncryptorDecryptor.encryptData(userReq.getParentUserName()))
//					.setResultTransformer(Transformers.aliasToBean(CorpUserEntity.class)).list();
//
//		} catch (Exception e) {
//			logger.info("Exception:", e);
//		}
//		return list;
//	}

	@Override
	public boolean deleteCorpUserData(CorpUserEntity corpData) {
		logger.info("deleteCorpUserData: ");
		int res = 0;
		try {
			String sql = "update CORP_USERS set statusid = 0 where id=:id";
			res = getSession().createSQLQuery(sql).setParameter("id", corpData.getId()).executeUpdate();
			logger.info("deleteCorpUserByParentId: " + corpData.getId().toBigInteger());
			if (corpData.getCorpRoleId().intValue() == 1) {
				res = deleteCorpUserByParentId(corpData.getId().toBigInteger());
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}
		return res > 0;
	}

	public int deleteCorpUserByParentId(BigInteger parentId) {
		logger.info("deleteCorpUserByParentId: " + parentId);
		try {
			String sql = "UPDATE CORP_USERS_MASTER set statusId = 0 where parentId = :parentId";
			return getSession().createSQLQuery(sql).setParameter("parentId", parentId).executeUpdate();
		} catch (Exception e) {
			logger.error("Exception:", e);
			return 0;
		}
	}

	public List<CorpUserEntity> getUserDetailsByCompAndRoleId(BigInteger compId) {
		logger.info("deleteCorpUserByParentId: " + compId);
		List<CorpUserEntity> list = null;
		try {
			String sqlQuery = "select u.id, u.user_name, u.rrn as rrn,u.updatedby as updatedby, u.parentId  from CORP_USERS u where u.corp_comp_id=:compId";// and
			// u.corpRoleId=1";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("user_name").addScalar("rrn")
					.addScalar("updatedby").addScalar("parentId").setParameter("compId", compId)
					.setResultTransformer(Transformers.aliasToBean(CorpUserEntity.class)).list();

		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return list;
	}

	@Override
	public ResponseMessageBean isCompanyUserExist(CorpUserEntity corpUser) {

		ResponseMessageBean rmb = new ResponseMessageBean();
		try {
			String sqlUserNameExist = " SELECT id FROM CORP_USERS WHERE user_name =:userName AND CORP_COMP_ID=:corpCompId";
			List userNameExist = getSession().createSQLQuery(sqlUserNameExist)
					.setParameter("userName", EncryptorDecryptor.encryptData(corpUser.getUser_name()))
					.setParameter("corpCompId", corpUser.getCorp_comp_id()).list();
			if (userNameExist.size() > 0) {
				rmb.setResponseCode("500");
				rmb.setResponseMessage("UserName Is Already Exist");
				rmb.setResult(userNameExist.get(0));
			} else {
				rmb.setResponseCode("200");
				rmb.setResponseMessage("Both Not Exist");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return rmb;
	}

	public CorpUserEntity saveCorpUserData(CorpUserEntity corpUser) {
		logger.info("saveCorpUserDataTmp");
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			corpUser.setWork_phone(corpUser.getPersonal_Phone());
			corpUser.setUser_type(corpUser.getCorpRoleId());
			corpUser.setAppid(BigDecimal.valueOf(2));
			corpUser.setCountry(BigDecimal.valueOf(1));
			corpUser.setCity(BigDecimal.valueOf(1));
			corpUser.setState(BigDecimal.valueOf(1));
			corpUser.setTpin_wrong_attempt(BigDecimal.valueOf(0));
			corpUser.setWrong_pwd_attempt(BigDecimal.valueOf(0));
			corpUser.setMpin_wrong_attempt(BigDecimal.valueOf(0));
			corpUser.setMobRegStatus(BigDecimal.valueOf(3));
			corpUser.setIbRegStatus(BigDecimal.valueOf(3));
			corpUser.setWrongAttemptSoftToken(BigDecimal.valueOf(0));
			corpUser.setPwd_status("Y");
			session.saveOrUpdate(corpUser);
			tx.commit();
			return corpUser;
		} catch (Exception e) {
			logger.error("Exception:", e);
			return corpUser;
		}

	}

	public BigDecimal updateCorpUserData(CorpUserEntity corpUser) {
		logger.info("updateCorpUserData");
		Session session = sessionFactory.getCurrentSession();
		try {
			corpUser.setWork_phone(corpUser.getPersonal_Phone());
			corpUser.setUser_type(corpUser.getCorpRoleId());
			corpUser.setAppid(BigDecimal.valueOf(2));
			corpUser.setCountry(BigDecimal.valueOf(1));
			corpUser.setCity(BigDecimal.valueOf(1));
			corpUser.setState(BigDecimal.valueOf(1));
			corpUser.setTpin_wrong_attempt(BigDecimal.valueOf(0));
			corpUser.setWrong_pwd_attempt(BigDecimal.valueOf(0));
			corpUser.setMpin_wrong_attempt(BigDecimal.valueOf(0));
			corpUser.setMobRegStatus(BigDecimal.valueOf(3));
			corpUser.setIbRegStatus(BigDecimal.valueOf(3));
			corpUser.setPwd_status("Y");
			session.update(corpUser);
			return corpUser.getId();
		} catch (Exception e) {
			logger.info("Exception:", e);
			return corpUser.getId();
		}

	}

	@Override
	public ResponseMessageBean addCorpMasData(CorpDataBean corpData) {
		logger.info("CreateCorpUserData.....");
		ResponseMessageBean response = new ResponseMessageBean();
		Session session = sessionFactory.getCurrentSession();
		CorpCompanyMasterEntity corpCompMasterObj = corpData.getCorpCompMasterData();
		try {
			if (!ObjectUtils.isEmpty(corpCompMasterObj)) {
				if (ObjectUtils.isEmpty(corpCompMasterObj.getCompanyCode())) {
					response.setResponseCode("202");
					response.setResponseMessage("CompanyCode field required");
					return response;
				} else if (corpCompMasterObj.getCompanyCode().length() < 8) {
					response.setResponseCode("202");
					response.setResponseMessage("Usage of keywords restricted.(ex:Delete, Update, Select)");
					return response;
				}

				String comId = corpCompMasterObj.getCompanyCode().toLowerCase();
				String cif = corpCompMasterObj.getCif();
				String pan = corpCompMasterObj.getPancardNo();
				BigDecimal maxLimit = corpCompMasterObj.getMaxLimit();
				String address = corpCompMasterObj.getAddress();

				corpCompMasterObj.setCompanyName(
						EncryptorDecryptor.encryptData(corpData.getCorpCompMasterData().getCompanyName()));
				corpCompMasterObj.setCompanyCode(EncryptorDecryptor.encryptData(comId));
				corpCompMasterObj.setCif(EncryptorDecryptor.encryptData(cif));// Add if statement
				corpCompMasterObj.setPancardNo(EncryptorDecryptor.encryptData(pan));
				logger.info("Offline request, CORP COMPRRN:" + corpCompMasterObj.getRrn());
				corpCompMasterObj.setCreatedOn(new Timestamp(System.currentTimeMillis()));
				if (corpCompMasterObj.getLoginId().equalsIgnoreCase("6")
						|| corpCompMasterObj.getLoginType().equalsIgnoreCase("Checker")) {
					corpCompMasterObj.setStatusId(BigDecimal.valueOf(36));
				} else {
					corpCompMasterObj.setStatusId(BigDecimal.valueOf(0));
				}
				corpCompMasterObj.setOgstatus(BigDecimal.valueOf(0));
				corpCompMasterObj.setMaxLimit(maxLimit);
				corpCompMasterObj.setAddress(address);
				corpCompMasterObj.setAdminTypes(corpCompMasterObj.getAdminTypes());
				corpCompMasterObj.setAppId(2l);
				corpCompMasterObj.setCreatedBy(corpData.getCreatedByUpdatedBy());
				corpCompMasterObj.setMakerLimit(maxLimit);
				corpCompMasterObj.setCheckerLimit(maxLimit);
				corpCompMasterObj.setApprovalLevel(corpData.getCorpCompMasterData().getUserTypes() + "");
				corpCompMasterObj.setIsCorporate('G');
				corpCompMasterObj.setBranchCode(corpData.getCorpCompMasterData().getBranchCode());
				if (corpData.getCorpCompMasterData().getUserTypes() == 'S') {
					corpCompMasterObj.setLevelMaster(BigDecimal.valueOf(0));
				} else {
					corpCompMasterObj
							.setLevelMaster(BigDecimal.valueOf(corpData.getCorpCompMasterData().getMultiUser()));
				}
				logger.info("create company Response Data : " + corpCompMasterObj.toString());
				BigDecimal compId = (BigDecimal) session.save(corpCompMasterObj);
				logger.info("Id of save object =" + compId);
				corpData.setCorpCompId(compId);
				if (!ObjectUtils.isEmpty(compId)) {
					response.setResult(compId);
					response.setResponseCode("200");
					response.setResponseMessage("Company Details Saved Successfully");
					return response;
				}
			}
		} catch (Exception e) {
			logger.error("Exception:", e);
			response.setResponseCode("200");
			response.setResponseMessage("Eror Occur! Please contact adminstrator");
			return response;
		}
		return response;
	}

	public String getRRN() {
		Calendar cal = Calendar.getInstance();
		String rrn = ("" + cal.get(Calendar.YEAR)).substring(3) + cal.get(Calendar.DAY_OF_YEAR)
				+ cal.get(Calendar.HOUR_OF_DAY) + cal.get(Calendar.MINUTE) + cal.get(Calendar.SECOND)
				+ cal.get(Calendar.MILLISECOND) + ((int) ((Math.random() * (99 - 10))));

		if (rrn.length() <= 12) {
			rrn = rrn + "109783";
		}

		return rrn.substring(rrn.length() - 12);
	}

	@Override
	public String getDataByCif(VerifyCifPara verifyCifPara) {
		String resp = null;

		try {
			resp = restServiceCall.getDataByCifService(verifyCifPara.getCorpCompId(), getRRN());
		} catch (Exception e) {
			logger.info("Exception:", e);
		}

		return resp;
	}

	@Override
	public boolean getCompanyDetailsByCif(String cif) {
		boolean resp = false;
		try {
			String sqlCifExist = "SELECT cif FROM CORP_COMPANY_MASTER WHERE cif =:cif and STATUSID <> 10";

			List cifExist = getSession().createSQLQuery(sqlCifExist)
					.setParameter("cif", EncryptorDecryptor.encryptData(cif)).list();
			if (!ObjectUtils.isEmpty(cifExist)) {
				resp = true;
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return resp;
	}

	public boolean getCompanyDupDetailsByCif(String cif) {
		boolean resp = false;
		try {
			String sqlCifExist = "SELECT cif FROM CORP_COMPANY_MASTER_DUP WHERE cif =:cif and MAKER_VALIDATED=1";

			List cifExist = getSession().createSQLQuery(sqlCifExist)
					.setParameter("cif", EncryptorDecryptor.encryptData(cif)).list();
			if (!ObjectUtils.isEmpty(cifExist)) {
				resp = true;
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return resp;
	}

	@Override
	public ResponseMessageBean checkCorpIdExist(CorpCompanyMasterEntity companyMasterEntity) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		try {
			String sqlcorpCodeExist = "SELECT companyCode FROM CORP_COMPANY_MASTER WHERE companyCode = :companyCode and STATUSID <> 10";
			List corpCodeExist = getSession().createSQLQuery(sqlcorpCodeExist)
					.setParameter("companyCode", EncryptorDecryptor.encryptData(companyMasterEntity.getCompanyCode()))
					.list();
			if (!(ObjectUtils.isEmpty(corpCodeExist))) {
				rmb.setResponseCode("202");
				rmb.setResponseMessage("Corporate ID is already exist");
			} else {
				rmb.setResponseCode("200");
				rmb.setResponseMessage("Corporate ID not exist ");
			}
		} catch (Exception e) {
			logger.error("Exception:", e);
		}
		return rmb;
	}

	@Override
	public ResponseMessageBean checkCorpIdInDup(CorpCompanyMasterEntity companyMasterEntity) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		try {
			String sqlcorpCodeExist = "SELECT companyCode FROM CORP_COMPANY_MASTER_DUP WHERE companyCode=:companyCode";

			List corpCodeExist = getSession().createSQLQuery(sqlcorpCodeExist)
					.setParameter("companyCode", EncryptorDecryptor.encryptData(companyMasterEntity.getCompanyCode()))
					.list();

			if (!(ObjectUtils.isEmpty(corpCodeExist))) {
				rmb.setResponseCode("202");
				rmb.setResponseMessage("Corporate ID is already exist");
			} else {
				rmb.setResponseCode("200");
				rmb.setResponseMessage("Corporate ID not exist ");
			}

		} catch (Exception e) {
			logger.info("Exception:", e);

			e.printStackTrace();
		}
		return rmb;
	}

	public List<CorpUserEntity> getUserDetailsByCorpCompId(CorpUserEntity userReq) {
		List<CorpUserEntity> list = null;
		List<Integer> statusList = new ArrayList<>();
		statusList = userReq.getStatusList();
		try {
			String sqlQuery = "select u.id, u.user_name , u.rrn as rrn ,u.email_id,u.personal_Phone,u.pancardNumber,u.user_disp_name,u.statusid,s.name as statusName from CORP_USERS u inner join statusmaster s on s.id=u.statusid where u.corp_comp_id=:compId and u.statusid in :statusid";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("user_name").addScalar("rrn")
					.addScalar("email_id").addScalar("personal_Phone").addScalar("pancardNumber")
					.addScalar("user_disp_name").addScalar("statusid").addScalar("statusName")
					.setParameter("compId", (userReq.getCorp_comp_id())).setParameterList("statusid", statusList)
					.setResultTransformer(Transformers.aliasToBean(CorpUserEntity.class)).list();

		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return list;
	}

	public List<CorpUserEntity> getUserDetailsByCorpCompId1(CorpUserEntity userReq) {
		List<CorpUserEntity> list = null;
		try {
			String sqlQuery = "SELECT cu.aadharcardno, cu.appid, cu.base64imagenew, cu.boardresolution, cu.certificate_incorporation, cu.city, cu.corproleid, cu.corp_comp_id, cu.country, cu.createdby, cu.createdon, cu.designation, cu.dob, cu.email_id, cu.first_name, cu.ibregstatus, cu.id, cu.isbiometricenabled, cu.ismobileenabled, cu.iswebenabled, cu.lastinvalidattemptpwd, cu.lastlogintime, cu.last_mpin_wrong_attempt, cu.last_name, cu.last_pwd_wrong_attempt, cu.last_tpin_wrong_attempt, cu.maxlimit as transMaxLimit, cu.mobregstatus, cu.mpin, cu.mpin_wrong_attempt, cu.multiple_user, cu.nationalid, cu.nationalidnumber, cu.otherdoc, cu.pancard, cu.pancardnumber, cu.parentid, cu.parentroleid, cu.parentrrn, cu.parentusername, cu.passport, cu.passportnumber, cu.personal_phone, cu.pkistatus, cu.pwdlockedon, cu.pwd_status, cu.remarks as remark, cu.rights, cu.rrn, cu.single_user, cu.state, cu.statusid, cu.tpin, cu.tpin_status, cu.tpin_wrong_attempt, cu.updatedby, cu.updatedon, cu.user_disp_name, cu.user_image, cu.user_name, cu.user_pwd, cu.user_type, cu.work_phone, cu.wrongattemptssofttoken as wrongAttemptSoftToken, cu.wrong_pwd_attempt, r.name AS parentrolename, r.name AS rolename FROM corp_users cu INNER JOIN corp_roles r ON r.id = cu.corproleid WHERE cu.corp_comp_id = :compId AND cu.statusid = 8";
			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("corp_comp_id").addScalar("appid")
					.addScalar("country").addScalar("createdby").addScalar("mobRegStatus").addScalar("transMaxLimit")
					.addScalar("user_type").addScalar("work_phone").addScalar("user_name").addScalar("first_name")
					.addScalar("last_name").addScalar("email_id").addScalar("parentId").addScalar("personal_Phone")
					.addScalar("user_pwd").addScalar("dob").addScalar("pancardNumber").addScalar("rrn")
					.addScalar("corpRoleId").addScalar("aadharCardNo", StandardBasicTypes.STRING)
					.addScalar("passport", StandardBasicTypes.STRING).addScalar("passportNumber")
					.addScalar("boardResolution", StandardBasicTypes.STRING)
					.addScalar("user_image", StandardBasicTypes.STRING).addScalar("otherDoc", StandardBasicTypes.STRING)
					.addScalar("certificate_incorporation", StandardBasicTypes.STRING).addScalar("designation")
					.addScalar("parentRrn").addScalar("statusid").addScalar("createdon").addScalar("updatedOn")
					.addScalar("updatedby").addScalar("parentRoleName").addScalar("parentRoleId")
					.addScalar("parentUserName").addScalar("nationalIdNumber").addScalar("tpin_status")
					.addScalar("rights").addScalar("pkiStatus").addScalar("tpin_wrong_attempt").addScalar("city")
					.addScalar("wrong_pwd_attempt").addScalar("pwd_status").addScalar("state")
					.addScalar("last_pwd_wrong_attempt").addScalar("mpin").addScalar("mpin_wrong_attempt")
					.addScalar("pancard").addScalar("ibRegStatus").addScalar("remark")
					.addScalar("wrongAttemptSoftToken").addScalar("roleName").addScalar("user_disp_name")
					.setParameter("compId", userReq.getCorp_comp_id())
					.setResultTransformer(Transformers.aliasToBean(CorpUserEntity.class)).list();

			/*
			 * String sqlQuery =
			 * "select u.id, u.user_name , u.rrn as rrn ,u.email_id,u.personal_Phone,u.pancardNumber,u.user_disp_name,r.name as roleName from CORP_USERS u inner join corp_roles r on r.id=u.corpRoleId where u.corp_comp_id=:compId  and u.statusId=8"
			 * ;
			 * 
			 * list =
			 * getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("user_name").
			 * addScalar("rrn")
			 * .addScalar("email_id").addScalar("personal_Phone").addScalar("pancardNumber")
			 * .addScalar("user_disp_name").addScalar("roleName").setParameter("compId",
			 * (userReq.getCorp_comp_id()))
			 * .setResultTransformer(Transformers.aliasToBean(CorpUserEntity.class)).list();
			 */

		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return list;
	}

	@Override
	public ResponseMessageBean resendUserKitOnEmail(CorpDataBean corpDataBean) {
		logger.info("Resend Email Kit...........");
		ResponseMessageBean response = new ResponseMessageBean();

		String companyCode = "";
		CorpCompanyDataBean corpCompanyDataBean = new CorpCompanyDataBean();
		corpCompanyDataBean.setBranchCode(corpDataBean.getBranchCode());
		corpCompanyDataBean.setId(corpDataBean.getCorpCompId());
		corpCompanyDataBean.setStatusList(Arrays.asList(8, 3));
		List<CorpCompanyDataBean> companyData = getOfflineCorpCompDataById(corpCompanyDataBean);
		companyCode = EncryptorDecryptor.decryptData(companyData.get(0).getCompanyCode());
		String companyName = EncryptorDecryptor.decryptData(companyData.get(0).getCompanyName());

		Session session = sessionFactory.getCurrentSession();
		CorpUserEntity corp = new CorpUserEntity();
		corp.setCorp_comp_id(corpDataBean.getCorpCompId());
		Map<String, String> userTempPasswordMap = new HashMap<>();
		List<CorpUserEntity> corpUserEntityList = getUserDetailsByCorpCompId1(corp);
		for (CorpUserEntity userEntity : corpUserEntityList) {
			for (CorpUserEntity corpUserEntity : corpDataBean.getCorpUserData()) {
				if (EncryptorDecryptor.decryptData(userEntity.getUser_name()).equals(corpUserEntity.getUser_name())) {
					logger.info("UserName: " + corpUserEntity.getUser_name() + ", Kit Password: "
							+ corpUserEntity.getTemp_pwd() + ", DB Password: " + corpUserEntity.getUser_pwd());
					userTempPasswordMap.put(corpUserEntity.getUser_name(), corpUserEntity.getTemp_pwd());
					userEntity.setUser_pwd(corpUserEntity.getUser_pwd());
					userEntity.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
					userEntity.setUpdatedby(corpDataBean.getCreatedByUpdatedBy());
					userEntity.setOgstatus(new BigDecimal(8));
					session.update(userEntity);
				}
			}
		}
		logger.info("fetch data from DB.... ");
		File file = null;
		List<Map<String, String>> record = null;

		for (CorpUserEntity corpUser : corpUserEntityList) {
			for (CorpUserEntity corpUserEntity : corpDataBean.getCorpUserData()) {
				if (EncryptorDecryptor.decryptData(corpUser.getUser_name()).equals(corpUserEntity.getUser_name())) {
					record = new ArrayList<Map<String, String>>();
					String nonEncUserName = corpUser.getUser_name();
					Map<String, String> map = new HashMap<>();
					String nonEncEmail = corpUser.getEmail_id();
					String nonEncMobile = EncryptorDecryptor.decryptData(corpUser.getPersonal_Phone());
					String nonEncPanCardNo = corpUser.getPancardNumber();
					String userName = EncryptorDecryptor.decryptData(nonEncUserName);
					String otp = RandomNumberGenerator.generateActivationCode();// otp generate
					String encPwd = (EncryptorDecryptor.decryptData(nonEncPanCardNo)).concat(otp);
					logger.info("Password for pdf:" + encPwd);

					map.put("User Name", userName);
					map.put("Password", userTempPasswordMap.get(userName));

					List<String> generalRec = new ArrayList<>();
					String userDisplyName = EncryptorDecryptor.decryptData(corpUser.getUser_disp_name());
					generalRec.add(userDisplyName);
					generalRec.add(EncryptorDecryptor.decryptData(corpUser.getPersonal_Phone()));
					generalRec.add(companyName);
					generalRec.add(userName);
					generalRec.add(companyCode);
					// generalRec.add(userTempPasswordMap.get(userName));
					generalRec.add(corpUserEntity.getTemp_pwd());
					/* generalRec.add(corpUser.getTemp_pwd()); */
					generalRec.add(corpUser.getRoleName());
					record.add(map);
					logger.info("userName: " + userName + ", Temp Password: " + userTempPasswordMap.get(userName));
					logger.info("start PDF Generation:");

					String finalCompanyName = new String();
					if (companyName.toString().contains("/")) {
						finalCompanyName = companyName.toString().replaceAll("/", "");
					} else {
						finalCompanyName = companyName;
					}
					String fileName = finalCompanyName + "_UserCredentials.pdf";
					file = pdfGenerator.generatePDF(fileName, "PSB: User Credentials", record, encPwd, encPwd,
							generalRec, pdfFilePath);
					logger.info("end PDF Generation:");
					List<String> toEmail = new ArrayList<>();
					List<String> ccEmail = new ArrayList<>();
					List<String> bccEmail = new ArrayList<>();
					List<File> files = new ArrayList<>();
					toEmail.add(EncryptorDecryptor.decryptData(nonEncEmail));
					files.add(file);
					Date subjectDate = new Date();

					String body = "<p style='margin-top:0in;margin-right:0in;margin-bottom:8.0pt;margin-left:0in;line-height:107%;font-size:15px;font-family:\\\"Calibri\\\",sans-serif;text-align:justify;'>Dear <strong>"
							+ userDisplyName
							+ ",</strong></p> <p style='margin-top:0in;margin-right:0in;margin-bottom:8.0pt;margin-left:0in;line-height:107%;font-size:15px;font-family:\\\"Calibri\\\",sans-serif;text-align:justify;'><br>We would like to welcome you as a new user of PSB UnIC Biz. We value your support and contribution to our business, and we believe that your experience with our services will bring you the utmost satisfaction.</p> <p style='margin-top:0in;margin-right:0in;margin-bottom:8.0pt;margin-left:0in;line-height:107%;font-size:15px;font-family:\\\"Calibri\\\",sans-serif;text-align:justify;'>At <strong>Punjab &amp; Sind Bank,&nbsp;</strong>we are committed to deliver responsive and excellent services to all our customers. We are pleased to serve you with our best<strong>&nbsp;</strong>services. Our customer&apos;s satisfaction is the most important part of our business, and we work hard to ensure our customers feel valued and heard. Please find in attachment your &ldquo;Registration Kit&rdquo;&nbsp;</p> <p style='margin-top:0in;margin-right:0in;margin-bottom:8.0pt;margin-left:0in;line-height:107%;font-size:15px;font-family:\\\"Calibri\\\",sans-serif;text-align:justify;'><br>&nbsp;Your Registration Kit is password protected. Please follow the below instructions to open your kit</p> <ul style=\\\"margin-bottom:0in;margin-top:0in;\\\" type=\\\"circle\\\">     <li style='margin-top:0in;margin-right:0in;margin-bottom:8.0pt;margin-left:0in;line-height:107%;font-size:15px;font-family:\\\"Calibri\\\",sans-serif;text-align:justify;'>Click on the attachment provided with this mail</li>     <li style='margin-top:0in;margin-right:0in;margin-bottom:8.0pt;margin-left:0in;line-height:107%;font-size:15px;font-family:\\\"Calibri\\\",sans-serif;text-align:justify;'>You will be prompted for your password</li>     <li style='margin-top:0in;margin-right:0in;margin-bottom:8.0pt;margin-left:0in;line-height:107%;font-size:15px;font-family:\\\"Calibri\\\",sans-serif;text-align:justify;'>The password is your PAN (in capital) + 6-digit OTP received over SMS. For eg. PAN number is: AAAAA1111A &amp; OTP received is 123456, then password to open the registration kit document is: &ldquo;AAAAA1111A123456&rdquo;.</li> </ul> <p style='margin-top:0in;margin-right:0in;margin-bottom:8.0pt;margin-left:0in;line-height:107%;font-size:15px;font-family:\\\"Calibri\\\",sans-serif;text-align:justify;'>We congratulate you to become a part of Punjab &amp; Sind Bank&rsquo;s digital banking journey. We are grateful for the opportunity to serve you with the best service.</p> <p style='margin-top:0in;margin-right:0in;margin-bottom:8.0pt;margin-left:0in;line-height:107%;font-size:15px;font-family:\\\"Calibri\\\",sans-serif;text-align:justify;'><br><strong>Warm Regards,</strong></p> <p style='margin-top:0in;margin-right:0in;margin-bottom:8.0pt;margin-left:0in;line-height:107%;font-size:15px;font-family:\\\"Calibri\\\",sans-serif;text-align:justify;'><strong>Punjab &amp; Sind Bank.</strong></p> <p style='margin-top:0in;margin-right:0in;margin-bottom:8.0pt;margin-left:0in;line-height:107%;font-size:15px;font-family:\\\"Calibri\\\",sans-serif;text-align:justify;'><strong>&nbsp;</strong></p> <p style='margin-top:0in;margin-right:0in;margin-bottom:8.0pt;margin-left:0in;line-height:107%;font-size:15px;font-family:\\\"Calibri\\\",sans-serif;text-align:justify;'>Note: Please do not reply to this email. This is sent from an unattended mail box. For any queries/complaints, please write to us at <a href=\\\"mailto:omni_support@psb.co.in\\\">omni_support@psb.co.in</a>.</p> <p style='margin-top:0in;margin-right:0in;margin-bottom:8.0pt;margin-left:0in;line-height:107%;font-size:15px;font-family:\\\"Calibri\\\",sans-serif;text-align:justify;'>&nbsp;<br>&nbsp;Disclaimer:<br>This email is confidential. If you are not the intended recipient, please notify us immediately on <a href=\\\"mailto:omni_support@psb.co.in\\\">omni_support@psb.co.in</a>; you should not copy, forward, disclose or use it for any purpose either partly or completely. Please delete the email and all copies from your system. Internet communications cannot be guaranteed to be timely, secure, error or virus-free. If you encounter any problems in opening your Welcome Kit, please check if your web/email administrator allows emails with attachments.</p>";

					// send mail to user with their credentials file
					// send mail to user with their credentials file
					if (emailUtil.sendEmailWithAttachment(toEmail, ccEmail, bccEmail, files, body,
							"PSB UnIC Biz- Corporate Registration Kit")) {
						file.delete();
					}

					// Registration OTP
					emailUtil.sendSMSNotification(nonEncMobile, otp
							+ " is your OTP to validate the PSB UnIC Biz registration kit. Do not share your OTP with anyone. Bank NEVER asks for OTP over Call, SMS or Email -Punjab%26Sind+Bank");

//						} else {
//							logger.info("SMS max attampt reached");
//						}
				}
			}
		}
		response.setResponseCode("200");
		response.setResponseMessage("Email/Otp Send Successfully.");
		return response;

//		} else {
//			response.setResponseCode("200");
//			response.setResponseMessage("KIT Generation Activity Is Not Yet Available for Multiuser.");
//			return response;
//		}

	}

	@Override
	public ResponseMessageBean blockUnblock(CorpUserBean corpUserBean) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		List<Integer> statusIdList = new ArrayList<>();
		statusIdList.add(3);
		statusIdList.add(0);
		statusIdList.add(36);
		statusIdList.add(35);
		List<CorpCompanyMasterEntity> corpCompanyList = corpCompanyMasterRepo
				.findByCifAndStatusIdList(EncryptorDecryptor.encryptData(corpUserBean.getCif()), statusIdList);

		if (!ObjectUtils.isEmpty(corpCompanyList)) {
			if (corpCompanyList.size() > 1) {
				for (CorpCompanyMasterEntity corpCompany : corpCompanyList) {
					if (corpCompany.getStatusId().equals(BigDecimal.valueOf(0))
							|| corpCompany.getStatusId().equals(BigDecimal.valueOf(35))) {
						rmb.setResponseCode("202");
						rmb.setResponseMessage("Request already in action pending mode for Action");
					} else if (corpCompany.getStatusId().equals(BigDecimal.valueOf(36))) {
						rmb.setResponseCode("202");
						rmb.setResponseMessage("Request is Pending to checker for approval");
					}
				}
				return rmb;
			}
		}

		logger.info("start Blocking/Unblocking company and user........");
		logger.info("Corporeate Id: " + corpUserBean.getCorpCompId());
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();

		List<String> userNameList = new ArrayList<>();
		for (String userName : corpUserBean.getUserNameList().keySet()) {
			userNameList.add(EncryptorDecryptor.encryptData(userName));
		}
		List<CorpUserEntity> corpUserList = new ArrayList<>();

		List<CorpUserEntity> corpUserEntityList = getAllCorpUsersByUserName(userNameList, corpUserBean.getCorpCompId());
		BigDecimal statusId = null;
		try {
			for (CorpUserEntity corpUserEntity : corpUserEntityList) {
				statusId = corpUserBean.getUserNameList()
						.get(EncryptorDecryptor.decryptData(corpUserEntity.getUser_name()));
				corpUserEntity.setStatusid(statusId);
				corpUserList.add(corpUserEntity);
				session.update(corpUserEntity);
				logger.info("User " + EncryptorDecryptor.decryptData(corpUserEntity.getUser_name()) + " is "
						+ corpUserEntity.getStatusName());
			}
			tx.commit();
			Session newSession = sessionFactory.getCurrentSession();

			CorpUserEntity corpUserEntity = new CorpUserEntity();
			corpUserEntity.setCorp_comp_id(corpUserBean.getCorpCompId());
			List<Integer> statusList = new ArrayList<Integer>();
			statusList.add(corpUserBean.getCorp_status().intValue());
			if (statusId.equals(BigDecimal.valueOf(3))) {
				Integer[] status = { 16, 53, 54 };
				statusList = Arrays.asList(status);
			} else if (statusId.equals(BigDecimal.valueOf(16))) {
				Integer[] status = { 53, 54, 3 };
				statusList = Arrays.asList(status);
			} else if (statusId.equals(BigDecimal.valueOf(53))) {
				Integer[] status = { 54, 3, 16 };
				statusList = Arrays.asList(status);
			} else if (statusId.equals(BigDecimal.valueOf(54))) {
				Integer[] status = { 3, 16, 53 };
				statusList = Arrays.asList(status);
			}

			corpUserEntity.setStatusList(statusList);
			List<CorpUserEntity> corpUserEntities = getAllCorpUsersByCompId(corpUserEntity, false);

			if (corpUserEntities.isEmpty() || statusId.equals(BigDecimal.valueOf(3))
					|| statusId.equals(BigDecimal.valueOf(16))) {
				CorpCompanyMasterEntity corpCompMasterData = new CorpCompanyMasterEntity();
				corpCompMasterData.setId(corpUserBean.getCorpCompId());
				corpCompMasterData.setStatusList(statusList);
				List<CorpCompanyMasterEntity> corpCompanyEntityList = getOfflineCorpCompDataByIdNew(corpCompMasterData);
				if (!ObjectUtils.isEmpty(corpCompanyEntityList)) {
					corpCompanyEntityList.get(0).setStatusId(statusId);
					logger.info("Company Name: "
							+ EncryptorDecryptor.decryptData(corpCompanyEntityList.get(0).getCompanyName()));
					if (statusId.equals(BigDecimal.valueOf(3))) {
						Map<String, Object> activeUserStatusMap = validateActiveHierarchy(
								corpUserBean.getCorpCompId().intValue());
						if (!ObjectUtils.isEmpty(activeUserStatusMap)) {
							Integer statusCode = (Integer) activeUserStatusMap.get("statusCode");
							if (statusCode == 0) {
								newSession.update(corpCompanyEntityList.get(0));
								logger.info("Company And his User Active Successfully");
								rmb.setResponseMessage("Company And Their Users Active Successfully.");
							} else if (statusId.equals(BigDecimal.valueOf(3))) {
								rmb.setResponseCode("200");
								rmb.setResponseMessage("User Active Successfully");
								logger.info("User Active Successfully");
							}
						}
					} else if (statusId.equals(BigDecimal.valueOf(16))) {
						if (corpUserEntities.isEmpty()) {
							newSession.update(corpCompanyEntityList.get(0));
							logger.info("Company And Their Users Blocked Successfully");
							rmb.setResponseMessage("Company And Their Users Blocked Successfully.");
						} else {
							rmb.setResponseCode("200");
							rmb.setResponseMessage("User Blocked Successfully");
							logger.info("User Blocked Successfully");
						}

					} else if (statusId.equals(BigDecimal.valueOf(53))) {
						rmb.setResponseCode("200");
						rmb.setResponseMessage("User Blocking in Progress");
						logger.info("User Blocked Successfully");
					} else if (statusId.equals(BigDecimal.valueOf(54))) {
						rmb.setResponseCode("200");
						rmb.setResponseMessage("User Activation in progress");
						logger.info("User Activation in progress");
					}
				} else if (statusId.equals(BigDecimal.valueOf(3))) {
					rmb.setResponseCode("200");
					rmb.setResponseMessage("User Active Successfully");
					logger.info("User Active Successfully");
				} else if (statusId.equals(BigDecimal.valueOf(53))) {
					rmb.setResponseCode("200");
					rmb.setResponseMessage("User Blocking in Progress");
					logger.info("User Blocking in Progress");
				} else if (statusId.equals(BigDecimal.valueOf(54))) {
					rmb.setResponseCode("200");
					rmb.setResponseMessage("User Activation in progress");
					logger.info("User Activation in progress");
				} else if (statusId.equals(BigDecimal.valueOf(16))) {
					rmb.setResponseCode("200");
					rmb.setResponseMessage("User Blocked Successfully");
					logger.info("User Blocked Successfully");
				}
				rmb.setResponseCode("200");
			} else if (!corpUserEntities.isEmpty() || statusId.equals(BigDecimal.valueOf(53))
					|| statusId.equals(BigDecimal.valueOf(54))) {
				rmb.setResponseCode("200");
				if (statusId.equals(BigDecimal.valueOf(3))) {
					rmb.setResponseCode("200");
					rmb.setResponseMessage("User Active Successfully");
					logger.info("User Active Successfully");
				} else if (statusId.equals(BigDecimal.valueOf(16))) {
					rmb.setResponseCode("200");
					rmb.setResponseMessage("User Blocked Successfully");
					logger.info("User Blocked Successfully");
				} else if (statusId.equals(BigDecimal.valueOf(53))) {
					rmb.setResponseCode("200");
					rmb.setResponseMessage("User Blocking in Progress");
					logger.info("User Blocking in Progress");
				} else if (statusId.equals(BigDecimal.valueOf(54))) {
					rmb.setResponseCode("200");
					rmb.setResponseMessage("User Activation in progress");
					logger.info("User Activation in progress");
				}
			} else {
				rmb.setResponseCode("202");
			}

		} catch (

		Exception e) {
			rmb.setResponseCode("202");
			logger.info("Exception:", e);
		}
		return rmb;
	}

	@Override
	public ResponseMessageBean updateOfflineCorpUserMasterDataTemp(CorpDataBean corpData) {
		BigDecimal regulatorId = BigDecimal.valueOf(0);
		BigDecimal userId = BigDecimal.valueOf(0);
		BigDecimal userParentId = BigDecimal.valueOf(0);
		String regulatorRRN = null;
		String userRRN = null;
		Map<String, BigDecimal> parentDataMap = new HashMap<>();
		String regulatorUserName = null;
		List<CorpUserEntity> corpUserList = new ArrayList<>();
		try {
			for (CorpUserEntity corpUser : corpData.getCorpUserMasterData()) {
				String[] CorpUserMenuList = null;
				String[] CorpUserAccountList = null;
				corpUser.setCorp_comp_id(corpData.getCorpCompId());
				corpUser.setCreatedon(new Timestamp(System.currentTimeMillis()));
				corpUser.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
				corpUser.setRights(corpUser.getRights());
				corpUser.setPkiStatus(corpUser.getPkiStatus());
				if (!ObjectUtils.isEmpty(corpData.getCorpUserMasterData().get(0).getStatusid())) {
					corpUser.setStatusid(corpData.getCorpUserMasterData().get(0).getStatusid());
				} else {
					corpUser.setStatusid(BigDecimal.valueOf(36));
				}

				corpUser.setRights(corpUser.getRights());
				corpUser.setPkiStatus(corpUser.getPkiStatus());
				corpUser.setTransMaxLimit(corpUser.getTransMaxLimit());
				corpUser.setUser_pwd("");
				String nonEncUserName = corpUser.getUser_name().toLowerCase();

				adminWorkFlowReqUtility.encryptCorpUserData(corpUser);

				CorpUserMenuList = corpUser.getMenuList().split(",");
				CorpUserAccountList = corpUser.getAccountList().split(",");

				// check is username and email exist and mobile exist
				ResponseMessageBean resp = new ResponseMessageBean();
				resp = isCompanyUserExist(corpUser);
				if (resp.getResponseCode().equalsIgnoreCase("200")) {

					if (corpUser.getCorpRoleId().intValue() == 1) {
						corpUser.setRrn(String.valueOf(System.currentTimeMillis()));
						regulatorRRN = corpUser.getRrn();
						userRRN = corpUser.getRrn();
						corpUser.setParentRrn("0");
						corpUser.setParentRoleId(BigDecimal.valueOf(0));
						corpUser.setParentId(BigDecimal.valueOf(0));
						regulatorUserName = nonEncUserName;
						corpUser = saveCorpUserData(corpUser);
						corpUserList.add(corpUser);
						regulatorId = corpUser.getId();
						saveToCorpUsersMenuMap(CorpUserMenuList, corpUser.getCorp_comp_id(), regulatorId, userRRN,
								corpUser.getUpdatedby());
						saveToCorpUsersAccMap(CorpUserAccountList, corpUser.getCorp_comp_id(), regulatorId, userRRN,
								corpUser.getUpdatedby());

					} else if (corpUser.getCorpRoleId().intValue() == 2) {
						corpUser.setRrn(String.valueOf(System.currentTimeMillis()));
						userRRN = corpUser.getRrn();
						corpUser.setParentRrn(regulatorRRN);
						corpUser.setParentRoleId(BigDecimal.valueOf(1));
						if (regulatorUserName != null) {
							corpUser.setParentUserName(regulatorUserName);
						}
						if (null != regulatorId) {
							corpUser.setParentId(regulatorId);
						} else {
							corpUser.setParentId(BigDecimal.valueOf(0));
						}

						corpUser = saveCorpUserData(corpUser);
						corpUserList.add(corpUser);
						userId = corpUser.getId();
						parentDataMap.put(nonEncUserName, userId);

						saveToCorpUsersMenuMap(CorpUserMenuList, corpUser.getCorp_comp_id(), userId, userRRN,
								corpUser.getUpdatedby());
						saveToCorpUsersAccMap(CorpUserAccountList, corpUser.getCorp_comp_id(), userId, userRRN,
								corpUser.getUpdatedby());

					} else if (corpUser.getCorpRoleId().intValue() == 3 || corpUser.getCorpRoleId().intValue() == 4
							|| corpUser.getCorpRoleId().intValue() == 5 || corpUser.getCorpRoleId().intValue() == 6) {

						corpUser.setRrn(String.valueOf(System.currentTimeMillis()));
						userRRN = corpUser.getRrn();
						corpUser.setParentRrn("0");
						corpUser.setParentRoleId(BigDecimal.valueOf(2));
						if (null != parentDataMap.get(corpUser.getParentUserName())) {
							corpUser.setParentId((BigDecimal) parentDataMap.get(corpUser.getParentUserName()));
						} else {
							corpUser.setParentId((BigDecimal) parentDataMap.get(0));
						}

						corpUser = saveCorpUserData(corpUser);
						corpUserList.add(corpUser);
						userParentId = corpUser.getId();

						saveToCorpUsersMenuMap(CorpUserMenuList, corpUser.getCorp_comp_id(), userParentId, userRRN,
								corpUser.getUpdatedby());
						saveToCorpUsersAccMap(CorpUserAccountList, corpUser.getCorp_comp_id(), userParentId, userRRN,
								corpUser.getUpdatedby());
					}

				}

			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		Session session = sessionFactory.getCurrentSession();
		ResponseMessageBean responseMessageBean = new ResponseMessageBean();
		// update company status record Inactive to Pending Checker
		CorpCompanyMasterEntity corpCompanyMasterData = new CorpCompanyMasterEntity();
		List<Integer> statusList = new ArrayList<>();
		if (!ObjectUtils.isEmpty(corpData.getCorpUserMasterData().get(0).getStatusid())) {
			statusList.add(corpData.getCorpUserMasterData().get(0).getStatusid().intValue());
		} else {
			statusList.add(0);
		}

		logger.info("Branch Code: " + corpData.getBranchCode());
		corpCompanyMasterData.setBranchCode(corpData.getBranchCode());
		corpCompanyMasterData.setStatusList(statusList);
		corpCompanyMasterData.setId(corpData.getCorpUserMasterData().get(0).getCorp_comp_id());
		List<CorpCompanyMasterEntity> companyData = getOfflineCorpCompDataByIdNew(corpCompanyMasterData);
		companyData.get(0).setStatusId(BigDecimal.valueOf(36));
		session.update(companyData.get(0));

		// update corpUser Status inactive/Pending Maker to Pending Checker
		for (CorpUserEntity corpUserEntityTmp1 : corpUserList) {
			corpUserEntityTmp1.setStatusid(BigDecimal.valueOf(36));
			session.update(companyData.get(0));
		}
		responseMessageBean.setResponseCode("200");
		responseMessageBean.setResponseMessage("Request Submitted Successfully For The Approver");
		return responseMessageBean;
//			}
//		}
//		return responseMessageBean;
	}

	@Override
	public ResponseMessageBean submitOfflineCorpUserMasterData(CorpDataBean corpData) {
		logger.info("submitOfflineCorpUserMasterData...........");
		ResponseMessageBean responseMessageBean = new ResponseMessageBean();
		Session session = sessionFactory.getCurrentSession();
		CorpCompanyMasterEntity corpCompanyMasterData = new CorpCompanyMasterEntity();
		corpCompanyMasterData.setBranchCode(corpData.getBranchCode());
		List<Integer> statusList = new ArrayList<>();
		statusList.add(0);
		statusList.add(8);
		statusList.add(35);
		statusList.add(141);
		corpCompanyMasterData.setStatusList(statusList);
		corpCompanyMasterData.setId(corpData.getCorpCompId());
		List<CorpCompanyMasterEntity> companyData = getOfflineCorpCompDataByIdNew(corpCompanyMasterData);
		companyData.get(0).setStatusId(BigDecimal.valueOf(36));
		companyData.get(0).setUpdatedOn(new Timestamp(System.currentTimeMillis()));
		companyData.get(0).setUpdatedBy(corpData.getCreatedByUpdatedBy());
		session.update(companyData.get(0));
		logger.info("Submit company data...." + companyData.get(0).toString());

		List<String> userNameList = new ArrayList<>();
		for (CorpUserEntity corpUser : corpData.getCorpUserMasterData()) { // Activation pending submit
			userNameList.add(corpUser.getUser_name());
			if ((corpUser.getStatusid().equals(BigDecimal.valueOf(141)) // 8 pending
					|| corpUser.getStatusid().equals(BigDecimal.valueOf(8))) && corpUser.getIsEdited()) {
				logger.info("Inside activation pending submit.....");
				String mobileNo = corpUser.getPersonal_Phone();
				corpUser.setUser_name(EncryptorDecryptor.encryptData(corpUser.getUser_name()));
				corpUser.setEmail_id(EncryptorDecryptor.encryptData(corpUser.getEmail_id()));
				corpUser.setPersonal_Phone(EncryptorDecryptor.encryptData(mobileNo));
				corpUser.setWork_phone(EncryptorDecryptor.encryptData(mobileNo));
				corpUser.setPancardNumber(EncryptorDecryptor.encryptData(corpUser.getPancardNumber()));
				corpUser.setDob(EncryptorDecryptor.encryptData(corpUser.getDob()));
				corpUser.setAadharCardNo(EncryptorDecryptor.encryptData(corpUser.getAadharCardNo()));
				corpUser.setWrongAttemptSoftToken(BigDecimal.valueOf(0));
				corpUser.setStatusid(BigDecimal.valueOf(36));
				session.update(corpUser);
				logger.info("Activation pending date submit.....Status: " + corpUser.getStatusid() + ", og_status: "
						+ corpUser.getOgstatus());
			}
		}

		List<CorpUserEntity> corpUserMasterEntityTmp = getAllCorpUsersByCompanyId(corpData.getCorpCompId());
		// update corpUser Status inactive/Pending Maker to Pending Checker
		for (CorpUserEntity corpUserEntityTmp : corpUserMasterEntityTmp) {
			logger.info("update corpUser Status inactive/Pending Maker to Pending Checker.....");
			logger.info("fetch data corpUserEntity: " + corpUserEntityTmp.toString());
			if (corpUserEntityTmp.getStatusid().intValue() != BigDecimal.valueOf(10).intValue()
					&& corpUserEntityTmp.getStatusid().intValue() != BigDecimal.valueOf(141).intValue()
					&& corpUserEntityTmp.getStatusid().intValue() != BigDecimal.valueOf(8).intValue()) {
				corpUserEntityTmp.setUpdatedby(corpData.getCreatedByUpdatedBy());
				corpUserEntityTmp.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
				if (corpUserEntityTmp.getStatusid().intValue() != BigDecimal.valueOf(3).intValue()) {
					corpUserEntityTmp.setStatusid(BigDecimal.valueOf(36));

				}
				logger.info("Update user Status after submit: " + corpUserEntityTmp.getStatusid() + ", og_status: "
						+ corpUserEntityTmp.getOgstatus());
				session.update(corpUserEntityTmp);

			}
		}
		responseMessageBean.setResponseCode("200");
		responseMessageBean.setResponseMessage("Request Submitted Successfully For The Approver");
		return responseMessageBean;

	}

	// Vishal

	@Override
	public ResponseMessageBean saveOfflineCorpSingleUserMasterDatatosave(CorpDataBean corpData) {
		ResponseMessageBean responseMessageBean = new ResponseMessageBean();
		Session session = sessionFactory.getCurrentSession();

		List<Integer> statusList = new ArrayList<>();

		// update company status record Inactive to Pending Checker
		CorpCompanyMasterEntity corpCompanyMasterData = new CorpCompanyMasterEntity();
		/*
		 * if
		 * (!ObjectUtils.isEmpty(corpData.getCorpUserMasterData().get(0).getStatusid()))
		 * {
		 * statusList.add(corpData.getCorpUserMasterData().get(0).getStatusid().intValue
		 * ()); } else {
		 */
		statusList.add(0);
		// }
		corpCompanyMasterData.setBranchCode(corpData.getBranchCode());
		corpCompanyMasterData.setStatusList(statusList);
		corpCompanyMasterData.setId(corpData.getCorpUserMasterData().get(0).getCorp_comp_id());
		List<CorpCompanyMasterEntity> companyData = getOfflineCorpCompDataByIdNew(corpCompanyMasterData);
		companyData.get(0).setStatusId(BigDecimal.valueOf(0));
		companyData.get(0).setIsCorporate('G');
		session.update(companyData.get(0));

		try {
			for (CorpUserEntity corpUser : corpData.getCorpUserMasterData()) {
				String[] CorpUserMenuList = null;
				String[] CorpUserAccountList = null;
				corpUser.setCorp_comp_id(corpData.getCorpCompId());
				corpUser.setCreatedon(new Timestamp(System.currentTimeMillis()));
				corpUser.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
				corpUser.setRights(corpUser.getRights());
				corpUser.setPkiStatus(corpUser.getPkiStatus());
				corpUser.setStatusid(BigDecimal.valueOf(0));
				corpUser.setRights(corpUser.getRights());
				corpUser.setPkiStatus(corpUser.getPkiStatus());
				corpUser.setTransMaxLimit(corpUser.getTransMaxLimit());
				corpUser.setUser_pwd("");

				adminWorkFlowReqUtility.encryptCorpUserData(corpUser);

				CorpUserMenuList = corpUser.getMenuList().split(",");
				CorpUserAccountList = corpUser.getAccountList().split(",");

				// check is username and email exist and mobile exist
				ResponseMessageBean resp = new ResponseMessageBean();
				resp = isCompanyUserExist(corpUser);
				if (resp.getResponseCode().equalsIgnoreCase("200")) {
					corpUser.setRrn(String.valueOf(System.currentTimeMillis()));

					corpUser = saveCorpUserData(corpUser);

					saveToCorpUsersMenuMaptosave(CorpUserMenuList, corpUser.getCorp_comp_id(), corpUser.getId(),
							corpUser.getRrn(), corpUser.getUpdatedby());
					saveToCorpUsersAccMaptosave(CorpUserAccountList, corpUser.getCorp_comp_id(), corpUser.getId(),
							corpUser.getRrn(), corpUser.getUpdatedby());

				}

			}
		} catch (Exception e) {
			logger.error("Exception:", e);
			responseMessageBean.setResponseCode("202");
			return responseMessageBean;
		}
		responseMessageBean.setResponseCode("200");
		return responseMessageBean;
	}

	@Override
	public void deleteAndMoveToTmpByCorpcompid(CorpDataBean corpData) {
		// move temp table data into master table after successfully approved
		moveDataFromTmpToDleTable(corpData.getCorpCompId());
	}

	public boolean saveToCorpUsersMenuMaptosave(String[] CorpUserMenuList, BigDecimal corpId, BigDecimal userId,
			String userRRN, BigDecimal createdBy) {
		Session session = sessionFactory.getCurrentSession();
		Date date = new Date();
		try {
			for (String corpMenu : CorpUserMenuList) {
				CorpUserMenuMapEntity corpMenuObj = new CorpUserMenuMapEntity();
				corpMenuObj.setCorpMenuId(new BigDecimal(corpMenu));
				corpMenuObj.setCorpUserId(userId);
				corpMenuObj.setCorpCompId(corpId);
				corpMenuObj.setStatusId(BigDecimal.valueOf(0));
				corpMenuObj.setUserRrn(userRRN);
				corpMenuObj.setCreatedon(date);
				corpMenuObj.setUpdatedon(date);
				session.save(corpMenuObj);
			}

		} catch (Exception e) {
			logger.info("Exception:", e);
			return false;
		}
		return true;
	}

	public boolean saveToCorpUsersAccMaptosave(String[] CorpUserAccList, BigDecimal corpId, BigDecimal userId,
			String userRRN, BigDecimal createdBy) {
		Session session = sessionFactory.getCurrentSession();
		Date date = new Date();
		try {
			for (String corpAcc : CorpUserAccList) {

				CorpUserAccMapEntity corpAccObj = new CorpUserAccMapEntity();
				corpAccObj.setAccountNo(EncryptorDecryptor.encryptData(corpAcc));
				corpAccObj.setCorpUserId(userId);
				corpAccObj.setCorpCompId(corpId);
				corpAccObj.setStatusId(BigDecimal.valueOf(0));
				corpAccObj.setUserRrn(userRRN);
				corpAccObj.setCreatedon(date);
				corpAccObj.setUpdatedon(date);
				corpAccObj.setUpdatedby(BigDecimal.valueOf(5));
				session.save(corpAccObj);
			}

		} catch (Exception e) {
			logger.info("Exception:", e);
			return false;
		}
		return true;
	}

	// by vishal
	private void moveDataFromTmpToDleTable(BigDecimal corpCompId) {
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			logger.info("corpUsers Moving and deleting....");
			session.createSQLQuery("UPDATE CORP_USERS SET STATUSID = 10 WHERE CORP_COMP_ID = :corpCompId")
					.setBigDecimal("corpCompId", corpCompId).executeUpdate();

			logger.info("corpcompanymaster Moving and deleting....");
			session.createSQLQuery("UPDATE CORP_COMPANY_MASTER SET STATUSID = 10 WHERE ID = :corpCompId")
					.setBigDecimal("corpCompId", corpCompId).executeUpdate();

			logger.info("CorpMenuMap Moving and deleting....");
			session.createSQLQuery("UPDATE CORP_MENU_MAP SET STATUSID = 10 WHERE corpId = :corpId")
					.setBigDecimal("corpId", corpCompId).executeUpdate();

			logger.info("CorpAccMap Moving and deleting....");
			session.createSQLQuery("UPDATE CORP_ACC_MAP SET STATUSID = 10 WHERE corpId = :corpId")
					.setBigDecimal("corpId", corpCompId).executeUpdate();

			logger.info("CorpUserMenuMap Moving and deleting....");
			session.createSQLQuery("UPDATE CORPUSER_MENU_MAP SET STATUSID = 10 WHERE corpCompId = :corpCompId")
					.setBigDecimal("corpCompId", corpCompId).executeUpdate();

			logger.info("CorpUserAccMap Moving and deleting....");
			session.createSQLQuery("UPDATE CORPUSER_ACC_MAP SET STATUSID = 10 WHERE corpCompId = :corpCompId")
					.setBigDecimal("corpCompId", corpCompId).executeUpdate();

			tx.commit();
		} catch (Exception e) {
			logger.error("Exception:", e);
		}

	}

	// Added By vishal-Changes by jeetu
	/* CORP_COMPANY_MASTER_TMP */
	public void movedatacorpcompanymastertmptodel(BigDecimal id) {

		try {
			String corpId = "SELECT id FROM CORP_COMPANY_MASTER_TMP WHERE id =:id";
			List corpCodeExist = getSession().createSQLQuery(corpId).setParameter("id", id).list();

			if (!(ObjectUtils.isEmpty(corpCodeExist))) {
				String sqlCorpName = "INSERT INTO CORP_COMPANY_MASTER_DEL (ID,COMPANYCODE,COMPANYNAME,SHORTNAME,COMPANYINFO,ESTABLISHMENTON,LOGO,CREATEDBY,STATUSID,APPID,CIF,MAKER_LIMIT,CHECKER_LIMIT,APPROVALLEVEL,CREATEDON,RRN,COI,MOA,OTHERDOC,CORPORATETYPE,PHONENO,PANCARDNO,UPDATEDON,UPDATEDBY,LEVELMASTER,MAX_LIMIT,ADDRESS,ADMIN_TYPES,BRANCHCODE,IS_CORPORATE,ADMIN_LEVEL) SELECT ID,COMPANYCODE,COMPANYNAME,SHORTNAME,COMPANYINFO,ESTABLISHMENTON,LOGO,CREATEDBY,STATUSID,APPID,CIF,MAKER_LIMIT,CHECKER_LIMIT,APPROVALLEVEL,CREATEDON,RRN,COI,MOA,OTHERDOC,CORPORATETYPE,PHONENO,PANCARDNO,UPDATEDON,UPDATEDBY,LEVELMASTER,MAX_LIMIT,ADDRESS,ADMIN_TYPES,BRANCHCODE,IS_CORPORATE,ADMIN_LEVEL FROM CORP_COMPANY_MASTER_TMP WHERE id =:id";
				getSession().createSQLQuery(sqlCorpName).setParameter("id", id).executeUpdate();
				logger.info("Inserted in CORP_COMPANY_MASTER_DEL :" + sqlCorpName);
			}

		} catch (Exception e) {
			logger.error("Exception:", e);
		}
	}

	/* CORP_MENU_MAP_TMP */
	public void movedatacorpmenumaptmptodel(BigDecimal id) {

		try {
			String corpId = "SELECT id FROM CORP_MENU_MAP_TMP WHERE corpId =:id";
			List corpCodeExist = getSession().createSQLQuery(corpId).setParameter("id", id).list();

			if (!(ObjectUtils.isEmpty(corpCodeExist))) {
				String sqlCorpName = "INSERT INTO CORP_MENU_MAP_DEL (ID,CORPID,CORPMENUID,STATUSID,UPDATEDBY,UPDATEDON,CREATEDON) SELECT ID,CORPID,CORPMENUID,STATUSID,UPDATEDBY,UPDATEDON,CREATEDON FROM CORP_MENU_MAP_TMP WHERE corpId =:id";
				getSession().createSQLQuery(sqlCorpName).setParameter("id", id).executeUpdate();
				logger.info("Inserted in CORP_MENU_MAP_DEL :" + sqlCorpName);
			}

		} catch (Exception e) {
			logger.error("Exception:", e);
		}

	}

	/* CORP_ACC_MAP_TMP */
	public void movedatacorpaccmaptmptodel(BigDecimal id) {

		try {
			String corpId = "SELECT id FROM CORP_ACC_MAP_TMP WHERE corpId =:id";
			List corpCodeExist = getSession().createSQLQuery(corpId).setParameter("id", id).list();

			if (!(ObjectUtils.isEmpty(corpCodeExist))) {
				String sqlCorpName = "INSERT INTO CORP_ACC_MAP_DEL (ID,CORPID,ACCOUNTNO,STATUSID,CREATEDON,UPDATEDBY,UPDATEDON) SELECT ID,CORPID,ACCOUNTNO,STATUSID,CREATEDON,UPDATEDBY,UPDATEDON FROM CORP_ACC_MAP_TMP WHERE corpId =:id";
				getSession().createSQLQuery(sqlCorpName).setParameter("id", id).executeUpdate();
				logger.info("Inserted in CORP_ACC_MAP_DEL :" + sqlCorpName);
			}
		} catch (Exception e) {
			logger.error("Exception:", e);
		}
	}

	/* CORPUSER_ACC_MAP_TMP */
	public void movedatacorpuseraccmaptmptodel(BigDecimal id) {

		try {
			String corpId = "SELECT id FROM CORPUSER_ACC_MAP_TMP WHERE corpCompId =:id";
			List corpCodeExist = getSession().createSQLQuery(corpId).setParameter("id", id).list();

			if (!(ObjectUtils.isEmpty(corpCodeExist))) {
				String sqlCorpName = "INSERT INTO CORPUSER_ACC_MAP_DEL (ID,CORPCOMPID,ACCOUNTNO,CORPUSERID,STATUSID,CREATEDON,UPDATEDBY,UPDATEDON,USERRRN,ACCOUNTNAME,BRANCHNAME,IFSCCODE) SELECT ID,CORPCOMPID,ACCOUNTNO,CORPUSERID,STATUSID,CREATEDON,UPDATEDBY,UPDATEDON,USERRRN,ACCOUNTNAME,BRANCHNAME,IFSCCODE FROM CORPUSER_ACC_MAP_TMP WHERE corpCompId =:id";
				getSession().createSQLQuery(sqlCorpName).setParameter("id", id).executeUpdate();
				logger.info("Inserted in CORPUSER_ACC_MAP_DEL :" + sqlCorpName);
			}
		} catch (Exception e) {
			logger.error("Exception:", e);
		}

	}

	/* CORPUSER_MENU_MAP_TMP */
	public void movedatacorpusermenumaptmptodel(BigDecimal id) {
		try {
			String corpId = "SELECT id FROM CORPUSER_MENU_MAP_TMP WHERE corpCompId =:id";
			List corpCodeExist = getSession().createSQLQuery(corpId).setParameter("id", id).list();

			if (!(ObjectUtils.isEmpty(corpCodeExist))) {
				String sqlCorpName = "INSERT INTO CORPUSER_MENU_MAP_DEL (ID,CORPCOMPID,CORPMENUID,CORPUSERID,STATUSID,CREATEDON,UPDATEDBY,UPDATEDON,USERRRN) SELECT ID,CORPCOMPID,CORPMENUID,CORPUSERID,STATUSID,CREATEDON,UPDATEDBY,UPDATEDON,USERRRN FROM CORPUSER_MENU_MAP_TMP WHERE corpCompId =:id";
				getSession().createSQLQuery(sqlCorpName).setParameter("id", id).executeUpdate();
				logger.info("Inserted in CORPUSER_MENU_MAP_DEL :" + sqlCorpName);
			}
		} catch (Exception e) {
			logger.error("Exception:", e);
		}
	}

	/* CORP_USERS_TMP */
	public void movedatacorpuserstmptodel(BigDecimal id) {
		try {
			String corpId = "SELECT id FROM CORP_USERS_TMP WHERE corp_Comp_Id =:id";
			List corpCodeExist = getSession().createSQLQuery(corpId).setParameter("id", id).list();
			if (!(ObjectUtils.isEmpty(corpCodeExist))) {
				String sqlCorpName = "INSERT INTO CORP_USERS_DEL (ID,CORP_COMP_ID,USER_DISP_NAME,USER_NAME,CREATEDBY,CREATEDON,STATUSID,APPID,LASTLOGINTIME,USER_TYPE,FIRST_NAME,LAST_NAME,EMAIL_ID,COUNTRY,WORK_PHONE,PERSONAL_PHONE,NATIONALID,PASSPORT,BOARDRESOLUTION,USER_IMAGE,TPIN,PASSPORTNUMBER,NATIONALIDNUMBER,TPIN_STATUS,TPIN_WRONG_ATTEMPT,CITY,WRONG_PWD_ATTEMPT,PWD_STATUS,OTHERDOC,CERTIFICATE_INCORPORATION,STATE,DESIGNATION,LAST_TPIN_WRONG_ATTEMPT,LAST_PWD_WRONG_ATTEMPT,MPIN,MPIN_WRONG_ATTEMPT,LAST_MPIN_WRONG_ATTEMPT,PANCARD,PANCARDNUMBER,UPDATEDBY,UPDATEDON,PWDLOCKEDON,LASTINVALIDATTEMPTPWD,SINGLE_USER,MULTIPLE_USER,DOB,RIGHTS,MAXLIMIT,PKISTATUS,BASE64IMAGENEW,ISMOBILEENABLED,ISWEBENABLED,ISBIOMETRICENABLED,MOBREGSTATUS,IBREGSTATUS,WRONGATTEMPTSSOFTTOKEN,PARENTID,PARENTROLEID,PARENTUSERNAME,AADHARCARDNO,CORPROLEID,PARENTRRN,RRN,REMARKS,USER_PWD) SELECT ID,CORP_COMP_ID,USER_DISP_NAME,USER_NAME,CREATEDBY,CREATEDON,STATUSID,APPID,LASTLOGINTIME,USER_TYPE,FIRST_NAME,LAST_NAME,EMAIL_ID,COUNTRY,WORK_PHONE,PERSONAL_PHONE,NATIONALID,PASSPORT,BOARDRESOLUTION,USER_IMAGE,TPIN,PASSPORTNUMBER,NATIONALIDNUMBER,TPIN_STATUS,TPIN_WRONG_ATTEMPT,CITY,WRONG_PWD_ATTEMPT,PWD_STATUS,OTHERDOC,CERTIFICATE_INCORPORATION,STATE,DESIGNATION,LAST_TPIN_WRONG_ATTEMPT,LAST_PWD_WRONG_ATTEMPT,MPIN,MPIN_WRONG_ATTEMPT,LAST_MPIN_WRONG_ATTEMPT,PANCARD,PANCARDNUMBER,UPDATEDBY,UPDATEDON,PWDLOCKEDON,LASTINVALIDATTEMPTPWD,SINGLE_USER,MULTIPLE_USER,DOB,RIGHTS,MAXLIMIT,PKISTATUS,BASE64IMAGENEW,ISMOBILEENABLED,ISWEBENABLED,ISBIOMETRICENABLED,MOBREGSTATUS,IBREGSTATUS,WRONGATTEMPTSSOFTTOKEN,PARENTID,PARENTROLEID,PARENTUSERNAME,AADHARCARDNO,CORPROLEID,PARENTRRN,RRN,REMARKS,USER_PWD FROM CORP_USERS_TMP WHERE corp_Comp_Id =:id";
				getSession().createSQLQuery(sqlCorpName).setParameter("id", id).executeUpdate();
				logger.info("Inserted in CORP_USERS_DEL :" + sqlCorpName);
			}
		} catch (Exception e) {
			logger.error("Exception:", e);
		}

	}

	public boolean deleteCorporateUserData(BigDecimal corpCompId, BigDecimal userId) {

		boolean isDelete = true;
		try {
			Session session = sessionFactory.getCurrentSession();
//			Transaction tx = session.beginTransaction();
			// Move and delete record for corporate user from temp table
			logger.info("corpUserAccount Moving and deleting....");
			session.createSQLQuery(
					"UPDATE CORPUSER_ACC_MAP SET STATUSID = 10 WHERE CORPCOMPID = :corpCompId and CORPUSERID = :corpUserId")
					.setBigDecimal("corpCompId", corpCompId).setBigDecimal("corpUserId", userId).executeUpdate();

			logger.info("corpUserMenu Moving and deleting....");
			session.createSQLQuery(
					"UPDATE CORPUSER_MENU_MAP SET STATUSID = 10 WHERE CORPCOMPID = :corpCompId and CORPUSERID = :corpUserId")
					.setBigDecimal("corpCompId", corpCompId).setBigDecimal("corpUserId", userId).executeUpdate();

			logger.info("corpUser Moving and deleting....");
			session.createSQLQuery(
					"UPDATE CORP_USERS SET STATUSID = 10 WHERE CORP_COMP_ID = :corpCompId and ID = :corpUserId")
//					"UPDATE CORP_USERS SET STATUSID = 10, og_status = 10 WHERE CORP_COMP_ID = :corpCompId and ID = :corpUserId")
					.setBigDecimal("corpCompId", corpCompId).setBigDecimal("corpUserId", userId).executeUpdate();
//			tx.commit();

		} catch (Exception e) {
			logger.error("Error while deleting records ", e);
			return false;
		}
		return isDelete;

	}

	// TODO uncomment code for enable delete functionality
	@Override
	public boolean moveAndDeleteDataFromTmpToMasterTableForUser(BigDecimal corpCompId, BigDecimal userId,
			BigDecimal ogStatus) {

//	public boolean moveAndDeleteDataFromTmpToMasterTableForUser(BigDecimal corpCompId, BigDecimal userId) {
		boolean isDelete = true;
		try {
			Session session = sessionFactory.getCurrentSession();
			Transaction tx = session.beginTransaction();
			// Move and delete record for corporate user from temp table
			logger.info("corpUserAccount Moving and deleting....");
			// TODO uncomment code for enable delete functionality
			if (BigDecimal.valueOf(0).equals(ogStatus) || ObjectUtils.isEmpty(ogStatus)) {
				session.createSQLQuery(
						"UPDATE CORPUSER_ACC_MAP SET STATUSID = 10 WHERE CORPCOMPID = :corpCompId and CORPUSERID = :corpUserId")
						.setBigDecimal("corpCompId", corpCompId).setBigDecimal("corpUserId", userId).executeUpdate();

				logger.info("corpUserMenu Moving and deleting....");
				session.createSQLQuery(
						"UPDATE CORPUSER_MENU_MAP SET STATUSID = 10 WHERE CORPCOMPID = :corpCompId and CORPUSERID = :corpUserId")
						.setBigDecimal("corpCompId", corpCompId).setBigDecimal("corpUserId", userId).executeUpdate();

				logger.info("corpUserMenu Moving and deleting....");
				session.createSQLQuery(
						"UPDATE CORP_USERS SET STATUSID = 10 WHERE CORP_COMP_ID = :corpCompId and ID = :corpUserId")
						.setBigDecimal("corpCompId", corpCompId).setBigDecimal("corpUserId", userId).executeUpdate();
				// TODO uncomment code for enable delete functionality
			} else {
				logger.info("corpUserMenu Moving and deleting....");
				session.createSQLQuery(
						"UPDATE CORP_USERS SET og_status = 102 WHERE CORP_COMP_ID = :corpCompId and ID = :corpUserId")
						.setBigDecimal("corpCompId", corpCompId).setBigDecimal("corpUserId", userId).executeUpdate();
			}
			tx.commit();

		} catch (

		Exception e) {
			logger.error("Error while deleting records ");
			return false;
		}
		return isDelete;
	}

	public boolean isUserLimitMoreThanCorporateLimit(int corpCompId) {
		try {
			String sqlCorpName = "select cut.id from corp_company_master ccmt, corp_users cut where ccmt.id = cut.corp_comp_id and ccmt.id = :corpCompId and cut.maxlimit > ccmt.max_limit and cut.statusid <> 10 and cut.OG_STATUS <> 102";
			return !getSession().createSQLQuery(sqlCorpName).setParameter("corpCompId", corpCompId).list().isEmpty();
		} catch (Exception e) {
			logger.error("Exception:", e);
		}
		return true;
	}

	@Override
	public List<String> getOfflineCorpCompDataByBranchCodeAndStatus(CorpCompanyMasterEntity corpCompMasterData) {
		List<CorpCompanyMasterEntity> list = null;
		try {
			String sqlQuery = "select distinct company.CIF from corp_users cu inner join corp_company_master company on company.id=cu.corp_comp_Id where company.branchCode=:branchCode and cu.STATUSID in :statusId and company.IS_CORPORATE='G' order by company.cif desc";
			list = getSession().createSQLQuery(sqlQuery).addScalar("cif")
					.setParameter("branchCode", corpCompMasterData.getBranchCode())
					.setParameterList("statusId", corpCompMasterData.getStatusList())
					.setResultTransformer(Transformers.aliasToBean(CorpCompanyMasterEntity.class)).list();
		} catch (Exception e) {
			logger.error("Exception:", e);
		}

		return getOfflineCorpCompDataBycif(list);
	}

	public List<String> getOfflineCorpCompDataBycif(List<CorpCompanyMasterEntity> corpCompMasterData) {
		List<CorpCompanyMasterDupEntity> list = null;
		Set<String> encCifList = new HashSet<>();
		for (CorpCompanyMasterEntity compObg : corpCompMasterData) {
			if (compObg.getCif() != null && compObg.getCif().contains("=")) {
				encCifList.add(compObg.getCif());
			}
		}

		try {
			String sqlQuery = "select cc.CIF as cif from corp_company_master_dup cc where cc.cif in :cif and cc.maker_validated=1";
			list = getSession().createSQLQuery(sqlQuery).addScalar("cif").setParameterList("cif", encCifList)
					.setResultTransformer(Transformers.aliasToBean(CorpCompanyMasterDupEntity.class)).list();
		} catch (Exception e) {
			logger.error("Exception:", e);
		}

		List<String> decCifList = new ArrayList<>();
		List<String> cif = new ArrayList<>();

		for (CorpCompanyMasterEntity compObg : corpCompMasterData) {
			if (compObg.getCif() != null && compObg.getCif().contains("=")) {
				decCifList.add(EncryptorDecryptor.decryptData(compObg.getCif()));
			}
		}

		for (CorpCompanyMasterDupEntity compObg : list) {
			if (compObg.getCif() != null && compObg.getCif().contains("=")) {
				cif.add(EncryptorDecryptor.decryptData(compObg.getCif()));
			}
		}
		if (!ObjectUtils.isEmpty(cif)) {
			decCifList.removeAll(cif);
		}
		return decCifList;
	}

	@Override
	public List<CorpCompanyMasterDupEntity> getOfflineCorpCompDataDupByBranchCodeAndStatus(
			CorpCompanyMasterDupEntity corpCompMasterDataDup) {
		logger.info("getOfflineCorpCompDataByIdNew service request.........id" + corpCompMasterDataDup.getId()
				+ "|satutsId:" + corpCompMasterDataDup.getStatusList());
		List<CorpCompanyMasterDupEntity> list = null;
		try {
			String sqlQuery = "select distinct cc.cif from corp_company_master_dup cc inner join corp_users_dup u on u.corp_comp_id=cc.id where cc.branchCode=:branchCode and cc.STATUSID in :statusId and cc.IS_CORPORATE='G' and u.MAKER_VALIDATED=1 and u.CHECKER_VALIDATED=0";
//			String sqlQuery = "select cc.CIF as cif from CORP_COMPANY_MASTER_DUP cc	where cc.branchCode=:branchCode and cc.STATUSID in :statusId and cc.IS_CORPORATE='G' and cc.MAKER_VALIDATED=1";
			list = getSession().createSQLQuery(sqlQuery).addScalar("cif")
					.setParameter("branchCode", corpCompMasterDataDup.getBranchCode())
					.setParameterList("statusId", corpCompMasterDataDup.getStatusList())
					.setResultTransformer(Transformers.aliasToBean(CorpCompanyMasterDupEntity.class)).list();
		} catch (Exception e) {
			logger.error("Exception:", e);
		}
		return list;
	}

	@Override
	public List<CorpCompanyMasterDupEntity> getOfflineCorpCompDupByCif(
			CorpCompanyMasterDupEntity corpCompanyMasterDupEntity) {
		List<CorpCompanyMasterDupEntity> list = null;
		try {
			String sqlQuery = "select corp.id as id , corp.COMPANYNAME as companyName,corp.COMPANYCODE as companyCode, corp.SHORTNAME as shortName, corp.COMPANYINFO as companyInfo, "
					+ "corp.ESTABLISHMENTON as establishmentOn,corp.LOGO as logo,corp.STATUSID as statusId,corp.CREATEDBY as createdBy, "
					+ "corp.CREATEDON as createdOn,corp.CIF as cif,corp.MAKER_LIMIT as makerLimit,corp.CHECKER_LIMIT as checkerLimit,corp.PHONENO as phoneNo, "
					+ "corp.RRN as rrn, corp.COI as coi,corp.MOA as moa,corp.OTHERDOC as otherDoc,corp.CORPORATETYPE as corporateType, "
					+ "corp.PANCARDNO as pancardNo,corp.UPDATEDON as updatedOn,corp.UPDATEDBY as updatedBy,corp.ApprovalLevel as approvalLevel, "
					+ "corp.LevelMaster as levelMaster,corp.APPID as appId,corp.MAX_LIMIT as maxLimit, corp.ADDRESS as address,corp.ADMIN_TYPES as adminTypes, "
					+ "corp.IS_CORPORATE as isCorporate,corp.BRANCHCODE as branchCode, corp.MAKER_ID as makerId, corp.CHECKER_ID as checkerId, corp.MAKER_VALIDATED as makerValidated, corp.CHECKER_VALIDATED as checkerValidated "
					+ "from CORP_COMPANY_MASTER_DUP corp where corp.cif=:cif and corp.branchCode=:branchCode and corp.IS_CORPORATE='G'";
			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("companyName")
					.addScalar("companyCode").addScalar("shortName").addScalar("companyInfo", StandardBasicTypes.STRING)
					.addScalar("establishmentOn").addScalar("logo", StandardBasicTypes.STRING).addScalar("statusId")
					.addScalar("createdBy").addScalar("createdOn").addScalar("cif").addScalar("makerLimit")
					.addScalar("checkerLimit").addScalar("phoneNo").addScalar("rrn")
					.addScalar("coi", StandardBasicTypes.STRING).addScalar("moa", StandardBasicTypes.STRING)
					.addScalar("otherDoc", StandardBasicTypes.STRING).addScalar("corporateType").addScalar("pancardNo")
					.addScalar("updatedOn").addScalar("updatedBy").addScalar("approvalLevel").addScalar("levelMaster")
					.addScalar("appId", StandardBasicTypes.LONG).addScalar("maxLimit").addScalar("address")
					.addScalar("adminTypes").addScalar("isCorporate").addScalar("branchCode")
					.addScalar("makerId", StandardBasicTypes.INTEGER).addScalar("checkerId", StandardBasicTypes.INTEGER)
					.addScalar("makerValidated", StandardBasicTypes.INTEGER)
					.addScalar("checkerValidated", StandardBasicTypes.INTEGER)
					.setParameter("cif", EncryptorDecryptor.encryptData(corpCompanyMasterDupEntity.getCif()))
					.setParameter("branchCode", corpCompanyMasterDupEntity.getBranchCode())
					.setResultTransformer(Transformers.aliasToBean(CorpCompanyMasterDupEntity.class)).list();
		} catch (Exception e) {
			logger.error("Error: " + e);
		}
		return list;
	}

	@Override
	public List<CorpUserDupEntity> getOfflineCorpUsersDupById(CorpUserDupEntity corpUserDupEntity) {
		List<CorpUserDupEntity> list = null;
		try {
			String sqlQuery = "select cu.ID as id,cu.CORP_COMP_ID as corp_comp_id, cu.USER_DISP_NAME as user_disp_name, cu.USER_NAME as user_name, cu.CREATEDBY as createdby, cu.CREATEDON as createdon, "
					+ "cu.STATUSID as statusid, cu.APPID as appid, cu.LASTLOGINTIME as lastLoginTime, cu.USER_TYPE as user_type, "
					+ "cu.FIRST_NAME as first_name, cu.LAST_NAME as last_name, cu.EMAIL_ID as email_id, cu.COUNTRY as country, cu.WORK_PHONE as work_phone, "
					+ "cu.PERSONAL_PHONE as personal_Phone, cu.NATIONALID as nationalId, cu.PASSPORT as passport, cu.BOARDRESOLUTION as boardResolution, "
					+ "cu.USER_IMAGE as user_image, cu.TPIN as tpin, cu.PASSPORTNUMBER as passportNumber, cu.NATIONALIDNUMBER as nationalIdNumber, "
					+ "cu.TPIN_STATUS as tpin_status, cu.TPIN_WRONG_ATTEMPT as tpin_wrong_attempt, cu.CITY as city, "
					+ "cu.WRONG_PWD_ATTEMPT as wrong_pwd_attempt, cu.PWD_STATUS as pwd_status, cu.OTHERDOC as otherDoc, "
					+ "cu.CERTIFICATE_INCORPORATION as certificate_incorporation, cu.STATE as state, cu.DESIGNATION as designation, "
					+ "cu.LAST_TPIN_WRONG_ATTEMPT as last_tpin_wrong_attempt, cu.LAST_PWD_WRONG_ATTEMPT as last_pwd_wrong_attempt, cu.MPIN as mpin, "
					+ "cu.MPIN_WRONG_ATTEMPT as mpin_wrong_attempt, cu.LAST_MPIN_WRONG_ATTEMPT as last_mpin_wrong_attempt, cu.PANCARD as pancard, "
					+ "cu.PANCARDNUMBER as pancardNumber, cu.UPDATEDBY as updatedby, cu.UPDATEDON as updatedOn,cu.DOB as dob, cu.RIGHTS as rights, "
					+ "cu.MAXLIMIT as transMaxLimit, cu.PKISTATUS as pkiStatus, cu.MOBREGSTATUS as mobRegStatus, cu.IBREGSTATUS as ibRegStatus, "
					+ "cu.WRONGATTEMPTSSOFTTOKEN as wrongAttemptSoftToken, cu.PARENTID as parentId, cu.PARENTROLEID as parentRoleId, "
					+ "cu.PARENTUSERNAME as parentUserName, cu.AADHARCARDNO as aadharCardNo, cu.CORPROLEID as corpRoleId, cu.PARENTRRN as parentRrn, "
					+ "cu.RRN as rrn, cu.REMARKS as remark, cu.USER_PWD as user_pwd, cu.MAKER_ID as makerId, cu.CHECKER_ID as checkerId, cu.MAKER_VALIDATED as makerValidated, cu.CHECKER_VALIDATED as checkerValidated from CORP_USERS_DUP cu "
					+ "where cu.id=:userId and cu.Corp_Comp_Id=:corpCompId";
			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("corp_comp_id")
					.addScalar("user_disp_name").addScalar("user_name").addScalar("createdby").addScalar("createdon")
					.addScalar("statusid").addScalar("appid", StandardBasicTypes.BIG_DECIMAL).addScalar("lastLoginTime")
					.addScalar("user_type").addScalar("first_name").addScalar("last_name").addScalar("email_id")
					.addScalar("country").addScalar("work_phone").addScalar("personal_Phone").addScalar("nationalId")
					.addScalar("passport", StandardBasicTypes.STRING).addScalar("boardResolution")
					.addScalar("user_image").addScalar("tpin", StandardBasicTypes.STRING)
					.addScalar("passportNumber", StandardBasicTypes.STRING).addScalar("nationalIdNumber")
					.addScalar("tpin_status").addScalar("tpin_wrong_attempt").addScalar("city")
					.addScalar("wrong_pwd_attempt").addScalar("pwd_status").addScalar("otherDoc")
					.addScalar("certificate_incorporation").addScalar("state").addScalar("designation")
					.addScalar("last_tpin_wrong_attempt").addScalar("last_pwd_wrong_attempt").addScalar("mpin")
					.addScalar("mpin_wrong_attempt").addScalar("pancard").addScalar("pancardNumber")
					.addScalar("updatedby").addScalar("updatedOn").addScalar("dob").addScalar("rights")
					.addScalar("transMaxLimit").addScalar("pkiStatus").addScalar("mobRegStatus")
					.addScalar("ibRegStatus").addScalar("wrongAttemptSoftToken").addScalar("parentId")
					.addScalar("parentRoleId").addScalar("parentUserName").addScalar("aadharCardNo")
					.addScalar("corpRoleId").addScalar("parentRrn").addScalar("rrn").addScalar("remark")
					.addScalar("user_pwd").addScalar("makerId", StandardBasicTypes.INTEGER)
					.addScalar("checkerId", StandardBasicTypes.INTEGER)
					.addScalar("makerValidated", StandardBasicTypes.INTEGER)
					.addScalar("checkerValidated", StandardBasicTypes.INTEGER)
					.setParameter("userId", corpUserDupEntity.getId())
					.setParameter("corpCompId", corpUserDupEntity.getCorp_comp_id())
					.setResultTransformer(Transformers.aliasToBean(CorpUserDupEntity.class)).list();
		} catch (Exception e) {
			logger.error("Error: " + e);
		}
		return list;
	}

	@Override
	public List<CorpCompanyMasterEntity> getOfflineCorpCompDatByCif(CorpCompanyMasterEntity corpCompanyMasterEntity) {
		List<CorpCompanyMasterEntity> list = null;
		try {
			String sqlQuery = "select corp.id as id , corp.COMPANYNAME as companyName,corp.COMPANYCODE as companyCode, corp.SHORTNAME as shortName, corp.COMPANYINFO as companyInfo, "
					+ "corp.ESTABLISHMENTON as establishmentOn,corp.LOGO as logo,corp.STATUSID as statusId,corp.CREATEDBY as createdBy, "
					+ "corp.CREATEDON as createdOn,corp.CIF as cif,corp.MAKER_LIMIT as makerLimit,corp.CHECKER_LIMIT as checkerLimit,corp.PHONENO as phoneNo, "
					+ "corp.RRN as rrn, corp.COI as coi,corp.MOA as moa,corp.OTHERDOC as otherDoc,corp.CORPORATETYPE as corporateType, "
					+ "corp.PANCARDNO as pancardNo,corp.UPDATEDON as updatedOn,corp.UPDATEDBY as updatedBy,corp.ApprovalLevel as approvalLevel, "
					+ "corp.LevelMaster as levelMaster,corp.APPID as appId,corp.MAX_LIMIT as maxLimit, corp.ADDRESS as address,corp.ADMIN_TYPES as adminTypes, "
					+ "corp.IS_CORPORATE as isCorporate,corp.BRANCHCODE as branchCode "
					+ "from CORP_COMPANY_MASTER corp where corp.cif=:cif and corp.branchCode=:branchCode and corp.IS_CORPORATE='G' and corp.statusId in(8,3)";
			logger.info("fetch data for update query............" + sqlQuery + ", cif: "
					+ corpCompanyMasterEntity.getCif() + ", BranchCode: " + corpCompanyMasterEntity.getBranchCode());
			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("companyName")
					.addScalar("companyCode").addScalar("shortName").addScalar("companyInfo", StandardBasicTypes.STRING)
					.addScalar("establishmentOn").addScalar("logo", StandardBasicTypes.STRING).addScalar("statusId")
					.addScalar("createdBy").addScalar("createdOn").addScalar("cif").addScalar("makerLimit")
					.addScalar("checkerLimit").addScalar("phoneNo").addScalar("rrn")
					.addScalar("coi", StandardBasicTypes.STRING).addScalar("moa", StandardBasicTypes.STRING)
					.addScalar("otherDoc", StandardBasicTypes.STRING).addScalar("corporateType").addScalar("pancardNo")
					.addScalar("updatedOn").addScalar("updatedBy").addScalar("approvalLevel").addScalar("levelMaster")
					.addScalar("appId", StandardBasicTypes.LONG).addScalar("maxLimit").addScalar("address")
					.addScalar("adminTypes").addScalar("isCorporate").addScalar("branchCode")
					.setParameter("cif", EncryptorDecryptor.encryptData(corpCompanyMasterEntity.getCif()))
					.setParameter("branchCode", corpCompanyMasterEntity.getBranchCode())
					.setResultTransformer(Transformers.aliasToBean(CorpCompanyMasterEntity.class)).list();
		} catch (Exception e) {
			logger.error("Error: " + e);
		}
		return list;
	}

	@Override
	public List<CorpUserEntity> getOfflineCorpUsersById(CorpUserEntity corpUserEntity) {
		List<CorpUserEntity> list = null;
		try {
			String sqlQuery = "select cu.ID as id,cu.CORP_COMP_ID as corp_comp_id, cu.USER_DISP_NAME as user_disp_name, cu.USER_NAME as user_name, cu.CREATEDBY as createdby, cu.CREATEDON as createdon, "
					+ "cu.STATUSID as statusid, cu.APPID as appid, cu.LASTLOGINTIME as lastLoginTime, cu.USER_TYPE as user_type, "
					+ "cu.FIRST_NAME as first_name, cu.LAST_NAME as last_name, cu.EMAIL_ID as email_id, cu.COUNTRY as country, cu.WORK_PHONE as work_phone, "
					+ "cu.PERSONAL_PHONE as personal_Phone, cu.NATIONALID as nationalId, cu.PASSPORT as passport, cu.BOARDRESOLUTION as boardResolution, "
					+ "cu.USER_IMAGE as user_image, cu.TPIN as tpin, cu.PASSPORTNUMBER as passportNumber, cu.NATIONALIDNUMBER as nationalIdNumber, "
					+ "cu.TPIN_STATUS as tpin_status, cu.TPIN_WRONG_ATTEMPT as tpin_wrong_attempt, cu.CITY as city, "
					+ "cu.WRONG_PWD_ATTEMPT as wrong_pwd_attempt, cu.PWD_STATUS as pwd_status, cu.OTHERDOC as otherDoc, "
					+ "cu.CERTIFICATE_INCORPORATION as certificate_incorporation, cu.STATE as state, cu.DESIGNATION as designation, "
					+ "cu.LAST_TPIN_WRONG_ATTEMPT as last_tpin_wrong_attempt, cu.LAST_PWD_WRONG_ATTEMPT as last_pwd_wrong_attempt, cu.MPIN as mpin, "
					+ "cu.MPIN_WRONG_ATTEMPT as mpin_wrong_attempt, cu.LAST_MPIN_WRONG_ATTEMPT as last_mpin_wrong_attempt, cu.PANCARD as pancard, "
					+ "cu.PANCARDNUMBER as pancardNumber, cu.UPDATEDBY as updatedby, cu.UPDATEDON as updatedOn,cu.DOB as dob, cu.RIGHTS as rights, "
					+ "cu.MAXLIMIT as transMaxLimit, cu.PKISTATUS as pkiStatus, cu.MOBREGSTATUS as mobRegStatus, cu.IBREGSTATUS as ibRegStatus, "
					+ "cu.WRONGATTEMPTSSOFTTOKEN as wrongAttemptSoftToken, cu.PARENTID as parentId, cu.PARENTROLEID as parentRoleId, "
					+ "cu.PARENTUSERNAME as parentUserName, cu.AADHARCARDNO as aadharCardNo, cu.CORPROLEID as corpRoleId, cu.PARENTRRN as parentRrn, "
					+ "cu.RRN as rrn, cu.REMARKS as remark, cu.USER_PWD as user_pwd from CORP_USERS cu "
					+ "where cu.id=:userId and cu.corp_comp_id=:corpCompId and cu.statusId=8";
			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("corp_comp_id")
					.addScalar("user_disp_name").addScalar("user_name").addScalar("createdby").addScalar("createdon")
					.addScalar("statusid").addScalar("appid", StandardBasicTypes.BIG_DECIMAL).addScalar("lastLoginTime")
					.addScalar("user_type").addScalar("first_name").addScalar("last_name").addScalar("email_id")
					.addScalar("country").addScalar("work_phone").addScalar("personal_Phone").addScalar("nationalId")
					.addScalar("passport", StandardBasicTypes.STRING).addScalar("boardResolution")
					.addScalar("user_image").addScalar("tpin", StandardBasicTypes.STRING)
					.addScalar("passportNumber", StandardBasicTypes.STRING).addScalar("nationalIdNumber")
					.addScalar("tpin_status").addScalar("tpin_wrong_attempt").addScalar("city")
					.addScalar("wrong_pwd_attempt").addScalar("pwd_status").addScalar("otherDoc")
					.addScalar("certificate_incorporation").addScalar("state").addScalar("designation")
					.addScalar("last_tpin_wrong_attempt").addScalar("last_pwd_wrong_attempt").addScalar("mpin")
					.addScalar("mpin_wrong_attempt").addScalar("pancard").addScalar("pancardNumber")
					.addScalar("updatedby").addScalar("updatedOn").addScalar("dob").addScalar("rights")
					.addScalar("transMaxLimit").addScalar("pkiStatus").addScalar("mobRegStatus")
					.addScalar("ibRegStatus").addScalar("wrongAttemptSoftToken").addScalar("parentId")
					.addScalar("parentRoleId").addScalar("parentUserName").addScalar("aadharCardNo")
					.addScalar("corpRoleId").addScalar("parentRrn").addScalar("rrn").addScalar("remark")
					.addScalar("user_pwd").setParameter("userId", corpUserEntity.getId())
					.setParameter("corpCompId", corpUserEntity.getCorp_comp_id())
					.setResultTransformer(Transformers.aliasToBean(CorpUserEntity.class)).list();
		} catch (Exception e) {
			logger.error("Error: " + e);
		}
		return list;
	}

	@Override
	public List<CorpCompanyCorpUserDupBean> getOfflineCorpCompAndCorpUserData(
			CorpCompanyCorpUserDupBean corpCompanyCorpUserDupBean) {
		List<CorpCompanyCorpUserDupBean> list = null;
		try {
			String sqlQuery = "select corp.id as id , corp.COMPANYNAME as companyName,corp.COMPANYCODE as companyCode, corp.SHORTNAME as shortName, corp.COMPANYINFO as companyInfo, "
					+ "corp.ESTABLISHMENTON as establishmentOn,corp.LOGO as logo,corp.STATUSID as statusId,corp.CREATEDBY as createdBy, "
					+ "corp.CREATEDON as createdOn,corp.CIF as cif,corp.MAKER_LIMIT as makerLimit,corp.CHECKER_LIMIT as checkerLimit,corp.PHONENO as phoneNo, "
					+ "corp.RRN as rrn, corp.COI as coi,corp.MOA as moa,corp.OTHERDOC as otherDoc,corp.CORPORATETYPE as corporateType, "
					+ "corp.PANCARDNO as pancardNo,corp.UPDATEDON as updatedOn,corp.UPDATEDBY as updatedBy,corp.ApprovalLevel as approvalLevel, "
					+ "corp.LevelMaster as levelMaster,corp.APPID as appId,corp.MAX_LIMIT as maxLimit, corp.ADDRESS as address,corp.ADMIN_TYPES as adminTypes, "
					+ "corp.IS_CORPORATE as isCorporate,corp.BRANCHCODE as branchCode, cu.ID as userId, cu.CORP_COMP_ID as corp_comp_id, "
					+ "cu.USER_DISP_NAME as user_disp_name, cu.USER_NAME as user_name, cu.CREATEDBY as userCreatedBy, cu.CREATEDON as userCreatedOn, "
					+ "cu.STATUSID as userStatusId, cu.APPID as userAppId, cu.LASTLOGINTIME as lastLoginTime, cu.USER_TYPE as user_type, "
					+ "cu.FIRST_NAME as first_name, cu.LAST_NAME as last_name, cu.EMAIL_ID as email_id, cu.COUNTRY as country, cu.WORK_PHONE as work_phone, "
					+ "cu.PERSONAL_PHONE as personal_Phone, cu.NATIONALID as nationalId, cu.PASSPORT as passport, cu.BOARDRESOLUTION as boardResolution, "
					+ "cu.USER_IMAGE as user_image, cu.TPIN as tpin, cu.PASSPORTNUMBER as passportNumber, cu.NATIONALIDNUMBER as nationalIdNumber, "
					+ "cu.TPIN_STATUS as tpin_status, cu.TPIN_WRONG_ATTEMPT as tpin_wrong_attempt, cu.CITY as city, "
					+ "cu.WRONG_PWD_ATTEMPT as wrong_pwd_attempt, cu.PWD_STATUS as pwd_status, cu.OTHERDOC as userOtherDoc, "
					+ "cu.CERTIFICATE_INCORPORATION as certificate_incorporation, cu.STATE as state, cu.DESIGNATION as designation, "
					+ "cu.LAST_TPIN_WRONG_ATTEMPT as last_tpin_wrong_attempt, cu.LAST_PWD_WRONG_ATTEMPT as last_pwd_wrong_attempt, cu.MPIN as mpin, "
					+ "cu.MPIN_WRONG_ATTEMPT as mpin_wrong_attempt, cu.LAST_MPIN_WRONG_ATTEMPT as last_mpin_wrong_attempt, cu.PANCARD as pancard, "
					+ "cu.PANCARDNUMBER as pancardNumber, cu.UPDATEDBY as userUpdatedBy, cu.UPDATEDON as userUpdatedOn,cu.DOB as dob, cu.RIGHTS as rights, "
					+ "cu.MAXLIMIT as transMaxLimit, cu.PKISTATUS as pkiStatus, cu.MOBREGSTATUS as mobRegStatus, cu.IBREGSTATUS as ibRegStatus, "
					+ "cu.WRONGATTEMPTSSOFTTOKEN as wrongAttemptSoftToken, cu.PARENTID as parentId, cu.PARENTROLEID as parentRoleId, "
					+ "cu.PARENTUSERNAME as parentUserName, cu.AADHARCARDNO as aadharCardNo, cu.CORPROLEID as corpRoleId, cu.PARENTRRN as parentRrn, "
					+ "cu.RRN as userRrn, cu.REMARKS as remark, cu.USER_PWD as user_pwd from CORP_COMPANY_MASTER corp "
					+ "inner join corp_users cu on corp.id=cu.CORP_COMP_ID where corp.cif=:cif and corp.branchCode=:branchCode and corp.IS_CORPORATE='G' and cu.user_Name=:userName AND corp.STATUSID in(8,3) AND cu.STATUSID=8";
			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("companyName")
					.addScalar("companyCode").addScalar("shortName").addScalar("companyInfo", StandardBasicTypes.STRING)
					.addScalar("establishmentOn").addScalar("logo", StandardBasicTypes.STRING).addScalar("statusId")
					.addScalar("createdBy").addScalar("createdOn").addScalar("makerLimit").addScalar("checkerLimit")
					.addScalar("phoneNo").addScalar("rrn").addScalar("coi", StandardBasicTypes.STRING)
					.addScalar("moa", StandardBasicTypes.STRING).addScalar("otherDoc", StandardBasicTypes.STRING)
					.addScalar("corporateType").addScalar("pancardNo").addScalar("updatedOn").addScalar("updatedBy")
					.addScalar("approvalLevel").addScalar("levelMaster").addScalar("appId", StandardBasicTypes.LONG)
					.addScalar("maxLimit").addScalar("address").addScalar("adminTypes").addScalar("isCorporate")
					.addScalar("branchCode").addScalar("userId").addScalar("corp_comp_id").addScalar("user_disp_name")
					.addScalar("user_name").addScalar("userCreatedBy").addScalar("userCreatedOn")
					.addScalar("userStatusId").addScalar("userAppId", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("lastLoginTime").addScalar("user_type").addScalar("first_name").addScalar("last_name")
					.addScalar("email_id").addScalar("country").addScalar("work_phone").addScalar("personal_Phone")
					.addScalar("nationalId").addScalar("passport", StandardBasicTypes.STRING)
					.addScalar("boardResolution").addScalar("user_image").addScalar("tpin", StandardBasicTypes.STRING)
					.addScalar("passportNumber", StandardBasicTypes.STRING).addScalar("nationalIdNumber")
					.addScalar("tpin_status").addScalar("tpin_wrong_attempt").addScalar("city")
					.addScalar("wrong_pwd_attempt").addScalar("pwd_status").addScalar("userOtherDoc")
					.addScalar("certificate_incorporation").addScalar("state").addScalar("designation")
					.addScalar("last_tpin_wrong_attempt").addScalar("last_pwd_wrong_attempt").addScalar("mpin")
					.addScalar("mpin_wrong_attempt").addScalar("pancard").addScalar("pancardNumber")
					.addScalar("userUpdatedBy").addScalar("userUpdatedOn").addScalar("dob").addScalar("rights")
					.addScalar("transMaxLimit").addScalar("pkiStatus").addScalar("mobRegStatus")
					.addScalar("ibRegStatus").addScalar("wrongAttemptSoftToken").addScalar("parentId")
					.addScalar("parentRoleId").addScalar("parentUserName").addScalar("aadharCardNo")
					.addScalar("corpRoleId").addScalar("parentRrn").addScalar("userRrn").addScalar("remark")
					.addScalar("user_pwd")
					.setParameter("cif", EncryptorDecryptor.encryptData(corpCompanyCorpUserDupBean.getCif()))
					.setParameter("userName", EncryptorDecryptor.encryptData(corpCompanyCorpUserDupBean.getUser_name()))
					.setParameter("branchCode", corpCompanyCorpUserDupBean.getBranchCode())
					.setResultTransformer(Transformers.aliasToBean(CorpCompanyCorpUserDupBean.class)).list();
		} catch (Exception e) {
			logger.info("Error: " + e);
		}
		return list;
	}

	@Override
	public List<CorpCompanyCorpUserDupBean> getOfflineCorpCompAndCorpUserDataDup(
			CorpCompanyCorpUserDupBean corpCompanyCorpUserDupBean) {
		List<CorpCompanyCorpUserDupBean> list = null;
		try {
			String sqlQuery = "select corp.id as id , corp.COMPANYNAME as companyName,corp.COMPANYCODE as companyCode, corp.SHORTNAME as shortName, corp.COMPANYINFO as companyInfo, "
					+ "corp.ESTABLISHMENTON as establishmentOn,corp.LOGO as logo,corp.STATUSID as statusId,corp.CREATEDBY as createdBy, "
					+ "corp.CREATEDON as createdOn,corp.CIF as cif,corp.MAKER_LIMIT as makerLimit,corp.CHECKER_LIMIT as checkerLimit,corp.PHONENO as phoneNo, "
					+ "corp.RRN as rrn, corp.COI as coi,corp.MOA as moa,corp.OTHERDOC as otherDoc,corp.CORPORATETYPE as corporateType, "
					+ "corp.PANCARDNO as pancardNo,corp.UPDATEDON as updatedOn,corp.UPDATEDBY as updatedBy,corp.ApprovalLevel as approvalLevel, "
					+ "corp.LevelMaster as levelMaster,corp.APPID as appId,corp.MAX_LIMIT as maxLimit, corp.ADDRESS as address,corp.ADMIN_TYPES as adminTypes, "
					+ "corp.IS_CORPORATE as isCorporate,corp.BRANCHCODE as branchCode, cu.ID as userId, cu.CORP_COMP_ID as corp_comp_id, "
					+ "cu.USER_DISP_NAME as user_disp_name, cu.USER_NAME as user_name, cu.CREATEDBY as userCreatedBy, cu.CREATEDON as userCreatedOn, "
					+ "cu.STATUSID as userStatusId, cu.APPID as userAppId, cu.LASTLOGINTIME as lastLoginTime, cu.USER_TYPE as user_type, "
					+ "cu.FIRST_NAME as first_name, cu.LAST_NAME as last_name, cu.EMAIL_ID as email_id, cu.COUNTRY as country, cu.WORK_PHONE as work_phone, "
					+ "cu.PERSONAL_PHONE as personal_Phone, cu.NATIONALID as nationalId, cu.PASSPORT as passport, cu.BOARDRESOLUTION as boardResolution, "
					+ "cu.USER_IMAGE as user_image, cu.TPIN as tpin, cu.PASSPORTNUMBER as passportNumber, cu.NATIONALIDNUMBER as nationalIdNumber, "
					+ "cu.TPIN_STATUS as tpin_status, cu.TPIN_WRONG_ATTEMPT as tpin_wrong_attempt, cu.CITY as city, "
					+ "cu.WRONG_PWD_ATTEMPT as wrong_pwd_attempt, cu.PWD_STATUS as pwd_status, cu.OTHERDOC as userOtherDoc, "
					+ "cu.CERTIFICATE_INCORPORATION as certificate_incorporation, cu.STATE as state, cu.DESIGNATION as designation, "
					+ "cu.LAST_TPIN_WRONG_ATTEMPT as last_tpin_wrong_attempt, cu.LAST_PWD_WRONG_ATTEMPT as last_pwd_wrong_attempt, cu.MPIN as mpin, "
					+ "cu.MPIN_WRONG_ATTEMPT as mpin_wrong_attempt, cu.LAST_MPIN_WRONG_ATTEMPT as last_mpin_wrong_attempt, cu.PANCARD as pancard, "
					+ "cu.PANCARDNUMBER as pancardNumber, cu.UPDATEDBY as userUpdatedBy, cu.UPDATEDON as userUpdatedOn,cu.DOB as dob, cu.RIGHTS as rights, "
					+ "cu.MAXLIMIT as transMaxLimit, cu.PKISTATUS as pkiStatus, cu.MOBREGSTATUS as mobRegStatus, cu.IBREGSTATUS as ibRegStatus, "
					+ "cu.WRONGATTEMPTSSOFTTOKEN as wrongAttemptSoftToken, cu.PARENTID as parentId, cu.PARENTROLEID as parentRoleId, "
					+ "cu.PARENTUSERNAME as parentUserName, cu.AADHARCARDNO as aadharCardNo, cu.CORPROLEID as corpRoleId, cu.PARENTRRN as parentRrn, "
					+ "cu.RRN as userRrn, cu.REMARKS as remark, cu.USER_PWD as user_pwd, cu.MAKER_VALIDATED as makerValidated, cu.CHECKER_VALIDATED as checkerValidated from CORP_COMPANY_MASTER_DUP corp "
					+ "inner join corp_users_dup cu on corp.id=cu.CORP_COMP_ID where corp.cif=:cif and cu.user_Name=:userName and corp.branchCode=:branchCode  and corp.IS_CORPORATE='G'";
			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("companyName")
					.addScalar("companyCode").addScalar("shortName").addScalar("companyInfo", StandardBasicTypes.STRING)
					.addScalar("establishmentOn").addScalar("logo", StandardBasicTypes.STRING).addScalar("statusId")
					.addScalar("createdBy").addScalar("createdOn").addScalar("makerLimit").addScalar("checkerLimit")
					.addScalar("phoneNo").addScalar("rrn").addScalar("coi", StandardBasicTypes.STRING)
					.addScalar("moa", StandardBasicTypes.STRING).addScalar("otherDoc", StandardBasicTypes.STRING)
					.addScalar("corporateType").addScalar("pancardNo").addScalar("updatedOn").addScalar("updatedBy")
					.addScalar("approvalLevel").addScalar("levelMaster").addScalar("appId", StandardBasicTypes.LONG)
					.addScalar("maxLimit").addScalar("address").addScalar("adminTypes").addScalar("isCorporate")
					.addScalar("branchCode").addScalar("userId").addScalar("corp_comp_id").addScalar("user_disp_name")
					.addScalar("user_name").addScalar("userCreatedBy").addScalar("userCreatedOn")
					.addScalar("userStatusId").addScalar("userAppId", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("lastLoginTime").addScalar("user_type").addScalar("first_name").addScalar("last_name")
					.addScalar("email_id").addScalar("country").addScalar("work_phone").addScalar("personal_Phone")
					.addScalar("nationalId").addScalar("passport", StandardBasicTypes.STRING)
					.addScalar("boardResolution").addScalar("user_image").addScalar("tpin", StandardBasicTypes.STRING)
					.addScalar("passportNumber", StandardBasicTypes.STRING).addScalar("nationalIdNumber")
					.addScalar("tpin_status").addScalar("tpin_wrong_attempt").addScalar("city")
					.addScalar("wrong_pwd_attempt").addScalar("pwd_status").addScalar("userOtherDoc")
					.addScalar("certificate_incorporation").addScalar("state").addScalar("designation")
					.addScalar("last_tpin_wrong_attempt").addScalar("last_pwd_wrong_attempt").addScalar("mpin")
					.addScalar("mpin_wrong_attempt").addScalar("pancard").addScalar("pancardNumber")
					.addScalar("userUpdatedBy").addScalar("userUpdatedOn").addScalar("dob").addScalar("rights")
					.addScalar("transMaxLimit").addScalar("pkiStatus").addScalar("mobRegStatus")
					.addScalar("ibRegStatus").addScalar("wrongAttemptSoftToken").addScalar("parentId")
					.addScalar("parentRoleId").addScalar("parentUserName").addScalar("aadharCardNo")
					.addScalar("corpRoleId").addScalar("parentRrn").addScalar("userRrn").addScalar("remark")
					.addScalar("user_pwd").addScalar("makerValidated", StandardBasicTypes.BOOLEAN)
					.addScalar("checkerValidated", StandardBasicTypes.BOOLEAN)
					.setParameter("cif", EncryptorDecryptor.encryptData(corpCompanyCorpUserDupBean.getCif()))
					.setParameter("userName", EncryptorDecryptor.encryptData(corpCompanyCorpUserDupBean.getUser_name()))
					.setParameter("branchCode", corpCompanyCorpUserDupBean.getBranchCode())
					.setResultTransformer(Transformers.aliasToBean(CorpCompanyCorpUserDupBean.class)).list();
		} catch (Exception e) {
			logger.info("Error: " + e);
		}
		return list;
	}

	public void saveOfflineCorpCompAndCorpUserData(CorpCompanyCorpUserDupBean corpCompanyCorpUserDupBean,
			List<CorpUserEntity> corpUserEntities) {

		CorpCompanyMasterDupEntity corpCompanyMasterDupEntity = new CorpCompanyMasterDupEntity();
		corpCompanyMasterDupEntity.setBranchCode(corpCompanyCorpUserDupBean.getBranchCode());
//		corpCompanyMasterDupEntity.setCif(corpCompanyCorpUserDupBean.getCif());
		CorpCompanyMasterDupEntity corpCompanyMasterDupEntityDb = corpCompanyMasterDupRepository.findByCifAndBranchCode(
				EncryptorDecryptor.encryptData(corpCompanyCorpUserDupBean.getCif()),
				corpCompanyCorpUserDupBean.getBranchCode());
//		List<CorpCompanyMasterDupEntity> corpCompanyMasterDupEntities = getOfflineCorpCompDupByCif(
//				corpCompanyMasterDupEntity);
		try {

			if (ObjectUtils.isEmpty(corpCompanyMasterDupEntityDb)) {
				corpCompanyMasterDupEntity.setId(corpCompanyCorpUserDupBean.getId());
				corpCompanyMasterDupEntity
						.setCompanyCode(EncryptorDecryptor.encryptData(corpCompanyCorpUserDupBean.getCompanyCode()));
				corpCompanyMasterDupEntity
						.setCompanyName(EncryptorDecryptor.encryptData(corpCompanyCorpUserDupBean.getCompanyName()));
				corpCompanyMasterDupEntity.setShortName(corpCompanyCorpUserDupBean.getShortName());
				corpCompanyMasterDupEntity.setCompanyInfo(corpCompanyCorpUserDupBean.getCompanyInfo());
				corpCompanyMasterDupEntity.setEstablishmentOn(corpCompanyCorpUserDupBean.getEstablishmentOn());
				corpCompanyMasterDupEntity.setLogo(corpCompanyCorpUserDupBean.getLogo());
				corpCompanyMasterDupEntity.setStatusId(corpCompanyCorpUserDupBean.getStatusId());
				corpCompanyMasterDupEntity.setCreatedBy(corpCompanyCorpUserDupBean.getCreatedBy());
				corpCompanyMasterDupEntity.setCreatedOn(new Timestamp(System.currentTimeMillis()));
				corpCompanyMasterDupEntity.setCif(EncryptorDecryptor.encryptData(corpCompanyCorpUserDupBean.getCif()));
				corpCompanyMasterDupEntity.setMakerLimit(corpCompanyCorpUserDupBean.getMaxLimit());
				corpCompanyMasterDupEntity.setCheckerLimit(corpCompanyCorpUserDupBean.getMaxLimit());
				corpCompanyMasterDupEntity.setPhoneNo(corpCompanyCorpUserDupBean.getPhoneNo());
				corpCompanyMasterDupEntity.setRrn(corpCompanyCorpUserDupBean.getRrn());
				corpCompanyMasterDupEntity.setCoi(corpCompanyCorpUserDupBean.getCoi());
				corpCompanyMasterDupEntity.setMoa(corpCompanyCorpUserDupBean.getMoa());
				corpCompanyMasterDupEntity.setOtherDoc(corpCompanyCorpUserDupBean.getOtherDoc());
				corpCompanyMasterDupEntity.setCorporateType(corpCompanyCorpUserDupBean.getCorporateType());
				corpCompanyMasterDupEntity
						.setPancardNo(EncryptorDecryptor.encryptData(corpCompanyCorpUserDupBean.getPancardNo()));
				corpCompanyMasterDupEntity.setApprovalLevel(corpCompanyCorpUserDupBean.getApprovalLevel());
				corpCompanyMasterDupEntity.setLevelMaster(corpCompanyCorpUserDupBean.getLevelMaster());
				corpCompanyMasterDupEntity.setAppId(corpCompanyCorpUserDupBean.getAppId());
				corpCompanyMasterDupEntity.setMaxLimit(corpCompanyCorpUserDupBean.getMaxLimit());
				corpCompanyMasterDupEntity.setAddress(corpCompanyCorpUserDupBean.getAddress());
				corpCompanyMasterDupEntity.setAdminTypes(corpCompanyCorpUserDupBean.getAdminTypes());
				corpCompanyMasterDupEntity.setIsCorporate(corpCompanyCorpUserDupBean.getIsCorporate());
				corpCompanyMasterDupEntity.setBranchCode(corpCompanyCorpUserDupBean.getBranchCode());
				corpCompanyMasterDupEntity.setMakerId(corpCompanyCorpUserDupBean.getMakerId());
				corpCompanyMasterDupEntity.setMakerValidated(0);
				corpCompanyMasterDupEntity.setCheckerValidated(0);
				Session session = sessionFactory.getCurrentSession();
				Transaction tx = session.beginTransaction();
				logger.info("Company Request in DUP......." + corpCompanyMasterDupEntity.toString());
				session.save(corpCompanyMasterDupEntity);
				tx.commit();
			}
		} catch (Exception e) {
			logger.error("Error to create new corporate DUP... " + e);
		}

		CorpUserDupEntity corpUserDupEntity = new CorpUserDupEntity();
		corpUserDupEntity.setId(corpCompanyCorpUserDupBean.getUserId());
		corpUserDupEntity.setCorp_comp_id(corpCompanyCorpUserDupBean.getCorp_comp_id());
		List<CorpUserDupEntity> corpUserDupEntities = getOfflineCorpUsersDupById(corpUserDupEntity);
		try {
			Session newSession = sessionFactory.getCurrentSession();
			Transaction newTx = newSession.beginTransaction();
			if (ObjectUtils.isEmpty(corpUserDupEntities)) {
				corpUserDupEntity.setUser_disp_name(
						EncryptorDecryptor.encryptData(corpCompanyCorpUserDupBean.getUser_disp_name()));
				corpUserDupEntity
						.setUser_name(EncryptorDecryptor.encryptData(corpCompanyCorpUserDupBean.getUser_name()));
				corpUserDupEntity.setUser_pwd(corpCompanyCorpUserDupBean.getUser_pwd());
				corpUserDupEntity.setCreatedby(corpCompanyCorpUserDupBean.getUserCreatedBy());
				corpUserDupEntity.setCreatedon(new Timestamp(System.currentTimeMillis()));
				corpUserDupEntity.setStatusid(corpCompanyCorpUserDupBean.getUserStatusId());
				corpUserDupEntity.setAppid(corpCompanyCorpUserDupBean.getUserAppId());
				corpUserDupEntity.setLastLoginTime(corpCompanyCorpUserDupBean.getLastLoginTime());
				corpUserDupEntity.setUser_type(corpCompanyCorpUserDupBean.getUser_type());
				corpUserDupEntity.setFirst_name(corpCompanyCorpUserDupBean.getFirst_name());
				corpUserDupEntity.setLast_name(corpCompanyCorpUserDupBean.getLast_name());
				corpUserDupEntity.setEmail_id(EncryptorDecryptor.encryptData(corpCompanyCorpUserDupBean.getEmail_id()));
				corpUserDupEntity.setCountry(corpCompanyCorpUserDupBean.getCountry());
				corpUserDupEntity
						.setWork_phone(EncryptorDecryptor.encryptData(corpCompanyCorpUserDupBean.getPersonal_Phone()));
				corpUserDupEntity.setPersonal_Phone(
						EncryptorDecryptor.encryptData(corpCompanyCorpUserDupBean.getPersonal_Phone()));
				corpUserDupEntity.setNationalId(corpCompanyCorpUserDupBean.getNationalId());
				if (!ObjectUtils.isEmpty(corpCompanyCorpUserDupBean.getPassport())) {
					corpUserDupEntity
							.setPassport(EncryptorDecryptor.encryptData(corpCompanyCorpUserDupBean.getPassport()));
				}
				corpUserDupEntity
						.setAadharCardNo(EncryptorDecryptor.encryptData(corpCompanyCorpUserDupBean.getAadharCardNo()));
				corpUserDupEntity.setBoardResolution(corpCompanyCorpUserDupBean.getBoardResolution());
				corpUserDupEntity.setUser_image(corpCompanyCorpUserDupBean.getUser_image());
				corpUserDupEntity.setTpin(corpCompanyCorpUserDupBean.getTpin());
				corpUserDupEntity.setPassportNumber(corpCompanyCorpUserDupBean.getPassportNumber());
				corpUserDupEntity.setNationalIdNumber(corpCompanyCorpUserDupBean.getNationalIdNumber());
				corpUserDupEntity.setTpin_status(corpCompanyCorpUserDupBean.getTpin_status());
				corpUserDupEntity.setRights(corpCompanyCorpUserDupBean.getRights());
				corpUserDupEntity.setPkiStatus(corpCompanyCorpUserDupBean.getPkiStatus());
				corpUserDupEntity.setTpin_wrong_attempt(corpCompanyCorpUserDupBean.getTpin_wrong_attempt());
				corpUserDupEntity.setCity(corpCompanyCorpUserDupBean.getCity());
				corpUserDupEntity.setWrong_pwd_attempt(corpCompanyCorpUserDupBean.getWrong_pwd_attempt());
				corpUserDupEntity.setCity(corpCompanyCorpUserDupBean.getCity());
				corpUserDupEntity.setWrong_pwd_attempt(corpCompanyCorpUserDupBean.getWrong_pwd_attempt());
				corpUserDupEntity.setPwd_status(corpCompanyCorpUserDupBean.getPwd_status());
				corpUserDupEntity.setOtherDoc(corpCompanyCorpUserDupBean.getUserOtherDoc());
				corpUserDupEntity
						.setCertificate_incorporation(corpCompanyCorpUserDupBean.getCertificate_incorporation());
				corpUserDupEntity.setState(corpCompanyCorpUserDupBean.getState());
				corpUserDupEntity.setDesignation(corpCompanyCorpUserDupBean.getDesignation());
				corpUserDupEntity.setLast_tpin_wrong_attempt(corpCompanyCorpUserDupBean.getLast_mpin_wrong_attempt());
				corpUserDupEntity.setLast_pwd_wrong_attempt(corpCompanyCorpUserDupBean.getLast_pwd_wrong_attempt());
				corpUserDupEntity.setMpin(corpCompanyCorpUserDupBean.getMpin());
				corpUserDupEntity.setMpin_wrong_attempt(corpCompanyCorpUserDupBean.getMpin_wrong_attempt());
				corpUserDupEntity.setLast_mpin_wrong_attempt(corpCompanyCorpUserDupBean.getLast_mpin_wrong_attempt());
				corpUserDupEntity.setPancardNumber(
						EncryptorDecryptor.encryptData(corpCompanyCorpUserDupBean.getPancardNumber()));
				corpUserDupEntity.setTransMaxLimit(corpCompanyCorpUserDupBean.getTransMaxLimit());
				corpUserDupEntity.setMobRegStatus(corpCompanyCorpUserDupBean.getMobRegStatus());
				corpUserDupEntity.setIbRegStatus(corpCompanyCorpUserDupBean.getIbRegStatus());
				corpUserDupEntity.setPersonal_Phone(
						EncryptorDecryptor.encryptData(corpCompanyCorpUserDupBean.getPersonal_Phone()));
				corpUserDupEntity
						.setWork_phone(EncryptorDecryptor.encryptData(corpCompanyCorpUserDupBean.getPersonal_Phone()));
				corpUserDupEntity.setParentRoleId(corpCompanyCorpUserDupBean.getParentRoleId());
				corpUserDupEntity.setParentId(corpCompanyCorpUserDupBean.getParentId());
				corpUserDupEntity.setDob(EncryptorDecryptor.encryptData(corpCompanyCorpUserDupBean.getDob()));
				corpUserDupEntity.setParentUserName(corpCompanyCorpUserDupBean.getParentUserName());
				corpUserDupEntity.setCorpRoleId(corpCompanyCorpUserDupBean.getCorpRoleId());
				corpUserDupEntity.setRrn(corpCompanyCorpUserDupBean.getUserRrn());
				corpUserDupEntity.setParentRrn(corpCompanyCorpUserDupBean.getParentRrn());
				corpUserDupEntity.setRemark(corpCompanyCorpUserDupBean.getRemark());
				corpUserDupEntity.setWrongAttemptSoftToken(corpCompanyCorpUserDupBean.getWrongAttemptSoftToken());
				corpUserDupEntity.setMakerId(corpCompanyCorpUserDupBean.getMakerId());
				corpUserDupEntity.setMakerValidated(1);
				corpUserDupEntity.setCheckerValidated(0);

				logger.info("User Request in DUP......." + corpUserDupEntity.toString());

				newSession.save(corpUserDupEntity);

			} else {
				corpUserDupEntities.get(0).setPersonal_Phone(
						EncryptorDecryptor.encryptData(corpCompanyCorpUserDupBean.getPersonal_Phone()));
				corpUserDupEntities.get(0)
						.setWork_phone(EncryptorDecryptor.encryptData(corpCompanyCorpUserDupBean.getPersonal_Phone()));
				corpUserDupEntities.get(0)
						.setEmail_id(EncryptorDecryptor.encryptData(corpCompanyCorpUserDupBean.getEmail_id()));
				corpUserDupEntities.get(0).setPancardNumber(
						EncryptorDecryptor.encryptData(corpCompanyCorpUserDupBean.getPancardNumber()));
				corpUserDupEntities.get(0).setRemark(corpCompanyCorpUserDupBean.getRemark());
				corpUserDupEntities.get(0).setMakerId(corpCompanyCorpUserDupBean.getMakerId());
				corpUserDupEntities.get(0).setMakerValidated(1);
				corpUserDupEntities.get(0).setCheckerValidated(0);
				corpUserDupEntities.get(0).setUpdatedby(corpCompanyCorpUserDupBean.getUserCreatedBy());
				corpUserDupEntities.get(0).setUpdatedOn(new Timestamp(System.currentTimeMillis()));
				newSession.update(corpUserDupEntities.get(0));
			}
			newTx.commit();
		} catch (Exception e) {
			logger.error("error while create new corporate user in DUP..." + e);
		}

		if (!ObjectUtils.isEmpty(corpCompanyMasterDupEntityDb)) {
			Integer makerValidated = 0;
			if (!ObjectUtils.isEmpty(corpCompanyMasterDupEntity)) {
				makerValidated = corpCompanyMasterDupEntityDb.getMakerValidated();
			} else {
				makerValidated = corpCompanyMasterDupEntity.getMakerValidated();
			}

			CorpUserDupEntity corpUserDupReqData = new CorpUserDupEntity();
			corpUserDupReqData.setCorp_comp_id(corpCompanyCorpUserDupBean.getCorp_comp_id());
			List<Integer> statusList = new ArrayList<>();
			statusList.add(8);
			corpUserDupReqData.setStatusList(statusList);
			List<CorpUserDupEntity> corpUserDupEntityList = getAllCorpUsersDupByCompId(corpUserDupReqData, true, true);
			Set<String> userDupSet = new HashSet<>();
			corpUserDupEntityList.forEach(u -> userDupSet.add(u.getUser_name()));

			CorpUserEntity corpUserReqData = new CorpUserEntity();
			corpUserReqData.setCorp_comp_id(corpCompanyCorpUserDupBean.getCorp_comp_id());
			List<Integer> statusList1 = new ArrayList<>();
			statusList1.add(8);
			corpUserReqData.setStatusList(statusList1);
			List<CorpUserEntity> corpUserEntityList = getAllCorpUsersByCompId(corpUserReqData, true);
			Set<String> userSet = new HashSet<>();
			corpUserEntityList.forEach(u -> userSet.add(u.getUser_name()));

			if (!ObjectUtils.isEmpty(userSet)) {
				userSet.removeAll(userDupSet);
			}

			if (makerValidated == 0 && ObjectUtils.isEmpty(userSet)) {
				corpCompanyMasterDupRepository.updateCorpCompanyMasterDup(1, 0, corpCompanyMasterDupEntityDb.getId());
			}
		}
	}

	public void updateCorpCompanyAndUserDetails(CorpCompanyCorpUserDupBean corpCompanyCorpUserDupBean) {

		try {
			Session session = sessionFactory.getCurrentSession();

			logger.info("fetch company dup data..............");
			CorpCompanyMasterDupEntity corpCompanyMasterDupEntityDb = corpCompanyMasterDupRepository
					.findByCifAndBranchCode(EncryptorDecryptor.encryptData(corpCompanyCorpUserDupBean.getCif()),
							corpCompanyCorpUserDupBean.getBranchCode());

			BigDecimal corpCompId = corpCompanyMasterDupEntityDb.getId();
//			corpCompanyMasterDupEntityDb.setCheckerId(corpCompanyCorpUserDupBean.getCheckerId());
//			corpCompanyMasterDupEntityDb.setCheckerValidated(1);
//			corpCompanyMasterDupEntityDb.setUpdatedBy(corpCompanyCorpUserDupBean.getUpdatedBy());
//			corpCompanyMasterDupEntityDb.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
//			session.update(corpCompanyMasterDupEntityDb);
//			logger.info("update company dup data.........");

			CorpUserDupEntity corpUserDupEntity = new CorpUserDupEntity();
			corpUserDupEntity.setId(corpCompanyCorpUserDupBean.getUserId());
			corpUserDupEntity.setCorp_comp_id(corpCompId);
			logger.info("fetch user dup data.........");
			List<CorpUserDupEntity> corpUserDupEntities = getOfflineCorpUsersDupById(corpUserDupEntity);

			String email = corpUserDupEntities.get(0).getEmail_id();
			String panCard = corpUserDupEntities.get(0).getPancardNumber();
			String personalPhone = corpUserDupEntities.get(0).getPersonal_Phone();

			corpUserDupEntities.get(0).setCheckerId(corpCompanyCorpUserDupBean.getCheckerId());
			corpUserDupEntities.get(0).setCheckerValidated(1);
			corpUserDupEntities.get(0).setUpdatedby(corpCompanyCorpUserDupBean.getUpdatedBy());
			corpUserDupEntities.get(0).setUpdatedOn(new Timestamp(System.currentTimeMillis()));
			session.update(corpUserDupEntities.get(0));
			logger.info("update user dup data.........");

//			CorpCompanyMasterEntity corpCompanyMasterEntity = new CorpCompanyMasterEntity();
//			corpCompanyMasterEntity.setCif(corpCompanyCorpUserDupBean.getCif());
//			corpCompanyMasterEntity.setBranchCode(corpCompanyCorpUserDupBean.getBranchCode());
//			corpCompanyMasterEntity.setId(corpCompId);
//			logger.info("fetch company data.........");
//			List<CorpCompanyMasterEntity> corpCompanyMasterEntityList = getOfflineCorpCompDatByCif(
//					corpCompanyMasterEntity);
//
//			corpCompanyMasterEntityList.get(0).setUpdatedBy(corpCompanyCorpUserDupBean.getUpdatedBy());
//			corpCompanyMasterEntityList.get(0).setUpdatedOn(new Timestamp(System.currentTimeMillis()));
//			session.update(corpCompanyMasterEntityList.get(0));
			logger.info("update company data.........");

			CorpUserEntity corpUserEntity = new CorpUserEntity();
			corpUserEntity.setId(corpCompanyCorpUserDupBean.getUserId());
			corpUserEntity.setCorp_comp_id(corpCompId);
			logger.info("fetch user data.........");
			List<CorpUserEntity> corpUserEntities = getOfflineCorpUsersById(corpUserEntity);

			logger.info("Email to be update: " + email);
			logger.info("PanCard to be update: " + panCard);
			logger.info("Mobile to be update: " + personalPhone);
			// update corpUser master
			corpUserEntities.get(0).setEmail_id(email);
			corpUserEntities.get(0).setPancardNumber(panCard);
			corpUserEntities.get(0).setWork_phone(personalPhone);
			corpUserEntities.get(0).setPersonal_Phone(personalPhone);
			corpUserEntities.get(0).setUpdatedby(corpCompanyCorpUserDupBean.getUpdatedBy());
			corpUserEntities.get(0).setUpdatedOn(new Timestamp(System.currentTimeMillis()));
			session.update(corpUserEntities.get(0));
			logger.info("update user data.........");
		} catch (Exception e) {
			logger.error("Invalid Request.......");
		}
	}

	@Override
	public List<CorpUserDupEntity> getAllCorpUsersDupByCompId(CorpUserDupEntity corpuserReqData, boolean isDecrypted,
			boolean makerValidated) {
		logger.info("getAllCorpUsersByCompId service request.........CORP_ID" + corpuserReqData.getCorp_comp_id()
				+ "|statusId:" + corpuserReqData.getStatusList());
		List<CorpUserDupEntity> list = null;
		try {

			String sqlQuery = "select cu.id,cu.corp_comp_id, cu.user_name,cu.user_pwd,cu.first_name,cu.last_name,cu.email_id,cu.parentId, "
					+ "cu.user_disp_name, cu.createdby, cu.appid, cu.country, cu.work_phone, cu.tpin_wrong_attempt, cu.city, cu.wrong_pwd_attempt, "
					+ "cu.pwd_status, cu.state, cu.mpin_wrong_attempt, cu.rights,cu.MAXLIMIT as transMaxLimit, cu.pkistatus, cu.mobRegStatus, cu.ibRegStatus,"
					+ "	cu.personal_Phone, cu.dob,cu.pancardNumber, cu.rrn as rrn,cu.corpRoleId, cu.aadharCardNo, "
					+ "	cu.passport, cu.boardResolution, cu.user_image,cu.user_type as user_type,  "
					+ "	cu.otherDoc as otherDoc,cu.certificate_incorporation, cu.designation,cu.parentRrn, "
					+ "	cu.statusid, cu.createdon, cu.updatedOn,cu.updatedby as updatedby,cr.name as corpRoleName, cc.name as parentRoleName, "
					+ " cu.parentRoleId as parentRoleId, cu.parentUserName as parentUserName, cu.maker_validated as makerValidated, cu.checker_validated as checkerValidated from CORP_USERS_DUP  cu  "
					+ " left join corp_roles cr on cr.id=cu.corpRoleId  left join corp_roles cc on cc.id=cu.parentRoleId where cu.corp_comp_id=:compId AND cu.statusId in :statusId and cu.maker_validated=:makerValidated order by cu.id";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("corp_comp_id")
					.addScalar("user_name").addScalar("user_pwd", StandardBasicTypes.STRING).addScalar("first_name")
					.addScalar("last_name").addScalar("email_id").addScalar("parentId").addScalar("user_disp_name")
					.addScalar("createdby").addScalar("appid").addScalar("country").addScalar("work_phone")
					.addScalar("tpin_wrong_attempt").addScalar("city").addScalar("wrong_pwd_attempt")
					.addScalar("pwd_status").addScalar("state").addScalar("mpin_wrong_attempt").addScalar("rights")
					.addScalar("transMaxLimit").addScalar("pkiStatus").addScalar("mobRegStatus")
					.addScalar("ibRegStatus").addScalar("personal_Phone").addScalar("dob").addScalar("pancardNumber")
					.addScalar("rrn").addScalar("corpRoleId").addScalar("aadharCardNo", StandardBasicTypes.STRING)
					.addScalar("passport", StandardBasicTypes.STRING)// .addScalar("passportNo")
					.addScalar("boardResolution", StandardBasicTypes.STRING)
					.addScalar("user_image", StandardBasicTypes.STRING).addScalar("user_type")
					.addScalar("otherDoc", StandardBasicTypes.STRING)
					.addScalar("certificate_incorporation", StandardBasicTypes.STRING).addScalar("designation")
					.addScalar("parentRrn").addScalar("statusid").addScalar("createdon").addScalar("updatedOn")
					.addScalar("updatedby").addScalar("corpRoleName").addScalar("parentRoleName")
					.addScalar("parentRoleId").addScalar("parentUserName")
					.addScalar("makerValidated", StandardBasicTypes.INTEGER)
					.addScalar("checkerValidated", StandardBasicTypes.INTEGER)
					.setParameter("compId", corpuserReqData.getCorp_comp_id())
					.setParameterList("statusId", corpuserReqData.getStatusList()).setParameter("makerValidated", 1)
					.setResultTransformer(Transformers.aliasToBean(CorpUserDupEntity.class)).list();
			if (isDecrypted) {
				for (CorpUserDupEntity corpData : list) {
					corpData.setPersonal_Phone(EncryptorDecryptor.decryptData(corpData.getPersonal_Phone()));
					corpData.setUser_name(EncryptorDecryptor.decryptData(corpData.getUser_name()));
					corpData.setEmail_id(EncryptorDecryptor.decryptData(corpData.getEmail_id()));
					corpData.setDob(EncryptorDecryptor.decryptData(corpData.getDob()));
					corpData.setPancardNumber(EncryptorDecryptor.decryptData(corpData.getPancardNumber()));
					if (!ObjectUtils.isEmpty(corpData.getPassport())) {
						corpData.setPassport(EncryptorDecryptor.decryptData(corpData.getPassport()));
					} else {
						corpData.setPassport("");
					}
					corpData.setAadharCardNo(EncryptorDecryptor.decryptData(corpData.getAadharCardNo()));

				}
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return list;
	}

	@Override
	public boolean isCorporateWithTransactionRights(int corpCompId) {
		try {
			String sqlCorpName = "select cut.id from corp_company_master ccmt, corp_users cut where ccmt.id = cut.corp_comp_id and ccmt.id = :corpCompId and ccmt.max_limit = 0 and cut.maxlimit > 0 and cut.statusid <> 10";
			return !getSession().createSQLQuery(sqlCorpName).setParameter("corpCompId", corpCompId).list().isEmpty();
		} catch (Exception e) {
			logger.error("Exception:", e);
		}
		return true;
	}

	@Override
	public Map<String, Object> validateHierarchy(BigDecimal corpCompId) {
		logger.info("Validation start........corpCompId: " + corpCompId);
		Map<String, Object> response = new HashMap<>();

		List<BigDecimal> statusIdList = new ArrayList<>();
		statusIdList.add(BigDecimal.valueOf(0));
		statusIdList.add(BigDecimal.valueOf(35));
		statusIdList.add(BigDecimal.valueOf(36));
		statusIdList.add(BigDecimal.valueOf(8));

		CorpCompanyMasterEntity corpCompany = corpCompanyMasterRepo.findOne(corpCompId);
		List<CorpMenuMapEntity> corpMenuMap = corpMenuMapRepository.findByCorpIdAndStatusId(corpCompany.getId(),
				new BigDecimal(3));
		List<CorpAccMapEntity> corpAccMap = corpAccMapRepository.findByCorpIdAndStatusId(corpCompany.getId(),
				new BigDecimal(3));
		List<CorpUserMenuMapEntity> corpUserMenuMap = corpUsersMenuMapRepository
				.findByCorpCompIdAndStatusId(corpCompany.getId(), new BigDecimal(3));
		List<CorpUserAccMapEntity> corpUserAccMap = corpUsersAccMapRepository
				.findByCorpCompIdAndStatusId(corpCompany.getId(), new BigDecimal(3));

		List<CorpUserEntity> corpUsers = corpUserRepository.findByCorpCompIdAndStatusidIN(corpCompId, statusIdList);

		List<CorpRoles> corpRoles = corpRolesRepository.findAll();
		Set<BigDecimal> roleIdList = new HashSet<>();
		Set<String> roleNameList = new HashSet();
		for (CorpRoles roles : corpRoles) {
			roleIdList.add(roles.getId());
			roleNameList.add(roles.getName());
		}

		int corpId = corpCompany.getId().intValue();
		int userLevel = corpCompany.getLevelMaster().intValue();
		String adminType = corpCompany.getAdminTypes();
		String userType = corpCompany.getApprovalLevel();
		int corpTranxLimit = corpCompany.getMaxLimit().intValue();

		Set<Integer> moduleSet = new HashSet<>();
		Set<String> accountSet = new HashSet<>();
		// MenuName List
		corpMenuMap.forEach(m -> moduleSet.add(m.getCorpMenuId().intValue()));
		// AccountList
		corpAccMap.forEach(a -> accountSet.add(EncryptorDecryptor.decryptData(a.getAccountNo())));

		Set<Integer> regulatorModuleSet = new HashSet<>();
		Set<String> regulatorAccSet = new HashSet<>();

		Map<Integer, Set<Integer>> regulatorModuleMap = new HashMap<>();
		Map<Integer, Set<Integer>> adminModuleMap = new HashMap<>();
		Map<Integer, Set<Integer>> makerModuleMap = new HashMap<>();
		Map<Integer, Set<Integer>> checkerModuleMap = new HashMap<>();
		Map<Integer, Set<Integer>> approverModuleMap = new HashMap<>();
		Map<Integer, Set<Integer>> operatorModuleMap = new HashMap<>();

		Map<Integer, Set<String>> regulatorAccMap = new HashMap<>();
		Map<Integer, Set<String>> adminAccMap = new HashMap<>();
		Map<Integer, Set<String>> makerAccMap = new HashMap<>();
		Map<Integer, Set<String>> checkerAccMap = new HashMap<>();
		Map<Integer, Set<String>> approverAccMap = new HashMap<>();
		Map<Integer, Set<String>> operatorAccMap = new HashMap<>();

		Map<Integer, List<CorpUserEntity>> usersMap = new HashMap<>();
		Set<Integer> adminIdList = new HashSet<>();
		CorpUserEntity regulatorUser = new CorpUserEntity();
		Integer regulatorUserId = 0;
		Integer adminCount = 0;

		List<Integer> operatorModuleList = new ArrayList<>();
		List<String> operatorAccountList = new ArrayList<>();

		for (CorpUserEntity user : corpUsers) {
			if (user.getCorpRoleId().equals(BigDecimal.valueOf(1)) && roleIdList.contains(user.getCorpRoleId())) {
				regulatorUser = user;
			}
			if (user.getCorpRoleId().equals(BigDecimal.valueOf(2)) && roleIdList.contains(user.getCorpRoleId())) {
				adminCount++;
				adminIdList.add(user.getId().intValue());
				List<CorpUserEntity> corpUserEntityList = new ArrayList<>();
				if (adminType.equals("MA")) {
					corpUserEntityList.add(regulatorUser);
				}
				corpUserEntityList.add(user);
				usersMap.put(user.getId().intValue(), corpUserEntityList);
			}
			if (roleIdList.contains(user.getCorpRoleId()) && (user.getCorpRoleId().equals(BigDecimal.valueOf(3))
					|| user.getCorpRoleId().equals(BigDecimal.valueOf(4))
					|| user.getCorpRoleId().equals(BigDecimal.valueOf(5))
					|| user.getCorpRoleId().equals(BigDecimal.valueOf(6)))) {
				if (usersMap.containsKey(user.getParentId().intValue())) {
					List<CorpUserEntity> corpUserEntityList = usersMap.get(user.getParentId().intValue());
					corpUserEntityList.add(user);
					usersMap.put(user.getParentId().intValue(), corpUserEntityList);
				} else if (!ObjectUtils.isEmpty(user.getParentRoleId())
						&& BigDecimal.valueOf(2).equals(user.getParentRoleId())) {
					adminIdList.add(user.getParentId().intValue());
					List<CorpUserEntity> corpUserEntityList = new ArrayList<>();
					corpUserEntityList.add(user);
					usersMap.put(user.getParentId().intValue(), corpUserEntityList);
				}

			}

			if (roleIdList.contains(user.getCorpRoleId())) {
				for (CorpUserMenuMapEntity menu : corpUserMenuMap) {
					if (user.getId().equals(menu.getCorpUserId()) && user.getCorpRoleId().equals(BigDecimal.valueOf(1)))
						regulatorModuleSet.add(menu.getCorpMenuId().intValue());
					if (user.getId().equals(menu.getCorpUserId())
							&& user.getCorpRoleId().equals(BigDecimal.valueOf(2))) {
						Set<Integer> adminModuleSet = adminModuleMap.get(user.getId().intValue());
						if (ObjectUtils.isEmpty(adminModuleSet)) {
							adminModuleSet = new HashSet<>();
						}
						adminModuleSet.add(menu.getCorpMenuId().intValue());
						adminModuleMap.put(user.getId().intValue(), adminModuleSet);
					}
					if (user.getId().equals(menu.getCorpUserId())
							&& user.getCorpRoleId().equals(BigDecimal.valueOf(3))) {
						Set<Integer> makerModuleSet = makerModuleMap.get(user.getParentId().intValue());
						if (ObjectUtils.isEmpty(makerModuleSet)) {
							makerModuleSet = new HashSet<>();
						}
						makerModuleSet.add(menu.getCorpMenuId().intValue());
						makerModuleMap.put(user.getParentId().intValue(), makerModuleSet);
					}
					if (user.getId().equals(menu.getCorpUserId())
							&& user.getCorpRoleId().equals(BigDecimal.valueOf(4))) {
						Set<Integer> checkerModuleSet = checkerModuleMap.get(user.getParentId().intValue());
						if (ObjectUtils.isEmpty(checkerModuleSet)) {
							checkerModuleSet = new HashSet<>();
						}
						checkerModuleSet.add(menu.getCorpMenuId().intValue());
						checkerModuleMap.put(user.getParentId().intValue(), checkerModuleSet);
					}
					if (user.getId().equals(menu.getCorpUserId())
							&& user.getCorpRoleId().equals(BigDecimal.valueOf(5))) {
						Set<Integer> approverModuleSet = approverModuleMap.get(user.getParentId().intValue());
						if (ObjectUtils.isEmpty(approverModuleSet)) {
							approverModuleSet = new HashSet<>();
						}
						approverModuleSet.add(menu.getCorpMenuId().intValue());
						approverModuleMap.put(user.getParentId().intValue(), approverModuleSet);
					}
					if (user.getId().equals(menu.getCorpUserId())
							&& user.getCorpRoleId().equals(BigDecimal.valueOf(6))) {
						Set<Integer> operatorModuleSet = operatorModuleMap.get(user.getParentId().intValue());
						if (ObjectUtils.isEmpty(operatorModuleSet)) {
							operatorModuleSet = new HashSet<>();
						}
						operatorModuleSet.add(menu.getCorpMenuId().intValue());
						if (!userType.equals("S")) {
							operatorModuleMap.put(user.getParentId().intValue(), operatorModuleSet);
						} else {
							operatorModuleList.add(menu.getCorpMenuId().intValue());
						}

					}
				}

				for (CorpUserAccMapEntity acc : corpUserAccMap) {
					if (user.getId().equals(acc.getCorpUserId()) && user.getCorpRoleId().equals(BigDecimal.valueOf(1)))
						regulatorAccSet.add(EncryptorDecryptor.decryptData(acc.getAccountNo()));
					if (user.getId().equals(acc.getCorpUserId())
							&& user.getCorpRoleId().equals(BigDecimal.valueOf(2))) {
						Set<String> adminAccSet = adminAccMap.get(user.getId().intValue());
						if (ObjectUtils.isEmpty(adminAccSet)) {
							adminAccSet = new HashSet<>();
						}
						adminAccSet.add(EncryptorDecryptor.decryptData(acc.getAccountNo()));
						adminAccMap.put(user.getId().intValue(), adminAccSet);
					}

					if (user.getId().equals(acc.getCorpUserId())
							&& user.getCorpRoleId().equals(BigDecimal.valueOf(3))) {
						Set<String> makerAccSet = makerAccMap.get(user.getParentId().intValue());
						if (ObjectUtils.isEmpty(makerAccSet)) {
							makerAccSet = new HashSet<>();
						}
						makerAccSet.add(EncryptorDecryptor.decryptData(acc.getAccountNo()));
						makerAccMap.put(user.getParentId().intValue(), makerAccSet);
					}
					if (user.getId().equals(acc.getCorpUserId())
							&& user.getCorpRoleId().equals(BigDecimal.valueOf(4))) {
						Set<String> checkerAccSet = checkerAccMap.get(user.getParentId().intValue());
						if (ObjectUtils.isEmpty(checkerAccSet)) {
							checkerAccSet = new HashSet<>();
						}
						checkerAccSet.add(EncryptorDecryptor.decryptData(acc.getAccountNo()));
						checkerAccMap.put(user.getParentId().intValue(), checkerAccSet);
					}
					if (user.getId().equals(acc.getCorpUserId())
							&& user.getCorpRoleId().equals(BigDecimal.valueOf(5))) {
						Set<String> approverAccSet = approverAccMap.get(user.getParentId().intValue());
						if (ObjectUtils.isEmpty(approverAccSet)) {
							approverAccSet = new HashSet<>();
						}
						approverAccSet.add(EncryptorDecryptor.decryptData(acc.getAccountNo()));
						approverAccMap.put(user.getParentId().intValue(), approverAccSet);
					}
					if (user.getId().equals(acc.getCorpUserId())
							&& user.getCorpRoleId().equals(BigDecimal.valueOf(6))) {
						if (!userType.equals("S")) {
							Set<String> operatorAccSet = operatorAccMap.get(user.getParentId().intValue());
							if (ObjectUtils.isEmpty(operatorAccSet)) {
								operatorAccSet = new HashSet<>();
							}
							operatorAccSet.add(EncryptorDecryptor.decryptData(acc.getAccountNo()));
							operatorAccMap.put(user.getParentId().intValue(), operatorAccSet);
						} else {
							operatorAccountList.add(EncryptorDecryptor.decryptData(acc.getAccountNo()));
						}
					}
				}
			}
		}

		Iterator adminModuleIt = adminModuleMap.values().iterator();
		Iterator adminAccIt = adminAccMap.values().iterator();
		Set<Integer> adminModule = new HashSet<>();
		Set<String> adminAcc = new HashSet<>();
		while (adminModuleIt.hasNext()) {
			Set<Integer> module = (Set<Integer>) adminModuleIt.next();
			adminModule.addAll(module);
		}

		while (adminAccIt.hasNext()) {
			Set<String> acc = (Set<String>) adminAccIt.next();
			adminAcc.addAll(acc);
		}

		Integer regulatorId = 0;
		String regulatorUserName = "";
		Integer adminId = 0;
		String adminUserName = "";
		if (userType.equals("M") && adminType.equals("SA") || userType.equals("M") && adminType.equals("MA")) {
			if (adminType.equals("SA") && adminCount > 0 || adminType.equals("MA") && adminCount > 1) {
				for (Integer adminUserId : adminIdList) {
					boolean regulator = false;
					boolean admin = false;
					boolean maker = false;
					boolean checker = false;
					boolean approver = false;
					boolean operator = false;

					boolean regulatorTransactionRights = false;
					boolean adminTransactionRights = false;
					boolean makerTransactionRights = false;
					boolean checkerTransactionRights = false;
					boolean approverTransactionRights = false;
					boolean operatorTransactionRights = false;
					List<CorpUserEntity> corpUserEntities = usersMap.get(adminUserId);
					for (CorpUserEntity user : corpUserEntities) {
						if (user.getCorpRoleId().equals(BigDecimal.valueOf(1))) {
							regulatorId = user.getId().intValue();
							regulatorUserName = user.getUser_name();
							logger.info("RegulatorId: " + regulatorId + ", Regulator UserName: " + regulatorUserName);
							regulator = true;
							if (!user.getRights().equals("V")) {
								response.put("statusCode", 1);
								response.put("statusMsg", "Regulator should have only view rights");
								return response;
							}
							if (ObjectUtils.isEmpty(regulatorModuleSet) || !regulatorModuleSet.containsAll(moduleSet)) {
								logger.info("Regulator module partilally/not available");
								response.put("statusCode", 1);
								response.put("statusMsg",
										"All selected modules at corprorate level should be assigned between Regulator to proceed further");
								return response;
							}
							if (ObjectUtils.isEmpty(regulatorAccSet) || !regulatorAccSet.containsAll(accountSet)) {
								logger.info("Admin acc partilally/not available");
								response.put("statusCode", 1);
								response.put("statusMsg",
										"All selected accounts at corprorate level should be assigned between Regulator to proceed further");
								return response;
							}
						}
						if (user.getCorpRoleId().equals(BigDecimal.valueOf(2))) {
							if (adminType.equals("MA")) {
								if (regulatorId.equals(user.getParentId().intValue()) && regulatorUserName
										.equals(EncryptorDecryptor.encryptData(user.getParentUserName()))) {
									adminId = user.getId().intValue();
									adminUserName = user.getUser_name();
									logger.info("AdminId: " + adminId + ", Admin UserName: " + adminUserName);
									admin = true;
								}
							} else {
								adminId = user.getId().intValue();
								adminUserName = user.getUser_name();
								logger.info("AdminId: " + adminId + ", Admin UserName: " + adminUserName);
								admin = true;
							}

							if (!user.getRights().equals("V")) {
								response.put("statusCode", 1);
								response.put("statusMsg", "Admin should have only view rights");
								return response;
							}
							if (ObjectUtils.isEmpty(adminModuleMap.get(adminUserId))
									|| !adminModule.containsAll(moduleSet)) {
								logger.info("Admin module partilally/not available");
								response.put("statusCode", 1);
								response.put("statusMsg",
										"All selected modules at corprorate level should be assigned between admin to proceed further");
								return response;
							}
							if (ObjectUtils.isEmpty(adminAccMap.get(adminUserId))
									|| !adminAcc.containsAll(accountSet)) {
								logger.info("Admin acc partilally/not available");
								response.put("statusCode", 1);
								response.put("statusMsg",
										"All selected accounts at corprorate level should be assigned between admin to proceed further");
								return response;
							}
						}
						if (user.getCorpRoleId().equals(BigDecimal.valueOf(3))) {
							logger.info("RoleId: " + 3 + "parentId: " + user.getParentId().intValue()
									+ ", Parent UserName: " + EncryptorDecryptor.encryptData(user.getParentUserName()));
							if (adminId.equals(user.getParentId().intValue())
									&& adminUserName.equals(EncryptorDecryptor.encryptData(user.getParentUserName()))) {
								logger.info("Maker is available");
								maker = true;
								if (corpTranxLimit > 0 && user.getRights().equals("T")) {
									makerTransactionRights = true;
								}
								if (ObjectUtils.isEmpty(makerModuleMap.get(adminUserId)) || !makerModuleMap
										.get(adminUserId).containsAll(adminModuleMap.get(adminUserId))) {
									logger.info("Maker module partilally/not available");
									response.put("statusCode", 1);
									response.put("statusMsg",
											"All selected modules at corprorate level should be assigned between maker(s) to proceed further");
									return response;
								}
								if (ObjectUtils.isEmpty(makerAccMap.get(adminUserId))
										|| !makerAccMap.get(adminUserId).containsAll(adminAccMap.get(adminUserId))) {
									logger.info("Maker acc partilally/not available");
									response.put("statusCode", 1);
									response.put("statusMsg",
											"All selected accounts at corprorate level should be assigned between maker(s) to proceed further");
									return response;
								}
							}
						} else if (user.getCorpRoleId().equals(BigDecimal.valueOf(4))) {
							logger.info("RoleId: " + 4 + "parentId: " + user.getParentId().intValue()
									+ ", Parent UserName: " + EncryptorDecryptor.encryptData(user.getParentUserName()));
							if (adminId.equals(user.getParentId().intValue())
									&& adminUserName.equals(EncryptorDecryptor.encryptData(user.getParentUserName()))) {
								logger.info("Checker is available");
								checker = true;
								if (corpTranxLimit > 0 && user.getRights().equals("T")) {
									checkerTransactionRights = true;
								}
								if (ObjectUtils.isEmpty(checkerModuleMap.get(adminUserId)) || !checkerModuleMap
										.get(adminUserId).containsAll(adminModuleMap.get(adminUserId))) {
									logger.info("Checker module partilally/not available");
									response.put("statusCode", 1);
									response.put("statusMsg",
											"All selected modules at corprorate level should be assigned between checker(s) to proceed further");
									return response;
								}
								if (ObjectUtils.isEmpty(checkerAccMap.get(adminUserId))
										|| !checkerAccMap.get(adminUserId).containsAll(adminAccMap.get(adminUserId))) {
									logger.info("Checker acc partilally/not available");
									response.put("statusCode", 1);
									response.put("statusMsg",
											"All selected accounts at corprorate level should be assigned between checker(s) to proceed further");
									return response;
								}
							}

						} else if (user.getCorpRoleId().equals(BigDecimal.valueOf(5))) {
							logger.info("RoleId: " + 5 + "parentId: " + user.getParentId().intValue()
									+ ", Parent UserName: " + EncryptorDecryptor.encryptData(user.getParentUserName()));
							if (adminId.equals(user.getParentId().intValue())
									&& adminUserName.equals(EncryptorDecryptor.encryptData(user.getParentUserName()))) {
								logger.info("Approver is available");
								approver = true;
								if (corpTranxLimit > 0 && user.getRights().equals("T")) {
									approverTransactionRights = true;
								}
								if (ObjectUtils.isEmpty(approverModuleMap.get(adminUserId)) || !approverModuleMap
										.get(adminUserId).containsAll(adminModuleMap.get(adminUserId))) {
									logger.info("Approver module partilally/not available");
									response.put("statusCode", 1);
									response.put("statusMsg",
											"All selected modules at corprorate level should be assigned between approver(s) to proceed further");
									return response;
								}
								if (ObjectUtils.isEmpty(approverAccMap.get(adminUserId))
										|| !approverAccMap.get(adminUserId).containsAll(adminAccMap.get(adminUserId))) {
									logger.info("Approver acc partilally/not available");
									response.put("statusCode", 1);
									response.put("statusMsg",
											"All selected accounts at corprorate level should be assigned between approver(s) to proceed further");
									return response;
								}
							}

						} else if (user.getCorpRoleId().equals(BigDecimal.valueOf(6))) {
							logger.info("RoleId: " + 6 + "parentId: " + user.getParentId().intValue()
									+ ", Parent UserName: " + EncryptorDecryptor.encryptData(user.getParentUserName()));
							if (adminId.equals(user.getParentId().intValue())
									&& adminUserName.equals(EncryptorDecryptor.encryptData(user.getParentUserName()))) {
								logger.info("Operator is available");
								operator = true;
								if (corpTranxLimit > 0 && user.getRights().equals("T")) {
									operatorTransactionRights = true;
								}
//								Set operatorModule = operatorModuleMap.get(adminUserId);
//								Set adminModule = adminModuleMap.get(adminUserId);
								if (ObjectUtils.isEmpty(operatorModuleMap.get(adminUserId)) || !operatorModuleMap
										.get(adminUserId).containsAll(adminModuleMap.get(adminUserId))) {
//										|| !adminModule.containsAll(operatorModule)) {
									logger.info("Operator module partilally/not available");
									response.put("statusCode", 1);
									response.put("statusMsg",
											"All selected module at corprorate level should be assigned between operator(s) to proceed further");
									return response;
								}
//								Set operatorAcc = operatorAccMap.get(adminUserId);
//								Set adminAcc = adminAccMap.get(adminUserId);
								if (ObjectUtils.isEmpty(operatorAccMap.get(adminUserId))
										|| !operatorAccMap.get(adminUserId).containsAll(adminAccMap.get(adminUserId))) {
//										|| !adminAcc.containsAll(operatorAcc)) {
									logger.info("Operator account partilally/not available");
									response.put("statusCode", 1);
									response.put("statusMsg",
											"All selected accounts at corprorate level should be assigned between operator(s) to proceed further");
									return response;
								}
							}
						}
						if (corpTranxLimit > 0) {
							if ((corpTranxLimit != user.getTransMaxLimit().intValue())
									&& user.getTransMaxLimit().intValue() > corpTranxLimit) {
								response.put("statusCode", 1);
								response.put("statusMsg",
										"User transaction limit should not exceed to corporate limit");
								return response;
							}
						}
					}

					if (userLevel == 1) {
						if (!admin || !operator) {
							response.put("statusCode", 1);
							response.put("statusMsg",
									"Admin,Operator level1 hierarchy must be complete to proceed further");
							return response;
						}
						if (corpTranxLimit > 0) {
							if (!operatorTransactionRights) {
								response.put("statusCode", 1);
								response.put("statusMsg",
										"At least 1 operator should be assigned with transaction rights to proceed further");
								return response;
							}
						}
					} else if (userLevel == 2) {
						if (!admin || !maker || !checker) {
							response.put("statusCode", 1);
							response.put("statusMsg",
									"Admin,Maker,Checker level2 hierarchy must be complete to proceed further");
							return response;
						}
						if (corpTranxLimit > 0) {
							if (!makerTransactionRights || !checkerTransactionRights) {
								response.put("statusCode", 1);
								response.put("statusMsg", "Maker,Checker should have transaction rights");
								return response;
							}
						}

					} else if (userLevel == 3) {
						if (!admin || !maker || !checker || !approver) {
							response.put("statusCode", 1);
							response.put("statusMsg",
									"Admin,Maker,Checker,Approver level3 hierarchy must be complete to proceed further");
							return response;
						}
						if (corpTranxLimit > 0) {
							if (!makerTransactionRights || !checkerTransactionRights || !approverTransactionRights) {
								response.put("statusCode", 1);
								response.put("statusMsg",
										"At least 1 user of every role should be assigned with transaction rights to proceed further");
								return response;
							}
						}

					}
				}
			} else {
				if (adminType.equals("SA") && adminCount <= 0) {
					response.put("statusCode", 1);
					response.put("statusMsg",
							"Admin role users are required to proceed further with single admin hierarchy");
				}

				if (adminType.equals("MA") && adminCount <= 1) {
					response.put("statusCode", 1);
					response.put("statusMsg",
							"Mininum 2 admin role users are required to proceed further with multiadmin hierarchy");
				}
				return response;
			}
		} else if (userType.equals("S")) {
			boolean operatorTransactionRights = false;
			for (CorpUserEntity user : corpUsers) {
				if (corpTranxLimit > 0 && user.getRights().equals("T")) {
					operatorTransactionRights = true;
				}
				if (ObjectUtils.isEmpty(operatorModuleList) || !operatorModuleList.containsAll(moduleSet)) {
					response.put("statusCode", 1);
					response.put("statusMsg",
							"All selected modules at corprorate level should be assigned between admin to proceed further");
					return response;
				}
				if (ObjectUtils.isEmpty(operatorAccountList) || !operatorAccountList.containsAll(accountSet)) {
					response.put("statusCode", 1);
					response.put("statusMsg",
							"All selected accounts at corprorate level should be assigned between admin to proceed further");
					return response;
				}
			}
			if (corpTranxLimit > 0) {
				if (!operatorTransactionRights) {
					response.put("statusCode", 1);
					response.put("statusMsg",
							"At least 1 user of operator role should be assigned with transaction rights to proceed further");
					return response;
				}
			}

		}
		response.put("statusCode", 0);
		response.put("statusMsg", "Request Saved Successfully");
		logger.info("Validation end........");
		return response;
	}

	@Override
	public void updateCompanyDetails(CorpCompanyMasterEntity corpCompanyMasterEntity) {
		Session session = sessionFactory.getCurrentSession();
		corpCompanyMasterEntity.setStatusId(BigDecimal.valueOf(8));
		session.update(corpCompanyMasterEntity);
	}

	@Override
	public void rejectDupUser(CorpCompanyCorpUserDupBean corpCompanyCorpUserDupBean) {
		Session session = sessionFactory.getCurrentSession();

		CorpCompanyMasterDupEntity corpCompanyMasterDupEntityDb = corpCompanyMasterDupRepository.findByCifAndBranchCode(
				EncryptorDecryptor.encryptData(corpCompanyCorpUserDupBean.getCif()),
				corpCompanyCorpUserDupBean.getBranchCode());

		CorpUserDupEntity corpUserDupEntity = new CorpUserDupEntity();
		corpUserDupEntity.setId(corpCompanyCorpUserDupBean.getUserId());
		corpUserDupEntity.setCorp_comp_id(corpCompanyMasterDupEntityDb.getId());
		List<CorpUserDupEntity> corpUserDupEntities = getOfflineCorpUsersDupById(corpUserDupEntity);
		corpUserDupEntities.get(0).setRemark(corpCompanyCorpUserDupBean.getRemark());
		corpUserDupEntities.get(0).setCheckerId(corpCompanyCorpUserDupBean.getCheckerId());
		corpUserDupEntities.get(0).setMakerValidated(0);
		corpUserDupEntities.get(0).setUpdatedby(corpCompanyCorpUserDupBean.getUpdatedBy());
		corpUserDupEntities.get(0).setUpdatedOn(new Timestamp(System.currentTimeMillis()));
		session.update(corpUserDupEntities.get(0));

		corpCompanyMasterDupEntityDb.setCheckerId(corpCompanyCorpUserDupBean.getCheckerId());
		corpCompanyMasterDupEntityDb.setUpdatedBy(corpCompanyCorpUserDupBean.getUpdatedBy());
		corpCompanyMasterDupEntityDb.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
		corpCompanyMasterDupEntityDb.setMakerValidated(0);
		session.update(corpCompanyMasterDupEntityDb);
	}

	// TODO COMMENT CODE FOR UAT RELEASE
//	@Override
//	public void verifyCompanyAndUserOgstatus(CorpDataBean corpData) {
//		Session session = sessionFactory.getCurrentSession();
//		CorpCompanyMasterEntity corpCompanyMasterData = new CorpCompanyMasterEntity();
//		corpCompanyMasterData.setBranchCode(corpData.getBranchCode());
//		List<Integer> statusList = new ArrayList<>();
//		statusList.add(3);
//		statusList.add(36);
//		corpCompanyMasterData.setStatusList(statusList);
//		corpCompanyMasterData.setId(corpData.getCorpCompId());
//		List<CorpCompanyMasterEntity> companyData = getOfflineCorpCompDataByIdNew(corpCompanyMasterData);
//
//		if (!ObjectUtils.isEmpty(companyData)) {
//			Iterator corporateIterator = companyData.iterator();
//			while (corporateIterator.hasNext()) {
//				CorpCompanyMasterEntity corpCompanyMasterEntity = (CorpCompanyMasterEntity) corporateIterator.next();
//				if (ObjectUtils.isEmpty(corpCompanyMasterEntity.getOgstatus())) {
//					if (companyData.size() > 1) {
//						corpCompanyMasterEntity.setOgstatus(BigDecimal.valueOf(3));
//					} else {
//						corpCompanyMasterEntity.setOgstatus(BigDecimal.valueOf(0));
//					}
//					session.update(corpCompanyMasterEntity);
//				}
//
//				List<CorpUserEntity> corpUserMasterEntityList = getAllCorpUsersByCompanyId(
//						corpCompanyMasterEntity.getId());
//				Iterator userIterator = corpUserMasterEntityList.iterator();
//				while (userIterator.hasNext()) {
//					CorpUserEntity corpUserMasterEntity = (CorpUserEntity) userIterator.next();
//					if (ObjectUtils.isEmpty(corpUserMasterEntity.getOgstatus())) {
//						if (companyData.size() > 1) {
//							
//							
//							
//							corpUserMasterEntity.setOgstatus(BigDecimal.valueOf(3));
//						} else {
//							corpUserMasterEntity.setOgstatus(BigDecimal.valueOf(0));
//						}
//						session.update(corpUserMasterEntity);
//					}
//				}
//			}
//
//		}
//
//	}

}