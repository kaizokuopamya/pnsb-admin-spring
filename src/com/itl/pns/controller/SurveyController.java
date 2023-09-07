package com.itl.pns.controller;

import java.sql.SQLException;
import java.util.Date;
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
import com.itl.pns.entity.CustomerSurveyEntity;
import com.itl.pns.entity.SurveyAnsMasterEntity;
import com.itl.pns.entity.SurveyMasterEntity;
import com.itl.pns.entity.SurveyQueMasterEntity;
import com.itl.pns.service.SurveyService;
import com.itl.pns.util.AdminWorkFlowReqUtility;

@RestController
@RequestMapping("survey")
public class SurveyController {

	private static final Logger logger = LogManager.getLogger(SurveyController.class);
	@Autowired
	SurveyService surveyService;

	@Autowired
	AdminWorkFlowReqUtility adminWorkFlowReqUtility;

	@RequestMapping(value = "/getActiveSurveyDetails", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getActiveSurveyDetails() {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<SurveyMasterEntity> surveyData = surveyService.getActiveSurveyDetails();

			if (!ObjectUtils.isEmpty(surveyData)) {
				res.setResponseCode("200");
				res.setResult(surveyData);
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

	@RequestMapping(value = "/getSurveyQue", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getSurveyQue() {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<SurveyQueMasterEntity> surveyData = surveyService.getSurveyQue();
			if (!ObjectUtils.isEmpty(surveyData)) {
				res.setResponseCode("200");
				res.setResult(surveyData);
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

	@RequestMapping(value = "/getSurveyAns", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getSurveyAns() {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<SurveyAnsMasterEntity> surveyData = surveyService.getSurveyAns();
			if (!ObjectUtils.isEmpty(surveyData)) {
				res.setResponseCode("200");
				res.setResult(surveyData);
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

	@RequestMapping(value = "/saveCustAnsOfSurvey", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> saveCustAnsOfSurvey(@RequestBody CustomerSurveyEntity custSurveyData) {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			boolean isAdded = surveyService.saveCustAnsOfSurvey(custSurveyData);

			if (isAdded) {
				res.setResponseCode("200");
				res.setResponseMessage("Survey Details Has Been Saved Successfully");
			} else {
				res.setResponseCode("500");
				res.setResponseMessage("Error Occured While Saving Records");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}

		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/getCustSurveyDetails", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getCustSurveyDetails() {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<CustomerSurveyEntity> surveyData = surveyService.getCustSurveyDetails();
			if (!ObjectUtils.isEmpty(surveyData)) {
				res.setResponseCode("200");
				res.setResult(surveyData);
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

	@RequestMapping(value = "/addSurveMasterDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> addSurveMasterDetails(@RequestBody List<SurveyMasterEntity> surveyData) {
		logger.info("In add survey details ->addSurveMasterDetails()");
		ResponseMessageBean res = new ResponseMessageBean();
		ResponseMessageBean responsecode = new ResponseMessageBean();
		for (SurveyMasterEntity surveyMasterDetails : surveyData) {
			try {
				surveyMasterDetails.setSurveyNameClob(
						new javax.sql.rowset.serial.SerialClob(surveyMasterDetails.getSurveyName().toCharArray()));
			} catch (SerialException e) {
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (!ObjectUtils.isEmpty(surveyData)) {
			// responsecode = surveyService.chechSurvey(surveyData);
			responsecode.setResponseCode("200");
			if (responsecode.getResponseCode().equalsIgnoreCase("200")) {
				boolean isAdded = surveyService.addSurveMasterDetails(surveyData);

				List<SurveyMasterEntity> list = null;
				if (isAdded) {
					list = surveyService.getActiveSurveyDetails();

				}
				if (isAdded) {
					res.setResponseCode("200");
					res.setResponseMessage("Survey Details Has Been Saved Successfully");
					res.setResult(list.get(0));
					System.out.println(res);

				} else {
					res.setResponseCode("500");
					res.setResponseMessage("Error Occured While Saving Records");
				}
			} else {
				res.setResponseCode("200");
				res.setResponseMessage("Name Already Exist");
			}
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/addSurveyQue", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> addSurveyQue(@RequestBody SurveyQueMasterEntity surveyQue) {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			boolean isAdded = surveyService.addSurveyQue(surveyQue);

			if (isAdded) {
				res.setResponseCode("200");
				res.setResponseMessage("Survey Question Has Been Saved Successfully");
			} else {
				res.setResponseCode("500");
				res.setResponseMessage("Error Occured While Saving Records");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/addSurveyAns", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> addSurveyAns(@RequestBody SurveyAnsMasterEntity surveyAns) {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			boolean isAdded = surveyService.addSurveyAns(surveyAns);

			if (isAdded) {
				res.setResponseCode("200");
				res.setResponseMessage("Survey Ans Has Been Saved Successfully");

			} else {
				res.setResponseCode("500");
				res.setResponseMessage("Error Occured While Saving Records");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}

		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/addSurveyQueAndAns", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> addSurveyQueAndAns(@RequestBody SurveyAnsMasterEntity surveyAns) {
		ResponseMessageBean res = new ResponseMessageBean();
		List<SurveyQueMasterEntity> surveyQueData = null;
		try {
			SurveyQueMasterEntity surveyQue = new SurveyQueMasterEntity();
			surveyQue.setSurveyQue(surveyAns.getSurveyQue());
			surveyQue.setSurveyId(surveyAns.getSurveyId());
			surveyQue.setCreatedby(surveyAns.getCreatedby());
			surveyQue.setCreatedon(surveyAns.getCreatedon());
			surveyQue.setStatusId(surveyAns.getStatusId());

			boolean isQueAdded = surveyService.addSurveyQue(surveyQue);

			if (isQueAdded) {
				surveyQueData = surveyService.getSurveyQue();
				surveyAns.setSurveyQueId(surveyQueData.get(0).getId());
			}
			boolean isAdded = surveyService.addSurveyAns(surveyAns);

			if (isAdded) {
				res.setResponseCode("200");
				res.setResponseMessage("Survey Question And Answer Has Been Added Successfully");
			} else {
				res.setResponseCode("500");
				res.setResponseMessage("Error Occured While Saving Records");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}

		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/getSurveyMasterDetailsById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getSurveyMasterDetailsById(@RequestBody RequestParamBean requestBean) {
		logger.info(" get Survey Master Details By Id ");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<SurveyMasterEntity> surveyList = surveyService
					.getSurveyMasterDetailsById(Integer.parseInt(requestBean.getId1()));

			if (!ObjectUtils.isEmpty(surveyList)) {
				res.setResponseCode("200");
				res.setResult(surveyList);
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

	@RequestMapping(value = "/getSurveyQueBySurveyId", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getSurveyQueBySurveyId(@RequestBody RequestParamBean requestBean) {
		logger.info(" get Survey Que By Survey Id ");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<SurveyQueMasterEntity> surveyList = surveyService
					.getSurveyQueBySurveyId(Integer.parseInt(requestBean.getId1()));

			if (!ObjectUtils.isEmpty(surveyList)) {
				res.setResponseCode("200");
				res.setResult(surveyList);
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

	@RequestMapping(value = "/getSurveyAnsRequest", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getSurveyAnsByQueSUrveyId(@RequestBody SurveyAnsMasterEntity surveyAns) {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<SurveyAnsMasterEntity> surveyAnsList = surveyService.getSurveyAnsByQueSUrveyId(surveyAns);

			if (!ObjectUtils.isEmpty(surveyAnsList)) {
				res.setResponseCode("200");
				res.setResult(surveyAnsList);
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

	@RequestMapping(value = "/updateSurveMasterDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateSurveMasterDetails(@RequestBody SurveyMasterEntity surveyData) {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			boolean isAdded = surveyService.updateSurveMasterDetails(surveyData);
			if (isAdded) {
				res.setResponseCode("200");
				res.setResponseMessage("Survey Has Been Updated Successfully");
			} else {
				res.setResponseCode("500");
				res.setResponseMessage("Error Occured While Updating Records");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/UpdateSurveyQueAndAns", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> UpdateSurveyQueAndAns(@RequestBody SurveyAnsMasterEntity surveyAns) {
		ResponseMessageBean res = new ResponseMessageBean();

		try {
			SurveyQueMasterEntity surveyQue = new SurveyQueMasterEntity();
			surveyQue.setId(surveyAns.getSurveyQueId());
			surveyQue.setSurveyQue(surveyAns.getSurveyQue());
			surveyQue.setSurveyId(surveyAns.getSurveyId());
			surveyQue.setStatusId(surveyAns.getStatusId());
			surveyQue.setCreatedon(surveyAns.getCreatedon());
			surveyQue.setCreatedby(surveyAns.getCreatedby());

			boolean isQueUpdated = surveyService.updateSurveyQue(surveyQue);

			if (isQueUpdated) {
				surveyAns.setId(surveyAns.getSurveyAnsId());
			}
			boolean ansUpdated = surveyService.updateSurveyAns(surveyAns);

			if (ansUpdated) {

				res.setResponseCode("200");

				res.setResponseMessage("Survey Question And Answers Has Been Updated Successfully");

			} else {
				res.setResponseCode("500");
				res.setResponseMessage("Error Occured While Updating Records");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}

		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/deleteSurvey", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> deleteSurvey(@RequestBody RequestParamBean requestBean) {
		logger.info(" get Survey Master Details By Id ");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			boolean isSurveyDelted = surveyService.deleteSurvey(Integer.parseInt(requestBean.getId1()));
			boolean isSurveyQueDelted = surveyService.deleteSurveyQue(Integer.parseInt(requestBean.getId1()));
			boolean isSurveyAnsDelted = surveyService.deleteSurveyAns(Integer.parseInt(requestBean.getId1()));
			if (isSurveyDelted && isSurveyQueDelted && isSurveyAnsDelted) {
				res.setResponseCode("200");
				res.setResponseMessage("Details Has Been Deleted Successfully");
			} else {
				res.setResponseCode("500");
				res.setResponseMessage("Error Occured While Deleting Records");
			}} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);

	}

}
