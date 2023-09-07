package com.itl.pns.dao.impl;

import java.util.List;

import javax.transaction.Transactional;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import com.itl.pns.bean.CorpUserBean;
import com.itl.pns.bean.DateBean;
import com.itl.pns.bean.FinancialCountMobBean;
import com.itl.pns.bean.OltasTinBean;
import com.itl.pns.bean.Payaggregatorbean;
import com.itl.pns.bean.ReconcileBillDeskBean;
import com.itl.pns.bean.SftpFileStatuses;
import com.itl.pns.bean.SolePropritorRegistrationBean;
import com.itl.pns.bean.Tin2;
import com.itl.pns.bean.TinMultilevelCallBackLogs;
import com.itl.pns.bean.TinServicesData;
import com.itl.pns.bean.TimestampDateBean;
import com.itl.pns.dao.FinancialCountMobDao;

@Repository
@Transactional
public class FinancialCountMobDaoImpl implements FinancialCountMobDao {

	private static final Logger logger = LogManager.getLogger(TransactionDaoImpl.class);

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public List<FinancialCountMobBean> getTotalFinancialTransaction(DateBean datebean) {

		String sqlQuery = "";
		List<FinancialCountMobBean> list = null;
		try {

			// below is query for UAT and prod environment
			sqlQuery = "select count(*) countDaily,a.SHORTNAME ,S.NAME STATUS"
					+ " from transactionlogs t inner join appmaster a on a.id=t.appid"
					+ " inner join statusmaster s on s.id=t.STATUSID" + " where trunc(t.createdon) between TO_DATE('"
					+ datebean.getFromdate() + "','yyyy-mm-dd'" + ")" + " AND  TO_DATE('" + datebean.getTodate()
					+ "','yyyy-mm-dd'"
					+ ") and  t.activityid in (Select id from activitymaster where activitycode='TRANSFERTRANSACTION')"
					+ " and req_res='RES' group by  a.SHORTNAME,S.NAME";

			// below is query for dev environment
			/*
			 * sqlQuery = "select count(*) countDaily,a.SHORTNAME ,S.NAME STATUS" +
			 * " from transactionlogs t inner join appmaster a on a.id=t.appid" +
			 * " inner join statusmaster s on s.id=t.STATUSID" +
			 * " where trunc(t.createdon) between TO_DATE('" + datebean.getFromdate() +
			 * "','yyyy-mm-dd'" + ")" + " AND  TO_DATE('" + datebean.getTodate() +
			 * "','yyyy-mm-dd'" + ") " + " and req_res='RES' group by  a.SHORTNAME,S.NAME";
			 */

			list = getSession().createSQLQuery(sqlQuery).addScalar("COUNTDAILY", StandardBasicTypes.DOUBLE)
					.addScalar("SHORTNAME", StandardBasicTypes.STRING).addScalar("STATUS", StandardBasicTypes.STRING)
					.setResultTransformer(Transformers.aliasToBean(FinancialCountMobBean.class)).list();
		} catch (Exception e) {
			logger.error("Exception Occured", e);
		}
		return list;

	}

	@Override
	public List<FinancialCountMobBean> getTotalNonfinancialTransaction(DateBean datebean) {

		String sqlQuery = "";
		List<FinancialCountMobBean> list = null;
		try {

			// below is query for UAT and prod environment
			sqlQuery = "select count(*) countDaily,a.SHORTNAME ,S.NAME STATUS from transactionlogs t inner join appmaster a on a.id=t.appid"
					+ " inner join statusmaster s on s.id=t.STATUSID where trunc(t.createdon) between TO_DATE('"
					+ datebean.getFromdate() + "','yyyy-mm-dd'" + ")" + " AND  TO_DATE('" + datebean.getTodate()
					+ "','yyyy-mm-dd'"
					+ ") and  t.activityid not in (Select id from activitymaster where activitycode='TRANSFERTRANSACTION')"
					+ " and req_res='RES' group by a.SHORTNAME,S.NAME ";

			// below is query for dev environment
			/*
			 * sqlQuery = "select count(*) countDaily,a.SHORTNAME ,S.NAME STATUS" +
			 * " from transactionlogs t inner join appmaster a on a.id=t.appid" +
			 * " inner join statusmaster s on s.id=t.STATUSID" +
			 * " where trunc(t.createdon) between TO_DATE('" + datebean.getFromdate() +
			 * "','yyyy-mm-dd'" + ")" + " AND  TO_DATE('" + datebean.getTodate() +
			 * "','yyyy-mm-dd'" + ")" + " and req_res='RES' group by  a.SHORTNAME,S.NAME ";
			 */
			list = getSession().createSQLQuery(sqlQuery).addScalar("COUNTDAILY", StandardBasicTypes.DOUBLE)
					.addScalar("SHORTNAME", StandardBasicTypes.STRING).addScalar("STATUS", StandardBasicTypes.STRING)
					.setResultTransformer(Transformers.aliasToBean(FinancialCountMobBean.class)).list();
		} catch (Exception e) {
			logger.error("Exception Occured", e);
		}
		return list;
	}

	@Override
	public List<FinancialCountMobBean> getTotalNonfinancialTransactionExclude(DateBean datebean) {
		String sqlQuery = "";
		List<FinancialCountMobBean> list = null;
		try {
			// below is query for UAT and prod environment
			sqlQuery = "select count(*) countDaily,a.SHORTNAME ,S.NAME STATUS"
					+ " from transactionlogs t inner join appmaster a on a.id=t.appid"
					+ " inner join statusmaster s on s.id=t.STATUSID" + " where trunc(t.createdon) between TO_DATE('"
					+ datebean.getFromdate() + "','yyyy-mm-dd'" + ")" + " AND  TO_DATE('" + datebean.getTodate()
					+ "','yyyy-mm-dd'"
					+ ") and  t.activityid not in (Select id from activitymaster where activitycode in ('TRANSFERTRANSACTION','OMNIDASHBOARD'))"
					+ " and req_res='RES' group by  a.SHORTNAME,S.NAME ";

			// below is query for dev environment
			/*
			 * sqlQuery = "select count(*) countDaily,a.SHORTNAME ,S.NAME STATUS" +
			 * " from transactionlogs t inner join appmaster a on a.id=t.appid" +
			 * " inner join statusmaster s on s.id=t.STATUSID" +
			 * " where trunc(t.createdon) between TO_DATE('" + datebean.getFromdate() +
			 * "','yyyy-mm-dd'" + ")" + " AND  TO_DATE('" + datebean.getTodate() +
			 * "','yyyy-mm-dd'" + ")" + " and req_res='RES' group by  a.SHORTNAME,S.NAME ";
			 */

			System.out.println("exclude done");

			list = getSession().createSQLQuery(sqlQuery).addScalar("COUNTDAILY", StandardBasicTypes.DOUBLE)
					.addScalar("SHORTNAME", StandardBasicTypes.STRING).addScalar("STATUS", StandardBasicTypes.STRING)
					.setResultTransformer(Transformers.aliasToBean(FinancialCountMobBean.class)).list();
		} catch (Exception e) {
			logger.error("Exception Occured", e);
		}
		return list;
	}

	@Override
	public List<FinancialCountMobBean> getTotalCountFromCustomers(FinancialCountMobBean financialCountMobBean) {
		String sqlQuery = "";
		List<FinancialCountMobBean> list = null;
		try {

			sqlQuery = "select DISTINCT(id) ID, COUNT(*) as TotalCount from customers where statusid=3 and appid=:ID and trunc(createdon) between TO_DATE('"
					+ financialCountMobBean.getFromdate() + "','yyyy-mm-dd') and TO_DATE('"
					+ financialCountMobBean.getTodate() + "','yyyy-mm-dd')" + " GROUP BY id";

			list = getSession().createSQLQuery(sqlQuery).addScalar("ID", StandardBasicTypes.INTEGER)
					.addScalar("TotalCount", StandardBasicTypes.DOUBLE)
					.setParameter("ID", financialCountMobBean.getID())
					.setResultTransformer(Transformers.aliasToBean(FinancialCountMobBean.class)).list();
		} catch (Exception e) {
			logger.error("Exception Occured", e);
		}
		return list;

	}

	@Override
	public List<SolePropritorRegistrationBean> getSolePropritorRegistrationDetails(DateBean datebean) {
		String sqlQuery = "";
		List<SolePropritorRegistrationBean> list = null;
		try {

			sqlQuery = "select trunc(b.createdon) CREATEDON, a.COMPANYCODE,a.COMPANYNAME, a.SHORTNAME ,a.CIF,b.EMAIL_ID,a.IS_CORPORATE from corp_company_master a inner join corp_users b on a.id = b.CORP_COMP_ID where trunc(b.createdon) between TO_DATE('"
					+ datebean.getFromdate() + "','yyyy-mm-dd'" + ")" + " AND TO_DATE('" + datebean.getTodate()
					+ "','yyyy-mm-dd'" + ")" + " and b.STATUSID=3";

			list = getSession().createSQLQuery(sqlQuery).addScalar("CREATEDON", StandardBasicTypes.DATE)
					.addScalar("COMPANYCODE", StandardBasicTypes.STRING)
					.addScalar("COMPANYNAME", StandardBasicTypes.STRING)
					.addScalar("SHORTNAME", StandardBasicTypes.STRING).addScalar("CIF", StandardBasicTypes.STRING)
					.addScalar("EMAIL_ID", StandardBasicTypes.STRING)
					.addScalar("IS_CORPORATE", StandardBasicTypes.CHARACTER)
					.setResultTransformer(Transformers.aliasToBean(SolePropritorRegistrationBean.class)).list();
		} catch (Exception e) {
			logger.error("Exception Occured", e);
		}
		return list;
	}

	@Override
	public List<CorpUserBean> getSolePropritorRegistrationCount(DateBean datebean) {
		String sqlQuery = "";
		List<CorpUserBean> list = null;
		try {
			sqlQuery = "select trunc(createdon) CREATEDON,  count(*) COUNT,IS_CORPORATE from corp_company_master where STATUSID=3 and trunc(createdon) between TO_DATE('"
					+ datebean.getFromdate() + "','yyyy-mm-dd'" + ")" + " AND  TO_DATE('" + datebean.getTodate()
					+ "','yyyy-mm-dd'" + ")"
					+ " group by trunc(CREATEDON),  IS_CORPORATE order by trunc(CREATEDON) desc";

			list = getSession().createSQLQuery(sqlQuery).addScalar("CREATEDON", StandardBasicTypes.DATE)

					.addScalar("COUNT", StandardBasicTypes.BIG_INTEGER)
					.addScalar("IS_CORPORATE", StandardBasicTypes.CHARACTER)
					.setResultTransformer(Transformers.aliasToBean(CorpUserBean.class)).list();
		} catch (Exception e) {
			logger.error("Exception Occured", e);
		}
		return list;
	}

	@Override
	public List<FinancialCountMobBean> getTransactionsById(FinancialCountMobBean financialCountMobBean) {
		String sqlQuery = "";
		List<FinancialCountMobBean> list = null;
		try {
			sqlQuery = "select sum(TXN_AMOUNT) total_txn_amount from transactions where appid=:ID and statusid=4 and trunc(createdon) between TO_DATE('"
					+ financialCountMobBean.getFromdate() + "','yyyy-mm-dd'" + ") and TO_DATE('"
					+ financialCountMobBean.getTodate() + "','yyyy-mm-dd'" + ")";
			list = getSession().createSQLQuery(sqlQuery).addScalar("total_txn_amount", StandardBasicTypes.BIG_DECIMAL)
					.setParameter("ID", financialCountMobBean.getID())
					.setResultTransformer(Transformers.aliasToBean(FinancialCountMobBean.class)).list();

		} catch (Exception e) {
			logger.info("Exception:", e);
		}

		return list;
	}

	@Override
	public List<FinancialCountMobBean> getFundTransferDetails(FinancialCountMobBean financialCountMobBean) {
		String sqlQuery = "";
		List<FinancialCountMobBean> list = null;
		try {

			sqlQuery = "select biller,count(*) TotalCount, sum(txn_amount) total_txn_amount from transactions where appid=:ID and trunc(CREATEDON) between"
					+ "	TO_DATE('" + financialCountMobBean.getFromdate() + "','yyyy-mm-dd'" + ") and TO_DATE('"
					+ financialCountMobBean.getTodate() + "','yyyy-mm-dd'" + ")"
					+ " GROUP BY biller having biller in( 'NEFT','self','within','RTGS','IMPS','UPI' ) ORDER BY SUM(txn_amount) DESC ";

			list = getSession().createSQLQuery(sqlQuery).addScalar("biller", StandardBasicTypes.STRING)
					.addScalar("TotalCount", StandardBasicTypes.DOUBLE)
					.addScalar("total_txn_amount", StandardBasicTypes.BIG_DECIMAL)
					.setParameter("ID", financialCountMobBean.getID())
					.setResultTransformer(Transformers.aliasToBean(FinancialCountMobBean.class)).list();
		} catch (Exception e) {
			logger.info("Exception:", e);
		}

		return list;

	}

	@Override
	public List<FinancialCountMobBean> getRibandRmob() {
		String sqlQuery = "";
		List<FinancialCountMobBean> list = null;
		try {
			sqlQuery = "select id, SHORTNAME from APPMASTER where id in(4,5)";
			list = getSession().createSQLQuery(sqlQuery).addScalar("ID", StandardBasicTypes.INTEGER)
					.addScalar("SHORTNAME", StandardBasicTypes.STRING)
					.setResultTransformer(Transformers.aliasToBean(FinancialCountMobBean.class)).list();
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return list;
	}

	@Override
	public List<FinancialCountMobBean> getTotalCustRegWithRibRmob(FinancialCountMobBean financialCountMobBean) {

		String sqlQuery = "";
		List<FinancialCountMobBean> list = null;
		try {

			sqlQuery = "select count(*) TotalCount from customers where id in (select customerid from transactionlogs where appid=:ID and REQ_RES='RES' and activityid = (select id from activitymaster where activitycode='AUTOLINKACCOUNTS')"
					+ " and trunc(CREATEDON) between TO_DATE('" + financialCountMobBean.getFromdate() + "','yyyy-mm-dd'"
					+ ") and TO_DATE('" + financialCountMobBean.getTodate() + "','yyyy-mm-dd'" + ")" + ")";

			list = getSession().createSQLQuery(sqlQuery).addScalar("TotalCount", StandardBasicTypes.DOUBLE)
					.setParameter("ID", financialCountMobBean.getID())
					.setResultTransformer(Transformers.aliasToBean(FinancialCountMobBean.class)).list();
		} catch (Exception e) {
			logger.info("Exception:", e);
		}

		return list;

	}

	@Override
	public List<FinancialCountMobBean> getTotalActiveCustWithRibRmob(FinancialCountMobBean financialCountMobBean) {

		String sqlQuery = "";
		List<FinancialCountMobBean> list = null;
		try {

			sqlQuery = "select customerid, COUNT(*) TotalCount from transactionlogs where appid=:ID and REQ_RES='RES' and trunc(CREATEDON) between TO_DATE('"
					+ financialCountMobBean.getFromdate() + "','yyyy-mm-dd'" + ") and TO_DATE('"
					+ financialCountMobBean.getTodate() + "','yyyy-mm-dd'" + ")"
					+ " and activityid =(select id from activitymaster where activitycode='TRANSFERTRANSACTION' ) group by customerid having count(*) >=1";

			list = getSession().createSQLQuery(sqlQuery).addScalar("customerid", StandardBasicTypes.BIG_INTEGER)
					.addScalar("TotalCount", StandardBasicTypes.DOUBLE)
					.setParameter("ID", financialCountMobBean.getID())
					.setResultTransformer(Transformers.aliasToBean(FinancialCountMobBean.class)).list();
		} catch (Exception e) {
			logger.info("Exception:", e);
		}

		return list;

	}

	@Override
	public List<Payaggregatorbean> getMerchantNameDropdown() {
		String sqlQuery = "";
		List<Payaggregatorbean> list = null;
		try {

			sqlQuery = "select distinct(MERCHANTNAME) MERCHANTNAME from paymentaggregators";
			list = getSession().createSQLQuery(sqlQuery).addScalar("MERCHANTNAME", StandardBasicTypes.STRING)
					.setResultTransformer(Transformers.aliasToBean(Payaggregatorbean.class)).list();
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return list;
	}

	@Override
	public List<Payaggregatorbean> getPaymentaggDataByMerchantname(Payaggregatorbean payaggregatorbean) {
		String sqlQuery = "";
		List<Payaggregatorbean> list = null;
		try {

			if (payaggregatorbean.getFROMDATE() != null && payaggregatorbean.getTODATE() != null) {
				sqlQuery = "select p.id,p.merchantname,p.apptype,p.merchantcode,p.pgrefno,p.txnamount,p.checksum,p.returnurl,p.banktxnid,p.fullurl,"
						+ " p.accountno,p.verifyurl,p.verifyrespondedon,p.statusid,p.remarks,p.consumer_code,p.payeeaccount,c.customername,c.mobile"
						+ " from paymentaggregators p inner join customers c on p.CUSTOMERID=c.id where p.merchantname=:MERCHANTNAME"
						+ " and trunc(p.CREATEDON) between TO_DATE('" + payaggregatorbean.getFROMDATE()
						+ "','yyyy-mm-dd'" + ") and TO_DATE('" + payaggregatorbean.getTODATE() + "','yyyy-mm-dd'" + ")";
			} else {

				sqlQuery = "select p.id,p.merchantname,p.apptype,p.merchantcode,p.pgrefno,p.txnamount,p.checksum,p.returnurl,p.banktxnid,p.fullurl,"
						+ " p.accountno,p.verifyurl,p.verifyrespondedon,p.statusid,p.remarks,p.consumer_code,p.payeeaccount,c.customername,c.mobile"
						+ " from paymentaggregators p inner join customers c on p.CUSTOMERID=c.id where p.merchantname=:MERCHANTNAME";
			}

			list = getSession().createSQLQuery(sqlQuery).addScalar("ID", StandardBasicTypes.BIG_INTEGER)
					.addScalar("MERCHANTNAME", StandardBasicTypes.STRING)
					.addScalar("APPTYPE", StandardBasicTypes.STRING)
					.addScalar("MERCHANTCODE", StandardBasicTypes.STRING)
					.addScalar("PGREFNO", StandardBasicTypes.STRING)
					.addScalar("TXNAMOUNT", StandardBasicTypes.BIG_DECIMAL)
					.addScalar("CHECKSUM", StandardBasicTypes.STRING).addScalar("RETURNURL", StandardBasicTypes.STRING)
					.addScalar("BANKTXNID", StandardBasicTypes.STRING).addScalar("FULLURL", StandardBasicTypes.STRING)
					.addScalar("ACCOUNTNO", StandardBasicTypes.STRING).addScalar("VERIFYURL", StandardBasicTypes.STRING)
					.addScalar("VERIFYRESPONDEDON", StandardBasicTypes.DATE)
					.addScalar("STATUSID", StandardBasicTypes.BIG_INTEGER)
					.addScalar("REMARKS", StandardBasicTypes.STRING)
					.addScalar("CONSUMER_CODE", StandardBasicTypes.STRING)
					.addScalar("PAYEEACCOUNT", StandardBasicTypes.STRING)
					.addScalar("CUSTOMERNAME", StandardBasicTypes.STRING).addScalar("MOBILE", StandardBasicTypes.STRING)
					.setParameter("MERCHANTNAME", payaggregatorbean.getMERCHANTNAME())
					.setResultTransformer(Transformers.aliasToBean(Payaggregatorbean.class)).list();
		} catch (Exception e) {
			logger.info("Exception:", e);
		}

		return list;
	}

	@Override
	public List<TimestampDateBean> getTableNameDropdown() {
		String sqlQuery = "";
		List<TimestampDateBean> list = null;
		try {

			sqlQuery = "SELECT table_name FROM user_tables ORDER BY table_name";
			list = getSession().createSQLQuery(sqlQuery).addScalar("table_name", StandardBasicTypes.STRING)
					.setResultTransformer(Transformers.aliasToBean(TimestampDateBean.class)).list();
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return list;
	}

	@Override
	public List<TinServicesData> getTinServiceData(TinServicesData TinServicesData) {
		String sqlQuery = "";
		List<TinServicesData> list = null;
		try {
			sqlQuery = "select p.id,p.merchantid,p.txnid,p.pan,p.crn,p.assessmentyear,p.totalamount,p.basictax,p.surcharge,p.educationalcess,p.interest,p.penalty,"
					+ " p.others,p.majorhead,p.minorhead,p.paymentmode,p.instrumenttype,p.requesttime,p.crndatetime,p.returnurl,p.banktxnid,p.validatecrnresponse,"
					+ " p.challanvalidtilldate,p.paymentdatetime,p.createdby,p.createdon,p.updatedby,p.updatedon,p.statusid,p.custid,p.fromaccount,"
					+ "p.cif,p.appid,p.sync_status_with_prakalp,p.tinerrorcode,p.lugg_file_sync_status from TINSERVICEDATA p, customers c where p.id = c.id ";
			/*
			 * + " trunc(p.CREATEDON) between TO_DATE('" + TinServiceData.getFROMDATE() +
			 * "','yyyy-mm-dd'" + ") and TO_DATE('" + TinServiceData.getTODATE() +
			 * "','yyyy-mm-dd'" + ")";
			 */

			list = getSession().createSQLQuery(sqlQuery).addScalar("ID").addScalar("MERCHANTID").addScalar("TXNID")
					.addScalar("PAN").addScalar("CRN").addScalar("ASSESSMENTYEAR").addScalar("TOTALAMOUNT")
					.addScalar("BASICTAX").addScalar("SURCHARGE").addScalar("EDUCATIONALCESS").addScalar("INTEREST")
					.addScalar("PENALTY").addScalar("OTHERS").addScalar("MAJORHEAD").addScalar("MINORHEAD")
					.addScalar("PAYMENTMODE").addScalar("INSTRUMENTTYPE").addScalar("REQUESTTIME")
					.addScalar("CRNDATETIME").addScalar("RETURNURL").addScalar("BANKTXNID")
					.addScalar("VALIDATECRNRESPONSE").addScalar("CHALLANVALIDTILLDATE").addScalar("PAYMENTDATETIME")
					.addScalar("CREATEDBY").addScalar("CREATEDON").addScalar("STATUSID").addScalar("UPDATEDBY")
					.addScalar("UPDATEDON").addScalar("CUSTID").addScalar("FROMACCOUNT").addScalar("CIF")
					.addScalar("APPID").addScalar("TINERRORCODE").addScalar("LUGG_FILE_SYNC_STATUS")
					.addScalar("SYNC_STATUS_WITH_PRAKALP")
					.setResultTransformer(Transformers.aliasToBean(TinServicesData.class)).list();
		} catch (Exception e) {
			logger.info("Exception:", e);
		}

		return list;
	}

	@Override
	public List<Tin2> getTin2ServiceData(Tin2 tin2) {
		String sqlQuery = "";
		List<Tin2> list = null;
		try {

			sqlQuery = "select p.id,p.merchantid,p.txnid,p.pan,p.crn,p.ay,p.total_amt,p.basic_tax,p.sur_charge,p.edu_cess,p.interest,"
					+ "p.penalty,p.others,p.major_head,p.minor_head,p.payment_mode,p.instrument_ty,p.request_tim,p.crn_date_time,"
					+ "p.returnurl,p.chln_valid_till_dt,p.verifyrespondedon,p.createdby,p.createdon,p.statusid from TIN2 p, customers c where p.merchantid = c.id "
					+ "and trunc(p.CREATEDON) between TO_DATE('" + tin2.getFROMDATE() + "' ,'yyyy-mm-dd') and TO_DATE('"
					+ tin2.getTODATE() + "','yyyy-mm-dd')";

			list = getSession().createSQLQuery(sqlQuery).addScalar("ID").addScalar("MERCHANTID").addScalar("TXNID")
					.addScalar("PAN").addScalar("CRN").addScalar("AY").addScalar("TOTAL_AMT").addScalar("BASIC_TAX")
					.addScalar("SUR_CHARGE").addScalar("EDU_CESS").addScalar("INTEREST").addScalar("PENALTY")
					.addScalar("OTHERS").addScalar("MAJOR_HEAD").addScalar("MINOR_HEAD").addScalar("PAYMENT_MODE")
					.addScalar("INSTRUMENT_TY").addScalar("REQUEST_TIM").addScalar("CRN_DATE_TIME")
					.addScalar("RETURNURL").addScalar("CHLN_VALID_TILL_DT").addScalar("VERIFYRESPONDEDON")
					.addScalar("CREATEDBY").addScalar("CREATEDON").addScalar("STATUSID")
					.setResultTransformer(Transformers.aliasToBean(Tin2.class)).list();
		} catch (Exception e) {
			logger.info("Exception:", e);
		}

		return list;
	}

	@Override
	public List<TinMultilevelCallBackLogs> gettinMultilevelCallLogs(TinMultilevelCallBackLogs tinCallLogs) {
		String sqlQuery = "";
		List<TinMultilevelCallBackLogs> list = null;
		try {

			sqlQuery = "select p.id,p.txnid,p.crn,p.banktxnid,p.request,p.response,p.updatedon,p.createdon,p.sync_status"
					+ " from TIN_MULTILVL_CALLBACK_LOGS p";
			/*
			 * + " trunc(p.CREATEDON) between TO_DATE('" + tinCallLogs.getFROMDATE() +
			 * "','yyyy-mm-dd'" + ") and TO_DATE('" + tinCallLogs.getTODATE() +
			 * "','yyyy-mm-dd'" + ")";
			 */
			list = getSession().createSQLQuery(sqlQuery).addScalar("ID").addScalar("TXNID").addScalar("CRN")
					.addScalar("BANKTXNID").addScalar("REQUEST").addScalar("RESPONSE").addScalar("UPDATEDON")
					.addScalar("CREATEDON").addScalar("SYNC_STATUS")
					.setResultTransformer(Transformers.aliasToBean(TinMultilevelCallBackLogs.class)).list();
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return list;
	}

	@Override
	public List<OltasTinBean> getOltasTinServiceData(OltasTinBean oltasTin) {
		String sqlQuery = "";
		List<OltasTinBean> list = null;
		try {

			sqlQuery = "select p.id,p.custid,p.fromaccount,p.banknameurl,p.majorhead,p.radioindex,p.bankurl,p.custname,p.transactiondate,"
					+ "p.add_state,p.bankname_c,p.add_line1,p.add_line2,p.add_line3,p.add_line4,p.add_line5,p.zaocode,p.valid,p.financialyear,"
					+ "p.pan,p.challanno,p.minorhead,p.add_pin,p.minorindex,p.r2,p.flag_var,p.tran_id,p.rrn,p.record_used,p.createdby,p.createdon,p.statusid,"
					+ "p.basictax,p.educess,p.surcharge,p.paymentcode1,p.paymentcode2,p.paymentcode3,p.interest,p.penalty,p.other,p.fee234e,p.fee26qb,p.totalamt,p.scrollno,"
					+ "p.paymentmode,p.chqcreditdate,p.brscrolldate,p.nodalscrollno,p.nodalscrolldt,p.challantype,p.formno,p.cbflag,p.cin,p.appid,p.naturepayment,p.cif"
					+ " from OLTAS_TIN p, customers c where p.custid = c.id ";			

			list = getSession().createSQLQuery(sqlQuery).addScalar("ID").addScalar("CUSTID").addScalar("FROMACCOUNT")
					.addScalar("BANKNAMEURL").addScalar("MAJORHEAD").addScalar("RADIOINDEX").addScalar("BANKURL")
					.addScalar("CUSTNAME").addScalar("TRANSACTIONDATE").addScalar("ADD_STATE").addScalar("BANKNAME_C")
					.addScalar("ADD_LINE1").addScalar("ADD_LINE2").addScalar("ADD_LINE3").addScalar("ADD_LINE4")
					.addScalar("ADD_LINE5").addScalar("ZAOCODE").addScalar("VALID").addScalar("FINANCIALYEAR")
					.addScalar("PAN").addScalar("CHALLANNO").addScalar("MINORHEAD").addScalar("ADD_PIN")
					.addScalar("MINORINDEX").addScalar("R2").addScalar("FLAG_VAR").addScalar("TRAN_ID").addScalar("RRN")
					.addScalar("RECORD_USED").addScalar("CREATEDBY").addScalar("CREATEDON").addScalar("STATUSID")
					.addScalar("BASICTAX").addScalar("EDUCESS").addScalar("SURCHARGE").addScalar("PAYMENTCODE1")
					.addScalar("PAYMENTCODE2").addScalar("PAYMENTCODE3").addScalar("INTEREST").addScalar("PENALTY")
					.addScalar("OTHER").addScalar("FEE234E").addScalar("FEE26QB").addScalar("TOTALAMT")
					.addScalar("SCROLLNO").addScalar("PAYMENTMODE")
					.addScalar("CHQCREDITDATE", StandardBasicTypes.STRING).addScalar("BRSCROLLDATE")
					.addScalar("NODALSCROLLNO").addScalar("NODALSCROLLDT").addScalar("CHALLANTYPE").addScalar("FORMNO")
					.addScalar("CBFLAG").addScalar("CIN").addScalar("APPID").addScalar("NATUREPAYMENT").addScalar("CIF")
					.setResultTransformer(Transformers.aliasToBean(OltasTinBean.class)).list();
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return list;
	}

	@Override
	public List<ReconcileBillDeskBean> getReconcileBillDeskServiceData(ReconcileBillDeskBean billDesk) {
		List<ReconcileBillDeskBean> list = null;
		try {

			String sqlQuery = "select p.id,p.customer_id,p.merchant_code,p.merchant_ref_no,p.bank_ref_no,p.tran_date,"
					+ "p.tran_amount,p.debit_acc_no,p.recon_status,p.refund_till_date,p.processing_status,p.jno,p.paid_status,p.narration,"
					+ "p.bill_type,p.mobile_no from RECONCILE_BILLDESK p, customers c where p.customer_id = c.id";
				/*	+ "trunc(p.TRAN_DATE) between TO_DATE('" + billDesk.getFROMDATE() + "','yyyy-mm-dd') and TO_DATE('"
					+ billDesk.getTODATE() + "','yyyy-mm-dd')";*/

			list = getSession().createSQLQuery(sqlQuery).addScalar("CUSTOMER_ID").addScalar("MERCHANT_CODE")
					.addScalar("MERCHANT_REF_NO").addScalar("BANK_REF_NO").addScalar("TRAN_DATE")
					.addScalar("TRAN_AMOUNT").addScalar("DEBIT_ACC_NO").addScalar("RECON_STATUS")
					.addScalar("REFUND_TILL_DATE").addScalar("PROCESSING_STATUS").addScalar("JNO")
					.addScalar("PAID_STATUS").addScalar("NARRATION").addScalar("BILL_TYPE").addScalar("MOBILE_NO")
					.setResultTransformer(Transformers.aliasToBean(ReconcileBillDeskBean.class)).list();
		} catch (Exception e) {
			System.out.println("Exception" + e);
		}

		return list;
	}

	@Override
	public List<SftpFileStatuses> getSftpFileStatus(SftpFileStatuses fileStatus) {
		List<SftpFileStatuses> list = null;
		try {

			String sqlQuery = "select p.id,p.filename,p.createdon from SFTP_FILE_STATUS p, customers c where p.id = c.id ";
				//	+ "and trunc(p.CREATEDON) between TO_DATE('" + fileStatus.getFROMDATE()
				//	+ "' ,'yyyy-mm-dd') and TO_DATE('" + fileStatus.getTODATE() + "','yyyy-mm-dd')";

			list = getSession().createSQLQuery(sqlQuery).addScalar("ID").addScalar("FILENAME").addScalar("CREATEDON")
					.setResultTransformer(Transformers.aliasToBean(SftpFileStatuses.class)).list();
		} catch (Exception e) {
			logger.info("Exception:", e);
		}

		return list;
	}
}
