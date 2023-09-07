package com.itl.pns.dao;

import java.util.List;

import com.itl.pns.entity.MessageCodeMasterEntity;

public interface MessageCodeMasterDao {

	public List<MessageCodeMasterEntity> getMessageCodeMasterDetails();

	public List<MessageCodeMasterEntity> getMessageCodeMasterDetailsById(MessageCodeMasterEntity messageCodeData);

	public boolean addMessageCode(MessageCodeMasterEntity messageCodeData);

	public boolean updateMessageCode(MessageCodeMasterEntity messageCodeData);
}
