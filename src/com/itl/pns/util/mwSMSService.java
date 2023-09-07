package com.itl.pns.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalTime;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;



@Service("mwSMSService")
public class mwSMSService {

	static Logger LOGGER = Logger.getLogger(mwSMSService.class);


	public static void sendSMS(String mobileNumber, String message, String fromtime, String totime) {
		LocalTime now = LocalTime.now();
		LocalTime fromlimit = LocalTime.parse(fromtime);
		LocalTime tolimit = LocalTime.parse(totime);

		if (now.isAfter(fromlimit) && now.isBefore(tolimit)) {
			sendSMS(mobileNumber, message);
		}
	}


	 public static void sendSMS(String mobileNumber, String message) {
		StringBuilder result = new StringBuilder();
		URL url;
		try {
				String smsUrl = "http://sms6.rmlconnect.net:8080/bulksms/bulksms?username=psbomnii&password=oK53tzLz&type=0&dlr=1&destination=xxxxxxxxxx&source=PSBANK&message=$message$";
				smsUrl= smsUrl.replace("xxxxxxxxxx", mobileNumber).replace("$message$", message).replace(" ", "%20");
				url = new URL(smsUrl);
					
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String line;
				while ((line = rd.readLine()) != null) {
					result.append(line);
				}
				rd.close();
			

		} catch (MalformedURLException e) {
			LOGGER.error(e.getMessage(), e);
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
	
	public static void sendSMSABH(String mobileNumber, String message) {
		StringBuilder result = new StringBuilder();
		URL url;
		try {
			url = new URL("http://arsms.info/SendSMS/sendmsg.php?uname=acblbkP&pass=acblbkp1&send=ACBLBK&dest=" + mobileNumber + "&msg=" + message.replaceAll(" ", "%20"));
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			rd.close();
		} catch (MalformedURLException e) {
			LOGGER.error(e.getMessage(), e);
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

}
