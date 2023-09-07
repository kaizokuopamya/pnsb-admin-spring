package com.itl.pns.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itl.pns.bean.LinkRolesBean;
import com.itl.pns.bean.MainMenuBean;
import com.itl.pns.bean.MainMenuDetailsBean;
import com.itl.pns.bean.MenuBean;
import com.itl.pns.bean.MenuSubmenuBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.bean.RoleBean;
import com.itl.pns.constant.ApplicationConstants;
import com.itl.pns.dao.MenuDao;
import com.itl.pns.entity.AccessRights;
import com.itl.pns.entity.ActivitySettingMasterEntity;
import com.itl.pns.entity.AdminWorkFlowRequestEntity;
import com.itl.pns.entity.CustomizationMenuSubmenuEntity;
import com.itl.pns.entity.CustomizationSubmenuEntity;
import com.itl.pns.entity.CustomizeMenuSubMenuEntity;
import com.itl.pns.entity.CustomizeMenus;
import com.itl.pns.entity.CustomizeSubmenuEntity;
import com.itl.pns.entity.LinkRolesEntity;
import com.itl.pns.entity.MainMenu;
import com.itl.pns.entity.MenuSubmenuEntity;
import com.itl.pns.entity.SubMenuEntity;
import com.itl.pns.repository.AccessRightsRepository;
import com.itl.pns.repository.CustomizeMenuRepository;
import com.itl.pns.repository.CustomizeMenuSubmenuRepository;
import com.itl.pns.repository.CustomizeSubMenuRepository;
import com.itl.pns.repository.LinkMenuRoleRepository;
import com.itl.pns.repository.LinkRolesRepository;
import com.itl.pns.repository.MainMenuRepository;
import com.itl.pns.repository.MenuSubmenuRepository;
import com.itl.pns.repository.SubMenuRepository;
import com.itl.pns.service.MenuService;
import com.itl.pns.util.AdminWorkFlowReqUtility;

@Service
@Qualifier("MenuService")
public class MenuServiceImpl implements MenuService {

	static Logger LOGGER = Logger.getLogger(MenuServiceImpl.class);

	@Autowired
	MenuDao menuServicedao;

	@Autowired
	LinkRolesRepository linkRolesRepository;

	@Autowired
	AccessRightsRepository accessRightsRepository;

	@Autowired
	CustomizeMenuRepository customizeMenuRepository;

	@Autowired
	MenuSubmenuRepository menuSubmenuRepository;

	@Autowired
	SubMenuRepository subMenuRepository;

	@Autowired
	CustomizeMenuSubmenuRepository customizeMenuSubmenuRepository;

	@Autowired
	MainMenuRepository mainMenuRepository;

	@Autowired
	LinkMenuRoleRepository linkMenuRoleRepository;

	@Autowired
	AdminWorkFlowReqUtility adminWorkFlowReqUtility;

	@Autowired
	CustomizeSubMenuRepository customizeSubMenuRepository;

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public List<MenuBean> getMenuDetails(int roleId) {
		return menuServicedao.getMenuDetails(roleId);
	}

	@Override
	public List<MenuBean> getMenuDetailsAccess() {
		return menuServicedao.getMenuDetailsAccess();
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	public void saveAccessRights(List<AccessRights> accessRights) {
		boolean success = true;

		try {

			if (accessRights != null && !accessRights.isEmpty()) {
				List<AccessRights> accList = menuServicedao
						.getAccessRightsForRoleId(accessRights.get(0).getRoleId().toBigInteger());
				if (!accList.isEmpty())// If Exist
				{
					success = menuServicedao.deleteExistingRole(accessRights.get(0).getRoleId().toBigInteger());
				}
			}
			if (success) {
				accessRightsRepository.save(accessRights);
			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
	}

	@Override
	public List<AccessRights> getAccessRightsForRoleId(BigInteger roleId) {
		return menuServicedao.getAccessRightsForRoleId(roleId);
	}

	@Override
	public List<LinkRolesBean> getAllPrivileges(BigInteger roleId) {

		List<LinkRolesBean> list = menuServicedao.findAllByroleId(roleId);
		return list;
	}

	@Override
	public List<LinkRolesBean> getPrivilegeById(BigInteger moduleId, BigInteger roleId) {

		List<LinkRolesBean> list = menuServicedao.findPreviligeByroleId(moduleId, roleId);
		return list;
	}

	@Override
	public ResponseMessageBean getMenusForAppId(BigInteger appId, BigInteger roleid) {
		ResponseMessageBean res = new ResponseMessageBean();
		List<CustomizeMenus> PSBmenu = menuServicedao.getPSBMenusForAppId(appId, roleid);
		if (!ObjectUtils.isEmpty(PSBmenu)) {
			res.setResponseCode("200");
			res.setResult(PSBmenu);
		} else {
			res.setResponseCode("202");
			res.setResponseMessage("No Menu mapped ");
		}
		return res;
	}

	@Override
	public ResponseMessageBean getPSBAppMenuRightById(BigInteger id) {
		ResponseMessageBean res = new ResponseMessageBean();
		List<CustomizeMenus> PSBmenu = menuServicedao.getPSBAppMenuRightById(id);
		if (!ObjectUtils.isEmpty(PSBmenu)) {
			res.setResponseCode("200");
			res.setResult(PSBmenu);
		} else {
			res.setResponseCode("202");
			res.setResponseMessage("No Menu mapped ");
		}
		return res;
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	public void addPSBAppMenus(CustomizeMenus addPSBAppMenus) {
		try {
			addPSBAppMenus.setCreatedOn(new Date());
			addPSBAppMenus.setCreatedby(BigInteger.valueOf(1));
			customizeMenuRepository.save(addPSBAppMenus);
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	public void updatePSBAppMenus(CustomizeMenus psbAppMenusData) {
		try {

			CustomizeMenus customizeMenuData = customizeMenuRepository.findOne(psbAppMenusData.getId());
			customizeMenuData.setCreatedOn(customizeMenuData.getCreatedOn());
			customizeMenuData.setCreatedby(customizeMenuData.getCreatedby());
			customizeMenuData.setRoleId(customizeMenuData.getRoleId());
			customizeMenuData.setType(psbAppMenusData.getType());

			customizeMenuData.setModuleName(psbAppMenusData.getModuleName());
			customizeMenuData.setAppId(psbAppMenusData.getAppId());
			customizeMenuData.setStatusId(psbAppMenusData.getStatusId());
			customizeMenuData.setJson_key(psbAppMenusData.getJson_key());
			customizeMenuData.setMenuImage(psbAppMenusData.getMenuImage());

			customizeMenuRepository.save(customizeMenuData);
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

	}

	private List<CustomizeMenus> getCustomizeMenuDetails() {

		List<CustomizeMenus> list = null;
		try {

			String sqlQuery = "select c.id,c.appId,c.createdby,c.createdOn,c.menuImage,c.moduleName,c.roleId,c.statusId from "
					+ "customizationmenus c order by createdon desc";

			list = getSession().createSQLQuery(sqlQuery)
					.setResultTransformer(Transformers.aliasToBean(CustomizeMenus.class)).list();

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;

	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	public void saveCustomMenus(List<CustomizeMenus> saveCustomMenus) {

		try {
			for (CustomizeMenus custMenu : saveCustomMenus) {
				CustomizeMenus cust = customizeMenuRepository.getOne(custMenu.getId());
				cust.setStatusId(custMenu.getStatusId());
				customizeMenuRepository.save(cust);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	@Override
	public List<MenuSubmenuBean> findAll(int roleId) {

		List<MenuSubmenuBean> menuSubmenuBean = new ArrayList<MenuSubmenuBean>();
		int menuId = 0;
		String menuName = "";
		String menuImage = "";

		List<MenuBean> MenuIdList = menuServicedao.findMenuId(roleId);
		if (!ObjectUtils.isEmpty(MenuIdList)) {
			for (MenuBean menuBean : MenuIdList) {
				List<SubMenuEntity> subMenuList = new ArrayList<SubMenuEntity>();
				MenuSubmenuBean menu = new MenuSubmenuBean();
				menuId = menuBean.getMenuid().intValue();
				List<MenuSubmenuEntity> submenuId = menuSubmenuRepository.findByRoleidAndMenuId(roleId, menuId);
				if (!ObjectUtils.isEmpty(submenuId)) {
					menuName = submenuId.get(0).getMenuId().getMenuname();
					menuImage = submenuId.get(0).getMenuId().getMenuLogo();
					for (MenuSubmenuEntity menuSubmenuEntity : submenuId) {
						subMenuList.add(menuSubmenuEntity.getSubmenuId());
					}
					menu.setMenuId(menuId);
					menu.setMenuName(menuName);
					menu.setMenuImage(menuImage);
					menu.setSubMenuList(subMenuList);
					menuSubmenuBean.add(menu);
				}

			}
		}

		return menuSubmenuBean;
	}

	@Override
	public List<MainMenuBean> getMainMenu() {

		/*
		 * List<MainMenu> list = mainMenuRepository.findAllByOrderByIdDesc();
		 */
		List<MainMenuBean> list = menuServicedao.getAllMainMenus();
		return list;
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	public void addMenu(MainMenu mainMenu) {
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(mainMenu.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(mainMenu.getActivityName());

		try {
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				mainMenu.setStatusId(statusId); // 97- ADMIN_CHECKER_PENDIN
			}
			Date date = new Date();
			mainMenu.setCreatedon(date);
			mainMenu.setUpdatedon(date);
			mainMenuRepository.save(mainMenu);
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	public void updateMainMenu(MainMenu mainMenu) {
		int userStatus = mainMenu.getStatusId();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(mainMenu.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(mainMenu.getActivityName());
		try {

			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				mainMenu.setStatusId(statusId); // 97- ADMIN_CHECKER_PENDIN
			}

			MainMenu menu = mainMenuRepository.findOne(mainMenu.getId());
			menu.setMenuname(mainMenu.getMenuname());
			menu.setUpdatedon(new Date());
			menu.setStatusId(mainMenu.getStatusId());
			menu.setUpdatedby(mainMenu.getUpdatedby());
			menu.setMenuLogo(mainMenu.getMenuLogo());
			mainMenuRepository.save(menu);

			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

				ObjectMapper mapper = new ObjectMapper();

				List<AdminWorkFlowRequestEntity> AdminDataList = adminWorkFlowReqUtility
						.getAdminWorkFlowDataByActivityRefNo(mainMenu.getId(), mainMenu.getSubMenu_ID());

				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
				mainMenu.setUpdatedon(new Date());
				adminData.setCreatedOn(mainMenu.getCreatedon());
				adminData.setCreatedByUserId(mainMenu.getUser_ID());
				adminData.setCreatedByRoleId(mainMenu.getRole_ID());
				adminData.setPageId(mainMenu.getSubMenu_ID()); // set submenuId
																// as pageid
				adminData.setCreatedBy(BigDecimal.valueOf(menu.getCreatedby()));
				adminData.setContent(mapper.writeValueAsString(mainMenu));
				adminData.setActivityId(mainMenu.getSubMenu_ID()); // set
																	// submenuId
																	// as
																	// activityid
				adminData.setStatusId(BigDecimal.valueOf(statusId)); // 97-
																		// ADMIN_CHECKER_PENDIN
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6)); // 6-
																		// checker
																		// roleid
				adminData.setRemark(mainMenu.getRemark());
				adminData.setActivityName(mainMenu.getActivityName());
				adminData.setActivityRefNo(BigDecimal.valueOf(mainMenu.getId()));
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminData.setTableName("MAIN_MENU");

				if (ObjectUtils.isEmpty(AdminDataList)) {
					adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);
				} else if (!ObjectUtils.isEmpty(AdminDataList)) {
					adminData.setId(BigDecimal.valueOf(AdminDataList.get(0).getId().intValue()));
					adminWorkFlowReqUtility.updateAdminWorkFlowRequest(adminData);

				}

				// Save data to admin work flow history
				adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(mainMenu.getSubMenu_ID(),
						new BigDecimal(mainMenu.getId()), new BigDecimal(menu.getCreatedby()), mainMenu.getRemark(),
						mainMenu.getRole_ID(), mapper.writeValueAsString(mainMenu));

			} else {

				// if record is present in admin work flow then update status
				adminWorkFlowReqUtility.getCheckerDataByctivityRefId(BigInteger.valueOf(mainMenu.getId()),
						BigInteger.valueOf(userStatus), mainMenu.getSubMenu_ID());
			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
	}

	@Override
	public MainMenuBean getMainMenuByid(int id) {

		/* MainMenu list = mainMenuRepository.findByid(id); */
		MainMenuBean list = menuServicedao.getAllMainMenusById(id);

		return list;
	}

	@Override
	public List<MainMenuDetailsBean> getMainMenuDetailsByid(int id) {

		return menuServicedao.getMainMenuDetailsByid(id);
	}

	@Override
	public List<MenuSubmenuEntity> getSubMenuBymenuId(int menuId) {

		List<MenuSubmenuEntity> MenuSubmenuList = menuSubmenuRepository.findByMenuIdOrderByMenuName(menuId);
		return MenuSubmenuList;
	}

	@Override
	public List<SubMenuEntity> getSubMenuListBymenuId(int menuid) {

		List<SubMenuEntity> subMenuList = subMenuRepository.findByMainMenuid(menuid);

		return subMenuList;
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	public void addSubMenu(SubMenuEntity subMenuEntity) {

		subMenuRepository.save(subMenuEntity);
	}

	@Override
	public SubMenuEntity getSubMenuByid(int id) {

		SubMenuEntity subMenuEntity = subMenuRepository.findByid(id);
		return subMenuEntity;
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	public void updateSubMenu(SubMenuEntity subMenuEntity) {

		SubMenuEntity subMenu = subMenuRepository.findOne(subMenuEntity.getId());
		subMenu.setMenuName(subMenuEntity.getMenuName());
		subMenu.setMenudesc(subMenuEntity.getMenudesc());
		subMenu.setMenuLogo(subMenuEntity.getMenuLogo());
		subMenu.setMenuLink(subMenuEntity.getMenuLink());
		subMenu.setStatusId(subMenuEntity.getStatusId());
		subMenuRepository.save(subMenu);
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	public void addMenuSubMenuMapping(MenuSubmenuEntity menuSubmenuEntity) {

		Date date = new Date();
		menuSubmenuEntity.setCreatedon(date);
		menuSubmenuEntity.setUpdatedon(date);
		menuSubmenuRepository.save(menuSubmenuEntity);
	}

	@Override
	public MenuSubmenuEntity getMenuSubMenuMappingByid(int id) {
		MenuSubmenuEntity menuSubmenuEntity = menuSubmenuRepository.findOne(id);
		return menuSubmenuEntity;
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	public void updateMenuSubMenuMapping(MenuSubmenuEntity menuSubmenuEntity) {
		Date date = new Date();
		MenuSubmenuEntity menuSubmenu = menuSubmenuRepository.findOne(menuSubmenuEntity.getId());
		menuSubmenu.setUpdatedon(date);

		menuSubmenu.setSubmenuId(menuSubmenuEntity.getSubmenuId());
		menuSubmenu.setStatusId(menuSubmenuEntity.getStatusId());
		menuSubmenu.setUpdatedby(menuSubmenuEntity.getUpdatedby());
		menuSubmenuRepository.save(menuSubmenu);
	}

	@Override
	public ResponseMessageBean getMenuRightsForRoleId(int roleId) {

		System.out.println("-------roleid-->" + roleId);
		ResponseMessageBean res = new ResponseMessageBean();
		List<MenuBean> Allmenu = menuServicedao.finaAllActiveMenu();
		if (!ObjectUtils.isEmpty(Allmenu)) {
			List<MenuBean> Menulist = menuServicedao.findMenuId(roleId);
			if (!ObjectUtils.isEmpty(Menulist)) {
				for (MenuBean mainMenu : Allmenu) {
					if (!Menulist.stream().anyMatch(o -> o.getMenuid().equals(mainMenu.getMenuid()))) {
						MenuBean newEntity = new MenuBean();
						newEntity.setIsActive(BigDecimal.valueOf(0));
						newEntity.setMenuid(mainMenu.getMenuid());
						newEntity.setMenuName(mainMenu.getMenuName());
						Menulist.add(newEntity);
					}
				}
				res.setResponseCode("200");
				res.setResult(Menulist);
			} else {
				res.setResponseCode("202");
				res.setResult(Allmenu);
				res.setResponseMessage("No Menu Mapped For This Role");
			}
		} else {
			res.setResponseCode("202");
			res.setResponseMessage("No Active Menu Found");
		}
		return res;
	}

	@Override
	public ResponseMessageBean getSubMenuRightsForMenuId(int menuId, int roleId) {
		ResponseMessageBean res = new ResponseMessageBean();
		List<MenuBean> submenuList = null;
		List<MenuBean> allSubmenu = menuServicedao.findActiveSubmenu(menuId);

		if (!ObjectUtils.isEmpty(allSubmenu)) {
			submenuList = menuServicedao.findSubmenuList(roleId, menuId);
			if (!ObjectUtils.isEmpty(submenuList)) {
				for (MenuBean subMenu : allSubmenu) {
					if (!submenuList.stream().anyMatch(o -> o.getSubmenuId().equals(subMenu.getSubmenuId()))) {
						MenuBean newEntity = new MenuBean();
						newEntity.setIsActive(BigDecimal.valueOf(0));
						newEntity.setSubmenuId(subMenu.getSubmenuId());
						newEntity.setSubmenuName(subMenu.getSubmenuName());
						submenuList.add(newEntity);
					}
				}
				res.setResponseCode("200");
				res.setResult(submenuList);
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Submenu Mapped To This Menu");
				res.setResult(allSubmenu);
			}
		} else {
			res.setResponseCode("202");
			res.setResponseMessage("No Active Submenu Found");
		}
		return res;
	}

	@Override
	public ResponseMessageBean getCustomizeSubMenuRightsForMenuId(int bankingType, int menuId, int roleId) {
		ResponseMessageBean res = new ResponseMessageBean();
		List<MenuBean> submenuList = null;
		List<MenuBean> allSubmenu = menuServicedao.findCustmizeActiveSubmenu(menuId);

		if (!ObjectUtils.isEmpty(allSubmenu)) {
			submenuList = menuServicedao.findCustomizeSubmenuList(bankingType, menuId);
			if (!ObjectUtils.isEmpty(submenuList)) {
				for (MenuBean subMenu : allSubmenu) {
					if (!submenuList.stream().anyMatch(o -> o.getSubmenuId().equals(subMenu.getSubmenuId()))) {
						MenuBean newEntity = new MenuBean();
						newEntity.setIsActive(BigDecimal.valueOf(0));
						newEntity.setSubmenuId(subMenu.getSubmenuId());
						newEntity.setSubmenuName(subMenu.getSubmenuName());
						newEntity.setMenuid(BigDecimal.valueOf(menuId));
						newEntity.setMenuName(subMenu.getMenuName());
						submenuList.add(newEntity);
					}
				}
				res.setResponseCode("200");
				res.setResult(submenuList);
			} else {
				res.setResponseCode("204");
				res.setResponseMessage("No Submenu Mapped To This Menu");
				res.setResult(allSubmenu);
			}
		} else {
			res.setResponseCode("202");
			res.setResponseMessage("No Active Submenu Found");
		}
		return res;
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	public void saveMenuRights(List<MenuSubmenuEntity> menuSubmenuEntity) {
		try {
			if (menuSubmenuEntity != null && !menuSubmenuEntity.isEmpty()) {
				for (MenuSubmenuEntity menu : menuSubmenuEntity) {

					int status = menu.getStatusId().getId().intValue();
					System.out.println(status);
					if (status == 0) {
						menuSubmenuRepository.deleteByRoleidAndMenuId(menu.getRoleid(), menu.getMenuId().getId());
					}
				}

			}

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	public void saveSubMenuRights(List<MenuSubmenuEntity> menuSubmenuEntity) {

		try {
			boolean success = true;
			System.out.println("menu" + menuSubmenuEntity.get(0).getMenuId().getId() + "role"
					+ menuSubmenuEntity.get(0).getRoleid());
			if (menuSubmenuEntity != null && !menuSubmenuEntity.isEmpty()) {
				List<MenuSubmenuEntity> menuSubmenuEntityNew = menuSubmenuRepository.findByRoleidAndMenuId(
						menuSubmenuEntity.get(0).getRoleid(), menuSubmenuEntity.get(0).getMenuId().getId());

				System.out.println("***hi");
				if (!menuSubmenuEntityNew.isEmpty())// If Exist
				{
					System.out.println("***in delete");
					menuSubmenuRepository.deleteByRoleidAndMenuId(menuSubmenuEntity.get(0).getRoleid(),
							menuSubmenuEntity.get(0).getMenuId().getId());

				}
			}
			if (success) {
				for (MenuSubmenuEntity aa : menuSubmenuEntity) {
					aa.setCreatedon(new Date());
				}
				menuSubmenuRepository.save(menuSubmenuEntity);
				LinkRolesEntity linkRoleObj = new LinkRolesEntity();
				linkRoleObj.setMenuId(menuSubmenuEntity.get(0).getMenuId().getId());
				linkRoleObj.setCreatedOn(new Date());
				linkRoleObj.setCreatedBy(BigInteger.valueOf(menuSubmenuEntity.get(0).getCreatedby()));
				linkRoleObj.setPrivilegeId(3);
				linkRoleObj.setRoleId(menuSubmenuEntity.get(0).getRoleid());
				linkRolesRepository.save(linkRoleObj);

			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

	}

	@Override
	public List<RoleBean> findAllRoles(int number, int offset) {

		return menuServicedao.findAllRoles(number, offset);
	}

	@Override
	public ResponseMessageBean checkMenuName(MainMenu mainMenu) {
		return menuServicedao.checkMenuName(mainMenu);
	}

	@Override
	public ResponseMessageBean checkSubMenuName(SubMenuEntity subMenuEntity) {
		return menuServicedao.checkSubMenuName(subMenuEntity);
	}

	@Override
	public ResponseMessageBean checkMenuNameWhileUpdate(MainMenu mainMenu) {
		return menuServicedao.checkMenuNameWhileUpdate(mainMenu);

	}

	@Override
	public ResponseMessageBean checkSubMenuNameWhileUpdate(SubMenuEntity subMenuEntity) {
		return menuServicedao.checkSubMenuNameWhileUpdate(subMenuEntity);

	}

	@Override
	public ResponseMessageBean checkCustomMenu(CustomizeMenus addPSBAppMenus) {
		return menuServicedao.checkCustomMenu(addPSBAppMenus);
	}

	@Override
	public ResponseMessageBean checkUpdateCustomMenu(CustomizeMenus addPSBAppMenus) {
		return menuServicedao.checkUpdateCustomMenu(addPSBAppMenus);
	}

	@Override
	public List<AccessRights> getPrivilageDataByMenuId(BigInteger menuId, BigInteger roleId) {
		return menuServicedao.getPrivilageDataByMenuId(menuId, roleId);

	}

	@Override
	public List<SubMenuEntity> getAllSubMenuList() {
		List<SubMenuEntity> subMenuEntity = subMenuRepository.findAll();
		return subMenuEntity;
	}

	@Override
	public List<SubMenuEntity> findAllSubmenu(String menuLink) {

		List<SubMenuEntity> subMenuEntity = subMenuRepository.findSubmenuByMneuLink(menuLink);
		return subMenuEntity;
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	public void addMenuInAdminWorkFlow(MainMenu mainMenu, int userStatus) {

		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(mainMenu.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(mainMenu.getActivityName());

		try {

			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

				List<MainMenu> list = mainMenuRepository.findAllByOrderByIdDesc();
				ObjectMapper mapper = new ObjectMapper();

				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();

				adminData.setCreatedByUserId(mainMenu.getUser_ID());
				adminData.setCreatedByRoleId(mainMenu.getRole_ID());
				adminData.setPageId(mainMenu.getSubMenu_ID()); // set submenuId
																// as pageid
				adminData.setCreatedBy(BigDecimal.valueOf((mainMenu.getCreatedby())));
				adminData.setContent(mapper.writeValueAsString(mainMenu));
				adminData.setActivityId(mainMenu.getSubMenu_ID()); // set
																	// submenuId
																	// as
																	// activityid
				adminData.setStatusId(BigDecimal.valueOf(statusId)); // 97-
																		// ADMIN_CHECKER_PENDIN
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6)); // 6-
																		// checker
																		// roleid
				adminData.setRemark(mainMenu.getRemark());
				adminData.setActivityName(mainMenu.getActivityName());
				adminData.setActivityRefNo(BigDecimal.valueOf(list.get(0).getId()));
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminData.setTableName("MAIN_MENU");
				adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);

				// Save data to admin work flow history
				adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(mainMenu.getSubMenu_ID(),
						new BigDecimal(list.get(0).getId()), new BigDecimal(mainMenu.getCreatedby()),
						mainMenu.getRemark(), mainMenu.getRole_ID(), mapper.writeValueAsString(mainMenu));
			}

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
	}

	public List<MainMenu> getAllMenuName() {
		List<MainMenu> list = null;
		try {
			String sqlQuery = "select m.id from MAIN_MENU m order by m.id desc";

			list = getSession().createSQLQuery(sqlQuery).setResultTransformer(Transformers.aliasToBean(MainMenu.class))
					.list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;

	}

	@Override
	public List<CustomizeSubmenuEntity> getCustomizeSubMenuByMenuId(int menuid) {
		List<CustomizeSubmenuEntity> subMenuList = customizeSubMenuRepository
				.findByCustomizeMenuid(BigInteger.valueOf(menuid));
		return subMenuList;
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	public void addCustomizeSubMenu(CustomizeSubmenuEntity customizeSubMenuEntity) {
		customizeSubMenuRepository.save(customizeSubMenuEntity);

	}

	@Override
	public CustomizeSubmenuEntity getCustomizeSubMenuByid(int id) {
		CustomizeSubmenuEntity subMenuEntity = customizeSubMenuRepository.findByid(id);
		return subMenuEntity;
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	public void updateCustomizeSubMenu(CustomizeSubmenuEntity customizeSubMenuEntity) {

		CustomizeSubmenuEntity subMenu = customizeSubMenuRepository.findOne(customizeSubMenuEntity.getId());
		subMenu.setMenuName(customizeSubMenuEntity.getMenuName());
		subMenu.setMenudesc(customizeSubMenuEntity.getMenudesc());
		subMenu.setMenuLogo(customizeSubMenuEntity.getMenuLogo());
		subMenu.setMenuLink(customizeSubMenuEntity.getMenuLink());
		subMenu.setIsActive(customizeSubMenuEntity.getIsActive());
		subMenu.setJson_key(customizeSubMenuEntity.getJson_key());
		customizeSubMenuRepository.save(subMenu);

	}

	@Override
	public ResponseMessageBean checkCustomizeSubMenuName(CustomizeSubmenuEntity customizeSubMenuEntity) {
		return menuServicedao.checkCustomizeSubMenuName(customizeSubMenuEntity);

	}

	@Override
	public ResponseMessageBean checkUpdateCustomizeSubMenu(CustomizeSubmenuEntity customizeSubMenuEntity) {
		return menuServicedao.checkUpdateCustomizeSubMenu(customizeSubMenuEntity);

	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	public void saveCustomizeMenuRights(List<CustomizeMenuSubMenuEntity> menuSubmenuEntity) {
		try {
			if (menuSubmenuEntity != null && !menuSubmenuEntity.isEmpty()) {
				for (CustomizeMenuSubMenuEntity menu : menuSubmenuEntity) {

					int status = menu.getStatusId().getId().intValue();
					System.out.println(status);
					if (status == 0) {
						customizeMenuSubmenuRepository.deleteByBankingTypeAndMenuId(menu.getBankingType().toUpperCase(),
								menu.getCustomizeMenuId().getId());
					}
				}

			}

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	public void saveCustomizeSubMenuRights(List<CustomizeMenuSubMenuEntity> menuSubmenuEntity) {

		try {
			boolean success = true;
			/*
			 * System.out.println("menu" + menuSubmenuEntity.get(0).getMenuId().getId() +
			 * "role" + menuSubmenuEntity.get(0).getRoleid());
			 */
			if (menuSubmenuEntity != null && !menuSubmenuEntity.isEmpty()) {
				List<CustomizeMenuSubMenuEntity> menuSubmenuEntityNew = customizeMenuSubmenuRepository
						.findByBankingTypeAndMenuId(menuSubmenuEntity.get(0).getBankingType(),
								menuSubmenuEntity.get(0).getCustomizeMenuId().getId());

				System.out.println("***hi");
				if (!menuSubmenuEntityNew.isEmpty())// If Exist
				{
					System.out.println("***in delete");
					customizeMenuSubmenuRepository.deleteByBankingTypeAndMenuId(
							menuSubmenuEntity.get(0).getBankingType(),
							menuSubmenuEntity.get(0).getCustomizeMenuId().getId());

				}
			}
			if (success) {

				customizeMenuSubmenuRepository.save(menuSubmenuEntity);
			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

	}

	@Autowired
	MenuService menuService;

	@Override
	public ResponseMessageBean getCustomizeMenuRightsByBankTypeAndAppId(int bankType, int appId, int roleId) {
		ResponseMessageBean res = new ResponseMessageBean();
		List<CustomizeMenuSubMenuEntity> CustMenulist = new ArrayList<>();

		List<CustomizeMenus> Allmenu = menuServicedao.finaAllCustomizeActiveMenu(appId, roleId);
		if (!ObjectUtils.isEmpty(Allmenu)) {
			List<CustomizeMenuSubMenuEntity> Menulist = menuServicedao.findCustomizeMenuId(bankType, appId);
			if (!ObjectUtils.isEmpty(Menulist)) {
				for (CustomizeMenus mainMenu : Allmenu) {
					if (!Menulist.stream().anyMatch(o -> o.getCustMenuId().equals(mainMenu.getMenuid()))) {

						CustomizeMenuSubMenuEntity newEntity = new CustomizeMenuSubMenuEntity();

						newEntity.setIsActive(BigInteger.valueOf(0));
						newEntity.setCustMenuId(mainMenu.getCustMenuId());
						newEntity.setMenuname(mainMenu.getMenuName());
						Menulist.add(newEntity);
					}
				}
				res.setResponseCode("200");
				res.setResult(Menulist);
			} else {
				res.setResponseCode("204");
				res.setResult(Allmenu);
				res.setResponseMessage("No Menu Mapped For This");
			}
		} else {
			res.setResponseCode("202");
			res.setResponseMessage("No Active Menu Found");
		}
		return res;
	}

	@Override
	public List<CustomizationSubmenuEntity> getAllCustomizationSubMenu() {
		return menuServicedao.getAllCustomizationSubMenu();
	}

	@Override
	public void addCustomizationSubMenu(CustomizationSubmenuEntity customizeSubMenuEntity) {
		menuServicedao.addCustomizationSubMenu(customizeSubMenuEntity);

	}

	@Override
	public List<CustomizationSubmenuEntity> getCustomizationSubMenuByid(int id) {
		return menuServicedao.getCustomizationSubMenuByid(id);
	}

	@Override
	public void updateCustomizationSubMenu(CustomizationSubmenuEntity customizeSubMenuEntity) {
		menuServicedao.updateCustomizationSubMenu(customizeSubMenuEntity);

	}

	@Override
	public ResponseMessageBean checkUpdateCustomizationSubMenu(CustomizationSubmenuEntity customizeSubMenuEntity) {
		return menuServicedao.checkUpdateCustomizationSubMenu(customizeSubMenuEntity);
	}

	@Override
	public ResponseMessageBean checkCustomizationSubMenuName(CustomizationSubmenuEntity customizeSubMenuEntity) {
		return menuServicedao.checkCustomizationSubMenuName(customizeSubMenuEntity);
	}

	@Override
	public ResponseMessageBean getCustomizationMenuByRoleIdAndAppId(int roleId, int appId) {
		ResponseMessageBean res = new ResponseMessageBean();
		List<CustomizeMenus> Allmenu = menuServicedao.finaAllCustomizeActiveMenu(appId, roleId);
		if (!ObjectUtils.isEmpty(Allmenu)) {
			List<MenuBean> Menulist = menuServicedao.findCustomizationMenuId(appId);
			if (!ObjectUtils.isEmpty(Menulist)) {
				for (CustomizeMenus mainMenu : Allmenu) {
					if (!Menulist.stream().anyMatch(o -> o.getMenuid().equals(mainMenu.getMenuid()))) {

						MenuBean newEntity = new MenuBean();

						newEntity.setIsActive(BigDecimal.valueOf(0));
						newEntity.setMenuid(new BigDecimal(mainMenu.getCustMenuId()));
						newEntity.setMenuName(mainMenu.getMenuName());
						Menulist.add(newEntity);
					}
				}
				res.setResponseCode("200");
				res.setResult(Menulist);
			} else {
				res.setResponseCode("202");
				res.setResult(Allmenu);
				res.setResponseMessage("No Menu Mapped For This");
			}
		} else {
			res.setResponseCode("202");
			res.setResponseMessage("No Active Menu Found");
		}
		return res;
	}

	@Override
	public ResponseMessageBean getCustomizationSubMenuByAppId(int menuId, int appId) {
		ResponseMessageBean res = new ResponseMessageBean();

		List<CustomizationMenuSubmenuEntity> MenulistNew = new ArrayList<>();
		List<CustomizationSubmenuEntity> Allmenu = menuServicedao.findAllActivePSBSubmenu(appId);
		if (!ObjectUtils.isEmpty(Allmenu)) {
			List<CustomizationMenuSubmenuEntity> Menulist = menuServicedao.findAllCustmizationActiveSubmenu(menuId,
					appId);
			if (!ObjectUtils.isEmpty(Menulist)) {
				for (CustomizationSubmenuEntity mainMenu : Allmenu) {

					for (CustomizationMenuSubmenuEntity menuSubMeu : Menulist) {
						if (menuSubMeu.getCustSubmenuId().equals(mainMenu.getSubmenuid())) {

							CustomizationMenuSubmenuEntity newEntity = new CustomizationMenuSubmenuEntity();

							newEntity.setIsActive(mainMenu.getStatusId());
							newEntity.setCustMenuId(menuSubMeu.getCustMenuId());
							newEntity.setMenuName(menuSubMeu.getMenuName());

							newEntity.setCustSubmenuId(menuSubMeu.getCustSubmenuId());
							newEntity.setSubMenuName(menuSubMeu.getSubMenuName());
							newEntity.setAppId(menuSubMeu.getAppId());
							MenulistNew.add(newEntity);
						}
					}
				}
				res.setResponseCode("200");
				res.setResult(MenulistNew);
			} else {
				res.setResponseCode("204");
				CustomizationMenuSubmenuEntity newEntity = new CustomizationMenuSubmenuEntity();

				List<CustomizeMenus> menuList = menuServicedao.getPSBAppMenuRightById(BigInteger.valueOf(menuId));
				newEntity.setCustMenuId(BigInteger.valueOf(menuId));
				newEntity.setMenuName(menuList.get(0).getModuleName());
				newEntity.setAppId(BigInteger.valueOf(appId));
				MenulistNew.add(newEntity);

				res.setResult(MenulistNew);
				res.setResponseMessage("No Menu Mapped For This");
			}
		} else {
			res.setResponseCode("202");
			res.setResponseMessage("No Active Menu Found");
		}
		return res;
	}

	@Override
	public boolean addCustomizationMenuSubMenuMapping(CustomizationMenuSubmenuEntity menuSubmenuEntity) {
		return menuServicedao.addCustomizationMenuSubMenuMapping(menuSubmenuEntity);
	}

	@Override
	public boolean saveCustomizationMenuSubMenuMapping(List<CustomizationMenuSubmenuEntity> menuSubmenuEntity) {
		return menuServicedao.saveCustomizationMenuSubMenuMapping(menuSubmenuEntity);
	}

	@Override
	public boolean saveCustomizationMainMenuMapping(List<CustomizationMenuSubmenuEntity> menuSubmenuEntity) {
		return menuServicedao.saveCustomizationMainMenuMapping(menuSubmenuEntity);
	}

}
