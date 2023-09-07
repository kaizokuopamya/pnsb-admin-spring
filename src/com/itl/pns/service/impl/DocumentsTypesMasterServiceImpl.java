package com.itl.pns.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.dao.DocumentsTypesMasterDao;
import com.itl.pns.entity.DocumentMasterEntity;
import com.itl.pns.entity.DocumentTypeMasterEntity;
import com.itl.pns.entity.Donation;
import com.itl.pns.service.DocumentsTypesMasterService;
import com.itl.pns.util.AdminWorkFlowReqUtility;

@Service
public class DocumentsTypesMasterServiceImpl implements DocumentsTypesMasterService {
	
	@Autowired 
	DocumentsTypesMasterDao documentTypeDao;
	
	@Autowired
	AdminWorkFlowReqUtility adminWorkFlowReqUtility;
	
	@Override
	public List<DocumentTypeMasterEntity> getDocumentTypeById(int id) {
		return documentTypeDao.getDocumentTypeById(id);
	}
	
	@Override
	public Boolean updateDocumentTypeDetails(DocumentTypeMasterEntity documentType) {
		return documentTypeDao.updateDocumentTypeDetails(documentType);
	}

	@Override
	public List<DocumentTypeMasterEntity> getDocumentTypeList() {
		List<DocumentTypeMasterEntity> list =documentTypeDao.getDocumentTypeList();
		return list;
	}

	@Override
	public Boolean saveDocumentTypeDetails(DocumentTypeMasterEntity documentType) {
		return documentTypeDao.saveDocumentTypeDetails(documentType);
		
		
	}

	@Override
	public List<DocumentMasterEntity> getDocumentList() {
		return documentTypeDao.getDocumentList();
	}

	@Override
	public List<DocumentMasterEntity> getDocumentById(int id) {
		return documentTypeDao.getDocumentById(id);
	}

	@Override
	public Boolean saveDocumentData(DocumentMasterEntity documentData) {
		return documentTypeDao.saveDocumentData(documentData);
	}

	@Override
	public Boolean updateDocumentData(DocumentMasterEntity documentData) {
		return documentTypeDao.updateDocumentData(documentData);
	}

	@Override
	public ResponseMessageBean isDocumentList(DocumentMasterEntity documentData) {
		return documentTypeDao.isDocumentList(documentData);
	}

	@Override
	public ResponseMessageBean isUpdateDocumentList(DocumentMasterEntity documentData) {
		return documentTypeDao.isUpdateDocumentList(documentData);
	}

}
