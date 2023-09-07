package com.itl.pns.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.itl.pns.dao.AdminSessionReqDao;

@Component
public class RequestHandler {

	@Autowired
	AdminSessionReqDao adminSessionReqDao;

	public String getSessionIdFromDeviceId(String deviceId) {
		return adminSessionReqDao.getSessionIdFromDeviceId(deviceId);
	}

	/*
	 * @Autowired omniChannelDAO mobiledao;
	 * 
	 * 
	 * public RequestHandler() { logger.info("Inside mee RequestHandler"); }
	 * 
	 * 
	 * private static Logger logger = Logger.getLogger(RequestHandler.class);
	 * 
	 * public String getServiceEncryptionType(String serviceName, int appId) { try {
	 * GenerateDBRRNOmni dbrrnomni = new GenerateDBRRNOmni(mobiledao);
	 * System.out.println(dbrrnomni.getServiceEncryptionType(serviceName, appId));
	 * return dbrrnomni.getServiceEncryptionType(serviceName, appId); } catch
	 * (Exception e) { logger.error("Error : COZ: " + e.getMessage() + "\n" +
	 * Utils.exceptionToString(e)); logger.info(e.getMessage()); return null; } }
	 * 
	 * 
	 * 
	 * // DB sessionId public String getSessionIdfromDeviceId(String deviceId) { try
	 * { GenerateDBRRNOmni dbrrnomni = new GenerateDBRRNOmni(mobiledao); return
	 * dbrrnomni.getSessionIdFromDeviceId(deviceId); } catch (Exception e) {
	 * logger.error("Error : COZ: " + e.getMessage() + "\n" +
	 * Utils.exceptionToString(e)); logger.info(e.getMessage()); return null; } }
	 * 
	 * 
	 * public String getSplitMobileNumberFromDeviceId(String deviceId) { try {
	 * GenerateDBRRNOmni dbrrnomni = new GenerateDBRRNOmni(mobiledao); return
	 * dbrrnomni.getSplitMobileNumberFromDeviceId(deviceId); } catch (Exception e) {
	 * logger.error("Error : COZ: " + e.getMessage() + "\n" +
	 * Utils.exceptionToString(e)); logger.info(e.getMessage()); return null; } }
	 * 
	 * public long getAppId(String entityid) { try { GenerateDBRRNOmni dbrrnomni =
	 * new GenerateDBRRNOmni(mobiledao); return dbrrnomni.getAppId(entityid); }
	 * catch (Exception e) { logger.error("Error : COZ: " + e.getMessage() + "\n" +
	 * Utils.exceptionToString(e)); logger.info(e.getMessage()); return 0; } }
	 * 
	 * public void insertTransactin(TRANSACTIONLOGS trnsactionlgs) { try {
	 * GenerateDBRRNOmni dbrrnomni = new GenerateDBRRNOmni(mobiledao);
	 * dbrrnomni.insertTransactin(trnsactionlgs); } catch (Exception e) {
	 * e.printStackTrace(); logger.info("Exception: ", e); } }
	 * 
	 * 
	 * public USERDEVICESMASTER getDevice(String deviceId) { try { GenerateDBRRNOmni
	 * dbrrnomni = new GenerateDBRRNOmni(mobiledao); return
	 * dbrrnomni.getDeviceId(deviceId); } catch (Exception e) {
	 * logger.error("Error : COZ: " + e.getMessage() + "\n" +
	 * Utils.exceptionToString(e)); logger.info("Exception: ", e); return null; } }
	 */
}
