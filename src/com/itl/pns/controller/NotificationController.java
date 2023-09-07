package com.itl.pns.controller;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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

import com.itl.pns.bean.RegistrationDetailsBean;
import com.itl.pns.bean.RequestParamBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.entity.MessageMasterEntity;
import com.itl.pns.entity.NotificationMaster;
import com.itl.pns.entity.NotificationTemplateMaster;
import com.itl.pns.entity.NotificationsEntity;
import com.itl.pns.repository.MessageMasterRepository;
import com.itl.pns.service.MessageMasterService;
import com.itl.pns.service.NotificationService;
import com.itl.pns.util.AdminWorkFlowReqUtility;
import com.itl.pns.util.EncryptorDecryptor;
import com.itl.pns.util.Utils;

/**
 * @author shubham.lokhande
 *
 */
@RestController
@RequestMapping("notification")
public class NotificationController {

	private static final Logger logger = LogManager.getLogger(NotificationController.class);

	@Autowired
	NotificationService notificationService;

	@Autowired
	AdminWorkFlowReqUtility adminWorkFlowReqUtility;

	@Autowired
	private MessageMasterService messageMasterService;

	@Autowired
	private MessageMasterRepository messageMasterRepository;

	@RequestMapping(value = "/getNotificationMasterList", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getNotificationMasterList() {
		logger.info("In Notification Controller ->getNotificationMasterList()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<NotificationMaster> notificationMasterList = notificationService.getNotificationMasterList();

			if (!ObjectUtils.isEmpty(notificationMasterList)) {
				res.setResponseCode("200");
				res.setResult(notificationMasterList);
				res.setResponseMessage("SUCCESS");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("NO RECORDS FOUND");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/getNotificationMasterById/{id}", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getNotificationMasterById(@RequestBody RequestParamBean requestBean) {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<NotificationMaster> notificationMasterList = notificationService
					.getNotificationMasterById(Integer.parseInt(requestBean.getId1()));

			if (!ObjectUtils.isEmpty(notificationMasterList)) {
				res.setResponseCode("200");
				res.setResult(notificationMasterList);
				res.setResponseMessage("SUCCESS");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("NO RECORDS FOUND");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/getNotificationTempData", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getNotificationTempData() {
		logger.info("In Notification Controller ->getNotificationTempData()");
		ResponseMessageBean res = new ResponseMessageBean();

		try {
			List<NotificationTemplateMaster> notificationTempData = notificationService.getNotificationTempData();

			if (!ObjectUtils.isEmpty(notificationTempData)) {
				res.setResponseCode("200");
				res.setResult(notificationTempData);
				res.setResponseMessage("Success");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/getNotificationTempDataById", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getNotificationTempDataById(@RequestBody RequestParamBean requestBean) {
		logger.info("In Notification Controller ->getNotificationTempDataById()");
		ResponseMessageBean res = new ResponseMessageBean();

		try {
			List<NotificationTemplateMaster> notificationTempData = notificationService
					.getNotificationTempDataById(new BigInteger(requestBean.getId1()));

			if (!ObjectUtils.isEmpty(notificationTempData)) {
				res.setResponseCode("200");
				res.setResult(notificationTempData);
				res.setResponseMessage("Success");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/saveNotificationTempData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> saveNotificationTempData(
			@RequestBody NotificationTemplateMaster notificationTempData) {
		logger.info("In Notification Controller ->saveNotificationTempData()");
		ResponseMessageBean res = new ResponseMessageBean();
		ResponseMessageBean responsecode = new ResponseMessageBean();
		boolean isDataRefresh = false;
		try {
			if (!ObjectUtils.isEmpty(notificationTempData)) {
				responsecode = notificationService.chechNotificatioExit(notificationTempData);
				if (responsecode.getResponseCode().equalsIgnoreCase("200")) {
					notificationService.saveNotificationTempData(notificationTempData);

					isDataRefresh = adminWorkFlowReqUtility.refreshCacheData("NotificationTemplateMasterReader");
					res.setResponseCode("200");
					res.setResponseMessage("Notification Template Has Been Saved Successfully");
				} else {
					res.setResponseCode(responsecode.getResponseCode());
					res.setResponseMessage(responsecode.getResponseMessage());
				}
			}
		} catch (Exception e) {
			logger.error(e);
			res.setResponseCode("500");
			res.setResponseMessage("Error Occured While Saving The Record");
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateNotificationTempData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateNotificationTempData(
			@RequestBody NotificationTemplateMaster notificationTempData) {
		logger.info("In Notification Controller ->updateNotificationTempData()");
		ResponseMessageBean res = new ResponseMessageBean();
		boolean isUpdated = false;
		boolean isDataRefresh = false;
		try {
			isUpdated = notificationService.updateNotificationTempData(notificationTempData);

			isDataRefresh = adminWorkFlowReqUtility.refreshCacheData("NotificationTemplateMasterReader");
			res.setResponseCode("200");
			res.setResponseMessage("Notification Template  Has Been Updated Successfully");
		} catch (Exception e) {
			logger.info("Exception:", e);
			res.setResponseCode("500");
			res.setResponseMessage("Error Occured While Updating The Record");
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/deletetNotificationTemp", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> deletetNotificationTemp(@RequestBody RequestParamBean requestBean) {
		logger.info("In Notification Controller ->deletetNotificationTemp()");
		ResponseMessageBean bean = new ResponseMessageBean();
		try {
			Boolean response = notificationService.deletetNotificationTemp(new BigInteger(requestBean.getId1()));
			if (response) {
				bean.setResponseCode("200");
				bean.setResponseMessage("Deleted Successfully");
			} else {
				bean.setResponseCode("202");
				bean.setResponseMessage("Error Occured While Deleting The Record");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(bean, HttpStatus.OK);
	}

	@RequestMapping(value = "/getCustDetails", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getCustDetails(@RequestBody RegistrationDetailsBean detailsBean) {
		logger.info("In Notification Controller ->deletetNotificationTemp()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<RegistrationDetailsBean> registrationList = new ArrayList<RegistrationDetailsBean>();
			registrationList = notificationService.getCustDetails(detailsBean);

			if (!ObjectUtils.isEmpty(registrationList)) {
				res.setResponseCode("200");
				for (RegistrationDetailsBean record : registrationList) {

					if (record.getDOB() != null && record.getDOB().contains("=")) {
						record.setDOB(EncryptorDecryptor.decryptData(record.getDOB()));
					}

					if (record.getCUSTOMERNAME() != null && record.getCUSTOMERNAME().contains("=")) {
						record.setCUSTOMERNAME(EncryptorDecryptor.decryptData(record.getCUSTOMERNAME()));

					}
					if (record.getMOBILE() != null && record.getMOBILE().contains("=")) {
						record.setMOBILE(EncryptorDecryptor.decryptData(record.getMOBILE()));
					}
					if (record.getEMAIL() != null && record.getEMAIL().contains("=")) {
						record.setEMAIL(EncryptorDecryptor.decryptData(record.getEMAIL()));
					}
					if (record.getUSERNAME() != null && record.getUSERNAME().contains("=")) {
						record.setUSERNAME(EncryptorDecryptor.decryptData(record.getUSERNAME()));
					}
					if (record.getCIF() != null && record.getCIF().contains("=")) {
						record.setCIF(EncryptorDecryptor.decryptData(record.getCIF()));
					}
				}
				res.setResult(registrationList);
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/getNotificationList", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getNotificationList(@RequestBody NotificationsEntity notification) {
		logger.info("In Notification Controller ->getNotificationList()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<NotificationsEntity> notificationList = notificationService
					.getNotificationList(notification.getAppId(), notification.getFromDate(), notification.getToDate());

			if (!ObjectUtils.isEmpty(notificationList)) {
				res.setResponseCode("200");
				for (NotificationsEntity record : notificationList) {
					if (record.getMobile() != null && record.getMobile().contains("=")) {
						record.setMobile(EncryptorDecryptor.decryptData(record.getMobile()));
					}
					if (record.getEmail() != null && record.getEmail().contains("=")) {
						record.setEmail(EncryptorDecryptor.decryptData(record.getEmail()));
					}
					if (record.getEmail() != null && record.getEmail().contains("=")) {
						record.setEmail(EncryptorDecryptor.decryptData(record.getEmail()));
					}
					if (record.getCustomerName() != null && record.getCustomerName().contains("=")) {
						record.setCustomerName(EncryptorDecryptor.decryptData(record.getCustomerName()));
					}
				}
				res.setResult(notificationList);
				res.setResponseMessage("Success");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/sendNotificationTocust", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> sendNotificationTocust(@RequestBody NotificationsEntity notification) {
		logger.info("In Notification Controller ->sendNotificationTocust()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			boolean sentSuccess = notificationService.sendNotificationTocust(notification);

			if (sentSuccess) {
				res.setResponseCode("200");
				res.setResponseMessage("Notification Resend Successfully");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("Sending Failed");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/addNotificationDetails", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> addNotificationDetails(
			@RequestBody List<NotificationsEntity> notification) {
		ResponseMessageBean res = new ResponseMessageBean();
		logger.info("In Notification Controller ->addNotificationDetails()");

		boolean isAdded = notificationService.addNotificationDetails(notification);
		try {
			if (isAdded) {
				res.setResponseCode("200");

				res.setResponseMessage("Notification Details Has Been Saved Successfully");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("Error Occured While Saving The Record ");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/sendNotificationToAll", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> sendNotificationToAll(@RequestBody NotificationsEntity notification,
			HttpServletRequest httpRequest) {
		logger.info("In Notification Controller ->sendNotificationToAll()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			notification.setCreatedBy(Utils.getUpdatedBy(httpRequest));
			res = notificationService.sendNotificationToAll(notification);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	// Notification Template Data Latest update code by Ashish Yadav
	@RequestMapping(value = "/notificationTempList", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getNotificationTemp() {
		logger.info("In Notification Controller ->getNotificationTempData()");
		ResponseMessageBean res = new ResponseMessageBean();

		try {
			List<MessageMasterEntity> notificationTempData = messageMasterService.messageTemplateList();

			if (!ObjectUtils.isEmpty(notificationTempData)) {
				res.setResponseCode("200");
				res.setResult(notificationTempData);
				res.setResponseMessage("Success");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/getNotificationTempById", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getNotificationTempById(@RequestBody RequestParamBean requestBean) {
		logger.info("In Notification Controller ->getNotificationTempDataById()");
		ResponseMessageBean res = new ResponseMessageBean();

		try {
			List<MessageMasterEntity> messageMasterEntity = messageMasterService
					.getmessageTemplate(new BigDecimal(new Long(requestBean.getId1())));

			if (!ObjectUtils.isEmpty(messageMasterEntity)) {
				res.setResponseCode("200");
				res.setResult(messageMasterEntity);
				res.setResponseMessage("Success");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/saveNotificationTemp", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> saveNotificationTemp(
			@RequestBody MessageMasterEntity messageMasterEntity) {
		logger.info("In Notification Controller ->saveNotificationTempData()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			if (!ObjectUtils.isEmpty(messageMasterEntity)) {
				messageMasterService.saveMessageTemplate(messageMasterEntity);
				res.setResponseCode("200");
				res.setResponseMessage("Notification Template Has Been Saved Successfully");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("Invalid request");
			}

		} catch (Exception e) {
			logger.error(e);
			res.setResponseCode("500");
			res.setResponseMessage("Error Occured While Saving The Record");
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateNotificationTemp", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateNotificationTemp(
			@RequestBody MessageMasterEntity messageMasterEntity) {
		logger.info("In Notification Controller ->updateNotificationTempData()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			messageMasterService.updateMessageTemplate(messageMasterEntity);
			res.setResponseCode("200");
			res.setResponseMessage("Notification Template  Has Been Updated Successfully");
		} catch (Exception e) {
			logger.info("Exception:", e);
			res.setResponseCode("500");
			res.setResponseMessage("Error Occured While Updating The Record");
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/deletetNotificationTemplate", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> deletetNotification(@RequestBody RequestParamBean requestBean) {
		logger.info("In Notification Controller ->deletetNotificationTemp()");
		ResponseMessageBean bean = new ResponseMessageBean();
		try {
			Boolean response = notificationService.deletetNotificationTemp(new BigInteger(requestBean.getId1()));
			if (response) {
				bean.setResponseCode("200");
				bean.setResponseMessage("Deleted Successfully");
			} else {
				bean.setResponseCode("202");
				bean.setResponseMessage("Error Occured While Deleting The Record");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(bean, HttpStatus.OK);
	}

}
