package com.itl.pns.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
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

import com.itl.pns.bean.RequestParamBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.entity.Ease5MoreIconsEntity;
import com.itl.pns.entity.Ease5MoreSubIconsEntity;
import com.itl.pns.entity.Ease5QuickAccessEntity;
import com.itl.pns.entity.Ease5QuickSubMenuAccessEntity;
import com.itl.pns.entity.Ease5TermsConditionEntity;
import com.itl.pns.service.Ease5MoreIconsService;
import com.itl.pns.service.Ease5MoreSubIconsService;
import com.itl.pns.service.Ease5QuickAccessService;
import com.itl.pns.service.Ease5QuickSubMenuAccessService;
import com.itl.pns.service.Ease5TermsConditionService;

@RestController
@RequestMapping("ease")
public class Ease5Controller {
	private static final Logger logger = LogManager.getLogger(Ease5Controller.class);

	@Autowired
	private Ease5MoreIconsService ease5MoreIconsService;

	@Autowired
	private Ease5MoreSubIconsService ease5MoreSubIconsService;

	@Autowired
	private Ease5QuickAccessService ease5QuickAccessService;

	@Autowired
	private Ease5QuickSubMenuAccessService ease5QuickSubMenuAccessService;

	@Autowired
	private Ease5TermsConditionService ease5TermsConditionService;

	@RequestMapping(value = "/easeMoreIconsList", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getEaseMoreIconsList(@RequestBody RequestParamBean requestBean) {
		logger.info("In Ease Controller -> EaseMoreIconsList()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<Ease5MoreIconsEntity> easeMoreIconsList = ease5MoreIconsService
					.getEaseMoreIconsList(requestBean.getId1());
			if (!ObjectUtils.isEmpty(easeMoreIconsList)) {
				res.setResponseCode("200");
				res.setResult(easeMoreIconsList);
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

	@RequestMapping(value = "/ease5MoreIconsDelt", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> ease5MoreIconsDelt(@RequestBody RequestParamBean requestBean)
			throws IOException {
		logger.info("In BulkUserDetails Controller-> ease5MoreIconsDelt() :: ");
		ResponseMessageBean res = new ResponseMessageBean();
		String isAdded = "";
		try {

			isAdded = ease5MoreIconsService.ease5MoreIconsDelt(requestBean.getId1(), requestBean.getId2());

			if ("Success" == isAdded && !isAdded.isEmpty()) {
				res.setResponseCode("200");
				res.setResponseMessage("Deleted Successfully");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}

		return new ResponseEntity<>(res, HttpStatus.OK);

	}

	@RequestMapping(value = "/addEase5MoreIconsData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> addEase5MoreIconsData(
			@RequestBody Ease5MoreIconsEntity ease5MoreIconsList) {
		logger.info("In Ease Controller ->addEase5MoreIconsData----------Start");
		ResponseMessageBean res = new ResponseMessageBean();
		String isAdded = "";
		try {
			isAdded = ease5MoreIconsService.addEase5MoreIconsData(ease5MoreIconsList);
			if ("Success" == isAdded && !isAdded.isEmpty()) {
				res.setResponseCode("200");
				res.setResponseMessage("Add Successfully");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);

	}

	@RequestMapping(value = "/getEaseMoreIconsId", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getEaseMoreIconsId(@RequestBody RequestParamBean requestBean) {
		logger.info("In Ease Controller -> getEaseMoreIconsId() :: " + requestBean.getId1());
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<Ease5MoreIconsEntity> easeMoreIconsList = ease5MoreIconsService
					.getEaseMoreIconsId(requestBean.getId1());
			if (!ObjectUtils.isEmpty(easeMoreIconsList)) {
				res.setResponseCode("200");
				res.setResult(easeMoreIconsList);
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

	@RequestMapping(value = "/editEase5MoreIconsData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> editEase5MoreIconsData(
			@RequestBody Ease5MoreIconsEntity ease5MoreIconsList) {
		logger.info("In Ease Controller ->editEase5MoreIconsData----------Start");
		ResponseMessageBean res = new ResponseMessageBean();
		String isAdded = "";
		try {
			isAdded = ease5MoreIconsService.editEase5MoreIconsData(ease5MoreIconsList);
			if ("Success" == isAdded && !isAdded.isEmpty()) {
				res.setResponseCode("200");
				res.setResponseMessage("Update Successfully");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);

	}

	@RequestMapping(value = "/easeMoreSubIconsList", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> easeMoreSubIconsList(@RequestBody RequestParamBean requestBean) {
		logger.info("In Ease Controller -> EaseMoreSubIconsList()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<Ease5MoreSubIconsEntity> easeMoreIconsList = ease5MoreSubIconsService
					.getEaseMoreSubIconsList(requestBean.getId1());
			if (!ObjectUtils.isEmpty(easeMoreIconsList)) {
				res.setResponseCode("200");
				res.setResult(easeMoreIconsList);
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

	@RequestMapping(value = "/ease5MoreSubIconsDelt", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> ease5MoreSubIconsDelt(@RequestBody RequestParamBean requestBean)
			throws IOException {
		logger.info("In BulkUserDetails Controller-> easeMoreSubIconsList() :: ");
		ResponseMessageBean res = new ResponseMessageBean();
		String isAdded = "";
		try {

			isAdded = ease5MoreSubIconsService.ease5MoreSubIconsDelt(requestBean.getId1(), requestBean.getId2());

			if ("Success" == isAdded && !isAdded.isEmpty()) {
				res.setResponseCode("200");
				res.setResponseMessage("Deleted Successfully");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}

		return new ResponseEntity<>(res, HttpStatus.OK);

	}

	@RequestMapping(value = "/addEase5MoreSubIconsData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> addEase5MoreSubIconsData(
			@RequestBody Ease5MoreSubIconsEntity ease5MoreIconsList) {
		logger.info("In Ease Controller ->addEase5MoreSubIconsData----------Start");
		logger.info("In BulkUserDetails Controller-> easeMoreSubIconsList() :: ");
		ResponseMessageBean res = new ResponseMessageBean();
		String isAdded = "";
		try {

			isAdded = ease5MoreSubIconsService.addEase5MoreSubIconsData(ease5MoreIconsList);

			if ("Success" == isAdded && !isAdded.isEmpty()) {
				res.setResponseCode("200");
				res.setResponseMessage("Add Successfully");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}

		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/getEaseMoreSubIconsId", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getEaseMoreSubIconsId(@RequestBody RequestParamBean requestBean) {
		logger.info("In Ease Controller -> getEaseMoreSubIconsId() :: " + requestBean.getId1());
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<Ease5MoreSubIconsEntity> easeMoreIconsList = ease5MoreSubIconsService
					.getEaseMoreSubIconsId(requestBean.getId1());
			if (!ObjectUtils.isEmpty(easeMoreIconsList)) {
				res.setResponseCode("200");
				res.setResult(easeMoreIconsList);
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

	@RequestMapping(value = "/editEase5MoreSubIconsData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> editEase5MoreSubIconsData(
			@RequestBody Ease5MoreSubIconsEntity ease5MoreIconsList) {
		logger.info("In Ease Controller ->editEase5MoreSubIconsData----------Start");
		logger.info("In BulkUserDetails Controller-> easeMoreSubIconsList() :: ");
		ResponseMessageBean res = new ResponseMessageBean();
		String isAdded = "";
		try {

			isAdded = ease5MoreSubIconsService.editEase5MoreSubIconsData(ease5MoreIconsList);

			if ("Success" == isAdded && !isAdded.isEmpty()) {
				res.setResponseCode("200");
				res.setResponseMessage("Update Successfully");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}

		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/ease5QuickAccessList", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getEase5QuickAccessList(@RequestBody RequestParamBean requestBean) {
		logger.info("In Ease Controller -> getEase5QuickAccessList()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<Ease5QuickAccessEntity> easeMoreIconsList = ease5QuickAccessService
					.getEase5QuickAccessList(requestBean.getId1());
			if (!ObjectUtils.isEmpty(easeMoreIconsList)) {
				res.setResponseCode("200");
				res.setResult(easeMoreIconsList);
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

	@RequestMapping(value = "/ease5QuickAccessDelt", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> ease5QuickAccessDelt(@RequestBody RequestParamBean requestBean)
			throws IOException {
		logger.info("In BulkUserDetails Controller-> ease5QuickAccessDelt() :: ");
		ResponseMessageBean res = new ResponseMessageBean();
		String isAdded = "";
		try {

			isAdded = ease5QuickAccessService.ease5QuickAccessDelt(requestBean.getId1(), requestBean.getId2());

			if ("Success" == isAdded && !isAdded.isEmpty()) {
				res.setResponseCode("200");
				res.setResponseMessage("Deleted Successfully");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}

		return new ResponseEntity<>(res, HttpStatus.OK);

	}

	@RequestMapping(value = "/addEase5QuickAccessData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> addEase5QuickAccess(
			@RequestBody Ease5QuickAccessEntity ease5QuickAccessList) {
		logger.info("In Ease Controller ->addEase5MoreIconsData----------Start");
		ResponseMessageBean res = new ResponseMessageBean();
		String isAdded = "";
		try {
			isAdded = ease5QuickAccessService.addEase5QuickAccess(ease5QuickAccessList);
			if ("Success" == isAdded && !isAdded.isEmpty()) {
				res.setResponseCode("200");
				res.setResponseMessage("Add Successfully");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);

	}

	@RequestMapping(value = "/getEase5QuickAccessId", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getEase5QuickAccessId(@RequestBody RequestParamBean requestBean) {
		logger.info("In Ease Controller -> getEase5QuickAccessId() :: " + requestBean.getId1());
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<Ease5QuickAccessEntity> easeMoreIconsList = ease5QuickAccessService
					.getEase5QuickAccessId(requestBean.getId1());
			if (!ObjectUtils.isEmpty(easeMoreIconsList)) {
				res.setResponseCode("200");
				res.setResult(easeMoreIconsList);
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

	@RequestMapping(value = "/editEase5QuickAccessData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> editEase5QuickAccessData(
			@RequestBody Ease5QuickAccessEntity ease5QuickAccessList) {
		logger.info("In Ease Controller ->editEase5QuickAccessData----------Start");
		ResponseMessageBean res = new ResponseMessageBean();
		String isAdded = "";
		try {
			isAdded = ease5QuickAccessService.editEase5QuickAccessData(ease5QuickAccessList);
			logger.info("ease5QuickAccessList " + ease5QuickAccessList);
			if ("Success" == isAdded && !isAdded.isEmpty()) {
				res.setResponseCode("200");
				res.setResponseMessage("Update Successfully");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);

	}

	@RequestMapping(value = "/ease5QuickSubMenuAccessList", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> ease5QuickSubMenuAccessList(@RequestBody RequestParamBean requestBean) {
		logger.info("In Ease Controller -> ease5QuickSubMenuAccessList()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<Ease5QuickSubMenuAccessEntity> easeMoreIconsList = null;
			if (!ObjectUtils.isEmpty(requestBean)) {
				easeMoreIconsList = ease5QuickSubMenuAccessService
						.getEase5QuickSubMenuAccessList(BigDecimal.valueOf(Long.valueOf(requestBean.getId1())));
			}

			if (!ObjectUtils.isEmpty(easeMoreIconsList)) {
				res.setResponseCode("200");
				res.setResult(easeMoreIconsList);
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

	@RequestMapping(value = "/ease5QuickSubMenuAccessActiveInactiveDelete", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> ease5QuickSubMenuAccessActiveInactiveDelete(
			@RequestBody RequestParamBean requestBean) throws IOException {
		logger.info("In BulkUserDetails Controller-> ease5QuickSubMenuAccessActiveInactiveDelete() :: ");
		ResponseMessageBean res = new ResponseMessageBean();
		String isAdded = "";
		try {

			isAdded = ease5QuickSubMenuAccessService.ease5QuickSubMenuAccessDelt(requestBean.getId1(),
					requestBean.getId2());

			if ("Success" == isAdded && !isAdded.isEmpty()) {
				res.setResponseCode("200");
				if ("10".equalsIgnoreCase(requestBean.getId2())) {
					res.setResponseMessage("Deleted Successfully");
				} else if ("0".equalsIgnoreCase(requestBean.getId2())) {
					res.setResponseMessage("Inactive Successfully");
				} else {
					res.setResponseMessage("Active Successfully");
				}
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}

		return new ResponseEntity<>(res, HttpStatus.OK);

	}

	@RequestMapping(value = "/addEase5QuickSubMenuAccessData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> addEase5QuickSubMenuAccessData(
			@RequestBody Ease5QuickSubMenuAccessEntity ease5QuickSubMenuAccessEntity) {
		logger.info("In Ease Controller ->addEase5QuickSubMenuAccessData----------Start");
		ResponseMessageBean res = new ResponseMessageBean();
		String isAdded = "";
		try {
			isAdded = ease5QuickSubMenuAccessService.addEase5QuickSubMenuAccess(ease5QuickSubMenuAccessEntity);
			if ("Success" == isAdded && !isAdded.isEmpty()) {
				res.setResponseCode("200");
				res.setResponseMessage("Add Successfully");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);

	}

	@RequestMapping(value = "/getEase5QuickSubMenuAccessId", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getEase5QuickSubMenuAccessId(@RequestBody RequestParamBean requestBean) {
		logger.info("In Ease Controller -> getEase5QuickSubMenuAccessId() :: " + requestBean.getId1());
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<Ease5QuickSubMenuAccessEntity> easeMoreIconsList = ease5QuickSubMenuAccessService
					.getEase5QuickSubMenuAccessId(requestBean.getId1());
			if (!ObjectUtils.isEmpty(easeMoreIconsList)) {
				res.setResponseCode("200");
				res.setResult(easeMoreIconsList);
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

	@RequestMapping(value = "/updateEase5QuickSubMenuAccessData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateEase5QuickSubMenuAccessData(
			@RequestBody Ease5QuickSubMenuAccessEntity ease5QuickAccessList) {
		logger.info("In Ease Controller ->updateEase5QuickSubMenuAccessData----------Start");
		ResponseMessageBean res = new ResponseMessageBean();
		String isAdded = "";
		try {
			isAdded = ease5QuickSubMenuAccessService.editEase5QuickSubMenuAccessData(ease5QuickAccessList);
			if ("Success" == isAdded && !isAdded.isEmpty()) {
				res.setResponseCode("200");
				res.setResponseMessage("Update Successfully");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);

	}

	@RequestMapping(value = "/ease5TermsConditionList", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> ease5TermsConditionList(@RequestBody RequestParamBean requestBean) {
		logger.info("In Ease Controller -> ease5TermsConditionList()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<Ease5TermsConditionEntity> easeMoreIconsList = ease5TermsConditionService
					.getEase5TermsConditionList(requestBean.getId1());
			
			for(Ease5TermsConditionEntity easeMoreIcons: easeMoreIconsList) {
				StringBuilder sb= new StringBuilder();
				if(!ObjectUtils.isEmpty(easeMoreIcons.getTermsCondition1())) 
					sb.append(easeMoreIcons.getTermsCondition1()).append(" ");
				if(!ObjectUtils.isEmpty(easeMoreIcons.getTermsCondition2())) 
					sb.append(easeMoreIcons.getTermsCondition2()).append(" ");
				if(!ObjectUtils.isEmpty(easeMoreIcons.getTermsCondition3())) 
					sb.append(easeMoreIcons.getTermsCondition3()).append(" ");
				if(!ObjectUtils.isEmpty(easeMoreIcons.getTermsCondition4())) 
					sb.append(easeMoreIcons.getTermsCondition4()).append(" ");
				if(!ObjectUtils.isEmpty(easeMoreIcons.getTermsCondition5())) 
					sb.append(easeMoreIcons.getTermsCondition5()).append(" ");
				if(!ObjectUtils.isEmpty(easeMoreIcons.getTermsCondition6())) 
					sb.append(easeMoreIcons.getTermsCondition6()).append(" ");
				if(!ObjectUtils.isEmpty(easeMoreIcons.getTermsCondition7())) 
					sb.append(easeMoreIcons.getTermsCondition7()).append(" ");
				if(!ObjectUtils.isEmpty(easeMoreIcons.getTermsCondition8())) 
					sb.append(easeMoreIcons.getTermsCondition8());
				
				easeMoreIcons.setTermsCondition(sb.toString());
			}
			
//			easeMoreIconsList.stream()
//					.forEach(etd -> etd.setTermsCondition((etd.getTermsCondition1()).append(" ").append(etd.getTermsCondition2()).append(" ")
//									.append(etd.getTermsCondition3()).append(" ").append(etd.getTermsCondition4()).append(" ")
//									.append(etd.getTermsCondition5()).append(" ").append(etd.getTermsCondition6()).append(" ")
//									.append(etd.getTermsCondition7()).append(" ").append(etd.getTermsCondition8()).toString()));

			if (!ObjectUtils.isEmpty(easeMoreIconsList)) {
				res.setResponseCode("200");
				res.setResult(easeMoreIconsList);
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

	@RequestMapping(value = "/ease5TermsConditionActiveInactiveDelete", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> ease5TermsConditionActiveInactiveDelete(
			@RequestBody RequestParamBean requestBean) throws IOException {
		logger.info("In BulkUserDetails Controller-> ease5TermsConditionActiveInactiveDelete() :: ");
		ResponseMessageBean res = new ResponseMessageBean();
		String isAdded = "";
		try {

			isAdded = ease5TermsConditionService.ease5TermsConditionDelt(requestBean.getId1(), requestBean.getId2());

			if ("Success" == isAdded && !isAdded.isEmpty()) {
				res.setResponseCode("200");
				if ("10".equalsIgnoreCase(requestBean.getId2())) {
					res.setResponseMessage("Deleted Successfully");
				} else if ("0".equalsIgnoreCase(requestBean.getId2())) {
					res.setResponseMessage("Inactive Successfully");
				} else {
					res.setResponseMessage("Active Successfully");
				}

			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}

		return new ResponseEntity<>(res, HttpStatus.OK);

	}

	@RequestMapping(value = "/addEase5TermsConditionData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> addEase5TermsConditionData(
			@RequestBody Ease5TermsConditionEntity ease5TermsConditionList) {
		logger.info("In Ease Controller ->addEase5TermsConditionData----------Start");
		ResponseMessageBean res = new ResponseMessageBean();
		String isAdded = "";
		try {
			isAdded = ease5TermsConditionService.addEase5TermsCondition(ease5TermsConditionList);
			if ("Success" == isAdded && !isAdded.isEmpty()) {
				res.setResponseCode("200");
				res.setResponseMessage("Add Successfully");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);

	}

	@RequestMapping(value = "/getEase5TermsConditionId", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getEase5TermsConditionId(@RequestBody RequestParamBean requestBean) {
		logger.info("In Ease Controller -> getEase5TermsConditionId() :: " + requestBean.getId1());
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<Ease5TermsConditionEntity> easeMoreIconsList = ease5TermsConditionService
					.getEase5TermsConditionId(requestBean.getId1());
			
			
			for(Ease5TermsConditionEntity easeMoreIcons: easeMoreIconsList) {
				StringBuilder sb= new StringBuilder();
				if(!ObjectUtils.isEmpty(easeMoreIcons.getTermsCondition1())) 
					sb.append(easeMoreIcons.getTermsCondition1()).append(" ");
				if(!ObjectUtils.isEmpty(easeMoreIcons.getTermsCondition2())) 
					sb.append(easeMoreIcons.getTermsCondition2()).append(" ");
				if(!ObjectUtils.isEmpty(easeMoreIcons.getTermsCondition3())) 
					sb.append(easeMoreIcons.getTermsCondition3()).append(" ");
				if(!ObjectUtils.isEmpty(easeMoreIcons.getTermsCondition4())) 
					sb.append(easeMoreIcons.getTermsCondition4()).append(" ");
				if(!ObjectUtils.isEmpty(easeMoreIcons.getTermsCondition5())) 
					sb.append(easeMoreIcons.getTermsCondition5()).append(" ");
				if(!ObjectUtils.isEmpty(easeMoreIcons.getTermsCondition6())) 
					sb.append(easeMoreIcons.getTermsCondition6()).append(" ");
				if(!ObjectUtils.isEmpty(easeMoreIcons.getTermsCondition7())) 
					sb.append(easeMoreIcons.getTermsCondition7()).append(" ");
				if(!ObjectUtils.isEmpty(easeMoreIcons.getTermsCondition8())) 
					sb.append(easeMoreIcons.getTermsCondition8());
				
				easeMoreIcons.setTermsCondition(sb.toString());
			}
//			easeMoreIconsList.stream()
//			.forEach(etd -> etd.setTermsCondition(new StringBuilder(etd.getTermsCondition1()).append(" ").append(etd.getTermsCondition2()).append(" ")
//							.append(etd.getTermsCondition3()).append(" ").append(etd.getTermsCondition4()).append(" ")
//							.append(etd.getTermsCondition5()).append(" ").append(etd.getTermsCondition6()).append(" ")
//							.append(etd.getTermsCondition7()).append(" ").append(etd.getTermsCondition8()).toString()));
			
			if (!ObjectUtils.isEmpty(easeMoreIconsList)) {
				res.setResponseCode("200");
				res.setResult(easeMoreIconsList);
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

	@RequestMapping(value = "/updateEase5TermsConditionData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateEase5TermsConditionData(
			@RequestBody Ease5TermsConditionEntity ease5TermsConditionList) {
		logger.info("In Ease Controller ->editEase5TermsConditionData----------Start");
		ResponseMessageBean res = new ResponseMessageBean();
		String isAdded = "";
		try {
			isAdded = ease5TermsConditionService.editEase5TermsConditionData(ease5TermsConditionList);
			if ("Success" == isAdded && !isAdded.isEmpty()) {
				res.setResponseCode("200");
				res.setResponseMessage("Update Successfully");
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
