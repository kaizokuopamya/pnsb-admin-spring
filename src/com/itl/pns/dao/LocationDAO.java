package com.itl.pns.dao;

import java.util.List;

import com.itl.pns.bean.LocationsBean;
import com.itl.pns.entity.LocationsEntity;

public interface LocationDAO {

	List<LocationsEntity> getLocations();

	List<LocationsEntity> getLocationById(int id);

	List<LocationsBean> getCityNames(int countryid,int stateid);

	List<LocationsBean> getCountryNames();

	List<LocationsEntity> getLocationsTypes();

	List<LocationsBean> getStateNames(int countryid);

}
