package com.itl.pns.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itl.pns.dao.CBSMessageTemplateDao;
import com.itl.pns.entity.CBSMessageTemplateMasterEntity;
import com.itl.pns.service.CBSMessageTemplateService;

@Service
public class CBSMessageTemplateServiceImpl implements CBSMessageTemplateService {

	@Autowired
	CBSMessageTemplateDao cbsMessageTemplateDao;
	
	@Override
	public boolean addCbsMessageTemplate(CBSMessageTemplateMasterEntity templateData) {
		
		return cbsMessageTemplateDao.addCbsMessageTemplate(templateData);
	}

	@Override
	public boolean updateCbsMessageTemplate(CBSMessageTemplateMasterEntity templateData) {
		return cbsMessageTemplateDao.updateCbsMessageTemplate(templateData);
	}

	@Override
	public List<CBSMessageTemplateMasterEntity> getAllCbsMessageTemplate() {
		return cbsMessageTemplateDao.getAllCbsMessageTemplate();
	}

	@Override
	public List<CBSMessageTemplateMasterEntity> getCbsMessageTemplateById(BigDecimal id) {
		return cbsMessageTemplateDao.getCbsMessageTemplateById(id);
	}

}
