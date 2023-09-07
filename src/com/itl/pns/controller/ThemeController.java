package com.itl.pns.controller;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.itl.pns.service.ThemeService;
import com.itl.pns.util.AdminWorkFlowReqUtility;
import com.itl.pns.bean.RequestParamBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.bean.ThemesBean;
import com.itl.pns.constant.ApplicationConstants;
import com.itl.pns.dao.ThemeDao;
import com.itl.pns.entity.ActivitySettingMasterEntity;
import com.itl.pns.entity.BulkUsersExcelDetailsEntity;
import com.itl.pns.entity.ThemeMenuOptionEntity;
import com.itl.pns.entity.ThemeNameEntity;
import com.itl.pns.entity.ThemeSideBarBackgroundEntity;
import com.itl.pns.entity.ThemeSideBarColorEntity;
import com.itl.pns.entity.ThemesEntity;
import com.itl.pns.entity.ThemesSubEntity;

/**
 * @author shubham.lokhande
 *
 */
@RestController
@RequestMapping("theme")
public class ThemeController {

	private static final Logger logger = LogManager.getLogger(ThemeController.class);

	@Autowired
	ThemeService themeService;

	@Autowired
	ThemeDao themeDao;

	@Autowired
	AdminWorkFlowReqUtility adminWorkFlowReqUtility;

	@RequestMapping(value = "/getThemeNamesById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getThemeNamesById(@RequestBody RequestParamBean requestBean) {
		logger.info("In Theme  Controller -> getThemeNamesById()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<ThemesBean> themeNamesList = themeService.getThemeNamesById(Integer.parseInt(requestBean.getId1()));

			if (!ObjectUtils.isEmpty(themeNamesList)) {
				res.setResponseCode("200");
				res.setResult(themeNamesList);
				res.setResponseMessage("Success");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);

	}

	@RequestMapping(value = "/getAllThemeNames", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getAllThemeNames() {
		logger.info("In Theme  Controller -> getAllThemeNames()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<ThemesBean> themeNamesList = themeService.getAllThemeNames();

			if (!ObjectUtils.isEmpty(themeNamesList)) {
				res.setResponseCode("200");
				res.setResult(themeNamesList);
				res.setResponseMessage("Success");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);

	}

	@RequestMapping(value = "/getThemesSideBarColorById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getThemesSideBarColorById(@RequestBody RequestParamBean requestBean) {
		logger.info("In Theme  Controller -> getThemesSideBarColorById()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<ThemesBean> themesData = themeService
					.getThemesSideBarColorById(Integer.parseInt(requestBean.getId1()));

			if (!ObjectUtils.isEmpty(themesData)) {
				res.setResponseCode("200");
				res.setResult(themesData);
				res.setResponseMessage("Success");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);

	}

	@RequestMapping(value = "/getAllThemesSideBarColor", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getAllThemesSideBarColor() {
		logger.info("In Theme  Controller -> getAllThemesSideBarColor()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<ThemesBean> themesData = themeService.getAllThemesSideBarColor();

			if (!ObjectUtils.isEmpty(themesData)) {
				res.setResponseCode("200");
				res.setResult(themesData);
				res.setResponseMessage("Success");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);

	}

	@RequestMapping(value = "/getThemesBackgroundColorById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getThemesBackgroundColorById(@RequestBody RequestParamBean requestBean) {
		logger.info("In Theme  Controller -> getThemesBackgroundColorById()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<ThemesBean> themesData = themeService
					.getThemesBackgroundColorById(Integer.parseInt(requestBean.getId1()));

			if (!ObjectUtils.isEmpty(themesData)) {
				res.setResponseCode("200");
				res.setResult(themesData);
				res.setResponseMessage("Success");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);

	}

	@RequestMapping(value = "/getAllThemesBackgroundColor", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getAllThemesBackgroundColor() {
		logger.info("In Theme  Controller -> getAllThemesBackgroundColor()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<ThemesBean> themesData = themeService.getAllThemesBackgroundColor();

			if (!ObjectUtils.isEmpty(themesData)) {
				res.setResponseCode("200");
				res.setResult(themesData);
				res.setResponseMessage("Success");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);

	}

	@RequestMapping(value = "/getThemeMenuOptionById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getThemeMenuOptionById(@RequestBody RequestParamBean requestBean) {
		logger.info("In Theme  Controller -> getThemeMenuOptionById()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<ThemesBean> themesData = themeService.getThemeMenuOptionById(Integer.parseInt(requestBean.getId1()));

			if (!ObjectUtils.isEmpty(themesData)) {
				res.setResponseCode("200");
				res.setResult(themesData);
				res.setResponseMessage("Success");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);

	}

	@RequestMapping(value = "/getAllThemeMenuOption", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getAllThemeMenuOption() {
		logger.info("In Theme  Controller -> getAllThemeMenuOption()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<ThemesBean> themesData = themeService.getAllThemeMenuOption();

			if (!ObjectUtils.isEmpty(themesData)) {
				res.setResponseCode("200");
				res.setResult(themesData);
				res.setResponseMessage("Success");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);

	}

	@RequestMapping(value = "/addThemeNames", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> addThemeNames(@RequestBody ThemeNameEntity themesData) {
		logger.info("In Theme Controller -> addThemeNames()");
		ResponseMessageBean response = new ResponseMessageBean();

		Boolean res = false;
		try {
			if (!ObjectUtils.isEmpty(themesData)) {
				res = themeService.addThemeNames(themesData);
				if (res) {
					response.setResponseCode("200");
					response.setResponseMessage("Theme Name Saved Successfully");
				} else {
					response.setResponseCode("202");
					response.setResponseMessage("Error While Saving Records");
				}

			}
		} catch (Exception e) {
			logger.info("Exception:", e);
			response.setResponseCode("500");
			response.setResponseMessage("Error While  Adding Records!");
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateThemeNames", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateThemeNames(@RequestBody ThemeNameEntity themesData) {
		logger.info("In Theme Controller -> updateThemeNames()");
		ResponseMessageBean response = new ResponseMessageBean();

		Boolean res = false;
		try {
			if (!ObjectUtils.isEmpty(themesData)) {
				res = themeService.updateThemeNames(themesData);
				if (res) {
					response.setResponseCode("200");
					response.setResponseMessage("Theme Name Updated Successfully");
				} else {
					response.setResponseCode("202");
					response.setResponseMessage("Error While Updating Theme Name");
				}

			}
		} catch (Exception e) {
			logger.info("Exception:", e);
			response.setResponseCode("500");
			response.setResponseMessage("Error While  Updating Records!");
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/addThemeSideBarColor", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> addThemeSideBarColor(@RequestBody ThemeSideBarColorEntity themesData) {
		logger.info("In Theme Controller -> addThemeSideBarColor()");
		ResponseMessageBean response = new ResponseMessageBean();

		Boolean res = false;
		try {
			if (!ObjectUtils.isEmpty(themesData)) {
				res = themeService.addThemeSideBarColor(themesData);
				if (res) {
					response.setResponseCode("200");
					response.setResponseMessage("Theme Side Bar Color Saved Successfully");
				} else {
					response.setResponseCode("202");
					response.setResponseMessage("Error While Saving Theme Side Bar Color");
				}

			}
		} catch (Exception e) {
			logger.info("Exception:", e);
			response.setResponseCode("500");
			response.setResponseMessage("Error While  Adding Records!");
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateThemeSideBarColor", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateThemeSideBarColor(
			@RequestBody ThemeSideBarColorEntity themesData) {
		logger.info("In Theme Controller -> updateThemeSideBarColor()");
		ResponseMessageBean response = new ResponseMessageBean();

		Boolean res = false;
		try {
			if (!ObjectUtils.isEmpty(themesData)) {
				res = themeService.updateThemeSideBarColor(themesData);
				if (res) {
					response.setResponseCode("200");
					response.setResponseMessage("Theme Side Bar Color Updated Successfully");
				} else {
					response.setResponseCode("202");
					response.setResponseMessage("Error While Updating Theme Side Bar Color");
				}

			}
		} catch (Exception e) {
			logger.info("Exception:", e);
			response.setResponseCode("500");
			response.setResponseMessage("Error While  Updating Records!");
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/addThemesBackgroundColor", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> addThemesBackgroundColor(
			@RequestBody ThemeSideBarBackgroundEntity themesData) {
		logger.info("In Theme Controller -> addThemesBackgroundColor()");
		ResponseMessageBean response = new ResponseMessageBean();

		Boolean res = false;
		try {
			if (!ObjectUtils.isEmpty(themesData)) {
				res = themeService.addThemesBackgroundColor(themesData);
				if (res) {
					response.setResponseCode("200");
					response.setResponseMessage("Theme Background Color Saved Successfully");
				} else {
					response.setResponseCode("202");
					response.setResponseMessage("Error While Saving Theme Background Color");
				}

			}
		} catch (Exception e) {
			logger.info("Exception:", e);
			response.setResponseCode("500");
			response.setResponseMessage("Error While  Adding Records!");
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateThemesBackgroundColor", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateThemesBackgroundColor(
			@RequestBody ThemeSideBarBackgroundEntity themesData) {
		logger.info("In Theme Controller -> updateThemesBackgroundColor()");
		ResponseMessageBean response = new ResponseMessageBean();

		Boolean res = false;
		try {
			if (!ObjectUtils.isEmpty(themesData)) {
				res = themeService.updateThemesBackgroundColor(themesData);
				if (res) {
					response.setResponseCode("200");
					response.setResponseMessage("Theme Background Color Updated Successfully");
				} else {
					response.setResponseCode("202");
					response.setResponseMessage("Error While Updating Theme Background Color");
				}

			}
		} catch (Exception e) {
			logger.info("Exception:", e);
			response.setResponseCode("500");
			response.setResponseMessage("Error While  Updating Records!");
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/addAllThemeMenuOption", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> addAllThemeMenuOption(@RequestBody ThemeMenuOptionEntity themesData) {
		logger.info("In Theme Controller -> addAllThemeMenuOption()");
		ResponseMessageBean response = new ResponseMessageBean();

		Boolean res = false;
		try {
			if (!ObjectUtils.isEmpty(themesData)) {
				res = themeService.addAllThemeMenuOption(themesData);
				if (res) {
					response.setResponseCode("200");
					response.setResponseMessage("Theme Menu Option Saved Successfully");
				} else {
					response.setResponseCode("202");
					response.setResponseMessage("Error While Saving Theme Menu Option");
				}

			}
		} catch (Exception e) {
			logger.info("Exception:", e);
			response.setResponseCode("500");
			response.setResponseMessage("Error While  Adding Records!");
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateThemeMenuOption", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateThemeMenuOption(@RequestBody ThemeMenuOptionEntity themesData) {
		logger.info("In Theme Controller -> updateThemeMenuOption()");
		ResponseMessageBean response = new ResponseMessageBean();

		Boolean res = false;
		try {
			if (!ObjectUtils.isEmpty(themesData)) {
				res = themeService.updateThemeMenuOption(themesData);
				if (res) {
					response.setResponseCode("200");
					response.setResponseMessage("Theme Menu Option Updated Successfully");
				} else {
					response.setResponseCode("202");
					response.setResponseMessage("Error While Updating Theme Menu Option");
				}

			}
		} catch (Exception e) {
			logger.info("Exception:", e);
			response.setResponseCode("500");
			response.setResponseMessage("Error While  Adding Records!");
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/saveTheme", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> saveTheme(@RequestBody ThemesEntity themesData) {
		logger.info("In Theme Controller -> saveTheme()");
		ResponseMessageBean response = new ResponseMessageBean();
		Boolean res = false;
		if (ObjectUtils.isEmpty(themesData.getForcedToAll())) {
			themesData.setForcedToAll('N');
		}
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(themesData.getRole_ID().intValue());
		themesData.setStatusName(adminWorkFlowReqUtility.getStatusNameByStatusId(themesData.getStatusid().intValue()));
		themesData.setRoleName(roleName);
		themesData.setCreatedByName(
				adminWorkFlowReqUtility.getCreatedByNameByCreatedId(themesData.getCreatedby().intValue()));
		themesData.setAction("ADD");
		ResponseMessageBean responsecode = new ResponseMessageBean();
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(themesData.getActivityName());
		try {
			if (!ObjectUtils.isEmpty(themesData)) {
				responsecode = themeDao.checkIsThemeExistForSameDate(themesData);
				if (responsecode.getResponseCode().equalsIgnoreCase("200")) {
					res = themeService.saveTheme(themesData);
					if (res) {
						response.setResponseCode("200");
						if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getChecker()))
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
							response.setResponseMessage("Request Submitted To Admin Checker For Approval");
						} else {
							response.setResponseMessage("Themes Data Saved Successfully");
						}

					} else {
						response.setResponseCode("202");
						response.setResponseMessage("Error While Saving Records");
					}
				} else {
					response.setResponseCode(responsecode.getResponseCode());
					response.setResponseMessage(responsecode.getResponseMessage());
				}

			}
		} catch (Exception e) {
			logger.info("Exception:", e);
			response.setResponseCode("500");
			response.setResponseMessage("Error While  Adding Records!");
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateThemes", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateThemes(@RequestBody ThemesEntity themesData) {
		logger.info("In Theme Controller -> updateThemes()");
		if (ObjectUtils.isEmpty(themesData.getForcedToAll())) {
			themesData.setForcedToAll('N');
		}
		ResponseMessageBean response = new ResponseMessageBean();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(themesData.getRole_ID().intValue());
		themesData.setStatusName(adminWorkFlowReqUtility.getStatusNameByStatusId(themesData.getStatusid().intValue()));
		themesData.setRoleName(roleName);
		themesData.setCreatedByName(
				adminWorkFlowReqUtility.getCreatedByNameByCreatedId(themesData.getCreatedby().intValue()));
		themesData.setAction("EDIT");
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(themesData.getActivityName());
		ResponseMessageBean responsecode = new ResponseMessageBean();
		Boolean res = false;
		try {
			if (!ObjectUtils.isEmpty(themesData)) {
				responsecode = themeDao.checkUpdateIsThemeExistForSameDate(themesData);
				if (responsecode.getResponseCode().equalsIgnoreCase("200")) {
					res = themeService.updateThemes(themesData);
					if (res) {
						response.setResponseCode("200");
						if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getChecker()))
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
							response.setResponseMessage("Request Submitted To Admin Checker For Approval");
						} else {
							response.setResponseMessage("Themes Data Updated Successfully");
						}

					} else {
						response.setResponseCode("202");
						response.setResponseMessage("Error While Saving Records");
					}
				} else {
					response.setResponseCode(responsecode.getResponseCode());
					response.setResponseMessage(responsecode.getResponseMessage());
				}

			}
		} catch (Exception e) {
			logger.info("Exception:", e);
			response.setResponseCode("500");
			response.setResponseMessage("Error While  Adding Records!");
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/getAllThemes", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getAllThemes() {
		logger.info("In Theme  Controller -> getAllThemes()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<ThemesEntity> themesData = themeService.getAllThemes();

			if (!ObjectUtils.isEmpty(themesData)) {
				res.setResponseCode("200");
				res.setResult(themesData);
				res.setResponseMessage("Success");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);

	}

	@RequestMapping(value = "/getThemesById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getThemesById(@RequestBody RequestParamBean requestBean) {
		logger.info("In Theme  Controller -> getThemesById()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<ThemesEntity> themesData = themeService.getThemesById(Integer.parseInt(requestBean.getId1()));

			if (!ObjectUtils.isEmpty(themesData)) {
				res.setResponseCode("200");
				res.setResult(themesData);
				res.setResponseMessage("Success");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);

	}
	
	@RequestMapping(value = "/getThemes", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getThemes(@RequestBody RequestParamBean requestBean) {
		logger.info("In Theme  Controller -> getThemes()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<ThemesEntity> themeDetails = themeService.getThemes(requestBean.getId1());
			List<ThemesSubEntity> themeSubDetails = themeService.getSubDetails(themeDetails.get(0).getId());
			
		    for(ThemesEntity te : themeDetails) {
		    	te.setThemesSubEntity(themeSubDetails);
		    }

			if (!ObjectUtils.isEmpty(themeDetails)) {
				res.setResponseCode("200");
				res.setResult(themeDetails);
				res.setResponseMessage("Success");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);

	}

	@RequestMapping(value = "/addThemesDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> addThemesDetails(@RequestBody ThemesEntity themesEntity) {
		logger.info("In Theme  Controller -> addThemesDetails()");
		ResponseMessageBean response = new ResponseMessageBean();
		Boolean res = false;
		try {
			if (!ObjectUtils.isEmpty(themesEntity)) {
				
				response = themeService.checkThemes(themesEntity.getThemeName());// Check if themes already exists
			
			}
			
			if(response.getResponseCode().equalsIgnoreCase("200")) {
				
						res = themeService.addThemes(themesEntity);
			
			
				if (res) {
					response.setResponseCode("200");
					response.setResponseMessage("Theme Menu Option Added Successfully");
				} else {
					response.setResponseCode("202");
					response.setResponseMessage("Error While Adding Theme Menu Option");
				}
				
			}	
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);

	}
	
	@RequestMapping(value = "/editThemesDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> editThemesDetails(@RequestBody ThemesEntity themesEntity) {
		logger.info("In Theme  Controller -> editThemesDetails()");
		ResponseMessageBean response = new ResponseMessageBean();
		Boolean res = false;
		try {
				if (!ObjectUtils.isEmpty(themesEntity)) {
						res = themeService.editThemesDetails(themesEntity);
				}
			
				if (res) {
					response.setResponseCode("200");
					response.setResponseMessage("Theme Menu Option Update Successfully");
				} else {
					response.setResponseCode("202");
					response.setResponseMessage("Error While Updateing Theme Menu Option");
				}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@RequestMapping(value = "/themesExistOrNot", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> themesExistOrNot(@RequestBody RequestParamBean requestBean) {
		logger.info("In Theme  Controller->themesExistOrNot----------Start");
		ResponseMessageBean responsecode = new ResponseMessageBean();
		try {
	
				responsecode = themeService.checkThemes(requestBean.getId1());// Check if themes already exists

		} catch (Exception e) {
			logger.info("Exception:", e);
			responsecode.setResponseCode("500");
			responsecode.setResponseMessage("Error While Adding Themes!");
		}
		logger.info("In Theme  Controller->themesExistOrNot------End");
		return new ResponseEntity<>(responsecode, HttpStatus.OK);
	}
}
