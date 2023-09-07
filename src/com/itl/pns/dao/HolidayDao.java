package com.itl.pns.dao;

import java.util.List;

import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.entity.HolidayEntity;

public interface HolidayDao {

	public List<HolidayEntity> getHolidayDetailsById(int id);
	
	public List<HolidayEntity> getHolidayDetailsByState(String state);
	
	public List<HolidayEntity> getHolidayDetailsByHolidayName(String holidayName);

	public List<HolidayEntity> getHolidayDetails();
	
	
	
	public boolean addHolidayDetails(HolidayEntity holidayData);

	public boolean updateHolidayDetails (HolidayEntity holidayData);
	
	public ResponseMessageBean isHolidayExist(HolidayEntity holidayData);
	
	public ResponseMessageBean updateHolidayExist (HolidayEntity holidayData);
	
	
	public ResponseMessageBean isHolidayExistForState(HolidayEntity holidayData);
	
	
	
	public boolean addBulkHolidayDetails(List<HolidayEntity> holidayData);
}


