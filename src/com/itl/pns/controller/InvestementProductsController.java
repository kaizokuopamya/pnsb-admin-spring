package com.itl.pns.controller;

import java.sql.SQLException;
import java.util.List;

import javax.sql.rowset.serial.SerialException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.constant.ApplicationConstants;
import com.itl.pns.dao.InvestementProductDao;

import com.itl.pns.entity.ActivitySettingMasterEntity;
import com.itl.pns.entity.InvestementProductsEntity;
import com.itl.pns.service.InvestementProductService;
import com.itl.pns.util.AdminWorkFlowReqUtility;

/**
 * @author shubham.lokhande
 *
 */
@RestController
@RequestMapping("investementProduct")
public class InvestementProductsController {

	@Autowired
	InvestementProductService investementProductService;

	@Autowired
	AdminWorkFlowReqUtility adminWorkFlowReqUtility;

	@Autowired
	InvestementProductDao investementProductDao;

	private static final Logger logger = LogManager.getLogger(InvestementProductsController.class);

	@RequestMapping(value = "/getInvestementProducts", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getInvestementProducts() {
		logger.info("In InvestementProductController -> getInvestementProducts()");
		ResponseMessageBean response = new ResponseMessageBean();

		try {
			List<InvestementProductsEntity> resultData = investementProductService.getInvestementProducts();

			if (!ObjectUtils.isEmpty(resultData)) {
				response.setResponseCode("200");
				response.setResult(resultData);
				response.setResponseMessage("success");
			} else {
				response.setResponseCode("202");
				response.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/getInvestementProductById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getInvestementProductById(
			@RequestBody InvestementProductsEntity investementProductData) {
		logger.info("In InvestementProductController -> getInvestementProductById()");

		ResponseMessageBean response = new ResponseMessageBean();
		try {
			List<InvestementProductsEntity> resultData = investementProductService
					.getInvestementProductById(investementProductData);

			if (!ObjectUtils.isEmpty(resultData)) {
				response.setResponseCode("200");
				response.setResult(resultData);
				response.setResponseMessage("success");
			} else {
				response.setResponseCode("202");
				response.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/addInvestementProducts", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> addInvestementProducts(
			@RequestBody InvestementProductsEntity investementProductData) {
		ResponseMessageBean response = new ResponseMessageBean();
		logger.info("In InvestementProductController -> addInvestementProducts()");
		Boolean res = false;
		investementProductData.setCreatedByName(
				adminWorkFlowReqUtility.getCreatedByNameByCreatedId(investementProductData.getCreatedby().intValue()));
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(investementProductData.getRole_ID().intValue());
		investementProductData.setRoleName(roleName);
		investementProductData.setAction("ADD");
		investementProductData.setStatusName(
				adminWorkFlowReqUtility.getStatusNameByStatusId(investementProductData.getStatusId().intValue()));
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(investementProductData.getActivityName());
		ResponseMessageBean responsecode = new ResponseMessageBean();

		try {
			investementProductData.setLogoClob(
					new javax.sql.rowset.serial.SerialClob(investementProductData.getLogo().toCharArray()));
		} catch (SerialException e) {
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			if (!ObjectUtils.isEmpty(investementProductData)) {
				responsecode = investementProductDao.isInvestementProductExist(investementProductData);
				if (responsecode.getResponseCode().equalsIgnoreCase("200")) {

					res = investementProductService.addInvestementProducts(investementProductData);
					if (res) {
						response.setResponseCode("200");
						if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getChecker()))
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

							response.setResponseMessage("Request Submitted To Admin Checker For Approval");
						} else {
							response.setResponseMessage("Investement Details Saved Successfully");
						}

					} else {
						response.setResponseCode("202");
						response.setResponseMessage("Error While Saving Records");
					}
				} else {
					response.setResponseCode("202");
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

	@RequestMapping(value = "/updateInvestementProducts", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateInvestementProducts(
			@RequestBody InvestementProductsEntity investementProductData) {
		ResponseMessageBean response = new ResponseMessageBean();
		logger.info("In InvestementProductController -> updateInvestementProducts()");
		Boolean res = false;

		investementProductData.setCreatedByName(
				adminWorkFlowReqUtility.getCreatedByNameByCreatedId(investementProductData.getCreatedby().intValue()));
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(investementProductData.getRole_ID().intValue());
		investementProductData.setRoleName(roleName);
		investementProductData.setAction("EDIT");
		investementProductData.setStatusName(
				adminWorkFlowReqUtility.getStatusNameByStatusId(investementProductData.getStatusId().intValue()));
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(investementProductData.getActivityName());
		ResponseMessageBean responsecode = new ResponseMessageBean();
		try {
			investementProductData.setLogoClob(
					new javax.sql.rowset.serial.SerialClob(investementProductData.getLogo().toCharArray()));
		} catch (SerialException e) {
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if (!ObjectUtils.isEmpty(investementProductData)) {
				responsecode = investementProductDao.isUpdateInvestementProductExist(investementProductData);
				if (responsecode.getResponseCode().equalsIgnoreCase("200")) {
					res = investementProductService.updateInvestementProducts(investementProductData);
					if (res) {
						response.setResponseCode("200");
						if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getChecker()))
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

							response.setResponseMessage("Request Submitted To Admin Checker For Approval");
						} else {
							response.setResponseMessage("Investement Details Updated Successfully");
						}

					} else {
						response.setResponseCode("202");
						response.setResponseMessage("Error While Updating Records");
					}
				} else {
					response.setResponseCode("500");
					response.setResponseMessage(responsecode.getResponseMessage());
				}

			}
		} catch (Exception e) {
			logger.info("Exception:", e);
			response.setResponseCode("500");
			response.setResponseMessage("Error While Updating Records!");
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
