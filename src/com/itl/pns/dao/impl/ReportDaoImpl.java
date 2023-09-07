package com.itl.pns.dao.impl;

import java.io.Reader;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.itl.pns.bean.MonthlyReportData;
import com.itl.pns.bean.RequestParamBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.dao.ReportDao;
import com.itl.pns.dao.TransactionDao;
import com.itl.pns.entity.ReportMsterEntity;
import com.itl.pns.entity.SubReportEntity;
import com.itl.pns.jdbc.MySqlJDBCConnection;
import com.itl.pns.service.ReportService;
import com.itl.pns.util.AdminWorkFlowReqUtility;
import com.itl.pns.util.EmailUtil;
import com.itl.pns.util.EncryptDeryptUtility;
import com.itl.pns.util.EncryptorDecryptor;

import oracle.jdbc.internal.OracleTypes;

@Repository
@Transactional
public class ReportDaoImpl implements ReportDao {

	static Logger LOGGER = Logger.getLogger(ReportDaoImpl.class);

	@Autowired
	EmailUtil util;

	@Autowired
	TransactionDao transDao;

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	@Autowired
	AdminWorkFlowReqUtility adminWorkFlowReqUtility;

	@Autowired
	ReportService reportService;

	protected Session getSession() {
		if (sessionFactory.getCurrentSession() != null)
			return sessionFactory.getCurrentSession();
		else
			return sessionFactory.openSession();
	}

	@Override
	public List<ReportMsterEntity> getReportDetailsById(int id) {
		String sqlQuery = "";
		List<ReportMsterEntity> list = null;
		try {
			sqlQuery = "select rm.id,rm.REPORT_NAME as reportName,rm.REPORT_DESCRIPTION as reportDescription,rm.statusId as statusId, "
					+ " rm.createdOn,rm.createdBy, rm.updatedBy,s.name as statusName from REPORT_MASTER rm  inner join statusMaster s on s.id = rm.statusId where rm.id=:id";
			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("reportName")
					.addScalar("reportDescription").addScalar("statusId").addScalar("createdOn").addScalar("createdBy")
					.addScalar("updatedBy").addScalar("statusName").setParameter("id", id)
					.setResultTransformer(Transformers.aliasToBean(ReportMsterEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return list;
	}

	@Override
	public List<ReportMsterEntity> getAllReportDetails() {
		String sqlQuery = "";
		List<ReportMsterEntity> list = null;
		try {
			sqlQuery = "select rm.id,rm.REPORT_NAME as reportName,rm.REPORT_DESCRIPTION as reportDescription,rm.statusId as statusId,um.userid as createdByName, "
					+ " rm.createdOn,rm.createdBy, rm.updatedBy,s.name as statusName from REPORT_MASTER rm   left join user_master um on um.id=rm.createdby "
					+ " inner join statusMaster s on s.id = rm.statusId order by rm.id desc";
			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("reportName")
					.addScalar("reportDescription").addScalar("statusId").addScalar("createdByName")
					.addScalar("createdOn").addScalar("createdBy").addScalar("updatedBy").addScalar("statusName")
					.setResultTransformer(Transformers.aliasToBean(ReportMsterEntity.class)).list();
			System.out.println(list);
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return list;
	}

	@Override
	public boolean addReportDetails(ReportMsterEntity reportMasterDetails) {
		Session session = sessionFactory.getCurrentSession();
		try {
			reportMasterDetails.setUpdatedBy(reportMasterDetails.getCreatedBy());
			reportMasterDetails.setCreatedOn(new Date());
			reportMasterDetails.setUpdatedOn(new Date());
			session.save(reportMasterDetails);

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
		return true;
	}

	@Override
	public boolean updateReportDetails(ReportMsterEntity reportMasterDetails) {
		Session session = sessionFactory.getCurrentSession();
		try {
			reportMasterDetails.setUpdatedBy(reportMasterDetails.getCreatedBy());
			reportMasterDetails.setUpdatedOn(new Date());
			session.update(reportMasterDetails);

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
		return true;
	}

	@Override
	public ResponseMessageBean isReportNameExist(ReportMsterEntity reportMasterDetails) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		try {
			String sqlNameExit = "SELECT count(*) FROM REPORT_MASTER WHERE Lower(REPORT_NAME) =:reportName  ";
			List isReportNameExit = getSession().createSQLQuery(sqlNameExit)
					.setParameter("reportName", reportMasterDetails.getReportName().toLowerCase()).list();

			if (!(isReportNameExit.get(0).toString().equalsIgnoreCase("0"))) {
				rmb.setResponseCode("451");
				rmb.setResponseMessage("Name Already Exist");
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
	public ResponseMessageBean updateReportDetailsExist(ReportMsterEntity reportMasterDetails) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		try {
			String sqlNameExit = "SELECT count(*) FROM REPORT_MASTER WHERE Lower(REPORT_NAME) =:reportName  AND id !=:id ";
			List isReportNameExit = getSession().createSQLQuery(sqlNameExit)
					.setParameter("id", reportMasterDetails.getId())
					.setParameter("reportName", reportMasterDetails.getReportName().toLowerCase()).list();

			if (!(isReportNameExit.get(0).toString().equalsIgnoreCase("0"))) {
				rmb.setResponseCode("451");
				rmb.setResponseMessage("Name Already Exist");
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
	public ResponseMessageBean getReportDynamicReports(ReportMsterEntity reportMasterDetails) {
		LOGGER.info("get dynamic  reports -> getReportDynamicReports()");
		ResponseMessageBean res = new ResponseMessageBean();
		CallableStatement callableStatement = null;
		Connection dbConnection = MySqlJDBCConnection.getMysqlDBConnection();

		try {

			String prcUserPassExpr = "{call SP_INFRA_GET_REPORTS(?,?,?,?)}";
			callableStatement = dbConnection.prepareCall(prcUserPassExpr);
			callableStatement.setInt(1, reportMasterDetails.getSubid().intValue());
			callableStatement.setString(2, reportMasterDetails.getDateBean().getFromdate());
			callableStatement.setString(3, reportMasterDetails.getDateBean().getTodate());
			callableStatement.registerOutParameter(4, OracleTypes.CURSOR);
			callableStatement.executeQuery();

			ResultSet rs1 = (ResultSet) callableStatement.getObject(4);

			List<Map<String, String>> listObj = new ArrayList<>();
			ResultSetMetaData rsmd = rs1.getMetaData();
			while (rs1.next()) {
				int numColumns = rsmd.getColumnCount();
				Map<String, String> mapObj = new HashMap<>();
				String column_value = "";
				for (int i = 1; i <= numColumns; i++) {
					String column_name = rsmd.getColumnName(i);
					if (!ObjectUtils.isEmpty(rs1.getObject(column_name))) {
						column_value = rs1.getObject(column_name).toString();
					}
					System.out.println("Hello-->" + column_name + "====" + column_value);
					if (column_value.contains("CLOB")) {
						Clob clob = rs1.getClob(column_name);

						String remarkString = EncryptDeryptUtility.clobStringConversion(clob);
						column_value = remarkString;

					}

					mapObj.put(column_name, column_value);
				}

				listObj.add(mapObj);

			}

			if (!ObjectUtils.isEmpty(listObj)) {
				res.setResponseCode("200");
				res.setResult(listObj);
				res.setResponseMessage("Success");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}

			/*
			 * String sqlQuery =
			 * "call SP_INFRA_GET_REPORTS('"+reportMasterDetails.getId()+"','"+
			 * reportMasterDetails.
			 * getDateBean().getFromdate()+"','"+reportMasterDetails.getDateBean().getTodate
			 * ()+"','"+Types.REF+"')"; List<String> list = null;
			 * System.out.println(sqlQuery);
			 * 
			 * list = getSession().createSQLQuery(sqlQuery)
			 * .setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE) .list(); if
			 * (!ObjectUtils.isEmpty(list)) { res.setResponseCode("200");
			 * res.setResult(list); res.setResponseMessage("Success"); } else {
			 * res.setResponseCode("202"); res.setResponseMessage("No Records Found"); }
			 */

			/*
			 * if(reportMasterDetails.getReportName().equalsIgnoreCase("Transaction Report")
			 * ){ List<TransactionBean> list =
			 * transDao.getTransactions(reportMasterDetails.getDateBean());
			 * reso.setResult(list); }
			 */

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return res;
	}

	@Override
	public List<SubReportEntity> getSubReportDetailsById(int id) {
		String sqlQuery = "";
		List<SubReportEntity> list = null;
		try {
			sqlQuery = "select rm.id,rm.subreportname as subreportname,rm.subreportdescription as subreportdescription,rm.reportid as reportid,rm.statusid as statusid,um.userid as createdByName, "
					+ " rm.createdon as createdon,rm.createdby as createdby, rm.updatedby as updatedby ,s.name as statusName , rmm.REPORT_NAME as reportName from SUBREPORT_DETAILS rm   left join user_master um on um.id=rm.createdby "
					+ " inner join statusMaster s on s.id = rm.statusId inner join REPORT_MASTER rmm on rmm.id=rm.reportid where rm.id=:id";
			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("subreportname")
					.addScalar("subreportdescription").addScalar("statusid").addScalar("createdByName")
					.addScalar("createdon").addScalar("reportid").addScalar("createdby").addScalar("updatedby")
					.addScalar("statusName").addScalar("reportName").setParameter("id", id)
					.setResultTransformer(Transformers.aliasToBean(SubReportEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return list;
	}

	@Override
	public List<SubReportEntity> getAllSubReportDetails() {
		String sqlQuery = "";
		List<SubReportEntity> list = null;
		try {
			sqlQuery = "select rm.id,rm.subreportname as subreportname,rm.subreportdescription as subreportdescription,rm.statusid as statusid,rm.reportid as reportid,um.userid as createdByName, "
					+ " rm.createdon as createdon,rm.createdby as createdby, rm.updatedby as updatedby ,s.name as statusName , rmm.REPORT_NAME as reportName from SUBREPORT_DETAILS rm   left join user_master um on um.id=rm.createdby "
					+ " inner join statusMaster s on s.id = rm.statusId inner join REPORT_MASTER rmm on rmm.id=rm.reportid order by rm.id desc";
			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("subreportname")
					.addScalar("subreportdescription").addScalar("statusid").addScalar("createdByName")
					.addScalar("createdon").addScalar("reportid").addScalar("createdby").addScalar("updatedby")
					.addScalar("statusName").addScalar("reportName")
					.setResultTransformer(Transformers.aliasToBean(SubReportEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return list;
	}

	@Override
	public List<SubReportEntity> getSubReportDetailsByReportId(int rptId) {
		String sqlQuery = "";
		List<SubReportEntity> list = null;
		try {
			sqlQuery = "select rm.id,rm.subreportname as subreportname,rm.subreportdescription as subreportdescription,rm.reportid as reportid,rm.statusid as statusid,um.userid as createdByName, "
					+ " rm.createdon as createdon,rm.createdby as createdby, rm.updatedby as updatedby ,s.name as statusName , rmm.REPORT_NAME as reportName from SUBREPORT_DETAILS rm   left join user_master um on um.id=rm.createdby "
					+ " inner join statusMaster s on s.id = rm.statusId inner join REPORT_MASTER rmm on rmm.id=rm.reportid where rm.reportid=:rpId";
			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("subreportname")
					.addScalar("subreportdescription").addScalar("statusid").addScalar("createdByName")
					.addScalar("createdon").addScalar("reportid").addScalar("createdby").addScalar("updatedby")
					.addScalar("statusName").addScalar("reportName").setParameter("rpId", rptId)
					.setResultTransformer(Transformers.aliasToBean(SubReportEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return list;
	}

	@Override
	public boolean addSubReportDetails(SubReportEntity reportMasterDetails) {
		Session session = sessionFactory.getCurrentSession();
		try {

			reportMasterDetails.setCreatedon(new Date());
			session.save(reportMasterDetails);
			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}

	@Override
	public boolean updateSubReportDetails(SubReportEntity reportMasterDetails) {
		Session session = sessionFactory.getCurrentSession();
		try {

			reportMasterDetails.setUpdatedon(new Date());
			session.update(reportMasterDetails);
			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}

	@Override
	public ResponseMessageBean isSubReportNameExist(SubReportEntity reportMasterDetails) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		try {
			String sqlNameExit = "SELECT count(*) FROM SUBREPORT_DETAILS WHERE Lower(subreportname) =:subRptName  ";
			List isReportNameExit = getSession().createSQLQuery(sqlNameExit)
					.setParameter("subRptName", reportMasterDetails.getSubreportname().toLowerCase()).list();

			if (!(isReportNameExit.get(0).toString().equalsIgnoreCase("0"))) {
				rmb.setResponseCode("451");
				rmb.setResponseMessage("Name Already Exist");
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
	public ResponseMessageBean updateSubReportNameExist(SubReportEntity reportMasterDetails) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		try {
			String sqlNameExit = "SELECT count(*) FROM SUBREPORT_DETAILS WHERE Lower(subreportname) =:subRptName  AND id !=:id ";
			List isReportNameExit = getSession().createSQLQuery(sqlNameExit)
					.setParameter("id", reportMasterDetails.getId())
					.setParameter("subRptName", reportMasterDetails.getSubreportname().toLowerCase()).list();

			if (!(isReportNameExit.get(0).toString().equalsIgnoreCase("0"))) {
				rmb.setResponseCode("451");
				rmb.setResponseMessage("Name Already Exist");
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
	public List<MonthlyReportData> getMonthlyReport(MonthlyReportData reportsData) {
		String sqlQuery = "";
		List<MonthlyReportData> list = null;
		try {
			sqlQuery = "select m.id,m.appId,m.numberOfRecords,m.service_name,m.createdOn,m.txnAmount,m.fromDate,m.toDate from MONTHLY_REPORT_DATA m "
					+ " WHERE to_char(m.fromDate, 'mm-yyyy')=:fromDate and to_char(m.toDate, 'mm-yyyy')=:toDate";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("appId").addScalar("numberOfRecords")
					.addScalar("service_Name").addScalar("createdOn").addScalar("txnAmount").addScalar("fromDate")
					.addScalar("toDate").setParameter("fromDate", reportsData.getDate1())
					.setParameter("toDate", reportsData.getDate1())
					.setResultTransformer(Transformers.aliasToBean(MonthlyReportData.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return list;
	}

}