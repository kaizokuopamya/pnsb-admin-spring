package com.itl.pns.dao.impl;

import java.util.Arrays;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.constant.ApplicationConstants;
import com.itl.pns.util.GlobalPropertyReader;

@Component
public class NotifcationEngineImpl {

	private static Logger LOGGER = Logger.getLogger(NotifcationEngineImpl.class);

	/*
	 * @Autowired RestTemplate restTemplate;
	 */

	@SuppressWarnings("unchecked")
	public boolean sendSms(Map<String, String> notificationData) {
		try {
			RestTemplate restTemplate = new RestTemplate();
			notificationData.put(ApplicationConstants.NOTIFICATION_TYPE, ApplicationConstants.NOTIFICATION_TYPE_SMS);
			String notificationEngineUrl = GlobalPropertyReader.getInstance().getValue("NOTICATION_ENGINE_URL_SMS");
			Map<String, String> response = null;
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			HttpEntity<Map<String, String>> entity = new HttpEntity<Map<String, String>>(notificationData, headers);
			LOGGER.debug("SMS Request" + entity.toString());
			response = restTemplate.exchange(notificationEngineUrl, HttpMethod.POST, entity, Map.class).getBody();
			if (null != response && response.get(ApplicationConstants.RES_CODE).equals("200")) {
				LOGGER.debug("SMS Response" + response.toString());
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public boolean sendEmail(Map<String, String> notificationData) {
		RestTemplate restTemplate = new RestTemplate();
		try {

			notificationData.put(ApplicationConstants.NOTIFICATION_TYPE, ApplicationConstants.NOTIFICATION_TYPE_EMAIL);
			String notificationEngineUrl = GlobalPropertyReader.getInstance().getValue("NOTICATION_ENGINE_URL_EMAIL");
			Map<String, String> response = null;
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			HttpEntity<Map<String, String>> entity = new HttpEntity<Map<String, String>>(notificationData, headers);
			System.out.println("Email Request" + entity.toString());
			response = restTemplate.exchange(notificationEngineUrl, HttpMethod.POST, entity, Map.class).getBody();
			if (null != response && response.get(ApplicationConstants.RES_CODE).equals("200")) {
				System.out.println("Email Response" + response.toString());
				return true;
			} else {
				System.out.println("Failed");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public ResponseMessageBean sendPushNotification(Map<String, String> notificationData) {
		ResponseMessageBean responseMessageBean = new ResponseMessageBean();
		RestTemplate restTemplate = new RestTemplate();
		try {
			String notificationEngineUrl = GlobalPropertyReader.getInstance().getValue("NOTICATION_ENGINE_URL_PUSH");
			Map<String, String> response = null;
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			HttpEntity<Map<String, String>> entity = new HttpEntity<Map<String, String>>(notificationData, headers);
			LOGGER.debug("PUSH Notification Request" + entity.toString());
			response = restTemplate.exchange(notificationEngineUrl, HttpMethod.POST, entity, Map.class).getBody();
			if (null != response && response.get(ApplicationConstants.RES_CODE).equals("200")) {
				LOGGER.debug("push Notification Response" + response.toString());
				responseMessageBean.setResponseCode("200");
				responseMessageBean.setResponseMessage("Notification Send Successfully");
			} else {
				responseMessageBean.setResponseCode("202");
				responseMessageBean.setResponseMessage("Notification sending failed");
			}
		} catch (Exception e) {
			LOGGER.error("Error while sending notification: " + e);
			responseMessageBean.setResponseCode("202");
			responseMessageBean.setResponseMessage("Notification sending failed");
		}
		return responseMessageBean;
	}

}
