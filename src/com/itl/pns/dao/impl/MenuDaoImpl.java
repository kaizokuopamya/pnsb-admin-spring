package com.itl.pns.dao.impl;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.itl.pns.bean.LinkRolesBean;
import com.itl.pns.bean.MainMenuBean;
import com.itl.pns.bean.MainMenuDetailsBean;
import com.itl.pns.bean.MenuBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.bean.RoleBean;
import com.itl.pns.constant.ApplicationConstants;
import com.itl.pns.dao.MenuDao;
import com.itl.pns.entity.AccessRights;
import com.itl.pns.entity.CustomizationMenuSubmenuEntity;
import com.itl.pns.entity.CustomizationSubmenuEntity;
import com.itl.pns.entity.CustomizeMenuSubMenuEntity;
import com.itl.pns.entity.CustomizeMenus;
import com.itl.pns.entity.CustomizeSubmenuEntity;
import com.itl.pns.entity.MainMenu;
import com.itl.pns.entity.SubMenuEntity;


@SuppressWarnings("unchecked")
@Repository("MenuDao")
@Transactional
public class MenuDaoImpl implements MenuDao{
	
	static Logger LOGGER = Logger.getLogger(MenuDaoImpl.class);
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;
	
	
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MenuBean> getMenuDetails(int roleId) {
		List<MenuBean> list = null;
		try {

			String sqlQuery = "select distinct(m.id),m.menu_name as menuName,m.menu_desc as menuDescription,"
					+ "m.menu_link as menuUrl,m.menu_logo as menuLogo ,m.is_active as isActive from submenu m "
					+ "inner join link_roles li on li.menu_id=m.id where li.role_id=:roleid"
					+ " and m.is_active=0 and li.privilege_id=1 order by m.menu_desc";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("menuName")
					.addScalar("menuDescription").addScalar("menuUrl").addScalar("menuLogo").addScalar("isActive")
					.setParameter("roleid", roleId).setResultTransformer(Transformers.aliasToBean(MenuBean.class))
					.list();

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MenuBean> getMenuDetailsAccess() {
		List<MenuBean> list = null;
		try {
			String sqlQuery = "select m.id,m.menu_name as menuName,m.menu_desc as menuDescription,m.menu_link as menuUrl,m.menu_logo as menuLogo ,"
					+ "m.statusId as statusId from submenu m where m.statusId=:statusIds order by m.menu_desc ";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("menuName").addScalar("statusId")
					.addScalar("menuDescription").addScalar("menuUrl").addScalar("menuLogo")
					.setParameter("statusIds", ApplicationConstants.ACTIVE_SATUS)	
					.setResultTransformer(Transformers.aliasToBean(MenuBean.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AccessRights> getAccessRightsForRoleId(BigInteger roleId) {
		List<AccessRights> list=null;
		try{
		String sqlQuery= "select li.id as id,li.role_id as roleId ,li.statusId as statusId ,li.privilege_id as privilegeId,li.menu_id as menuId from LINK_ROLES li where li.role_id=:roleid";
		 list = getSession().createSQLQuery(sqlQuery)
				    .addScalar("id")
					.addScalar("roleId")
					.addScalar("statusId")
					.addScalar("privilegeId")
					.addScalar("menuId").setParameter("roleid", roleId)
					.setResultTransformer(Transformers.aliasToBean(AccessRights.class)).list();
		}
		catch(Exception e){
			LOGGER.info("Exception:", e);
		}
	
		return list;
	
	}

	@Override
	public boolean deleteExistingRole(BigInteger roleId) {
		boolean success=true;
		try{
		String sqlQuery= "delete from LINK_ROLES where role_id=:roleid";
		getSession().createSQLQuery(sqlQuery).setParameter("roleid", roleId).executeUpdate();
		}
		catch(Exception e){
			LOGGER.info("Exception:", e);
			return false;
		}
	
		return success;
	
	}
	
	
	@Override
	public List<LinkRolesBean> findAllByroleId(BigInteger roleId) {
		List<LinkRolesBean> list=null;
		try{
	
			String sqlQuery="SELECT LI.ID,LI.IS_ACTIVE,LI.MENU_ID,LI.ROLE_ID,LI.PRIVILEGE_ID,LI.UPDATED_BY,LI.UPDATED_ON,LI.CREATED_BY,LI.CREATED_ON,"
					+ "M.MENU_NAME FROM LINK_ROLES LI INNER JOIN SUBMENU M ON M.ID=LI.MENU_ID WHERE ROLE_ID=:roleid ";
		list = getSession().createSQLQuery(sqlQuery).setParameter("roleid", roleId).setResultTransformer(Transformers.aliasToBean(LinkRolesBean.class)).list();
		}
		catch(Exception e){
			LOGGER.info("Exception:", e);
			return null;
		}
	
		return list;
	
	}
	@Override
	public List<LinkRolesBean> findPreviligeByroleId(BigInteger moduleId,BigInteger roleId) {
		List<LinkRolesBean> list=null;
		try{
	
		String sqlQuery= "SELECT LI.PRIVILEGE_ID, P.PRIVILEGENAME,M.MENU_NAME  FROM LINK_ROLES LI INNER JOIN "
				+ " PRIVILEGE P ON P.ID=LI.PRIVILEGE_ID INNER JOIN SUBMENU M ON M.ID=LI.MENU_ID WHERE MENU_ID=:menuid AND ROLE_ID=:roleid";
				list = getSession().createSQLQuery(sqlQuery).setParameter("menuid", moduleId).setParameter("roleid",roleId)
				.setResultTransformer(Transformers.aliasToBean(LinkRolesBean.class)).list();
		}
		catch(Exception e){
			LOGGER.info("Exception:", e);
			return null;
		}
	
		return list;
	
	}
	@Override
	public List<CustomizeMenus> getAllPSBMenusList() {
		List<CustomizeMenus> list=null;
		try{
		String sqlQuery= "select  distinct(c.modulename) as moduleName from customizationmenus c";
		 list = getSession().createSQLQuery(sqlQuery)
					.addScalar("moduleName")
					.setResultTransformer(Transformers.aliasToBean(CustomizeMenus.class)).list();
		}
		catch(Exception e){
			LOGGER.info("Exception:", e);
		}
	
		return list;
	
	}
	
	@Override
	public List<CustomizeMenus> getPSBMenusForAppId(BigInteger appId,BigInteger roleid) {
		List<CustomizeMenus> list=null;
		try{
		String sqlQuery= "select cm.id as id,cm.modulename as moduleName,cm.statusid as statusId, cm.menuImage as menuImageString,cm.type from customizationmenus cm "
				+ "inner join appmaster a on a.id=cm.appid where  cm.appid=:appid AND cm.roleid=:roleid";
		 list = getSession().createSQLQuery(sqlQuery)
				    .addScalar("id",StandardBasicTypes.BIG_INTEGER)
					.addScalar("moduleName").addScalar("menuImageString",StandardBasicTypes.STRING).addScalar("type")
					.addScalar("statusId",StandardBasicTypes.BIG_INTEGER)
					.setParameter("appid", appId).setParameter("roleid", roleid)
					.setResultTransformer(Transformers.aliasToBean(CustomizeMenus.class)).list();
		}
		catch(Exception e){
			LOGGER.info("Exception:", e);
		}
	
		return list;
	
	}
	
	@Override
	public List<CustomizeMenus> getPSBAppMenuRightById(BigInteger id) {
		List<CustomizeMenus> list=null;
		try{
		String sqlQuery= "select cm.id as id,cm.modulename as moduleName,cm.statusid as statusId,cm.appId,cm.roleId, cm.menuImage as menuImageString,cm.type, a.shortname as appName from customizationmenus cm "
				+ " inner join appmaster a on a.id=cm.appid where  cm.id=:id";
		 list = getSession().createSQLQuery(sqlQuery)
				    .addScalar("id",StandardBasicTypes.BIG_INTEGER).addScalar("appId",StandardBasicTypes.BIG_INTEGER).addScalar("roleId",StandardBasicTypes.BIG_INTEGER).addScalar("appName")
					.addScalar("moduleName").addScalar("menuImageString",StandardBasicTypes.STRING).addScalar("type")
					.addScalar("statusId",StandardBasicTypes.BIG_INTEGER)
					.setParameter("id", id)
					.setResultTransformer(Transformers.aliasToBean(CustomizeMenus.class)).list();
		}
		catch(Exception e){
			LOGGER.info("Exception:", e);
		}
	
		return list;
	
	}
	
	@Override
	public List<MenuBean> findMenuId(int roleId) {
		List<MenuBean> list=null;
		try{
		String sqlQuery="select distinct(menuid),m.menuname ,m.statusId as  isActive from menu_submenu sb "
				+ "inner join main_menu  m on sb.menuid=m.id where sb.roleid=:roleid and m.statusId=:stsId order by m.menuname";
		list = getSession().createSQLQuery(sqlQuery)
				.addScalar("menuid",StandardBasicTypes.BIG_DECIMAL)
				.addScalar("menuName").addScalar("isActive").setParameter("roleid", roleId).setParameter("stsId", ApplicationConstants.ACTIVE_SATUS)
				.setResultTransformer(Transformers.aliasToBean(MenuBean.class)).list();
					
		}
		catch(Exception e){
			LOGGER.info("Exception:", e);
		}
	
		return list;
		
	}
	
	@Override
	public List<CustomizeMenuSubMenuEntity> findCustomizeMenuId(int bankType,int appId) {
		List<CustomizeMenuSubMenuEntity> list=null;
		String bankingType = null;
		if(bankType == 0){
			bankingType ="RETAIL";
		}else if(bankType == 1){
			bankingType ="CORPORATE";
		}
		try{
		String sqlQuery="select distinct(customizeMenuId)as custMenuId,m.moduleName as menuname ,m.STATUSID as  isActive from CUSTOMIZE_MENU_SUBMENU sb "
				+ "inner join customizationmenus  m on sb.CUSTOMIZEMENUID=m.id where sb.BANKINGTYPE=:bankingType and sb.appid=:appId and m.STATUSID=3 ";
		list = getSession().createSQLQuery(sqlQuery)
				.addScalar("custMenuId")
				.addScalar("menuname").addScalar("isActive").setParameter("bankingType", bankingType).setParameter("appId", appId)
				.setResultTransformer(Transformers.aliasToBean(CustomizeMenuSubMenuEntity.class)).list();
					
		}
		catch(Exception e){
			LOGGER.info("Exception:", e);
		}
	
		return list;
		
	}
	
	
	
	
	
	
	
	
	
	
	@Override
	public List<MenuBean> findCustomizationMenuId(int appId) {
		List<MenuBean> list=null;
	
		try{
		String sqlQuery="select distinct(customizeMenuId)as menuid,m.moduleName as menuName ,m.STATUSID as  isActive from CUSTOMIZATIONMENUSUBMENU sb "
				+ "inner join customizationmenus  m on sb.CUSTOMIZEMENUID=m.id where  sb.appid=:appId and m.STATUSID=3 ";
		list = getSession().createSQLQuery(sqlQuery)
				.addScalar("menuid",StandardBasicTypes.BIG_DECIMAL)
				.addScalar("menuName").addScalar("isActive").setParameter("appId", appId)
				.setResultTransformer(Transformers.aliasToBean(MenuBean.class)).list();
					
		}
		catch(Exception e){
			LOGGER.info("Exception:", e);
		}
	
		return list;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	


	@Override
	public List<MenuBean> findSubmenuList(int roleId, int menuId) {
		List<MenuBean> list=null;
		try{
		String sqlQuery="select m.submenuid ,m.statusId as isactive,s.menu_name as submenuName from menu_submenu m inner join submenu"
				+ " s on m.submenuid=s.id where roleid=:roleid and menuid=:menuid";
		list = getSession().createSQLQuery(sqlQuery)
				.addScalar("submenuid")
				.addScalar("submenuName").addScalar("isActive").setParameter("roleid", roleId).setParameter("menuid", menuId)
				.setResultTransformer(Transformers.aliasToBean(MenuBean.class)).list();
					
		}
		catch(Exception e){
			LOGGER.info("Exception:", e);
		}
	
		return list;
	}
	
	@Override
	public List<MenuBean> findCustomizeSubmenuList(int bankType, int menuId) {
		String bankingType = null;
		if(bankType == 0){
			bankingType ="RETAIL";
		}else if(bankType == 1){
			bankingType ="CORPORATE";
		}
		List<MenuBean> list=null;
		try{
		String sqlQuery="select m.CUSTOMIZESUBMENUID as submenuid ,m.statusId as isactive,s.menu_name as submenuName,cm.id as menuid, cm.MODULENAME as menuName from CUSTOMIZE_MENU_SUBMENU m inner join customize_submenu"
				+ " s on m.CUSTOMIZESUBMENUID=s.id inner join customizationmenus cm on cm.id=m.CUSTOMIZEMENUID where m.bankingType=:bankingType and m.CUSTOMIZEMENUID=:menuid";
		list = getSession().createSQLQuery(sqlQuery)
				.addScalar("submenuid",StandardBasicTypes.BIG_INTEGER).addScalar("menuid").addScalar("menuName")
				.addScalar("submenuName").addScalar("isActive",StandardBasicTypes.BIG_INTEGER).setParameter("bankingType", bankingType).setParameter("menuid", menuId)
				.setResultTransformer(Transformers.aliasToBean(MenuBean.class)).list();
					
		}
		catch(Exception e){
			LOGGER.info("Exception:", e);
		}
	
		return list;
	}

	@Override
	public List<MenuBean> findActiveSubmenu(int menuId) {
		List<MenuBean> list=null;
		try{
		/*String sqlQuery="select m.id as submenuid,  m.menu_name as submenuName ,m.statusId  as isActive from submenu m where main_menuid=:menuid and statusId=:stsId";
			
		list = getSession().createSQLQuery(sqlQuery)
				.addScalar("submenuid",StandardBasicTypes.BIG_DECIMAL)
				.addScalar("submenuName").addScalar("isActive",StandardBasicTypes.BIG_DECIMAL).setParameter("menuid", menuId).setParameter("stsId", ApplicationConstants.ACTIVE_SATUS)
				.setResultTransformer(Transformers.aliasToBean(MenuBean.class)).list();
		
		*/
		String sqlQuery="select m.id as submenuid,  m.menu_name as submenuName ,m.statusId  as isActive from submenu m where main_menuid=:menuid and statusId=3";
		list = getSession().createSQLQuery(sqlQuery)
				.addScalar("submenuid")
				.addScalar("submenuName").addScalar("isActive").setParameter("menuid", menuId)
				.setResultTransformer(Transformers.aliasToBean(MenuBean.class)).list();
		}
		catch(Exception e){
			LOGGER.info("Exception:", e);
		}
	
		return list;
	}
	
	
	@Override
	public List<MenuBean> findCustmizeActiveSubmenu(int menuId) {
		List<MenuBean> list=null;
		try{
		String sqlQuery="select m.id as submenuid,  m.menu_name as submenuName ,m.is_active  as isActive, m.CUSTOMIZE_MENUID as menuid, cm.MODULENAME as menuName  from "
				+ " customize_submenu m inner join customizationmenus cm on cm.id=m.CUSTOMIZE_MENUID  where CUSTOMIZE_MENUID=:menuid and is_active=3";
			
		list = getSession().createSQLQuery(sqlQuery)
				.addScalar("submenuid",StandardBasicTypes.BIG_INTEGER).addScalar("menuid").addScalar("menuName")
				.addScalar("submenuName").addScalar("isActive",StandardBasicTypes.BIG_INTEGER).setParameter("menuid", menuId)
				.setResultTransformer(Transformers.aliasToBean(MenuBean.class)).list();
		}
		catch(Exception e){
			LOGGER.info("Exception:", e);
		}
	
		return list;
	}

	@Override
	public List<MenuBean> finaAllActiveMenu() {
		List<MenuBean> list=null;
		try{
		String sqlQuery="select m.id as menuid ,m.statusId as isActive,m.menuname as menuName from main_menu m where m.statusId =:stsId";
		list = getSession().createSQLQuery(sqlQuery)
				.addScalar("menuid").addScalar("isActive")
				.addScalar("menuName").setParameter("stsId", ApplicationConstants.ACTIVE_SATUS)
				.setResultTransformer(Transformers.aliasToBean(MenuBean.class)).list();
					
		}
		catch(Exception e){
			LOGGER.info("Exception:", e);
		}
	
		return list;
	}
	
	
	
	 
	@Override
	public List<CustomizeMenus> finaAllCustomizeActiveMenu(int appId, int roleId) {
		List<CustomizeMenus> list=null;
		try{
		String sqlQuery="select m.id as custMenuId ,m.statusId as isActive,m.moduleName as menuName from customizationmenus m where m.statusId =3  AND m.appid=:appId AND m.roleid=:roleId";
		list = getSession().createSQLQuery(sqlQuery)
				.addScalar("custMenuId",StandardBasicTypes.BIG_INTEGER).addScalar("isActive",StandardBasicTypes.BIG_INTEGER)
				.addScalar("menuName").setParameter("appId", appId).setParameter("roleId", roleId)
				.setResultTransformer(Transformers.aliasToBean(CustomizeMenus.class)).list();
					
		}
		catch(Exception e){
			LOGGER.info("Exception:", e);
		}
	
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RoleBean> findAllRoles(int number, int offset) {
		
		String sqlQuery="select id as ID,code as code,displayname as displayName , isactive as isActive ,isdeleted as isDeleted,"
			+" description as description,createdby as createdBy,updateby as updatedBy from roles where ISACTIVE='Y' and ISDELETED='N'";
	

		List<RoleBean> list = getSession().createSQLQuery(sqlQuery)	
				.addScalar("ID")
				.addScalar("code")
				.addScalar("displayName")
				.addScalar("isActive")
				.addScalar("isDeleted")
				.addScalar("description")
				.addScalar("createdBy")
				.addScalar("updatedBy")
				
				.setResultTransformer(Transformers.aliasToBean(RoleBean.class)).list();
	
			return list;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public ResponseMessageBean checkMenuName(MainMenu mainMenu) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		try{
		String sqlMenuNameExit = "SELECT count(*) FROM MAIN_MENU WHERE Lower(MENUNAME) =:menuName";
		List isMenuNameExit = getSession().createSQLQuery(sqlMenuNameExit).setParameter("menuName", mainMenu.getMenuname().toLowerCase()).list();
		
		System.out.println(sqlMenuNameExit);
		System.out.println(isMenuNameExit);
		
		if (!(isMenuNameExit.get(0).toString().equalsIgnoreCase("0"))) {
			rmb.setResponseCode("500");
			rmb.setResponseMessage("Name Already Exist");
		}else {
			rmb.setResponseCode("200");
			rmb.setResponseMessage("Both not exist");
		}
		}
		catch(Exception e){
			LOGGER.info("Exception:", e);
		}
		return rmb;
		
	}

	@Override
	@SuppressWarnings("rawtypes")
	public ResponseMessageBean checkSubMenuName(SubMenuEntity subMenuEntity) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		try{
		String sqlMenuNameExit = "SELECT count(*) FROM SUBMENU WHERE Lower(MENU_NAME) =:menuName and MAIN_MENUID=:mainMenuId";
		List isMenuNameExit = getSession().createSQLQuery(sqlMenuNameExit).setParameter("mainMenuId", subMenuEntity.getMainMenuid()).setParameter("menuName", subMenuEntity.getMenuName().toLowerCase()).list();
		
		
		if (!(isMenuNameExit.get(0).toString().equalsIgnoreCase("0"))) {
			rmb.setResponseCode("500");
			rmb.setResponseMessage("Name Already Exist");
		}else {
			rmb.setResponseCode("200");
			rmb.setResponseMessage("Both not exist");
		}
		}
		catch(Exception e){
			LOGGER.info("Exception:", e);
		}
		return rmb;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public ResponseMessageBean checkMenuNameWhileUpdate(MainMenu mainMenu) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		try{
		String sqlMenuNameExit = "SELECT count(*) FROM MAIN_MENU WHERE Lower(MENUNAME) =:menuName  AND  ID!=:id";
		List isMenuNameExit = getSession().createSQLQuery(sqlMenuNameExit).setParameter("id",mainMenu.getId()).setParameter("menuName", mainMenu.getMenuname().toLowerCase()).list();
		
		System.out.println(sqlMenuNameExit);
		System.out.println(isMenuNameExit);
		
		if (!(isMenuNameExit.get(0).toString().equalsIgnoreCase("0"))) {
			rmb.setResponseCode("500");
			rmb.setResponseMessage("Name Already Exist");
		}else {
			rmb.setResponseCode("200");
			rmb.setResponseMessage("Both not exist");
		}
		}
		catch(Exception e){
			LOGGER.info("Exception:", e);
		}
		return rmb;
		
	}

	@Override
	@SuppressWarnings("rawtypes")
	public ResponseMessageBean checkSubMenuNameWhileUpdate(SubMenuEntity subMenuEntity) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		try{
			System.out.println(subMenuEntity.getMainMenuid());
			System.out.println("hello Shubham");
		String sqlMenuNameExit = "SELECT count(*) FROM SUBMENU WHERE Lower(MENU_NAME) =:menuName and MAIN_MENUID=:mainMenuId and id !=:id";
		List isMenuNameExit = getSession().createSQLQuery(sqlMenuNameExit).setParameter("id", subMenuEntity.getId()).setParameter("mainMenuId", subMenuEntity.getMenuId()).setParameter("menuName", subMenuEntity.getMenuName().toLowerCase()).list();
		
		
		if (!(isMenuNameExit.get(0).toString().equalsIgnoreCase("0"))) {
			rmb.setResponseCode("500");
			rmb.setResponseMessage("Name Already Exist");
		}else {
			rmb.setResponseCode("200");
			rmb.setResponseMessage("Both not exist");
		}
		}
		catch(Exception e){
			LOGGER.info("Exception:", e);
		}
		return rmb;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public ResponseMessageBean checkCustomMenu(CustomizeMenus customMenuData) {
		ResponseMessageBean rmb = new ResponseMessageBean();
	

		try{
			
		String sqlNameExit = "SELECT count(*) FROM CUSTOMIZATIONMENUS WHERE Lower(MODULENAME) =:moduleName AND  APPID=:appId ";
		List isNameExit = getSession().createSQLQuery(sqlNameExit).setParameter("appId",customMenuData.getAppId())
				.setParameter("moduleName", customMenuData.getModuleName().toLowerCase()).list();
		
		
		if (!(isNameExit.get(0).toString().equalsIgnoreCase("0"))) {
			rmb.setResponseCode("500");
			rmb.setResponseMessage("Menu Name Already Exist For Same Channel");
		}else {
			rmb.setResponseCode("200");
			rmb.setResponseMessage("Both not exist");
		}
		}
		catch(Exception e){
			LOGGER.info("Exception:", e);
		}
		
		
		return rmb;
	}

	
	@Override
	@SuppressWarnings("rawtypes")
	public ResponseMessageBean checkUpdateCustomMenu(CustomizeMenus customMenuData) {
		ResponseMessageBean rmb = new ResponseMessageBean();
	

		try{
			
		String sqlNameExit = "SELECT count(*) FROM CUSTOMIZATIONMENUS WHERE Lower(MODULENAME) =:moduleName AND  APPID=:appId AND id !=:id";
		List isNameExit = getSession().createSQLQuery(sqlNameExit).setParameter("appId",customMenuData.getAppId()).setParameter("id", customMenuData.getId())
				.setParameter("moduleName", customMenuData.getModuleName().toLowerCase()).list();
		
		
		if (!(isNameExit.get(0).toString().equalsIgnoreCase("0"))) {
			rmb.setResponseCode("500");
			rmb.setResponseMessage("Menu Name Already Exist For Same Channel");
		}else {
			rmb.setResponseCode("200");
			rmb.setResponseMessage("Both not exist");
		}
		}
		catch(Exception e){
			LOGGER.info("Exception:", e);
		}
		
		
		return rmb;
	}

	@Override
	public List<AccessRights> getPrivilageDataByMenuId(BigInteger menuId,BigInteger roleId) {
		List<AccessRights> list=null;
		try{
		String sqlQuery= "select distinct li.privilege_id as privilegeId from LINK_ROLES li where li.MENU_ID=:MENU_ID and ROLE_ID=:ROLE_ID";
		 list = getSession().createSQLQuery(sqlQuery)
					.addScalar("privilegeId")
					.setParameter("MENU_ID", menuId)
					.setParameter("ROLE_ID", roleId)
					.setResultTransformer(Transformers.aliasToBean(AccessRights.class)).list();
		}
		catch(Exception e){
			LOGGER.info("Exception:", e);
		}
	
		return list;
	}

	@Override
	public List<MainMenuDetailsBean> getMainMenuDetailsByid(int id) {
		List<MainMenuDetailsBean> list = null;
		try {
			String sqlQuery = "select m.id,m.menuname,m.statusId as statusId,m.createdon,m.updatedon,m.createdby,m.updatedby ,m.menuLogo ,aw.remark,aw.userAction,sm.name as statusName from MAIN_MENU m "
					+ "	left join ADMINWORKFLOWREQUEST  aw on aw.activityrefno=:id and aw.tablename='MAIN_MENU' inner join statusmaster  sm on m.statusId=sm.id where m.id=:id ";

		list =  getSession().createSQLQuery(sqlQuery)
				.addScalar("id").addScalar("menuname").addScalar("statusId").addScalar("createdon").addScalar("updatedon").addScalar("createdby").addScalar("updatedby")
				.addScalar("menuLogo").addScalar("remark").addScalar("userAction").addScalar("statusName")
				.setParameter("id", id)
				.setResultTransformer(Transformers.aliasToBean(MainMenuDetailsBean.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
		
	}

	@Override
	@SuppressWarnings("rawtypes")
	public ResponseMessageBean checkCustomizeSubMenuName(CustomizeSubmenuEntity customizeSubMenuEntity) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		try {
			String sqlMenuNameExit = "SELECT count(*) FROM CUSTOMIZE_SUBMENU WHERE Lower(MENU_NAME) =:menuName and CUSTOMIZE_MENUID=:customizeMenuId";
			List isMenuNameExit = getSession().createSQLQuery(sqlMenuNameExit)
					.setParameter("customizeMenuId", customizeSubMenuEntity.getCustomizeMenuid())
					.setParameter("menuName", customizeSubMenuEntity.getMenuName().toLowerCase()).list();

			if (!(isMenuNameExit.get(0).toString().equalsIgnoreCase("0"))) {
				rmb.setResponseCode("500");
				rmb.setResponseMessage("Name Already Exist");
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
	public ResponseMessageBean checkUpdateCustomizeSubMenu(CustomizeSubmenuEntity customizeSubMenuEntity) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		try {
			String sqlMenuNameExit = "SELECT count(*) FROM CUSTOMIZE_SUBMENU WHERE Lower(MENU_NAME) =:menuName and CUSTOMIZE_MENUID=:customizeMenuId and id!=:id";
			List isMenuNameExit = getSession().createSQLQuery(sqlMenuNameExit)
					.setParameter("customizeMenuId", customizeSubMenuEntity.getCustomizeMenuid())
					.setParameter("id", customizeSubMenuEntity.getId())
					.setParameter("menuName", customizeSubMenuEntity.getMenuName().toLowerCase()).list();

			if (!(isMenuNameExit.get(0).toString().equalsIgnoreCase("0"))) {
				rmb.setResponseCode("500");
				rmb.setResponseMessage("Name Already Exist");
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
	public List<CustomizationSubmenuEntity> getAllCustomizationSubMenu() {
		List<CustomizationSubmenuEntity> list = null;
		try {

			String sqlQuery = "select cs.id , cs.submenuName as submenuName, cs.menuLogo as menuLogo, cs.JSONKEY as jsonKey,"
					+ "cs.appId as appId, cs.statusId as statusId, cs.createdOn as createdOn, cs. createdby as createdby,s.name as statusName from CUSTOMIZATIONSUBMENUS cs "
					+ " inner join statusmaster s on s.id=cs.statusid order by cs.id desc ";
			list = getSession().createSQLQuery(sqlQuery)
					.addScalar("id",StandardBasicTypes.BIG_INTEGER).addScalar("submenuName").addScalar("menuLogo",StandardBasicTypes.STRING)
					.addScalar("jsonKey").addScalar("appId",StandardBasicTypes.BIG_INTEGER).addScalar("statusId",StandardBasicTypes.BIG_INTEGER).addScalar("createdOn")
                    .addScalar("createdby",StandardBasicTypes.BIG_INTEGER).addScalar("statusName")
					.setResultTransformer(Transformers.aliasToBean(CustomizationSubmenuEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return null;
		}

		return list;
	}


	@Override
	public void addCustomizationSubMenu(CustomizationSubmenuEntity customizeSubMenuEntity) {
		Session session = sessionFactory.getCurrentSession();
		try {
			
			customizeSubMenuEntity.setCreatedOn(new Date());
			session.save(customizeSubMenuEntity);
			
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			
		}
	}

	@Override
	public List<CustomizationSubmenuEntity> getCustomizationSubMenuByid(int id) {
		List<CustomizationSubmenuEntity> list = null;
		try {

			String sqlQuery = "select cs.id , cs.submenuName as submenuName, cs.menuLogo as menuLogo, cs.JSONKEY as jsonKey,"
					+ "cs.appId as appId, cs.statusId as statusId, cs.createdOn as createdOn, cs. createdby as createdby,s.name as statusName from CUSTOMIZATIONSUBMENUS cs "
					+ "inner join statusmaster s on s.id=cs.statusid  where cs.id=:id  ";
			list = getSession().createSQLQuery(sqlQuery)
					.addScalar("id",StandardBasicTypes.BIG_INTEGER).addScalar("submenuName").addScalar("menuLogo",StandardBasicTypes.STRING).addScalar("jsonKey")
					.addScalar("appId",StandardBasicTypes.BIG_INTEGER).addScalar("statusId",StandardBasicTypes.BIG_INTEGER).addScalar("createdOn")
                    .addScalar("createdby",StandardBasicTypes.BIG_INTEGER).addScalar("statusName")
                    .setParameter("id", id).setResultTransformer(Transformers.aliasToBean(CustomizationSubmenuEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return null;
		}

		return list;
	}

	@Override
	public void updateCustomizationSubMenu(CustomizationSubmenuEntity customizeSubMenuEntity) {
		Session session = sessionFactory.getCurrentSession();
		try {

			session.update(customizeSubMenuEntity);
			
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			
		}
	}

	@Override
	public ResponseMessageBean checkUpdateCustomizationSubMenu(CustomizationSubmenuEntity customizeSubMenuEntity) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		try {
			String sqlMenuNameExit = "SELECT count(*) FROM CUSTOMIZATIONSUBMENUS WHERE Lower(SUBMENUNAME) =:subMenuName AND id !=:id ";
			List isMenuNameExit = getSession().createSQLQuery(sqlMenuNameExit)
					.setParameter("id", customizeSubMenuEntity.getId())
					.setParameter("subMenuName", customizeSubMenuEntity.getSubmenuName().toLowerCase()).list();

			if (!(isMenuNameExit.get(0).toString().equalsIgnoreCase("0"))) {
				rmb.setResponseCode("500");
				rmb.setResponseMessage("Name Already Exist");
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
	public ResponseMessageBean checkCustomizationSubMenuName(CustomizationSubmenuEntity customizeSubMenuEntity) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		try {
			String sqlMenuNameExit = "SELECT count(*) FROM CUSTOMIZATIONSUBMENUS WHERE Lower(SUBMENUNAME) =:subMenuName ";
			List isMenuNameExit = getSession().createSQLQuery(sqlMenuNameExit)
					.setParameter("subMenuName", customizeSubMenuEntity.getSubmenuName().toLowerCase()).list();

			if (!(isMenuNameExit.get(0).toString().equalsIgnoreCase("0"))) {
				rmb.setResponseCode("500");
				rmb.setResponseMessage("Name Already Exist");
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
	public List<CustomizationMenuSubmenuEntity> findAllCustmizationActiveSubmenu(int menuId, int appId) {
		List<CustomizationMenuSubmenuEntity> list = null;
		try {
			String sqlQuery = "select m.CUSTOMIZEMENUID as custMenuId,m.CUSTOMIZESUBMENUID as custSubmenuId, cm.MODULENAME as menuName, cs.SUBMENUNAME as subMenuName,m.appId as appId from "
					+ " CUSTOMIZATIONMENUSUBMENU m  inner join CUSTOMIZATIONMENUS cm on cm.id=m.CUSTOMIZEMENUID "
					+ " inner join CUSTOMIZATIONSUBMENUS cs on cs.id =m.CUSTOMIZESUBMENUID where m.CUSTOMIZEMENUID=:menuid and m.appID=:appId ";

			list = getSession().createSQLQuery(sqlQuery)
					.addScalar("custMenuId").addScalar("custSubmenuId").addScalar("menuName").addScalar("subMenuName").addScalar("appId")
					.setParameter("menuid", menuId).setParameter("appId", appId)
					.setResultTransformer(Transformers.aliasToBean(CustomizationMenuSubmenuEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}
	
	@Override
	public List<CustomizationSubmenuEntity> findAllActivePSBSubmenu(int appId) {
		List<CustomizationSubmenuEntity> list=null;
		try{
		String sqlQuery="select m.id as submenuid,  m.SUBMENUNAME as submenuName ,m.STATUSID as statusId from "
				+ " CUSTOMIZATIONSUBMENUS m  where m.appid=:appId ";
			
		list = getSession().createSQLQuery(sqlQuery)
				.addScalar("submenuid").addScalar("submenuName").addScalar("statusId")
				.setParameter("appId", appId)
				.setResultTransformer(Transformers.aliasToBean(CustomizationSubmenuEntity.class)).list();
		}
		catch(Exception e){
			LOGGER.info("Exception:", e);
		}
	
		return list;
	}

	@Override
	public ResponseMessageBean checkIsSubMenuMapped(CustomizationMenuSubmenuEntity menuSubmenuEntity) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		try {
			String sqlMenuNameExit = "SELECT count(*) FROM CUSTOMIZATIONMENUSUBMENU WHERE CUSTOMIZEMENUID=:menuId AND customizeSubmenuId=:subMenuId ";
			List isMenuNameExit = getSession().createSQLQuery(sqlMenuNameExit)
					.setParameter("menuId", menuSubmenuEntity.getCustomizeMenuId())
					.setParameter("subMenuId", menuSubmenuEntity.getCustomizeSubmenuId()).list();

			if (!(isMenuNameExit.get(0).toString().equalsIgnoreCase("0"))) {
				rmb.setResponseCode("500");
				rmb.setResponseMessage("SubMenu Is Already Mapped For Selected Menu");
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
	public boolean addCustomizationMenuSubMenuMapping(CustomizationMenuSubmenuEntity menuSubmenuEntity) {
		Session session = sessionFactory.getCurrentSession();
		try {
			menuSubmenuEntity.setUpdatedon(new Date());
			menuSubmenuEntity.setCreatedon(new Date());
			session.save(menuSubmenuEntity);
			
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
			
		}
		return true;
	}

	@Override
	public boolean saveCustomizationMenuSubMenuMapping(List<CustomizationMenuSubmenuEntity> menuSubmenuEntity) {
		int res = 0;
		try {

			for (CustomizationMenuSubmenuEntity menuSubMenu : menuSubmenuEntity) {
				String sql = "update CUSTOMIZATIONSUBMENUS set statusId=:status where  id=:subMenuId and appId=:appId";
				res = getSession().createSQLQuery(sql).setParameter("status", menuSubMenu.getIsActive()).setParameter("appId", menuSubMenu.getAppId())
						.setParameter("subMenuId", menuSubMenu.getCustomizeSubmenuId()).executeUpdate();
			}

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}

		if (res != 0) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public boolean saveCustomizationMainMenuMapping(List<CustomizationMenuSubmenuEntity> menuSubmenuEntity) {
		int res = 0;
		try {

			for (CustomizationMenuSubmenuEntity menuSubMenu : menuSubmenuEntity) {
				String sql = "update CUSTOMIZATIONMENUS set statusId=:status where  id=:menuId and appId=:appId";
				res = getSession().createSQLQuery(sql).setParameter("status", menuSubMenu.getIsActive()).setParameter("appId", menuSubMenu.getAppId())
						.setParameter("menuId", menuSubMenu.getCustomizeMenuId()).executeUpdate();
			}

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}

		if (res != 0) {
			return true;
		} else {
			return false;
		}
	}
	
	
	
	@Override
	public List<MainMenuBean> getAllMainMenus() {
		List<MainMenuBean> list = null;
		try {

			String sqlQuery = " select  m.id as id, m.menuname as menuname, m.statusId as statusId, m.createdon as createdon,m.updatedon as updatedon,"
					+ "m.createdby as createdby, m.updatedby as updatedby, s.name as statusName,m.menuLogo as menuLogo from main_menu m inner join statusmaster s on "
					+ " s.id=m.statusid  order by id desc ";
			list = getSession().createSQLQuery(sqlQuery)
					.addScalar("id").addScalar("menuname").addScalar("statusId").addScalar("createdon").addScalar("updatedon").addScalar("createdby")
					.addScalar("updatedby").addScalar("statusName").addScalar("menuLogo")
					.setResultTransformer(Transformers.aliasToBean(MainMenuBean.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return null;
		}

		return list;
	}
	
	@Override
	public MainMenuBean getAllMainMenusById(int id) {
		Query list = null;
		try {

			String sqlQuery = " select  m.id as id, m.menuname as menuname, m.statusId as statusId, m.createdon as createdon,m.updatedon as updatedon,m.menuLogo as menuLogo,"
					+ "m.createdby as createdby, m.updatedby as updatedby, s.name as statusName,aw.remark,aw.userAction from main_menu m inner join statusmaster s on "
					+ " s.id=m.statusid  left join ADMINWORKFLOWREQUEST aw on aw.activityrefno=m.id and aw.tablename='MAIN_MENU' where m.id=:id";
			list = getSession().createSQLQuery(sqlQuery)
					.addScalar("id").addScalar("menuname").addScalar("statusId").addScalar("createdon").addScalar("updatedon").addScalar("createdby")
					.addScalar("updatedby").addScalar("statusName").addScalar("menuLogo").addScalar("remark").addScalar("userAction")
					.setParameter("id", id)
					.setResultTransformer(Transformers.aliasToBean(MainMenuBean.class));
			
			 
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return null;
		}

		return (MainMenuBean) list.uniqueResult();
		
		
	}

}
