package com.itl.pns.dao;

import java.util.List;

import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.entity.DocumentMasterEntity;
import com.itl.pns.entity.DocumentTypeMasterEntity;

public interface DocumentsTypesMasterDao {

	List<DocumentTypeMasterEntity> getDocumentTypeById(int id);

	List<DocumentTypeMasterEntity> getDocumentTypeList();

	Boolean updateDocumentTypeDetails(DocumentTypeMasterEntity documentType);

	Boolean saveDocumentTypeDetails(DocumentTypeMasterEntity documentType);
	
	
	List<DocumentMasterEntity> getDocumentList();

	List<DocumentMasterEntity> getDocumentById(int id);

	Boolean saveDocumentData(DocumentMasterEntity documentData);

	Boolean updateDocumentData(DocumentMasterEntity documentData);
	
    ResponseMessageBean isDocumentList(DocumentMasterEntity documentData);
	
	ResponseMessageBean isUpdateDocumentList(DocumentMasterEntity documentData);
	
	ResponseMessageBean isDocumentExist(DocumentTypeMasterEntity docType); 
	
	public ResponseMessageBean isUpdateDocumentExist(DocumentTypeMasterEntity docType);

}
