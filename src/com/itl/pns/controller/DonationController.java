package com.itl.pns.controller;

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
import com.itl.pns.dao.DonationDao;
import com.itl.pns.entity.ActivitySettingMasterEntity;
import com.itl.pns.entity.BeneficaryMasterEntity;
import com.itl.pns.entity.Donation;
import com.itl.pns.service.DonationService;
import com.itl.pns.util.AdminWorkFlowReqUtility;

@RestController
@RequestMapping("donation")
public class DonationController {

	private static final Logger logger = Logger.getLogger(DonationController.class);

	@Autowired
	DonationService donationService;

	@Autowired
	AdminWorkFlowReqUtility adminWorkFlowReqUtility;

	@Autowired
	DonationDao donationDao;

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getDonationsList", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getDonations() {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<Donation> getDonationList = donationService.getDonations();
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
			logger.info("Exception:", e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	/**
	 * gets Donation by Id
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/getDonationById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getDonationById(@RequestBody RequestParamBean requestBean) {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<Donation> donation = donationService.getDonationById(Integer.parseInt(requestBean.getId1()));
			if (!ObjectUtils.isEmpty(donation)) {
				res.setResponseCode("200");
				res.setResult(donation);
			} else {
				res.setResponseCode("202");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	/**
	 * Adds new Donation details.
	 * 
	 * @param Donations
	 * @return
	 */
	@RequestMapping(value = "/saveDonationDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> saveDonationDetails(@RequestBody Donation donation) {
		int userStatus = donation.getStatusId().intValue();
		Boolean response = false;
		donation.setBankingType("RETAIL");
		donation.setCreatedByName(
				adminWorkFlowReqUtility.getCreatedByNameByCreatedId(donation.getCreatedby().intValue()));
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(donation.getRole_ID().intValue());
		donation.setStatusName(adminWorkFlowReqUtility.getStatusNameByStatusId(donation.getStatusId().intValue()));
		donation.setRoleName(roleName);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(donation.getActivityName());

		ResponseMessageBean res = new ResponseMessageBean();
		ResponseMessageBean responsecode = new ResponseMessageBean();
		responsecode = donationDao.checkIsAccNoExistForSameTrustName(donation);
		try {
			if (responsecode.getResponseCode().equalsIgnoreCase("200")) {
				response = donationService.saveDonationDetails(donation);
				if (response) {
					donationService.saveDonationToAdminWorkFlow(donation, userStatus);
					res.setResponseCode("200");
					if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
							&& (ApplicationConstants.YESVALUE)
									.equals(Character.toString(activityMasterData.get(0).getChecker()))
							&& (ApplicationConstants.YESVALUE)
									.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

						res.setResponseMessage("Request Submitted To Admin Checker For Approval");
					} else {
						res.setResponseMessage("Details Added Successfully");
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
			logger.info("Exception:", e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	/**
	 * Update Donation details by Id
	 * 
	 * @param donation
	 * @return
	 */
	@RequestMapping(value = "/updateDonationDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateDonationDetails(@RequestBody Donation donation) {
		Boolean response = false;
		donation.setBankingType("RETAIL");
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(donation.getRole_ID().intValue());
		donation.setRoleName(roleName);
		donation.setCreatedByName(
				adminWorkFlowReqUtility.getCreatedByNameByCreatedId(donation.getCreatedby().intValue()));
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(donation.getActivityName());
		donation.setStatusName(adminWorkFlowReqUtility.getStatusNameByStatusId(donation.getStatusId().intValue()));
		ResponseMessageBean res = new ResponseMessageBean();
		ResponseMessageBean responsecode = new ResponseMessageBean();
		responsecode = donationDao.checIsAccNoExistForSameTrustNameWhileUpdate(donation);
		try {
			if (responsecode.getResponseCode().equalsIgnoreCase("200")) {
				response = donationService.updateDonationDetails(donation);
				if (response) {

					res.setResponseCode("200");
					if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
							&& (ApplicationConstants.YESVALUE)
									.equals(Character.toString(activityMasterData.get(0).getChecker()))
							&& (ApplicationConstants.YESVALUE)
									.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

						res.setResponseMessage("Request Submitted To Admin Checker For Approval");
					} else {
						res.setResponseMessage("Details Updated Successfully");
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
			logger.info("Exception:", e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getBeneficaryList", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getBeneficaryList() {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<BeneficaryMasterEntity> getDonationList = donationService.getBeneficaryList();
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
			logger.info("Exception:", e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	/**
	 * gets Donation by Id
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/getBeneficaryById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getBeneficaryById(@RequestBody RequestParamBean requestBean) {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<BeneficaryMasterEntity> donation = donationService
					.getBeneficaryById(Integer.parseInt(requestBean.getId1()));

			if (!ObjectUtils.isEmpty(donation)) {
				res.setResponseCode("200");
				res.setResult(donation);
			} else {
				res.setResponseCode("202");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	/**
	 * Adds new Donation details.
	 * 
	 * @param Donations
	 * @return
	 */
	@RequestMapping(value = "/saveBeneficaryList", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> saveBeneficaryList(@RequestBody BeneficaryMasterEntity donation) {
		int userStatus = donation.getStatusid().intValue();
		Boolean response = false;
		donation.setCreatedByName(
				adminWorkFlowReqUtility.getCreatedByNameByCreatedId(donation.getCreatedby().intValue()));
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(donation.getRole_ID().intValue());
		donation.setStatusName(adminWorkFlowReqUtility.getStatusNameByStatusId(donation.getStatusid().intValue()));
		donation.setRoleName(roleName);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(donation.getActivityName());

		ResponseMessageBean res = new ResponseMessageBean();
		ResponseMessageBean responsecode = new ResponseMessageBean();
		// responsecode = donationDao.checkIsAccNoExistForSameTrustName(donation);
		try {
			responsecode.setResponseCode("200");
			if (responsecode.getResponseCode().equalsIgnoreCase("200")) {
				response = donationService.saveBeneficaryList(donation);
				if (response) {
					donationService.saveBeneficaryToAdminWorkFlow(donation, userStatus);
					res.setResponseCode("200");
					if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
							&& (ApplicationConstants.YESVALUE)
									.equals(Character.toString(activityMasterData.get(0).getChecker()))
							&& (ApplicationConstants.YESVALUE)
									.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

						res.setResponseMessage("Request Submitted To Admin Checker For Approval");
					} else {
						res.setResponseMessage("Details Added Successfully");
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
			logger.info("Exception:", e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	/**
	 * Update Donation details by Id
	 * 
	 * @param donation
	 * @return
	 */
	@RequestMapping(value = "/updateBeneficaryList", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateBeneficaryList(@RequestBody BeneficaryMasterEntity donation) {
		Boolean response = false;
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(donation.getRole_ID().intValue());
		donation.setRoleName(roleName);
		donation.setCreatedByName(
				adminWorkFlowReqUtility.getCreatedByNameByCreatedId(donation.getCreatedby().intValue()));
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(donation.getActivityName());
		donation.setStatusName(adminWorkFlowReqUtility.getStatusNameByStatusId(donation.getStatusid().intValue()));
		ResponseMessageBean res = new ResponseMessageBean();
		ResponseMessageBean responsecode = new ResponseMessageBean();
		// responsecode =
		// donationDao.checIsAccNoExistForSameTrustNameWhileUpdate(donation);
		try {
			responsecode.setResponseCode("200");
			if (responsecode.getResponseCode().equalsIgnoreCase("200")) {
				response = donationService.updateBeneficaryList(donation);
				if (response) {

					res.setResponseCode("200");
					if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
							&& (ApplicationConstants.YESVALUE)
									.equals(Character.toString(activityMasterData.get(0).getChecker()))
							&& (ApplicationConstants.YESVALUE)
									.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

						res.setResponseMessage("Request Submitted To Admin Checker For Approval");
					} else {
						res.setResponseMessage("Details Updated Successfully");
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
			logger.info("Exception:", e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

}
