package com.itl.pns.corp.controller;

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

import com.itl.pns.bean.MerchantDetailsBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.controller.MessageController;
import com.itl.pns.corp.entity.MerchantEntity;
import com.itl.pns.corp.service.MerchantDetailService;


@RestController
@RequestMapping("merchant")
public class MerchantDetailController {

	private static final Logger logger = LogManager.getLogger(MessageController.class);

	@Autowired
	MerchantDetailService merchantDetailService;

	@RequestMapping(value = "/getMerchantDetail", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getMerchantDetail(@RequestBody MerchantDetailsBean merchantDetailsBean) {
		ResponseMessageBean res = new ResponseMessageBean();
		List merchantdetails = merchantDetailService.getMerchantDetail(merchantDetailsBean);
		try {
			if (!ObjectUtils.isEmpty(merchantdetails)) {
				res.setResponseCode("200");
				res.setResult(merchantdetails);
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