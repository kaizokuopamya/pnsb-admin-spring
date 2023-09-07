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
import com.itl.pns.bean.TinServicesData;
import com.itl.pns.service.FinancialCountMobService;
import com.itl.pns.util.EncryptorDecryptor;


@RestController
@RequestMapping("tin")
public class TinServiceData {
	
	private static final Logger logger = LogManager.getLogger(TinServiceData.class);

	@Autowired
	FinancialCountMobService financialCountMobService;
	
	@PostMapping("/getTinServiceData")
	public ResponseEntity<ResponseMessageBean> getTinServiceData(
			@RequestBody TinServicesData TinServiceData) {

		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<TinServicesData> service = financialCountMobService.getTinServiceData(TinServiceData);
			
			logger.info(" inside getTinServiceData ");
			
			for (TinServicesData data : service) {

				if (null != data.getCIF() && data.getCIF().contains("=")) {
					data.setCIF(EncryptorDecryptor.decryptData(data.getCIF()));
				}

				if (null != data.getFROMACCOUNT() && data.getFROMACCOUNT().contains("=")) {
					data.setFROMACCOUNT(EncryptorDecryptor.decryptData(data.getFROMACCOUNT()));
				}

				logger.info(" inside getTinServiceData ");
				
				if (!ObjectUtils.isEmpty(service)) {

					res.setResponseCode("200");
					res.setResult(service);
				} else {
					res.setResponseCode("202");
					res.setResponseMessage("No Records Found");
				}
			}
		} catch (Exception e) {
			logger.error("Exception in TinServiceData",e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}


}
