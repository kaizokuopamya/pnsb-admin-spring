package com.itl.pns.dao;

import java.util.List;

import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.entity.CitiesMasterEntity;
import com.itl.pns.entity.CountryMasterEntity;
import com.itl.pns.entity.StateMasterEntity;

public interface CountryStateCityDao {


	public List<CountryMasterEntity> getAllCountryDetails();

	public List<CountryMasterEntity> getCountryById(CountryMasterEntity countryData);

	public boolean addCountryDetails(CountryMasterEntity countryData);

	public boolean updateCountryDetails(CountryMasterEntity countryData);
	

	public List<StateMasterEntity> getAllStateDetails();

	public List<StateMasterEntity> getStateById(StateMasterEntity stateData);

	public boolean addStateDetails(StateMasterEntity stateData);

	public boolean updateStateDetails(StateMasterEntity stateData);
	

	public List<CitiesMasterEntity> getAllCitiesDetails();

	public List<CitiesMasterEntity> getCityById(CitiesMasterEntity stateData);

	public boolean addCityDetails(CitiesMasterEntity stateData);

	public boolean updateCityDetails(CitiesMasterEntity stateData);
	
	public ResponseMessageBean isCountryExists(CountryMasterEntity countryData);
	
	public ResponseMessageBean isUpdateCountryExists(CountryMasterEntity countryData);
	
	public ResponseMessageBean isStateExists(StateMasterEntity stateData);
	
	public ResponseMessageBean isUpdateStateExists(StateMasterEntity stateData);
	
	public ResponseMessageBean isCityExists(CitiesMasterEntity stateData);
	
	public ResponseMessageBean isUpdateCityExists(CitiesMasterEntity stateData);

}
