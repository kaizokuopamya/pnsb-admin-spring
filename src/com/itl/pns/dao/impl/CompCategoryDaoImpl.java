package com.itl.pns.dao.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.constant.ApplicationConstants;
import com.itl.pns.dao.CompCategoryDao;
import com.itl.pns.entity.ActivitySettingMasterEntity;
import com.itl.pns.entity.AdminWorkFlowRequestEntity;
import com.itl.pns.entity.CategoryCompanyMasterEntity;
import com.itl.pns.entity.CategoryCompanyProductEntity;
import com.itl.pns.entity.CategoryMasterEntity;
import com.itl.pns.entity.CustNotificationCategoriesEntity;
import com.itl.pns.entity.NotificationCategoriesMasterEntity;
import com.itl.pns.util.AdminWorkFlowReqUtility;
import com.itl.pns.util.EncryptDeryptUtility;
/**
 * @author shubham.lokhande
 *
 */
@Repository
@Transactional
public class CompCategoryDaoImpl implements CompCategoryDao {


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
	public boolean addCompCategoryMasterData(CategoryMasterEntity categoryMasterData) {
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
			categoryMasterData.setCreatedon(new Date());
			categoryMasterData.setUpdatedon(new Date());
			session.save(categoryMasterData);
			
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getChecker())) 
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getMaker())))  {
			List<CategoryMasterEntity> list= getAllCompCategoriesMaster();		
			ObjectMapper mapper = new ObjectMapper();
			
			AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
			adminData.setCreatedByUserId(categoryMasterData.getUser_ID());
			adminData.setCreatedByRoleId(categoryMasterData.getRole_ID());
			adminData.setPageId(categoryMasterData.getSubMenu_ID());       //set submenuId as pageid
			adminData.setCreatedBy(categoryMasterData.getCreatedby());
			adminData.setContent(mapper.writeValueAsString(categoryMasterData));   
			adminData.setActivityId(categoryMasterData.getSubMenu_ID());  //set submenuId as activityid
			adminData.setStatusId(BigDecimal.valueOf(statusId));  //97- ADMIN_CHECKER_PENDIN
			adminData.setPendingWithRoleId(BigDecimal.valueOf(6));    // 6- checker roleid
			adminData.setRemark(categoryMasterData.getRemark());
			adminData.setActivityName(categoryMasterData.getActivityName());
			adminData.setActivityRefNo(list.get(0).getId());
			adminData.setTableName("CATEGORY_MASTER");
			adminData.setUserAction(BigDecimal.valueOf(userStatus));
			adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);
			
			 //Save data to admin work flow history
			adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(categoryMasterData.getSubMenu_ID(),
					list.get(0).getId(),categoryMasterData.getCreatedby(),
					categoryMasterData.getRemark(), categoryMasterData.getRole_ID(),mapper.writeValueAsString(categoryMasterData));
			}
			
			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}

	@Override
	public boolean updateCompCategoryMasterData(CategoryMasterEntity categoryMasterData) {
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
				
				
				categoryMasterData.setUpdatedon(new Date());
			session.update(categoryMasterData);
			
			
			
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getMaker())))  {
				
			ObjectMapper mapper = new ObjectMapper();
			
				List<AdminWorkFlowRequestEntity> AdminDataList = adminWorkFlowReqUtility
						.getAdminWorkFlowDataByActivityRefNo(categoryMasterData.getId().intValue(),categoryMasterData.getSubMenu_ID());
			
			AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
			adminData.setCreatedOn(categoryMasterData.getCreatedon());
			adminData.setCreatedByUserId(categoryMasterData.getUser_ID());
			adminData.setCreatedByRoleId(categoryMasterData.getRole_ID());
			adminData.setPageId(categoryMasterData.getSubMenu_ID());       //set submenuId as pageid
			adminData.setCreatedBy(categoryMasterData.getCreatedby());
			adminData.setContent(mapper.writeValueAsString(categoryMasterData));   
			adminData.setActivityId(categoryMasterData.getSubMenu_ID());  //set submenuId as activityid
			adminData.setStatusId(BigDecimal.valueOf(statusId));  //97- ADMIN_CHECKER_PENDIN
			adminData.setPendingWithRoleId(BigDecimal.valueOf(6));    // 6- checker roleid
			adminData.setRemark(categoryMasterData.getRemark());
			adminData.setActivityName(categoryMasterData.getActivityName());
			adminData.setActivityRefNo(categoryMasterData.getId());
			adminData.setUserAction(BigDecimal.valueOf(userStatus));
			adminData.setTableName("CATEGORY_MASTER");

			if(ObjectUtils.isEmpty(AdminDataList)){
				adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);
				}else if(!ObjectUtils.isEmpty(AdminDataList)){
					adminData.setId(BigDecimal.valueOf(AdminDataList.get(0).getId().intValue()));
					adminWorkFlowReqUtility.updateAdminWorkFlowRequest(adminData);
					
					 //Save data to admin work flow history
					adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(categoryMasterData.getSubMenu_ID(),
							categoryMasterData.getId(),categoryMasterData.getCreatedby(),
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
	public List<CategoryMasterEntity> getCompCategoriesMasterById(BigInteger id) {
		List<CategoryMasterEntity> list = null;
		try {
			String sqlQuery = "select cm.id,cm.categoryName as categoryName,cm.createdon as createdon,cm.createdBy as createdby,cm.updatedOn as updatedon,cm.updatedBy as updatedby,"
					+ " cm.statusId as statusId,s.name as statusName ,aw.remark,aw.userAction "
					+ " from CATEGORY_MASTER cm inner join statusmaster s on s.id=cm.statusId  "
					+ " left join ADMINWORKFLOWREQUEST aw on aw.activityrefno=:id and aw.tablename='CATEGORY_MASTER' where cm.id=:id";

			list = getSession().createSQLQuery(sqlQuery)
					.addScalar("id").addScalar("categoryName").addScalar("createdon").addScalar("createdby").addScalar("updatedon").addScalar("updatedby")
					.addScalar("statusId").addScalar("statusName").addScalar("remark").addScalar("userAction")
					.setParameter("id", id)
					.setResultTransformer(Transformers.aliasToBean(CategoryMasterEntity.class)).list();
			
			
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public List<CategoryMasterEntity> getAllCompCategoriesMaster() {
		List<CategoryMasterEntity> list = null;
		try {
			String sqlQuery = "select cm.id,cm.categoryName as categoryName,cm.createdon as createdon,cm.createdBy as createdby,cm.updatedOn as updatedon,cm.updatedBy as updatedby,"
					+ " cm.statusId as statusId,s.name as statusName  ,um.USERID as createdByName "
					+ " from CATEGORY_MASTER cm inner join statusmaster s on s.id=cm.statusId  left join user_master um on cm.createdby = um.id"
					+ " order by cm.id desc";
			
		
			list = getSession().createSQLQuery(sqlQuery)
					.addScalar("id").addScalar("categoryName").addScalar("createdon").addScalar("createdby").addScalar("updatedon").addScalar("updatedby")
					.addScalar("statusId").addScalar("statusName").addScalar("createdByName")
					.setResultTransformer(Transformers.aliasToBean(CategoryMasterEntity.class)).list();
			
			
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}
	
	

	@Override
	public ResponseMessageBean checkCategoryExist(CategoryMasterEntity categoryMasterData) {

		ResponseMessageBean rmb = new ResponseMessageBean();
		String sqlNameExist = "select count(*) from CATEGORY_MASTER  WHERE Lower(categoryName) =:category ";
		
		
		List notiNameExist = getSession().createSQLQuery(sqlNameExist).setParameter("category", categoryMasterData.getCategoryName().toLowerCase())
				.list();
				
		if (!(notiNameExist.get(0).toString().equalsIgnoreCase("0"))) {
			rmb.setResponseCode("451");
			rmb.setResponseMessage("Category Name Already Exist");
		}else {
			rmb.setResponseCode("200");
			rmb.setResponseMessage("Both Not Exist");
		}
		return rmb;
	}

	@Override
	public ResponseMessageBean checkUpdateCategoryExist(CategoryMasterEntity categoryMasterData) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		String sqlNameExist = "select count(*) from CATEGORY_MASTER  WHERE Lower(CATEGORYNAME) =:category   AND id !=:id ";
		
		
		List notiNameExist = getSession().createSQLQuery(sqlNameExist).setParameter("category", categoryMasterData.getCategoryName().toLowerCase())
				.setParameter("id", categoryMasterData.getId()).list();
		
		if (!(notiNameExist.get(0).toString().equalsIgnoreCase("0"))) {
			rmb.setResponseCode("451");
			rmb.setResponseMessage("Category Name Already Exist");
		}else {
			rmb.setResponseCode("200");
			rmb.setResponseMessage("Both Not Exist");
		}
		return rmb;
	}

	@Override
	public boolean addComapnyMasterData(CategoryCompanyMasterEntity categorcategoryCompData) {
		Session session = sessionFactory.getCurrentSession();
		int userStatus = categorcategoryCompData.getStatusId().intValue();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(categorcategoryCompData.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(categorcategoryCompData.getActivityName());
		
		try {
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getChecker())) 
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getMaker())))  {
				categorcategoryCompData.setStatusId(BigDecimal.valueOf(statusId));   //97-  ADMIN_CHECKER_PENDIN
			}
			categorcategoryCompData.setCreatedon(new Date());
			categorcategoryCompData.setUpdatedon(new Date());
			session.save(categorcategoryCompData);
			
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getChecker())) 
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getMaker())))  {
			List<CategoryCompanyMasterEntity> list= getAllComapnyMasterData();		
			ObjectMapper mapper = new ObjectMapper();
			
			AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
			adminData.setCreatedByUserId(categorcategoryCompData.getUser_ID());
			adminData.setCreatedByRoleId(categorcategoryCompData.getRole_ID());
			adminData.setPageId(categorcategoryCompData.getSubMenu_ID());       //set submenuId as pageid
			adminData.setCreatedBy(categorcategoryCompData.getCreatedby());
			adminData.setContent(mapper.writeValueAsString(categorcategoryCompData));   
			adminData.setActivityId(categorcategoryCompData.getSubMenu_ID());  //set submenuId as activityid
			adminData.setStatusId(BigDecimal.valueOf(statusId));  //97- ADMIN_CHECKER_PENDIN
			adminData.setPendingWithRoleId(BigDecimal.valueOf(6));    // 6- checker roleid
			adminData.setRemark(categorcategoryCompData.getRemark());
			adminData.setActivityName(categorcategoryCompData.getActivityName());
			adminData.setActivityRefNo(list.get(0).getId());
			adminData.setTableName("CATEGORY_COMP_MASTER");
			adminData.setUserAction(BigDecimal.valueOf(userStatus));
			adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);
			
			 //Save data to admin work flow history
			adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(categorcategoryCompData.getSubMenu_ID(),
					list.get(0).getId(), categorcategoryCompData.getCreatedby(),
					categorcategoryCompData.getRemark(), categorcategoryCompData.getRole_ID(),mapper.writeValueAsString(categorcategoryCompData));
			}
			
			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}

	@Override
	public boolean updateComapnyMasterData(CategoryCompanyMasterEntity categorcategoryCompData) {
		Session session = sessionFactory.getCurrentSession();
		int userStatus = categorcategoryCompData.getStatusId().intValue();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(categorcategoryCompData.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(categorcategoryCompData.getActivityName());
		
			
			try {
				if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
						&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getChecker())) 
						&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getMaker())))  {
					categorcategoryCompData.setStatusId(BigDecimal.valueOf(statusId));
			}
				
				
				categorcategoryCompData.setUpdatedon(new Date());
			session.update(categorcategoryCompData);
			
			
			
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getMaker())))  {
				
			ObjectMapper mapper = new ObjectMapper();
			
				List<AdminWorkFlowRequestEntity> AdminDataList = adminWorkFlowReqUtility
						.getAdminWorkFlowDataByActivityRefNo(categorcategoryCompData.getId().intValue(),categorcategoryCompData.getSubMenu_ID());
			
			AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
			adminData.setCreatedOn(categorcategoryCompData.getCreatedon());
			adminData.setCreatedByUserId(categorcategoryCompData.getUser_ID());
			adminData.setCreatedByRoleId(categorcategoryCompData.getRole_ID());
			adminData.setPageId(categorcategoryCompData.getSubMenu_ID());       //set submenuId as pageid
			adminData.setCreatedBy(categorcategoryCompData.getCreatedby());
			adminData.setContent(mapper.writeValueAsString(categorcategoryCompData));   
			adminData.setActivityId(categorcategoryCompData.getSubMenu_ID());  //set submenuId as activityid
			adminData.setStatusId(BigDecimal.valueOf(statusId));  //97- ADMIN_CHECKER_PENDIN
			adminData.setPendingWithRoleId(BigDecimal.valueOf(6));    // 6- checker roleid
			adminData.setRemark(categorcategoryCompData.getRemark());
			adminData.setActivityName(categorcategoryCompData.getActivityName());
			adminData.setActivityRefNo(categorcategoryCompData.getId());
			adminData.setUserAction(BigDecimal.valueOf(userStatus));
			adminData.setTableName("CATEGORY_COMP_MASTER");

			if(ObjectUtils.isEmpty(AdminDataList)){
				adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);
				}else if(!ObjectUtils.isEmpty(AdminDataList)){
					adminData.setId(BigDecimal.valueOf(AdminDataList.get(0).getId().intValue()));
					adminWorkFlowReqUtility.updateAdminWorkFlowRequest(adminData);
					
					 //Save data to admin work flow history
					adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(categorcategoryCompData.getSubMenu_ID(),
							categorcategoryCompData.getId(), categorcategoryCompData.getCreatedby(),
							categorcategoryCompData.getRemark(), categorcategoryCompData.getRole_ID(),mapper.writeValueAsString(categorcategoryCompData));
				}
				
			
		}else{

			//if record is present in admin work flow then update status
			adminWorkFlowReqUtility.getCheckerDataByctivityRefId(categorcategoryCompData.getId().toBigInteger(),
					 BigInteger.valueOf(userStatus),categorcategoryCompData.getSubMenu_ID());
		}
			return true;
			}catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}

	@Override
	public List<CategoryCompanyMasterEntity> getComapnyMasterDataById(BigInteger id) {
		List<CategoryCompanyMasterEntity> list = null;
		try {
			String sqlQuery = "select cm.id,cm.companyName as companyName,cm.categoryId as categoryId,cm.createdon as createdon,cm.createdBy as createdby,cm.updatedOn as updatedon,cm.updatedBy as updatedby,"
					+ " cm.statusId as statusId,s.name as statusName  ,cmm.categoryName as categoryName ,aw.remark,aw.userAction,cm.logo as cloblogo,cm.companyInfo as companyInfo "
					+ " from CATEGORY_COMP_MASTER cm inner join statusmaster s on s.id=cm.statusId  inner join CATEGORY_MASTER cmm on cmm.id=cm.categoryId "
					+ " left join ADMINWORKFLOWREQUEST aw on aw.activityrefno=:id and aw.tablename='CATEGORY_COMP_MASTER' where cm.id=:id";

			list = getSession().createSQLQuery(sqlQuery)
					.addScalar("id").addScalar("companyName").addScalar("categoryId").addScalar("createdon").addScalar("createdby").addScalar("updatedon").addScalar("updatedby")
					.addScalar("statusId").addScalar("statusName").addScalar("categoryName").addScalar("remark").addScalar("userAction").addScalar("cloblogo").addScalar("companyInfo")
					.setParameter("id", id)
					.setResultTransformer(Transformers.aliasToBean(CategoryCompanyMasterEntity.class)).list();
			
			
			for (CategoryCompanyMasterEntity cm : list) {
				try {
					String image1 = EncryptDeryptUtility.clobStringConversion(cm.getCloblogo());
					cm.setLogo(image1);
					cm.setCloblogo(null);

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
	
	@Override
	public List<CategoryCompanyMasterEntity> getComapnyMasterDataByCategoryId(BigInteger categoryId) {
		List<CategoryCompanyMasterEntity> list = null;
		try {
			String sqlQuery = "select cm.id,cm.companyName as companyName,cm.categoryId as categoryId,cm.createdon as createdon,cm.createdBy as createdby,cm.updatedOn as updatedon,cm.updatedBy as updatedby,"
					+ " cm.statusId as statusId,s.name as statusName  ,cmm.categoryName as categoryName ,aw.remark,aw.userAction,cm.logo as cloblogo ,cm.companyInfo as companyInfo "
					+ " from CATEGORY_COMP_MASTER cm inner join statusmaster s on s.id=cm.statusId  inner join CATEGORY_MASTER cmm on cmm.id=cm.categoryId "
					+ " left join ADMINWORKFLOWREQUEST aw on aw.activityrefno=cm.id and aw.tablename='CATEGORY_COMP_MASTER' where cm.categoryId=:categoryId";

			list = getSession().createSQLQuery(sqlQuery)
					.addScalar("id").addScalar("companyName").addScalar("categoryId").addScalar("createdon").addScalar("createdby").addScalar("updatedon").addScalar("updatedby")
					.addScalar("statusId").addScalar("statusName").addScalar("categoryName").addScalar("remark").addScalar("userAction").addScalar("cloblogo").addScalar("companyInfo")
					.setParameter("categoryId", categoryId)
					.setResultTransformer(Transformers.aliasToBean(CategoryCompanyMasterEntity.class)).list();
			
			
			for (CategoryCompanyMasterEntity cm : list) {
				try {
					String image1 = EncryptDeryptUtility.clobStringConversion(cm.getCloblogo());
					cm.setLogo(image1);
					cm.setCloblogo(null);

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
	
	

	@Override
	public List<CategoryCompanyMasterEntity> getAllComapnyMasterData() {
		List<CategoryCompanyMasterEntity> list = null;
		try {
			String sqlQuery = "select cm.id,cm.companyName as companyName,cm.categoryId as categoryId,cm.createdon as createdon,cm.createdBy as createdby,cm.updatedOn as updatedon,cm.updatedBy as updatedby,"
					+ " cm.statusId as statusId,s.name as statusName  ,um.USERID as createdByName, cmm.categoryName as categoryName ,cm.logo as cloblogo,cm.companyInfo as companyInfo "
					+ " from CATEGORY_COMP_MASTER cm inner join statusmaster s on s.id=cm.statusId  left join user_master um on cm.createdby = um.id  inner join CATEGORY_MASTER cmm on cmm.id=cm.categoryId "
					+ " order by cm.id desc";
			
		
			list = getSession().createSQLQuery(sqlQuery)
					.addScalar("id").addScalar("companyName").addScalar("categoryId").addScalar("createdon").addScalar("createdby").addScalar("updatedon").addScalar("updatedby")
					.addScalar("statusId").addScalar("statusName").addScalar("createdByName").addScalar("categoryName").addScalar("cloblogo").addScalar("companyInfo")
					.setResultTransformer(Transformers.aliasToBean(CategoryCompanyMasterEntity.class)).list();
			
			for (CategoryCompanyMasterEntity cm : list) {
				try {
					String image1 = EncryptDeryptUtility.clobStringConversion(cm.getCloblogo());
					cm.setLogo(image1);
					cm.setCloblogo(null);

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

	@Override
	public ResponseMessageBean checkCompExitForCategory(CategoryCompanyMasterEntity categorcategoryCompData) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		String sqlNameExist = "select count(*) from CATEGORY_COMP_MASTER  WHERE Lower(companyName) =:compName AND categoryId =:categoryId ";
		
		
		List notiNameExist = getSession().createSQLQuery(sqlNameExist).setParameter("compName", categorcategoryCompData.getCompanyName().toLowerCase())
				.setParameter("categoryId", categorcategoryCompData.getCategoryId()).list();
				
				
		if (!(notiNameExist.get(0).toString().equalsIgnoreCase("0"))) {
			rmb.setResponseCode("451");
			rmb.setResponseMessage("Company Name Already Exist For Same Category");
		}else {
			rmb.setResponseCode("200");
			rmb.setResponseMessage("Both Not Exist");
		}
		return rmb;
	}

	@Override
	public ResponseMessageBean checkUpdateCompExitForCategory(CategoryCompanyMasterEntity categorcategoryCompData) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		String sqlNameExist = "select count(*) from CATEGORY_COMP_MASTER  WHERE Lower(companyName) =:compName AND categoryId =:categoryId AND id !=:id";
		
		
		List notiNameExist = getSession().createSQLQuery(sqlNameExist).setParameter("compName", categorcategoryCompData.getCompanyName().toLowerCase())
				.setParameter("categoryId", categorcategoryCompData.getCategoryId())
				.setParameter("id", categorcategoryCompData.getId()).list();
				
				
		if (!(notiNameExist.get(0).toString().equalsIgnoreCase("0"))) {
			rmb.setResponseCode("451");
			rmb.setResponseMessage("Company Name Already Exist For Same Category");
		}else {
			rmb.setResponseCode("200");
			rmb.setResponseMessage("Both Not Exist");
		}
		return rmb;
	}

	@Override
	public boolean addProductMasterData(CategoryCompanyProductEntity productData) {
		Session session = sessionFactory.getCurrentSession();
		int userStatus = productData.getStatusId().intValue();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(productData.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(productData.getActivityName());
		
		try {
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getChecker())) 
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getMaker())))  {
				productData.setStatusId(BigDecimal.valueOf(statusId));   //97-  ADMIN_CHECKER_PENDIN
			}
			productData.setCreatedon(new Date());
			productData.setUpdatedon(new Date());
			session.save(productData);
			
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getChecker())) 
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getMaker())))  {
			List<CategoryCompanyProductEntity> list= getAllProductMasterData();		
			ObjectMapper mapper = new ObjectMapper();
			
			AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
			adminData.setCreatedByUserId(productData.getUser_ID());
			adminData.setCreatedByRoleId(productData.getRole_ID());
			adminData.setPageId(productData.getSubMenu_ID());       //set submenuId as pageid
			adminData.setCreatedBy(productData.getCreatedby());
			adminData.setContent(mapper.writeValueAsString(productData));   
			adminData.setActivityId(productData.getSubMenu_ID());  //set submenuId as activityid
			adminData.setStatusId(BigDecimal.valueOf(statusId));  //97- ADMIN_CHECKER_PENDIN
			adminData.setPendingWithRoleId(BigDecimal.valueOf(6));    // 6- checker roleid
			adminData.setRemark(productData.getRemark());
			adminData.setActivityName(productData.getActivityName());
			adminData.setActivityRefNo(list.get(0).getId());
			adminData.setTableName("CATEGORY_COMPANY_PRODUCTS");
			adminData.setUserAction(BigDecimal.valueOf(userStatus));
			adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);
			
			 //Save data to admin work flow history
			adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(productData.getSubMenu_ID(),
					list.get(0).getId(), productData.getCreatedby(),
					productData.getRemark(), productData.getRole_ID(),mapper.writeValueAsString(productData));
			}
			
			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}

	@Override
	public boolean updateProductMasterData(CategoryCompanyProductEntity productData) {
		Session session = sessionFactory.getCurrentSession();
		int userStatus = productData.getStatusId().intValue();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(productData.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(productData.getActivityName());
		
			
			try {
				if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
						&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getChecker())) 
						&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getMaker())))  {
					productData.setStatusId(BigDecimal.valueOf(statusId));
			}
				
				
				productData.setUpdatedon(new Date());
			session.update(productData);
			
			
			
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getMaker())))  {
				
			ObjectMapper mapper = new ObjectMapper();
			
				List<AdminWorkFlowRequestEntity> AdminDataList = adminWorkFlowReqUtility
						.getAdminWorkFlowDataByActivityRefNo(productData.getId().intValue(),productData.getSubMenu_ID());
			
			AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
			adminData.setCreatedOn(productData.getCreatedon());
			adminData.setCreatedByUserId(productData.getUser_ID());
			adminData.setCreatedByRoleId(productData.getRole_ID());
			adminData.setPageId(productData.getSubMenu_ID());       //set submenuId as pageid
			adminData.setCreatedBy(productData.getCreatedby());
			adminData.setContent(mapper.writeValueAsString(productData));   
			adminData.setActivityId(productData.getSubMenu_ID());  //set submenuId as activityid
			adminData.setStatusId(BigDecimal.valueOf(statusId));  //97- ADMIN_CHECKER_PENDIN
			adminData.setPendingWithRoleId(BigDecimal.valueOf(6));    // 6- checker roleid
			adminData.setRemark(productData.getRemark());
			adminData.setActivityName(productData.getActivityName());
			adminData.setActivityRefNo(productData.getId());
			adminData.setUserAction(BigDecimal.valueOf(userStatus));
			adminData.setTableName("CATEGORY_COMPANY_PRODUCTS");

			if(ObjectUtils.isEmpty(AdminDataList)){
				adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);
				}else if(!ObjectUtils.isEmpty(AdminDataList)){
					adminData.setId(BigDecimal.valueOf(AdminDataList.get(0).getId().intValue()));
					adminWorkFlowReqUtility.updateAdminWorkFlowRequest(adminData);
					
					 //Save data to admin work flow history
					adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(productData.getSubMenu_ID(),
							productData.getId(),productData.getCreatedby(),
							productData.getRemark(), productData.getRole_ID(),mapper.writeValueAsString(productData));
				}
				
			
		}else{

			//if record is present in admin work flow then update status
			adminWorkFlowReqUtility.getCheckerDataByctivityRefId(productData.getId().toBigInteger(),
					 BigInteger.valueOf(userStatus),productData.getSubMenu_ID());
		}
			return true;
			}catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}

	@Override
	public List<CategoryCompanyProductEntity> getProductMasterDataById(BigInteger id) {
		List<CategoryCompanyProductEntity> list = null;
		try {
			String sqlQuery = "select cm.id,cm.categoryId as categoryId,cm.compId as compId,cm.productName as productName,cm.productImg as productImgClob,cm.productDescription as productDescription,"
					+ "cm.productUrl as productUrl, cm.createdon as createdon,cm.createdBy as createdby,cm.updatedOn as updatedon,cm.updatedBy as updatedby,cmm.categoryName as categoryName,cc.companyName as companyName,"
					+ " cm.statusId as statusId,s.name as statusName,aw.remark,aw.userAction "
					+ " from CATEGORY_COMPANY_PRODUCTS cm inner join statusmaster s on s.id=cm.statusId  inner join CATEGORY_MASTER cmm on cmm.id=cm.categoryId "
					+ " inner join CATEGORY_COMP_MASTER cc on cc.id=cm.compId left join ADMINWORKFLOWREQUEST aw on aw.activityrefno=:id and aw.tablename='CATEGORY_COMPANY_PRODUCTS' where cm.id=:id";

			list = getSession().createSQLQuery(sqlQuery)
					.addScalar("id").addScalar("categoryId").addScalar("compId").addScalar("productName").addScalar("productImgClob").addScalar("productDescription")
					.addScalar("productUrl").addScalar("createdon").addScalar("createdby").addScalar("updatedon").addScalar("updatedby").addScalar("categoryName").addScalar("companyName")
					.addScalar("statusId").addScalar("statusName").addScalar("remark").addScalar("userAction")
					.setParameter("id", id)
					.setResultTransformer(Transformers.aliasToBean(CategoryCompanyProductEntity.class)).list();
			
			
			
			for (CategoryCompanyProductEntity cm : list) {
				try {
					String image1 = EncryptDeryptUtility.clobStringConversion(cm.getProductImgClob());
					cm.setProductImg(image1);
					cm.setProductImgClob(null);

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

	@Override
	public List<CategoryCompanyProductEntity> getAllProductMasterData() {
		List<CategoryCompanyProductEntity> list = null;
		try {
			String sqlQuery = "select cm.id,cm.categoryId as categoryId,cm.compId as compId,cm.productName as productName,cm.productImg as productImgClob,cm.productDescription as productDescription,"
					+ "cm.productUrl as productUrl, cm.createdon as createdon,cm.createdBy as createdby,cm.updatedOn as updatedon,cm.updatedBy as updatedby,cmm.categoryName as categoryName,cc.companyName as companyName,"
					+ " cm.statusId as statusId,s.name as statusName  ,um.USERID as createdByName "
					+ " from CATEGORY_COMPANY_PRODUCTS cm inner join statusmaster s on s.id=cm.statusId  left join user_master um on cm.createdby = um.id  inner join CATEGORY_MASTER cmm on cmm.id=cm.categoryId "
					+ " inner join CATEGORY_COMP_MASTER cc on cc.id=cm.compId order by cm.id desc";
			
		
			list = getSession().createSQLQuery(sqlQuery)
					.addScalar("id").addScalar("categoryId").addScalar("compId").addScalar("productName").addScalar("productImgClob").addScalar("productDescription")
					.addScalar("productUrl").addScalar("createdon").addScalar("createdby").addScalar("updatedon").addScalar("updatedby").addScalar("categoryName").addScalar("companyName")
					.addScalar("statusId").addScalar("statusName").addScalar("createdByName")
					.setResultTransformer(Transformers.aliasToBean(CategoryCompanyProductEntity.class)).list();
			
			for (CategoryCompanyProductEntity cm : list) {
				try {
					String image1 = EncryptDeryptUtility.clobStringConversion(cm.getProductImgClob());
					cm.setProductImg(image1);
					cm.setProductImgClob(null);

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

	@Override
	public ResponseMessageBean checkProductExitForCompany(CategoryCompanyProductEntity productData) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		String sqlNameExist = "select count(*) from CATEGORY_COMPANY_PRODUCTS  WHERE Lower(productName) =:productName AND categoryId =:categoryId AND compId =:compId";
		
		
		List notiNameExist = getSession().createSQLQuery(sqlNameExist).setParameter("productName", productData.getProductName().toLowerCase())
				.setParameter("categoryId", productData.getCategoryId())
				.setParameter("compId", productData.getCompId()).list();
				
				
		if (!(notiNameExist.get(0).toString().equalsIgnoreCase("0"))) {
			rmb.setResponseCode("451");
			rmb.setResponseMessage("Product Name Already Exist For Select Company And Category");
		}else {
			rmb.setResponseCode("200");
			rmb.setResponseMessage("Both Not Exist");
		}
		return rmb;
	}

	@Override
	public ResponseMessageBean checkUpdateProductExitForCompany(CategoryCompanyProductEntity productData) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		String sqlNameExist = "select count(*) from CATEGORY_COMPANY_PRODUCTS  WHERE Lower(productName) =:productName AND categoryId =:categoryId AND compId =:compId AND id !=:id";
		
		
		List notiNameExist = getSession().createSQLQuery(sqlNameExist).setParameter("productName", productData.getProductName().toLowerCase())
				.setParameter("categoryId", productData.getCategoryId())
				.setParameter("compId", productData.getCompId())
				.setParameter("id", productData.getId()).list();
	
		if (!(notiNameExist.get(0).toString().equalsIgnoreCase("0"))) {
			rmb.setResponseCode("451");
			rmb.setResponseMessage("Product Name Already Exist For Select Company And Category");
		}else {
			rmb.setResponseCode("200");
			rmb.setResponseMessage("Both Not Exist");
		}
		return rmb;
	}


	
	
	
	
}
