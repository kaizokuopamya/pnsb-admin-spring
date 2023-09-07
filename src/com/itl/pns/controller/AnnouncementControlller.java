package com.itl.pns.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.itl.pns.annotation.Authorization;
import com.itl.pns.bean.RequestParamBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.constant.ApplicationConstants;
import com.itl.pns.entity.ActivitySettingMasterEntity;
import com.itl.pns.entity.AnnouncementsEntity;
import com.itl.pns.service.AnnouncementService;
import com.itl.pns.util.AdminWorkFlowReqUtility;

/**
 * @author shubham.lokhande
 *
 */
@RestController
@RequestMapping("announcement")
public class AnnouncementControlller {

	private static final Logger logger = LogManager.getLogger(AnnouncementControlller.class);

	@Autowired
	AnnouncementService announcementService;

	@Autowired
	AdminWorkFlowReqUtility adminWorkFlowReqUtility;

	@Value("${bot.image.folder}")
	private String botImageFolder;

	@RequestMapping(value = "/getAnnouncementsById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getAnnouncementsById(@RequestBody RequestParamBean requestBean) {
		logger.info("get Announcements  Details by id ");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<AnnouncementsEntity> announcementList = announcementService
					.getAnnouncementsById(Integer.parseInt(requestBean.getId1()));

			if (null != announcementList) {
				res.setResponseCode("200");
				res.setResult(announcementList);
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

	@RequestMapping(value = "/getAccouncementsDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getAccouncementsDetails(@RequestBody RequestParamBean requestBean) {
		logger.info("get All Announcements  Details ");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<AnnouncementsEntity> announcementList = announcementService
					.getAccouncementsDetails(Integer.parseInt(requestBean.getId1()));

			if (null != announcementList) {
				res.setResponseCode("200");
				res.setResult(announcementList);
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
	
	@Authorization
	@RequestMapping(value = "/saveAnnouncementsDetais", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> saveAnnouncementsDetais(
			@RequestBody AnnouncementsEntity announcementsData) {
		logger.info("In save Announcement  Details ");
		int userStatus = announcementsData.getStatusId().intValue();
		announcementsData.setStatusName(
				adminWorkFlowReqUtility.getStatusNameByStatusId(announcementsData.getStatusId().intValue()));
		announcementsData
				.setProductName(adminWorkFlowReqUtility.getAppNameByAppId(announcementsData.getAppId().intValue()));
		announcementsData.setCreatedByName(
				adminWorkFlowReqUtility.getCreatedByNameByCreatedId(announcementsData.getCreatedby().intValue()));

		ResponseMessageBean response = new ResponseMessageBean();
		Boolean res = false;
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(announcementsData.getRole_ID().intValue());
		announcementsData.setAction("ADD");
		announcementsData.setRoleName(roleName);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(announcementsData.getActivityName());

		try {

			ResponseMessageBean responsecode = new ResponseMessageBean();
			if (!ObjectUtils.isEmpty(announcementsData)) {

				responsecode = announcementService.checkSeqNo(announcementsData);
				if (responsecode.getResponseCode().equalsIgnoreCase("200")) {
					res = announcementService.saveAnnouncementsDetais(announcementsData);

					if (res) {
						announcementService.saveAnnouncementsDetaisToAdminWorkFlow(announcementsData, userStatus);

						response.setResponseCode("200");
						response.setResponseMessage("Details Saved Successfully");
						if ((roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
								|| roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER))
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getChecker()))
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

							if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)) {
								response.setResponseMessage("Request Submitted To Admin Checker For Approval");
							} else {
								response.setResponseMessage("Request Submitted To Corporate Checker For Approval");
							}
						} else {
							response.setResponseMessage("Announcement Details Saved Successfully");
						}
					} else {
						response.setResponseCode("202");
						response.setResponseMessage("Error While Saving Records");
					}
				} else {
					response.setResponseCode("202");
					response.setResponseMessage("Sequence Number Is Already Exist");
				}
			}
		}

		catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateAnnouncementsDetais", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateAnnouncementsDetais(
			@RequestBody AnnouncementsEntity announcementsData) {
		logger.info("In Update Announcement  Details");
		ResponseMessageBean response = new ResponseMessageBean();
		int userStatus = announcementsData.getStatusId().intValue();
		announcementsData.setStatusName(
				adminWorkFlowReqUtility.getStatusNameByStatusId(announcementsData.getStatusId().intValue()));
		announcementsData
				.setProductName(adminWorkFlowReqUtility.getAppNameByAppId(announcementsData.getAppId().intValue()));
		announcementsData.setCreatedByName(
				adminWorkFlowReqUtility.getCreatedByNameByCreatedId(announcementsData.getCreatedby().intValue()));
		announcementsData.setAction("EDIT");
		Boolean res = false;
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(announcementsData.getRole_ID().intValue());
		announcementsData.setRoleName(roleName);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(announcementsData.getActivityName());

		/*
		 * String splitArray[] = announcementsData.getBaseImageSmall().split("#"); File
		 * file1 = new File(botImageFolder+splitArray[0]);
		 * announcementsData.setAnnouncementDescription(splitArray[0]); File file2 = new
		 * File(botImageFolder+announcementsData.getBaseImageLarge());
		 */
		try {

			/*
			 * byte[] fileContent = FileUtils.readFileToByteArray(file1); String
			 * encodedStringSmall = Base64.getEncoder().encodeToString(fileContent);
			 * 
			 * byte[] fileContent1 = FileUtils.readFileToByteArray(file2); String
			 * encodedStringLarge = Base64.getEncoder().encodeToString(fileContent1);
			 * 
			 * announcementsData.setBase64ImageLarge(new
			 * javax.sql.rowset.serial.SerialClob(encodedStringLarge.toCharArray()));
			 * announcementsData.setBase64ImageSmall(new
			 * javax.sql.rowset.serial.SerialClob(encodedStringSmall.toCharArray()));
			 */
			ResponseMessageBean responsecode = new ResponseMessageBean();
			announcementsData.setBase64ImageSmall(announcementsData.getBaseImageSmall());
			announcementsData.setBase64ImageLarge(announcementsData.getBaseImageLarge());
			;
			if (!ObjectUtils.isEmpty(announcementsData)) {
				responsecode = announcementService.updateCheckSeqNo(announcementsData);
				if (responsecode.getResponseCode().equalsIgnoreCase("200")) {
					res = announcementService.updateAnnouncementsDetais(announcementsData);

					if (res) {

						// String
						// image1=EncryptDeryptUtility.clobStringConversion(announcementsData.getBase64ImageLarge());
//						String image1 = announcementsData.getBase64ImageLarge();
//						announcementsData.setBaseImageLarge(image1);
//						announcementsData.setBase64ImageLarge(null);

						// String
						// image2=EncryptDeryptUtility.clobStringConversion(announcementsData.getBase64ImageSmall());
//						String image2 = announcementsData.getBase64ImageSmall();
//						announcementsData.setBaseImageSmall(image2);
//						announcementsData.setBase64ImageSmall(null);
						announcementService.updateAnnouncementsDetaisToAdminWorkFlow(announcementsData, userStatus);
						response.setResponseCode("200");
						if ((roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
								|| roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER))
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getChecker()))
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

							if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)) {
								response.setResponseMessage("Request Submitted To Admin Checker For Approval");
							} else {
								response.setResponseMessage("Request Submitted To Corporate Checker For Approval");
							}

						} else {
							response.setResponseMessage("Announcement Details Updated Successfully");
						}

					} else {
						response.setResponseCode("202");
						response.setResponseMessage("Error While Updating Details");
					}
				} else {
					response.setResponseCode("202");
					response.setResponseMessage("Sequence Number Is Already Exist");
				}
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/getAnnouncementType", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getAnnouncementType() {
		logger.info("get All Announcements Type Details ");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<String> typeList = new ArrayList<>();
			typeList.add("Customer");
			typeList.add("General");

//			if (null != typeList) {
			res.setResponseCode("200");
			res.setResult(typeList);
			res.setResponseMessage("Success");
//			}
//			else {
//				res.setResponseCode("202");
//				res.setResponseMessage("No Records Found");
//			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);

	}

}
