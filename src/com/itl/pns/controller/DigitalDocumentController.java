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
import com.itl.pns.entity.KycDocumentEntity;
import com.itl.pns.entity.KycFolderEntity;
import com.itl.pns.service.DigitalDocumentService;
import com.itl.pns.util.AdminWorkFlowReqUtility;
import com.itl.pns.util.RandomNumberGenerator;

/**
 * @author shubham.lokhande
 *
 */
@RestController
@RequestMapping("digiDocument")
public class DigitalDocumentController {

	static Logger logger = Logger.getLogger(DigitalDocumentController.class);

	@Autowired
	AdminWorkFlowReqUtility adminWorkFlowReqUtility;

	@Autowired
	DigitalDocumentService digitalDocumentService;

	/**
	 * createFolder(createFolder)
	 * 
	 * @param kycFolderEntity
	 * @return
	 */
	@RequestMapping(value = "/createFolder", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> createFolder(@RequestBody KycFolderEntity kycFolderEntity) {
		logger.info("in DigitalDocumentController -> createFolder()");
		ResponseMessageBean response = new ResponseMessageBean();
		ResponseMessageBean responsecode = new ResponseMessageBean();
		RandomNumberGenerator object = new RandomNumberGenerator();
		String uuid = object.generateRandomStringForOoenKmDoc();
		System.out.println(uuid);
		kycFolderEntity.setUuid(uuid);

		Boolean res = false;
		try {
			if (!ObjectUtils.isEmpty(kycFolderEntity)) {

				responsecode = digitalDocumentService.isFolderNameExist(kycFolderEntity);
				if (responsecode.getResponseCode().equalsIgnoreCase("200")) {
					res = digitalDocumentService.createFolder(kycFolderEntity);
					if (res) {
						response.setResponseCode("200");
						response.setResponseMessage("Folder Created Successfully");

					} else {
						response.setResponseCode("202");
						response.setResponseMessage("Error While Creating Folder");
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

	/**
	 * deleteFolder(kycFolderEntity)
	 * 
	 * @param kycFolderEntity
	 * @return
	 */
	@RequestMapping(value = "/deleteFolder", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> deleteFolder(@RequestBody KycFolderEntity kycFolderEntity) {
		logger.info("in DigitalDocumentController -> deleteFolder()");
		ResponseMessageBean response = new ResponseMessageBean();
		ResponseMessageBean responsecode = new ResponseMessageBean();
		Boolean res = false;
		try {
			if (!ObjectUtils.isEmpty(kycFolderEntity)) {
				res = digitalDocumentService.deleteFolder(kycFolderEntity);
				if (res) {
					response.setResponseCode("200");
					response.setResponseMessage("Folder Deleted Successfully");

				} else {
					response.setResponseCode("202");
					response.setResponseMessage("Error While Deleting Folder");
				}

			}
		} catch (Exception e) {
			logger.info("Exception:", e);
			responsecode.setResponseCode("500");
			responsecode.setResponseMessage("Error While  Adding Records!");
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * createDocument(kycDocumentEntity)
	 * 
	 * @param kycDocumentEntity
	 * @return
	 */
	@RequestMapping(value = "/createDocument", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> createDocument(@RequestBody KycDocumentEntity kycDocumentEntity) {
		logger.info("in DigitalDocumentController -> createDocument()");
		ResponseMessageBean response = new ResponseMessageBean();
		ResponseMessageBean responsecode = new ResponseMessageBean();
		RandomNumberGenerator object = new RandomNumberGenerator();
		String uuid = object.generateRandomStringForOoenKmDoc();
		System.out.println(uuid);
		kycDocumentEntity.setUuid(uuid);

		Boolean res = false;
		try {
			if (!ObjectUtils.isEmpty(kycDocumentEntity)) {

				responsecode = digitalDocumentService.isDocumentNameExist(kycDocumentEntity);
				if (responsecode.getResponseCode().equalsIgnoreCase("200")) {
					res = digitalDocumentService.createDocument(kycDocumentEntity);
					if (res) {
						response.setResponseCode("200");
						response.setResponseMessage("Document Uploaded Successfully");

					} else {
						response.setResponseCode("202");
						response.setResponseMessage("Error While Uploading Document");
					}
				} else {
					response.setResponseCode("202");
					response.setResponseMessage(responsecode.getResponseMessage());
				}
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
			responsecode.setResponseCode("500");
			responsecode.setResponseMessage("Error While  Uploading Document");
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * deleteDocument
	 * 
	 * @param kycDocumentEntity
	 * @return
	 */
	@RequestMapping(value = "/deleteDocument", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> deleteDocument(@RequestBody KycDocumentEntity kycDocumentEntity) {
		logger.info("in DigitalDocumentController -> deleteDocument()");
		ResponseMessageBean response = new ResponseMessageBean();
		ResponseMessageBean responsecode = new ResponseMessageBean();
		Boolean res = false;
		try {
			if (!ObjectUtils.isEmpty(kycDocumentEntity)) {
				res = digitalDocumentService.deleteDocument(kycDocumentEntity);
				if (res) {
					response.setResponseCode("200");
					response.setResponseMessage("Document Deleted Successfully");

				} else {
					response.setResponseCode("202");
					response.setResponseMessage("Error While Deleting Document");
				}

			}
		} catch (Exception e) {
			logger.info("Exception:", e);
			responsecode.setResponseCode("500");
			responsecode.setResponseMessage("Error While  Adding Records!");
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * getFolderList
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getFolderList", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getFolderList() {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
		List<KycFolderEntity> kycFolderEntity = digitalDocumentService.getFolderList();
		
			if (!ObjectUtils.isEmpty(kycFolderEntity)) {
				res.setResponseCode("200");
				res.setResult(kycFolderEntity);
			} else {
				res.setResponseCode("202");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	/**
	 * getDocumentListByFolderId(folderId)
	 * 
	 * @param folderId
	 * @return
	 */
	@RequestMapping(value = "/getDocumentListByFolderId", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getDocumentListByFolderId(@RequestBody RequestParamBean requestBean) {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
		List<KycDocumentEntity> kycDocumentEntity = digitalDocumentService
				.getDocumentListByFolderId(Integer.parseInt(requestBean.getId1()));
		
			if (!ObjectUtils.isEmpty(kycDocumentEntity)) {
				res.setResponseCode("200");
				res.setResult(kycDocumentEntity);
			} else {
				res.setResponseCode("202");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

}
