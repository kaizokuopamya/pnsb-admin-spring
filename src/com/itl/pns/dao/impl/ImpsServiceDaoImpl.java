package com.itl.pns.dao.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.itl.pns.bean.DateBean;
import com.itl.pns.bean.ImpsCustBean;
import com.itl.pns.bean.ImpsOtpLog;
import com.itl.pns.bean.ImpsRevisionBean;
import com.itl.pns.bean.ImpsStationBean;
import com.itl.pns.bean.ImpsTotalTransLogBean;
import com.itl.pns.bean.ImpsTransactionFeeRevisionBean;
import com.itl.pns.bean.ImpsTransactionFeeSetupBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.bean.TransLogBean;

import com.itl.pns.dao.ImpsServiceDao;
import com.itl.pns.entity.ImpsStatusEntity;
import com.itl.pns.entity.SyslogEntity;
import com.itl.pns.impsEntity.CreditPoolAccountConfEntity;
import com.itl.pns.impsEntity.DebitPoolAccountConfEntity;
import com.itl.pns.impsEntity.DeliveryChannelsEntity;
import com.itl.pns.impsEntity.EeuserEntity;
import com.itl.pns.impsEntity.EeuserRolesEntity;
import com.itl.pns.impsEntity.IfscCodeMasterEntity;
import com.itl.pns.impsEntity.ImpsMasterEntity;
import com.itl.pns.impsEntity.ImpsReportEntity;
import com.itl.pns.impsEntity.ImpsRolesEntity;
import com.itl.pns.impsEntity.ImpsTaskEntity;
import com.itl.pns.impsEntity.ImpsTransactionFeeSetUpEntity;
import com.itl.pns.impsEntity.PermissionEntity;
import com.itl.pns.impsEntity.ReportCatrgoryEntity;
import com.itl.pns.impsEntity.RolePermsEntity;
import com.itl.pns.impsEntity.ScheduleEntity;
import com.itl.pns.impsEntity.SmsTemplatesEntity;
import com.itl.pns.impsEntity.SysconfigEntity;
import com.itl.pns.util.AdminWorkFlowReqUtility;

/**
 * @author shubham.lokhande
 *
 */
@Repository
@Transactional
public class ImpsServiceDaoImpl implements ImpsServiceDao {

	static Logger LOGGER = Logger.getLogger(ImpsServiceDaoImpl.class);

	AdminWorkFlowReqUtility adminWorkFlowReqUtility;

	@Autowired
	// @Qualifier("impsSessionFactory")
	private SessionFactory impsSessionFactory;

	protected Session getSession() {
		return impsSessionFactory.getCurrentSession();
	}

	@Override
	public List<ImpsStatusEntity> getImpsStatusList() {
		List<ImpsStatusEntity> list = null;
		try {
			String sqlQuery = " select s.id as id, s.name as name,s.state as state,s.detail as detail, s.GROUP_NAME as groupName, s.LAST_TICK as lastTick, s.TIMEOUT as timeOut,"
					+ "   s.timeout_state as timeoutState, s.COMMAND as command, s.VALID_COMMANDS as validCommands, s.EXPIRED as expired, s.MAX_EVENTS as maxEvents,"
					+ "   s.TAGS as tags, s.ENABLE_EMAIL as enableEmail from status s";
			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("name").addScalar("state")
					.addScalar("detail").addScalar("groupName").addScalar("lastTick").addScalar("timeOut")
					.addScalar("timeoutState").addScalar("command").addScalar("validCommands").addScalar("expired")
					.addScalar("maxEvents").addScalar("tags").addScalar("enableEmail")
					.setResultTransformer(Transformers.aliasToBean(ImpsStatusEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	public List<ImpsStatusEntity> getImpsStatusById(ImpsStatusEntity impsStatusData) {
		List<ImpsStatusEntity> list = null;
		try {
			String sqlQuery = " select s.id as id, s.name as name,s.state as state,s.detail as detail, s.GROUP_NAME as groupName, s.LAST_TICK as lastTick, s.TIMEOUT as timeOut,"
					+ "   s.timeout_state as timeoutState, s.COMMAND as command, s.VALID_COMMANDS as validCommands, s.EXPIRED as expired, s.MAX_EVENTS as maxEvents,"
					+ "   s.TAGS as tags, s.ENABLE_EMAIL as enableEmail from status s where s.id=:id";
			list = getSession().createSQLQuery(sqlQuery).setParameter("id", impsStatusData.getId())
					.setResultTransformer(Transformers.aliasToBean(ImpsStatusEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	public boolean deleteImpsStatusById(ImpsStatusEntity impsStatusData) {
		boolean success = true;
		try {
			String sqlQuery = "delete from STATUS where id=:id";
			getSession().createSQLQuery(sqlQuery).setParameter("id", impsStatusData.getId()).executeUpdate();
			success = true;
		}

		catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}

		return success;
	}

	public List<ImpsStatusEntity> getImpsStatusByState(ImpsStatusEntity impsStatusData) {
		List<ImpsStatusEntity> list = null;
		try {
			String sqlQuery = " select s.id as id, s.name as name,s.state as state,s.detail as detail, s.GROUP_NAME as groupName, s.LAST_TICK as lastTick, s.TIMEOUT as timeOut,"
					+ "   s.timeout_state as timeoutState, s.COMMAND as command, s.VALID_COMMANDS as validCommands, s.EXPIRED as expired, s.MAX_EVENTS as maxEvents,"
					+ "   s.TAGS as tags, s.ENABLE_EMAIL as enableEmail from status s where LOWER(s.state)=:state";
			list = getSession().createSQLQuery(sqlQuery).addScalar("state").addScalar("groupName").addScalar("name")
					.addScalar("detail").addScalar("lastTick").addScalar("expired").addScalar("timeoutState")
					.addScalar("enableEmail").setParameter("state", impsStatusData.getState().toLowerCase())
					.setResultTransformer(Transformers.aliasToBean(ImpsStatusEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public boolean updateImpsStatusData(ImpsStatusEntity impsStatusData) {
		Session session = impsSessionFactory.getCurrentSession();
		try {
			session.update(impsStatusData);

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
		return true;
	}

	@Override
	public List<SyslogEntity> getSyslogs() {
		List<SyslogEntity> list = null;
		try {
			String sqlQuery = " select s.id as id, s.LOG_DATE as logDate , s.deleted as deleted, s.source as source, s.type as type, s.severity as severity,"
					+ "    s.summary as summary, s.detail as detail, s.trace as trace from SYSLOG s";
			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("logDate").addScalar("source")
					.addScalar("type").addScalar("summary")
					.setResultTransformer(Transformers.aliasToBean(SyslogEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	public ResponseMessageBean getStationCount() {
		ResponseMessageBean res = new ResponseMessageBean();
		try {

			String sqlQuery = "select count(*) as stationCount from stations ";
			List<String> list = null;
			System.out.println(sqlQuery);

			list = getSession().createSQLQuery(sqlQuery)
					.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).list();
			if (!ObjectUtils.isEmpty(list)) {
				res.setResponseCode("200");
				res.setResult(list);
				res.setResponseMessage("Success");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return res;
	}

	@Override
	public ResponseMessageBean getTransLogs(DateBean dateObj) {
		List<String> list = null;
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			String sqlQuery = "select * from tranlog where tdate between TO_DATE('" + dateObj.getFromdate()
					+ "','yyyy-mm-dd'" + ") and TO_DATE('" + dateObj.getTodate() + "','yyyy-mm-dd'" + ")";
			list = getSession().createSQLQuery(sqlQuery).addScalar("local_transaction_date").addScalar("ss")
					.addScalar("itc").addScalar("irc").addScalar("pan").addScalar("approval_number").addScalar("amount")
					.addScalar("duration").setResultTransformer(Transformers.aliasToBean(TransLogBean.class)).list();
			if (!ObjectUtils.isEmpty(list)) {
				res.setResponseCode("200");
				res.setResult(list);
				res.setResponseMessage("Success");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return res;
	}

	@Override
	public ResponseMessageBean getAllTransLog(DateBean dateObj) {
		List<String> list = null;
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			String sqlQuery = "   select *from tranlog t where t.tdate>=:fromDate and t.tdate<=:toDate";
			list = getSession().createSQLQuery(sqlQuery).setParameter("fromDate", dateObj.getFromdate())
					.setParameter("toDate", dateObj.getTodate())
					.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).list();
			;

			if (!ObjectUtils.isEmpty(list)) {
				res.setResponseCode("200");
				res.setResult(list);
				res.setResponseMessage("Success");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return res;
	}

	@Override
	public ResponseMessageBean getImpsTransLog(DateBean dateObj) {
		List<String> list = null;
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			String sqlQuery = "select * from imps_tranlog where created_date between TO_DATE('" + dateObj.getFromdate()
					+ "','yyyy-mm-dd'" + ") and TO_DATE('" + dateObj.getTodate() + "','yyyy-mm-dd'" + ")";
			list = getSession().createSQLQuery(sqlQuery).addScalar("tran_type").addScalar("direction")
					.addScalar("created_date").addScalar("amount")
					.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).list();
			if (!ObjectUtils.isEmpty(list)) {
				res.setResponseCode("200");
				res.setResult(list);
				res.setResponseMessage("Success");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return res;
	}

	@Override
	public ResponseMessageBean getImpsTransLogByRRN(TransLogBean transLogData) {
		List<String> list = null;
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			String sqlQuery = "select it.id, it.direction, it.tran_type, it.bene_nbin, it.bene_mas, it.bene_ifsc, it.bene_account, it.bene_mobile, it.bene_name, it.remitter_mmid, it.remitter_mobile, "
					+ "		it.remitter_name, it.product_indicator, it.remarks, it.originating_channel, it.status, it.original_data, it.f120_request, it.f120_response, it.cdci_req_status, "
					+ "		it.cdci_rc, it.cdci_rev_status, it.cdci_rev_rc, it.tran_id, it.asp_id, it.submember_id,	"
					+ " it.created_date, it.nfs_status, it.nfs_response_code, it.remitter_account, it.remitter_charge, it.remitter_service_tax, it.amount,"
					+ "it.nfs_verify_status, it.nfs_verify_rc, it.f120_vm_response, it.delivery_channel_id, it.cust_id_remitter, it.cust_id_bene, it.bc_id, it.bc_retailer_code, it.bc_trans_ref_no, "
					+ "it.merchant_detail_id, it.merchant_tran_ref, it.nfs_error_text, it.merchant_tran_id, it.pg_tran_id, it.bene_aadhar_number, it.ipay_tran_id, it.intermediary_ifsc as intermediary_ifsc,"
					+ "   it.NRI_INSTITUTION_NAME as nri_institution_name, it.NRI_INST_ACCOUNT_NUMBER as nri_inst_account_number, it.ORIGINATOR_NAME as originator_name, it.ORIGINATOR_ACCOUNT_NUMBER as originator_account_number, it.ORIGINATOR_ADDRESS as originator_address, "
					+ "it.PURPOSE_CODE as purpose_code, it.NRI_REFERENCENO as nri_referenceno , it.EXCHANGE_REFERENCENO as exchange_referenceno, "
					+ "it.CDCI_TXN_TYPE as cdci_txn_type, it.bc_transaction_time, it.BC_MER_BENE_TXN as bc_mer_bene_txn, it. CDCI_IP as cdci_ip , it.CDCI_PORT as cdci_port, it.USER_ID as user_id, it.CUSTOMER_ID as customer_id, it.DEBIT_PULL_ACCOUNT as debit_pull_account, it.CREDIT_PULL_ACCOUNT as credit_pull_account, it.ss_trans_ref_no, "
					+ "   it.ss_transaction_time, it.ATM_TID as atm_tid, it.ATM_LOCATION as atm_location, it.instrument_dtls, it.additonal_info, it.source_channel,it.source_payee_name,it.payer_bank_name, t.id as trasId,  t.local_id,  "
					+ "  t.node,  t.ss , t.ds,  t.acquirer,  t.forwarding,  t.mid,  t.tid,  t.hash,  t.pan,  t.kid,  t.ss_stan,  t.ss_rrn,  t.ds_rrn,  t.ds_stan,  t.ca_name,  t.ca_city, "
					+ "   t.ca_region,  t.ca_postal_code,  t.ca_country,  t.mcc,  t.function_code,  t.ss_transport_data,  t.ds_transport_data, t.response_code,  t.approval_number, "
					+ "  t.display_message,  t.reversal_count,  t.reversal_id,  t.completion_count,  t.completion_id,  t.void_count,  t.void_id,  t.notification_count,  t.original_mti,  t.tdate,  t.transmission_date,"
					+ "  t.local_transaction_date,  t.capture_date,  t.settlement_date,  t.batch_number,   t.source_batch_number,  t.destination_batch_number,  t.itc,  t.src_acct_type,  t.dest_acct_type, irc,  t.currency_code,"
					+ " t.amount as trasAmt,  t.additional_amount,  t.amount_cardholder_billing,  t.acquirer_fee,  t.issuer_fee,  t.returned_balances, t.from_account,  t.to_account,  t.mini_statement,  t.smw_command,  t.smw_params,"
					+ "  t.smw_cmd_response,  t.smw_ext_response, "
					+ "  t.avlbl_bal_from,  t.legder_bal_from,  t.avlbl_bal_to,  t.legder_bal_to,  t.rc,  t.extrc,  t.duration,  t.outstanding,  t.ss_rc,  t.ds_rc,  t.ss_pcode,  t.ds_pcode,"
					+ "   t.host_command,  t.host_cmd_response,  t.host_ext_response, "
					+ "  t.cardProduct as cardproduct ,  t.refId as refid,  t.notification from imps_tranlog it left join tranlog t  on it.tran_id=t.id where t.ss_rrn=:rrn order by it.id asc ";
			list = getSession().createSQLQuery(sqlQuery).addScalar("tran_type").addScalar("direction")
					.addScalar("created_date").addScalar("amount").setParameter("rrn", transLogData.getSs_rrn())
					.setResultTransformer(Transformers.aliasToBean(ImpsTotalTransLogBean.class)).list();

			if (!ObjectUtils.isEmpty(list)) {
				res.setResponseCode("200");
				res.setResult(list);
				res.setResponseMessage("Success");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return res;
	}

	@Override
	public ResponseMessageBean getImpsCustDetails(ImpsCustBean impsCustReq) {
		List<String> list = null;
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			String sqlQuery = "  select *from  imps_customer_details where  mbl_del_flag=:mblDelFlag and lower(mbl_mobile_no) 	Like("
					+ impsCustReq.getMbl_mobile_no() + ") order by  id desc ";
			list = getSession().createSQLQuery(sqlQuery).addScalar("mbl_mobile_no").addScalar("mbl_account_no")
					.addScalar("mbl_bene_name").addScalar("mbl_reg_channel").addScalar("mbl_rcre_date")
					.setParameter("mblDelFlag", impsCustReq.getMbl_del_flag())
					.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).list();
			;

			if (!ObjectUtils.isEmpty(list)) {
				res.setResponseCode("200");
				res.setResult(list);
				res.setResponseMessage("Success");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return res;
	}

	@Override
	public List<SysconfigEntity> getSysConfigDataById(SysconfigEntity sysConfigReq) {
		List<SysconfigEntity> list = null;
		try {
			String sqlQuery = "select s.id as id, s.value as value, s.readperm as readperm, s.writeperm as writeperm from SYSCONFIG s where s.id=:id";
			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("value").addScalar("readperm")
					.addScalar("writeperm").setParameter("id", sysConfigReq.getId())
					.setResultTransformer(Transformers.aliasToBean(SysconfigEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public List<SysconfigEntity> getAllSysConfigData() {
		List<SysconfigEntity> list = null;
		try {
			String sqlQuery = "select s.id as id, s.value as value, s.readperm as readperm, s.writeperm as writeperm from SYSCONFIG s ";
			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("value")
					.setResultTransformer(Transformers.aliasToBean(SysconfigEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public boolean insertSysConfigData(SysconfigEntity sysConfigReq) {
		Session session = impsSessionFactory.getCurrentSession();
		try {
			session.save(sysConfigReq);

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
		return true;
	}

	@Override
	public boolean updateSysConfigData(SysconfigEntity sysConfigReq) {
		Session session = impsSessionFactory.getCurrentSession();
		try {
			session.update(sysConfigReq);

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
		return true;
	}

	@Override
	public boolean deleteSysConfigData(SysconfigEntity sysConfigReq) {
		boolean success = true;
		try {
			String sqlQuery = "delete from SYSCONFIG where id=:id";
			getSession().createSQLQuery(sqlQuery).setParameter("id", sysConfigReq.getId()).executeUpdate();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}

		return success;
	}

	@Override
	public List<EeuserRolesEntity> getEeuserDataByEeuser(EeuserRolesEntity eeUserRoleData) {
		List<EeuserRolesEntity> list = null;
		try {
			String sqlQuery = "select eeuser, role,  id as roleId , name  from  eeuser_roles inner join role on role=id   where  eeuser=:eeuserData";
			list = getSession().createSQLQuery(sqlQuery).setParameter("eeuserData", eeUserRoleData.getEeuser())
					.setResultTransformer(Transformers.aliasToBean(EeuserRolesEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public boolean insertEeuserRole(EeuserRolesEntity eeUserRoleData) {
		Session session = impsSessionFactory.getCurrentSession();
		try {
			session.save(eeUserRoleData);

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
		return true;
	}

	@Override
	public boolean updateEeuserData(EeuserEntity eeuserData) {
		Session session = impsSessionFactory.getCurrentSession();
		try {
			session.update(eeuserData);

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
		return true;
	}

	@Override
	public boolean deleteEeuserRole(EeuserRolesEntity eeuserData) {
		boolean success = true;
		try {
			String sqlQuery = "delete from   eeuser_roles  where  eeuser=:eeuserData ";
			getSession().createSQLQuery(sqlQuery).setParameter("eeuserData", eeuserData.getEeuser()).executeUpdate();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}

		return success;
	}

	@Override
	public List<PermissionEntity> getPermissionByName(PermissionEntity permissionReq) {
		List<PermissionEntity> list = null;
		try {
			String sqlQuery = "select  name, value from  permission  where  Lower(name)=:nameData ";
			list = getSession().createSQLQuery(sqlQuery).setParameter("nameData", permissionReq.getName().toLowerCase())
					.setResultTransformer(Transformers.aliasToBean(PermissionEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public boolean updatePermission(PermissionEntity permissionReq) {
		Session session = impsSessionFactory.getCurrentSession();
		try {
			session.update(permissionReq);

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
		return true;
	}

	@Override
	public boolean insertPermission(PermissionEntity permissionReq) {
		Session session = impsSessionFactory.getCurrentSession();
		try {
			session.save(permissionReq);

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
		return true;
	}

	@Override
	public boolean deletePermission(PermissionEntity permissionReq) {
		boolean success = true;
		try {
			String sqlQuery = "delete from   PERMISSION  where  name=:name ";
			getSession().createSQLQuery(sqlQuery).setParameter("name", permissionReq.getName()).executeUpdate();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}

		return success;
	}

	@Override
	public boolean insertRolePerms(RolePermsEntity rolePermsReq) {
		Session session = impsSessionFactory.getCurrentSession();
		try {
			session.save(rolePermsReq);

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
		return true;
	}

	@Override
	public boolean deleteRolePerms(RolePermsEntity rolePermsReq) {
		boolean success = true;
		try {
			String sqlQuery = "delete from   ROLE_PERMS  where  role=:roleData ";
			getSession().createSQLQuery(sqlQuery).setParameter("roleData", rolePermsReq.getRole()).executeUpdate();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}

		return success;
	}

	@Override
	public List<ImpsRolesEntity> getAllImpsRoles() {
		List<ImpsRolesEntity> list = null;
		try {
			String sqlQuery = "select  id ,name from  ROLE ";
			list = getSession().createSQLQuery(sqlQuery)
					.setResultTransformer(Transformers.aliasToBean(ImpsRolesEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public List<ImpsRolesEntity> getAllImpsRolesById(ImpsRolesEntity ImpsRolesReq) {
		List<ImpsRolesEntity> list = null;
		try {
			String sqlQuery = "select  id ,name from  ROLE where id=:id";
			list = getSession().createSQLQuery(sqlQuery).setParameter("id", ImpsRolesReq.getId())
					.setResultTransformer(Transformers.aliasToBean(ImpsRolesEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public boolean updateImpsRole(ImpsRolesEntity impsRoleReq) {
		Session session = impsSessionFactory.getCurrentSession();
		try {
			session.update(impsRoleReq);

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
		return true;
	}

	@Override
	public boolean insertImpsRole(ImpsRolesEntity impsRoleReq) {
		Session session = impsSessionFactory.getCurrentSession();
		try {
			session.save(impsRoleReq);

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
		return true;
	}

	@Override
	public boolean deleteImpsRole(ImpsRolesEntity impsRoleReq) {
		boolean success = true;
		try {
			String sqlQuery = "delete from   ROLE  where  id=:id ";
			getSession().createSQLQuery(sqlQuery).setParameter("id", impsRoleReq.getId()).executeUpdate();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}

		return success;
	}

	@Override
	public List<ImpsStationBean> getStationsByName(ImpsStationBean impsStationData) {
		List<ImpsStationBean> list = null;
		try {
			String sqlQuery = "select *from stations where name=:name";
			list = getSession().createSQLQuery(sqlQuery).setParameter("name", impsStationData.getName())
					.setResultTransformer(Transformers.aliasToBean(ImpsStationBean.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public List<ImpsStationBean> getAllStations() {
		List<ImpsStationBean> list = null;
		try {
			String sqlQuery = "select *from stations";
			list = getSession().createSQLQuery(sqlQuery).addScalar("name").addScalar("station_type")
					.addScalar("signed_on").addScalar("connected").addScalar("last_connect")
					.addScalar("last_disconnect").setResultTransformer(Transformers.aliasToBean(ImpsStationBean.class))
					.list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return list;
	}

	@Override
	public List<SmsTemplatesEntity> getSmsTemplates() {
		List<SmsTemplatesEntity> list = null;
		try {
			String sqlQuery = "select *from SMS_TEMPLATES ";
			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("type").addScalar("name").addScalar("target")
					.addScalar("tran_type").addScalar("cdci_status").addScalar("nfs_response_code")
					.addScalar("template").setResultTransformer(Transformers.aliasToBean(SmsTemplatesEntity.class))
					.list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public List<SmsTemplatesEntity> getSmsTemplatesById(SmsTemplatesEntity smsTempReq) {
		List<SmsTemplatesEntity> list = null;
		try {
			String sqlQuery = "select *from SMS_TEMPLATES where id=:id";
			list = getSession().createSQLQuery(sqlQuery).addScalar("type").addScalar("name").addScalar("target")
					.addScalar("tran_type").addScalar("cdci_status").addScalar("nfs_response_code")
					.addScalar("template").setParameter("id", smsTempReq.getId())
					.setResultTransformer(Transformers.aliasToBean(SmsTemplatesEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public boolean updateSmsTemplate(SmsTemplatesEntity impsStationData) {
		Session session = impsSessionFactory.getCurrentSession();
		try {
			session.update(impsStationData);

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
		return true;
	}

	@Override
	public List<ImpsTransactionFeeSetupBean> getTransFeeSetupByApplyFeeAndTransType(
			ImpsTransactionFeeSetupBean transactionFeeSetupReq) {
		List<ImpsTransactionFeeSetupBean> list = null;
		try {
			String sqlQuery = "select *from transaction_fee_setup where apply_fee=:applyfee and  transaction_type=:transType ";
			list = getSession().createSQLQuery(sqlQuery).addScalar("transaction_type")
					.addScalar("transaction_direction").addScalar("apply_fee").addScalar("description")
					.setParameter("applyfee", transactionFeeSetupReq.getApply_fee())
					.setParameter("transType", transactionFeeSetupReq.getTransaction_type())
					.setResultTransformer(Transformers.aliasToBean(ImpsTransactionFeeSetupBean.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public ResponseMessageBean getTransactionFeeRevisionCount(ImpsTransactionFeeRevisionBean transFreeReq) {
		ResponseMessageBean res = new ResponseMessageBean();
		try {

			String sqlQuery = "select count(*) from transaction_fee_revision where deleted =:del and fee_setup is not null ";
			List<String> list = null;
			System.out.println(sqlQuery);

			list = getSession().createSQLQuery(sqlQuery).setParameter("del", transFreeReq.getDeleted())
					.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).list();
			if (!ObjectUtils.isEmpty(list)) {
				res.setResponseCode("200");
				res.setResult(list);
				res.setResponseMessage("Success");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return res;
	}

	@Override
	public List<IfscCodeMasterEntity> getAllIfscCodeDetails() {
		List<IfscCodeMasterEntity> list = null;
		try {
			String sqlQuery = "select *from IFSC_CODE_MASTER order by id desc";
			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("ifsc_code").addScalar("nbin").addScalar("bank_name")
					.addScalar("bank_type").addScalar("is_live").addScalar("use_customized_ifsc")
					.setResultTransformer(Transformers.aliasToBean(IfscCodeMasterEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public boolean insertIfscCode(IfscCodeMasterEntity ifscCodeMaster) {
		Session session = impsSessionFactory.getCurrentSession();
		try {
			ifscCodeMaster.setCreated_on(new Date());
			session.save(ifscCodeMaster);

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
		return true;
	}

	@Override
	public boolean updateIfscCode(IfscCodeMasterEntity ifscCodeMaster) {
		Session session = impsSessionFactory.getCurrentSession();
		try {
			ifscCodeMaster.setCreated_on(new Date());
			session.update(ifscCodeMaster);

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
		return true;
	}

	@Override
	public boolean deleteIfscCode(IfscCodeMasterEntity ifscCodeMaster) {
		boolean success = true;
		try {
			String sqlQuery = "delete from IFSC_CODE_MASTER  where  id=:id ";
			getSession().createSQLQuery(sqlQuery).setParameter("id", ifscCodeMaster.getId()).executeUpdate();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}

		return success;
	}

	@Override
	public List<DeliveryChannelsEntity> getAllDeliveryChannelDetails() {
		List<DeliveryChannelsEntity> list = null;
		try {
			String sqlQuery = "select *from DELIVERY_CHANNELS ";
			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("name").addScalar("nfs_channel_code")
					.addScalar("daily_limit_amount").addScalar("monthly_limit_amount")
					.setResultTransformer(Transformers.aliasToBean(DeliveryChannelsEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public boolean insertDeliveryChannel(DeliveryChannelsEntity deliverChannelReq) {
		Session session = impsSessionFactory.getCurrentSession();
		try {
			session.save(deliverChannelReq);

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
		return true;
	}

	@Override
	public boolean updateDeliveryChannel(DeliveryChannelsEntity deliverChannelReq) {
		Session session = impsSessionFactory.getCurrentSession();
		try {
			session.update(deliverChannelReq);

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
		return true;
	}

	@Override
	public boolean deleteDeliveryChannel(DeliveryChannelsEntity deliverChannelReq) {
		boolean success = true;
		try {
			String sqlQuery = "delete from DELIVERY_CHANNELS  where  id=:id ";
			getSession().createSQLQuery(sqlQuery).setParameter("id", deliverChannelReq.getId()).executeUpdate();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}

		return success;
	}

	@Override
	public List<ImpsRevisionBean> getAllRevisionDetails() {
		List<ImpsRevisionBean> list = null;
		try {
			String sqlQuery = "select *from revision order by id desc ";
			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("rev_date").addScalar("ref")
					.addScalar("author").addScalar("info")
					.setResultTransformer(Transformers.aliasToBean(ImpsRevisionBean.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public List<CreditPoolAccountConfEntity> getCreditPoolAccDetails() {
		List<CreditPoolAccountConfEntity> list = null;
		try {
			String sqlQuery = "   select id, TRAN_TYPE as tran_type ,  CHANNEL_ID as channel_id, DEFAULT_ACCOUNT as default_account ,"
					+ "SOURCE_IDENTIFIER as source_identifier from  CREDIT_POOL_ACCOUNT_CONF  ";
			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("tran_type").addScalar("source_identifier")
					.addScalar("default_account")
					.setResultTransformer(Transformers.aliasToBean(CreditPoolAccountConfEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public boolean insertCreditPoolAccDetails(CreditPoolAccountConfEntity creditPoolAccReq) {
		Session session = impsSessionFactory.getCurrentSession();
		try {
			session.save(creditPoolAccReq);

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
		return true;
	}

	@Override
	public boolean updateCreditPoolAccDetails(CreditPoolAccountConfEntity creditPoolAccReq) {
		Session session = impsSessionFactory.getCurrentSession();
		try {
			session.update(creditPoolAccReq);

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
		return true;
	}

	@Override
	public boolean deleteCreditPoolAccDetails(CreditPoolAccountConfEntity creditPoolAccReq) {
		boolean success = true;
		try {
			String sqlQuery = "delete from CREDIT_POOL_ACCOUNT_CONF  where  id=:id ";
			getSession().createSQLQuery(sqlQuery).setParameter("id", creditPoolAccReq.getId()).executeUpdate();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}

		return success;
	}

	@Override
	public List<DebitPoolAccountConfEntity> getDebitPoolAccDetails() {
		List<DebitPoolAccountConfEntity> list = null;
		try {
			String sqlQuery = "select *from DEBIT_POOL_ACCOUNT_CONF ";
			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("tran_type").addScalar("default_account")
					.addScalar("source_identifier")
					.setResultTransformer(Transformers.aliasToBean(DebitPoolAccountConfEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public boolean insertDebitPoolAccDetails(DebitPoolAccountConfEntity debitPoolAccReq) {
		Session session = impsSessionFactory.getCurrentSession();
		try {
			session.save(debitPoolAccReq);

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
		return true;
	}

	@Override
	public boolean updateDebitPoolAccDetails(DebitPoolAccountConfEntity debitPoolAccReq) {
		Session session = impsSessionFactory.getCurrentSession();
		try {
			session.update(debitPoolAccReq);

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
		return true;
	}

	@Override
	public boolean deleteDebitPoolAccDetails(DebitPoolAccountConfEntity debitPoolAccReq) {
		boolean success = true;
		try {
			String sqlQuery = "delete from DEBIT_POOL_ACCOUNT_CONF  where  id=:id ";
			getSession().createSQLQuery(sqlQuery).setParameter("id", debitPoolAccReq.getId()).executeUpdate();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}

		return success;

	}

	@Override
	public List<ImpsReportEntity> getAllReports() {
		List<ImpsReportEntity> list = null;
		try {
			String sqlQuery = "select *from REPORT ";
			list = getSession().createSQLQuery(sqlQuery).addScalar("name").addScalar("title").addScalar("query")
					.addScalar("sub_title_template").addScalar("file_name_template")
					.setResultTransformer(Transformers.aliasToBean(ImpsReportEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public List<ImpsReportEntity> getAllReportsById(ImpsReportEntity impsReportReq) {
		List<ImpsReportEntity> list = null;
		try {
			String sqlQuery = "select *from REPORT where id=:id ";
			list = getSession().createSQLQuery(sqlQuery).setParameter("id", impsReportReq.getId())
					.setResultTransformer(Transformers.aliasToBean(ImpsReportEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public boolean insertReportData(ImpsReportEntity impsReportReq) {
		Session session = impsSessionFactory.getCurrentSession();
		try {
			session.save(impsReportReq);

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
		return true;
	}

	@Override
	public boolean updateReportData(ImpsReportEntity impsReportReq) {
		Session session = impsSessionFactory.getCurrentSession();
		try {
			session.update(impsReportReq);

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
		return true;
	}

	@Override
	public boolean deleteReportData(ImpsReportEntity impsReportReq) {
		boolean success = true;
		try {
			String sqlQuery = "delete from REPORT  where  id=:id ";
			getSession().createSQLQuery(sqlQuery).setParameter("id", impsReportReq.getId()).executeUpdate();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}

		return success;
	}

	@Override
	public List<ImpsTaskEntity> getAllTasks() {
		List<ImpsTaskEntity> list = null;
		try {
			String sqlQuery = "select id as id, type as type,task_desc as task_desc from TASK ";
			list = getSession().createSQLQuery(sqlQuery).addScalar("type").addScalar("task_desc")
					.setResultTransformer(Transformers.aliasToBean(ImpsTaskEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public List<ImpsTaskEntity> getAllTasksById(ImpsTaskEntity taskReq) {
		List<ImpsTaskEntity> list = null;
		try {
			String sqlQuery = "select id as id, type as type,task_desc as task_desc from TASK where id=:id ";
			list = getSession().createSQLQuery(sqlQuery).setParameter("id", taskReq.getId())
					.setResultTransformer(Transformers.aliasToBean(ImpsTaskEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public boolean inserTaskData(ImpsTaskEntity taskReq) {
		Session session = impsSessionFactory.getCurrentSession();
		try {
			session.save(taskReq);

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
		return true;
	}

	@Override
	public boolean updateTaskData(ImpsTaskEntity taskReq) {
		Session session = impsSessionFactory.getCurrentSession();
		try {
			session.update(taskReq);

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
		return true;
	}

	@Override
	public boolean deleteTaskData(ImpsTaskEntity taskReq) {
		boolean success = true;
		try {
			String sqlQuery = "delete from TASK  where  id=:id ";
			getSession().createSQLQuery(sqlQuery).setParameter("id", taskReq.getId()).executeUpdate();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}

		return success;
	}

	@Override
	public List<ScheduleEntity> getScheduleData() {
		List<ScheduleEntity> list = null;
		try {
			String sqlQuery = "select  *from SCHEDULE ";
			list = getSession().createSQLQuery(sqlQuery).addScalar("schedule_desc").addScalar("task_interval")
					.addScalar("interval_unit").addScalar("next_exec_datetime").addScalar("active")
					.addScalar("delivery_type").setResultTransformer(Transformers.aliasToBean(ScheduleEntity.class))
					.list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public List<ScheduleEntity> getScheduleDataById(ScheduleEntity scheduleReq) {
		List<ScheduleEntity> list = null;
		try {
			String sqlQuery = "select  *from SCHEDULE where id=:id ";
			list = getSession().createSQLQuery(sqlQuery).setParameter("id", scheduleReq.getId())
					.setResultTransformer(Transformers.aliasToBean(ScheduleEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public boolean insertScheduleData(ScheduleEntity scheduleReq) {
		Session session = impsSessionFactory.getCurrentSession();
		try {

			session.save(scheduleReq);

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
		return true;
	}

	@Override
	public boolean updateScheduleData(ScheduleEntity scheduleReq) {
		Session session = impsSessionFactory.getCurrentSession();
		try {
			session.update(scheduleReq);

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
		return true;
	}

	@Override
	public boolean deleteScheduleData(ScheduleEntity scheduleReq) {
		boolean success = true;
		try {
			String sqlQuery = "delete from SCHEDULE  where  id=:id ";
			getSession().createSQLQuery(sqlQuery).setParameter("id", scheduleReq.getId()).executeUpdate();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}

		return success;
	}

	@Override
	public List<ReportCatrgoryEntity> getReportCategory() {
		List<ReportCatrgoryEntity> list = null;
		try {
			String sqlQuery = "select  *from REPORT_CATEGORY  ";
			list = getSession().createSQLQuery(sqlQuery).addScalar("title").addScalar("show_as_submenu")
					.setResultTransformer(Transformers.aliasToBean(ReportCatrgoryEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public List<ReportCatrgoryEntity> getReportCategoryById(ReportCatrgoryEntity reportCatReq) {
		List<ReportCatrgoryEntity> list = null;
		try {
			String sqlQuery = "select  *from REPORT_CATEGORY where id=:id ";
			list = getSession().createSQLQuery(sqlQuery).setParameter("id", reportCatReq.getId())
					.setResultTransformer(Transformers.aliasToBean(ReportCatrgoryEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public boolean insertReportCategory(ReportCatrgoryEntity reportCatReq) {
		Session session = impsSessionFactory.getCurrentSession();
		try {
			session.save(reportCatReq);

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
		return true;
	}

	@Override
	public boolean updateReportCategory(ReportCatrgoryEntity reportCatReq) {
		Session session = impsSessionFactory.getCurrentSession();
		try {
			session.update(reportCatReq);

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
		return true;
	}

	@Override
	public boolean deleteReportCategory(ReportCatrgoryEntity reportCatReq) {
		boolean success = true;
		try {
			String sqlQuery = "delete from REPORT_CATEGORY  where  id=:id ";
			getSession().createSQLQuery(sqlQuery).setParameter("id", reportCatReq.getId()).executeUpdate();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}

		return success;
	}

	@Override
	public List<CreditPoolAccountConfEntity> getCreditPoolAccDetailsById(CreditPoolAccountConfEntity creditPoolAccReq) {
		List<CreditPoolAccountConfEntity> list = null;
		try {
			String sqlQuery = "   select id, TRAN_TYPE as tran_type ,  CHANNEL_ID as channel_id, DEFAULT_ACCOUNT as default_account ,"
					+ "SOURCE_IDENTIFIER as source_identifier from  CREDIT_POOL_ACCOUNT_CONF  where id=:id";
			list = getSession().createSQLQuery(sqlQuery).addScalar("tran_type").addScalar("source_identifier")
					.addScalar("default_account").setParameter("id", creditPoolAccReq.getId())
					.setResultTransformer(Transformers.aliasToBean(CreditPoolAccountConfEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public List<DebitPoolAccountConfEntity> getDebitPoolAccDetailsBYId(DebitPoolAccountConfEntity debitPoolAccReq) {
		List<DebitPoolAccountConfEntity> list = null;
		try {
			String sqlQuery = "select *from DEBIT_POOL_ACCOUNT_CONF where id=:id";
			list = getSession().createSQLQuery(sqlQuery).addScalar("tran_type").addScalar("default_account")
					.addScalar("source_identifier").setParameter("id", debitPoolAccReq.getId())
					.setResultTransformer(Transformers.aliasToBean(DebitPoolAccountConfEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public List<IfscCodeMasterEntity> getAllIfscCodeDetailsById(IfscCodeMasterEntity ifscCodeData) {
		List<IfscCodeMasterEntity> list = null;
		try {
			String sqlQuery = "select *from IFSC_CODE_MASTER where id=:id";
			list = getSession().createSQLQuery(sqlQuery).addScalar("ifsc_code").addScalar("short_code").addScalar("nbin")
					.addScalar("bank_name").addScalar("bank_type").addScalar("member_type").addScalar("use_customized_ifsc").addScalar("is_live").setParameter("id", ifscCodeData.getId())
					.setResultTransformer(Transformers.aliasToBean(IfscCodeMasterEntity.class)).list();
			
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public List<DeliveryChannelsEntity> getAllDeliveryChannelDetailsById(DeliveryChannelsEntity deliveryChannelsReq) {
		List<DeliveryChannelsEntity> list = null;
		try {
			String sqlQuery = "select * from DELIVERY_CHANNELS where id=:id ";
			list = getSession().createSQLQuery(sqlQuery).addScalar("name").addScalar("nfs_channel_code")
					.addScalar("daily_limit_amount").addScalar("monthly_limit_amount").addScalar("mcc").addScalar("pos_entry_mode").addScalar("pos_condition_code").addScalar("check_mpin").addScalar("accnum_partial")
					.addScalar("otp_limit").addScalar("otp_validity").addScalar("check_remitter_mobile").addScalar("cust_authenticated").setParameter("id", deliveryChannelsReq.getId())
					.setResultTransformer(Transformers.aliasToBean(DeliveryChannelsEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public List<ImpsOtpLog> getOtpLogsDetails(ImpsOtpLog otpLogReq) {
		List<ImpsOtpLog> list = null;
		try {
			String sqlQuery = " select ol.id,ol.secured_otp, ol.otp_reference_number,ol.status,ol.creation_txn_id,ol.created_on,ol.expire_on,ol.used_txn_id,"
					+ "	ol.used_on,ol.customer_id,ol.channel_id,ic.id as impsCustId, ic.mbl_account_no,ic.mbl_mobile_no,ic.mbl_mascode,ic.mbl_tran_limit,"
					+ "	ic.mbl_reg_channel,ic.mbl_rcre_date,ic.mbl_rcre_user,ic.mbl_modified_date,ic.mbl_bene_name,ic.mbl_solid,"
					+ "	ic.mbl_req_stan,ic.mbl_pingen_date,ic.mbl_wrongpin_count,ic.mbl_cust_type,ic.mbl_m_pin,ic.bdk,ic.mbl_aadhar_number,ic.cust_id,"
					+ "	ic.user_id,ic.joint_acc,ic.opmode,ic.account_type_code from otp_log ol left join imps_customer_details ic on ol.customer_id=ic.id "
					+ "    where ic.mbl_mobile_no=:mobileNo order by ol.id desc  ";
			list = getSession().createSQLQuery(sqlQuery).addScalar("creation_txn_id").addScalar("created_on")
					.addScalar("used_on").addScalar("used_txn_id").addScalar("status").addScalar("mbl_account_no")
					.addScalar("mbl_bene_name").addScalar("mbl_reg_channel").addScalar("secured_otp")
					.addScalar("otp_reference_number").setParameter("mobileNo", otpLogReq.getMbl_mobile_no())
					.setResultTransformer(Transformers.aliasToBean(ImpsOtpLog.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public List<ImpsTransactionFeeSetupBean> getTransFeeSetupByApplyFeeAndTransTypeById(
			ImpsTransactionFeeSetupBean transactionFeeSetupReq) {
		List<ImpsTransactionFeeSetupBean> list = null;
		try {
			String sqlQuery = "select *from transaction_fee_setup where id=:id ";
			list = getSession().createSQLQuery(sqlQuery).setParameter("id", transactionFeeSetupReq.getId())
					.setResultTransformer(Transformers.aliasToBean(ImpsTransactionFeeSetupBean.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public boolean updateTransFeeSetup(ImpsTransactionFeeSetUpEntity transactionFeeSetupReq) {
		Session session = impsSessionFactory.getCurrentSession();
		try {
			session.update(transactionFeeSetupReq);

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
		return true;
	}

	@Override
	public boolean insertImpsMasterDetails(ImpsMasterEntity impsMasterData) {
		Session session = impsSessionFactory.getCurrentSession();
		try {
			impsMasterData.setCreatedon(new Date());
			session.save(impsMasterData);

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
		return true;
	}

	@Override
	public boolean updateImpsMasterDetails(ImpsMasterEntity impsMasterData) {
		Session session = impsSessionFactory.getCurrentSession();
		try {
			impsMasterData.setCreatedon(new Date());
			session.update(impsMasterData);

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
		return true;
	}

	@Override
	public List<ImpsMasterEntity> getImpsMasterDetails() {
		List<ImpsMasterEntity> list = null;
		try {
			String sqlQuery = "select *from IMPS_MASTER  ";
			list = getSession().createSQLQuery(sqlQuery).addScalar("bank").addScalar("ifsc").addScalar("branch")
					.addScalar("center").addScalar("district").addScalar("city").addScalar("state").addScalar("address")
					.addScalar("micr").addScalar("contact").addScalar("imps").addScalar("neft").addScalar("rtgs")
					.addScalar("upi").addScalar("createdon")
					.setResultTransformer(Transformers.aliasToBean(ImpsMasterEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public List<ImpsMasterEntity> getImpsMasterDetailsById(ImpsMasterEntity impsMasterData) {
		List<ImpsMasterEntity> list = null;
		try {
			String sqlQuery = "select *from IMPS_MASTER where ifsc=:ifsc ";
			list = getSession().createSQLQuery(sqlQuery).addScalar("bank").addScalar("ifsc").addScalar("branch")
					.addScalar("center").addScalar("district").addScalar("city").addScalar("state").addScalar("address")
					.addScalar("micr").addScalar("contact").addScalar("imps").addScalar("neft").addScalar("rtgs")
					.addScalar("upi").addScalar("createdon").setParameter("ifsc", impsMasterData.getId())
					.setResultTransformer(Transformers.aliasToBean(ImpsMasterEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

}
