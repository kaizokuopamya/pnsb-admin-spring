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
import com.itl.pns.bean.ReconcileBillDeskBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.service.FinancialCountMobService;
import com.itl.pns.util.EncryptorDecryptor;

@RequestMapping("/billDesk")
@RestController
public class ReconcileBillDeskController {
	
	private static final Logger logger = LogManager.getLogger(ReconcileBillDeskController.class);

	@Autowired
	FinancialCountMobService financialCountMobService;
	
	@PostMapping("/getReconcileBillDeskServiceData")
	public ResponseEntity<ResponseMessageBean> getReconcileBillDeskServiceData(
			@RequestBody ReconcileBillDeskBean billDesk) {

		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<ReconcileBillDeskBean> service = financialCountMobService
					.getReconcileBillDeskServiceData(billDesk);
			logger.info(" inside reconcilebilldesk ");
			for (ReconcileBillDeskBean data : service) {

				if (null != data.getCUSTOMER_ID() && data.getCUSTOMER_ID().contains("=")) {
					data.setCUSTOMER_ID(EncryptorDecryptor.decryptData(data.getCUSTOMER_ID()));
				}

				if (null != data.getBANK_REF_NO() && data.getBANK_REF_NO().contains("=")) {
					data.setBANK_REF_NO(EncryptorDecryptor.decryptData(data.getBANK_REF_NO()));
				}

				if (null != data.getMERCHANT_REF_NO() && data.getMERCHANT_REF_NO().contains("=")) {
					data.setMERCHANT_REF_NO(EncryptorDecryptor.decryptData(data.getMERCHANT_REF_NO()));
				}

				logger.info(" inside reconcilebilldesk ");
				if (!ObjectUtils.isEmpty(service)) {

					res.setResponseCode("200");
					res.setResult(service);
				} else {
					res.setResponseCode("202");
					res.setResponseMessage("No Records Found");
				}
			}
		} catch (Exception e) {
			logger.error("Exception in ReconcileBillDeskServiceData ", e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
}
