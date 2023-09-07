package com.itl.pns.controller;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.itl.pns.bean.RequestParamBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.entity.RoleTypesEntity;
import com.itl.pns.service.RoleTypesService;

/**
 * @author Sushant.tiple
 *
 */

@RestController
@RequestMapping("roletypes")
public class RoleTypesController {

	private static final Logger logger = LogManager.getLogger(RoleTypesController.class);

	@Autowired
	RoleTypesService roleTypeService;

	@RequestMapping(value = "/getAllRoleTypes", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> listAllRoleTypes() {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<RoleTypesEntity> roleTypes = roleTypeService.getRoleTypeList();
			if (!roleTypes.isEmpty()) {
				res.setResponseCode("200");
				res.setResult(roleTypes);
			} else {
				res.setResponseCode("202");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/getRoleTypesById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getRoleTypesById(@RequestBody RequestParamBean requestBean) {
		ResponseMessageBean bean = new ResponseMessageBean();
		try {
			List<RoleTypesEntity> listUser = roleTypeService.getRoleTypesById(Integer.parseInt(requestBean.getId1()));
			if (null != listUser) {
				bean.setResponseCode("200");
				bean.setResult(listUser);
			} else {
				bean.setResponseCode("202");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(bean, HttpStatus.OK);
	}

	@RequestMapping(value = "/getRoleTypesByRoleId", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getRoleTypesByRoleId(@RequestBody RequestParamBean requestBean) {
		ResponseMessageBean bean = new ResponseMessageBean();
		try {
			List<RoleTypesEntity> listUser = roleTypeService.getRoleTypesById(Integer.parseInt(requestBean.getId1()));
			if (null != listUser) {
				bean.setResponseCode("200");
				bean.setResult(listUser);
			} else {
				bean.setResponseCode("202");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(bean, HttpStatus.OK);
	}

}
