package com.itl.pns.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.itl.pns.dao.MessageMasterDao;
import com.itl.pns.entity.MessageMasterEntity;
import com.itl.pns.service.MessageMasterService;

@Service
@Qualifier("messageMasterService")
public class MessageMasterServiceImpl implements MessageMasterService {

	static Logger LOGGER = Logger.getLogger(MessageMasterServiceImpl.class);

	@Autowired
	private MessageMasterDao messageMasterDao;

	@Override
	public List<MessageMasterEntity> messageTemplateList() {
		return messageMasterDao.messageTemplateList();
	}

	@Override
	public List<MessageMasterEntity> getmessageTemplate(BigDecimal id) {
		return messageMasterDao.getmessageTemplate(id);
	}

	@Override
	public MessageMasterEntity getmessageTemplateById(BigDecimal id) {
		return messageMasterDao.getmessageTemplateById(id);
	}

	@Override
	public void saveMessageTemplate(MessageMasterEntity messageMasterEntity) {
		messageMasterDao.saveMessageTemplate(messageMasterEntity);
	}

	@Override
	public void updateMessageTemplate(MessageMasterEntity messageMasterEntity) {
		messageMasterDao.updateMessageTemplate(messageMasterEntity);
	}

}
