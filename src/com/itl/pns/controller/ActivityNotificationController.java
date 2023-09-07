package com.itl.pns.controller;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.itl.pns.bean.ActivityMasterBean;
import com.itl.pns.bean.ActivityNotificationBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.entity.ActivityNotificationMasterEntity;
import com.itl.pns.entity.KycFolderEntity;
import com.itl.pns.entity.MenuSubmenuEntity;
import com.itl.pns.service.ActivityNotificationService;
import com.itl.pns.util.PSBCommonUtility;
import com.itl.pns.util.RandomNumberGenerator;

@RestController
@RequestMapping("activityNotification")
public class ActivityNotificationController {

	private static final Logger logger = LogManager.getLogger(ActivityNotificationController.class);

	@Autowired
	ActivityNotificationService actiNotiService;

	@RequestMapping(value = "/getAllActivityNotifications", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getAllActivityNotifications() {
		logger.info("in activityNotification -> getAllActivityNotifications()");
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			List<ActivityNotificationBean> list = new ArrayList<>();

			List<Object[]> actiNotiList = actiNotiService.findAllData();
			for (Object[] result : actiNotiList) {
				ActivityNotificationBean obj = new ActivityNotificationBean();
				obj.setID(PSBCommonUtility.checkIsNotNull(result[0]) ? result[0].toString() : null);
				obj.setACTIVITYCODE(PSBCommonUtility.checkIsNotNull(result[1]) ? result[1].toString() : null);
				obj.setDISPLAYNAME(PSBCommonUtility.checkIsNotNull(result[2]) ? result[2].toString() : null);
				obj.setACTIVITYID(PSBCommonUtility.checkIsNotNull(result[3]) ? result[3].toString() : null);
				obj.setSMS(PSBCommonUtility.checkIsNotNull(result[4]) ? result[4].toString() : "N");
				obj.setEMAIL(PSBCommonUtility.checkIsNotNull(result[5]) ? result[5].toString() : "N");
				obj.setPUSH(PSBCommonUtility.checkIsNotNull(result[6]) ? result[6].toString() : "N");
				obj.setACTINOTIID(PSBCommonUtility.checkIsNotNull(result[7]) ? result[7].toString() : null);
				obj.setCREATEDON(
						Timestamp.valueOf(PSBCommonUtility.checkIsNotNull(result[8]) ? result[8].toString() : null));
				list.add(obj);

			}

			if (!ObjectUtils.isEmpty(list)) {
				response.setResponseCode("200");
				response.setResponseMessage("Success");
				response.setResult(list);
			} else {
				response.setResponseCode("202");
				response.setResponseMessage("No Records Found");
			}

		} catch (Exception e) {
			logger.info("Exception:", e);
			response.setResponseCode("500");
			response.setResponseMessage("Error While  Adding Records!");
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/saveActivityNotification", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> saveActivityNotification(
			@RequestBody List<ActivityNotificationMasterEntity> actiNotiData) {
		logger.info("in activityNotification -> saveActivityNotification()");
		ResponseMessageBean response = new ResponseMessageBean();
		boolean isSaved = false;

		try {

			isSaved = actiNotiService.saveActivityNotification(actiNotiData);
			if (isSaved) {
				response.setResponseCode("200");
				response.setResponseMessage("Data Updated Successfully");

			} else {
				response.setResponseCode("202");
				response.setResponseMessage("Error While  Updated Records!");
			}

		} catch (Exception e) {
			logger.info("Exception:", e);
			response.setResponseCode("500");
			response.setResponseMessage("Error While Updated Records!");
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/getAllActivityNotificationsById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getAllActivityNotificationsById(
			@RequestBody ActivityNotificationMasterEntity actiNotiData) {
		logger.info("in activityNotification -> getAllActivityNotifications()");
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			List<ActivityNotificationBean> list = new ArrayList<>();

			List<Object[]> actiNotiList = actiNotiService.getAllActivityNotificationsById(actiNotiData);

			for (Object[] result : actiNotiList) {
				ActivityNotificationBean obj = new ActivityNotificationBean();
				obj.setID(PSBCommonUtility.checkIsNotNull(result[0]) ? result[0].toString() : null);
				obj.setACTIVITYCODE(PSBCommonUtility.checkIsNotNull(result[1]) ? result[1].toString() : null);
				obj.setDISPLAYNAME(PSBCommonUtility.checkIsNotNull(result[2]) ? result[2].toString() : null);
				obj.setACTIVITYID(PSBCommonUtility.checkIsNotNull(result[3]) ? result[3].toString() : null);
				obj.setSMS(PSBCommonUtility.checkIsNotNull(result[4]) ? result[4].toString() : "N");
				obj.setEMAIL(PSBCommonUtility.checkIsNotNull(result[5]) ? result[5].toString() : "N");
				obj.setPUSH(PSBCommonUtility.checkIsNotNull(result[6]) ? result[6].toString() : "N");
				obj.setCREATEDON(
						Timestamp.valueOf(PSBCommonUtility.checkIsNotNull(result[7]) ? result[7].toString() : null));

				list.add(obj);

			}
			if (!ObjectUtils.isEmpty(list)) {
				response.setResponseCode("200");
				response.setResponseMessage("Success");
				response.setResult(list);
			} else {
				response.setResponseCode("202");
				response.setResponseMessage("No Records Found");
			}

		} catch (Exception e) {
			logger.info("Exception:", e);
			response.setResponseCode("500");
			response.setResponseMessage("Error While  Adding Records!");
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
