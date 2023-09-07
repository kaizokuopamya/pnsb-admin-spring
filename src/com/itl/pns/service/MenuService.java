package com.itl.pns.service;
import java.math.BigInteger;
import java.util.List;

import com.itl.pns.bean.LinkRolesBean;
import com.itl.pns.bean.MainMenuBean;
import com.itl.pns.bean.MainMenuDetailsBean;
import com.itl.pns.bean.MenuBean;
import com.itl.pns.bean.MenuSubmenuBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.bean.RoleBean;
import com.itl.pns.entity.AccessRights;
import com.itl.pns.entity.CustomizationMenuSubmenuEntity;
import com.itl.pns.entity.CustomizationSubmenuEntity;
import com.itl.pns.entity.CustomizeMenuSubMenuEntity;
import com.itl.pns.entity.CustomizeMenus;
import com.itl.pns.entity.CustomizeSubmenuEntity;
import com.itl.pns.entity.MainMenu;
import com.itl.pns.entity.MenuSubmenuEntity;
import com.itl.pns.entity.SubMenuEntity;

public interface MenuService {


	void saveAccessRights(List<AccessRights> accessRightsBean);

	List<AccessRights> getAccessRightsForRoleId(BigInteger roleId);
	

	List<LinkRolesBean> getPrivilegeById(BigInteger moduleId, BigInteger roleId);

	List<LinkRolesBean> getAllPrivileges(BigInteger rn);

	List<MenuBean> getMenuDetails(int roleId);

	List<MenuBean> getMenuDetailsAccess();

	void saveCustomMenus(List<CustomizeMenus> saveCustomMenus);

	List<MenuSubmenuBean> findAll(int roleId);

	List<MainMenuBean> getMainMenu();

	void addMenu(MainMenu mainMenu);
	

	MainMenuBean getMainMenuByid(int id);

	List<SubMenuEntity> getSubMenuListBymenuId(int menuid);

	void addSubMenu(SubMenuEntity subMenuEntity);

	SubMenuEntity getSubMenuByid(int id);

	void updateSubMenu(SubMenuEntity subMenuEntity);

	void addMenuSubMenuMapping(MenuSubmenuEntity menuSubmenuEntity);

	MenuSubmenuEntity getMenuSubMenuMappingByid(int id);

	void updateMenuSubMenuMapping(MenuSubmenuEntity menuSubmenuEntity);

	void updateMainMenu(MainMenu mainMenu);



	List<MenuSubmenuEntity> getSubMenuBymenuId(int menuId);

	void saveSubMenuRights(List<MenuSubmenuEntity> menuSubmenuEntity);

	void saveMenuRights(List<MenuSubmenuEntity> menuSubmenuEntity);

	ResponseMessageBean getSubMenuRightsForMenuId(int menuId, int roleId);
	
	ResponseMessageBean getMenusForAppId(BigInteger appId, BigInteger roleId);
	
	ResponseMessageBean getPSBAppMenuRightById(BigInteger id);
	
	void addPSBAppMenus(CustomizeMenus addPSBAppMenus);
	
	void updatePSBAppMenus(CustomizeMenus psbAppMenusData);
	
	List<RoleBean> findAllRoles(int number, int offset);
	
	public ResponseMessageBean checkMenuName(MainMenu mainMenu);

	public ResponseMessageBean checkSubMenuName(SubMenuEntity subMenuEntity);
	
	public ResponseMessageBean checkMenuNameWhileUpdate(MainMenu mainMenu);

	public ResponseMessageBean checkSubMenuNameWhileUpdate(SubMenuEntity subMenuEntity);
	
	public ResponseMessageBean checkCustomMenu(CustomizeMenus addPSBAppMenus);
	
	public ResponseMessageBean checkUpdateCustomMenu(CustomizeMenus addPSBAppMenus);
	
	
	List<AccessRights> getPrivilageDataByMenuId(BigInteger menuId,BigInteger roleId);
	
	public List<SubMenuEntity> getAllSubMenuList();

	public List<SubMenuEntity> findAllSubmenu(String menuLink);
	
	void addMenuInAdminWorkFlow(MainMenu mainMenu,int userStatus);
	
	public List<MainMenuDetailsBean> getMainMenuDetailsByid(int id);
	
	
	List<CustomizeSubmenuEntity> getCustomizeSubMenuByMenuId(int menuid);

	void addCustomizeSubMenu(CustomizeSubmenuEntity customizeSubMenuEntity);

	CustomizeSubmenuEntity getCustomizeSubMenuByid(int id);

	void updateCustomizeSubMenu(CustomizeSubmenuEntity customizeSubMenuEntity);
	
	
	
	public ResponseMessageBean checkCustomizeSubMenuName(CustomizeSubmenuEntity customizeSubMenuEntity);

	public ResponseMessageBean checkUpdateCustomizeSubMenu(CustomizeSubmenuEntity customizeSubMenuEntity);
	
	
	
	void saveCustomizeMenuRights(List<CustomizeMenuSubMenuEntity> menuSubmenuEntity);

	void saveCustomizeSubMenuRights(List<CustomizeMenuSubMenuEntity> menuSubmenuEntity);
	
	ResponseMessageBean getMenuRightsForRoleId(int roleId);
	
	ResponseMessageBean getCustomizeMenuRightsByBankTypeAndAppId(int bankType, int appId,int roleId);
	
	public ResponseMessageBean getCustomizeSubMenuRightsForMenuId(int bankingType, int menuId, int roleId);
	
	
	
	
	public List<CustomizationSubmenuEntity> getAllCustomizationSubMenu();
	
	void addCustomizationSubMenu(CustomizationSubmenuEntity customizeSubMenuEntity);

	public List<CustomizationSubmenuEntity> getCustomizationSubMenuByid(int id);

	void updateCustomizationSubMenu(CustomizationSubmenuEntity customizeSubMenuEntity);
	
	public ResponseMessageBean checkUpdateCustomizationSubMenu(CustomizationSubmenuEntity customizeSubMenuEntity);

	public ResponseMessageBean checkCustomizationSubMenuName(CustomizationSubmenuEntity customizeSubMenuEntity);
	
	
	public ResponseMessageBean getCustomizationMenuByRoleIdAndAppId(int roleId,int appId);
	
	public ResponseMessageBean getCustomizationSubMenuByAppId(int menuId,int appId);
	
	
	public boolean addCustomizationMenuSubMenuMapping(CustomizationMenuSubmenuEntity menuSubmenuEntity);
	
	public boolean saveCustomizationMenuSubMenuMapping(List<CustomizationMenuSubmenuEntity> menuSubmenuEntity);
	
	public boolean saveCustomizationMainMenuMapping(List<CustomizationMenuSubmenuEntity> menuSubmenuEntity);
	
	
	
	
	
	
	
	

}
