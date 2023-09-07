package com.itl.pns.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.itl.pns.bean.DateBean;
import com.itl.pns.bean.LanguageJsonBean;
import com.itl.pns.bean.TicketBean;
import com.itl.pns.dao.MessageDao;
import com.itl.pns.entity.LanguageJson;
import com.itl.pns.repository.LanguageJsonRepository;
import com.itl.pns.service.MessageService;


@Service
@Qualifier("MessageDetailsService")

public class MessageServiceImpl implements MessageService {

	@Autowired
	MessageDao messageDetailsDao;
	
	@Autowired
	LanguageJsonRepository languagejsonRepository;
	

	
	@SuppressWarnings("rawtypes")
	@Override
	public List getCountByProcessing() {
		List l= messageDetailsDao.getCountByProcessing();
	
		return l;
	}
	
	
	@SuppressWarnings("rawtypes")
	@Override
	public List getChannelCount(DateBean date) {
		return messageDetailsDao.getChannelCount(date);
	}


	
	
	@SuppressWarnings("rawtypes")
	@Override
	public List getCountByDate(DateBean date) {
		return messageDetailsDao.getCountByDate(date);
	}
	
	
	@SuppressWarnings("rawtypes")
	@Override
	public List getCustomerCount(DateBean datebean) {
		return messageDetailsDao.getCustomerCount(datebean);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List getCustomerLocation(String type) {
		List list=messageDetailsDao.getCustomerlocation(type);
	
		return list;
	}
	

	@SuppressWarnings("rawtypes")
	@Override
	public List getCustomerLocation(DateBean date) {
		List list=messageDetailsDao.getCustomerlocation(date);
	
		return list;
	}
	
	
	@SuppressWarnings("rawtypes")
	@Override
	public List<TicketBean> getStatusById(int id) {
		List<TicketBean> list=messageDetailsDao.getStatusById(id);
	
		return list;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List<TicketBean> getStatus() {
		List<TicketBean> list=messageDetailsDao.getStatus();
	
		return list;
	}


	@Override
	public List<LanguageJsonBean> getLanguageJson() {
		List<LanguageJsonBean> list= messageDetailsDao.getLanguageJson();

		return list;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List<TicketBean> getShortname() {
		List<TicketBean> list=messageDetailsDao.getShortname();
	
		return list;
	}
	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	public boolean addLanguagejson(LanguageJson languagejson) {

		String pattern = "dd/MM/yyyy";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		Date date = new Date();
		

	
		languagejson.setCreatedon(date);
		languagejson.setCreatedby(languagejson.getCreatedby());
		LanguageJson languagejson1=languagejsonRepository.save(languagejson);
		System.out.println(languagejson1.getId());
		return true;
	}

	@Override
	public List getTransactionlogByStatus(String type) {
		return messageDetailsDao.getTransactionlogByStatus(type);
	}

	@Override
	public List getTransactionlogByStatusNew(DateBean datebean) {
		return messageDetailsDao.getTransactionlogByStatusNew(datebean);
	}

	@Override
	public List getTransactionByChannel(String type) {
		return messageDetailsDao.getTransactionByChannel(type);
	}

	@Override
	public List getTransactionByChannelNew(DateBean datebean) {
		return messageDetailsDao.getTransactionByChannelNew(datebean);
	}

	@Override
	public List getServiceRequestByStatus(String type) {
		return messageDetailsDao.getServiceRequestByStatus(type);
	}

	@Override
	public List getServiceRequestByStatusNew(DateBean datebean) {
		return messageDetailsDao.getServiceRequestByStatusNew(datebean);
	}

	@Override
	public List getMessagingReport(String type) {
		// TODO Auto-generated method stub
		return null;
	}
}
