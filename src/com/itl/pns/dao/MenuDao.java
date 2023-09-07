package com.itl.pns.dao;

import java.math.BigInteger;
import java.util.List;

import com.itl.pns.bean.LinkRolesBean;
import com.itl.pns.bean.MainMenuBean;
import com.itl.pns.bean.MainMenuDetailsBean;
import com.itl.pns.bean.MenuBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.bean.RoleBean;
import com.itl.pns.entity.AccessRights;
import com.itl.pns.entity.CustomizationMenuSubmenuEntity;
import com.itl.pns.entity.CustomizationSubmenuEntity;
import com.itl.pns.entity.CustomizeMenuSubMenuEntity;
import com.itl.pns.entity.CustomizeMenus;
import com.itl.pns.entity.CustomizeSubmenuEntity;
import com.itl.pns.entity.MainMenu;
import com.itl.pns.entity.SubMenuEntity;

public interface MenuDao {


	List<AccessRights> getAccessRightsForRoleId(BigInteger roleId);

	boolean deleteExistingRole(BigInteger roleId);


	List<LinkRolesBean> findAllByroleId(BigInteger roleId);


	List<LinkRolesBean> findPreviligeByroleId(BigInteger roleId,
			BigInteger moduleId);

	List<MenuBean> getMenuDetails(int roleId);

	List<MenuBean> getMenuDetailsAccess();


	List<MenuBean> findMenuId(int roleId);
	
	public List<CustomizeMenuSubMenuEntity> findCustomizeMenuId(int bankType,int appId);

	List<CustomizeMenus> getPSBMenusForAppId(BigInteger appId, BigInteger roleid);
	
	List<CustomizeMenus> getPSBAppMenuRightById(BigInteger id);
	

	List<MenuBean> findSubmenuList(int roleId, int menuId);


	List<MenuBean> findActiveSubmenu(int menuId);
	
	public List<MenuBean> findCustmizeActiveSubmenu(int menuId);

	List<MenuBean> finaAllActiveMenu();
	
	 List<CustomizeMenus> finaAllCustomizeActiveMenu(int appId, int roleId);

	List<CustomizeMenus> getAllPSBMenusList();
	
	 List<RoleBean> findAllRoles(int number, int offset); 
	 
	 public ResponseMessageBean checkMenuName(MainMenu mainMenu);

	 public ResponseMessageBean checkSubMenuName(SubMenuEntity subMenuEntity);
	 
	 public ResponseMessageBean checkMenuNameWhileUpdate(MainMenu mainMenu);

	public ResponseMessageBean checkSubMenuNameWhileUpdate(SubMenuEntity subMenuEntity);
	
	public ResponseMessageBean checkCustomMenu(CustomizeMenus addPSBAppMenus);
	
	public ResponseMessageBean checkUpdateCustomMenu(CustomizeMenus addPSBAppMenus);
	
	List<AccessRights> getPrivilageDataByMenuId(BigInteger menuId,BigInteger roleId);
	
	public List<MainMenuDetailsBean> getMainMenuDetailsByid(int id);
	
	
	public ResponseMessageBean checkCustomizeSubMenuName(CustomizeSubmenuEntity customizeSubMenuEntity);

	public ResponseMessageBean checkUpdateCustomizeSubMenu(CustomizeSubmenuEntity customizeSubMenuEntity);
	
	public List<MenuBean> findCustomizeSubmenuList(int bankType, int menuId);
	
	
	
	
	
	
	
	
	
	

	public List<CustomizationSubmenuEntity> getAllCustomizationSubMenu();
	
	void addCustomizationSubMenu(CustomizationSubmenuEntity customizeSubMenuEntity);

	public List<CustomizationSubmenuEntity> getCustomizationSubMenuByid(int id);

	void updateCustomizationSubMenu(CustomizationSubmenuEntity customizeSubMenuEntity);
	
	public ResponseMessageBean checkUpdateCustomizationSubMenu(CustomizationSubmenuEntity customizeSubMenuEntity);

	public ResponseMessageBean checkCustomizationSubMenuName(CustomizationSubmenuEntity customizeSubMenuEntity);
	
	
	
	public List<MenuBean> findCustomizationMenuId(int appId);
	
	public List<CustomizationMenuSubmenuEntity>findAllCustmizationActiveSubmenu(int menuId, int appId);
	
	public List<CustomizationSubmenuEntity>findAllActivePSBSubmenu(int appId);
	
	public ResponseMessageBean checkIsSubMenuMapped(CustomizationMenuSubmenuEntity menuSubmenuEntity);
	
	public boolean addCustomizationMenuSubMenuMapping(CustomizationMenuSubmenuEntity menuSubmenuEntity);
	
	public boolean saveCustomizationMenuSubMenuMapping(List<CustomizationMenuSubmenuEntity> menuSubmenuEntity);

	public boolean saveCustomizationMainMenuMapping(List<CustomizationMenuSubmenuEntity> menuSubmenuEntity);
	
	public MainMenuBean getAllMainMenusById(int id);

	public List<MainMenuBean>  getAllMainMenus();
}
