package com.itl.pns.util;
import java.util.Date;
import java.util.Properties;

import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;  
import java.text.ParseException;   
import java.util.Date;

@Component
public class AdminEmailUtil {
	
private static final Logger LOGGER = LoggerFactory.getLogger(AdminEmailUtil.class);
	
	@Value("${subject}")
	private  String subject;
	
	@Value("${body}")
	private  String body;
	
	@Value("${body2}")
	private  String body2;
	
	@Value("${email_smtp_server}")
	private  String  email_smtp_server;
	
	@Value("${email_smtp_server_port}")
	public  String  email_smtp_server_port;
	
	@Value("${mail_smtp_socketFactory_class}")
	public  String  mail_smtp_socketFactory_class;
	
	@Value("${email_smtp_auth}")
	private  String  email_smtp_auth;
	
	@Value("${mail_smtp_port}")
	private  String  mail_smtp_port;
	
	@Value("${email_server_username}")
	private  String email_server_username;
	
	@Value("${email_server_password}")
	private  String email_server_password;
	
	@Value("${email_server_from_address}")
	private  String email_server_from_address;
	
	@Value("${smsUrl}")
	private String smsUrl;
	
	@Value("${newUserCreationbody}")
	private String newUserCreationbody;
	
	@Value("${auth_key}")
	private String auth_key;
	
	@Value("${FMC_url}")
	private String FMC_url;
	
	@Value("${htmlMailBody}")
	private String htmlMailBody;
	
	@Value("${htmlMailBody2}")
	private String htmlMailBody2;
	
	@Value("${emailImage}")
	private String emailImage;
	
	@Value("${customEmailHtmlBody}")
	private String customEmailHtmlBody;
	
   public static void main(String[] args) {
	 
	  // EncryptorDecryptor.encryptData("");
	   
	// Set values for both dates  
       String join = "12-12-2018 02:11:20";   
       String leave  = "1-26-2020 07:15:50";   
       // Calling find() method for getting difference bwtween dates  
     //  findTimeDifference(join, leave);   
       
       generateEncSessionId();	   
	   
   }
	   
	   
	public void sendEmailWithLink(String mailTo, String resetLink){   
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
				message.setRecipients(Message.RecipientType.TO,
						mailTo);
				message.setSubject(subject);
				message.setSentDate(new Date());
			
			    String text ="To reset your password, click the link below:\n";
				String html =htmlMailBody+"<a href='xxxxxxxxxx'>Click Here</a></br></br>"+htmlMailBody2;	
				html=html.replace("xxxxxxxxxx", resetLink);
				
				
				message.setText(html, "UTF-8", "html");
                  	
				
				Transport.send(message);

				 System.out.println("email sent");
				 

	        } catch (MessagingException e) {
	            System.out.println("send failed, exception: " + e);
	            LOGGER.info("Exception:", e);
	        }
	        System.out.println("Sent Ok") ;
	    
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			
	
		}
	}
	
	
	 public long  findTimeDifference(String join_date, String leave_date)   
	    {   
		 long minutes_difference  =0;
	        // Create an instance of the SimpleDateFormat class  
	        SimpleDateFormat obj = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");   
	        // In the try block, we will try to find the difference  
	        try {   
	            // Use parse method to get date object of both dates  
	            Date date1 = obj.parse(join_date);   
	            Date date2 = obj.parse(leave_date);   
	            // Calucalte time difference in milliseconds   
	            long time_difference = date2.getTime() - date1.getTime();  
	            // Calucalte time difference in days  
	            long days_difference = (time_difference / (1000*60*60*24)) % 365;   
	            // Calucalte time difference in years  
	            long years_difference = (time_difference / (1000l*60*60*24*365));   
	            // Calucalte time difference in seconds  
	            long seconds_difference = (time_difference / 1000)% 60;   
	            // Calucalte time difference in minutes  
	            minutes_difference  = (time_difference / (1000*60)) % 60;   	              
	            // Calucalte time difference in hours  
	            long hours_difference = (time_difference / (1000*60*60)) % 24;   
	            // Show difference in years, in days, hours, minutes, and seconds   
	            System.out.print(   
	                "Difference "  
	                + "between two dates is: ");   
	            System.out.println(   
	                hours_difference   
	                + " hours, "  
	                + minutes_difference   
	                + " minutes, "  
	                + seconds_difference   
	                + " seconds, "  
	                + years_difference   
	                + " years, "  
	                + days_difference   
	                + " days"  
	                );   
	        }   
	        // Catch parse exception   
	        catch (ParseException excep) {   
	            excep.printStackTrace();   
	        }   
	        
	        return minutes_difference;
	    } 
	 

		public static String generateEncSessionId(){
		  SimpleDateFormat obj = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");   
		  String strDate = obj.format(new Date());		  
		  System.out.println(strDate);
		 String encDate = EncryptorDecryptor.encryptData(strDate);
		 System.out.println("Encrypted date :"+encDate);
		/* 
		String decDate =  EncryptorDecryptor.decryptData(encDate);
		 System.out.println("Decrypted date :"+decDate);*/
		 return encDate;
	 }
		
		public void sendBulkEmailToCustomer(String[] emailList, String messageBody){   
			  
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
					
					
					 for(String addressTo : emailList){
			            	message.addRecipient(Message.RecipientType.TO, new InternetAddress(addressTo)); 
			            }
					 
					message.setSubject("Scheduled Maintaince..!");
					message.setSentDate(new Date());
				
				  
				    
				    customEmailHtmlBody =customEmailHtmlBody.replace("xnamex", "Customer");
				    customEmailHtmlBody =customEmailHtmlBody.replace("xcustomMsgx",messageBody);
					String html =customEmailHtmlBody;	
					//html=html.replace("xxxxxxxxxx", resetLink);
					
					
					message.setText(html, "UTF-8", "html");
	                  	
					
					Transport.send(message);

					 System.out.println("email sent");
					 

		        } catch (MessagingException e) {
		            System.out.println("send failed, exception: " + e);
		            LOGGER.info("Exception:", e);
		        }
		        System.out.println("Sent Ok") ;
		    
			} catch (Exception e) {
				LOGGER.info("Exception:", e);
				
		
			}
		}	
		
}