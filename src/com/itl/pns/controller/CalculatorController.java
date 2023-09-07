package com.itl.pns.controller;

import java.sql.SQLException;
import java.util.List;

import javax.sql.rowset.serial.SerialException;

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

import com.itl.pns.bean.RequestParamBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.constant.ApplicationConstants;
import com.itl.pns.entity.ActivitySettingMasterEntity;
import com.itl.pns.entity.CalculatorFormulaEntity;
import com.itl.pns.entity.CalculatorMasterEntity;
import com.itl.pns.service.CalculatorService;
import com.itl.pns.util.AdminWorkFlowReqUtility;

@RestController
@RequestMapping("calculator")
public class CalculatorController {

	private static final Logger logger = LogManager.getLogger(CalculatorController.class);

	@Autowired
	CalculatorService calculatorService;

	@Autowired
	AdminWorkFlowReqUtility adminWorkFlowReqUtility;

	@RequestMapping(value = "/getCalculatorMasterById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getCalculatorMasterById(@RequestBody RequestParamBean requestBean) {
		logger.info("get calculator MasterDetails by id -> getCalculatorMasterById()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<CalculatorMasterEntity> calculatorList = calculatorService
					.getCalculatorMasterById(Integer.parseInt(requestBean.getId1()));

			if (null != calculatorList) {
				res.setResponseCode("200");
				res.setResult(calculatorList);
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

	@RequestMapping(value = "/getCalculatorMasterDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getCalculatorMasterDetails() {
		logger.info("get calculator MasterDetails  -> getCalculatorMasterDetails()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<CalculatorMasterEntity> calculatorList = calculatorService.getCalculatorMasterDetails();

			if (null != calculatorList) {
				res.setResponseCode("200");
				res.setResult(calculatorList);
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

	@RequestMapping(value = "/addCalculatorMasterDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> addCalculatorMasterDetails(
			@RequestBody CalculatorMasterEntity calculatorMaster) {
		logger.info("In save Calculator Master  Details -> addCalculatorMasterDetails()");
		ResponseMessageBean response = new ResponseMessageBean();
		ResponseMessageBean responsecode = new ResponseMessageBean();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(calculatorMaster.getRole_ID().intValue());
		calculatorMaster.setRoleName(roleName);
		calculatorMaster.setStatusName(
				adminWorkFlowReqUtility.getStatusNameByStatusId(calculatorMaster.getStatusId().intValue()));
		calculatorMaster
				.setProductName(adminWorkFlowReqUtility.getAppNameByAppId(calculatorMaster.getAppId().intValue()));
		calculatorMaster.setCreatedByName(
				adminWorkFlowReqUtility.getCreatedByNameByCreatedId(calculatorMaster.getCreatedby().intValue()));
		calculatorMaster.setAction("ADD");
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(calculatorMaster.getActivityName());
		Boolean res = false;
		try {
			if (!ObjectUtils.isEmpty(calculatorMaster)) {
				responsecode = calculatorService.isCalculatorExit(calculatorMaster);
				if (responsecode.getResponseCode().equalsIgnoreCase("200")) {
					res = calculatorService.addCalculatorMasterDetails(calculatorMaster);
					if (res) {
						response.setResponseCode("200");
						if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getChecker()))
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
							response.setResponseMessage("Request Submitted To Admin Checker For Approval");
						} else {
							response.setResponseMessage("Details Saved Successfully");
						}

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
			responsecode.setResponseMessage("Error While  Adding Records!");
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateCalculatorMasterDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateCalculatorMasterDetails(
			@RequestBody CalculatorMasterEntity calculatorMaster) {
		logger.info("In updateCalculator Master  Details -> updateCalculatorMasterDetails()");
		ResponseMessageBean response = new ResponseMessageBean();
		ResponseMessageBean responsecode = new ResponseMessageBean();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(calculatorMaster.getRole_ID().intValue());
		calculatorMaster.setRoleName(roleName);
		calculatorMaster.setAction("EDIT");
		calculatorMaster.setStatusName(
				adminWorkFlowReqUtility.getStatusNameByStatusId(calculatorMaster.getStatusId().intValue()));
		calculatorMaster
				.setProductName(adminWorkFlowReqUtility.getAppNameByAppId(calculatorMaster.getAppId().intValue()));
		calculatorMaster.setCreatedByName(
				adminWorkFlowReqUtility.getCreatedByNameByCreatedId(calculatorMaster.getCreatedby().intValue()));
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(calculatorMaster.getActivityName());
		Boolean res = false;

		try {
			if (!ObjectUtils.isEmpty(calculatorMaster)) {
				responsecode = calculatorService.updateCalculatorExit(calculatorMaster);
				if (responsecode.getResponseCode().equalsIgnoreCase("200")) {
					res = calculatorService.updateCalculatorMasterDetails(calculatorMaster);
					if (res) {
						response.setResponseCode("200");
						if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getChecker()))
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

							response.setResponseMessage("Request Submitted To Admin Checker For Approval");
						} else {
							response.setResponseMessage("Details Updated Successfully");
						}

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

	@RequestMapping(value = "/getCalculatorFormulaById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getCalculatorFormulaById(@RequestBody RequestParamBean requestBean) {
		logger.info("get calculator Formula by id -> getCalculatorFormulaById()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<CalculatorFormulaEntity> calculatorList = calculatorService
					.getCalculatorFormulaById(Integer.parseInt(requestBean.getId1()));

			if (null != calculatorList) {
				res.setResponseCode("200");
				res.setResult(calculatorList);
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

	@RequestMapping(value = "/getCalculatorFormulaDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getCalculatorFormulaDetails() {
		logger.info("get calculator Formula Details  -> getCalculatorFormulaDetails()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<CalculatorFormulaEntity> calculatorList = calculatorService.getCalculatorFormulaDetails();

			if (null != calculatorList) {
				res.setResponseCode("200");
				res.setResult(calculatorList);
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

	@RequestMapping(value = "/addCalculatorFormulaDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> addCalculatorMasterDetails(
			@RequestBody CalculatorFormulaEntity calculatorFormulaData) {
		logger.info("In save Calculator FormulaDetails -> addCalculatorFormulaDetails()");
		ResponseMessageBean response = new ResponseMessageBean();
		Boolean res = false;
		try {
			String roleName = adminWorkFlowReqUtility
					.getRoleNameByRoleId(calculatorFormulaData.getRole_ID().intValue());
			calculatorFormulaData.setRoleName(roleName);
			calculatorFormulaData.setAction("ADD");
			calculatorFormulaData.setStatusName(
					adminWorkFlowReqUtility.getStatusNameByStatusId(calculatorFormulaData.getStatusId().intValue()));
			calculatorFormulaData.setCalculatorname(
					adminWorkFlowReqUtility.getcalciNameById(calculatorFormulaData.getCalculatorId().intValue()));
			calculatorFormulaData.setProductName(
					adminWorkFlowReqUtility.getAppNameByAppId(calculatorFormulaData.getAppId().intValue()));
			calculatorFormulaData.setCreatedByName(adminWorkFlowReqUtility
					.getCreatedByNameByCreatedId(calculatorFormulaData.getCreatedby().intValue()));

			List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
					.isMakerCheckerPresentForReq(calculatorFormulaData.getActivityName());

			if (!ObjectUtils.isEmpty(calculatorFormulaData)) {
				res = calculatorService.addCalculatorFormulaDetails(calculatorFormulaData);
			}
			if (res) {
				response.setResponseCode("200");
				if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
						&& (ApplicationConstants.YESVALUE)
								.equals(Character.toString(activityMasterData.get(0).getChecker()))
						&& (ApplicationConstants.YESVALUE)
								.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
					response.setResponseMessage("Request Submitted To Admin Checker For Approval");
				} else {
					response.setResponseMessage("Details Saved Successfully");
				}
			} else {
				response.setResponseCode("202");
				response.setResponseMessage("Error While Saving Records");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
			response.setResponseCode("500");
			response.setResponseMessage("Error While  Adding Records!");
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateCalculatorFormulaDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateCalculatorFormulaDetails(
			@RequestBody CalculatorFormulaEntity calculatorFormulaData) {
		logger.info("In save Calculator FormulaDetails -> updateCalculatorFormulaDetails()");
		ResponseMessageBean response = new ResponseMessageBean();
		Boolean res = false;
		try {
			String roleName = adminWorkFlowReqUtility
					.getRoleNameByRoleId(calculatorFormulaData.getRole_ID().intValue());
			calculatorFormulaData.setRoleName(roleName);
			calculatorFormulaData.setAction("EDIT");
			calculatorFormulaData.setStatusName(
					adminWorkFlowReqUtility.getStatusNameByStatusId(calculatorFormulaData.getStatusId().intValue()));
			calculatorFormulaData.setCalculatorname(
					adminWorkFlowReqUtility.getcalciNameById(calculatorFormulaData.getCalculatorId().intValue()));
			calculatorFormulaData.setProductName(
					adminWorkFlowReqUtility.getAppNameByAppId(calculatorFormulaData.getAppId().intValue()));
			calculatorFormulaData.setCreatedByName(adminWorkFlowReqUtility
					.getCreatedByNameByCreatedId(calculatorFormulaData.getCreatedby().intValue()));

			List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
					.isMakerCheckerPresentForReq(calculatorFormulaData.getActivityName());
			if (!ObjectUtils.isEmpty(calculatorFormulaData)) {
				res = calculatorService.updateCalculatorFormulaDetails(calculatorFormulaData);
			}
			if (res) {
				response.setResponseCode("200");
				if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
						&& (ApplicationConstants.YESVALUE)
								.equals(Character.toString(activityMasterData.get(0).getChecker()))
						&& (ApplicationConstants.YESVALUE)
								.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
					response.setResponseMessage("Request Submitted To Admin Checker For Approval");
				} else {
					response.setResponseMessage("Details Updated Successfully");
				}
			} else {
				response.setResponseCode("202");
				response.setResponseMessage("Error While Updating Records");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
			response.setResponseCode("500");
			response.setResponseMessage("Error While  Updating Records!");
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
