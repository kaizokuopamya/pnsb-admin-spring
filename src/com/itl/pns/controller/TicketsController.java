package com.itl.pns.controller;

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

import com.itl.pns.bean.RequestParamBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.bean.TicketBean;
import com.itl.pns.constant.ApplicationConstants;
import com.itl.pns.entity.ActivitySettingMasterEntity;
import com.itl.pns.entity.BankTokenEntity;
import com.itl.pns.entity.CustomerTokenEntity;
import com.itl.pns.service.TicketsService;
import com.itl.pns.util.AdminWorkFlowReqUtility;
import com.itl.pns.util.EncryptorDecryptor;
import com.itl.pns.util.Utils;

@RestController
@RequestMapping("tickets")
public class TicketsController {

	private static final Logger logger = LogManager.getLogger(TicketsController.class);

	@Autowired
	TicketsService ticketService;

	@Autowired
	AdminWorkFlowReqUtility adminWorkFlowReqUtility;

	@RequestMapping(value = "/getTickets", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getTickets(@RequestBody RequestParamBean requestBean, HttpServletRequest httpRequest) {
		logger.info("UserActivity :: Get token : userId : " + Utils.getUserId(httpRequest));
		
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<TicketBean> ticketLst = ticketService.getTickets(Integer.parseInt(requestBean.getId1()));

			if (!ObjectUtils.isEmpty(ticketLst)) {
				res.setResponseCode("200");
				res.setResult(ticketLst);
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

	@RequestMapping(value = "/getTicketsById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getTicketsById(@RequestBody RequestParamBean requestBean) {
		logger.info("get tickets by id ");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<TicketBean> ticketLst = ticketService.getTicketsById(Integer.parseInt(requestBean.getId1()));

			if (!ObjectUtils.isEmpty(ticketLst)) {
				res.setResponseCode("200");
				res.setResult(ticketLst);
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

	@RequestMapping(value = "/updateTicketStatus", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateTicketStatus(@RequestBody TicketBean ticketBean) {
		logger.info("update ticket satus");
		ResponseMessageBean response = new ResponseMessageBean();
		Boolean res = false;
		try {
			String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(ticketBean.getRole_ID().intValue());
			ticketBean.setSTATUSNAME(
					adminWorkFlowReqUtility.getStatusNameByStatusId(ticketBean.getSTATUSID().intValue()));
			ticketBean.setRoleName(roleName);
			ticketBean.setProductName(adminWorkFlowReqUtility.getAppNameByAppId(ticketBean.getAPPID().intValue()));
			ticketBean.setCreatedByName(
					adminWorkFlowReqUtility.getCreatedByNameByCreatedId(ticketBean.getCREATEDBY().intValue()));
			ticketBean.setAction("EDIT");

			List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
					.isMakerCheckerPresentForReq(ticketBean.getActivityName());

			if (!ObjectUtils.isEmpty(ticketBean)) {
				res = ticketService.updateTicketStatus(ticketBean);
			}
			if (res) {
				response.setResponseCode("200");
				if ((roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
						|| roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER))
						&& (ApplicationConstants.YESVALUE)
								.equals(Character.toString(activityMasterData.get(0).getChecker()))
						&& (ApplicationConstants.YESVALUE)
								.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

					response.setResponseMessage("Request Submitted To Admin Checker For Approval");
				} else {
					response.setResponseMessage("Ticket Details Saved Successfully");
				}

			} else {
				response.setResponseCode("202");
				response.setResponseMessage("Error While Generating Token");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/generateTokenRequest", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> generateTokenRequest(@RequestBody TicketBean ticketBean) {
		logger.info("generateTokenRequest ticket...." + ticketBean.toString());
		ResponseMessageBean response = new ResponseMessageBean();
		Boolean res = false;
		try {

			if (!ObjectUtils.isEmpty(ticketBean)) {
				res = ticketService.generateTokenRequest(ticketBean);
			}
			if (res) {
				response.setResponseCode("200");
				response.setResponseMessage("Token Generated Successfully");
			} else {
				response.setResponseCode("202");
				response.setResponseMessage("Error While Generating Token");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/getAllTicketsList", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getAllTicketsList() {
		logger.info("get tickets ->getAllTicketsList()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<TicketBean> ticketLst = ticketService.getAllTicketsList();

			if (null != ticketLst) {
				res.setResponseCode("200");
				res.setResult(ticketLst);
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

	@RequestMapping(value = "/rejectTokenRequest", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> rejectTokenRequest(@RequestBody TicketBean ticketBean) {
		logger.info("Reject token request......." + ticketBean.toString());
		ResponseMessageBean response = new ResponseMessageBean();
		Boolean res = false;
		try {
			if (!ObjectUtils.isEmpty(ticketBean)) {
				res = ticketService.rejectTokenRequest(ticketBean);
			}
			if (res) {
				response.setResponseCode("200");
				response.setResponseMessage("Token Has Been Rejected");
			} else {
				response.setResponseCode("202");
				response.setResponseMessage("Error While Rejecting Token");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/getBankTokenRequest", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getBankTokenRequest(@RequestBody RequestParamBean requestBean) {
		logger.info("get Bank token ->getBankTokenRequest()........" + requestBean.toString());
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<BankTokenEntity> ticketLst = ticketService.getBankTokenRequest(requestBean);			
			for (BankTokenEntity ticketObj : ticketLst) {

				if (null != ticketObj.getMobile()) {
					ticketObj.setMobile(EncryptorDecryptor.decryptData(ticketObj.getMobile()));
				} else {
					ticketObj.setMobile("0000000000");
				}
				if (null != ticketObj.getCustomername()) {
					ticketObj.setCustomername(EncryptorDecryptor.decryptData(ticketObj.getCustomername()));
				}
				if (null != ticketObj.getCif()) {
					ticketObj.setCif(EncryptorDecryptor.decryptData(ticketObj.getCif()));
				}
				ticketObj.setRoleName("Retail");
			}			
			if (!ObjectUtils.isEmpty(ticketLst)) {
				res.setResponseCode("200");
				res.setResult(ticketLst);
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

	@RequestMapping(value = "/generateBankToken", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> generateBankToken(@RequestBody BankTokenEntity bankToken,
			HttpServletRequest httpRequest) {
		logger.info("generate bank  token request..." + bankToken.toString());
		ResponseMessageBean response = new ResponseMessageBean();
		String res = "";

		try {
			bankToken
					.setStatusName(adminWorkFlowReqUtility.getStatusNameByStatusId(bankToken.getStatusId().intValue()));
			bankToken.setAction("GENERATE TOKEN");

			List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
					.isMakerCheckerPresentForReq(bankToken.getActivityName());
			if (!ObjectUtils.isEmpty(bankToken)) {
				bankToken.setCreatedUpdatedBy(Utils.getUpdatedBy(httpRequest));
				response = ticketService.generateBankToken(bankToken);
				// System.out.println(res);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/rejectBankToken", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> rejectBankToken(@RequestBody BankTokenEntity bankToken) {
		logger.info("rejectBankToken request..." + bankToken.toString());
		ResponseMessageBean response = new ResponseMessageBean();
		Boolean res = false;
		try {
			if (!ObjectUtils.isEmpty(bankToken)) {
				res = ticketService.rejectBankToken(bankToken);
			}
			if (res) {
				response.setResponseCode("200");
				response.setResponseMessage("Token Has Been Rejected");
			} else {
				response.setResponseCode("202");
				response.setResponseMessage("Error While Rejecting Token");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/getBankTokenById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getBankTokenById(@RequestBody RequestParamBean requestBean) {
		logger.info("get tickets by id ");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<BankTokenEntity> ticketLst = ticketService.getBankTokenById(Integer.parseInt(requestBean.getId1()));

			for (BankTokenEntity ticketObj : ticketLst) {
				if (null != ticketObj.getMobile()) {
					ticketObj.setMobile(EncryptorDecryptor.decryptData(ticketObj.getMobile()));
				} else {
					ticketObj.setMobile("0000000000");
				}
				if (ticketObj.getCustomername() != null) {
					ticketObj.setCustomername(EncryptorDecryptor.decryptData(ticketObj.getCustomername()));
				}
				if (null != ticketObj.getCif() && ticketObj.getCif().contains("=")) {
					ticketObj.setCif(EncryptorDecryptor.decryptData(ticketObj.getCif()));
				}

			}

			if (null != ticketLst) {
				res.setResponseCode("200");
				res.setResult(ticketLst);
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

	@RequestMapping(value = "/getBankTokenByRefNo", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getBankTokenByRefNo(@RequestBody RequestParamBean requestBean) {
		logger.info("get tickets by id ");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<BankTokenEntity> ticketLst = ticketService.getBankTokenByRefNo(Long.parseLong(requestBean.getId1()));

			if (null != ticketLst) {
				res.setResponseCode("200");
				res.setResult(ticketLst);
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

	@RequestMapping(value = "/getAllCustomerTokenRequest", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getCustomerTokenRequest() {
		logger.info("get Bank token ->getAllBankTokenRequests()");

		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<CustomerTokenEntity> bankTokenList = ticketService.getAllCustomerTokenRequest();

			if (!ObjectUtils.isEmpty(bankTokenList)) {
				res.setResponseCode("200");
				res.setResult(bankTokenList);
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

	@RequestMapping(value = "/generateCustomerToken", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> generateCustomerToken(@RequestBody CustomerTokenEntity custToken) {
		logger.info("generate bank  token by Admin");
		ResponseMessageBean response = new ResponseMessageBean();
		Boolean res = false;
		custToken.setAppId(custToken.getChannelId());
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(custToken.getRole_ID().intValue());
		custToken.setRoleName(roleName);
		custToken.setValidatedOnChannelId(custToken.getAppId());
		custToken.setStatusName(adminWorkFlowReqUtility.getStatusNameByStatusId(custToken.getStatusId().intValue()));
		custToken.setAppName(adminWorkFlowReqUtility.getAppNameByAppId(custToken.getAppId().intValue()));
		custToken.setType(custToken.getAppName());
		custToken.setCreatedByName(adminWorkFlowReqUtility.getCreatedByNameByCreatedId(custToken.getCreatedBy()));
		custToken.setAction("TOKEN GENERATE");
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(custToken.getActivityName());
		try {
			if (!ObjectUtils.isEmpty(custToken)) {
				res = ticketService.generateCustomerToken(custToken);
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

					response.setResponseMessage("Token Generated Successfully");
				}
			} else {
				response.setResponseCode("202");
				response.setResponseMessage("Error While Generating Token,Please Try Again");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/rejectBankTokenByAdmin", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> rejectBankTokenByAdmin(@RequestBody CustomerTokenEntity bankToken) {
		logger.info("Reject Token By Admin");
		ResponseMessageBean response = new ResponseMessageBean();
		Boolean res = false;
		try {
			if (!ObjectUtils.isEmpty(bankToken)) {
				res = ticketService.rejectBankTokenByAdmin(bankToken);
			}
			if (res) {
				response.setResponseCode("200");
				response.setResponseMessage("Token Has Been Rejected");
			} else {
				response.setResponseCode("202");
				response.setResponseMessage("Error While Rejecting Token");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/getAdminBankTokenById/{id}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getAdminBankTokenById(@RequestBody RequestParamBean requestBean) {
		logger.info("get admin bank token by id ");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<CustomerTokenEntity> ticketLst = ticketService
					.getAdminBankTokenById(Integer.parseInt(requestBean.getId1()));

			if (null != ticketLst) {
				res.setResponseCode("200");
				res.setResult(ticketLst);
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
