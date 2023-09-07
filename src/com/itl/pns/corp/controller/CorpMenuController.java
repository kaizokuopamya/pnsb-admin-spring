package com.itl.pns.corp.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itl.pns.bean.CorpMenuSubMenuBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.constant.ApplicationConstants;
import com.itl.pns.corp.entity.CorpMenuEntity;
import com.itl.pns.corp.entity.CorpMenuSubMenuMappingEntity;
import com.itl.pns.corp.entity.CorpSubMenuEntity;
import com.itl.pns.corp.service.CorpMenuService;
import com.itl.pns.corp.service.CorpMenuSubMenuMappingService;
import com.itl.pns.corp.service.CorpSubMenuService;

@RestController
@RequestMapping("corpMenuSubmenu")
public class CorpMenuController {

	private static final Logger logger = LogManager.getLogger(CorpMenuController.class);

	@Autowired
	private CorpMenuService corpMenuService;

	@Autowired
	private CorpSubMenuService corpSubMenuService;

	@Autowired
	private CorpMenuSubMenuMappingService corpMenuSubMenuMappingService;

	@PostMapping("/getCorpMenu")
	public ResponseEntity<ResponseMessageBean> getCorpMenu(@RequestBody CorpMenuEntity CorpMenuData) {
		ResponseMessageBean response = new ResponseMessageBean();
		CorpMenuEntity corpMenuEntity = null;
		try {
			corpMenuEntity = corpMenuService.getCorpMenuById(CorpMenuData.getId().longValue());
			if (!ObjectUtils.isEmpty(corpMenuEntity)) {
				response.setResponseCode("200");
				response.setResponseMessage("Data fetched successfully");
				response.setResult(corpMenuEntity);
			} else {
				response.setResponseCode("202");
				response.setResponseMessage("");
			}
		} catch (Exception e) {
			response.setResponseCode("202");
			response.setResponseMessage("");
			logger.error("Error while fetching corporate menu data...", e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/corpMenuList")
	public ResponseEntity<ResponseMessageBean> corpMenuList(@RequestBody CorpMenuEntity CorpMenuData) {
		ResponseMessageBean response = new ResponseMessageBean();
		List<CorpMenuEntity> corpMenuList = null;
		try {
			corpMenuList = corpMenuService.corpMenuList();
			if (!ObjectUtils.isEmpty(corpMenuList)) {
				response.setResponseCode("200");
				response.setResponseMessage("Data fetched successfully");
				response.setResult(corpMenuList);
			} else {
				response.setResponseCode("202");
				response.setResponseMessage("");
			}
		} catch (Exception e) {
			response.setResponseCode("202");
			response.setResponseMessage("");
			logger.error("Error while fetching corporate menu data list...", e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/getCorpSubMenu")
	public ResponseEntity<ResponseMessageBean> getCorpMenuMap(@RequestBody CorpSubMenuEntity corpSubMenuEntity) {
		ResponseMessageBean response = new ResponseMessageBean();
		CorpSubMenuEntity corpSubMenu = null;
		try {
			corpSubMenu = corpSubMenuService.getCorpSubMenuById(corpSubMenuEntity.getId());
			if (!ObjectUtils.isEmpty(corpSubMenu)) {
				response.setResponseCode("200");
				response.setResponseMessage("Data fetched successfully");
				response.setResult(corpSubMenu);
			} else {
				response.setResponseCode("202");
				response.setResponseMessage("");
			}
		} catch (Exception e) {
			response.setResponseCode("202");
			response.setResponseMessage("");
			logger.error("Error while fetching corporate menu data list...", e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/corpSubMenuList")
	public ResponseEntity<ResponseMessageBean> corpMenuMappingList(@RequestBody CorpSubMenuEntity corpSubMenuEntity) {
		ResponseMessageBean response = new ResponseMessageBean();
		List<CorpSubMenuEntity> corpSubMenuList = null;
		try {
			corpSubMenuList = corpSubMenuService.corpSubMenuList();
			if (!ObjectUtils.isEmpty(corpSubMenuList)) {
				response.setResponseCode("200");
				response.setResponseMessage("Data fetched successfully");
				response.setResult(corpSubMenuList);
			} else {
				response.setResponseCode("202");
				response.setResponseMessage("");
			}
		} catch (Exception e) {
			response.setResponseCode("202");
			response.setResponseMessage("");
			logger.error("Error while fetching corporate menu data list...", e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/getCorpMenuSubMenuMappingByMenuId")
	public ResponseEntity<ResponseMessageBean> getCorpMenuSubmenuMap(
			@RequestBody CorpMenuSubMenuMappingEntity corpMenuSubMenuMappingEntity) {
		ResponseMessageBean response = new ResponseMessageBean();
		List<CorpMenuSubMenuMappingEntity> corpMenuSubMenuMappingList = null;
		try {
			corpMenuSubMenuMappingList = corpMenuSubMenuMappingService.findCorpMenuSubMenuByMenuIdAndStatusId(
					corpMenuSubMenuMappingEntity.getId(), ApplicationConstants.ACTIVE_STATUS);
			if (!ObjectUtils.isEmpty(corpMenuSubMenuMappingList)) {
				response.setResponseCode("200");
				response.setResponseMessage("Data fetched successfully");
				response.setResult(corpMenuSubMenuMappingList);
			} else {
				response.setResponseCode("202");
				response.setResponseMessage("");
			}
		} catch (Exception e) {
			response.setResponseCode("202");
			response.setResponseMessage("");
			logger.error("Error while fetching corporate menu data list...", e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/menuSubMenuList")
	public ResponseEntity<ResponseMessageBean> menuSubMenuList(
			@RequestBody CorpMenuSubMenuMappingEntity corpMenuSubMenuMappingEntity) {
		ResponseMessageBean response = new ResponseMessageBean();
		List<CorpMenuSubMenuBean> menuSubMenuList = new ArrayList<>();
		List<Object[]> corpMenuSubMenuMappingList = null;
		try {
			corpMenuSubMenuMappingList = corpMenuSubMenuMappingService.menuSubMenuList();
			if (!ObjectUtils.isEmpty(corpMenuSubMenuMappingList)) {
				CorpMenuSubMenuBean menuSubMenuBean = null;
				for (Object[] ob : corpMenuSubMenuMappingList) {
					menuSubMenuBean = new CorpMenuSubMenuBean();
					menuSubMenuBean.setMenuId(Integer.valueOf(ob[0].toString()));
					menuSubMenuBean.setMenuName(ob[1].toString());
					if (ObjectUtils.isEmpty(ob[3])) {
						menuSubMenuBean.setSubMenuId(0);
						menuSubMenuBean.setSubMenuName(null);
					} else {
						menuSubMenuBean.setSubMenuId(Integer.valueOf(ob[2].toString()));
						menuSubMenuBean.setSubMenuName(ob[3].toString());
					}
					menuSubMenuList.add(menuSubMenuBean);
				}

				response.setResponseCode("200");
				response.setResponseMessage("Data fetched successfully");
				response.setResult(menuSubMenuList);
			} else {
				response.setResponseCode("202");
				response.setResponseMessage("");
			}
		} catch (Exception e) {
			response.setResponseCode("202");
			response.setResponseMessage("");
			logger.error("Error while fetching corporate menu data list...", e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/corpMenuSubMenuMappingList")
	public ResponseEntity<ResponseMessageBean> corpMenuSubMenuMappingList(
			@RequestBody CorpMenuSubMenuMappingEntity corpMenuSubMenuMappingEntity) {
		ResponseMessageBean response = new ResponseMessageBean();
		List<CorpMenuSubMenuMappingEntity> corpMenuSubMenuMappingList = null;
		try {
			corpMenuSubMenuMappingList = corpMenuSubMenuMappingService.corpMenuSubMenuMappingList();
			if (!ObjectUtils.isEmpty(corpMenuSubMenuMappingList)) {
				response.setResponseCode("200");
				response.setResponseMessage("Data fetched successfully");
				response.setResult(corpMenuSubMenuMappingList);
			} else {
				response.setResponseCode("202");
				response.setResponseMessage("");
			}
		} catch (Exception e) {
			response.setResponseCode("202");
			response.setResponseMessage("");
			logger.error("Error while fetching corporate menu data list...", e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
