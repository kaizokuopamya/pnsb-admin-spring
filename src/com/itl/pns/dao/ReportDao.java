package com.itl.pns.dao;

import java.util.List;

import com.itl.pns.bean.MonthlyReportData;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.entity.ReportMsterEntity;
import com.itl.pns.entity.SubReportEntity;

public interface ReportDao {

	public List<ReportMsterEntity> getReportDetailsById(int id);

	public List<ReportMsterEntity> getAllReportDetails();

	public boolean addReportDetails(ReportMsterEntity reportMasterDetails);

	public boolean updateReportDetails(ReportMsterEntity reportMasterDetails);

	public ResponseMessageBean isReportNameExist(ReportMsterEntity reportMasterDetails);

	public ResponseMessageBean updateReportDetailsExist(ReportMsterEntity reportMasterDetails);

	public ResponseMessageBean getReportDynamicReports(ReportMsterEntity reportMasterDetails);

	public List<SubReportEntity> getSubReportDetailsById(int id);

	public List<SubReportEntity> getAllSubReportDetails();

	public List<SubReportEntity> getSubReportDetailsByReportId(int id);

	public boolean addSubReportDetails(SubReportEntity reportMasterDetails);

	public boolean updateSubReportDetails(SubReportEntity reportMasterDetails);
	
	public ResponseMessageBean isSubReportNameExist(SubReportEntity reportMasterDetails);

	public ResponseMessageBean updateSubReportNameExist(SubReportEntity reportMasterDetails);

	public List<MonthlyReportData> getMonthlyReport(MonthlyReportData reportsData);

}
