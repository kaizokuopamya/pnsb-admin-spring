package com.itl.pns.filter;


import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.DispatcherType;
import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.util.EncryptDeryptUtility;



/**
 * @author shubham.lokhande
 *
 */
@SuppressWarnings("serial")
@WebFilter(urlPatterns = "/*", dispatcherTypes = {DispatcherType.REQUEST })
public class RequestCheckFilter extends HttpFilter {
int i= 0;
	static Logger LOGGER = Logger.getLogger(RequestCheckFilter.class);

	@Autowired
	RequestHandler handler;

	public RequestCheckFilter() {
	}

//	@SuppressWarnings("unused")
//	@Override
//	protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
//	
//		if (handler == null) {
//			ServletContext servletContext = request.getServletContext();
//			WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
//			handler = webApplicationContext.getBean(RequestHandler.class);
//		}
//		
//		String[] actionname = request.getRequestURL().toString().split("/");
//		XSSRequestWrapper wrappedRequest = new XSSRequestWrapper((HttpServletRequest) request);		
//		String jsonToOriginator = "";
//		Gson gson = new Gson();
//		String decKey = "";
//		String[] decKeyCustId = null;
//		String custid = "8982";		
//		wrappedRequest.setAttribute("updatedBy", custid);
//		int appId = 0;
//		int deviceDBId = 0;
//		String keyType = "P";
//		String deviceValid = "3";
//		JSONObject json = null;
//		JSONArray jsonArray = null;
//		JSONParser parser = new JSONParser();
//		boolean isreqvalid = true;
//		System.out.println("getContentLength ::"+wrappedRequest.getContentLength());
//		
//		
//		/*
//		 * byte[] buffer = new byte[wrappedRequest.getContentLength()];
//		 * IOUtils.readFully(wrappedRequest.getInputStream(), buffer); String body1 =
//		 * new String(buffer,StandardCharsets.UTF_8);
//		 */
//		String body = IOUtils.toString(wrappedRequest.getReader());
//		String requestUrl=wrappedRequest.getRequestURI();	
//		LOGGER.info("Requested URL is:-->>"+requestUrl);
//		//JsonObject obj = null;
//	    JsonObject obj = new JsonParser().parse(body).getAsJsonObject();
//	    Enumeration<String> headerNames = wrappedRequest.getHeaderNames();
//	  
//		if (wrappedRequest.getHeader("deviceid").length() > 7) {
//			 
//			decKey =handler.getSessionIdFromDeviceId(wrappedRequest.getHeader("deviceid"));
//			if (null != decKey && decKey.length() > 2 && decKey.contains("~~~")) {
//				decKeyCustId = decKey.split("~~~");
//				decKey = decKeyCustId[0].toString(); // session token
//				custid = (decKeyCustId[1]); // userid
//				deviceValid = decKeyCustId[2]; // statusid
//				wrappedRequest.setAttribute("updatedBy", custid);
//			} else
//				deviceValid = "0";
//
//			keyType = "D";
//
//		} else {
//			decKey = "jrD@Mt6i#0mnip$b";
//			keyType = "M";
//		}
//		
//	
//
//		try { 
//			if (wrappedRequest.getHeader("deviceid").equalsIgnoreCase("9")) {
//				deviceDBId = 629;
//				json=(JSONObject) parser.parse(obj.toString());
//		
//			} else {
//				if(obj.get("map") != null) {
//	
//				String decReq =EncryptDeryptUtility.decryptNonAndroid(obj.get("map").toString(), decKey);
//				//LOGGER.info("Encrypted request ##:"+decReq);
//				  String first = decReq.toString().split("")[0];
//				 // LOGGER.info("splitted req #*:"+first);
//				  
//				  if(first.equals("[")){
//					  jsonArray= (JSONArray) parser.parse(decReq);
//				  }else{
//					  //for sql injection
//					   decReq=decReq.replaceAll("(?i)DELETE", "").replaceAll("(?i)UPDATE", "")
//					    		 .replaceAll("(?i)DROP", "").replaceAll("(?i)TRUNCATE", "").replaceAll("(?i)SELECT", "");
//					   
//					   //for CSR(cross site scripting)
//					   decReq= decReq.replaceAll("&amp", "").replaceAll("&lt", "")
//								.replaceAll("&gt", "").replaceAll("&quot", "").replaceAll("&#x27", "").replaceAll("&#x2F", "");
//					   
//					  json = (JSONObject) parser.parse(decReq);
//				  }
//				
//				}else {
//					  //for sql injection
//					   String decReq =EncryptDeryptUtility.decryptNonAndroid(obj.toString(), decKey);
//					     decReq=decReq.replaceAll("(?i)DELETE", "").replaceAll("(?i)UPDATE", "")
//					    		 .replaceAll("(?i)DROP", "").replaceAll("(?i)TRUNCATE", "").replaceAll("(?i)SELECT", "");
//					     
//					   //for CSR(cross site scrripting)
//						   decReq= decReq.replaceAll("&amp", "").replaceAll("&lt", "")
//									.replaceAll("&gt", "").replaceAll("&quot", "").replaceAll("&#x27", "").replaceAll("&#x2F", "");
//						   
//						json = (JSONObject) parser.parse(decReq);
//					
//				}
//				if(json != null){
//				JsonElement element = gson.fromJson(json.toString(), JsonElement.class);
//                obj=element.getAsJsonObject();
//				}
//		
//			}
//		} catch (Exception e) {
//			LOGGER.error(e.getMessage(), e);
//			isreqvalid = false;
//		}
//
//		if (isreqvalid == true) {
//	
//		Gson gsonObj = new Gson();
//		if(json != null ){
//		wrappedRequest.resetInputStream(json.toString().getBytes());
//		
//		}else if(!ObjectUtils.isEmpty(jsonArray)){
//			wrappedRequest.resetInputStream(jsonArray.toString().getBytes());
//		}
//		ResponseWrapper responseWrapper = new ResponseWrapper((HttpServletResponse) response);
//		
//		chain.doFilter(wrappedRequest, responseWrapper);
//		
//		String responseContent = new String(responseWrapper.getDataOutput());
//	//	System.out.println(" Response Data :-->"+responseContent);
//		
//		String rsString = "";
//		if(requestUrl.equalsIgnoreCase("/PSBAdminPortal/masterconfig/getStatusList") || requestUrl.equalsIgnoreCase("/PSBAdminPortal/masterconfig/getAppMasterList")
//				 || requestUrl.equalsIgnoreCase("/PSBAdminPortal/menu/getSubMenuRightsForMenuId")
//				 || requestUrl.equalsIgnoreCase("/PSBAdminPortal/menu/getCustomizeSubMenuRightsForMenuId")  || requestUrl.equalsIgnoreCase("/PSBAdminPortal/masterconfig/getLanguageJsonById")){
//			
//			
//			System.out.println("Inside selected url data--->"+requestUrl);
//			rsString =	responseWrapper.getDataOutput();
//		
//			 String encryptedRes= "";
//			// System.out.println( "+++++++++++okokok"+rsString.replace("\n", "").replace("\r", ""));
//				if(!wrappedRequest.getHeader("deviceid").equalsIgnoreCase("9")){
//				EncryptDeryptUtility encDecObj = new EncryptDeryptUtility();
//			    encryptedRes = encDecObj.encryptNonAndroid(rsString, decKey);
//				encryptedRes = encryptedRes.replace("\n", "").replace("\r", "");
//				response.getOutputStream().write(gsonObj.toJson(encryptedRes).toString().replace("\n", "").replace("\r", "").getBytes());
//				}else{
//					response.getOutputStream().write(rsString.toString().replace("\n", "").replace("\r", "").getBytes());
//				}
//				
//			
//		}else{
//			JsonObject resObj = new JsonParser().parse(new String(responseWrapper.getDataOutput())).getAsJsonObject();
//		    rsString  =  resObj.get("result").toString();
//		    
//			String szUT8 = new String(rsString.getBytes(), "UTF-8").replace("\ufffd?", "").replace("\ufffd", "");	
//			szUT8 = szUT8.replace("\n", "").replace("\r", "");
//			
//			ResponseMessageBean resPOJO = gson.fromJson(responseContent, ResponseMessageBean.class);
//			rsString =szUT8;
//
//			if(!wrappedRequest.getHeader("deviceid").equalsIgnoreCase("9")){
//			EncryptDeryptUtility encDecObj = new EncryptDeryptUtility();
//			String encryptedRes = encDecObj.encryptNonAndroid(rsString, decKey);
//			encryptedRes = encryptedRes.replace("\n", "").replace("\r", "");
//		    resPOJO.setResult(encryptedRes);
//			}
//			response.getOutputStream().write(gsonObj.toJson(resPOJO).toString().replace("\n", "").replace("\r", "").getBytes());
//		}
//		
//		
//		}else {
//			ResponseMessageBean resPOJO = new ResponseMessageBean();
//			Gson gsonObj = new Gson();
//			response.setContentType("application/json");
//			resPOJO.setResponseCode("92");
//			resPOJO.setResponseMessage("Your Session Has Been Expired. Another User Logged In With Same Credentials");
//			resPOJO.setResult("");
//			
//			response.getWriter().write(gsonObj.toJson(resPOJO).toString().replace("\n", "").replace("\r", ""));
//		}
//		}
//	    

	@SuppressWarnings("unused")
	@Override
	protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
	
		if (handler == null) {
			ServletContext servletContext = request.getServletContext();
			WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			handler = webApplicationContext.getBean(RequestHandler.class);
		}
		
//		String[] actionname = request.getRequestURL().toString().split("/");
//		//XSSRequestWrapper wrappedRequest = new XSSRequestWrapper((HttpServletRequest) request);		
//		String jsonToOriginator = "";
//		Gson gson = new Gson();
//		String decKey = "";
//		String[] decKeyCustId = null;
//		String custid = "8982";		
//		//wrappedRequest.setAttribute("updatedBy", custid);
//		int appId = 0;
//		int deviceDBId = 0;
//		String keyType = "P";
//		String deviceValid = "3";
//		String body ="";
//		JSONObject json = null;
//		JSONArray jsonArray = null;
//		JSONParser parser = new JSONParser();
//		boolean isreqvalid = true;
		System.out.println("getContentLength ::"+request.getContentLength());
		System.out.println("deviceid ::"+request.getHeader("deviceid"));
		System.out.println("wrappedRequest.getContentType() ::"+request.getContentType());
		   String requestUrl1=request.getRequestURI();	
			LOGGER.info("Requested URL is:-->>"+requestUrl1);
		if(request.getContentType().contains("multipart/form-data")) {

		    ResponseWrapper responseWrapper = new ResponseWrapper((HttpServletResponse) response);
			
			chain.doFilter(request, responseWrapper);
			
			Gson gson = new Gson();
 			String decKey = "";
			decKey = handler.getSessionIdFromDeviceId(request.getHeader("deviceid"));
			String responseContent = new String(responseWrapper.getDataOutput());
			String rsStrin = "";
			JsonObject resObj = new JsonParser().parse(new String(responseWrapper.getDataOutput())).getAsJsonObject();
		    rsStrin  =  resObj.get("result").toString();
		    
			String szUT8 = new String(rsStrin.getBytes(), "UTF-8").replace("\ufffd?", "").replace("\ufffd", "");	
			szUT8 = szUT8.replace("\n", "").replace("\r", "");
			
			ResponseMessageBean resPOJO = gson.fromJson(responseContent, ResponseMessageBean.class);
			//rsStrin =szUT8;
			Gson gsonObj = new Gson();
			if(!request.getHeader("deviceid").equalsIgnoreCase("9")){
			EncryptDeryptUtility encDecObj = new EncryptDeryptUtility();
			String encryptedRes = encDecObj.encryptNonAndroid(rsStrin, decKey);
			encryptedRes = encryptedRes.replace("\n", "").replace("\r", "");
		    resPOJO.setResult(encryptedRes);
			}
			response.getOutputStream().write(gsonObj.toJson(resPOJO).toString().replace("\n", "").replace("\r", "").getBytes());
		 
		}
 	     else {
 	    	 
 	    	String[] actionname = request.getRequestURL().toString().split("/");
 			XSSRequestWrapper wrappedRequest = new XSSRequestWrapper((HttpServletRequest) request);		
 			String jsonToOriginator = "";
 			Gson gson = new Gson();
 			String decKey = "";
 			String[] decKeyCustId = null;
 			String custid = "8982";		
 		    wrappedRequest.setAttribute("updatedBy", custid);
 			int appId = 0;
 			int deviceDBId = 0;
 			String keyType = "P";
 			String deviceValid = "3";
 			String body ="";
 			JSONObject json = null;
 			JSONArray jsonArray = null;
 			JSONParser parser = new JSONParser();
 			boolean isreqvalid = true;
 			System.out.println("getContentLength ::"+wrappedRequest.getContentLength());
 			System.out.println("wrappedRequest.getContentType() ::"+wrappedRequest.getContentType());
 			   String requestUrl=wrappedRequest.getRequestURI();	
 				LOGGER.info("Requested URL is:-->>"+requestUrl);

		   body = IOUtils.toString(wrappedRequest.getReader());
		    JsonObject obj = new JsonParser().parse(body).getAsJsonObject();
		    Enumeration<String> headerNames = wrappedRequest.getHeaderNames();
		    
			if (wrappedRequest.getHeader("deviceid").length() > 7) {
				 
				decKey =handler.getSessionIdFromDeviceId(wrappedRequest.getHeader("deviceid"));
				if (null != decKey && decKey.length() > 2 && decKey.contains("~~~")) {
					decKeyCustId = decKey.split("~~~");
					decKey = decKeyCustId[0].toString(); // session token
					custid = (decKeyCustId[1]); // userid
					deviceValid = decKeyCustId[2]; // statusid
					wrappedRequest.setAttribute("updatedBy", custid);
				} else
					deviceValid = "0";

				keyType = "D";

			} else {
				decKey = "jrD@Mt6i#0mnip$b";
				keyType = "M";
			}
			
		

			try { 
				if (wrappedRequest.getHeader("deviceid").equalsIgnoreCase("9")) {
					deviceDBId = 629;
					json=(JSONObject) parser.parse(obj.toString());
			
				} else {
					if(obj.get("map") != null) {
		
					String decReq =EncryptDeryptUtility.decryptNonAndroid(obj.get("map").toString(), decKey);
					//LOGGER.info("Encrypted request ##:"+decReq);
					  String first = decReq.toString().split("")[0];
					 // LOGGER.info("splitted req #*:"+first);
					  
					  if(first.equals("[")){
						  jsonArray= (JSONArray) parser.parse(decReq);
					  }else{
						  //for sql injection
						   decReq=decReq.replaceAll("(?i)DELETE", "").replaceAll("(?i)UPDATE", "")
						    		 .replaceAll("(?i)DROP", "").replaceAll("(?i)TRUNCATE", "").replaceAll("(?i)SELECT", "");
						   
						   //for CSR(cross site scripting)
						   decReq= decReq.replaceAll("&amp", "").replaceAll("&lt", "")
									.replaceAll("&gt", "").replaceAll("&quot", "").replaceAll("&#x27", "").replaceAll("&#x2F", "");
						   
						  json = (JSONObject) parser.parse(decReq);
					  }
					
					}else {
						  //for sql injection
						   String decReq =EncryptDeryptUtility.decryptNonAndroid(obj.toString(), decKey);
						     decReq=decReq.replaceAll("(?i)DELETE", "").replaceAll("(?i)UPDATE", "")
						    		 .replaceAll("(?i)DROP", "").replaceAll("(?i)TRUNCATE", "").replaceAll("(?i)SELECT", "");
						     
						   //for CSR(cross site scrripting)
							   decReq= decReq.replaceAll("&amp", "").replaceAll("&lt", "")
										.replaceAll("&gt", "").replaceAll("&quot", "").replaceAll("&#x27", "").replaceAll("&#x2F", "");
							   
							json = (JSONObject) parser.parse(decReq);
						
					}
					if(json != null){
					JsonElement element = gson.fromJson(json.toString(), JsonElement.class);
	                obj=element.getAsJsonObject();
					}
			
				}
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				isreqvalid = false;
			}

			if (isreqvalid == true) {
		
			Gson gsonObj = new Gson();
			if(json != null ){
			wrappedRequest.resetInputStream(json.toString().getBytes());
			
			}else if(!ObjectUtils.isEmpty(jsonArray)){
				wrappedRequest.resetInputStream(jsonArray.toString().getBytes());
			}
			ResponseWrapper responseWrapper = new ResponseWrapper((HttpServletResponse) response);
			
			chain.doFilter(wrappedRequest, responseWrapper);
			
			String responseContent = new String(responseWrapper.getDataOutput());
		//	System.out.println(" Response Data :-->"+responseContent);
			
			String rsString = "";
			if(requestUrl.equalsIgnoreCase("/PSBAdminPortal/masterconfig/getStatusList") || requestUrl.equalsIgnoreCase("/PSBAdminPortal/masterconfig/getAppMasterList")
					 || requestUrl.equalsIgnoreCase("/PSBAdminPortal/menu/getSubMenuRightsForMenuId")
					 || requestUrl.equalsIgnoreCase("/PSBAdminPortal/menu/getCustomizeSubMenuRightsForMenuId")  || requestUrl.equalsIgnoreCase("/PSBAdminPortal/masterconfig/getLanguageJsonById")){
				
				
				System.out.println("Inside selected url data--->"+requestUrl);
				rsString =	responseWrapper.getDataOutput();
			
				 String encryptedRes= "";
				// System.out.println( "+++++++++++okokok"+rsString.replace("\n", "").replace("\r", ""));
					if(!wrappedRequest.getHeader("deviceid").equalsIgnoreCase("9")){
					EncryptDeryptUtility encDecObj = new EncryptDeryptUtility();
				    encryptedRes = encDecObj.encryptNonAndroid(rsString, decKey);
					encryptedRes = encryptedRes.replace("\n", "").replace("\r", "");
					response.getOutputStream().write(gsonObj.toJson(encryptedRes).toString().replace("\n", "").replace("\r", "").getBytes());
					}else{
						response.getOutputStream().write(rsString.toString().replace("\n", "").replace("\r", "").getBytes());
					}
					
				
			}else{
				JsonObject resObj = new JsonParser().parse(new String(responseWrapper.getDataOutput())).getAsJsonObject();
			    rsString  =  resObj.get("result").toString();
			    
				String szUT8 = new String(rsString.getBytes(), "UTF-8").replace("\ufffd?", "").replace("\ufffd", "");	
				szUT8 = szUT8.replace("\n", "").replace("\r", "");
				
				ResponseMessageBean resPOJO = gson.fromJson(responseContent, ResponseMessageBean.class);
				rsString =szUT8;

				if(!wrappedRequest.getHeader("deviceid").equalsIgnoreCase("9")){
				EncryptDeryptUtility encDecObj = new EncryptDeryptUtility();
				String encryptedRes = encDecObj.encryptNonAndroid(rsString, decKey);
				encryptedRes = encryptedRes.replace("\n", "").replace("\r", "");
			    resPOJO.setResult(encryptedRes);
				}
				response.getOutputStream().write(gsonObj.toJson(resPOJO).toString().replace("\n", "").replace("\r", "").getBytes());
			}
			
			
			}else {
				ResponseMessageBean resPOJO = new ResponseMessageBean();
				Gson gsonObj = new Gson();
				response.setContentType("application/json");
				resPOJO.setResponseCode("92");
				resPOJO.setResponseMessage("Your Session Has Been Expired. Another User Logged In With Same Credentials");
				resPOJO.setResult("");
				
				response.getWriter().write(gsonObj.toJson(resPOJO).toString().replace("\n", "").replace("\r", ""));
			}
		}
		
	}
	
	@Override
	public void destroy() {
		
	}

	public boolean isJsonArrayRequest(String requestUrl) {
		if (requestUrl.equalsIgnoreCase("")) {

			return true;
		} else {
			return false;
		}
	}
	


}
	
