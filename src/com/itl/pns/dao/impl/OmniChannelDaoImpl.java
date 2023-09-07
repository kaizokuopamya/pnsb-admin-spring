package com.itl.pns.dao.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.itl.pns.bean.OmniChannelBean;
import com.itl.pns.dao.OmniChannelDao;
import com.itl.pns.impsEntity.ImpsMasterEntity;

@Repository("OmniChannelDao")
@Transactional
public class OmniChannelDaoImpl implements OmniChannelDao{

	static Logger LOGGER = Logger.getLogger(OmniChannelDaoImpl.class);
	
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;
	
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public List<OmniChannelBean> getomniChannel(int roleid) {
		String sqlQuery="";
		if(roleid==8){
		
			
			/*
			 * sqlQuery=
			 * "SELECT T.ID, T.CONTENT,T.CUSTOMERID,T.ACCOUNTNO,T.REFNO,T.SERVICETYPE,T.CHANNELACTION,T.CREATEDON,T.UPDATEDON,"
			 * +
			 * "			T.STATUSID, S.NAME AS STATUSNAME ,C.CUSTOMERNAME AS CUSTNAME, C.MOBILE,am.FT_NFT   FROM OMNICHANNELREQUEST "
			 * +
			 * "	T INNER JOIN STATUSMASTER S  ON T.STATUSID=S.ID INNER JOIN CUSTOMERS C ON T.CUSTOMERID=C.ID  "
			 * + " inner join activitymaster am on am.ACTIVITYCODE=T.SERVICETYPE WHERE " +
			 * "T.STATUSID IN (15) ORDER BY T.CREATEDON DESC ";
			 */
			
			  sqlQuery="select t.id,CAST(t.content AS VARCHAR(1000)) AS content , t.customerid,t.accountno,t.refno,t.servicetype,t.channelaction,t.createdon,t.updatedon,"
			  +
			  "t.statusid, s.name as statusname ,c.customername as custname,c.mobile,am.ft_nft "
			  +"\"ft_nft\""+ " " +"from omnichannelrequest " +
			  "t  inner join activitymaster am on am.ACTIVITYCODE=t.servicetype inner join statusmaster s  on t.statusid=s.id inner join customers c on t.customerid=c.id where "
			  + "t.statusid in (15) order by t.createdon desc ";
			 

		}
		
		
		else{
		
		
			/*sqlQuery=	"SELECT T.ID , T.CONTENT,T.CUSTOMERID,T.ACCOUNTNO,T.REFNO,T.SERVICETYPE,T.CHANNELACTION,T.CREATEDON,T.UPDATEDON,"
					+ "			T.STATUSID, S.NAME AS STATUSNAME ,C.CUSTOMERNAME AS CUSTNAME, C.MOBILE,am.FT_NFT as ft_nft   FROM OMNICHANNELREQUEST "
					+ "	T INNER JOIN STATUSMASTER S  ON T.STATUSID=S.ID INNER JOIN CUSTOMERS C ON T.CUSTOMERID=C.ID  "
					+ " inner join activitymaster am on am.ACTIVITYCODE=T.SERVICETYPE WHERE "
					+ "T.STATUSID IN (11,12,1,4) ORDER BY T.CREATEDON DESC ";
			
			*/
			sqlQuery="select t.id,CAST(t.content AS VARCHAR(1000)) AS content , t.customerid,t.accountno,t.refno,t.servicetype,t.channelaction,t.createdon,t.updatedon,"
			 		+ "t.statusid, s.name as statusname ,c.customername as custname,c.mobile,am.ft_nft "+"\"ft_nft\""+ " " +"from omnichannelrequest "
			 		+ "t  inner join activitymaster am on am.ACTIVITYCODE=t.servicetype inner join statusmaster s  on t.statusid=s.id inner join customers c on t.customerid=c.id where "
			 		+ "t.statusid in (11,12,1,4) order by t.createdon desc ";
		}
		
	
	List<OmniChannelBean> list = getSession().createSQLQuery(sqlQuery)
			.setResultTransformer(Transformers.aliasToBean(OmniChannelBean.class)).list();
	return list;
	
	}

	@Override
	public List<OmniChannelBean> getOmniChannelRequestReport(String status) {
	
		String sqlQuery="SELECT T.*, S.NAME AS STATUSNAME ,C.CUSTOMERNAME AS CUSTNAME  FROM OMNICHANNELREQUEST T INNER JOIN STATUSMASTER S  ON T.STATUSID=S.ID"
				+ " INNER JOIN CUSTOMERS C ON T.CUSTOMERID=C.ID   WHERE T.STATUSID=:STATUS ORDER BY T.CREATEDON DESC ";
		
		List<OmniChannelBean> list = getSession().createSQLQuery(sqlQuery).setParameter("status", status)
				.setResultTransformer(Transformers.aliasToBean(OmniChannelBean.class)).list();
		return list;
	}


	@Override
	public List<OmniChannelBean> getOmniChannelRequestById(int id) {
		
	/*	String sqlQuery="SELECT T.CUSTOMERID,T.ACCOUNTNO,T.REFNO,T.SERVICETYPE,T.CHANNELACTION,T.CREATEDON,T.UPDATEDON,T.CONTENT,T.STATUSID, "
				+ "S.NAME AS STATUSNAME ,C.CUSTOMERNAME AS CUSTNAME ,C.MOBILE ,am.FT_NFT as ft_nft FROM OMNICHANNELREQUEST T INNER JOIN STATUSMASTER S  ON T.STATUSID=S.ID "
				+ " inner join activitymaster am on am.ACTIVITYCODE=T.SERVICETYPE INNER JOIN CUSTOMERS C ON T.CUSTOMERID=C.ID AND T.ID=:id";
		*/
		
		String sqlQuery="select t.id,CAST(t.content AS VARCHAR(1000)) AS content , t.customerid,t.accountno,t.refno,t.servicetype,t.channelaction,t.createdon,t.updatedon,"
		 		+ "t.statusid, s.name as statusname ,c.customername as custname,c.mobile,am.ft_nft "+"\"ft_nft\""+ " " +"from omnichannelrequest "
		 		+ "t  inner join activitymaster am on am.ACTIVITYCODE=t.servicetype inner join statusmaster s  on t.statusid=s.id inner join customers c on t.customerid=c.id "
		 		+ " and t.ID=:id";

		List<OmniChannelBean> list = getSession().createSQLQuery(sqlQuery).setParameter("id", id)
				.setResultTransformer(Transformers.aliasToBean(OmniChannelBean.class)).list();
		return list;
	}

	@Override
	public List<OmniChannelBean> getMappedMobile(String mobile) {
		String sqlQuery=" select ivrmobileno from mobilemapping where mobileno=:mobile";
		List<OmniChannelBean> list = getSession().createSQLQuery(sqlQuery).setParameter("mobile", mobile)
				.setResultTransformer(Transformers.aliasToBean(OmniChannelBean.class)).list();
		return list;
	}

	@Override
	public List<OmniChannelBean> findMappedMobileAccount(String mobile) {
		String sqlQuery=" select c.mappedsavingaccount from customerdetailsmapping c where c.mobienumber=:mobile";
		List<OmniChannelBean> list = getSession().createSQLQuery(sqlQuery).setParameter("mobile", mobile).setResultTransformer(Transformers.aliasToBean(OmniChannelBean.class)).list();
		return list;
	}

	@Override
	public List<OmniChannelBean> findIVR(String mobile) {
		String sqlQuery=" select c.ivrmobileno from mobilemapping c where  c.mobileno=:mobile";
		List<OmniChannelBean> list = getSession().createSQLQuery(sqlQuery).setParameter("mobile", mobile).setResultTransformer(Transformers.aliasToBean(OmniChannelBean.class)).list();
		return list;
	}

	
	
	@Override
	public boolean insertImpsMasterDetails(ImpsMasterEntity impsMasterData) {
		Session session = sessionFactory.getCurrentSession();
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
		Session session = sessionFactory.getCurrentSession();
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
			String sqlQuery = "select im.id as id,im.bank as bank,im.ifsc as ifsc,im.branch as branch,im.center as center,im.district as district,"
					+ " im.state as state , im.address as address,im.contact as contact, im.imps as imps, im.rtgs as rtgs,im.city as city,"
					+ " im.neft as neft, im.micr as micr,im.upi as upi,im.createdby as createdby, im.createdon as createdon , im.statusId as statusId, "
					+ "im.updatedby as updatedby, im.updatedon as updatedon from IMPS_MASTER im ";
			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("bank").addScalar("ifsc")
					.addScalar("branch").addScalar("center").addScalar("district").addScalar("state")
					.addScalar("address").addScalar("contact").addScalar("imps").addScalar("rtgs")
					.addScalar("city").addScalar("neft").addScalar("micr").addScalar("upi").addScalar("createdby")
					.addScalar("createdon").addScalar("statusId").addScalar("updatedby").addScalar("updatedon")
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
			String sqlQuery = "select im.id as id,im.bank as bank,im.ifsc as ifsc,im.branch as branch,im.center as center,im.district as district,"
					+ " im.state as state , im.address as address,im.contact as contact, im.imps as imps, im.rtgs as rtgs,im.city as city,"
					+ " im.neft as neft, im.micr as micr,im.upi as upi,im.createdby as createdby, im.createdon as createdon , im.statusId as statusId, "
					+ "im.updatedby as updatedby, im.updatedon as updatedon from IMPS_MASTER im where im.id=:id ";
			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("bank").addScalar("ifsc")
					.addScalar("branch").addScalar("center").addScalar("district").addScalar("state")
					.addScalar("address").addScalar("contact").addScalar("imps").addScalar("rtgs")
					.addScalar("city").addScalar("neft").addScalar("micr").addScalar("upi").addScalar("createdby")
					.addScalar("createdon").addScalar("statusId").addScalar("updatedby").addScalar("updatedon").setParameter("id", impsMasterData.getId())
					.setResultTransformer(Transformers.aliasToBean(ImpsMasterEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public List<ImpsMasterEntity> getImpsMasterState() {
		List<ImpsMasterEntity> list = null;
		try {
			String sqlQuery = " select distinct(state) from IMPS_MASTER ";
			list = getSession().createSQLQuery(sqlQuery).addScalar("state")
					.setResultTransformer(Transformers.aliasToBean(ImpsMasterEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}
	 

	@Override
	public List<ImpsMasterEntity> getImpsMasterDistrictByState(ImpsMasterEntity impsMasterData) {
		List<ImpsMasterEntity> list = null;
		try {
			String sqlQuery = " select distinct(district) from IMPS_MASTER where state=:state";
			list = getSession().createSQLQuery(sqlQuery).addScalar("district").setParameter("state", impsMasterData.getState())
					.setResultTransformer(Transformers.aliasToBean(ImpsMasterEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public List<ImpsMasterEntity> getImpsMasterCityByDistrict(ImpsMasterEntity impsMasterData) {
		List<ImpsMasterEntity> list = null;
		try {
			String sqlQuery = " select distinct(city) from IMPS_MASTER where district=:district";
			list = getSession().createSQLQuery(sqlQuery).addScalar("city").setParameter("district", impsMasterData.getDistrict())
					.setResultTransformer(Transformers.aliasToBean(ImpsMasterEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public List<ImpsMasterEntity> getImpsMasterDataByCity(ImpsMasterEntity impsMasterData) {
		List<ImpsMasterEntity> list = null;
		try {
			String sqlQuery = "select im.id as id,im.bank as bank,im.ifsc as ifsc,im.branch as branch,im.center as center,im.district as district,"
					+ " im.state as state , im.address as address,im.contact as contact, im.imps as imps, im.rtgs as rtgs,im.city as city,"
					+ " im.neft as neft, im.micr as micr,im.upi as upi,im.createdby as createdby, im.createdon as createdon , im.statusId as statusId, "
					+ "im.updatedby as updatedby, im.updatedon as updatedon from IMPS_MASTER im where im.city=:city";
			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("bank").addScalar("ifsc")
					.addScalar("branch").addScalar("center").addScalar("district").addScalar("state")
					.addScalar("address").addScalar("contact").addScalar("imps").addScalar("rtgs")
					.addScalar("city").addScalar("neft").addScalar("micr").addScalar("upi").addScalar("createdby")
					.addScalar("createdon").addScalar("statusId").addScalar("updatedby").addScalar("updatedon").setParameter("city", impsMasterData.getCity())
					.setResultTransformer(Transformers.aliasToBean(ImpsMasterEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public List<ImpsMasterEntity> getImpsMasterDataByIFSC(ImpsMasterEntity impsMasterData) {
		List<ImpsMasterEntity> list = null;
		try {
			String sqlQuery = "select im.id as id,im.bank as bank,im.ifsc as ifsc,im.branch as branch,im.center as center,im.district as district,"
					+ " im.state as state , im.address as address,im.contact as contact, im.imps as imps, im.rtgs as rtgs,im.city as city,"
					+ " im.neft as neft, im.micr as micr,im.upi as upi,im.createdby as createdby, im.createdon as createdon , im.statusId as statusId, "
					+ "im.updatedby as updatedby, im.updatedon as updatedon from IMPS_MASTER im where im.ifsc=:ifsc";
			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("bank").addScalar("ifsc")
					.addScalar("branch").addScalar("center").addScalar("district").addScalar("state")
					.addScalar("address").addScalar("contact").addScalar("imps").addScalar("rtgs")
					.addScalar("city").addScalar("neft").addScalar("micr").addScalar("upi").addScalar("createdby")
					.addScalar("createdon").addScalar("statusId").addScalar("updatedby").addScalar("updatedon").setParameter("ifsc", impsMasterData.getIfsc())
					.setResultTransformer(Transformers.aliasToBean(ImpsMasterEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	
	

}
