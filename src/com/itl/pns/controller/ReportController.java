package com.itl.pns.controller;

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

import com.itl.pns.bean.MonthlyReportData;
import com.itl.pns.bean.RequestParamBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.corp.entity.CorpServiceRequestEntity;
import com.itl.pns.corp.service.CorpServiceRequestService;
import com.itl.pns.entity.ActivityLogReport;
import com.itl.pns.entity.MessageReport;
import com.itl.pns.entity.ReportMsterEntity;
import com.itl.pns.entity.ServiceRequestEntity;
import com.itl.pns.entity.SubReportEntity;
import com.itl.pns.service.ActivityLogReportService;
import com.itl.pns.service.MessageReportService;
import com.itl.pns.service.ReportService;
import com.itl.pns.service.ServiceRequestService;
import com.itl.pns.util.AdminWorkFlowReqUtility;

/**
 * @author shubham.lokhande
 *
 */
@RestController
@RequestMapping("report")
public class ReportController {

	private static final Logger logger = LogManager.getLogger(ReportController.class);

	@Autowired
	ReportService ReportService;

	@Autowired
	private MessageReportService messageReportService;

	@Autowired
	private ActivityLogReportService activityLogReportService;

	@Autowired
	AdminWorkFlowReqUtility adminWorkFlowReqUtility;

	@Autowired
	private ServiceRequestService serviceRequestService;

	@Autowired
	private CorpServiceRequestService corpserviceRequestService;
	/**
	 * Get Report details by id
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/getReportDetailsById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getReportDetailsById(@RequestBody RequestParamBean requestBean) {
		logger.info("in ReportController -> getReportDetailsById()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<ReportMsterEntity> reportMasterList = ReportService
					.getReportDetailsById(Integer.parseInt(requestBean.getId1()));

			if (null != reportMasterList) {
				res.setResponseCode("200");
				res.setResult(reportMasterList);
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

	@RequestMapping(value = "/getServiceRequestReportDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getServiceRequestReportDetails(
			@RequestBody ServiceRequestEntity serviceRequestEntity) {
		logger.info("in ServiceReportController -> getServiceRequestReportDetails()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<ServiceRequestEntity> serviceRequest = serviceRequestService
					.getServiceRequestReportDetails(serviceRequestEntity);
			if (null != serviceRequest) {
				res.setResponseCode("200");
				res.setResult(serviceRequest);
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

	@RequestMapping(value = "/getChannelDetailService", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getChannelDetailService() {
		logger.info("in MessageReportController -> getChannelDetailService()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<ServiceRequestEntity> serviceRequest = serviceRequestService.getChannelDetailService();
			if (null != serviceRequest) {
				res.setResponseCode("200");
				res.setResult(serviceRequest);
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

	@RequestMapping(value = "/getChannelDetailsService", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)

	public ResponseEntity<ResponseMessageBean> getChannelDetailsService() {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<CorpServiceRequestEntity> corpserviceRequestEntity = corpserviceRequestService
					.getChannelDetailsService();
			if (null != corpserviceRequestEntity) {
				res.setResponseCode("200");
				res.setResult(corpserviceRequestEntity);
				res.setResponseMessage("Success");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No records found");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);

	}

	@RequestMapping(value = "/getServiceRequestReportDetail", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getServiceRequestReportDetail(
			@RequestBody CorpServiceRequestEntity corpserviceRequestEntity) {
		logger.info("in ServiceReportController -> getServiceRequestReportDetail()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<CorpServiceRequestEntity> serviceRequest = corpserviceRequestService
					.getServiceRequestReportDetail(corpserviceRequestEntity);
			if (null != serviceRequest) {
				res.setResponseCode("200");
				res.setResult(serviceRequest);
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

	/**
	 * get All report details
	 *
	 * @return
	 */
	@RequestMapping(value = "/getAllReportDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getAllReportDetails() {
		logger.info("in ReportController -> getAllReportDetails()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<ReportMsterEntity> reportMasterList = ReportService.getAllReportDetails();
			if (null != reportMasterList) {
				res.setResponseCode("200");
				res.setResult(reportMasterList);
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

	/**
	 * Add report details
	 *
	 * @param reportMasterDetails
	 * @return
	 */
	@RequestMapping(value = "/addReportDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> addReportDetails(@RequestBody ReportMsterEntity reportMasterDetails) {
		logger.info("in ReportController -> addReportDetails()");
		ResponseMessageBean response = new ResponseMessageBean();
		ResponseMessageBean responsecode = new ResponseMessageBean();
		/*
		 * String roleName =
		 * adminWorkFlowReqUtility.getRoleNameByRoleId(reportMasterDetails.getRole_ID().
		 * intValue()); reportMasterDetails.setRoleName(roleName);
		 * reportMasterDetails.setStatusName(
		 * adminWorkFlowReqUtility.getStatusNameByStatusId(reportMasterDetails.
		 * getStatusId().intValue())); //
		 * reportMasterDetails.setProductName(adminWorkFlowReqUtility.getAppNameByAppId(
		 * reportMasterDetails.getAppId().intValue()));
		 * reportMasterDetails.setCreatedByName(adminWorkFlowReqUtility.
		 * getCreatedByNameByCreatedId(reportMasterDetails.getCreatedBy().intValue()));
		 * reportMasterDetails.setAction("ADD"); List<ActivitySettingMasterEntity>
		 * activityMasterData = adminWorkFlowReqUtility
		 * .isMakerCheckerPresentForReq(reportMasterDetails.getActivityName());
		 */
		Boolean res = false;
		try {
			if (!ObjectUtils.isEmpty(reportMasterDetails)) {

				responsecode = ReportService.isReportNameExist(reportMasterDetails);
				if (responsecode.getResponseCode().equalsIgnoreCase("200")) {
					res = ReportService.addReportDetails(reportMasterDetails);
					if (res) {
						response.setResponseCode("200");
						response.setResponseMessage("Report Details Saved Successfully");
						/*
						 * if (roleName.equalsIgnoreCase(ApplicationConstants.Maker) &&
						 * (ApplicationConstants.YESVALUE).equals(activityMasterData.get(0).getChecker()
						 * ) &&
						 * (ApplicationConstants.YESVALUE).equals(activityMasterData.get(0).getMaker()))
						 * {
						 *
						 * response.setResponseMessage("Request Submitted To Admin Checker For Approval"
						 * ); } else { response.setResponseMessage("Details Saved Successfully"); }
						 */

					} else {
						response.setResponseCode("202");
						response.setResponseMessage("Error While Saving Records");
					}
				} else {
					response.setResponseCode("202");
					response.setResponseMessage(responsecode.getResponseMessage());
				}
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
			responsecode.setResponseCode("500");
			responsecode.setResponseMessage("Error While Adding Records!");
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Update report details
	 *
	 * @param reportMasterDetails
	 * @return
	 */
	@RequestMapping(value = "/updateReportDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateReportDetails(@RequestBody ReportMsterEntity reportMasterDetails) {
		logger.info("in ReportController -> updateReportDetails()");
		ResponseMessageBean response = new ResponseMessageBean();
		ResponseMessageBean responsecode = new ResponseMessageBean();

		/*
		 * String roleName =
		 * adminWorkFlowReqUtility.getRoleNameByRoleId(reportMasterDetails.getRole_ID().
		 * intValue()); reportMasterDetails.setRoleName(roleName);
		 * reportMasterDetails.setAction("EDIT"); reportMasterDetails.setStatusName(
		 * adminWorkFlowReqUtility.getStatusNameByStatusId(reportMasterDetails.
		 * getStatusId().intValue()));
		 * //reportMasterDetails.setProductName(adminWorkFlowReqUtility.
		 * getAppNameByAppId(reportMasterDetails.getAppId().intValue()));
		 * reportMasterDetails.setCreatedByName(adminWorkFlowReqUtility.
		 * getCreatedByNameByCreatedId(reportMasterDetails.getCreatedBy().intValue()));
		 * List<ActivitySettingMasterEntity> activityMasterData =
		 * adminWorkFlowReqUtility
		 * .isMakerCheckerPresentForReq(reportMasterDetails.getActivityName());
		 */
		Boolean res = false;
		try {
			if (!ObjectUtils.isEmpty(reportMasterDetails)) {
				responsecode = ReportService.updateReportDetailsExist(reportMasterDetails);
				if (responsecode.getResponseCode().equalsIgnoreCase("200")) {
					res = ReportService.updateReportDetails(reportMasterDetails);
					if (res) {
						response.setResponseCode("200");
						response.setResponseMessage("Report Details Updated Successfully");
						/*
						 * if (roleName.equalsIgnoreCase(ApplicationConstants.Maker) &&
						 * (ApplicationConstants.YESVALUE).equals(activityMasterData.get(0).getChecker()
						 * ) &&
						 * (ApplicationConstants.YESVALUE).equals(activityMasterData.get(0).getMaker()))
						 * {
						 *
						 * response.setResponseMessage("Request Submitted To Admin Checker For Approval"
						 * ); }else{ response.setResponseMessage("Details Updated Successfully"); }
						 */

					} else {
						response.setResponseCode("202");
						response.setResponseMessage("Error While Updating Records");
					}
				} else {
					response.setResponseCode("202");
					response.setResponseMessage(responsecode.getResponseMessage());
				}
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
			responsecode.setResponseCode("500");
			responsecode.setResponseMessage("Error While Updating Records!");
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * get dynamic reports
	 *
	 * @param reportMasterDetails
	 * @return
	 */
	@RequestMapping(value = "/getDynamicReports", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getReportDynamicReports(
			@RequestBody ReportMsterEntity reportMasterDetails) {
		logger.info("in ReportController -> getDynamicReports()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			ResponseMessageBean response = ReportService.getReportDynamicReports(reportMasterDetails);

			if (null != response) {
				res.setResponseCode("200");
				res.setResult(response);
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

	@RequestMapping(value = "/getSubReportDetailsById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getSubReportDetailsById(@RequestBody RequestParamBean requestBean) {
		logger.info("in ReportController -> getReportDetailsById()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<SubReportEntity> reportMasterList = ReportService
					.getSubReportDetailsById(Integer.parseInt(requestBean.getId1()));

			if (null != reportMasterList) {
				res.setResponseCode("200");
				res.setResult(reportMasterList);
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

	/**
	 * get All report details
	 *
	 * @return
	 */
	@RequestMapping(value = "/getAllSubReportDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getAllSubReportDetails() {
		logger.info("in ReportController -> getAllSubReportDetails()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<SubReportEntity> reportMasterList = ReportService.getAllSubReportDetails();
			if (null != reportMasterList) {
				res.setResponseCode("200");
				res.setResult(reportMasterList);
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

	@RequestMapping(value = "/getSubReportDetailsByReportId", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getSubReportDetailsByReportId(@RequestBody SubReportEntity subReport) {
		logger.info("in ReportController -> getSubReportDetailsByReportId()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<SubReportEntity> reportMasterList = ReportService
					.getSubReportDetailsByReportId(subReport.getReportid().intValue());

			if (null != reportMasterList) {
				res.setResponseCode("200");
				res.setResult(reportMasterList);
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

	/**
	 * Add report details
	 *
	 * @param reportMasterDetails
	 * @return
	 */
	@RequestMapping(value = "/addSubReportDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> addSubReportDetails(@RequestBody SubReportEntity reportMasterDetails) {
		logger.info("in ReportController -> addSubReportDetails()");
		ResponseMessageBean response = new ResponseMessageBean();
		ResponseMessageBean responsecode = new ResponseMessageBean();
		Boolean res = false;
		try {
			if (!ObjectUtils.isEmpty(reportMasterDetails)) {

				responsecode = ReportService.isSubReportNameExist(reportMasterDetails);
				if (responsecode.getResponseCode().equalsIgnoreCase("200")) {
					res = ReportService.addSubReportDetails(reportMasterDetails);
					if (res) {
						response.setResponseCode("200");
						response.setResponseMessage("Report Details Saved Successfully");

					} else {
						response.setResponseCode("202");
						response.setResponseMessage("Error While Saving Records");
					}
				} else {
					response.setResponseCode("202");
					response.setResponseMessage(responsecode.getResponseMessage());
				}
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
			responsecode.setResponseCode("500");
			responsecode.setResponseMessage("Error While Adding Records!");
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Update report details
	 *
	 * @param reportMasterDetails
	 * @return
	 */
	@RequestMapping(value = "/updateSubReportDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateSubReportDetails(
			@RequestBody SubReportEntity reportMasterDetails) {
		logger.info("in ReportController -> updateSubReportDetails()");
		ResponseMessageBean response = new ResponseMessageBean();
		ResponseMessageBean responsecode = new ResponseMessageBean();

		Boolean res = false;
		try {
			if (!ObjectUtils.isEmpty(reportMasterDetails)) {
				responsecode = ReportService.updateSubReportNameExist(reportMasterDetails);
				if (responsecode.getResponseCode().equalsIgnoreCase("200")) {
					res = ReportService.updateSubReportDetails(reportMasterDetails);
					if (res) {
						response.setResponseCode("200");
						response.setResponseMessage("Report Details Updated Successfully");

					} else {
						response.setResponseCode("202");
						response.setResponseMessage("Error While Updating Records");
					}
				} else {
					response.setResponseCode("202");
					response.setResponseMessage(responsecode.getResponseMessage());
				}
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
			responsecode.setResponseCode("500");
			responsecode.setResponseMessage("Error While Updating Records!");
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/getActivityLogReport", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getActivityLogReport(@RequestBody ActivityLogReport activityLogReport) {
		logger.info("in ActivityLogReportController -> getActivityLogReport()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<ActivityLogReport> activityLogReports = activityLogReportService
					.getActivityLogReport(activityLogReport);
			if (null != activityLogReports) {
				res.setResponseCode("200");
				res.setResult(activityLogReports);
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

	@RequestMapping(value = "/getChannelDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getChannelDetails() {
		logger.info("in MessageReportController -> getChannelDetails()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<MessageReport> messageReport = messageReportService.getChannelDetails();
			if (null != messageReport) {
				res.setResponseCode("200");
				res.setResult(messageReport);
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

	@RequestMapping(value = "/getMessageReportDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getMessageReportDetails(
			@RequestBody MessageReport messageReportDeatils) {
		logger.info("in MessageReportController -> getMessageReportDetails()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<MessageReport> messageReport = messageReportService.getMessageReportDetails(messageReportDeatils);
			if (null != messageReport) {
				res.setResponseCode("200");
				res.setResult(messageReport);
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
	
	@RequestMapping(value = "/getChannelDetailsActiveService", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getChannelDetailsActiveService() {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<CorpServiceRequestEntity> corpserviceRequestEntity = corpserviceRequestService
					.getChannelDetailsActiveService();
			if (null != corpserviceRequestEntity) {
				res.setResponseCode("200");
				res.setResult(corpserviceRequestEntity);
				res.setResponseMessage("Success");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No records found");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);

	}
	
	@RequestMapping(value = "/getMonthlyReport", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getMonthlyReport(
			@RequestBody MonthlyReportData reportsData) {
		logger.info("in ReportController -> MonthlyReport()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			
			List<MonthlyReportData> response = ReportService.getMonthlyReport(reportsData);

			if (null != response && !response.isEmpty()) {
				res.setResponseCode("200");
				res.setResult(response);
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

}