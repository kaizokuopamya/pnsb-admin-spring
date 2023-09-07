package com.itl.pns.controller;

import java.util.List;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.bean.Tin2;
import com.itl.pns.service.FinancialCountMobService;
import com.itl.pns.util.EncryptorDecryptor;

@RequestMapping("/tin2")
@RestController
public class Tin2Controller {
	
	private static final Logger logger = LogManager.getLogger(Tin2Controller.class);

	@Autowired
	FinancialCountMobService financialCountMobService;
	
	@PostMapping("/getTin2ServiceData")
	public ResponseEntity<ResponseMessageBean> getTin2ServiceData(
			@RequestBody Tin2 tin2) {

		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<Tin2> service = financialCountMobService
					.getTin2ServiceData(tin2);
			
			logger.info(" inside getTin2ServiceData ");
				
				if (!ObjectUtils.isEmpty(service)) {
					res.setResponseCode("200");
					res.setResult(service);
				} else {
					res.setResponseCode("202");
					res.setResponseMessage("No Records Found");
				}
		} catch (Exception e) {
			logger.error("Exception in Tin2ServiceData ", e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

}
