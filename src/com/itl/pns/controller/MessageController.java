package com.itl.pns.controller;

import java.util.List;

import org.apache.log4j.LogManager;
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

import com.itl.pns.bean.DateBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.service.MessageService;

@RestController
@RequestMapping("message")
public class MessageController{
	private static final Logger logger = LogManager.getLogger(MessageController.class);

	@Autowired
	private MessageService messageDetailsService;
	

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getCustomerLocation", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getCustomerLocation(@RequestBody DateBean bean) {
		ResponseMessageBean res=new ResponseMessageBean();
		List getCustomerLocation = messageDetailsService.getCustomerLocation(bean);
		try {
		if(!ObjectUtils.isEmpty(getCustomerLocation)){
			res.setResponseCode("200");
			res.setResult(getCustomerLocation);
		}else{
			res.setResponseCode("202");
			res.setResponseMessage("No Records Found");
		}} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}


	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getCustomerCount", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getCustomerCount(@RequestBody DateBean datebean) {
		ResponseMessageBean res=new ResponseMessageBean();
try {
		List inMessages = messageDetailsService.getCustomerCount(datebean);
		
		if(!ObjectUtils.isEmpty(inMessages)){
			res.setResponseCode("200");
			res.setResult(inMessages);
		}else{
			res.setResponseCode("202");
		}} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getChannelCount", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getChannelCount(@RequestBody DateBean datebean) {
		ResponseMessageBean res=new ResponseMessageBean();
		List list = messageDetailsService.getChannelCount(datebean);
		try {
		if(!ObjectUtils.isEmpty(list)){
			res.setResponseCode("200");
			res.setResult(list);
		}else{
			res.setResponseCode("202");
		}} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(res,HttpStatus.OK);
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getActivityLogCountByDate", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> getCountByDate(@RequestBody DateBean datebean) {
		ResponseMessageBean res=new ResponseMessageBean();
		List list = messageDetailsService.getCountByDate(datebean);
		try {
		if(!ObjectUtils.isEmpty(list)){
			res.setResponseCode("200");
			res.setResult(list);
		}else{
			res.setResponseCode("202");
		}} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getCountByProcessing", method = RequestMethod.GET)
	public ResponseEntity<List> getCountByProcessing() {
		
		List inMessages = messageDetailsService.getCountByProcessing();
		return new ResponseEntity<>(inMessages, HttpStatus.OK);
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getCustomerLocation/{type}", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List> getCustomerLocation(@PathVariable ("type") String type) {
		List getCustomerLocation = messageDetailsService.getCustomerLocation(type);
			
		return new ResponseEntity<>(getCustomerLocation, HttpStatus.OK);
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getTransactionlogByStatus/{type}", method = RequestMethod.POST)
	public ResponseEntity<List> getTransactionlogByStatus(@PathVariable ("type") String type) {
		List transactionLog = messageDetailsService.getTransactionlogByStatus(type);
		return new ResponseEntity<>(transactionLog, HttpStatus.OK);
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getTransactionlogByStatusNew", method = RequestMethod.POST)
	public ResponseEntity<List> getTransactionlogByStatusNew(@RequestBody DateBean datebean) {
		List transactionLog = messageDetailsService.getTransactionlogByStatusNew(datebean);
		return new ResponseEntity<>(transactionLog, HttpStatus.OK);
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getTransactionByChannel/{type}", method = RequestMethod.POST)
	public ResponseEntity<List> getTransactionByChannel(@PathVariable ("type") String type) {
		List transactionLog = messageDetailsService.getTransactionByChannel(type);
		return new ResponseEntity<>(transactionLog, HttpStatus.OK);
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getTransactionByChannelNew", method = RequestMethod.POST)
	public ResponseEntity<List> getTransactionByChannelNew(@RequestBody DateBean datebean) {
		List transactionLog = messageDetailsService.getTransactionByChannelNew(datebean);
		return new ResponseEntity<>(transactionLog, HttpStatus.OK);
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getServiceRequestByStatus/{type}", method = RequestMethod.POST)
	public ResponseEntity<List> getServiceRequestByStatus(@PathVariable ("type") String type) {
		List transactionLog = messageDetailsService.getServiceRequestByStatus(type);
		return new ResponseEntity<>(transactionLog, HttpStatus.OK);
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getServiceRequestByStatusNew", method = RequestMethod.POST)
	public ResponseEntity<List> getServiceRequestByStatusNew(@RequestBody DateBean datebean) {
		List transactionLog = messageDetailsService.getServiceRequestByStatusNew(datebean);
		return new ResponseEntity<>(transactionLog, HttpStatus.OK);
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getMessagingReport/{type}", method = RequestMethod.POST)
	public ResponseEntity<List> getMessagingReport(@PathVariable ("type") String type) {
		List transactionLog = messageDetailsService.getMessagingReport(type);
		return new ResponseEntity<>(transactionLog, HttpStatus.OK);
	}
	
}
