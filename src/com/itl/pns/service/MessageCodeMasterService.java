package com.itl.pns.service;

import java.util.List;

import com.itl.pns.entity.MessageCodeMasterEntity;

public interface MessageCodeMasterService {

	
	public List<MessageCodeMasterEntity> getMessageCodeMasterDetails();
	
	public List<MessageCodeMasterEntity> getMessageCodeMasterDetailsById(MessageCodeMasterEntity messageCodeData);
	
	public boolean addMessageCode(MessageCodeMasterEntity messageCodeData);
	
	public boolean updateMessageCode(MessageCodeMasterEntity messageCodeData);
}
