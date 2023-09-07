package com.itl.pns.controller;

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
import com.itl.pns.dao.DocumentsTypesMasterDao;
import com.itl.pns.entity.ActivitySettingMasterEntity;
import com.itl.pns.entity.DocumentMasterEntity;
import com.itl.pns.entity.DocumentTypeMasterEntity;
import com.itl.pns.service.DocumentsTypesMasterService;
import com.itl.pns.util.AdminWorkFlowReqUtility;

/**
 * @author sushant.tiple
 *
 */

@RestController
@RequestMapping("documentTypes")
public class DocumentsTypesMasterController {

	private static final Logger logger = Logger.getLogger(DocumentsTypesMasterController.class);

	@Autowired
	DocumentsTypesMasterService documentTypeService;

	@Autowired
	AdminWorkFlowReqUtility adminWorkFlowReqUtility;

	@Autowired
	DocumentsTypesMasterDao documentsTypesMasterDao;

	@RequestMapping(value = "/getDocumentTypeList", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getDocumentType() {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
		List<DocumentTypeMasterEntity> getDocumentTypeList = documentTypeService.getDocumentTypeList();
		
			if (!ObjectUtils.isEmpty(getDocumentTypeList)) {
				res.setResponseCode("200");
				res.setResult(getDocumentTypeList);
			} else {
				res.setResponseCode("202");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	/**
	 * gets Document Type by Id
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/getDocumentTypeById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getDocumentTypeById(@RequestBody RequestParamBean requestBean) {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
		List<DocumentTypeMasterEntity> documentTypeList = documentTypeService
				.getDocumentTypeById(Integer.parseInt(requestBean.getId1()));
		
			if (!ObjectUtils.isEmpty(documentTypeList)) {
				res.setResponseCode("200");
				res.setResult(documentTypeList);
			} else {
				res.setResponseCode("202");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	/**
	 * Adds new document type details.
	 * 
	 * @param DocumentTypeMasterEntity
	 * @return
	 */
	@RequestMapping(value = "/saveDocumentTypeDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> saveDocumentTypeDetails(
			@RequestBody DocumentTypeMasterEntity documentType) {
		int userStatus = documentType.getStatusId().intValue();
		Boolean response = false;
		documentType.setCreatedByName(
				adminWorkFlowReqUtility.getCreatedByNameByCreatedId(documentType.getCreatedBy().intValue()));
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(documentType.getRole_ID().intValue());
		documentType.setRoleName(roleName);
		documentType
				.setStatusName(adminWorkFlowReqUtility.getStatusNameByStatusId(documentType.getStatusId().intValue()));
		documentType.setProductName(adminWorkFlowReqUtility.getAppNameByAppId(documentType.getAppId().intValue()));
		documentType.setAction("ADD");
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(documentType.getActivityName());

		ResponseMessageBean res = new ResponseMessageBean();
		ResponseMessageBean responsecode = new ResponseMessageBean();
		responsecode = documentsTypesMasterDao.isDocumentExist(documentType);
		try {
			if (responsecode.getResponseCode().equalsIgnoreCase("200")) {
				response = documentTypeService.saveDocumentTypeDetails(documentType);

				if (response) {
					res.setResponseCode("200");
					if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
							&& (ApplicationConstants.YESVALUE)
									.equals(Character.toString(activityMasterData.get(0).getChecker()))
							&& (ApplicationConstants.YESVALUE)
									.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

						res.setResponseMessage("Request Submitted To Admin Checker For Approval");
					} else {
						res.setResponseMessage("Document Type  Added Successfully");
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
	 * Update document type details by Id
	 * 
	 * @param documentType
	 * @return
	 */
	@RequestMapping(value = "/updateDocumentTypeDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateDocumentTypeDetails(
			@RequestBody DocumentTypeMasterEntity documentType) {
		Boolean response = false;
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(documentType.getRole_ID().intValue());
		documentType.setCreatedByName(
				adminWorkFlowReqUtility.getCreatedByNameByCreatedId(documentType.getCreatedBy().intValue()));
		documentType.setRoleName(roleName);
		documentType
				.setStatusName(adminWorkFlowReqUtility.getStatusNameByStatusId(documentType.getStatusId().intValue()));
		documentType.setProductName(adminWorkFlowReqUtility.getAppNameByAppId(documentType.getAppId().intValue()));
		documentType.setAction("EDIT");
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(documentType.getActivityName());
		documentType
				.setStatusName(adminWorkFlowReqUtility.getStatusNameByStatusId(documentType.getStatusId().intValue()));
		ResponseMessageBean res = new ResponseMessageBean();
		ResponseMessageBean responsecode = new ResponseMessageBean();

		responsecode = documentsTypesMasterDao.isUpdateDocumentExist(documentType);
		try {
			if (responsecode.getResponseCode().equalsIgnoreCase("200")) {

				response = documentTypeService.updateDocumentTypeDetails(documentType);
				if (response) {
					res.setResponseCode("200");
					if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
							&& (ApplicationConstants.YESVALUE)
									.equals(Character.toString(activityMasterData.get(0).getChecker()))
							&& (ApplicationConstants.YESVALUE)
									.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
						res.setResponseMessage("Request Submitted To Admin Checker For Approval");
					} else {
						res.setResponseMessage("Document Type Updated Successfully");
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

	@RequestMapping(value = "/getDocumentList", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getDocumentList() {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
		List<DocumentMasterEntity> documentData = documentTypeService.getDocumentList();
		
			if (!ObjectUtils.isEmpty(documentData)) {
				res.setResponseCode("200");
				res.setResult(documentData);
			} else {
				res.setResponseCode("202");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	/**
	 * gets Document Type by Id
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/getDocumentById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getDocumentById(@RequestBody RequestParamBean requestBean) {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
		List<DocumentMasterEntity> documentData = documentTypeService
				.getDocumentById(Integer.parseInt(requestBean.getId1()));
		
			if (!ObjectUtils.isEmpty(documentData)) {
				res.setResponseCode("200");
				res.setResult(documentData);
			} else {
				res.setResponseCode("202");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	/**
	 * Adds new document details.
	 * 
	 * @param documentData
	 * @return
	 */
	@RequestMapping(value = "/saveDocumentData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> saveDocumentData(@RequestBody DocumentMasterEntity documentData) {
		Boolean response = false;
		ResponseMessageBean res = new ResponseMessageBean();
		ResponseMessageBean responsecode = new ResponseMessageBean();
		try {
		responsecode = documentTypeService.isDocumentList(documentData);
		
			if (responsecode.getResponseCode().equalsIgnoreCase("200")) {
				response = documentTypeService.saveDocumentData(documentData);
				if (response) {
					res.setResponseCode("200");
					res.setResponseMessage("Document Data Added Successfully");

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
	 * Update document by Id
	 * 
	 * @param documentData
	 * @return
	 */
	@RequestMapping(value = "/updateDocumentData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateDocumentData(@RequestBody DocumentMasterEntity documentData) {
		Boolean response = false;

		ResponseMessageBean res = new ResponseMessageBean();
		ResponseMessageBean responsecode = new ResponseMessageBean();
		try {
		responsecode = documentTypeService.isUpdateDocumentList(documentData);
		
			if (responsecode.getResponseCode().equalsIgnoreCase("200")) {
				response = documentTypeService.updateDocumentData(documentData);
				if (response) {
					res.setResponseCode("200");
					res.setResponseMessage("Document Data Updated Successfully");

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
