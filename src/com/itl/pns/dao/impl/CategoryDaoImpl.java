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
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.constant.ApplicationConstants;
import com.itl.pns.dao.CategoryDao;
import com.itl.pns.entity.ActivitySettingMasterEntity;
import com.itl.pns.entity.AdminWorkFlowRequestEntity;
import com.itl.pns.entity.CustNotificationCategoriesEntity;
import com.itl.pns.entity.NotificationCategoriesMasterEntity;
import com.itl.pns.util.AdminWorkFlowReqUtility;

/**
 * @author shubham.lokhande
 *
 */
@Repository
@Transactional
@Service
public class CategoryDaoImpl implements CategoryDao {

	static Logger LOGGER = Logger.getLogger(CategoryDaoImpl.class);
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;	
	
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Autowired
	AdminWorkFlowReqUtility adminWorkFlowReqUtility;
	
	@Override
	public boolean addCategoriesMaster(NotificationCategoriesMasterEntity categoryMasterData) {
		Session session = sessionFactory.getCurrentSession();
		int userStatus = categoryMasterData.getStatusId().intValue();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(categoryMasterData.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(categoryMasterData.getActivityName());
		
		try {
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getChecker())) 
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getMaker())))  {
				categoryMasterData.setStatusId(BigDecimal.valueOf(statusId));   //97-  ADMIN_CHECKER_PENDIN
			}
			categoryMasterData.setCreatedOn(new Date());
			categoryMasterData.setUpdatedOn(new Date());
			session.save(categoryMasterData);
			
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getChecker())) 
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getMaker())))  {
			List<NotificationCategoriesMasterEntity> list= getAllCategoriesMaster();		
			ObjectMapper mapper = new ObjectMapper();
			
			AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
			adminData.setAppId(categoryMasterData.getAppId());
			adminData.setCreatedByUserId(categoryMasterData.getUser_ID());
			adminData.setCreatedByRoleId(categoryMasterData.getRole_ID());
			adminData.setPageId(categoryMasterData.getSubMenu_ID());       //set submenuId as pageid
			adminData.setCreatedBy(categoryMasterData.getCreatedBy());
			adminData.setContent(mapper.writeValueAsString(categoryMasterData));   
			adminData.setActivityId(categoryMasterData.getSubMenu_ID());  //set submenuId as activityid
			adminData.setStatusId(BigDecimal.valueOf(statusId));  //97- ADMIN_CHECKER_PENDIN
			adminData.setPendingWithRoleId(BigDecimal.valueOf(6));    // 6- checker roleid
			adminData.setRemark(categoryMasterData.getRemark());
			adminData.setActivityName(categoryMasterData.getActivityName());
			adminData.setActivityRefNo(list.get(0).getId());
			adminData.setTableName("NOTIFICATIONCATEGORIESMASTER");
			adminData.setUserAction(BigDecimal.valueOf(userStatus));
			adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);
			
			 //Save data to admin work flow history
			adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(categoryMasterData.getSubMenu_ID(),
					list.get(0).getId(), categoryMasterData.getCreatedBy(),
					categoryMasterData.getRemark(), categoryMasterData.getRole_ID(),mapper.writeValueAsString(categoryMasterData));
			}
			
			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}

	@Override
	public boolean updateCategoriesMaster(NotificationCategoriesMasterEntity categoryMasterData) {
		Session session = sessionFactory.getCurrentSession();
		int userStatus = categoryMasterData.getStatusId().intValue();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(categoryMasterData.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(categoryMasterData.getActivityName());
		
			
			try {
				if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
						&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getChecker())) 
						&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getMaker())))  {
					categoryMasterData.setStatusId(BigDecimal.valueOf(statusId));
			}
				
				
				categoryMasterData.setUpdatedOn(new Date());
			session.update(categoryMasterData);
			
			
			
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getMaker())))  {
				
			ObjectMapper mapper = new ObjectMapper();
			
				List<AdminWorkFlowRequestEntity> AdminDataList = adminWorkFlowReqUtility
						.getAdminWorkFlowDataByActivityRefNo(categoryMasterData.getId().intValue(),categoryMasterData.getSubMenu_ID());
			
			AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
			adminData.setAppId(categoryMasterData.getAppId());
			adminData.setCreatedOn(categoryMasterData.getCreatedOn());
			adminData.setCreatedByUserId(categoryMasterData.getUser_ID());
			adminData.setCreatedByRoleId(categoryMasterData.getRole_ID());
			adminData.setPageId(categoryMasterData.getSubMenu_ID());       //set submenuId as pageid
			adminData.setCreatedBy(categoryMasterData.getCreatedBy());
			adminData.setContent(mapper.writeValueAsString(categoryMasterData));   
			adminData.setActivityId(categoryMasterData.getSubMenu_ID());  //set submenuId as activityid
			adminData.setStatusId(BigDecimal.valueOf(statusId));  //97- ADMIN_CHECKER_PENDIN
			adminData.setPendingWithRoleId(BigDecimal.valueOf(6));    // 6- checker roleid
			adminData.setRemark(categoryMasterData.getRemark());
			adminData.setActivityName(categoryMasterData.getActivityName());
			adminData.setActivityRefNo(categoryMasterData.getId());
			adminData.setUserAction(BigDecimal.valueOf(userStatus));
			adminData.setTableName("NOTIFICATIONCATEGORIESMASTER");

			if(ObjectUtils.isEmpty(AdminDataList)){
				adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);
				}else if(!ObjectUtils.isEmpty(AdminDataList)){
					adminData.setId(BigDecimal.valueOf(AdminDataList.get(0).getId().intValue()));
					adminWorkFlowReqUtility.updateAdminWorkFlowRequest(adminData);
					
					 //Save data to admin work flow history
					adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(categoryMasterData.getSubMenu_ID(),
						categoryMasterData.getId(), categoryMasterData.getCreatedBy(),
							categoryMasterData.getRemark(), categoryMasterData.getRole_ID(),mapper.writeValueAsString(categoryMasterData));
				}
				
			
		}else{

			//if record is present in admin work flow then update status
			adminWorkFlowReqUtility.getCheckerDataByctivityRefId(categoryMasterData.getId().toBigInteger(),
					 BigInteger.valueOf(userStatus),categoryMasterData.getSubMenu_ID());
		}
			return true;
			}catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}

	@Override
	public List<NotificationCategoriesMasterEntity> getCategoriesMasterById(int id) {
		List<NotificationCategoriesMasterEntity> list = null;
		try {
			String sqlQuery = "  select cm.id,cm.categoryName,cm.createdOn,cm.createdBy,cm.updatedOn,cm.updatedBy,cm.statusId,"
					+ "	cm.appId,s.name as statusName,a.shortname as appName ,aw.remark,aw.userAction from NOTIFICATIONCATEGORIESMASTER cm "
					+ " inner join statusmaster s on s.id=cm.statusId inner join appmaster a on a.id=cm.appid "
					+ " left join ADMINWORKFLOWREQUEST aw on aw.activityrefno=cm.id and aw.tablename='NOTIFICATIONCATEGORIESMASTER' where cm.id=:id";

			list = getSession().createSQLQuery(sqlQuery)
					.addScalar("id").addScalar("categoryName").addScalar("createdOn").addScalar("createdBy").addScalar("updatedOn").addScalar("updatedBy")
					.addScalar("statusId").addScalar("appId").addScalar("statusName").addScalar("appName").addScalar("remark").addScalar("userAction")
					.setParameter("id", id)
					.setResultTransformer(Transformers.aliasToBean(NotificationCategoriesMasterEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public List<NotificationCategoriesMasterEntity> getAllCategoriesMaster() {
		List<NotificationCategoriesMasterEntity> list = null;
		try {
			String sqlQuery = " select cm.id,cm.categoryName,cm.createdOn,cm.createdBy,cm.updatedOn,cm.updatedBy,cm.statusId,"
					+ "	cm.appId,s.name as statusName,a.shortname as appName , um.USERID as createdByName from NOTIFICATIONCATEGORIESMASTER cm "
					+ "  inner join statusmaster s on s.id=cm.statusId left join appmaster a on a.id=cm.appid "
					+ " left join user_master um on cm.createdby = um.id order by cm.createdOn desc ";

			list = getSession().createSQLQuery(sqlQuery)
					.addScalar("id").addScalar("categoryName").addScalar("createdOn").addScalar("createdBy").addScalar("updatedOn").addScalar("updatedBy")
					.addScalar("statusId").addScalar("appId").addScalar("statusName").addScalar("appName").addScalar("createdByName")
					.setResultTransformer(Transformers.aliasToBean(NotificationCategoriesMasterEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public boolean addCustNotificationCategories(CustNotificationCategoriesEntity categoryMasterData) {
		Session session = sessionFactory.getCurrentSession();
		int userStatus = categoryMasterData.getStatusId().intValue();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(categoryMasterData.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(categoryMasterData.getActivityName());
		
		try {
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getChecker())) 
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getMaker())))  {
				categoryMasterData.setStatusId(BigDecimal.valueOf(statusId));   //97-  ADMIN_CHECKER_PENDIN
			}
			categoryMasterData.setCreatedOn(new Date());
			categoryMasterData.setUpdatedOn(new Date());
			session.save(categoryMasterData);
			
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getChecker())) 
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getMaker())))  {
			List<CustNotificationCategoriesEntity> list= getAllCustNotificationCategories();		
			ObjectMapper mapper = new ObjectMapper();
			
			AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
			adminData.setAppId(categoryMasterData.getAppId());
			adminData.setCreatedByUserId(categoryMasterData.getUser_ID());
			adminData.setCreatedByRoleId(categoryMasterData.getRole_ID());
			adminData.setPageId(categoryMasterData.getSubMenu_ID());       //set submenuId as pageid
			adminData.setCreatedBy(categoryMasterData.getCreatedBy());
			adminData.setContent(mapper.writeValueAsString(categoryMasterData));   
			adminData.setActivityId(categoryMasterData.getSubMenu_ID());  //set submenuId as activityid
			adminData.setStatusId(BigDecimal.valueOf(statusId));  //97- ADMIN_CHECKER_PENDIN
			adminData.setPendingWithRoleId(BigDecimal.valueOf(6));    // 6- checker roleid
			adminData.setRemark(categoryMasterData.getRemark());
			adminData.setActivityName(categoryMasterData.getActivityName());
			adminData.setActivityRefNo(list.get(0).getId());
			adminData.setTableName("CUSTNOTIFICATIONCATEGORIES");
			adminData.setUserAction(BigDecimal.valueOf(userStatus));
			adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);
			
			 //Save data to admin work flow history
			adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(categoryMasterData.getSubMenu_ID(),
					list.get(0).getId(), categoryMasterData.getCreatedBy(),
					categoryMasterData.getRemark(), categoryMasterData.getRole_ID(),mapper.writeValueAsString(categoryMasterData));
			}
			
			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}

	@Override
	public boolean updateCustNotificationCategories(CustNotificationCategoriesEntity categoryMasterData) {
		Session session = sessionFactory.getCurrentSession();
		int userStatus = categoryMasterData.getStatusId().intValue();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(categoryMasterData.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(categoryMasterData.getActivityName());
		
			
			try {
				if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
						&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getChecker())) 
						&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getMaker())))  {
					categoryMasterData.setStatusId(BigDecimal.valueOf(statusId));
			}
				
				
				categoryMasterData.setUpdatedOn(new Date());
			session.update(categoryMasterData);
			
			
			
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getMaker())))  {
				
			ObjectMapper mapper = new ObjectMapper();
			
				List<AdminWorkFlowRequestEntity> AdminDataList = adminWorkFlowReqUtility
						.getAdminWorkFlowDataByActivityRefNo(categoryMasterData.getId().intValue(),categoryMasterData.getSubMenu_ID());
			
			AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
			adminData.setAppId(categoryMasterData.getAppId());
			adminData.setCreatedOn(categoryMasterData.getCreatedOn());
			adminData.setCreatedByUserId(categoryMasterData.getUser_ID());
			adminData.setCreatedByRoleId(categoryMasterData.getRole_ID());
			adminData.setPageId(categoryMasterData.getSubMenu_ID());       //set submenuId as pageid
			adminData.setCreatedBy(BigDecimal.valueOf(categoryMasterData.getCreatedBy().intValue()));
			adminData.setContent(mapper.writeValueAsString(categoryMasterData));   
			adminData.setActivityId(categoryMasterData.getSubMenu_ID());  //set submenuId as activityid
			adminData.setStatusId(BigDecimal.valueOf(statusId));  //97- ADMIN_CHECKER_PENDIN
			adminData.setPendingWithRoleId(BigDecimal.valueOf(6));    // 6- checker roleid
			adminData.setRemark(categoryMasterData.getRemark());
			adminData.setActivityName(categoryMasterData.getActivityName());
			adminData.setActivityRefNo(categoryMasterData.getId());
			adminData.setUserAction(BigDecimal.valueOf(userStatus));
			adminData.setTableName("CUSTNOTIFICATIONCATEGORIES");

			if(ObjectUtils.isEmpty(AdminDataList)){
				adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);
				}else if(!ObjectUtils.isEmpty(AdminDataList)){
					adminData.setId(BigDecimal.valueOf(AdminDataList.get(0).getId().intValue()));
					adminWorkFlowReqUtility.updateAdminWorkFlowRequest(adminData);
					
					 //Save data to admin work flow history
					adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(categoryMasterData.getSubMenu_ID(),
							categoryMasterData.getId(), categoryMasterData.getCreatedBy(),
							categoryMasterData.getRemark(), categoryMasterData.getRole_ID(),mapper.writeValueAsString(categoryMasterData));
				}
				
			
		}else{

			//if record is present in admin work flow then update status
			adminWorkFlowReqUtility.getCheckerDataByctivityRefId(categoryMasterData.getId().toBigInteger(),
					 BigInteger.valueOf(userStatus),categoryMasterData.getSubMenu_ID());
		}
			return true;
			}catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}

	@Override
	public List<CustNotificationCategoriesEntity> getCustNotificationCategoriesById(int id) {
		List<CustNotificationCategoriesEntity> list = null;
		try {
			String sqlQuery = "select cm.id,cm.categoryId,cm.customerId,cm.createdOn,cm.createdBy,cm.updatedOn,cm.updatedBy,cm.statusId,"
					+ " cm.fromTime,cm.toTime,cm.appId,s.name as statusName,a.shortname as appName ,aw.remark,aw.userAction ,nm.CATEGORYNAME as categoryName from CUSTNOTIFICATIONCATEGORIES cm "
					+ " inner join statusmaster s on s.id=cm.statusId inner join appmaster a on a.id=cm.appid  "
					+ " inner join NOTIFICATIONCATEGORIESMASTER nm on nm.id=cm.categoryId "
					+ "left join ADMINWORKFLOWREQUEST aw on aw.activityrefno=:id and aw.tablename='CUSTNOTIFICATIONCATEGORIES' where cm.id=:id";


			list = getSession().createSQLQuery(sqlQuery)
					.addScalar("id",StandardBasicTypes.BIG_DECIMAL).addScalar("categoryId",StandardBasicTypes.BIG_DECIMAL)
					.addScalar("customerId",StandardBasicTypes.BIG_DECIMAL).addScalar("createdOn").addScalar("createdBy",StandardBasicTypes.BIG_DECIMAL).addScalar("updatedOn")
					.addScalar("updatedBy",StandardBasicTypes.BIG_DECIMAL).addScalar("statusId",StandardBasicTypes.BIG_DECIMAL).addScalar("fromTime")
					.addScalar("toTime").addScalar("appId",StandardBasicTypes.BIG_DECIMAL).addScalar("statusName")
					.addScalar("appName").addScalar("remark").addScalar("categoryName").addScalar("userAction",StandardBasicTypes.BIG_DECIMAL)
					.setParameter("id", id)
					.setResultTransformer(Transformers.aliasToBean(CustNotificationCategoriesEntity.class)).list();
			
			
			
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public List<CustNotificationCategoriesEntity> getAllCustNotificationCategories() {
		List<CustNotificationCategoriesEntity> list = null;
		try {
			String sqlQuery = "select cm.id,cm.categoryId,cm.customerId,cm.createdOn,cm.createdBy,cm.updatedOn,cm.updatedBy,cm.statusId,"
					+ " cm.fromTime,cm.toTime,cm.appId,s.name as statusName,a.shortname as appName ,um.USERID as createdByName,"
					+ "nm.CATEGORYNAME as categoryName, cu.CUSTOMERNAME as customerName from CUSTNOTIFICATIONCATEGORIES cm "
					+ " inner join statusmaster s on s.id=cm.statusId inner join appmaster a on a.id=cm.appid "
					+ " inner join NOTIFICATIONCATEGORIESMASTER nm on nm.id=cm.categoryId  inner join customers cu on cu.id=cm.customerId "
					+ "inner join user_master um on cm.createdby = um.id order by cm.createdOn desc";

			list = getSession().createSQLQuery(sqlQuery)
					.addScalar("id",StandardBasicTypes.BIG_DECIMAL).addScalar("categoryId",StandardBasicTypes.BIG_DECIMAL)
					.addScalar("customerId",StandardBasicTypes.BIG_DECIMAL).addScalar("createdOn").addScalar("createdBy",StandardBasicTypes.BIG_DECIMAL).addScalar("updatedOn")
					.addScalar("updatedBy",StandardBasicTypes.BIG_DECIMAL).addScalar("statusId",StandardBasicTypes.BIG_DECIMAL).addScalar("fromTime")
					.addScalar("toTime").addScalar("appId",StandardBasicTypes.BIG_DECIMAL).addScalar("statusName")
					.addScalar("appName").addScalar("createdByName").addScalar("categoryName").addScalar("customerName")
					.setResultTransformer(Transformers.aliasToBean(CustNotificationCategoriesEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}
	
	@Override
	@SuppressWarnings("rawtypes")
	public ResponseMessageBean checkCategoryExist(NotificationCategoriesMasterEntity categoryMasterData) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		String sqlNameExist = "select count(*) from NOTIFICATIONCATEGORIESMASTER  WHERE Lower(CATEGORYNAME) =:category  AND  appId=:appId ";
		
		
		List notiNameExist = getSession().createSQLQuery(sqlNameExist).setParameter("category", categoryMasterData.getCategoryName().toLowerCase())
				.setParameter("appId", categoryMasterData.getAppId()).list();
		
		
		if (!(notiNameExist.get(0).toString().equalsIgnoreCase("0"))) {
			rmb.setResponseCode("451");
			rmb.setResponseMessage("Name Already Exist For Same Channel");
		}else {
			rmb.setResponseCode("200");
			rmb.setResponseMessage("Both Not Exist");
		}
		return rmb;
	}
	
	
	@Override
	@SuppressWarnings("rawtypes")
	public ResponseMessageBean checkUpdateCategoryExist(NotificationCategoriesMasterEntity categoryMasterData) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		String sqlNameExist = "select count(*) from NOTIFICATIONCATEGORIESMASTER  WHERE Lower(CATEGORYNAME) =:category  AND  appId=:appId AND id !=:id ";
		
		
		List notiNameExist = getSession().createSQLQuery(sqlNameExist).setParameter("category", categoryMasterData.getCategoryName().toLowerCase())
				.setParameter("id", categoryMasterData.getId())
				.setParameter("appId", categoryMasterData.getAppId()).list();
		
		if (!(notiNameExist.get(0).toString().equalsIgnoreCase("0"))) {
			rmb.setResponseCode("451");
			rmb.setResponseMessage("Name Already Exist For Same Channel");
		}else {
			rmb.setResponseCode("200");
			rmb.setResponseMessage("Both Not Exist");
		}
		return rmb;
	}
}
