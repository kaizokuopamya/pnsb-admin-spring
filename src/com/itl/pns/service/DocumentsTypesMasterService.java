package com.itl.pns.service;

import java.util.List;

import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.entity.DocumentMasterEntity;
import com.itl.pns.entity.DocumentTypeMasterEntity;

public interface DocumentsTypesMasterService {

	List<DocumentTypeMasterEntity> getDocumentTypeList();

	List<DocumentTypeMasterEntity> getDocumentTypeById(int id);

	Boolean updateDocumentTypeDetails(DocumentTypeMasterEntity documentType);

	Boolean saveDocumentTypeDetails(DocumentTypeMasterEntity documentType);
	
	
	List<DocumentMasterEntity> getDocumentList();

	List<DocumentMasterEntity> getDocumentById(int id);

	Boolean saveDocumentData(DocumentMasterEntity documentData);

	Boolean updateDocumentData(DocumentMasterEntity documentData);
	
	
	ResponseMessageBean isDocumentList(DocumentMasterEntity documentData);
	
	ResponseMessageBean isUpdateDocumentList(DocumentMasterEntity documentData);
	


}
