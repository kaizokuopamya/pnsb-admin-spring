package com.itl.pns.controller;

import java.util.List;

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
import com.itl.pns.entity.CBSMessageTemplateMasterEntity;
import com.itl.pns.service.CBSMessageTemplateService;

/**
 * @author shubham.lokhande
 *
 */
@RestController
@RequestMapping("cbsMessageTemplate")
public class CBSMessageTemplateController {

	private static final Logger logger = LogManager.getLogger(CBSMessageTemplateController.class);

	@Autowired
	CBSMessageTemplateService cbsMessageTemplateService;

	@RequestMapping(value = "/addCbsMessageTemplate", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> addCbsMessageTemplate(
			@RequestBody CBSMessageTemplateMasterEntity activity) {
		logger.info("In cbsMessageTemplate Master  Details -> addCbsMessageTemplate()");
		ResponseMessageBean response = null;
		boolean isDataRefresh = false;
		Boolean res = false;
		try {
			if (!ObjectUtils.isEmpty(activity)) {
				res = cbsMessageTemplateService.addCbsMessageTemplate(activity);
				if (res) {

					// isDataRefresh=adminWorkFlowReqUtility.refreshCacheData("ActivitySettingMasterReader");
					response = new ResponseMessageBean();
					response.setResponseCode("200");
					response.setResponseMessage("Details Added Successfully!");
				} else {
					response = new ResponseMessageBean();
					response.setResponseCode("202");
					response.setResponseMessage("Failed To Add Details");
				}
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
			response.setResponseCode("500");
			response.setResponseMessage("Error While Adding Records !");
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateCbsMessageTemplate", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateCbsMessageTemplate(
			@RequestBody CBSMessageTemplateMasterEntity activity) {
		logger.info("In cbsMessageTemplate Master  Details -> updateCbsMessageTemplate()");
		boolean isDataRefresh = false;
		ResponseMessageBean response = new ResponseMessageBean();
		ResponseMessageBean responsecode = new ResponseMessageBean();
		Boolean res = false;
		try {
			if (!ObjectUtils.isEmpty(activity)) {
				res = cbsMessageTemplateService.updateCbsMessageTemplate(activity);
				if (res) {

					// isDataRefresh=adminWorkFlowReqUtility.refreshCacheData("ActivitySettingMasterReader");
					response.setResponseCode("200");
					response.setResponseMessage("Details Updated Successfully!");
				} else {
					response.setResponseCode("202");
					response.setResponseMessage("Error While Updating Records");
				}

			}
		} catch (Exception e) {
			logger.info("Exception:", e);
			responsecode.setResponseCode("500");
			responsecode.setResponseMessage("Error While Updating Records!");
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/getAllCbsMessageTemplate", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getAllCbsMessageTemplate() {
		logger.info("In cbsMessageTemplate Controller -> getAllCbsMessageTemplate()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
		List<CBSMessageTemplateMasterEntity> list = cbsMessageTemplateService.getAllCbsMessageTemplate();
		
			if (!ObjectUtils.isEmpty(list)) {
				res.setResponseCode("200");
				res.setResult(list);
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

	@RequestMapping(value = "/getCbsMessageTemplateById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getCbsMessageTemplateById(
			@RequestBody CBSMessageTemplateMasterEntity requestBean) {
		logger.info("In cbsMessageTemplate Controller -> getCbsMessageTemplateById()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
		List<CBSMessageTemplateMasterEntity> list = cbsMessageTemplateService
				.getCbsMessageTemplateById(requestBean.getId());
		
			if (!ObjectUtils.isEmpty(list)) {
				res.setResponseCode("200");
				res.setResult(list);
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
}
