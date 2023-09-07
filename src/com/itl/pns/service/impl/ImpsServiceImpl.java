package com.itl.pns.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itl.pns.bean.DateBean;
import com.itl.pns.bean.ImpsOtpLog;
import com.itl.pns.bean.ImpsRevisionBean;
import com.itl.pns.bean.ImpsStationBean;
import com.itl.pns.bean.ImpsTransactionFeeRevisionBean;
import com.itl.pns.bean.ImpsTransactionFeeSetupBean;
import com.itl.pns.bean.TransLogBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.dao.ImpsServiceDao;
import com.itl.pns.entity.ImpsStatusEntity;
import com.itl.pns.entity.SyslogEntity;
import com.itl.pns.impsEntity.CreditPoolAccountConfEntity;
import com.itl.pns.impsEntity.DebitPoolAccountConfEntity;
import com.itl.pns.impsEntity.DeliveryChannelsEntity;
import com.itl.pns.impsEntity.EeuserEntity;
import com.itl.pns.impsEntity.EeuserRolesEntity;
import com.itl.pns.impsEntity.IfscCodeMasterEntity;
import com.itl.pns.impsEntity.ImpsMasterEntity;
import com.itl.pns.impsEntity.ImpsReportEntity;
import com.itl.pns.impsEntity.ImpsRolesEntity;
import com.itl.pns.impsEntity.ImpsTaskEntity;
import com.itl.pns.impsEntity.ImpsTransactionFeeSetUpEntity;
import com.itl.pns.impsEntity.PermissionEntity;
import com.itl.pns.impsEntity.ReportCatrgoryEntity;
import com.itl.pns.impsEntity.RolePermsEntity;
import com.itl.pns.impsEntity.ScheduleEntity;
import com.itl.pns.impsEntity.SmsTemplatesEntity;
import com.itl.pns.impsEntity.SysconfigEntity;
import com.itl.pns.service.ImpsService;

@Service
public class ImpsServiceImpl implements ImpsService {

	static Logger LOGGER = Logger.getLogger(ImpsServiceImpl.class);
	
	@Autowired
	ImpsServiceDao  impsServiceDao;

	@Override
	public List<ImpsStatusEntity> getImpsStatusList() {
		return impsServiceDao.getImpsStatusList();
	}

	@Override
	public boolean updateImpsStatusData(ImpsStatusEntity impsStatusData) {
		return impsServiceDao.updateImpsStatusData(impsStatusData);
	}

	@Override
	public List<ImpsStatusEntity> getImpsStatusById(ImpsStatusEntity impsStatusData) {
		return impsServiceDao.getImpsStatusById(impsStatusData);
	}
	
	@Override
	public boolean deleteImpsStatusById(ImpsStatusEntity impsStatusData) {
		return impsServiceDao.deleteImpsStatusById(impsStatusData);
	}
	
	@Override
	public List<ImpsStatusEntity> getImpsStatusByState(ImpsStatusEntity impsStatusData) {
		return impsServiceDao.getImpsStatusByState(impsStatusData);
	}

	@Override
	public List<SyslogEntity> getSyslogs() {
		return impsServiceDao.getSyslogs();
	}

	@Override
	public ResponseMessageBean getStationCount() {
		return impsServiceDao.getStationCount();
	}

	@Override
	public ResponseMessageBean getTransLogs(DateBean dateObj) {
		return impsServiceDao.getTransLogs(dateObj);
	}

	@Override
	public List<SysconfigEntity> getSysConfigDataById(SysconfigEntity sysConfigReq) {
		return impsServiceDao.getSysConfigDataById(sysConfigReq);
	}

	@Override
	public List<SysconfigEntity> getAllSysConfigData() {
		return impsServiceDao.getAllSysConfigData();
	}

	@Override
	public boolean insertSysConfigData(SysconfigEntity sysConfigReq) {
		return impsServiceDao.insertSysConfigData(sysConfigReq);
	}

	@Override
	public boolean updateSysConfigData(SysconfigEntity sysConfigReq) {
		return impsServiceDao.updateSysConfigData(sysConfigReq);	}

	@Override
	public boolean deleteSysConfigData(SysconfigEntity sysConfigReq) {
		return impsServiceDao.deleteSysConfigData(sysConfigReq);
	}

	@Override
	public List<EeuserRolesEntity> getEeuserDataByEeuser(EeuserRolesEntity eeUserRoleData) {
		return impsServiceDao.getEeuserDataByEeuser(eeUserRoleData);
	}

	@Override
	public boolean insertEeuserRole(EeuserRolesEntity eeUserRoleData) {
		return impsServiceDao.insertEeuserRole(eeUserRoleData);
	}

	@Override
	public boolean updateEeuserData(EeuserEntity eeuserData) {
		return impsServiceDao.updateEeuserData(eeuserData);
	}

	@Override
	public boolean deleteEeuserRole(EeuserRolesEntity eeuserData) {
		return impsServiceDao.deleteEeuserRole(eeuserData);
	}

	@Override
	public List<PermissionEntity> getPermissionByName(PermissionEntity permissionReq) {
		return impsServiceDao.getPermissionByName(permissionReq);
	}

	@Override
	public boolean updatePermission(PermissionEntity permissionReq) {
		return impsServiceDao.updatePermission(permissionReq);
	}


	@Override
	public boolean insertPermission(PermissionEntity permissionReq){
		return impsServiceDao.insertPermission(permissionReq);
	}


	@Override
	public boolean deletePermission(PermissionEntity permissionReq){
		return impsServiceDao.deletePermission(permissionReq);
	}
	
	
	@Override
	public boolean insertRolePerms(RolePermsEntity rolePermsReq) {
		return impsServiceDao.insertRolePerms(rolePermsReq);
	}

	@Override
	public boolean deleteRolePerms(RolePermsEntity rolePermsReq) {
		return impsServiceDao.deleteRolePerms(rolePermsReq);
	}

	@Override
	public List<ImpsRolesEntity> getAllImpsRoles() {
		return impsServiceDao.getAllImpsRoles();
	}

	@Override
	public List<ImpsRolesEntity> getAllImpsRolesById(ImpsRolesEntity impsRolesReq) {
		return impsServiceDao.getAllImpsRolesById(impsRolesReq);
	}

	@Override
	public boolean updateImpsRole(ImpsRolesEntity impsRoleReq) {
		return impsServiceDao.updateImpsRole(impsRoleReq);
	}

	@Override
	public boolean insertImpsRole(ImpsRolesEntity impsRoleReq) {
		return impsServiceDao.insertImpsRole(impsRoleReq);
	}

	@Override
	public boolean deleteImpsRole(ImpsRolesEntity impsRoleReq) {
		return impsServiceDao.deleteImpsRole(impsRoleReq);
	}

	@Override
	public List<ImpsStationBean> getStationsByName(ImpsStationBean impsStationData) {
		return impsServiceDao.getStationsByName(impsStationData);

	}

	@Override
	public List<SmsTemplatesEntity> getSmsTemplates() {
		return impsServiceDao.getSmsTemplates();
	}
	
	@Override
	public List<SmsTemplatesEntity> getSmsTemplatesById(SmsTemplatesEntity smsTempReq) {
		return impsServiceDao.getSmsTemplatesById(smsTempReq);
	}
	
	@Override
	public boolean updateSmsTemplate(SmsTemplatesEntity impsStationData) {
		return impsServiceDao.updateSmsTemplate(impsStationData);
	}

	@Override
	public List<ImpsTransactionFeeSetupBean> getTransFeeSetupByApplyFeeAndTransType(
			ImpsTransactionFeeSetupBean transactionFeeSetupReq) {
		return impsServiceDao.getTransFeeSetupByApplyFeeAndTransType(transactionFeeSetupReq);
	}

	@Override
	public ResponseMessageBean getTransactionFeeRevisionCount(ImpsTransactionFeeRevisionBean transFreeReq) {
		return impsServiceDao.getTransactionFeeRevisionCount(transFreeReq);
	}

	@Override
	public List<IfscCodeMasterEntity> getAllIfscCodeDetails() {
		return impsServiceDao.getAllIfscCodeDetails();
	}
	
	@Override
	public List<IfscCodeMasterEntity> getAllIfscCodeDetailsById(IfscCodeMasterEntity ifscCodeMaster) {
		return impsServiceDao.getAllIfscCodeDetailsById(ifscCodeMaster);
	}
	
	

	@Override
	public boolean insertIfscCode(IfscCodeMasterEntity ifscCodeMaster) {
		return impsServiceDao.insertIfscCode(ifscCodeMaster);
	}

	@Override
	public boolean updateIfscCode(IfscCodeMasterEntity ifscCodeMaster) {
		return impsServiceDao.updateIfscCode(ifscCodeMaster);
	}

	@Override
	public boolean deleteIfscCode(IfscCodeMasterEntity ifscCodeMaster) {
		return impsServiceDao.deleteIfscCode(ifscCodeMaster);
	}

	@Override
	public List<DeliveryChannelsEntity> getAllDeliveryChannelDetails() {
		return impsServiceDao.getAllDeliveryChannelDetails();
	}

	@Override
	public boolean insertDeliveryChannel(DeliveryChannelsEntity deliverChannelReq) {
		return impsServiceDao.insertDeliveryChannel(deliverChannelReq);
	}

	@Override
	public boolean updateDeliveryChannel(DeliveryChannelsEntity deliverChannelReq) {
		return impsServiceDao.updateDeliveryChannel(deliverChannelReq);
	}

	@Override
	public boolean deleteDeliveryChannel(DeliveryChannelsEntity deliverChannelReq) {
		return impsServiceDao.deleteDeliveryChannel(deliverChannelReq);
	}

	@Override
	public List<ImpsRevisionBean> getAllRevisionDetails() {
		return impsServiceDao.getAllRevisionDetails();
	}

	@Override
	public List<CreditPoolAccountConfEntity> getCreditPoolAccDetails() {
		return impsServiceDao.getCreditPoolAccDetails();
	}
	
	@Override
	public List<CreditPoolAccountConfEntity> getCreditPoolAccDetailsById(CreditPoolAccountConfEntity creditPoolAccReq) {
		return impsServiceDao.getCreditPoolAccDetailsById(creditPoolAccReq);
	}

	@Override
	public List<DebitPoolAccountConfEntity> getDebitPoolAccDetailsBYId(DebitPoolAccountConfEntity debitPoolAccReq) {
		return impsServiceDao.getDebitPoolAccDetailsBYId(debitPoolAccReq);
	}
	
	@Override
	public boolean insertCreditPoolAccDetails(CreditPoolAccountConfEntity creditPoolAccReq) {
		return impsServiceDao.insertCreditPoolAccDetails(creditPoolAccReq);
	}

	@Override
	public boolean updateCreditPoolAccDetails(CreditPoolAccountConfEntity creditPoolAccReq) {
		return impsServiceDao.updateCreditPoolAccDetails(creditPoolAccReq);
	}

	@Override
	public boolean deleteCreditPoolAccDetails(CreditPoolAccountConfEntity creditPoolAccReq) {
		return impsServiceDao.deleteCreditPoolAccDetails(creditPoolAccReq);
	}

	@Override
	public List<DebitPoolAccountConfEntity> getDebitPoolAccDetails() {
		return impsServiceDao.getDebitPoolAccDetails();
	}

	@Override
	public boolean insertDebitPoolAccDetails(DebitPoolAccountConfEntity debitPoolAccReq) {
		return impsServiceDao.insertDebitPoolAccDetails(debitPoolAccReq);
	}

	@Override
	public boolean updateDebitPoolAccDetails(DebitPoolAccountConfEntity debitPoolAccReq) {
		return impsServiceDao.updateDebitPoolAccDetails(debitPoolAccReq);
	}

	@Override
	public boolean deleteDebitPoolAccDetails(DebitPoolAccountConfEntity debitPoolAccReq) {
		return impsServiceDao.deleteDebitPoolAccDetails(debitPoolAccReq);
	}

	@Override
	public List<ImpsReportEntity> getAllReports() {
		return impsServiceDao.getAllReports();
	}

	@Override
	public List<ImpsReportEntity> getAllReportsById(ImpsReportEntity impsReportReq) {
		return impsServiceDao.getAllReportsById(impsReportReq);
	}

	@Override
	public boolean insertReportData(ImpsReportEntity impsReportReq) {
		return impsServiceDao.insertReportData(impsReportReq);
	}

	@Override
	public boolean updateReportData(ImpsReportEntity impsReportReq) {
		return impsServiceDao.updateReportData(impsReportReq);
	}

	@Override
	public boolean deleteReportData(ImpsReportEntity impsReportReq) {
		return impsServiceDao.deleteReportData(impsReportReq);
	}

	@Override
	public List<ImpsTaskEntity> getAllTasks() {
		return impsServiceDao.getAllTasks();
	}

	@Override
	public List<ImpsTaskEntity> getAllTasksById(ImpsTaskEntity taskReq) {
		return impsServiceDao.getAllTasksById(taskReq);
	}

	@Override
	public boolean inserTaskData(ImpsTaskEntity taskReq) {
		return impsServiceDao.inserTaskData(taskReq);
	}

	@Override
	public boolean updateTaskData(ImpsTaskEntity taskReq) {
		return impsServiceDao.updateTaskData(taskReq);
	}

	@Override
	public boolean deleteTaskData(ImpsTaskEntity taskReq) {
		return impsServiceDao.deleteTaskData(taskReq);
	}

	@Override
	public List<ScheduleEntity> getScheduleData() {
		return impsServiceDao.getScheduleData();
	}

	@Override
	public List<ScheduleEntity> getScheduleDataById(ScheduleEntity scheduleReq) {
		return impsServiceDao.getScheduleDataById(scheduleReq);
	}

	@Override
	public boolean insertScheduleData(ScheduleEntity scheduleReq) {
		return impsServiceDao.insertScheduleData(scheduleReq);
	}

	@Override
	public boolean updateScheduleData(ScheduleEntity scheduleReq) {
		return impsServiceDao.updateScheduleData(scheduleReq);
	}

	@Override
	public boolean deleteScheduleData(ScheduleEntity scheduleReq) {
		return impsServiceDao.deleteScheduleData(scheduleReq);
	}

	@Override
	public List<ReportCatrgoryEntity> getReportCategory() {
		return impsServiceDao.getReportCategory();
	}

	@Override
	public List<ReportCatrgoryEntity> getReportCategoryById(ReportCatrgoryEntity reportCatReq) {
		return impsServiceDao.getReportCategoryById(reportCatReq);
	}

	@Override
	public boolean insertReportCategory(ReportCatrgoryEntity reportCatReq) {
		return impsServiceDao.insertReportCategory(reportCatReq);
	}

	@Override
	public boolean updateReportCategory(ReportCatrgoryEntity reportCatReq) {
		return impsServiceDao.updateReportCategory(reportCatReq);
	}

	@Override
	public boolean deleteReportCategory(ReportCatrgoryEntity reportCatReq) {
		return impsServiceDao.deleteReportCategory(reportCatReq);
	}

	@Override
	public List<DeliveryChannelsEntity> getAllDeliveryChannelDetailsById(DeliveryChannelsEntity deliveryChannelsReq) {
		return impsServiceDao.getAllDeliveryChannelDetailsById(deliveryChannelsReq);
	}

	@Override
	public ResponseMessageBean getImpsTransLogByRRN(TransLogBean transLogData) {
		return impsServiceDao.getImpsTransLogByRRN(transLogData);
	}

	@Override
	public List<ImpsOtpLog> getOtpLogsDetails(ImpsOtpLog otpLogReq) {
		return impsServiceDao.getOtpLogsDetails(otpLogReq);
	}

	@Override
	public List<ImpsTransactionFeeSetupBean> getTransFeeSetupByApplyFeeAndTransTypeById(
			ImpsTransactionFeeSetupBean transactionFeeSetupReq) {
		return impsServiceDao.getTransFeeSetupByApplyFeeAndTransTypeById(transactionFeeSetupReq);
	}

	@Override
	public boolean updateTransFeeSetup(ImpsTransactionFeeSetUpEntity transactionFeeSetupReq) {
		return impsServiceDao.updateTransFeeSetup(transactionFeeSetupReq);
	}

	@Override
	public boolean insertImpsMasterDetails(ImpsMasterEntity impsMasterData) {
		return impsServiceDao.insertImpsMasterDetails(impsMasterData);
	}

	@Override
	public boolean updateImpsMasterDetails(ImpsMasterEntity impsMasterData) {
		return impsServiceDao.updateImpsMasterDetails(impsMasterData);
	}

	@Override
	public List<ImpsMasterEntity> getImpsMasterDetails() {
		return impsServiceDao.getImpsMasterDetails();
	}

	@Override
	public List<ImpsMasterEntity> getImpsMasterDetailsById(ImpsMasterEntity impsMasterData) {
		return impsServiceDao.getImpsMasterDetailsById(impsMasterData);
	}
	


}
