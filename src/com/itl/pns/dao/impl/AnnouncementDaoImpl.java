package com.itl.pns.dao.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.constant.ApplicationConstants;
import com.itl.pns.dao.AnnouncementDao;
import com.itl.pns.entity.ActivitySettingMasterEntity;
import com.itl.pns.entity.AdminWorkFlowRequestEntity;
import com.itl.pns.entity.AnnouncementsEntity;
import com.itl.pns.repository.AnnouncementRepository;
import com.itl.pns.util.AdminWorkFlowReqUtility;

@Repository
@Transactional
@Service
public class AnnouncementDaoImpl implements AnnouncementDao{

	
	static Logger LOGGER = Logger.getLogger(AnnouncementDaoImpl.class);

	@Autowired
	AdminWorkFlowReqUtility adminWorkFlowReqUtility;
	
	@Autowired
	AnnouncementRepository announcementRepo;
	
	
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;
	
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	@Override
	public List<AnnouncementsEntity> getAnnouncementsById(int id) {
		List<AnnouncementsEntity> list = null;
		try {
			String sqlQuery = "select a.id,a.announcementHeader as announcementHeader,a.announcementDescription as announcementDescription,"
					+ "a.imagecaption as imagecaption,a.seqNumber as seqNumber,a.createdby as createdby,a.createdon as createdon,a.statusId as statusId,"
//					+ "a.appId as appId,a.base64ImageSmall as baseImageSmall,a.base64ImageLarge as baseImageLarge,a.updatedby as updatedby,"
					+ "a.appId as appId,a.updatedby as updatedby,"
					+ "a.updatedon as updatedon,a.validFrom as validFrom,a.validTo as validTo,a.latitude as latitude,a.longitude as longitude,"
					+ "a.weblink as weblink,a.type as type, s.name as statusname,ap.shortname as productname, aw.remark,aw.userAction from announcements a 	 "
					+ " inner join statusmaster s on a.statusid=s.id  inner join appmaster ap on ap.id=a.appid "
					+ " left join ADMINWORKFLOWREQUEST aw on aw.activityrefno= a.id and aw.tablename='ANNOUNCEMENTS' where a.id=:id order by a.createdon desc";
			
			list = getSession().createSQLQuery(sqlQuery)
					.addScalar("id")
					.addScalar("announcementHeader")
					.addScalar("announcementDescription",StandardBasicTypes.STRING)
					.addScalar("imagecaption")
					.addScalar("seqNumber")
					.addScalar("createdby")
					.addScalar("createdon")
					.addScalar("statusId")
					.addScalar("appId")
					.addScalar("updatedby")
					.addScalar("updatedon")
					.addScalar("validFrom")
					.addScalar("validTo")
					.addScalar("latitude")
					.addScalar("longitude")
					.addScalar("weblink")
					.addScalar("type")
					.addScalar("statusName")
//					.addScalar("baseImageSmall",StandardBasicTypes.STRING)
//					.addScalar("baseImageLarge",StandardBasicTypes.STRING)
					.addScalar("remark")
					.addScalar("userAction")
					.addScalar("productName").setParameter("id", id)
					.setResultTransformer(Transformers.aliasToBean(AnnouncementsEntity.class)).list();
	
		
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public List<AnnouncementsEntity> getAccouncementsDetails(int statusId) {
		List<AnnouncementsEntity> list = null;
	
		try {
			if(statusId !=22){
				String sqlQuery = "select a.id,a.announcementHeader as announcementHeader,a.announcementDescription as announcementDescription,"
						+ "a.imagecaption as imagecaption,a.seqNumber as seqNumber,a.createdby as createdby,um.USERID as createdByName, a.createdon as createdon,a.statusId as statusId,"
//						+ "a.appId as appId,a.base64ImageSmall as baseImageSmall,a.base64ImageLarge as baseImageLarge,a.updatedby as updatedby,"
						+ "a.appId as appId,a.updatedby as updatedby,"
						+ "a.updatedon as updatedon,a.validFrom as validFrom,a.validTo as validTo,a.latitude as latitude,a.longitude as longitude,"
						+ "a.weblink as weblink,a.type as type, s.name as statusname,ap.shortname as productname from announcements a 	 "
						+ "inner join statusmaster s on a.statusid=s.id  inner join appmaster ap on ap.id=a.appid inner join user_master um on a.createdby = um.id where a.statusId=:statusId order by a.createdon desc ";

				list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("announcementHeader")
						.addScalar("announcementDescription",StandardBasicTypes.STRING).addScalar("imagecaption").addScalar("seqNumber")
						.addScalar("createdby").addScalar("createdByName").addScalar("createdon").addScalar("statusId")
						.addScalar("appId")
						.addScalar("updatedby").addScalar("updatedon").addScalar("validFrom").addScalar("validTo")
						.addScalar("latitude").addScalar("longitude").addScalar("weblink").addScalar("type")
						.addScalar("statusName")//.addScalar("baseImageSmall",StandardBasicTypes.STRING).addScalar("baseImageLarge",StandardBasicTypes.STRING)
						.addScalar("productName").setParameter("statusId", statusId)
						.setResultTransformer(Transformers.aliasToBean(AnnouncementsEntity.class)).list();
			}else{
				String sqlQuery = "select a.id,a.announcementHeader as announcementHeader,a.announcementDescription as announcementDescription,"
						+ "a.imagecaption as imagecaption,a.seqNumber as seqNumber,a.createdby as createdby,um.USERID as createdByName, a.createdon as createdon,a.statusId as statusId,"
//						+ "a.appId as appId,a.base64ImageSmall as baseImageSmall,a.base64ImageLarge as baseImageLarge,a.updatedby as updatedby,"
						+ "a.appId as appId,a.updatedby as updatedby,"
						+ "a.updatedon as updatedon,a.validFrom as validFrom,a.validTo as validTo,a.latitude as latitude,a.longitude as longitude,"
						+ "a.weblink as weblink,a.type as type, s.name as statusname,ap.shortname as productname from announcements a 	 "
						+ "inner join statusmaster s on a.statusid=s.id  inner join appmaster ap on ap.id=a.appid inner join user_master um on a.createdby = um.id "
						+ " order by a.createdon desc ";

				list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("announcementHeader")
						.addScalar("announcementDescription",StandardBasicTypes.STRING).addScalar("imagecaption").addScalar("seqNumber")
						.addScalar("createdby").addScalar("createdByName").addScalar("createdon").addScalar("statusId")
						.addScalar("appId")
						.addScalar("updatedby").addScalar("updatedon").addScalar("validFrom").addScalar("validTo")
						.addScalar("latitude").addScalar("longitude").addScalar("weblink").addScalar("type")
						.addScalar("statusName")//.addScalar("baseImageSmall",StandardBasicTypes.STRING).addScalar("baseImageLarge",StandardBasicTypes.STRING)
						.addScalar("productName")
						.setResultTransformer(Transformers.aliasToBean(AnnouncementsEntity.class)).list();
			}
			
			
	
		
			return list;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public boolean saveAnnouncementsDetais(AnnouncementsEntity announcementData) {
		Session session = sessionFactory.getCurrentSession();
		
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(announcementData.getRole_ID().intValue());
		int statusId = 0;
		if(roleName.equalsIgnoreCase(ApplicationConstants.MAKER)){
			 statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		}else if(roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)){
			 statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.CORP_CHECKER_PENDING);
		}
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(announcementData.getActivityName());

		
		try {
			if((roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					|| roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER))
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getChecker())) 
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getMaker())))  {
				announcementData.setStatusId(BigDecimal.valueOf(statusId));   //97-  ADMIN_CHECKER_PENDIN
			}
			
			announcementData.setCreatedon(new Date());
			announcementData.setUpdatedon(new Date());
			session.save(announcementData);
			
			
			
			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}

	@Override
	public boolean updateAnnouncementsDetais(AnnouncementsEntity announcementData) {		
			Session session = sessionFactory.getCurrentSession();
			String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(announcementData.getRole_ID().intValue());
			int statusId = 0;
			if(roleName.equalsIgnoreCase(ApplicationConstants.MAKER)){
				 statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
			}else if(roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)){
				 statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.CORP_CHECKER_PENDING);
			}
			List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
					.isMakerCheckerPresentForReq(announcementData.getActivityName());
			try {
				
				if((roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
						|| roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER))
						&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getChecker())) 
						&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getMaker())))  {
					announcementData.setStatusId(BigDecimal.valueOf(statusId));
			}
				announcementData.setUpdatedon(new Date());
				session.update(announcementData);
				return true;
			} catch (Exception e) {
				LOGGER.info("Exception:", e);
				return false;
			}

	}
	
	@Override
	public List<AnnouncementsEntity> getAnnouncementType() {
	
		
		return null;
	}
	@Override
	public ResponseMessageBean checkSeqNo(AnnouncementsEntity announcementData) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		try{
		String sqlSeqNoExit = "SELECT count(*) FROM ANNOUNCEMENTS WHERE seqNumber =:seqno";
		List isSeqNoExit = getSession().createSQLQuery(sqlSeqNoExit).setParameter("seqno", announcementData.getSeqNumber()).list();
		
		
		if (!(isSeqNoExit.get(0).toString().equalsIgnoreCase("0"))) {
			rmb.setResponseCode("451");
			rmb.setResponseMessage("Sequence Number Already Exist");
		}else {
			rmb.setResponseCode("200");
			rmb.setResponseMessage("Both not exist");
		}
		}
		catch(Exception e){
			LOGGER.info("Exception:", e);
		}
		return rmb;
	}
	
	
	@Override
	public ResponseMessageBean updateCheckSeqNo(AnnouncementsEntity announcementData) {

		ResponseMessageBean rmb = new ResponseMessageBean();
		try{
			String sqlSeqNoExit = "SELECT count(*) FROM ANNOUNCEMENTS WHERE seqNumber =:seqno AND ID !=:id";
			List isSeqNoExit = getSession().createSQLQuery(sqlSeqNoExit).setParameter("id",announcementData.getId()).setParameter("seqno", announcementData.getSeqNumber()).list();
			
		
		if (!(isSeqNoExit.get(0).toString().equalsIgnoreCase("0"))) {
			rmb.setResponseCode("451");
			rmb.setResponseMessage("Sequence Number Already Exist");
		}else {
			rmb.setResponseCode("200");
			rmb.setResponseMessage("Both not exist");
		}
		}
		catch(Exception e){
			LOGGER.info("Exception:", e);
		}
		return rmb;
	}
	
	public List<AnnouncementsEntity> getAllAnnouncementsDetails() {
		List<AnnouncementsEntity> list = null;
	
		try {
			String sqlQuery = "select a.id,a.announcementHeader as announcementHeader,a.announcementDescription as announcementDescription,"
					+ "a.imagecaption as imagecaption,a.seqNumber as seqNumber,a.createdby as createdby,a.createdon as createdon,a.statusId as statusId,"
					+ "a.appId as appId,a.base64ImageSmall as baseImageSmall,a.base64ImageLarge as baseImageLarge,a.updatedby as updatedby,"
					+ "a.updatedon as updatedon,a.validFrom as validFrom,a.validTo as validTo,a.latitude as latitude,a.longitude as longitude,"
					+ "a.weblink as weblink,a.type as type, s.name as statusname,ap.shortname as productname from announcements a 	 "
					+ "inner join statusmaster s on a.statusid=s.id  inner join appmaster ap on ap.id=a.appid order by a.createdon desc";
			
			list = getSession().createSQLQuery(sqlQuery)
					.addScalar("id")
					.addScalar("announcementHeader")
					.addScalar("announcementDescription",StandardBasicTypes.STRING)
					.addScalar("imagecaption")
					.addScalar("seqNumber")
					.addScalar("createdby")
					.addScalar("createdon")
					.addScalar("statusId")
					.addScalar("appId")
					.addScalar("updatedby")
					.addScalar("updatedon")
					.addScalar("validFrom")
					.addScalar("validTo")
					.addScalar("latitude")
					.addScalar("longitude")
					.addScalar("weblink")
					.addScalar("type")
					.addScalar("statusName")
					.addScalar("baseImageSmall",StandardBasicTypes.STRING)
					.addScalar("baseImageLarge",StandardBasicTypes.STRING)
					.addScalar("productName")
					.setResultTransformer(Transformers.aliasToBean(AnnouncementsEntity.class)).list();
		
		
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}
	@Override
	public boolean saveAnnouncementsDetaisToAdminWorkFlow(AnnouncementsEntity announcementData, int userStatus) {
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(announcementData.getRole_ID().intValue());
		int statusId = 0;
		if(roleName.equalsIgnoreCase(ApplicationConstants.MAKER)){
			 statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		}else if(roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)){
			 statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.CORP_CHECKER_PENDING);
		}
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(announcementData.getActivityName());

		
		try {
			
			if((roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					|| roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER))
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getChecker())) 
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getMaker())))  {
				
				List<AnnouncementsEntity> list= getAllAnnouncementsDetails();
				ObjectMapper mapper = new ObjectMapper();
				
				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
				adminData.setAppId(announcementData.getAppId());
				adminData.setCreatedByUserId(announcementData.getUser_ID());
				adminData.setCreatedByRoleId(announcementData.getRole_ID());
				adminData.setPageId(announcementData.getSubMenu_ID());       //set submenuId as pageid
				adminData.setCreatedBy(announcementData.getCreatedby());
				adminData.setContent(mapper.writeValueAsString(announcementData));   
				adminData.setActivityId(announcementData.getSubMenu_ID());  //set submenuId as activityid
				adminData.setStatusId(BigDecimal.valueOf(statusId));  //97- ADMIN_CHECKER_PENDIN
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6));    // 6- checker roleid
				adminData.setRemark(announcementData.getRemark());
				adminData.setActivityName(announcementData.getActivityName());
				adminData.setActivityRefNo(list.get(0).getId());
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminData.setTableName("ANNOUNCEMENTS");
				adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);
				
				  //Save data to admin work flow history
				adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(announcementData.getSubMenu_ID(),
						list.get(0).getId(), announcementData.getCreatedby(),
						announcementData.getRemark(), announcementData.getRole_ID(),mapper.writeValueAsString(announcementData));

			}
			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}
	
	@Override
	public boolean updateAnnouncementsDetaisToAdminWorkFlow(AnnouncementsEntity announcementData, int userStatus) {
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(announcementData.getRole_ID().intValue());
		int statusId = 0;
		if(roleName.equalsIgnoreCase(ApplicationConstants.MAKER)){
			 statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		}else if(roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)){
			 statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.CORP_CHECKER_PENDING);
		}
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(announcementData.getActivityName());
		
		try {
			
			if((roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					|| roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER))
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getChecker())) 
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getMaker())))  {

				List<AdminWorkFlowRequestEntity> AdminDataList = adminWorkFlowReqUtility
						.getAdminWorkFlowDataByActivityRefNo(announcementData.getId().intValue(),announcementData.getSubMenu_ID());

				ObjectMapper mapper = new ObjectMapper();
				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
				adminData.setAppId(announcementData.getAppId());
				adminData.setCreatedOn(announcementData.getCreatedon());
				adminData.setCreatedByUserId(announcementData.getUser_ID());
				adminData.setCreatedByRoleId(announcementData.getRole_ID());
				adminData.setPageId(announcementData.getSubMenu_ID());       //set submenuId as pageid
				adminData.setCreatedBy(announcementData.getCreatedby());
				adminData.setContent(mapper.writeValueAsString(announcementData));   
				adminData.setActivityId(announcementData.getSubMenu_ID());  //set submenuId as activityid
				adminData.setStatusId(BigDecimal.valueOf(statusId));  //97- ADMIN_CHECKER_PENDIN
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6));    // 6- checker roleid
				adminData.setRemark(announcementData.getRemark());
				adminData.setActivityName(announcementData.getActivityName());
				adminData.setActivityRefNo(announcementData.getId());
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminData.setTableName("ANNOUNCEMENTS");
				if(ObjectUtils.isEmpty(AdminDataList)){
				adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);
				}else if(!ObjectUtils.isEmpty(AdminDataList)){
					adminData.setId(BigDecimal.valueOf(AdminDataList.get(0).getId().intValue()));
					adminWorkFlowReqUtility.updateAdminWorkFlowRequest(adminData);

				}
				// Save data to admin work flow history
				adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(announcementData.getSubMenu_ID(),
					announcementData.getId(), announcementData.getCreatedby(),
						announcementData.getRemark(), announcementData.getRole_ID(),mapper.writeValueAsString(announcementData));

			} else {
				// if record is present in admin work flow then update status
				adminWorkFlowReqUtility.getCheckerDataByctivityRefId(announcementData.getId().toBigInteger(),
						BigInteger.valueOf(userStatus), announcementData.getSubMenu_ID());
			}
			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}

}
