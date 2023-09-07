package com.itl.pns.dao.impl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.dao.DigitalDocumentDao;
import com.itl.pns.entity.KycDocumentEntity;
import com.itl.pns.entity.KycFolderEntity;
/**
 * @author shubham.lokhande
 *
 */
@Repository
@Transactional
public class DigitalDocumentDaoImpl implements DigitalDocumentDao {

		static Logger LOGGER = Logger.getLogger(DigitalDocumentDaoImpl.class);
		
		@Autowired
		@Qualifier("sessionFactory")
		private SessionFactory sessionFactory;
		
		protected Session getSession() {
			return sessionFactory.getCurrentSession();
		}

		@Override
		public boolean createFolder(KycFolderEntity kycFolderEntity) {
			Session session = sessionFactory.getCurrentSession();
			try {
				kycFolderEntity.setUpdatedOn(new Date());
				kycFolderEntity.setCreatedOn(new Date());
				session.save(kycFolderEntity);
				
			} catch (Exception e) {
				LOGGER.info("Exception:", e);
				return false;
			}
			return true;
		}
		

		@Override
		public boolean deleteFolder(KycFolderEntity kycFolderEntity) {
			int res = 0;
			try {
				String sql = "update KYC_FOLDER set statusid=0 where id=:id";
				res = getSession().createSQLQuery(sql).setParameter("id", kycFolderEntity.getId())
						.executeUpdate();
				
				res= deleteAllDocumentsByFolderId(kycFolderEntity.getId().intValue());

			} catch (Exception e) {
				LOGGER.info("Exception:", e);
			}

			if(res == 1)
				return true;
				else
			return false;
		}
		
		

		public int deleteAllDocumentsByFolderId(int  folderId) {
			int res = 0;
			try {
				String sql = "update KYC_DOCUMENT set statusid=0 where KYCFOLDERID=:folderId";
				res = getSession().createSQLQuery(sql).setParameter("folderId", folderId)
						.executeUpdate();
			} catch (Exception e) {
				LOGGER.info("Exception:", e);
			}

			return res;
		
		}
		
		

		@Override
		public boolean createDocument(KycDocumentEntity kycDocumentEntity) {
			Session session = sessionFactory.getCurrentSession();
			try {
				kycDocumentEntity.setUpdatedOn(new Date());
				kycDocumentEntity.setCreatedOn(new Date());
				session.save(kycDocumentEntity);
				
			} catch (Exception e) {
				LOGGER.info("Exception:", e);
				return false;
			}
			return true;
		}

		
		
		public boolean deleteDocument(KycDocumentEntity kycDocumentEntity) {
			int res = 0;
			try {
				String sql = "update KYC_DOCUMENT set statusid=0 where id=:id";
				res = getSession().createSQLQuery(sql).setParameter("id", kycDocumentEntity.getId())
						.executeUpdate();

			} catch (Exception e) {
				LOGGER.info("Exception:", e);
			}

			if(res == 1)
				return true;
				else
			return false;
		
		}

		@Override
		public List<KycFolderEntity> getFolderList() {
			String sqlQuery = "";
			List<KycFolderEntity> list = null;
			try {
				sqlQuery = " select kf.id ,kf.NAME as name,kf.UUID as uuid, kf.PARENTNAME as parentName, kf.PARENTUUID as parentUuid, "
						+ " kf.createdOn as createdOn , kf.statusId as statusId from KYC_FOLDER kf where kf.statusid=3 ";
				list = getSession().createSQLQuery(sqlQuery)
						.setResultTransformer(Transformers.aliasToBean(KycFolderEntity.class)).list();
			} catch (Exception e) {
				LOGGER.info("Exception:", e);
			}
			return list;
		}
		

		@Override
		public List<KycDocumentEntity> getDocumentListByFolderId(int folderId) {
			String sqlQuery = "";
			List<KycDocumentEntity> list = null;
			try {
				sqlQuery = " select kd.id, kd.kycFolderId as kycFolderId, kd.NAME as name,kd.UUID as uuid, kd.kycFolderUuid as kycFolderUuid, kd.CREATEDON as createdOn,"
						+ " kd.updatedon as updatedOn, kd.statusId as statusId, kf.name as KycFolderName from KYC_DOCUMENT kd inner join KYC_FOLDER kf on kf.id= kd.kycFolderId "
						+ " where kd.kycFolderId=:folderId and kd.statusid=3";
				list = getSession().createSQLQuery(sqlQuery).setParameter("folderId", folderId)
						.setResultTransformer(Transformers.aliasToBean(KycDocumentEntity.class)).list();
			} catch (Exception e) {
				LOGGER.info("Exception:", e);
			}
			return list;
		}


		@Override
		public ResponseMessageBean isFolderNameExist(KycFolderEntity kycFolderEntity) {
			ResponseMessageBean rmb = new ResponseMessageBean();
			try {
				String sqlNameExit = "SELECT count(*) FROM KYC_FOLDER WHERE Lower(NAME) =:name";
				List isNameExit = getSession().createSQLQuery(sqlNameExit)
						.setParameter("name", kycFolderEntity.getName().toLowerCase()).list();

				if (!(isNameExit.get(0).toString().equalsIgnoreCase("0"))) {
					rmb.setResponseCode("451");
					rmb.setResponseMessage("Folder Name Already Exist");
				} else {
					rmb.setResponseCode("200");
					rmb.setResponseMessage("Both not exist");
				}
			} catch (Exception e) {
				LOGGER.info("Exception:", e);
			}
			return rmb;
		}

		@Override
		public ResponseMessageBean isDocumentNameExist(KycDocumentEntity kycDocumentEntity) {
			ResponseMessageBean rmb = new ResponseMessageBean();
			try {
				String sqlNameExit = "SELECT count(*) FROM KYC_DOCUMENT WHERE Lower(NAME) =:name AND kycFolderId =:kycFolderId";
				List isNameExit = getSession().createSQLQuery(sqlNameExit).setParameter("kycFolderId", kycDocumentEntity.getKycFolderId())
						.setParameter("name", kycDocumentEntity.getName().toLowerCase()).list();

				if (!(isNameExit.get(0).toString().equalsIgnoreCase("0"))) {
					rmb.setResponseCode("451");
					rmb.setResponseMessage("Document Name Already Exist");
				} else {
					rmb.setResponseCode("200");
					rmb.setResponseMessage("Both not exist");
				}
			} catch (Exception e) {
				LOGGER.info("Exception:", e);
			}
			return rmb;
		}
			
		
		
		@Override
		public List<KycFolderEntity> getFolerUUIDByFolderName(String folderName) {
			String sqlQuery = "";
			List<KycFolderEntity> list = null;
			try {
				sqlQuery = "select UUID as uuid from KYC_FOLDER where name=:folderName";
				list = getSession().createSQLQuery(sqlQuery).setParameter("folderName", folderName)
						.setResultTransformer(Transformers.aliasToBean(KycFolderEntity.class)).list();
			} catch (Exception e) {
				LOGGER.info("Exception:", e);
			}
			return list;
		}
		
		@Override
		public List<KycDocumentEntity> getDocumentUUIDByDocumentName(String documentName) {
			String sqlQuery = "";
			List<KycDocumentEntity> list = null;
			try {
				sqlQuery = "select UUID as uuid from KYC_DOCUMENT where name=:documentName";
				list = getSession().createSQLQuery(sqlQuery).setParameter("documentName", documentName)
						.setResultTransformer(Transformers.aliasToBean(KycDocumentEntity.class)).list();
			} catch (Exception e) {
				LOGGER.info("Exception:", e);
			}
			return list;
		}



}
