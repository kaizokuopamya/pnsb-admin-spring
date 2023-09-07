package com.itl.pns.corp.controller;

import java.text.SimpleDateFormat;
import java.util.List;

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
import com.itl.pns.corp.service.CorpDonationService;
import com.itl.pns.dao.DonationDao;
import com.itl.pns.entity.ActivitySettingMasterEntity;
import com.itl.pns.entity.Donation;
import com.itl.pns.util.AdminWorkFlowReqUtility;

@RestController
@RequestMapping("corpDonation")
public class CorpDonationController {

	static Logger LOGGER = Logger.getLogger(CorpDonationController.class);

	@Autowired
	CorpDonationService corpDonationService;

	@Autowired
	AdminWorkFlowReqUtility adminWorkFlowReqUtility;

	@Autowired
	DonationDao donationDao;

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getCorpDonationsList", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getCorpDonationsList() {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<Donation> getDonationList = corpDonationService.getCorpDonations();
			getDonationList.forEach(Donation -> {
				String create = new SimpleDateFormat(" dd/MM/YYYY HH:mm:ssZ").format(Donation.getCreatedon());
				Donation.setCreatedDate(create);
			});
			if (!ObjectUtils.isEmpty(getDonationList)) {
				res.setResponseCode("200");
				res.setResult(getDonationList);
			} else {
				res.setResponseCode("202");
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	/**
	 * gets Donation by Id
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/getCorpDonationById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getCorpDonationById(@RequestBody RequestParamBean requestBean) {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<Donation> donation = corpDonationService.getCorpDonationById(Integer.parseInt(requestBean.getId1()));
			if (!ObjectUtils.isEmpty(donation)) {
				res.setResponseCode("200");
				res.setResult(donation);
			} else {
				res.setResponseCode("202");
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	/**
	 * Adds new Donation details.
	 * 
	 * @param Donations
	 * @return
	 */
	@RequestMapping(value = "/saveCorpDonationDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> saveCorpDonationDetails(@RequestBody Donation donation) {
		int userStatus = donation.getStatusId().intValue();
		Boolean response = false;
		donation.setBankingType("CORPORATE");
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(donation.getRole_ID().intValue());
		donation.setStatusName(adminWorkFlowReqUtility.getStatusNameByStatusId(donation.getStatusId().intValue()));
		donation.setCreatedByName(
				adminWorkFlowReqUtility.getCreatedByNameByCreatedId(donation.getCreatedby().intValue()));
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(donation.getActivityName());

		ResponseMessageBean res = new ResponseMessageBean();
		ResponseMessageBean responsecode = new ResponseMessageBean();
		try {
			responsecode = donationDao.checkIsAccNoExistForSameTrustName(donation);
			if (responsecode.getResponseCode().equalsIgnoreCase("200")) {

				response = corpDonationService.saveCorpDonationDetails(donation);
				if (response) {
					corpDonationService.saveCorpDonationToAdminWorkFlow(donation, userStatus);
					res.setResponseCode("200");
					if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)
							&& (ApplicationConstants.YESVALUE)
									.equals(Character.toString(activityMasterData.get(0).getChecker()))
							&& (ApplicationConstants.YESVALUE)
									.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

						res.setResponseMessage("Request Submitted To Admin Checker For Approval");
					} else {
						res.setResponseMessage("Corporate Donation Added Successfully");
					}

				} else {
					res.setResponseCode("500");
					res.setResponseMessage("Error occured while adding the record");
				}
			} else {
				res.setResponseCode("202");
				res.setResponseMessage(responsecode.getResponseMessage());
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	/**
	 * Update Donation details by Id
	 * 
	 * @param donation
	 * @return
	 */
	@RequestMapping(value = "/updateCorpDonationDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateCorpDonationDetails(@RequestBody Donation donation) {
		Boolean response = false;
		donation.setBankingType("CORPORATE");
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(donation.getRole_ID().intValue());
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(donation.getActivityName());
		donation.setCreatedByName(
				adminWorkFlowReqUtility.getCreatedByNameByCreatedId(donation.getCreatedby().intValue()));
		donation.setStatusName(adminWorkFlowReqUtility.getStatusNameByStatusId(donation.getStatusId().intValue()));
		ResponseMessageBean res = new ResponseMessageBean();
		ResponseMessageBean responsecode = new ResponseMessageBean();
		try {
			responsecode = donationDao.checIsAccNoExistForSameTrustNameWhileUpdate(donation);
			if (responsecode.getResponseCode().equalsIgnoreCase("200")) {

				response = corpDonationService.updateCorpDonationDetails(donation);
				if (response) {
					res.setResponseCode("200");
					if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)
							&& (ApplicationConstants.YESVALUE)
									.equals(Character.toString(activityMasterData.get(0).getChecker()))
							&& (ApplicationConstants.YESVALUE)
									.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

						res.setResponseMessage("Request Submitted To Admin Checker For Approval");
					} else {
						res.setResponseMessage("Corporate Donation Updated Successfully");
					}

				} else {
					res.setResponseCode("500");
					res.setResponseMessage("Error Occured While Updating The Record");
				}
			} else {
				res.setResponseCode("202");
				res.setResponseMessage(responsecode.getResponseMessage());
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
}
