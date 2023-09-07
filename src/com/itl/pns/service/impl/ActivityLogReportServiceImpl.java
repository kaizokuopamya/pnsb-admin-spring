package com.itl.pns.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itl.pns.dao.ActivityLogReportDao;
import com.itl.pns.entity.ActivityLogReport;
import com.itl.pns.service.ActivityLogReportService;

@Service
public class ActivityLogReportServiceImpl implements ActivityLogReportService{
	
	@Autowired
	ActivityLogReportDao activityLogReportDao;

	@Override
	public List<ActivityLogReport> getActivityLogReport(ActivityLogReport activityLogReport) {
		return activityLogReportDao.getActivityLogReport(activityLogReport);
	}

}
