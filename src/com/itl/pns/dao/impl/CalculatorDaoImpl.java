package com.itl.pns.dao.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.constant.ApplicationConstants;
import com.itl.pns.dao.AdminWorkFlowDao;
import com.itl.pns.dao.CalculatorDao;
import com.itl.pns.entity.ActivitySettingMasterEntity;
import com.itl.pns.entity.AdminWorkFlowRequestEntity;
import com.itl.pns.entity.CalculatorFormulaEntity;
import com.itl.pns.entity.CalculatorMasterEntity;
import com.itl.pns.util.AdminWorkFlowReqUtility;
import com.itl.pns.util.EmailUtil;
import com.itl.pns.util.EncryptDeryptUtility;




@Repository
@Transactional
public class CalculatorDaoImpl implements CalculatorDao{

	static Logger LOGGER = Logger.getLogger(CalculatorDaoImpl.class);

	@Autowired
	AdminWorkFlowDao adminWorkFlowDao;

	
	@Autowired
	EmailUtil util;
	
	@Autowired
	AdminWorkFlowReqUtility adminWorkFlowReqUtility;
	
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;
	
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	 
	@Override
	public List<CalculatorMasterEntity> getCalculatorMasterById(int id) {
		List<CalculatorMasterEntity> list = null;
		try {
			String sqlQuery = "select cm.id,cm.calculatorName as calculatorName,CAST(cm.calculatorDescription AS VARCHAR(1000)) AS calculatorDescription, cm.seqNumber as seqNumber, cm.createdby as createdby,"
					+ "  cm.createdon as createdon, cm.statusId as statusId , cm.appId as appId,cm.updatedby as updatedby, cm.updatedon as updatedon,"
					+ "  s.name as statusName,a.shortname as productName, aw.remark,aw.userAction from calculatormaster_prd cm inner join statusmaster s on s.Id=cm.statusid  "
					+ "  inner join appmaster a on a.id=cm.appid "
					+ " left join ADMINWORKFLOWREQUEST aw on aw.activityrefno=cm.id and aw.tablename='CALCULATORMASTER_PRD' where cm.id =:id";

			list = getSession().createSQLQuery(sqlQuery)
					.addScalar("id")
					.addScalar("calculatorName")
					.addScalar("calculatorDescription")
					.addScalar("seqNumber")
					.addScalar("createdby")
					.addScalar("createdon")
					.addScalar("updatedby")
					.addScalar("updatedon")
					.addScalar("statusName")
					.addScalar("statusId")
					.addScalar("userAction")
					.addScalar("remark")
					.addScalar("appId")
					.addScalar("productName").setParameter("id", id)
					.setResultTransformer(Transformers.aliasToBean(CalculatorMasterEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public List<CalculatorMasterEntity> getCalculatorMasterDetails() {
		List<CalculatorMasterEntity> list = null;
		try {
			String sqlQuery = "select cm.id,cm.calculatorName as calculatorName,CAST(cm.calculatorDescription AS VARCHAR(1000)) AS calculatorDescription, cm.seqNumber as seqNumber, cm.createdby as createdby,"
					+ "  cm.createdon as createdon, cm.statusId as statusId , cm.appId as appId,cm.updatedby as updatedby, cm.updatedon as updatedon,"
					+ "  s.name as statusName,a.shortname as productName, um.USERID as createdByName from calculatormaster_prd cm inner join statusmaster s on s.Id=cm.statusid  "
					+ "  inner join appmaster a on a.id=cm.appid inner join user_master um on cm.createdby = um.id order by cm.id desc";

			list = getSession().createSQLQuery(sqlQuery)
					.addScalar("id")     
					.addScalar("calculatorName")
					.addScalar("calculatorDescription")
					.addScalar("seqNumber")
					.addScalar("createdby")
					.addScalar("createdon")
					.addScalar("updatedby")
					.addScalar("updatedon")
					.addScalar("statusName")
					.addScalar("statusId")
					.addScalar("appId")
					.addScalar("productName")
					.addScalar("createdByName")
					.setResultTransformer(Transformers.aliasToBean(CalculatorMasterEntity.class)).list();
			
			
			
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public boolean addCalculatorMasterDetails(CalculatorMasterEntity calculatorMaster) {
		Session session = sessionFactory.getCurrentSession();
		int userStatus = calculatorMaster.getStatusId().intValue();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(calculatorMaster.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(calculatorMaster.getActivityName());
		
		try {
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getChecker())) 
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getMaker())))  {
				calculatorMaster.setStatusId(BigDecimal.valueOf(statusId));   //97-  ADMIN_CHECKER_PENDIN
			}
			calculatorMaster.setCreatedon(new Date());
			calculatorMaster.setUpdatedon(new Date());
			session.save(calculatorMaster);
			
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getChecker())) 
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getMaker())))  {
			List<CalculatorMasterEntity> list= getCalculatorMasterDetails();		
			ObjectMapper mapper = new ObjectMapper();
			
			AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
			adminData.setAppId(calculatorMaster.getAppId());
			adminData.setCreatedByUserId(calculatorMaster.getUser_ID());
			adminData.setCreatedByRoleId(calculatorMaster.getRole_ID());
			adminData.setPageId(calculatorMaster.getSubMenu_ID());       //set submenuId as pageid
			adminData.setCreatedBy(calculatorMaster.getCreatedby());
			adminData.setContent(mapper.writeValueAsString(calculatorMaster));   
			adminData.setActivityId(calculatorMaster.getSubMenu_ID());  //set submenuId as activityid
			adminData.setStatusId(BigDecimal.valueOf(statusId));  //97- ADMIN_CHECKER_PENDIN
			adminData.setPendingWithRoleId(BigDecimal.valueOf(6));    // 6- checker roleid
			adminData.setRemark(calculatorMaster.getRemark());
			adminData.setActivityName(calculatorMaster.getActivityName());
			adminData.setActivityRefNo(list.get(0).getId());
			adminData.setTableName("CALCULATORMASTER_PRD");
			adminData.setUserAction(BigDecimal.valueOf(userStatus));
			adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);
			
			 //Save data to admin work flow history
			adminWorkFlowReqUtility.saveToAdminWorkFlowHistoryContent(calculatorMaster.getSubMenu_ID(),
				list.get(0).getId(), calculatorMaster.getCreatedby(),
					calculatorMaster.getRemark(), calculatorMaster.getRole_ID(),mapper.writeValueAsString(calculatorMaster));
			}
			
			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}

	@Override
	public boolean updateCalculatorMasterDetails(CalculatorMasterEntity calculatorMaster) {
		Session session = sessionFactory.getCurrentSession();
		int userStatus = calculatorMaster.getStatusId().intValue();
			String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(calculatorMaster.getRole_ID().intValue());
			int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
			List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
					.isMakerCheckerPresentForReq(calculatorMaster.getActivityName());
			
			try {
				if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
						&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getChecker())) 
						&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getMaker())))  {
				calculatorMaster.setStatusId(BigDecimal.valueOf(statusId));
			}
			calculatorMaster.setUpdatedon(new Date());
			session.update(calculatorMaster);
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getMaker())))  {
				
			ObjectMapper mapper = new ObjectMapper();
			
				List<AdminWorkFlowRequestEntity> AdminDataList = adminWorkFlowReqUtility
						.getAdminWorkFlowDataByActivityRefNo(calculatorMaster.getId().intValue(),calculatorMaster.getSubMenu_ID());
			
			AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
			adminData.setAppId(calculatorMaster.getAppId());
			adminData.setCreatedOn(calculatorMaster.getCreatedon());
			adminData.setCreatedByUserId(calculatorMaster.getUser_ID());
			adminData.setCreatedByRoleId(calculatorMaster.getRole_ID());
			adminData.setPageId(calculatorMaster.getSubMenu_ID());       //set submenuId as pageid
			adminData.setCreatedBy(calculatorMaster.getCreatedby());
			adminData.setContent(mapper.writeValueAsString(calculatorMaster));   
			adminData.setActivityId(calculatorMaster.getSubMenu_ID());  //set submenuId as activityid
			adminData.setStatusId(BigDecimal.valueOf(statusId));  //97- ADMIN_CHECKER_PENDIN
			adminData.setPendingWithRoleId(BigDecimal.valueOf(6));    // 6- checker roleid
			adminData.setRemark(calculatorMaster.getRemark());
			adminData.setActivityName(calculatorMaster.getActivityName());
			adminData.setActivityRefNo(calculatorMaster.getId());
			adminData.setUserAction(BigDecimal.valueOf(userStatus));
			adminData.setTableName("CALCULATORMASTER_PRD");

			if(ObjectUtils.isEmpty(AdminDataList)){
				adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);
				}else if(!ObjectUtils.isEmpty(AdminDataList)){
					adminData.setId(BigDecimal.valueOf(AdminDataList.get(0).getId().intValue()));
					adminWorkFlowReqUtility.updateAdminWorkFlowRequest(adminData);
				}
			
			 //Save data to admin work flow history
			adminWorkFlowReqUtility.saveToAdminWorkFlowHistoryContent(calculatorMaster.getSubMenu_ID(),
				calculatorMaster.getId(),calculatorMaster.getCreatedby(),
					calculatorMaster.getRemark(), calculatorMaster.getRole_ID(),mapper.writeValueAsString(calculatorMaster));
				
			
		}else{

			//if record is present in admin work flow then update status
			adminWorkFlowReqUtility.getCheckerDataByctivityRefId(calculatorMaster.getId().toBigInteger(),
					 BigInteger.valueOf(userStatus),calculatorMaster.getSubMenu_ID());
		}
			return true;
			}catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}
	
	@Override
	public List<CalculatorFormulaEntity> getCalculatorFormulaById(int id) {
		List<CalculatorFormulaEntity> list = null;
		try {
			String sqlQuery = " select cf.id,cf.calculatorId as calculatorId, CAST(cf.calculatorFormula AS VARCHAR(1000)) AS calculatorFormula ,cf.RATES_CHARGES as ratesCharges, cf.createdby as createdby,"
					+ " cf.createdon as createdon,cf.statusId as statusId, cf.appId as appId,cf.updatedby as updatedby,cf.updatedon as updatedon,cs.calculatorname as calculatorname,"
					+ "  s.name as statusName,aw.remark,aw.userAction from calculatorformula_prd cf  inner join statusmaster s on s.Id=cf.statusid "
					+ " inner join calculatormaster_prd cs on cs.Id=cf.calculatorId "
					+ " left join ADMINWORKFLOWREQUEST aw on aw.activityrefno=cf.id and aw.tablename='CALCULATORFORMULA_PRD' where cf.id=:id ";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("calculatorId")
					.addScalar("calculatorFormula").addScalar("ratesCharges").addScalar("createdby")
					.addScalar("calculatorname").addScalar("createdon").addScalar("statusId").addScalar("appId")
					.addScalar("updatedby").addScalar("updatedon").addScalar("remark").addScalar("userAction")
					.addScalar("statusName").setParameter("id", id)
					.setResultTransformer(Transformers.aliasToBean(CalculatorFormulaEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		

		return list;
	}

	@Override
	public List<CalculatorFormulaEntity> getCalculatorFormulaDetails() {
		List<CalculatorFormulaEntity> list = null;
		try {
			String sqlQuery = "select cf.id,cf.calculatorId as calculatorId,CAST(cf.calculatorFormula AS VARCHAR(1000)) AS calculatorFormula,cf.RATES_CHARGES as ratesCharges, cf.createdby as createdby,"
					+ "cf.createdon as createdon,cf.statusId as statusId, cf.appId as appId,cf.updatedby as updatedby,cf.updatedon as updatedon,"
					+ " s.name as statusName,cs.calculatorname as calculatorname, um.USERID as createdByName from calculatorformula_prd cf  inner join statusmaster s on s.Id=cf.statusid "
					+ " inner join calculatormaster_prd cs on cs.Id=cf.calculatorId inner join user_master um on cf.createdby = um.id order by cf.createdon desc ";

			list = getSession().createSQLQuery(sqlQuery)
					.addScalar("id")
					.addScalar("calculatorId")
					.addScalar("calculatorFormula")
					.addScalar("ratesCharges")
					.addScalar("createdby")
					.addScalar("createdon")
					.addScalar("statusId")
					.addScalar("appId")
					.addScalar("updatedby")
					.addScalar("updatedon")
					.addScalar("statusName")
					.addScalar("calculatorname")
					.addScalar("createdByName")
					.setResultTransformer(Transformers.aliasToBean(CalculatorFormulaEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		
		

		return list;
	}

	@Override
	public boolean addCalculatorFormulaDetails(CalculatorFormulaEntity calculatorFormulaData) {
		Session session = sessionFactory.getCurrentSession();
		int userStatus = calculatorFormulaData.getStatusId().intValue();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(calculatorFormulaData.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(calculatorFormulaData.getActivityName());

		try {
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getChecker())) 
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getMaker())))  {
				calculatorFormulaData.setStatusId(BigDecimal.valueOf(statusId));   //97-  ADMIN_CHECKER_PENDIN
			}
	
			calculatorFormulaData.setCreatedon(new Date());
			calculatorFormulaData.setUpdatedon(new Date());
			session.save(calculatorFormulaData);
			
			
			
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getChecker())) 
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getMaker())))  {
				
				List<CalculatorFormulaEntity> list= getCalculatorFormulaDetails();
				ObjectMapper mapper = new ObjectMapper();
				
				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
				adminData.setAppId(calculatorFormulaData.getAppId());
				adminData.setCreatedByUserId(calculatorFormulaData.getUser_ID());
				adminData.setCreatedByRoleId(calculatorFormulaData.getRole_ID());
				adminData.setPageId(calculatorFormulaData.getSubMenu_ID());       //set submenuId as pageid
				adminData.setCreatedBy(calculatorFormulaData.getCreatedby());
				adminData.setContent(mapper.writeValueAsString(calculatorFormulaData));   
				adminData.setActivityId(calculatorFormulaData.getSubMenu_ID());  //set submenuId as activityid
				adminData.setStatusId(BigDecimal.valueOf(statusId));  //97- ADMIN_CHECKER_PENDIN
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6));    // 6- checker roleid
				adminData.setRemark(calculatorFormulaData.getRemark());
				adminData.setActivityName(calculatorFormulaData.getActivityName());
				adminData.setActivityRefNo(list.get(0).getId());
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminData.setTableName("CALCULATORFORMULA_PRD");
				adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);
				
                   //Save data to admin work flow history
				adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(calculatorFormulaData.getSubMenu_ID(),
						list.get(0).getId(), new BigDecimal(calculatorFormulaData.getCreatedby().toBigInteger()),
						calculatorFormulaData.getRemark(), calculatorFormulaData.getRole_ID(),mapper.writeValueAsString(calculatorFormulaData));
				}
			
			
			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}

	@Override
	public boolean updateCalculatorFormulaDetails(CalculatorFormulaEntity calculatorFormulaData) {
		Session session = sessionFactory.getCurrentSession();
		int userStatus = calculatorFormulaData.getStatusId().intValue();
	    String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(calculatorFormulaData.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(calculatorFormulaData.getActivityName());

		try {
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getChecker())) 
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getMaker())))  {
				calculatorFormulaData.setStatusId(BigDecimal.valueOf(statusId));   //97-  ADMIN_CHECKER_PENDIN
			}
			
			calculatorFormulaData.setUpdatedon(new Date());
			session.update(calculatorFormulaData);
			
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getChecker())) 
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getMaker())))  {

				saveOrUpdatCalciFormulaWorkFlow(calculatorFormulaData, statusId,userStatus);
			}else{
			
			//if record is present in admin work flow then update status
			adminWorkFlowReqUtility.getCheckerDataByctivityRefId(calculatorFormulaData.getId().toBigInteger(),
					 BigInteger.valueOf(userStatus),calculatorFormulaData.getSubMenu_ID());
			}
			
			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public ResponseMessageBean isCalculatorExit(CalculatorMasterEntity calculatorFormulaData) {
		
		ResponseMessageBean rmb = new ResponseMessageBean();
		try{
		String sqlCalculartorNameExist = " SELECT count(*) FROM CALCULATORMASTER_PRD WHERE Lower(CALCULATORNAME) =:calciName";
		String sqlSequeanceExist = "   SELECT count(*) FROM CALCULATORMASTER_PRD WHERE SEQNUMBER =:seqNum";
		
		
		List calciNameExit = getSession().createSQLQuery(sqlCalculartorNameExist).setParameter("calciName", calculatorFormulaData.getCalculatorName().toLowerCase()).list();
		List seqNoExit = getSession().createSQLQuery(sqlSequeanceExist).setParameter("seqNum", calculatorFormulaData.getSeqNumber()).list();

		if (!(calciNameExit.get(0).toString().equalsIgnoreCase("0"))) {
			rmb.setResponseCode("500");
			rmb.setResponseMessage("Calculator Name Is Already Exist");
		} else if (!(seqNoExit.get(0).toString().equalsIgnoreCase("0"))) {
			rmb.setResponseCode("500");
			rmb.setResponseMessage("Sequence Number Is Already Exist");
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
	public ResponseMessageBean updateCalculatorExit(CalculatorMasterEntity calculatorFormulaData) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		try{
		String sqlCalculartorNameExist = " SELECT count(*) FROM CALCULATORMASTER_PRD WHERE Lower(CALCULATORNAME) =:calciName  AND  ID!=:id";
		String sqlSequeanceExist = "   SELECT count(*) FROM CALCULATORMASTER_PRD WHERE SEQNUMBER =:seqNum AND ID!=:id";
		
		
		List calciNameExit = getSession().createSQLQuery(sqlCalculartorNameExist).setParameter("id", calculatorFormulaData.getId())
				.setParameter("calciName", calculatorFormulaData.getCalculatorName().toLowerCase()).list();
		List seqNoExit = getSession().createSQLQuery(sqlSequeanceExist).setParameter("id", calculatorFormulaData.getId())
				.setParameter("seqNum", calculatorFormulaData.getSeqNumber()).list();

		if (!(calciNameExit.get(0).toString().equalsIgnoreCase("0"))) {
			rmb.setResponseCode("500");
			rmb.setResponseMessage("Calculator Name Is Already Exist");
		} else if (!(seqNoExit.get(0).toString().equalsIgnoreCase("0"))) {
			rmb.setResponseCode("500");
			rmb.setResponseMessage("Sequence Number Is Already Exist");
		} else {
			rmb.setResponseCode("200");
			rmb.setResponseMessage("Both Not Exist");
		}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return rmb;
	}
	
	
	public boolean saveOrUpdatCalciFormulaWorkFlow(CalculatorFormulaEntity calculatorFormulaData, int statusId, int userStatus){
		try {
		
		List<AdminWorkFlowRequestEntity> AdminDataList= adminWorkFlowReqUtility.getAdminWorkFlowDataByActivityRefNo(calculatorFormulaData.getId().intValue(),calculatorFormulaData.getSubMenu_ID());
			
			ObjectMapper mapper = new ObjectMapper();
			AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
			adminData.setAppId(calculatorFormulaData.getAppId());
			adminData.setCreatedOn(calculatorFormulaData.getCreatedon());
			adminData.setCreatedByUserId(calculatorFormulaData.getUser_ID());
			adminData.setCreatedByRoleId(calculatorFormulaData.getRole_ID());
			adminData.setPageId(calculatorFormulaData.getSubMenu_ID());       //set submenuId as pageid
			adminData.setCreatedBy(calculatorFormulaData.getCreatedby());
			adminData.setContent(mapper.writeValueAsString(calculatorFormulaData));   
			adminData.setActivityId(calculatorFormulaData.getSubMenu_ID());  //set submenuId as activityid
			adminData.setStatusId(BigDecimal.valueOf(statusId));  //97- ADMIN_CHECKER_PENDIN
			adminData.setPendingWithRoleId(BigDecimal.valueOf(6));    // 6- checker roleid
			adminData.setRemark(calculatorFormulaData.getRemark());
			adminData.setActivityName(calculatorFormulaData.getActivityName());
			adminData.setActivityRefNo(calculatorFormulaData.getId());
			adminData.setUserAction(BigDecimal.valueOf(userStatus));
			adminData.setTableName("CALCULATORFORMULA_PRD");
			if(ObjectUtils.isEmpty(AdminDataList)){
			adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);
			}else if(!ObjectUtils.isEmpty(AdminDataList)){
				adminData.setId(BigDecimal.valueOf(AdminDataList.get(0).getId().intValue()));
				adminWorkFlowReqUtility.updateAdminWorkFlowRequest(adminData);	
				
			}
			 //Save data to admin work flow history
			adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(calculatorFormulaData.getSubMenu_ID(),
					calculatorFormulaData.getId(), calculatorFormulaData.getCreatedby(),
					calculatorFormulaData.getRemark(), calculatorFormulaData.getRole_ID(),mapper.writeValueAsString(calculatorFormulaData));
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return true;
	}
	
}
