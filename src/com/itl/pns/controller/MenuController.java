package com.itl.pns.controller;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.itl.pns.bean.LinkRolesBean;
import com.itl.pns.bean.MainMenuBean;
import com.itl.pns.bean.MainMenuDetailsBean;
import com.itl.pns.bean.MenuBean;
import com.itl.pns.bean.MenuSubMenuDetailsBean;
import com.itl.pns.bean.MenuSubmenuBean;
import com.itl.pns.bean.RequestParamBean;
import com.itl.pns.bean.Response;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.bean.RoleBean;
import com.itl.pns.constant.ApplicationConstants;
import com.itl.pns.dao.MenuDao;
import com.itl.pns.entity.AccessRights;
import com.itl.pns.entity.ActivitySettingMasterEntity;
import com.itl.pns.entity.CustomizationMenuSubmenuEntity;
import com.itl.pns.entity.CustomizationSubmenuEntity;
import com.itl.pns.entity.CustomizeMenuSubMenuEntity;
import com.itl.pns.entity.CustomizeMenus;
import com.itl.pns.entity.CustomizeSubmenuEntity;
import com.itl.pns.entity.MainMenu;
import com.itl.pns.entity.MenuSubmenuEntity;
import com.itl.pns.entity.SubMenuEntity;
import com.itl.pns.service.MenuService;
import com.itl.pns.util.AdminWorkFlowReqUtility;
import com.itl.pns.util.RandomNumberGenerator;

@RestController
@RequestMapping("menu")
public class MenuController {

	private static final Logger logger = LogManager.getLogger(MenuController.class);

	@Value("${bot.image.folder}")
	private String botImageFolder;

	@Autowired
	MenuService menuService;

	@Autowired
	AdminWorkFlowReqUtility adminWorkFlowReqUtility;

	@Autowired
	MenuDao menuDao;

	/**
	 * getMenuDetails Details
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getMenuDetails", method = RequestMethod.POST)
	public ResponseEntity<List<MenuBean>> getLocations(@RequestBody RequestParamBean requestBean) {

		List<MenuBean> getMenuDetails = menuService.getMenuDetails(Integer.parseInt(requestBean.getId1()));

		return new ResponseEntity<>(getMenuDetails, HttpStatus.OK);
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getMenuDetailsAccess", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getMenuDetailsAccess() {
		ResponseMessageBean response = new ResponseMessageBean();
		try {
		List<MenuBean> getMenuDetails = menuService.getMenuDetailsAccess();

		
			if (!getMenuDetails.isEmpty()) {
				response.setResponseCode("200");
				response.setResult(getMenuDetails);
				response.setResponseMessage("Success");
			} else {
				response.setResponseCode("202");
				response.setResponseMessage("No record found");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@RequestMapping(value = "/saveAccessRights", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> saveAccessRights(@RequestBody List<AccessRights> accessRights) {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
		menuService.saveAccessRights(accessRights);
		
			res.setResponseCode("200");
			res.setResponseMessage("Access Right Has Been Updated Successfully");
		} catch (Exception e) {
			logger.info("Exception:", e);
			res.setResponseCode("500");
			res.setResponseMessage("Error Occured While Updating The Record");
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/getAccessRightsForRoleId", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getAccessRightsForRoleId(@RequestBody RequestParamBean requestBean) {
		ResponseMessageBean response = new ResponseMessageBean();
		try {
		List<AccessRights> accessRights = menuService.getAccessRightsForRoleId(new BigInteger(requestBean.getId1()));
		
			if (!accessRights.isEmpty()) {
				response.setResponseCode("200");
				response.setResult(accessRights);
				response.setResponseMessage("Success");
			} else {
				response.setResponseCode("202");
				response.setResponseMessage("No record found");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@RequestMapping(value = "/getRolePrivileges", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Response> getPrivilegesA(@RequestBody RequestParamBean requestBean) {

		Response response = new Response();
		HttpStatus status = null;
		BigInteger rn = new BigInteger(requestBean.getId1());
		if (!ObjectUtils.isEmpty(rn)) {

			Map<String, Object> responseMap = new HashMap<>();

			Map<String, List<String>> modulePrivileges = new HashMap<>();
			try {
				List<LinkRolesBean> privileges = menuService.getAllPrivileges(rn);

				if (!ObjectUtils.isEmpty(privileges)) {
					for (LinkRolesBean linkRolesBean : privileges) {
						List<String> privilegeNameList = new ArrayList<>();
						BigInteger moduleId = linkRolesBean.getMENU_ID().toBigInteger();
						String moduleName = linkRolesBean.getMENU_NAME();

						List<LinkRolesBean> linkRolesBean1 = menuService.getPrivilegeById(moduleId, rn);

						if (!ObjectUtils.isEmpty(linkRolesBean)) {
							for (LinkRolesBean linkRolesBean2 : linkRolesBean1) {

								privilegeNameList.add(linkRolesBean2.getPRIVILEGENAME());
							}
						}

						if (!ObjectUtils.isEmpty(moduleId)) {
							modulePrivileges.put(moduleName, privilegeNameList);
						}

					}
				}
				responseMap.put("role", rn);
				responseMap.put("entitlement", modulePrivileges);
				Set set = responseMap.entrySet();
				Iterator iterator = set.iterator();
				while (iterator.hasNext()) {
					Map.Entry mentry = (Map.Entry) iterator.next();
				}
				response.setData(responseMap);
				status = HttpStatus.OK;
				response.setStatus(status.toString());

			} catch (Exception e) {
				status = HttpStatus.EXPECTATION_FAILED;
				response.setMessage(e.getMessage());
				response.setStatus(status.toString());
				logger.info("Exception:", e);
			}
		} else {
			status = HttpStatus.BAD_REQUEST;
			response.setMessage("Invalid input param received. Params cannot be null");
			response.setStatus(status.toString());
		}

		return new ResponseEntity<>(response, status);

	}

	@RequestMapping(value = "/getRolePrivilegesby", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Response> getPrivilegesByMenuId(@RequestBody RequestParamBean requestBean) {

		Response response = new Response();
		HttpStatus status = null;
		BigInteger moduleId = new BigInteger(requestBean.getId1());
		BigInteger rn = new BigInteger(requestBean.getId2());
		if (!ObjectUtils.isEmpty(rn)) {

			Map<String, Object> responseMap = new HashMap<>();

			Map<String, List<String>> modulePrivileges = new HashMap<>();
			try {
				List<String> privilegeNameList = new ArrayList<>();
				List<LinkRolesBean> linkRolesBean1 = menuService.getPrivilegeById(moduleId, rn);
				String moduleName = "";
				if (!ObjectUtils.isEmpty(linkRolesBean1)) {
					for (LinkRolesBean linkRolesBean2 : linkRolesBean1) {
						moduleName = linkRolesBean2.getMENU_NAME();
						privilegeNameList.add(linkRolesBean2.getPRIVILEGENAME());
					}
				}

				if (!ObjectUtils.isEmpty(moduleId)) {
					modulePrivileges.put(moduleName, privilegeNameList);
				}
				responseMap.put("role", rn);
				responseMap.put("entitlement", modulePrivileges);
				Set set = responseMap.entrySet();
				Iterator iterator = set.iterator();
				while (iterator.hasNext()) {
					Map.Entry mentry = (Map.Entry) iterator.next();
				}
				response.setData(responseMap);
				status = HttpStatus.OK;
				response.setStatus(status.toString());

			} catch (Exception e) {
				status = HttpStatus.EXPECTATION_FAILED;
				response.setMessage(e.getMessage());
				response.setStatus(status.toString());
				logger.info("Exception:", e);
			}
		} else {
			status = HttpStatus.BAD_REQUEST;
			response.setMessage("Invalid input param received. Params cannot be null");
			response.setStatus(status.toString());
		}
		return new ResponseEntity<>(response, status);

	}

	@RequestMapping(value = "/getPSBAppMenuRight", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getMenusForAppId(@RequestBody RequestParamBean requestBean) {
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			response = menuService.getMenusForAppId(new BigInteger(requestBean.getId1()),
					new BigInteger(requestBean.getId2()));
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/getPSBAppMenuRightById", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getMenusForAppIdById(@RequestBody RequestParamBean requestBean) {
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			response = menuService.getPSBAppMenuRightById(new BigInteger(requestBean.getId1()));
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/addPSBAppMenus", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> addPSBAppMenus(@RequestBody CustomizeMenus addPSBAppMenus) {
		ResponseMessageBean res = new ResponseMessageBean();
		ResponseMessageBean responsecode = new ResponseMessageBean();
		try {

			if (!ObjectUtils.isEmpty(addPSBAppMenus)) {

				/*
				 * String splitArray[] = addPSBAppMenus.getMenuImageString().split("#"); File
				 * file1 = new File(botImageFolder + splitArray[0]); byte[] fileContent =
				 * FileUtils.readFileToByteArray(file1); String encodedStringSmall =
				 * Base64.getEncoder().encodeToString(fileContent);
				 * addPSBAppMenus.setMenuImage(encodedStringSmall);
				 */
				addPSBAppMenus.setMenuImage(addPSBAppMenus.getMenuImageString());
				responsecode = menuService.checkCustomMenu(addPSBAppMenus);
				if (responsecode.getResponseCode().equalsIgnoreCase("200")) {
					menuService.addPSBAppMenus(addPSBAppMenus);
					res.setResponseCode("200");
					res.setResponseMessage("Details Has Been Added Successfully");

				} else {
					res.setResponseCode("202");
					res.setResponseMessage(responsecode.getResponseMessage());

				}
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
			res.setResponseCode("500");
			res.setResponseMessage("Error occured while adding the record");
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/updatePSBAppMenus", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updatePSBAppMenus(@RequestBody CustomizeMenus psbAppMenusData) {
		ResponseMessageBean res = new ResponseMessageBean();
		ResponseMessageBean responsecode = new ResponseMessageBean();
		try {

			if (!ObjectUtils.isEmpty(psbAppMenusData)) {

				/*
				 * String splitArray[] = psbAppMenusData.getMenuImageString().split("#"); File
				 * file1 = new File(botImageFolder + splitArray[0]); byte[] fileContent =
				 * FileUtils.readFileToByteArray(file1); String encodedStringSmall =
				 * Base64.getEncoder().encodeToString(fileContent);
				 * //psbAppMenusData.setMenuImage(new
				 * javax.sql.rowset.serial.SerialClob(encodedStringSmall.toCharArray()));
				 * psbAppMenusData.setMenuImage(encodedStringSmall);
				 */
				psbAppMenusData.setMenuImage(psbAppMenusData.getMenuImageString());
				responsecode = menuService.checkUpdateCustomMenu(psbAppMenusData);
				if (responsecode.getResponseCode().equalsIgnoreCase("200")) {
					menuService.updatePSBAppMenus(psbAppMenusData);
					res.setResponseCode("200");
					res.setResponseMessage("Customization Menus Has Been Updated Successfully");

				} else {
					res.setResponseCode("202");
					res.setResponseMessage(responsecode.getResponseMessage());

				}
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
			res.setResponseCode("500");
			res.setResponseMessage("Error occured while Updating the record");
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/savePSBAppMenus", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> saveCustomMenus(@RequestBody List<CustomizeMenus> saveCustomMenus) {
		ResponseMessageBean res = new ResponseMessageBean();

		try {

			menuService.saveCustomMenus(saveCustomMenus);
			res.setResponseCode("200");
			res.setResponseMessage("Customization Menus  Has Been Updated Successfully");

		} catch (Exception e) {
			logger.info("Exception:", e);
			res.setResponseCode("500");
			res.setResponseMessage("Error Occured While Updating The Tecord");
		}
		return new ResponseEntity<>(res, HttpStatus.OK);

	}

	@RequestMapping(value = "/findAllLeftMenu", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> findAll(@RequestBody RequestParamBean requestBean) {
		ResponseMessageBean res = new ResponseMessageBean();
		List<MenuSubmenuBean> menuSubmenuBean = menuService.findAll(Integer.parseInt(requestBean.getId1()));
		try {
			if (null != menuSubmenuBean) {
				res.setResponseCode("200");
				res.setResult(menuSubmenuBean);
				res.setResponseMessage("Success");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);

	}

	@RequestMapping(value = "/getMainMenu", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getMainMenu() {
		ResponseMessageBean res = new ResponseMessageBean();
		List<MainMenuBean> mainMenu = menuService.getMainMenu();
		mainMenu.forEach(mainMenuBean -> {
			String create = new SimpleDateFormat(" dd/MM/YYYY HH:mm:ssZ").format(mainMenuBean.getCreatedon());
			String update = new SimpleDateFormat(" dd/MM/YYYY HH:mm:ssZ").format(mainMenuBean.getUpdatedon());

		});
		try {

			if (null != mainMenu) {
				res.setResponseCode("200");
				res.setResult(mainMenu);

			} else {
				res.setResponseCode("202");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/getMainMenuByid", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getMainMenuByid(@RequestBody RequestParamBean requestBean) {
		ResponseMessageBean response = new ResponseMessageBean();
		MainMenuBean mainMenu = menuService.getMainMenuByid(Integer.parseInt(requestBean.getId1()));
		try {
			if (null != mainMenu) {
				response.setResponseCode("200");
				response.setResult(mainMenu);
			} else {
				response.setResponseCode("202");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/getMainMenuDetailsByid", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getMainMenuDetailsByid(@RequestBody RequestParamBean requestBean) {
		ResponseMessageBean response = new ResponseMessageBean();
		try {
		List<MainMenuDetailsBean> mainMenu = menuService.getMainMenuDetailsByid(Integer.parseInt(requestBean.getId1()));
		
			if (null != mainMenu) {
				response.setResponseCode("200");
				response.setResult(mainMenu);
			} else {
				response.setResponseCode("202");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/addMainMenu", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> addMainMenu(@RequestBody MainMenu mainMenu) {
		int userStatus = mainMenu.getStatusId();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(mainMenu.getRole_ID().intValue());
		mainMenu.setStatusName(adminWorkFlowReqUtility.getStatusNameByStatusId(mainMenu.getStatusId()));
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(mainMenu.getActivityName());

		ResponseMessageBean res = new ResponseMessageBean();
		ResponseMessageBean responsecode = new ResponseMessageBean();

		try {
			if (mainMenu.getMenuname() != null) {
				responsecode = menuService.checkMenuName(mainMenu);

				if (responsecode.getResponseCode().equalsIgnoreCase("200")) {

					menuService.addMenu(mainMenu);
					menuService.addMenuInAdminWorkFlow(mainMenu, userStatus);
					res.setResponseCode("200");

					if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
							&& (ApplicationConstants.YESVALUE)
									.equals(Character.toString(activityMasterData.get(0).getChecker()))
							&& (ApplicationConstants.YESVALUE)
									.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
						res.setResponseMessage("Request Submitted To Admin Checker For Approval");
					} else {

						res.setResponseMessage("Main Menu Has Been Added Successfully");
					}

				} else {
					res.setResponseCode("500");
					res.setResponseMessage("Name Already Exists");
				}
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
			res.setResponseCode("500");
			res.setResponseMessage("Error occured while adding the record");
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateMainMenu", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateMainMenu(@RequestBody MainMenu mainMenu) {

		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(mainMenu.getRole_ID().intValue());
		mainMenu.setStatusName(adminWorkFlowReqUtility.getStatusNameByStatusId(mainMenu.getStatusId()));
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(mainMenu.getActivityName());
		ResponseMessageBean res = new ResponseMessageBean();
		ResponseMessageBean responsecode = new ResponseMessageBean();
		try {
			if (mainMenu.getId() != 0) {
				responsecode = menuService.checkMenuNameWhileUpdate(mainMenu);
				if (responsecode.getResponseCode().equalsIgnoreCase("200")) {
					menuService.updateMainMenu(mainMenu);
					res.setResponseCode("200");
					if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
							&& (ApplicationConstants.YESVALUE)
									.equals(Character.toString(activityMasterData.get(0).getChecker()))
							&& (ApplicationConstants.YESVALUE)
									.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
						res.setResponseMessage("Request Submitted To Admin Checker For Approval");
					} else {

						res.setResponseMessage("Main Menu Has Been Updated Successfully");
					}

				} else {
					res.setResponseCode("500");
					res.setResponseMessage("Name Already Exists");
				}
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
			res.setResponseCode("500");
			res.setResponseMessage("Error occured while updating the record");
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/getSubMenuBymenuId", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getSubMenuListBymenuId(@RequestBody RequestParamBean requestBean) {
		List<SubMenuEntity> subMenuList = menuService.getSubMenuListBymenuId(Integer.parseInt(requestBean.getId1()));
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			if (null != subMenuList) {
				res.setResponseCode("200");
				res.setResult(subMenuList);
			} else {
				res.setResponseCode("202");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/addSubMenu", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> addSubMenu(@RequestBody SubMenuEntity subMenuEntity) {
		ResponseMessageBean res = new ResponseMessageBean();
		ResponseMessageBean responsecode = new ResponseMessageBean();
		try {

			if (subMenuEntity.getMenuName() != null) {
				responsecode = menuService.checkSubMenuName(subMenuEntity);

				if (responsecode.getResponseCode().equalsIgnoreCase("200")) {

					menuService.addSubMenu(subMenuEntity);
					res.setResponseCode("200");
					res.setResponseMessage("Sub Menu Has Been Added Successfully");
				} else {
					res.setResponseCode("500");
					res.setResponseMessage("Name Already Exists");
				}
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
			res.setResponseCode("500");
			res.setResponseMessage("Error Occured While Adding The Record");
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/getSubMenuByid", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getSubMenuByid(@RequestBody RequestParamBean requestBean) {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			SubMenuEntity subMenuEntity = menuService.getSubMenuByid(Integer.parseInt(requestBean.getId1()));

			if (null != subMenuEntity) {
				res.setResponseCode("200");

				res.setResult(subMenuEntity);
			} else {
				res.setResponseCode("202");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateSubMenu", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateSubMenu(@RequestBody SubMenuEntity subMenuEntity) {
		ResponseMessageBean res = new ResponseMessageBean();
		ResponseMessageBean responsecode = new ResponseMessageBean();
		try {
			if (subMenuEntity.getId() != 0) {
				responsecode = menuService.checkSubMenuNameWhileUpdate(subMenuEntity);
				if (responsecode.getResponseCode().equalsIgnoreCase("200")) {
					menuService.updateSubMenu(subMenuEntity);
					res.setResponseCode("200");
					res.setResponseMessage("Sub Menu Has Been Updated Successfully");
				} else {
					res.setResponseCode("500");
					res.setResponseMessage("Name Already Exists");
				}
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
			res.setResponseCode("500");
			res.setResponseMessage("Error Occured While Updating The Record");
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/addMenuSubMenuMapping", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> addMenuSubMenuMapping(@RequestBody MenuSubmenuEntity menuSubmenuEntity) {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			menuService.addMenuSubMenuMapping(menuSubmenuEntity);
			res.setResponseCode("200");
			res.setResponseMessage("Details Has Been Added Successfully");
		} catch (Exception e) {
			res.setResponseCode("500");
			res.setResponseMessage("Error Occured While Adding The Record");
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/getMenuSubMenuMappingByid", method = RequestMethod.POST)
	public ResponseEntity<MenuSubmenuEntity> getMenuSubMenuMappingByid(@RequestBody RequestParamBean requestBean) {
		MenuSubmenuEntity menuSubmenuEntity = null;
		try {

			menuSubmenuEntity = menuService.getMenuSubMenuMappingByid(Integer.parseInt(requestBean.getId1()));
			if (null == menuSubmenuEntity) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(menuSubmenuEntity, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateMenuSubMenuMapping", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateMenuSubMenuMapping(
			@RequestBody MenuSubmenuEntity menuSubmenuEntity) {
		ResponseMessageBean res = new ResponseMessageBean();

		try {
			menuService.updateMenuSubMenuMapping(menuSubmenuEntity);
			res.setResponseCode("200");
			res.setResponseMessage("Details Has Been Updated Successfully");
		} catch (Exception e) {
			logger.info("Exception:", e);
			res.setResponseCode("500");
			res.setResponseMessage("Error Occured While Updating The Record");
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	/*
	 * @RequestMapping(value = "/getMenuRightsForRoleId/{roleId}", method =
	 * RequestMethod.GET) public ResponseEntity<ResponseMessageBean>
	 * getMenuRightsForRoleId(@PathVariable("roleId") int roleId) {
	 * ResponseMessageBean res = new ResponseMessageBean(); res =
	 * menuService.getMenuRightsForRoleId(roleId);
	 * 
	 * return new ResponseEntity<>(res, HttpStatus.OK); }
	 */

	@RequestMapping(value = "/getSubMenuRightsForMenuId", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getSubMenuRightsForMenuId(@RequestBody RequestParamBean requestBean) {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			res = menuService.getSubMenuRightsForMenuId(Integer.parseInt(requestBean.getId1()),
					Integer.parseInt(requestBean.getId2()));
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/getCustomizeSubMenuRightsForMenuId", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getCustomizeSubMenuRightsForMenuId(
			@RequestBody RequestParamBean requestBean) {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			res = menuService.getCustomizeSubMenuRightsForMenuId(Integer.parseInt(requestBean.getId1()),
					Integer.parseInt(requestBean.getId2()), Integer.parseInt(requestBean.getId3()));
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/saveMenuRights", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> saveMenuRights(@RequestBody List<MenuSubmenuEntity> menuSubmenuEntity) {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			menuService.saveMenuRights(menuSubmenuEntity);
			res.setResponseCode("200");
			res.setResponseMessage("Details Has Been Updated Successfully");
		} catch (Exception e) {
			logger.info("Exception:", e);
			res.setResponseCode("500");
			res.setResponseMessage("Error Occured While Updating The Record");
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/saveSubMenuRights", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> saveSubMenuRights(
			@RequestBody List<MenuSubmenuEntity> menuSubmenuEntity) {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			menuService.saveSubMenuRights(menuSubmenuEntity);
			res.setResponseCode("200");
			res.setResponseMessage("Submenu Has Been Updated Successfully");
		} catch (Exception e) {
			logger.info("Exception:", e);
			res.setResponseCode("500");
			res.setResponseMessage("Error Occured While Updating The Record");
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/getAllRoles", method = RequestMethod.POST)
	public ResponseEntity<List<RoleBean>> listAllRoles(@RequestBody RequestParamBean requestBean) {
		List<RoleBean> roles = menuService.findAllRoles(Integer.parseInt(requestBean.getId1()),
				Integer.parseInt(requestBean.getId2()));
		if (roles.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);// You many
																// decide to
																// return
																// HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<>(roles, HttpStatus.OK);
	}

	@RequestMapping(value = "/getPrivilageDataForMenuId", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getPrivilageDataForRoleId(@RequestBody RequestParamBean requestBean) {
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			List<AccessRights> accessRights = menuService.getPrivilageDataByMenuId(new BigInteger(requestBean.getId1()),
					new BigInteger(requestBean.getId2()));
			List<MenuSubMenuDetailsBean> privilageDetails = new ArrayList<>();

			MenuSubMenuDetailsBean privilageData = new MenuSubMenuDetailsBean();
			for (AccessRights accessData : accessRights) {

				if (accessData.getPrivilegeId().intValue() == 1) {
					privilageData.setViewChecked(true);

				} else if (accessData.getPrivilegeId().intValue() == 2) {
					privilageData.setCreateChecked(true);
				} else if (accessData.getPrivilegeId().intValue() == 3) {
					privilageData.setUpdateChecked(true);
				} else if (accessData.getPrivilegeId().intValue() == 4) {
					privilageData.setReviewChecked(true);
				} else if (accessData.getPrivilegeId().intValue() == 5) {
					privilageData.setApproveChecked(true);
				} else if (accessData.getPrivilegeId().intValue() == 6) {
					privilageData.setDeleteChecked(true);
				} else if (accessData.getPrivilegeId().intValue() == 7) {
					privilageData.setRejectChecked(true);
				}
				privilageDetails.add(privilageData);
			}

			if (!privilageDetails.isEmpty()) {
				response.setResponseCode("200");
				response.setResult(privilageData);
				response.setResponseMessage("Success");
			} else {
				response.setResponseCode("202");
				response.setResponseMessage("No record found");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@RequestMapping(value = "/getAllSubMenuList", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getSubMenuListBymenuId() { // @SessionAttribute("userLogin")UserLogin
																			// userLogin
		logger.info("In Menu Controller ->getAllSubMenuList()");
		ResponseMessageBean res = new ResponseMessageBean();

		List<SubMenuEntity> subMenuList = menuService.getAllSubMenuList();
		try {
			if (null != subMenuList) {
				res.setResponseCode("200");
				res.setResult(subMenuList);
			} else {
				res.setResponseCode("202");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/getLeftMenuByMenuLink", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getLeftMenuByMenuLink(@RequestBody RequestParamBean requestBean) {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<SubMenuEntity> subMenuList = menuService.findAllSubmenu(requestBean.getId1());
			if (null != subMenuList && !subMenuList.isEmpty()) {
				res.setResponseCode("200");
				res.setResult(subMenuList);
				res.setResponseMessage("Success");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/getCustomizeSubMenuByMenuId", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getCustomizeSubMenuByMenuId(@RequestBody RequestParamBean requestBean) {
		List<CustomizeSubmenuEntity> subMenuList = menuService
				.getCustomizeSubMenuByMenuId(Integer.parseInt(requestBean.getId1()));
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			if (null != subMenuList) {
				res.setResponseCode("200");
				res.setResult(subMenuList);
			} else {
				res.setResponseCode("202");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/addCustomizeSubMenu", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> addCustomizeSubMenu(
			@RequestBody CustomizeSubmenuEntity customizeSubMenuEntity) {
		ResponseMessageBean res = new ResponseMessageBean();
		ResponseMessageBean responsecode = new ResponseMessageBean();
		try {

			if (customizeSubMenuEntity.getMenuName() != null) {
				responsecode = menuService.checkCustomizeSubMenuName(customizeSubMenuEntity);

				if (responsecode.getResponseCode().equalsIgnoreCase("200")) {

					menuService.addCustomizeSubMenu(customizeSubMenuEntity);
					res.setResponseCode("200");
					res.setResponseMessage("Sub Menu Has Been Added Successfully");
				} else {
					res.setResponseCode("451");
					res.setResponseMessage("Name Already Exists");
				}
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
			res.setResponseCode("500");
			res.setResponseMessage("Error Occured While Adding The Record");
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/getCustomizeSubMenuByid", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getCustomizeSubMenuByid(@RequestBody RequestParamBean requestBean) {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			CustomizeSubmenuEntity customizeSubmenuEntity = menuService
					.getCustomizeSubMenuByid(Integer.parseInt(requestBean.getId1()));
			if (null != customizeSubmenuEntity) {
				res.setResponseCode("200");

				res.setResult(customizeSubmenuEntity);
			} else {
				res.setResponseCode("202");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateCustomizeSubMenu", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateCustomizeSubMenu(
			@RequestBody CustomizeSubmenuEntity customizeSubmenuEntity) {
		ResponseMessageBean res = new ResponseMessageBean();
		ResponseMessageBean responsecode = new ResponseMessageBean();
		try {
			if (customizeSubmenuEntity.getId() != 0) {
				responsecode = menuService.checkUpdateCustomizeSubMenu(customizeSubmenuEntity);
				if (responsecode.getResponseCode().equalsIgnoreCase("200")) {
					menuService.updateCustomizeSubMenu(customizeSubmenuEntity);
					res.setResponseCode("200");
					res.setResponseMessage("Sub Menu Has Been Updated Successfully");
				} else {
					res.setResponseCode("451");
					res.setResponseMessage("Name Already Exists");
				}
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
			res.setResponseCode("500");
			res.setResponseMessage("Error Occured While Updating The Record");
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/getMenuRightsForRoleId", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getMenuRightsForRoleId(@RequestBody RequestParamBean requestBean) {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			res = menuService.getMenuRightsForRoleId(Integer.parseInt(requestBean.getId1()));
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/getCustomizeMenuRightsByBankTypeAndAppId", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getCustomizeMenuRightsByBankTypeAndAppId(
			@RequestBody RequestParamBean requestBean) {

		ResponseMessageBean res = new ResponseMessageBean();
		try {
			res = menuService.getCustomizeMenuRightsByBankTypeAndAppId(Integer.parseInt(requestBean.getId1()),
					Integer.parseInt(requestBean.getId2()), Integer.parseInt(requestBean.getId3()));
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/saveCustomizeMenuRights", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> saveCustomizeMenuRights(
			@RequestBody List<CustomizeMenuSubMenuEntity> menuSubmenuEntity) {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			menuService.saveCustomizeMenuRights(menuSubmenuEntity);
			res.setResponseCode("200");
			res.setResponseMessage("Details Has Been Updated Successfully");
		} catch (Exception e) {
			logger.info("Exception:", e);
			res.setResponseCode("500");
			res.setResponseMessage("Error Occured While Updating The Record");
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/saveCustomizeSubMenuRights", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> saveCustomizeSubMenuRights(
			@RequestBody List<CustomizeMenuSubMenuEntity> menuSubmenuEntity) {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			menuService.saveCustomizeSubMenuRights(menuSubmenuEntity);
			res.setResponseCode("200");
			res.setResponseMessage("Details Has Been Updated Successfully");
		} catch (Exception e) {
			logger.info("Exception:", e);
			res.setResponseCode("500");
			res.setResponseMessage("Error Occured While Updating The Record");
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/getAllCustomizationSubMenu", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getAllCustomizationSubMenu() {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<CustomizationSubmenuEntity> customizeSubmenuEntity = menuService.getAllCustomizationSubMenu();
			if (null != customizeSubmenuEntity) {
				res.setResponseCode("200");

				res.setResult(customizeSubmenuEntity);
			} else {
				res.setResponseMessage("No Records Found");
				res.setResponseCode("202");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/addCustomizationSubMenu", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> addCustomizationSubMenu(
			@RequestBody CustomizationSubmenuEntity customizeSubMenuEntity) {
		ResponseMessageBean res = new ResponseMessageBean();
		ResponseMessageBean responsecode = new ResponseMessageBean();
		try {

			if (customizeSubMenuEntity.getSubmenuName() != null) {
				responsecode = menuService.checkCustomizationSubMenuName(customizeSubMenuEntity);

				if (responsecode.getResponseCode().equalsIgnoreCase("200")) {

					menuService.addCustomizationSubMenu(customizeSubMenuEntity);
					res.setResponseCode("200");
					res.setResponseMessage("Sub Menu Has Been Added Successfully");
				} else {
					res.setResponseCode("500");
					res.setResponseMessage("Name Already Exists");
				}
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
			res.setResponseCode("500");
			res.setResponseMessage("Error Occured While Adding The Record");
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/getCustomizationSubMenuByid", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getCustomizationSubMenuByid(@RequestBody RequestParamBean requestBean) {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<CustomizationSubmenuEntity> customizeSubmenuEntity = menuService
					.getCustomizationSubMenuByid(Integer.parseInt(requestBean.getId1()));
			if (null != customizeSubmenuEntity) {
				res.setResponseCode("200");

				res.setResult(customizeSubmenuEntity);
			} else {
				res.setResponseMessage("No Records Found");
				res.setResponseCode("202");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateCustomizationSubMenu", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateCustomizeSubMenu(
			@RequestBody CustomizationSubmenuEntity customizeSubmenuEntity) {
		ResponseMessageBean res = new ResponseMessageBean();
		ResponseMessageBean responsecode = new ResponseMessageBean();
		try {
			if (customizeSubmenuEntity.getId().intValue() != 0) {
				responsecode = menuService.checkUpdateCustomizationSubMenu(customizeSubmenuEntity);
				if (responsecode.getResponseCode().equalsIgnoreCase("200")) {
					menuService.updateCustomizationSubMenu(customizeSubmenuEntity);
					res.setResponseCode("200");
					res.setResponseMessage("Sub Menu Has Been Updated Successfully");
				} else {
					res.setResponseCode("500");
					res.setResponseMessage("Name Already Exists");
				}
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
			res.setResponseCode("500");
			res.setResponseMessage("Error Occured While Updating The Record");
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/getCustomizationMenuByRoleIdAndAppId", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getCustomizationMenuByRoleIdAndAppId(
			@RequestBody RequestParamBean requestBean) {

		RandomNumberGenerator object = new RandomNumberGenerator();
		String newpassword = object.generateRandomStringForOoenKmDoc();
		System.out.println(newpassword);
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			res = menuService.getCustomizationMenuByRoleIdAndAppId(Integer.parseInt(requestBean.getId1()),
					Integer.parseInt(requestBean.getId2()));
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/getCustomizationSubMenuByMenuId", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getCustomizationSubMenuByAppId(
			@RequestBody RequestParamBean requestBean) {

		ResponseMessageBean res = new ResponseMessageBean();
		try {
			res = menuService.getCustomizationSubMenuByAppId(Integer.parseInt(requestBean.getId1()),
					Integer.parseInt(requestBean.getId2()));
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/addCustomizationMenuSubMenuMapping", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> addCustomizationMenuSubMenuMapping(
			@RequestBody CustomizationMenuSubmenuEntity menuSubmenuEntity) {
		ResponseMessageBean res = new ResponseMessageBean();
		ResponseMessageBean responsecode = new ResponseMessageBean();
		try {

			responsecode = menuDao.checkIsSubMenuMapped(menuSubmenuEntity);
			if (responsecode.getResponseCode().equalsIgnoreCase("200")) {
				menuService.addCustomizationMenuSubMenuMapping(menuSubmenuEntity);
				res.setResponseCode("200");
				res.setResponseMessage("Details Has Been Updated Successfully");
			} else {
				res.setResponseCode("500");
				res.setResponseMessage(responsecode.getResponseMessage());
			}

		} catch (Exception e) {
			logger.info("Exception:", e);
			res.setResponseCode("500");
			res.setResponseMessage("Error Occured While Updating The Record");
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/saveCustomizationMenuSubMenuMapping", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> saveCustomizationMenuSubMenuMapping(
			@RequestBody List<CustomizationMenuSubmenuEntity> menuSubmenuEntity) {
		ResponseMessageBean res = new ResponseMessageBean();

		try {

			menuService.saveCustomizationMenuSubMenuMapping(menuSubmenuEntity);
			res.setResponseCode("200");
			res.setResponseMessage("Details Has Been Updated Successfully");

		} catch (Exception e) {
			logger.info("Exception:", e);
			res.setResponseCode("500");
			res.setResponseMessage("Error Occured While Updating The Record");
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/saveCustomizationMainMenuMapping", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> saveCustomizationMainMenuMapping(
			@RequestBody List<CustomizationMenuSubmenuEntity> menuSubmenuEntity) {
		ResponseMessageBean res = new ResponseMessageBean();

		try {
			menuService.saveCustomizationMainMenuMapping(menuSubmenuEntity);
			res.setResponseCode("200");
			res.setResponseMessage("Details Has Been Updated Successfully");

		} catch (Exception e) {
			logger.info("Exception:", e);
			res.setResponseCode("500");
			res.setResponseMessage("Error Occured While Updating The Record");
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

}
