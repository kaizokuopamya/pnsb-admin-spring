package com.itl.pns.util;
import java.util.Arrays;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

import com.itl.pns.constant.ApplicationConstants;

@Service
public class CommonCbsCall {

	private static Logger LOGGER = Logger.getLogger(CommonCbsCall.class);

/*	@Autowired
	omniChannelDAO mobileDao;*/

	@Autowired
	RestTemplate restTemplate;

	@SuppressWarnings("unchecked")
	public Map<String, String> getCBSResposne(String serviceName, Map<String, Object> reqData) {
		Map<String, String> result = null;
		try {
			Map<String, Object> response = null;
			Gson gson = new Gson();
			String cbsUrl = GlobalPropertyReader.getInstance().getValue("CBS_URL");
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

			reqData.put(ApplicationConstants.IS_ENCRYPTED, 'N');

			System.out.println(reqData);
			HttpEntity<Map<String, Object>> entity = new HttpEntity<Map<String, Object>>(reqData, headers);
			System.out.println(entity);
			response = restTemplate.exchange(cbsUrl, HttpMethod.POST, entity, Map.class).getBody();
			if (reqData.get(ApplicationConstants.IS_ENCRYPTED).equals("Y")) {
				result = gson.fromJson(EncryptDeryptUtility.decryptNonAndroid(response.get(ApplicationConstants.REQ_DATA).toString(), GlobalPropertyReader.getInstance().getValue("ENCRYPTIONKEY")), Map.class);
			} else {
				if (response.get(ApplicationConstants.STATUS).equals("00")) {
					result = (Map<String, String>) response.get(ApplicationConstants.REQUEST_MAP);
				}
			}
			LOGGER.info("response-->" + result.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("Exception: ", e);
		}

		return result;
	}
}
