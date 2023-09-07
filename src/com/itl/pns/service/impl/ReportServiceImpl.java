package com.itl.pns.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itl.pns.bean.MonthlyReportData;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.dao.ReportDao;
import com.itl.pns.entity.ReportMsterEntity;
import com.itl.pns.entity.SubReportEntity;
import com.itl.pns.service.ReportService;

@Service
public class ReportServiceImpl implements ReportService {
	
	@Autowired
	ReportDao reportDao;

	@Override
	public List<ReportMsterEntity> getReportDetailsById(int id) {
		return reportDao.getReportDetailsById(id);
	}

	@Override
	public List<ReportMsterEntity> getAllReportDetails() {
		return reportDao.getAllReportDetails();
	}

	@Override
	public boolean addReportDetails(ReportMsterEntity reportMasterDetails) {
		return reportDao.addReportDetails(reportMasterDetails);
	}

	@Override
	public boolean updateReportDetails(ReportMsterEntity reportMasterDetails) {
		return reportDao.updateReportDetails(reportMasterDetails);
	}

	@Override
	public ResponseMessageBean isReportNameExist(ReportMsterEntity reportMasterDetails) {
		return reportDao.isReportNameExist(reportMasterDetails);
	}

	@Override
	public ResponseMessageBean updateReportDetailsExist(ReportMsterEntity reportMasterDetails) {
		return reportDao.updateReportDetailsExist(reportMasterDetails);
	}

	@Override
	public ResponseMessageBean getReportDynamicReports(ReportMsterEntity reportMasterDetails) {
		return reportDao.getReportDynamicReports(reportMasterDetails);
		
	}

	@Override
	public List<SubReportEntity> getSubReportDetailsById(int id) {
		return reportDao.getSubReportDetailsById(id);
	}

	@Override
	public List<SubReportEntity> getAllSubReportDetails() {
		return reportDao.getAllSubReportDetails();
	}

	@Override
	public List<SubReportEntity> getSubReportDetailsByReportId(int id) {
		return reportDao.getSubReportDetailsByReportId(id);
	}

	@Override
	public boolean addSubReportDetails(SubReportEntity reportMasterDetails) {
		return reportDao.addSubReportDetails(reportMasterDetails);
	}

	@Override
	public boolean updateSubReportDetails(SubReportEntity reportMasterDetails) {
		return reportDao.updateSubReportDetails(reportMasterDetails);
	}

	@Override
	public ResponseMessageBean isSubReportNameExist(SubReportEntity reportMasterDetails) {
		return reportDao.isSubReportNameExist(reportMasterDetails);
	}

	@Override
	public ResponseMessageBean updateSubReportNameExist(SubReportEntity reportMasterDetails) {
		return reportDao.updateSubReportNameExist(reportMasterDetails);
	}

	@Override
	public List<MonthlyReportData> getMonthlyReport(MonthlyReportData reportsData) {
		return reportDao.getMonthlyReport(reportsData);
	}

	
}
