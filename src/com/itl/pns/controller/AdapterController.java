package com.itl.pns.controller;

import java.math.BigDecimal;
import java.math.BigInteger;
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

import com.itl.pns.bean.AdapterAuditLogsBean;
import com.itl.pns.bean.DateBean;
import com.itl.pns.bean.RequestParamBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.constant.ApplicationConstants;
import com.itl.pns.entity.ActivitySettingMasterEntity;
import com.itl.pns.entity.AdapterSrcChannelEntity;
import com.itl.pns.entity.AdapterSrcIPEntity;
import com.itl.pns.service.AdapterService;
import com.itl.pns.util.AdminWorkFlowReqUtility;

/**
 * @author shubham.lokhande
 *
 */
@RestController
@RequestMapping("adapter")
public class AdapterController {

	private static final Logger logger = LogManager.getLogger(AdapterController.class);

	@Autowired
	AdminWorkFlowReqUtility adminWorkFlowReqUtility;

	@Autowired
	AdapterService adapterService;

	@RequestMapping(value = "/addAdapterSrcChannel", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> addAdapterSrcChannel(
			@RequestBody AdapterSrcChannelEntity adapterSrcChanneData) {
		logger.info("In add adapter src channel  -> addAdapterSrcChannel()");
		if ((adapterSrcChanneData.getStatusId().intValue() == 3)) {
			adapterSrcChanneData.setStatusId(BigDecimal.valueOf(3));
		} else {
			adapterSrcChanneData.setStatusId(BigDecimal.valueOf(0));
		}
		adapterSrcChanneData.setCreatedByName(
				adminWorkFlowReqUtility.getCreatedByNameByCreatedId(adapterSrcChanneData.getCreatedby().intValue()));
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(adapterSrcChanneData.getRole_ID().intValue());
		adapterSrcChanneData.setRoleName(roleName);
		adapterSrcChanneData.setAction("ADD");
		adapterSrcChanneData.setStatusName(
				adminWorkFlowReqUtility.getStatusNameByStatusId(adapterSrcChanneData.getStatusId().intValue()));
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(adapterSrcChanneData.getActivityName());

		ResponseMessageBean response = new ResponseMessageBean();
		Boolean res = false;
		ResponseMessageBean responsecode = new ResponseMessageBean();
		try {
			if (!ObjectUtils.isEmpty(adapterSrcChanneData)) {

				responsecode = adapterService.isChannelNameExist(adapterSrcChanneData);

				if (responsecode.getResponseCode().equalsIgnoreCase("200")) {
					res = adapterService.addAdapterSrcChannel(adapterSrcChanneData);
					if (res) {
						response.setResponseCode("200");
						if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getChecker()))
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

							response.setResponseMessage("Request Submitted To Admin Checker For Approval");
						} else {
							response.setResponseMessage("Adapter Channel Added Successfully");
						}

					} else {
						response.setResponseCode("202");
						response.setResponseMessage("Error While Saving Records");
					}
				} else {
					response.setResponseCode("451");
					response.setResponseMessage("Channel Already Exists");
				}
			}

		} catch (Exception e) {
			logger.info("Exception:", e);
			response.setResponseCode("500");
			response.setResponseMessage("Error Occured While Adding The Record");
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateAdapterSrcChannel", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateAdapterSrcChannel(
			@RequestBody AdapterSrcChannelEntity adapterSrcChanneData) {
		logger.info("In  update adapter src channel -> updateAdapterSrcChannel()");
		if ((adapterSrcChanneData.getStatusId().intValue() == 3)) {
			adapterSrcChanneData.setStatusId(BigDecimal.valueOf(3));
		} else {
			adapterSrcChanneData.setStatusId(BigDecimal.valueOf(0));
		}
		ResponseMessageBean response = new ResponseMessageBean();
		adapterSrcChanneData.setCreatedByName(
				adminWorkFlowReqUtility.getCreatedByNameByCreatedId(adapterSrcChanneData.getCreatedby().intValue()));
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(adapterSrcChanneData.getRole_ID().intValue());
		adapterSrcChanneData.setRoleName(roleName);
		adapterSrcChanneData.setAction("EDIT");
		adapterSrcChanneData.setStatusName(
				adminWorkFlowReqUtility.getStatusNameByStatusId(adapterSrcChanneData.getStatusId().intValue()));
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(adapterSrcChanneData.getActivityName());

		Boolean res = false;
		ResponseMessageBean responsecode = new ResponseMessageBean();
		try {
			if (!ObjectUtils.isEmpty(adapterSrcChanneData) && adapterSrcChanneData.getId() != null) {
				responsecode = adapterService.updateChannelNameExist(adapterSrcChanneData);
				if (responsecode.getResponseCode().equalsIgnoreCase("200")) {
					res = adapterService.updateAdapterSrcChannel(adapterSrcChanneData);
					if (res != null) {
						response.setResponseCode("200");

						if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getChecker()))
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

							response.setResponseMessage("Request Submitted To Admin Checker For Approval");
						} else {
							response.setResponseMessage("Adapter Channel Updated Successfully");
						}
					} else {
						response.setResponseCode("202");
						response.setResponseMessage("Error While Updating Records");
					}
				} else {
					response.setResponseCode("451");
					response.setResponseMessage("Channel Already Exists");
				}
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
			response.setResponseCode("500");
			response.setResponseMessage("Error Occured While Updating The Record");
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/deletetAdapterSrcChannel", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> deletetAdapterSrcChannel(
			@RequestBody AdapterSrcChannelEntity adapterSrcChanneData) {
		ResponseMessageBean bean = new ResponseMessageBean();
		if ((adapterSrcChanneData.getStatusId().intValue() == 3)) {
			adapterSrcChanneData.setStatusId(BigDecimal.valueOf(3));
		} else {
			adapterSrcChanneData.setStatusId(BigDecimal.valueOf(0));
		}
		adapterSrcChanneData.setCreatedByName(
				adminWorkFlowReqUtility.getCreatedByNameByCreatedId(adapterSrcChanneData.getCreatedby().intValue()));
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(adapterSrcChanneData.getRole_ID().intValue());
		adapterSrcChanneData.setRoleName(roleName);
		adapterSrcChanneData.setAction("DELETE");
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq("ADAPTERSRCCHANNELDELETE");
		Boolean res = false;
		ResponseMessageBean responsecode = new ResponseMessageBean();
		try {
			Boolean response = adapterService.deletetAdapterSrcChannel(adapterSrcChanneData);
			if (response) {
				bean.setResponseCode("200");
				if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
						&& (ApplicationConstants.YESVALUE)
								.equals(Character.toString(activityMasterData.get(0).getChecker()))
						&& (ApplicationConstants.YESVALUE)
								.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
					bean.setResponseMessage("Request Submitted To Admin Checker For Approval");
				} else {
					bean.setResponseMessage("Adapter Channel Deleted Successfully");
				}

			} else {
				bean.setResponseCode("202");
				bean.setResponseMessage("Cannot Delete Record As IP Is Mapped");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}
		return new ResponseEntity<>(bean, HttpStatus.OK);
	}

	@RequestMapping(value = "/getAdaptrSrcChannel", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getAdaptrSrcChannel() {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<AdapterSrcChannelEntity> adapterData = adapterService.getAdaptrSrcChannel();

			if (!ObjectUtils.isEmpty(adapterData)) {
				res.setResponseCode("200");
				res.setResult(adapterData);
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

	@RequestMapping(value = "/getAdaptrSrcChannelById", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getAdaptrSrcChannelById(@RequestBody RequestParamBean requestBean) {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<AdapterSrcChannelEntity> adapterData = adapterService
					.getAdaptrSrcChannelById(new BigInteger(requestBean.getId1()));

			if (!ObjectUtils.isEmpty(adapterData)) {
				res.setResponseCode("200");
				res.setResult(adapterData);
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

	@RequestMapping(value = "/addAdapterSrcIP", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> addAdapterSrcIP(@RequestBody AdapterSrcIPEntity adapterSrcIpData) {
		logger.info("In add adapter src IP  -> addAdapterSrcIP()");
		ResponseMessageBean response = new ResponseMessageBean();
		if (adapterSrcIpData.getStatusId().intValue() == 3) {
			adapterSrcIpData.setStatusId(BigDecimal.valueOf(3));
		} else {
			adapterSrcIpData.setStatusId(BigDecimal.valueOf(0));
		}
		Boolean res = false;

		adapterSrcIpData.setCreatedByName(
				adminWorkFlowReqUtility.getCreatedByNameByCreatedId(adapterSrcIpData.getCreatedby().intValue()));
		ResponseMessageBean responsecode = new ResponseMessageBean();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(adapterSrcIpData.getRole_ID().intValue());
		adapterSrcIpData.setRoleName(roleName);
		adapterSrcIpData.setAppName(adminWorkFlowReqUtility.getAppNameByAppId(adapterSrcIpData.getAppId().intValue()));
		adapterSrcIpData.setAction("ADD");
		adapterSrcIpData.setStatusName(
				adminWorkFlowReqUtility.getStatusNameByStatusId(adapterSrcIpData.getStatusId().intValue()));
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(adapterSrcIpData.getActivityName());
		// adapterSrcIpData.setChannelname(adminWorkFlowReqUtility.getChannelNameBychannelId(adapterSrcIpData.getAdapterChannel().intValue()));
		try {
			if (!ObjectUtils.isEmpty(adapterSrcIpData)) {
				responsecode = adapterService.isIpExitForSameChannel(adapterSrcIpData);
				if (responsecode.getResponseCode().equalsIgnoreCase("200")) {
					res = adapterService.addAdapterSrcIP(adapterSrcIpData);
					if (res) {
						response.setResponseCode("200");
						if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getChecker()))
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
							response.setResponseMessage("Request Submitted To Admin Checker For Approval");
						} else {
							response.setResponseMessage("Adapter IP Added Successfully");
						}

					} else {
						response.setResponseCode("500");
						response.setResponseMessage("Error While Adding Records");
					}
				} else {
					response.setResponseCode("500");
					response.setResponseMessage("IP Already Exists For Same Channel");
				}
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
			response.setResponseCode("500");
			response.setResponseMessage("Error Occured While Adding the record");
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateAdapterSrcIP", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateAdapterSrcIP(@RequestBody AdapterSrcIPEntity adapterSrcIpData) {
		logger.info("In  update adapter src IP -> updateAdapterSrcIP()");
		ResponseMessageBean response = new ResponseMessageBean();
		if (adapterSrcIpData.getStatusId().intValue() == 3) {
			adapterSrcIpData.setStatusId(BigDecimal.valueOf(3));
		} else {
			adapterSrcIpData.setStatusId(BigDecimal.valueOf(0));
		}
		Boolean res = false;
		// adapterSrcIpData.setChannelname(adminWorkFlowReqUtility.getChannelNameBychannelId(adapterSrcIpData.getAdapterChannel().intValue()));
		ResponseMessageBean responsecode = new ResponseMessageBean();
		adapterSrcIpData.setCreatedByName(
				adminWorkFlowReqUtility.getCreatedByNameByCreatedId(adapterSrcIpData.getCreatedby().intValue()));
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(adapterSrcIpData.getRole_ID().intValue());
		adapterSrcIpData.setRoleName(roleName);
		adapterSrcIpData.setAppName(adminWorkFlowReqUtility.getAppNameByAppId(adapterSrcIpData.getAppId().intValue()));
		adapterSrcIpData.setAction("EDIT");
		adapterSrcIpData.setStatusName(
				adminWorkFlowReqUtility.getStatusNameByStatusId(adapterSrcIpData.getStatusId().intValue()));
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(adapterSrcIpData.getActivityName());

		try {
			if (!ObjectUtils.isEmpty(adapterSrcIpData)) {
				responsecode = adapterService.UpdateIpExitForSameChannel(adapterSrcIpData);
				if (responsecode.getResponseCode().equalsIgnoreCase("200")) {
					res = adapterService.updateAdapterSrcIP(adapterSrcIpData);
					if (res) {
						response.setResponseCode("200");
						if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getChecker()))
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
							response.setResponseMessage("Request Submitted To Admin Checker For Approval");
						} else {
							response.setResponseMessage("Adapter IP Updated Successfully");
						}

					} else {
						response.setResponseCode("500");
						response.setResponseMessage("Error While Updating Records");
					}
				} else {
					response.setResponseCode("500");
					response.setResponseMessage("IP Already Exists For Same Channel");
				}
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
			response.setResponseCode("500");
			response.setResponseMessage("Error Occured While Adding the record");
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/deleteAdapterSrcIP", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> deleteAdapterSrcIP(@RequestBody AdapterSrcIPEntity adapterIpdata) {
		ResponseMessageBean bean = new ResponseMessageBean();
		try {
			if (adapterIpdata.getStatusId().intValue() == 3) {
				adapterIpdata.setStatusId(BigDecimal.valueOf(3));
			} else {
				adapterIpdata.setStatusId(BigDecimal.valueOf(0));
			}
			adapterIpdata.setCreatedByName(
					adminWorkFlowReqUtility.getCreatedByNameByCreatedId(adapterIpdata.getCreatedby().intValue()));
			String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(adapterIpdata.getRole_ID().intValue());
			adapterIpdata.setRoleName(roleName);
			adapterIpdata.setAppName(adminWorkFlowReqUtility.getAppNameByAppId(adapterIpdata.getAppId().intValue()));
			adapterIpdata.setStatusName(
					adminWorkFlowReqUtility.getStatusNameByStatusId(adapterIpdata.getStatusId().intValue()));
			adapterIpdata.setAction("DELETE");
			List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
					.isMakerCheckerPresentForReq("ADAPTERSRCIPDELETE");

			Boolean response = adapterService.deleteAdapterSrcIP(adapterIpdata);
			if (response) {
				bean.setResponseCode("200");
				if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
						&& (ApplicationConstants.YESVALUE)
								.equals(Character.toString(activityMasterData.get(0).getChecker()))
						&& (ApplicationConstants.YESVALUE)
								.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
					bean.setResponseMessage("Request Submitted To Admin Checker For Approval");
				} else {
					bean.setResponseMessage("Adapter IP Deleted Successfully");
				}

			} else {
				bean.setResponseCode("202");
				bean.setResponseMessage("Error Occured While Deleting The Record");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}
		return new ResponseEntity<>(bean, HttpStatus.OK);
	}

	@RequestMapping(value = "/getAdapterSrcIpDetails", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getAdapterSrcIpDetails() {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
		List<AdapterSrcIPEntity> adapterData = adapterService.getAdapterSrcIpDetails();
		
			if (!ObjectUtils.isEmpty(adapterData)) {
				res.setResponseCode("200");
				res.setResult(adapterData);
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

	@RequestMapping(value = "/getAdapterSrcIpById", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getAdapterSrcIpById(@RequestBody RequestParamBean requestBean) {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
		List<AdapterSrcIPEntity> adapterData = adapterService.getAdapterSrcIpById(new BigInteger(requestBean.getId1()));
		
			if (!ObjectUtils.isEmpty(adapterData)) {
				res.setResponseCode("200");
				res.setResult(adapterData);
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

	@RequestMapping(value = "/getAdapterAuditLogs", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getAdapterAuditLogs() {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
		List<AdapterAuditLogsBean> adapterData = adapterService.getAdapterAuditLogs();
		
			if (!ObjectUtils.isEmpty(adapterData)) {
				res.setResponseCode("200");
				res.setResult(adapterData);
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

	@RequestMapping(value = "/getAdapterAuditLogsById", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getAdapterAuditLogsById(@RequestBody RequestParamBean requestBean) {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
		List<AdapterAuditLogsBean> adapterData = adapterService
				.getAdapterAuditLogsById(new BigInteger(requestBean.getId1()));
		
			if (!ObjectUtils.isEmpty(adapterData)) {
				res.setResponseCode("200");
				res.setResult(adapterData);
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

	@RequestMapping(value = "/getAdapterAuditLogByDate", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getAdapterAuditLogByDate(@RequestBody DateBean datebean) {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
		List<AdapterAuditLogsBean> list = adapterService.getAdapterAuditLogByDate(datebean);
		
			if (!list.isEmpty()) {
				res.setResponseCode("200");
				res.setResult(list);
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
}
