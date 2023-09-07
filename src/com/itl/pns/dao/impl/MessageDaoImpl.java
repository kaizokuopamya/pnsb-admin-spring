package com.itl.pns.dao.impl;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.itl.pns.bean.DateBean;
import com.itl.pns.bean.LanguageJsonBean;
import com.itl.pns.bean.TicketBean;
import com.itl.pns.dao.MessageDao;

@Repository("IMessageDetailsDAO")
@Transactional
public class MessageDaoImpl implements MessageDao {

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;
	private static final Logger logger = LogManager.getLogger(MessageDaoImpl.class);

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public List getCountByProcessing() {
		StringBuilder sqlsb = new StringBuilder();
		sqlsb.append(
				"select * from (SELECT   a.displayname,count(*) FROM TransactionLogs t inner join activitymaster a on a.id=t.activityid GROUP BY  a.displayname order by count(*) desc) where rownum<11");
		return getSession().createSQLQuery(sqlsb.toString()).list();

	}

	@Override
	public List getChannelCount(DateBean datebean) {
		StringBuilder sqlsb = new StringBuilder();

		sqlsb.append(
				" select  a.shortname, count(t.id) from appmaster a  inner join transactionlogs t on a.id = t.appid and "
						+ " date(t.createdon) BETWEEN  TO_DATE('"
				+ datebean.getFromdate() + "','yyyy-mm-dd'" + ")"
				+ "	 AND  TO_DATE('"
				+ datebean.getTodate() + "','yyyy-mm-dd'" + ")+1 and t.REQ_RES='REQ' group by a.shortname");

		return getSession().createSQLQuery(sqlsb.toString()).list();

	}

	@Override
	public List getCountByDate(String type) {
		StringBuilder sqlsb = new StringBuilder();

		if (type.equalsIgnoreCase("retail")) {

			sqlsb.append("Select FT_NFT,dt,  Case when FT_NFT = 'NFT' THEN NFTCOUNT ELSE FTCOUNT End as cnt FROM");
			sqlsb.append("( Select to_char(t.createdon, 'YYYY/MM/DD')dt, ");
			sqlsb.append(" a.ft_nft , Sum(Case when FT_NFT = 'FT' then 1 else 0 end) FTcount,  ");
			sqlsb.append(" Sum(Case when FT_NFT = 'NFT' then 1 else 0 end) NFTcount from transactionlogs t ");
			sqlsb.append("left join ");
			sqlsb.append("activitymaster a on a.id=t.activityid  ");
			sqlsb.append("Group by to_char(t.createdon, 'YYYY/MM/DD'),  a.ft_nft )");
			sqlsb.append("UNION ALL ");
			sqlsb.append("SELECT CASE WHEN FTCNT= 0 THEN 'FT' ELSE 'NFT' END  ,DT, 0 FROM ");
			sqlsb.append(
					" ( SELECT DT , SUM(Case when FT_NFT = 'FT' THEN 1 ELSE 0 END)FTCNT ,SUM(Case when FT_NFT = 'NFT' THEN 1 ELSE 0 END)NFTCNT FROM ");
			sqlsb.append(" ( Select to_char(t.createdon, 'YYYY/MM/DD')dt,");
			sqlsb.append(" a.ft_nft , Sum(Case when FT_NFT = 'FT' then 1 else 0 end) FTcount,  ");
			sqlsb.append(" Sum(Case when FT_NFT = 'NFT' then 1 else 0 end) NFTcount from transactionlogs t ");
			sqlsb.append("left join ");
			sqlsb.append("activitymaster a on a.id=t.activityid  ");
			sqlsb.append("Group by to_char(t.createdon, 'YYYY/MM/DD'),  a.ft_nft )");
			sqlsb.append("GROUP BY DT ");
			sqlsb.append("Having Count(DT) = 1 )");
			sqlsb.append("ORDER BY DT ASC, FT_NFT DESC ");
		} else {
			sqlsb.append("Select FT_NFT,dt,  Case when FT_NFT = 'NFT' THEN NFTCOUNT ELSE FTCOUNT End as cnt FROM");
			sqlsb.append("( Select to_char(t.createdon, 'YYYY/MM/DD')dt, ");
			sqlsb.append(" a.ft_nft , Sum(Case when FT_NFT = 'FT' then 1 else 0 end) FTcount,  ");
			sqlsb.append(" Sum(Case when FT_NFT = 'NFT' then 1 else 0 end) NFTcount from transactionlogs t ");
			sqlsb.append("left join ");
			sqlsb.append(
					"activitymaster a on a.id=t.activityid  where t.customerid in (select cu.custid from corporate_user cu) ");
			sqlsb.append("Group by to_char(t.createdon, 'YYYY/MM/DD'),  a.ft_nft )");
			sqlsb.append("UNION ALL ");
			sqlsb.append("SELECT CASE WHEN FTCNT= 0 THEN 'FT' ELSE 'NFT' END  ,DT, 0 FROM ");
			sqlsb.append(
					" ( SELECT DT , SUM(Case when FT_NFT = 'FT' THEN 1 ELSE 0 END)FTCNT ,SUM(Case when FT_NFT = 'NFT' THEN 1 ELSE 0 END)NFTCNT FROM ");
			sqlsb.append(" ( Select to_char(t.createdon, 'YYYY/MM/DD')dt,");
			sqlsb.append(" a.ft_nft , Sum(Case when FT_NFT = 'FT' then 1 else 0 end) FTcount,  ");
			sqlsb.append(" Sum(Case when FT_NFT = 'NFT' then 1 else 0 end) NFTcount from transactionlogs t ");
			sqlsb.append("left join ");
			sqlsb.append(
					"activitymaster a on a.id=t.activityid   where t.customerid in (select cu.custid from corporate_user cu) ");
			sqlsb.append("Group by to_char(t.createdon, 'YYYY/MM/DD'),  a.ft_nft )");
			sqlsb.append("GROUP BY DT ");
			sqlsb.append("Having Count(DT) = 1 )");
			sqlsb.append("ORDER BY DT ASC, FT_NFT DESC ");
		}

		return getSession().createSQLQuery(sqlsb.toString()).list();

	}

	@Override
	public List getCountByDate(DateBean datebean) {

		String sqlsb = " select a.FT_NFT,a.DT, CASE WHEN a.FT_NFT = 'NFT' THEN NFTCOUNT ELSE FTCOUNT END AS CNT FROM ( SELECT DATE_FORMAT(T.CREATEDON, '%Y-%m-%d')DT, "
				+ "A.FT_NFT , SUM(CASE WHEN FT_NFT = 'FT' THEN 1 ELSE 0 END) FTCOUNT,   SUM(CASE WHEN FT_NFT = 'NFT' THEN 1 ELSE 0 END) NFTCOUNT FROM TRANSACTIONLOGS T"
				+ "	 LEFT JOIN ACTIVITYMASTER A ON A.ID=T.ACTIVITYID " + "	 WHERE date(T.CREATEDON) BETWEEN  ('"
				+ datebean.getFromdate() + "' ) AND ('" + datebean.getTodate() + "') and T.REQ_RES='REQ' "
				+ " GROUP BY DATE_FORMAT(T.CREATEDON, '%Y-%m-%d'),  A.FT_NFT ) as a  " + " UNION ALL "
				+ " SELECT CASE WHEN b.FTCNT= 0 THEN 'FT' ELSE 'NFT' END  ,b.DT, 0 "
				+ " FROM ( SELECT c.DT , SUM(CASE WHEN c.FT_NFT = 'FT' THEN 1 ELSE 0 END)FTCNT ,SUM(CASE WHEN c.FT_NFT = 'NFT' THEN 1 ELSE 0 END)NFTCNT"
				+ "	 FROM ( SELECT DATE_FORMAT(T.CREATEDON, '%Y-%m-%d')DT, A.FT_NFT , SUM(CASE WHEN FT_NFT = 'FT' THEN 1 ELSE 0 END) FTCOUNT,  "
				+ " SUM(CASE WHEN FT_NFT = 'NFT' THEN 1 ELSE 0 END) NFTCOUNT FROM TRANSACTIONLOGS T "
				+ " LEFT JOIN ACTIVITYMASTER A ON A.ID=T.ACTIVITYID  WHERE date(T.CREATEDON) BETWEEN  ('"
				+ datebean.getFromdate() + "') AND ('" + datebean.getTodate() + "') and T.REQ_RES='REQ' "
				+ "  GROUP BY DATE_FORMAT(T.CREATEDON, '%Y-%m-%d %T'),  A.FT_NFT ) as c "
				+ "	 GROUP BY DT HAVING COUNT(DT) = 1 ) as b ORDER BY DT DESC, FT_NFT DESC";

		return getSession().createSQLQuery(sqlsb.toString()).list();

	}

	@Override
	public List getCustomerCount(DateBean datebean) {

		StringBuilder sqlsb = new StringBuilder();

		sqlsb.append(
				"select DATE_FORMAT(createdon, '%Y-%m-%d') as createdon,sum(case when cif ='Pending' then 1 else 0 end) as PendingCustomer,sum(case when cif <>'Pending' then 1 else 0 end) as ApprovedCustomer,count(*) as TotalCustomer "
						+ "  from customers where " + " date(createdon) BETWEEN  ('" + datebean.getFromdate()
						+ "') AND ('" + datebean.getTodate() + "') group by DATE_FORMAT(createdon,'%Y-%m-%d')"
						+ " order by DATE_FORMAT(createdon,'%Y-%m-%d') DESC");

		return getSession().createSQLQuery(sqlsb.toString()).list();
	}

	@Override
	public List getCustomerlocation(String type) {
		StringBuilder sqlsb = new StringBuilder();
		if (type.equalsIgnoreCase("retail")) {
			sqlsb.append("select latitude, longitude,STARTTIME from customersessions where latitude is not null");
		} else {
			sqlsb.append(
					"select latitude, longitude,STARTTIME from customersessions  where customerid in (select cu.custid from corporate_user cu) and latitude is not null");
		}
		return getSession().createSQLQuery(sqlsb.toString()).list();

	}

	@Override
	public List getCustomerlocation(DateBean datebean) {
		StringBuilder sqlsb = new StringBuilder();

		sqlsb.append("SELECT LATITUDE, LONGITUDE,STARTTIME FROM CUSTOMERSESSIONS WHERE STARTTIME BETWEEN TO_DATE('"
				+ datebean.getFromdate() + "','yyyy-mm-dd'" + ") AND TO_DATE('" + datebean.getTodate()
				+ "','yyyy-mm-dd'" + ")+1 AND LATITUDE IS NOT NULL");

		return getSession().createSQLQuery(sqlsb.toString()).list();

	}

	@Override
	public List<TicketBean> getStatusById(int id) {
		String sqlQuery = "";
		if (id == 5) {

			sqlQuery = "select id,shortname from  statusmaster where id in(0,21,22) order by shortname  ";
		} else {
			sqlQuery = "select id,shortname from  statusmaster where id not in(0,21,22) order by shortname  ";
		}
		List<TicketBean> list = getSession().createSQLQuery(sqlQuery)
				.setResultTransformer(Transformers.aliasToBean(TicketBean.class)).list();
		return list;
	}

	public List<TicketBean> getStatus() {

		String sqlQuery = "select id,shortname from  statusmaster  where statusid=3 order by shortname  ";

		List<TicketBean> list = getSession().createSQLQuery(sqlQuery)
				.setResultTransformer(Transformers.aliasToBean(TicketBean.class)).list();
		return list;
	}

	@Override
	public List<LanguageJsonBean> getLanguageJson() {

		String sqlQuery = "select t.ENGLISHTEXT ,t.LANGUAGECODEDESC,t.LANGUAGECODE,t.LANGUAGETEXT,t.ISACTIVE,t.CREATEDON,t.APPID ,a.shortname from languagejson t,appmaster a where t.appid=a.id order by t.ENGLISHTEXT ";

		List<LanguageJsonBean> list = getSession().createSQLQuery(sqlQuery)
				.setResultTransformer(Transformers.aliasToBean(LanguageJsonBean.class)).list();
		return list;
	}

	@Override
	public List<TicketBean> getShortname() {

		String sqlQuery = "select id,shortname from  appmaster where statusid=3 ";

		List<TicketBean> list = getSession().createSQLQuery(sqlQuery)
				.setResultTransformer(Transformers.aliasToBean(TicketBean.class)).list();
		return list;
	}

	@Override
	public List getTransactionlogByStatus(String type) {
		StringBuilder sqlsb = new StringBuilder();

		if (type.equalsIgnoreCase("retail")) {
			sqlsb.append(
					"select count(*),s.shortname,t.req_res from transactionlogs t inner join statusmaster s on s.id=t.statusid group by s.shortname , t.req_res");
		} else {
			sqlsb.append("select count(*),s.shortname,t.req_res from transactionlogs t inner join statusmaster s"
					+ " on s.id=t.statusid  where t.customerid  in(select cu.custid from corporate_user cu)  group by s.shortname , t.req_res");
		}

		return getSession().createSQLQuery(sqlsb.toString()).list();
	}

	@Override
	public List getTransactionlogByStatusNew(DateBean datebean) {
		StringBuilder sqlsb = new StringBuilder();

		sqlsb.append(
				"select count(*),s.shortname,t.req_res  from transactionlogs t inner join statusmaster s on s.id=t.statusid and t.createdon between TO_DATE('"
						+ datebean.getFromdate() + "','%Y-%M-%D'" + ") AND TO_DATE('" + datebean.getTodate()
						+ "','%Y-%M-%D'" + ")+1 group by s.shortname,t.req_res ");
		return getSession().createSQLQuery(sqlsb.toString()).list();

	}

	@Override
	public List getTransactionByChannel(String type) {
		StringBuilder sqlsb = new StringBuilder();

		if (type.equalsIgnoreCase("retail")) {
			sqlsb.append(
					"select count(*),s.shortname from transactions t inner join appmaster s on s.id=t.appid group by s.shortname");
		} else {
			sqlsb.append(
					"select count(appid),a.shortname from transactions t inner join appmaster a on t.appid=a.id where t.sentcif in"
							+ "(select cu.custid from corporate_user cu) group by a.shortname");
		}

		return getSession().createSQLQuery(sqlsb.toString()).list();
	}

	@Override
	public List getTransactionByChannelNew(DateBean datebean) {
		StringBuilder sqlsb = new StringBuilder();

		sqlsb.append(
				"select count(*),s.shortname from transactions t inner join appmaster s on s.id=t.appid and t.createdon between TO_DATE('"
						+ datebean.getFromdate() + "','%Y-%M-%D'" + ") AND TO_DATE('" + datebean.getTodate()
						+ "','%Y-%M-%D'" + ")+1 group by s.shortname ");
		return getSession().createSQLQuery(sqlsb.toString()).list();
	}

	@Override
	public List getServiceRequestByStatus(String type) {
		StringBuilder sqlsb = new StringBuilder();

		if (type.equalsIgnoreCase("retail")) {
			sqlsb.append(
					"select count(*),s.shortname from tickets t  inner join statusmaster s on t.statusid =s.id inner join  customers c on t.customerid=c.id  group by s.shortname");
		} else {
			sqlsb.append(
					"select count(*),s.shortname from tickets t  inner join statusmaster s on t.statusid =s.id inner join "
							+ "customers c on t.customerid=c.id  where t.customerid in (select cu.custid from corporate_user cu) group by s.shortname");

		}

		return getSession().createSQLQuery(sqlsb.toString()).list();
	}

	@Override
	public List getServiceRequestByStatusNew(DateBean datebean) {
		StringBuilder sqlsb = new StringBuilder();

		sqlsb.append(
				"select count(*),s.shortname from tickets t  inner join statusmaster s on t.statusid =s.id inner join  customers c on t.customerid=c.id  and t.createdon between TO_DATE('"
						+ datebean.getFromdate() + "','%Y-%M-%D'" + ") AND TO_DATE('" + datebean.getTodate()
						+ "','%Y-%M-%D'" + ")+1  group by s.shortname");
		return getSession().createSQLQuery(sqlsb.toString()).list();
	}

}
