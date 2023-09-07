package com.itl.pns.corp.controller;

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

import com.itl.pns.bean.CorpDataBean;
import com.itl.pns.bean.CorpMenuAccBean;
import com.itl.pns.bean.CorpMenuAccountBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.corp.dao.CorpUserDao;
import com.itl.pns.corp.entity.CorpAccReqEntity;
import com.itl.pns.corp.entity.CorpCompDataMasterEntity;
import com.itl.pns.corp.entity.CorpCompReqEntity;
import com.itl.pns.corp.entity.CorpMenuEntity;
import com.itl.pns.corp.entity.CorpMenuReqEntity;
import com.itl.pns.corp.entity.CorpUserAccReqEntity;
import com.itl.pns.corp.entity.CorpUserMenuMapEntity;
import com.itl.pns.corp.entity.CorpUserMenuReqEntity;
import com.itl.pns.corp.entity.CorpUserReqEntity;
import com.itl.pns.corp.service.CorpUserService;
import com.itl.pns.util.AdminWorkFlowReqUtility;

/**
 * @author shubham.lokhande
 *
 */
@RestController
@RequestMapping("corpUser")
public class CorpUserController {

	private static final Logger logger = LogManager.getLogger(CorpUserController.class);

	@Autowired
	AdminWorkFlowReqUtility adminWorkFlowReqUtility;

	@Autowired
	CorpUserService CorpUserService;

	@Autowired
	CorpUserDao corpUserDao;

	@RequestMapping(value = "/getCorpCompRequests", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getCorpCompRequests() {
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			List<CorpCompReqEntity> corpCompData = CorpUserService.getCorpCompRequests();
			if (!ObjectUtils.isEmpty(corpCompData)) {
				response.setResponseMessage("Success");
				response.setResponseCode("200");
				response.setResult(corpCompData);
			} else {
				response.setResponseMessage("No Records Found");
				response.setResponseCode("202");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/getCorpCompRequestsById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getCorpCompRequestsById(
			@RequestBody CorpCompReqEntity corpCompReqEntity) {
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			List<CorpCompReqEntity> corpCompData = CorpUserService.getCorpCompRequestsById(corpCompReqEntity);
			if (!ObjectUtils.isEmpty(corpCompData)) {
				response.setResponseMessage("Success");
				response.setResponseCode("200");
				response.setResult(corpCompData);
			} else {
				response.setResponseMessage("No Records Found");
				response.setResponseCode("202");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/getCorpCompRequestsByRrn", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getCorpCompRequestsByRrn(
			@RequestBody CorpCompReqEntity corpCompReqEntity) {
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			List<CorpCompReqEntity> corpCompData = CorpUserService.getCorpCompRequestsByRrn(corpCompReqEntity);
			if (!ObjectUtils.isEmpty(corpCompData)) {
				response.setResponseMessage("Success");
				response.setResponseCode("200");
				response.setResult(corpCompData);
			} else {
				response.setResponseMessage("No Records Found");
				response.setResponseCode("202");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateCorpCompReqData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateCorpCompReqData(
			@RequestBody CorpCompDataMasterEntity corpCompData) {
		ResponseMessageBean response = new ResponseMessageBean();
		boolean isUpdate = false;
		try {
			isUpdate = CorpUserService.updateCorpCompReqData(corpCompData);
			if (isUpdate) {
				response.setResponseMessage("Corp Company Registered Successfully");
				response.setResponseCode("200");

			} else {
				response.setResponseMessage("Error While Corp Company Registration");
				response.setResponseCode("500");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/getCorpMenuByCompanyId", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getCorpMenuByCompanyId(@RequestBody CorpMenuReqEntity CorpMenuData) {
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			List<CorpMenuReqEntity> corpCompData = CorpUserService.getCorpMenuByCompanyId(CorpMenuData);
			if (!ObjectUtils.isEmpty(corpCompData)) {
				response.setResponseMessage("Success");
				response.setResponseCode("200");
				response.setResult(corpCompData);
			} else {
				response.setResponseMessage("No Records Found");
				response.setResponseCode("202");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/getCorpAccByCompanyId", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getCorpAccByCompanyId(@RequestBody CorpAccReqEntity corpAccData) {
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			List<CorpAccReqEntity> corpCompData = CorpUserService.getCorpAccByCompanyId(corpAccData);
			if (!ObjectUtils.isEmpty(corpCompData)) {
				response.setResponseMessage("Success");
				response.setResponseCode("200");
				response.setResult(corpCompData);
			} else {
				response.setResponseMessage("No Records Found");
				response.setResponseCode("202");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/getCorpMenuAndAccCompanyId", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getCorpMenuAndAccCompanyId(@RequestBody CorpMenuReqEntity CorpMenuData) {
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			CorpMenuAccBean corpMenuAccData = new CorpMenuAccBean();
			CorpAccReqEntity corpAccObj = new CorpAccReqEntity();
			corpAccObj.setCorpReqId(CorpMenuData.getCorpReqId());
			List<CorpMenuReqEntity> corpMenuData = CorpUserService.getCorpMenuByCompanyId(CorpMenuData);
			List<CorpAccReqEntity> corpAccData = CorpUserService.getCorpAccByCompanyId(corpAccObj);

			corpMenuAccData.setCorpMenuList(corpMenuData);
			corpMenuAccData.setCorpAccList(corpAccData);
			if (!ObjectUtils.isEmpty(corpMenuAccData)) {
				response.setResponseMessage("Success");
				response.setResponseCode("200");
				response.setResult(corpMenuAccData);
			} else {
				response.setResponseMessage("No Records Found");
				response.setResponseCode("202");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/saveCorpMenuAccData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> saveCorpMenuAccData(@RequestBody CorpMenuAccountBean menuAccData) {
		ResponseMessageBean response = new ResponseMessageBean();
		boolean isDataSaved = false;
		try {
			isDataSaved = CorpUserService.saveCorpMenuAccData(menuAccData);
			if (!ObjectUtils.isEmpty(menuAccData)) {
				response.setResponseMessage("Success");
				response.setResponseCode("200");
				response.setResult(menuAccData);
			} else {
				response.setResponseMessage("No Records Found");
				response.setResponseCode("202");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/verifyAccountNumber", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> verifyAccountNumber(@RequestBody CorpMenuAccBean corpMenuAccData) {
		ResponseMessageBean response = new ResponseMessageBean();
		boolean isAccVerified = false;
		try {
			isAccVerified = CorpUserService.verifyAccountNumber(corpMenuAccData);
			if (!ObjectUtils.isEmpty(isAccVerified)) {
				response.setResponseMessage("Account Verified Successfully");
				response.setResponseCode("200");
				response.setResult(isAccVerified);
			} else {
				CorpDataBean corpData = new CorpDataBean();
				corpData.setRemark("Acoount Verification Failed,Request Rejected.");
				corpData.setReqStatus("Rejected");
				corpData.setCorpCompReqId(corpMenuAccData.getCorpAccList().get(0).getCorpReqId());
				corpUserDao.updateStatusOfCorpRequestData(corpData);
				response.setResponseMessage("Acoount Verification Failed.Request Rejected");
				response.setResponseCode("202");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/saveCorpMenuAccDataList", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> saveCorpMenuAccDataList(@RequestBody CorpMenuAccBean corpMenuAccData) {
		ResponseMessageBean response = new ResponseMessageBean();
		boolean isDataSaved = false;
		try {
			for (CorpMenuReqEntity corpMenuObj : corpMenuAccData.getCorpMenuList()) {
				isDataSaved = corpUserDao.insertToCorpMenuMap(corpMenuObj);
			}

			for (CorpAccReqEntity corpAccObj : corpMenuAccData.getCorpAccList()) {
				isDataSaved = corpUserDao.insertToCorpAccMap(corpAccObj);
			}

			if (isDataSaved) {
				response.setResponseMessage("Success");
				response.setResponseCode("200");

			} else {
				response.setResponseMessage("Error While Saving Records");
				response.setResponseCode("202");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/getAllCorpUsersByCompId", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getAllCorpUsersByCompId(@RequestBody CorpUserReqEntity corpuserReqData) {
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			List<CorpUserReqEntity> corpCompData = CorpUserService.getAllCorpUsersByCompId(corpuserReqData);

			List<CorpUserMenuReqEntity> corpUserMenuList = corpUserDao
					.getMenuListByCorpCompId(corpuserReqData.getCorpid());
			List<CorpUserAccReqEntity> corpUserAccList = corpUserDao
					.getAccountListByCorpCompId(corpuserReqData.getCorpid());

			corpCompData.get(0).setCorpUserMenuData(corpUserMenuList);
			corpCompData.get(0).setCorpUserAccData(corpUserAccList);

			if (!ObjectUtils.isEmpty(corpCompData)) {
				response.setResponseMessage("Success");
				response.setResponseCode("200");
				response.setResult(corpCompData);
			} else {
				response.setResponseMessage("No Records Found");
				response.setResponseCode("202");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/getMenuListByCorpUserId", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getMenuListByCorpUserId(
			@RequestBody CorpUserMenuReqEntity corpUserMenuData) {
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			List<CorpUserMenuReqEntity> corpCompData = CorpUserService.getMenuListByCorpUserId(corpUserMenuData);
			if (!ObjectUtils.isEmpty(corpCompData)) {
				response.setResponseMessage("Success");
				response.setResponseCode("200");
				response.setResult(corpCompData);
			} else {
				response.setResponseMessage("No Records Found");
				response.setResponseCode("202");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/getAccountListByCorpUserId", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getAccountListByCorpUserId(
			@RequestBody CorpUserAccReqEntity corpUserAccData) {
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			List<CorpUserAccReqEntity> corpCompData = CorpUserService.getAccountListByCorpUserId(corpUserAccData);
			if (!ObjectUtils.isEmpty(corpCompData)) {
				response.setResponseMessage("Success");
				response.setResponseCode("200");
				response.setResult(corpCompData);
			} else {
				response.setResponseMessage("No Records Found");
				response.setResponseCode("202");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/getCorpByCompNameCifCorpId", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getCorpByCompNameCifCorpId(
			@RequestBody CorpCompReqEntity corpCompReqEntity) {
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			List<CorpCompReqEntity> corpCompData = CorpUserService.getCorpByCompNameCifCorpId(corpCompReqEntity);
			if (!ObjectUtils.isEmpty(corpCompData)) {
				response.setResponseMessage("Success");
				response.setResponseCode("200");
				response.setResult(corpCompData);
			} else {
				response.setResponseMessage("No Records Found");
				response.setResponseCode("202");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/saveAllCorpData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> saveAllCorpData(@RequestBody CorpDataBean corpData) {
		ResponseMessageBean response = new ResponseMessageBean();
		boolean isDataSaved = false;
		ResponseMessageBean responsecode = new ResponseMessageBean();
		try {
			corpData.setCorpCompReqId(corpData.getCorpCompData().getId());

			if (corpData.getReqStatus().equalsIgnoreCase("Approved")) {
				responsecode = corpUserDao.isCompanyExist(corpData.getCorpCompData());
				if (responsecode.getResponseCode().equalsIgnoreCase("200")) {
					corpUserDao.saveToCorpCompMasterData(corpData);
					corpUserDao.saveToCorpMenuMap(corpData);
					corpUserDao.saveToCorpAccMap(corpData);
					corpUserDao.saveCorpUserMasterData(corpData);
					corpUserDao.saveToCorpUsersMenuMap(corpData);
					corpUserDao.saveToCorpUsersAccMap(corpData);
					corpUserDao.updateStatusOfCorpRequestData(corpData);
					isDataSaved = true;
				} else {
					response.setResponseMessage(responsecode.getResponseMessage());
					response.setResponseCode("500");
				}

			} else {
				corpUserDao.updateStatusOfCorpRequestData(corpData);
				isDataSaved = false;
			}
			if (isDataSaved) {
				response.setResponseMessage("Records Saved Successfully");
				response.setResponseCode("200");
			} else {
				response.setResponseMessage("Request Rejected");
				response.setResponseCode("202");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
