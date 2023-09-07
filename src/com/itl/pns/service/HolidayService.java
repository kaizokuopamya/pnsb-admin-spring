package com.itl.pns.service;

import java.util.List;

import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.entity.HolidayEntity;

/**
 * @author shubham.lokhande
 *
 */
public interface HolidayService {
	
	public List<HolidayEntity> getHolidayDetailsById(int id);
	
	public List<HolidayEntity> getHolidayDetailsByState(String state);
	
	public List<HolidayEntity> getHolidayDetailsByHolidayName(String holidayNme);

	public List<HolidayEntity> getHolidayDetails();
	

	
	public boolean addHolidayDetails(HolidayEntity holidayData);

	public boolean updateHolidayDetails (HolidayEntity holidayData);
	
	public ResponseMessageBean isHolidayExist(HolidayEntity holidayData);
	
	public ResponseMessageBean updateHolidayExist (HolidayEntity holidayData);
	
	
	public boolean addBulkHolidayDetails(List<HolidayEntity> holidayData);
	
}
