package com.itl.pns.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.dao.DigitalDocumentDao;
import com.itl.pns.entity.KycDocumentEntity;
import com.itl.pns.entity.KycFolderEntity;
import com.itl.pns.service.DigitalDocumentService;
@Service
public class DigitalDocumentServiceImpl implements DigitalDocumentService {

	@Autowired 
	DigitalDocumentDao digitalDocumentDao;

	@Override
	public boolean createFolder(KycFolderEntity kycFolderEntity) {
		return digitalDocumentDao.createFolder(kycFolderEntity);
		 
	}

	@Override
	public boolean deleteFolder(KycFolderEntity kycFolderEntity) {
		return digitalDocumentDao.createFolder(kycFolderEntity);
	}

	@Override
	public boolean createDocument(KycDocumentEntity kycDocumentEntity) {
		return digitalDocumentDao.createDocument(kycDocumentEntity);
	}

	@Override
	public boolean deleteDocument(KycDocumentEntity kycDocumentEntity) {
		return digitalDocumentDao.deleteDocument(kycDocumentEntity);
	}

	@Override
	public List<KycFolderEntity> getFolderList() {
		return digitalDocumentDao.getFolderList();
	}

	@Override
	public List<KycDocumentEntity> getDocumentListByFolderId(int folderId) {
		return digitalDocumentDao.getDocumentListByFolderId(folderId);
	}

	@Override
	public ResponseMessageBean isFolderNameExist(KycFolderEntity kycFolderEntity) {
		return digitalDocumentDao.isFolderNameExist(kycFolderEntity);	}

	@Override
	public ResponseMessageBean isDocumentNameExist(KycDocumentEntity kycDocumentEntity) {
		return digitalDocumentDao.isDocumentNameExist(kycDocumentEntity);
	}
	
	
}
