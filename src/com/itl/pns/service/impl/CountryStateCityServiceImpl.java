package com.itl.pns.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itl.pns.dao.CountryStateCityDao;
import com.itl.pns.entity.CitiesMasterEntity;
import com.itl.pns.entity.CountryMasterEntity;
import com.itl.pns.entity.StateMasterEntity;
import com.itl.pns.service.CountryStateCityService;

@Service
public class CountryStateCityServiceImpl implements CountryStateCityService {

static Logger LOGGER = Logger.getLogger(CountryStateCityServiceImpl.class);
	
	@Autowired
	CountryStateCityDao countryStateCityDao;
	
	@Override
	public List<CountryMasterEntity> getAllCountryDetails() {
		return countryStateCityDao.getAllCountryDetails();
	}

	@Override
	public List<CountryMasterEntity> getCountryById(CountryMasterEntity countryData) {
		return countryStateCityDao.getCountryById(countryData);
	}

	@Override
	public boolean addCountryDetails(CountryMasterEntity countryData) {
		return countryStateCityDao.addCountryDetails(countryData);
	}

	@Override
	public boolean updateCountryDetails(CountryMasterEntity countryData) {
		return countryStateCityDao.updateCountryDetails(countryData);
	}

	@Override
	public List<StateMasterEntity> getAllStateDetails() {
		return countryStateCityDao.getAllStateDetails();
	}

	@Override
	public List<StateMasterEntity> getStateById(StateMasterEntity stateData) {
		return countryStateCityDao.getStateById(stateData);
	}

	@Override
	public boolean addStateDetails(StateMasterEntity stateData) {
		return countryStateCityDao.addStateDetails(stateData);
	}

	@Override
	public boolean updateStateDetails(StateMasterEntity stateData) {
		return countryStateCityDao.updateStateDetails(stateData);
	}

	@Override
	public List<CitiesMasterEntity> getAllCitiesDetails() {
		return countryStateCityDao.getAllCitiesDetails();
	}

	@Override
	public List<CitiesMasterEntity> getCityById(CitiesMasterEntity stateData) {
		return countryStateCityDao.getCityById(stateData);
	}

	@Override
	public boolean addCityDetails(CitiesMasterEntity stateData) {
		return countryStateCityDao.addCityDetails(stateData);
	}

	@Override
	public boolean updateCityDetails(CitiesMasterEntity stateData) {
		return countryStateCityDao.updateCityDetails(stateData);
	}

}
