package com.itl.pns.controller;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.itl.pns.bean.OmniChannelBean;
import com.itl.pns.bean.RequestParamBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.bean.VerifyCifPara;
import com.itl.pns.entity.OmniChannelRequest;
import com.itl.pns.entity.Product;
import com.itl.pns.service.OmniChannelService;
import com.itl.pns.util.EncryptorDecryptor;

@RestController
@RequestMapping("omniChannel")
public class OmniChannelController {

	static Logger LOGGER = Logger.getLogger(OmniChannelController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	OmniChannelService omniChannel;

	@RequestMapping(value = "/getOmniChannelRequest", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getOmniChannelRequest(@RequestBody RequestParamBean requestBean) {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<OmniChannelBean> getOmniChannel = omniChannel
					.getOmniChannelRequest(Integer.parseInt(requestBean.getId1()));
			/*
			 * getOmniChannel.forEach(OmniChannelBean->{ String create=new
			 * SimpleDateFormat(" dd/MM/YYYY HH:mm:ssZ").format(OmniChannelBean.getCREATEDON
			 * ()); String update=new
			 * SimpleDateFormat(" dd/MM/YYYY HH:mm:ssZ").format(OmniChannelBean.getUPDATEDON
			 * ()); OmniChannelBean.setUpdatedDate(create);
			 * OmniChannelBean.setUpdatedDate(update); });
			 */
			if (!ObjectUtils.isEmpty(getOmniChannel)) {
				res.setResponseCode("200");
				for (OmniChannelBean record : getOmniChannel) {
					if (record.getMOBILE() != null && record.getMOBILE().contains("=")) {
						record.setMOBILE(EncryptorDecryptor.decryptData(record.getMOBILE()));
					}
					if (record.getCUSTNAME() != null && record.getCUSTNAME().contains("=")) {
						record.setCUSTNAME(EncryptorDecryptor.decryptData(record.getCUSTNAME()));
					}
					if (record.getCONTENT() != null && record.getCONTENT().contains("=")) {
						record.setCONTENT(EncryptorDecryptor.decryptData(record.getCONTENT()));
					}
				}
				res.setResult(getOmniChannel);
			} else {
				res.setResponseCode("202");
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/getOmniChannelRequestReport", method = RequestMethod.POST)
	public ResponseEntity<List<OmniChannelBean>> getOmniChannelRequestReport(
			@RequestBody RequestParamBean requestBean) {
		List<OmniChannelBean> getOmniChannel = omniChannel.getOmniChannelRequestReport((requestBean.getId1()));

		return new ResponseEntity<>(getOmniChannel, HttpStatus.OK);
	}

	@RequestMapping(value = "/getOmniChannelRequestById", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getOmniChannelRequestById(@RequestBody RequestParamBean requestBean) {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<OmniChannelBean> list = omniChannel.getOmniChannelRequestById(Integer.parseInt(requestBean.getId1()));
			if (null != list) {
				res.setResponseCode("200");
				for (OmniChannelBean record : list) {
					if (record.getMOBILE().contains("=")) {
						record.setMOBILE(EncryptorDecryptor.decryptData(record.getMOBILE()));
					}
				}
				res.setResult(list);
			} else {
				res.setResponseCode("202");
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/proceedOmniChannelRequest/{roleId}", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> proceedOmniChannelRequest(@RequestBody OmniChannelRequest omni,
			@PathVariable("roleId") int roleid) {
		String res = "";
		ResponseMessageBean bean = new ResponseMessageBean();
		try {
			if (roleid == 6) {
				res = omniChannel.proceedOmniChannelRequestNew(omni, roleid, 84);
			} else {
				res = omniChannel.proceedOmniChannelRequest(omni);
			}
			if (!res.equalsIgnoreCase("error")) {
				bean.setResponseCode("200");
				bean.setResponseMessage("success");
			} else {
				bean.setResponseCode("202");
				bean.setResponseMessage("Request has been decline");
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(bean, HttpStatus.OK);
	}

	@RequestMapping(value = "/generateOtpRequest", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> generateOtpRequest(@RequestBody OmniChannelRequest omni) {
		String res = "";
		ResponseMessageBean bean = new ResponseMessageBean();
		try {
			res = omniChannel.generateOtpRequest(omni);
			if (!res.equalsIgnoreCase("error")) {
				bean.setResponseCode("200");
				bean.setResponseMessage("OTP Send Successfully");
			} else {
				bean.setResponseCode("202");
				bean.setResponseMessage("Request Has Been Decline");
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(bean, HttpStatus.OK);
	}

	@RequestMapping(value = "/validateOtpRequest", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> validateOtpRequest(@RequestBody OmniChannelRequest omni) {
		String res = "";
		ResponseMessageBean bean = new ResponseMessageBean();
		try {
			res = omniChannel.validateOtpRequest(omni);
			System.out.println(res);
			if (res.equalsIgnoreCase("OTP Verified")) {
				System.out.println("***********************");
				bean.setResponseCode("200");
				bean.setResponseMessage("Transaction Processed Successfully");
			} else {
				bean.setResponseCode("202");
				bean.setResponseMessage("OTP Not Match");
			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			bean.setResponseCode("500");
			bean.setResponseMessage("Error Occured While OTP Match");
		}
		return new ResponseEntity<>(bean, HttpStatus.OK);
	}

	/*
	 * @RequestMapping(value = "/getDataByCif", method = RequestMethod.POST) public
	 * ResponseEntity<VerifyCifPara> getDataByCif(@RequestBody VerifyCifPara
	 * verifyCifPara) {
	 * 
	 * String res = ""; VerifyCifPara verif = null; ResponseMessageBean bean = new
	 * ResponseMessageBean(); try { verif = omniChannel.getDataByCif(verifyCifPara);
	 * bean.setResult(verif); bean.setResponseCode("200");
	 * 
	 * } catch (Exception e) { LOGGER.info("Exception:", e);
	 * bean.setResponseCode("500");
	 * bean.setResponseMessage("Error Occured While OTP Match"); } return new
	 * ResponseEntity<>(bean, HttpStatus.OK);
	 * 
	 * 
	 * HttpHeaders headers = new HttpHeaders();
	 * headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	 * HttpEntity<VerifyCifPara> entity = new
	 * HttpEntity<VerifyCifPara>(verifyCifPara, headers);
	 * 
	 * return restTemplate.postForEntity(
	 * "https://infrabotsdev.infrasofttech.com/PNSMiddleware/rest/LOGINENQUIRY/CUSTPROFILEDETAILS",
	 * verifyCifPara, VerifyCifPara.class);
	 * 
	 * }
	 */
}
