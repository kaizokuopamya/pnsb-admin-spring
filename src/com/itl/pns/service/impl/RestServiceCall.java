package com.itl.pns.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.itl.pns.entity.OmniChannelRequest;
import com.itl.pns.repository.OmniChannelRequestRepo;
import com.itl.pns.util.GlobalPropertyReader;

@Service
public class RestServiceCall {

	private static final Logger logger = LogManager.getLogger(RestServiceCall.class);

	static String filename = "application.properties";

	@Autowired
	OmniChannelRequestRepo omniChannelRequestRepo;

	String getURL() {
		Properties prop = new Properties();
		InputStream input = null;
		String url = "";
		try {
			input = RestServiceCall.class.getClassLoader().getResourceAsStream(filename);
			if (input == null) {
				logger.info("Sorry, unable to find " + filename);
				return "";
			}
			// load a properties file from class path, inside static method
			prop.load(input);
			// get the property value and print it out
			url = prop.getProperty("restServiceCallMobile");

		} catch (IOException ex) {
			logger.error(ex);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}
		}
		return url;
	}

	public void callRestService(String rrn, String mobileNumber) {

		try {
			URL url = new URL(getURL());

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			String input = "{\"entityId\": \"ADMIN\",\"deviceId\":\"2\",\"subActionId\": \"CREATEACCOUNT\",\"map\": {\"entityId\": \"ADMIN\",\"cbsType\": \"FLEXCUBE\",\"mobPlatform\": \"android\",\"mobileAppVersion\": \"0.0.1\",\"clientAppVer\": \"0.0.1\",\"latitude\": \"19.1256397\",\"longitute\": \"72.8809359\",\"prefered_Language\":\"en_US\",\"MobileNo\" :\""
					+ mobileNumber + "\",\"TransactionId\":\"" + rrn + "\"}}";
			logger.info(input);
			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			String output;
			logger.info("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				logger.info(output);
			}

			conn.disconnect();

		} catch (MalformedURLException e) {

			logger.info("Exception:", e);

		} catch (IOException e) {

			logger.info("Exception:", e);

		}
	}

	/**
	 * Generates Token
	 * 
	 * @param mobileNumber
	 * @param string
	 * @return
	 */
	public String generateTokenService(String mobileNumber, String appId, String strRef, String typeOfRequest) {

		String response = "";
		try {
			URL url = null;
			if (appId.equalsIgnoreCase("RIB")) {
				url = new URL(GlobalPropertyReader.getInstance().getValue("PNS_MW_SENTDOTP_URL") + "/OTP/RESENDOTP");
			} else {
				url = new URL(GlobalPropertyReader.getInstance().getValue("PNS_MW_SENTDOTP_URL")
						+ "/CORPREGISTRATION/CORPRESENDOTP");
			}
			logger.info("Generate Token service endpoint....." + url);
			// URL url = new
			// URL("https://infrabotsdev.infrasofttech.com/PNSMiddleware/rest/OTP/RESENDOTP");

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			String referenceNumber = "";
			referenceNumber = strRef;

			String input = "{" + "\"entityId\": \"DESKTOP\"," + "\"prefered_Language\": \"en\","
					+ "\"sourceIp\": \"106.209.177.216\"," + "\"deviceId\": \"9\"," + "\"map\": {"
					+ "\"entityId\": \"DESKTOP\"," + "\"cbsType\": \"TCS\"," + "\"deviceId\": \"9\","
					+ "\"mobPlatform\": \"android\"," + "\"mobileAppVersion\": \"1.0.0\"," + "\"referenceNumber\": \""
					+ referenceNumber + "\"," + "\"service_Type\": \"TokenVerification\","
					+ "\"clientAppVer\": \"1.0.0\"," + "\"actionType\": \"" + typeOfRequest + "\"," + "\"MobileNo\": \""
					+ mobileNumber + "\"" + "}" + "}";

			logger.info("Generate Token request......." + input);
			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());

			}

			String output;
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			logger.info("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				response = output;
			}
			logger.info("Generate Token response......." + response);
			conn.disconnect();

		} catch (MalformedURLException e) {

			logger.info("Exception:", e);
			return "error";

		} catch (IOException e) {

			logger.info("Exception:", e);
			return "error";

		} catch (Exception e) {

			logger.info("Exception:", e);
			return "error";

		}
		return response;
	}

	/**
	 * Validate OTP
	 * 
	 * @param otp
	 * @return
	 */
	public String validateOtpService(String otp, String mobileNumber) {

		String response = "";
		try {

			URL url = new URL(GlobalPropertyReader.getInstance().getValue("PNS_MW_VALIDATEOTP_URL"));

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			String input = "{" + "\"entityId\": \"DESKTOP\"," + "\"prefered_Language\": \"en\","
					+ "\"sourceIp\": \"106.209.177.216\"," + "\"deviceId\": \"9\","

					+ "\"map\": {" + "\"entityId\": \"DESKTOP\"," + "\"cbsType\": \"TCS\"," + "\"deviceId\": \"9\","
					+ "\"mobPlatform\": \"android\"," + "\"mobileAppVersion\": \"1.0.0\","
					+ "\"clientAppVer\": \"1.0.0\"," + "\"otpCode\": \"" + otp + "\"," + "\"MobileNo\": \""
					+ mobileNumber + "\""

					+ "}" + "}";

			logger.info(input);

			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			String output;
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			while ((output = br.readLine()) != null) {
				response = output;
			}

			conn.disconnect();

		} catch (MalformedURLException e) {

			logger.info("Exception:", e);

		} catch (IOException e) {

			logger.info("Exception:", e);

		}
		return response;
	}

	public String sendNotificationMsg(String message, String requestType, String sendTo, String mobileNo) {

		String response = "";
		try {
			URL url = new URL(getURL());

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			String input = "{" + "\"entityId\": \"MJBL_WAL\", " + "\"deviceId\": \"9\","
					+ "\"subActionId\": \"BULKNOTIFICATIONS\"," + "\"map\": {" + "\"entityId\": \"MJBL_WAL\","
					+ "\"cbsType\": \"TCS\"," + "\"mobPlatform\": \"android\"," + "\"mobileAppVersion\": \"0.0.1\","
					+ "\"MobileNo\": \"" + mobileNo + "\"," + "\"clientAppVer\": \"0.0.1\","
					+ "\"latitude\": \"19.1256397\"," + "\"longitute\": \"72.8809359\"," + "\"requestType\":\""
					+ requestType + "\"," + "\"DESCRIPTION\":\"" + message + "\"," + "\"sendTo\":\"" + sendTo + "\""
					+ "}" + "}";

			logger.info(input);
			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
			String output;
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			logger.info("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				response = output;
			}
			logger.info(response);
			conn.disconnect();

		} catch (MalformedURLException e) {

			logger.info("Exception:", e);
			return "error";

		} catch (IOException e) {

			logger.info("Exception:", e);
			return "error";

		} catch (Exception e) {

			logger.info("Exception:", e);
			return "error";

		}
		return response;
	}

	public String fundTransfer(String content, String serviceType, int custId) {
		String response = "";
		try {
			URL url = new URL(getURL() + "/TRANSACTION/FUNDTRANSFER");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			String input = "{" + "\"entityId\": \"DESKTOP\"," + "\"prefered_Language\": \"en\","
					+ "\"sourceIp\": \"106.209.177.216\"," + "\"deviceId\": \"9\"," + "\"customerId\": \"" + custId
					+ "\"," + "\"map\":" + content + "}";
			logger.info(input);
			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();
			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
			String output;
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			logger.info("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				response = output;
			}
			conn.disconnect();
		} catch (MalformedURLException e) {
			logger.error(e);
			return "error";
		} catch (IOException e) {
			logger.error(e);
			return "error";
		} catch (Exception e) {
			logger.error(e);
			return "error";
		}
		return response;
	}

	public String walletSendMoney(int customerId, String senderName, String senderNumber, String paymode, String amount,
			String remarks, int mobile) {
		String response = "";
		try {
			URL url = new URL(getURL());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			String input = "{" + "\"entityId\": \"MJBL_WAL\"," + "\"deviceId\": \"9\","
					+ "\"subActionId\": \"WALLETSENDMONEY\", " + "\"customerId\": \"" + customerId + "\","
					+ "\"map\": {" + "\"MobileNo\": \"" + mobile + "\"," + "\"paymentMode\": \"" + paymode + "\","
					+ "\"latitude\": \"19.1276576\"," + "\"thirdPartyRefNo\": \"92909371623370422\","
					+ "\"entityId\": \"MJBL_WAL\"," + "\"txn_amount\": \"" + amount + "\"," + "\"cbsType\": \"TCS\","
					+ "\"mobileAppVersion\": \"1.0.0\"," + "\"longitute\": \"72.8754751\","
					+ "\"mobPlatform\": \"android\"," + "\"prefered_Language\": \"en_US\","
					+ "\"beneficiaryMobileNo\": \"" + senderNumber + "\"," + "\"remarks\": \"" + remarks + "\","
					+ "\"RemitterName\": \"" + senderName + "\","

					+ "}" + "}";

			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
			String output;
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			logger.info("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				response = output;
			}
			conn.disconnect();

		} catch (MalformedURLException e) {

			logger.error(e);
			return "error";

		} catch (IOException e) {

			logger.error(e);
			return "error";

		} catch (Exception e) {

			logger.error(e);
			return "error";

		}
		return response;
	}

	public String createCustomer(String name, String mobile, String email, String appid) {

		String response = "";
		try {

			URL url = new URL(getURL());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			/*------------- check while creating submerchant---------------*/
			String input = "{" + "\"subActionId\": \"CREATESUBMERCHANTACCOUNT\", " + "\"deviceId\": \"9\","
					+ "\"entityId\": \"MJBL_WAL\"," + "\"map\": {" + "\"entityId\": \"MJBL_WAL\","
					+ "\"deviceId\":\"9\"," + "\"cbsType\": \"TCS\"," + "\"mobPlatform\": \"android\","
					+ "\"customerName\": \"" + name + "\"," + "\"MobileNo\": \"" + mobile + "\"," + "\"email_id\": \""
					+ email + "\"," + "\"appId\": \"" + appid + "\"" + "}" + "}";

			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
			String output;
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			while ((output = br.readLine()) != null) {
				response = output;
			}

			conn.disconnect();

		} catch (MalformedURLException e) {

			logger.error(e);
			return "error";

		} catch (IOException e) {

			logger.error(e);
			return "error";

		} catch (Exception e) {

			logger.error(e);
			return "error";

		}
		return response;
	}

	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	public String addOmniChannelreq(String fromAccount, String toAccount, String amount, String debitCurrency,
			String creditCurrency, String username, String mobile, String desc, String type, String instituteId,
			int roleId) {

		JSONObject object = new JSONObject();
		JSONObject map = new JSONObject();
		JSONObject reqdata = new JSONObject();
		String response = "";
		Date date = new Date();
		Random random = new Random();
		try {

			URL url = new URL(getURL());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			object.put("subActionId", "ADDOMNICHANNELREQ");
			object.put("deviceId", "9");
			object.put("entityId", "MJBL_WAL");
			if (type.equalsIgnoreCase("CORP")) {

				reqdata.put("fromAccount", fromAccount);
				reqdata.put("debitCurrency", "INR");
				reqdata.put("toAccount", toAccount);
				reqdata.put("creditCurrency", "INR");
				reqdata.put("amount", amount);
				reqdata.put("beneficiaryType", "1");
				reqdata.put("beneficiaryBIC", "MJBL");
				reqdata.put("description", desc);

				try {
					OmniChannelRequest omniChannelRequest = new OmniChannelRequest();
					omniChannelRequest.setCustomerId(8976);
					omniChannelRequest.setMobile(mobile);
					omniChannelRequest.setAccountno(fromAccount);
					omniChannelRequest.setRefno(Integer.toString(random.nextInt(1000000000)));
					omniChannelRequest.setServiceType("WALLETFUNDTRANSFER");
					omniChannelRequest.setContent(reqdata.toString());
					omniChannelRequest.setChannelaction("MJBL_WAL.MJBLWALFI.WalletFundTransfer");
					omniChannelRequest.setCreatedOn(date);
					omniChannelRequest.setUpdatedOn(date);
					omniChannelRequest.setUpdatedBy("1");
					omniChannelRequest.setCreatedBy("1");
					omniChannelRequest.setAppId(5);
					if (roleId == 5) {
						logger.info("maker");
						omniChannelRequest.setStatus(45);
					} else {
						omniChannelRequest.setStatus(84);
					}
					logger.info(omniChannelRequest.toString());
					omniChannelRequestRepo.save(omniChannelRequest);
					return "success";
				} catch (Exception e) {
					logger.info("Exception:", e);
					return "error";
				}

			} else {
				reqdata.put("entityId", "MJBL_WAL");
				reqdata.put("cbsType", "TCS");
				reqdata.put("mobPlatform", "android");
				reqdata.put("mobileAppVersion", "1.0.0");
				reqdata.put("clientAppVer", "1.0.0");
				reqdata.put("MobileNo", mobile);
				reqdata.put("txn_amount", amount);
				reqdata.put("beneficiary_account_no", toAccount);
				reqdata.put("accountno", fromAccount);
				reqdata.put("remarks", desc);
				reqdata.put("beneficiaryType", "1");
				reqdata.put("beneficiaryBIC", "test");
				reqdata.put("debitCurrency", "INR");
				reqdata.put("creditCurrency", "INR");
				reqdata.put("paymentMode", "donation");
				map.put("pendingat", "TPIN");

				map.put("entityId", "MJBL_WAL");
				map.put("cbsType", "TCS");
				map.put("service_Type", "WALLETFUNDTRANSFER");
				map.put("mobPlatform", "android");
				map.put("mobileAppVersion", "1.0.0");
				map.put("clientAppVer", "1.0.0");
				map.put("MobileNo", mobile);
				map.put("userName", username);
				map.put("accountNumber", fromAccount);
				map.put("reqData", reqdata.toString());
				map.put("channelAction", "MJBL_WAL.MJBLWALFI.WalletFundTransfer");
				object.put("map", map);

				String input = object.toString();
				logger.info(object.toString());
				OutputStream os = conn.getOutputStream();
				os.write(input.getBytes());
				os.flush();

				if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
					throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
				}
				String output;
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

				while ((output = br.readLine()) != null) {
					response = output;
				}
				logger.info(response);
				conn.disconnect();

			}

		} catch (MalformedURLException e) {

			logger.error(e);
			return "error";

		} catch (IOException e) {

			logger.error(e);
			return "error";

		} catch (Exception e) {

			logger.error(e);
			return "error";

		}
		return response;
	}

	public String EMIschedular() {
		String response = "";
		try {

			URL url = new URL(getURL());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			String input = "{" + "\"subActionId\": \"EMISCHEDULAR\", " + "\"deviceId\": \"9\","
					+ "\"entityId\": \"MJBL_WAL\"," + "\"map\": {" + "\"entityId\": \"MJBL_WAL\","
					+ "\"deviceId\":\"9\"," + "\"cbsType\": \"TCS\"," + "\"mobPlatform\": \"android\","
					+ "\"mobileAppVersion\": \"1.0.0\"," + "\"clientAppVer\": \"1.0.0\"" + "}" + "}";

			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
			String output;
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			while ((output = br.readLine()) != null) {
				response = output;
			}

			conn.disconnect();

		} catch (MalformedURLException e) {

			logger.error(e);
			return "error";

		} catch (IOException e) {

			logger.error(e);
			return "error";

		} catch (Exception e) {

			logger.error(e);
			return "error";

		}
		return response;
	}

	public String refreshCache(String cacheInstanceName) {

		String response = "";
		try {

			URL url = new URL(GlobalPropertyReader.getInstance().getValue("PNS_MW_REFRESHCACHE_URL"));

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			String input = "{" + "\"entityId\": \"DESKTOP\"," + "\"prefered_Language\": \"en\","
					+ "\"sourceIp\": \"106.209.177.216\"," + "\"deviceId\": \"9\"," + "\"map\": {"
					+ "\"entityId\": \"DESKTOP\"," + "\"cbsType\": \"TCS\"," + "\"deviceId\": \"9\","
					+ "\"mobPlatform\": \"android\"," + "\"mobileAppVersion\": \"1.0.0\","
					+ "\"clientAppVer\": \"1.0.0\"," + "\"cache_instance_name\": \"" + cacheInstanceName + "\""

					+ "}" + "}";

			logger.info(input);
			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());

			}

			String output;
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			logger.info("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				response = output;
			}
			logger.info(response);
			conn.disconnect();

		} catch (MalformedURLException e) {

			logger.info("Exception:", e);
			return "error";

		} catch (IOException e) {

			logger.info("Exception:", e);
			return "error";

		} catch (Exception e) {

			logger.info("Exception:", e);
			return "error";

		}
		return response;
	}

	public String restFectchAccByCif(String omniDashData, String rrn, String referenceNumber) {
		String response = "";
		try {
			URL url = new URL(GlobalPropertyReader.getInstance().getValue("PNS_MW_FETCHACC_BY_CIF_URL"));

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			String input = "{" + "\"entityId\": \"CIB\"," + "\"channelRefNo\": \"" + referenceNumber + "\","
					+ "\"prefered_Language\": \"en\"," + "\"sourceIp\": \"106.209.177.216\"," + "\"deviceId\": \"9\","
					+ "\"map\": {" + "\"entityId\": \"CMOB\"," + "\"cbsType\": \"TCS\","
					+ "\"requestFrom\": \"CORPORATE\"," + "\"latitude\": \"null\"," + "\"longitute\": \"null\","
					+ "\"MobileNo\": \"7738650729\"," + "\"deviceId\": \"9\"," + "\"mobPlatform\": \"android\","
					+ "\"mobileAppVersion\": \"1.0.0\"," + "\"clientAppVer\": \"1.0.0\"," + "\"omniDashData\": \""
					+ omniDashData + "\"," + "\"RRN\": \"" + rrn + "\"," + "\"referenceNumber\": \"" + referenceNumber
					+ "\"" + "}" + "}";

			logger.info("RestFetchAccByCIF service request.........." + input);
			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());

			}

			String output;
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			logger.info("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				response = output;
			}
			logger.info("RestFetchAccByCIF service response.........." + response);
			conn.disconnect();

		} catch (MalformedURLException e) {

			logger.info("Exception:", e);
			return "error";

		} catch (IOException e) {

			logger.info("Exception:", e);
			return "error";

		} catch (Exception e) {

			logger.info("Exception:", e);
			return "error";

		}
		return response;
	}

	public String restcustomerValidation(String mobile, String cif, String accountNumber, String emailId,
			String referenceNumber, String entityId) {
		String response = "";
		try {

			URL url = new URL(GlobalPropertyReader.getInstance().getValue("PNS_MW_CIF_ACC_MOBILE_CHECK_URL"));

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			String custAccountData = mobile + "|" + cif + "|" + accountNumber;

			String input = "{" + "\"entityId\": \"" + entityId + "\"," + "\"prefered_Language\": \"en\","
					+ "\"sourceIp\": \"106.209.177.216\"," + "\"deviceId\": \"9\"," + "\"map\": {" + "\"entityId\": \""
					+ entityId + "\"," + "\"cbsType\": \"TCS\"," + "\"MobileNoOrg\": \"" + mobile + "\","
					+ "\"deviceId\": \"9\"," + "\"mobPlatform\": \"android\"," + "\"mobileAppVersion\": \"1.0.0\","
					+ "\"clientAppVer\": \"1.0.0\"," + "\"emailId\": \"" + emailId + "\"," + "\"RRN\": \""
					+ referenceNumber + "\"," + "\"referenceNumber\": \"" + referenceNumber + "\","
					+ "\"accountNo\": \"" + accountNumber + "\"," + "\"customerID\": \"" + cif + "\","
					+ "\"MobileNo\": \"" + mobile + "\"," + "\"custAccountData\": \"" + custAccountData + "\"" + "}"
					+ "}";

			logger.info(input);
			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());

			}

			String output;
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			logger.info("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				response = output;
			}
			logger.info(response);
			conn.disconnect();

		} catch (MalformedURLException e) {

			logger.info("Exception:", e);
			return "error";

		} catch (IOException e) {

			logger.info("Exception:", e);
			return "error";

		} catch (Exception e) {

			logger.info("Exception:", e);
			return "error";

		}
		return response;
	}

	public String linkDLinkAccounts(String mobile, String accountNumberData, String referenceNumber, String entityId) {
		String response = "";
		try {

			URL url = new URL(GlobalPropertyReader.getInstance().getValue("PNS_MW_LINK_DLINK_ACC"));

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			String input = "{" + "\"entityId\": \"CIB\"," + "\"channelRefNo\": \"" + referenceNumber + "\","
					+ "\"prefered_Language\": \"en\"," + "\"sourceIp\": \"106.209.177.216\"," + "\"deviceId\": \"9\","
					+ "\"map\": {" + "\"entityId\":\"CIB\"," + "\"cbsType\": \"TCS\"," + "\"deviceId\": \"9\","
					+ "\"mobPlatform\": \"android\"," + "\"mobileAppVersion\": \"1.0.0\","
					+ "\"clientAppVer\": \"1.0.0\"," + "\"RRN\": \"" + referenceNumber + "\","
					+ "\"referenceNumber\": \"" + referenceNumber + "\"," + "\"latitude\": \"null\","
					+ "\"longitute\": \"null\"," + "\"MobileNo\": \"" + mobile + "\"," + "\"corpLinkDelinkData\": \""
					+ accountNumberData + "\"" + "}" + "}";

			logger.info("Link Dlink Service Requet: " + input);
			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());

			}

			String output;
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			logger.info("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				response = output;
			}
			logger.info("Link Dlink Service response.............." + response);
			conn.disconnect();

		} catch (MalformedURLException e) {

			logger.info("Exception:", e);
			return "error";

		} catch (IOException e) {

			logger.info("Exception:", e);
			return "error";

		} catch (Exception e) {

			logger.info("Exception:", e);
			return "error";

		}
		logger.info("Link Dlink Service response from Middleware: " + response);
		return response;
	}

	public String getDataByCifService(String corpCompId, String rrn) {
		String response = "";
		try {
			URL url = new URL(GlobalPropertyReader.getInstance().getValue("PNS_MW_SENTDOTP_URL")
					+ "/LOGINENQUIRY/CUSTPROFILEDETAILSGENPART");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			logger.info("getDataByCifService request endpoint............" + url);

			String input = "{" + "\"entityId\": \"DESKTOP\"," + "\"prefered_Language\": \"en\","
					+ "\"sourceIp\": \"106.209.177.216\"," + "\"deviceId\": \"9\"," + "\"map\": {"
					+ "\"entityId\": \"CIB\"," + "\"cbsType\": \"TCS\"," + "\"mobPlatform\": \"android\","
					+ "\"mobileAppVersion\": \"1.0.2\"," + "\"clientAppVer\": \"1.1.0\"," + "\"UserID\": \"rajesh\","
					+ "\"corpCompId\": \"" + corpCompId + "\"," + "\"latitude\": \"null\"," + "\"longitute\": \"null\","
					+ "\"MobileNo\": \"7738650729\"," + "\"referenceNumber\": \"" + rrn + "\"," + "\"RRN\": \"" + rrn
					+ "\"" + "}" + "}";

			logger.info("getDataByCifService request............" + input);
			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());

			}

			String output;
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			logger.info("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				response = output;
			}
			logger.info(response);
			conn.disconnect();

		} catch (MalformedURLException e) {

			logger.info("Exception:", e);
			return "error";

		} catch (IOException e) {

			logger.info("Exception:", e);
			return "error";

		} catch (Exception e) {

			logger.info("Exception:", e);
			return "error";

		}
		return response;

	}

}