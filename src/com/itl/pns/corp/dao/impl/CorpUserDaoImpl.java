package com.itl.pns.corp.dao.impl;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.itl.pns.bean.CorpDataBean;
import com.itl.pns.bean.CorpMenuAccBean;
import com.itl.pns.bean.CorpMenuAccountBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.corp.dao.CorpUserDao;
import com.itl.pns.corp.dao.OfflineCorpUserDao;
import com.itl.pns.corp.entity.CorpAccMapEntity;
import com.itl.pns.corp.entity.CorpAccMapEntityTmp;
import com.itl.pns.corp.entity.CorpAccReqEntity;
import com.itl.pns.corp.entity.CorpCompDataMasterEntity;
import com.itl.pns.corp.entity.CorpCompReqEntity;
import com.itl.pns.corp.entity.CorpCompanyMasterEntity;
import com.itl.pns.corp.entity.CorpMenuMapEntity;
import com.itl.pns.corp.entity.CorpMenuMapEntityTmp;
import com.itl.pns.corp.entity.CorpMenuReqEntity;
import com.itl.pns.corp.entity.CorpUserAccMapEntity;
import com.itl.pns.corp.entity.CorpUserAccReqEntity;
import com.itl.pns.corp.entity.CorpUserEntity;
import com.itl.pns.corp.entity.CorpUserMenuMapEntity;
import com.itl.pns.corp.entity.CorpUserMenuReqEntity;
import com.itl.pns.corp.entity.CorpUserReqEntity;
import com.itl.pns.dao.MasterConfigDao;
import com.itl.pns.entity.ConfigMasterEntity;
import com.itl.pns.util.AdminWorkFlowReqUtility;
import com.itl.pns.util.EmailUtil;
import com.itl.pns.util.EncryptorDecryptor;
import com.itl.pns.util.PdfGenerator;
import com.itl.pns.util.RandomNumberGenerator;

@Repository
@Transactional
public class CorpUserDaoImpl implements CorpUserDao {

	static Logger logger = Logger.getLogger(CorpUserDaoImpl.class);

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	@Autowired
	private PdfGenerator pdfGenerator;

	@Autowired
	AdminWorkFlowReqUtility adminWorkFlowReqUtility;

	@Autowired
	EmailUtil emailUtil;

	@Autowired
	MasterConfigDao masterConfigDao;

	@Autowired
	OfflineCorpUserDao offlineCorpUserDao;

	@Value("${PDF_FILE_PATH}")
	private String pdfFilePath;

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public List<CorpCompReqEntity> getCorpCompRequests() {
		List<CorpCompReqEntity> list = null;
		try {

			String sqlQuery = "select cc.id ,cc.companyName as companyName,cc.COMPANYINFO as companyInfo,cc.rrn as rrn, "
					+ "cc.establishmentOn as establishmentOn,cc.pancardNo as pancardNo,cc.phoneNo as phoneNo,"
					+ "	cc.logo as logo,cc.coi as coi, cc.moa as moa,cc.otherDoc as otherDoc,cc.corporateType as corporateType,"
					+ "	cc.statusId as statusId,cc.createdOn as createdOn , cc.updatedOn as updatedOn, cc.updatedBy as updatedBy,"
					+ "	cc.cif as cif,cc.companyCode as companyCode from CORP_COMP_REQ cc where cc.statusid=15 ";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id", StandardBasicTypes.BIG_INTEGER)
					.addScalar("companyName", StandardBasicTypes.STRING)
					.addScalar("companyInfo", StandardBasicTypes.STRING).addScalar("rrn", StandardBasicTypes.STRING)
					.addScalar("establishmentOn", StandardBasicTypes.DATE)
					.addScalar("pancardNo", StandardBasicTypes.STRING).addScalar("phoneNo", StandardBasicTypes.STRING)
					.addScalar("logo", StandardBasicTypes.STRING).addScalar("coi", StandardBasicTypes.STRING)
					.addScalar("moa", StandardBasicTypes.STRING).addScalar("otherDoc", StandardBasicTypes.STRING)
					.addScalar("corporateType", StandardBasicTypes.STRING)
					.addScalar("statusId", StandardBasicTypes.BIG_INTEGER)
					.addScalar("createdOn", StandardBasicTypes.DATE).addScalar("updatedOn", StandardBasicTypes.DATE)
					.addScalar("updatedBy", StandardBasicTypes.BIG_INTEGER).addScalar("cif", StandardBasicTypes.STRING)
					.addScalar("companyCode", StandardBasicTypes.STRING)
					.setResultTransformer(Transformers.aliasToBean(CorpCompReqEntity.class)).list();
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return list;
	}

	@Override
	public List<CorpCompReqEntity> getCorpCompRequestsById(CorpCompReqEntity corpCompReqEntity) {
		List<CorpCompReqEntity> list = null;
		try {

			String sqlQuery = "select cc.id ,cc.companyName as companyName,cc.COMPANYINFO as companyInfo,cc.rrn as rrn,"
					+ " cc.establishmentOn as establishmentOn,cc.pancardNo as pancardNo,cc.phoneNo as phoneNo,"
					+ "	cc.logo as logo,cc.coi as coi, cc.moa as moa,cc.otherDoc as otherDoc,cc.corporateType as corporateType,"
					+ "	cc.statusId as statusId,cc.createdOn as createdOn , cc.updatedOn as updatedOn, cc.updatedBy as updatedBy,"
					+ "	cc.cif as cif,cc.companyCode as companyCode from CORP_COMP_REQ cc	where cc.id=:id";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id", StandardBasicTypes.BIG_INTEGER)
					.addScalar("companyName", StandardBasicTypes.STRING)
					.addScalar("companyInfo", StandardBasicTypes.STRING).addScalar("rrn", StandardBasicTypes.STRING)
					.addScalar("establishmentOn", StandardBasicTypes.DATE)
					.addScalar("pancardNo", StandardBasicTypes.STRING).addScalar("phoneNo", StandardBasicTypes.STRING)
					.addScalar("logo", StandardBasicTypes.STRING).addScalar("coi", StandardBasicTypes.STRING)
					.addScalar("moa", StandardBasicTypes.STRING).addScalar("otherDoc", StandardBasicTypes.STRING)
					.addScalar("corporateType", StandardBasicTypes.STRING)
					.addScalar("statusId", StandardBasicTypes.BIG_INTEGER)
					.addScalar("createdOn", StandardBasicTypes.DATE).addScalar("updatedOn", StandardBasicTypes.DATE)
					.addScalar("updatedBy", StandardBasicTypes.BIG_INTEGER).addScalar("cif", StandardBasicTypes.STRING)
					.addScalar("companyCode", StandardBasicTypes.STRING).setParameter("id", corpCompReqEntity.getId())
					.setResultTransformer(Transformers.aliasToBean(CorpCompReqEntity.class)).list();
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return list;
	}

	@Override
	public List<CorpCompReqEntity> getCorpCompRequestsByRrn(CorpCompReqEntity corpCompReqEntity) {
		List<CorpCompReqEntity> list = null;
		try {

			String sqlQuery = "select cc.id ,cc.companyName as companyName,cc.COMPANYINFO as companyInfo,cc.rrn as rrn, cc.establishmentOn as establishmentOn,cc.pancardNo as pancardNo,cc.phoneNo as phoneNo,"
					+ "	cc.logo as logo,cc.coi as coi, cc.moa as moa,cc.otherDoc as otherDoc,cc.corporateType as corporateType,"
					+ "	cc.statusId as statusId,cc.createdOn as createdOn , cc.updatedOn as updatedOn, cc.updatedBy as updatedBy,"
					+ "	cc.cif as cif,cc.companyCode as companyCode from CORP_COMP_REQ cc	where cc.rrn=:rrn";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id", StandardBasicTypes.BIG_INTEGER)
					.addScalar("companyName", StandardBasicTypes.STRING)
					.addScalar("companyInfo", StandardBasicTypes.STRING).addScalar("rrn", StandardBasicTypes.STRING)
					.addScalar("establishmentOn", StandardBasicTypes.DATE)
					.addScalar("pancardNo", StandardBasicTypes.STRING).addScalar("phoneNo", StandardBasicTypes.STRING)
					.addScalar("logo", StandardBasicTypes.STRING).addScalar("coi", StandardBasicTypes.STRING)
					.addScalar("moa", StandardBasicTypes.STRING).addScalar("otherDoc", StandardBasicTypes.STRING)
					.addScalar("corporateType", StandardBasicTypes.STRING)
					.addScalar("statusId", StandardBasicTypes.BIG_INTEGER)
					.addScalar("createdOn", StandardBasicTypes.DATE).addScalar("updatedOn", StandardBasicTypes.DATE)
					.addScalar("updatedBy", StandardBasicTypes.BIG_INTEGER).addScalar("cif", StandardBasicTypes.STRING)
					.addScalar("companyCode", StandardBasicTypes.STRING).setParameter("rrn", corpCompReqEntity.getRrn())
					.setResultTransformer(Transformers.aliasToBean(CorpCompReqEntity.class)).list();
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return list;
	}

	@Override
	public boolean updateCorpCompReqData(CorpCompDataMasterEntity compData) {
		Session session = sessionFactory.getCurrentSession();
		Date date = new Date();
		try {
			if (!ObjectUtils.isEmpty(compData)) {
				compData.setCreatedOn(date);
				compData.setUpdatedOn(date);
				BigInteger compId = (BigInteger) session.save(compData);
				System.out.println("Id of save object =" + compId);

			}
		} catch (Exception e) {
			logger.info("Exception:", e);
			return false;
		}

		return true;

	}

	@Override
	public List<CorpMenuReqEntity> getCorpMenuByCompanyId(CorpMenuReqEntity CorpMenuData) {
		List<CorpMenuReqEntity> list = null;
		try {

			String sqlQuery = "	select cm.id as id ,cm.corpReqId as corpReqId, cm.Menureqid as menuReqId, cm.StatusId as statusId, cm.Createdon as createdon,"
					+ "	cm.Updatedon as updatedon,cm.UPDATEDBY as updatedby , cmm.menuname as menuName from CORP_MENU_REQ cm "
					+ "	inner join corp_menu cmm on cmm.id =cm.menuReqId  where cm.corpReqId=:corpId";

			list = getSession().createSQLQuery(sqlQuery).addScalar("corpReqId", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("id", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("menuReqId", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("statusId", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("createdon", StandardBasicTypes.DATE).addScalar("updatedon", StandardBasicTypes.DATE)
					.addScalar("updatedby", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("menuName", StandardBasicTypes.STRING)
					.setParameter("corpId", CorpMenuData.getCorpReqId())
					.setResultTransformer(Transformers.aliasToBean(CorpMenuReqEntity.class)).list();
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return list;
	}

	@Override
	public List<CorpAccReqEntity> getCorpAccByCompanyId(CorpAccReqEntity corpAccData) {
		List<CorpAccReqEntity> list = null;
		try {

			String sqlQuery = "	select cm.id as id ,cm.corpReqId as corpReqId, cm.Accountno as accountNo, cm.StatusId as statusId, cm.Createdon as createdon,"
					+ "	cm.Updatedon as updatedon,cm.UPDATEDBY as updatedby  from CORP_ACC_REQ cm where cm.corpReqId=:corpId ";

			list = getSession().createSQLQuery(sqlQuery).addScalar("corpReqId").addScalar("id").addScalar("accountNo")
					.addScalar("statusId").addScalar("createdon").addScalar("updatedon").addScalar("updatedby")
					.setParameter("corpId", corpAccData.getCorpReqId())
					.setResultTransformer(Transformers.aliasToBean(CorpAccReqEntity.class)).list();

			for (CorpAccReqEntity accData : list) {

				accData.setAccountNo(EncryptorDecryptor.decryptData(accData.getAccountNo()));

			}

		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return list;
	}

	@Override
	public boolean saveCorpMenuAccData(CorpMenuAccountBean menuAccData) {
		try {
			if (checkIsAccExistWithCBS(menuAccData)) {
				saveToCorpMenuMap(menuAccData);
				saveToCorpAccMap(menuAccData);
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return false;
	}

	public boolean checkIsAccExistWithCBS(CorpMenuAccountBean menuAccData) {

		if (menuAccData.getAccountNo() != null) {
			return true;
		} else {
			return false;
		}

	}

	public boolean saveToCorpMenuMap(CorpMenuAccountBean menuAccData) {
		Session session = sessionFactory.getCurrentSession();
		Date date = new Date();
		try {
			CorpMenuMapEntity menuObj = new CorpMenuMapEntity();
			menuObj.setCorpId(menuAccData.getCorpReqId());
			menuObj.setCorpMenuId(menuAccData.getMenuReqId());
			menuObj.setStatusId(menuAccData.getStatusId());
			menuObj.setCreatedon(date);
			menuObj.setUpdatedon(date);
			menuObj.setUpdatedby(menuAccData.getUpdatedby());
			session.save(menuObj);

		} catch (Exception e) {
			logger.info("Exception:", e);
			return false;
		}
		return true;
	}

	public boolean saveToCorpAccMap(CorpMenuAccountBean menuAccData) {
		Session session = sessionFactory.getCurrentSession();
		Date date = new Date();
		try {
			CorpAccMapEntity accObj = new CorpAccMapEntity();

			accObj.setCorpId(menuAccData.getCorpReqId());
			accObj.setAccountNo(menuAccData.getAccountNo());
			accObj.setStatusId(menuAccData.getStatusId());
			accObj.setCreatedon(date);
			accObj.setUpdatedon(date);
			accObj.setUpdatedby(menuAccData.getUpdatedby());
			session.save(accObj);

		} catch (Exception e) {
			logger.info("Exception:", e);
			return false;
		}
		return true;
	}

	@Override
	public boolean verifyAccountNumber(CorpMenuAccBean corpMenuAccData) {
		boolean isAccExist = false;
		try {
			for (CorpAccReqEntity accObj : corpMenuAccData.getCorpAccList()) {

				if (accObj.getAccountNo() != null) {
					isAccExist = true;
				} else {
					isAccExist = false;
					break;

				}

			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return isAccExist;
	}

	@Override
	public boolean insertToCorpMenuMap(CorpMenuReqEntity menuData) {
		Session session = sessionFactory.getCurrentSession();
		Date date = new Date();
		try {
			CorpMenuMapEntity menuObj = new CorpMenuMapEntity();
			menuObj.setCorpId(menuData.getCorpReqId());
			menuObj.setCorpMenuId(menuData.getMenuReqId());
			menuObj.setStatusId(menuData.getStatusId());
			menuObj.setCreatedon(date);
			menuObj.setUpdatedon(date);
			menuObj.setUpdatedby(menuData.getUpdatedby());
			session.save(menuObj);

		} catch (Exception e) {
			logger.info("Exception:", e);
			return false;
		}
		return true;
	}

	@Override
	public boolean insertToCorpAccMap(CorpAccReqEntity accData) {
		Session session = sessionFactory.getCurrentSession();
		Date date = new Date();
		try {
			CorpAccMapEntity accObj = new CorpAccMapEntity();

			accObj.setCorpId(accData.getCorpReqId());
			accObj.setAccountNo(accData.getAccountNo());
			accObj.setStatusId(accData.getStatusId());
			accObj.setCreatedon(date);
			accObj.setUpdatedon(date);
			accObj.setUpdatedby(accData.getUpdatedby());
			session.save(accObj);

		} catch (Exception e) {
			logger.info("Exception:", e);
			return false;
		}
		return true;
	}

	@Override
	public List<CorpUserReqEntity> getAllCorpUsersByCompId(CorpUserReqEntity corpuserReqData) {
		List<CorpUserReqEntity> list = null;
		try {

			String sqlQuery = "select cu.id,cu.corpid as corpid, cu.userName as userName,cu.firstName as firstName,cu.lastName as lastName,cu.email as email,cu.TOKEN as token,"
					+ "	cu.mobile as mobile, cu.dob as dob,cu.pancardNo as pancardNo, cu.rrn as rrn,cu.corpRoleId as corpRoleId, cu.aadharCard as aadharCard,ccr.companyname as companyName,"
					+ "	cu.passport as passport, cu.passportNo as passportNo, cu.boardResolution as boardResolution, cu.userImage as userImage, cu.aadharCardNo as aadharCardNo,cu.parentUserName as parentUserName,"
					+ "	cu.otherDoc as otherDoc,cu.certificateIncorporation as certificateIncorporation, cu.designation as designation,cu.parentRrn as parentRrn, cu.authType as authType,"
					+ "	cu.statusId as statusId, cu.createdon as createdon, cu.updatedon as updatedon,cu.updatedby as updatedby,cr.name as corpRoleName, cc.name as parentRoleName, cu.parentRoleId as parentRoleId from CORP_USER_REQ  cu  "
					+ " left join corp_roles cr on cr.id=cu.corpRoleId    left join corp_roles cc on cc.id=cu.parentRoleId left join CORP_COMP_REQ ccr on ccr.id=cu.corpid where cu.corpid=:compId	";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("corpid").addScalar("userName")
					.addScalar("firstName").addScalar("lastName").addScalar("email").addScalar("token")
					.addScalar("mobile").addScalar("dob").addScalar("pancardNo").addScalar("rrn")
					.addScalar("corpRoleId").addScalar("aadharCard").addScalar("companyName").addScalar("passport")
					.addScalar("passportNo").addScalar("boardResolution").addScalar("userImage")
					.addScalar("aadharCardNo").addScalar("parentUserName").addScalar("otherDoc")
					.addScalar("certificateIncorporation").addScalar("designation").addScalar("parentRrn")
					.addScalar("authType").addScalar("statusId").addScalar("createdon").addScalar("updatedon")
					.addScalar("updatedby").addScalar("corpRoleName").addScalar("parentRoleName")
					.addScalar("parentRoleId").setParameter("compId", corpuserReqData.getCorpid())
					.setResultTransformer(Transformers.aliasToBean(CorpUserReqEntity.class)).list();

			for (CorpUserReqEntity corpData : list) {
				corpData.setMobile(EncryptorDecryptor.decryptData(corpData.getMobile()));
				corpData.setUserName(EncryptorDecryptor.decryptData(corpData.getUserName()));
				corpData.setEmail(EncryptorDecryptor.decryptData(corpData.getEmail()));
				corpData.setDob(EncryptorDecryptor.decryptData(corpData.getDob()));
				corpData.setPancardNo(EncryptorDecryptor.decryptData(corpData.getPancardNo()));
				corpData.setPassportNo(EncryptorDecryptor.decryptData(corpData.getPassportNo()));

			}

		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return list;
	}

	@Override
	public List<CorpUserMenuReqEntity> getMenuListByCorpUserId(CorpUserMenuReqEntity corpUserMenuData) {
		List<CorpUserMenuReqEntity> list = null;
		try {

			String sqlQuery = "select cm.id as id, cm.corpCompId as corpCompId,cm.menuReqId as menuReqId, cm.Userreqid as UserReqId,"
					+ "cm.statusId as statusId, cc.menuName as menuName ,cm.userRrn as userRrn from CORPUSER_MENU_REQ cm inner join corp_menu cc on cc.id =cm.menuReqId"
					+ "    where cm.Userreqid =:userId";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("corpCompId").addScalar("menuReqId")
					.addScalar("UserReqId").addScalar("statusId").addScalar("menuName").addScalar("userRrn")
					.setParameter("userId", corpUserMenuData.getUserReqId())
					.setResultTransformer(Transformers.aliasToBean(CorpUserMenuReqEntity.class)).list();
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return list;
	}

	@Override
	public List<CorpUserAccReqEntity> getAccountListByCorpUserId(CorpUserAccReqEntity corpUserAccData) {
		List<CorpUserAccReqEntity> list = null;
		try {

			String sqlQuery = "select ca.id as id, ca.corpCompId as corpCompId, ca.accountNo as accountNo,ca.userReqId as userReqId, ca.statusId as statusId, "
					+ " ca.userRrn as userRrn from CORPUSER_ACC_REQ ca where ca.userReqId =:userId";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("corpCompId").addScalar("accountNo")
					.addScalar("UserReqId").addScalar("statusId").addScalar("userRrn")
					.setParameter("userId", corpUserAccData.getUserReqId())
					.setResultTransformer(Transformers.aliasToBean(CorpUserAccReqEntity.class)).list();

			for (CorpUserAccReqEntity accData : list) {
				if (accData.getAccountNo().contains("=")) {
					accData.setAccountNo(EncryptorDecryptor.decryptData(accData.getAccountNo()));
				}
			}

		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return list;
	}

	@Override
	public List<CorpUserMenuReqEntity> getMenuListByCorpCompId(BigInteger companyId) {
		List<CorpUserMenuReqEntity> list = null;
		try {

			String sqlQuery = "select cm.id as id, cm.corpCompId as corpCompId,cm.menuReqId as menuReqId, cm.Userreqid as UserReqId,"
					+ "cm.statusId as statusId, cc.menuName as menuName, cm.userRrn as userRrn from CORPUSER_MENU_REQ cm "
					+ "inner join corp_menu cc on cc.id =cm.menuReqId" + "    where cm.Corpcompid =:compId";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("corpCompId").addScalar("menuReqId")
					.addScalar("UserReqId").addScalar("statusId").addScalar("menuName").addScalar("userRrn")
					.setParameter("compId", companyId)
					.setResultTransformer(Transformers.aliasToBean(CorpUserMenuReqEntity.class)).list();
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return list;
	}

	@Override
	public List<CorpUserAccReqEntity> getAccountListByCorpCompId(BigInteger companyId) {
		List<CorpUserAccReqEntity> list = null;
		try {

			String sqlQuery = "select ca.id as id, ca.corpCompId as corpCompId, ca.accountNo as accountNo,ca.userReqId as userReqId, ca.statusId as statusId, ca.userRrn as userRrn "
					+ "from CORPUSER_ACC_REQ ca where ca.Corpcompid =:compId";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("corpCompId").addScalar("accountNo")
					.addScalar("UserReqId").addScalar("statusId").addScalar("userRrn").setParameter("compId", companyId)
					.setResultTransformer(Transformers.aliasToBean(CorpUserAccReqEntity.class)).list();
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return list;
	}

	@Override
	public List<CorpCompReqEntity> getCorpByCompNameCifCorpId(CorpCompReqEntity corpCompReqEntity) {
		List<CorpCompReqEntity> list = null;
		try {

			String sqlQuery = "select cc.id ,cc.companyName as companyName,cc.COMPANYINFO as companyInfo,cc.rrn as rrn, cc.establishmentOn as establishmentOn,cc.pancardNo as pancardNo,cc.phoneNo as phoneNo,"
					+ "	cc.logo as logo,cc.coi as coi, cc.moa as moa,cc.otherDoc as otherDoc,cc.corporateType as corporateType,"
					+ "	cc.statusId as statusId,cc.createdOn as createdOn , cc.updatedOn as updatedOn, cc.updatedBy as updatedBy,"
					+ "	cc.cif as cif,cc.companyCode as companyCode from CORP_COMP_REQ cc	where ";

			if (corpCompReqEntity.getCif() != null) {
				sqlQuery = sqlQuery.concat(" " + "cc.cif=:cif");
				list = getSession().createSQLQuery(sqlQuery).addScalar("id", StandardBasicTypes.BIG_INTEGER)
						.addScalar("companyName", StandardBasicTypes.STRING)
						.addScalar("companyInfo", StandardBasicTypes.STRING).addScalar("rrn", StandardBasicTypes.STRING)
						.addScalar("establishmentOn", StandardBasicTypes.DATE)
						.addScalar("pancardNo", StandardBasicTypes.STRING)
						.addScalar("phoneNo", StandardBasicTypes.STRING).addScalar("logo", StandardBasicTypes.STRING)
						.addScalar("coi", StandardBasicTypes.STRING).addScalar("moa", StandardBasicTypes.STRING)
						.addScalar("otherDoc", StandardBasicTypes.STRING)
						.addScalar("corporateType", StandardBasicTypes.STRING)
						.addScalar("statusId", StandardBasicTypes.BIG_INTEGER)
						.addScalar("createdOn", StandardBasicTypes.DATE).addScalar("updatedOn", StandardBasicTypes.DATE)
						.addScalar("updatedBy", StandardBasicTypes.BIG_INTEGER)
						.addScalar("cif", StandardBasicTypes.STRING).addScalar("companyCode", StandardBasicTypes.STRING)
						.setParameter("cif", corpCompReqEntity.getCif())
						.setResultTransformer(Transformers.aliasToBean(CorpCompReqEntity.class)).list();

			} else if (corpCompReqEntity.getCompanyCode() != null) {
				sqlQuery = sqlQuery.concat(" " + "cc.companyCode=:compCode");
				list = getSession().createSQLQuery(sqlQuery).addScalar("id", StandardBasicTypes.BIG_INTEGER)
						.addScalar("companyName", StandardBasicTypes.STRING)
						.addScalar("companyInfo", StandardBasicTypes.STRING).addScalar("rrn", StandardBasicTypes.STRING)
						.addScalar("establishmentOn", StandardBasicTypes.DATE)
						.addScalar("pancardNo", StandardBasicTypes.STRING)
						.addScalar("phoneNo", StandardBasicTypes.STRING).addScalar("logo", StandardBasicTypes.STRING)
						.addScalar("coi", StandardBasicTypes.STRING).addScalar("moa", StandardBasicTypes.STRING)
						.addScalar("otherDoc", StandardBasicTypes.STRING)
						.addScalar("corporateType", StandardBasicTypes.STRING)
						.addScalar("statusId", StandardBasicTypes.BIG_INTEGER)
						.addScalar("createdOn", StandardBasicTypes.DATE).addScalar("updatedOn", StandardBasicTypes.DATE)
						.addScalar("updatedBy", StandardBasicTypes.BIG_INTEGER)
						.addScalar("cif", StandardBasicTypes.STRING).addScalar("companyCode", StandardBasicTypes.STRING)
						.setParameter("compCode", corpCompReqEntity.getCompanyCode())
						.setResultTransformer(Transformers.aliasToBean(CorpCompReqEntity.class)).list();
			} else if (corpCompReqEntity.getCompanyName() != null) {
				sqlQuery = sqlQuery.concat(" " + "cc.companyName LIKE('%" + corpCompReqEntity.getCompanyName() + "%')");
				list = getSession().createSQLQuery(sqlQuery).addScalar("id", StandardBasicTypes.BIG_INTEGER)
						.addScalar("companyName", StandardBasicTypes.STRING)
						.addScalar("companyInfo", StandardBasicTypes.STRING).addScalar("rrn", StandardBasicTypes.STRING)
						.addScalar("establishmentOn", StandardBasicTypes.DATE)
						.addScalar("pancardNo", StandardBasicTypes.STRING)
						.addScalar("phoneNo", StandardBasicTypes.STRING).addScalar("logo", StandardBasicTypes.STRING)
						.addScalar("coi", StandardBasicTypes.STRING).addScalar("moa", StandardBasicTypes.STRING)
						.addScalar("otherDoc", StandardBasicTypes.STRING)
						.addScalar("corporateType", StandardBasicTypes.STRING)
						.addScalar("statusId", StandardBasicTypes.BIG_INTEGER)
						.addScalar("createdOn", StandardBasicTypes.DATE).addScalar("updatedOn", StandardBasicTypes.DATE)
						.addScalar("updatedBy", StandardBasicTypes.BIG_INTEGER)
						.addScalar("cif", StandardBasicTypes.STRING).addScalar("companyCode", StandardBasicTypes.STRING)
						.setResultTransformer(Transformers.aliasToBean(CorpCompReqEntity.class)).list();
			}

		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return list;
	}

	@Override
	public boolean saveAllCorpData(CorpDataBean corpData) {

		try {
			/*
			 * saveToCorpCompMasterData(corpData); saveToCorpMenuMap(corpData);
			 * saveToCorpAccMap(corpData); saveCorpUserMasterData(corpData);
			 * saveToCorpUsersMenuMap(corpData); saveToCorpUsersAccMap(corpData);
			 * updateStatusOfCorpRequestData(corpData);
			 */

		} catch (Exception e) {
			logger.info("Exception:", e);
			return false;
		}

		return true;
	}

	@Override
	public boolean saveToCorpCompMasterData(CorpDataBean corpData) {
		Session session = sessionFactory.getCurrentSession();
		Date date = new Date();
		CorpCompDataMasterEntity corpCompMasterObj = corpData.getCorpCompData();
		try {
			if (!ObjectUtils.isEmpty(corpCompMasterObj)) {

				corpCompMasterObj.setCreatedOn(date);
				corpCompMasterObj.setUpdatedOn(date);
				corpCompMasterObj.setStatusId(BigDecimal.valueOf(3));
				BigDecimal compId = (BigDecimal) session.save(corpData.getCorpCompData());
				System.out.println("Id of save object =" + compId);
				corpData.setCorpCompId(compId);

			}
		} catch (Exception e) {
			logger.info("Exception:", e);
			return false;
		}

		return true;

	}

	@Override
	public boolean saveToCorpMenuMap(CorpDataBean corpData) {
		Session session = sessionFactory.getCurrentSession();
		Date date = new Date();
		List<CorpMenuMapEntity> corpMenulist = getOfflineCorpMenuByCompanyId(corpData.getCorpCompId().toBigInteger());
		if (!ObjectUtils.isEmpty(corpMenulist)) {
			for (CorpMenuMapEntity menuObj : corpMenulist) {
				deleteFromMenuMapMenuById(menuObj.getId().toBigInteger());
			}
		}
		try {
			for (CorpMenuReqEntity corpMenu : corpData.getCorpMenuAccData().getCorpMenuList()) {
				if(!ObjectUtils.isEmpty(corpMenu.getSubMenuReqIdArray())) {
					for(int i=0;i<corpMenu.getSubMenuReqIdArray().length;i++) {
						CorpMenuMapEntity menuObj = new CorpMenuMapEntity();
						menuObj.setCorpId(corpData.getCorpCompId());
						menuObj.setCorpMenuId(corpMenu.getMenuReqId());
						menuObj.setCorpSubMenuId(corpMenu.getSubMenuReqIdArray()[i]);
						menuObj.setStatusId(new BigDecimal(3));
						menuObj.setCreatedon(date);
						menuObj.setUpdatedby(corpData.getCreatedByUpdatedBy());
						session.save(menuObj);
					}
				}else {
					CorpMenuMapEntity menuObj = new CorpMenuMapEntity();
					menuObj.setCorpId(corpData.getCorpCompId());
					menuObj.setCorpMenuId(corpMenu.getMenuReqId());
//					menuObj.setCorpSubMenuId(corpMenu.getSubMenuReqIdArray()[i]);
					menuObj.setStatusId(new BigDecimal(3));
					menuObj.setCreatedon(date);
					menuObj.setUpdatedby(corpData.getCreatedByUpdatedBy());
					session.save(menuObj);
				}
				
			}

		} catch (Exception e) {
			logger.info("Exception:", e);
			return false;
		}
		return true;
	}

//	@Override
//	public boolean saveToCorpMenuMapTmp(CorpDataBeanTmp corpData) {
//		Session session = sessionFactory.getCurrentSession();
//		Date date = new Date();
//		List<CorpMenuMapEntityTmp> corpMenulist = getOfflineCorpMenuByCompanyIdTmp(
//				corpData.getCorpCompId().toBigInteger());
//		if (!ObjectUtils.isEmpty(corpMenulist)) {
//			for (CorpMenuMapEntityTmp menuObj : corpMenulist) {
//				deleteFromMenuMapMenuByIdTmp(menuObj.getId().toBigInteger());
//			}
//		}
//		try {
//			for (CorpMenuReqEntityTmp corpMenu : corpData.getCorpMenuAccData().getCorpMenuList()) {
//				CorpMenuMapEntityTmp menuObj = new CorpMenuMapEntityTmp();
//				menuObj.setCorpId(corpData.getCorpCompId());
//				menuObj.setCorpMenuId(corpMenu.getMenuReqId());
//				menuObj.setStatusId(corpMenu.getStatusId());
//				menuObj.setCreatedon(date);
//				menuObj.setStatusId(BigDecimal.valueOf(3));
//				menuObj.setUpdatedby(corpData.getCreatedByUpdatedBy());
//				session.save(menuObj);
//			}
//
//		} catch (Exception e) {
//			logger.info("Exception:", e);
//			return false;
//		}
//		return true;
//	}

	@Override
	public boolean saveToCorpAccMap(CorpDataBean corpData) {
		Session session = sessionFactory.getCurrentSession();
		Date date = new Date();

		List<CorpAccMapEntity> corpAcclist = getOfflineCorpAccByCompanyId(corpData.getCorpCompId().toBigInteger());
		if (!ObjectUtils.isEmpty(corpAcclist)) {
			for (CorpAccMapEntity accObj : corpAcclist) {
				deleteFromAccMapById(accObj.getId().toBigInteger());
			}
		}

		try {
			for (CorpAccReqEntity corpAcc : corpData.getCorpMenuAccData().getCorpAccList()) {
				CorpAccMapEntity accObj = new CorpAccMapEntity();
				accObj.setCorpId(corpData.getCorpCompId());
				accObj.setAccountNo(EncryptorDecryptor.encryptData(corpAcc.getAccountNo()));
				accObj.setCreatedon(date);
				accObj.setStatusId(BigDecimal.valueOf(3));
				if (!ObjectUtils.isEmpty(corpAcc.getUpdatedby())) {
					accObj.setUpdatedby(corpAcc.getUpdatedby());
				} else {
					accObj.setUpdatedby(new BigDecimal(1));
				}
				session.save(accObj);
			}

		} catch (Exception e) {
			logger.info("Exception:", e);
			return false;
		}
		return true;
	}

//	@Override
//	public boolean saveToCorpAccMapTmp(CorpDataBeanTmp corpData) {
//		Session session = sessionFactory.getCurrentSession();
//		Date date = new Date();
//		List<CorpAccMapEntityTmp> corpAcclist = getOfflineCorpAccByCompanyIdTmp(
//				corpData.getCorpCompId().toBigInteger());
//		if (!ObjectUtils.isEmpty(corpAcclist)) {
//			for (CorpAccMapEntityTmp accObj : corpAcclist) {
//				deleteFromAccMapById(accObj.getId().toBigInteger());
//			}
//		}
//		try {
//			for (CorpAccReqEntityTmp corpAcc : corpData.getCorpMenuAccData().getCorpAccList()) {
//				CorpAccMapEntityTmp accObj = new CorpAccMapEntityTmp();
//				accObj.setCorpId(corpData.getCorpCompId());
//				accObj.setAccountNo(EncryptorDecryptor.encryptData(corpAcc.getAccountNo()));
//				accObj.setCreatedon(date);
//				accObj.setStatusId(BigDecimal.valueOf(3));
//				accObj.setUpdatedby(corpData.getCreatedByUpdatedBy());
//				session.save(accObj);
//			}
//		} catch (Exception e) {
//			logger.info("Exception:", e);
//			return false;
//		}
//		return true;
//	}

	@Override
	public boolean saveCorpUserMasterData(CorpDataBean corpData) {
		Session session = sessionFactory.getCurrentSession();
		Map<String, String> map = new HashMap<>();
		List<Map<String, String>> record = new ArrayList<>();
		List<String> generalRec = new ArrayList<>();
		File file = null;
		try {
			for (CorpUserEntity corpUser : corpData.getCorpUserMasterData()) {

				corpUser.setCorp_comp_id(corpData.getCorpCompId());
				corpUser.setCreatedon(new Timestamp(System.currentTimeMillis()));
				corpUser.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
				corpUser.setStatusid(BigDecimal.valueOf(3));

				RandomNumberGenerator randomNumberGenerator = new RandomNumberGenerator();
				String randomPassword = randomNumberGenerator.generateRandomString();
				String encryptpass = EncryptorDecryptor.encryptData(randomPassword);
				corpUser.setUser_pwd(encryptpass);

				String nonEncUserName = corpUser.getUser_name();
				String nonEncEmail = corpUser.getEmail_id();
				String nonEncPanCardNo = corpUser.getPancardNumber();

				adminWorkFlowReqUtility.encryptCorpUserData(corpUser);

				if (corpUser.getCorpRoleId().intValue() == 1) {
					corpUser.setParentId(BigDecimal.valueOf(0));

				} else {
					corpUser.setParentId(new BigDecimal(getUserIdByRRN(corpUser.getParentRrn())));
				}

				session.save(corpUser);
				String otp = RandomNumberGenerator.generateActivationCode();
				String encPwd = (nonEncPanCardNo).concat(otp);
				System.out.println("Password for pdf:" + encPwd);
				map.put("User Name", nonEncUserName);
				map.put("Password", randomPassword);

				List<ConfigMasterEntity> configData = masterConfigDao.getConfigByConfigKey("CORP_PORTAL_URL_LINK");
				generalRec.add(corpUser.getCompanyName());
				generalRec.add(corpUser.getCorpRoleName());
				generalRec.add(configData.get(0).getDescription());

				record.add(map);
				file = pdfGenerator.generatePDF(corpUser.getCompanyName() + "_UserCredentials.pdf",
						"PSB: User Credentials", record, encPwd, encPwd, generalRec, pdfFilePath);

				List<String> toEmail = new ArrayList<>();
				List<String> ccEmail = new ArrayList<>();
				List<String> bccEmail = new ArrayList<>();
				List<File> files = new ArrayList<>();
				toEmail.add(nonEncEmail);
				files.add(file);
				Date subjectDate = new Date();
				String subject = "PSB: Notification " + subjectDate;
//				if (emailUtil.sendEmailWithAttachment(toEmail, ccEmail, bccEmail, files,
//						"Note: Please enter your pancard number and otp(sent on your mobile number) to view login credentials.",
//						subject)) {
//					file.delete();
//				}

//				if (emailUtil.sendEmailWithAttachment(nonEncEmail,
//						"Note: Please enter your pancard number and otp(sent on your mobile number) to view login credentials.",
//						file)) {
//					file.delete();
//				}
//				emailUtil.sendSMSNotification(nonEncMobile, "Please enter otp" + " " + otp + " "
//						+ "along with your pancard number as password to view PDF sent on your email");

			}

		} catch (Exception e) {
			logger.info("Exception:", e);
			return false;
		}
		return true;
	}

	@Override
	public boolean saveToCorpUsersMenuMap(CorpDataBean corpData) {
		Session session = sessionFactory.getCurrentSession();
		Date date = new Date();
		try {
			for (CorpUserMenuMapEntity corpMenu : corpData.getCorpUserMenuAccMapData().getCorpUserMenuMapData()) {

				corpMenu.setCorpUserId(new BigDecimal(getUserIdByRRN(corpMenu.getUserRrn())));
				corpMenu.setCorpCompId(corpData.getCorpCompId());
				corpMenu.setStatusId(BigDecimal.valueOf(3));
				corpMenu.setCreatedon(date);
				corpMenu.setUpdatedon(date);
				session.save(corpMenu);
			}

		} catch (Exception e) {
			logger.info("Exception:", e);
			return false;
		}
		return true;
	}

	@Override
	public boolean saveToCorpUsersAccMap(CorpDataBean corpData) {
		Session session = sessionFactory.getCurrentSession();
		Date date = new Date();
		try {
			for (CorpUserAccMapEntity corpAcc : corpData.getCorpUserMenuAccMapData().getCorpUserAccMapData()) {
				corpAcc.setCorpUserId(new BigDecimal(getUserIdByRRN(corpAcc.getUserRrn())));
				corpAcc.setCorpCompId(corpData.getCorpCompId());
				corpAcc.setStatusId(BigDecimal.valueOf(3));
				corpAcc.setCreatedon(date);
				corpAcc.setUpdatedon(date);

				session.save(corpAcc);
			}

		} catch (Exception e) {
			logger.info("Exception:", e);
			return false;
		}
		return true;
	}

	@Override
	public BigInteger getUserIdByRRN(String rrn) {
		List<CorpCompanyMasterEntity> list = null;
		try {

			String sqlQuery = "select cm.id from CORP_USERS_MASTER cm where cm.RRN =:rrnNo ";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").setParameter("rrnNo", rrn)
					.setResultTransformer(Transformers.aliasToBean(CorpCompanyMasterEntity.class)).list();
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return list.get(0).getId().toBigInteger();
	}

	@Override
	public boolean updateStatusOfCorpRequestData(CorpDataBean corpData) {
		String sql = null;
		int res = 0;
		int reqStatus;

		if (corpData.getReqStatus().equalsIgnoreCase("Approved")) {
			reqStatus = 7;
		} else {
			reqStatus = 6;
		}
		try {
			sql = "update corp_comp_req set statusid=:status, remark=:remark where id=:corpId";

			res = getSession().createSQLQuery(sql).setParameter("corpId", corpData.getCorpCompReqId())
					.setParameter("remark", corpData.getRemark()).setParameter("status", reqStatus).executeUpdate();
		} catch (Exception e) {
			logger.info("Exception:", e);
			return false;
		}
		return true;
	}

	@Override
	public ResponseMessageBean isCompanyExist(CorpCompDataMasterEntity corpCompData) {

		ResponseMessageBean rmb = new ResponseMessageBean();
		try {
			String sqlCorpNameExist = " SELECT count(*) FROM CORP_COMP_DATA_MASTER WHERE Lower(Companyname) =:compName";

			List compNameExit = getSession().createSQLQuery(sqlCorpNameExist)
					.setParameter("compName", corpCompData.getCompanyName().toLowerCase()).list();

			if (!(compNameExit.get(0).toString().equalsIgnoreCase("0"))) {
				rmb.setResponseCode("500");
				rmb.setResponseMessage("Company Is Already Exist");
			} else {
				rmb.setResponseCode("200");
				rmb.setResponseMessage("Both Not Exist");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return rmb;
	}

	public List<CorpMenuMapEntity> getOfflineCorpMenuByCompanyId(BigInteger corpId) {
		List<CorpMenuMapEntity> list = null;
		try {

			String sqlQuery = "	select cm.id as id ,cm.corpId as corpId, cm.corpMenuId as corpMenuId,cm.corpSubMenuId as corpSubMenuId, cm.StatusId as statusId, cm.Createdon as createdon,"
					+ "	cm.Updatedon as updatedon,cm.UPDATEDBY as updatedby , cmm.menuname as menuName from CORP_MENU_MAP cm "
					+ "	inner join corp_menu cmm on cmm.id =cm.corpMenuId  where cm.corpId=:corpId ";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("corpMenuId")
					.addScalar("corpSubMenuId").addScalar("corpId").addScalar("statusId").addScalar("createdon")
					.addScalar("updatedon").addScalar("updatedby").addScalar("menuName").setParameter("corpId", corpId)
					.setResultTransformer(Transformers.aliasToBean(CorpMenuMapEntity.class)).list();
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return list;
	}

	public List<CorpMenuMapEntityTmp> getOfflineCorpMenuByCompanyIdTmp(BigInteger corpId) {
		List<CorpMenuMapEntityTmp> list = null;
		try {

			String sqlQuery = "	select cm.id as id ,cm.corpId as corpId, cm.corpMenuId as corpMenuId, cm.StatusId as statusId, cm.Createdon as createdon,"
					+ "	cm.Updatedon as updatedon,cm.UPDATEDBY as updatedby , cmm.menuname as menuName from CORP_MENU_MAP_TMP cm "
					+ "	inner join corp_menu cmm on cmm.id =cm.corpMenuId  where cm.corpId=:corpId ";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("corpMenuId").addScalar("corpId")
					.addScalar("statusId").addScalar("createdon").addScalar("updatedon").addScalar("updatedby")
					.addScalar("menuName").setParameter("corpId", corpId)
					.setResultTransformer(Transformers.aliasToBean(CorpMenuMapEntityTmp.class)).list();
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return list;
	}

	public boolean deleteFromMenuMapMenuById(BigInteger id) {
		try {
			String sqlQuery = "UPDATE corp_menu_map SET STATUSID = 10 where  id=:id ";
			getSession().createSQLQuery(sqlQuery).setParameter("id", id).executeUpdate();
		} catch (Exception e) {
			logger.info("Exception:", e);
			return false;
		}
		return true;
	}

	public List<CorpAccMapEntity> getOfflineCorpAccByCompanyId(BigInteger corpId) {
		List<CorpAccMapEntity> list = null;
		try {

			String sqlQuery = "	select cm.id as id ,cm.corpId as corpId, cm.Accountno as accountNo, cm.StatusId as statusId, cm.Createdon as createdon,"
					+ "	cm.Updatedon as updatedon,cm.UPDATEDBY as updatedby  from CORP_ACC_MAP cm where cm.corpId=:corpId ";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("accountNo").addScalar("corpId")
					.addScalar("statusId").addScalar("createdon").addScalar("updatedon").addScalar("updatedby")
					.setParameter("corpId", corpId)
					.setResultTransformer(Transformers.aliasToBean(CorpAccMapEntity.class)).list();
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return list;
	}

	public List<CorpAccMapEntityTmp> getOfflineCorpAccByCompanyIdTmp(BigInteger corpId) {
		List<CorpAccMapEntityTmp> list = null;
		try {

			String sqlQuery = "	select cm.id as id ,cm.corpId as corpId, cm.Accountno as accountNo, cm.StatusId as statusId, cm.Createdon as createdon,"
					+ "	cm.Updatedon as updatedon,cm.UPDATEDBY as updatedby  from CORP_ACC_MAP_TMP cm where cm.corpId=:corpId ";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("accountNo").addScalar("corpId")
					.addScalar("statusId").addScalar("createdon").addScalar("updatedon").addScalar("updatedby")
					.setParameter("corpId", corpId)
					.setResultTransformer(Transformers.aliasToBean(CorpAccMapEntityTmp.class)).list();
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return list;
	}

	public boolean deleteFromAccMapById(BigInteger id) {
		try {
			String sqlQuery = "UPDATE corp_acc_map SET STATUSID = 10 where id=:id ";
			getSession().createSQLQuery(sqlQuery).setParameter("id", id).executeUpdate();
		} catch (Exception e) {
			logger.info("Exception:", e);
			return false;
		}
		return true;
	}

	@Override
	public ResponseMessageBean isCorpCompanyExist(CorpCompanyMasterEntity corpCompMasterData) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		try {
			String sqlCorpNameExist = " SELECT count(*) FROM CORP_COMPANY_MASTER WHERE Lower(Companyname) =:compName";

			List compNameExit = getSession().createSQLQuery(sqlCorpNameExist)
					.setParameter("compName", corpCompMasterData.getCompanyName().toLowerCase()).list();

			if (!(compNameExit.get(0).toString().equalsIgnoreCase("0"))) {
				rmb.setResponseCode("500");
				rmb.setResponseMessage("Company Is Already Exist");
			} else {
				rmb.setResponseCode("200");
				rmb.setResponseMessage("Both Not Exist");
			}
		} catch (Exception e) {
			logger.error("Exception:", e);
		}
		return rmb;
	}

	public String getCompanyMasterById(BigDecimal id) {

		ResponseMessageBean rmb = new ResponseMessageBean();
		List compName = null;
		try {
			String sqlCorpName = " SELECT COMPANYNAME FROM CORP_COMPANY_MASTER WHERE id =:id";

			compName = getSession().createSQLQuery(sqlCorpName).setParameter("id", id).list();

		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return (String) compName.get(0);

	}

	public String getCompanyMasterByIdTmp(BigDecimal id) {

		ResponseMessageBean rmb = new ResponseMessageBean();
		List compName = null;
		try {
			String sqlCorpName = " SELECT COMPANYNAME FROM CORP_COMPANY_MASTER_TMP WHERE id =:id";

			compName = getSession().createSQLQuery(sqlCorpName).setParameter("id", id).list();

		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return (String) compName.get(0);

	}

	public String getBranchCodeMasterByIdTmp(BigDecimal id) {

		List branchCode = null;
		try {
			String sqlBranchCode = " SELECT BRANCHCODE FROM CORP_COMPANY_MASTER_TMP WHERE id =:id";

			branchCode = getSession().createSQLQuery(sqlBranchCode).setParameter("id", id).list();

		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return (String) branchCode.get(0);

	}

	/*
	 * @Override public String getCompanyMasterByCompanyCode(String companyCode) {
	 * 
	 * ResponseMessageBean rmb = new ResponseMessageBean(); List compName=null; try
	 * { String sqlCorpName =
	 * " SELECT COMPANYNAME FROM CORP_COMPANY_MASTER WHERE id =:id";
	 * 
	 * compName= getSession().createSQLQuery(sqlCorpName)
	 * .setParameter("companyCode", companyCode).list();
	 * 
	 * } catch (Exception e) { logger.info("Exception:", e); } return (String)
	 * compName.get(0);
	 * 
	 * }
	 */
}
