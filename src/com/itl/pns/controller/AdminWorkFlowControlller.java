package com.itl.pns.controller;

import java.util.Date;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.itl.pns.bean.RequestParamBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.constant.ApplicationConstants;
import com.itl.pns.entity.ActivitySettingMasterEntity;
import com.itl.pns.entity.AdminWorkFlowRequestEntity;
import com.itl.pns.service.AdminWorkFlowService;
import com.itl.pns.util.AdminWorkFlowReqUtility;

/**
 * @author shubham.lokhande
 *
 */
@RestController
@RequestMapping("adminWorkFlow")
public class AdminWorkFlowControlller {
	private static final Logger logger = LogManager.getLogger(AdminWorkFlowControlller.class);
	@Autowired
	AdminWorkFlowService adminWorkFlowService;

	@Autowired
	AdminWorkFlowReqUtility adminWorkFlowReqUtility;

	@RequestMapping(value = "/getAllDataForChecker", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getAllDataForChecker() {
		logger.info("In Admin Work Flow Controller -> getAllDataForChecker()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<AdminWorkFlowRequestEntity> adminWorkFlowData = adminWorkFlowService.getAllDataForChecker();

			if (!ObjectUtils.isEmpty(adminWorkFlowData)) {
				res.setResponseCode("200");
				res.setResult(adminWorkFlowData);
				res.setResponseMessage("Success");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);

	}

	@RequestMapping(value = "/getCheckerDataById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getCheckerDataById(@RequestBody RequestParamBean requestBean) {
		logger.info("In Admin Work Flow Controller -> getCheckerDataById()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<AdminWorkFlowRequestEntity> adminWorkFlowData = adminWorkFlowService
					.getCheckerDataById(Integer.parseInt(requestBean.getId1()));

			if (!ObjectUtils.isEmpty(adminWorkFlowData)) {
				res.setResponseCode("200");
				res.setResult(adminWorkFlowData);
				res.setResponseMessage("Success");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);

	}

	@RequestMapping(value = "/getAllDataForMaker", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getAllDataForMaker() {
		logger.info("In Admin Work Flow Controller -> getAllDataForMaker()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			System.out.println(new Date());
			List<AdminWorkFlowRequestEntity> adminWorkFlowData = adminWorkFlowService.getAllDataForMaker();

			if (!ObjectUtils.isEmpty(adminWorkFlowData)) {
				res.setResponseCode("200");
				res.setResult(adminWorkFlowData);
				res.setResponseMessage("Success");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);

	}

	@RequestMapping(value = "/getMakerDataById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getMakerDataById(@PathVariable("id") int id) {
		logger.info("In Admin Work Flow Controller -> getMakerDataById()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<AdminWorkFlowRequestEntity> adminWorkFlowData = adminWorkFlowService.getMakerDataById(id);

			if (!ObjectUtils.isEmpty(adminWorkFlowData)) {
				res.setResponseCode("200");
				res.setResult(adminWorkFlowData);
				res.setResponseMessage("Success");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);

	}

	@RequestMapping(value = "/updateStatusByChecker", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateStatusByChecker(
			@RequestBody AdminWorkFlowRequestEntity adminWorkFlowData) {
		logger.info("In Admin Work Flow Controller -> getCheckerDataById()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			String stsName = adminWorkFlowReqUtility
					.getStatusNameByStatusId(adminWorkFlowData.getStatusId().intValue());
			Boolean response = adminWorkFlowService.updateStatusByChecker(adminWorkFlowData);
			List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
					.isMakerCheckerPresentForReq(adminWorkFlowData.getActivityName());
			if (response) {
				res.setResponseCode("200");
				if (stsName.equalsIgnoreCase(ApplicationConstants.APPROVED)
						&& ApplicationConstants.YESVALUE.equals((activityMasterData.get(0).getApprover()))) {
					res.setResponseMessage("Request Sumitted To Admin Approver For Approval");
				} else if (stsName.equalsIgnoreCase(ApplicationConstants.APPROVED)
						&& adminWorkFlowData.getActivityName().equalsIgnoreCase("bankTokenEdit")) {
					res.setResponseMessage("Token Generated Successfully");
				} else if (stsName.equalsIgnoreCase(ApplicationConstants.APPROVED)
						&& !ApplicationConstants.YESVALUE.equals(activityMasterData.get(0).getApprover())) {// if approver not present & approved by checker
					res.setResponseMessage("Request Approved ");
				} else {
					res.setResponseMessage("Request Rejected And Sent To Maker");
				}
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("Error Occured While Updating The Record");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateRequestByMaker", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateRequestByMaker(
			@RequestBody AdminWorkFlowRequestEntity adminWorkFlowData) {
		logger.info("In Admin Work Flow Controller -> updateRequestByMaker()");
		ResponseMessageBean res = new ResponseMessageBean();
		Boolean response = adminWorkFlowService.updateRequestByMaker(adminWorkFlowData);
		try {
			if (response) {
				res.setResponseCode("200");
				res.setResponseMessage("Request Closed Successfully");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("Error Occured While Updating The Record");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateStatusListByChecker", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateStatusListByChecker(
			@RequestBody List<AdminWorkFlowRequestEntity> adminWorkFlowDataList) {
		logger.info("In Admin Work Flow Controller -> getCheckerDataById()");
		ResponseMessageBean res = new ResponseMessageBean();
		Boolean response = adminWorkFlowService.updateStatusByListChecker(adminWorkFlowDataList);
		String type = null;
		try {
			for (AdminWorkFlowRequestEntity dataList : adminWorkFlowDataList) {
				type = dataList.getType();
			}
			if (response) {
				res.setResponseCode("200");
				res.setResponseCode("200");
				if (type.equalsIgnoreCase(ApplicationConstants.APPROVED)) {
					res.setResponseMessage("Request Approved ");
				} else {
					res.setResponseMessage("Request Rejected And Sent To Maker");
				}

			} else {
				res.setResponseCode("202");
				res.setResponseMessage("Error Occured While Updating The Record");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateStatusByApprover", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateStatusByApprover(
			@RequestBody AdminWorkFlowRequestEntity adminWorkFlowData) {
		logger.info("In Admin Work Flow Controller -> updateStatusByApprover()");

		ResponseMessageBean res = new ResponseMessageBean();
		try {
			String stsName = adminWorkFlowReqUtility
					.getStatusNameByStatusId(adminWorkFlowData.getStatusId().intValue());// status
																							// 13
																							// approve
			Boolean response = adminWorkFlowService.updateStatusByApprover(adminWorkFlowData);

			if (response) {
				res.setResponseCode("200");
				if (stsName.equalsIgnoreCase(ApplicationConstants.APPROVED)) {// if approver approved the request
					res.setResponseMessage("Request Approved");
				} else {
					res.setResponseMessage("Request Rejected And Submitted To Checker"); /// if rejected by approver
				}

			} else {
				res.setResponseCode("202");
				res.setResponseMessage("Error Occured While Updating The Record");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/getApproverDataById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getApproverDataById(@RequestBody RequestParamBean requestBean) {
		logger.info("In Admin Work Flow Controller -> getApproverDataById()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<AdminWorkFlowRequestEntity> adminWorkFlowData = adminWorkFlowService
					.getApproverDataById(Integer.parseInt(requestBean.getId1()));

			if (!ObjectUtils.isEmpty(adminWorkFlowData)) {
				res.setResponseCode("200");
				res.setResult(adminWorkFlowData);
				res.setResponseMessage("Success");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);

	}

	@RequestMapping(value = "/getAllDataForApprover", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getAllDataForApprover() {
		logger.info("In Admin Work Flow Controller -> getAllDataForApprover()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			System.out.println(new Date());
			List<AdminWorkFlowRequestEntity> adminWorkFlowData = adminWorkFlowService.getAllDataForApprover();

			if (!ObjectUtils.isEmpty(adminWorkFlowData)) {
				res.setResponseCode("200");
				res.setResult(adminWorkFlowData);
				res.setResponseMessage("Success");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);

	}

	@RequestMapping(value = "/updateStatusListByApprover", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateStatusListByApprover(
			@RequestBody List<AdminWorkFlowRequestEntity> adminWorkFlowDataList) {
		logger.info("In Admin Work Flow Controller -> getCheckerDataById()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			Boolean response = adminWorkFlowService.updateStatusByListApprover(adminWorkFlowDataList);
			String type = null;
			for (AdminWorkFlowRequestEntity dataList : adminWorkFlowDataList) {
				type = dataList.getType();
			}

			if (response) {
				res.setResponseCode("200");
				if (type.equalsIgnoreCase(ApplicationConstants.APPROVED)) {
					res.setResponseMessage("Request Approved");
				} else {
					res.setResponseMessage("Request Rejected And Submitted To Checker");
				}

			} else {
				res.setResponseCode("202");
				res.setResponseMessage("Error Occured While Updating The Record");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/getAllDataForCorpChecker", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getAllDataForCorpChecker() {
		logger.info("In Admin Work Flow Controller -> getAllDataForCorpChecker()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<AdminWorkFlowRequestEntity> adminWorkFlowData = adminWorkFlowService.getAllDataForCorpChecker();

			if (!ObjectUtils.isEmpty(adminWorkFlowData)) {
				res.setResponseCode("200");
				res.setResult(adminWorkFlowData);
				res.setResponseMessage("Success");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);

	}

	@RequestMapping(value = "/getAllCorpDataForMaker", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getAllCorpDataForMaker() {
		logger.info("In Admin Work Flow Controller -> getAllCorpDataForMaker()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			System.out.println(new Date());
			List<AdminWorkFlowRequestEntity> adminWorkFlowData = adminWorkFlowService.getAllCorpDataForMaker();

			if (!ObjectUtils.isEmpty(adminWorkFlowData)) {
				res.setResponseCode("200");
				res.setResult(adminWorkFlowData);
				res.setResponseMessage("Success");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);

	}

	@RequestMapping(value = "/getAllCorpDataForApprover", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getAllCorpDataForApprover() {
		logger.info("In Admin Work Flow Controller -> getAllCorpDataForApprover()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			System.out.println(new Date());
			List<AdminWorkFlowRequestEntity> adminWorkFlowData = adminWorkFlowService.getAllCorpDataForApprover();

			if (!ObjectUtils.isEmpty(adminWorkFlowData)) {
				res.setResponseCode("200");
				res.setResult(adminWorkFlowData);
				res.setResponseMessage("Success");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);

	}

	@RequestMapping(value = "/updateStatusByCorpChecker", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateStatusByCorpChecker(
			@RequestBody AdminWorkFlowRequestEntity adminWorkFlowData) {
		logger.info("In Admin Work Flow Controller -> updateStatusByCorpChecker()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			String stsName = adminWorkFlowReqUtility
					.getStatusNameByStatusId(adminWorkFlowData.getStatusId().intValue());
			Boolean response = adminWorkFlowService.updateStatusByCorpChecker(adminWorkFlowData);
			List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
					.isMakerCheckerPresentForReq(adminWorkFlowData.getActivityName());

			if (response) {
				res.setResponseCode("200");
				if (stsName.equalsIgnoreCase(ApplicationConstants.APPROVED)
						&& ApplicationConstants.YESVALUE.equals(activityMasterData.get(0).getApprover())) {
					res.setResponseMessage("Request Sumitted To Corporate Approver For Approval");
				} else if (stsName.equalsIgnoreCase(ApplicationConstants.APPROVED)
						&& !ApplicationConstants.YESVALUE.equals(activityMasterData.get(0).getApprover())) {// if
																											// approver
					// checker
					res.setResponseMessage("Request Approved ");
				} else {
					res.setResponseMessage("Request Rejected And Sent To Corporate Maker");
				}

			} else {
				res.setResponseCode("202");
				res.setResponseMessage("Error Occured While Updating The Record");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateRequestByCorpMaker", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateRequestByCorpMaker(
			@RequestBody AdminWorkFlowRequestEntity adminWorkFlowData) {
		logger.info("In Admin Work Flow Controller -> updateRequestByCorpMaker()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			Boolean response = adminWorkFlowService.updateRequestByCorpMaker(adminWorkFlowData);

			if (response) {
				res.setResponseCode("200");
				res.setResponseMessage("Request Closed Successfully");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("Error Occured While Updating The Record");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateStatusListByCorpChecker", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateStatusListByCorpChecker(
			@RequestBody List<AdminWorkFlowRequestEntity> adminWorkFlowDataList) {
		logger.info("In Admin Work Flow Controller -> updateStatusListByCorpChecker()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			Boolean response = adminWorkFlowService.updateStatusListByCorpChecker(adminWorkFlowDataList);
			String type = null;
			for (AdminWorkFlowRequestEntity dataList : adminWorkFlowDataList) {
				type = dataList.getType();
			}

			if (response) {
				res.setResponseCode("200");
				res.setResponseCode("200");
				if (type.equalsIgnoreCase(ApplicationConstants.APPROVED)) {
					res.setResponseMessage("Request Approved ");
				} else {
					res.setResponseMessage("Request Rejected And Sent To Corporate Maker");
				}

			} else {
				res.setResponseCode("202");
				res.setResponseMessage("Error Occured While Updating The Record");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateStatusByCorpApprover", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateStatusByCorpApprover(
			@RequestBody AdminWorkFlowRequestEntity adminWorkFlowData) {
		logger.info("In Admin Work Flow Controller -> updateStatusByCorpApprover()");

		ResponseMessageBean res = new ResponseMessageBean();
		try {
			String stsName = adminWorkFlowReqUtility
					.getStatusNameByStatusId(adminWorkFlowData.getStatusId().intValue());// status
																							// 13
																							// approve
			Boolean response = adminWorkFlowService.updateStatusByCorpApprover(adminWorkFlowData);

			if (response) {
				res.setResponseCode("200");
				if (stsName.equalsIgnoreCase(ApplicationConstants.APPROVED)) {// if approver approved the request
					res.setResponseMessage("Request Approved");
				} else {
					res.setResponseMessage("Request Rejected And Submitted To Corporate Checker"); /// if rejected by
																									/// approver
				}

			} else {
				res.setResponseCode("202");
				res.setResponseMessage("Error Occured While Updating The Record");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateStatusListByCorpApprover", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateStatusListByCorpApprover(
			@RequestBody List<AdminWorkFlowRequestEntity> adminWorkFlowDataList) {
		logger.info("In Admin Work Flow Controller -> getCheckerDataById()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			Boolean response = adminWorkFlowService.updateStatusListByCorpApprover(adminWorkFlowDataList);
			String type = null;
			for (AdminWorkFlowRequestEntity dataList : adminWorkFlowDataList) {
				type = dataList.getType();
			}

			if (response) {
				res.setResponseCode("200");
				if (type.equalsIgnoreCase(ApplicationConstants.APPROVED)) {
					res.setResponseMessage("Request Approved");
				} else {
					res.setResponseMessage("Request Rejected And Submitted To Corporate Checker");
				}

			} else {
				res.setResponseCode("202");
				res.setResponseMessage("Error Occured While Updating The Record");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

}
