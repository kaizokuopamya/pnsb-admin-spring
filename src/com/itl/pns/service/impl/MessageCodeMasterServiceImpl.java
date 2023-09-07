package com.itl.pns.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itl.pns.dao.MenuDao;
import com.itl.pns.dao.MessageCodeMasterDao;
import com.itl.pns.entity.MessageCodeMasterEntity;
import com.itl.pns.service.MessageCodeMasterService;

@Service
public class MessageCodeMasterServiceImpl implements MessageCodeMasterService {

	@Autowired
	MessageCodeMasterDao messageCodeDao;
	
	@Override
	public List<MessageCodeMasterEntity> getMessageCodeMasterDetails() {
		
		return messageCodeDao.getMessageCodeMasterDetails();
	}

	@Override
	public List<MessageCodeMasterEntity> getMessageCodeMasterDetailsById(MessageCodeMasterEntity messageCodeData) {
		return messageCodeDao.getMessageCodeMasterDetailsById(messageCodeData);
	}

	@Override
	public boolean addMessageCode(MessageCodeMasterEntity messageCodeData) {
		return messageCodeDao.addMessageCode(messageCodeData);
	}

	@Override
	public boolean updateMessageCode(MessageCodeMasterEntity messageCodeData) {
		return messageCodeDao.updateMessageCode(messageCodeData);
	}

}
