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

import com.itl.pns.bean.RequestParamBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.corp.service.CorpBankTokenService;
import com.itl.pns.entity.BankTokenEntity;
import com.itl.pns.util.EncryptorDecryptor;

@RestController
@RequestMapping("corpbanktoken")
public class CorpBankTokenController {

	private static final Logger logger = LogManager.getLogger(CorpBankTokenController.class);

	@Autowired
	CorpBankTokenService corpBankTokenService;

	@RequestMapping(value = "/getBankTokenRequestForCorp", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getBankTokenRequestForCorp(
			@RequestBody BankTokenEntity bankTokenEntity) {
		logger.info("get Bank token ->getBankTokenRequestForCorp()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {

			List<BankTokenEntity> banktokeniLst = corpBankTokenService.getBankTokenRequestForCorp(bankTokenEntity);

			for (BankTokenEntity banktokenObj : banktokeniLst) {

				if (null != banktokenObj.getMobile()) {
					banktokenObj.setMobile(EncryptorDecryptor.decryptData(banktokenObj.getMobile()));
				}
				if (null!=banktokenObj.getCustomername()) {
					banktokenObj.setCustomername(EncryptorDecryptor.decryptData(banktokenObj.getCustomername()));
				}
				if (null != banktokenObj.getCif()) {
					banktokenObj.setCif(EncryptorDecryptor.decryptData(banktokenObj.getCif()));
				}
				if (null != banktokenObj.getCompanyName() && null != banktokenObj.getIsCorporate()
						&& banktokenObj.getIsCorporate() == 'G') {
					banktokenObj.setCompanyName(EncryptorDecryptor.decryptData(banktokenObj.getCompanyName()));
				} else {
					if (null != banktokenObj.getCompanyName() && banktokenObj.getCompanyName().contains("=")) {
						banktokenObj.setCompanyName(EncryptorDecryptor.decryptData(banktokenObj.getCompanyName()));
					}else {
						banktokenObj.setCompanyName(banktokenObj.getCompanyName());
					}
					banktokenObj.setUserRole("Sole Proprieter");   
				}

				if (null != banktokenObj.getTypeOfRequest()) {
					if(banktokenObj.getTypeOfRequest().equals("ACCESSRIGHTS")){
						banktokenObj.setTypeOfRequest("Switch Transaction Mode");
					}else if(banktokenObj.getTypeOfRequest().equals("RESETTPIN")){
						banktokenObj.setTypeOfRequest("Reset TPIN");
					}else {
						banktokenObj.setTypeOfRequest(banktokenObj.getTypeOfRequest());
					}
				}
				
				if (!ObjectUtils.isEmpty(banktokenObj.getApprovalLevel()) && banktokenObj.getApprovalLevel().equals("S")) {
					banktokenObj.setUserRole("Single User");
				}
			}
			if (!ObjectUtils.isEmpty(banktokeniLst)) {
				res.setResponseCode("200");
				res.setResult(banktokeniLst);
				res.setResponseMessage("Success");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);

	}

	@RequestMapping(value = "/getBankTokenByIdForCorp", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getBankTokenByIdForCorp(@RequestBody RequestParamBean requestBean) {
		logger.info("getBankTokenByIdForCorp request: "+requestBean.toString());
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<BankTokenEntity> banktokeniLst = corpBankTokenService
					.getBankTokenByIdForCorp(Integer.parseInt(requestBean.getId1()));

			for (BankTokenEntity banktokenObj : banktokeniLst) {
				if (null != banktokenObj.getMobile()) {
					banktokenObj.setMobile(EncryptorDecryptor.decryptData(banktokenObj.getMobile()));
				}
				if (banktokenObj.getCustomername() != null) {
					banktokenObj.setCustomername(EncryptorDecryptor.decryptData(banktokenObj.getCustomername()));
				}
				if (null != banktokenObj.getCif()) {
					banktokenObj.setCif(EncryptorDecryptor.decryptData(banktokenObj.getCif()));
				}
				if (null != banktokenObj.getCompanyName() && null != banktokenObj.getIsCorporate()
						&& banktokenObj.getIsCorporate() == 'G') {
					banktokenObj.setCompanyName(EncryptorDecryptor.decryptData(banktokenObj.getCompanyName()));
				} else {
					if (null != banktokenObj.getCompanyName() && banktokenObj.getCompanyName().contains("=")) {
						banktokenObj.setCompanyName(EncryptorDecryptor.decryptData(banktokenObj.getCompanyName()));
					}else {
						banktokenObj.setCompanyName(banktokenObj.getCompanyName());
					}
					
					banktokenObj.setRoleName("Sole Proprieter");   
				}

				if (null != banktokenObj.getTypeOfRequest()) {
					if(banktokenObj.getTypeOfRequest().equals("ACCESSRIGHTS")){
						banktokenObj.setTypeOfRequest("Switch Transaction Mode");
					}else if(banktokenObj.getTypeOfRequest().equals("RESETTPIN")){
						banktokenObj.setTypeOfRequest("Reset TPIN");
					}else {
						banktokenObj.setTypeOfRequest(banktokenObj.getTypeOfRequest());
					}
				}
				if (!ObjectUtils.isEmpty(banktokenObj.getApprovalLevel()) && banktokenObj.getApprovalLevel().equals("S")) {
					banktokenObj.setRoleName("Single User");
				}
			}
			logger.info("getBankTokenByIdForCorp Response: "+banktokeniLst.toString());
			if (null != banktokeniLst) {
				res.setResponseCode("200");
				res.setResult(banktokeniLst);
				res.setResponseMessage("Success");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);

	}

	@RequestMapping(value = "/rejectBankTokenForCorp", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> rejectBankToken(@RequestBody BankTokenEntity bankToken) {
		logger.info("Reject token ");
		ResponseMessageBean response = new ResponseMessageBean();
		Boolean res = false;
		try {
			if (!ObjectUtils.isEmpty(bankToken)) {
				res = corpBankTokenService.rejectBankTokenForCorp(bankToken);
				System.out.println("rejectBankToken : " + res);
			}
			if (res) {
				response.setResponseCode("200");
				response.setResponseMessage("Token Has Been Rejected");
			} else {
				response.setResponseCode("202");
				response.setResponseMessage("Error While Rejecting Token");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
