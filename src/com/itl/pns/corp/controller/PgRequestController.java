package com.itl.pns.corp.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;

import com.itl.pns.bean.Payaggregatorbean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.corp.entity.PgRequestEntity;
import com.itl.pns.corp.service.PgRequestService;
import com.itl.pns.util.EncryptorDecryptor;

@RestController
@RequestMapping("pgRequest")
public class PgRequestController {
	
	@Autowired
	private PgRequestService pgRequestService;
	
	private static final Logger logger = LogManager.getLogger(PgRequestController.class);
	
	@RequestMapping(value = "/getMerchantDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getMerchantDetails(
			@RequestBody PgRequestEntity pgRequestEntity) {
		logger.info("in MessageReportController -> getMessageReportDetails()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<PgRequestEntity> pgRequest = pgRequestService.getMerchantDetails(pgRequestEntity);
			for (PgRequestEntity pgReq : pgRequest) {

				if (null != pgReq.getUuid() && pgReq.getUuid().contains("=")) {
					pgReq.setUuid(EncryptorDecryptor.decryptData(pgReq.getUuid()));
				}

				if (null != pgReq.getEncData() && pgReq.getEncData().contains("=")) {
					pgReq.setEncData(EncryptorDecryptor.decryptData(pgReq.getEncData()));
				}

				if (!ObjectUtils.isEmpty(pgRequest)) {

					res.setResponseCode("200");
					res.setResult(pgRequest);
				} else {
					res.setResponseCode("202");
					res.setResponseMessage("No Records Found");
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(res,HttpStatus.OK);
	}
}
