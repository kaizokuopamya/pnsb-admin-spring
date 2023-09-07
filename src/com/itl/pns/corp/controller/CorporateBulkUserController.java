package com.itl.pns.corp.controller;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.constant.ApplicationConstants;
import com.itl.pns.corp.entity.CorpUserEntity;
import com.itl.pns.corp.service.CorpBulkUserService;
import com.itl.pns.entity.ActivitySettingMasterEntity;
import com.itl.pns.util.AdminWorkFlowReqUtility;

@RestController
@RequestMapping("corpBulkUser")
public class CorporateBulkUserController {

	private static final Logger logger = LogManager.getLogger(CorporateBulkUserController.class);

	@Autowired
	CorpBulkUserService CorpBulkUserService;

	@Autowired
	AdminWorkFlowReqUtility adminWorkFlowReqUtility;

	@RequestMapping(value = "/saveBulkCorpUsers", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> saveBulkCorpUsers(@RequestBody List<CorpUserEntity> custDataList) {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(custDataList.get(0).getRole_ID().intValue());
			List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
					.isMakerCheckerPresentForReq(custDataList.get(0).getActivityName());
			boolean isAdded = CorpBulkUserService.saveBulkCorpUsers(custDataList);

			if (isAdded) {
				res.setResponseCode("200");
				if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)
						&& (ApplicationConstants.YESVALUE)
								.equals(Character.toString(activityMasterData.get(0).getChecker()))
						&& (ApplicationConstants.YESVALUE)
								.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
					res.setResponseMessage("Request Submitted To Corporate Checker For Approval");
				} else {
					res.setResponseMessage("User Details Has Been Saved Successfully");
				}

			} else {
				res.setResponseCode("500");
				res.setResponseMessage("Error Ocured While Saving The Record");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}

		return new ResponseEntity<>(res, HttpStatus.OK);
	}

}
