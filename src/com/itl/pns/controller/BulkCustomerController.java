package com.itl.pns.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.service.BulkCustCreationService;

@RestController
@RequestMapping("bulkCreation")
public class BulkCustomerController {

	@Autowired
	BulkCustCreationService bulkCustCreationService;

	@RequestMapping(value = "/bulkCustomerCreation", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> createBulkCustomerFile(
			@RequestParam("BulkCustomerFile") MultipartFile file1) {
		ResponseMessageBean res = new ResponseMessageBean();
		String isAdded = "";
		try {
		isAdded = bulkCustCreationService.createBulkCustomerFile(file1);
		
		if (isAdded == "success") {
			res.setResponseCode("200");
			res.setResponseMessage("Customer created successfully and username ,password sent to your email");
		} else if (isAdded == "error") {
			res.setResponseCode("500");
			res.setResponseMessage("Error While Creating Customer ");
		} else {
			res.setResult(isAdded);
			res.setResponseCode("202");
			res.setResponseMessage("This Mobile Number :" + isAdded + " Already Exists");
		}
		} catch (Exception e) {
			//logger.info("Exception:", e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);

	}

}
