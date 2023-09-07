package com.itl.pns.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;
//import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itl.pns.constant.ApplicationConstants;
import com.itl.pns.entity.ConfigMasterEntity;

@Configuration
@EnableTransactionManagement
@PropertySource(value = { "classpath:application.properties" })
@EnableAutoConfiguration
/**
 * @author shubham.lokhande
 *
 */
@Component
public class EmailUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmailUtil.class);

	@Value("${subject}")
	private String subject;

	@Value("${body}")
	private String body;

	@Value("${body2}")
	private String body2;

	@Value("${email_smtp_server}")
	private String email_smtp_server;
	@Value("${email_smtp_server_port}")
	public String email_smtp_server_port;
	@Value("${mail_smtp_socketFactory_class}")
	public String mail_smtp_socketFactory_class;
	@Value("${email_smtp_auth}")
	private String email_smtp_auth;
	@Value("${mail_smtp_port}")
	private String mail_smtp_port;

	@Value("${email_server_username}")
	private String email_server_username;

	@Value("${email_server_password}")
	private String email_server_password;

	@Value("${email_server_from_address}")
	private String email_server_from_address;

	@Value("${smsUrl}")
	private String smsUrl;

	@Value("${newUserCreationbody}")
	private String newUserCreationbody;

	@Value("${auth_key}")
	private String auth_key;

	@Value("${FMC_url}")
	private String FMC_url;

	@Value("${NOTICATION_ENGINE_URL_EMAIL}")
	private String notificationEngineUrlEmail;
	
	@Value("${EMAIL_ADAPTOR_PUBLIC_URL_EMAIL}")
	private String publicEmailAdaptor;
	
	@Value("${EMAIL_ADAPTOR_PRIVATE_URL_EMAIL}")
	private String privateEmailAdaptor;
	
	@Value("${env}")
	private String env;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	public Environment environment;
	
	@Lazy
	@Autowired
	public AdminWorkFlowReqUtility  adminWorkFlowReqUtility;

	public static void main(String[] args) {
		List<Map<String, String>> record = new ArrayList<Map<String, String>>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", "superadmin");
		map.put("password", "password1");
		record.add(map);
		/*
		 * 
		 * File file = PdfGenerator.generatePDF("customer.pdf", "PSB: Corp user",
		 * record, "password1", "password2"); EmailUtil object = new EmailUtil();
		 * object.sendEmailWithAttachment("shubham.lokhande@infrasofttech.com",
		 * "PSB: Testing mail with attachment", file);
		 */
	}

	public void setUpandSendEmail(String mailTo, String password) {
		try {

			Properties props = new Properties();
			props.put("mail.smtp.host", email_smtp_server);
			props.put("mail.smtp.auth", email_smtp_auth);
			props.put("mail.smtp.port", mail_smtp_port);
			props.put("mail.smtp.starttls.enable", "true");

			Session session = Session.getInstance(props, new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(email_server_username, email_server_password);
				}
			});

			try {

				MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress(email_server_from_address));
				message.setRecipients(Message.RecipientType.TO, mailTo);
				message.setSubject(subject);
				message.setSentDate(new Date());
				message.setText(body + "\n\n" + "Your New Password: " + password + "\n\n" + body2);

				Transport.send(message);

				LOGGER.info("email sent");

			} catch (MessagingException e) {
				LOGGER.error("send failed, exception: " + e);				
			}
			LOGGER.info("Sent Ok");

		} catch (Exception e) {
			LOGGER.info("Exception:", e);

		}
	}

	public void setUpandSendEmailForNewUser(String mailTo, String password) {
		try {

			Properties props = new Properties();
			props.put("mail.smtp.host", email_smtp_server);
			props.put("mail.smtp.auth", email_smtp_auth);
			props.put("mail.smtp.port", mail_smtp_port);
			props.put("mail.smtp.starttls.enable", "true");

			Session session = Session.getInstance(props, new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(email_server_username, email_server_password);
				}
			});

			try {

				MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress(email_server_from_address));
				message.setRecipients(Message.RecipientType.TO, mailTo);
				message.setSubject(subject);
				message.setSentDate(new Date());
				message.setText(newUserCreationbody + "\n\n" + "Your Password: " + password + "\n\n" + body2);

				Transport.send(message);

				LOGGER.info("email sent");

			} catch (MessagingException e) {
				LOGGER.error("send failed, exception: " + e);				
			}
			LOGGER.info("Sent Ok");

		} catch (Exception e) {
			LOGGER.info("Exception:", e);

		}
	}

	public String setUpandSendEmailWithBody(String toEmail, String subject, String body) {
		try {
			Properties props = new Properties();
			props.put("mail.smtp.host", email_smtp_server);
			props.put("mail.smtp.auth", email_smtp_auth);
			props.put("mail.smtp.port", mail_smtp_port);
			props.put("mail.smtp.starttls.enable", "true");

			Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(email_server_username, email_server_password);
				}
			});

			try {

				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(email_server_from_address));
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
				message.setSubject(subject);
				message.setText(body);
				Transport.send(message);
				LOGGER.info("Email sent");

			} catch (MessagingException e) {
				throw new RuntimeException(e);
			}

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return "00";

	}

	public void SendNotificationEmail(String mailTo, String notificationMsg) {
		try {

			Properties props = new Properties();
			props.put("mail.smtp.host", email_smtp_server);
			props.put("mail.smtp.auth", email_smtp_auth);
			props.put("mail.smtp.port", mail_smtp_port);
			props.put("mail.smtp.starttls.enable", "true");

			Session session = Session.getInstance(props, new javax.mail.Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(email_server_username, email_server_password);
				}
			});

			try {

				MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress(email_server_from_address));
				message.setRecipients(Message.RecipientType.TO, mailTo);
				message.setSubject("Notification Alert...!!");
				message.setSentDate(new Date());
				message.setText(notificationMsg, "UTF-8", "html");
				Transport.send(message);

				LOGGER.info("email sent");

			} catch (MessagingException e) {
				LOGGER.error("send failed, exception: " + e);				
			}
			LOGGER.info("Sent Ok");

		} catch (Exception e) {
			LOGGER.info("Exception:", e);

		}
	}

	public void setSMS(String mobileNumber, String template) {
		try {

			smsUrl = smsUrl.replace("xxxxxxxxxx", mobileNumber).replace("$message$", template).replace(" ", "%20");
			URL obj = new URL(smsUrl);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			// optional default is GET
			con.setRequestMethod("GET");
			// add request header
			con.setRequestProperty("User-Agent", "Mozilla/5.0");

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}

			in.close();
			// print result

		} catch (IOException e) {
			// TODO Auto-generated catch block
			LOGGER.info("Exception:", e);

		}

	}

	public void sendBulkEmail(String[] emailList, String exlFileName) {
		try {

			Properties props = new Properties();
			props.put("mail.smtp.host", email_smtp_server);
			props.put("mail.smtp.auth", email_smtp_auth);
			props.put("mail.smtp.port", mail_smtp_port);
			props.put("mail.smtp.starttls.enable", "true");

			Session session = Session.getInstance(props, new javax.mail.Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(email_server_username, email_server_password);
				}
			});

			try {

				MimeMessage message = new MimeMessage(session);

				// Set From Field: adding senders email to from field.
				message.setFrom(new InternetAddress(email_server_from_address));

				// Set To Field: adding recipient's email to from field.
				for (String addressTo : emailList) {
					message.addRecipient(Message.RecipientType.TO, new InternetAddress(addressTo));
				}

				// Set Subject: subject of the email
				message.setSubject("Facility Details");

				// creating first MimeBodyPart object
				BodyPart messageBodyPart1 = new MimeBodyPart();
				messageBodyPart1.setText("This is to test multi receiver with attachment");

				// creating second MimeBodyPart object
				BodyPart messageBodyPart2 = new MimeBodyPart();

				DataSource source = new FileDataSource(exlFileName);
				messageBodyPart2.setDataHandler(new DataHandler(source));
				messageBodyPart2.setFileName(exlFileName);

				// creating MultiPart object
				Multipart multipartObject = new MimeMultipart();
				multipartObject.addBodyPart(messageBodyPart1);
				multipartObject.addBodyPart(messageBodyPart2);

				// set body of the email.
				message.setContent(multipartObject);

				// Send email.
				Transport.send(message);
				LOGGER.info("Mail successfully sent");

			} catch (MessagingException e) {
				LOGGER.error("send failed, exception: " + e);				
			}
			LOGGER.info("Sent Ok");

		} catch (Exception e) {
			LOGGER.info("Exception:", e);

		}
	}

	public void sendSMSNotification(String mobileNumber, String messageContent) {
		boolean isSMSSend = false;
		try {
			LOGGER.info("SMS Sending starting....");		
			List<ConfigMasterEntity> configMasterEntityList=adminWorkFlowReqUtility.getomniChannelConfigDetails(4, ApplicationConstants.SMS_URL);

			// URL for sending SMS
			String url=configMasterEntityList.get(0).getConfigValue();
			LOGGER.info("Sms URL: "+url);
			url = url.replace("$mobileNo$", mobileNumber).replace("$message$", messageContent).replace(" ", "%20");
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			// optional default is GET
			con.setRequestMethod("GET");

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			isSMSSend = true;
			in.close();
			LOGGER.info(""+response);
			LOGGER.info("SMS Sending ending....");

		} catch (IOException e) {
			LOGGER.error("Exception:", e);

		}

	}

	public void sendBulkEailToListOfCust(List<String> custEmailList, String msgContent) {
		try {

			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			Date date = new Date();
			String subject = "Notification: ";
			String emailContent = "<span style=\"text-transform:capitalize\">Hi,</span> <br><br>{msg}.<br><br>Regards, <br> Team Infrasoft.";
			emailContent = emailContent.replace("{msg}", msgContent);
			// logger.info("Inside Send Email-->",+toemail);
			final String username = "kiya.support@infrasofttech.com";
			final String passwd = "ks$sep2020";
			final String from = "kiya.support@infrasofttech.com";
			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.office365.com");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", "587");
			props.put("mail.smtp.starttls.enable", "true");

			Session session = Session.getInstance(props, new javax.mail.Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(email_server_username, email_server_password);
				}
			});

			try {
				MimeMessage msg = new MimeMessage(session);
				msg.setFrom(new InternetAddress(from));
				// Set To Field: adding recipient's email to from field.
				for (String addressTo : custEmailList) {
					msg.addRecipient(Message.RecipientType.TO, new InternetAddress(addressTo));
				}
				// msg.setRecipients(Message.RecipientType.TO,emailId);
				msg.setSubject(subject + " " + date);
				msg.setSentDate(new Date());

				msg.setContent(emailContent, "text/html; charset=utf-8");

				BodyPart messageBodyPart = new MimeBodyPart();

				messageBodyPart.setText("This is message body");

				Transport.send(msg);
			} catch (MessagingException e) {
				LOGGER.error("send failed, exception: " + e);
				e.printStackTrace();
			}
			LOGGER.info("Sent Ok");

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

	}

	public boolean sendEmail() {
		boolean isMailSend = false;
		try {
			Date date = new Date();

			final String username = "kiya.support@infrasofttech.com";
			final String passwd = "ks$sep2020";
			final String from = "kiya.support@infrasofttech.com";
			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.office365.com");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", "587");
			props.put("mail.smtp.starttls.enable", "true");

			Session session = Session.getInstance(props, new javax.mail.Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, passwd);
				}
			});

			try {
				MimeMessage msg = new MimeMessage(session);
				msg.setFrom(new InternetAddress(from));
				msg.setRecipients(Message.RecipientType.TO, "shubhamlokhande1511@gmail.com");
				msg.setSubject("Subject :Hii" + " " + date);
				msg.setSentDate(new Date());

				msg.setContent("Helloo...", "text/html; charset=utf-8");

				BodyPart messageBodyPart = new MimeBodyPart();

				messageBodyPart.setText("This is message body");

				Transport.send(msg);
				isMailSend = true;
			} catch (MessagingException e) {
				LOGGER.error("send failed, exception: " + e);				
			}
			LOGGER.info("Email sent successfully!");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return isMailSend;
	}

	public boolean pushNotification(String deviceTokenId, String notificationMsg) throws IOException {
		boolean isPushNotificationSend = false;
		try {
			URL url = new URL(FMC_url);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setUseCaches(false);
			conn.setDoInput(true);
			conn.setDoOutput(true);

			conn.setRequestMethod("POST");
			conn.setRequestProperty("Authorization", "key=" + auth_key);
			conn.setRequestProperty("Content-Type", "application/json");

			JSONObject data = new JSONObject();
			data.put("to", deviceTokenId);
			JSONObject info = new JSONObject();
			info.put("title", "PSB: Notification"); // Notification title
			info.put("text", notificationMsg); // Notification body
			data.put("notification", info);

			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(data.toString());
			wr.flush();
			wr.close();

			int responseCode = conn.getResponseCode();
			LOGGER.info("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			isPushNotificationSend = true;
			in.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isPushNotificationSend;

	}

	public boolean sendEmailWithAttachment(String mailTo, String notificationMsg, File file) {
		boolean isMailSend = false;
		try {

			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			Date date = new Date();
			BodyPart messageBodyPart2 = new MimeBodyPart();
			Properties props = new Properties();

			// LOCAL EMAIL
			props.put("mail.smtp.host", "smtp.office365.com");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", "587");
			props.put("mail.smtp.starttls.enable", "true");

			// UAT EMAIL
//			props.put("mail.smtp.host", "webmail.psb.co.in");
//            props.put("mail.smtp.auth", "true");
//            props.put("mail.smtp.port", "25");
//            props.put("mail.smtp.starttls.enable", "true");

			/*
			 * Properties props = new Properties(); props.put("mail.smtp.host",
			 * email_smtp_server); props.put("mail.smtp.auth", email_smtp_auth);
			 * props.put("mail.smtp.port", mail_smtp_port);
			 * props.put("mail.smtp.starttls.enable", "true");
			 */
			Session session = Session.getInstance(props, new javax.mail.Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					// return new PasswordAuthentication(email_server_username,
					// email_server_password);
					// LOCAL EMAIL AND PASSWORD
					return new PasswordAuthentication("kiya.support@kiya.ai", "ks$sep2020");

					// UAT EMAIL AND PASSWORD
					// return new PasswordAuthentication("noreply@psb.co.in", "Omni@12340");
				}
			});

			try {
				MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress("kiya.support@kiya.ai"));
				// message.setFrom(new InternetAddress(email_server_from_address));
				message.setRecipients(Message.RecipientType.TO, mailTo);
				message.setSubject("PSB: Notification " + date);
				message.setSentDate(date);

				message.setContent(notificationMsg, "text/html; charset=utf-8");
				DataSource source = new FileDataSource(file);
				messageBodyPart2.setDataHandler(new DataHandler(source));
				messageBodyPart2.setFileName(file.getName());
				BodyPart messageBodyPart = new MimeBodyPart();

				messageBodyPart.setText(notificationMsg);
				Multipart multipart = new MimeMultipart();
				multipart.addBodyPart(messageBodyPart);
				multipart.addBodyPart(messageBodyPart2);
				message.setContent(multipart);

				Transport.send(message);
				isMailSend = true;
			} catch (MessagingException e) {
				LOGGER.error("send failed, exception: " + e);
				e.printStackTrace();
			}
			LOGGER.info("Email sent successfully!");
			// }
		} catch (Exception e) {
			e.printStackTrace();
			// logger.error("Exception Occured sendmail:" + e);
		}
		return isMailSend;
	}

//	@SuppressWarnings("unchecked")
//	public boolean sendEmailWithAttachment(Map<String, String> notificationData) {
//		try {
//			notificationData.put(ApplicationConstants.NOTIFICATION_TYPE, ApplicationConstants.NOTIFICATION_TYPE_EMAIL);
//			String notificationEngineUrl = GlobalPropertyReader.getInstance()
//					.getValue("NOTICATION_ENGINE_URL_EMAIL_USER_CREDENTIALS");
//			Map<String, String> response = null;
//			HttpHeaders headers = new HttpHeaders();
//			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//			HttpEntity<Map<String, String>> entity = new HttpEntity<Map<String, String>>(notificationData, headers);
//			response = restTemplate.exchange(notificationEngineUrl, HttpMethod.POST, entity, Map.class).getBody();
//			if (null != response && response.get(ApplicationConstants.RES_CODE).equals("200")) {
//				return true;
//			} else {
//				return false;
//			}
//		} catch (Exception e) {
//			LOGGER.error(e + "");
//			return false;
//		}
//	}

	@SuppressWarnings("unchecked")
	public boolean sendEmail(Map<String, String> notificationData) {

		String response = "";
		try {

			URL url = new URL(GlobalPropertyReader.getInstance().getValue("NOTICATION_ENGINE_URL_EMAIL"));

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(5000);
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			ObjectMapper objectMapper = new ObjectMapper();
			String inpuData = objectMapper.writeValueAsString(notificationData);
			LOGGER.info(inpuData);

			conn.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
			wr.writeBytes(inpuData);
			wr.flush();
			wr.close();

			int responseCode = conn.getResponseCode();
			LOGGER.info("nSending 'POST' request to URL : " + url);
			LOGGER.info("Post Data : " + inpuData);
			LOGGER.info("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String output;
			StringBuffer res = new StringBuffer();

			while ((output = in.readLine()) != null) {
				res.append(output);
			}
			in.close();

//			LOGGER.info(">mahendra>" + response.toString());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

//	public boolean sendSms(Map<String, String> notificationData) {
//		notificationTemplate = NotificationTemplateMasterReader.getInstance().getValue(transactionPOJO.getSubActionId(), CONSTANT.NOTIFICATION_ID_SMS);
//		if (ObjectUtils.isEmpty(notificationTemplate)) {
//		messaeg = GlobalPropertyReader.getInstance().getValue("DEFAULT_OTP_TEMPLATE_SMS");
//		} else {
//		messaeg = notificationTemplate.getCONTENTS();
//		}
//		messaeg = messaeg.replace("Xotp", actCodeNew);
//		if (null == map.get(MOBILE_PARAM.SERVICE_TYPE))
//		map.put(MOBILE_PARAM.SERVICE_TYPE, "RESENDOTP");
//
//
//
//		messaeg = messaeg.replace("XType", map.get(MOBILE_PARAM.SERVICE_TYPE));
//
//
//
//		if (null != map.get(MOBILE_PARAM.SERVICE_TYPE) && map.get(MOBILE_PARAM.SERVICE_TYPE).equalsIgnoreCase("Login")) {
//		messaeg = "Dear Customer, Your OTP for Logging into " + appType + " Banking is " + actCodeNew + ". This OTP is valid for 5 minutes. Do not share your OTP with anyone. Bank NEVER asks for OTP over Call, SMS or Email -Punjab%26Sind+Bank";
//		}
//
//
//
//		Map<String, String> notifcaion = new HashMap<String, String>();
//		notifcaion.put(MOBILE_PARAM.CUSTOMER_ID, mobileNum);
//		notifcaion.put(MOBILE_PARAM.CONTENT, messaeg);
//		notifcaion.put(MOBILE_PARAM.APP_NAME, map.get(MOBILE_PARAM.ENTITY_ID));
//		notifcaion.put(MOBILE_PARAM.RRN, transactionPOJO.getRRN());
//		notifcaion.put(MOBILE_PARAM.ACTIVITY_NAME, transactionPOJO.getSubActionId());
//		notifcaion.put(MOBILE_PARAM.FILE_PATH, "");
//		new Thread(new Runnable() {
//		public void run() {
//		boolean notifcaionSent = notificationEngineImpl.sendSms(notifcaion);
//
//
//
//		if (!notifcaionSent) {
//		omniCbsDao.insert(new RESENDNOTIFICATION(notifcaion.toString(), CONSTANT.NOTIFICATION_ID_SMS, Calendar.getInstance().getTime(), notifcaion.get(MOBILE_PARAM.CUSTOMER_ID), transactionPOJO.getAppId(), CONSTANT.STATUS_PENDING, transactionPOJO.getSubActionId()));
//		}
//		}
//		}).start();
//		}

	// Send otp on email
//	if(null!=configEmail&&configEmail.getConfigValue().equalsIgnoreCase("Y"))
//
//	{
//		String messaeg;
//		String subject;
//		NotificationTemplateMaster notificationTemplate = NotificationTemplateMasterReader.getInstance()
//				.getValue(transactionPOJO.getSubActionId(), CONSTANT.NOTIFICATION_ID_EMAIL);
//
//		if (null != map.get(MOBILE_PARAM.SERVICE_TYPE)
//				&& map.get(MOBILE_PARAM.SERVICE_TYPE).equalsIgnoreCase("Login")) {
//			messaeg = "Dear Customer, Your OTP for Logging into " + appType
//					+ " Banking is Xotp. This OTP is valid for 5 minutes. Do not share your OTP with anyone. Bank NEVER asks for OTP over Call, SMS or Email -Punjab & Sind Bank";
//			subject = "Retail Login OTP";
//		} else {
//			if (ObjectUtils.isEmpty(notificationTemplate)) {
//				messaeg = GlobalPropertyReader.getInstance().getValue("DEFAULT_OTP_TEMPLATE_EMAIL");
//				subject = GlobalPropertyReader.getInstance().getValue("COMMON_SUBJECT");
//			} else {
//				messaeg = notificationTemplate.getCONTENTS();
//				subject = notificationTemplate.getSUBJECT();
//			}
//
//		}
//
//		messaeg = messaeg.replace("Xotp", actCodeNew);
//		if (null == map.get(MOBILE_PARAM.SERVICE_TYPE))
//			map.put(MOBILE_PARAM.SERVICE_TYPE, "RESENDOTP");
//
//		messaeg = messaeg.replace("XType", map.get(MOBILE_PARAM.SERVICE_TYPE));
//
//		Map<String, String> notifcaion = new HashMap<String, String>();
//		notifcaion.put(MOBILE_PARAM.CUSTOMER_ID, mobileNum);
//		notifcaion.put(MOBILE_PARAM.APP_NAME, map.get(MOBILE_PARAM.ENTITY_ID));
//		notifcaion.put(MOBILE_PARAM.RRN, transactionPOJO.getRRN());
//		notifcaion.put(MOBILE_PARAM.ACTIVITY_NAME, transactionPOJO.getSubActionId());
//		notifcaion.put(MOBILE_PARAM.FILE_PATH, "");
//		notifcaion.put(MOBILE_PARAM.EMAILCONTENT, messaeg);
//		notifcaion.put(MOBILE_PARAM.NOTFICATION_EMAILID, emailID);
//		notifcaion.put(MOBILE_PARAM.PUSH_TOKEN, subject); // Need to check
//		notifcaion.put(MOBILE_PARAM.SUBJECT, subject); // Need to check
//		if (null == map.get("service_Type")
//				|| (null != map.get("service_Type") && !map.get("service_Type").toUpperCase().contains("TRANS"))) {
//
//			new Thread(new Runnable() {
//				public void run() {
//					boolean notifcaionSent = notificationEngineImpl.sendEmail(notifcaion);
//
//					if (!notifcaionSent) {
//						// Failure handling
//						omniCbsDao.insert(new RESENDNOTIFICATION(notifcaion.toString(), CONSTANT.NOTIFICATION_ID_EMAIL,
//								Calendar.getInstance().getTime(), notifcaion.get(MOBILE_PARAM.CUSTOMER_ID),
//								transactionPOJO.getAppId(), CONSTANT.STATUS_PENDING, transactionPOJO.getSubActionId()));
//					}
//				}
//			}).start();
//		}
//	}
//	}

	@SuppressWarnings("unchecked")
	public boolean sendSms(Map<String, String> notificationData) {

		String response = "";
		try {

			URL url = new URL(GlobalPropertyReader.getInstance().getValue("NOTICATION_ENGINE_URL_SMS"));

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setReadTimeout(5000);
			conn.setRequestProperty("Content-Type", "application/json");

			ObjectMapper objectMapper = new ObjectMapper();
			String inpuData = objectMapper.writeValueAsString(notificationData);
			LOGGER.info(inpuData);

			conn.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
			wr.writeBytes(inpuData);
			wr.flush();
			wr.close();

			int responseCode = conn.getResponseCode();
			LOGGER.info("nSending 'POST' request to URL : " + url);
			LOGGER.info("Post Data : " + inpuData);
			LOGGER.info("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String output;
			StringBuffer res = new StringBuffer();

			while ((output = in.readLine()) != null) {
				res.append(output);
			}
			in.close();

//			LOGGER.info(">mahendra>" + response.toString());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean sendEmailWithAttachment(List<String> to, List<String> cc, List<String> bcc, List<File> files, String body,
			String subject) {
		try {
			LOGGER.info("Email to User: "+to);
			LOGGER.info("Email body: "+body);			
			MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
			multipartEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
			addRecepients(multipartEntityBuilder, to, "to");
			addRecepients(multipartEntityBuilder, cc, "cc");
			addRecepients(multipartEntityBuilder, bcc, "bcc");
			LOGGER.info("Emai Sending starting...");
			addFiles(multipartEntityBuilder, files);
			multipartEntityBuilder.addTextBody("subject", subject);
			multipartEntityBuilder.addTextBody("body", body);
			HttpPost post = new HttpPost(publicEmailAdaptor);

			post.setEntity(multipartEntityBuilder.build());
			try (CloseableHttpClient httpClient = HttpClients.createDefault();
					CloseableHttpResponse response = httpClient.execute(post);) {
				return response.getStatusLine().getStatusCode() == 200;
			} catch (Exception e) {
				LOGGER.error("FAILED TO SEND EMAIL DUE TO:",e);
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	private void addFiles(MultipartEntityBuilder multipartEntityBuilder, List<File> files) {
		for (File file : files) {
			multipartEntityBuilder.addPart("files", new FileBody(file));
		}
	}

	private void addRecepients(MultipartEntityBuilder multipartEntityBuilder, List<String> recepients, String key) {
		for (String recepient : recepients) {
			multipartEntityBuilder.addTextBody(key, recepient);
		}
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		// Do any additional configuration here
		return builder.build();
	}

}
