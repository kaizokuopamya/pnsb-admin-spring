package com.itl.pns.dao.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
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
import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.constant.ApplicationConstants;
import com.itl.pns.dao.AdminWorkFlowDao;
import com.itl.pns.dao.DocumentsTypesMasterDao;
import com.itl.pns.entity.ActivitySettingMasterEntity;
import com.itl.pns.entity.AdminWorkFlowRequestEntity;
import com.itl.pns.entity.CalculatorFormulaEntity;
import com.itl.pns.entity.DocumentMasterEntity;
import com.itl.pns.entity.DocumentTypeMasterEntity;
import com.itl.pns.entity.HolidayEntity;
import com.itl.pns.entity.ThemesEntity;
import com.itl.pns.util.AdminWorkFlowReqUtility;

@Repository
@Transactional
public class DocumentsTypesMasterDaoImpl implements DocumentsTypesMasterDao {
	
	static Logger LOGGER = Logger.getLogger(DocumentsTypesMasterDaoImpl.class);
	
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;
	
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@Autowired
	AdminWorkFlowDao adminWorkFlowDao;

	@Autowired
	AdminWorkFlowReqUtility adminWorkFlowReqUtility;
	
	public List<DocumentTypeMasterEntity> getDocumentTypeById(int id) {
		List<DocumentTypeMasterEntity> list = null;
		try {

			String sqlQuery = "select dt.id, dt.type as type,dt.document_list as document_list, dt.createdBy as createdBy,"
					+ " dt.createdOn as createdOn,dt.statusId as statusId, dt.appId as appId,dt.updatedBy as updatedBy,dt.updatedOn as updatedOn,"
					+ "  s.name as statusName,aw.remark,aw.userAction from document_types_master dt  inner join statusmaster s on s.Id=dt.statusid "
					+ " left join ADMINWORKFLOWREQUEST aw on aw.activityrefno=dt.id and aw.tablename='DOCUMENT_TYPES_MASTER' where dt.id=:id";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("type").addScalar("document_list")
					.addScalar("createdBy").addScalar("createdOn").addScalar("statusId").addScalar("appId")
					.addScalar("updatedBy").addScalar("updatedOn").addScalar("remark").addScalar("userAction")
					.addScalar("statusName").setParameter("id", id)
					.setResultTransformer(Transformers.aliasToBean(DocumentTypeMasterEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	public List<DocumentTypeMasterEntity> getDocumentTypeList() {
		List<DocumentTypeMasterEntity> list = null;
		try {

			String sqlQuery = " select cf.id,cf.type as type,cf.document_list as document_list, cf.createdBy as createdBy,"
					+ "		cf.createdOn as createdOn,cf.statusId as statusId, cf.appId as appId,cf.updatedBy as updatedBy,cf.updatedOn as updatedOn,"
					+ "		 s.name as statusName, um.USERID as createdByName, a.shortname as productName from DOCUMENT_TYPES_MASTER cf  inner join statusmaster s on s.Id=cf.statusId "
					+ "		 inner join user_master um on cf.createdby = um.id inner join appmaster a  on a.id=cf.appId order by cf.createdOn desc";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("type").addScalar("document_list")
					.addScalar("createdBy").addScalar("createdOn").addScalar("statusId").addScalar("appId")
					.addScalar("updatedBy").addScalar("updatedOn").addScalar("statusName").addScalar("createdByName")
					.addScalar("productName")
					.setResultTransformer(Transformers.aliasToBean(DocumentTypeMasterEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public Boolean updateDocumentTypeDetails(DocumentTypeMasterEntity docTypeData) {
		Session session = sessionFactory.getCurrentSession();
		int userStatus = docTypeData.getStatusId().intValue();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(docTypeData.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData =  adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(docTypeData.getActivityName());
		
		try {
			if(roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getChecker())) 
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				docTypeData.setStatusId(BigDecimal.valueOf(statusId));   //97-  ADMIN_CHECKER_PENDIN
			}
			
			docTypeData.setUpdatedOn(new Date());
			session.update(docTypeData);
			
			if(roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getChecker())) 
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				
				List<AdminWorkFlowRequestEntity> AdminDataList = adminWorkFlowReqUtility
						.getAdminWorkFlowDataByActivityRefNo(docTypeData.getId().intValue(),docTypeData.getSubMenu_ID());
				
				ObjectMapper mapper = new ObjectMapper();
				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
			
				adminData.setCreatedOn(docTypeData.getCreatedOn());
				adminData.setCreatedByUserId(docTypeData.getUser_ID());
				adminData.setCreatedByRoleId(docTypeData.getRole_ID());
				adminData.setPageId(docTypeData.getSubMenu_ID());       //set submenuId as pageid
				adminData.setCreatedBy(docTypeData.getCreatedBy());
				adminData.setContent(mapper.writeValueAsString(docTypeData));   
				adminData.setActivityId(docTypeData.getSubMenu_ID());  //set submenuId as activityid
				adminData.setStatusId(BigDecimal.valueOf(statusId));  //97- ADMIN_CHECKER_PENDIN
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6));    // 6- checker roleid
				adminData.setRemark(docTypeData.getRemark());
				adminData.setActivityName(docTypeData.getActivityName());
				adminData.setActivityRefNo(docTypeData.getId());
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminData.setTableName("DOCUMENT_TYPES_MASTER");
				if(ObjectUtils.isEmpty(AdminDataList)){
				adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);
				}else if(!ObjectUtils.isEmpty(AdminDataList)){
					adminData.setId(BigDecimal.valueOf(AdminDataList.get(0).getId().intValue()));
					adminWorkFlowReqUtility.updateAdminWorkFlowRequest(adminData);
		
				}
				//Save data to admin work flow history
				adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(docTypeData.getSubMenu_ID(),
				docTypeData.getId(), BigDecimal.valueOf(docTypeData.getCreatedBy().longValue()),
				docTypeData.getRemark(), docTypeData.getRole_ID(),mapper.writeValueAsString(docTypeData));
			}else{

				//if record is present in admin work flow then update status
				 adminWorkFlowReqUtility.getCheckerDataByctivityRefId(docTypeData.getId().toBigInteger(),
				 BigInteger.valueOf(userStatus),docTypeData.getSubMenu_ID());
			}
			
			
			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}

	}

	@Override
	public Boolean saveDocumentTypeDetails(DocumentTypeMasterEntity docType) {
		Session session = sessionFactory.getCurrentSession();
		int userStatus = docType.getStatusId().intValue();
		BigDecimal docId = new BigDecimal(0);
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(docType.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(docType.getActivityName());

		try {
			
		/*	if (!(isDocumentExist(docType).getResponseCode().equalsIgnoreCase("200"))) {
				String docNames = docType.getDocument_list();
				String[] values = docNames.split(",");

				for (String docList : values) {
					ResponseMessageBean responseCode = new ResponseMessageBean();
					responseCode = isTypeExistForDocument(docType.getType(), docList);
					if ((responseCode.getResponseCode().equalsIgnoreCase("200"))) {

						BigInteger docTypeId = getDocumentTypeDetailsByType(docType.getType()).get(0)
								.getId();

						addDocListForPresentDocType(docTypeId, docList);

					}

				}
			} else {*/
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getChecker())) 
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getMaker())))  {
				docType.setStatusId(BigDecimal.valueOf(statusId));   //97-  ADMIN_CHECKER_PENDIN
			}
	
			docType.setCreatedOn(new Date());
			docType.setUpdatedOn(new Date());
			docId =(BigDecimal)session.save(docType);
			
			
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getChecker())) 
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getMaker()))) {
			//	List<DocumentTypeMasterEntity> list= getDocumentTypeList();		
				ObjectMapper mapper = new ObjectMapper();
				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
				
				adminData.setCreatedByUserId(docType.getUser_ID());
				adminData.setCreatedByRoleId(docType.getRole_ID());
				adminData.setPageId(docType.getSubMenu_ID());       //set submenuId as pageid
				adminData.setCreatedBy(docType.getCreatedBy());
				adminData.setContent(mapper.writeValueAsString(docType));   
				adminData.setActivityId(docType.getSubMenu_ID());  //set submenuId as activityid
				adminData.setStatusId(BigDecimal.valueOf(statusId));  //97- ADMIN_CHECKER_PENDIN
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6));    // 6- checker roleid
				adminData.setRemark(docType.getRemark());
				adminData.setActivityName(docType.getActivityName());
				adminData.setActivityRefNo(docId);
				adminData.setTableName("DOCUMENT_TYPES_MASTER");
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);
				
				//Save data to admin work flow history
				adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(docType.getSubMenu_ID(),
						docId, BigDecimal.valueOf(docType.getCreatedBy().longValue()),
				docType.getRemark(), docType.getRole_ID(),mapper.writeValueAsString(docType));
				}
			
			
			return true;

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}

	@Override
	public List<DocumentMasterEntity> getDocumentList() {
		List<DocumentMasterEntity> list = null;
		try {
			
			String sqlQuery = "select dt.id, dt.documentName as documentName, dt.createdBy as createdBy," + 
					" dt.createdOn as createdOn,dt.statusId as statusId,dt.updatedBy as updatedBy,dt.updatedOn as updatedOn," + 
					"  s.name as statusName from DOCUMENT_MASTER dt  inner join statusmaster s on s.Id=dt.statusid  order by dt.id desc";

			list = getSession().createSQLQuery(sqlQuery)
					.addScalar("id").addScalar("documentName").addScalar("createdBy").addScalar("createdOn").addScalar("statusId").addScalar("updatedBy")
					.addScalar("updatedOn").addScalar("statusName")
					.setResultTransformer(Transformers.aliasToBean(DocumentMasterEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public List<DocumentMasterEntity> getDocumentById(int id) {
		List<DocumentMasterEntity> list = null;
		try {
			
			String sqlQuery = "select dt.id, dt.documentName as documentName, dt.createdBy as createdBy," + 
					" dt.createdOn as createdOn,dt.statusId as statusId,dt.updatedBy as updatedBy,dt.updatedOn as updatedOn," + 
					"  s.name as statusName from DOCUMENT_MASTER dt  inner join statusmaster s on s.Id=dt.statusid where dt.id=:id ";

			list = getSession().createSQLQuery(sqlQuery)
					.addScalar("id").addScalar("documentName").addScalar("createdBy").addScalar("createdOn").addScalar("statusId").addScalar("updatedBy")
					.addScalar("updatedOn").addScalar("statusName")
					.setParameter("id", id)
					.setResultTransformer(Transformers.aliasToBean(DocumentMasterEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public Boolean saveDocumentData(DocumentMasterEntity documentData) {
		Session session = sessionFactory.getCurrentSession();
		try {
			documentData.setUpdatedOn(new Date());
			documentData.setCreatedOn(new Date());
			session.save(documentData);
			
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
			
		}
		return true;
	}

	@Override
	public Boolean updateDocumentData(DocumentMasterEntity documentData) {
		Session session = sessionFactory.getCurrentSession();
		try {
			
			documentData.setUpdatedOn(new Date());
			session.update(documentData);
			
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
			
		}
		return true;
	}

	@Override
	public ResponseMessageBean isDocumentList(DocumentMasterEntity documentData) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		try {
			String sqlNameExist = " SELECT count(*) FROM DOCUMENT_MASTER WHERE Lower(DOCUMENTNAME) =:documentName";

			List nameExit = getSession().createSQLQuery(sqlNameExist)
					.setParameter("documentName", documentData.getDocumentName().toLowerCase()).list();

			if (!(nameExit.get(0).toString().equalsIgnoreCase("0"))) {
				rmb.setResponseCode("451");
				rmb.setResponseMessage("Document Name  Already Exist");
			} else {
				rmb.setResponseCode("200");
				rmb.setResponseMessage("Both Not Exist");
			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return rmb;
	}

	
	public ResponseMessageBean isUpdateDocumentList(DocumentMasterEntity documentData) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		try {
			String sqlNameExist = " SELECT count(*) FROM DOCUMENT_MASTER WHERE Lower(DOCUMENTNAME) =:documentName AND id !=:id";

			List nameExit = getSession().createSQLQuery(sqlNameExist).setParameter("id", documentData.getId())
					.setParameter("documentName", documentData.getDocumentName().toLowerCase()).list();

			if (!(nameExit.get(0).toString().equalsIgnoreCase("0"))) {
				rmb.setResponseCode("451");
				rmb.setResponseMessage("Document Name  Already Exist");
			} else {
				rmb.setResponseCode("200");
				rmb.setResponseMessage("Both Not Exist");
			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return rmb;
	}

	@Override
	public ResponseMessageBean isDocumentExist(DocumentTypeMasterEntity docType) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		try {
			String sqlNameExit = "SELECT count(*) FROM DOCUMENT_TYPES_MASTER WHERE Lower(type) =:type  ";
			
			List isHolidayNameExit = getSession().createSQLQuery(sqlNameExit)
					.setParameter("type", docType.getType().toLowerCase()).list();
			         
			if (!(isHolidayNameExit.get(0).toString().equalsIgnoreCase("0"))) {
				rmb.setResponseCode("451");
				rmb.setResponseMessage("Document Type Already Exist");
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
	public ResponseMessageBean isUpdateDocumentExist(DocumentTypeMasterEntity docType) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		try {
			String sqlNameExit = "SELECT count(*) FROM DOCUMENT_TYPES_MASTER WHERE Lower(type) =:type AND id !=:id ";
			
			List isHolidayNameExit = getSession().createSQLQuery(sqlNameExit).setParameter("id", docType.getId())
					.setParameter("type", docType.getType().toLowerCase()).list();
			         
			if (!(isHolidayNameExit.get(0).toString().equalsIgnoreCase("0"))) {
				rmb.setResponseCode("451");
				rmb.setResponseMessage("Document Type Already Exist");
			} else {
				rmb.setResponseCode("200");
				rmb.setResponseMessage("Both not exist");
			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return rmb;
	}
	
	
	public ResponseMessageBean isTypeExistForDocument(String type, String docName) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		try {

			String sqlStateExit = "SELECT count(*) FROM DOCUMENT_TYPES_MASTER WHERE Lower(TYPE) =:type AND "
					+ "Lower(DOCUMENT_LIST) LIKE ('%" + docName.toLowerCase() + "%')  ";

			List isHolidayNameExit = getSession().createSQLQuery(sqlStateExit)
					.setParameter("type", type.toLowerCase()).list();

			if (!(isHolidayNameExit.get(0).toString().equalsIgnoreCase("0"))) {
				rmb.setResponseCode("451");
				rmb.setResponseMessage("Document Name Already Exist For Same Type");
			} else {
				rmb.setResponseCode("200");
				rmb.setResponseMessage("Both not exist");
			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return rmb;
	}

	public int addDocListForPresentDocType(BigInteger docTypeId, String document_list) {
		int res = 0;

		try {
			String sql = "update DOCUMENT_TYPES_MASTER set DOCUMENT_LIST = concat(DOCUMENT_LIST,'," + document_list
					+ "') where id=:docTypeId";
			res = getSession().createSQLQuery(sql).setParameter("docTypeId", docTypeId).executeUpdate();

			return res;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return res;

		}
	}
	
	

	public List<DocumentTypeMasterEntity> getDocumentTypeDetailsByType(String type) {
		List<DocumentTypeMasterEntity> list = null;
		try { 
			String sqlQuery = "select id  from DOCUMENT_TYPES_MASTER where Lower(type) LIKE  ('%" + type.toLowerCase() + "%') ";
			list = getSession().createSQLQuery(sqlQuery)
					.setResultTransformer(Transformers.aliasToBean(DocumentTypeMasterEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	

}
