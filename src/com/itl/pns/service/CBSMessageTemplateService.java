package com.itl.pns.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import com.itl.pns.entity.CBSMessageTemplateMasterEntity;

public interface CBSMessageTemplateService {

	
	public boolean addCbsMessageTemplate(CBSMessageTemplateMasterEntity templateData);

	public boolean updateCbsMessageTemplate(CBSMessageTemplateMasterEntity templateData);

	public List<CBSMessageTemplateMasterEntity> getAllCbsMessageTemplate();

	public List<CBSMessageTemplateMasterEntity> getCbsMessageTemplateById(BigDecimal id);
}
