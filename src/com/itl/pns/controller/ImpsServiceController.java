/*
 * package com.itl.pns.controller;
 * 
 * import java.io.File; import java.util.ArrayList; import java.util.Date;
 * import java.util.HashMap; import java.util.List; import java.util.Map;
 * 
 * import org.apache.log4j.LogManager; import org.apache.log4j.Logger; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.beans.factory.annotation.Value; import
 * org.springframework.http.HttpStatus; import
 * org.springframework.http.MediaType; import
 * org.springframework.http.ResponseEntity; import
 * org.springframework.util.ObjectUtils; import
 * org.springframework.web.bind.annotation.RequestBody; import
 * org.springframework.web.bind.annotation.RequestMapping; import
 * org.springframework.web.bind.annotation.RequestMethod; import
 * org.springframework.web.bind.annotation.RestController;
 * 
 * import com.itl.pns.bean.DateBean; import com.itl.pns.bean.ImpsCustBean;
 * import com.itl.pns.bean.ImpsOtpLog; import com.itl.pns.bean.ImpsRevisionBean;
 * import com.itl.pns.bean.ImpsStationBean; import
 * com.itl.pns.bean.ImpsTransactionFeeRevisionBean; import
 * com.itl.pns.bean.ImpsTransactionFeeSetupBean; import
 * com.itl.pns.bean.ResponseMessageBean; import com.itl.pns.bean.TransLogBean;
 * import com.itl.pns.dao.ImpsServiceDao; import
 * com.itl.pns.dao.MasterConfigDao; import com.itl.pns.dao.OmniChannelDao;
 * import com.itl.pns.entity.ConfigMasterEntity; import
 * com.itl.pns.entity.ImpsStatusEntity; import com.itl.pns.entity.SyslogEntity;
 * import com.itl.pns.impsEntity.CreditPoolAccountConfEntity; import
 * com.itl.pns.impsEntity.DebitPoolAccountConfEntity; import
 * com.itl.pns.impsEntity.DeliveryChannelsEntity; import
 * com.itl.pns.impsEntity.EeuserEntity; import
 * com.itl.pns.impsEntity.EeuserRolesEntity; import
 * com.itl.pns.impsEntity.IfscCodeMasterEntity; import
 * com.itl.pns.impsEntity.ImpsMasterEntity; import
 * com.itl.pns.impsEntity.ImpsReportEntity; import
 * com.itl.pns.impsEntity.ImpsRolesEntity; import
 * com.itl.pns.impsEntity.ImpsTaskEntity; import
 * com.itl.pns.impsEntity.ImpsTransactionFeeSetUpEntity; import
 * com.itl.pns.impsEntity.PermissionEntity; import
 * com.itl.pns.impsEntity.ReportCatrgoryEntity; import
 * com.itl.pns.impsEntity.RolePermsEntity; import
 * com.itl.pns.impsEntity.ScheduleEntity; import
 * com.itl.pns.impsEntity.SmsTemplatesEntity; import
 * com.itl.pns.impsEntity.SysconfigEntity; import
 * com.itl.pns.service.ImpsService; import com.itl.pns.util.EmailUtil; import
 * com.itl.pns.util.PdfGenerator; import com.itl.pns.util.RandomNumberGenerator;
 * 
 *//**
	 * @author shubham.lokhande
	 *
	 *//*
		 * @RestController
		 * 
		 * @RequestMapping("impsService") public class ImpsServiceController {
		 * 
		 * private static final Logger logger =
		 * LogManager.getLogger(ImpsServiceController.class);
		 * 
		 * 
		 * @Autowired ImpsService impsService;
		 * 
		 * @Autowired ImpsServiceDao impsServiceDao;
		 * 
		 * @Autowired EmailUtil emailUtil;
		 * 
		 * @Autowired MasterConfigDao masterConfigDao;
		 * 
		 * @Autowired OmniChannelDao omniChannelDao;
		 * 
		 * @Value("${PDF_FILE_PATH}") private String pdfFilePath;
		 * 
		 * @RequestMapping(value = "/getStatus", method = RequestMethod.POST, consumes =
		 * MediaType.APPLICATION_JSON_VALUE) public ResponseEntity<ResponseMessageBean>
		 * getStatus() { logger.info("In ImpsServiceController -> getStatus()");
		 * ResponseMessageBean res = new ResponseMessageBean(); try {
		 * List<ImpsStatusEntity> impsData = impsService.getImpsStatusList(); String
		 * lastLogin[] = null; for (ImpsStatusEntity aa : impsData) { lastLogin =
		 * aa.getDetail().split(","); } if (!ObjectUtils.isEmpty(impsData)) {
		 * res.setResponseCode("200"); res.setResult(impsData);
		 * res.setResponseMessage("Success"); } else { res.setResponseCode("202");
		 * res.setResponseMessage("No Records Found"); } } catch (Exception e) {
		 * logger.info("Exception:", e);
		 * 
		 * } return new ResponseEntity<>(res, HttpStatus.OK);
		 * 
		 * }
		 * 
		 * @RequestMapping(value = "/getImpsStatusById", method = RequestMethod.POST,
		 * consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> getImpsStatusById(@RequestBody
		 * ImpsStatusEntity impsStatusData) {
		 * logger.info("In ImpsServiceController -> getImpsStatusById()");
		 * ResponseMessageBean res = new ResponseMessageBean(); try {
		 * List<ImpsStatusEntity> impsData =
		 * impsService.getImpsStatusById(impsStatusData);
		 * 
		 * if (!ObjectUtils.isEmpty(impsData)) { res.setResponseCode("200");
		 * res.setResult(impsData); res.setResponseMessage("Success"); } else {
		 * res.setResponseCode("202"); res.setResponseMessage("No Records Found"); } }
		 * catch (Exception e) { logger.info("Exception:", e);
		 * 
		 * } return new ResponseEntity<>(res, HttpStatus.OK);
		 * 
		 * }
		 * 
		 * @RequestMapping(value = "/deleteImpsStatusById", method = RequestMethod.POST,
		 * consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> deleteImpsStatusById(@RequestBody
		 * ImpsStatusEntity impsStatusData) {
		 * logger.info("In ImpsServiceController -> deleteImpsStatusById()");
		 * ResponseMessageBean response = new ResponseMessageBean(); boolean isUpdate =
		 * false; try { isUpdate = impsService.deleteImpsStatusById(impsStatusData); if
		 * (isUpdate) { response.setResponseMessage("Details Deleted Successfully");
		 * response.setResponseCode("200");
		 * 
		 * } else { response.setResponseMessage("Error While Deleting Details");
		 * response.setResponseCode("500"); } } catch (Exception e) {
		 * logger.info("Exception:", e);
		 * 
		 * } return new ResponseEntity<>(response, HttpStatus.OK);
		 * 
		 * }
		 * 
		 * @RequestMapping(value = "/getImpsStatusByState", method = RequestMethod.POST,
		 * consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> getImpsStatusByState(@RequestBody
		 * ImpsStatusEntity impsStatusData) {
		 * logger.info("In ImpsServiceController -> getImpsStatusByState()");
		 * ResponseMessageBean res = new ResponseMessageBean(); try {
		 * List<ImpsStatusEntity> impsData =
		 * impsService.getImpsStatusByState(impsStatusData);
		 * 
		 * if (!ObjectUtils.isEmpty(impsData)) { res.setResponseCode("200");
		 * res.setResult(impsData); res.setResponseMessage("Success"); } else {
		 * res.setResponseCode("202"); res.setResponseMessage("No Records Found"); } }
		 * catch (Exception e) { logger.info("Exception:", e);
		 * 
		 * } return new ResponseEntity<>(res, HttpStatus.OK);
		 * 
		 * }
		 * 
		 * @RequestMapping(value = "/updateImpsStatusData", method = RequestMethod.POST,
		 * consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> updateImpsStatusData(@RequestBody
		 * ImpsStatusEntity impsStatusData) { ResponseMessageBean response = new
		 * ResponseMessageBean(); boolean isUpdate = false; try { isUpdate =
		 * impsService.updateImpsStatusData(impsStatusData); if (isUpdate) {
		 * response.setResponseMessage("Details Updated Successfully");
		 * response.setResponseCode("200");
		 * 
		 * } else { response.setResponseMessage("Error While Updating Details");
		 * response.setResponseCode("500"); } } catch (Exception e) {
		 * logger.info("Exception:", e);
		 * 
		 * } return new ResponseEntity<>(response, HttpStatus.OK); }
		 * 
		 * @RequestMapping(value = "/getSyslogs", method = RequestMethod.POST, consumes
		 * = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> getSyslogs() {
		 * logger.info("In ImpsServiceController -> getSyslogs()"); ResponseMessageBean
		 * res = new ResponseMessageBean(); try { List<SyslogEntity> impsData =
		 * impsService.getSyslogs();
		 * 
		 * if (!ObjectUtils.isEmpty(impsData)) { res.setResponseCode("200");
		 * res.setResult(impsData); res.setResponseMessage("Success"); } else {
		 * res.setResponseCode("202"); res.setResponseMessage("No Records Found"); } }
		 * catch (Exception e) { logger.info("Exception:", e);
		 * 
		 * } return new ResponseEntity<>(res, HttpStatus.OK);
		 * 
		 * }
		 * 
		 * @RequestMapping(value = "/getStationCount", method = RequestMethod.POST,
		 * consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> getStationCount() {
		 * logger.info("in ImpsServiceController -> getStationCount()");
		 * ResponseMessageBean response = impsService.getStationCount(); return new
		 * ResponseEntity<>(response, HttpStatus.OK);
		 * 
		 * }
		 * 
		 * @RequestMapping(value = "/getTransLogs", method = RequestMethod.POST,
		 * consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> getTransLogs(@RequestBody DateBean
		 * dateObj) { logger.info("In ImpsServiceController -> getTransLogs()");
		 * ResponseMessageBean res = new ResponseMessageBean();
		 * //System.out.println(transLogBean.getTdate()+" "+transLogBean.getToDate()+" "
		 * +transLogBean.getFromDate()); //List<TransLogBean> impsData =
		 * impsService.getTransLogs(transLogBean); try { ResponseMessageBean respm =
		 * impsServiceDao.getTransLogs(dateObj);
		 * 
		 * if (!ObjectUtils.isEmpty(respm)) { res.setResponseCode("200");
		 * res.setResult(respm); res.setResponseMessage("Success"); } else {
		 * res.setResponseCode("202"); res.setResponseMessage("No Records Found"); } }
		 * catch (Exception e) { logger.info("Exception:", e);
		 * 
		 * } return new ResponseEntity<>(res, HttpStatus.OK);
		 * 
		 * }
		 * 
		 * @RequestMapping(value = "/getSysConfigDataById", method = RequestMethod.POST,
		 * consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> getSysConfigDataById(@RequestBody
		 * SysconfigEntity sysConfigReq) {
		 * logger.info("In ImpsServiceController -> getSysConfigDataById()");
		 * ResponseMessageBean res = new ResponseMessageBean(); try {
		 * List<SysconfigEntity> sysConfigData =
		 * impsService.getSysConfigDataById(sysConfigReq);
		 * 
		 * if (!ObjectUtils.isEmpty(sysConfigData)) { res.setResponseCode("200");
		 * res.setResult(sysConfigData); res.setResponseMessage("Success"); } else {
		 * res.setResponseCode("202"); res.setResponseMessage("No Records Found"); } }
		 * catch (Exception e) { logger.info("Exception:", e);
		 * 
		 * } return new ResponseEntity<>(res, HttpStatus.OK);
		 * 
		 * }
		 * 
		 * @RequestMapping(value = "/getAllSysConfigData", method = RequestMethod.POST,
		 * consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> getAllSysConfigData() {
		 * logger.info("In ImpsServiceController -> getAllSysConfigData()");
		 * ResponseMessageBean res = new ResponseMessageBean(); try {
		 * List<SysconfigEntity> sysConfigData = impsService.getAllSysConfigData();
		 * 
		 * if (!ObjectUtils.isEmpty(sysConfigData)) { res.setResponseCode("200");
		 * res.setResult(sysConfigData); res.setResponseMessage("Success"); } else {
		 * res.setResponseCode("202"); res.setResponseMessage("No Records Found"); } }
		 * catch (Exception e) { logger.info("Exception:", e);
		 * 
		 * } return new ResponseEntity<>(res, HttpStatus.OK);
		 * 
		 * }
		 * 
		 * @RequestMapping(value = "/insertSysConfigData", method = RequestMethod.POST,
		 * consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> insertSysConfigData(@RequestBody
		 * SysconfigEntity sysConfigReq) { ResponseMessageBean response = new
		 * ResponseMessageBean(); boolean isSave = false; try { isSave =
		 * impsService.insertSysConfigData(sysConfigReq); if (isSave) {
		 * response.setResponseMessage("Details Inserted Successfully");
		 * response.setResponseCode("200");
		 * 
		 * } else { response.setResponseMessage("Error While Saving Details");
		 * response.setResponseCode("500"); } } catch (Exception e) {
		 * logger.info("Exception:", e);
		 * 
		 * } return new ResponseEntity<>(response, HttpStatus.OK); }
		 * 
		 * @RequestMapping(value = "/updateSysConfigData", method = RequestMethod.POST,
		 * consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> updateSysConfigData(@RequestBody
		 * SysconfigEntity sysConfigReq) { ResponseMessageBean response = new
		 * ResponseMessageBean(); boolean isUpdate = false; try { isUpdate =
		 * impsService.updateSysConfigData(sysConfigReq); if (isUpdate) {
		 * response.setResponseMessage("Details Updated Successfully");
		 * response.setResponseCode("200");
		 * 
		 * } else { response.setResponseMessage("Error While Updating Details");
		 * response.setResponseCode("500"); } } catch (Exception e) {
		 * logger.info("Exception:", e);
		 * 
		 * } return new ResponseEntity<>(response, HttpStatus.OK); }
		 * 
		 * @RequestMapping(value = "/deleteSysConfigData", method = RequestMethod.POST,
		 * consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> deleteSysConfigData(@RequestBody
		 * SysconfigEntity sysConfigReq) { ResponseMessageBean response = new
		 * ResponseMessageBean(); boolean isUpdate = false; try { isUpdate =
		 * impsService.deleteSysConfigData(sysConfigReq); if (isUpdate) {
		 * response.setResponseMessage("Details Deleted Successfully");
		 * response.setResponseCode("200");
		 * 
		 * } else { response.setResponseMessage("Error While Deleting Details");
		 * response.setResponseCode("500"); } } catch (Exception e) {
		 * logger.info("Exception:", e);
		 * 
		 * } return new ResponseEntity<>(response, HttpStatus.OK); }
		 * 
		 * @RequestMapping(value = "/getEeuserDataByEeuser", method =
		 * RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> getEeuserDataByEeuser(@RequestBody
		 * EeuserRolesEntity eeUserRoleData) {
		 * logger.info("In ImpsServiceController -> getEeuserDataByEeuser()");
		 * ResponseMessageBean res = new ResponseMessageBean(); try {
		 * List<EeuserRolesEntity> sysConfigData =
		 * impsService.getEeuserDataByEeuser(eeUserRoleData);
		 * 
		 * if (!ObjectUtils.isEmpty(sysConfigData)) { res.setResponseCode("200");
		 * res.setResult(sysConfigData); res.setResponseMessage("Success"); } else {
		 * res.setResponseCode("202"); res.setResponseMessage("No Records Found"); } }
		 * catch (Exception e) { logger.info("Exception:", e);
		 * 
		 * } return new ResponseEntity<>(res, HttpStatus.OK);
		 * 
		 * }
		 * 
		 * @RequestMapping(value = "/insertEeuserRole", method = RequestMethod.POST,
		 * consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> insertEeuserRole(@RequestBody
		 * EeuserRolesEntity eeUserRoleData) { ResponseMessageBean response = new
		 * ResponseMessageBean(); boolean isSave = false; try { isSave =
		 * impsService.insertEeuserRole(eeUserRoleData); if (isSave) {
		 * response.setResponseMessage("Details Inserted Successfully");
		 * response.setResponseCode("200");
		 * 
		 * } else { response.setResponseMessage("Error While Saving Details");
		 * response.setResponseCode("500"); } } catch (Exception e) {
		 * logger.info("Exception:", e);
		 * 
		 * } return new ResponseEntity<>(response, HttpStatus.OK); }
		 * 
		 * @RequestMapping(value = "/updateEeuserData", method = RequestMethod.POST,
		 * consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> updateSysConfigData(@RequestBody
		 * EeuserEntity eeuserData) { ResponseMessageBean response = new
		 * ResponseMessageBean(); boolean isUpdate = false; try { isUpdate =
		 * impsService.updateEeuserData(eeuserData); if (isUpdate) {
		 * response.setResponseMessage("Details Updated Successfully");
		 * response.setResponseCode("200");
		 * 
		 * } else { response.setResponseMessage("Error While Updating Details");
		 * response.setResponseCode("500"); } } catch (Exception e) {
		 * logger.info("Exception:", e);
		 * 
		 * } return new ResponseEntity<>(response, HttpStatus.OK); }
		 * 
		 * @RequestMapping(value = "/deleteEeuserRole", method = RequestMethod.POST,
		 * consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> deleteEeuserRole(@RequestBody
		 * EeuserRolesEntity eeUserRoleData) { ResponseMessageBean response = new
		 * ResponseMessageBean(); boolean isUpdate = false; try { isUpdate =
		 * impsService.deleteEeuserRole(eeUserRoleData); if (isUpdate) {
		 * response.setResponseMessage("Details Deleted Successfully");
		 * response.setResponseCode("200");
		 * 
		 * } else { response.setResponseMessage("Error While Deleting Details");
		 * response.setResponseCode("500"); } } catch (Exception e) {
		 * logger.info("Exception:", e);
		 * 
		 * } return new ResponseEntity<>(response, HttpStatus.OK); }
		 * 
		 * @RequestMapping(value = "/getPermissionByName", method = RequestMethod.POST,
		 * consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> getPermissionByName(@RequestBody
		 * PermissionEntity permissionReq) {
		 * logger.info("In ImpsServiceController -> getPermissionByName()");
		 * ResponseMessageBean res = new ResponseMessageBean(); try {
		 * List<PermissionEntity> permissionData =
		 * impsService.getPermissionByName(permissionReq);
		 * 
		 * if (!ObjectUtils.isEmpty(permissionData)) { res.setResponseCode("200");
		 * res.setResult(permissionData); res.setResponseMessage("Success"); } else {
		 * res.setResponseCode("202"); res.setResponseMessage("No Records Found"); } }
		 * catch (Exception e) { logger.info("Exception:", e);
		 * 
		 * } return new ResponseEntity<>(res, HttpStatus.OK);
		 * 
		 * }
		 * 
		 * @RequestMapping(value = "/insertRolePerms", method = RequestMethod.POST,
		 * consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> insertRolePerms(@RequestBody
		 * RolePermsEntity rolePermsReq) { ResponseMessageBean response = new
		 * ResponseMessageBean(); boolean isSave = false; try { isSave =
		 * impsService.insertRolePerms(rolePermsReq); if (isSave) {
		 * response.setResponseMessage("Details Inserted Successfully");
		 * response.setResponseCode("200");
		 * 
		 * } else { response.setResponseMessage("Error While Saving Details");
		 * response.setResponseCode("500"); } } catch (Exception e) {
		 * logger.info("Exception:", e);
		 * 
		 * } return new ResponseEntity<>(response, HttpStatus.OK); }
		 * 
		 * @RequestMapping(value = "/updatePermission", method = RequestMethod.POST,
		 * consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> updatePermission(@RequestBody
		 * PermissionEntity permissionReq) { ResponseMessageBean response = new
		 * ResponseMessageBean(); boolean isUpdate = false; isUpdate =
		 * impsService.updatePermission(permissionReq); if (isUpdate) {
		 * response.setResponseMessage("Details Updated Successfully");
		 * response.setResponseCode("200");
		 * 
		 * } else { response.setResponseMessage("Error While Updating Details");
		 * response.setResponseCode("500"); } return new ResponseEntity<>(response,
		 * HttpStatus.OK); }
		 * 
		 * @RequestMapping(value = "/insertPermission", method = RequestMethod.POST,
		 * consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> insertPermission(@RequestBody
		 * PermissionEntity permissionReq) { ResponseMessageBean response = new
		 * ResponseMessageBean(); boolean isUpdate = false; isUpdate =
		 * impsService.insertPermission(permissionReq); if (isUpdate) {
		 * response.setResponseMessage("Details Updated Successfully");
		 * response.setResponseCode("200");
		 * 
		 * } else { response.setResponseMessage("Error While Updating Details");
		 * response.setResponseCode("500"); } return new ResponseEntity<>(response,
		 * HttpStatus.OK); }
		 * 
		 * @RequestMapping(value = "/deletePermission", method = RequestMethod.POST,
		 * consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> deletePermission(@RequestBody
		 * PermissionEntity permissionReq) { ResponseMessageBean response = new
		 * ResponseMessageBean(); boolean isUpdate = false; isUpdate =
		 * impsService.deletePermission(permissionReq); if (isUpdate) {
		 * response.setResponseMessage("Details Updated Successfully");
		 * response.setResponseCode("200");
		 * 
		 * } else { response.setResponseMessage("Error While Updating Details");
		 * response.setResponseCode("500"); } return new ResponseEntity<>(response,
		 * HttpStatus.OK); }
		 * 
		 * @RequestMapping(value = "/deleteRolePerms", method = RequestMethod.POST,
		 * consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> deleteRolePerms(@RequestBody
		 * RolePermsEntity rolePermsReq) { ResponseMessageBean response = new
		 * ResponseMessageBean(); boolean isUpdate = false; isUpdate =
		 * impsService.deleteRolePerms(rolePermsReq); if (isUpdate) {
		 * response.setResponseMessage("Details Deleted Successfully");
		 * response.setResponseCode("200");
		 * 
		 * } else { response.setResponseMessage("Error While Deleting Details");
		 * response.setResponseCode("500"); } return new ResponseEntity<>(response,
		 * HttpStatus.OK); }
		 * 
		 * @RequestMapping(value = "/getAllImpsRoles", method = RequestMethod.POST,
		 * consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> getAllImpsRoles() {
		 * logger.info("In ImpsServiceController -> getAllImpsRoles()");
		 * ResponseMessageBean res = new ResponseMessageBean(); List<ImpsRolesEntity>
		 * impsRoles = impsService.getAllImpsRoles();
		 * 
		 * if (!ObjectUtils.isEmpty(impsRoles)) { res.setResponseCode("200");
		 * res.setResult(impsRoles); res.setResponseMessage("Success"); } else {
		 * res.setResponseCode("202"); res.setResponseMessage("No Records Found"); }
		 * return new ResponseEntity<>(res, HttpStatus.OK);
		 * 
		 * }
		 * 
		 * @RequestMapping(value = "/getAllImpsRolesById", method = RequestMethod.POST,
		 * consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> getAllImpsRolesById(@RequestBody
		 * ImpsRolesEntity impsRolesReq) {
		 * logger.info("In ImpsServiceController -> getAllImpsRolesById()");
		 * ResponseMessageBean res = new ResponseMessageBean(); List<ImpsRolesEntity>
		 * impsRoles = impsService.getAllImpsRolesById(impsRolesReq);
		 * 
		 * if (!ObjectUtils.isEmpty(impsRoles)) { res.setResponseCode("200");
		 * res.setResult(impsRoles); res.setResponseMessage("Success"); } else {
		 * res.setResponseCode("202"); res.setResponseMessage("No Records Found"); }
		 * return new ResponseEntity<>(res, HttpStatus.OK);
		 * 
		 * }
		 * 
		 * @RequestMapping(value = "/insertImpsRole", method = RequestMethod.POST,
		 * consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> insertImpsRole(@RequestBody
		 * ImpsRolesEntity impsRoleData) { ResponseMessageBean response = new
		 * ResponseMessageBean(); boolean isSave = false; isSave =
		 * impsService.insertImpsRole(impsRoleData); if (isSave) {
		 * response.setResponseMessage("Details Inserted Successfully");
		 * response.setResponseCode("200");
		 * 
		 * } else { response.setResponseMessage("Error While Saving Details");
		 * response.setResponseCode("500"); } return new ResponseEntity<>(response,
		 * HttpStatus.OK); }
		 * 
		 * @RequestMapping(value = "/updateImpsRole", method = RequestMethod.POST,
		 * consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> updateImpsRole(@RequestBody
		 * ImpsRolesEntity impsRoleData) { ResponseMessageBean response = new
		 * ResponseMessageBean(); boolean isUpdate = false; isUpdate =
		 * impsService.updateImpsRole(impsRoleData); if (isUpdate) {
		 * response.setResponseMessage("Details Updated Successfully");
		 * response.setResponseCode("200");
		 * 
		 * } else { response.setResponseMessage("Error While Updating Details");
		 * response.setResponseCode("500"); } return new ResponseEntity<>(response,
		 * HttpStatus.OK); }
		 * 
		 * @RequestMapping(value = "/deleteImpsRole", method = RequestMethod.POST,
		 * consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> deleteImpsRole(@RequestBody
		 * ImpsRolesEntity impsRoleData) { ResponseMessageBean response = new
		 * ResponseMessageBean(); boolean isUpdate = false; isUpdate =
		 * impsService.deleteImpsRole(impsRoleData); if (isUpdate) {
		 * response.setResponseMessage("Details Deleted Successfully");
		 * response.setResponseCode("200");
		 * 
		 * } else { response.setResponseMessage("Error While Deleting Details");
		 * response.setResponseCode("500"); } return new ResponseEntity<>(response,
		 * HttpStatus.OK); }
		 * 
		 * @RequestMapping(value = "/getStationsByName", method = RequestMethod.POST,
		 * consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> getStationsByName(@RequestBody
		 * ImpsStationBean impsStationData) {
		 * logger.info("In ImpsServiceController -> getStationsByName()");
		 * ResponseMessageBean res = new ResponseMessageBean(); List<ImpsStationBean>
		 * stationData = impsService.getStationsByName(impsStationData);
		 * 
		 * if (!ObjectUtils.isEmpty(stationData)) { res.setResponseCode("200");
		 * res.setResult(stationData); res.setResponseMessage("Success"); } else {
		 * res.setResponseCode("202"); res.setResponseMessage("No Records Found"); }
		 * return new ResponseEntity<>(res, HttpStatus.OK);
		 * 
		 * }
		 * 
		 * @RequestMapping(value = "/getSmsTemplates", method = RequestMethod.POST,
		 * consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> getSmsTemplates() {
		 * logger.info("In ImpsServiceController -> getSmsTemplates()");
		 * ResponseMessageBean res = new ResponseMessageBean(); List<SmsTemplatesEntity>
		 * smsTemplateList = impsService.getSmsTemplates();
		 * 
		 * if (!ObjectUtils.isEmpty(smsTemplateList)) { res.setResponseCode("200");
		 * res.setResult(smsTemplateList); res.setResponseMessage("Success"); } else {
		 * res.setResponseCode("202"); res.setResponseMessage("No Records Found"); }
		 * return new ResponseEntity<>(res, HttpStatus.OK);
		 * 
		 * }
		 * 
		 * @RequestMapping(value = "/getSmsTemplatesById", method = RequestMethod.POST,
		 * consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> getSmsTemplatesById(@RequestBody
		 * SmsTemplatesEntity smsTemplateReq) {
		 * logger.info("In ImpsServiceController -> getSmsTemplatesById()");
		 * ResponseMessageBean res = new ResponseMessageBean(); List<SmsTemplatesEntity>
		 * smsTemplateList = impsService.getSmsTemplatesById(smsTemplateReq);
		 * 
		 * if (!ObjectUtils.isEmpty(smsTemplateList)) {
		 * smsTemplateList.get(0).setId(smsTemplateReq.getId());
		 * res.setResponseCode("200"); res.setResult(smsTemplateList);
		 * res.setResponseMessage("Success"); } else { res.setResponseCode("202");
		 * res.setResponseMessage("No Records Found"); } return new
		 * ResponseEntity<>(res, HttpStatus.OK);
		 * 
		 * }
		 * 
		 * @RequestMapping(value = "/updateSmsTemplate", method = RequestMethod.POST,
		 * consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> updateSmsTemplate(@RequestBody
		 * SmsTemplatesEntity smsTemplateReq) { ResponseMessageBean response = new
		 * ResponseMessageBean(); boolean isUpdate = false; isUpdate =
		 * impsService.updateSmsTemplate(smsTemplateReq); if (isUpdate) {
		 * response.setResponseMessage("Details Updated Successfully");
		 * response.setResponseCode("200");
		 * 
		 * } else { response.setResponseMessage("Error While Updating Details");
		 * response.setResponseCode("500"); } return new ResponseEntity<>(response,
		 * HttpStatus.OK); }
		 * 
		 * @RequestMapping(value = "/getTransFeeSetupByApplyFeeAndTransType", method =
		 * RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> getTransFeeSetupByApplyFeeAndTransType(
		 * 
		 * @RequestBody ImpsTransactionFeeSetupBean transactionFeeSetupReq) { logger.
		 * info("In ImpsServiceController -> getTransFeeSetupByApplyFeeAndTransType()");
		 * ResponseMessageBean res = new ResponseMessageBean();
		 * List<ImpsTransactionFeeSetupBean> transFeeSetupList = impsService
		 * .getTransFeeSetupByApplyFeeAndTransType(transactionFeeSetupReq);
		 * 
		 * if (!ObjectUtils.isEmpty(transFeeSetupList)) { res.setResponseCode("200");
		 * res.setResult(transFeeSetupList); res.setResponseMessage("Success"); } else {
		 * res.setResponseCode("202"); res.setResponseMessage("No Records Found"); }
		 * return new ResponseEntity<>(res, HttpStatus.OK);
		 * 
		 * }
		 * 
		 * @RequestMapping(value = "/getTransFeeSetupByApplyFeeAndTransTypeById", method
		 * = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean>
		 * getTransFeeSetupByApplyFeeAndTransTypeById(
		 * 
		 * @RequestBody ImpsTransactionFeeSetupBean transactionFeeSetupReq) { logger.
		 * info("In ImpsServiceController -> getTransFeeSetupByApplyFeeAndTransType()");
		 * ResponseMessageBean res = new ResponseMessageBean();
		 * List<ImpsTransactionFeeSetupBean> transFeeSetupList = impsService
		 * .getTransFeeSetupByApplyFeeAndTransTypeById(transactionFeeSetupReq);
		 * 
		 * if (!ObjectUtils.isEmpty(transFeeSetupList)) { res.setResponseCode("200");
		 * res.setResult(transFeeSetupList); res.setResponseMessage("Success"); } else {
		 * res.setResponseCode("202"); res.setResponseMessage("No Records Found"); }
		 * return new ResponseEntity<>(res, HttpStatus.OK);
		 * 
		 * }
		 * 
		 * @RequestMapping(value = "/updateTransFeeSetup", method = RequestMethod.POST,
		 * consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> updateTransFeeSetup(
		 * 
		 * @RequestBody ImpsTransactionFeeSetUpEntity transactionFeeSetupReq) {
		 * ResponseMessageBean response = new ResponseMessageBean(); boolean isUpdate =
		 * false; isUpdate = impsService.updateTransFeeSetup(transactionFeeSetupReq); if
		 * (isUpdate) { response.setResponseMessage("Details Updated Successfully");
		 * response.setResponseCode("200");
		 * 
		 * } else { response.setResponseMessage("Error While Updating Details");
		 * response.setResponseCode("500"); } return new ResponseEntity<>(response,
		 * HttpStatus.OK); }
		 * 
		 * @RequestMapping(value = "/getTransactionFeeRevisionCount", method =
		 * RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> getTransactionFeeRevisionCount(
		 * 
		 * @RequestBody ImpsTransactionFeeRevisionBean transFreeReq) {
		 * logger.info("in ImpsServiceController -> getTransactionFeeRevisionCount()");
		 * ResponseMessageBean response =
		 * impsService.getTransactionFeeRevisionCount(transFreeReq); return new
		 * ResponseEntity<>(response, HttpStatus.OK);
		 * 
		 * }
		 * 
		 * @RequestMapping(value = "/getAllIfscCodeDetails", method =
		 * RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> getAllIfscCodeDetails() {
		 * logger.info("In ImpsServiceController -> getAllIfscCodeDetails()");
		 * ResponseMessageBean res = new ResponseMessageBean();
		 * List<IfscCodeMasterEntity> ifscCodeList =
		 * impsService.getAllIfscCodeDetails();
		 * 
		 * if (!ObjectUtils.isEmpty(ifscCodeList)) { res.setResponseCode("200");
		 * res.setResult(ifscCodeList); res.setResponseMessage("Success"); } else {
		 * res.setResponseCode("202"); res.setResponseMessage("No Records Found"); }
		 * return new ResponseEntity<>(res, HttpStatus.OK);
		 * 
		 * }
		 * 
		 * @RequestMapping(value = "/getAllIfscCodeDetailsById", method =
		 * RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> getAllIfscCodeDetailsById(
		 * 
		 * @RequestBody IfscCodeMasterEntity ifscCodeMaster) {
		 * logger.info("In ImpsServiceController -> getAllIfscCodeDetailsById()");
		 * ResponseMessageBean res = new ResponseMessageBean();
		 * List<IfscCodeMasterEntity> ifscCodeList =
		 * impsService.getAllIfscCodeDetailsById(ifscCodeMaster);
		 * 
		 * if (!ObjectUtils.isEmpty(ifscCodeList)) {
		 * ifscCodeList.get(0).setId(ifscCodeMaster.getId());
		 * res.setResponseCode("200"); res.setResult(ifscCodeList);
		 * res.setResponseMessage("Success"); } else { res.setResponseCode("202");
		 * res.setResponseMessage("No Records Found"); } return new
		 * ResponseEntity<>(res, HttpStatus.OK);
		 * 
		 * }
		 * 
		 * @RequestMapping(value = "/insertIfscCode", method = RequestMethod.POST,
		 * consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> insertIfscCode(@RequestBody
		 * IfscCodeMasterEntity ifscCodeMaster) { ResponseMessageBean response = new
		 * ResponseMessageBean(); boolean isSave = false; isSave =
		 * impsService.insertIfscCode(ifscCodeMaster); if (isSave) {
		 * response.setResponseMessage("Details Inserted Successfully");
		 * response.setResponseCode("200");
		 * 
		 * } else { response.setResponseMessage("Error While Saving Details");
		 * response.setResponseCode("500"); } return new ResponseEntity<>(response,
		 * HttpStatus.OK); }
		 * 
		 * @RequestMapping(value = "/updateIfscCode", method = RequestMethod.POST,
		 * consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> updateIfscCode(@RequestBody
		 * IfscCodeMasterEntity ifscCodeMaster) { ResponseMessageBean response = new
		 * ResponseMessageBean(); boolean isUpdate = false; isUpdate =
		 * impsService.updateIfscCode(ifscCodeMaster); if (isUpdate) {
		 * response.setResponseMessage("Details Updated Successfully");
		 * response.setResponseCode("200");
		 * 
		 * } else { response.setResponseMessage("Error While Updating Details");
		 * response.setResponseCode("500"); } return new ResponseEntity<>(response,
		 * HttpStatus.OK); }
		 * 
		 * @RequestMapping(value = "/deleteIfscCode", method = RequestMethod.POST,
		 * consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> deleteIfscCode(@RequestBody
		 * IfscCodeMasterEntity ifscCodeMaster) { ResponseMessageBean response = new
		 * ResponseMessageBean(); boolean isUpdate = false; isUpdate =
		 * impsService.deleteIfscCode(ifscCodeMaster); if (isUpdate) {
		 * response.setResponseMessage("Details Deleted Successfully");
		 * response.setResponseCode("200");
		 * 
		 * } else { response.setResponseMessage("Error While Deleting Details");
		 * response.setResponseCode("500"); } return new ResponseEntity<>(response,
		 * HttpStatus.OK); }
		 * 
		 * @RequestMapping(value = "/getAllDeliveryChannelDetails", method =
		 * RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> getAllDeliveryChannelDetails() {
		 * logger.info("In ImpsServiceController -> getAllDeliveryChannelDetails()");
		 * ResponseMessageBean res = new ResponseMessageBean();
		 * List<DeliveryChannelsEntity> deliveryChannelList =
		 * impsService.getAllDeliveryChannelDetails();
		 * 
		 * if (!ObjectUtils.isEmpty(deliveryChannelList)) { res.setResponseCode("200");
		 * res.setResult(deliveryChannelList); res.setResponseMessage("Success"); } else
		 * { res.setResponseCode("202"); res.setResponseMessage("No Records Found"); }
		 * return new ResponseEntity<>(res, HttpStatus.OK);
		 * 
		 * }
		 * 
		 * @RequestMapping(value = "/getAllDeliveryChannelDetailsById", method =
		 * RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> getAllDeliveryChannelDetailsById(
		 * 
		 * @RequestBody DeliveryChannelsEntity deliveryChannelsReq) {
		 * logger.info("In ImpsServiceController -> getAllDeliveryChannelDetailsById()"
		 * ); ResponseMessageBean res = new ResponseMessageBean();
		 * List<DeliveryChannelsEntity> deliveryChannelList = impsService
		 * .getAllDeliveryChannelDetailsById(deliveryChannelsReq);
		 * 
		 * if (!ObjectUtils.isEmpty(deliveryChannelList)) {
		 * deliveryChannelList.get(0).setId(deliveryChannelsReq.getId());
		 * res.setResponseCode("200"); res.setResult(deliveryChannelList);
		 * res.setResponseMessage("Success"); } else { res.setResponseCode("202");
		 * res.setResponseMessage("No Records Found"); } return new
		 * ResponseEntity<>(res, HttpStatus.OK);
		 * 
		 * }
		 * 
		 * @RequestMapping(value = "/insertDeliveryChannel", method =
		 * RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> insertDeliveryChannel(
		 * 
		 * @RequestBody DeliveryChannelsEntity deliverChannelReq) { ResponseMessageBean
		 * response = new ResponseMessageBean(); boolean isSave = false; isSave =
		 * impsService.insertDeliveryChannel(deliverChannelReq); if (isSave) {
		 * response.setResponseMessage("Details Inserted Successfully");
		 * response.setResponseCode("200");
		 * 
		 * } else { response.setResponseMessage("Error While Saving Details");
		 * response.setResponseCode("500"); } return new ResponseEntity<>(response,
		 * HttpStatus.OK); }
		 * 
		 * @RequestMapping(value = "/updateDeliveryChannel", method =
		 * RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> updateDeliveryChannel(
		 * 
		 * @RequestBody DeliveryChannelsEntity deliverChannelReq) { ResponseMessageBean
		 * response = new ResponseMessageBean(); boolean isUpdate = false; isUpdate =
		 * impsService.updateDeliveryChannel(deliverChannelReq); if (isUpdate) {
		 * response.setResponseMessage("Details Updated Successfully");
		 * response.setResponseCode("200");
		 * 
		 * } else { response.setResponseMessage("Error While Updating Details");
		 * response.setResponseCode("500"); } return new ResponseEntity<>(response,
		 * HttpStatus.OK); }
		 * 
		 * @RequestMapping(value = "/deleteDeliveryChannel", method =
		 * RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> deleteDeliveryChannel(
		 * 
		 * @RequestBody DeliveryChannelsEntity deliverChannelReq) { ResponseMessageBean
		 * response = new ResponseMessageBean(); try { boolean isUpdate = false;
		 * isUpdate = impsService.deleteDeliveryChannel(deliverChannelReq); if
		 * (isUpdate) { response.setResponseMessage("Details Deleted Successfully");
		 * response.setResponseCode("200");
		 * 
		 * } else { response.setResponseMessage("Error While Deleting Details");
		 * response.setResponseCode("500"); } } catch (Exception e) {
		 * logger.info("Exception:", e);
		 * 
		 * } return new ResponseEntity<>(response, HttpStatus.OK); }
		 * 
		 * @RequestMapping(value = "/getAllRevisionDetails", method =
		 * RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> getAllRevisionDetails() {
		 * logger.info("In ImpsServiceController -> getAllRevisionDetails()");
		 * ResponseMessageBean res = new ResponseMessageBean(); try {
		 * List<ImpsRevisionBean> rivisionList = impsService.getAllRevisionDetails();
		 * 
		 * if (!ObjectUtils.isEmpty(rivisionList)) { res.setResponseCode("200");
		 * res.setResult(rivisionList); res.setResponseMessage("Success"); } else {
		 * res.setResponseCode("202"); res.setResponseMessage("No Records Found"); } }
		 * catch (Exception e) { logger.info("Exception:", e);
		 * 
		 * } return new ResponseEntity<>(res, HttpStatus.OK);
		 * 
		 * }
		 * 
		 * @RequestMapping(value = "/getCreditPoolAccDetails", method =
		 * RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> getCreditPoolAccDetails() {
		 * logger.info("In ImpsServiceController -> getCreditPoolAccDetails()");
		 * ResponseMessageBean res = new ResponseMessageBean(); try {
		 * List<CreditPoolAccountConfEntity> creditPollAccList =
		 * impsService.getCreditPoolAccDetails();
		 * 
		 * if (!ObjectUtils.isEmpty(creditPollAccList)) { res.setResponseCode("200");
		 * res.setResult(creditPollAccList); res.setResponseMessage("Success"); } else {
		 * res.setResponseCode("202"); res.setResponseMessage("No Records Found"); } }
		 * catch (Exception e) { logger.info("Exception:", e);
		 * 
		 * } return new ResponseEntity<>(res, HttpStatus.OK);
		 * 
		 * }
		 * 
		 * @RequestMapping(value = "/getCreditPoolAccDetailsById", method =
		 * RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> getCreditPoolAccDetails(
		 * 
		 * @RequestBody CreditPoolAccountConfEntity creditPoolAccReq) {
		 * logger.info("In ImpsServiceController -> getCreditPoolAccDetailsById()");
		 * ResponseMessageBean res = new ResponseMessageBean(); List<String> generalRec
		 * = new ArrayList<>(); List<ConfigMasterEntity> configData =
		 * masterConfigDao.getConfigByConfigKey("CORP_PORTAL_URL_LINK");
		 * 
		 * File file = null; Map<String, String> map = new HashMap<String, String>();
		 * List<Map<String, String>> record = new ArrayList<Map<String, String>>();
		 * map.put("User Name", "shubham.lokhande");
		 * 
		 * String otp = RandomNumberGenerator.generateActivationCode(); String encPwd =
		 * ("PAN123").concat(otp); System.out.println("Password:" + encPwd);
		 * map.put("User Name", "sive.harris"); map.put("Password", "AXPL795ki");
		 * 
		 * generalRec.add("Infrasoft Tech Pvt Ltd"); generalRec.add("Maker");
		 * generalRec.add(configData.get(0).getDescription());
		 * 
		 * record.add(map);
		 * 
		 * file = PdfGenerator.generatePDF("UserCredentials.pdf",
		 * "PSB: User Credentials", record, encPwd, encPwd, generalRec, pdfFilePath);
		 * 
		 * List<String> toEmail=new ArrayList<>(); List<String> ccEmail=new
		 * ArrayList<>(); List<String> bccEmail=new ArrayList<>(); List<File> files=new
		 * ArrayList<>(); toEmail.add("shubham.lokhande@infrasofttech.com");
		 * files.add(file); Date subjectDate = new Date(); String
		 * subject="PSB: Notification " + subjectDate;
		 * if(emailUtil.sendEmailWithAttachment(toEmail, ccEmail, bccEmail, files,
		 * "Note: Please enter your pancard number and otp(sent on your mobile number) to view login credentials."
		 * , subject)) { file.delete(); } // if
		 * (emailUtil.sendEmailWithAttachment("shubham.lokhande@infrasofttech.com", //
		 * "Note: Please enter your pancard number and otp(sent on your mobile number) to view login credentials."
		 * , // file)) { // file.delete(); // }
		 * emailUtil.sendSMSNotification("7972423552", "Please enter otp" + " " + otp +
		 * " " +
		 * "along with your pancard number as password to view PDF sent on your email");
		 * 
		 * List<CreditPoolAccountConfEntity> creditPollAccList =
		 * impsService.getCreditPoolAccDetailsById(creditPoolAccReq);
		 * 
		 * if (!ObjectUtils.isEmpty(creditPollAccList)) {
		 * 
		 * creditPollAccList.get(0).setId(creditPoolAccReq.getId());
		 * res.setResponseCode("200"); res.setResult(creditPollAccList);
		 * res.setResponseMessage("Success"); } else { res.setResponseCode("202");
		 * res.setResponseMessage("No Records Found"); } return new
		 * ResponseEntity<>(res, HttpStatus.OK);
		 * 
		 * }
		 * 
		 * @RequestMapping(value = "/insertCreditPoolAccDetails", method =
		 * RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> insertCreditPoolAccDetails(
		 * 
		 * @RequestBody CreditPoolAccountConfEntity creditPoolAccReq) {
		 * ResponseMessageBean response = new ResponseMessageBean(); boolean isSave =
		 * false; try { isSave =
		 * impsService.insertCreditPoolAccDetails(creditPoolAccReq); if (isSave) {
		 * response.setResponseMessage("Details Inserted Successfully");
		 * response.setResponseCode("200");
		 * 
		 * } else { response.setResponseMessage("Error While Saving Details");
		 * response.setResponseCode("500"); } } catch (Exception e) {
		 * logger.info("Exception:", e);
		 * 
		 * } return new ResponseEntity<>(response, HttpStatus.OK); }
		 * 
		 * @RequestMapping(value = "/updateCreditPoolAccDetails", method =
		 * RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> updateCreditPoolAccDetails(
		 * 
		 * @RequestBody CreditPoolAccountConfEntity creditPoolAccReq) {
		 * ResponseMessageBean response = new ResponseMessageBean(); boolean isUpdate =
		 * false; try { isUpdate =
		 * impsService.updateCreditPoolAccDetails(creditPoolAccReq); if (isUpdate) {
		 * response.setResponseMessage("Details Updated Successfully");
		 * response.setResponseCode("200");
		 * 
		 * } else { response.setResponseMessage("Error While Updating Details");
		 * response.setResponseCode("500"); } } catch (Exception e) {
		 * logger.info("Exception:", e);
		 * 
		 * } return new ResponseEntity<>(response, HttpStatus.OK); }
		 * 
		 * @RequestMapping(value = "/deleteCreditPoolAccDetails", method =
		 * RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> deleteCreditPoolAccDetails(
		 * 
		 * @RequestBody CreditPoolAccountConfEntity creditPoolAccReq) {
		 * ResponseMessageBean response = new ResponseMessageBean(); boolean isUpdate =
		 * false; try { isUpdate =
		 * impsService.deleteCreditPoolAccDetails(creditPoolAccReq); if (isUpdate) {
		 * response.setResponseMessage("Details Deleted Successfully");
		 * response.setResponseCode("200");
		 * 
		 * } else { response.setResponseMessage("Error While Deleting Details");
		 * response.setResponseCode("500"); } } catch (Exception e) {
		 * logger.info("Exception:", e);
		 * 
		 * } return new ResponseEntity<>(response, HttpStatus.OK); }
		 * 
		 * @RequestMapping(value = "/getDebitPoolAccDetails", method =
		 * RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> getDebitPoolAccDetails() {
		 * logger.info("In ImpsServiceController -> getDebitPoolAccDetails()");
		 * ResponseMessageBean res = new ResponseMessageBean(); try {
		 * List<DebitPoolAccountConfEntity> debitPoolAccList =
		 * impsService.getDebitPoolAccDetails();
		 * 
		 * if (!ObjectUtils.isEmpty(debitPoolAccList)) { res.setResponseCode("200");
		 * res.setResult(debitPoolAccList); res.setResponseMessage("Success"); } else {
		 * res.setResponseCode("202"); res.setResponseMessage("No Records Found"); } }
		 * catch (Exception e) { logger.info("Exception:", e);
		 * 
		 * } return new ResponseEntity<>(res, HttpStatus.OK);
		 * 
		 * }
		 * 
		 * @RequestMapping(value = "/getDebitPoolAccDetailsById", method =
		 * RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> getDebitPoolAccDetailsById(
		 * 
		 * @RequestBody DebitPoolAccountConfEntity debitPoolAccReq) {
		 * logger.info("In ImpsServiceController -> getDebitPoolAccDetailsById()");
		 * ResponseMessageBean res = new ResponseMessageBean(); try {
		 * List<DebitPoolAccountConfEntity> debitPoolAccList =
		 * impsService.getDebitPoolAccDetailsBYId(debitPoolAccReq);
		 * 
		 * if (!ObjectUtils.isEmpty(debitPoolAccList)) {
		 * debitPoolAccList.get(0).setId(debitPoolAccReq.getId());
		 * res.setResponseCode("200"); res.setResult(debitPoolAccList);
		 * res.setResponseMessage("Success"); } else { res.setResponseCode("202");
		 * res.setResponseMessage("No Records Found"); } } catch (Exception e) {
		 * logger.info("Exception:", e);
		 * 
		 * } return new ResponseEntity<>(res, HttpStatus.OK);
		 * 
		 * }
		 * 
		 * @RequestMapping(value = "/insertDebitPoolAccDetails", method =
		 * RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> insertDebitPoolAccDetails(
		 * 
		 * @RequestBody DebitPoolAccountConfEntity DebitPoolAccReq) {
		 * ResponseMessageBean response = new ResponseMessageBean(); boolean isSave =
		 * false; try { isSave = impsService.insertDebitPoolAccDetails(DebitPoolAccReq);
		 * if (isSave) { response.setResponseMessage("Details Inserted Successfully");
		 * response.setResponseCode("200");
		 * 
		 * } else { response.setResponseMessage("Error While Saving Details");
		 * response.setResponseCode("500"); } } catch (Exception e) {
		 * logger.info("Exception:", e);
		 * 
		 * } return new ResponseEntity<>(response, HttpStatus.OK); }
		 * 
		 * @RequestMapping(value = "/updateDebitPoolAccDetails", method =
		 * RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> updateDebitPoolAccDetails(
		 * 
		 * @RequestBody DebitPoolAccountConfEntity DebitPoolAccReq) {
		 * ResponseMessageBean response = new ResponseMessageBean(); boolean isUpdate =
		 * false; try { isUpdate =
		 * impsService.updateDebitPoolAccDetails(DebitPoolAccReq); if (isUpdate) {
		 * response.setResponseMessage("Details Updated Successfully");
		 * response.setResponseCode("200");
		 * 
		 * } else { response.setResponseMessage("Error While Updating Details");
		 * response.setResponseCode("500"); } } catch (Exception e) {
		 * logger.info("Exception:", e);
		 * 
		 * } return new ResponseEntity<>(response, HttpStatus.OK); }
		 * 
		 * @RequestMapping(value = "/deleteDebitPoolAccDetails", method =
		 * RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> deleteDebitPoolAccDetails(
		 * 
		 * @RequestBody DebitPoolAccountConfEntity DebitPoolAccReq) {
		 * ResponseMessageBean response = new ResponseMessageBean(); boolean isUpdate =
		 * false; try { isUpdate =
		 * impsService.deleteDebitPoolAccDetails(DebitPoolAccReq); if (isUpdate) {
		 * response.setResponseMessage("Details Deleted Successfully");
		 * response.setResponseCode("200");
		 * 
		 * } else { response.setResponseMessage("Error While Deleting Details");
		 * response.setResponseCode("500"); } } catch (Exception e) {
		 * logger.info("Exception:", e);
		 * 
		 * } return new ResponseEntity<>(response, HttpStatus.OK); }
		 * 
		 * @RequestMapping(value = "/getAllReports", method = RequestMethod.POST,
		 * consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> getAllReports() {
		 * logger.info("In ImpsServiceController -> getAllReports()");
		 * ResponseMessageBean res = new ResponseMessageBean(); try {
		 * List<ImpsReportEntity> reportList = impsService.getAllReports();
		 * 
		 * if (!ObjectUtils.isEmpty(reportList)) { res.setResponseCode("200");
		 * res.setResult(reportList); res.setResponseMessage("Success"); } else {
		 * res.setResponseCode("202"); res.setResponseMessage("No Records Found"); } }
		 * catch (Exception e) { logger.info("Exception:", e);
		 * 
		 * } return new ResponseEntity<>(res, HttpStatus.OK);
		 * 
		 * }
		 * 
		 * @RequestMapping(value = "/getAllReportsById", method = RequestMethod.POST,
		 * consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> getAllReportsById(@RequestBody
		 * ImpsReportEntity impsReportReq) {
		 * logger.info("In ImpsServiceController -> getAllReportsById()");
		 * ResponseMessageBean res = new ResponseMessageBean(); List<ImpsReportEntity>
		 * reportData = impsService.getAllReportsById(impsReportReq); try { if
		 * (!ObjectUtils.isEmpty(reportData)) { res.setResponseCode("200");
		 * res.setResult(reportData); res.setResponseMessage("Success"); } else {
		 * res.setResponseCode("202"); res.setResponseMessage("No Records Found"); } }
		 * catch (Exception e) { logger.info("Exception:", e);
		 * 
		 * } return new ResponseEntity<>(res, HttpStatus.OK);
		 * 
		 * }
		 * 
		 * @RequestMapping(value = "/insertReportData", method = RequestMethod.POST,
		 * consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> inserReportData(@RequestBody
		 * ImpsReportEntity impsReportReq) { ResponseMessageBean response = new
		 * ResponseMessageBean(); boolean isSave = false; try { isSave =
		 * impsService.insertReportData(impsReportReq); if (isSave) {
		 * response.setResponseMessage("Details Inserted Successfully");
		 * response.setResponseCode("200");
		 * 
		 * } else { response.setResponseMessage("Error While Saving Details");
		 * response.setResponseCode("500"); } } catch (Exception e) {
		 * logger.info("Exception:", e);
		 * 
		 * } return new ResponseEntity<>(response, HttpStatus.OK); }
		 * 
		 * @RequestMapping(value = "/updateReportData", method = RequestMethod.POST,
		 * consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> updateReportData(@RequestBody
		 * ImpsReportEntity impsReportReq) { ResponseMessageBean response = new
		 * ResponseMessageBean(); boolean isUpdate = false; try { isUpdate =
		 * impsService.updateReportData(impsReportReq); if (isUpdate) {
		 * response.setResponseMessage("Details Updated Successfully");
		 * response.setResponseCode("200");
		 * 
		 * } else { response.setResponseMessage("Error While Updating Details");
		 * response.setResponseCode("500"); } } catch (Exception e) {
		 * logger.info("Exception:", e);
		 * 
		 * } return new ResponseEntity<>(response, HttpStatus.OK); }
		 * 
		 * @RequestMapping(value = "/deleteReportData", method = RequestMethod.POST,
		 * consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> deleteReportData(@RequestBody
		 * ImpsReportEntity impsReportReq) { ResponseMessageBean response = new
		 * ResponseMessageBean(); boolean isUpdate = false; try { isUpdate =
		 * impsService.deleteReportData(impsReportReq); if (isUpdate) {
		 * response.setResponseMessage("Details Deleted Successfully");
		 * response.setResponseCode("200");
		 * 
		 * } else { response.setResponseMessage("Error While Deleting Details");
		 * response.setResponseCode("500"); } } catch (Exception e) {
		 * logger.info("Exception:", e);
		 * 
		 * } return new ResponseEntity<>(response, HttpStatus.OK); }
		 * 
		 * @RequestMapping(value = "/getAllTasks", method = RequestMethod.POST, consumes
		 * = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> getAllTasks() {
		 * logger.info("In ImpsServiceController -> getAllTasks()"); ResponseMessageBean
		 * res = new ResponseMessageBean(); try { List<ImpsTaskEntity> taskData =
		 * impsService.getAllTasks();
		 * 
		 * if (!ObjectUtils.isEmpty(taskData)) { res.setResponseCode("200");
		 * res.setResult(taskData); res.setResponseMessage("Success"); } else {
		 * res.setResponseCode("202"); res.setResponseMessage("No Records Found"); } }
		 * catch (Exception e) { logger.info("Exception:", e);
		 * 
		 * } return new ResponseEntity<>(res, HttpStatus.OK);
		 * 
		 * }
		 * 
		 * @RequestMapping(value = "/getAllTasksById", method = RequestMethod.POST,
		 * consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> getAllTasksById(@RequestBody
		 * ImpsTaskEntity taskReq) {
		 * logger.info("In ImpsServiceController -> getAllTasksById()");
		 * ResponseMessageBean res = new ResponseMessageBean(); try {
		 * List<ImpsTaskEntity> taskData = impsService.getAllTasksById(taskReq);
		 * 
		 * if (!ObjectUtils.isEmpty(taskData)) { res.setResponseCode("200");
		 * res.setResult(taskData); res.setResponseMessage("Success"); } else {
		 * res.setResponseCode("202"); res.setResponseMessage("No Records Found"); } }
		 * catch (Exception e) { logger.info("Exception:", e);
		 * 
		 * } return new ResponseEntity<>(res, HttpStatus.OK);
		 * 
		 * }
		 * 
		 * @RequestMapping(value = "/inserTaskData", method = RequestMethod.POST,
		 * consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> inserTaskData(@RequestBody ImpsTaskEntity
		 * taskReq) { ResponseMessageBean response = new ResponseMessageBean(); boolean
		 * isSave = false; try { isSave = impsService.inserTaskData(taskReq); if
		 * (isSave) { response.setResponseMessage("Details Inserted Successfully");
		 * response.setResponseCode("200");
		 * 
		 * } else { response.setResponseMessage("Error While Saving Details");
		 * response.setResponseCode("500"); } } catch (Exception e) {
		 * logger.info("Exception:", e);
		 * 
		 * } return new ResponseEntity<>(response, HttpStatus.OK); }
		 * 
		 * @RequestMapping(value = "/updateTaskData", method = RequestMethod.POST,
		 * consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> updateTaskData(@RequestBody
		 * ImpsTaskEntity taskReq) { ResponseMessageBean response = new
		 * ResponseMessageBean(); boolean isUpdate = false; try { isUpdate =
		 * impsService.updateTaskData(taskReq); if (isUpdate) {
		 * response.setResponseMessage("Details Updated Successfully");
		 * response.setResponseCode("200");
		 * 
		 * } else { response.setResponseMessage("Error While Updating Details");
		 * response.setResponseCode("500"); } } catch (Exception e) {
		 * logger.info("Exception:", e);
		 * 
		 * } return new ResponseEntity<>(response, HttpStatus.OK); }
		 * 
		 * @RequestMapping(value = "/deleteTaskData", method = RequestMethod.POST,
		 * consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> deleteTaskData(@RequestBody
		 * ImpsTaskEntity taskReq) { ResponseMessageBean response = new
		 * ResponseMessageBean(); boolean isUpdate = false; try { isUpdate =
		 * impsService.deleteTaskData(taskReq); if (isUpdate) {
		 * response.setResponseMessage("Details Deleted Successfully");
		 * response.setResponseCode("200");
		 * 
		 * } else { response.setResponseMessage("Error While Deleting Details");
		 * response.setResponseCode("500"); } } catch (Exception e) {
		 * logger.info("Exception:", e);
		 * 
		 * } return new ResponseEntity<>(response, HttpStatus.OK); }
		 * 
		 * @RequestMapping(value = "/getScheduleData", method = RequestMethod.POST,
		 * consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> getScheduleData() {
		 * logger.info("In ImpsServiceController -> getScheduleData()");
		 * ResponseMessageBean res = new ResponseMessageBean(); try {
		 * List<ScheduleEntity> scheduleList = impsService.getScheduleData();
		 * 
		 * if (!ObjectUtils.isEmpty(scheduleList)) { res.setResponseCode("200");
		 * res.setResult(scheduleList); res.setResponseMessage("Success"); } else {
		 * res.setResponseCode("202"); res.setResponseMessage("No Records Found"); } }
		 * catch (Exception e) { logger.info("Exception:", e);
		 * 
		 * } return new ResponseEntity<>(res, HttpStatus.OK);
		 * 
		 * }
		 * 
		 * @RequestMapping(value = "/getScheduleDataById", method = RequestMethod.POST,
		 * consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> getScheduleDataById(@RequestBody
		 * ScheduleEntity scheduleReq) {
		 * logger.info("In ImpsServiceController -> getScheduleDataById()");
		 * ResponseMessageBean res = new ResponseMessageBean(); try {
		 * List<ScheduleEntity> scheduleList =
		 * impsService.getScheduleDataById(scheduleReq);
		 * 
		 * if (!ObjectUtils.isEmpty(scheduleList)) { res.setResponseCode("200");
		 * res.setResult(scheduleList); res.setResponseMessage("Success"); } else {
		 * res.setResponseCode("202"); res.setResponseMessage("No Records Found"); } }
		 * catch (Exception e) { logger.info("Exception:", e);
		 * 
		 * } return new ResponseEntity<>(res, HttpStatus.OK);
		 * 
		 * }
		 * 
		 * @RequestMapping(value = "/insertScheduleData", method = RequestMethod.POST,
		 * consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> insertScheduleData(@RequestBody
		 * ScheduleEntity scheduleReq) { ResponseMessageBean response = new
		 * ResponseMessageBean(); boolean isSave = false; try { isSave =
		 * impsService.insertScheduleData(scheduleReq); if (isSave) {
		 * response.setResponseMessage("Details Inserted Successfully");
		 * response.setResponseCode("200");
		 * 
		 * } else { response.setResponseMessage("Error While Saving Details");
		 * response.setResponseCode("500"); } } catch (Exception e) {
		 * logger.info("Exception:", e);
		 * 
		 * } return new ResponseEntity<>(response, HttpStatus.OK); }
		 * 
		 * @RequestMapping(value = "/updateScheduleData", method = RequestMethod.POST,
		 * consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> updateScheduleData(@RequestBody
		 * ScheduleEntity scheduleReq) { ResponseMessageBean response = new
		 * ResponseMessageBean(); boolean isUpdate = false; try { isUpdate =
		 * impsService.updateScheduleData(scheduleReq); if (isUpdate) {
		 * response.setResponseMessage("Details Updated Successfully");
		 * response.setResponseCode("200");
		 * 
		 * } else { response.setResponseMessage("Error While Updating Details");
		 * response.setResponseCode("500"); } } catch (Exception e) {
		 * logger.info("Exception:", e);
		 * 
		 * } return new ResponseEntity<>(response, HttpStatus.OK); }
		 * 
		 * @RequestMapping(value = "/deleteScheduleData", method = RequestMethod.POST,
		 * consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> deleteScheduleData(@RequestBody
		 * ScheduleEntity scheduleReq) { ResponseMessageBean response = new
		 * ResponseMessageBean(); boolean isUpdate = false; try { isUpdate =
		 * impsService.deleteScheduleData(scheduleReq); if (isUpdate) {
		 * response.setResponseMessage("Details Deleted Successfully");
		 * response.setResponseCode("200");
		 * 
		 * } else { response.setResponseMessage("Error While Deleting Details");
		 * response.setResponseCode("500"); } } catch (Exception e) {
		 * logger.info("Exception:", e);
		 * 
		 * } return new ResponseEntity<>(response, HttpStatus.OK); }
		 * 
		 * @RequestMapping(value = "/getReportCategory", method = RequestMethod.POST,
		 * consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> getReportCategory() {
		 * logger.info("In ImpsServiceController -> getReportCategory()");
		 * ResponseMessageBean res = new ResponseMessageBean(); try {
		 * List<ReportCatrgoryEntity> scheduleList = impsService.getReportCategory();
		 * 
		 * if (!ObjectUtils.isEmpty(scheduleList)) { res.setResponseCode("200");
		 * res.setResult(scheduleList); res.setResponseMessage("Success"); } else {
		 * res.setResponseCode("202"); res.setResponseMessage("No Records Found"); } }
		 * catch (Exception e) { logger.info("Exception:", e);
		 * 
		 * } return new ResponseEntity<>(res, HttpStatus.OK);
		 * 
		 * }
		 * 
		 * @RequestMapping(value = "/getReportCategoryById", method =
		 * RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> getReportCategoryById(@RequestBody
		 * ReportCatrgoryEntity reportCatReq) {
		 * logger.info("In ImpsServiceController -> getReportCategoryById()");
		 * ResponseMessageBean res = new ResponseMessageBean(); try {
		 * List<ReportCatrgoryEntity> scheduleList =
		 * impsService.getReportCategoryById(reportCatReq);
		 * 
		 * if (!ObjectUtils.isEmpty(scheduleList)) { res.setResponseCode("200");
		 * res.setResult(scheduleList); res.setResponseMessage("Success"); } else {
		 * res.setResponseCode("202"); res.setResponseMessage("No Records Found"); } }
		 * catch (Exception e) { logger.info("Exception:", e);
		 * 
		 * } return new ResponseEntity<>(res, HttpStatus.OK);
		 * 
		 * }
		 * 
		 * @RequestMapping(value = "/insertReportCategory", method = RequestMethod.POST,
		 * consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> insertReportCategory(@RequestBody
		 * ReportCatrgoryEntity reportCatReq) { ResponseMessageBean response = new
		 * ResponseMessageBean(); boolean isSave = false; try { isSave =
		 * impsService.insertReportCategory(reportCatReq); if (isSave) {
		 * response.setResponseMessage("Details Inserted Successfully");
		 * response.setResponseCode("200");
		 * 
		 * } else { response.setResponseMessage("Error While Saving Details");
		 * response.setResponseCode("500"); } } catch (Exception e) {
		 * logger.info("Exception:", e);
		 * 
		 * } return new ResponseEntity<>(response, HttpStatus.OK); }
		 * 
		 * @RequestMapping(value = "/updateReportCategory", method = RequestMethod.POST,
		 * consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> updateReportCategory(@RequestBody
		 * ReportCatrgoryEntity reportCatReq) { ResponseMessageBean response = new
		 * ResponseMessageBean(); boolean isUpdate = false; try { isUpdate =
		 * impsService.updateReportCategory(reportCatReq); if (isUpdate) {
		 * response.setResponseMessage("Details Updated Successfully");
		 * response.setResponseCode("200");
		 * 
		 * } else { response.setResponseMessage("Error While Updating Details");
		 * response.setResponseCode("500"); } } catch (Exception e) {
		 * logger.info("Exception:", e);
		 * 
		 * } return new ResponseEntity<>(response, HttpStatus.OK); }
		 * 
		 * @RequestMapping(value = "/deleteReportCategory", method = RequestMethod.POST,
		 * consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> deleteReportCategory(@RequestBody
		 * ReportCatrgoryEntity reportCatReq) { ResponseMessageBean response = new
		 * ResponseMessageBean(); boolean isUpdate = false; try { isUpdate =
		 * impsService.deleteReportCategory(reportCatReq); if (isUpdate) {
		 * response.setResponseMessage("Details Deleted Successfully");
		 * response.setResponseCode("200");
		 * 
		 * } else { response.setResponseMessage("Error While Deleting Details");
		 * response.setResponseCode("500"); } } catch (Exception e) {
		 * logger.info("Exception:", e);
		 * 
		 * } return new ResponseEntity<>(response, HttpStatus.OK); }
		 * 
		 * @RequestMapping(value = "/getImpsTransLogs", method = RequestMethod.POST,
		 * consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> getImpsTransLogs(@RequestBody DateBean
		 * dateObj) { logger.info("In ImpsServiceController -> getImpsTransLogs()");
		 * ResponseMessageBean res = new ResponseMessageBean(); // List<TransLogBean>
		 * impsData = impsService.getTransLogs(dateObj); try { ResponseMessageBean respm
		 * = impsServiceDao.getImpsTransLog(dateObj);
		 * 
		 * if (!ObjectUtils.isEmpty(respm)) { res.setResponseCode("200");
		 * res.setResult(respm); res.setResponseMessage("Success"); } else {
		 * res.setResponseCode("202"); res.setResponseMessage("No Records Found"); } }
		 * catch (Exception e) { logger.info("Exception:", e);
		 * 
		 * } return new ResponseEntity<>(res, HttpStatus.OK);
		 * 
		 * }
		 * 
		 * @RequestMapping(value = "/getImpsTransLogByRRN", method = RequestMethod.POST,
		 * consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> getImpsTransLogByRRN(@RequestBody
		 * TransLogBean transLogData) {
		 * logger.info("In ImpsServiceController -> getImpsTransLogByRRN()");
		 * ResponseMessageBean res = new ResponseMessageBean(); try {
		 * ResponseMessageBean respm =
		 * impsServiceDao.getImpsTransLogByRRN(transLogData);
		 * 
		 * if (!ObjectUtils.isEmpty(respm)) { res.setResponseCode("200");
		 * res.setResult(respm); res.setResponseMessage("Success"); } else {
		 * res.setResponseCode("202"); res.setResponseMessage("No Records Found"); } }
		 * catch (Exception e) { logger.info("Exception:", e);
		 * 
		 * } return new ResponseEntity<>(res, HttpStatus.OK);
		 * 
		 * }
		 * 
		 * @RequestMapping(value = "/getImpsCustDetails", method = RequestMethod.POST,
		 * consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> getImpsCustDetails(@RequestBody
		 * ImpsCustBean impsCustReq) {
		 * logger.info("In ImpsServiceController -> getImpsTransLogs()");
		 * ResponseMessageBean res = new ResponseMessageBean(); try {
		 * ResponseMessageBean respm = impsServiceDao.getImpsCustDetails(impsCustReq);
		 * 
		 * if (!ObjectUtils.isEmpty(respm)) { res.setResponseCode("200");
		 * res.setResult(respm); res.setResponseMessage("Success"); } else {
		 * res.setResponseCode("202"); res.setResponseMessage("No Records Found"); } }
		 * catch (Exception e) { logger.info("Exception:", e);
		 * 
		 * } return new ResponseEntity<>(res, HttpStatus.OK);
		 * 
		 * }
		 * 
		 * @RequestMapping(value = "/getOtpLogsDetails", method = RequestMethod.POST,
		 * consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> getOtpLogsDetails(@RequestBody ImpsOtpLog
		 * otpLogReq) { logger.info("In ImpsServiceController -> getOtpLogsDetails()");
		 * ResponseMessageBean res = new ResponseMessageBean(); try { List<ImpsOtpLog>
		 * respm = impsServiceDao.getOtpLogsDetails(otpLogReq);
		 * 
		 * if (!ObjectUtils.isEmpty(respm)) { res.setResponseCode("200");
		 * res.setResult(respm); res.setResponseMessage("Success"); } else {
		 * res.setResponseCode("202"); res.setResponseMessage("No Records Found"); } }
		 * catch (Exception e) { logger.info("Exception:", e);
		 * 
		 * } return new ResponseEntity<>(res, HttpStatus.OK);
		 * 
		 * }
		 * 
		 * @RequestMapping(value = "/insertImpsMasterDetails", method =
		 * RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> insertReportCategory(@RequestBody
		 * ImpsMasterEntity impsReqData) { ResponseMessageBean response = new
		 * ResponseMessageBean(); boolean isSave = false; try { isSave =
		 * omniChannelDao.insertImpsMasterDetails(impsReqData); if (isSave) {
		 * response.setResponseMessage("Details Inserted Successfully");
		 * response.setResponseCode("200");
		 * 
		 * } else { response.setResponseMessage("Error While Saving Details");
		 * response.setResponseCode("500"); } } catch (Exception e) {
		 * logger.info("Exception:", e);
		 * 
		 * } return new ResponseEntity<>(response, HttpStatus.OK); }
		 * 
		 * @RequestMapping(value = "/updateImpsMasterDetails", method =
		 * RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> updateReportCategory(@RequestBody
		 * ImpsMasterEntity impsReqData) { ResponseMessageBean response = new
		 * ResponseMessageBean(); boolean isUpdate = false; try { isUpdate =
		 * omniChannelDao.updateImpsMasterDetails(impsReqData); if (isUpdate) {
		 * response.setResponseMessage("Details Updated Successfully");
		 * response.setResponseCode("200");
		 * 
		 * } else { response.setResponseMessage("Error While Updating Details");
		 * response.setResponseCode("500"); } } catch (Exception e) {
		 * logger.info("Exception:", e);
		 * 
		 * } return new ResponseEntity<>(response, HttpStatus.OK); }
		 * 
		 * @RequestMapping(value = "/getImpsMasterDetails", method = RequestMethod.POST,
		 * consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> getImpsMasterDetails() {
		 * logger.info("In ImpsServiceController -> getOtpLogsDetails()");
		 * ResponseMessageBean res = new ResponseMessageBean(); try {
		 * List<ImpsMasterEntity> list = omniChannelDao.getImpsMasterDetails();
		 * 
		 * if (!ObjectUtils.isEmpty(list)) { res.setResponseCode("200");
		 * res.setResult(list); res.setResponseMessage("Success"); } else {
		 * res.setResponseCode("202"); res.setResponseMessage("No Records Found"); } }
		 * catch (Exception e) { logger.info("Exception:", e);
		 * 
		 * } return new ResponseEntity<>(res, HttpStatus.OK);
		 * 
		 * }
		 * 
		 * @RequestMapping(value = "/getImpsMasterDetailsById", method =
		 * RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> getOtpLogsDetails(@RequestBody
		 * ImpsMasterEntity impsReqData) {
		 * logger.info("In ImpsServiceController -> getImpsMasterDetailsById()");
		 * ResponseMessageBean res = new ResponseMessageBean(); try {
		 * List<ImpsMasterEntity> list =
		 * omniChannelDao.getImpsMasterDetailsById(impsReqData);
		 * 
		 * if (!ObjectUtils.isEmpty(list)) { res.setResponseCode("200");
		 * res.setResult(list); res.setResponseMessage("Success"); } else {
		 * res.setResponseCode("202"); res.setResponseMessage("No Records Found"); } }
		 * catch (Exception e) { logger.info("Exception:", e);
		 * 
		 * } return new ResponseEntity<>(res, HttpStatus.OK);
		 * 
		 * }
		 * 
		 * @RequestMapping(value = "/getImpsMasterState", method = RequestMethod.POST,
		 * consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> getImpsMasterState() {
		 * logger.info("In ImpsServiceController -> getImpsMasterState()");
		 * ResponseMessageBean res = new ResponseMessageBean(); try {
		 * List<ImpsMasterEntity> list = omniChannelDao.getImpsMasterState();
		 * 
		 * if (!ObjectUtils.isEmpty(list)) { res.setResponseCode("200");
		 * res.setResult(list); res.setResponseMessage("Success"); } else {
		 * res.setResponseCode("202"); res.setResponseMessage("No Records Found"); } }
		 * catch (Exception e) { logger.info("Exception:", e);
		 * 
		 * } return new ResponseEntity<>(res, HttpStatus.OK);
		 * 
		 * }
		 * 
		 * @RequestMapping(value = "/getImpsMasterDistrictByState", method =
		 * RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> getImpsMasterDistrictByState(@RequestBody
		 * ImpsMasterEntity impsReqData) {
		 * logger.info("In ImpsServiceController -> getImpsMasterDistrictByState()");
		 * ResponseMessageBean res = new ResponseMessageBean(); try {
		 * List<ImpsMasterEntity> list =
		 * omniChannelDao.getImpsMasterDistrictByState(impsReqData);
		 * 
		 * if (!ObjectUtils.isEmpty(list)) { res.setResponseCode("200");
		 * res.setResult(list); res.setResponseMessage("Success"); } else {
		 * res.setResponseCode("202"); res.setResponseMessage("No Records Found"); } }
		 * catch (Exception e) { logger.info("Exception:", e);
		 * 
		 * } return new ResponseEntity<>(res, HttpStatus.OK);
		 * 
		 * }
		 * 
		 * @RequestMapping(value = "/getImpsMasterCityByDistrict", method =
		 * RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> getImpsMasterCityByDistrict(@RequestBody
		 * ImpsMasterEntity impsReqData) {
		 * logger.info("In ImpsServiceController -> getImpsMasterCityByDistrict()");
		 * ResponseMessageBean res = new ResponseMessageBean(); try {
		 * List<ImpsMasterEntity> list =
		 * omniChannelDao.getImpsMasterCityByDistrict(impsReqData);
		 * 
		 * if (!ObjectUtils.isEmpty(list)) { res.setResponseCode("200");
		 * res.setResult(list); res.setResponseMessage("Success"); } else {
		 * res.setResponseCode("202"); res.setResponseMessage("No Records Found"); } }
		 * catch (Exception e) { logger.info("Exception:", e);
		 * 
		 * } return new ResponseEntity<>(res, HttpStatus.OK);
		 * 
		 * }
		 * 
		 * @RequestMapping(value = "/getImpsMasterDataByCity", method =
		 * RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> getImpsMasterDataByCity(@RequestBody
		 * ImpsMasterEntity impsReqData) {
		 * logger.info("In ImpsServiceController -> getImpsMasterDataByCity()");
		 * ResponseMessageBean res = new ResponseMessageBean(); try {
		 * List<ImpsMasterEntity> list =
		 * omniChannelDao.getImpsMasterDataByCity(impsReqData);
		 * 
		 * if (!ObjectUtils.isEmpty(list)) { res.setResponseCode("200");
		 * res.setResult(list); res.setResponseMessage("Success"); } else {
		 * res.setResponseCode("202"); res.setResponseMessage("No Records Found"); } }
		 * catch (Exception e) { logger.info("Exception:", e);
		 * 
		 * } return new ResponseEntity<>(res, HttpStatus.OK);
		 * 
		 * }
		 * 
		 * @RequestMapping(value = "/getImpsMasterDataByIFSC", method =
		 * RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE) public
		 * ResponseEntity<ResponseMessageBean> getImpsMasterDataByIfsc(@RequestBody
		 * ImpsMasterEntity impsReqData) {
		 * logger.info("In ImpsServiceController -> getImpsMasterDataByIFSC()");
		 * ResponseMessageBean res = new ResponseMessageBean(); try {
		 * List<ImpsMasterEntity> list =
		 * omniChannelDao.getImpsMasterDataByIFSC(impsReqData);
		 * 
		 * if (!ObjectUtils.isEmpty(list)) { res.setResponseCode("200");
		 * res.setResult(list); res.setResponseMessage("Success"); } else {
		 * res.setResponseCode("202"); res.setResponseMessage("No Records Found"); } }
		 * catch (Exception e) { logger.info("Exception:", e);
		 * 
		 * } return new ResponseEntity<>(res, HttpStatus.OK);
		 * 
		 * }
		 * 
		 * }
		 */