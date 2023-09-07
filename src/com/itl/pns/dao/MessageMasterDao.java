package com.itl.pns.dao;

import java.math.BigDecimal;
import java.util.List;

import com.itl.pns.entity.MessageMasterEntity;

public interface MessageMasterDao {

	public List<MessageMasterEntity> messageTemplateList();

	public List<MessageMasterEntity> getmessageTemplate(BigDecimal id);

	public MessageMasterEntity getmessageTemplateById(BigDecimal id);

	public void saveMessageTemplate(MessageMasterEntity messageMasterEntity);

	public void updateMessageTemplate(MessageMasterEntity messageMasterEntity);

}
