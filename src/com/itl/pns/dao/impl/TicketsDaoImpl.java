package com.itl.pns.dao.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itl.pns.bean.RequestParamBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.bean.TicketBean;
import com.itl.pns.constant.ApplicationConstants;
import com.itl.pns.corp.dao.CorpBankTokenDao;
import com.itl.pns.dao.TicketsDao;
import com.itl.pns.entity.ActivitySettingMasterEntity;
import com.itl.pns.entity.AdminWorkFlowRequestEntity;
import com.itl.pns.entity.BankTokenEntity;
import com.itl.pns.entity.ConfigMasterEntity;
import com.itl.pns.entity.CustomerTokenEntity;
import com.itl.pns.service.CalculatorService;
import com.itl.pns.service.impl.RestServiceCall;
import com.itl.pns.util.AdminWorkFlowReqUtility;
import com.itl.pns.util.EncryptorDecryptor;

@Repository
@Transactional
public class TicketsDaoImpl implements TicketsDao {
	static Logger LOGGER = Logger.getLogger(TicketsDaoImpl.class);

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	@Autowired
	private RestServiceCall rest;

	@Autowired
	private AdminWorkFlowReqUtility adminWorkFlowReqUtility;

	@Autowired
	private CalculatorService calculatorService;

	@Autowired
	private CorpBankTokenDao corpBankTokenDao;

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public List<TicketBean> getTicketsList(int statusId) {
		String sqlQuery = "";
		List<TicketBean> list = null;
		try {
			if (statusId != 22) {
				sqlQuery = "select t.ID,t.CUSTOMERID,t.PRODUCTID,t.CATEGORYNAME,t.DESCRIPTION,t.EMAIL,t.RESOLUTION,t.APPID,t.CREATEDON,t.UPDATEDBY,t.UPDATEDON,t.THIRDPARTYREFERENCENO,"
						+ "t.STATUSID,t.DEVICEID,t.ASSIGNTO,s.name as STATUSNAME, a.SHORTNAME,c.CUSTOMERNAME,c.Image as BASEIMAGE,c.cif as CIF,c.mobile as MOBILEOFCUST"
						+ " from tickets_prd t inner join statusmaster s on s.id= t.STATUSID   inner join appmaster a on a.id=t.appid "
						+ " inner join customers c on c.id=t.CUSTOMERID where t.STATUSID =:statusId order by t.CREATEDON desc ";

				list = getSession().createSQLQuery(sqlQuery).setParameter("statusId", statusId)
						.setResultTransformer(Transformers.aliasToBean(TicketBean.class)).list();
			} else {
				sqlQuery = "select t.ID,t.CUSTOMERID,t.PRODUCTID,t.CATEGORYNAME,t.DESCRIPTION,t.EMAIL,t.RESOLUTION,t.APPID,t.CREATEDON,t.UPDATEDBY,t.UPDATEDON,t.THIRDPARTYREFERENCENO,"
						+ "t.STATUSID,t.DEVICEID,t.ASSIGNTO,s.name as STATUSNAME, a.SHORTNAME,c.CUSTOMERNAME,c.Image as BASEIMAGE,c.cif as CIF,c.mobile as MOBILEOFCUST"
						+ " from tickets_prd t inner join statusmaster s on s.id= t.STATUSID   inner join appmaster a on a.id=t.appid "
						+ " inner join customers c on c.id=t.CUSTOMERID  order by t.CREATEDON desc ";

				list = getSession().createSQLQuery(sqlQuery)
						.setResultTransformer(Transformers.aliasToBean(TicketBean.class)).list();
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);

		}
		return list;
	}

	@Override
	public List<TicketBean> getTicketsListById(int ticketId) {
		String sqlQuery = "";
		List<TicketBean> list = null;
		try {

			sqlQuery = "select t.ID,t.CUSTOMERID,t.PRODUCTID,t.CATEGORYNAME,t.DESCRIPTION,t.EMAIL,t.RESOLUTION,t.APPID,t.CREATEDON,t.UPDATEDBY,t.UPDATEDON,t.THIRDPARTYREFERENCENO,"
					+ "t.STATUSID,t.DEVICEID,t.ASSIGNTO,s.shortname as STATUSNAME, a.SHORTNAME,c.CUSTOMERNAME,c.Image as BASEIMAGE,c.cif as CIF,c.mobile as MOBILEOFCUST, aw.remark, aw.userAction "
					+ "from tickets t inner join statusmaster s on s.id= t.STATUSID   inner join appmaster a on a.id=t.appid "
					+ "inner join customers c on c.id=t.CUSTOMERID left join ADMINWORKFLOWREQUEST AS aw on aw.activityrefno=t.id and aw.tablename='TICKETS' where t.id =:ticketId";

			list = getSession().createSQLQuery(sqlQuery).setParameter("ticketId", ticketId)
					.setResultTransformer(Transformers.aliasToBean(TicketBean.class)).list();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);

		}
		return list;
	}

	@Override
	public boolean updateTicketStatus(TicketBean ticketBean) {
		int res = 0;
		int userStatus = ticketBean.getSTATUSID().intValue();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(ticketBean.getRole_ID().intValue());
		int statusId = 0;
		if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)) {
			statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		} else if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)) {
			statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.CORP_CHECKER_PENDING);
		}
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(ticketBean.getActivityName());
		try {
			if ((roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					|| roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				ticketBean.setSTATUSID(BigDecimal.valueOf(statusId));

			}

			String sql = "update Tickets set statusid=:status where id=:id";
			res = getSession().createSQLQuery(sql).setParameter("status", ticketBean.getSTATUSID())
					.setParameter("id", ticketBean.getID()).executeUpdate();
			LOGGER.info("2");

			if ((roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					|| roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

				List<AdminWorkFlowRequestEntity> AdminDataList = adminWorkFlowReqUtility
						.getAdminWorkFlowDataByActivityRefNo(ticketBean.getID().intValue(), ticketBean.getSubMenu_ID());

				ObjectMapper mapper = new ObjectMapper();
				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
				adminData.setAppId(ticketBean.getAPPID());
				adminData.setCreatedOn(ticketBean.getCREATEDON());
				adminData.setCreatedByUserId(ticketBean.getUser_ID());
				adminData.setCreatedByRoleId(ticketBean.getRole_ID());
				adminData.setPageId(ticketBean.getSubMenu_ID()); // set submenuId as pageid
				adminData.setCreatedBy(ticketBean.getCREATEDBY());
				adminData.setContent(mapper.writeValueAsString(ticketBean));
				adminData.setActivityId(ticketBean.getSubMenu_ID()); // set submenuId as activityid
				adminData.setStatusId(BigDecimal.valueOf(statusId)); // 97- ADMIN_CHECKER_PENDIN
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6)); // 6- checker roleid
				adminData.setRemark(ticketBean.getRemark());
				adminData.setActivityName(ticketBean.getActivityName());
				adminData.setActivityRefNo(ticketBean.getID());
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminData.setTableName("TICKETS");
				if (ObjectUtils.isEmpty(AdminDataList)) {
					adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);
				} else if (!ObjectUtils.isEmpty(AdminDataList)) {
					adminData.setId(BigDecimal.valueOf(AdminDataList.get(0).getId().intValue()));
					adminWorkFlowReqUtility.updateAdminWorkFlowRequest(adminData);
				}
				// Save data to admin work flow history
				adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(ticketBean.getSubMenu_ID(), ticketBean.getID(),
						BigDecimal.valueOf(ticketBean.getCREATEDBY().intValue()), ticketBean.getRemark(),
						ticketBean.getRole_ID(), mapper.writeValueAsString(ticketBean));

			} else {
				// if record is present in admin work flow then update status
				adminWorkFlowReqUtility.getCheckerDataByctivityRefId(ticketBean.getID().toBigInteger(),
						BigInteger.valueOf(userStatus), ticketBean.getSubMenu_ID());
			}

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		if (res > 0)
			return true;
		else
			return false;

	}

	@Override
	public List<TicketBean> getAllTicketsList() {
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String currDate = df.format(date);
		List<TicketBean> list = null;
		String sqlQuery = "";
		try {

			sqlQuery = "select t.ID, t.CHANNELID,t.CUSTOMERID,t.PRODUCTID,t.CATEGORYNAME,t.DESCRIPTION,t.EMAIL,t.RESOLUTION,t.APPID,t.CREATEDBY,t.CREATEDON,t.UPDATEDBY,t.UPDATEDON,t.THIRDPARTYREFERENCENO,"
					+ "t.STATUSID,t.DEVICEID,t.ASSIGNTO,s.shortname as STATUSNAME, a.SHORTNAME,c.CUSTOMERNAME,c.Image as BASEIMAGE,c.cif as CIF,c.mobile as MOBILEOFCUST "
					+ "from tickets t inner join statusmaster s on s.id= t.STATUSID   inner join appmaster a on a.id=t.appid "
					+ "inner join customers c on c.id=t.CUSTOMERID where t.statusid !=23  "
					+ " and t.CREATEDON = TO_DATE('" + currDate + "', '%Y-%m-%d %T') order by t.id desc";

			list = getSession().createSQLQuery(sqlQuery)
					.setResultTransformer(Transformers.aliasToBean(TicketBean.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return list;
	}

	@Override
	public boolean generateTokenRequest(TicketBean ticketBean) {
		int res = 0;
		String resStatus = "";
		String resp = "";
		String result = "";
		try {

			LOGGER.info("1");
			resp = rest.generateTokenService(ticketBean.getMOBILE(), "RIB", "", "");
			LOGGER.info(resp);

			LOGGER.info(resp);
			if (resp.equalsIgnoreCase("error")) {
				String sql = "update Tickets set statusid=:status where id=:id";
				res = getSession().createSQLQuery(sql).setParameter("status", ticketBean.getSTATUSID())
						.setParameter("id", ticketBean.getID()).executeUpdate();
			}

			JSONObject json1 = new JSONObject(resp);
			JSONObject json2 = json1.getJSONObject("responseParameter");
			resStatus = json2.getString("opstatus");
			result = json2.getString("Result");
			LOGGER.info(result);
			if (resStatus.equalsIgnoreCase("00") || resStatus.equalsIgnoreCase("04")) {
				String sql = "update Tickets set statusid=:status where id=:id";
				res = getSession().createSQLQuery(sql).setParameter("status", 87).setParameter("id", ticketBean.getID())
						.executeUpdate();
			} else {
				String sql = "update Tickets set statusid=:status where id=:id";
				res = getSession().createSQLQuery(sql).setParameter("status", ticketBean.getSTATUSID())
						.setParameter("id", ticketBean.getID()).executeUpdate();
			}
		} catch (Exception e) {
			LOGGER.error("Exception:", e);
		}

		if (res > 0)
			return true;
		else
			return false;
	}

	@Override
	public boolean rejectTokenRequest(TicketBean ticketBean) {
		int res = 0;
		try {
			LOGGER.info("1");
			String sql = "update Tickets set statusid=:status where id=:id";
			res = getSession().createSQLQuery(sql).setParameter("status", 12).setParameter("id", ticketBean.getID())
					.executeUpdate();
		} catch (Exception e) {
			LOGGER.error("Exception:", e);
		}
		if (res > 0)
			return true;
		else
			return false;

	}

	@Override
	public List<BankTokenEntity> getBankTokenRequest(RequestParamBean requestParamBean) {
		List<BankTokenEntity> list = null;

		try {
			String sqlquery = "select bk.id, bk.customerId as customerId,bk.accountNumber, bk.statusId ,bk.referencenumber , bk.appId,bk.reqInitiatedFor,bk.typeOfRequest,bk.createdOn, "
					+ " s.Name as statusName,c.cif as cif,c.CUSTOMERNAME as customername, c.mobile as mobile, bk.branch_code as branchCode from BANKTOKEN bk inner join customers c on c.id=bk.customerId "
					+ "inner join statusmaster s on s.id =bk.statusid where bk.statusid in :statusId and bk.branch_code=:branchCode and length(c.cif)>=9 and bk.appId in(4,5) order by bk.id desc";
			list = getSession().createSQLQuery(sqlquery).addScalar("id").addScalar("customerId")
					.addScalar("accountNumber").addScalar("statusId").addScalar("referencenumber").addScalar("appId")
					.addScalar("reqInitiatedFor").addScalar("typeOfRequest").addScalar("createdOn").addScalar("statusName").addScalar("cif")
					.addScalar("customername").addScalar("mobile").addScalar("branchCode")
					.setParameter("branchCode", requestParamBean.getBranchCode())
					.setParameterList("statusId", requestParamBean.getStatusList())
					.setResultTransformer(Transformers.aliasToBean(BankTokenEntity.class)).list();
		} catch (Exception e) {
			LOGGER.error("Exception:", e);
		}
		return list;
	}

	@Override
	public ResponseMessageBean generateBankToken(BankTokenEntity bankToken) {
		ResponseMessageBean response = new ResponseMessageBean();
		int res = 0;
		String resStatus = "";
		String resp = "";
		String result = "";
		Session session = sessionFactory.getCurrentSession();
		Transaction tx=session.beginTransaction();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(bankToken.getRole_ID().intValue());
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(bankToken.getActivityName());
		int userStatus = bankToken.getStatusId().intValue();
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.VERIFICATION_PENDING);

		List<BankTokenEntity> oldBankToken = new ArrayList<>();
		if (bankToken.getChannel().equalsIgnoreCase(ApplicationConstants.CHANNEL_RETAIL)) {
			oldBankToken = corpBankTokenDao.getBankTokenByIdForRetail(bankToken.getId().intValue());
		} else {
			oldBankToken = corpBankTokenDao.getBankTokenByIdForCorp(bankToken.getId().intValue());
		}

		try {

			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER) || roleName.equalsIgnoreCase("Corporate Maker")
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				LOGGER.info("inside if condition Maker/Corporate Maker");
				oldBankToken.get(0).setCreatedOn(new Timestamp(System.currentTimeMillis()));
				oldBankToken.get(0).setCreatedBy(bankToken.getCreatedUpdatedBy());
				oldBankToken.get(0).setUpdatedBy(bankToken.getCreatedUpdatedBy());
				oldBankToken.get(0).setUpdatedOn(new Timestamp(System.currentTimeMillis()));
				oldBankToken.get(0).setStatusId(BigDecimal.valueOf(36));
				// update bank token
				try {
					session.update(oldBankToken.get(0));
					tx.commit();
					response.setResponseCode("200");
					response.setResponseMessage("Request Submitted To Checker For Approval");
					LOGGER.info("Ticket Dao IMPL......Request Submitted To Checker For Approval");
				} catch (Exception e) {
					response.setResponseCode("202");
					response.setResponseMessage("Error While Generating Token, Please Try Again");
					LOGGER.info("Ticket Dao IMPL......Error While Generating Token, Please Try Again");
				}
			} else {
				LOGGER.info("Banktoken status before reject of approve by checker: "+bankToken.getStatusId());
				oldBankToken.get(0).setUpdatedBy(bankToken.getCreatedUpdatedBy());
				oldBankToken.get(0).setUpdatedOn(new Timestamp(System.currentTimeMillis()));
				oldBankToken.get(0).setStatusId(bankToken.getStatusId());
				session.update(oldBankToken.get(0));
				tx.commit();
				ObjectMapper mapper = new ObjectMapper();
				adminWorkFlowReqUtility.saveToAdminWorkFlowHistoryContent(bankToken.getSubMenu_ID(), bankToken.getId(),
						bankToken.getCreatedUpdatedBy(), bankToken.getRemark(), bankToken.getRole_ID(),
						mapper.writeValueAsString(bankToken));
				
				if (bankToken.getStatusId().equals(BigDecimal.valueOf(35))) {					
					// update bank token
					try {												
						LOGGER.info("update bank token....");
						//add history for checker rejection												
						response.setResponseCode("200");
						response.setResponseMessage("Token Request Rejected Successfully");
						
					} catch (Exception e) {
						response.setResponseCode("202");
						response.setResponseMessage("Error While Generating Token,Please Try Again");
					}
				} else {
					LOGGER.info("inside Maker/Corporate Maker");
					if(bankToken.getTypeOfRequest().equals("Switch Transaction Mode")){
						bankToken.setTypeOfRequest("ACCESSRIGHTS");
					}else if(bankToken.getTypeOfRequest().equals("Reset TPIN")){
						bankToken.setTypeOfRequest("RESETTPIN");
					}else {
						bankToken.getTypeOfRequest();
					}
					resp = rest.generateTokenService(bankToken.getMobile(), bankToken.getAppName(),
							bankToken.getReferencenumber().toString(), bankToken.getTypeOfRequest());
					LOGGER.info("Middleware response before approve: "+resp);
					if (resp.equalsIgnoreCase("error")) {
						response.setResponseCode("202");
						response.setResponseMessage("We are currently upgrading system, please try again later");
						return response;
					}
					JSONObject json1 = new JSONObject(resp);
					JSONObject json2 = json1.getJSONObject("responseParameter");
					resStatus = json2.getString("opstatus");
					/*
					 * result=json2.getString("Result"); LOGGER.info(result);
					 */
					LOGGER.info("Opstatus: "+resStatus);
					if (resStatus.equalsIgnoreCase("00")) {
						String sql = "update BANKTOKEN set statusid=:status where id=:id";
						res = getSession().createSQLQuery(sql).setParameter("status", 30)
								.setParameter("id", bankToken.getId()).executeUpdate();
						LOGGER.info("bank token generation response: "+res);
						bankToken.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
						res = updateCustomerStatus(bankToken);
						response.setResponseMessage("Token Generated Successfully");
						response.setResponseCode("200");
						
						return response;
					} else if (resStatus.equalsIgnoreCase("04") || resStatus.equalsIgnoreCase("01")
							|| resStatus.equalsIgnoreCase("02")) {
						response.setResponseMessage("We are currently upgrading system, please try again later");
						response.setResponseCode("202");
						return response;
					}
				}

			}

			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER) || roleName.equalsIgnoreCase("Corporate Maker")
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				ObjectMapper mapper = new ObjectMapper();

				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
				adminData.setAppId(bankToken.getAppId());
				adminData.setCreatedByUserId(bankToken.getUser_ID());
				adminData.setCreatedByRoleId(bankToken.getRole_ID());
				adminData.setPageId(bankToken.getSubMenu_ID()); // set submenuId as pageid
				adminData.setCreatedBy(bankToken.getCreatedUpdatedBy());
				adminData.setCreatedOn(new Timestamp(System.currentTimeMillis()));
				adminData.setContent(mapper.writeValueAsString(bankToken));
				adminData.setActivityId(bankToken.getSubMenu_ID()); // set submenuId as activityid
				adminData.setStatusId(BigDecimal.valueOf(statusId)); // 97- ADMIN_CHECKER_PENDIN
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6)); // 6- checker roleid
				adminData.setRemark(bankToken.getRemark());
				adminData.setActivityName(bankToken.getActivityName());
				adminData.setActivityRefNo(bankToken.getId());
				adminData.setTableName("BANKTOKEN");
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);

				adminWorkFlowReqUtility.saveToAdminWorkFlowHistoryContent(bankToken.getSubMenu_ID(), bankToken.getId(),
						bankToken.getCreatedUpdatedBy(), bankToken.getRemark(), bankToken.getRole_ID(),
						mapper.writeValueAsString(bankToken));
			}

		} catch (Exception e) {
			LOGGER.error("Exception:", e);
		}

		/*
		 * if (res > 0) { return true; } else { return false; }
		 */
		return response;
	}

	@Override
	public List<BankTokenEntity> getBankTokenById(int id) {
		List<BankTokenEntity> list = null;
		try {
			String sqlquery = "select bk.id, bk.customerId as customerId,bk.accountNumber, bk.statusId , bk.referencenumber , bk.appId,bk.reqInitiatedFor,bk.typeOfRequest,  "
					+ " s.name as statusName, c.CUSTOMERNAME as customername, c.mobile as mobile, c.cif, bk.branch_Code as branchCode from BANKTOKEN bk "
					+ " inner join customers c on c.id=bk.customerId inner join statusMaster s on s.id=bk.statusid and bk.id=:id";

			list = getSession().createSQLQuery(sqlquery).addScalar("id").addScalar("customerId")
					.addScalar("accountNumber").addScalar("statusId").addScalar("referencenumber").addScalar("appId")
					.addScalar("reqInitiatedFor").addScalar("typeOfRequest").addScalar("statusName")
					.addScalar("customername").addScalar("mobile").addScalar("cif").addScalar("branchCode")
					.setParameter("id", id).setResultTransformer(Transformers.aliasToBean(BankTokenEntity.class))
					.list();
		} catch (Exception e) {
			LOGGER.error("Exception:", e);
		}
		return list;
	}

	@Override
	public boolean rejectBankToken(BankTokenEntity bankToken) {
		int res = 0;
		int rejectSts = adminWorkFlowReqUtility.getStatusIdByName("REJECTED");
		try {

			String sql = "update BANKTOKEN set statusid=:status where id=:id";
			res = getSession().createSQLQuery(sql).setParameter("status", rejectSts)
					.setParameter("id", bankToken.getId()).executeUpdate();
			LOGGER.info("2");
		} catch (Exception e) {
			LOGGER.error("Exception:", e);
		}
		if (res > 0)
			return true;
		else
			return false;

	}

	public int updateCustomerStatus(BankTokenEntity bankToken) {
		int res = 0;
		String encMobileNo = EncryptorDecryptor.encryptData(bankToken.getMobile());

		try {
			if (bankToken.getReqInitiatedFor().intValue() == 4) {
				String sql = "update Customers set IBREGSTATUS=:status where  mobile=:mobile";
				res = getSession().createSQLQuery(sql).setParameter("status", 30).setParameter("mobile", encMobileNo)
						.executeUpdate();
			} else {
				String sql = "update Customers set MOBREGSTATUS=:status where  mobile=:mobile";
				res = getSession().createSQLQuery(sql).setParameter("status", 30).setParameter("mobile", encMobileNo)
						.executeUpdate();
			}

			return res;
		} catch (Exception e) {
			LOGGER.error("Exception:", e);
			return res;

		}

	}

	@Override
	public List<CustomerTokenEntity> getAllCustomerTokenRequest() {
		List<CustomerTokenEntity> list = null;

		try {
			String sqlquery = " select ct.id,ct.channelId,ct.appId,ct.rrn,ct.token,ct.tokenValidatedOn,ct.generatedOnChannelId,ct.customerId,"
					+ "ct.validatedOnChannelId,ct.invalidTokenAttempts , ct.activityId,ct.createdBy, ct.createdOn,ct.updatedBy,"
					+ " ct.updatedOn,ct.statusId,ct.type, s.shortname as statusName, a.shortname as appName from CUSTOMERTOKEN ct  "
					+ "inner join statusMaster s on s.id= ct.statusId inner join appmaster a on a.id=ct.appId";

			list = getSession().createSQLQuery(sqlquery).addScalar("id").addScalar("channelId").addScalar("appId")
					.addScalar("rrn").addScalar("token").addScalar("tokenValidatedOn").addScalar("generatedOnChannelId")
					.addScalar("customerId").addScalar("validatedOnChannelId").addScalar("invalidTokenAttempts")
					.addScalar("activityId").addScalar("createdBy").addScalar("createdOn").addScalar("updatedBy")
					.addScalar("updatedOn").addScalar("statusId").addScalar("type").addScalar("statusName")
					.addScalar("appName").setResultTransformer(Transformers.aliasToBean(CustomerTokenEntity.class))
					.list();
		} catch (Exception e) {
			LOGGER.error("Exception:", e);
		}
		return list;
	}

	@Override
	public boolean generateCustomerToken(CustomerTokenEntity custToken) {
		Session session = sessionFactory.getCurrentSession();
		int userStatus = custToken.getStatusId().intValue();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(custToken.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(custToken.getActivityName());
		custToken.setActivityId(BigDecimal.valueOf(2020181158));

		try {
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				custToken.setStatusId(BigDecimal.valueOf(statusId)); // 97-ADMIN_CHECKER_PENDIN
				custToken.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
				custToken.setRrn(String.valueOf(System.currentTimeMillis()));
				session.save(custToken);
			} else {
				List<ConfigMasterEntity> Configlist = adminWorkFlowReqUtility.getomniChannelConfigDetails(3,
						"OTP_LENGTH");
				String actCodeNew = String
						.valueOf(adminWorkFlowReqUtility.zeroPad(adminWorkFlowReqUtility.generateActivationCode(),
								Integer.parseInt(Configlist.get(0).getConfigValue().toString())));
				adminWorkFlowReqUtility.sendTokenNotificationToCust(custToken.getCustomerId().intValue(),
						custToken.getAppId().intValue(), actCodeNew);
				LOGGER.info("otp:" + actCodeNew);
				custToken.setToken(Integer.parseInt(actCodeNew));
				custToken.setUpdatedOn(new Date());
				custToken.setRrn(String.valueOf(System.currentTimeMillis()));
				session.save(custToken);
			}

			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				List<CustomerTokenEntity> list = getAllCustomerTokenRequest();
				ObjectMapper mapper = new ObjectMapper();

				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
				adminData.setAppId(custToken.getAppId());
				adminData.setCreatedByUserId(custToken.getUser_ID());
				adminData.setCreatedByRoleId(custToken.getRole_ID());
				adminData.setPageId(custToken.getSubMenu_ID());
				adminData.setCreatedBy(BigDecimal.valueOf(custToken.getCreatedBy()));
				adminData.setContent(mapper.writeValueAsString(custToken));
				adminData.setActivityId(custToken.getSubMenu_ID());
				adminData.setStatusId(BigDecimal.valueOf(statusId));
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6));
				adminData.setRemark(custToken.getRemark());
				adminData.setActivityName(custToken.getActivityName());
				adminData.setActivityRefNo(list.get(0).getId());
				adminData.setTableName("CUSTOMERTOKEN");
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);

				// Save data to admin work flow history
				adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(custToken.getSubMenu_ID(), list.get(0).getId(),
						new BigDecimal(custToken.getCreatedBy()), custToken.getRemark(), custToken.getRole_ID(),
						mapper.writeValueAsString(custToken));
			}

			return true;
		} catch (Exception e) {
			LOGGER.error("Exception:", e);
			return false;
		}
	}

	@Override
	public List<CustomerTokenEntity> getAdminBankTokenById(int id) {
		List<CustomerTokenEntity> list = null;
		int pendingStts = adminWorkFlowReqUtility.getStatusIdByName("PENDING");
		try {
			String sqlquery = "select bk.id, bk.customername ,bk.accountNumber, bk.mobile , bk.statusId , bk.referencenumber , bk.channel, "
					+ " 'Pending' as statusName from BANKTOKEN bk  where bk.statusid =:pendingStts and bk.id=:id";

			list = getSession().createSQLQuery(sqlquery).addScalar("id").addScalar("customername")
					.addScalar("accountNumber").addScalar("mobile").addScalar("statusId").addScalar("referencenumber")
					.addScalar("channel").addScalar("statusName").setParameter("id", id)
					.setParameter("pendingStts", pendingStts)
					.setResultTransformer(Transformers.aliasToBean(CustomerTokenEntity.class)).list();
		} catch (Exception e) {
			LOGGER.error("Exception:", e);
		}
		return list;
	}

	@Override
	public boolean rejectBankTokenByAdmin(CustomerTokenEntity bankToken) {
		int res = 0;
		int rejectSts = adminWorkFlowReqUtility.getStatusIdByName("REJECTED");
		try {
			LOGGER.info("1");
			String sql = "update BANKTOKEN set statusid=:status where id=:id";
			res = getSession().createSQLQuery(sql).setParameter("status", rejectSts)
					.setParameter("id", bankToken.getId()).executeUpdate();
			LOGGER.info("2");
		} catch (Exception e) {
			LOGGER.error("Exception:", e);
		}
		if (res > 0)
			return true;
		else
			return false;

	}

	@Override
	public List<BankTokenEntity> getBankTokenByRefNo(long refNo) {
		List<BankTokenEntity> list = null;
		try {
			String sqlquery = "select bk.id, bk.customername ,bk.accountNumber, bk.mobile , bk.statusId , bk.referencenumber , bk.channel, "
					+ " 'Pending' as statusName from BANKTOKEN bk  where bk.REFERENCENUMBER =:refNo  ";

			list = getSession().createSQLQuery(sqlquery).addScalar("id").addScalar("customername")
					.addScalar("accountNumber").addScalar("mobile").addScalar("statusId").addScalar("referencenumber")
					.addScalar("channel").addScalar("statusName").setParameter("refNo", refNo)
					.setResultTransformer(Transformers.aliasToBean(BankTokenEntity.class)).list();
		} catch (Exception e) {
			LOGGER.error("Exception:", e);
		}
		return list;
	}

}
