package com.itl.pns.corp.dao.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

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
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.constant.ApplicationConstants;
import com.itl.pns.corp.dao.CorpMakerCheckerDao;
import com.itl.pns.corp.entity.CorpActivitySettingMasterEntity;
import com.itl.pns.corp.entity.CorpCompanyMasterEntity;
import com.itl.pns.corp.entity.CorpCompanyMenuMappingEntity;
import com.itl.pns.corp.entity.CorpMenuEntity;
import com.itl.pns.corp.entity.CorpTempSalProcessEntity;
import com.itl.pns.corp.entity.CorpUserTypeEntity;
import com.itl.pns.corp.entity.DesignationHierarchyMasterEntity;
import com.itl.pns.entity.AccountTypesEntity;
import com.itl.pns.entity.ActivitySettingMasterEntity;
import com.itl.pns.entity.AdminWorkFlowRequestEntity;
import com.itl.pns.repository.CorpActivitySettingMasterRepository;
import com.itl.pns.repository.CorpTempSalProcessRepository;
import com.itl.pns.util.AdminWorkFlowReqUtility;

/**
 * @author shubham.lokhande
 *
 */
@Repository
@Transactional
public class CorpMakerCheckerDaoImpl implements CorpMakerCheckerDao {

	static Logger LOGGER = Logger.getLogger(CorpMakerCheckerDaoImpl.class);
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;
	
	@Autowired
	CorpActivitySettingMasterRepository corpActivityRepository;
	
	@Autowired
	AdminWorkFlowReqUtility adminWorkFlowReqUtility;

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public List<ActivitySettingMasterEntity> getAllActivitySettingForCorp() {
		List<ActivitySettingMasterEntity> list = null;
		try {
			String sqlQuery = "select am.id,am.activityId,am.createdBy,am.createdOn,am.statusId,am.maker,am.approver,"
					+ " am.checker,s.name as statusName,ac.displayname as activityName from ACTIVITYSETTINGMASTER am "
					+ " inner join statusmaster s on s.id=statusId inner join  ACTIVITYMASTER ac on  ac.id = am.activityId where ac.FT_NFT ='ADM' and ac.appid=2 "
					+ " order by am.id desc";
			list = getSession().createSQLQuery(sqlQuery)
					.addScalar("id",StandardBasicTypes.BIG_DECIMAL).addScalar("activityId",StandardBasicTypes.BIG_DECIMAL).addScalar("createdBy",StandardBasicTypes.BIG_DECIMAL)
					.addScalar("createdOn").addScalar("statusId",StandardBasicTypes.BIG_DECIMAL).addScalar("maker")
					.addScalar("approver").addScalar("checker").addScalar("statusName").addScalar("activityName")	
					.setResultTransformer(Transformers.aliasToBean(ActivitySettingMasterEntity.class)).list();

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public List<CorpActivitySettingMasterEntity> getCorpActivitiesAndMapping(int companyId) {
		List<CorpActivitySettingMasterEntity> list = null;
		try {
			String sqlQuery = "select am.id,am.activityId,am.createdBy,am.createdOn,am.statusId,am.maker,am.approver,"
					+ " am.checker,s.name as statusName,ac.displayname as activityName, am.companyId from CORPACTIVITYSETTINGMASTER am "
					+ " inner join statusmaster s on s.id=am.statusId inner join  ACTIVITYMASTER  ac on  ac.id = am.activityId where am.companyId=:companyId "
					+ " order by am.id desc";
			list = getSession().createSQLQuery(sqlQuery)				
					.addScalar("id",StandardBasicTypes.BIG_DECIMAL).addScalar("activityId",StandardBasicTypes.BIG_DECIMAL).addScalar("createdBy",StandardBasicTypes.BIG_DECIMAL).addScalar("createdOn").addScalar("statusId",StandardBasicTypes.BIG_DECIMAL)
					.addScalar("maker")	.addScalar("approver").addScalar("checker").addScalar("statusName").addScalar("activityName").addScalar("companyId",StandardBasicTypes.BIG_DECIMAL)
					.setParameter("companyId", companyId)
					.setResultTransformer(Transformers.aliasToBean(CorpActivitySettingMasterEntity.class)).list();

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}
	
	
     @Autowired
	CorpTempSalProcessRepository corpTempSalProcessRepository;
     
     
	@Override
	public boolean bulkSalaryUpload(List<CorpTempSalProcessEntity> corpSalData) {
		Session session = sessionFactory.getCurrentSession();	
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(corpSalData.get(0).getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.CORP_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData =  adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(corpSalData.get(0).getActivityName());
		
	
	                 
		try { 
			 
			for(CorpTempSalProcessEntity corpSalDataObj : corpSalData){
				
	    		
				if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)
						&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getChecker())) 
						&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getMaker()))) {
					corpSalDataObj.setStatusId(BigDecimal.valueOf(statusId));   //97-  ADMIN_CHECKER_PENDIN
				}
				
				List<CorpTempSalProcessEntity> salBean =corpTempSalProcessRepository.findByPayeeAccountNumber(corpSalDataObj.getPayeeAccountNumber());
				if(ObjectUtils.isEmpty(salBean)) {
					
					corpSalDataObj.setAppId(BigDecimal.valueOf(2));
					corpSalDataObj.setCreatedon(new Date());
					session.save(corpSalDataObj);

					if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getChecker())) 
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getMaker()))) {
			
				List<CorpTempSalProcessEntity> corpTempSalObj = getAllCorpSalData();
				
				ObjectMapper mapper = new ObjectMapper();
				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
				adminData.setAppId(corpSalDataObj.getAppId());
				adminData.setCreatedByUserId(corpSalDataObj.getUser_ID());
				adminData.setCreatedByRoleId(corpSalDataObj.getRole_ID());
				adminData.setPageId(corpSalDataObj.getSubMenu_ID());     //set submenuId as pageid
				adminData.setCreatedBy(corpSalDataObj.getUser_ID());
				adminData.setContent(mapper.writeValueAsString(corpSalDataObj));   
				adminData.setActivityId(corpSalDataObj.getSubMenu_ID());  //set submenuId as activityid
				adminData.setStatusId(BigDecimal.valueOf(statusId));  //97- ADMIN_CHECKER_PENDIN
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6));    // 6- checker roleid
				adminData.setRemark(corpSalDataObj.getRemark());
				adminData.setActivityName(corpSalDataObj.getActivityName());
				adminData.setActivityRefNo(corpTempSalObj.get(0).getId());
				adminData.setTableName("CORP_TEMP_SAL_PROCESS");
				adminData.setUserAction(BigDecimal.valueOf(3));
				adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);

						// Save data to admin work flow history
						adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(corpSalDataObj.getSubMenu_ID(),
								corpTempSalObj.get(0).getId(), corpSalDataObj.getUser_ID(), corpSalDataObj.getRemark(),
								corpSalDataObj.getRole_ID(),mapper.writeValueAsString(corpSalDataObj));
					}

				}
			}

			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}

	
	public List<CorpTempSalProcessEntity> getAllCorpSalData() {
		List<CorpTempSalProcessEntity> list = null;
		try {
			String sqlQuery = "select cs.id from CORP_TEMP_SAL_PROCESS cs order by cs.id desc";
			list = getSession().createSQLQuery(sqlQuery)
					.addScalar("id")				
					.setResultTransformer(Transformers.aliasToBean(CorpTempSalProcessEntity.class)).list();

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}
	
	
	@Override
	@SuppressWarnings("rawtypes")
	public ResponseMessageBean checkUpdateCompanyExit(CorpCompanyMasterEntity corpCompanyMasterData) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		try{
		String sqlNameExist = " SELECT count(*) FROM CORP_COMPANY_MASTER WHERE Lower(COMPANYNAME) =:compName  AND  ID!=:id";
		String sqlCompCodeExist = "   SELECT count(*) FROM CORP_COMPANY_MASTER WHERE companyCode =:compCode AND ID!=:id";
		
		
		List nameExit = getSession().createSQLQuery(sqlNameExist).setParameter("id", corpCompanyMasterData.getId())
				.setParameter("compName", corpCompanyMasterData.getCompanyName().toLowerCase()).list();
		List seqNoExit = getSession().createSQLQuery(sqlCompCodeExist).setParameter("id", corpCompanyMasterData.getId())
				.setParameter("compCode", corpCompanyMasterData.getCompanyCode()).list();

		if (!(nameExit.get(0).toString().equalsIgnoreCase("0"))) {
			rmb.setResponseCode("500");
			rmb.setResponseMessage("Company Name  Already Exist");
		} else if (!(seqNoExit.get(0).toString().equalsIgnoreCase("0"))) {
			rmb.setResponseCode("500");
			rmb.setResponseMessage("Comapany Code  Already Exist");
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
	@SuppressWarnings("rawtypes")
	public ResponseMessageBean checkCompanyExit(CorpCompanyMasterEntity corpCompanyMasterData) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		try{
		String sqlNameExist = " SELECT count(*) FROM CORP_COMPANY_MASTER WHERE Lower(COMPANYNAME) =:compName";
		String sqlCompCodeExist = "   SELECT count(*) FROM CORP_COMPANY_MASTER WHERE companyCode =:compCode ";
		
		
		List nameExit = getSession().createSQLQuery(sqlNameExist)
				.setParameter("compName", corpCompanyMasterData.getCompanyName().toLowerCase()).list();
		List seqNoExit = getSession().createSQLQuery(sqlCompCodeExist)
				.setParameter("compCode", corpCompanyMasterData.getCompanyCode()).list();

		if (!(nameExit.get(0).toString().equalsIgnoreCase("0"))) {
			rmb.setResponseCode("500");
			rmb.setResponseMessage("Company Name Already Exist");
		} else if (!(seqNoExit.get(0).toString().equalsIgnoreCase("0"))) {
			rmb.setResponseCode("500");
			rmb.setResponseMessage("Comapany Code  Already Exist");
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
	public ResponseMessageBean checkAccountTypeExit(AccountTypesEntity accountType) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		try{
		String sqlNameExist = " SELECT count(*) FROM ACCOUNT_TYPE_MASTER_PRD WHERE Lower(ACCOUNT_TYPE) =:accType ";
		String sqlCompCodeExist = "   SELECT count(*) FROM ACCOUNT_TYPE_MASTER_PRD WHERE ACCOUNT_CODE =:accountCode ";
		
		
		List nameExit = getSession().createSQLQuery(sqlNameExist)
				.setParameter("accType", accountType.getAccountType().toLowerCase()).list();
		List seqNoExit = getSession().createSQLQuery(sqlCompCodeExist)
				.setParameter("accountCode", accountType.getAccountCode()).list();

		if (!(nameExit.get(0).toString().equalsIgnoreCase("0"))) {
			rmb.setResponseCode("500");
			rmb.setResponseMessage("Account Type Is Already Exist");
		} else if (!(seqNoExit.get(0).toString().equalsIgnoreCase("0"))) {
			rmb.setResponseCode("500");
			rmb.setResponseMessage("Account Code Is Already Exist");
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
	public ResponseMessageBean checkUpdateAccountTypeExit(AccountTypesEntity accountType) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		try{
		String sqlNameExist = " SELECT count(*) FROM ACCOUNT_TYPE_MASTER_PRD WHERE Lower(ACCOUNT_TYPE) =:accType  AND  ID!=:id ";
		String sqlCodeExist = "   SELECT count(*) FROM ACCOUNT_TYPE_MASTER_PRD WHERE ACCOUNT_CODE =:accountCode  AND  ID!=:id";
		
		
		List nameExit = getSession().createSQLQuery(sqlNameExist).setParameter("id", accountType.getId())
				.setParameter("accType", accountType.getAccountType().toLowerCase()).list();
		List seqNoExit = getSession().createSQLQuery(sqlCodeExist).setParameter("id", accountType.getId())
				.setParameter("accountCode", accountType.getAccountCode()).list();

		if (!(nameExit.get(0).toString().equalsIgnoreCase("0"))) {
			rmb.setResponseCode("500");
			rmb.setResponseMessage("Account Type Is Already Exist");
		} else if (!(seqNoExit.get(0).toString().equalsIgnoreCase("0"))) {
			rmb.setResponseCode("500");
			rmb.setResponseMessage("Account Code Is Already Exist");
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
	public ResponseMessageBean checkUserTypeExit(CorpUserTypeEntity corpUserType) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		try{
		String sqlNameExist = " SELECT count(*) FROM CORP_USER_TYPES WHERE Lower(USER_TYPE) =:userType ";
		String sqlCodeExist = " SELECT count(*) FROM CORP_USER_TYPES WHERE  RULE_SEQ=:ruleSeq ";
		
		
		List nameExit = getSession().createSQLQuery(sqlNameExist)
				.setParameter("userType", corpUserType.getUSER_TYPE().toLowerCase()).list();
		List seqNoExit = getSession().createSQLQuery(sqlCodeExist)
				.setParameter("ruleSeq", corpUserType.getRULE_SEQ()).list();

		if (!(nameExit.get(0).toString().equalsIgnoreCase("0"))) {
			rmb.setResponseCode("500");
			rmb.setResponseMessage("User Type Is Already Exist");
		} else if (!(seqNoExit.get(0).toString().equalsIgnoreCase("0"))) {
			rmb.setResponseCode("500");
			rmb.setResponseMessage("Rule Seq Code Is Already Exist");
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
	public ResponseMessageBean checkUpdateUserTypeExit(CorpUserTypeEntity corpUserType) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		try{
		String sqlNameExist = " SELECT count(*) FROM CORP_USER_TYPES WHERE Lower(USER_TYPE) =:userType  AND  ID!=:id";
		String sqlCodeExist = " SELECT count(*) FROM CORP_USER_TYPES WHERE  RULE_SEQ=:ruleSeq  AND  ID!=:id ";
		
		
		List nameExit = getSession().createSQLQuery(sqlNameExist).setParameter("id", corpUserType.getId())
				.setParameter("userType", corpUserType.getUSER_TYPE().toLowerCase()).list();
		List seqNoExit = getSession().createSQLQuery(sqlCodeExist).setParameter("id", corpUserType.getId())
				.setParameter("ruleSeq", corpUserType.getRULE_SEQ()).list();

		if (!(nameExit.get(0).toString().equalsIgnoreCase("0"))) {
			rmb.setResponseCode("500");
			rmb.setResponseMessage("User Type Is Already Exist");
		} else if (!(seqNoExit.get(0).toString().equalsIgnoreCase("0"))) {
			rmb.setResponseCode("500");
			rmb.setResponseMessage("Rule Seq Code Is Already Exist");
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
	public ResponseMessageBean checkCorpMenuNameExit(CorpMenuEntity corpMenuData) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		try{
		String sqlNameExist = " SELECT count(*) FROM CORP_MENU WHERE Lower(MENUNAME) =:menuName ";
		
		
		
		List nameExit = getSession().createSQLQuery(sqlNameExist)
				.setParameter("menuName", corpMenuData.getMenuName().toLowerCase()).list();
		

		if (!(nameExit.get(0).toString().equalsIgnoreCase("0"))) {
			rmb.setResponseCode("500");
			rmb.setResponseMessage("Menu Name Is Already Exist");
		}else {
			rmb.setResponseCode("200");
			rmb.setResponseMessage("Both Not Exist");
		}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return rmb;
	}

	@Override
	public ResponseMessageBean checkUpdateCorpMenuNameExit(CorpMenuEntity corpMenuData) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		try{
		String sqlNameExist = " SELECT count(*) FROM CORP_MENU WHERE Lower(MENUNAME) =:menuName  AND  ID!=:id  ";
		
		
		
		List nameExit = getSession().createSQLQuery(sqlNameExist).setParameter("id", corpMenuData.getId())
				.setParameter("menuName", corpMenuData.getMenuName().toLowerCase()).list();
		

		if (!(nameExit.get(0).toString().equalsIgnoreCase("0"))) {
			rmb.setResponseCode("500");
			rmb.setResponseMessage("Menu Name Is Already Exist");
		}else {
			rmb.setResponseCode("200");
			rmb.setResponseMessage("Both Not Exist");
		}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return rmb;
	}  

	
	@Override
	public ResponseMessageBean checkCorpMenuExitForCompany(CorpCompanyMenuMappingEntity corpCompMenuData) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		try{
		String sqlNameExist = " SELECT count(*) FROM CORP_COMP_MENU_MAPPING WHERE Corp_Menu_Id=:corpMenuId  AND  Company_Id=:compId  ";
		
		
		
		List nameExit = getSession().createSQLQuery(sqlNameExist).setParameter("compId", corpCompMenuData.getCompanyId())
				.setParameter("corpMenuId", corpCompMenuData.getCorpMenuId()).list();
		

		if (!(nameExit.get(0).toString().equalsIgnoreCase("0"))) {
			rmb.setResponseCode("500");
			rmb.setResponseMessage("Menu Is Already Mapped ");
		}else {
			rmb.setResponseCode("200");
			rmb.setResponseMessage("Both Not Exist");
		}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return rmb;
	}

	@Override
	public ResponseMessageBean checkUpdateCorpMenuExitForCompany(CorpCompanyMenuMappingEntity corpCompMenuData) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		try{
		String sqlNameExist = " SELECT count(*) FROM CORP_COMP_MENU_MAPPING WHERE Corp_Menu_Id=:corpMenuId  AND  Company_Id=:compId AND ID !=:id  ";
		
		
		
		List nameExit = getSession().createSQLQuery(sqlNameExist).setParameter("id", corpCompMenuData.getId()).setParameter("compId", corpCompMenuData.getCompanyId())
				.setParameter("corpMenuId", corpCompMenuData.getCorpMenuId()).list();
		

		if (!(nameExit.get(0).toString().equalsIgnoreCase("0"))) {
			rmb.setResponseCode("500");
			rmb.setResponseMessage("Menu Is Already Mapped ");
		}else {
			rmb.setResponseCode("200");
			rmb.setResponseMessage("Both Not Exist");
		}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return rmb;
	}

	@Override
	public boolean addDesignationHierarchy(DesignationHierarchyMasterEntity designationData) {
		Session session = sessionFactory.getCurrentSession();
		designationData.setAppId(BigDecimal.valueOf(2));
		int userStatus = designationData.getStatusId().intValue();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(designationData.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.CORP_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(designationData.getActivityName());
		
		try {
			if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getChecker())) 
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getMaker())))  {
				designationData.setStatusId(BigDecimal.valueOf(statusId));   //97-  ADMIN_CHECKER_PENDIN
			}
			designationData.setCreatedOn(new Date());
			designationData.setUpdatedOn(new Date());
			session.save(designationData);
			
			if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getChecker())) 
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getMaker())))  {
			List<DesignationHierarchyMasterEntity> list= getAllDesignationHierarchy();	
			designationData.setCompanyName(list.get(0).getCompanyName());
			ObjectMapper mapper = new ObjectMapper();
			
			AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
			adminData.setAppId(BigDecimal.valueOf(2));
			adminData.setCreatedByUserId(designationData.getUser_ID());
			adminData.setCreatedByRoleId(designationData.getRole_ID());
			adminData.setPageId(designationData.getSubMenu_ID());       //set submenuId as pageid
			adminData.setCreatedBy(designationData.getCreatedBy());
			adminData.setContent(mapper.writeValueAsString(designationData));   
			adminData.setActivityId(designationData.getSubMenu_ID());  //set submenuId as activityid
			adminData.setStatusId(BigDecimal.valueOf(statusId));  //97- ADMIN_CHECKER_PENDIN
			adminData.setPendingWithRoleId(BigDecimal.valueOf(6));    // 6- checker roleid
			adminData.setRemark(designationData.getRemark());
			adminData.setActivityName(designationData.getActivityName());
			adminData.setActivityRefNo(list.get(0).getId());
			adminData.setTableName("DESIGNATION_HIERARCHY_MASTER");
			adminData.setUserAction(BigDecimal.valueOf(userStatus));
			adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);
			
			 //Save data to admin work flow history
			adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(designationData.getSubMenu_ID(),
					list.get(0).getId(), designationData.getCreatedBy(),
					designationData.getRemark(), designationData.getRole_ID(),mapper.writeValueAsString(designationData));
			}
			
			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}

	@Override
	public boolean updateDesignationHierarchy(DesignationHierarchyMasterEntity designationData) {
		Session session = sessionFactory.getCurrentSession();
		designationData.setAppId(BigDecimal.valueOf(2));
		int userStatus = designationData.getStatusId().intValue();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(designationData.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.CORP_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(designationData.getActivityName());
		try {
			if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getChecker())) 
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getMaker())))  {
				designationData.setStatusId(BigDecimal.valueOf(statusId));
		}
			designationData.setUpdatedOn(new Date());
			session.update(designationData);
			
			if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getMaker())))  {
				
			ObjectMapper mapper = new ObjectMapper();
			
			List<DesignationHierarchyMasterEntity> list= getDesignationHierarchyById(designationData.getId().intValue());	
			designationData.setCompanyName(list.get(0).getCompanyName());
			
			
			List<AdminWorkFlowRequestEntity> AdminDataList = adminWorkFlowReqUtility
						.getAdminWorkFlowDataByActivityRefNo(designationData.getId().intValue(),designationData.getSubMenu_ID());
			
			AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
			adminData.setAppId(BigDecimal.valueOf(2));
			adminData.setCreatedOn(designationData.getCreatedOn());
			adminData.setCreatedByUserId(designationData.getUser_ID());
			adminData.setCreatedByRoleId(designationData.getRole_ID());
			adminData.setPageId(designationData.getSubMenu_ID());       //set submenuId as pageid
			adminData.setCreatedBy(designationData.getCreatedBy());
			adminData.setContent(mapper.writeValueAsString(designationData));   
			adminData.setActivityId(designationData.getSubMenu_ID());  //set submenuId as activityid
			adminData.setStatusId(BigDecimal.valueOf(statusId));  //97- ADMIN_CHECKER_PENDIN
			adminData.setPendingWithRoleId(BigDecimal.valueOf(6));    // 6- checker roleid
			adminData.setRemark(designationData.getRemark());
			adminData.setActivityName(designationData.getActivityName());
			adminData.setActivityRefNo(designationData.getId());
			adminData.setUserAction(BigDecimal.valueOf(userStatus));
			adminData.setTableName("DESIGNATION_HIERARCHY_MASTER");

			if(ObjectUtils.isEmpty(AdminDataList)){
				adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);
				}else if(!ObjectUtils.isEmpty(AdminDataList)){
					adminData.setId(BigDecimal.valueOf(AdminDataList.get(0).getId().intValue()));
					adminWorkFlowReqUtility.updateAdminWorkFlowRequest(adminData);
					
				}
			 //Save data to admin work flow history
			adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(designationData.getSubMenu_ID(),
					designationData.getId(), designationData.getCreatedBy(),
					designationData.getRemark(), designationData.getRole_ID(),mapper.writeValueAsString(designationData));
	
		}else{

			//if record is present in admin work flow then update status
			adminWorkFlowReqUtility.getCheckerDataByctivityRefId(designationData.getId().toBigInteger(),
					 BigInteger.valueOf(userStatus),designationData.getSubMenu_ID());
		}
			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}

	@Override
	public List<DesignationHierarchyMasterEntity> getAllDesignationHierarchy() {
		List<DesignationHierarchyMasterEntity> list = null;
		try {
			String sqlQuery ="select cu.id ,cu.companyId,  cu.createdBy,"
					+ "cu.createdOn, cu.statusId, cu.updatedOn, cu.updatedBy, "
					+ "	cm.companyName,cu.designationName,cu.designationCode,cu.hierarchyLevel, "
					+ "s.name as statusName,um.USERID as createdByName, cu.authType as authType from DESIGNATION_HIERARCHY_MASTER cu "
					+ "	left join CORP_COMPANY_MASTER cm on cm.id=cu.companyId "
					+ "left join user_master um on cu.createdBy = um.id left join statusmaster s on s.id=cu.statusId order by cu.id desc ";
			list = getSession().createSQLQuery(sqlQuery)
					.addScalar("id",StandardBasicTypes.BIG_DECIMAL).addScalar("companyId",StandardBasicTypes.BIG_DECIMAL).addScalar("createdBy",StandardBasicTypes.BIG_DECIMAL).addScalar("createdOn").addScalar("statusId",StandardBasicTypes.BIG_DECIMAL).addScalar("updatedOn")
					.addScalar("updatedBy",StandardBasicTypes.BIG_DECIMAL).addScalar("companyName").addScalar("designationName").addScalar("designationCode",StandardBasicTypes.BIG_DECIMAL).addScalar("hierarchyLevel",StandardBasicTypes.BIG_DECIMAL).addScalar("statusName")
					.addScalar("createdByName").addScalar("authType")
					.setResultTransformer(Transformers.aliasToBean(DesignationHierarchyMasterEntity.class)).list();
			
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	
	
	@Override
	public List<DesignationHierarchyMasterEntity> getDesignationHierarchyById(int id) {
		List<DesignationHierarchyMasterEntity> list = null;
		try {
			String sqlQuery ="select cu.id ,cu.companyId,  cu.createdBy,"
					+ "cu.createdOn, cu.statusId, cu.updatedOn, cu.updatedBy, "
					+ "	cm.companyName,cu.designationName,cu.designationCode,cu.hierarchyLevel, "
					+ "s.name as statusName,um.USERID as createdByName,aw.remark,aw.userAction, cu.authType as authType from DESIGNATION_HIERARCHY_MASTER cu "
					+ "	left join CORP_COMPANY_MASTER cm on cm.id=cu.companyId "
					+ " left join ADMINWORKFLOWREQUEST aw on aw.activityrefno=:id and aw.tablename='DESIGNATION_HIERARCHY_MASTER'"
					+ "left join user_master um on cu.createdBy = um.id left join statusmaster s on s.id=cu.statusId where cu.id=:id order by cu.id desc ";
			list = getSession().createSQLQuery(sqlQuery)
					.addScalar("id",StandardBasicTypes.BIG_DECIMAL).addScalar("companyId",StandardBasicTypes.BIG_DECIMAL).addScalar("createdBy",StandardBasicTypes.BIG_DECIMAL).addScalar("createdOn").addScalar("statusId",StandardBasicTypes.BIG_DECIMAL).addScalar("updatedOn")
					.addScalar("updatedBy",StandardBasicTypes.BIG_DECIMAL).addScalar("companyName").addScalar("designationName").addScalar("designationCode",StandardBasicTypes.BIG_DECIMAL).addScalar("hierarchyLevel",StandardBasicTypes.BIG_DECIMAL).addScalar("statusName")
					.addScalar("createdByName").addScalar("authType").addScalar("remark").addScalar("userAction",StandardBasicTypes.BIG_DECIMAL)
					.setParameter("id", id)
					.setResultTransformer(Transformers.aliasToBean(DesignationHierarchyMasterEntity.class)).list();
			
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public ResponseMessageBean checkDesignationAndLevelExits(DesignationHierarchyMasterEntity designationData) {
		
		ResponseMessageBean rmb = new ResponseMessageBean();
		try{
		String sqlNameExist = " SELECT count(*) FROM DESIGNATION_HIERARCHY_MASTER WHERE Lower(DESIGNATIONNAME) =:desiName AND  COMPANYID=:compId";

		List nameExit = getSession().createSQLQuery(sqlNameExist).setParameter("compId", designationData.getCompanyId())
				.setParameter("desiName", designationData.getDesignationName().toLowerCase()).list();
		
		if (!(nameExit.get(0).toString().equalsIgnoreCase("0"))) {
			rmb.setResponseCode("500");
			rmb.setResponseMessage("Designation Is Already Exist For Selected Company");
		}else {
			rmb.setResponseCode("200");
			rmb.setResponseMessage("Both Not Exist");
		}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return rmb;
	}
	

	@Override
	public ResponseMessageBean checkUpdateDesignationAndLevelExits(DesignationHierarchyMasterEntity designationData) {
		
		ResponseMessageBean rmb = new ResponseMessageBean();
		try{
		String sqlNameExist = " SELECT count(*) FROM DESIGNATION_HIERARCHY_MASTER WHERE Lower(DESIGNATIONNAME) =:desiName AND  COMPANYID=:compId  AND ID !=:id ";

		List nameExit = getSession().createSQLQuery(sqlNameExist).setParameter("compId", designationData.getCompanyId()).setParameter("id", designationData.getId())
				.setParameter("desiName", designationData.getDesignationName().toLowerCase()).list();
		
		if (!(nameExit.get(0).toString().equalsIgnoreCase("0"))) {
			rmb.setResponseCode("500");
			rmb.setResponseMessage("Designation Is Already Exist For Selected Company");
		}else {
			rmb.setResponseCode("200");
			rmb.setResponseMessage("Both Not Exist");
		}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return rmb;
	}

	@Override
	public List<DesignationHierarchyMasterEntity> getDesignationHierarchyByCompId(int compId) {
		List<DesignationHierarchyMasterEntity> list = null;
		try {
			String sqlQuery ="select cu.id ,cu.companyId,  cu.createdBy,"
					+ "cu.createdOn, cu.statusId, cu.updatedOn, cu.updatedBy, "
					+ "	cm.companyName,cu.designationName,cu.designationCode,cu.hierarchyLevel, "
					+ "s.name as statusName,um.USERID as createdByName, cu.authType as authType from DESIGNATION_HIERARCHY_MASTER cu "
					+ "	left join CORP_COMPANY_MASTER cm on cm.id=cu.companyId "
					+ "left join user_master um on cu.createdBy = um.id left join statusmaster s on s.id=cu.statusId where cu.companyId=:compId order by cu.id desc ";
			list = getSession().createSQLQuery(sqlQuery)
					.addScalar("id",StandardBasicTypes.BIG_DECIMAL).addScalar("companyId",StandardBasicTypes.BIG_DECIMAL).addScalar("createdBy",StandardBasicTypes.BIG_DECIMAL).addScalar("createdOn").addScalar("statusId",StandardBasicTypes.BIG_DECIMAL).addScalar("updatedOn")
					.addScalar("updatedBy",StandardBasicTypes.BIG_DECIMAL).addScalar("companyName").addScalar("designationName").addScalar("designationCode",StandardBasicTypes.BIG_DECIMAL).addScalar("hierarchyLevel",StandardBasicTypes.BIG_DECIMAL).addScalar("statusName")
					.addScalar("createdByName").addScalar("authType")
					.setParameter("compId", compId)
					.setResultTransformer(Transformers.aliasToBean(DesignationHierarchyMasterEntity.class)).list();
			
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public boolean saveCorpUserLevels(List<CorpActivitySettingMasterEntity> corpActivityData) {
		Session session = sessionFactory.getCurrentSession();

		try {
			for (CorpActivitySettingMasterEntity aa : corpActivityData) {
				aa.setCreatedOn(new Date());
				session.save(aa);

			}

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
		return true;
	}

	@Override
	public boolean updateCorpUserLevels(List<CorpActivitySettingMasterEntity> corpActivityData) {
		Session session = sessionFactory.getCurrentSession();

		try {

			for (CorpActivitySettingMasterEntity aa : corpActivityData) {
				session.update(aa);

			}

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
		return true;
	}


	
	@Override
	public List<DesignationHierarchyMasterEntity> getAuthTypeByCompIdAndDesignationId(
			DesignationHierarchyMasterEntity corpReq) {
		List<DesignationHierarchyMasterEntity> list = null;
		try {
			String sqlQuery ="select cu.authType as authType ,cu.hierarchyLevel from DESIGNATION_HIERARCHY_MASTER cu "
					+" where cu.companyId=:compId and cu.id=:id order by cu.id desc ";
			list = getSession().createSQLQuery(sqlQuery)
					.addScalar("authType").addScalar("hierarchyLevel",StandardBasicTypes.BIG_DECIMAL)			
					.setParameter("compId", corpReq.getCompanyId()).setParameter("id", corpReq.getId())
					.setResultTransformer(Transformers.aliasToBean(DesignationHierarchyMasterEntity.class)).list();
			
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

}
