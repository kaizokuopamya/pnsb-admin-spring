package com.itl.pns.dao.impl;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.itl.pns.bean.DeviceMasterDetailsBean;
import com.itl.pns.bean.OfferDetailsBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.bean.TicketBean;
import com.itl.pns.dao.AbstractDao;
import com.itl.pns.dao.OfferDetailsDAO;
import com.itl.pns.entity.OfferDetailsEntity;
import com.itl.pns.util.EncryptDeryptUtility;

@Repository
@Transactional
public class OfferDetailsDAOImpl extends AbstractDao<Integer, OfferDetailsDAO> implements OfferDetailsDAO {

	static Logger LOGGER = Logger.getLogger(OfferDetailsDAOImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<OfferDetailsBean> getOfferDetails() {
		String sqlQuery = "select ofr.ID as id,ofr.weblink as weblink,ofr.servicetype as serviceType,ofr.BASE64IMAGESMALL,ofr.BASE64IMAGELARGE,ofr.IMAGEDESCRIPTIONLARGE as imgdescLarge,ofr.IMAGEDESCRIPTIONSMALL as imgdescSmall,"
				+ " ofr.IMAGECAPTION as imageCaption,ofr.seqnumber,ofr.latitude,ofr.longitude,ofr.validfrom as validFrom,ofr.validto as validTo,ofr.appid,s.name as statusname,a.shortname as productname "
				+ " from OFFERSDETAILS_PRD ofr "
				+ " inner join statusmaster s on ofr.statusid=s.id inner join appmaster a on a.id=ofr.appid  order by ofr.seqnumber ";

		List<OfferDetailsBean> list = getSession().createSQLQuery(sqlQuery).addScalar("ID").addScalar("weblink")
				.addScalar("serviceType").addScalar("base64ImageSmall").addScalar("base64ImageLarge")
				.addScalar("imgdescLarge").addScalar("imgdescSmall").addScalar("imageCaption").addScalar("statusName")
				.addScalar("appId").addScalar("latitude").addScalar("longitude").addScalar("validFrom")
				.addScalar("validTo").addScalar("seqNumber").addScalar("productName")

				.setResultTransformer(Transformers.aliasToBean(OfferDetailsBean.class)).list();

		for (OfferDetailsBean cm : list) {
			try {
				String image2 = EncryptDeryptUtility.clobStringConversion(cm.getBase64ImageSmall());
				cm.setBaseImageSmall(image2);
				cm.setBase64ImageSmall(null);

			} catch (IOException e) {

				LOGGER.info("Exception:", e);
			} catch (SQLException e) {

				LOGGER.info("Exception:", e);
			}
		}

		return list;

	}

	@Override
	public List<DeviceMasterDetailsBean> getDeviceMasterDetails() {
		try {
			String sqlQuery = "select d.ID as id ,d.customerid as customerId,c.customername as customerName ,c.mobile as mobileNumber "
					+ " ,d.imei,d.osversion as osVersion ,s.name as statusDescription,d.statusid as statusId ,d.devicemodel as deviceModel, d.createdby as createdby, um.USERID as createdByName from userdevicesmaster d "
					+ "inner join statusmaster s on s.Id=d.statusid inner join customers c on c.id=d.customerid "
					+ "inner join user_master um on d.createdby = um.id where d.appid in (4,147) order by 1 desc";

			List<DeviceMasterDetailsBean> list = getSession().createSQLQuery(sqlQuery).addScalar("id")
					.addScalar("customerId").addScalar("customerName").addScalar("mobileNumber").addScalar("imei")
					.addScalar("osVersion").addScalar("statusDescription").addScalar("statusId")
					.addScalar("deviceModel").addScalar("createdby").addScalar("createdByName")
					.setResultTransformer(Transformers.aliasToBean(DeviceMasterDetailsBean.class)).list();
			return list;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return null;

		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DeviceMasterDetailsBean> getDeviceMasterDetailsById(int id) {

		try {

			String sqlQuery = "select d.ID as id ,d.customerid as customerId,c.customername as customerName ,c.mobile as mobileNumber ,d.imei,d.osversion as osVersion ,s.name as statusDescription,d.statusid as statusId, d.devicemodel as deviceModel ,aw.remark,aw.userAction from userdevicesmaster d "
					+ "inner join statusmaster s on s.Id=d.statusid "
					+ "inner join customers c on c.id=d.customerid left join ADMINWORKFLOWREQUEST aw on aw.activityrefno=:id and aw.tablename='USERDEVICESMASTER' "
					+ " where d.ID=:id ";

			List<DeviceMasterDetailsBean> list = getSession().createSQLQuery(sqlQuery).addScalar("id")
					.addScalar("customerId").addScalar("customerName").addScalar("mobileNumber").addScalar("imei")
					.addScalar("osVersion").addScalar("statusDescription").addScalar("statusId")
					.addScalar("deviceModel").addScalar("remark").addScalar("userAction").setParameter("id", id)
					.setResultTransformer(Transformers.aliasToBean(DeviceMasterDetailsBean.class)).list();
			return list;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return null;

		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DeviceMasterDetailsBean> getDeviceMasterDetailsByCustId(BigInteger custId) {

		try {

			String sqlQuery = "select d.ID as id ,d.customerid as customerId,c.customername as customerName ,c.mobile as mobileNumber ,d.imei,d.osversion as osVersion ,d.deviceuuid as deviceuuid,"
					+ "d.biometricSupport as biometricSupport,d.imsi as imsi,d.lastUsedOn as lastUsedOn, d.appId, d.updatedOn as updatedOn, d.updatedBy , d.createdOn as createdOn, d.createdBy as createdBy, "
					+ "d.pushNotificationToken as pushNotificationToken,d.macAddress as macAddress ,"
					+ "s.name as statusDescription,d.statusid as statusId, d.devicemodel as deviceModel ,aw.remark,aw.userAction from userdevicesmaster d "
					+ " inner join statusmaster s on s.Id=d.statusid "
					+ " left join customers c on c.id=d.customerid left join ADMINWORKFLOWREQUEST aw on aw.activityrefno=d.id and aw.tablename='USERDEVICESMASTER' "
					+ " where d.customerId=:custId ";

			/*
			 * String sqlQuery =
			 * "select d.ID as id ,d.customerid as customerId,c.customername as customerName ,c.mobile as mobileNumber ,d.imei,d.osversion as osVersion ,s.name as statusDescription,d.statusid as statusId, d.devicemodel as deviceModel ,aw.remark,aw.userAction from userdevicesmaster d "
			 * + "inner join statusmaster s on s.Id=d.statusid " +
			 * "left join customers c on c.id=d.customerid left join ADMINWORKFLOWREQUEST aw on aw.activityrefno=d.id and aw.tablename='USERDEVICESMASTER' "
			 * + " where d.customerId=:custId ";
			 */

			List<DeviceMasterDetailsBean> list = getSession().createSQLQuery(sqlQuery).addScalar("id")
					.addScalar("customerId").addScalar("customerName").addScalar("mobileNumber").addScalar("imei")
					.addScalar("osVersion").addScalar("deviceuuid").addScalar("biometricSupport").addScalar("imsi")
					.addScalar("lastUsedOn").addScalar("appId").addScalar("updatedOn").addScalar("updatedBy")
					.addScalar("createdOn").addScalar("createdBy").addScalar("macAddress")
					.addScalar("statusDescription").addScalar("statusId").addScalar("deviceModel").addScalar("remark")
					.addScalar("userAction").setParameter("custId", custId)
					.setResultTransformer(Transformers.aliasToBean(DeviceMasterDetailsBean.class)).list();
			return list;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return null;

		}
	}

	@Override
	public List<TicketBean> getKycImage(int id) {
		List<TicketBean> list = null;
		try {
			String sqlQuery = "select ck.base64 as BASEIMAGE from CUSTOMERKYCDOCUMENTS_PRD ck where ck.customerid=:custid and ck.statusid=3";
			list = getSession().createSQLQuery(sqlQuery).setParameter("custid", id)
					.setResultTransformer(Transformers.aliasToBean(TicketBean.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return null;

		}
		return list;
	}

	
	@Override
	public ResponseMessageBean isSeqnoExit(OfferDetailsEntity offerDetail) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		try {
			String sqlSeqNoExit = "SELECT count(*) FROM OFFERSDETAILS_PRD WHERE seqNumber =:seqno";
			String sqlNameExit = "SELECT count(*) FROM OFFERSDETAILS_PRD WHERE Lower(IMAGECAPTION) =:imageCaption AND appId=:appId ";

			List isSeqNoExit = getSession().createSQLQuery(sqlSeqNoExit)
					.setParameter("seqno", offerDetail.getSeqNumber()).list();

			List isReportNameExit = getSession().createSQLQuery(sqlNameExit)
					.setParameter("appId", offerDetail.getAppId())
					.setParameter("imageCaption", offerDetail.getImgcaption().toLowerCase()).list();

			if (!(isReportNameExit.get(0).toString().equalsIgnoreCase("0"))) {
				rmb.setResponseCode("452");
				rmb.setResponseMessage("Offer Name Already Exist");
			} else if (!(isSeqNoExit.get(0).toString().equalsIgnoreCase("0"))) {
				rmb.setResponseCode("451");
				rmb.setResponseMessage("Sequence Number Already Exist");
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
	public ResponseMessageBean updateCheckSeqno(OfferDetailsEntity offerDetail) {
		ResponseMessageBean rmb = new ResponseMessageBean();
		try {
			String sqlSeqNoExit = "SELECT count(*) FROM OFFERSDETAILS_PRD WHERE seqNumber =:seqno AND ID !=:id";

			String sqlNameExit = "SELECT count(*) FROM OFFERSDETAILS_PRD WHERE Lower(IMAGECAPTION) =:imageCaption AND appId=:appId AND ID !=:id ";

			List isSeqNoExit = getSession().createSQLQuery(sqlSeqNoExit).setParameter("id", offerDetail.getId())
					.setParameter("seqno", offerDetail.getSeqNumber()).list();

			List isReportNameExit = getSession().createSQLQuery(sqlNameExit).setParameter("id", offerDetail.getId())
					.setParameter("appId", offerDetail.getAppId())
					.setParameter("imageCaption", offerDetail.getImgcaption().toLowerCase()).list();

			if (!(isReportNameExit.get(0).toString().equalsIgnoreCase("0"))) {
				rmb.setResponseCode("452");
				rmb.setResponseMessage("Offer Name Already Exist");
			} else if (!(isSeqNoExit.get(0).toString().equalsIgnoreCase("0"))) {
				rmb.setResponseCode("451");
				rmb.setResponseMessage("Sequence Number Already Exist");
			} else {
				rmb.setResponseCode("200");
				rmb.setResponseMessage("Both not exist");
			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return rmb;
	}

	public List<OfferDetailsEntity> getAllOfferDetails() {
		List<OfferDetailsEntity> list = null;
		try {

			String sqlQuery = "select ofr.ID as id,ofr.weblink as weblink,ofr.servicetype as serviceType,ofr.BASE64IMAGESMALL as baseImageSmall,ofr.BASE64IMAGELARGE as baseImageLarge,ofr.IMAGEDESCRIPTIONLARGE as imgdescLarge,ofr.IMAGEDESCRIPTIONSMALL as imgdescSmall,"
					+ " ofr.IMAGECAPTION as imgcaption,ofr.createdon as createdon,ofr.createdby as createdby,ofr.updatedon as updatedon,ofr.updatedby as updatedby,"
					+ " ofr.seqnumber,ofr.statusId as statusId,ofr.latitude,ofr.longitude,ofr.validfrom as validFrom,ofr.validto as validTo,ofr.appid,s.name as statusname,a.shortname as productname "
					+ " from OFFERSDETAILS_PRD ofr "
					+ " inner join statusmaster s on ofr.statusid=s.id inner join appmaster a on a.id=ofr.appid  order by ofr.ID desc  ";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("weblink").addScalar("serviceType")
					.addScalar("baseImageLarge").addScalar("baseImageSmall").addScalar("imgdescLarge")
					.addScalar("imgdescSmall").addScalar("imgcaption").addScalar("statusName").addScalar("statusId")
					.addScalar("appId").addScalar("updatedby").addScalar("updatedon").addScalar("createdby")
					.addScalar("createdon").addScalar("latitude").addScalar("longitude").addScalar("validFrom")
					.addScalar("validTo").addScalar("seqNumber").addScalar("productName")
					.setResultTransformer(Transformers.aliasToBean(OfferDetailsEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;

	}

}
