package com.itl.pns.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.entity.KycDocumentEntity;
import com.itl.pns.entity.KycFolderEntity;

@Repository
@Transactional
public interface DigitalDocumentDao {

	static Logger LOGGER = Logger.getLogger(DigitalDocumentDao.class);

	public boolean createFolder(KycFolderEntity kycFolderEntity);

	public boolean deleteFolder(KycFolderEntity kycFolderEntity);

	public boolean createDocument(KycDocumentEntity kycDocumentEntity);

	public boolean deleteDocument(KycDocumentEntity kycDocumentEntity);

	public List<KycFolderEntity> getFolderList();

	public List<KycDocumentEntity> getDocumentListByFolderId(int folderId);

	public ResponseMessageBean isFolderNameExist(KycFolderEntity kycFolderEntity);

	public ResponseMessageBean isDocumentNameExist(KycDocumentEntity kycDocumentEntity);

	public List<KycFolderEntity> getFolerUUIDByFolderName(String folderName);

	public List<KycDocumentEntity> getDocumentUUIDByDocumentName(String documentName);

}
