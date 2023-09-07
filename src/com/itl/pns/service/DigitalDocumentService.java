package com.itl.pns.service;

import java.util.List;

import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.entity.KycDocumentEntity;
import com.itl.pns.entity.KycFolderEntity;

public interface DigitalDocumentService {

	
	public boolean createFolder(KycFolderEntity kycFolderEntity);
	
	public boolean deleteFolder(KycFolderEntity kycFolderEntity);
	
	public boolean createDocument(KycDocumentEntity kycDocumentEntity);
	
	public boolean deleteDocument(KycDocumentEntity kycDocumentEntity);
	
	public List<KycFolderEntity> getFolderList();
	
	public List<KycDocumentEntity> getDocumentListByFolderId(int folderId);
	
	
	public ResponseMessageBean isFolderNameExist(KycFolderEntity kycFolderEntity);
	
	public ResponseMessageBean isDocumentNameExist(KycDocumentEntity kycDocumentEntity);
}
