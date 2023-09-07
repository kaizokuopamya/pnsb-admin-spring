package com.itl.pns.dao.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.constant.ApplicationConstants;
import com.itl.pns.dao.AdminWorkFlowDao;
import com.itl.pns.dao.HolidayDao;
import com.itl.pns.entity.ActivitySettingMasterEntity;
import com.itl.pns.entity.AdminWorkFlowRequestEntity;
import com.itl.pns.entity.CalculatorFormulaEntity;
import com.itl.pns.entity.CalculatorMasterEntity;
import com.itl.pns.entity.HolidayEntity;
import com.itl.pns.util.AdminWorkFlowReqUtility;
/**
 * @author shubham.lokhande
 *
 */
@Repository
@Transactional
public class HolidayDaoImpl implements HolidayDao {

	static Logger LOGGER = Logger.getLogger(HolidayDaoImpl.class);


	@Autowired
	AdminWorkFlowDao adminWorkFlowDao;

	@Autowired
	AdminWorkFlowReqUtility adminWorkFlowReqUtility;
	
	
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;
	
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	

	@Override
	public List<HolidayEntity> getHolidayDetailsById(int id) {
		List<HolidayEntity> list = null;
		try {
			String sqlQuery = "select h.id,h.name as name ,h.HOLIDAYDATE as holidayDate,h.STATENAME as stateName, h.statusid as statusId,h.CREATEDON as createdOn,  "
					+ " h.createdBy as createdBy, s.name as statusName,aw.remark,aw.userAction from holidaylist h inner join statusmaster s on s.id=h.statusid "
					+ " left join ADMINWORKFLOWREQUEST aw on aw.activityrefno=:id and aw.tablename='HOLIDAYLIST'  where h.id=:id";
			list = getSession().createSQLQuery(sqlQuery)
					.addScalar("id").addScalar("name").addScalar("holidayDate").addScalar("stateName").addScalar("statusId").addScalar("createdOn")
					.addScalar("createdBy").addScalar("statusName").addScalar("remark").addScalar("userAction").setParameter("id", id)
					.setResultTransformer(Transformers.aliasToBean(HolidayEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}
	
	@Override
	public List<HolidayEntity> getHolidayDetailsByState(String state) {
		List<HolidayEntity> list = null;
		try { 
			String sqlQuery = "select distinct(name)as name,HOLIDAYDATE as holidayDate,STATENAME as stateName, statusid as statusId,CREATEDON as createdOn, "
					+ "createdBy as createdBy,id  from holidaylist where Lower(STATENAME) LIKE  ('%" + state.toLowerCase() + "%') and statusid=3";
			list = getSession().createSQLQuery(sqlQuery)
					.addScalar("name").addScalar("holidayDate").addScalar("stateName").addScalar("statusId").addScalar("createdOn")
					.addScalar("createdBy").addScalar("id")
					.setResultTransformer(Transformers.aliasToBean(HolidayEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public List<HolidayEntity> getHolidayDetails() {
		List<HolidayEntity> list = null;
		try {
			String sqlQuery = "select hl.id ,hl.name as name ,hl.HOLIDAYDATE as holidayDate,hl.STATENAME as stateName, hl.statusid as statusId,hl.CREATEDON as createdOn,  "
					+ " hl.createdBy as createdBy , s.name as statusName from holidaylist hl inner join statusmaster s on s.id =hl.statusid order by id desc";
			list = getSession().createSQLQuery(sqlQuery)
					.addScalar("id").addScalar("name").addScalar("holidayDate").addScalar("stateName").addScalar("statusId").addScalar("createdOn")
					.addScalar("createdBy").addScalar("statusName")
					.setResultTransformer(Transformers.aliasToBean(HolidayEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public boolean addHolidayDetails(HolidayEntity holidayData) {
		Session session = sessionFactory.getCurrentSession();
		int userStatus = holidayData.getStatusId().intValue();
		BigDecimal holidayId = new BigDecimal(0);
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(holidayData.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(holidayData.getActivityName());
		
		try {

	/*		if (!(isHolidayExist(holidayData).getResponseCode().equalsIgnoreCase("200"))) {
				String stateNames = holidayData.getStateName();
				String[] values = stateNames.split(",");

				for (String state : values) {
					ResponseMessageBean responseCode = new ResponseMessageBean();
					responseCode = isHolidayExistForStateName(holidayData.getHolidayName(), state);
					if ((responseCode.getResponseCode().equalsIgnoreCase("200"))) {

						BigInteger holidayId = getHolidayDetailsByHolidayName(holidayData.getHolidayName()).get(0)
								.getId();

						addStateNameForPresentHoliday(holidayId, state);

					}

				}
			} else {*/
				
				
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getChecker())) 
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getMaker())))  {
				holidayData.setStatusId(BigDecimal.valueOf(statusId));   //97-  ADMIN_CHECKER_PENDIN
			}
			holidayData.setCreatedOn(new Date());
			holidayData.setUpdatedOn(new Date());
			holidayId =(BigDecimal)session.save(holidayData);
			
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getChecker())) 
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getMaker())))  {
			//List<HolidayEntity> list= getHolidayDetails();		
			ObjectMapper mapper = new ObjectMapper();
			
			AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
			//adminData.setChannelId(new BigDecimal(holidayData.getAppId()));
			adminData.setCreatedByUserId(holidayData.getUser_ID());
			adminData.setCreatedByRoleId(holidayData.getRole_ID());
			adminData.setPageId(holidayData.getSubMenu_ID());       //set submenuId as pageid
			adminData.setCreatedBy(holidayData.getCreatedBy());
			adminData.setContent(mapper.writeValueAsString(holidayData));   
			adminData.setActivityId(holidayData.getSubMenu_ID());  //set submenuId as activityid
			adminData.setStatusId(BigDecimal.valueOf(statusId));  //97- ADMIN_CHECKER_PENDIN
			adminData.setPendingWithRoleId(BigDecimal.valueOf(6));    // 6- checker roleid
			adminData.setRemark(holidayData.getRemark());
			adminData.setActivityName(holidayData.getActivityName());
			adminData.setActivityRefNo(holidayId);
			adminData.setTableName("HOLIDAYLIST");
			adminData.setUserAction(BigDecimal.valueOf(userStatus));
			adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);
			
			 //Save data to admin work flow history
			adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(holidayData.getSubMenu_ID(),
					holidayId, holidayData.getCreatedBy(),
					holidayData.getRemark(), holidayData.getRole_ID(),mapper.writeValueAsString(holidayData));
				}
			

			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}

	@Override
	public boolean updateHolidayDetails(HolidayEntity holidayData) {
		Session session = sessionFactory.getCurrentSession();
		int userStatus = holidayData.getStatusId().intValue();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(holidayData.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData =  adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(holidayData.getActivityName());
		
		try {
			
			if(roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getChecker())) 
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				  holidayData.setStatusId(BigDecimal.valueOf(statusId));   //97-  ADMIN_CHECKER_PENDIN
			}
			
			holidayData.setUpdatedOn(new Date());
			session.update(holidayData);
			
			if(roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getChecker())) 
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				
				List<AdminWorkFlowRequestEntity> AdminDataList = adminWorkFlowReqUtility
						.getAdminWorkFlowDataByActivityRefNo(holidayData.getId().intValue(),holidayData.getSubMenu_ID());
				
				ObjectMapper mapper = new ObjectMapper();
				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
			
				adminData.setCreatedOn(holidayData.getCreatedOn());
				adminData.setCreatedByUserId(holidayData.getUser_ID());
				adminData.setCreatedByRoleId(holidayData.getRole_ID());
				adminData.setPageId(holidayData.getSubMenu_ID());       //set submenuId as pageid
				adminData.setCreatedBy(holidayData.getCreatedBy());
				adminData.setContent(mapper.writeValueAsString(holidayData));   
				adminData.setActivityId(holidayData.getSubMenu_ID());  //set submenuId as activityid
				adminData.setStatusId(BigDecimal.valueOf(statusId));  //97- ADMIN_CHECKER_PENDIN
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6));    // 6- checker roleid
				adminData.setRemark(holidayData.getRemark());
				adminData.setActivityName(holidayData.getActivityName());
				adminData.setActivityRefNo(holidayData.getId());
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminData.setTableName("HOLIDAYLIST");
				if(ObjectUtils.isEmpty(AdminDataList)){
				adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);
				}else if(!ObjectUtils.isEmpty(AdminDataList)){
					adminData.setId(BigDecimal.valueOf(AdminDataList.get(0).getId().intValue()));
					adminWorkFlowReqUtility.updateAdminWorkFlowRequest(adminData);
		
				}
				//Save data to admin work flow history
				adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(holidayData.getSubMenu_ID(),
					holidayData.getId(), holidayData.getCreatedBy(),
						holidayData.getRemark(), holidayData.getRole_ID(),mapper.writeValueAsString(holidayData));
					}else{

				//if record is present in admin work flow then update status
				 adminWorkFlowReqUtility.getCheckerDataByctivityRefId(holidayData.getId().toBigInteger(),
				 BigInteger.valueOf(userStatus),holidayData.getSubMenu_ID());
			}
			
			
			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}

	@Override
	public ResponseMessageBean isHolidayExist(HolidayEntity holidayData) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		try {
			String sqlNameExit = "SELECT count(*) FROM HOLIDAYLIST WHERE Lower(name) =:holidayName  ";
			
			List isHolidayNameExit = getSession().createSQLQuery(sqlNameExit)
					.setParameter("holidayName", holidayData.getName().toLowerCase()).list();
			         
			if (!(isHolidayNameExit.get(0).toString().equalsIgnoreCase("0"))) {
				rmb.setResponseCode("500");
				rmb.setResponseMessage("Holiday Name Already Exist");
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
	public ResponseMessageBean isHolidayExistForState(HolidayEntity holidayData) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		try {
			
			String sqlStateExit = "SELECT count(*) FROM HOLIDAYLIST WHERE Lower(name) =:holidayName AND "
					+ "Lower(STATENAME) LIKE ('%" + holidayData.getStateName().toLowerCase() + "%')  ";
		
			List isHolidayNameExit = getSession().createSQLQuery(sqlStateExit)
					.setParameter("holidayName", holidayData.getName().toLowerCase()).list();
			         
			if (!(isHolidayNameExit.get(0).toString().equalsIgnoreCase("0"))) {
				rmb.setResponseCode("500");
				rmb.setResponseMessage("State Already Exist For Same Holiday");
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
	public ResponseMessageBean updateHolidayExist(HolidayEntity holidayData) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		try {
			String sqlNameExit = "SELECT count(*) FROM HOLIDAYLIST WHERE Lower(name) =:holidayName  AND id !=:id ";
			List isHolidayNameExit = getSession().createSQLQuery(sqlNameExit)
					.setParameter("id", holidayData.getId())
					.setParameter("holidayName", holidayData.getName().toLowerCase()).list();
			         
			if (!(isHolidayNameExit.get(0).toString().equalsIgnoreCase("0"))) {
				rmb.setResponseCode("500");
				rmb.setResponseMessage("Holiday Name Already Exist");
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
	public List<HolidayEntity> getHolidayDetailsByHolidayName(String holidayName) {
		List<HolidayEntity> list = null;
		try { 
			String sqlQuery = "select distinct(name)as name,HOLIDAYDATE as holidayDate,STATENAME as stateName, statusid as statusId,CREATEDON as createdOn, "
					+ "createdBy as createdBy,id  from holidaylist where Lower(name) LIKE  ('%" + holidayName.toLowerCase() + "%') ";
			list = getSession().createSQLQuery(sqlQuery)
					.addScalar("name").addScalar("holidayDate").addScalar("stateName").addScalar("statusId").addScalar("createdOn")
					.addScalar("createdBy").addScalar("id")
					.setResultTransformer(Transformers.aliasToBean(HolidayEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}



	@Override
	public boolean addBulkHolidayDetails(List<HolidayEntity> holidayDataList) {
		holidayDataList.get(0).setStatusId(BigDecimal.valueOf(3));
		Session session = sessionFactory.getCurrentSession();
		int userStatus = holidayDataList.get(0).getStatusId().intValue();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(holidayDataList.get(0).getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(holidayDataList.get(0).getActivityName());

		try {
			for (HolidayEntity holidayData : holidayDataList) {
				if ((!isHolidayExist(holidayData).getResponseCode().equalsIgnoreCase("200"))) {
					String stateNames = holidayData.getStateName();
					String[] values = stateNames.split(",");

					for (String state : values) {
						ResponseMessageBean responseCode = new ResponseMessageBean();
						responseCode = isHolidayExistForStateName(holidayData.getName(), state);
						if ((responseCode.getResponseCode().equalsIgnoreCase("200"))){
							
							BigDecimal holidayId = getHolidayDetailsByHolidayName(holidayData.getName()).get(0)
									.getId();

							addStateNameForPresentHoliday(holidayId.toBigInteger(), state);

						}

					}
				} else {
					holidayData.setStatusId(BigDecimal.valueOf(3));
					if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
							&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getChecker()))
							&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getMaker())))  {
						holidayData.setStatusId(BigDecimal.valueOf(statusId));
					}
					holidayData.setCreatedOn(new Date());
					holidayData.setUpdatedOn(new Date());
					session.save(holidayData);

					if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
							&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getChecker()))
							&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getMaker())))  {
						
						holidayData.setRoleName(roleName);
			    		holidayData.setStatusName( adminWorkFlowReqUtility.getStatusNameByStatusId(holidayData.getStatusId().intValue()));
			    		holidayData.setCreatedByName(adminWorkFlowReqUtility.getCreatedByNameByCreatedId(holidayData.getCreatedBy().intValue()));
			    		holidayData.setAction("ADD");
						
						
						List<HolidayEntity> list = getHolidayDetails();
						ObjectMapper mapper = new ObjectMapper();

						AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
						adminData.setCreatedByUserId(holidayData.getUser_ID());
						adminData.setCreatedByRoleId(holidayData.getRole_ID());
						adminData.setPageId(holidayData.getSubMenu_ID());
						adminData.setCreatedBy(holidayData.getCreatedBy());
						adminData.setContent(mapper.writeValueAsString(holidayData));
						adminData.setActivityId(holidayData.getSubMenu_ID());
						adminData.setStatusId(BigDecimal.valueOf(statusId));
						adminData.setPendingWithRoleId(BigDecimal.valueOf(6));
						adminData.setRemark(holidayData.getRemark());
						adminData.setActivityName(holidayData.getActivityName());
						adminData.setActivityRefNo(list.get(0).getId());
						adminData.setTableName("HOLIDAYLIST");
						adminData.setUserAction(BigDecimal.valueOf(userStatus));
						adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);

						// Save data to admin work flow history
						adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(holidayData.getSubMenu_ID(),
								list.get(0).getId(), holidayData.getCreatedBy(),
								holidayData.getRemark(), holidayData.getRole_ID(),mapper.writeValueAsString(holidayData));
					}
				}
			}

			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}

	public ResponseMessageBean isHolidayExistForStateName(String holidayName, String stateName) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		try {

			String sqlStateExit = "SELECT count(*) FROM HOLIDAYLIST WHERE Lower(name) =:holidayName AND "
					+ "Lower(STATENAME) LIKE ('%" + stateName.toLowerCase() + "%')  ";

			List isHolidayNameExit = getSession().createSQLQuery(sqlStateExit)
					.setParameter("holidayName", holidayName.toLowerCase()).list();

			if (!(isHolidayNameExit.get(0).toString().equalsIgnoreCase("0"))) {
				rmb.setResponseCode("500");
				rmb.setResponseMessage("State Already Exist For Same Holiday");
			} else {
				rmb.setResponseCode("200");
				rmb.setResponseMessage("Both not exist");
			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return rmb;
	}

	public int addStateNameForPresentHoliday(BigInteger holidayId, String stateName) {
		int res = 0;

		try {
			String sql = "update holidaylist set statename = concat(statename,'," + stateName
					+ "') where id=:holidayId";
			res = getSession().createSQLQuery(sql).setParameter("holidayId", holidayId).executeUpdate();

			return res;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return res;

		}
	}
}
