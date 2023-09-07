package com.itl.pns.controller;

import java.util.List;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.itl.pns.bean.OltasTinBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.service.FinancialCountMobService;
import com.itl.pns.util.EncryptorDecryptor;

@RequestMapping("/oltasTin")
@RestController
public class OltasTinController {
	
	private static final Logger logger = LogManager.getLogger(OltasTinController.class);

	@Autowired
	FinancialCountMobService financialCountMobService;
	
	@RequestMapping(value = "/getOltasTinServiceData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getOltasTinServiceData(
			@RequestBody OltasTinBean oltasTin) {

		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<OltasTinBean> service = financialCountMobService
					.getOltasTinServiceData(oltasTin);
			
			logger.info(" inside getOltasTinServiceData ");
			
			for (OltasTinBean data : service) {

				if (null != data.getFROMACCOUNT() && data.getFROMACCOUNT().contains("=")) {
					data.setFROMACCOUNT(EncryptorDecryptor.decryptData(data.getFROMACCOUNT()));
				}

				if (null != data.getCIF() && data.getCIF().contains("=")) {
					data.setCIF(EncryptorDecryptor.decryptData(data.getCIF()));
				}

				logger.info(" inside getOltasTinServiceData ");
				
				if (!ObjectUtils.isEmpty(service)) {

					res.setResponseCode("200");
					res.setResult(service);
				} else {
					res.setResponseCode("202");
					res.setResponseMessage("No Records Found");
				}
			}
		} catch (Exception e) {
			logger.error("Exception in OltasTinServiceData", e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

}
