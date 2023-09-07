package com.itl.pns.dao.impl;

import java.util.List;
import java.util.logging.LogManager;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.itl.pns.bean.LocationsBean;
import com.itl.pns.dao.LocationDAO;
import com.itl.pns.entity.LocationsEntity;

@Repository("LocationDAO")
@Transactional
public class LocationDAOImpl implements LocationDAO {

	static Logger LOGGER = Logger.getLogger(LocationDAOImpl.class);

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LocationsEntity> getLocations() {
		List<LocationsEntity> list = null;
		try {
			String sqlQuery = "select loc.id,l.name as locationType,loc.locationtypeid,loc.displayName,loc.countryId,loc.stateId,loc.cityId,loc.address ,loc.languageCode,"
					+ "	 loc.latitude,loc.longitude,ap.shortname as appName,loc.appId as appId,loc.phoneNumber,loc.EMAILADDRESS as emailAddress,loc.postCode,loc.branchCode,loc.statusId, loc.CREATEDBY as createdby,"
					+ " con.name as countryName,st.SHORTNAME as stateName,cty.CITYNAME as cityName,s.name as statusName, um.USERID as createdByName from locations loc"
					+ " inner join countrymaster con on con.id=loc.countryid inner join statemaster st  on st.id=loc.stateid"
					+ " inner join appmaster ap  on loc.appid=ap.id left outer join citiesmaster cty on cty.id=loc.cityid"
					+ "	inner join user_master um on loc.createdby = um.id inner join locationtypes l on l.id=loc.locationtypeid "
					+ "	inner join statusmaster s  on loc.statusid=s.id order by loc.id desc";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("locationType")
					.addScalar("locationTypeId").addScalar("displayName").addScalar("countryId").addScalar("stateId")
					.addScalar("cityId").addScalar("address", StandardBasicTypes.STRING).addScalar("languageCode")
					.addScalar("latitude").addScalar("longitude").addScalar("phoneNumber").addScalar("emailAddress")
					.addScalar("postCode").addScalar("branchCode").addScalar("statusId").addScalar("countryName")
					.addScalar("stateName").addScalar("cityName").addScalar("statusName").addScalar("createdby")
					.addScalar("createdByName").addScalar("appName").addScalar("appId")
					.setResultTransformer(Transformers.aliasToBean(LocationsEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LocationsEntity> getLocationsTypes() {
		List<LocationsEntity> list = null;
		try {
			String sqlQuery = "    select l.id as id ,l.name as locationType from locationtypes l ";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("locationType")
					.setResultTransformer(Transformers.aliasToBean(LocationsEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LocationsEntity> getLocationById(int id) {
		List<LocationsEntity> list = null;
		try {
			String sqlQuery = "    select loc.id, loc.locationtypeid,loc.displayName,loc.countryId,loc.stateId,loc.cityId,loc.address,loc.languageCode,"
					+ "  loc.latitude,loc.longitude,loc.phoneNumber,ap.shortname as appName,loc.EMAILADDRESS as emailAddress,loc.postCode,loc.branchCode,loc.statusId,loc.appId as appId,loc.createdon as createdon,"
					+ "  con.name as countryName,st.shortname as stateName,cty.CITYNAME as cityName,s.name as statusName, aw.remark,aw.userAction from locations loc"
					+ "  inner join countrymaster con on con.id=loc.countryid"
					+ "  inner join statemaster st  on st.id=loc.stateid"
					+ "  left outer join citiesmaster cty on cty.id=loc.cityid"
					+ "  inner join statusmaster s  on loc.statusid=s.id "
					+ "  inner join appmaster ap  on loc.appid=ap.id "
					+ " left join ADMINWORKFLOWREQUEST aw on aw.activityrefno=loc.id and aw.tablename='LOCATIONS' "
					+ "  where loc.id=:id";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("locationTypeId")
					.addScalar("displayName").addScalar("countryId").addScalar("stateId").addScalar("cityId")
					.addScalar("address", StandardBasicTypes.STRING).addScalar("languageCode").addScalar("appId")
					.addScalar("latitude").addScalar("longitude").addScalar("phoneNumber").addScalar("emailAddress")
					.addScalar("postCode").addScalar("branchCode").addScalar("statusId").addScalar("countryName")
					.addScalar("appName").addScalar("createdon").addScalar("stateName").addScalar("cityName")
					.addScalar("statusName").addScalar("remark").addScalar("userAction").setParameter("id", id)
					.setResultTransformer(Transformers.aliasToBean(LocationsEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LocationsBean> getStateNames(int countryid) {
		List<LocationsBean> list = null;
		try {
			String sqlQuery = "select id as stateId ,shortname as stateName , statusId as statusId from  statemaster  where countryid=:countryid and statusid=3";
			list = getSession().createSQLQuery(sqlQuery).addScalar("stateId").addScalar("statusId")
					.addScalar("stateName").setParameter("countryid", countryid)
					.setResultTransformer(Transformers.aliasToBean(LocationsBean.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LocationsBean> getCityNames(int countryid, int stateid) {
		List<LocationsBean> list = null;
		try {
			String sqlQuery = "select id as cityId ,CITYNAME as cityName ,statusId as statusId from  citiesmaster where countryid=:countryid and stateid=:stateid";

			list = getSession().createSQLQuery(sqlQuery).addScalar("cityId").addScalar("statusId").addScalar("cityName")
					.setParameter("countryid", countryid).setParameter("stateid", stateid)
					.setResultTransformer(Transformers.aliasToBean(LocationsBean.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LocationsBean> getCountryNames() {
		List<LocationsBean> list = null;
		try {
			String sqlQuery = "select c.id as countryId ,c.name as countryName,c.createdby as createdby, um.USERID as createdByName, c.statusId as statusId   from  countrymaster c inner join user_master um on c.createdby = um.id";
			list = getSession().createSQLQuery(sqlQuery).addScalar("countryId").addScalar("countryName")
					.addScalar("createdby").addScalar("createdByName").addScalar("statusId")
					.setResultTransformer(Transformers.aliasToBean(LocationsBean.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return list;
	}

}
