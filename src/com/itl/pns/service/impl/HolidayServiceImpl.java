package com.itl.pns.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.dao.HolidayDao;
import com.itl.pns.entity.HolidayEntity;
import com.itl.pns.service.HolidayService;

/**
 * @author shubham.lokhande
 *
 */
@Service
public class HolidayServiceImpl implements HolidayService {

	static Logger LOGGER = Logger.getLogger(HolidayServiceImpl.class);

	@Autowired
	HolidayDao holidayDao;

	@Override
	public List<HolidayEntity> getHolidayDetailsById(int id) {
		return holidayDao.getHolidayDetailsById(id);
	}
	
	@Override
	public List<HolidayEntity> getHolidayDetailsByState(String state) {
		return holidayDao.getHolidayDetailsByState(state);
	}

	@Override
	public List<HolidayEntity> getHolidayDetails() {
		return holidayDao.getHolidayDetails();
	}

	@Override
	public boolean addHolidayDetails(HolidayEntity holidayData) {
		return holidayDao.addHolidayDetails(holidayData);
	}

	@Override
	public boolean updateHolidayDetails(HolidayEntity holidayData) {
		return holidayDao.updateHolidayDetails(holidayData);
	}

	@Override
	public ResponseMessageBean isHolidayExist(HolidayEntity holidayData) {
		return holidayDao.isHolidayExist(holidayData);
	}

	@Override
	public ResponseMessageBean updateHolidayExist(HolidayEntity holidayData) {
		return holidayDao.updateHolidayExist(holidayData);
	}

	@Override
	public List<HolidayEntity> getHolidayDetailsByHolidayName(String holidayNme) {
		return holidayDao.getHolidayDetailsByHolidayName(holidayNme);
	}

	@Override
	public boolean addBulkHolidayDetails(List<HolidayEntity> holidayData) {
		return holidayDao.addBulkHolidayDetails(holidayData);
	}


	
}
