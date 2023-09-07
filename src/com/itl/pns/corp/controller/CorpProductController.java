package com.itl.pns.corp.controller;

import java.math.BigInteger;
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

import com.itl.pns.bean.BotsResponseBean;
import com.itl.pns.bean.ProductBean;
import com.itl.pns.bean.RequestParamBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.constant.ApplicationConstants;
import com.itl.pns.corp.service.CorpProductService;
import com.itl.pns.entity.ActivitySettingMasterEntity;
import com.itl.pns.entity.Product;
import com.itl.pns.util.AdminWorkFlowReqUtility;

/**
 * @author shubham.lokhande
 *
 */
@RestController
@RequestMapping("corpProduct")
public class CorpProductController {

	private static final Logger logger = LogManager.getLogger(CorpProductController.class);

	@Autowired
	AdminWorkFlowReqUtility adminWorkFlowReqUtility;

	@Autowired
	CorpProductService corpProductService;

	@RequestMapping(value = "/saveCorpProductDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> saveCorpProductDetails(@RequestBody Product product) {
		ResponseMessageBean res = new ResponseMessageBean();
		ResponseMessageBean responsecode = new ResponseMessageBean();
		ResponseMessageBean response = new ResponseMessageBean();
		product.setAppId(BigInteger.valueOf(2));
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(product.getRole_ID().intValue());
		product.setStatusName(adminWorkFlowReqUtility.getStatusNameByStatusId(product.getStatusId()));
		product.setAppName(adminWorkFlowReqUtility.getAppNameByAppId(product.getAppId().intValue()));
		product.setRoleName(roleName);
		product.setStatusName(adminWorkFlowReqUtility.getStatusNameByStatusId(product.getStatusId()));
		product.setCreatedByName(adminWorkFlowReqUtility.getCreatedByNameByCreatedId(product.getCreatedby()));
		product.setAction("ADD");
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(product.getActivityName());

		try {
			responsecode = corpProductService.checkCorpProduct(product);
			if (responsecode.getResponseCode().equalsIgnoreCase("200")) {
				corpProductService.saveCorpProductDetails(product);
				res.setResponseCode("200");

				if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)
						&& (ApplicationConstants.YESVALUE)
								.equals(Character.toString(activityMasterData.get(0).getChecker()))
						&& (ApplicationConstants.YESVALUE)
								.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
					res.setResponseMessage("Request Submitted To Corporate Checker For Approval");
				} else {
					res.setResponseMessage("Product Details Has Been Saved Successfully");

				}

			} else {
				res.setResponseCode("202");
				res.setResponseMessage(responsecode.getResponseMessage());
			}
		} catch (Exception e) {
			res.setResponseCode("500");
			res.setResponseMessage("Error Occured While Updating The Record");
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateCorpProductDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateCorpProductDetails(@RequestBody Product product) {
		ResponseMessageBean res = new ResponseMessageBean();
		ResponseMessageBean responsecode = new ResponseMessageBean();
		product.setAppId(BigInteger.valueOf(2));
		product.setStatusName(adminWorkFlowReqUtility.getStatusNameByStatusId(product.getStatusId()));
		product.setAppName(adminWorkFlowReqUtility.getAppNameByAppId(product.getAppId().intValue()));
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(product.getRole_ID().intValue());
		product.setRoleName(roleName);
		product.setCreatedByName(adminWorkFlowReqUtility.getCreatedByNameByCreatedId(product.getCreatedby()));
		product.setAction("EDIT");
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(product.getActivityName());

		try {
			responsecode = corpProductService.checkCorpUpdateProduct(product);
			if (responsecode.getResponseCode().equalsIgnoreCase("200")) {
				corpProductService.updateCorpProductDetails(product);
				res.setResponseCode("200");
				if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)
						&& (ApplicationConstants.YESVALUE)
								.equals(Character.toString(activityMasterData.get(0).getChecker()))
						&& (ApplicationConstants.YESVALUE)
								.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
					res.setResponseMessage("Request Submitted To Corporate Checker For Approval");
				} else {
					res.setResponseMessage("Product Has Been Updated Successfully");
				}
			} else {
				res.setResponseCode("202");
				res.setResponseMessage(responsecode.getResponseMessage());
			}
		} catch (Exception e) {
			res.setResponseCode("500");
			res.setResponseMessage("Error Occured While Updating The Record");
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/getCorpProducts", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getCorpProducts() {
		List<ProductBean> getProduct = corpProductService.getCorpProducts();
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			if (!getProduct.isEmpty()) {
				response.setResponseCode("200");
				response.setResult(getProduct);
				response.setResponseMessage("Success");
			} else {
				response.setResponseCode("202");
				response.setResponseMessage("No record found");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/getCorpProductById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getCorpProductById(@RequestBody RequestParamBean requestBean) {
		List<ProductBean> product = corpProductService.getCorpProductById(Integer.parseInt(requestBean.getId1()));
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			if (!ObjectUtils.isEmpty(product)) {
				response.setResponseCode("200");
				response.setResult(product);
				response.setResponseMessage("Success");
			} else {
				response.setResponseCode("202");
				response.setResponseMessage("No record found");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	

}
