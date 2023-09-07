package com.itl.pns.dao;

import java.math.BigDecimal;
import java.util.List;

import com.itl.pns.entity.CBSMessageTemplateMasterEntity;

public interface CBSMessageTemplateDao {

	public boolean addCbsMessageTemplate(CBSMessageTemplateMasterEntity templateData);

	public boolean updateCbsMessageTemplate(CBSMessageTemplateMasterEntity templateData);

	public List<CBSMessageTemplateMasterEntity> getAllCbsMessageTemplate();

	public List<CBSMessageTemplateMasterEntity> getCbsMessageTemplateById(BigDecimal id);
}
