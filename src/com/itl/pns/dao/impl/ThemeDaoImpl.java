package com.itl.pns.dao.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
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
import com.itl.pns.bean.ThemesBean;
import com.itl.pns.constant.ApplicationConstants;
import com.itl.pns.corp.entity.DesignationHierarchyMasterEntity;
import com.itl.pns.dao.ThemeDao;
import com.itl.pns.entity.ActivitySettingMasterEntity;
import com.itl.pns.entity.AdminWorkFlowRequestEntity;
import com.itl.pns.entity.ThemeMenuOptionEntity;
import com.itl.pns.entity.ThemeNameEntity;
import com.itl.pns.entity.ThemeSideBarBackgroundEntity;
import com.itl.pns.entity.ThemeSideBarColorEntity;
import com.itl.pns.entity.ThemesEntity;
import com.itl.pns.util.AdminWorkFlowReqUtility;

@Repository
@Transactional
public class ThemeDaoImpl implements ThemeDao {

	static Logger LOGGER = Logger.getLogger(ThemeDaoImpl.class);
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;
	
	@Autowired
	AdminWorkFlowReqUtility adminWorkFlowReqUtility;
	
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public List<ThemesBean> getThemeNamesById(int id) {
		String sqlQuery = "";
		List<ThemesBean> list = null;
		try {
			sqlQuery = "select tm.ID,tm.DESCRIPTION,tm.DETAILS,tm.statusid as statusid,tm.createdby as createdby,tm.createdon,tm.updatedon,"
					+ "tm.updatedby as updatedby from THEMENAMES_PRD tm where id=:id";
			list = getSession().createSQLQuery(sqlQuery)
					.addScalar("ID").addScalar("DESCRIPTION").addScalar("DETAILS").addScalar("statusid").addScalar("createdby").addScalar("createdon")
					.addScalar("updatedon").addScalar("updatedby")
					.setParameter("id", id)
					.setResultTransformer(Transformers.aliasToBean(ThemesBean.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return list;
	}


	@Override
	public List<ThemesBean> getAllThemeNames() {
		String sqlQuery = "";
		List<ThemesBean> list = null;
		try {
			sqlQuery = "select tm.ID,tm.DESCRIPTION,tm.DETAILS,tm.statusid as statusid,tm.createdby as createdby,tm.createdon,tm.updatedon,"
					+ "tm.updatedby as updatedby from THEMENAMES_PRD tm order by tm.createdon desc";
			list = getSession().createSQLQuery(sqlQuery)
					.addScalar("ID").addScalar("DESCRIPTION").addScalar("DETAILS").addScalar("statusid").addScalar("createdby").addScalar("createdon")
					.addScalar("updatedon").addScalar("updatedby")
					.setResultTransformer(Transformers.aliasToBean(ThemesBean.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return list;
	}

	@Override
	public List<ThemesBean> getThemesSideBarColorById(int id) {
		String sqlQuery = "";
		List<ThemesBean> list = null;
		try {
			sqlQuery = "select tm.ID,tm.DESCRIPTION,tm.DETAILS,tm.statusid as statusid,tm.createdby as createdby,tm.createdon,tm.updatedon,"
					+ "tm.updatedby as updatedby from THEMESIDEBARCOLOR tm where id=:id";
			list = getSession().createSQLQuery(sqlQuery)
					.addScalar("ID").addScalar("DESCRIPTION").addScalar("DETAILS").addScalar("statusid").addScalar("createdby").addScalar("createdon")
					.addScalar("updatedon").addScalar("updatedby")				
					.setParameter("id", id)
					.setResultTransformer(Transformers.aliasToBean(ThemesBean.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return list;
	}

	@Override
	public List<ThemesBean> getAllThemesSideBarColor() {
		String sqlQuery = "";
		List<ThemesBean> list = null;
		try {
			sqlQuery = "select tm.ID,tm.DESCRIPTION,tm.DETAILS,tm.statusid as statusid,tm.createdby as createdby,tm.createdon,tm.updatedon,"
					+ "tm.updatedby as updatedby from THEMESIDEBARCOLOR tm order by tm.createdon desc";
			list = getSession().createSQLQuery(sqlQuery)
					.addScalar("ID").addScalar("DESCRIPTION").addScalar("DETAILS").addScalar("statusid").addScalar("createdby").addScalar("createdon")
					.addScalar("updatedon").addScalar("updatedby")
					.setResultTransformer(Transformers.aliasToBean(ThemesBean.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return list;
	}


	@Override
	public List<ThemesBean> getThemesBackgroundColorById(int id) {
		String sqlQuery = "";
		List<ThemesBean> list = null;
		try {
			sqlQuery = "select tm.ID,tm.DESCRIPTION,tm.DETAILS,tm.statusid as statusid,tm.createdby as createdby,tm.createdon,tm.updatedon,"
					+ "tm.updatedby as updatedby from THEMESIDEBARBACKGROUND tm where id=:id";
			list = getSession().createSQLQuery(sqlQuery)
					.addScalar("ID").addScalar("DESCRIPTION").addScalar("DETAILS").addScalar("statusid").addScalar("createdby").addScalar("createdon")
					.addScalar("updatedon").addScalar("updatedby")					
					.setParameter("id", id)
					.setResultTransformer(Transformers.aliasToBean(ThemesBean.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return list;
	}

	@Override
	public List<ThemesBean> getAllThemesBackgroundColor() {
		String sqlQuery = "";
		List<ThemesBean> list = null;
		try {
			sqlQuery = "select tm.ID,tm.DESCRIPTION,tm.DETAILS,tm.statusid as statusid,tm.createdby as createdby,tm.createdon,tm.updatedon,"
					+ "tm.updatedby as updatedby from THEMESIDEBARBACKGROUND tm order by tm.createdon desc";
			list = getSession().createSQLQuery(sqlQuery)
					.addScalar("ID").addScalar("DESCRIPTION").addScalar("DETAILS").addScalar("statusid").addScalar("createdby").addScalar("createdon")
					.addScalar("updatedon").addScalar("updatedby")
					.setResultTransformer(Transformers.aliasToBean(ThemesBean.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return list;
	}


	@Override
	public List<ThemesBean> getThemeMenuOptionById(int id) {
		String sqlQuery = "";
		List<ThemesBean> list = null;
		try {
			sqlQuery = "select tm.ID,tm.DESCRIPTION,tm.DETAILS,tm.statusid as statusid,tm.createdby as createdby,tm.createdon,tm.updatedon,"
					+ "tm.updatedby as updatedby from THEMEMENUOPTION tm where id=:id";
			list = getSession().createSQLQuery(sqlQuery)
					.addScalar("ID").addScalar("DESCRIPTION").addScalar("DETAILS").addScalar("statusid").addScalar("createdby").addScalar("createdon")
					.addScalar("updatedon").addScalar("updatedby")
					.setParameter("id", id)
					.setResultTransformer(Transformers.aliasToBean(ThemesBean.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return list;
	}

	@Override
	public List<ThemesBean> getAllThemeMenuOption() {
		String sqlQuery = "";
		List<ThemesBean> list = null;
		try {
			sqlQuery = "select tm.ID,tm.DESCRIPTION,tm.DETAILS,tm.statusid as statusid,tm.createdby as createdby,tm.createdon,tm.updatedon,"
					+ "tm.updatedby as updatedby from THEMEMENUOPTION tm order by tm.createdon desc";
			list = getSession().createSQLQuery(sqlQuery)
					.addScalar("ID").addScalar("DESCRIPTION").addScalar("DETAILS").addScalar("statusid").addScalar("createdby").addScalar("createdon")
					.addScalar("updatedon").addScalar("updatedby")
					.setResultTransformer(Transformers.aliasToBean(ThemesBean.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return list;
	}

	@Override
	public boolean addThemeNames(ThemeNameEntity themeData) {
		Session session = sessionFactory.getCurrentSession();
		try {
			themeData.setStatusid(BigDecimal.valueOf(3));
			themeData.setCreatedon(new Date());
			themeData.setUpdatedon(new Date());
			session.save(themeData);
			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}

	@Override
	public boolean addThemeSideBarColor(ThemeSideBarColorEntity themeData) {
		Session session = sessionFactory.getCurrentSession();
		try {
			themeData.setStatusid(BigDecimal.valueOf(3));
			themeData.setCreatedon(new Date());
			themeData.setUpdatedon(new Date());
			session.save(themeData);
			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}

	@Override
	public boolean addThemesBackgroundColor(ThemeSideBarBackgroundEntity themeData) {
		Session session = sessionFactory.getCurrentSession();
		try {
			themeData.setStatusid(BigDecimal.valueOf(3));
			themeData.setCreatedon(new Date());
			themeData.setUpdatedon(new Date());
			session.save(themeData);
			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}
	@Override
	public boolean addAllThemeMenuOption(ThemeMenuOptionEntity themeData) {
		Session session = sessionFactory.getCurrentSession();
		try {
			themeData.setStatusid(BigDecimal.valueOf(3));
			themeData.setCreatedon(new Date());
			themeData.setUpdatedon(new Date());
			session.save(themeData);
			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}

	@Override
	public boolean saveTheme(ThemesEntity themeData) {
		Session session = sessionFactory.getCurrentSession();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(themeData.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		int userStatus = themeData.getStatusid().intValue();
		List<ActivitySettingMasterEntity> activityMasterData =  adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(themeData.getActivityName());
		try {
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getChecker())) 
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				themeData.setStatusid(BigDecimal.valueOf(statusId));  
			}
		
			themeData.setCreatedon(new Date());
			themeData.setUpdatedon(new Date());
			session.save(themeData);
			
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getChecker())) 
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				List<ThemesEntity> list= getAllThemes();		
				ObjectMapper mapper = new ObjectMapper();
				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
				
				adminData.setCreatedByUserId(themeData.getUser_ID());
				adminData.setCreatedByRoleId(themeData.getRole_ID());
				adminData.setPageId(themeData.getSubMenu_ID());       //set submenuId as pageid
				//adminData.setCreatedBy(themeData.getCreatedby());
				adminData.setContent(mapper.writeValueAsString(themeData));   
				adminData.setActivityId(themeData.getSubMenu_ID());  //set submenuId as activityid
				adminData.setStatusId(BigDecimal.valueOf(statusId));  //97- ADMIN_CHECKER_PENDIN
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6));    // 6- checker roleid
				adminData.setRemark(themeData.getRemark());
				adminData.setActivityName(themeData.getActivityName());
				adminData.setActivityRefNo(list.get(0).getId());
				adminData.setTableName("THEMES");
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);
				
				//Save data to admin work flow history
				adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(themeData.getSubMenu_ID(),
				list.get(0).getId(), themeData.getCreatedby(),
				themeData.getRemark(), themeData.getRole_ID(),mapper.writeValueAsString(themeData));
				}
			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}

	@Override
	public boolean updateThemes(ThemesEntity themesData) {
		Session session = sessionFactory.getCurrentSession();
		int userStatus = themesData.getStatusid().intValue();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(themesData.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData =  adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(themesData.getActivityName());
		
		try {
			if(roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getChecker())) 
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				themesData.setStatusid(BigDecimal.valueOf(statusId));   //97-  ADMIN_CHECKER_PENDIN
			}
			
			themesData.setUpdatedon(new Date());
			session.update(themesData);
			
			if(roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getChecker())) 
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				
				List<AdminWorkFlowRequestEntity> AdminDataList = adminWorkFlowReqUtility
						.getAdminWorkFlowDataByActivityRefNo(themesData.getId().intValue(),themesData.getSubMenu_ID());
				
				ObjectMapper mapper = new ObjectMapper();
				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
			
				adminData.setCreatedOn(themesData.getCreatedon());
				adminData.setCreatedByUserId(themesData.getUser_ID());
				adminData.setCreatedByRoleId(themesData.getRole_ID());
				adminData.setPageId(themesData.getSubMenu_ID());       //set submenuId as pageid
				//adminData.setCreatedBy(themesData.getCreatedby());
				adminData.setContent(mapper.writeValueAsString(themesData));   
				adminData.setActivityId(themesData.getSubMenu_ID());  //set submenuId as activityid
				adminData.setStatusId(BigDecimal.valueOf(statusId));  //97- ADMIN_CHECKER_PENDIN
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6));    // 6- checker roleid
				adminData.setRemark(themesData.getRemark());
				adminData.setActivityName(themesData.getActivityName());
				adminData.setActivityRefNo(themesData.getId());
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminData.setTableName("THEMES");
				if(ObjectUtils.isEmpty(AdminDataList)){
				adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);
				}else if(!ObjectUtils.isEmpty(AdminDataList)){
					adminData.setId(BigDecimal.valueOf(AdminDataList.get(0).getId().intValue()));
					adminWorkFlowReqUtility.updateAdminWorkFlowRequest(adminData);
		
				}
				//Save data to admin work flow history
				adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(themesData.getSubMenu_ID(),
				themesData.getId(), themesData.getCreatedby(),
				themesData.getRemark(), themesData.getRole_ID(),mapper.writeValueAsString(themesData));
			}else{

				//if record is present in admin work flow then update status
				 adminWorkFlowReqUtility.getCheckerDataByctivityRefId(themesData.getId().toBigInteger(),
				 BigInteger.valueOf(userStatus),themesData.getSubMenu_ID());
			}
			
			
			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}
	
	@Column(name = "updatedon")
    Date updatedon;
	@Override
	public List<ThemesEntity> getAllThemes() {
		String sqlQuery = "";
		List<ThemesEntity> list = null;
		try {
			sqlQuery = " select t.id , t.themeName , t.themeSideBarColor, t.themeSideBarBackground, t.fromDate, t.themeMenuOption as themeMenuOptions, t.toDate,"
					+ " t.forcedToAll, t.statusid, t.createdby, t.createdon, t.updatedby, um.USERID as createdByName ,s.name as statusName from Themes t "
					+ "inner join statusmaster s on s.id=t.statusid inner join user_master um on t.createdby = um.id order by t.createdon desc";
			list = getSession().createSQLQuery(sqlQuery)
					.addScalar("id").addScalar("themeName").addScalar("themeSideBarColor").addScalar("themeSideBarBackground").addScalar("fromDate").addScalar("themeMenuOptions")
					.addScalar("toDate").addScalar("forcedToAll").addScalar("statusid").addScalar("createdby").addScalar("createdon").addScalar("updatedby")
					.addScalar("createdByName").addScalar("statusName")
					.setResultTransformer(Transformers.aliasToBean(ThemesEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return list;
	}

	@Override
	public List<ThemesEntity> getThemesById(int id) {
		String sqlQuery = "";
		List<ThemesEntity> list = null;
		try {
			sqlQuery = " select t.id , t.themeName , t.themeSideBarColor, t.themeSideBarBackground, t.fromDate, t.themeMenuOption as themeMenuOptions, t.toDate,aw.remark, aw.userAction,"
					+ " t.forcedToAll, t.statusid, t.createdby, t.createdon, t.updatedby from Themes t left join ADMINWORKFLOWREQUEST aw on aw.activityrefno=t.id and aw.tablename='THEMES' where t.id=:id ";
			list = getSession().createSQLQuery(sqlQuery)
					.addScalar("id").addScalar("themeName").addScalar("themeSideBarColor").addScalar("themeSideBarBackground").addScalar("fromDate")
					.addScalar("themeMenuOptions").addScalar("remark").addScalar("userAction")
					.addScalar("toDate").addScalar("forcedToAll").addScalar("statusid").addScalar("createdby").addScalar("createdon").addScalar("updatedby")
					.setParameter("id", id)
					.setResultTransformer(Transformers.aliasToBean(ThemesEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return list;
	}

	@Override
	public boolean updateThemeNames(ThemeNameEntity themeData) {
		Session session = sessionFactory.getCurrentSession();
		try {
			
			themeData.setUpdatedon(new Date());
			session.update(themeData);
			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}

	@Override
	public boolean updateThemeSideBarColor(ThemeSideBarColorEntity themeData) {
		Session session = sessionFactory.getCurrentSession();
		try {
			
			themeData.setUpdatedon(new Date());
			session.update(themeData);
			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}
	@Override
	public boolean updateThemesBackgroundColor(ThemeSideBarBackgroundEntity themeData) {
		Session session = sessionFactory.getCurrentSession();
		try {
			
			themeData.setUpdatedon(new Date());
			session.update(themeData);
			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}

	@Override
	public boolean updateThemeMenuOption(ThemeMenuOptionEntity themeData) {
		Session session = sessionFactory.getCurrentSession();
		try {
			
			themeData.setUpdatedon(new Date());
			session.update(themeData);
			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}

	@Override
	public ResponseMessageBean checkIsThemeExistForSameDate(ThemesEntity themeData) {

		ResponseMessageBean rmb = new ResponseMessageBean();
		try {
			String sqlNameExist = "SELECT count(*) FROM THEMES WHERE Lower(THEMENAME) =:themename AND FROMDATE=:fromDate AND TODATE=:toDate ";

			List nameExit = getSession().createSQLQuery(sqlNameExist).setParameter("fromDate", themeData.getFromDateStr())
					.setParameter("toDate", themeData.getToDateStr())
					.setParameter("themename", themeData.getThemeName().toLowerCase()).list();

			if (!(nameExit.get(0).toString().equalsIgnoreCase("0"))) {
				rmb.setResponseCode("500");
				rmb.setResponseMessage("Theme Is Already Exist For Selected Date");
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
	public ResponseMessageBean checkUpdateIsThemeExistForSameDate(ThemesEntity themeData) {

		ResponseMessageBean rmb = new ResponseMessageBean();
		try {
			String sqlNameExist = "SELECT count(*) FROM THEMES WHERE Lower(THEMENAME) =:themename AND FROMDATE=:fromDate AND TODATE=:toDate AND ID !=:id";

			List nameExit = getSession().createSQLQuery(sqlNameExist).setParameter("fromDate", themeData.getFromDateStr())
					.setParameter("toDate", themeData.getToDateStr()).setParameter("id", themeData.getId())
					.setParameter("themename", themeData.getThemeName().toLowerCase()).list();

			if (!(nameExit.get(0).toString().equalsIgnoreCase("0"))) {
				rmb.setResponseCode("500");
				rmb.setResponseMessage("Theme Is Already Exist For Selected Date");
			} else {
				rmb.setResponseCode("200");
				rmb.setResponseMessage("Both Not Exist");
			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return rmb;
	}

}
