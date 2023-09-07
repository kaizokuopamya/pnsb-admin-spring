package com.itl.pns.corp.controller;

import com.itl.pns.bean.DateBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.corp.entity.CorpTransactionsEntity;
import com.itl.pns.corp.entity.CorporateTransactionLogs;
import com.itl.pns.corp.service.CorpTransactionService;
import java.util.List;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({ "corptransaction" })
public class CorpTransactionController {
	private static final Logger logger = LogManager
			.getLogger(com.itl.pns.corp.controller.CorpTransactionController.class);

	@Autowired
	CorpTransactionService corptransactionservice;

	@RequestMapping(value = { "/getCorporateTransactions" }, method = { RequestMethod.POST }, consumes = {
			"application/json" })
	public ResponseEntity<ResponseMessageBean> getCorporateTransactions() {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<CorpTransactionsEntity> transactionList = this.corptransactionservice.getCorporateTransactions();
			if (!ObjectUtils.isEmpty(transactionList)) {
				res.setResponseCode("200");
				res.setResult(transactionList);
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return new ResponseEntity(res, HttpStatus.OK);
	}

	@RequestMapping(value = { "/getCorpTransactionLogs" }, method = { RequestMethod.POST }, consumes = {
			"application/json" })
	public ResponseEntity<ResponseMessageBean> getCorpTransactionLogs() {
		logger.info("In Corporate Controller -> getCorpTransactionLogs()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<CorporateTransactionLogs> corpTransactionLogs = this.corptransactionservice.getCorpTransactionLogs();
			if (null != corpTransactionLogs) {
				res.setResponseCode("200");
				res.setResult(corpTransactionLogs);
				res.setResponseMessage("Success");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return new ResponseEntity(res, HttpStatus.OK);
	}

	@RequestMapping(value = { "/getCorpTransLogsByDate" }, method = { RequestMethod.POST }, consumes = {
			"application/json" })
	public ResponseEntity<ResponseMessageBean> getCorpTransLogsByDate(@RequestBody DateBean datebean) {
		logger.info("In Corporate Controller -> getCorpTransLogsByDate()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<CorporateTransactionLogs> corpTransactionLogs = this.corptransactionservice
					.getCorpTransLogsByDate(datebean);
			if (null != corpTransactionLogs) {
				res.setResponseCode("200");
				res.setResult(corpTransactionLogs);
				res.setResponseMessage("Success");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return new ResponseEntity(res, HttpStatus.OK);
	}
}
