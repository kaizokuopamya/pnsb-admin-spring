package com.itl.pns.service;

import java.util.List;

import com.itl.pns.bean.LocationsBean;
import com.itl.pns.entity.LocationsEntity;

public interface LocationService {

	List<LocationsEntity> getLocations();

	List<LocationsBean> getCityNames(int countryid,int stateid);

	List<LocationsBean> getCountryNames();
	
	List<LocationsEntity> getLocationsTypes();

	boolean saveLocationDetails(LocationsEntity locations);

	void updateLocationDetails(LocationsEntity locations);

	List<LocationsBean> getStateNames(int countryid);

	List<LocationsEntity> getLocationById(int id);
	public boolean saveLocationToAdminWorkFlow(LocationsEntity locations,int userStatus);



	
}
