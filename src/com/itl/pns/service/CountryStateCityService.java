package com.itl.pns.service;

import java.util.List;

import com.itl.pns.entity.CitiesMasterEntity;
import com.itl.pns.entity.CountryMasterEntity;
import com.itl.pns.entity.StateMasterEntity;

public interface CountryStateCityService {

	
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
	
	
	
	
}
